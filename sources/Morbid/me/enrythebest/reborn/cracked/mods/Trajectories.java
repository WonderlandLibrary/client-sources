package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import java.util.*;
import net.minecraft.src.*;

public final class Trajectories extends ModBase
{
    private boolean release;
    public int Tempo;
    
    public Trajectories() {
        super("Trajectories", "V", true, ".t trajectories");
        this.Tempo = 0;
        this.setDescription("Mostra dove punta l'arco / palla di neve / Ender Pearl / Uovo.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            try {
                boolean var1 = false;
                MorbidWrapper.mcObj();
                final EntityClientPlayerMP var2 = Minecraft.thePlayer;
                if (var2.getCurrentEquippedItem() == null) {
                    return;
                }
                final Item var3 = var2.getCurrentEquippedItem().getItem();
                if (!(var3 instanceof ItemBow) && !(var3 instanceof ItemSnowball) && !(var3 instanceof ItemEnderPearl) && !(var3 instanceof ItemEgg)) {
                    return;
                }
                var1 = (var3 instanceof ItemBow);
                double var4 = RenderManager.renderPosX - MathHelper.cos(var2.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
                double var5 = RenderManager.renderPosY + var2.getEyeHeight() - 0.10000000149011612;
                double var6 = RenderManager.renderPosZ - MathHelper.sin(var2.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
                double var7 = -MathHelper.sin(var2.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(var2.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
                double var8 = -MathHelper.sin(var2.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
                double var9 = MathHelper.cos(var2.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(var2.rotationPitch / 180.0f * 3.1415927f) * (var1 ? 1.0 : 0.4);
                MorbidWrapper.mcObj();
                boolean b = false;
                Label_0305: {
                    if (!Minecraft.theWorld.extendedLevelsInChunkCache()) {
                        MorbidWrapper.mcObj();
                        if (Minecraft.theWorld.blockExists((int)var4, (int)var5, (int)var6)) {
                            MorbidWrapper.mcObj();
                            if (Minecraft.thePlayer.getDistance(var4, var5, var6) <= 255.0) {
                                b = false;
                                break Label_0305;
                            }
                        }
                    }
                    b = true;
                }
                final boolean var10 = b;
                if (var10) {
                    return;
                }
                final float var11 = MathHelper.sqrt_double(var7 * var7 + var8 * var8 + var9 * var9);
                if (var2.getItemInUseCount() <= 0 && var1) {
                    return;
                }
                final int var12 = 72000 - var2.getItemInUseCount();
                float var13 = var12 / 20.0f;
                var13 = (var13 * var13 + var13 * 2.0f) / 3.0f;
                if (var13 < 0.1) {
                    return;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glColor3f(1.0f - var13, var13, 0.0f);
                var7 /= var11;
                var8 /= var11;
                var9 /= var11;
                var7 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
                var8 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
                var9 *= (var1 ? (var13 * 2.0f) : 1.0f) * 1.5;
                GL11.glLineWidth(1.0f);
                GL11.glBegin(3);
                boolean var14 = false;
                boolean var15 = false;
                MovingObjectPosition var16 = null;
                final float var17 = var1 ? 0.3f : 0.25f;
                while (!var14 && !var10) {
                    MorbidWrapper.mcObj();
                    Vec3 var18 = Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
                    MorbidWrapper.mcObj();
                    Vec3 var19 = Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var7, var5 + var8, var6 + var9);
                    MorbidWrapper.mcObj();
                    final MovingObjectPosition var20 = Minecraft.theWorld.rayTraceBlocks_do_do(var18, var19, false, true);
                    MorbidWrapper.mcObj();
                    var18 = Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
                    MorbidWrapper.mcObj();
                    var19 = Minecraft.theWorld.getWorldVec3Pool().getVecFromPool(var4 + var7, var5 + var8, var6 + var9);
                    if (var20 != null) {
                        var14 = true;
                        var16 = var20;
                    }
                    final AxisAlignedBB var21 = new AxisAlignedBB(var4 - var17, var5 - var17, var6 - var17, var4 + var17, var5 + var17, var6 + var17);
                    final List var22 = getEntitiesWithinAABB(var21.addCoord(var7, var8, var9).expand(1.0, 1.0, 1.0));
                    for (int var23 = 0; var23 < var22.size(); ++var23) {
                        final Entity var24 = var22.get(var23);
                        if (var24.canBeCollidedWith() && var24 != var2) {
                            final AxisAlignedBB var25 = var24.boundingBox.expand(0.3, 0.3, 0.3);
                            final MovingObjectPosition var26 = var25.calculateIntercept(var18, var19);
                            if (var26 != null) {
                                var14 = true;
                                var15 = true;
                                var16 = var26;
                            }
                        }
                    }
                    var4 += var7;
                    var5 += var8;
                    var6 += var9;
                    float var27 = 0.99f;
                    final AxisAlignedBB var28 = new AxisAlignedBB(var4 - var17, var5 - var17, var6 - var17, var4 + var17, var5 + var17, var6 + var17);
                    if (isInMaterial(var28.expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water)) {
                        var27 = 0.8f;
                    }
                    var7 *= var27;
                    var8 *= var27;
                    var9 *= var27;
                    var8 -= (var1 ? 0.05 : 0.03);
                    GL11.glVertex3d(var4 - RenderManager.renderPosX, var5 - RenderManager.renderPosY, var6 - RenderManager.renderPosZ);
                }
                GL11.glEnd();
                GL11.glPushMatrix();
                GL11.glTranslated(var4 - RenderManager.renderPosX, var5 - RenderManager.renderPosY, var6 - RenderManager.renderPosZ);
                if (var16 != null) {
                    switch (var16.sideHit) {
                        case 2: {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            break;
                        }
                        case 3: {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            break;
                        }
                        case 4: {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                            break;
                        }
                        case 5: {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                            break;
                        }
                    }
                    if (var15) {
                        GL11.glColor3f(1.0f, 0.0f, 0.0f);
                        ++this.Tempo;
                        if (this.release) {
                            this.spara();
                        }
                    }
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                this.renderPoint();
                GL11.glEnable(2929);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
            catch (Exception var29) {}
        }
    }
    
    private void renderPoint() {
        GL11.glBegin(1);
        GL11.glVertex3d(-0.4, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, -0.4);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.4, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.4);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glEnd();
        final Cylinder var1 = new Cylinder();
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        var1.setDrawStyle(100011);
        var1.draw(0.4f, 0.4f, 0.1f, 24, 1);
    }
    
    public void spara() {
        if (this.Tempo == 4) {
            Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed = false;
            this.Tempo = 0;
        }
    }
    
    public static boolean isInMaterial(final AxisAlignedBB var0, final Material var1) {
        boolean var2 = false;
        final int var3 = MathHelper.floor_double(var0.minX);
        final int var4 = MathHelper.floor_double(var0.maxX + 1.0);
        final int var5 = MathHelper.floor_double(var0.minY);
        final int var6 = MathHelper.floor_double(var0.maxY + 1.0);
        final int var7 = MathHelper.floor_double(var0.minZ);
        final int var8 = MathHelper.floor_double(var0.maxZ + 1.0);
        MorbidWrapper.mcObj();
        if (!Minecraft.theWorld.checkChunksExist(var3, var5, var7, var4, var6, var8)) {
            return false;
        }
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    final Block[] blocksList = Block.blocksList;
                    MorbidWrapper.mcObj();
                    final Block var12 = blocksList[Minecraft.theWorld.getBlockId(var9, var10, var11)];
                    if (var12 != null && var12.blockMaterial == var1) {
                        final float n = var10 + 1;
                        MorbidWrapper.mcObj();
                        final double var13 = n - BlockFluid.getFluidHeightPercent(Minecraft.theWorld.getBlockMetadata(var9, var10, var11));
                        if (var6 >= var13) {
                            var2 = true;
                        }
                    }
                }
            }
        }
        return var2;
    }
    
    public static List getEntitiesWithinAABB(final AxisAlignedBB var0) {
        final ArrayList var = new ArrayList();
        final int var2 = MathHelper.floor_double((var0.minX - 2.0) / 16.0);
        final int var3 = MathHelper.floor_double((var0.maxX + 2.0) / 16.0);
        final int var4 = MathHelper.floor_double((var0.minZ - 2.0) / 16.0);
        final int var5 = MathHelper.floor_double((var0.maxZ + 2.0) / 16.0);
        for (int var6 = var2; var6 <= var3; ++var6) {
            for (int var7 = var4; var7 <= var5; ++var7) {
                MorbidWrapper.mcObj();
                if (Minecraft.theWorld.chunkExists(var6, var7)) {
                    MorbidWrapper.mcObj();
                    final Chunk chunkFromChunkCoords = Minecraft.theWorld.getChunkFromChunkCoords(var6, var7);
                    MorbidWrapper.mcObj();
                    chunkFromChunkCoords.getEntitiesWithinAABBForEntity(Minecraft.thePlayer, var0, var, null);
                }
            }
        }
        return var;
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".t release")) {
            this.release = !this.release;
            ModBase.setCommandExists(true);
            this.getWrapper();
            MorbidWrapper.addChat("AutoRelease toggled " + (this.release ? "on" : "off"));
        }
    }
}
