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
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;
import us.amerikan.amerikan;
import us.amerikan.events.EventRender;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.RenderHelper;
import us.amerikan.utils.RenderUtils;

public class ItemESP
extends Module {
    public ItemESP() {
        super("ItemESP", "ItemESP", 0, Category.RENDER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("3D OutlineBox");
        options.add("Trace Skid");
        options.add("2D");
        amerikan.setmgr.rSetting(new Setting("ItemBox Design", this, "3D OutlineBox", options));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onDisable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (amerikan.setmgr.getSettingByName("ItemBox Design").getValString().equalsIgnoreCase("Trace Skid")) {
            this.setAddon("Trace Skid");
        } else if (amerikan.setmgr.getSettingByName("ItemBox Design").getValString().equalsIgnoreCase("3D OutlineBox")) {
            this.setAddon("3D OutlineBox");
        } else {
            this.setAddon("2D");
        }
    }

    @EventTarget
    public void onRender(EventRender event) {
        for (Object o2 : ItemESP.mc.theWorld.loadedEntityList) {
            if (!(o2 instanceof EntityItem)) continue;
            EntityItem e2 = (EntityItem)o2;
            EntityItem p2 = (EntityItem)o2;
            if (amerikan.setmgr.getSettingByName("ItemBox Design").getValString().equalsIgnoreCase("Trace Skid")) {
                this.setAddon("Trace Skid");
                this.passive(e2);
                continue;
            }
            if (amerikan.setmgr.getSettingByName("ItemBox Design").getValString().equalsIgnoreCase("3D OutlineBox")) {
                this.setAddon("3D OutlineBox");
                mc.getRenderManager();
                double x2 = p2.lastTickPosX + (p2.posX - p2.lastTickPosX) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                mc.getRenderManager();
                double y2 = p2.lastTickPosY + (p2.posY - p2.lastTickPosY) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                mc.getRenderManager();
                double z2 = p2.lastTickPosZ + (p2.posZ - p2.lastTickPosZ) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                RenderUtils.drawOutlinedEntityESP(x2, y2, z2, p2.width, (double)p2.height + 0.4, 255.0f, 255.0f, 255.0f, 255.0f);
                continue;
            }
            this.setAddon("2D");
            ItemESP.renderTwoDimensionalESP(e2, Color.WHITE.getRGB());
        }
        super.onRender();
    }

    public static void renderTwoDimensionalESP(Entity e2, int color) {
        FontRenderer fr2 = ItemESP.mc.fontRendererObj;
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
        RenderHelper.drawBorderedRect(-e2.width, -e2.height / 2.0f + 0.2f - (float)extraDist, e2.width, e2.height + 0.1f + (float)extraDist, 1.5f, 1426063360 | color, new Color(0, 0, 0, 0).getRGB());
        GL11.glEnable(2929);
        Minecraft.getMinecraft().entityRenderer.func_180436_i();
        GL11.glPopMatrix();
    }

    public void passive(EntityItem entityItem) {
        Color color = Color.ORANGE;
        mc.getRenderManager();
        double x2 = entityItem.lastTickPosX + (entityItem.posX - entityItem.lastTickPosX) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosX;
        mc.getRenderManager();
        double y2 = entityItem.lastTickPosY + (entityItem.posY - entityItem.lastTickPosY) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosY;
        mc.getRenderManager();
        double z2 = entityItem.lastTickPosZ + (entityItem.posZ - entityItem.lastTickPosZ) * (double)ItemESP.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
        this.render(color, x2, y2, z2, entityItem.width);
    }

    private void render(Color color, double x2, double y2, double z2, float width) {
        RenderHelper.drawOutlinedEntityESP(x2, y2, z2, width, 0.5, color.getRed(), color.getGreen(), color.getBlue(), 0.75f);
    }
}

