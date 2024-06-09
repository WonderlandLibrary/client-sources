package com.client.glowclient;

import net.minecraft.client.gui.inventory.*;
import com.client.glowclient.modules.server.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class xE extends GuiInventory
{
    public final XCarry b;
    
    public xE(final XCarry b) {
        this.b = b;
        super((EntityPlayer)Wrapper.mc.player);
    }
    
    public void onGuiClosed() {
        if (XCarry.M(this.b) || !this.b.k()) {
            super.onGuiClosed();
        }
    }
    
    public void keyTyped(final char c, final int n) throws IOException {
        if (this.b.k() && (n == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(n))) {
            XCarry.M(this.b).set(true);
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
            return;
        }
        super.keyTyped(c, n);
    }
}
