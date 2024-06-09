/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import us.amerikan.amerikan;
import us.amerikan.events.EventRender;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.RenderHelper;
import us.amerikan.utils.RenderUtils;

public class PlayerESP
extends Module {
    public PlayerESP() {
        super("PlayerESP", "", 0, Category.RENDER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Default");
        options.add("CSGO");
        options.add("Shader");
        amerikan.setmgr.rSetting(new Setting("Player Design", this, "Default", options));
        amerikan.setmgr.rSetting(new Setting("Chams", this, false));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate e2) {
        if (amerikan.setmgr.getSettingByName("Player Design").getValString().equalsIgnoreCase("Default")) {
            this.setAddon("Default");
        }
        if (amerikan.setmgr.getSettingByName("Player Design").getValString().equalsIgnoreCase("CSGO")) {
            this.setAddon("CSGO");
        }
    }

    protected static int getHealthColor(EntityLivingBase e2) {
        int hp2 = (int)e2.getHealth();
        if (hp2 > 15) {
            return new Color(85, 255, 85, 255).getRGB();
        }
        if (hp2 > 10) {
            return new Color(255, 255, 85, 255).getRGB();
        }
        if (hp2 > 5) {
            return new Color(255, 170, 0, 255).getRGB();
        }
        if (hp2 < 2) {
            return new Color(170, 0, 0, 255).getRGB();
        }
        return new Color(255, 85, 85, 255).getRGB();
    }

    public static void renderTwoDimensionalESP(Entity e2, int color) {
        FontRenderer fr2 = PlayerESP.mc.fontRendererObj;
        GL11.glPushMatrix();
        GL11.glTranslated(e2.posX - RenderManager.renderPosX, e2.posY - RenderManager.renderPosY + (double)(e2.height / 2.0f), e2.posZ - RenderManager.renderPosZ);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft();
        GL11.glRotatef(-Minecraft.thePlayer.rotationYaw, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft();
        GL11.glRotatef(Minecraft.thePlayer.rotationPitch, 1.0f, 0.0f, 0.0f);
        Minecraft.getMinecraft().entityRenderer.func_175072_h();
        GL11.glDisable(2929);
        double extraDist = 0.2;
        RenderHelper.drawBorderedRect(-e2.width, -e2.height / 2.0f - (float)extraDist, e2.width, e2.height - 0.8f + (float)extraDist, 1.5f, 1426063360 | color, new Color(0, 0, 0, 0).getRGB());
        RenderHelper.drawBorderedRect(e2.width + 0.15f, -e2.height / 2.0f - (float)extraDist, e2.width + 0.05f, e2.height - 0.8f + (float)extraDist, 1.5f, 1426063360 | color, new Color(0, 0, 0, 0).getRGB());
        Gui.drawRect(e2.width + 0.15f, -e2.height / 2.0f - (float)extraDist, e2.width + 0.05f, (double)(-e2.height / 2.0f - (float)extraDist) + (double)((EntityLivingBase)e2).getHealth() / 8.7, PlayerESP.getHealthColor((EntityLivingBase)e2));
        GL11.glEnable(2929);
        Minecraft.getMinecraft().entityRenderer.func_180436_i();
        GL11.glPopMatrix();
    }

    @EventTarget
    public void onRender(EventRender e2) {
        if (this.isToggled()) {
            for (Object autism : PlayerESP.mc.theWorld.loadedEntityList) {
                if (!(autism instanceof EntityPlayer)) continue;
                if (autism == Minecraft.thePlayer) continue;
                EntityPlayer p2 = (EntityPlayer)autism;
                if (amerikan.setmgr.getSettingByName("Player Design").getValString().equalsIgnoreCase("Default")) {
                    this.setAddon("Default");
                    Color color = Color.ORANGE;
                    mc.getRenderManager();
                    double x2 = p2.lastTickPosX + (p2.posX - p2.lastTickPosX) * (double)PlayerESP.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                    mc.getRenderManager();
                    double y2 = p2.lastTickPosY + (p2.posY - p2.lastTickPosY) * (double)PlayerESP.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                    mc.getRenderManager();
                    double z2 = p2.lastTickPosZ + (p2.posZ - p2.lastTickPosZ) * (double)PlayerESP.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                    RenderUtils.drawOutlinedEntityESP(x2, y2, z2, (double)p2.width - 0.2, (double)p2.height + 0.2, 255.0f, 255.0f, 255.0f, 255.0f);
                    continue;
                }
                if (!amerikan.setmgr.getSettingByName("Player Design").getValString().equalsIgnoreCase("CSGO")) continue;
                this.setAddon("CSGO");
                PlayerESP.renderTwoDimensionalESP(p2, new Color(255, 255, 255, 255).getRGB());
            }
        }
        super.onRender();
    }

    protected static String getWeapon(EntityPlayer e2) {
        if (e2 != null) {
            return e2.getItemInUse().getDisplayName().toUpperCase();
        }
        return null;
    }

    private int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + (long)offset) % (long)speed;
        return Color.getHSBColor(hue /= (float)speed, 1.0f, 1.0f).getRGB();
    }
}

