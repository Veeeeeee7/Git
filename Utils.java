import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Scanner;

public class Utils {

    public static void writeStringToFile(String filename, String str) throws FileNotFoundException {
        int index = filename.indexOf("/");
        while (index > -1) {
            File folder = new File(filename.substring(0, index));
            folder.mkdir();
            index = filename.indexOf("/", index + 1);
        }
        if (!fileExists(filename)) {
            createFile(filename);
        }
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
        File directory = new File(f);
        File[] files = directory.listFiles();
        if (files == null) {
            directory.delete();
            return;
        }
        for (File file : files) {
            file.delete();
        }
        directory.delete();
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

    // public static String readFile(String fileName) {
    // try {
    // File file = new File(fileName);
    // StringBuilder contents = new StringBuilder();
    // Scanner scanner = new Scanner(file);
    // while (scanner.hasNextLine()) {
    // contents.append(scanner.nextLine() + "\n");
    // }
    // if (contents.isEmpty()) {
    // return "";
    // }
    // contents.deleteCharAt(contents.length() - 1);
    // return contents.toString();
    // } catch (Exception e) {
    // System.out.println("File doesnt exist");
    // }
    // return null;
    // }

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

            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            System.out.println("Error in hashing blob");
        }
        return null;
    }

    public static boolean equalContents(String c1, String c2) {
        HashSet<String> c1Lines = new HashSet<>();
        HashSet<String> temp1 = new HashSet<>();
        for (String line : c1.split("\n")) {
            c1Lines.add(line);
            temp1.add(line);
        }

        HashSet<String> temp2 = new HashSet<>();
        HashSet<String> c2Lines = new HashSet<>();
        for (String line : c2.split("\n")) {
            c2Lines.add(line);
            temp2.add(line);
        }

        if (c1Lines.size() != c2Lines.size()) {
            return false;
        }

        for (String line : c1Lines) {
            if (!c2Lines.contains(line)) {
                return false;
            }
            temp1.remove(line);
        }

        if (!temp1.isEmpty()) {
            return false;
        }

        for (String line : c2Lines) {
            if (!c1Lines.contains(line)) {
                return false;
            }
            temp2.remove(line);
        }

        if (!temp2.isEmpty()) {
            return false;
        }

        return true;
    }

}
