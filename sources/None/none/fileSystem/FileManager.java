/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package none.fileSystem;

import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.Minecraft;
import none.Client;
import none.command.commands.Config;
import none.event.events.EventChat;
import none.module.Category;
import none.module.Checker;
import none.module.Module;
import none.module.modules.combat.AutoAwakeNgineXE;
import none.module.modules.render.NameProtect;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.Targeter;
import none.valuesystem.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {
    private final File clientDir = new File(Minecraft.getMinecraft().mcDataDir, Client.instance.name);
    private final File backupDir = new File(clientDir, "backups");
    private final File saveFile = new File(clientDir, "client.json");
    private final File targeterFile = new File(clientDir, "Target.json");
    private final File nameFile = new File(clientDir, "NameProtect.json");
    private final File Configs = new File(clientDir, "Configs");
    public void checkfile() throws Exception {
    	if (!saveFile.exists()) {
    		save();
    	}
    	if (!targeterFile.exists()) {
    		saveTargeter();
    	}
    	if (!nameFile.exists()) {
    		saveName();
    	}
    	if (!Configs.exists()) {
    		Configs.mkdirs();
    	}
    }
    
    public void save() throws Exception {
        //noinspection ResultOfMethodCallIgnored
        clientDir.mkdirs();

        if (!saveFile.exists() && !saveFile.createNewFile())
            throw new IOException("Failed to create " + saveFile.getAbsolutePath());

        Files.write(toJsonObject().toString().getBytes(StandardCharsets.UTF_8), saveFile);
    }

    private JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();


        {
            JsonObject metadata = new JsonObject();

            metadata.addProperty("clientVersion", Client.instance.version);

            obj.add("metadata", metadata);
        }

        {
            JsonObject modules = new JsonObject();

            for (Module module : Client.instance.moduleManager.getModules()) {
            	if (module.getClass().equals(Checker.class) || module.getClass().equals(AutoAwakeNgineXE.class)) {
                	continue;
                }
                JsonObject moduleObject = new JsonObject();

                moduleObject.addProperty("state", module.isEnabled());
                moduleObject.addProperty("keybind", module.getKeyCode());
                
                if (module.getValues() != null) {

                    JsonObject valueObject = new JsonObject();
                    
                    for (Value value : module.getValues()) {
                        value.addToJsonObject(valueObject);
                    }
                    
                    moduleObject.add("values", valueObject);
                }
                modules.add(module.getName(), moduleObject);
            }
            
            obj.add("modules", modules);
            obj.addProperty("Awakening", Client.ISAwakeNgineXE);
        }

        return obj;
    }
    
    private JsonObject TargetertoJsonObject() {
        JsonObject obj = new JsonObject();


        {
            JsonObject metadata = new JsonObject();

            metadata.addProperty("clientVersion", Client.instance.version);

            obj.add("metadata", metadata);
        }

        {
        	JsonObject moduleObject = new JsonObject();

            moduleObject.addProperty("Player", Targeter.player);
            moduleObject.addProperty("Other", Targeter.other);
            moduleObject.addProperty("Invisible", Targeter.invisible);
            
            obj.add("Target", moduleObject);
        }

        return obj;
    }
    
    private JsonObject NametoJsonObject() {
        JsonObject obj = new JsonObject();
        {
            JsonObject metadata = new JsonObject();

            metadata.addProperty("clientVersion", Client.instance.version);

            obj.add("metadata", metadata);
        }

        {
        	JsonObject moduleObject = new JsonObject();

            moduleObject.addProperty("Name", NameProtect.GetName());
            
            obj.add("NameProtect", moduleObject);
        }

        return obj;
    }
    
    public void saveTargeter() throws Exception {
    	clientDir.mkdirs();
    	
    	if (!targeterFile.exists() && !targeterFile.createNewFile())
            throw new IOException("Failed to create " + targeterFile.getAbsolutePath());
    	Files.write(TargetertoJsonObject().toString().getBytes(StandardCharsets.UTF_8), targeterFile);
    }
    
    public void saveName() throws Exception {
    	clientDir.mkdirs();
    	
    	if (!nameFile.exists() && !nameFile.createNewFile())
            throw new IOException("Failed to create " + nameFile.getAbsolutePath());
    	Files.write(NametoJsonObject().toString().getBytes(StandardCharsets.UTF_8), nameFile);
    }

    public void load() {
        if (!saveFile.exists()) return;

        List<String> backupReasons = new ArrayList<>();

        try {
            JsonObject object = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(saveFile)));
            JsonObject objectTarget = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(targeterFile)));
            JsonObject objectName = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(nameFile)));

            //<editor-fold desc="metadata">
            if (object.has("metadata")) {
                JsonElement metadataElement = object.get("metadata");

                if (metadataElement instanceof JsonObject) {
                    JsonObject metadata = (JsonObject) metadataElement;

                    JsonElement clientVersion = metadata.get("clientVersion");

                    if (clientVersion != null && clientVersion.isJsonPrimitive() && ((JsonPrimitive) clientVersion).isNumber()) {
                        double version = clientVersion.getAsDouble();

                        if (version > Client.instance.version) {
                            backupReasons.add("Version number of save file (" + version + ") is higher than " + Client.instance.version);
                        }
//                        if (version < Client.instance.version) {
//                            backupReasons.add("Version number of save file (" + version + ") is lower than " + Client.instance.version);
//                        }
                    } else {
                        backupReasons.add("'clientVersion' object is not valid.");
                    }
                } else {
                    backupReasons.add("'metadata' object is not valid.");
                }

            } else {
                backupReasons.add("Save file has no metadata");
            }
            //</editor-fold>

            //<editor-fold desc="modules">
            JsonElement modulesElement = object.get("modules");

            if (modulesElement instanceof JsonObject) {
                JsonObject modules = (JsonObject) modulesElement;

                for (Map.Entry<String, JsonElement> stringJsonElementEntry : modules.entrySet()) {
                    Module module = Client.instance.moduleManager.getModule(stringJsonElementEntry.getKey(), true);

                    if (module == null) {
                        backupReasons.add("Module '" + stringJsonElementEntry.getKey() + "' doesn't exist");
                        continue;
                    }
                    
                    if (module.getClass().equals(Checker.class) || module.getClass().equals(AutoAwakeNgineXE.class)) {
                    	continue;
                    }

                    if (stringJsonElementEntry.getValue() instanceof JsonObject) {
                        JsonObject moduleObject = (JsonObject) stringJsonElementEntry.getValue();

                        JsonElement state = moduleObject.get("state");

                        if (state instanceof JsonPrimitive && ((JsonPrimitive) state).isBoolean()) {
                            module.setState(state.getAsBoolean());
                        } else {
                            backupReasons.add("'" + stringJsonElementEntry.getKey() + "/state' isn't valid");
                        }

                        JsonElement keybind = moduleObject.get("keybind");

                        if (keybind instanceof JsonPrimitive && ((JsonPrimitive) keybind).isNumber()) {
                            module.setKeyCode(keybind.getAsInt());
                        } else {
                            backupReasons.add("'" + stringJsonElementEntry.getKey() + "/keybind' isn't valid");
                        }
                        
                        JsonElement valuesElement = moduleObject.get("values");

                            for (Value values : module.getValues()) {

                                if (values == null) {
                                    backupReasons.add("Value owner '" + stringJsonElementEntry.getKey() + "' doesn't exist");
                                    continue;
                                }

                                if (!stringJsonElementEntry.getValue().isJsonObject()) {
                                    backupReasons.add("'values/" + stringJsonElementEntry.getKey() + "' is not valid");
                                    continue;
                                }

                                try {
                                    values.fromJsonObject(valuesElement.getAsJsonObject());
                                } catch (Exception e) {
                                    backupReasons.add("Error while applying 'values/" + stringJsonElementEntry.getKey() + "' " + e.toString());
                                    continue;
                                }
                            }
                    } else {
                        backupReasons.add("Module object '" + stringJsonElementEntry.getKey() + "' isn't valid");
                        continue;
                    }
                }
            } else {
                backupReasons.add("'modules' object is not valid");
            }
            
            JsonElement AwakeningElement = object.get("Awakening");
            if (AwakeningElement instanceof JsonPrimitive) {
            	if (((JsonPrimitive) AwakeningElement).isBoolean()) {
            		Client.ISAwakeNgineXE = AwakeningElement.getAsBoolean();
            	}
            }
            
            if (objectTarget.has("metadata")) {
                JsonElement metadataElement = object.get("metadata");

                if (metadataElement instanceof JsonObject) {
                    JsonObject metadata = (JsonObject) metadataElement;

                    JsonElement clientVersion = metadata.get("clientVersion");

                    if (clientVersion != null && clientVersion.isJsonPrimitive() && ((JsonPrimitive) clientVersion).isNumber()) {
                        double version = clientVersion.getAsDouble();

                        if (version > Client.instance.version) {
                            backupReasons.add("Version number of save file (" + version + ") is higher than " + Client.instance.version);
                        }
                        if (version < Client.instance.version) {
                            backupReasons.add("Version number of save file (" + version + ") is lower than " + Client.instance.version);
                        }
                    } else {
                        backupReasons.add("'clientVersion' object is not valid.");
                    }
                } else {
                    backupReasons.add("'metadata' object is not valid.");
                }

            } else {
                backupReasons.add("Save file has no metadata");
            }
            
            if (!targeterFile.exists()) return;
            
            JsonElement TargeterElement = objectTarget.get("Target");
            
            if (TargeterElement instanceof JsonObject) {
            	JsonObject targeters = (JsonObject) TargeterElement;
            	JsonElement player = targeters.get("Player");
            	JsonElement other = targeters.get("Other");
            	JsonElement invisible = targeters.get("Invisible");
            	
            	if (player instanceof JsonPrimitive && ((JsonPrimitive) player).isBoolean()) {
                    Targeter.setPlayer(player.getAsBoolean());
                }else if (other instanceof JsonPrimitive && ((JsonPrimitive) other).isBoolean()) {
                    Targeter.setOther(other.getAsBoolean());
                }else if (invisible instanceof JsonPrimitive && ((JsonPrimitive) invisible).isBoolean()) {
                    Targeter.setInvisible(invisible.getAsBoolean());
                }
            }
            
            if (!nameFile.exists()) return;
            
            JsonElement NameElement = objectName.get("NameProtect");
            
            if (NameElement instanceof JsonObject) {
            	JsonObject names = (JsonObject) NameElement;
            	JsonElement name = names.get("Name");
            	
            	if (name instanceof JsonPrimitive && ((JsonPrimitive) name).isString()) {
                    NameProtect.setNameProtect(name.getAsString());
                }
            }
            
            //</editor-fold>

            if (backupReasons.size() > 0) {
                backup(backupReasons);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void backup(List<String> backupReasons) {
        System.out.println("Creating backup " + backupReasons);

        try {
            backupDir.mkdirs();

            File out = new File(backupDir, "backup_" + System.currentTimeMillis() + ".zip");
            out.createNewFile();

            StringBuilder reason = new StringBuilder();

            for (String backupReason : backupReasons) {
                reason.append("- ").append(backupReason).append("\n");
            }

            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(out));

            outputStream.putNextEntry(new ZipEntry("client.json"));
            Files.copy(saveFile, outputStream);
            outputStream.closeEntry();

            outputStream.putNextEntry(new ZipEntry("reason.txt"));
            outputStream.write(reason.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.closeEntry();

            outputStream.close();
        } catch (Exception e) {
            System.out.println("Failed to backup");
            e.printStackTrace();
        }

    }
    
    public ArrayList<String> getConfigList(){
    	ArrayList<String>list = new ArrayList();
        File dir = new File(clientDir.getAbsolutePath() + "\\Configs");
        File[] directoryListing = dir.listFiles();
        if(!dir.exists()){
        	dir.mkdir();
        }else
        for(File child : directoryListing){
        	String fileName = child.getName().split("\\.")[0];
        	list.add(fileName);
        } 
    	return list;
    }
    
    public void SaveConfig(String name) {
    	File file = new File(Configs.getAbsolutePath(), name + ".config");
    	try {
    		Files.write(ConfigtoJsonObject().toString().getBytes(StandardCharsets.UTF_8), file);
    	}catch (IOException io) {
    		io.printStackTrace();
    		System.out.println("Error While Save Config (Input/Output)");
    		return;
    	}catch (Exception io) {
    		io.printStackTrace();
    		System.out.println("Error While Save Config");
    		return;
    	}
    	EventChat.addchatmessage("Saved" + " " + name + " " + "Config.");
    	Client.instance.notification.show(new Notification(NotificationType.INFO, "Config System", "Saved" + " " + name + " " + "Config.", 5));
    }
    
    public void RemoveConfig(String name) {
    	File file = new File(Configs.getAbsolutePath(), name + ".config");
    	try {
    		file.delete();
    	}catch (Exception io) {
    		io.printStackTrace();
    		System.out.println("Error While Remove Config");
    		return;
    	}
    }
    
    private JsonObject ConfigtoJsonObject() {
        JsonObject obj = new JsonObject();
        {
            JsonObject modules = new JsonObject();

            for (Module module : Client.instance.moduleManager.getModules()) {
            	
            	if (module.getCategory().equals(Category.RENDER)) {
                	continue;
                }
            	
            	if (module.getClass().equals(Checker.class) || module.getClass().equals(AutoAwakeNgineXE.class)) {
                	continue;
                }
                JsonObject moduleObject = new JsonObject();

                moduleObject.addProperty("state", module.isEnabled());
                
                if (module.getValues() != null) {

                    JsonObject valueObject = new JsonObject();
                    
                    for (Value value : module.getValues()) {
                        value.addToJsonObject(valueObject);
                    }
                    
                    moduleObject.add("values", valueObject);
                }
                modules.add(module.getName(), moduleObject);
            }
            
            obj.add("modules", modules);
        }

        return obj;
    }
    
    public void loadConfig(String name) {
    	File file = new File(Configs.getAbsolutePath(), name + ".config");
    	if (!file.exists())  {
    		EventChat.addchatmessage("No" + " " + name + " " + "Config Found.");
        	Client.instance.notification.show(new Notification(NotificationType.INFO, "Config System", "No" + " " + name + " " + "Config Found.", 5));
    		return;
    	}
    	try {
    		JsonObject object = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(file)));
    		JsonElement modulesElement = object.get("modules");

            if (modulesElement instanceof JsonObject) {
                JsonObject modules = (JsonObject) modulesElement;
                boolean error = false;
                for (Map.Entry<String, JsonElement> stringJsonElementEntry : modules.entrySet()) {
                    Module module = Client.instance.moduleManager.getModule(stringJsonElementEntry.getKey(), true);

                    if (module == null) {
                        continue;
                    }
                    
                    if (module.getCategory().equals(Category.RENDER)) {
                    	continue;
                    }
                    
                    if (module.getClass().equals(Checker.class) || module.getClass().equals(AutoAwakeNgineXE.class)) {
                    	continue;
                    }
                    
                    if (stringJsonElementEntry.getValue() instanceof JsonObject) {
                        JsonObject moduleObject = (JsonObject) stringJsonElementEntry.getValue();

                        JsonElement state = moduleObject.get("state");

                        if (state instanceof JsonPrimitive && ((JsonPrimitive) state).isBoolean()) {
                            module.setState(state.getAsBoolean());
                        }
                        
                        JsonElement valuesElement = moduleObject.get("values");
                        	for (Value values : module.getValues()) {

                                if (values == null) {
                                	continue;
                                }

                                if (!stringJsonElementEntry.getValue().isJsonObject()) {
                                	continue;
                                }

                                try {
                                    values.fromJsonObject(valuesElement.getAsJsonObject());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    error = true;
                                    continue;
                                }
                            }
                    }
                }
                if (error) {
                	EventChat.addchatmessage("Config -> Error While Apply Values");
                	Client.instance.notification.show(Client.notification("Config", "Error While Apply Values"));
                }
            }
    	}catch (IOException io) {
    		io.printStackTrace();
    		System.out.println("Error While Load Config (Input/Output)");
    		return;
    	}catch (Exception io) {
    		io.printStackTrace();
    		System.out.println("Error While Load Config");
    		return;
    	}
    	EventChat.addchatmessage("Loaded" + " " + name + " " + "Config.");
    	Client.instance.notification.show(new Notification(NotificationType.INFO, "Config System", "Loaded" + " " + name + " " + "Config.", 5));
	}
}
