// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.file.files;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.file.BasicFile;
import me.kaktuswasser.client.values.ModeValue;
import me.kaktuswasser.client.values.Value;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ValuesConfiguration extends BasicFile
{
    public ValuesConfiguration() {
        super("valuesconfiguration");
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final Value value : Client.getValueManager().getValues()) {
                if (value == null) {
                    continue;
                }
                if (value instanceof ModeValue) {
                    final ModeValue v = (ModeValue)value;
                    writer.write(String.valueOf(v.getName()) + ":" + v.getStringValue());
                    writer.newLine();
                }
                else {
                    writer.write(String.valueOf(value.getName()) + ":" + value.getValue());
                    writer.newLine();
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void loadFile() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] arguments = line.split(":");
                if (arguments.length == 2) {
                    for (final Value value : Client.getValueManager().getValues()) {
                        if (value == null) {
                            continue;
                        }
                        if (value instanceof ModeValue) {
                            if (!arguments[0].equalsIgnoreCase(value.getName())) {
                                continue;
                            }
                            try {
                                ((ModeValue)value).setStringValue(arguments[1]);
                            }
                            catch (Exception e3) {
                                value.setValue(value.getDefaultValue());
                            }
                        }
                        else {
                            if (!arguments[0].equalsIgnoreCase(value.getName())) {
                                continue;
                            }
                            try {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(Boolean.parseBoolean(arguments[1]));
                                }
                                else if (value.getValue() instanceof Byte) {
                                    value.setValue(Byte.parseByte(arguments[1]));
                                }
                                else if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(arguments[1]));
                                }
                                else if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(arguments[1]));
                                }
                                else if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(arguments[1]));
                                }
                                else if (value.getValue() instanceof Long) {
                                    value.setValue(Long.parseLong(arguments[1]));
                                }
                                else {
                                    if (!(value.getValue() instanceof String) && !(value instanceof ModeValue)) {
                                        continue;
                                    }
                                    value.setValue(arguments[1]);
                                }
                            }
                            catch (Exception e3) {
                                value.setValue(value.getDefaultValue());
                            }
                        }
                    }
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
