package tk.rektsky.ui;

import net.minecraft.client.*;
import tk.rektsky.Guis.*;
import tk.rektsky.*;
import net.minecraft.client.gui.*;
import java.util.*;
import tk.rektsky.Module.*;

public class HUD
{
    private Minecraft mc;
    private UnicodeFontRenderer fr;
    private ScaledResolution sr;
    
    public HUD() {
        this.mc = Minecraft.getMinecraft();
        this.fr = Client.getFont();
        this.sr = new ScaledResolution(this.mc);
    }
    
    public void draw(final GuiIngame gui) {
        this.sr = new ScaledResolution(this.mc);
        this.ArrayList(gui);
        this.Notification();
    }
    
    public void ArrayList(final GuiIngame gui) {
    }
    
    public void Notification() {
    }
    
    public static class ModuleComparator implements Comparator<Module>
    {
        @Override
        public int compare(final Module first, final Module second) {
            boolean firstHasAdditionalText = false;
            if (!first.getSuffix().equals("")) {
                firstHasAdditionalText = true;
            }
            boolean secondHasAdditionalText = false;
            if (!second.getSuffix().equals("")) {
                secondHasAdditionalText = true;
            }
            final String f = first.name + (firstHasAdditionalText ? (" " + first.getSuffix()) : "");
            final String s = second.name + (secondHasAdditionalText ? (" " + second.getSuffix()) : "");
            if (Client.hud.fr.getStringWidth(f) < Client.hud.fr.getStringWidth(s)) {
                return 1;
            }
            if (Client.hud.fr.getStringWidth(f) > Client.hud.fr.getStringWidth(s)) {
                return -1;
            }
            return 0;
        }
    }
}
