// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.andrewsnetwork.icarus.module.Module;
import org.lwjgl.input.Keyboard;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.Icarus;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Bind extends Command
{
    public Bind() {
        super("bind", "<mod name> <key>");
    }
    
    @Override
    public void run(final String message) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
        final Module module = Icarus.getModuleManager().getModuleByName(message.split(" ")[1]);
        if (module == null) {
            try {
                Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 1.0f, 1.0f);
            }
            catch (Exception ex2) {}
            Logger.writeChat("Module \"" + message.split(" ")[1] + "\" wasn't found!");
        }
        else {
            module.setKey(Keyboard.getKeyIndex(message.split(" ")[2].toUpperCase()));
            try {
                Minecraft.getMinecraft().thePlayer.playSound("random.anvil_use", 1.0f, 1.0f);
            }
            catch (Exception ex3) {}
            Logger.writeChat("Module \"" + module.getName() + "\" bound to: " + Keyboard.getKeyName(module.getKey()));
        }
        if (Icarus.getFileManager().getFileByName("modulesconfiguration") != null) {
            Icarus.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
    }
}
