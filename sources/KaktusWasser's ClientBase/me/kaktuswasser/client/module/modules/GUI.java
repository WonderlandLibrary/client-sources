// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.module.Module;
import net.minecraft.client.gui.GuiScreen;

public class GUI extends Module
{
    public GUI() {
        super("GUI", Category.MISC);
    }
    
    @Override
    public void onEnabled() {
        if (GUI.mc.theWorld != null) {
            GUI.mc.displayGuiScreen(Client.getClickGUI());
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onEvent(final Event e) {

    }
}
