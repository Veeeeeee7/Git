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
        // FileUtils.deleteDirectory("Objects");
        FileUtils.createDirectory("Objects");

        FileUtils.createDirectory("test");
        FileUtils.createFile("test/ab");
        FileUtils.writeFile("test/ab", "BOB");
        FileUtils.createFile("test/cd");
        FileUtils.writeFile("test/cd", "ZAA");
        FileUtils.createDirectory("test/zz");
        FileUtils.createFile("test/zz/aa");
        FileUtils.writeFile("test/zz/aa", "YUP");
        FileUtils.createFile("test/zz/cc");
        FileUtils.writeFile("test/zz/cc", "MOP");

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
    void testAddDirectory() throws Exception {
        Tree tree = new Tree();
        String hash = tree.addDirectory("test");
        String hash1 = FileUtils.sha1("BOB");
        String hash2 = FileUtils.sha1("ZAA");
        String hash3 = FileUtils.sha1("YUP");
        String hash4 = FileUtils.sha1("MOP");
        assertTrue(FileUtils.fileExists("Objects/" + hash1));
        assertTrue(FileUtils.fileExists("Objects/" + hash2));
        assertTrue(FileUtils.fileExists("Objects/" + hash3));
        assertTrue(FileUtils.fileExists("Objects/" + hash4));
        assertEquals(FileUtils.readFile("Objects/" + hash1), "BOB");
        assertEquals(FileUtils.readFile("Objects/" + hash2), "ZAA");
        assertEquals(FileUtils.readFile("Objects/" + hash3), "YUP");
        assertEquals(FileUtils.readFile("Objects/" + hash4), "MOP");
        assertTrue(FileUtils.fileExists("Objects/" + hash));
        assertEquals(FileUtils.readFile("Objects/" + hash),
                "blob : 581fd1622f7174405f03bea4a099538e7f253671 : ab\nblob :43b0b7d9b8f99e369478bda2399f1ac98d124fd4 : cd\ntree :3448752373dfe872923cabce6607c9637676ee9e : zz");

        // Path resourceDirectory = Paths.get("objects");
        // String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        // File f = new File(absolutePath);
        // String[] fNames = f.list();
        // String a = "";
        // for (String fName : fNames) {
        // a += fName + "\n";
        // }
        // throw new Exception(a);
        // throw new Exception(absolutePath);
    }
}
