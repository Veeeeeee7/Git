import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class Blob {
    protected String fileName;
    protected String fileContents;
    protected String hash;

    public Blob(String fileName) {
        this.fileName = fileName;
        this.fileContents = getContent();
    }

    private String getContent() {
        try {
            StringBuilder contents = new StringBuilder();
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine());
            }
            return contents.toString();

        } catch (Exception e) {
            System.out.println("File doesnt exist");
        }
        return null;

    }

}