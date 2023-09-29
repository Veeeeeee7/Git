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

    public Commit(String parentCommit, String author, String summary) throws Exception {
        this.parentCommit = parentCommit;
        this.summary = summary;
        this.author = author;
        createTree();
    }

    public String createCommit() throws Exception {
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
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}
