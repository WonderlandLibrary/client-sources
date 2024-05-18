package net.minecraft.client.renderer.chunk;

import java.nio.*;
import java.util.concurrent.locks.*;
import net.minecraft.client.renderer.vertex.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.state.*;
import optfine.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class RenderChunk
{
    public CompiledChunk compiledChunk;
    private final int index;
    private final FloatBuffer modelviewMatrix;
    private static final String[] I;
    private final RenderGlobal renderGlobal;
    private boolean needsUpdate;
    private final Set field_181056_j;
    private World world;
    private static final String __OBFID;
    private final VertexBuffer[] vertexBuffers;
    private EnumMap field_181702_p;
    public AxisAlignedBB boundingBox;
    private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS;
    public static int renderChunksUpdated;
    private int frameIndex;
    private EnumWorldBlockLayer[] blockLayersSingle;
    private final ReentrantLock lockCompileTask;
    private BlockPos[] positionOffsets16;
    private BlockPos position;
    private ChunkCompileTaskGenerator compileTask;
    private final ReentrantLock lockCompiledChunk;
    
    public BlockPos func_181701_a(final EnumFacing enumFacing) {
        return this.getPositionOffset16(enumFacing);
    }
    
    private void preRenderBlocks(final WorldRenderer worldRenderer, final BlockPos blockPos) {
        worldRenderer.begin(0x6D ^ 0x6A, DefaultVertexFormats.BLOCK);
        worldRenderer.setTranslation(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
    }
    
    public void stopCompileTask() {
        this.finishCompileTask();
        this.compiledChunk = CompiledChunk.DUMMY;
    }
    
    public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
        this.lockCompileTask.lock();
        ChunkCompileTaskGenerator compileTask;
        try {
            if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
                return null;
            }
            if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
                this.compileTask.finish();
                this.compileTask = null;
            }
            (this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY)).setCompiledChunk(this.compiledChunk);
            compileTask = this.compileTask;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        finally {
            this.lockCompileTask.unlock();
        }
        this.lockCompileTask.unlock();
        return compileTask;
    }
    
    public void setCompiledChunk(final CompiledChunk compiledChunk) {
        this.lockCompiledChunk.lock();
        try {
            this.compiledChunk = compiledChunk;
            "".length();
            if (!true) {
                throw null;
            }
        }
        finally {
            this.lockCompiledChunk.unlock();
        }
        this.lockCompiledChunk.unlock();
    }
    
    private void postRenderBlocks(final EnumWorldBlockLayer enumWorldBlockLayer, final float n, final float n2, final float n3, final WorldRenderer worldRenderer, final CompiledChunk compiledChunk) {
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunk.isLayerEmpty(enumWorldBlockLayer)) {
            worldRenderer.func_181674_a(n, n2, n3);
            compiledChunk.setState(worldRenderer.func_181672_a());
        }
        worldRenderer.finishDrawing();
    }
    
    public void rebuildChunk(final float n, final float n2, final float n3, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        final CompiledChunk compiledChunk = new CompiledChunk();
        " ".length();
        final BlockPos position = this.position;
        final BlockPos add = position.add(0x43 ^ 0x4C, 0x2 ^ 0xD, 0x66 ^ 0x69);
        chunkCompileTaskGenerator.getLock().lock();
        RegionRenderCache regionRenderCache;
        try {
            if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
                return;
            }
            if (this.world == null) {
                return;
            }
            regionRenderCache = new RegionRenderCache(this.world, position.add(-" ".length(), -" ".length(), -" ".length()), add.add(" ".length(), " ".length(), " ".length()), " ".length());
            chunkCompileTaskGenerator.setCompiledChunk(compiledChunk);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        finally {
            chunkCompileTaskGenerator.getLock().unlock();
        }
        chunkCompileTaskGenerator.getLock().unlock();
        final VisGraph visGraph = new VisGraph();
        final HashSet hashSet = Sets.newHashSet();
        if (!regionRenderCache.extendedLevelsInChunkCache()) {
            RenderChunk.renderChunksUpdated += " ".length();
            final boolean[] array = new boolean[EnumWorldBlockLayer.values().length];
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            final Iterator iterator = BlockPosM.getAllInBoxMutable(position, add).iterator();
            final boolean exists = Reflector.ForgeBlock_hasTileEntity.exists();
            final boolean exists2 = Reflector.ForgeBlock_canRenderInLayer.exists();
            final boolean exists3 = Reflector.ForgeHooksClient_setRenderLayer.exists();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockPosM blockPosM = iterator.next();
                final IBlockState blockState = regionRenderCache.getBlockState(blockPosM);
                final Block block = blockState.getBlock();
                if (block.isOpaqueCube()) {
                    visGraph.func_178606_a(blockPosM);
                }
                boolean b;
                if (exists) {
                    final Iterator<BlockPosM> iterator2 = (Iterator<BlockPosM>)iterator;
                    final ReflectorMethod forgeBlock_hasTileEntity = Reflector.ForgeBlock_hasTileEntity;
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = blockRendererDispatcher;
                    b = Reflector.callBoolean(iterator2, forgeBlock_hasTileEntity, array2);
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    b = block.hasTileEntity();
                }
                if (b) {
                    final TileEntity tileEntity = regionRenderCache.getTileEntity(new BlockPos(blockPosM));
                    final TileEntitySpecialRenderer<TileEntity> specialRenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileEntity);
                    if (tileEntity != null && specialRenderer != null) {
                        compiledChunk.addTileEntity(tileEntity);
                        if (specialRenderer.func_181055_a()) {
                            hashSet.add(tileEntity);
                        }
                    }
                }
                EnumWorldBlockLayer[] array3;
                if (exists2) {
                    array3 = RenderChunk.ENUM_WORLD_BLOCK_LAYERS;
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else {
                    array3 = this.blockLayersSingle;
                    array3["".length()] = block.getBlockLayer();
                }
                int i = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (i < array3.length) {
                    final EnumWorldBlockLayer enumWorldBlockLayer = array3[i];
                    Label_0694: {
                        if (exists2) {
                            final Block block2 = block;
                            final ReflectorMethod forgeBlock_canRenderInLayer = Reflector.ForgeBlock_canRenderInLayer;
                            final Object[] array4 = new Object[" ".length()];
                            array4["".length()] = enumWorldBlockLayer;
                            if (!Reflector.callBoolean(block2, forgeBlock_canRenderInLayer, array4)) {
                                "".length();
                                if (-1 >= 4) {
                                    throw null;
                                }
                                break Label_0694;
                            }
                        }
                        final EnumWorldBlockLayer fixBlockLayer = this.fixBlockLayer(block, enumWorldBlockLayer);
                        if (exists3) {
                            final ReflectorMethod forgeHooksClient_setRenderLayer = Reflector.ForgeHooksClient_setRenderLayer;
                            final Object[] array5 = new Object[" ".length()];
                            array5["".length()] = fixBlockLayer;
                            Reflector.callVoid(forgeHooksClient_setRenderLayer, array5);
                        }
                        final int ordinal = fixBlockLayer.ordinal();
                        if (block.getRenderType() != -" ".length()) {
                            final WorldRenderer worldRendererByLayerId = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(ordinal);
                            worldRendererByLayerId.setBlockLayer(fixBlockLayer);
                            if (!compiledChunk.isLayerStarted(fixBlockLayer)) {
                                compiledChunk.setLayerStarted(fixBlockLayer);
                                this.preRenderBlocks(worldRendererByLayerId, position);
                            }
                            final boolean[] array6 = array;
                            final int n4 = ordinal;
                            array6[n4] |= blockRendererDispatcher.renderBlock(blockState, blockPosM, regionRenderCache, worldRendererByLayerId);
                        }
                    }
                    ++i;
                }
            }
            final EnumWorldBlockLayer[] values;
            final int length = (values = EnumWorldBlockLayer.values()).length;
            int j = "".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
            while (j < length) {
                final EnumWorldBlockLayer layerUsed = values[j];
                if (array[layerUsed.ordinal()]) {
                    compiledChunk.setLayerUsed(layerUsed);
                }
                if (compiledChunk.isLayerStarted(layerUsed)) {
                    this.postRenderBlocks(layerUsed, n, n2, n3, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(layerUsed), compiledChunk);
                }
                ++j;
            }
        }
        compiledChunk.setVisibility(visGraph.computeVisibility());
        this.lockCompileTask.lock();
        try {
            final HashSet hashSet2 = Sets.newHashSet((Iterable)hashSet);
            final HashSet hashSet3 = Sets.newHashSet((Iterable)this.field_181056_j);
            hashSet2.removeAll(this.field_181056_j);
            hashSet3.removeAll(hashSet);
            this.field_181056_j.clear();
            this.field_181056_j.addAll(hashSet);
            this.renderGlobal.func_181023_a(hashSet3, hashSet2);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        finally {
            this.lockCompileTask.unlock();
        }
        this.lockCompileTask.unlock();
    }
    
    public void multModelviewMatrix() {
        GlStateManager.multMatrix(this.modelviewMatrix);
    }
    
    public VertexBuffer getVertexBufferByLayer(final int n) {
        return this.vertexBuffers[n];
    }
    
    public void resortTransparency(final float n, final float n2, final float n3, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        final CompiledChunk compiledChunk = chunkCompileTaskGenerator.getCompiledChunk();
        if (compiledChunk.getState() != null && !compiledChunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
            final WorldRenderer worldRendererByLayer = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
            this.preRenderBlocks(worldRendererByLayer, this.position);
            worldRendererByLayer.setVertexState(compiledChunk.getState());
            this.postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, n, n2, n3, worldRendererByLayer, compiledChunk);
        }
    }
    
    public ReentrantLock getLockCompileTask() {
        return this.lockCompileTask;
    }
    
    public boolean setFrameIndex(final int frameIndex) {
        if (this.frameIndex == frameIndex) {
            return "".length() != 0;
        }
        this.frameIndex = frameIndex;
        return " ".length() != 0;
    }
    
    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }
    
    public ChunkCompileTaskGenerator makeCompileTaskChunk() {
        this.lockCompileTask.lock();
        ChunkCompileTaskGenerator compileTask;
        try {
            this.finishCompileTask();
            this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
            compileTask = this.compileTask;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        finally {
            this.lockCompileTask.unlock();
        }
        this.lockCompileTask.unlock();
        return compileTask;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPos getPositionOffset16(final EnumFacing enumFacing) {
        final int index = enumFacing.getIndex();
        BlockPos offset = this.positionOffsets16[index];
        if (offset == null) {
            offset = this.getPosition().offset(enumFacing, 0x5E ^ 0x4E);
            this.positionOffsets16[index] = offset;
        }
        return offset;
    }
    
    private EnumWorldBlockLayer fixBlockLayer(final Block block, final EnumWorldBlockLayer enumWorldBlockLayer) {
        EnumWorldBlockLayer cutout_MIPPED;
        if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT) {
            if (block instanceof BlockRedstoneWire) {
                cutout_MIPPED = enumWorldBlockLayer;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if (block instanceof BlockCactus) {
                cutout_MIPPED = enumWorldBlockLayer;
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                cutout_MIPPED = EnumWorldBlockLayer.CUTOUT_MIPPED;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            cutout_MIPPED = enumWorldBlockLayer;
        }
        return cutout_MIPPED;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000e?\u0017{@}Cz\u007fE\u007f", "MsHKp");
    }
    
    protected void finishCompileTask() {
        this.lockCompileTask.lock();
        try {
            if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
                this.compileTask.finish();
                this.compileTask = null;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
        }
        finally {
            this.lockCompileTask.unlock();
        }
        this.lockCompileTask.unlock();
    }
    
    public void deleteGlResources() {
        this.stopCompileTask();
        this.world = null;
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < EnumWorldBlockLayer.values().length) {
            if (this.vertexBuffers[i] != null) {
                this.vertexBuffers[i].deleteGlBuffers();
            }
            ++i;
        }
    }
    
    static {
        I();
        __OBFID = RenderChunk.I["".length()];
        RenderChunk.ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
    }
    
    private void initModelviewMatrix() {
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        final float n = 1.000001f;
        GlStateManager.translate(-8.0f, -8.0f, -8.0f);
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.getFloat(2082 + 982 - 1264 + 1182, this.modelviewMatrix);
        GlStateManager.popMatrix();
    }
    
    public void setNeedsUpdate(final boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    
    public RenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos position, final int index) {
        this.compiledChunk = CompiledChunk.DUMMY;
        this.lockCompileTask = new ReentrantLock();
        this.lockCompiledChunk = new ReentrantLock();
        this.compileTask = null;
        this.field_181056_j = Sets.newHashSet();
        this.modelviewMatrix = GLAllocation.createDirectFloatBuffer(0x91 ^ 0x81);
        this.vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.frameIndex = -" ".length();
        this.needsUpdate = (" ".length() != 0);
        this.positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
        this.blockLayersSingle = new EnumWorldBlockLayer[" ".length()];
        this.world = world;
        this.renderGlobal = renderGlobal;
        this.index = index;
        if (!position.equals(this.getPosition())) {
            this.setPosition(position);
        }
        if (OpenGlHelper.useVbo()) {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < EnumWorldBlockLayer.values().length) {
                this.vertexBuffers[i] = new VertexBuffer(DefaultVertexFormats.BLOCK);
                ++i;
            }
        }
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public boolean isNeedsUpdate() {
        return this.needsUpdate;
    }
    
    public void setPosition(final BlockPos position) {
        this.stopCompileTask();
        this.position = position;
        this.boundingBox = new AxisAlignedBB(position, position.add(0xB4 ^ 0xA4, 0xBE ^ 0xAE, 0xAE ^ 0xBE));
        final int length = EnumFacing.values().length;
        this.initModelviewMatrix();
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.positionOffsets16.length) {
            this.positionOffsets16[i] = null;
            ++i;
        }
    }
}
