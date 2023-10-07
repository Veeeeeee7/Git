import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws Exception {
        //
        String c1 = "blob\nyup\nhi";
        String c2 = "hi\nyup\nblob";
        System.out.println(Utils.equalContents(c1, c2));

        // Utils.writeStringToFile("testFile1", "testttt");
        // Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
        // Utils.createDirectory("testFolder1");
        // Utils.writeStringToFile("testFolder1/testFile1-1", "t;kljfb");
        // Utils.writeStringToFile("testFolder1/testFile1-2", "BLKIEUY");
        // Utils.writeStringToFile("testFile3", "ajmjkmkm5t5");
        // Utils.writeStringToFile("testFile4", "aer:KJ");
        // Utils.createDirectory("testFolder2");
        // Utils.writeStringToFile("testFolder2/testFile2-1", "dgjndghj");
        // Utils.writeStringToFile("testFolder2/testFile2-2", "aerh");

        // Index index = new Index();
        // index.init();
        // index.add("testFile1");
        // index.add("testFile2");
        // Commit c1 = new Commit("", "AUTHOR", "first commit");
        // String hash1 = c1.createCommit();
        // index.add("testFolder1");
        // Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
        // String hash2 = c2.createCommit();
        // c1.setNextCommit(hash2);
        // index.add("testFile3");
        // index.add("testFile4");
        // Commit c3 = new Commit(hash2, "AUTHOR", "third commit");
        // String hash3 = c3.createCommit();
        // c2.setNextCommit(hash3);
        // index.add("testFolder2");
        // Commit c4 = new Commit(hash3, "AUTHOR", "fourth commit");
        // String hash4 = c4.createCommit();
        // c3.setNextCommit(hash4);

        /*
         * TESTING CODE FOR DELETING AND EDITING
         */

        // Utils.deleteDirectory("Objects");
        // Index i = new Index();
        // i.clearContent();
        // i.add("testFile1");
        // Commit c1 = new Commit("", "AUTHOR", "FIRST COMMIT");
        // String c1Hash = c1.createCommit();
        // i.add("testFile2");
        // // i.add("testFolder1");
        // Commit c2 = new Commit(c1Hash, "AUTHOR", "SECOND COMMIT");
        // String c2Hash = c2.createCommit();
        // c1.setNextCommit(c2Hash);
        // i.add("testFile3");
        // Commit c3 = new Commit(c2Hash, "AUTHOR", "THIRD COMMIT");
        // String c3Hash = c3.createCommit();
        // c2.setNextCommit(c3Hash);
        // Utils.writeStringToFile("testFile4", "OLD STUFF");
        // i.add("testFile4");
        // // i.deleteFile("testFolder1");
        // // i.deleteFile("testFile1-1");
        // Commit c4 = new Commit(c3Hash, "AUTHOR", "FOURTH COMMIT");
        // String c4Hash = c4.createCommit();
        // c3.setNextCommit(c4Hash);

        // Utils.writeStringToFile("testFile4", "NEW STUFF");
        // i.editFile("testFile4");

        // Commit c5 = new Commit(c4Hash, "AUTHOR", "FIFTH COMMIT");
        // String c5Hash = c5.createCommit();
        // c4.setNextCommit(c5Hash);

        // i.deleteFile("testFile2");
        // i.deleteFile("testFile1");
        // Commit c6 = new Commit(c5Hash, "AUTHOR", "SIXTH COMMIT");
        // String c6Hash = c6.createCommit();
        // c5.setNextCommit(c6Hash);

        //
        //
        //
        //
        // Index index = new Index();
        // index.init();
        // Utils.writeStringToFile("testFile1", "testttt");
        // Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
        // Utils.createDirectory("testFolder1");
        // Utils.writeStringToFile("testFolder1/testFile1-1", "t;kljfb");
        // Utils.writeStringToFile("testFolder1/testFile1-2", "BLKIEUY");
        // Utils.writeStringToFile("testFile3", "ajmjkmkm5t5");
        // Utils.writeStringToFile("testFile4", "aer:KJ");
        // Utils.createDirectory("testFolder2");
        // Utils.writeStringToFile("testFolder2/testFile2-1", "dgjndghj");
        // Utils.writeStringToFile("testFolder2/testFile2-2", "aerh");

        // index.add("testFile1");
        // index.add("testFile2");
        // Commit c1 = new Commit("", "AUTHOR", "first commit");
        // String hash1 = c1.createCommit();
        // index.add("testFolder1");
        // Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
        // String hash2 = c2.createCommit();
        // c1.setNextCommit(hash2);
        // index.add("testFile3");
        // index.add("testFile4");
        // Commit c3 = new Commit(hash2, "AUTHOR", "third commit");
        // String hash3 = c3.createCommit();
        // c2.setNextCommit(hash3);
        // index.add("testFolder2");
        // Commit c4 = new Commit(hash3, "AUTHOR", "fourth commit");
        // String hash4 = c4.createCommit();
        // c3.setNextCommit(hash4);

        // File file = new File("temp");
        // file.delete();

        // Index index = new Index();
        // index.init();
        // Utils.writeStringToFile("testFile1", "testttt");
        // Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
        // Utils.createDirectory("testFolder1");
        // Utils.writeStringToFile("testFolder1/testFile1-1", "t;kljfb");
        // Utils.writeStringToFile("testFolder1/testFile1-2", "BLKIEUY");

        // index.add("testFile1");
        // index.add("testFile2");
        // Commit c1 = new Commit("", "AUTHOR", "first commit");
        // String hash1 = c1.createCommit();
        // index.add("testFolder1");
        // Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
        // String hash2 = c2.createCommit();
        // c1.setNextCommit(hash2);
        // index.init();
        // Utils.writeStringToFile("testFile1", "testttt");
        // Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
        // index.add("testFile1");
        // index.add("lib");
        // index.remove("lib");
        // index.add("Tree.java");
        // Commit commit = new Commit("someParents", "author claire", "this is for
        // testing");
        // commit.createCommit();
        // Index i = new Index();
        // i.add("Blob.java");
        // i.add("Tree.java");
        // Commit c = new Commit("parent", "author", "summary");
        // c.createCommit();
        // System.out.println(c.getTreeHash());
        // Index ind = new Index();
        // Utils.writeStringToFile("testv", "test file contents");
        // Utils.writeStringToFile("testv2", "hello");
        // ind.add("testv");
        // ind.add("testv2");

        // ind.remove("testv");

        // String expectedResult = "blob : aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d :
        // testv2";
        // String indexContents = Utils.writeFileToString("Index");
        // Index index = new Index();
        // index.add("TestFolder");
        // index.add("Blob.java");
        // index.init();
        // index.remove("TestFolder");
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
