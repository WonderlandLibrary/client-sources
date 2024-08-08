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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class Trajectories extends Module {
	
    private ArrayList<Double[]> linePoints;
    private MovingObjectPosition hit;
	

	public Trajectories() {
		super("Trajectories", Keyboard.KEY_NONE, Category.RENDER, "What do you think this does?");
        this.linePoints = new ArrayList<Double[]>();
        this.hit = null;
	}
	
	

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	
/*	@Override
    public void onUpdate() {
	        this.linePoints.clear();
	        final EntityLivingBase p = (EntityLivingBase)Resilience.getInstance().getWrapper().getMinecraft().thePlayer;
	        if (Resilience.getInstance().getWrapper().getMinecraft().thePlayer.getCurrentEquippedItem() != null && this.isValidItem(Resilience.getInstance().getWrapper().getMinecraft().thePlayer.getCurrentEquippedItem().getItem())) {
	            double x = p.lastTickPosX + (p.posX - p.lastTickPosX) - MathHelper.cos((float)Math.toRadians(p.rotationYaw)) * 0.16f;
	            double y = p.lastTickPosY + (p.posY - p.lastTickPosY) + p.getEyeHeight() - 0.100149011612;
	            double z = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) - MathHelper.sin((float)Math.toRadians(p.rotationYaw)) * 0.16f;
	            float con = 1.0f;
	            if (!(Resilience.getInstance().getWrapper().getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow)) {
	                con = 0.4f;
	            }
	            double motionX = -MathHelper.sin((float)Math.toRadians(p.rotationYaw)) * MathHelper.cos((float)Math.toRadians(p.rotationPitch)) * con;
	            double motionZ = MathHelper.cos((float)Math.toRadians(p.rotationYaw)) * MathHelper.cos((float)Math.toRadians(p.rotationPitch)) * con;
	            double motionY = -MathHelper.sin((float)Math.toRadians(p.rotationPitch)) * con;
	            final double ssum = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
	            motionX /= ssum;
	            motionY /= ssum;
	            motionZ /= ssum;
	            if (Resilience.getInstance().getWrapper().getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
	                float pow = (72000 - Resilience.getInstance().getWrapper().getMinecraft().thePlayer.getItemInUseCount()) / 20.0f;
	                pow = (pow * pow + pow * 2.0f) / 3.0f;
	                if (pow > 1.0f) {
	                    pow = 1.0f;
	                }
	                if (pow <= 0.1f) {
	                    pow = 1.0f;
	                }
	                pow *= 2.0f;
	                pow *= 1.5f;
	                motionX *= pow;
	                motionY *= pow;
	                motionZ *= pow;
	            }
	            else {
	                motionX *= 1.5;
	                motionY *= 1.5;
	                motionZ *= 1.5;
	            }
	            boolean hasHitBlock = false;
	            final double grav = this.getGravity(this.invoker.getCurrentItem().getItem());
	            double lastX = x;
	            double lastY = y;
	            double lastZ = z;
	            final boolean entity = false;
	            int q = 0;
	            while (!hasHitBlock) {
	                lastX = x;
	                lastY = y;
	                lastZ = z;
	                final double rx = x * 1.0 - RenderManager.renderPosX;
	                final double ry = y * 1.0 - RenderManager.renderPosY;
	                final double rz = z * 1.0 - RenderManager.renderPosZ;
	                this.linePoints.add(new Double[] { rx, ry, rz });
	                x += motionX;
	                y += motionY - 0.05;
	                z += motionZ;
	                motionX *= 0.99;
	                motionY *= 0.99;
	                motionZ *= 0.99;
	                motionY -= grav;
	                final Vec3 now = mc.getMinecraft().theWorld.getWorldVec3Pool().getVecFromPool(lastX, lastY, lastZ);
	                final Vec3 after = mc.theWorld.getWorldVec3Pool().getVecFromPool(x, y, z);
	                final MovingObjectPosition possibleHit = mc.getWorld().func_147447_a(now, after, false, true, false);
	                hasHitBlock = (possibleHit != null);
	                if (hasHitBlock) {
	                    this.hit = possibleHit;
	                }
	                ++q;
	            }
	        }
	    
		
		super.onUpdate();
	}

	@Override
	public void onRender() {

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(3.0f);
        GL11.glColor4f(0.2f, 0.2f, 1.0f, 1.0f);
        GL11.glBegin(3);
        for (final Double[] vertex : this.linePoints) {
            GL11.glVertex3d((double)vertex[0], (double)vertex[1], (double)vertex[2]);
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        if (this.hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.19f);
            GL11.glEnableClientState(32884);
            GL11.glVertexPointer(3, 0, Utils.getBox(Utils.getAABB(this.hit.blockX, this.hit.blockY, this.hit.blockZ)));
            GL11.glDrawElements(7, Utils.getSides());
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glDrawElements(1, Utils.getSides());
            GL11.glDisableClientState(32884);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    public double getGravity(final Item item) {
        if (item instanceof ItemBow) {
            return 0.05;
        }
        return 0.03;
    }
    
    public boolean isValidItem(final Item item) {
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl;
    } */
}