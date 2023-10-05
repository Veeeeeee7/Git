import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class Commit {

    private String treeSHA1;
    private String parentCommit;
    private String nextCommit;
    private String summary;
    private String author;
    private String fileHash;
    private ArrayList<String> oldFiles;
    private ArrayList<String> filesToRemove;
    private ArrayList<String> filesToEdit;

    public Commit(String parentCommit, String author, String summary) throws Exception {
        this.parentCommit = parentCommit;
        this.summary = summary;
        this.author = author;
        this.oldFiles = new ArrayList<>();
        this.filesToRemove = new ArrayList<>();
        this.filesToEdit = new ArrayList<>();
        createTree();
    }

    public String createCommit() throws Exception {
        if (!filesToRemove.isEmpty() || !filesToEdit.isEmpty()) {
            for (String file : filesToRemove) {
                deleteFile(file);
                Tree t = new Tree();
                for (String line : oldFiles) {
                    t.add(line);
                    System.out.println(line);
                }
                t.finalize();
                Utils.deleteFile("Objects/" + treeSHA1);
                treeSHA1 = t.getSHA1();
                System.out.println(treeSHA1);
                System.out.println("\n\n");
            }
            for (String file : filesToEdit) {

                editFile(file);
                Tree t = new Tree();
                for (String line : oldFiles) {
                    t.add(line);
                    System.out.println(line);
                }
                t.finalize();
                Utils.deleteFile("Objects/" + treeSHA1);
                treeSHA1 = t.getSHA1();
                System.out.println(treeSHA1);
                System.out.println("\n\n");
            }
        }
        createFile();
        File file = new File("temp");
        File path = new File("Objects");
        path.mkdirs();

        // if (nextCommit.length() > 0) {
        // String tempContents = FileUtils.readFile("temp");
        // int index = 0;
        // for (int i = 0; i < 3; i++) {
        // index = tempContents.indexOf("\n");
        // }
        // String before = tempContents.substring(0, index + 1);
        // String after = tempContents.substring(index + 1, tempContents.length());
        // FileUtils.writeFile("temp", before + nextCommit + "\n" + after);
        // }

        Blob blob = new Blob(file);
        String hash = blob.createBlob();
        fileHash = hash;
        file.delete();
        return hash;
    }

    private void editFile(String fileName) throws Exception {
        deleteFile(fileName);
        File f = new File(fileName);
        Blob b = new Blob(f);
        b.createBlob();
        oldFiles.add("blob : " + b.hash + " : " + fileName);
    }

    private void deleteFile(String fileName) throws Exception {
        oldFiles = new ArrayList<>();
        String treeHash = treeSHA1;
        // System.out.println(treeHash);
        findTree(treeHash, fileName);
    }

    private String findTree(String treeHash, String fileName) throws Exception {
        String treeContents = Utils.writeFileToString("Objects/" + treeHash);
        String[] treeLines = treeContents.split("\n");
        String lastTreeHash = "";
        String previousTree = "";
        for (int i = 0; i < treeLines.length; i++) {
            // if the line is a blob
            if (treeLines[i].startsWith("blob")) {
                if (treeLines[i].substring(50).equals(fileName)) {
                    for (int j = i + 1; j < treeLines.length; j++) {
                        oldFiles.add(treeLines[j]);
                    }
                    // if (previousTree.length() > 0) {
                    // oldFiles.add(previousTree);
                    // }
                    return treeHash;
                }
                oldFiles.add(treeLines[i]);
            }
            // if the line is a tree and is a directory and not the previous commits tree
            else if (treeLines[i].startsWith("tree") && (treeLines[i].length() > 48)) {
                lastTreeHash = findTree(treeLines[i].substring(7, treeLines[i].substring(7).indexOf(" ") + 7),
                        fileName);
                if (lastTreeHash.equals("")) {
                    oldFiles.add(treeLines[i]);
                }
            }
            // if the line is not a blob (thus a tree) and the directory is the one to be
            // deleted
            else if ((treeLines[i].length() > 48) && treeLines[i].substring(50).equals(fileName)) {
                for (int j = i + 1; j < treeLines.length; j++) {
                    oldFiles.add(treeLines[j]);
                }
                return treeHash;
            }
            // if none of the above, it is the previous tree
            else {
                // lastTreeHash = findTree(treeLines[i].substring(7), fileName);
                // if (lastTreeHash.equals("")) {
                // oldFiles.add(treeLines[i]);
                // }
                // previousTree = treeLines[i].substring(7);
            }
        }
        return lastTreeHash;
    }

    public void setNextCommit(String next) throws Exception {
        nextCommit = next;
        String content = getContentWithThirdLine();
        Utils.deleteFile("objects/" + fileHash);
        File f = new File("objects/" + fileHash);
        f.createNewFile();
        Utils.writeStringToFile("objects/" + fileHash, content);
    }

    private File createFile() throws IOException {
        File file = new File("temp");
        PrintWriter pw = new PrintWriter(new FileWriter("temp", false));
        pw.print(getContentWithoutThirdLine());
        pw.close();
        return file;
    }

    private void createTree() throws Exception {
        Tree tree = new Tree();
        String indexContent = Utils.writeFileToString("Index");
        String[] content = indexContent.split("\n");
        for (int i = 0; i < content.length; i++) {
            // System.out.println(content[i]);
            if (content[i].startsWith("*deleted*")) {
                Index index = new Index();
                index.remove(content[i].substring(10));
                filesToRemove.add(content[i].substring(10));
            } else if (content[i].startsWith("*edited*")) {
                Index index = new Index();
                index.remove(content[i].substring(9));
                filesToEdit.add(content[i].substring(9));
            } else {
                tree.add(content[i]);
            }
        }
        if (parentCommit.length() > 0) {
            String parentContent = Utils.writeFileToString("Objects/" + parentCommit);
            String parentTree = parentContent.substring(0, parentContent.indexOf("\n"));
            tree.add("tree : " + parentTree);
        }
        tree.finalize();
        Index.clearContent();
        treeSHA1 = tree.getSHA1();
    }

    private String getContentWithoutThirdLine() {
        String content = "";
        content += treeSHA1 + "\n";
        content += parentCommit + "\n";
        content += "\n";
        content += author + "\n";
        content += getDate() + "\n";
        content += summary;
        return content;
    }

    private String getContentWithThirdLine() {
        String content = "";
        content += treeSHA1 + "\n";
        content += parentCommit + "\n";
        content += nextCommit + "\n";
        content += author + "\n";
        content += getDate() + "\n";
        content += summary;
        return content;
    }

    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        // System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }

    public String getTreeHash() {
        return treeSHA1;
    }
}
