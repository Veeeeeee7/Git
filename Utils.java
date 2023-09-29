import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class Utils {

    public static void writeStringToFile(String filename, String str) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(filename);
        pw.print(str);
        pw.close();
    }

    public static String writeFileToString(String f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append((char) br.read());
        }
        br.close();
        return sb.toString();
    }

    public static void deleteFile(String f) {
        File file = new File(f);
        file.delete();
    }

    public static void deleteDirectory(String f) {
        File file = new File(f);
        file.delete();
    }

    public static void createFile(String fileName) {
        File f = new File(fileName);
        try {
            f.createNewFile();
        } catch (Exception e) {
            System.out.println("can't create file");
        }
    }

    public static void createDirectory(String directoryName) {
        File folder = new File(directoryName);
        folder.mkdir();
    }

    public static String readFile(String fileName) {
        try {
            File file = new File(fileName);
            StringBuilder contents = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine() + "\n");
            }
            if (contents.isEmpty()) {
                return "";
            }
            contents.deleteCharAt(contents.length() - 1);
            return contents.toString();
        } catch (Exception e) {
            System.out.println("File doesnt exist");
        }
        return null;
    }

    public static boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    public static String sha1(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(content.getBytes());
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

}
