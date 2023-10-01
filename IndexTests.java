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

import Utilities.FileUtils;

public class IndexTests {
    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        Utils.writeStringToFile("testv", "test file contents");
        Utils.writeStringToFile("testv2", "hello");
        Utils.deleteFile("Index");
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Tree");

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteFile("testv");
        Utils.deleteFile("testv2");
        Utils.deleteFile("Index");
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Tree");
    }

    @Test
    void testAdd() throws Exception {

        Index ind = new Index();
        ind.add("testv");

        String expectedResult = "blob : cbaedccfded0c768295aae27c8e5b3a0025ef340 : testv";
        String index = Utils.writeFileToString("Index");

        assertEquals("Added to index incorrectly", expectedResult, index);
    }

    @Test
    void testInit() {
        Index ind = new Index();
        ind.init();

        Path path1 = Paths.get("Objects");
        Path path2 = Paths.get("Index");

        assertTrue("Did not create objects folder", Files.exists(path1));
        assertTrue("Did not create Index", Files.exists(path2));

    }

    @Test
    void testRemove() throws Exception {
        Index ind = new Index();
        ind.add("testv");
        ind.add("testv2");

        ind.remove("testv");

        String expectedResult = "blob : aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d : testv2";
        String indexContents = Utils.writeFileToString("Index");

        assertEquals("Did not remove correctly", expectedResult, indexContents);

    }
}
