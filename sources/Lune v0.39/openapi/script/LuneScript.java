package me.superskidder.lune.openapi.script;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.openapi.script.api.Values;
import me.superskidder.lune.utils.client.FileUtils;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;

/**
 * @description: 脚本
 * @author: QianXia
 * @create: 2020/11/4 18:08
 **/
public class LuneScript {
    private ScriptEngine scriptEngine;
    public String name, author, version, category;
    public ScriptModule scriptModule;
    public ScriptCommand scriptCommand;
    public Invocable invoke;

    public LuneScript(File scriptFile){
        ScriptEngineManager manager = new ScriptEngineManager();
        scriptEngine = manager.getEngineByName("JavaScript");
        // 读入脚本内容
        String scriptContent = FileUtils.readFile(scriptFile);
        invoke = (Invocable) scriptEngine;

        // 先跑一遍脚本 肯定报错 为了获取变量
        try {
            scriptEngine.eval(scriptContent);
        } catch (ScriptException ignored) {
        }

        // 获取必要信息
        this.name = (String) scriptEngine.get("name");
        this.author = (String) scriptEngine.get("author");
        this.version = (String) scriptEngine.get("version");
        this.category = (String) scriptEngine.get("category");
        String type = (String) scriptEngine.get("scriptType");

        if (type == null) {
            type = "Module";
        }

        if ("Command".equals(type)) {
            this.registerCommand(name, invoke);
        }

        // 从字符串到ModCategory
        ModCategory modCategory = null;
        try {
            modCategory = ModCategory.valueOf(this.category);
        } catch (Exception e) {
            if (scriptCommand == null) {
                e.printStackTrace();
                PlayerUtil.sendMessage("失败的操作去加载脚本：" + scriptFile.getAbsolutePath());
                PlayerUtil.sendMessage("功能分类: " + this.category + " 未找到");
                PlayerUtil.sendMessage("如果Category填写无误请检查语法错误");
                return;
            }
        }

        if ("Module".equals(type)) {
            this.registerModule(name, modCategory, invoke);
        }

        // 传递变量
        if (scriptCommand == null) {
            manager.put("values", new Values(scriptModule));
        }
        manager.put("out", System.out);
        manager.put("mc", Minecraft.getMinecraft());

        // 再次加载 这次不应该出错 如果出错即为加载失败
        try {
            scriptEngine.eval(scriptContent);
        } catch (ScriptException e) {
            e.printStackTrace();
            PlayerUtil.sendMessage("Failed to load script" + scriptFile.getAbsolutePath());
        }
    }

    public void registerCommand(String commandName, Invocable invocable) {
        scriptCommand = new ScriptCommand(commandName, invocable);
        Lune.commandManager.addCMD(scriptCommand, this);
    }

    public void registerModule(String moduleName, ModCategory category, Invocable invocable){
        scriptModule = new ScriptModule(moduleName, category, invocable);
        Lune.moduleManager.addPluginModule(scriptModule, this);
        Lune.moduleManager.sortModules();
    }
}
