import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Stack;

public class Index {
    protected String[] fileNames;
    protected HashMap<String, Stack<String>> index;

    public Index() {
        index = new HashMap<String, Stack<String>>();
        File folder = new File(System.getProperty("user.dir"));
        System.out.println(folder.getAbsolutePath());
        fileNames = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                File currentFile = new File(name);
                return !currentFile.isHidden() && !currentFile.isDirectory() && !name.equals("Index.txt");
            }
        });
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
            StringBuilder str = new StringBuilder();
            for (String hash : this.index.keySet()) {
                str.append(hash + " ");
                for (String fileName : this.index.get(hash)) {
                    str.append(fileName + " ");
                }
                str.append("\n");
            }
            writer.write(str.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create Index.txt");
        }
    }
}
