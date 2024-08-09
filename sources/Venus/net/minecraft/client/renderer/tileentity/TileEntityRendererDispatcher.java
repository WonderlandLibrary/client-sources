/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.BeaconTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.BedTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.CampfireTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EndGatewayTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.LecternTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.MobSpawnerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.PistonTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ShulkerBoxTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.StructureTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.optifine.EmissiveTextures;

public class TileEntityRendererDispatcher {
    public final Map<TileEntityType<?>, TileEntityRenderer<?>> renderers = Maps.newHashMap();
    public static final TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
    private final BufferBuilder fixedRenderBuffer = new BufferBuilder(256);
    private FontRenderer fontRenderer;
    public TextureManager textureManager;
    public World world;
    public ActiveRenderInfo renderInfo;
    public RayTraceResult cameraHitResult;
    public static TileEntity tileEntityRendered;

    private TileEntityRendererDispatcher() {
        this.register(TileEntityType.SIGN, new SignTileEntityRenderer(this));
        this.register(TileEntityType.MOB_SPAWNER, new MobSpawnerTileEntityRenderer(this));
        this.register(TileEntityType.PISTON, new PistonTileEntityRenderer(this));
        this.register(TileEntityType.CHEST, new ChestTileEntityRenderer(this));
        this.register(TileEntityType.ENDER_CHEST, new ChestTileEntityRenderer(this));
        this.register(TileEntityType.TRAPPED_CHEST, new ChestTileEntityRenderer(this));
        this.register(TileEntityType.ENCHANTING_TABLE, new EnchantmentTableTileEntityRenderer(this));
        this.register(TileEntityType.LECTERN, new LecternTileEntityRenderer(this));
        this.register(TileEntityType.END_PORTAL, new EndPortalTileEntityRenderer(this));
        this.register(TileEntityType.END_GATEWAY, new EndGatewayTileEntityRenderer(this));
        this.register(TileEntityType.BEACON, new BeaconTileEntityRenderer(this));
        this.register(TileEntityType.SKULL, new SkullTileEntityRenderer(this));
        this.register(TileEntityType.BANNER, new BannerTileEntityRenderer(this));
        this.register(TileEntityType.STRUCTURE_BLOCK, new StructureTileEntityRenderer(this));
        this.register(TileEntityType.SHULKER_BOX, new ShulkerBoxTileEntityRenderer(new ShulkerModel(), this));
        this.register(TileEntityType.BED, new BedTileEntityRenderer(this));
        this.register(TileEntityType.CONDUIT, new ConduitTileEntityRenderer(this));
        this.register(TileEntityType.BELL, new BellTileEntityRenderer(this));
        this.register(TileEntityType.CAMPFIRE, new CampfireTileEntityRenderer(this));
    }

    private <E extends TileEntity> void register(TileEntityType<E> tileEntityType, TileEntityRenderer<E> tileEntityRenderer) {
        this.renderers.put(tileEntityType, tileEntityRenderer);
    }

    @Nullable
    public <E extends TileEntity> TileEntityRenderer<E> getRenderer(E e) {
        return this.renderers.get(e.getType());
    }

    public void prepare(World world, TextureManager textureManager, FontRenderer fontRenderer, ActiveRenderInfo activeRenderInfo, RayTraceResult rayTraceResult) {
        if (this.world != world) {
            this.setWorld(world);
        }
        this.textureManager = textureManager;
        this.renderInfo = activeRenderInfo;
        this.fontRenderer = fontRenderer;
        this.cameraHitResult = rayTraceResult;
    }

    public <E extends TileEntity> void renderTileEntity(E e, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
        TileEntityRenderer<E> tileEntityRenderer;
        if (Vector3d.copyCentered(e.getPos()).isWithinDistanceOf(this.renderInfo.getProjectedView(), e.getMaxRenderDistanceSquared()) && (tileEntityRenderer = this.getRenderer(e)) != null && e.hasWorld() && e.getType().isValidBlock(e.getBlockState().getBlock())) {
            TileEntityRendererDispatcher.runCrashReportable(e, () -> TileEntityRendererDispatcher.lambda$renderTileEntity$0(tileEntityRenderer, e, f, matrixStack, iRenderTypeBuffer));
        }
    }

    private static <T extends TileEntity> void render(TileEntityRenderer<T> tileEntityRenderer, T t, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
        World world = t.getWorld();
        int n = world != null ? WorldRenderer.getCombinedLight(world, t.getPos()) : 0xF000F0;
        tileEntityRendered = t;
        if (EmissiveTextures.isActive()) {
            EmissiveTextures.beginRender();
        }
        tileEntityRenderer.render(t, f, matrixStack, iRenderTypeBuffer, n, OverlayTexture.NO_OVERLAY);
        if (EmissiveTextures.isActive()) {
            if (EmissiveTextures.hasEmissive()) {
                EmissiveTextures.beginRenderEmissive();
                tileEntityRenderer.render(t, f, matrixStack, iRenderTypeBuffer, LightTexture.MAX_BRIGHTNESS, OverlayTexture.NO_OVERLAY);
                EmissiveTextures.endRenderEmissive();
            }
            EmissiveTextures.endRender();
        }
        tileEntityRendered = null;
    }

    public <E extends TileEntity> boolean renderItem(E e, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        TileEntityRenderer<E> tileEntityRenderer = this.getRenderer(e);
        if (tileEntityRenderer == null) {
            return false;
        }
        TileEntityRendererDispatcher.runCrashReportable(e, () -> TileEntityRendererDispatcher.lambda$renderItem$1(e, tileEntityRenderer, matrixStack, iRenderTypeBuffer, n, n2));
        return true;
    }

    private static void runCrashReportable(TileEntity tileEntity, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block Entity Details");
            tileEntity.addInfoToCrashReport(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    public void setWorld(@Nullable World world) {
        this.world = world;
        if (world == null) {
            this.renderInfo = null;
        }
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public TileEntityRenderer getRenderer(TileEntityType tileEntityType) {
        return this.renderers.get(tileEntityType);
    }

    public synchronized <T extends TileEntity> void setSpecialRendererInternal(TileEntityType<T> tileEntityType, TileEntityRenderer<? super T> tileEntityRenderer) {
        this.renderers.put(tileEntityType, tileEntityRenderer);
    }

    private static void lambda$renderItem$1(TileEntity tileEntity, TileEntityRenderer tileEntityRenderer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        tileEntityRendered = tileEntity;
        tileEntityRenderer.render(tileEntity, 0.0f, matrixStack, iRenderTypeBuffer, n, n2);
        tileEntityRendered = null;
    }

    private static void lambda$renderTileEntity$0(TileEntityRenderer tileEntityRenderer, TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
        TileEntityRendererDispatcher.render(tileEntityRenderer, tileEntity, f, matrixStack, iRenderTypeBuffer);
    }
}

