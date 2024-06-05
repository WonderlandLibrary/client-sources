package cn.Hanabi.hmlProject;

import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.command.*;

public class HMLHook
{
    public static final int HML_VERSION = 0;
    public String name;
    public int version;
    public String author;
    
    public HMLHook(final String name, final int version, final String author) {
        this.name = name;
        this.version = version;
        this.author = author;
    }
    
    public void onClientStarted(final Hanabi hanabi) {
    }
    
    public void onModuleManagerLoading(final ModManager modManager) {
    }
    
    public void onCommandManagerLoading(final CommandManager commandManager) {
    }
}
