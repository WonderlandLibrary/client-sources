package me.napoleon.napoline.junk.openapi.script;

import net.minecraft.client.Minecraft;

import javax.script.ScriptException;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 脚本管理器
 * @author: QianXia
 * @create: 2020/11/4 17:25
 **/
public class ScriptManager {
    public List<NapolineScript> scripts;

    public ScriptManager(){
        this.loadScripts();
    }

    /**
     * 该函数与插件系统共用一个文件夹
     */
    public void loadScripts(){
        File clientDir = new File(Minecraft.getMinecraft().mcDataDir, Napoline.CLIENT_NAME);
        File scriptDir = new File(clientDir, "Plugins");

        File[] scriptsFiles = scriptDir.listFiles((dir, name) -> name.endsWith(".js"));

        if (scriptsFiles == null) {
            return;
        }

        scripts = new ArrayList<>();
        for (File scriptFile : scriptsFiles) {
            NapolineScript script = new NapolineScript(scriptFile);
            scripts.add(script);
        }
    }

    public boolean isScriptEnabled(NapolineScript script){
        for (Object value : ModuleManager.pluginModsList.values()) {
            if (value instanceof NapolineScript) {
                if (value.equals(script)) {
                    return true;
                }
            }
        }

        for (Object value : CommandManager.pluginCommands.values()) {
            if (value instanceof NapolineScript) {
                if (value.equals(script)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setScriptState(NapolineScript script, boolean state){
        AtomicReference<Mod> tempMod = new AtomicReference<>();
        AtomicReference<Command> tempCmd = new AtomicReference<>();

        if (state) {
            ModuleManager.disabledPluginList.forEach((mod, value) -> {
                if (value instanceof NapolineScript) {
                    if (value.equals(script)) {
                        tempMod.set(mod);
                    }
                }
            });

            if (tempMod.get() != null) {
                ModuleManager.disabledPluginList.remove(tempMod.get());
                ModuleManager.pluginModsList.put(tempMod.get(), script);
                ModuleManager.modList.add(tempMod.get());
            }


            CommandManager.disabledPluginCommands.forEach((cmd, value) -> {
                if (value instanceof NapolineScript) {
                    if (value.equals(script)) {
                        tempCmd.set(cmd);
                    }
                }
            });

            CommandManager.disabledPluginCommands.remove(tempCmd.get());
            CommandManager.pluginCommands.put(tempCmd.get(), script);
            CommandManager.commands.add(tempCmd.get());
        } else {
            ModuleManager.pluginModsList.forEach((mod, value) -> {
                if (value instanceof NapolineScript) {
                    if (value.equals(script)) {
                        tempMod.set(mod);
                    }
                }
            });

            if (tempMod.get() != null) {
                ModuleManager.pluginModsList.remove(tempMod.get());
                ModuleManager.modList.remove(tempMod.get());
                ModuleManager.disabledPluginList.put(tempMod.get(), script);
            }

            CommandManager.pluginCommands.forEach((cmd, value) -> {
                if (value instanceof NapolineScript) {
                    if (value.equals(script)) {
                        tempCmd.set(cmd);
                    }
                }
            });

            CommandManager.pluginCommands.remove(tempCmd.get());
            CommandManager.commands.remove(tempCmd.get());
            CommandManager.disabledPluginCommands.put(tempCmd.get(), script);
        }
        Napoline.moduleManager.sortModules();
    }

    public void unloadScript(String scriptName){
        Command removeCommand = null;
        Mod removeModule = null;
        NapolineScript removeScript = null;

        for (Command command : CommandManager.commands) {
            if(command.getName().equals(scriptName)){
                removeCommand = command;
            }
        }

        for (Mod mod : ModuleManager.modList) {
            if (mod.getName().equals(scriptName)) {
                removeModule = mod;
            }
        }

        for (NapolineScript script : this.scripts) {
            if (script.name.equals(scriptName)) {
                removeScript = script;
            }
        }

        if (removeCommand != null) {
            CommandManager.commands.remove(removeCommand);
            CommandManager.pluginCommands.remove(removeCommand);
        }
        if (removeModule != null) {
            ModuleManager.modList.remove(removeModule);
            ModuleManager.pluginModsList.remove(removeModule);
        }
        if (removeScript != null) {
            this.scripts.remove(removeScript);
        }
    }


    public void onClientStart(Napoline lune) {
        for (NapolineScript script : scripts) {
            try {
                script.invoke.invokeFunction("onClientStart", lune);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) { }
        }
    }

    public void onClientStop(Napoline lune) {
        for (NapolineScript script : scripts) {
            try {
                script.invoke.invokeFunction("onClientStop", lune);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) { }
        }
    }
}