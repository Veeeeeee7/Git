public class Tester {
    public static void main(String[] args) {
        Blob b = new Blob("test.txt");
        System.out.println(b.fileName);
        System.out.println(b.fileContents);
        System.out.println(b.hash);
    }
}
