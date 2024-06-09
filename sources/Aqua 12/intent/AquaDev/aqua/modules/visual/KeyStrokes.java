// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import events.listeners.EventRender2D;
import events.listeners.EventPostRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class KeyStrokes extends Module
{
    public KeyStrokes() {
        super("KeyStrokes", Type.Visual, "KeyStrokes", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Left|Right", this, false));
        Aqua.setmgr.register(new Setting("ClientColor", this, true));
        Aqua.setmgr.register(new Setting("PosY", this, 120.0, 0.0, 400.0, false));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPostRender2D) {
            this.render();
        }
        if (e instanceof EventRender2D) {
            this.renderShaders();
        }
    }
    
    public void renderShaders() {
        final int posX = 4;
        final int posY = (int)Aqua.setmgr.getSetting("KeyStrokesPosY").getCurrentNumber();
        final int width = 20;
        final int height = 20;
        final int cornerRadius = 3;
        final boolean lmbPressed = KeyStrokes.mc.gameSettings.keyBindAttack.pressed;
        final boolean rmbPressed = KeyStrokes.mc.gameSettings.keyBindUseItem.pressed;
        final boolean spacePressed = KeyStrokes.mc.gameSettings.keyBindJump.pressed;
        final boolean aPressed = KeyStrokes.mc.gameSettings.keyBindLeft.pressed;
        final boolean sPressed = KeyStrokes.mc.gameSettings.keyBindBack.pressed;
        final boolean dPressed = KeyStrokes.mc.gameSettings.keyBindRight.pressed;
        final boolean wPressed = KeyStrokes.mc.gameSettings.keyBindForward.pressed;
        boolean blurEnabled = false;
        final boolean shadow = Aqua.moduleManager.getModuleByName("Shadow").isToggled();
        final boolean glow = Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled();
        blurEnabled = Aqua.moduleManager.getModuleByName("Blur").isToggled();
        final Color pressed = RenderUtil.getColorAlpha(Aqua.setmgr.getSetting("KeyStrokesClientColor").isState() ? new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB() : new Color(this.getColor2()).getRGB(), 120);
        final int color = Color.black.getRGB();
        if (blurEnabled) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadius, pressed.getRGB()), false);
        }
        if (aPressed) {
            if (glow) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadius, pressed.getRGB()), false);
            }
        }
        else if (shadow) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY, width, height, cornerRadius, color), false);
        }
        if (blurEnabled) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX + 25, posY, width, height, cornerRadius, pressed.getRGB()), false);
        }
        if (sPressed) {
            if (glow) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX + 25, posY, width, height, cornerRadius, pressed.getRGB()), false);
            }
        }
        else if (shadow) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX + 25, posY, width, height, cornerRadius, color), false);
        }
        if (blurEnabled) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX + 50, posY, width, height, cornerRadius, pressed.getRGB()), false);
        }
        if (dPressed) {
            if (glow) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX + 50, posY, width, height, cornerRadius, pressed.getRGB()), false);
            }
        }
        else if (shadow) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX + 50, posY, width, height, cornerRadius, color), false);
        }
        if (blurEnabled) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX + 25, posY - 25, width, height, cornerRadius, pressed.getRGB()), false);
        }
        if (wPressed) {
            if (glow) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX + 25, posY - 25, width, height, cornerRadius, pressed.getRGB()), false);
            }
        }
        else if (shadow) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX + 25, posY - 25, width, height, cornerRadius, color), false);
        }
        if (Aqua.setmgr.getSetting("KeyStrokesLeft|Right").isState()) {
            if (blurEnabled) {
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 15, height - 5, cornerRadius, pressed.getRGB()), false);
            }
            if (lmbPressed) {
                if (glow) {
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 15, height - 5, cornerRadius, pressed.getRGB()), false);
                }
            }
            else if (shadow) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 15, height - 5, cornerRadius, color), false);
            }
            if (blurEnabled) {
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX + 36, posY + 25, width + 15, height - 5, cornerRadius, pressed.getRGB()), false);
            }
            if (rmbPressed) {
                if (glow) {
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX + 36, posY + 25, width + 15, height - 5, cornerRadius, pressed.getRGB()), false);
                }
            }
            else if (shadow) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX + 36, posY + 25, width + 15, height - 5, cornerRadius, color), false);
            }
            if (blurEnabled) {
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX, posY + 45, width + 50, height - 5, cornerRadius, pressed.getRGB()), false);
            }
            if (spacePressed) {
                if (glow) {
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX, posY + 45, width + 50, height - 5, cornerRadius, pressed.getRGB()), false);
                }
            }
            else if (shadow) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY + 45, width + 50, height - 5, cornerRadius, color), false);
            }
        }
        else {
            if (blurEnabled) {
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 50, height - 5, cornerRadius, pressed.getRGB()), false);
            }
            if (spacePressed) {
                if (glow) {
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 50, height - 5, cornerRadius, pressed.getRGB()), false);
                }
            }
            else if (shadow) {
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY + 25, width + 50, height - 5, cornerRadius, color), false);
            }
        }
    }
    
    public void render() {
        final int posX = 4;
        final int posY = (int)Aqua.setmgr.getSetting("KeyStrokesPosY").getCurrentNumber();
        final int width = 20;
        final int height = 20;
        final int cornerRadius = 3;
        final boolean lmbPressed = KeyStrokes.mc.gameSettings.keyBindAttack.pressed;
        final boolean rmbPressed = KeyStrokes.mc.gameSettings.keyBindUseItem.pressed;
        final boolean spacePressed = KeyStrokes.mc.gameSettings.keyBindJump.pressed;
        final boolean aPressed = KeyStrokes.mc.gameSettings.keyBindLeft.pressed;
        final boolean sPressed = KeyStrokes.mc.gameSettings.keyBindBack.pressed;
        final boolean dPressed = KeyStrokes.mc.gameSettings.keyBindRight.pressed;
        final boolean wPressed = KeyStrokes.mc.gameSettings.keyBindForward.pressed;
        final Color pressed = RenderUtil.getColorAlpha(Aqua.setmgr.getSetting("KeyStrokesClientColor").isState() ? new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB() : new Color(this.getColor2()).getRGB(), 120);
        if (aPressed) {
            RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadius, pressed);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(posX, posY, width, height, cornerRadius, new Color(20, 20, 20, 70));
        }
        if (sPressed) {
            RenderUtil.drawRoundedRect2Alpha(posX + 25, posY, width, height, cornerRadius, pressed);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(posX + 25, posY, width, height, cornerRadius, new Color(20, 20, 20, 70));
        }
        if (dPressed) {
            RenderUtil.drawRoundedRect2Alpha(posX + 50, posY, width, height, cornerRadius, pressed);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(posX + 50, posY, width, height, cornerRadius, new Color(20, 20, 20, 70));
        }
        if (wPressed) {
            RenderUtil.drawRoundedRect2Alpha(posX + 25, posY - 25, width, height, cornerRadius, pressed);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(posX + 25, posY - 25, width, height, cornerRadius, new Color(20, 20, 20, 70));
        }
        if (Aqua.setmgr.getSetting("KeyStrokesLeft|Right").isState()) {
            if (lmbPressed) {
                RenderUtil.drawRoundedRect2Alpha(posX, posY + 25, width + 15, height - 5, cornerRadius, pressed);
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(posX, posY + 25, width + 15, height - 5, cornerRadius, new Color(20, 20, 20, 70));
            }
            if (rmbPressed) {
                RenderUtil.drawRoundedRect2Alpha(posX + 36, posY + 25, width + 15, height - 5, cornerRadius, pressed);
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(posX + 36, posY + 25, width + 15, height - 5, cornerRadius, new Color(20, 20, 20, 70));
            }
            if (spacePressed) {
                RenderUtil.drawRoundedRect2Alpha(posX, posY + 45, width + 50, height - 5, cornerRadius, pressed);
            }
            else {
                RenderUtil.drawRoundedRect2Alpha(posX, posY + 45, width + 50, height - 5, cornerRadius, new Color(20, 20, 20, 70));
            }
        }
        else if (spacePressed) {
            RenderUtil.drawRoundedRect2Alpha(posX, posY + 25, width + 50, height - 5, cornerRadius, pressed);
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(posX, posY + 25, width + 50, height - 5, cornerRadius, new Color(20, 20, 20, 70));
        }
        Aqua.INSTANCE.comfortaa3.drawString("A", (float)(posX + 6), (float)(posY + 5), -1);
        Aqua.INSTANCE.comfortaa3.drawString("S", (float)(posX + 32), (float)(posY + 5), -1);
        Aqua.INSTANCE.comfortaa3.drawString("D", (float)(posX + 56), (float)(posY + 5), -1);
        Aqua.INSTANCE.comfortaa3.drawString("W", (float)(posX + 30), (float)(posY - 20), -1);
        if (Aqua.setmgr.getSetting("KeyStrokesLeft|Right").isState()) {
            Aqua.INSTANCE.comfortaa3.drawString("LMB", (float)(posX + 6), (float)(posY + 27), -1);
            Aqua.INSTANCE.comfortaa3.drawString("RMB", (float)(posX + 43), (float)(posY + 27), -1);
            Aqua.INSTANCE.comfortaa3.drawString("Space", (float)(posX + 20), (float)(posY + 47), -1);
        }
        else {
            Aqua.INSTANCE.comfortaa3.drawString("Space", (float)(posX + 20), (float)(posY + 27), -1);
        }
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("KeyStrokesColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
}
