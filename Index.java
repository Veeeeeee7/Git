import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Index {
    // protected ArrayList<String> fileNames;
    // protected HashSet<String> index;
    protected StringBuilder content = new StringBuilder();

    public Index() {
        // index = new HashSet<String>();
        // fileNames = new ArrayList<>();
        // File folder = new File(".");
        // fileNames = folder.list(new FilenameFilter() {
        // @Override
        // public boolean accept(File file, String name) {
        // File currentFile = new File(name);
        // return !currentFile.isHidden();
        // }
        // });
    }

    public void init() {
        Utils.deleteFile("Index");
        Utils.createFile("Index");
        File objects = new File("Objects");
        objects.mkdir();
        // if (index.isEmpty()) {
        // return;
        // }
        // for (String f : fileNames) {
        // File file = new File(f);
        // Blob blob = new Blob(file);
        // String hash = blob.hash;
        // blob.createBlob();
        // index.put(hash, f);
        // }

        // try {
        // File indexFile = new File("Index");
        // // if (!indexFile.exists()) {
        // // indexFile.createNewFile();
        // // }
        // FileWriter writer = new FileWriter(indexFile);
        // // StringBuilder str = new StringBuilder();
        // // for (String hash : this.index.keySet()) {
        // // content.append(hash + " : ");
        // // content.append(index.get(hash));
        // // content.delete(content.length() - 2, content.length());
        // // content.append("\n");
        // // }
        // // content.deleteCharAt(content.length() - 1);

        // writer.write(content.toString());
        // writer.close();
        // } catch (Exception e) {
        // System.out.println("cant create Index");
        // }
    }

    public static void clearContent() {
        // index = new HashMap<String, String>();
        // fileNames = new ArrayList<>();
        // content = new StringBuilder();
        Utils.deleteFile("Index");
        Utils.createFile("Index");
    }

    public void remove(String fileName) throws IOException {
        // fileNames.remove(fileName);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Index"));
        // String filename = "";

        while (br.ready()) {
            String line = br.readLine();
            // String check = "";
            // check = line.substring(50);
            // if (!check.equals(fileName)) {
            // if (sb.toString().equals(""))
            // sb.append(line);
            // else {
            // String s = '\n' + line;
            // sb.append(s);
            // }
            // }
            if (!(line + "\n").contains(" " + fileName + "\n")) {
                if (sb.toString().equals(""))
                    sb.append(line);
                else {
                    String s = '\n' + line;
                    sb.append(s);
                }
            }
        }
        PrintWriter pw = new PrintWriter("Index");
        pw.print(sb);
        pw.close();
        br.close();
    }

    public void deleteFile(String fileName) throws Exception {
        updateContent();
        if (!content.isEmpty()) {
            content.append("\n");
        }
        content.append("*deleted* " + fileName);
        Utils.writeStringToFile("Index", content.toString());
    }

    public void editFile(String fileName) throws Exception {
        updateContent();
        if (!content.isEmpty()) {
            content.append("\n");
        }
        content.append("*edited* " + fileName);
        Utils.writeStringToFile("Index", content.toString());
    }

    public void add(String fileName) throws Exception {
        // fileNames.add(fileName);
        updateContent();
        String temp = content.toString() + "\n";
        if (temp.contains(fileName + "\n")) {
            remove(fileName);
            add(fileName);
            return;
        }

        File f = new File(fileName);
        if (f.isFile()) {
            addFile(fileName);
        } else if (f.isDirectory()) {
            addDirectory(fileName);
        }
    }

    private void addFile(String fileName) throws IOException {
        // fileNames.add(fileName);
        File file = new File(fileName);
        Blob blob = new Blob(file);
        String hash = blob.hash;
        blob.createBlob();
        // if (index.containsKey(hash)) {
        // index.get(hash).push(fileName);
        // content.insert(content.indexOf(index.get(hash).peek()), " , " + fileName);
        // } else {
        // Stack<String> stack = new Stack<String>();
        // stack.push(fileName);
        // index.put(hash, stack);
        // content.append(hash + " : " + fileName + "\n");
        // }
        // index.put(hash, fileName);

        if (!content.isEmpty()) {
            content.append("\n");
        }
        content.append("blob : " + hash + " : " + fileName);

        try {
            File indexFile = new File("Index");
            FileWriter writer = new FileWriter(indexFile);
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index");
        }
        // removeNewLine();
    }

    private void addDirectory(String fileName) throws Exception {
        Tree t = new Tree();
        String treeHash = t.addDirectory(fileName);
        if (!content.isEmpty()) {
            content.append("\n");
        }
        content.append("tree : " + treeHash + " : " + fileName);
        try {
            File indexFile = new File("Index");
            FileWriter writer = new FileWriter(indexFile);
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index");
        }
        // removeNewLine();
    }

    // public void removeNewLine() throws IOException {
    // StringBuilder sb = new StringBuilder();
    // BufferedReader br = new BufferedReader(new FileReader("Index"));

    // while (br.ready()) {
    // char c = (char) br.read();
    // if (br.ready())
    // sb.append(c);
    // else
    // break;
    // }

    // FileWriter fw = new FileWriter("Index");
    // fw.write(sb.toString());
    // fw.close();
    // }

    private void updateContent() throws Exception {
        String contents = Utils.writeFileToString("Index");
        String[] lines = contents.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            sb.append(lines[i]);
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        content = sb;
    }

    // public void removeIndex() throws IOException {
    // try {
    // FileWriter writer = new FileWriter("Index");
    // // StringBuilder str = new StringBuilder();
    // for (String hash : this.index.keySet()) {
    // content.append(hash + " : ");
    // for (String fileName : this.index.get(hash)) {
    // content.append(fileName + ", ");
    // }
    // content.delete(content.length() - 2, content.length());
    // content.append("\n");
    // }
    // writer.write(content.toString());
    // writer.close();
    // } catch (Exception e) {
    // System.out.println("cant create Index");
    // }

    // }
}
