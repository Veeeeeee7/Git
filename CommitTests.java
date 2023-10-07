import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                index.init();
                index.add("testFile1");
                index.add("testFile2");
                Commit commit = new Commit("", "author claire", "this is for testing");
                String hash = commit.createCommit();
                Path path = Paths.get("Objects");
                assertTrue(Files.exists(path));
                File file = new File("Objects/" + hash);
                assertTrue(file.exists());
        }

        @Test
        void testGetDate() throws Exception {
                Index index = new Index();
                index.init();
                index.add("testFile1");
                index.add("testFile2");
                Commit commit = new Commit("", "author claire", "this is for testing");
                String actaul = commit.getDate();
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormater = new SimpleDateFormat("YYYY-MM-dd");
                String expected = dateFormater.format(date);
                assertEquals(expected, actaul);
        }

        @Test
        void testSetNextCommit() throws Exception {
                Index index = new Index();
                index.init();
                index.add("testFile1");
                index.add("testFile2");
                Commit c = new Commit("", "author", "summary");
                String hash = c.createCommit();
                c.setNextCommit("next commit");
                assertTrue("the right file isn't created", Utils.fileExists("objects/" + hash));
                assertEquals("the right file contents aren't there", Utils.writeFileToString("objects/" + hash),
                                "08458148ff577ce57243004fd84317bb14636107\n\nnext commit\nauthor\n" + c.getDate()
                                                + "\nsummary");
        }

        @Test
        void test1Commit() throws Exception {
                Index index = new Index();
                index.init();
                index.add("testFile1");
                index.add("testFile2");
                Commit c = new Commit("", "AUTHOR", "BEST SUMMARY");
                String hash = c.createCommit();
                assertTrue("the right file isn't created", Utils.fileExists("objects/" + hash));
                assertEquals("the right file contents aren't there", Utils.writeFileToString("objects/" + hash),
                                "08458148ff577ce57243004fd84317bb14636107\n\n\nAUTHOR\n" + c.getDate()
                                                + "\nBEST SUMMARY");
        }

        @Test
        void test2Commits() throws Exception {
                Index index = new Index();
                index.init();
                index.add("testFile1");
                index.add("testFile2");
                Commit c1 = new Commit("", "AUTHOR", "first commit");
                String hash1 = c1.createCommit();
                index.add("testFolder1");
                Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
                String hash2 = c2.createCommit();
                c1.setNextCommit(hash2);

                assertEquals("the right file contents for commit 1 aren't there",
                                "08458148ff577ce57243004fd84317bb14636107\n\n" + hash2 + "\nAUTHOR\n"
                                                + c1.getDate() + "\nfirst commit",
                                Utils.writeFileToString("Objects/" + hash1));

                assertEquals("the right file contents for commit 2 aren't there",
                                "b04401f53569e5e3aa6df71f61d326ff03c2efef\n" + hash1 + "\n\nAUTHOR\n"
                                                + c2.getDate() + "\nsecond commit",
                                Utils.writeFileToString("Objects/" + hash2));
        }

        @Test
        void test4Commits() throws Exception {
                Index index = new Index();
                index.init();
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
                                "08458148ff577ce57243004fd84317bb14636107\n\n" + hash2 + "\nAUTHOR\n"
                                                + c1.getDate() + "\nfirst commit",
                                Utils.writeFileToString("Objects/" + hash1));

                assertEquals("the right file contents for commit 2 aren't there",
                                "b04401f53569e5e3aa6df71f61d326ff03c2efef\n" + hash1 + "\n" + hash3 + "\nAUTHOR\n"
                                                + c2.getDate() + "\nsecond commit",
                                Utils.writeFileToString("Objects/" + hash2));

                assertEquals("the right file contents for commit 1 aren't there",
                                "de36afd1be64ddb112c36fdd318b93c9f84217be\n" + hash2 + "\n" + hash4 + "\nAUTHOR\n"
                                                + c3.getDate() + "\nthird commit",
                                Utils.writeFileToString("Objects/" + hash3));

                assertEquals("the right file contents for commit 2 aren't there",
                                "480480a54652fd248dfd5804411e941914d85db0\n" + hash3 + "\n\nAUTHOR\n"
                                                + c4.getDate() + "\nfourth commit",
                                Utils.writeFileToString("Objects/" + hash4));
        }

        @Test
        void testDeletingAndEditingFiles() throws Exception {
                Index index = new Index();
                index.init();
                index.add("testFile1");
                Commit c1 = new Commit("", "AUTHOR", "first commit");
                String hash1 = c1.createCommit();
                index.add("testFolder1");
                index.add("testFile2");
                Commit c2 = new Commit(hash1, "AUTHOR", "second commit");
                String hash2 = c2.createCommit();
                c1.setNextCommit(hash2);
                index.add("testFile3");
                Commit c3 = new Commit(hash2, "AUTHOR", "third commit");
                String hash3 = c3.createCommit();
                c2.setNextCommit(hash3);
                index.add("testFile4");
                Utils.writeStringToFile("testFile2", "TEST FILE 2 NEW CONTENTS");
                index.editFile("testFile2");
                index.deleteFile("testFolder1");
                Commit c4 = new Commit(hash3, "AUTHOR", "fourth commit");
                String hash4 = c4.createCommit();
                c3.setNextCommit(hash4);
                Utils.writeStringToFile("testFile4", "TEST FILE 4 NEW CONTENTS WOW");
                index.deleteFile("testFile3");
                index.editFile("testFile4");
                Commit c5 = new Commit(hash4, "AUTHOR", "fifth commit");
                String hash5 = c5.createCommit();
                c4.setNextCommit(hash5);

                assertEquals("commit 4 has doesn't have the right tree", "f1f3bb2ec45c70fc7f39572d19390fe709c453fa",
                                c4.getTreeHash());
                assertTrue("commit 4 tree doesn't have the right files", Utils.equalContents(
                                Utils.writeFileToString("Objects/" + c4.getTreeHash()),
                                "blob : 4a89db7c03ba322f55facedbc67904d1ecd4dbc8 : testFile2\nblob : 4124e9fe791cc21c4c3cf6a183d7cd3f7715b46c : testFile3\nblob : 9229e4d91535e956c7bc675f11bb2f90920dd6f8 : testFile4\ntree : 2382dc6f858ca7f4497ce948bfbdce527c77c474"));
                assertEquals("commit 5 has doesn't have the right tree", "12ecaab2c4f0ecb53a7b07ab228b1acdf2515248",
                                c5.getTreeHash());
                assertTrue("commit 5 tree doesn't have the right files", Utils.equalContents(
                                Utils.writeFileToString("Objects/" + c5.getTreeHash()),
                                "blob : f0b9227c15cfc2c0dbc1184ebd689a9f27e2e43f : testFile4\n" + //
                                                "blob : 4a89db7c03ba322f55facedbc67904d1ecd4dbc8 : testFile2\n" + //
                                                "tree : 2382dc6f858ca7f4497ce948bfbdce527c77c474"));

                Utils.writeStringToFile("testFile2", "TESTTJKFLGJL:KJ");
                Utils.writeStringToFile("testFile4", "aer:KJ");
        }
}
