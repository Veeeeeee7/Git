import java.io.File;

public class Tester {
    public static void main(String[] args) {
        Index index = new Index();
        for (String fileName : index.fileNames) {
            System.out.println(fileName);
        }

    }
}
