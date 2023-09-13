import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Tree {
    
    private File f;

    public Tree () {
        this.f = new File("Tree");
    }

    public void add (String str) throws FileNotFoundException, IOException {
        FileWriter fw = new FileWriter(f, true);
        fw.write(str);
        fw.close();
    }

    public void remove(String sha1) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(f);
        FileWriter fw = new FileWriter(f);
        StringBuilder str = new StringBuilder();
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            int i = line.indexOf(":");
            String sub = line.substring(i+2, i+42);
            if(!sub.equals(sha1)) {
                str.append(line);
            }

            PrintWriter pw = new PrintWriter(f);
            pw.print(str);
            pw.close();
        }
    }
}