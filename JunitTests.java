import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JunitTests {

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
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testInitialize() throws Exception {

        Index index = new Index();

        index.init();

        // check if the file exists
        File file = new File("Index.txt");
        Path path = Paths.get("Objects");

        assertTrue(file.exists());
        assertTrue(Files.exists(path));
    }

    @Test
    @DisplayName("[15] Test if adding a blob works.  5 for sha, 5 for file contents, 5 for correct location")
    void testCreateBlob() throws Exception {

        File file1 = new File("file1");
        Utils.writeStringToFile("file1", "hello");
        Blob blob = new Blob(file1);

        // Check blob exists in the objects folder
        File file_junit1 = new File("Objects/" + blob.getHash());
        assertTrue("Blob file to add not found", file_junit1.exists());

        // Read file contents
        String indexFileContents = Utils.writeFileToString("Objects/" + blob.getHash());
        assertEquals("File contents of Blob don't match file contents pre-blob creation", indexFileContents,
                "hello");
    }

    @Test
    @DisplayName("Testing if add to tree works")
    void testAddToTree() throws FileNotFoundException, IOException, URISyntaxException {
        Tree tree = new Tree();

        tree.add("hello");
        tree.add("hi");

        File f = new File("Tree");
        Blob blob = new Blob(f);
        String sha1 = blob.getHash();
        Path path = Paths.get("Objects/" + sha1);

        String treeContents = Utils.writeFileToString("Tree");
        assertEquals(treeContents, "hello\nhi");
    }

    @Test
    @DisplayName("Testing if remove from tree works")
    void testRemoveFromTree() throws FileNotFoundException, IOException, URISyntaxException {
        Tree tree = new Tree();

        tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");
        tree.add("blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt");
        tree.add("blob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");

        // checking that it puts a tree in the objects folder
        File f = new File("Tree");
        Blob blob = new Blob(f);

        String str = Utils.writeFileToString("Tree");

        tree.remove("6c834d62d7524442cdd32ab209c9b2c083c0a474");

        // checking that it added a new tree in the objects folder

        String treeContents = Utils.writeFileToString("Tree");
        assertEquals(treeContents,
                "blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt\nblob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");
    }
}