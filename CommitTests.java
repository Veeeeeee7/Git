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
                Utils.writeStringToFile("testFile3", "ajmjkmkm5t5");
                Utils.writeStringToFile("testFile4", "aer:KJ");
                Utils.createDirectory("testFolder2");
                Utils.writeStringToFile("testFolder2/testFile2-1", "dgjndghj");
                Utils.writeStringToFile("testFolder2/testFile2-2", "aerh");
        }

        @AfterAll
        static void cleanUp() {
                Utils.deleteDirectory("Objects");
                Utils.deleteFile("Index");
                Utils.deleteFile("testFile1");
                Utils.deleteFile("testFile2");
                Utils.deleteFile("testFile3");
                Utils.deleteFile("testFile4");
                Utils.deleteDirectory("testFolder1");
                Utils.deleteDirectory("testFolder2");
        }

        @Test
        void testCreateCommit() throws Exception {
                Index index = new Index();
                index.add("testFile1");
                index.add("testFile2");
                Commit commit = new Commit("", "author claire", "this is for testing");
                commit.createCommit();
                Path path = Paths.get("Objects");
                assertTrue(Files.exists(path));
                File file = new File("Objects/8458148ff577ce57243004fd84317bb14636107");
                assertTrue(file.exists());
        }

        @Test
        void testGetDate() throws Exception {
                Index index = new Index();
                index.add("testFile1");
                index.add("testFile2");
                Commit commit = new Commit("", "author claire", "this is for testing");
                String actaul = commit.getDate();
                String expected = "2023-10-02";
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

        @Test
        void test4Commits() throws Exception {
                Index index = new Index();
                index.add("testFile1");
                index.add("testFile2");
                Commit c1 = new Commit("", "AUTHOR", "first commit");
                String hash1 = c1.createCommit();
                index.add("testFolder1");
                Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
                String hash2 = c2.createCommit();
                c1.setNextCommit(hash2);
                index.add("testFile3");
                index.add("testFile4");
                Commit c3 = new Commit(hash2, "AUTHOR", "third commit");
                String hash3 = c3.createCommit();
                c2.setNextCommit(hash3);
                index.add("testFolder2");
                Commit c4 = new Commit(hash3, "AUTHOR", "fourth commit");
                String hash4 = c4.createCommit();
                c3.setNextCommit(hash4);

                assertEquals("the right file contents for commit 1 aren't there",
                                "8458148ff577ce57243004fd84317bb14636107\n\n4c7b64635e83037f75eb16d5a40f60c64cea64da\nAUTHOR\n"
                                                + c1.getDate() + "\nfirst commit",
                                Utils.writeFileToString("Objects/" + hash1));

                assertEquals("the right file contents for commit 2 aren't there",
                                "df589b9e759f5524c387a52e1a5b8435ff39f55a\n8b661cba890f75b3b0b9cbd44d7af76c3854f8d2\nedafdc75230d6ae93235e637b2f8ae2a7a82a014\nAUTHOR\n"
                                                + c2.getDate() + "\nsecond commit",
                                Utils.writeFileToString("Objects/" + hash2));

                assertEquals("the right file contents for commit 1 aren't there",
                                "8c2d9d64fc3d31546d7b0b26c6389e735080b895\n4c7b64635e83037f75eb16d5a40f60c64cea64da\n37336bf0a5151d1c7e838cc70b83344a44cd505b\nAUTHOR\n"
                                                + c3.getDate() + "\nthird commit",
                                Utils.writeFileToString("Objects/" + hash3));

                assertEquals("the right file contents for commit 2 aren't there",
                                "c8a119fdf57dee23cc22d52bb79ff37b1d329529\nedafdc75230d6ae93235e637b2f8ae2a7a82a014\n\nAUTHOR\n"
                                                + c4.getDate() + "\nfourth commit",
                                Utils.writeFileToString("Objects/" + hash4));
        }
}
