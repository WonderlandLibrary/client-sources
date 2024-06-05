package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class RenderManager
{
    private Map entityRenderMap;
    public static RenderManager instance;
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public RenderEngine renderEngine;
    public ItemRenderer itemRenderer;
    public World worldObj;
    public EntityLiving livingPlayer;
    public EntityLiving field_96451_i;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double viewerPosX;
    public double viewerPosY;
    public double viewerPosZ;
    public static boolean field_85095_o;
    
    static {
        RenderManager.instance = new RenderManager();
        RenderManager.field_85095_o = false;
    }
    
    private RenderManager() {
        (this.entityRenderMap = new HashMap()).put(EntitySpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5f), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish());
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman());
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan());
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton());
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch());
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze());
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie());
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube());
        this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager());
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem());
        this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5f));
        this.entityRenderMap.put(EntityBat.class, new RenderBat());
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon());
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal());
        this.entityRenderMap.put(EntityWither.class, new RenderWither());
        this.entityRenderMap.put(Entity.class, new RenderEntity());
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame());
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Item.snowball));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(Item.enderPearl));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(Item.eyeOfEnder));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Item.egg));
        this.entityRenderMap.put(EntityPotion.class, new RenderSnowball(Item.potion, 16384));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(Item.expBottle));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(Item.firework));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull());
        this.entityRenderMap.put(EntityItem.class, new RenderItem());
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb());
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart());
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner());
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish());
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
        for (final Render var2 : this.entityRenderMap.values()) {
            var2.setRenderManager(this);
        }
    }
    
    public Render getEntityClassRenderObject(final Class par1Class) {
        Render var2 = (Render)this.entityRenderMap.get(par1Class);
        if (var2 == null && par1Class != Entity.class) {
            var2 = this.getEntityClassRenderObject(par1Class.getSuperclass());
            this.entityRenderMap.put(par1Class, var2);
        }
        return var2;
    }
    
    public Render getEntityRenderObject(final Entity par1Entity) {
        return this.getEntityClassRenderObject(par1Entity.getClass());
    }
    
    public void cacheActiveRenderInfo(final World par1World, final RenderEngine par2RenderEngine, final FontRenderer par3FontRenderer, final EntityLiving par4EntityLiving, final EntityLiving par5EntityLiving, final GameSettings par6GameSettings, final float par7) {
        this.worldObj = par1World;
        this.renderEngine = par2RenderEngine;
        this.options = par6GameSettings;
        this.livingPlayer = par4EntityLiving;
        this.field_96451_i = par5EntityLiving;
        this.fontRenderer = par3FontRenderer;
        if (par4EntityLiving.isPlayerSleeping()) {
            final int var8 = par1World.getBlockId(MathHelper.floor_double(par4EntityLiving.posX), MathHelper.floor_double(par4EntityLiving.posY), MathHelper.floor_double(par4EntityLiving.posZ));
            if (var8 == Block.bed.blockID) {
                final int var9 = par1World.getBlockMetadata(MathHelper.floor_double(par4EntityLiving.posX), MathHelper.floor_double(par4EntityLiving.posY), MathHelper.floor_double(par4EntityLiving.posZ));
                final int var10 = var9 & 0x3;
                this.playerViewY = var10 * 90 + 180;
                this.playerViewX = 0.0f;
            }
        }
        else {
            this.playerViewY = par4EntityLiving.prevRotationYaw + (par4EntityLiving.rotationYaw - par4EntityLiving.prevRotationYaw) * par7;
            this.playerViewX = par4EntityLiving.prevRotationPitch + (par4EntityLiving.rotationPitch - par4EntityLiving.prevRotationPitch) * par7;
        }
        if (par6GameSettings.thirdPersonView == 2) {
            this.playerViewY += 180.0f;
        }
        this.viewerPosX = par4EntityLiving.lastTickPosX + (par4EntityLiving.posX - par4EntityLiving.lastTickPosX) * par7;
        this.viewerPosY = par4EntityLiving.lastTickPosY + (par4EntityLiving.posY - par4EntityLiving.lastTickPosY) * par7;
        this.viewerPosZ = par4EntityLiving.lastTickPosZ + (par4EntityLiving.posZ - par4EntityLiving.lastTickPosZ) * par7;
    }
    
    public void renderEntity(final Entity par1Entity, final float par2) {
        if (par1Entity.ticksExisted == 0) {
            par1Entity.lastTickPosX = par1Entity.posX;
            par1Entity.lastTickPosY = par1Entity.posY;
            par1Entity.lastTickPosZ = par1Entity.posZ;
        }
        final double var3 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par2;
        final double var4 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par2;
        final double var5 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par2;
        final float var6 = par1Entity.prevRotationYaw + (par1Entity.rotationYaw - par1Entity.prevRotationYaw) * par2;
        int var7 = par1Entity.getBrightnessForRender(par2);
        if (par1Entity.isBurning()) {
            var7 = 15728880;
        }
        final int var8 = var7 % 65536;
        final int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8 / 1.0f, var9 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.renderEntityWithPosYaw(par1Entity, var3 - RenderManager.renderPosX, var4 - RenderManager.renderPosY, var5 - RenderManager.renderPosZ, var6, par2);
    }
    
    public void renderEntityWithPosYaw(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        Render var10 = null;
        try {
            var10 = this.getEntityRenderObject(par1Entity);
            if (var10 != null && this.renderEngine != null) {
                if (RenderManager.field_85095_o && !par1Entity.isInvisible()) {
                    try {
                        this.func_85094_b(par1Entity, par2, par4, par6, par8, par9);
                    }
                    catch (Throwable var11) {
                        throw new ReportedException(CrashReport.makeCrashReport(var11, "Rendering entity hitbox in world"));
                    }
                }
                try {
                    var10.doRender(par1Entity, par2, par4, par6, par8, par9);
                }
                catch (Throwable var12) {
                    throw new ReportedException(CrashReport.makeCrashReport(var12, "Rendering entity in world"));
                }
                try {
                    var10.doRenderShadowAndFire(par1Entity, par2, par4, par6, par8, par9);
                }
                catch (Throwable var13) {
                    throw new ReportedException(CrashReport.makeCrashReport(var13, "Post-rendering entity in world"));
                }
            }
        }
        catch (Throwable var15) {
            final CrashReport var14 = CrashReport.makeCrashReport(var15, "Rendering entity in world");
            final CrashReportCategory var16 = var14.makeCategory("Entity being rendered");
            par1Entity.func_85029_a(var16);
            final CrashReportCategory var17 = var14.makeCategory("Renderer details");
            var17.addCrashSection("Assigned renderer", var10);
            var17.addCrashSection("Location", CrashReportCategory.func_85074_a(par2, par4, par6));
            var17.addCrashSection("Rotation", par8);
            var17.addCrashSection("Delta", par9);
            throw new ReportedException(var14);
        }
    }
    
    private void func_85094_b(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glDepthMask(false);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        GL11.glDisable(3042);
        GL11.glPushMatrix();
        final Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.setColorRGBA(255, 255, 255, 32);
        final double var11 = -par1Entity.width / 2.0f;
        final double var12 = -par1Entity.width / 2.0f;
        final double var13 = par1Entity.width / 2.0f;
        final double var14 = -par1Entity.width / 2.0f;
        final double var15 = -par1Entity.width / 2.0f;
        final double var16 = par1Entity.width / 2.0f;
        final double var17 = par1Entity.width / 2.0f;
        final double var18 = par1Entity.width / 2.0f;
        final double var19 = par1Entity.height;
        var10.addVertex(par2 + var11, par4 + var19, par6 + var12);
        var10.addVertex(par2 + var11, par4, par6 + var12);
        var10.addVertex(par2 + var13, par4, par6 + var14);
        var10.addVertex(par2 + var13, par4 + var19, par6 + var14);
        var10.addVertex(par2 + var17, par4 + var19, par6 + var18);
        var10.addVertex(par2 + var17, par4, par6 + var18);
        var10.addVertex(par2 + var15, par4, par6 + var16);
        var10.addVertex(par2 + var15, par4 + var19, par6 + var16);
        var10.addVertex(par2 + var13, par4 + var19, par6 + var14);
        var10.addVertex(par2 + var13, par4, par6 + var14);
        var10.addVertex(par2 + var17, par4, par6 + var18);
        var10.addVertex(par2 + var17, par4 + var19, par6 + var18);
        var10.addVertex(par2 + var15, par4 + var19, par6 + var16);
        var10.addVertex(par2 + var15, par4, par6 + var16);
        var10.addVertex(par2 + var11, par4, par6 + var12);
        var10.addVertex(par2 + var11, par4 + var19, par6 + var12);
        var10.draw();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
    }
    
    public void set(final World par1World) {
        this.worldObj = par1World;
    }
    
    public double getDistanceToCamera(final double par1, final double par3, final double par5) {
        final double var7 = par1 - this.viewerPosX;
        final double var8 = par3 - this.viewerPosY;
        final double var9 = par5 - this.viewerPosZ;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void updateIcons(final IconRegister par1IconRegister) {
        for (final Render var3 : this.entityRenderMap.values()) {
            var3.updateIcons(par1IconRegister);
        }
    }
}
