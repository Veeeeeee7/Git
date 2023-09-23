import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class Blob {
    protected File file;
    protected String fileContents;
    protected String hash;

    public Blob(File file) {
        this.file = file;
        this.fileContents = getContents();
        this.hash = getHash();
    }

    private String getContents() {
        try {
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine() + "\n");
            }
            contents.deleteCharAt(contents.length() - 1);
            return contents.toString();
        } catch (Exception e) {
            System.out.println("File doesnt exist");
        }
        return null;

    }

    public String getHash() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(fileContents.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            System.out.println("Error in hashing blob");
        }
        return null;
    }

    public void createBlob() {
        try {
            // String compressed = compress();
            File blob = new File("objects/" + hash);
            if (!blob.exists()) {
                blob.createNewFile();
            }
            FileWriter writer = new FileWriter(blob);
            // writer.write(compressed);
            writer.write(fileContents);
            writer.close();
        } catch (Exception e) {
            System.out.println("cant create blob file");
        }
    }

    private String compress() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(fileContents.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        } catch (Exception e) {
            System.out.println("cant compress");
        }
        return null;
    }

}