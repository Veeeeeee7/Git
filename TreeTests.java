import static org.junit.Assert.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Utilities.FileUtils;

public class TreeTests {
    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        Utils.writeStringToFile("junit_example_file_data.txt", "test file contents");
        Utils.deleteFile("Index.txt");
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Tree");
        FileUtils.createDirectory("Objects");

        FileUtils.createDirectory("test1");
        FileUtils.createFile("test1/ab");
        FileUtils.writeFile("test1/ab", "BOB");
        FileUtils.createFile("test1/cd");
        FileUtils.writeFile("test1/cd", "ZAA");
        FileUtils.createFile("test1/ef");
        FileUtils.writeFile("test1/ef", "YAH");

        FileUtils.createDirectory("test2");
        FileUtils.createFile("test2/aa");
        FileUtils.writeFile("test2/aa", "BOA");
        FileUtils.createFile("test2/bb");
        FileUtils.writeFile("test2/bb", "WAB");
        FileUtils.createFile("test2/cc");
        FileUtils.writeFile("test2/cc", "OOO");

        FileUtils.createDirectory("test2/fold1");
        FileUtils.createFile("test2/fold1/dd");
        FileUtils.writeFile("test2/fold1/dd", "DUO");
        FileUtils.createDirectory("test2/fold2");
        FileUtils.createFile("test2/fold2/ee");
        FileUtils.writeFile("test2/fold2/ee", "YYY");

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteFile("junit_example_file_data.txt");
        Utils.deleteFile("Index.txt");
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Tree");
        FileUtils.deleteDirectory("test");

    }

    @Test
    void testAdd() throws IOException, URISyntaxException {
        Tree tree = new Tree();

        tree.add("hello");
        tree.add("hi");

        String treeContents = Utils.writeFileToString("Tree");
        assertEquals("Add to Tree works incorrectly", treeContents, "hello\nhi");
    }

    @Test
    void testFinalize() throws Exception {
        Tree tree = new Tree();

        tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");

        String sha1 = "2f4bd4718a70761547c8cbc1e18d34d96c2ddf8d";

        tree.finalize();
        Path path = Paths.get("Objects/" + sha1);

        assertTrue("Blob does not exist", Files.exists(path));
    }

    @Test
    void testRemoveFromTree() throws FileNotFoundException, IOException, URISyntaxException {
        Tree tree = new Tree();

        tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");
        tree.add("blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt");
        tree.add("blob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");

        tree.remove("6c834d62d7524442cdd32ab209c9b2c083c0a474");

        String treeContents = Utils.writeFileToString("Tree");
        assertEquals(treeContents,
                "blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt\nblob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");
    }

    @Test
    void testAddDirectoryBasic() throws Exception {
        Tree tree = new Tree();
        String hash = tree.addDirectory("test1");
        String hash1 = FileUtils.sha1("BOB");
        String hash2 = FileUtils.sha1("ZAA");
        String hash3 = FileUtils.sha1("YAH");
        assertTrue(FileUtils.fileExists("Objects/" + hash1));
        assertTrue(FileUtils.fileExists("Objects/" + hash2));
        assertTrue(FileUtils.fileExists("Objects/" + hash3));
        assertEquals(FileUtils.readFile("Objects/" + hash1), "BOB");
        assertEquals(FileUtils.readFile("Objects/" + hash2), "ZAA");
        assertEquals(FileUtils.readFile("Objects/" + hash3), "YAH");
        assertTrue(FileUtils.fileExists("Objects/" + hash));
        assertEquals(FileUtils.readFile("Objects/" + hash),
                "blob : 581fd1622f7174405f03bea4a099538e7f253671 : ab\n" + //
                        "blob : 77f721df21a2d0b314694c692fd017d68350fd54 : ef\n" + //
                        "blob : 43b0b7d9b8f99e369478bda2399f1ac98d124fd4 : cd");
    }

    @Test
    void testAddDirectoryAdvanced() throws Exception {
        Tree tree = new Tree();
        String hash = tree.addDirectory("test2");
        String hash1 = FileUtils.sha1("BOB");
        String hash2 = FileUtils.sha1("WAB");
        String hash3 = FileUtils.sha1("OOO");
        String hash4 = FileUtils.sha1("DUO");
        String hash5 = FileUtils.sha1("YYY");
        assertTrue(FileUtils.fileExists("Objects/" + hash1));
        assertTrue(FileUtils.fileExists("Objects/" + hash2));
        assertTrue(FileUtils.fileExists("Objects/" + hash3));
        assertTrue(FileUtils.fileExists("Objects/" + hash4));
        assertTrue(FileUtils.fileExists("Objects/" + hash5));
        assertEquals(FileUtils.readFile("Objects/" + hash1), "BOB");
        assertEquals(FileUtils.readFile("Objects/" + hash2), "WAB");
        assertEquals(FileUtils.readFile("Objects/" + hash3), "OOO");
        assertEquals(FileUtils.readFile("Objects/" + hash4), "DUO");
        assertEquals(FileUtils.readFile("Objects/" + hash5), "YYY");
        assertTrue(FileUtils.fileExists("Objects/" + hash));
        assertEquals(FileUtils.readFile("Objects/" + hash), "blob : 97bdfda29cd652224745a552bc6e2f8cb7ab5d16 : bb\n" + //
                "blob : d652148cc8af1881cc6a1b041b14449a24e77d3 : aa\n" + //
                "blob : d34c0b1d281e4879b11710a91d3d9161f092b5bd : cc\n" + //
                "tree : 1f34cc55024356cec15c25c621fb8eeb2b33c000 : fold2\n" + //
                "tree : 46aeee1299fa4040e341f05a9bc3191d54c55126 : fold1");
    }
}
