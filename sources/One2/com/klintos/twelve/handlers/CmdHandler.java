// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers;

import java.util.Iterator;
import java.io.File;
import com.klintos.twelve.mod.cmd.Version;
import com.klintos.twelve.mod.cmd.Toggle;
import com.klintos.twelve.mod.cmd.Prefix;
import com.klintos.twelve.mod.cmd.Plugins;
import com.klintos.twelve.mod.cmd.Ping;
import com.klintos.twelve.mod.cmd.Mute;
import com.klintos.twelve.mod.cmd.Mods;
import com.klintos.twelve.mod.cmd.Help;
import com.klintos.twelve.mod.cmd.Ghost;
import com.klintos.twelve.mod.cmd.Friends;
import com.klintos.twelve.mod.cmd.Forward;
import com.klintos.twelve.mod.cmd.Damage;
import com.klintos.twelve.mod.cmd.CopyPos;
import com.klintos.twelve.mod.cmd.CopyIP;
import com.klintos.twelve.mod.cmd.ClearChat;
import com.klintos.twelve.mod.cmd.Bind;
import com.klintos.twelve.mod.cmd.Cmd;
import java.util.ArrayList;

public class CmdHandler
{
    private ArrayList<Cmd> cmds;
    private String prefix;
    
    public CmdHandler() {
        this.cmds = new ArrayList<Cmd>();
        this.prefix = ".";
        this.cmds.clear();
        this.cmds.add(new Bind());
        this.cmds.add(new ClearChat());
        this.cmds.add(new CopyIP());
        this.cmds.add(new CopyPos());
        this.cmds.add(new Damage());
        this.cmds.add(new Forward());
        this.cmds.add(new Friends());
        this.cmds.add(new Ghost());
        this.cmds.add(new Help());
        this.cmds.add(new Mods());
        this.cmds.add(new Mute());
        this.cmds.add(new Ping());
        this.cmds.add(new Plugins());
        this.cmds.add(new Prefix());
        this.cmds.add(new Toggle());
        this.cmds.add(new Version());
    }
    
    public void addCmd(final Cmd cmd) {
        this.cmds.add(cmd);
    }
    
    public ArrayList<Cmd> getCmds() {
        return this.cmds;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    public void loadCmds(final String pkg) {
        try {
            final File directory = new File(ClassLoader.getSystemClassLoader().getResource(pkg.replace(".", "/")).toURI());
            if (directory != null && directory.exists()) {
                String[] list;
                for (int length = (list = directory.list()).length, i = 0; i < length; ++i) {
                    String fileName = list[i];
                    if (fileName.contains(".class")) {
                        fileName = fileName.replace(".class", "");
                        final Class clazz = Class.forName(String.valueOf(pkg) + '.' + fileName);
                        if (clazz.getSuperclass() == Cmd.class) {
                            this.cmds.add((Cmd) clazz.newInstance());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Cmd getCmd(final String cmd) {
        Cmd finalCmd = null;
        for (final Cmd CMD : this.cmds) {
            if (CMD.getCmd().equalsIgnoreCase(cmd)) {
                finalCmd = CMD;
            }
        }
        return finalCmd;
    }
}
