// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import org.lwjgl.input.Keyboard;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.Logger;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Bind extends Command
{
    public Bind() {
        super("bind", "<mod name> <key>");
    }
    
    @Override
    public void run(final String message) {
        final Module module = Client.getModuleManager().getModuleByName(message.split(" ")[1]);
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
        if (Client.getFileManager().getFileByName("modulesconfiguration") != null) {
            Client.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
    }
}
