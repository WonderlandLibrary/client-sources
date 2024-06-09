package com.client.glowclient.sponge.mixin;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.multiplayer.*;
import com.client.glowclient.sponge.mixinutils.renderglobal.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.shader.*;
import org.lwjgl.util.vector.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.client.renderer.culling.*;
import com.google.common.collect.*;
import net.minecraftforge.common.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.function.*;
import net.minecraftforge.client.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;

@Mixin({ RenderGlobal.class })
public abstract class MixinRenderGlobal implements IWorldEventListener, IResourceManagerReloadListener
{
    @Shadow
    private Minecraft field_72777_q;
    @Shadow
    private TextureManager field_72770_i;
    @Shadow
    private RenderManager field_175010_j;
    @Shadow
    private WorldClient field_72769_h;
    @Shadow
    private Set<RenderChunk> field_175009_l;
    @Shadow
    private List<ContainerLocalRenderInformation> field_72755_R;
    @Shadow
    private final Set<TileEntity> field_181024_n;
    private ViewFrustum viewFrustum2;
    @Shadow
    private final Map<Integer, DestroyBlockProgress> field_72738_E;
    @Shadow
    private final Map<BlockPos, ISound> field_147593_P;
    @Shadow
    private final TextureAtlasSprite[] field_94141_F;
    @Shadow
    private Framebuffer field_175015_z;
    @Shadow
    private ShaderGroup field_174991_A;
    @Shadow
    private double field_174992_B;
    @Shadow
    private double field_174993_C;
    @Shadow
    private double field_174987_D;
    @Shadow
    private int field_174988_E;
    @Shadow
    private int field_174989_F;
    @Shadow
    private int field_174990_G;
    @Shadow
    private double field_174997_H;
    @Shadow
    private double field_174998_I;
    @Shadow
    private double field_174999_J;
    @Shadow
    private double field_175000_K;
    @Shadow
    private double field_174994_L;
    @Shadow
    private ChunkRenderDispatcher field_174995_M;
    @Shadow
    private ChunkRenderContainer field_174996_N;
    @Shadow
    private int field_72739_F;
    @Shadow
    private int field_72740_G;
    @Shadow
    private int field_72748_H;
    @Shadow
    private int field_72749_I;
    @Shadow
    private int field_72750_J;
    @Shadow
    private boolean field_175002_T;
    @Shadow
    private ClippingHelper field_175001_U;
    @Shadow
    private final Vector4f[] field_175004_V;
    @Shadow
    private final Vector3d field_175003_W;
    @Shadow
    private boolean field_175005_X;
    @Shadow
    IRenderChunkFactory field_175007_a;
    @Shadow
    private double field_147596_f;
    @Shadow
    private double field_147597_g;
    @Shadow
    private double field_147602_h;
    @Shadow
    private boolean field_147595_R;
    @Shadow
    private boolean field_184386_ad;
    @Shadow
    private final Set<BlockPos> field_184387_ae;
    
    public MixinRenderGlobal() {
        super();
        this.chunksToUpdate = Sets.newLinkedHashSet();
        this.renderInfos = Lists.newArrayListWithCapacity(69696);
        this.setTileEntities = Sets.newHashSet();
        this.damagedBlocks = Maps.newHashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.destroyBlockIcons = new TextureAtlasSprite[10];
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.lastViewEntityX = Double.MIN_VALUE;
        this.lastViewEntityY = Double.MIN_VALUE;
        this.lastViewEntityZ = Double.MIN_VALUE;
        this.lastViewEntityPitch = Double.MIN_VALUE;
        this.lastViewEntityYaw = Double.MIN_VALUE;
        this.renderDistanceChunks = -1;
        this.renderEntitiesStartupCounter = 2;
        this.debugTerrainMatrix = new Vector4f[8];
        this.debugTerrainFrustumPosition = new Vector3d();
        this.displayListEntitiesDirty = true;
        this.setLightUpdates = Sets.newHashSet();
    }
    
    @Shadow
    protected Vector3f getViewVector(final Entity entity, final double n) {
        return new Vector3f();
    }
    
    @Shadow
    private void fixTerrainFrustum(final double n, final double n2, final double n3) {
    }
    
    @Shadow
    private Set<EnumFacing> getVisibleFacings(final BlockPos blockPos) {
        return null;
    }
    
    @Shadow
    protected void stopChunkUpdates() {
    }
    
    @Shadow
    private void generateStars() {
    }
    
    @Shadow
    private void generateSky() {
    }
    
    @Shadow
    private void generateSky2() {
    }
    
    @Shadow
    private void renderBlockLayer(final BlockRenderLayer blockRenderLayer) {
    }
    
    @Shadow
    private boolean isOutlineActive(final Entity entity, final Entity entity2, final ICamera camera) {
        return false;
    }
    
    @Shadow
    protected boolean isRenderEntityOutlines() {
        return false;
    }
    
    @Shadow
    private void preRenderDamagedBlocks() {
    }
    
    @Shadow
    private void postRenderDamagedBlocks() {
    }
    
    @Overwrite
    public void setupTerrain(final Entity entity, final double n, ICamera camera, final int frameIndex, final boolean b) {
        try {
            if (HookTranslator.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
                this.loadRenderers();
            }
            this.world.profiler.startSection("camera");
            final double n2 = entity.posX - this.frustumUpdatePosX;
            final double n3 = entity.posY - this.frustumUpdatePosY;
            final double n4 = entity.posZ - this.frustumUpdatePosZ;
            if (this.frustumUpdatePosChunkX != entity.chunkCoordX || this.frustumUpdatePosChunkY != entity.chunkCoordY || this.frustumUpdatePosChunkZ != entity.chunkCoordZ || n2 * n2 + n3 * n3 + n4 * n4 > 16.0) {
                this.frustumUpdatePosX = entity.posX;
                this.frustumUpdatePosY = entity.posY;
                this.frustumUpdatePosZ = entity.posZ;
                this.frustumUpdatePosChunkX = entity.chunkCoordX;
                this.frustumUpdatePosChunkY = entity.chunkCoordY;
                this.frustumUpdatePosChunkZ = entity.chunkCoordZ;
                this.viewFrustum2.updateChunkPositions(entity.posX, entity.posZ);
            }
            this.world.profiler.endStartSection("renderlistcamera");
            final double n5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
            double m45;
            if (HookTranslator.v20) {
                m45 = HookTranslator.m45();
            }
            else {
                m45 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
            }
            final double n6 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
            this.renderContainer.initialize(n5, m45, n6);
            this.world.profiler.endStartSection("cull");
            if (this.debugFixedClippingHelper != null) {
                final Frustum frustum = new Frustum(this.debugFixedClippingHelper);
                frustum.setPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
                camera = (ICamera)frustum;
            }
            HookTranslator.mc.profiler.endStartSection("culling");
            final BlockPos blockPos = new BlockPos(n5, m45 + entity.getEyeHeight(), n6);
            final RenderChunk renderChunk = this.viewFrustum2.getRenderChunk(blockPos);
            final BlockPos blockPos2 = new BlockPos(MathHelper.floor(n5 / 16.0) * 16, MathHelper.floor(m45 / 16.0) * 16, MathHelper.floor(n6 / 16.0) * 16);
            this.displayListEntitiesDirty = (this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || entity.posX != this.lastViewEntityX || entity.posY != this.lastViewEntityY || entity.posZ != this.lastViewEntityZ || entity.rotationPitch != this.lastViewEntityPitch || entity.rotationYaw != this.lastViewEntityYaw);
            this.lastViewEntityX = entity.posX;
            this.lastViewEntityY = entity.posY;
            this.lastViewEntityZ = entity.posZ;
            this.lastViewEntityPitch = entity.rotationPitch;
            this.lastViewEntityYaw = entity.rotationYaw;
            final boolean b2 = this.debugFixedClippingHelper != null;
            HookTranslator.mc.profiler.endStartSection("update");
            if (!b2 && this.displayListEntitiesDirty) {
                this.displayListEntitiesDirty = false;
                this.renderInfos = Lists.newArrayList();
                final ArrayDeque arrayDeque = Queues.newArrayDeque();
                Entity.setRenderDistanceWeight(MathHelper.clamp(HookTranslator.mc.gameSettings.renderDistanceChunks / 8.0, 1.0, 2.5));
                boolean renderChunksMany = HookTranslator.mc.renderChunksMany;
                if (renderChunk != null) {
                    boolean b3 = false;
                    final ContainerLocalRenderInformation containerLocalRenderInformation = new ContainerLocalRenderInformation(renderChunk, null, 0);
                    final Set<EnumFacing> visibleFacings = this.getVisibleFacings(blockPos);
                    if (visibleFacings.size() == 1) {
                        final Vector3f viewVector = this.getViewVector(entity, n);
                        visibleFacings.remove(EnumFacing.getFacingFromVector(viewVector.x, viewVector.y, viewVector.z).getOpposite());
                    }
                    if (visibleFacings.isEmpty()) {
                        b3 = true;
                    }
                    if (b3 && !b) {
                        this.renderInfos.add(containerLocalRenderInformation);
                    }
                    else {
                        if (b && this.world.getBlockState(blockPos).isOpaqueCube()) {
                            renderChunksMany = false;
                        }
                        renderChunk.setFrameIndex(frameIndex);
                        arrayDeque.add(containerLocalRenderInformation);
                    }
                }
                else {
                    final int n7 = (blockPos.getY() > 0) ? 248 : 8;
                    for (int i = -this.renderDistanceChunks; i <= this.renderDistanceChunks; ++i) {
                        for (int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; ++j) {
                            final RenderChunk renderChunk2 = this.viewFrustum2.getRenderChunk(new BlockPos((i << 4) + 8, n7, (j << 4) + 8));
                            if (renderChunk2 != null && camera.isBoundingBoxInFrustum(renderChunk2.boundingBox.expand(0.0, (blockPos.getY() > 0) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY, 0.0))) {
                                renderChunk2.setFrameIndex(frameIndex);
                                arrayDeque.add(new ContainerLocalRenderInformation(renderChunk2, null, 0));
                            }
                        }
                    }
                }
                HookTranslator.mc.profiler.startSection("iteration");
                while (!arrayDeque.isEmpty()) {
                    final ContainerLocalRenderInformation containerLocalRenderInformation2 = arrayDeque.poll();
                    final RenderChunk renderChunk3 = containerLocalRenderInformation2.renderChunk;
                    final EnumFacing facing = containerLocalRenderInformation2.facing;
                    this.renderInfos.add(containerLocalRenderInformation2);
                    for (final EnumFacing enumFacing : EnumFacing.values()) {
                        final RenderChunk renderChunkOffset = this.getRenderChunkOffset(blockPos2, renderChunk3, enumFacing);
                        if ((!renderChunksMany || !containerLocalRenderInformation2.hasDirection(enumFacing.getOpposite())) && (!renderChunksMany || facing == null || renderChunk3.getCompiledChunk().isVisible(facing.getOpposite(), enumFacing)) && renderChunkOffset != null && renderChunkOffset.setFrameIndex(frameIndex) && camera.isBoundingBoxInFrustum(renderChunkOffset.boundingBox)) {
                            final ContainerLocalRenderInformation containerLocalRenderInformation3 = new ContainerLocalRenderInformation(renderChunkOffset, enumFacing, containerLocalRenderInformation2.counter + 1);
                            containerLocalRenderInformation3.setDirection(containerLocalRenderInformation2.setFacing, enumFacing);
                            arrayDeque.add(containerLocalRenderInformation3);
                        }
                    }
                }
                HookTranslator.mc.profiler.endSection();
            }
            HookTranslator.mc.profiler.endStartSection("captureFrustum");
            if (this.debugFixTerrainFrustum) {
                this.fixTerrainFrustum(n5, m45, n6);
                this.debugFixTerrainFrustum = false;
            }
            HookTranslator.mc.profiler.endStartSection("rebuildNear");
            final Set chunksToUpdate = this.chunksToUpdate;
            this.chunksToUpdate = Sets.newLinkedHashSet();
            final Iterator<ContainerLocalRenderInformation> iterator = (Iterator<ContainerLocalRenderInformation>)this.renderInfos.iterator();
            while (iterator.hasNext()) {
                final RenderChunk renderChunk4 = iterator.next().renderChunk;
                if (renderChunk4.needsUpdate() || chunksToUpdate.contains(renderChunk4)) {
                    this.displayListEntitiesDirty = true;
                    final boolean b4 = renderChunk4.getPosition().add(8, 8, 8).distanceSq((Vec3i)blockPos) < 768.0;
                    if (ForgeModContainer.alwaysSetupTerrainOffThread || (!renderChunk4.needsImmediateUpdate() && !b4)) {
                        this.chunksToUpdate.add(renderChunk4);
                    }
                    else {
                        HookTranslator.mc.profiler.startSection("build near");
                        this.renderDispatcher.updateChunkNow(renderChunk4);
                        renderChunk4.clearNeedsUpdate();
                        HookTranslator.mc.profiler.endSection();
                    }
                }
            }
            this.chunksToUpdate.addAll(chunksToUpdate);
            HookTranslator.mc.profiler.endSection();
        }
        catch (Exception ex) {}
    }
    
    @Overwrite
    public void loadRenderers() {
        if (this.world != null) {
            if (this.renderDispatcher == null) {
                this.renderDispatcher = new ChunkRenderDispatcher();
            }
            this.displayListEntitiesDirty = true;
            Blocks.LEAVES.setGraphicsLevel(HookTranslator.mc.gameSettings.fancyGraphics);
            Blocks.LEAVES2.setGraphicsLevel(HookTranslator.mc.gameSettings.fancyGraphics);
            this.renderDistanceChunks = HookTranslator.mc.gameSettings.renderDistanceChunks;
            final boolean vboEnabled = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (vboEnabled && !this.vboEnabled) {
                this.renderContainer = (ChunkRenderContainer)new RenderList();
                this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
            }
            else if (!vboEnabled && this.vboEnabled) {
                this.renderContainer = (ChunkRenderContainer)new VboRenderList();
                this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
            }
            if (vboEnabled != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }
            if (this.viewFrustum2 != null) {
                this.viewFrustum2.deleteGlResources();
            }
            this.stopChunkUpdates();
            synchronized (this.setTileEntities) {
                this.setTileEntities.clear();
            }
            this.viewFrustum2 = new ViewFrustum((World)this.world, HookTranslator.mc.gameSettings.renderDistanceChunks, RenderGlobal.class.cast(this), this.renderChunkFactory);
            if (this.world != null) {
                final Entity renderViewEntity = HookTranslator.mc.getRenderViewEntity();
                if (renderViewEntity != null) {
                    this.viewFrustum2.updateChunkPositions(renderViewEntity.posX, renderViewEntity.posZ);
                }
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }
    
    @Overwrite
    public int getRenderedChunks() {
        int n = 0;
        final Iterator<ContainerLocalRenderInformation> iterator = (Iterator<ContainerLocalRenderInformation>)this.renderInfos.iterator();
        while (iterator.hasNext()) {
            final CompiledChunk compiledChunk = iterator.next().renderChunk.compiledChunk;
            if (compiledChunk != CompiledChunk.DUMMY && !compiledChunk.isEmpty()) {
                ++n;
            }
        }
        return n;
    }
    
    @Overwrite
    public int renderBlockLayer(final BlockRenderLayer blockRenderLayer, final double n, final int n2, final Entity entity) {
        RenderHelper.disableStandardItemLighting();
        if (blockRenderLayer == BlockRenderLayer.TRANSLUCENT) {
            HookTranslator.mc.profiler.startSection("translucent_sort");
            final double n3 = entity.posX - this.prevRenderSortX;
            final double n4 = entity.posY - this.prevRenderSortY;
            final double n5 = entity.posZ - this.prevRenderSortZ;
            if (n3 * n3 + n4 * n4 + n5 * n5 > 1.0) {
                this.prevRenderSortX = entity.posX;
                this.prevRenderSortY = entity.posY;
                this.prevRenderSortZ = entity.posZ;
                int n6 = 0;
                for (final ContainerLocalRenderInformation containerLocalRenderInformation : this.renderInfos) {
                    if (containerLocalRenderInformation.renderChunk.compiledChunk.isLayerStarted(blockRenderLayer) && n6++ < 15) {
                        this.renderDispatcher.updateTransparencyLater(containerLocalRenderInformation.renderChunk);
                    }
                }
            }
            HookTranslator.mc.profiler.endSection();
        }
        HookTranslator.mc.profiler.startSection("filterempty");
        int n7 = 0;
        final boolean b = blockRenderLayer == BlockRenderLayer.TRANSLUCENT;
        final int n8 = b ? (this.renderInfos.size() - 1) : 0;
        for (int n9 = b ? -1 : this.renderInfos.size(), n10 = b ? -1 : 1, i = n8; i != n9; i += n10) {
            final RenderChunk renderChunk = this.renderInfos.get(i).renderChunk;
            if (!renderChunk.getCompiledChunk().isLayerEmpty(blockRenderLayer)) {
                ++n7;
                this.renderContainer.addRenderChunk(renderChunk, blockRenderLayer);
            }
        }
        HookTranslator.mc.profiler.func_194339_b((Supplier)MixinRenderGlobal::lambda$renderBlockLayer$0);
        this.renderBlockLayer(blockRenderLayer);
        HookTranslator.mc.profiler.endSection();
        return n7;
    }
    
    @Overwrite
    public void renderEntities(final Entity entity, final ICamera camera, final float n) {
        final int renderPass = MinecraftForgeClient.getRenderPass();
        if (this.renderEntitiesStartupCounter > 0) {
            if (renderPass > 0) {
                return;
            }
            --this.renderEntitiesStartupCounter;
        }
        else {
            final double n2 = entity.prevPosX + (entity.posX - entity.prevPosX) * n;
            final double n3 = entity.prevPosY + (entity.posY - entity.prevPosY) * n;
            final double n4 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * n;
            this.world.profiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.prepare((World)this.world, HookTranslator.mc.getTextureManager(), HookTranslator.mc.fontRenderer, HookTranslator.mc.getRenderViewEntity(), HookTranslator.mc.objectMouseOver, n);
            this.renderManager.cacheActiveRenderInfo((World)this.world, HookTranslator.mc.fontRenderer, HookTranslator.mc.getRenderViewEntity(), HookTranslator.mc.pointedEntity, HookTranslator.mc.gameSettings, n);
            if (renderPass == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
            }
            final Entity renderViewEntity = HookTranslator.mc.getRenderViewEntity();
            final double staticPlayerX = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n;
            double m45;
            if (HookTranslator.v20) {
                m45 = HookTranslator.m45();
            }
            else {
                m45 = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n;
            }
            final double staticPlayerZ = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n;
            TileEntityRendererDispatcher.staticPlayerX = staticPlayerX;
            TileEntityRendererDispatcher.staticPlayerY = m45;
            TileEntityRendererDispatcher.staticPlayerZ = staticPlayerZ;
            this.renderManager.setRenderPosition(staticPlayerX, m45, staticPlayerZ);
            HookTranslator.mc.entityRenderer.enableLightmap();
            this.world.profiler.endStartSection("global");
            final List loadedEntityList = this.world.getLoadedEntityList();
            if (renderPass == 0) {
                this.countEntitiesTotal = loadedEntityList.size();
            }
            for (int i = 0; i < this.world.weatherEffects.size(); ++i) {
                final Entity entity2 = this.world.weatherEffects.get(i);
                if (entity2.shouldRenderInPass(renderPass)) {
                    ++this.countEntitiesRendered;
                    if (entity2.isInRangeToRender3d(n2, n3, n4)) {
                        this.renderManager.renderEntityStatic(entity2, n, false);
                    }
                }
            }
            this.world.profiler.endStartSection("entities");
            final ArrayList<Object> arrayList = Lists.newArrayList();
            final ArrayList<Object> arrayList2 = Lists.newArrayList();
            final BlockPos.PooledMutableBlockPos retain = BlockPos.PooledMutableBlockPos.retain();
            for (final ContainerLocalRenderInformation containerLocalRenderInformation : this.renderInfos) {
                final ClassInheritanceMultiMap classInheritanceMultiMap = this.world.getChunk(containerLocalRenderInformation.renderChunk.getPosition()).getEntityLists()[containerLocalRenderInformation.renderChunk.getPosition().getY() / 16];
                if (!classInheritanceMultiMap.isEmpty()) {
                    for (final Entity pos : classInheritanceMultiMap) {
                        if (!pos.shouldRenderInPass(renderPass)) {
                            continue;
                        }
                        if (!this.renderManager.shouldRender(pos, camera, n2, n3, n4) && !pos.isRidingOrBeingRiddenBy((Entity)HookTranslator.mc.player)) {
                            continue;
                        }
                        final boolean b = HookTranslator.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)HookTranslator.mc.getRenderViewEntity()).isPlayerSleeping();
                        if ((pos == HookTranslator.mc.getRenderViewEntity() && HookTranslator.mc.gameSettings.thirdPersonView == 0 && !b) || (pos.posY >= 0.0 && pos.posY < 256.0 && !this.world.isBlockLoaded((BlockPos)retain.setPos(pos)))) {
                            continue;
                        }
                        ++this.countEntitiesRendered;
                        this.renderManager.renderEntityStatic(pos, n, false);
                        if (this.isOutlineActive(pos, renderViewEntity, camera)) {
                            arrayList.add(pos);
                        }
                        if (!this.renderManager.isRenderMultipass(pos)) {
                            continue;
                        }
                        arrayList2.add(pos);
                    }
                }
            }
            retain.release();
            if (!arrayList2.isEmpty()) {
                final Iterator<Entity> iterator3 = arrayList2.iterator();
                while (iterator3.hasNext()) {
                    this.renderManager.renderMultipass((Entity)iterator3.next(), n);
                }
            }
            if (renderPass == 0 && this.isRenderEntityOutlines() && (!arrayList.isEmpty() || this.entityOutlinesRendered)) {
                this.world.profiler.endStartSection("entityOutlines");
                this.entityOutlineFramebuffer.framebufferClear();
                this.entityOutlinesRendered = !arrayList.isEmpty();
                if (!arrayList.isEmpty()) {
                    GlStateManager.depthFunc(519);
                    GlStateManager.disableFog();
                    this.entityOutlineFramebuffer.bindFramebuffer(false);
                    RenderHelper.disableStandardItemLighting();
                    this.renderManager.setRenderOutlines(true);
                    for (int j = 0; j < arrayList.size(); ++j) {
                        this.renderManager.renderEntityStatic((Entity)arrayList.get(j), n, false);
                    }
                    this.renderManager.setRenderOutlines(false);
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.depthMask(false);
                    this.entityOutlineShader.render(n);
                    GlStateManager.enableLighting();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableFog();
                    GlStateManager.enableBlend();
                    GlStateManager.enableColorMaterial();
                    GlStateManager.depthFunc(515);
                    GlStateManager.enableDepth();
                    GlStateManager.enableAlpha();
                }
                HookTranslator.mc.getFramebuffer().bindFramebuffer(false);
            }
            this.world.profiler.endStartSection("blockentities");
            RenderHelper.enableStandardItemLighting();
            TileEntityRendererDispatcher.instance.preDrawBatch();
            final Iterator<ContainerLocalRenderInformation> iterator4 = this.renderInfos.iterator();
            while (iterator4.hasNext()) {
                final List tileEntities = iterator4.next().renderChunk.getCompiledChunk().getTileEntities();
                if (!tileEntities.isEmpty()) {
                    for (final TileEntity tileEntity : tileEntities) {
                        if (tileEntity.shouldRenderInPass(renderPass)) {
                            if (!camera.isBoundingBoxInFrustum(tileEntity.getRenderBoundingBox())) {
                                continue;
                            }
                            TileEntityRendererDispatcher.instance.render(tileEntity, n, -1);
                        }
                    }
                }
            }
            synchronized (this.setTileEntities) {
                for (final TileEntity tileEntity2 : this.setTileEntities) {
                    if (tileEntity2.shouldRenderInPass(renderPass)) {
                        if (!camera.isBoundingBoxInFrustum(tileEntity2.getRenderBoundingBox())) {
                            continue;
                        }
                        TileEntityRendererDispatcher.instance.render(tileEntity2, n, -1);
                    }
                }
            }
            TileEntityRendererDispatcher.instance.drawBatch(renderPass);
            this.preRenderDamagedBlocks();
            for (final DestroyBlockProgress destroyBlockProgress : this.damagedBlocks.values()) {
                BlockPos blockPos = destroyBlockProgress.getPosition();
                if (this.world.getBlockState(blockPos).getBlock().hasTileEntity()) {
                    TileEntity tileEntity3 = this.world.getTileEntity(blockPos);
                    if (tileEntity3 instanceof TileEntityChest) {
                        final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity3;
                        if (tileEntityChest.adjacentChestXNeg != null) {
                            blockPos = blockPos.offset(EnumFacing.WEST);
                            tileEntity3 = this.world.getTileEntity(blockPos);
                        }
                        else if (tileEntityChest.adjacentChestZNeg != null) {
                            blockPos = blockPos.offset(EnumFacing.NORTH);
                            tileEntity3 = this.world.getTileEntity(blockPos);
                        }
                    }
                    final IBlockState blockState = this.world.getBlockState(blockPos);
                    if (tileEntity3 == null || !blockState.hasCustomBreakingProgress()) {
                        continue;
                    }
                    TileEntityRendererDispatcher.instance.render(tileEntity3, n, destroyBlockProgress.getPartialBlockDamage());
                }
            }
            this.postRenderDamagedBlocks();
            HookTranslator.mc.entityRenderer.disableLightmap();
            HookTranslator.mc.profiler.endSection();
        }
    }
    
    @Overwrite
    public String getDebugInfoRenders() {
        return String.format("C: %d/%d %sD: %d, L: %d, %s", this.getRenderedChunks(), this.viewFrustum2.renderChunks.length, HookTranslator.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.setLightUpdates.size(), (this.renderDispatcher == null) ? "null" : this.renderDispatcher.getDebugInfo());
    }
    
    public RenderChunk getRenderChunkOffset(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos blockPosOffset16 = renderChunk.getBlockPosOffset16(enumFacing);
        if (MathHelper.abs(blockPos.getX() - blockPosOffset16.getX()) > this.renderDistanceChunks * 16) {
            return null;
        }
        if (blockPosOffset16.getY() >= 0 && blockPosOffset16.getY() < 256) {
            return (MathHelper.abs(blockPos.getZ() - blockPosOffset16.getZ()) > this.renderDistanceChunks * 16) ? null : this.viewFrustum2.getRenderChunk(blockPosOffset16);
        }
        return null;
    }
    
    @Overwrite
    public void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b) {
        this.viewFrustum2.markBlocksForUpdate(n, n2, n3, n4, n5, n6, b);
    }
    
    @Overwrite
    public void drawSelectionBox(final EntityPlayer entityPlayer, final RayTraceResult rayTraceResult, final int n, final float n2) {
        if (n == 0 && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(2.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            final BlockPos blockPos = rayTraceResult.getBlockPos();
            final IBlockState blockState = this.world.getBlockState(blockPos);
            if (blockState.getMaterial() != Material.AIR && this.world.getWorldBorder().contains(blockPos)) {
                final double n3 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * n2;
                double m45;
                if (HookTranslator.v20) {
                    m45 = HookTranslator.m45();
                }
                else {
                    m45 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * n2;
                }
                RenderGlobal.drawSelectionBoundingBox(blockState.getSelectedBoundingBox((World)this.world, blockPos).grow(0.0020000000949949026).offset(-n3, -m45, -(entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * n2)), 0.0f, 0.0f, 0.0f, 0.4f);
            }
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
    
    private static String lambda$renderBlockLayer$0(final BlockRenderLayer blockRenderLayer) {
        return "render_" + blockRenderLayer;
    }
}
