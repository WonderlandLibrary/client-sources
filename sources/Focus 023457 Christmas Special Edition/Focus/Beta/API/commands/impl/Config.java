package Focus.Beta.API.commands.impl;

import Focus.Beta.API.commands.Command;
import Focus.Beta.Client;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.impl.combat.Criticals;
import Focus.Beta.IMPL.Module.impl.combat.Killaura;
import Focus.Beta.IMPL.Module.impl.combat.Velocity;
import Focus.Beta.IMPL.Module.impl.exploit.Disabler;
import Focus.Beta.IMPL.Module.impl.misc.AutoArmor;
import Focus.Beta.IMPL.Module.impl.move.InvMove;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.IMPL.set.Value;
import Focus.Beta.UTILS.helper.Helper;
import net.minecraft.util.EnumChatFormatting;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class Config extends Command {

    private File dir;
    private File dataFile;
    public Config() {
        super("Config", new String[] { "c" }, "", "");
    }

    @Override
    public String execute(String[] var1) {
        if(var1.length > 1) {
            final String type = var1[0];
            final String name = var1[1];
            final String ss = type;
            switch (ss){
                case "save": {
                    Helper.sendMessage("Please wait...");
                    this.dir = new File(String.valueOf(Client.instance.dir));
                    this.dataFile = new File(this.dir, name);
                    if (!this.dataFile.exists()) {
                        try {
                            this.dataFile.createNewFile();
                            Helper.sendMessage("Created ConfigFile: " + name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    final ArrayList<String> toSave = new ArrayList<String>();
                    for (final Module m : Client.instance.getModuleManager().getModules()){
                        toSave.add("Focus_Beta_Version:" + Client.instance.version);
                        toSave.add("M:" + m.getName() + ":" + m.isEnabled());
                        for(final Value v : m.values){
                            if(v instanceof Numbers){
                                final Numbers numbers = (Numbers)v;
                                toSave.add("Number:"+ m.getName() + ":" + numbers.getName() + ":" + numbers.getValue());
                            }else if(v instanceof Option){
                                final Option option = (Option)v;
                                toSave.add("Option:"+ m.getName() + ":" + option.getName() + ":" + option.getValue());
                            }else if(v instanceof Mode){
                                final Mode mode = (Mode)v;
                                toSave.add("Mode:" + m.getName() + ":" + mode.getName() + ":" + mode.getModeAsString());
                            }
                        }
                    }
                    try{
                        final PrintWriter pw = new PrintWriter(this.dataFile);
                        for(final String str : toSave){
                            pw.println(str);
                            Helper.sendMessage("Created Config Content");
                        }
                        pw.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "load": {

                    for(final Module m : Client.instance.getModuleManager().getModules()){
                        m.setEnabled(false);
                    }
                    Helper.sendMessage("Please wait...");
                    this.dir = new File(String.valueOf(Client.instance.dir));
                    this.dataFile = new File(this.dir, name);
                    final ArrayList<String> lines = new ArrayList<>();
                    try{
                        final BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
                        for (String line = reader.readLine(); line != null; line = reader.readLine()){
                            lines.add(line);
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try{
                        for (final String s : lines){
                            final String[] args = s.split(":");
                            if(s.toLowerCase().startsWith("m:")){
                                for(final Module m : Client.instance.getModuleManager().getModules()){
                                    if(m.getName().equalsIgnoreCase(args[1])){
                                        final boolean shouldEnable = Boolean.parseBoolean(args[2]);
                                        if(shouldEnable && !m.isEnabled()){
                                            m.setEnabled(true);
                                        }
                                    }
                                }
                            }
                            if (s.toLowerCase().startsWith("number:")){
                                for(final Module m : Client.instance.getModuleManager().getModules()){
                                    if(m.getName().equalsIgnoreCase(args[1])){
                                        for(final Value v : m.values){
                                            if(!(v instanceof Numbers)){
                                                continue;
                                            }
                                            if(!v.getName().equalsIgnoreCase(args[2])){
                                                continue;
                                            }
                                            final Numbers numbers = (Numbers)v;
                                            numbers.setValue(args[3]);
                                        }
                                    }
                                }
                            }
                            if(s.toLowerCase().startsWith("option:")){
                                for(final Module m : Client.instance.getModuleManager().getModules()){
                                    if(m.getName().equalsIgnoreCase(args[1])){
                                        for(final Value v : m.values){
                                            if(!(v instanceof Option)){
                                                continue;
                                            }
                                            if(!v.getName().equalsIgnoreCase(args[2])){
                                                continue;
                                            }
                                            final Option option = (Option)v;
                                            option.setValue(Boolean.parseBoolean(args[3]));
                                        }
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "delete":{
                    Helper.sendMessage("Please wait...");
                    this.dir = new File(String.valueOf(Client.instance.dir));
                    this.dataFile = new File(this.dir, name);
                    try {
                        this.dataFile.delete();
                        Helper.sendMessage("Deleted Config: " + name);
                     }
                    catch (Exception ex) {}
                    break;
                }
            }
        }else{
            try{
                final String type = var1[0];
                if(type.equalsIgnoreCase("list")){
                    Helper.sendMessage("Config List:");
                    try{

                        for (final File archive : Objects.requireNonNull(Objects.requireNonNull(Client.instance.dir).listFiles())){
                            final String fileName = archive.getName().substring(0, archive.getName().length() - 5);
                            Helper.sendMessage(fileName);
                        }
                    }catch (NullPointerException exc){
                        Helper.sendMessage("No Configs Saved");
                    }
                    Helper.sendMessage(" ");
                }
            }catch (Exception e2){Helper.sendMessage(".c load/save/list/delete <configname>");}
        }
        return null;
    }
}
