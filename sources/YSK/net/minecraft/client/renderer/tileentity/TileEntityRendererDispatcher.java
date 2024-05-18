package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class TileEntityRendererDispatcher
{
    private Map<Class<? extends TileEntity>, TileEntitySpecialRenderer<? extends TileEntity>> mapSpecialRenderers;
    public static double staticPlayerY;
    public static TileEntityRendererDispatcher instance;
    public double entityX;
    public TextureManager renderEngine;
    public Entity entity;
    public static double staticPlayerX;
    public float entityPitch;
    public double entityZ;
    private static final String[] I;
    public double entityY;
    public float entityYaw;
    public World worldObj;
    private FontRenderer fontRenderer;
    public static double staticPlayerZ;
    
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4) {
        this.renderTileEntityAt(tileEntity, n, n2, n3, n4, -" ".length());
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(final TileEntity tileEntity) {
        TileEntitySpecialRenderer<T> specialRendererByClass;
        if (tileEntity == null) {
            specialRendererByClass = null;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            specialRendererByClass = this.getSpecialRendererByClass(tileEntity.getClass());
        }
        return specialRendererByClass;
    }
    
    public void renderTileEntity(final TileEntity tileEntity, final float n, final int n2) {
        if (tileEntity.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileEntity.getMaxRenderDistanceSquared()) {
            final int combinedLight = this.worldObj.getCombinedLight(tileEntity.getPos(), "".length());
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, combinedLight % (34297 + 35902 - 14901 + 10238) / 1.0f, combinedLight / (16162 + 47995 - 60464 + 61843) / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos pos = tileEntity.getPos();
            this.renderTileEntityAt(tileEntity, pos.getX() - TileEntityRendererDispatcher.staticPlayerX, pos.getY() - TileEntityRendererDispatcher.staticPlayerY, pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ, n, n2);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0018\t(#\u00008\u0005( E\b\u0000)$\u000ej)(3\f>\u0015", "JlFGe");
        TileEntityRendererDispatcher.I[" ".length()] = I("/=#\t%M\u0014\"\u001e'\u0019(l.+\u00190%\u0006=", "mQLjN");
    }
    
    public void cacheActiveRenderInfo(final World world, final TextureManager renderEngine, final FontRenderer fontRenderer, final Entity entity, final float n) {
        if (this.worldObj != world) {
            this.setWorld(world);
        }
        this.renderEngine = renderEngine;
        this.entity = entity;
        this.fontRenderer = fontRenderer;
        this.entityYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n;
        this.entityPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * n;
        this.entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        this.entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        this.entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
    }
    
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        final TileEntitySpecialRenderer<TileEntity> specialRenderer = this.getSpecialRenderer(tileEntity);
        if (specialRenderer != null) {
            try {
                specialRenderer.renderTileEntityAt(tileEntity, n, n2, n3, n4, n5);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, TileEntityRendererDispatcher.I["".length()]);
                tileEntity.addInfoToCrashReport(crashReport.makeCategory(TileEntityRendererDispatcher.I[" ".length()]));
                throw new ReportedException(crashReport);
            }
        }
    }
    
    public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(final Class<? extends TileEntity> clazz) {
        TileEntitySpecialRenderer<? extends TileEntity> specialRendererByClass = this.mapSpecialRenderers.get(clazz);
        if (specialRendererByClass == null && clazz != TileEntity.class) {
            specialRendererByClass = this.getSpecialRendererByClass((Class<? extends TileEntity>)clazz.getSuperclass());
            this.mapSpecialRenderers.put(clazz, specialRendererByClass);
        }
        return (TileEntitySpecialRenderer<T>)specialRendererByClass;
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    private TileEntityRendererDispatcher() {
        (this.mapSpecialRenderers = (Map<Class<? extends TileEntity>, TileEntitySpecialRenderer<? extends TileEntity>>)Maps.newHashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
        this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
        this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
        this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
        final Iterator<TileEntitySpecialRenderer<? extends TileEntity>> iterator = this.mapSpecialRenderers.values().iterator();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().setRendererDispatcher(this);
        }
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        TileEntityRendererDispatcher.instance = new TileEntityRendererDispatcher();
    }
}
