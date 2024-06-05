package koks.files.impl;

import koks.Koks;
import koks.files.Files;
import koks.modules.Module;

import java.io.BufferedReader;
import java.io.FileWriter;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 18:04
 */
public class Toggled extends Files {

    public Toggled() {
        super("toggled");
    }

    @Override
    public void writeToFile(FileWriter fileWriter) throws Exception {
        for(Module module : Koks.getKoks().moduleManager.getModules()) {
            fileWriter.write(module.getModuleName() + ":" + module.isToggled() + ":" + module.isVisible() + ":" + module.isBypassed() + "\n");
        }
        fileWriter.close();
    }

    @Override
    public void readFromFile(BufferedReader fileReader) throws Exception {
        String line;
        while((line = fileReader.readLine()) != null) {
            String[] args = line.split(":");
            Module module = Koks.getKoks().moduleManager.getModule(args[0]);
            if(module == null)continue;
                module.setToggled(Boolean.parseBoolean(args[1]));
                module.setVisible(Boolean.parseBoolean(args[2]));
                module.setBypassed(Boolean.parseBoolean(args[3]));
            }



        fileReader.close();
    }
}
