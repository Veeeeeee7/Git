import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Index index = new Index();
        // index.init();

        // Tree tree = new Tree();
        // tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");
        // tree.add("blob : 22343k2jn2njijfinein322i3n3in3i333in3333 : blok.txt");
        // tree.add("blob : 1263746536521765436527635421890jvncdeixs : 2i3nkd");

        // tree.remove("6c834d62d7524442cdd32ab209c9b2c083c0a474");

        // tree.finalize();
        // Commit commit = new Commit("someParents", "author claire", "this is for
        // testing");
        // commit.createCommit();

        Tree tree = new Tree();

        tree.add("blob : 6c834d62d7524442cdd32ab209c9b2c083c0a474 : BOB.txt");

        tree.finalize();
    }
}
