import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws Exception {
        Index index = new Index();
        index.add("TestFolder");
        index.add("Blob.java");
        index.init();
        // Utils.deleteFile("Index");

        // index.init();
        // Commit c = new Commit("parent commit", "author", "summary");
        // c.setNextCommit("NEXT COMMIT");
        // System.out.println(c.createCommit());
        // Index index = new Index();
        // index.init();

        // Tree tree = new Tree();
        // tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");
        // tree.add("blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt");
        // tree.add("blob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");

        // tree.remove("6c834d62d7524442cdd32ab209c9b2c083c0a474");

        // tree.finalize();
        // Commit commit = new Commit("someParents", "author claire", "this is for
        // testing");
        // commit.createCommit();

        // Tree tree = new Tree();

        // tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");

        // tree.finalize();
        // System.out.println(tree.getSHA1());
        // String a = tree.addDirectory("TestDirectory");
        // System.out.println(a);
        // System.out.println(FileUtils.sha1(FileUtils.readFile("Objects/2a4ed52d19de1e29406d7dc83b9596efb22dcbb8")));
        // System.out.println(FileUtils.readFile("Objects/2a4ed52d19de1e29406d7dc83b9596efb22dcbb8"));
        // FileUtils.createDirectory("test");
        // FileUtils.createFile("test/ab");
        // FileUtils.writeFile("test/ab", "BOB");
        // FileUtils.createFile("test/cd");
        // FileUtils.writeFile("test/cd", "ZAA");
        // FileUtils.createDirectory("test/zz");
        // FileUtils.createFile("test/zz/aa");
        // FileUtils.writeFile("test/zz/aa", "YUP");
        // FileUtils.createFile("test/zz/cc");
        // FileUtils.writeFile("test/zz/cc", "MOP");
        // Tree t = new Tree();
        // FileUtils.createDirectory("test1");
        // FileUtils.createFile("test1/ab");
        // FileUtils.writeFile("test1/ab", "BOB");
        // FileUtils.createFile("test1/cd");
        // FileUtils.writeFile("test1/cd", "ZAA");
        // FileUtils.createFile("test1/ef");
        // FileUtils.writeFile("test1/ef", "YAH");
        // t.addDirectory("test1");

        // FileUtils.createDirectory("test2");
        // FileUtils.createFile("test2/aa");
        // FileUtils.writeFile("test2/aa", "BOA");
        // FileUtils.createFile("test2/bb");
        // FileUtils.writeFile("test2/bb", "WAB");
        // FileUtils.createFile("test2/cc");
        // FileUtils.writeFile("test2/cc", "OOO");

        // FileUtils.createDirectory("test2/fold1");
        // FileUtils.createFile("test2/fold1/dd");
        // FileUtils.writeFile("test2/fold1/dd", "DUO");
        // FileUtils.createDirectory("test2/fold2");
        // t.addDirectory("test2");
        // FileUtils.createFile("test2/fold2/ee");
        // FileUtils.writeFile("test2/fold2/ee", "YYY");
        // t.addDirectory("test2");
        // Tree t = new Tree();
        // Tree tt = new Tree();
        // t.addDirectory("test1");
        // tt.addDirectory("test2");
    }
}
