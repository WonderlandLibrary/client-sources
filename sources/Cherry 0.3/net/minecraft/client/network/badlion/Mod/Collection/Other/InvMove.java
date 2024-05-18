// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Other;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.badlion.Events.EventRender2D;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class InvMove extends Mod
{
    public InvMove() {
        super("InvMove", Category.OTHER);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D event) {
        if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP thePlayer5;
                final EntityPlayerSP thePlayer = thePlayer5 = this.mc.thePlayer;
                thePlayer5.rotationPitch -= 4.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP thePlayer6;
                final EntityPlayerSP thePlayer2 = thePlayer6 = this.mc.thePlayer;
                thePlayer6.rotationPitch += 4.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP thePlayer7;
                final EntityPlayerSP thePlayer3 = thePlayer7 = this.mc.thePlayer;
                thePlayer7.rotationYaw -= 4.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP thePlayer8;
                final EntityPlayerSP thePlayer4 = thePlayer8 = this.mc.thePlayer;
                thePlayer8.rotationYaw += 4.0f;
            }
            Minecraft.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(Minecraft.gameSettings.keyBindForward.getKeyCode());
            Minecraft.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(Minecraft.gameSettings.keyBindBack.getKeyCode());
            Minecraft.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(Minecraft.gameSettings.keyBindLeft.getKeyCode());
            Minecraft.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(Minecraft.gameSettings.keyBindRight.getKeyCode());
            Minecraft.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(Minecraft.gameSettings.keyBindJump.getKeyCode());
        }
    }
}
