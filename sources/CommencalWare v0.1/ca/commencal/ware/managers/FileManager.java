package ca.commencal.ware.managers;

import ca.commencal.ware.Main;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.utils.system.Wrapper;
import ca.commencal.ware.value.*;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {

    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    private static JsonParser jsonParser = new JsonParser();

    public static final File Commencal_DIR = new File(String.format("%s%s%s-%s-%s%s", Wrapper.INSTANCE.mc().mcDataDir, File.separator, Main.NAME, Main.MCVERSION, Main.VERSION, File.separator));

    private static final File MODULES = new File(Commencal_DIR, "modules.json");

    
    public FileManager() {
        if (!Commencal_DIR.exists()) {
            Commencal_DIR.mkdir();
        }
        if (!MODULES.exists()) {
        	saveModules();
        }
        else
        {
        	loadModules();
        }
	}


    public static void loadModules() {
        try {
            BufferedReader loadJson = new BufferedReader(new FileReader(MODULES));
            JsonObject moduleJason = (JsonObject) jsonParser.parse(loadJson);
            loadJson.close();

            for (Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                Module mods = ModuleManager.getModule(entry.getKey());

                if (mods != null) {
                    JsonObject jsonMod = (JsonObject) entry.getValue();
                    boolean enabled = jsonMod.get("toggled").getAsBoolean();

                    if (enabled) {
                        mods.setToggled(true);
                    }

                    if (!mods.getValues().isEmpty()) {
                        for (Value value : mods.getValues()) {
                            if (value instanceof BooleanValue) {
                                boolean bvalue = jsonMod.get(value.getName()).getAsBoolean();
                                value.setValue(bvalue);
                            }
                            if (value instanceof NumberValue) {
                                double dvalue = jsonMod.get(value.getName()).getAsDouble();
                                value.setValue(dvalue);
                            }
                            if (value instanceof ModeValue) {
                            	ModeValue modeValue = (ModeValue) value;
                            	for(Mode mode : modeValue.getModes()) {
                                	boolean mvalue = jsonMod.get(mode.getName()).getAsBoolean();
                            		mode.setToggled(mvalue);
                            	}
                            }
                        }
                    }
                    mods.setKey(jsonMod.get("key").getAsInt());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    

    public static void saveModules() {
        try {
            JsonObject json = new JsonObject();
            
            for (Module module : ModuleManager.getModules()) {
                JsonObject jsonModule = new JsonObject();
                jsonModule.addProperty("toggled", module.isToggled());
                jsonModule.addProperty("key", module.getKey());

                if (!module.getValues().isEmpty()) {
                    for (Value value : module.getValues()) {
                        if (value instanceof BooleanValue) {
                        	jsonModule.addProperty(value.getName(), (Boolean) value.getValue());
                        }
                        if (value instanceof NumberValue) {
                        	jsonModule.addProperty(value.getName(), (Number) value.getValue());
                        }
                        if (value instanceof ModeValue) {
                        	ModeValue modeValue = (ModeValue) value;
                        	for(Mode mode : modeValue.getModes()) {
                        		jsonModule.addProperty(mode.getName(), mode.isToggled());
                        	}
                        }
                    }
                }
                json.add(module.getName(), jsonModule);
            }

            PrintWriter saveJson = new PrintWriter(new FileWriter(MODULES));
            saveJson.println(gsonPretty.toJson(json));
            saveJson.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(File outputFile, List<String> writeContent, boolean newline, boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
            for (final String outputLine : writeContent) {
                writer.write(outputLine);
                writer.flush();
                if(newline) {
                	writer.newLine();
                }
            }
        }
        catch (Exception ex) {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception ex2) {}
        }
    }
	
    public static List<String> read(File inputFile) {
        ArrayList<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                readContent.add(line);
            }
        }
        catch (Exception ex) {
        	try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex2) {}
        }
        return readContent;
    }
}
