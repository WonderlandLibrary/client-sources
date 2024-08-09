package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventAttackSilent;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ModuleAnnotation(name = "Particle", category = Category.RENDER)
public class Particle
        extends Module {
    ArrayList<partical> particals = new ArrayList();
    public static ModeSetting modeSetting = new ModeSetting("Particle-Mode", "Client", "Client", "Custom");
    public static ColorSetting color = new ColorSetting("Particles-Color", new Color(255, 255, 255).getRGB(), () -> modeSetting.is("Custom"));
    public static NumberSetting deleteAfter = new NumberSetting("Delete", 1500.0f, 100.0f, 10000.0f, 1.0f);

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.world != null) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity != null) {
                    EntityLivingBase player = (EntityLivingBase) entity;
                    String name = player.getName();
                    if (( player.hurtTime ) > 0 && player != mc.player) {
                        this.particals.add(new partical(player.posX + (double) MathUtility.randomNumber(-0.05f, 0.05f), MathUtility.randomNumber((float)(player.posY + (double)player.height), (float)player.posY), player.posZ + (double)MathUtility.randomNumber(-0.05f, 0.05f)));
                     //   this.particals.add(new partical(MathUtility.randomNumber((float)(player.posX - (double)player.width), (float)(player.posX + (double)0.1f)), MathUtility.randomNumber((float)(player.posY + (double)player.height), (float)(player.posY + (double)0.1f)), MathUtility.randomNumber((float)(player.posZ - (double)player.width), (float)(player.posZ + (double)0.1f))));
                        this.particals.add(new partical(MathUtility.randomNumber((float)(player.posX + (double)player.width), (float)(player.posX + (double)0.1f)), MathUtility.randomNumber((float)(player.posY + (double)player.height), (float)(player.posY + (double)0.1f)), MathUtility.randomNumber((float)(player.posZ + (double)player.width), (float)(player.posZ + (double)0.1f))));
                      //  this.particals.add(new partical(player.posX + (double)MathUtility.randomNumber(-0.05f, 0.05f), MathUtility.randomNumber((float)(player.posY + (double)player.height), (float)player.posY), player.posZ + (double)MathUtility.randomNumber(-0.05f, 0.05f)));
                    }
                    for (int i = 0; i < this.particals.size(); ++i) {
                        if (System.currentTimeMillis() - this.particals.get(i).getTime() < (long)deleteAfter.get()) continue;
                        this.particals.remove(i);
                    }
                }
            }
        }
    }

    @EventTarget
    public void render(EventRender3D event) {
        if (mc.world != null) {
            for (partical partical2 : this.particals) {
                float step = (System.currentTimeMillis() - partical2.time) / 10L;
                if (modeSetting.is("Client")) {
                    Color colorc = new Color(ColorUtility.getColor(90).getRGB());
                    partical2.render(new Color(colorc.getRed(), colorc.getGreen(), colorc.getBlue(), Math.round(partical2.alpha)).getRGB());
                } else if (modeSetting.is("Custom")) {
                    Color colorc = new Color(color.getColor().getRGB());
                    partical2.render(new Color(colorc.getRed(), colorc.getGreen(), colorc.getBlue(), Math.round(partical2.alpha)).getRGB());
                }
            }
        }
    }

    public class partical {
        double x;
        double y;
        double z;
        double motionX;
        double motionY;
        double motionZ;
        long time;
        public int alpha = 180;

        public partical(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.motionX = MathUtility.randomNumber(-0.04f, 0.06f);
            this.motionY = MathUtility.randomNumber(-0.02f, 0.04f);
            this.motionZ = MathUtility.randomNumber(-0.08f, 0.04f);
            this.time = System.currentTimeMillis();
        }

        public long getTime() {
            return this.time;
        }

        public void update() {
            double yEx = 0.0;
            double sp = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0;
            this.x += this.motionX;
            this.y += this.motionY;
            if (this.posBlock(this.x, this.y, this.z)) {
                this.motionY = -this.motionY / 1.1;
            } else if (this.posBlock(this.x, this.y, this.z) || this.posBlock(this.x, this.y - yEx, this.z) || this.posBlock(this.x, this.y + yEx, this.z) || this.posBlock(this.x - sp, this.y, this.z - sp) || this.posBlock(this.x + sp, this.y, this.z + sp) || this.posBlock(this.x + sp, this.y, this.z - sp) || this.posBlock(this.x - sp, this.y, this.z + sp) || this.posBlock(this.x + sp, this.y, this.z) || this.posBlock(this.x - sp, this.y, this.z) || this.posBlock(this.x, this.y, this.z + sp) || this.posBlock(this.x, this.y, this.z - sp) || this.posBlock(this.x - sp, this.y - yEx, this.z - sp) || this.posBlock(this.x + sp, this.y - yEx, this.z + sp) || this.posBlock(this.x + sp, this.y - yEx, this.z - sp) || this.posBlock(this.x - sp, this.y - yEx, this.z + sp) || this.posBlock(this.x + sp, this.y - yEx, this.z) || this.posBlock(this.x - sp, this.y - yEx, this.z) || this.posBlock(this.x, this.y - yEx, this.z + sp) || this.posBlock(this.x, this.y - yEx, this.z - sp) || this.posBlock(this.x - sp, this.y + yEx, this.z - sp) || this.posBlock(this.x + sp, this.y + yEx, this.z + sp) || this.posBlock(this.x + sp, this.y + yEx, this.z - sp) || this.posBlock(this.x - sp, this.y + yEx, this.z + sp) || this.posBlock(this.x + sp, this.y + yEx, this.z) || this.posBlock(this.x - sp, this.y + yEx, this.z) || this.posBlock(this.x, this.y + yEx, this.z + sp) || this.posBlock(this.x, this.y + yEx, this.z - sp)) {
                this.motionX = -this.motionX + this.motionZ;
                this.motionZ = -this.motionZ + this.motionX;
            }
            this.z += this.motionZ;
            this.motionX /= 1.005;
            this.motionZ /= 1.005;
            this.motionY /= 1.005;
        }

        public void render(int color) {
            this.update();
            this.alpha = (int)((double)this.alpha - 0.1);
            float scale = 0.07f;
            GlStateManager.disableDepth();
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glBlendFunc(770, 771);
            try {
                Minecraft.getMinecraft().getRenderManager();
                double posX = this.x - RenderManager.renderPosX;
                Minecraft.getMinecraft().getRenderManager();
                double posY = this.y - RenderManager.renderPosY;
                Minecraft.getMinecraft().getRenderManager();
                double posZ = this.z - RenderManager.renderPosZ;
                double distanceFromPlayer = Minecraft.getMinecraft().player.getDistance(this.x, this.y - 1.0, this.z);
                int quality = (int)(distanceFromPlayer * 4.0 + 10.0);
                if (quality > 350) {
                    quality = 350;
                }
                GL11.glPushMatrix();
                GL11.glTranslated(posX, posY, posZ);
                GL11.glScalef(-scale, -scale, -scale);
                GL11.glRotated(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0, 1.0, 0.0);
                GL11.glRotated(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0, 0.0, 0.0);
                Color c = new Color(color);
                RenderUtility.drawFilledCircleNoGL(0, 0, 0.7, c.hashCode(), quality);
              //  RenderUtility.drawFilledCircleNoGL(0, 0, 0.7, c.hashCode(), quality);
                if (distanceFromPlayer < 4.0) {
                    RenderUtility.drawFilledCircleNoGL(0, 0, 1.4, new Color(c.getRed(), c.getGreen(), c.getBlue(), 50).hashCode(), quality);
                }
                RenderUtility.drawFilledCircleNoGL(0, 0, 2.3, new Color(c.getRed(), c.getGreen(), c.getBlue(), 30).hashCode(), quality);
                GL11.glScalef(0.8f, 0.8f, 0.8f);
                GL11.glPopMatrix();
            }
            catch (ConcurrentModificationException concurrentModificationException) {
                // empty catch block
            }
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GlStateManager.enableDepth();
            GL11.glColor3d(255.0, 255.0, 255.0);
        }

        private boolean posBlock(double x, double y, double z) {
            return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WATER && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.LAVA && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BED && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CAKE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.TALLGRASS && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.GRASS && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.FLOWER_POT && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.RED_FLOWER && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.YELLOW_FLOWER && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SAPLING && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.NETHER_BRICK_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE_GATE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ENCHANTING_TABLE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.END_PORTAL_FRAME && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DOUBLE_PLANT && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STANDING_SIGN && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WALL_SIGN && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SKULL && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR_INVERTED && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE_SLAB && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WOODEN_SLAB && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CARPET && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DEADBUSH && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REDSTONE_WIRE && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REEDS && mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SNOW_LAYER;
        }
    }
}