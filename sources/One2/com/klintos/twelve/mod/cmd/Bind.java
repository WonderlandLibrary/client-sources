// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.utils.FileUtils;
import org.lwjgl.input.Keyboard;
import com.klintos.twelve.Twelve;

public class Bind extends Cmd
{
    public Bind() {
        super("bind", "Binds a mod to a key.", "bind add <Mod> <Key> / bind del <Mod>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            if (args[1].equalsIgnoreCase("add")) {
                final Mod mod = Twelve.getInstance().getModHandler().getMod(args[2]);
                final int key = Keyboard.getKeyIndex(args[3].toUpperCase());
                mod.setModKeybind(key);
                this.addMessage(String.valueOf(mod.getModName()) + " has been bound to key " + Keyboard.getKeyName(key) + ".");
            }
            else if (args[1].equalsIgnoreCase("del")) {
                final Mod mod = Twelve.getInstance().getModHandler().getMod(args[2]);
                mod.setModKeybind(0);
                this.addMessage(String.valueOf(mod.getModName()) + " has been unbound from all keys.");
            }
            else {
                this.runHelp();
            }
            FileUtils.saveBinds();
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
