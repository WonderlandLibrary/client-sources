package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class TileEntityRenderer
{
    private Map specialRendererMap;
    public static TileEntityRenderer instance;
    private FontRenderer fontRenderer;
    public static double staticPlayerX;
    public static double staticPlayerY;
    public static double staticPlayerZ;
    public RenderEngine renderEngine;
    public World worldObj;
    public EntityLiving entityLivingPlayer;
    public float playerYaw;
    public float playerPitch;
    public double playerX;
    public double playerY;
    public double playerZ;
    
    static {
        TileEntityRenderer.instance = new TileEntityRenderer();
    }
    
    private TileEntityRenderer() {
        (this.specialRendererMap = new HashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.specialRendererMap.put(TileEntityPiston.class, new TileEntityRendererPiston());
        this.specialRendererMap.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.specialRendererMap.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.specialRendererMap.put(TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
        this.specialRendererMap.put(TileEntityEndPortal.class, new RenderEndPortal());
        this.specialRendererMap.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.specialRendererMap.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        for (final TileEntitySpecialRenderer var2 : this.specialRendererMap.values()) {
            var2.setTileEntityRenderer(this);
        }
    }
    
    public TileEntitySpecialRenderer getSpecialRendererForClass(final Class par1Class) {
        TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.specialRendererMap.get(par1Class);
        if (var2 == null && par1Class != TileEntity.class) {
            var2 = this.getSpecialRendererForClass(par1Class.getSuperclass());
            this.specialRendererMap.put(par1Class, var2);
        }
        return var2;
    }
    
    public boolean hasSpecialRenderer(final TileEntity par1TileEntity) {
        return this.getSpecialRendererForEntity(par1TileEntity) != null;
    }
    
    public TileEntitySpecialRenderer getSpecialRendererForEntity(final TileEntity par1TileEntity) {
        return (par1TileEntity == null) ? null : this.getSpecialRendererForClass(par1TileEntity.getClass());
    }
    
    public void cacheActiveRenderInfo(final World par1World, final RenderEngine par2RenderEngine, final FontRenderer par3FontRenderer, final EntityLiving par4EntityLiving, final float par5) {
        if (this.worldObj != par1World) {
            this.setWorld(par1World);
        }
        this.renderEngine = par2RenderEngine;
        this.entityLivingPlayer = par4EntityLiving;
        this.fontRenderer = par3FontRenderer;
        this.playerYaw = par4EntityLiving.prevRotationYaw + (par4EntityLiving.rotationYaw - par4EntityLiving.prevRotationYaw) * par5;
        this.playerPitch = par4EntityLiving.prevRotationPitch + (par4EntityLiving.rotationPitch - par4EntityLiving.prevRotationPitch) * par5;
        this.playerX = par4EntityLiving.lastTickPosX + (par4EntityLiving.posX - par4EntityLiving.lastTickPosX) * par5;
        this.playerY = par4EntityLiving.lastTickPosY + (par4EntityLiving.posY - par4EntityLiving.lastTickPosY) * par5;
        this.playerZ = par4EntityLiving.lastTickPosZ + (par4EntityLiving.posZ - par4EntityLiving.lastTickPosZ) * par5;
    }
    
    public void renderTileEntity(final TileEntity par1TileEntity, final float par2) {
        if (par1TileEntity.getDistanceFrom(this.playerX, this.playerY, this.playerZ) < par1TileEntity.getMaxRenderDistanceSquared()) {
            final int var3 = this.worldObj.getLightBrightnessForSkyBlocks(par1TileEntity.xCoord, par1TileEntity.yCoord, par1TileEntity.zCoord, 0);
            final int var4 = var3 % 65536;
            final int var5 = var3 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var4 / 1.0f, var5 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.renderTileEntityAt(par1TileEntity, par1TileEntity.xCoord - TileEntityRenderer.staticPlayerX, par1TileEntity.yCoord - TileEntityRenderer.staticPlayerY, par1TileEntity.zCoord - TileEntityRenderer.staticPlayerZ, par2);
        }
    }
    
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        final TileEntitySpecialRenderer var9 = this.getSpecialRendererForEntity(par1TileEntity);
        if (var9 != null) {
            try {
                var9.renderTileEntityAt(par1TileEntity, par2, par4, par6, par8);
            }
            catch (Throwable var11) {
                final CrashReport var10 = CrashReport.makeCrashReport(var11, "Rendering Tile Entity");
                final CrashReportCategory var12 = var10.makeCategory("Tile Entity Details");
                par1TileEntity.func_85027_a(var12);
                throw new ReportedException(var10);
            }
        }
    }
    
    public void setWorld(final World par1World) {
        this.worldObj = par1World;
        for (final TileEntitySpecialRenderer var3 : this.specialRendererMap.values()) {
            if (var3 != null) {
                var3.onWorldChange(par1World);
            }
        }
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
}
