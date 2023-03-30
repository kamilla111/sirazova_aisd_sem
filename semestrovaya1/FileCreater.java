package semestr1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileCreater {
    public static void fileWriter() throws IOException {
        File file = new File("input.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter("input.txt");
        for (int i = 0; i < Math.random()*50 + 50; i++) {
            fileWriter.write((int) (Math.random()*100)
                    + "," + (int) (Math.random()*100)
                    + "," + (int) (Math.random()*Integer.MAX_VALUE - Integer.MAX_VALUE/2));
            for (int j = 0; j < Math.random()*9900 + 99; j++) {
                fileWriter.write(";" + (int) (Math.random()*100)
                        + "," + (int) (Math.random()*100)
                        + "," + (int) (Math.random()*Integer.MAX_VALUE - Integer.MAX_VALUE/2));
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        fileWriter();
    }
}
