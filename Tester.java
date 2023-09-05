import java.io.File;

public class Tester {
    public static void main(String[] args) {
        Index index = new Index();
        index.init();
        index.remove("Tester.java");
    }
}
