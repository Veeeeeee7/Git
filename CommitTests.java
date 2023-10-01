import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class CommitTests {

    @Test
    void testCreateCommit() throws Exception {
        Commit commit = new Commit("someParents", "author claire", "this is for testing");
        commit.createCommit();
        Path path = Paths.get("Objects");
        assertTrue(Files.exists(path));
        File file = new File("Objects/5a1f5a7c2b9a1bed276bafb2b2f4c68842842a18");
        assertTrue(file.exists());
    }

    @Test
    void testGetDate() throws Exception {
        Commit commit = new Commit("someParents", "author claire", "this is for testing");
        String actaul = commit.getDate();
        String expected = "2023-09-29";
        assertEquals(expected, actaul);
    }

    @Test
    void testSetNextCommit() throws Exception {
        Commit c = new Commit("parent commit", "author", "summary");
        String hash = c.createCommit();
        c.setNextCommit("next commit");
        assertTrue("the right file isn't created", Utils.fileExists("objects/" + hash));
        assertEquals("the right file contents aren't there", Utils.writeFileToString("objects/" + hash),
                "da39a3ee5e6b4b0d3255bfef95601890afd80709\nparent commit\nnext commit\nauthor\n2023-09-29\nsummary");
    }
}
