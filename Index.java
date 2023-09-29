import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

public class Index {
    protected String[] fileNames;
    protected HashMap<String, Stack<String>> index;
    protected StringBuilder content = new StringBuilder();

    public Index() {
        index = new HashMap<String, Stack<String>>();
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
        File objects = new File("Objects");
        objects.mkdir();
        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            Blob blob = new Blob(file);
            String hash = blob.hash;
            blob.createBlob();
            if (index.containsKey(hash)) {
                index.get(hash).push(fileNames[i]);
            } else {
                Stack<String> stack = new Stack<String>();
                stack.push(fileNames[i]);
                index.put(hash, stack);
            }
        }

        try {
            File indexFile = new File("Index.txt");
            if (!indexFile.exists()) {
                indexFile.createNewFile();
            }
            FileWriter writer = new FileWriter(indexFile);
            // StringBuilder str = new StringBuilder();
            for (String hash : this.index.keySet()) {
                content.append(hash + " : ");
                for (String fileName : this.index.get(hash)) {
                    content.append(fileName + ", ");
                }
                content.delete(content.length() - 2, content.length());
                content.append("\n");
            }
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index.txt");
        }
    }

    public void remove(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Index.txt"));
        String filename = "";

        while (br.ready()) {
            String line = br.readLine();
            String check = "";
            check = line.substring(43);
            if (!check.equals(fileName)) {
                if (sb.toString().equals(""))
                    sb.append(line);
                else {
                    String s = '\n' + line;
                    sb.append(s);
                }
            }
        }
        PrintWriter pw = new PrintWriter("Index.txt");
        pw.print(sb);
        pw.close();
        br.close();
    }

    public void add(String fileName) throws IOException {
        File file = new File(fileName);
        Blob blob = new Blob(file);
        String hash = blob.hash;
        blob.createBlob();
        if (index.containsKey(hash)) {
            index.get(hash).push(fileName);
            content.insert(content.indexOf(index.get(hash).peek()), " , " + fileName);
        } else {
            Stack<String> stack = new Stack<String>();
            stack.push(fileName);
            index.put(hash, stack);
            content.append(hash + " : " + fileName + "\n");
        }

        try {
            File indexFile = new File("Index.txt");
            FileWriter writer = new FileWriter(indexFile);
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index.txt");
        }
        removeNewLine();
    }

    public void removeNewLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Index.txt"));

        while (br.ready()) {
            char c = (char) br.read();
            if (br.ready())
                sb.append(c);
            else
                break;
        }

        FileWriter fw = new FileWriter("Index.txt");
        fw.write(sb.toString());
        fw.close();
    }

    public void removeIndex() throws IOException {
        try {
            FileWriter writer = new FileWriter("Index.txt");
            // StringBuilder str = new StringBuilder();
            for (String hash : this.index.keySet()) {
                content.append(hash + " : ");
                for (String fileName : this.index.get(hash)) {
                    content.append(fileName + ", ");
                }
                content.delete(content.length() - 2, content.length());
                content.append("\n");
            }
            writer.write(content.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index.txt");
        }

    }
}
