import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;

public class Index {
    protected String[] fileNames;
    protected File[] files;

    public Index() {
        File folder = new File("C:\\Users\\helen\\Documents\\GitHub\\Honors Topics\\Git");
        System.out.println(folder.getAbsolutePath());
        fileNames = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return !name.startsWith(".") && file.isDirectory();
            }
        });
    }

    public void init() {
        for (int i = 0; i < fileNames.length; i++) {

        }
    }
}
