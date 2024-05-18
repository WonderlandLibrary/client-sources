// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import org.lwjgl.input.Keyboard;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Mod.Mod;
import net.minecraft.client.network.badlion.Utils.ChatUtils;
import net.minecraft.client.network.badlion.Utils.FileManager;

@Info(name = "bind", syntax = { "<module> <key>" }, help = "binds modules to a key")
public class BindCmd extends Cmd
{
    private FileManager fileManager;
    
    public BindCmd() {
        this.fileManager = new FileManager();
    }
    
    @Override
    public void execute(final String[] p0) throws Error {
        if (p0.length < 2) {
            this.syntaxError();
        }
        else {
            final Mod mod = Badlion.getWinter().theMods.getMod(p0[0]);
            if (mod != null) {
                final int key = Keyboard.getKeyIndex(p0[1].toUpperCase());
                if (key != -1) {
                    mod.setBind(key);
                    this.fileManager.saveFiles();
                    ChatUtils.sendMessageToPlayer(String.valueOf(mod.getModName()) + " bind has been set to: " + Keyboard.getKeyName(mod.getBind()));
                }
                else {
                    ChatUtils.sendMessageToPlayer("Key not found!");
                }
            }
            else {
                ChatUtils.sendMessageToPlayer("Unkown module!");
            }
        }
    }
}
