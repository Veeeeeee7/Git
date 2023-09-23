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

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteFile("junit_example_file_data.txt");
        Utils.deleteFile("Index.txt");
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Tree");
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
}
