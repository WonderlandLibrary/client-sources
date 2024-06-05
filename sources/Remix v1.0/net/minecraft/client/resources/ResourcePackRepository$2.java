package net.minecraft.client.resources;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;

class ResourcePackRepository$2 implements Runnable {
    final /* synthetic */ Minecraft val$var7;
    final /* synthetic */ GuiScreenWorking val$var15;
    
    @Override
    public void run() {
        this.val$var7.displayGuiScreen(this.val$var15);
    }
}