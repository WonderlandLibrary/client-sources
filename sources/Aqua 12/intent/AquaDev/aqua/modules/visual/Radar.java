// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import java.util.Iterator;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.Gui;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import events.listeners.EventPostRender2D;
import events.listeners.EventRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Radar extends Module
{
    public static int mouseX;
    public static int mouseY;
    public static int lastMouseX;
    public static int lastMouseY;
    public static boolean dragging;
    public static int posX;
    public static int posY;
    private static double Alpha;
    
    public Radar() {
        super("Radar", Type.Overlay, "Radar", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("PosY", this, 23.0, 0.0, 325.0, false));
        Aqua.setmgr.register(new Setting("Size", this, 55.0, 18.0, 300.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Glow", new String[] { "Glow", "Shadow" }));
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
        if (e instanceof EventRender2D) {
            renderRadarShaders(Radar.mouseX, Radar.mouseY);
        }
        if (e instanceof EventPostRender2D) {
            renderRadar(Radar.mouseX, Radar.mouseY);
        }
    }
    
    public static void renderRadar(final int mouseX, final int mouseY) {
        final double width = (int)Aqua.setmgr.getSetting("RadarSize").getCurrentNumber();
        final double height = (int)Aqua.setmgr.getSetting("RadarSize").getCurrentNumber();
        Radar.posY = (int)Aqua.setmgr.getSetting("RadarPosY").getCurrentNumber();
        if (Radar.dragging) {
            Radar.mouseX = mouseX - Radar.lastMouseX;
            Radar.mouseY = mouseY - Radar.lastMouseY;
        }
        final int posX = Radar.posX + Radar.mouseX;
        final int posY = Radar.posY + Radar.mouseY;
        RenderUtil.drawRoundedRect2Alpha(posX, posY + 2, width, height - 2.0, 3.0, new Color(22, 22, 22, 60));
        final double halfWidth = width / 2.0 + 0.5;
        final double halfHeight = height / 2.0 - 0.5;
        Gui.drawRect2(posX + halfWidth, posY + halfHeight, posX + halfWidth + 1.0, posY + halfHeight + 1.0, ColorUtils.getColorAlpha(Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0), 255).getRGB());
        for (final EntityPlayer player : Radar.mc.theWorld.playerEntities) {
            if (player != Radar.mc.thePlayer) {
                final double playerX = player.posX;
                final double playerZ = player.posZ;
                final double diffX = playerX - Radar.mc.thePlayer.posX;
                final double diffZ = playerZ - Radar.mc.thePlayer.posZ;
                if (MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ) >= 50.0f) {
                    continue;
                }
                final double clampedX = MathHelper.clamp_double(diffX, -halfWidth + 3.0, halfWidth - 3.0);
                final double clampedY = MathHelper.clamp_double(diffZ, -halfHeight + 5.0, halfHeight - 3.0);
                Gui.drawRect2(posX + halfWidth + clampedX, posY + halfHeight + clampedY, posX + halfWidth + clampedX + 1.0, posY + halfHeight + clampedY + 1.0, Color.white.getRGB());
            }
        }
    }
    
    public static void renderRadarShaders(final int mouseX, final int mouseY) {
        final double width = (int)Aqua.setmgr.getSetting("RadarSize").getCurrentNumber();
        final double height = (int)Aqua.setmgr.getSetting("RadarSize").getCurrentNumber();
        Radar.posY = (int)Aqua.setmgr.getSetting("RadarPosY").getCurrentNumber();
        if (Radar.dragging) {
            Radar.mouseX = mouseX - Radar.lastMouseX;
            Radar.mouseY = mouseY - Radar.lastMouseY;
        }
        final int posX = Radar.posX + Radar.mouseX;
        final int posY = Radar.posY + Radar.mouseY;
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(posX, posY + 2, width, height - 2.0, 3.0, new Color(22, 22, 22, 100).getRGB()), false);
        }
        if (Aqua.setmgr.getSetting("RadarMode").getCurrentMode().equalsIgnoreCase("Glow")) {
            if (Aqua.moduleManager.getModuleByName("Arraylist").isToggled()) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect(posX, posY + 2, width, height - 2.0, 3.0, ColorUtils.getColorAlpha(Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0), 255).getRGB()), false);
            }
        }
        else if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
            Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(posX, posY + 2, width, height - 2.0, 3.0, Color.black.getRGB()), false);
        }
    }
    
    static {
        Radar.posX = 2;
        Radar.posY = 23;
        Radar.Alpha = 0.0;
    }
}
