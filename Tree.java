import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class Tree {
    
    private File f;
    private boolean filled; //returns false if f has no entries

    public Tree () {
        this.f = new File("Tree");
        filled = false;
    }

    public void add (String str) throws FileNotFoundException, IOException {
        FileWriter fw = new FileWriter(f, true);
        if(filled) {
            fw.write("\n" + str);
        }
        else
            fw.write(str);
            filled = true;
        fw.close();

        Index ind = new Index();
        ind.remove("Tree");
        finalize();
    }

    public void remove(String sha1) throws FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("Tree"));
        String filename = "";

        while(br.ready()) {
            String line = br.readLine();
            int i = line.indexOf(":");
            String check = "";
            if(i != 0)
                check = line.substring(i+2, i+42);
            if(!check.equals(sha1)) {
                if(sb.toString().equals(""))
                    sb.append(line);
                else
                    sb.append("\n" + line);
            }
            else{
                filename = line.substring(i+44);
            }
            
        }
        PrintWriter pw = new PrintWriter("Tree");
        pw.print(sb);
        pw.close();
        br.close();

        Index ind = new Index();
        ind.remove("Tree");
        finalize();
    }

    public void finalize() {
        Blob blob = new Blob(f);
        blob.createBlob();
    }
}