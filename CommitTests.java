import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class CommitTests {
    Commit commit = new Commit("someParents", "author claire", "this is for testing");

    @Test
    void testCreateCommit() throws IOException {
        commit.createCommit();
        Path path = Paths.get("objects");
        assertTrue(Files.exists(path));
        File file = new File("objects/5a1f5a7c2b9a1bed276bafb2b2f4c68842842a18");
        assertTrue(file.exists());
    }

    @Test
    void testGetDate() {
        String actaul = commit.getDate();
        String expected = "2023-09-22";
        assertEquals(expected, actaul);
    }
}
