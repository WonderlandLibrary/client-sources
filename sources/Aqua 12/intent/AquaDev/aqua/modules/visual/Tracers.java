// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import events.listeners.EventRender2D;
import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.ColorUtils;
import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import intent.AquaDev.aqua.utils.ColorUtil;
import org.lwjgl.opengl.Display;
import events.listeners.EventPostRender2D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Tracers extends Module
{
    public Tracers() {
        super("Tracers", Type.Visual, "Tracers", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Blur", this, true));
        Aqua.setmgr.register(new Setting("Bloom", this, true));
        Aqua.setmgr.register(new Setting("BloomMode", this, "Glow", new String[] { "Glow", "Shadow" }));
        Aqua.setmgr.register(new Setting("Alpha", this, 20.0, 2.0, 255.0, false));
        Aqua.setmgr.register(new Setting("DistanceToMiddle", this, 100.0, 2.0, 100.0, false));
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
            final int x = Display.getWidth() / 2 / Math.max(Tracers.mc.gameSettings.guiScale, 1);
            final int y = Display.getHeight() / 2 / Math.max(Tracers.mc.gameSettings.guiScale, 1);
            final List<EntityPlayer> playerList = Tracers.mc.theWorld.playerEntities;
            playerList.removeIf(entity -> entity == Tracers.mc.thePlayer || entity.isInvisible());
            final int[] rgb = ColorUtil.getRGB(Aqua.setmgr.getSetting("HUDColor").getColor());
            for (final EntityPlayer player : playerList) {
                final int alpha = (int)(255.0f - Math.min(Tracers.mc.thePlayer.getDistanceToEntity(player), 255.0f));
                GlStateManager.pushMatrix();
                final float angle = RotationUtil.getAngle(player) % 360.0f + 180.0f;
                GlStateManager.translate((float)x, (float)y, 0.0f);
                GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate((float)(-x), (float)(-y), 0.0f);
                final float crossDistance = (float)Aqua.setmgr.getSetting("TracersDistanceToMiddle").getCurrentNumber();
                if (Aqua.setmgr.getSetting("TracersBlur").isState() && Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                    final int n;
                    final int n2;
                    final float n3;
                    final Object o;
                    Blur.drawBlurred(() -> RenderUtil.drawTriangleFilled2((float)(n - 5), n2 + n3, 5.0f, 9.0f, ColorUtils.getColor(o[0], o[1], o[2], 255)), false);
                }
                GlStateManager.translate((float)x, (float)y, 0.0f);
                GlStateManager.rotate(-angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate((float)(-x), (float)(-y), 0.0f);
                GlStateManager.popMatrix();
                GlStateManager.resetColor();
            }
        }
        if (e instanceof EventRender2D) {
            final int x = Display.getWidth() / 2 / Math.max(Tracers.mc.gameSettings.guiScale, 1);
            final int y = Display.getHeight() / 2 / Math.max(Tracers.mc.gameSettings.guiScale, 1);
            final List<EntityPlayer> playerList = Tracers.mc.theWorld.playerEntities;
            playerList.removeIf(entity -> entity == Tracers.mc.thePlayer || entity.isInvisible());
            final int[] rgb = ColorUtil.getRGB(Aqua.setmgr.getSetting("HUDColor").getColor());
            for (final EntityPlayer player : playerList) {
                final int alpha = (int)(255.0f - Math.min(Tracers.mc.thePlayer.getDistanceToEntity(player), 255.0f));
                GlStateManager.pushMatrix();
                final float angle = RotationUtil.getAngle(player) % 360.0f + 180.0f;
                GlStateManager.translate((float)x, (float)y, 0.0f);
                GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate((float)(-x), (float)(-y), 0.0f);
                final float crossDistance = (float)Aqua.setmgr.getSetting("TracersDistanceToMiddle").getCurrentNumber();
                final int alphaBackground = (int)Aqua.setmgr.getSetting("TracersAlpha").getCurrentNumber();
                if (Aqua.setmgr.getSetting("TracersBloom").isState()) {
                    final String currentMode = Aqua.setmgr.getSetting("TracersBloomMode").getCurrentMode();
                    switch (currentMode) {
                        case "Glow": {
                            if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                                final int n5;
                                final int n6;
                                final float n7;
                                final Object o2;
                                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawTriangleFilled2((float)(n5 - 5), n6 + n7, 5.0f, 9.0f, ColorUtils.getColor(o2[0], o2[1], o2[2], 255)), false);
                                break;
                            }
                            break;
                        }
                        case "Shadow": {
                            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                                final int x2;
                                final int y2;
                                final float crossDistance2;
                                Shadow.drawGlow(() -> RenderUtil.drawTriangleFilled2((float)(x2 - 5), y2 + crossDistance2, 5.0f, 9.0f, Color.black.getRGB()), false);
                                break;
                            }
                            break;
                        }
                    }
                }
                RenderUtil.drawTriangleFilled2((float)(x - 5), y + crossDistance, 5.0f, 9.0f, ColorUtils.getColor(rgb[0], rgb[1], rgb[2], alphaBackground));
                GlStateManager.translate((float)x, (float)y, 0.0f);
                GlStateManager.rotate(-angle, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate((float)(-x), (float)(-y), 0.0f);
                GlStateManager.popMatrix();
                GlStateManager.resetColor();
            }
        }
    }
}
