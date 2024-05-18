package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.effect.*;
import net.minecraft.crash.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class RenderManager
{
    public Entity livingPlayer;
    private static final String[] I;
    public TextureManager renderEngine;
    public float playerViewY;
    private boolean renderShadow;
    private FontRenderer textRenderer;
    private double renderPosZ;
    private Map<String, RenderPlayer> skinMap;
    private boolean renderOutlines;
    public double viewerPosX;
    public Entity pointedEntity;
    public double viewerPosY;
    private double renderPosY;
    public float playerViewX;
    private double renderPosX;
    private boolean debugBoundingBox;
    public World worldObj;
    public GameSettings options;
    private RenderPlayer playerRenderer;
    public double viewerPosZ;
    private Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap;
    
    public void set(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public void renderWitherSkull(final Entity entity, final float n) {
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        final Render<Entity> entityRenderObject = this.getEntityRenderObject(entity);
        if (entityRenderObject != null && this.renderEngine != null) {
            final int brightnessForRender = entity.getBrightnessForRender(n);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (64495 + 32521 - 69438 + 37958) / 1.0f, brightnessForRender / (4029 + 58969 - 24141 + 26679) / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            entityRenderObject.renderName(entity, n2 - this.renderPosX, n3 - this.renderPosY, n4 - this.renderPosZ);
        }
    }
    
    public double getDistanceToCamera(final double n, final double n2, final double n3) {
        final double n4 = n - this.viewerPosX;
        final double n5 = n2 - this.viewerPosY;
        final double n6 = n3 - this.viewerPosZ;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public FontRenderer getFontRenderer() {
        return this.textRenderer;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x51 ^ 0x5D])["".length()] = I("\u001d\")7/\u00153", "yGOVZ");
        RenderManager.I[" ".length()] = I("\u001a \u0001<", "iLhQz");
        RenderManager.I["  ".length()] = I("\u0016$\r&16(\r%t!/\u0017+ =a\n,t3.\u0011.0", "DAcBT");
        RenderManager.I["   ".length()] = I("&\u0000\u0000\u0015H\u0004\n\u001d\u0005\u0000\u0004\u0006\u001d\u0006E\u0013\u0001\u0007\b\u0011\u000fO\u001a\u000fE\u0001\u0000\u0001\r\u0001", "vosae");
        RenderManager.I[0x5C ^ 0x58] = I("8\u0002\u000b\u0003$\u0018\u000e\u000b\u0000a\u000f\t\u0011\u000e5\u0013G\r\u000e5\b\b\u001dG(\u0004G\u0012\b3\u0006\u0003", "jgegA");
        RenderManager.I[0x4D ^ 0x48] = I("\u001f=\n6\u0015?1\n5P(6\u0010;\u00044x\r<P:7\u0016>\u0014", "MXdRp");
        RenderManager.I[0x9A ^ 0x9C] = I(".9\u000e\b\u001f\u0012w\u0018\u0004\u0002\u00050Z\u0013\u000e\u00053\u001f\u0013\u000e\u000f", "kWzak");
        RenderManager.I[0x15 ^ 0x12] = I("\u001c27\u0007\u0000<2+C\u0001+#8\n\t=", "NWYce");
        RenderManager.I[0xA9 ^ 0xA1] = I("(\u0016%%,\u0007\u00002l9\f\u000b2)9\f\u0017", "ieVLK");
        RenderManager.I[0xA2 ^ 0xAB] = I("\u001f*\b\u0016\u0003:*\u0005", "SEkww");
        RenderManager.I[0x4A ^ 0x40] = I("(\u001e\u0019\u000e\u0001\u0013\u001e\u0003", "zqmou");
        RenderManager.I[0x9C ^ 0x97] = I("\u0013\u000e)\u00192", "WkEmS");
    }
    
    public <T extends Entity> Render<T> getEntityClassRenderObject(final Class<? extends Entity> clazz) {
        Render<? extends Entity> entityClassRenderObject = this.entityRenderMap.get(clazz);
        if (entityClassRenderObject == null && clazz != Entity.class) {
            entityClassRenderObject = this.getEntityClassRenderObject((Class<? extends Entity>)clazz.getSuperclass());
            this.entityRenderMap.put(clazz, entityClassRenderObject);
        }
        return (Render<T>)entityClassRenderObject;
    }
    
    public RenderManager(final TextureManager renderEngine, final RenderItem renderItem) {
        this.entityRenderMap = (Map<Class<? extends Entity>, Render<? extends Entity>>)Maps.newHashMap();
        this.skinMap = (Map<String, RenderPlayer>)Maps.newHashMap();
        this.renderOutlines = ("".length() != 0);
        this.renderShadow = (" ".length() != 0);
        this.debugBoundingBox = ("".length() != 0);
        this.renderEngine = renderEngine;
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider<Entity>(this));
        this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3f));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
        this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
        this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(0x3A ^ 0x2A), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
        this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
        this.entityRenderMap.put(Entity.class, new RenderEntity(this));
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, renderItem));
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<Entity>(this, Items.snowball, renderItem));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<Entity>(this, Items.ender_pearl, renderItem));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<Entity>(this, Items.ender_eye, renderItem));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<Entity>(this, Items.egg, renderItem));
        this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, renderItem));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball<Entity>(this, Items.experience_bottle, renderItem));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<Entity>(this, Items.fireworks, renderItem));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, renderItem));
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<Entity>(this));
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75f));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.playerRenderer = new RenderPlayer(this);
        this.skinMap.put(RenderManager.I["".length()], this.playerRenderer);
        this.skinMap.put(RenderManager.I[" ".length()], new RenderPlayer(this, (boolean)(" ".length() != 0)));
    }
    
    public void setRenderOutlines(final boolean renderOutlines) {
        this.renderOutlines = renderOutlines;
    }
    
    public boolean doRenderEntity(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5, final boolean b) {
        Render<Entity> entityRenderObject = null;
        try {
            entityRenderObject = this.getEntityRenderObject(entity);
            if (entityRenderObject != null && this.renderEngine != null) {
                try {
                    if (entityRenderObject instanceof RendererLivingEntity) {
                        ((RendererLivingEntity)entityRenderObject).setRenderOutlines(this.renderOutlines);
                    }
                    entityRenderObject.doRender(entity, n, n2, n3, n4, n5);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    throw new ReportedException(CrashReport.makeCrashReport(t, RenderManager.I["  ".length()]));
                }
                try {
                    if (!this.renderOutlines) {
                        entityRenderObject.doRenderShadowAndFire(entity, n, n2, n3, n4, n5);
                        "".length();
                        if (0 == 4) {
                            throw null;
                        }
                    }
                }
                catch (Throwable t2) {
                    throw new ReportedException(CrashReport.makeCrashReport(t2, RenderManager.I["   ".length()]));
                }
                if (!this.debugBoundingBox || entity.isInvisible() || b) {
                    return " ".length() != 0;
                }
                try {
                    this.renderDebugBoundingBox(entity, n, n2, n3, n4, n5);
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                    return " ".length() != 0;
                }
                catch (Throwable t3) {
                    throw new ReportedException(CrashReport.makeCrashReport(t3, RenderManager.I[0x4F ^ 0x4B]));
                }
            }
            if (this.renderEngine != null) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        catch (Throwable t4) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t4, RenderManager.I[0x7 ^ 0x2]);
            entity.addEntityCrashInfo(crashReport.makeCategory(RenderManager.I[0x18 ^ 0x1E]));
            final CrashReportCategory category = crashReport.makeCategory(RenderManager.I[0x5 ^ 0x2]);
            category.addCrashSection(RenderManager.I[0x1B ^ 0x13], entityRenderObject);
            category.addCrashSection(RenderManager.I[0x2B ^ 0x22], CrashReportCategory.getCoordinateInfo(n, n2, n3));
            category.addCrashSection(RenderManager.I[0xA2 ^ 0xA8], n4);
            category.addCrashSection(RenderManager.I[0x7A ^ 0x71], n5);
            throw new ReportedException(crashReport);
        }
    }
    
    public void setRenderPosition(final double renderPosX, final double renderPosY, final double renderPosZ) {
        this.renderPosX = renderPosX;
        this.renderPosY = renderPosY;
        this.renderPosZ = renderPosZ;
    }
    
    public boolean renderEntitySimple(final Entity entity, final float n) {
        return this.renderEntityStatic(entity, n, "".length() != 0);
    }
    
    public void setRenderShadow(final boolean renderShadow) {
        this.renderShadow = renderShadow;
    }
    
    public boolean shouldRender(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        final Render<Entity> entityRenderObject = this.getEntityRenderObject(entity);
        if (entityRenderObject != null && entityRenderObject.shouldRender(entity, camera, n, n2, n3)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setPlayerViewY(final float playerViewY) {
        this.playerViewY = playerViewY;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isDebugBoundingBox() {
        return this.debugBoundingBox;
    }
    
    public boolean isRenderShadow() {
        return this.renderShadow;
    }
    
    private void renderDebugBoundingBox(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        final float n6 = entity.width / 2.0f;
        final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
        RenderGlobal.func_181563_a(new AxisAlignedBB(entityBoundingBox.minX - entity.posX + n, entityBoundingBox.minY - entity.posY + n2, entityBoundingBox.minZ - entity.posZ + n3, entityBoundingBox.maxX - entity.posX + n, entityBoundingBox.maxY - entity.posY + n2, entityBoundingBox.maxZ - entity.posZ + n3), 127 + 237 - 173 + 64, 251 + 58 - 255 + 201, 28 + 185 - 13 + 55, 224 + 171 - 309 + 169);
        if (entity instanceof EntityLivingBase) {
            RenderGlobal.func_181563_a(new AxisAlignedBB(n - n6, n2 + entity.getEyeHeight() - 0.009999999776482582, n3 - n6, n + n6, n2 + entity.getEyeHeight() + 0.009999999776482582, n3 + n6), 207 + 152 - 156 + 52, "".length(), "".length(), 101 + 90 - 30 + 94);
        }
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final Vec3 look = entity.getLook(n5);
        worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(n, n2 + entity.getEyeHeight(), n3).color("".length(), "".length(), 141 + 98 - 113 + 129, 131 + 31 - 25 + 118).endVertex();
        worldRenderer.pos(n + look.xCoord * 2.0, n2 + entity.getEyeHeight() + look.yCoord * 2.0, n3 + look.zCoord * 2.0).color("".length(), "".length(), 14 + 143 - 78 + 176, 224 + 177 - 353 + 207).endVertex();
        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(" ".length() != 0);
    }
    
    public boolean renderEntityWithPosYaw(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        return this.doRenderEntity(entity, n, n2, n3, n4, n5, "".length() != 0);
    }
    
    public boolean renderEntityStatic(final Entity entity, final float n, final boolean b) {
        if (entity.ticksExisted == 0) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
        }
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        final float n5 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n;
        int brightnessForRender = entity.getBrightnessForRender(n);
        if (entity.isBurning()) {
            brightnessForRender = 9921698 + 4624593 - 6793225 + 7975814;
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (49534 + 28003 - 44250 + 32249) / 1.0f, brightnessForRender / (13466 + 2246 + 22970 + 26854) / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return this.doRenderEntity(entity, n2 - this.renderPosX, n3 - this.renderPosY, n4 - this.renderPosZ, n5, n, b);
    }
    
    public <T extends Entity> Render<T> getEntityRenderObject(final Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            final RenderPlayer renderPlayer = this.skinMap.get(((AbstractClientPlayer)entity).getSkinType());
            RenderPlayer playerRenderer;
            if (renderPlayer != null) {
                playerRenderer = renderPlayer;
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                playerRenderer = this.playerRenderer;
            }
            return (Render<T>)playerRenderer;
        }
        return this.getEntityClassRenderObject(entity.getClass());
    }
    
    public void setDebugBoundingBox(final boolean debugBoundingBox) {
        this.debugBoundingBox = debugBoundingBox;
    }
    
    public void cacheActiveRenderInfo(final World worldObj, final FontRenderer textRenderer, final Entity livingPlayer, final Entity pointedEntity, final GameSettings options, final float n) {
        this.worldObj = worldObj;
        this.options = options;
        this.livingPlayer = livingPlayer;
        this.pointedEntity = pointedEntity;
        this.textRenderer = textRenderer;
        if (livingPlayer instanceof EntityLivingBase && ((EntityLivingBase)livingPlayer).isPlayerSleeping()) {
            final IBlockState blockState = worldObj.getBlockState(new BlockPos(livingPlayer));
            if (blockState.getBlock() == Blocks.bed) {
                this.playerViewY = blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING).getHorizontalIndex() * (0xED ^ 0xB7) + (130 + 21 - 118 + 147);
                this.playerViewX = 0.0f;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
        }
        else {
            this.playerViewY = livingPlayer.prevRotationYaw + (livingPlayer.rotationYaw - livingPlayer.prevRotationYaw) * n;
            this.playerViewX = livingPlayer.prevRotationPitch + (livingPlayer.rotationPitch - livingPlayer.prevRotationPitch) * n;
        }
        if (options.thirdPersonView == "  ".length()) {
            this.playerViewY += 180.0f;
        }
        this.viewerPosX = livingPlayer.lastTickPosX + (livingPlayer.posX - livingPlayer.lastTickPosX) * n;
        this.viewerPosY = livingPlayer.lastTickPosY + (livingPlayer.posY - livingPlayer.lastTickPosY) * n;
        this.viewerPosZ = livingPlayer.lastTickPosZ + (livingPlayer.posZ - livingPlayer.lastTickPosZ) * n;
    }
}
