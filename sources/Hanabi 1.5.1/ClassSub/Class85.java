package ClassSub;

import java.io.*;
import java.util.*;

public class Class85 extends Class275.Class5
{
    
    
    public Class85(final String s, final boolean b, final boolean b2) {
        super(s, b, b2);
    }
    
    @Override
    public void loadFile() throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getFile()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] array = line.split(":");
            for (int i = 0; i < 2; ++i) {
                array[i].replace(" ", "");
            }
            if (array.length > 2) {
                Class206.registry.add(new Class309(array[0], array[1], array[2]));
            }
            else {
                Class206.registry.add(new Class309(array[0], array[1]));
            }
        }
        bufferedReader.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }
    
    @Override
    public void saveFile() throws IOException {
        final PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile()));
        for (final Class309 class309 : Class206.registry) {
            if (class309.getMask().equals("")) {
                printWriter.println(String.valueOf(class309.getUsername()) + ":" + class309.getPassword());
            }
            else {
                printWriter.println(String.valueOf(class309.getUsername()) + ":" + class309.getPassword() + ":" + class309.getMask());
            }
        }
        printWriter.close();
    }
}
