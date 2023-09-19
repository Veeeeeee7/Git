import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IndexTests {
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        
         Utils.writeStringToFile("test", "test file contents");
         Utils.writeStringToFile("test2", "hello");
         Utils.deleteFile("Index.txt");
         Utils.deleteDirectory("Objects");
         Utils.deleteFile("Tree");
         
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
         Utils.deleteFile("test");
         Utils.deleteFile("test2");
         Utils.deleteFile("Index.txt");
         Utils.deleteDirectory("Objects");
         Utils.deleteFile("Tree");
    }
    
    @Test
    void testAdd() throws IOException {

        Index ind = new Index();
        ind.add("test");

        String expectedResult = "cbaedccfded0c768295aae27c8e5b3a0025ef340 : test";
        String index = Utils.writeFileToString("Index.txt");

        assertEquals("Added to index incorrectly", expectedResult, index);
    }

    @Test
    void testInit() {
        Index ind = new Index();
        ind.init();

        Path path1 = Paths.get("Objects");
        Path path2 = Paths.get("Index.txt");

        assertTrue("Did not create objects folder", Files.exists(path1));
        assertTrue("Did not create Index.txt", Files.exists(path2));
        
    }

    @Test
    void testRemove() throws IOException {
        Index ind = new Index();
        ind.add("test");
        ind.add("test2");

        ind.remove("test");

        String expectedResult = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d : test2";
        String indexContents = Utils.writeFileToString("Index.txt");

        assertEquals("Did not remove correctly", expectedResult, indexContents);

    }
}
