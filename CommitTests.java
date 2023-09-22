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
    void testCreateCommit() throws IOException
    {
        commit.createCommit();
        Path path = Paths.get("objects");
        assertTrue(Files.exists(path));
        File file = new File("2e1f8a7d1175ec4885c8fc8e7c13bb4f61a842e0");
        assertTrue(file.exists());
    }

    @Test
    void testGetDate() {
        String actaul = commit.getDate();
        String expected = "2023-09-22";
        assertEquals(expected, actaul);
    }
}
