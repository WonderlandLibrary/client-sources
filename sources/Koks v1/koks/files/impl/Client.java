package koks.files.impl;

import koks.Koks;
import koks.files.Files;
import koks.gui.clickgui.commonvalue.CommonValue;
import koks.gui.clickgui.commonvalue.elements.ElementColorPicker;
import koks.utilities.value.ColorPicker;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 02:00
 */
public class Client extends Files {

    public Client() {
        super("client");
    }

    @Override
    public void writeToFile(FileWriter fileWriter) throws Exception {
        int hue = 0;
        float mouseX = 0,mouseY = 0;
        for(CommonValue value : Koks.getKoks().commonValueManager.COMMON_SETTINGS) {
            hue = value.getHue();
            mouseX = value.getX();
            mouseY = value.getY();
        }
        fileWriter.write("clientcolor:" + Koks.getKoks().client_color.getRed() + ":" + Koks.getKoks().client_color.getGreen() + ":" + Koks.getKoks().client_color.getBlue() + ":" + hue + ":" + mouseX + ":" + mouseY + "\n");

        fileWriter.close();
    }

    @Override
    public void readFromFile(BufferedReader fileReader) throws Exception {
        String line;
        while((line = fileReader.readLine()) != null) {
            String[] args = line.split(":");
            if(args[0].equalsIgnoreCase("clientcolor")) {
                Koks.getKoks().client_color = new Color(Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                for(CommonValue value : Koks.getKoks().commonValueManager.COMMON_SETTINGS){
                    value.setColor(new Color(Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    value.setHue(Integer.parseInt(args[4]));
                    value.setX(Float.parseFloat(args[5]));
                    value.setY(Float.parseFloat(args[6]));
                }
            }
        }
        fileReader.close();
    }
}
