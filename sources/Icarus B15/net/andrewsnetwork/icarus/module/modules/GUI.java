// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.client.gui.GuiScreen;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.module.Module;

public class GUI extends Module
{
    public GUI() {
        super("GUI", Category.MISC);
    }
    
    @Override
    public void onEnabled() {
        if (GUI.mc.theWorld != null) {
            GUI.mc.displayGuiScreen(Icarus.getClickGUI());
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EatMyAssYouFuckingDecompiler) {
            OutputStreamWriter request = new OutputStreamWriter(System.out);
            try {
                request.flush();
            }
            catch (IOException ex) {
                return;
            }
            finally {
                request = null;
            }
            request = null;
        }
    }
}
