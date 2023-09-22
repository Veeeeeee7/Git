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
    private String summary;
    private String author;

    public Commit(String parentCommit, String author, String summary)
    {
        this.parentCommit = parentCommit;
        this.summary = summary;
        this.author = author;
        
    }

    public void createCommit() throws IOException
    {
        createFile();
        File file = new File("temp");
        File path = new File("objects");
        path.mkdirs();
        Blob blob = new Blob(file);
        blob.createBlob();
        file.delete();
    }

    public File createFile() throws IOException
    {
        File file = new File("temp");
        PrintWriter pw = new PrintWriter(new FileWriter("temp", false));
        pw.print(getContentWithoutThirdLine());
        pw.close();
        return file;
    }

    public void createTree()
    {
        Tree tree = new Tree();
        treeSHA1 = tree.getSHA1();
    }

    private String getContentWithoutThirdLine()
    {
        String content = "";
        content += treeSHA1 + "\n";
        content += parentCommit + "\n";
        content += "\n";
        content += author + "\n";
        content += getDate() + "\n";
        content += summary;
        return content;
    }

    public String getDate()
    {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
        
    }
}
