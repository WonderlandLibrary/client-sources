package cn.Hanabi.hmlProject;

import cn.Hanabi.*;
import java.util.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.command.*;

public class HMLManager
{
    public static ArrayList<HMLHook> hooks;
    
    public static boolean registerHook(final HMLHook hook) {
        HMLManager.hooks.add(hook);
        System.out.println("Hook " + hook.name + " Registered!");
        return true;
    }
    
    public void onClientStarted(final Hanabi instance) {
        for (final HMLHook hook : HMLManager.hooks) {
            hook.onClientStarted(instance);
        }
    }
    
    public void onModuleManagerLoading(final ModManager modManager) {
        for (final HMLHook hook : HMLManager.hooks) {
            hook.onModuleManagerLoading(modManager);
        }
    }
    
    public void onCommandManagerLoading(final CommandManager commandManager) {
        for (final HMLHook hook : HMLManager.hooks) {
            hook.onCommandManagerLoading(commandManager);
        }
    }
    
    static {
        HMLManager.hooks = new ArrayList<HMLHook>();
    }
}
