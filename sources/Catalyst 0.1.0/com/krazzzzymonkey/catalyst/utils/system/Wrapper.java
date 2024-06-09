// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.system;

import net.minecraft.client.settings.GameSettings;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Wrapper
{
    public static volatile /* synthetic */ Wrapper INSTANCE;
    
    public WorldClient world() {
        return Wrapper.INSTANCE.mc().world;
    }
    
    public EntityPlayerSP player() {
        return Wrapper.INSTANCE.mc().player;
    }
    
    public FontRenderer fontRenderer() {
        return Wrapper.INSTANCE.mc().fontRenderer;
    }
    
    public void sendPacket(final Packet lIllIllIlIIlIlI) {
        this.player().connection.sendPacket(lIllIllIlIIlIlI);
    }
    
    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    static {
        Wrapper.INSTANCE = new Wrapper();
    }
    
    public void copy(final String lIllIllIlIIIlII) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(lIllIllIlIIIlII), null);
    }
    
    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }
}
