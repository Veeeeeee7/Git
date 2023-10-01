import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tree {

    private File f;
    private boolean filled; // returns false if f has no entries
    private ArrayList<String> content;

    public Tree() throws URISyntaxException {
        content = new ArrayList<>();
        this.f = new File("Tree");
        if (Utils.fileExists("Tree")) {
            Utils.deleteFile("Tree");
        }
        filled = false;
    }

    public void add(String str) throws FileNotFoundException, IOException {
        content.add(str);
        FileWriter fw = new FileWriter("Tree", true);
        if (filled) {
            String s = '\n' + str;
            fw.write(s);
        } else {
            fw.write(str);
            filled = true;
        }
        fw.close();
    }

    public void remove(String sha1) throws FileNotFoundException, IOException {
        for (int i = content.size() - 1; i >= 0; i--) {
            if (content.get(i).contains(sha1)) {
                content.remove(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Tree"));
        String filename = "";

        while (br.ready()) {
            String line = br.readLine();
            int i = line.indexOf(":");
            String check = "";
            if (i != 0)
                check = line.substring(i + 2, i + 42);
            if (!check.equals(sha1)) {
                if (sb.toString().equals(""))
                    sb.append(line);
                else {
                    String s = '\n' + line;
                    sb.append(s);
                }
            } else {
                filename = line.substring(i + 44);
            }

        }
        PrintWriter pw = new PrintWriter("Tree");
        pw.print(sb);
        pw.close();
        br.close();
    }

    public void finalize() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s + "\n");
        }
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        Utils.deleteFile("Tree");
        f.createNewFile();
        Utils.writeStringToFile("Tree", sb.toString());
        Blob blob = new Blob(f);
        blob.createBlob();
    }

    public String getSHA1() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s + "\n");
        }
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return Utils.sha1(sb.toString());
    }

    public String addDirectory(String folderName) throws Exception {
        if (!Utils.fileExists(folderName)) {
            throw new Exception("Folder does not exist");
        }

        Tree t = searchDirectory(folderName);
        t.finalize();
        return t.getSHA1();
    }

    private Tree searchDirectory(String folderName) throws Exception {
        File folder = new File(folderName);
        File[] files = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                // System.out.println(pathname.getName() + ":" + pathname.isDirectory());
                return !pathname.isHidden() && !pathname.isDirectory();
            }
        });

        ArrayList<String> content = new ArrayList<>();
        for (File f : files) {
            Blob b = new Blob(f);
            b.createBlob();
            content.add("blob : " + b.hash + " : " + b.file.getName());
        }

        File[] folders = folder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                // System.out.println(pathname.getName() + ":" + pathname.isDirectory());
                return !pathname.isHidden() && pathname.isDirectory();
            }
        });

        for (File f : folders) {
            Tree t = searchDirectory(folderName + "/" + f.getName());
            t.finalize();
            content.add("tree : " + t.getSHA1() + " : " + f.getName());
        }

        Tree t = new Tree();
        for (String s : content) {
            t.add(s);
        }
        t.finalize();

        return t;
    }
}