package me.superskidder.lune.openapi.script;

import javax.script.Invocable;
import javax.script.ScriptException;

import me.superskidder.lune.Lune;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.client.ErrorUtil;
import me.superskidder.lune.utils.player.PlayerUtil;

/**
 * @author: QianXia
 * @description: 命令
 * @create: 2021/01/01-21:09
 */
public class ScriptCommand extends Command {
    private Invocable invoke;
    private String name;

    public ScriptCommand(String scriptCommandName, Invocable invokable){
        super(scriptCommandName);
        this.name = scriptCommandName;
        this.invoke = invokable;
    }

    @Override
    public void run(String[] args) {
        try {
            invoke.invokeFunction("run", args);
        } catch (ScriptException e) {
            e.printStackTrace();
            ErrorUtil.printException(e);
        } catch (NoSuchMethodException e) {
            Lune.scriptManager.unloadScript(this.name);
            PlayerUtil.sendMessage("脚本" + this.name + "没有必要的函数，已自动卸载");
        }
    }
}
