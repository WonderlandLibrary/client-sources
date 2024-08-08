package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.modules.target.AuraUtils;
import me.xatzdevelopments.xatz.client.modules.target.Team;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.client.tools.RenderTools;
import me.xatzdevelopments.xatz.client.tools.Utils;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.hackerdetect.Hacker;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class PenisESP extends Module {
	
	@Override
	public ModSetting[] getModSettings() {
		CheckBtnSetting panimation = new CheckBtnSetting("Animation", "panimation");
//		SliderSetting<Number> amount = new SliderSetting<Number>("Amount", ClientSettings.pamount, 1.0, 6, 0.0, ValueFormat.DECIMAL);
//		SliderSetting<Number> spin = new SliderSetting<Number>("Spin", ClientSettings.pspin, 0.1, 9, 0.0, ValueFormat.DECIMAL);
//		SliderSetting<Number> cumSize = new SliderSetting<Number>("Cum Size", ClientSettings.pcumsize, 1.0, 6, 0.0, ValueFormat.DECIMAL);
		return new ModSetting[] { panimation };//, amount, spin, cumSize };
	}

	public PenisESP() {
		super("PenisESP", Keyboard.KEY_NONE, Category.RENDER, "What do you think this does?");
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
	public void onRender() {

        for (final Object o : mc.theWorld.loadedEntityList) {
            if (o instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)o;
                final double n = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double x = n - RenderManager.renderPosX;
                final double n2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double y = n2 - RenderManager.renderPosY;
                final double n3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                final double z = n3 - RenderManager.renderPosZ;
                GL11.glPushMatrix();
                RenderHelper.disableStandardItemLighting();
                this.esp(player, x, y, z);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
            if (ClientSettings.panimation) {
                ++ClientSettings.pamount;
                if (ClientSettings.pamount > 25) {
                    ++ClientSettings.pspin;
                    if (ClientSettings.pspin > 50.0f) {
                    	ClientSettings.pspin = -50.0f;
                    }
                    else if (ClientSettings.pspin < -50.0f) {
                    	ClientSettings.pspin = 50.0f;
                    }
                    ClientSettings.pamount = 0;
                }
                ++ClientSettings.pcumsize;
                if (ClientSettings.pcumsize > 180.0f) {
                	ClientSettings.pcumsize = -180.0f;
                }
                else {
                    if (ClientSettings.pcumsize >= -180.0f) {
                        continue;
                    }
                    ClientSettings.pcumsize = 180.0f;
                }
            }
            else {
            	ClientSettings.pcumsize = 0.0f;
                ClientSettings.pamount = 0;
                ClientSettings.pspin = 0.0f;
            }
        }
    }
    
    public void esp(final EntityPlayer player, final double x, final double y, final double z) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + player.height / 2.0f - 0.22499999403953552, z);
        GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
        GL11.glRotated((player.isSneaking() ? 35 : 0) + ClientSettings.pspin, 1.0f + ClientSettings.pspin, 0.0f, ClientSettings.pcumsize);
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        final Cylinder shaft = new Cylinder();
        shaft.setDrawStyle(100013);
        shaft.draw(0.1f, 0.11f, 0.4f, 25, 20);
        GL11.glColor4f(1.38f, 0.85f, 1.38f, 1.0f);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        final Sphere right = new Sphere();
        right.setDrawStyle(100013);
        right.draw(0.14f, 10, 20);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        final Sphere left = new Sphere();
        left.setDrawStyle(100013);
        left.draw(0.14f, 10, 20);
        GL11.glColor4f(1.35f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
        final Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw(0.13f, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}
