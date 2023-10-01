import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommitTests {
    @BeforeAll
    static void setUp() throws Exception {
        Index index = new Index();
        index.init();
        Utils.writeStringToFile("testFile1", "testttt");
        Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
        Utils.createDirectory("testFolder1");
        Utils.writeStringToFile("testFolder1/testFile1-1", "t;kljfb");
        Utils.writeStringToFile("testFolder1/testFile1-2", "BLKIEUY");
    }

    @AfterAll
    static void cleanUp() {
        Utils.deleteDirectory("Objects");
        Utils.deleteFile("Index");
        Utils.deleteFile("testFile1");
        Utils.deleteFile("testFile2");
    }

    @Test
    void testCreateCommit() throws Exception {
        Index index = new Index();
        index.add("testFile1");
        index.add("testFile2");
        Commit commit = new Commit("someParents", "author claire", "this is for testing");
        commit.createCommit();
        Path path = Paths.get("Objects");
        assertTrue(Files.exists(path));
        File file = new File("Objects/84438138416e20a2f51fc75aaded42ea22e15082");
        assertTrue(file.exists());
    }

    @Test
    void testGetDate() throws Exception {
        Index index = new Index();
        index.add("testFile1");
        index.add("testFile2");
        Commit commit = new Commit("someParents", "author claire", "this is for testing");
        String actaul = commit.getDate();
        String expected = "2023-10-01";
        assertEquals(expected, actaul);
    }

    @Test
    void testSetNextCommit() throws Exception {
        Index index = new Index();
        index.add("testFile1");
        index.add("testFile2");
        Commit c = new Commit("", "author", "summary");
        String hash = c.createCommit();
        c.setNextCommit("next commit");
        assertTrue("the right file isn't created", Utils.fileExists("objects/" + hash));
        assertEquals("the right file contents aren't there", Utils.writeFileToString("objects/" + hash),
                "8458148ff577ce57243004fd84317bb14636107\n\nnext commit\nauthor\n" + c.getDate()
                        + "\nsummary");
    }

    @Test
    void test1Commit() throws Exception {
        Index index = new Index();
        index.add("testFile1");
        index.add("testFile2");
        Commit c = new Commit("", "AUTHOR", "BEST SUMMARY");
        String hash = c.createCommit();
        assertTrue("the right file isn't created", Utils.fileExists("objects/" + hash));
        assertEquals("the right file contents aren't there", Utils.writeFileToString("objects/" + hash),
                "8458148ff577ce57243004fd84317bb14636107\n\n\nAUTHOR\n" + c.getDate()
                        + "\nBEST SUMMARY");
    }

    @Test
    void test2Commits() throws Exception {
        Index index = new Index();
        index.add("testFile1");
        index.add("testFile2");
        Commit c1 = new Commit("", "AUTHOR", "first commit");
        String hash1 = c1.createCommit();
        index.add("testFolder1");
        Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
        String hash2 = c2.createCommit();
        c1.setNextCommit(hash2);

        assertEquals("the right file contents for commit 1 aren't there",
                "8458148ff577ce57243004fd84317bb14636107\n\n4c7b64635e83037f75eb16d5a40f60c64cea64da\nAUTHOR\n"
                        + c1.getDate() + "\nfirst commit",
                Utils.writeFileToString("Objects/" + hash1));

        assertEquals("the right file contents for commit 2 aren't there",
                "df589b9e759f5524c387a52e1a5b8435ff39f55a\n8b661cba890f75b3b0b9cbd44d7af76c3854f8d2\n\nAUTHOR\n"
                        + c2.getDate() + "\nsecond commit",
                Utils.writeFileToString("Objects/" + hash2));
    }
}
