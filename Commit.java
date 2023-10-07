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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private int layer;
    private ArrayList<String> directory;

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

    public void checkout() throws Exception {
        directory = new ArrayList<>();
        HashMap<String, String> treeResult = checkoutTraverse(treeSHA1);
        for (String key : treeResult.keySet()) {
            // System.out.println(key + " : " + treeResult.get(key));
            Utils.writeStringToFile(treeResult.get(key), Utils.writeFileToString("Objects/" + key));
        }
    }

    private HashMap<String, String> checkoutTraverse(String treeHash) throws Exception {
        String[] lines = Utils.writeFileToString("Objects/" + treeHash).split("\n");
        HashMap<String, String> result = new HashMap<>();

        for (String line : lines) {
            System.out.println(line);
            StringBuilder path = new StringBuilder();
            for (String folder : directory) {
                path.append(folder);
            }

            if (line.startsWith("blob")) {
                result.put(line.substring(7, 47), path + line.substring(50));
            } else if (line.length() < 50) {
                HashMap<String, String> treeResult = checkoutTraverse(line.substring(7));
                for (String key : treeResult.keySet()) {
                    result.put(key, path + treeResult.get(key));
                }
            } else {
                directory.add(line.substring(50) + "/");
                HashMap<String, String> treeResult = checkoutTraverse(line.substring(7, 47));
                for (String key : treeResult.keySet()) {
                    result.put(key, treeResult.get(key));
                }
                directory.remove(directory.size() - 1);
            }
        }
        return result;
    }

    public String traverse() throws Exception {
        layer = 0;
        ArrayList<String> content = traverseTree(treeSHA1);
        StringBuilder result = new StringBuilder();
        for (String line : content) {
            result.append(line + "\n");
        }
        return result.toString();
    }

    private ArrayList<String> traverseTree(String treeHash) throws Exception {
        layer++;
        String[] lines = Utils.writeFileToString("Objects/" + treeHash).split("\n");
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            StringBuilder tabs = new StringBuilder();
            for (int i = 1; i < layer; i++) {
                if (i == layer - 1) {
                    tabs.append("| - - - - ");
                } else {
                    tabs.append(".          ");
                }
            }
            // System.out.println(tabs.toString() + line);
            if (line.length() < 50) {
                layer--;
                result.addAll(traverseTree(line.substring(7, 47)));
            } else if (line.startsWith("tree")) {
                result.add(tabs.toString() + line.substring(50) + "/");
                result.addAll(traverseTree(line.substring(7, 47)));
                layer--;

            } else {
                result.add(tabs.toString() + line.substring(50));
            }
        }
        // layer--;
        return result;
    }

    private void editFile(String fileName) throws Exception {
        deleteFile(fileName);
        File f = new File(fileName);
        Blob b = new Blob(f);
        b.createBlob();
        oldFiles.add(0, "blob : " + b.hash + " : " + fileName);
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
        Arrays.sort(treeLines);
        for (int i = 0; i < treeLines.length; i++) {
            if (treeLines[i].length() == 47) {
                String temp = treeLines[i];
                treeLines[i] = treeLines[treeLines.length - 1];
                treeLines[treeLines.length - 1] = temp;
                break;
            }
        }
        String lastTreeHash = "";
        // String previousTree = "";
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
            // if the line is not a blob (thus a tree) and the directory is the one to be
            // deleted
            else if ((treeLines[i].length() > 48) && treeLines[i].substring(50).equals(fileName)) {
                for (int j = i + 1; j < treeLines.length; j++) {
                    oldFiles.add(treeLines[j]);
                }
                return treeHash;
            }
            // if the line is a tree and is a directory and not the previous commits tree
            else if (treeLines[i].startsWith("tree") && (treeLines[i].length() > 48)) {
                lastTreeHash = findTree(treeLines[i].substring(7, treeLines[i].substring(7).indexOf(" ") + 7),
                        fileName);
                if (lastTreeHash.equals("")) {
                    oldFiles.add(treeLines[i]);
                }
            }
            // if none of the above, it is the previous tree
            else {
                lastTreeHash = findTree(treeLines[i].substring(7), fileName);
                if (lastTreeHash.equals("")) {
                    oldFiles.add(treeLines[i]);
                }
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
