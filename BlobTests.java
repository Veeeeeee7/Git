import static org.junit.Assert.*;

import java.io.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlobTests {
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
         Utils.deleteFile("Index.txt");
         Utils.deleteDirectory("Objects");
         Utils.deleteFile("Tree");
         
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
         Utils.deleteFile("Index.txt");
         Utils.deleteDirectory("Objects");
         Utils.deleteFile("Tree");
    }
    
    @Test
    void testCreateBlob() throws IOException {
        File file1 = new File("file1");
        Utils.writeStringToFile("file1", "hello");
        Blob blob = new Blob(file1);

        // Check blob exists in the objects folder
        File file_junit1 = new File("Objects/aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d");
        assertTrue("Blob file to add not found", file_junit1.exists());

        // Read file contents
        String indexFileContents = Utils.writeFileToString("Objects/aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d");
        assertEquals("File contents of Blob don't match file contents pre-blob creation", indexFileContents,
                "hello");
    }

    @Test
    void testGetHash() throws FileNotFoundException {
        File f1 = new File("testFile1");
        Utils.writeStringToFile("testFile1", "hello");
        Blob blob1 = new Blob(f1);

        File f2 = new File("testFile2");
        Utils.writeStringToFile("testFile2", "hi");
        Blob blob2 = new Blob(f2);

        String hash1 = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d";
        String hash2 = "c22b5f9178342609428d6f51b2c5af4c0bde6a42";

        assertEquals("Incorrect Hash", blob1.getHash(), hash1);
        assertEquals("Incorrect Hash", blob2.getHash(), hash2);
    }
}
