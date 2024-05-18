package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.client.shader.*;
import java.io.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.chunk.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.world.border.*;
import org.lwjgl.util.vector.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.audio.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.entity.*;
import optfine.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;
import java.util.*;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener
{
    private List renderInfos;
    private int countEntitiesRendered;
    public Entity renderedEntity;
    private ShaderGroup entityOutlineShader;
    private int starGLCallList;
    private static final ResourceLocation locationSunPng;
    private final ChunkRenderDispatcher renderDispatcher;
    private final TextureAtlasSprite[] destroyBlockIcons;
    private VertexFormat vertexBufferFormat;
    private static final ResourceLocation locationMoonPhasesPng;
    private double lastViewEntityPitch;
    private double frustumUpdatePosZ;
    private VertexBuffer skyVBO;
    private double frustumUpdatePosY;
    public final Minecraft mc;
    private int renderDistanceSq;
    private double lastViewEntityX;
    public Set chunksToResortTransparency;
    private Framebuffer entityOutlineFramebuffer;
    private ChunkRenderContainer renderContainer;
    private static final Set SET_ALL_FACINGS;
    private final Set field_181024_n;
    private final TextureManager renderEngine;
    private int countEntitiesTotal;
    private int frustumUpdatePosChunkZ;
    private int renderDistance;
    private static final ResourceLocation locationEndSkyPng;
    private int cloudTickCounter;
    private List renderInfosTileEntities;
    private double prevRenderSortZ;
    private WorldClient theWorld;
    private ViewFrustum viewFrustum;
    private int renderDistanceChunks;
    private int frustumUpdatePosChunkY;
    private double lastViewEntityZ;
    private int renderEntitiesStartupCounter;
    IRenderChunkFactory renderChunkFactory;
    private CloudRenderer cloudRenderer;
    private double lastViewEntityY;
    private VertexBuffer starVBO;
    private double prevRenderSortY;
    private int countEntitiesHidden;
    private final Vector4f[] debugTerrainMatrix;
    private static final String __OBFID;
    private static final ResourceLocation locationCloudsPng;
    public Set chunksToUpdateForced;
    private boolean vboEnabled;
    private int frustumUpdatePosChunkX;
    private final RenderManager renderManager;
    private double lastViewEntityYaw;
    private final Map mapSoundPositions;
    private double prevRenderSortX;
    private double frustumUpdatePosX;
    private int glSkyList2;
    private List renderInfosEntities;
    private static final ResourceLocation locationForcefieldPng;
    private static final Logger logger;
    private final Vector3d debugTerrainFrustumPosition;
    private boolean debugFixTerrainFrustum;
    public final Map damagedBlocks;
    private static final String[] I;
    private Deque visibilityDeque;
    private int countTileEntitiesRendered;
    private VertexBuffer sky2VBO;
    public boolean displayListEntitiesDirty;
    private int glSkyList;
    private Set chunksToUpdate;
    private ClippingHelper debugFixedClippingHelper;
    
    private void renderSky(final WorldRenderer worldRenderer, final float n, final boolean b) {
        " ".length();
        " ".length();
        worldRenderer.begin(0x61 ^ 0x66, DefaultVertexFormats.POSITION);
        int i = -(216 + 167 - 148 + 149);
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (i <= 20 + 176 - 3 + 191) {
            int j = -(325 + 275 - 500 + 284);
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j <= 240 + 160 - 260 + 244) {
                float n2 = i;
                float n3 = i + (0x3 ^ 0x43);
                if (b) {
                    n3 = i;
                    n2 = i + (0xD4 ^ 0x94);
                }
                worldRenderer.pos(n2, n, j).endVertex();
                worldRenderer.pos(n3, n, j).endVertex();
                worldRenderer.pos(n3, n, j + (0x6F ^ 0x2F)).endVertex();
                worldRenderer.pos(n2, n, j + (0x29 ^ 0x69)).endVertex();
                j += 64;
            }
            i += 64;
        }
    }
    
    public void drawSelectionBox(final EntityPlayer entityPlayer, final MovingObjectPosition movingObjectPosition, final int n, final float n2) {
        if (n == 0 && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(450 + 435 - 701 + 586, 603 + 676 - 917 + 409, " ".length(), "".length());
            GlStateManager.color(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask("".length() != 0);
            final BlockPos blockPos = movingObjectPosition.getBlockPos();
            final Block block = this.theWorld.getBlockState(blockPos).getBlock();
            if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockPos)) {
                block.setBlockBoundsBasedOnState(this.theWorld, blockPos);
                func_181561_a(block.getSelectedBoundingBox(this.theWorld, blockPos).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).offset(-(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * n2), -(entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * n2), -(entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * n2)));
            }
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
    
    public void makeEntityOutlineShader() {
        if (OpenGlHelper.shadersSupported) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }
            final ResourceLocation resourceLocation = new ResourceLocation(RenderGlobal.I[0x98 ^ 0x9F]);
            try {
                (this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourceLocation)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw(RenderGlobal.I[0x8F ^ 0x87]);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                return;
            }
            catch (IOException ex) {
                RenderGlobal.logger.warn(RenderGlobal.I[0x96 ^ 0x9F] + resourceLocation, (Throwable)ex);
                this.entityOutlineShader = null;
                this.entityOutlineFramebuffer = null;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                return;
            }
            catch (JsonSyntaxException ex2) {
                RenderGlobal.logger.warn(RenderGlobal.I[0x52 ^ 0x58] + resourceLocation, (Throwable)ex2);
                this.entityOutlineShader = null;
                this.entityOutlineFramebuffer = null;
                "".length();
                if (0 == -1) {
                    throw null;
                }
                return;
            }
        }
        this.entityOutlineShader = null;
        this.entityOutlineFramebuffer = null;
    }
    
    static {
        I();
        __OBFID = RenderGlobal.I["".length()];
        logger = LogManager.getLogger();
        locationMoonPhasesPng = new ResourceLocation(RenderGlobal.I[" ".length()]);
        locationSunPng = new ResourceLocation(RenderGlobal.I["  ".length()]);
        locationCloudsPng = new ResourceLocation(RenderGlobal.I["   ".length()]);
        locationEndSkyPng = new ResourceLocation(RenderGlobal.I[0xC0 ^ 0xC4]);
        locationForcefieldPng = new ResourceLocation(RenderGlobal.I[0x44 ^ 0x41]);
        SET_ALL_FACINGS = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(Arrays.asList(EnumFacing.VALUES)));
    }
    
    private Set getVisibleFacings(final BlockPos blockPos) {
        final VisGraph visGraph = new VisGraph();
        final BlockPos blockPos2 = new BlockPos(blockPos.getX() >> (0x8E ^ 0x8A) << (0x68 ^ 0x6C), blockPos.getY() >> (0x95 ^ 0x91) << (0x4D ^ 0x49), blockPos.getZ() >> (0x11 ^ 0x15) << (0x67 ^ 0x63));
        final Chunk chunkFromBlockCoords = this.theWorld.getChunkFromBlockCoords(blockPos2);
        final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos2, blockPos2.add(0xB3 ^ 0xBC, 0x22 ^ 0x2D, 0x64 ^ 0x6B)).iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos.MutableBlockPos mutableBlockPos = iterator.next();
            if (chunkFromBlockCoords.getBlock(mutableBlockPos).isOpaqueCube()) {
                visGraph.func_178606_a(mutableBlockPos);
            }
        }
        return visGraph.func_178609_b(blockPos);
    }
    
    @Override
    public void broadcastSound(final int n, final BlockPos blockPos, final int n2) {
        switch (n) {
            case 1013:
            case 1018: {
                if (this.mc.getRenderViewEntity() == null) {
                    break;
                }
                final double n3 = blockPos.getX() - this.mc.getRenderViewEntity().posX;
                final double n4 = blockPos.getY() - this.mc.getRenderViewEntity().posY;
                final double n5 = blockPos.getZ() - this.mc.getRenderViewEntity().posZ;
                final double sqrt = Math.sqrt(n3 * n3 + n4 * n4 + n5 * n5);
                double posX = this.mc.getRenderViewEntity().posX;
                double posY = this.mc.getRenderViewEntity().posY;
                double posZ = this.mc.getRenderViewEntity().posZ;
                if (sqrt > 0.0) {
                    posX += n3 / sqrt * 2.0;
                    posY += n4 / sqrt * 2.0;
                    posZ += n5 / sqrt * 2.0;
                }
                if (n != 651 + 598 - 1209 + 973) {
                    this.theWorld.playSound(posX, posY, posZ, RenderGlobal.I[0xBC ^ 0x9A], 5.0f, 1.0f, "".length() != 0);
                    break;
                }
                this.theWorld.playSound(posX, posY, posZ, RenderGlobal.I[0x9 ^ 0x2C], 1.0f, 1.0f, "".length() != 0);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
        }
    }
    
    public int renderBlockLayer(final EnumWorldBlockLayer enumWorldBlockLayer, final double n, final int n2, final Entity entity) {
        RenderHelper.disableStandardItemLighting();
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
            this.mc.mcProfiler.startSection(RenderGlobal.I[0xBD ^ 0xA0]);
            final double n3 = entity.posX - this.prevRenderSortX;
            final double n4 = entity.posY - this.prevRenderSortY;
            final double n5 = entity.posZ - this.prevRenderSortZ;
            if (n3 * n3 + n4 * n4 + n5 * n5 > 1.0) {
                this.prevRenderSortX = entity.posX;
                this.prevRenderSortY = entity.posY;
                this.prevRenderSortZ = entity.posZ;
                int length = "".length();
                final Iterator iterator = this.renderInfos.iterator();
                this.chunksToResortTransparency.clear();
                "".length();
                if (4 < 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final ContainerLocalRenderInformation containerLocalRenderInformation = iterator.next();
                    if (containerLocalRenderInformation.renderChunk.compiledChunk.isLayerStarted(enumWorldBlockLayer) && length++ < (0x56 ^ 0x59)) {
                        this.chunksToResortTransparency.add(containerLocalRenderInformation.renderChunk);
                    }
                }
            }
            this.mc.mcProfiler.endSection();
        }
        this.mc.mcProfiler.startSection(RenderGlobal.I[0xB3 ^ 0xAD]);
        int length2 = "".length();
        int n6;
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
            n6 = " ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            n6 = "".length();
        }
        final int n7 = n6;
        int length3;
        if (n7 != 0) {
            length3 = this.renderInfos.size() - " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            length3 = "".length();
        }
        final int n8 = length3;
        int size;
        if (n7 != 0) {
            size = -" ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            size = this.renderInfos.size();
        }
        final int n9 = size;
        int length4;
        if (n7 != 0) {
            length4 = -" ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            length4 = " ".length();
        }
        final int n10 = length4;
        int i = n8;
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i != n9) {
            final RenderChunk renderChunk = this.renderInfos.get(i).renderChunk;
            if (!renderChunk.getCompiledChunk().isLayerEmpty(enumWorldBlockLayer)) {
                ++length2;
                this.renderContainer.addRenderChunk(renderChunk, enumWorldBlockLayer);
            }
            i += n10;
        }
        if (length2 == 0) {
            return length2;
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
        }
        this.mc.mcProfiler.endStartSection(RenderGlobal.I[0xDB ^ 0xC4] + enumWorldBlockLayer);
        this.renderBlockLayer(enumWorldBlockLayer);
        this.mc.mcProfiler.endSection();
        return length2;
    }
    
    @Override
    public void notifyLightSet(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - " ".length(), y - " ".length(), z - " ".length(), x + " ".length(), y + " ".length(), z + " ".length());
    }
    
    public RenderGlobal(final Minecraft mc) {
        this.chunksToUpdate = Sets.newLinkedHashSet();
        this.renderInfos = Lists.newArrayListWithCapacity(25831 + 21525 - 11547 + 33887);
        this.field_181024_n = Sets.newHashSet();
        this.starGLCallList = -" ".length();
        this.glSkyList = -" ".length();
        this.glSkyList2 = -" ".length();
        this.damagedBlocks = Maps.newHashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.destroyBlockIcons = new TextureAtlasSprite[0xB1 ^ 0xBB];
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = -"".length();
        this.frustumUpdatePosChunkY = -"".length();
        this.frustumUpdatePosChunkZ = -"".length();
        this.lastViewEntityX = Double.MIN_VALUE;
        this.lastViewEntityY = Double.MIN_VALUE;
        this.lastViewEntityZ = Double.MIN_VALUE;
        this.lastViewEntityPitch = Double.MIN_VALUE;
        this.lastViewEntityYaw = Double.MIN_VALUE;
        this.renderDispatcher = new ChunkRenderDispatcher();
        this.renderDistanceChunks = -" ".length();
        this.renderEntitiesStartupCounter = "  ".length();
        this.debugFixTerrainFrustum = ("".length() != 0);
        this.debugTerrainMatrix = new Vector4f[0x8D ^ 0x85];
        this.debugTerrainFrustumPosition = new Vector3d();
        this.vboEnabled = ("".length() != 0);
        this.displayListEntitiesDirty = (" ".length() != 0);
        this.chunksToResortTransparency = new LinkedHashSet();
        this.chunksToUpdateForced = new LinkedHashSet();
        this.visibilityDeque = new ArrayDeque();
        this.renderInfosEntities = new ArrayList(611 + 497 - 547 + 463);
        this.renderInfosTileEntities = new ArrayList(44 + 754 - 667 + 893);
        this.renderDistance = "".length();
        this.renderDistanceSq = "".length();
        this.cloudRenderer = new CloudRenderer(mc);
        this.mc = mc;
        this.renderManager = mc.getRenderManager();
        (this.renderEngine = mc.getTextureManager()).bindTexture(RenderGlobal.locationForcefieldPng);
        GL11.glTexParameteri(2197 + 1623 - 440 + 173, 2410 + 1447 - 1540 + 7925, 7254 + 9047 - 7142 + 1338);
        GL11.glTexParameteri(2512 + 574 - 313 + 780, 1994 + 7828 - 1759 + 2180, 6198 + 9681 - 14555 + 9173);
        GlStateManager.bindTexture("".length());
        this.updateDestroyBlockIcons();
        this.vboEnabled = OpenGlHelper.useVbo();
        if (this.vboEnabled) {
            this.renderContainer = new VboRenderList();
            this.renderChunkFactory = new VboChunkFactory();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.renderContainer = new RenderList();
            this.renderChunkFactory = new ListChunkFactory();
        }
        (this.vertexBufferFormat = new VertexFormat()).func_181721_a(new VertexFormatElement("".length(), VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, "   ".length()));
        this.generateStars();
        this.generateSky();
        this.generateSky2();
    }
    
    public boolean hasCloudFog(final double n, final double n2, final double n3, final float n4) {
        return "".length() != 0;
    }
    
    private void renderCloudsFancy(float n, final int n2) {
        this.cloudRenderer.prepareToRender(" ".length() != 0, this.cloudTickCounter, n);
        n = 0.0f;
        GlStateManager.disableCull();
        final float n3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * n);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final double n4 = (this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * n + (this.cloudTickCounter + n) * 0.029999999329447746) / 12.0;
        final double n5 = (this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * n) / 12.0 + 0.33000001311302185;
        final float n6 = this.theWorld.provider.getCloudHeight() - n3 + 0.33f + this.mc.gameSettings.ofCloudsHeight * 128.0f;
        final int floor_double = MathHelper.floor_double(n4 / 2048.0);
        final int floor_double2 = MathHelper.floor_double(n5 / 2048.0);
        final double n7 = n4 - floor_double * (1849 + 934 - 1431 + 696);
        final double n8 = n5 - floor_double2 * (1024 + 1238 - 1622 + 1408);
        this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(72 + 750 - 367 + 315, 559 + 470 - 918 + 660, " ".length(), "".length());
        final Vec3 cloudColour = this.theWorld.getCloudColour(n);
        float n9 = (float)cloudColour.xCoord;
        float n10 = (float)cloudColour.yCoord;
        float n11 = (float)cloudColour.zCoord;
        if (n2 != "  ".length()) {
            final float n12 = (n9 * 30.0f + n10 * 59.0f + n11 * 11.0f) / 100.0f;
            final float n13 = (n9 * 30.0f + n10 * 70.0f) / 100.0f;
            final float n14 = (n9 * 30.0f + n11 * 70.0f) / 100.0f;
            n9 = n12;
            n10 = n13;
            n11 = n14;
        }
        final float n15 = n9 * 0.9f;
        final float n16 = n10 * 0.9f;
        final float n17 = n11 * 0.9f;
        final float n18 = n9 * 0.7f;
        final float n19 = n10 * 0.7f;
        final float n20 = n11 * 0.7f;
        final float n21 = n9 * 0.8f;
        final float n22 = n10 * 0.8f;
        final float n23 = n11 * 0.8f;
        final float n24 = MathHelper.floor_double(n7) * 0.00390625f;
        final float n25 = MathHelper.floor_double(n8) * 0.00390625f;
        final float n26 = (float)(n7 - MathHelper.floor_double(n7));
        final float n27 = (float)(n8 - MathHelper.floor_double(n8));
        " ".length();
        " ".length();
        GlStateManager.scale(12.0f, 1.0f, 12.0f);
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < "  ".length()) {
            if (i == 0) {
                GlStateManager.colorMask("".length() != 0, "".length() != 0, "".length() != 0, "".length() != 0);
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                switch (n2) {
                    case 0: {
                        GlStateManager.colorMask("".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
                        "".length();
                        if (4 < -1) {
                            throw null;
                        }
                        break;
                    }
                    case 1: {
                        GlStateManager.colorMask(" ".length() != 0, "".length() != 0, "".length() != 0, " ".length() != 0);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        break;
                    }
                    case 2: {
                        GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
                        break;
                    }
                }
            }
            this.cloudRenderer.renderGlList();
            ++i;
        }
        if (this.cloudRenderer.shouldUpdateGlList()) {
            this.cloudRenderer.startUpdateGlList();
            int j = -"   ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (j <= (0x9F ^ 0x9B)) {
                int k = -"   ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                while (k <= (0x16 ^ 0x12)) {
                    worldRenderer.begin(0x2 ^ 0x5, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
                    final float n28 = j * (0x26 ^ 0x2E);
                    final float n29 = k * (0xCA ^ 0xC2);
                    final float n30 = n28 - n26;
                    final float n31 = n29 - n27;
                    if (n6 > -5.0f) {
                        worldRenderer.pos(n30 + 0.0f, n6 + 0.0f, n31 + 8.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n18, n19, n20, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 8.0f, n6 + 0.0f, n31 + 8.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n18, n19, n20, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 8.0f, n6 + 0.0f, n31 + 0.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n18, n19, n20, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 0.0f, n6 + 0.0f, n31 + 0.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n18, n19, n20, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    }
                    if (n6 <= 5.0f) {
                        worldRenderer.pos(n30 + 0.0f, n6 + 4.0f - 9.765625E-4f, n31 + 8.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n9, n10, n11, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 8.0f, n6 + 4.0f - 9.765625E-4f, n31 + 8.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n9, n10, n11, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 8.0f, n6 + 4.0f - 9.765625E-4f, n31 + 0.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n9, n10, n11, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldRenderer.pos(n30 + 0.0f, n6 + 4.0f - 9.765625E-4f, n31 + 0.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n9, n10, n11, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                    }
                    if (j > -" ".length()) {
                        int l = "".length();
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                        while (l < (0x4 ^ 0xC)) {
                            worldRenderer.pos(n30 + l + 0.0f, n6 + 0.0f, n31 + 8.0f).tex((n28 + l + 0.5f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + l + 0.0f, n6 + 4.0f, n31 + 8.0f).tex((n28 + l + 0.5f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + l + 0.0f, n6 + 4.0f, n31 + 0.0f).tex((n28 + l + 0.5f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + l + 0.0f, n6 + 0.0f, n31 + 0.0f).tex((n28 + l + 0.5f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            ++l;
                        }
                    }
                    if (j <= " ".length()) {
                        int length = "".length();
                        "".length();
                        if (3 == 0) {
                            throw null;
                        }
                        while (length < (0x2E ^ 0x26)) {
                            worldRenderer.pos(n30 + length + 1.0f - 9.765625E-4f, n6 + 0.0f, n31 + 8.0f).tex((n28 + length + 0.5f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + length + 1.0f - 9.765625E-4f, n6 + 4.0f, n31 + 8.0f).tex((n28 + length + 0.5f) * 0.00390625f + n24, (n29 + 8.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + length + 1.0f - 9.765625E-4f, n6 + 4.0f, n31 + 0.0f).tex((n28 + length + 0.5f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldRenderer.pos(n30 + length + 1.0f - 9.765625E-4f, n6 + 0.0f, n31 + 0.0f).tex((n28 + length + 0.5f) * 0.00390625f + n24, (n29 + 0.0f) * 0.00390625f + n25).color(n15, n16, n17, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            ++length;
                        }
                    }
                    if (k > -" ".length()) {
                        int length2 = "".length();
                        "".length();
                        if (0 < -1) {
                            throw null;
                        }
                        while (length2 < (0xBF ^ 0xB7)) {
                            worldRenderer.pos(n30 + 0.0f, n6 + 4.0f, n31 + length2 + 0.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + length2 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldRenderer.pos(n30 + 8.0f, n6 + 4.0f, n31 + length2 + 0.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + length2 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldRenderer.pos(n30 + 8.0f, n6 + 0.0f, n31 + length2 + 0.0f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + length2 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldRenderer.pos(n30 + 0.0f, n6 + 0.0f, n31 + length2 + 0.0f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + length2 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            ++length2;
                        }
                    }
                    if (k <= " ".length()) {
                        int length3 = "".length();
                        "".length();
                        if (2 < 0) {
                            throw null;
                        }
                        while (length3 < (0x1F ^ 0x17)) {
                            worldRenderer.pos(n30 + 0.0f, n6 + 4.0f, n31 + length3 + 1.0f - 9.765625E-4f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + length3 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldRenderer.pos(n30 + 8.0f, n6 + 4.0f, n31 + length3 + 1.0f - 9.765625E-4f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + length3 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldRenderer.pos(n30 + 8.0f, n6 + 0.0f, n31 + length3 + 1.0f - 9.765625E-4f).tex((n28 + 8.0f) * 0.00390625f + n24, (n29 + length3 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldRenderer.pos(n30 + 0.0f, n6 + 0.0f, n31 + length3 + 1.0f - 9.765625E-4f).tex((n28 + 0.0f) * 0.00390625f + n24, (n29 + length3 + 0.5f) * 0.00390625f + n25).color(n21, n22, n23, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            ++length3;
                        }
                    }
                    instance.draw();
                    ++k;
                }
                ++j;
            }
            this.cloudRenderer.endUpdateGlList();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    private void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), n, n2, n3, n4, n5, n6, array);
    }
    
    public int getCountActiveRenderers() {
        return this.renderInfos.size();
    }
    
    private void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.viewFrustum.markBlocksForUpdate(n, n2, n3, n4, n5, n6);
    }
    
    private void renderSkyEnd() {
        if (Config.isSkyEnabled()) {
            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(72 + 483 - 388 + 603, 320 + 679 - 318 + 90, " ".length(), "".length());
            RenderHelper.disableStandardItemLighting();
            GlStateManager.depthMask("".length() != 0);
            this.renderEngine.bindTexture(RenderGlobal.locationEndSkyPng);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            int i = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (i < (0xAC ^ 0xAA)) {
                GlStateManager.pushMatrix();
                if (i == " ".length()) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == "  ".length()) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == "   ".length()) {
                    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == (0x1B ^ 0x1F)) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (i == (0xA4 ^ 0xA1)) {
                    GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                worldRenderer.begin(0x94 ^ 0x93, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).color(0xA4 ^ 0x8C, 0xA ^ 0x22, 0x6F ^ 0x47, 250 + 120 - 352 + 237).endVertex();
                worldRenderer.pos(-100.0, -100.0, 100.0).tex(0.0, 16.0).color(0xBD ^ 0x95, 0x1 ^ 0x29, 0x4F ^ 0x67, 111 + 105 - 23 + 62).endVertex();
                worldRenderer.pos(100.0, -100.0, 100.0).tex(16.0, 16.0).color(0x2 ^ 0x2A, 0x6D ^ 0x45, 0x24 ^ 0xC, 79 + 221 - 180 + 135).endVertex();
                worldRenderer.pos(100.0, -100.0, -100.0).tex(16.0, 0.0).color(0x40 ^ 0x68, 0x3A ^ 0x12, 0x28 ^ 0x0, 53 + 216 - 229 + 215).endVertex();
                instance.draw();
                GlStateManager.popMatrix();
                ++i;
            }
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
        }
    }
    
    private void renderBlockLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.mc.entityRenderer.enableLightmap();
        if (OpenGlHelper.useVbo()) {
            GL11.glEnableClientState(9743 + 27615 - 8397 + 3923);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(28645 + 10742 - 15707 + 9208);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnableClientState(3588 + 13809 + 11529 + 3962);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(18287 + 28591 - 18967 + 4975);
        }
        this.renderContainer.renderChunkLayer(enumWorldBlockLayer);
        if (OpenGlHelper.useVbo()) {
            final Iterator<VertexFormatElement> iterator = DefaultVertexFormats.BLOCK.getElements().iterator();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final VertexFormatElement vertexFormatElement = iterator.next();
                final VertexFormatElement.EnumUsage usage = vertexFormatElement.getUsage();
                final int index = vertexFormatElement.getIndex();
                switch (RenderGlobal$2.field_178037_a[usage.ordinal()]) {
                    case 1: {
                        GL11.glDisableClientState(23865 + 1527 - 10564 + 18056);
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                        continue;
                    }
                    case 2: {
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index);
                        GL11.glDisableClientState(23744 + 27176 - 34952 + 16920);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                        continue;
                    }
                    case 3: {
                        GL11.glDisableClientState(4884 + 1906 + 555 + 25541);
                        GlStateManager.resetColor();
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        this.mc.entityRenderer.disableLightmap();
    }
    
    public void deleteAllDisplayLists() {
    }
    
    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }
    
    private static void I() {
        (I = new String[0x8C ^ 0xB0])["".length()] = I(" \u000f+aDSsDhAW", "cCtQt");
        RenderGlobal.I[" ".length()] = I("\u0011$:0\u000f\u0017$1k\u001f\u000b7+6\u0015\u000b,'*\u000eJ,-+\u0014:1*%\t\u00002l4\u0014\u0002", "eABDz");
        RenderGlobal.I["  ".length()] = I("\"\u000f\u0019- $\u000f\u0012v08\u001c\b+:8\u0007\u00047!y\u0019\u00147{&\u0004\u0006", "VjaYU");
        RenderGlobal.I["   ".length()] = I("$?\u0011\u0019'\"?\u001aB7>,\u0000\u001f=>7\f\u0003&\u007f9\u0005\u0002'4)G\u001d<7", "PZimR");
        RenderGlobal.I[0x3 ^ 0x7] = I("\u0012\u0010\u0014\u001d8\u0014\u0010\u001fF(\b\u0003\u0005\u001b\"\b\u0018\t\u00079I\u0010\u0002\r\u0012\u0015\u001e\u0015G=\b\u0012", "fuliM");
        RenderGlobal.I[0x80 ^ 0x85] = I("86\u0017\u001d\u0013>6\u001cF\u000b% \fF\u0000#!\f\f\u0000%6\u0003\rH<=\b", "LSoif");
        RenderGlobal.I[0x19 ^ 0x1F] = I("\u001c\u0002\u0006&!\u0003\n\u000e7x\u0013\u0007\u0007 )\u0002D\f&1\u0005\u0019\u0007:\u001d\u0002\u001f\t$'.", "qkhCB");
        RenderGlobal.I[0x99 ^ 0x9E] = I("\n%'\n\u000f\u000b>i\u001e\u0005\n9i\u000b\u0004\r$2\u00175\u001682\u0002\u0003\u0017(h\u0004\u0019\u0016#", "yMFnj");
        RenderGlobal.I[0x3C ^ 0x34] = I("7\u0004'6\u000b", "QmIWg");
        RenderGlobal.I[0xBB ^ 0xB2] = I("\b \r+<*a\u0010(y\".\u0005#y=)\u0005#<<{D", "NAdGY");
        RenderGlobal.I[0x44 ^ 0x4E] = I("\u0017\u0004;\b\u00165E&\u000bS=\n3\u0000S\"\r3\u0000\u0016#_r", "QeRds");
        RenderGlobal.I[0x79 ^ 0x72] = I("4\u000b\u0016#\u00156\u001c", "DysSt");
        RenderGlobal.I[0x33 ^ 0x3F] = I(")!6\u000f\u0018\"", "NMYmy");
        RenderGlobal.I[0xB9 ^ 0xB4] = I("=\u0007\r\u0019\u001c!&\f\u0004\u00041\u0007\u001c\u0003", "Xiyph");
        RenderGlobal.I[0x6D ^ 0x63] = I("\u0013\u0016-%?\u001f\u001d*", "vxYLK");
        RenderGlobal.I[0xBF ^ 0xB0] = I(".\u001f&\u0010.)\u001d=\u001a1%\u0016:", "LsIsE");
        RenderGlobal.I[0x18 ^ 0x8] = I("\u0012JfG\u0015~U\"BT\"4|BT5\\fG\u0002", "QpFbq");
        RenderGlobal.I[0xB5 ^ 0xA4] = I("x\u0003\u007fY", "PpVyJ");
        RenderGlobal.I[0x39 ^ 0x2B] = I("", "SwcKY");
        RenderGlobal.I[0x8 ^ 0x1B] = I(" uW", "eOwJF");
        RenderGlobal.I[0xA ^ 0x1E] = I("z", "ULSoB");
        RenderGlobal.I[0x29 ^ 0x3C] = I("nM ~k", "BmbDK");
        RenderGlobal.I[0xB9 ^ 0xAF] = I("YI\u001eQk", "uiWkK");
        RenderGlobal.I[0x4 ^ 0x13] = I("Ck", "oKZYb");
        RenderGlobal.I[0xB6 ^ 0xAE] = I("\f\"\u0007\u000b\u0011\u000e", "oCjnc");
        RenderGlobal.I[0x6B ^ 0x72] = I("\u0019\u0013\"\u000b\u0000\u0019\u001a%\u001c\u0011\b\u0017!\n\u0017\n", "kvLoe");
        RenderGlobal.I[0xDF ^ 0xC5] = I("\u0010\u00199\u0004", "slUhd");
        RenderGlobal.I[0x65 ^ 0x7E] = I("(\u0001>\u001b=%\u0013", "KtRwT");
        RenderGlobal.I[0x4D ^ 0x51] = I("77=\u0016\u0001u,1\u001b\u0017", "UBTze");
        RenderGlobal.I[0x9D ^ 0x80] = I("<0#%\u001f$7!.\u0002<\u001d1$\u001e<", "HBBKl");
        RenderGlobal.I[0x3F ^ 0x21] = I("/>)8\u000b;2(<\u001a0", "IWELn");
        RenderGlobal.I[0x10 ^ 0xF] = I(";\u000e%\u001c\u0014;4", "IkKxq");
        RenderGlobal.I[0x58 ^ 0x78] = I("(\u0011!-6\u0019\u0000-&f\u001a\u0001+$#M\b&,/\u0003\u000eb8'\u001f\u001d++*\b", "miBHF");
        RenderGlobal.I[0x3E ^ 0x1F] = I("(\u0017*;\u001f\u001b\u001a=o\u0014\u001d\u001f6(V\u0019\u0012<*\u0012", "xvXOv");
        RenderGlobal.I[0xB5 ^ 0x97] = I("&\u000e", "oJsLK");
        RenderGlobal.I[0x70 ^ 0x53] = I(";*',+\u000e?0?5", "kKUMF");
        RenderGlobal.I[0x72 ^ 0x56] = I("%\u0016\u0007\u001d.\u001c\u0016\u001a", "uyttZ");
        RenderGlobal.I[0x39 ^ 0x1C] = I("\u0007*,I\u0007\u00031&\u0002\u0002D6>\u0006\u0007\u0004", "jENgp");
        RenderGlobal.I[0xE6 ^ 0xC0] = I(";\u0016\u001be\u00048\u001d\u001c9\u0005$\u0018\u001e$\u000fx\u001c\u0017/", "VyyKa");
        RenderGlobal.I[0x7 ^ 0x20] = I("\u0011\u0013\u00006\u0016\u000e\\\r>\u0010\u0000\u0019", "crnRy");
        RenderGlobal.I[0x33 ^ 0x1B] = I("9\u0007%(\u001a&H( \u001c(\r", "KfKLu");
        RenderGlobal.I[0x6A ^ 0x43] = I("\b3\u0005%*\u0017|\t.2", "zRkAE");
        RenderGlobal.I[0x65 ^ 0x4F] = I("=\u00004.\u001b\"O>%\u001b=>5:\u0011!", "OaZJt");
        RenderGlobal.I[0x83 ^ 0xA8] = I("\u0016'\u0000!\b\th\b,\u001d\u001e", "dFnEg");
        RenderGlobal.I[0xA5 ^ 0x89] = I("(\u0006\f\u001c\u0002>\u0010A", "Zcosp");
        RenderGlobal.I[0x65 ^ 0x48] = I("5+!\u0006\u001b*d+\r\u001b5\u0015,\u000e\u001b4/", "GJObt");
        RenderGlobal.I[0x71 ^ 0x5F] = I("\u000f>\u0007Y$\n0\u0016\u0003m\u00019\u0004\u0005$\u0007", "bQewC");
        RenderGlobal.I[0x73 ^ 0x5C] = I("\u0007\r\u0007d*\u0002\u0003\u0016>c\f\u000b\u0017//\u000b\u000e\t", "jbeJM");
        RenderGlobal.I[0x25 ^ 0x15] = I("\u0000\u0006\u0014~3\u0005\b\u0005$z\u000b\u0000\u000456\f\u0005\u001a", "mivPT");
        RenderGlobal.I[0x5C ^ 0x6D] = I("$ %X\u0018&\"%\u001f\u0007g8(\u0019\u0006", "IOGvb");
        RenderGlobal.I[0x1 ^ 0x33] = I("\u0019+-f \u001b)-!?Z)*<;\u0018", "tDOHZ");
        RenderGlobal.I[0x87 ^ 0xB4] = I("\u000b>'F\u0014\t<'\u0001\u000bH&*\u0007\n\u0004# \t\u0005", "fQEhn");
        RenderGlobal.I[0x57 ^ 0x63] = I("\u00015/o0\u0005.%$5B)%.(\u0018", "lZMAG");
        RenderGlobal.I[0x71 ^ 0x44] = I("=\u000b*}&1\u0010f'%;\u0001'5\"", "PdHSD");
        RenderGlobal.I[0xB7 ^ 0x81] = I("\u001d\b,~\b\u001f\n,9\u0017^\u000e 6\u0017\u0013\u0013", "pgNPr");
        RenderGlobal.I[0x7D ^ 0x4A] = I(" &\u0017Z\b\"$\u0017\u001d\u0017c<\u001b\u0012\u0017.=", "MIutr");
        RenderGlobal.I[0x4E ^ 0x76] = I("\u0017\u0017)\u000f.\bX&\u00057\f\u001a\u0018\t3\u0000\u0017,", "evGkA");
        RenderGlobal.I[0x6F ^ 0x56] = I("\u001b.\u001d6 \u0004a\u0012<9\u0000#,'<\f", "iOsRO");
        RenderGlobal.I[0x61 ^ 0x5B] = I("\"#+\u0001\u000b=l$\u000b\u00129.\u001a\t\u0005>&", "PBEed");
        RenderGlobal.I[0xFD ^ 0xC6] = I("\u0011\u0000\t\u0015y\u0006\u000e\u0010\u00198\u0018O\u0017\u001d6\u0005\t", "vadpW");
    }
    
    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }
    
    public void renderSky(final float n, final int n2) {
        if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
            final Object call = Reflector.call(this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object["".length()]);
            if (call != null) {
                final Object o = call;
                final ReflectorMethod iRenderHandler_render = Reflector.IRenderHandler_render;
                final Object[] array = new Object["   ".length()];
                array["".length()] = n;
                array[" ".length()] = this.theWorld;
                array["  ".length()] = this.mc;
                Reflector.callVoid(o, iRenderHandler_render, array);
                return;
            }
        }
        if (this.mc.theWorld.provider.getDimensionId() == " ".length()) {
            this.renderSkyEnd();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (this.mc.theWorld.provider.isSurfaceWorld()) {
            GlStateManager.disableTexture2D();
            final Vec3 skyColor = CustomColorizer.getSkyColor(this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), n), this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ);
            float n3 = (float)skyColor.xCoord;
            float n4 = (float)skyColor.yCoord;
            float n5 = (float)skyColor.zCoord;
            if (n2 != "  ".length()) {
                final float n6 = (n3 * 30.0f + n4 * 59.0f + n5 * 11.0f) / 100.0f;
                final float n7 = (n3 * 30.0f + n4 * 70.0f) / 100.0f;
                final float n8 = (n3 * 30.0f + n5 * 70.0f) / 100.0f;
                n3 = n6;
                n4 = n7;
                n5 = n8;
            }
            GlStateManager.color(n3, n4, n5);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.enableFog();
            GlStateManager.color(n3, n4, n5);
            if (Config.isSkyEnabled()) {
                if (this.vboEnabled) {
                    this.skyVBO.bindBuffer();
                    GL11.glEnableClientState(29920 + 19439 - 42368 + 25893);
                    GL11.glVertexPointer("   ".length(), 2787 + 2473 - 2582 + 2448, 0x19 ^ 0x15, 0L);
                    this.skyVBO.drawArrays(0x86 ^ 0x81);
                    this.skyVBO.unbindBuffer();
                    GL11.glDisableClientState(15851 + 22975 - 37882 + 31940);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    GlStateManager.callList(this.glSkyList);
                }
            }
            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(4 + 542 - 242 + 466, 645 + 735 - 1233 + 624, " ".length(), "".length());
            RenderHelper.disableStandardItemLighting();
            final float[] calcSunriseSunsetColors = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(n), n);
            if (calcSunriseSunsetColors != null && Config.isSunMoonEnabled()) {
                GlStateManager.disableTexture2D();
                GlStateManager.shadeModel(7360 + 2758 - 8494 + 5801);
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                float n9;
                if (MathHelper.sin(this.theWorld.getCelestialAngleRadians(n)) < 0.0f) {
                    n9 = 180.0f;
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    n9 = 0.0f;
                }
                GlStateManager.rotate(n9, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                float n10 = calcSunriseSunsetColors["".length()];
                float n11 = calcSunriseSunsetColors[" ".length()];
                float n12 = calcSunriseSunsetColors["  ".length()];
                if (n2 != "  ".length()) {
                    final float n13 = (n10 * 30.0f + n11 * 59.0f + n12 * 11.0f) / 100.0f;
                    final float n14 = (n10 * 30.0f + n11 * 70.0f) / 100.0f;
                    final float n15 = (n10 * 30.0f + n12 * 70.0f) / 100.0f;
                    n10 = n13;
                    n11 = n14;
                    n12 = n15;
                }
                worldRenderer.begin(0x46 ^ 0x40, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos(0.0, 100.0, 0.0).color(n10, n11, n12, calcSunriseSunsetColors["   ".length()]).endVertex();
                " ".length();
                int i = "".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (i <= (0x73 ^ 0x63)) {
                    final float n16 = i * 3.1415927f * 2.0f / 16.0f;
                    final float sin = MathHelper.sin(n16);
                    final float cos = MathHelper.cos(n16);
                    worldRenderer.pos(sin * 120.0f, cos * 120.0f, -cos * 40.0f * calcSunriseSunsetColors["   ".length()]).color(calcSunriseSunsetColors["".length()], calcSunriseSunsetColors[" ".length()], calcSunriseSunsetColors["  ".length()], 0.0f).endVertex();
                    ++i;
                }
                instance.draw();
                GlStateManager.popMatrix();
                GlStateManager.shadeModel(1142 + 5584 - 3417 + 4115);
            }
            GlStateManager.enableTexture2D();
            GlStateManager.tryBlendFuncSeparate(339 + 210 - 405 + 626, " ".length(), " ".length(), "".length());
            GlStateManager.pushMatrix();
            final float n17 = 1.0f - this.theWorld.getRainStrength(n);
            GlStateManager.color(1.0f, 1.0f, 1.0f, n17);
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(n), n17);
            GlStateManager.rotate(this.theWorld.getCelestialAngle(n) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (Config.isSunMoonEnabled()) {
                final float n18 = 30.0f;
                this.renderEngine.bindTexture(RenderGlobal.locationSunPng);
                worldRenderer.begin(0x71 ^ 0x76, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(-n18, 100.0, -n18).tex(0.0, 0.0).endVertex();
                worldRenderer.pos(n18, 100.0, -n18).tex(1.0, 0.0).endVertex();
                worldRenderer.pos(n18, 100.0, n18).tex(1.0, 1.0).endVertex();
                worldRenderer.pos(-n18, 100.0, n18).tex(0.0, 1.0).endVertex();
                instance.draw();
                final float n19 = 20.0f;
                this.renderEngine.bindTexture(RenderGlobal.locationMoonPhasesPng);
                final int moonPhase = this.theWorld.getMoonPhase();
                final int n20 = moonPhase % (0x17 ^ 0x13);
                final int n21 = moonPhase / (0x43 ^ 0x47) % "  ".length();
                final float n22 = (n20 + "".length()) / 4.0f;
                final float n23 = (n21 + "".length()) / 2.0f;
                final float n24 = (n20 + " ".length()) / 4.0f;
                final float n25 = (n21 + " ".length()) / 2.0f;
                worldRenderer.begin(0x2C ^ 0x2B, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(-n19, -100.0, n19).tex(n24, n25).endVertex();
                worldRenderer.pos(n19, -100.0, n19).tex(n22, n25).endVertex();
                worldRenderer.pos(n19, -100.0, -n19).tex(n22, n23).endVertex();
                worldRenderer.pos(-n19, -100.0, -n19).tex(n24, n23).endVertex();
                instance.draw();
            }
            GlStateManager.disableTexture2D();
            final float n26 = this.theWorld.getStarBrightness(n) * n17;
            if (n26 > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
                GlStateManager.color(n26, n26, n26, n26);
                if (this.vboEnabled) {
                    this.starVBO.bindBuffer();
                    GL11.glEnableClientState(5862 + 6710 + 5735 + 14577);
                    GL11.glVertexPointer("   ".length(), 1260 + 254 - 398 + 4010, 0xCA ^ 0xC6, 0L);
                    this.starVBO.drawArrays(0xC6 ^ 0xC1);
                    this.starVBO.unbindBuffer();
                    GL11.glDisableClientState(3676 + 14851 - 9478 + 23835);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    GlStateManager.callList(this.starGLCallList);
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableFog();
            GlStateManager.popMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.color(0.0f, 0.0f, 0.0f);
            final double n27 = this.mc.thePlayer.getPositionEyes(n).yCoord - this.theWorld.getHorizon();
            if (n27 < 0.0) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0f, 12.0f, 0.0f);
                if (this.vboEnabled) {
                    this.sky2VBO.bindBuffer();
                    GL11.glEnableClientState(25789 + 9439 - 15552 + 13208);
                    GL11.glVertexPointer("   ".length(), 3048 + 2642 - 3171 + 2607, 0x75 ^ 0x79, 0L);
                    this.sky2VBO.drawArrays(0x3F ^ 0x38);
                    this.sky2VBO.unbindBuffer();
                    GL11.glDisableClientState(31489 + 7224 - 6523 + 694);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    GlStateManager.callList(this.glSkyList2);
                }
                GlStateManager.popMatrix();
                final float n28 = -(float)(n27 + 65.0);
                worldRenderer.begin(0x1F ^ 0x18, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos(-1.0, n28, 1.0).color("".length(), "".length(), "".length(), 186 + 1 - 85 + 153).endVertex();
                worldRenderer.pos(1.0, n28, 1.0).color("".length(), "".length(), "".length(), 94 + 101 + 52 + 8).endVertex();
                worldRenderer.pos(1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 183 + 174 - 160 + 58).endVertex();
                worldRenderer.pos(-1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 149 + 222 - 302 + 186).endVertex();
                worldRenderer.pos(-1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 122 + 162 - 135 + 106).endVertex();
                worldRenderer.pos(1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 48 + 47 - 17 + 177).endVertex();
                worldRenderer.pos(1.0, n28, -1.0).color("".length(), "".length(), "".length(), 165 + 29 + 3 + 58).endVertex();
                worldRenderer.pos(-1.0, n28, -1.0).color("".length(), "".length(), "".length(), 36 + 231 - 144 + 132).endVertex();
                worldRenderer.pos(1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 180 + 9 - 115 + 181).endVertex();
                worldRenderer.pos(1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 61 + 145 + 21 + 28).endVertex();
                worldRenderer.pos(1.0, n28, 1.0).color("".length(), "".length(), "".length(), 74 + 84 - 143 + 240).endVertex();
                worldRenderer.pos(1.0, n28, -1.0).color("".length(), "".length(), "".length(), 53 + 27 + 170 + 5).endVertex();
                worldRenderer.pos(-1.0, n28, -1.0).color("".length(), "".length(), "".length(), 209 + 149 - 279 + 176).endVertex();
                worldRenderer.pos(-1.0, n28, 1.0).color("".length(), "".length(), "".length(), 8 + 248 - 10 + 9).endVertex();
                worldRenderer.pos(-1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 146 + 123 - 174 + 160).endVertex();
                worldRenderer.pos(-1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 41 + 136 - 84 + 162).endVertex();
                worldRenderer.pos(-1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 137 + 233 - 182 + 67).endVertex();
                worldRenderer.pos(-1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 16 + 170 - 144 + 213).endVertex();
                worldRenderer.pos(1.0, -1.0, 1.0).color("".length(), "".length(), "".length(), 177 + 104 - 101 + 75).endVertex();
                worldRenderer.pos(1.0, -1.0, -1.0).color("".length(), "".length(), "".length(), 53 + 112 + 71 + 19).endVertex();
                instance.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GlStateManager.color(n3 * 0.2f + 0.04f, n4 * 0.2f + 0.04f, n5 * 0.6f + 0.1f);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                GlStateManager.color(n3, n4, n5);
            }
            if (this.mc.gameSettings.renderDistanceChunks <= (0x96 ^ 0x92)) {
                GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, -(float)(n27 - 16.0), 0.0f);
            if (Config.isSkyEnabled()) {
                GlStateManager.callList(this.glSkyList2);
            }
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(" ".length() != 0);
        }
    }
    
    @Override
    public void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        try {
            this.spawnEntityFX(n, b, n2, n3, n4, n5, n6, n7, array);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, RenderGlobal.I[0xAD ^ 0x8D]);
            final CrashReportCategory category = crashReport.makeCategory(RenderGlobal.I[0x1 ^ 0x20]);
            category.addCrashSection(RenderGlobal.I[0x9A ^ 0xB8], n);
            if (array != null) {
                category.addCrashSection(RenderGlobal.I[0x7A ^ 0x59], array);
            }
            category.addCrashSectionCallable(RenderGlobal.I[0xAF ^ 0x8B], new Callable(this, n2, n3, n4) {
                final RenderGlobal this$0;
                private final double val$yCoord;
                private static final String __OBFID;
                private final double val$zCoord;
                private static final String[] I;
                private final double val$xCoord;
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("!6\u0011SURJ~ZPW", "bzNce");
                }
                
                @Override
                public String call() throws Exception {
                    return CrashReportCategory.getCoordinateInfo(this.val$xCoord, this.val$yCoord, this.val$zCoord);
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
                        if (false) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                static {
                    I();
                    __OBFID = RenderGlobal$1.I["".length()];
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw new ReportedException(crashReport);
        }
    }
    
    public void renderEntityOutlineFramebuffer() {
        if (this.isRenderEntityOutlines()) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(60 + 343 + 33 + 334, 39 + 397 - 86 + 421, "".length(), " ".length());
            this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, "".length() != 0);
            GlStateManager.disableBlend();
        }
    }
    
    public void renderWorldBorder(final Entity entity, final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final WorldBorder worldBorder = this.theWorld.getWorldBorder();
        final double n2 = this.mc.gameSettings.renderDistanceChunks * (0x13 ^ 0x3);
        if (entity.posX >= worldBorder.maxX() - n2 || entity.posX <= worldBorder.minX() + n2 || entity.posZ >= worldBorder.maxZ() - n2 || entity.posZ <= worldBorder.minZ() + n2) {
            final double pow = Math.pow(1.0 - worldBorder.getClosestDistance(entity) / n2, 4.0);
            final double n3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
            final double n4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
            final double n5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(315 + 651 - 225 + 29, " ".length(), " ".length(), "".length());
            this.renderEngine.bindTexture(RenderGlobal.locationForcefieldPng);
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.pushMatrix();
            final int id = worldBorder.getStatus().getID();
            GlStateManager.color((id >> (0x78 ^ 0x68) & 239 + 225 - 422 + 213) / 255.0f, (id >> (0x1C ^ 0x14) & 84 + 66 - 139 + 244) / 255.0f, (id & 236 + 175 - 321 + 165) / 255.0f, (float)pow);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            GlStateManager.enablePolygonOffset();
            GlStateManager.alphaFunc(474 + 321 - 693 + 414, 0.1f);
            GlStateManager.enableAlpha();
            GlStateManager.disableCull();
            final float n6 = Minecraft.getSystemTime() % 3000L / 3000.0f;
            worldRenderer.begin(0x8B ^ 0x8C, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.setTranslation(-n3, -n4, -n5);
            final double max = Math.max(MathHelper.floor_double(n5 - n2), worldBorder.minZ());
            final double min = Math.min(MathHelper.ceiling_double_int(n5 + n2), worldBorder.maxZ());
            if (n3 > worldBorder.maxX() - n2) {
                float n7 = 0.0f;
                double n8 = max;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (n8 < min) {
                    final double min2 = Math.min(1.0, min - n8);
                    final float n9 = (float)min2 * 0.5f;
                    worldRenderer.pos(worldBorder.maxX(), 256.0, n8).tex(n6 + n7, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 256.0, n8 + min2).tex(n6 + n9 + n7, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 0.0, n8 + min2).tex(n6 + n9 + n7, n6 + 128.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 0.0, n8).tex(n6 + n7, n6 + 128.0f).endVertex();
                    ++n8;
                    n7 += 0.5f;
                }
            }
            if (n3 < worldBorder.minX() + n2) {
                float n10 = 0.0f;
                double n11 = max;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (n11 < min) {
                    final double min3 = Math.min(1.0, min - n11);
                    final float n12 = (float)min3 * 0.5f;
                    worldRenderer.pos(worldBorder.minX(), 256.0, n11).tex(n6 + n10, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 256.0, n11 + min3).tex(n6 + n12 + n10, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 0.0, n11 + min3).tex(n6 + n12 + n10, n6 + 128.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 0.0, n11).tex(n6 + n10, n6 + 128.0f).endVertex();
                    ++n11;
                    n10 += 0.5f;
                }
            }
            final double max2 = Math.max(MathHelper.floor_double(n3 - n2), worldBorder.minX());
            final double min4 = Math.min(MathHelper.ceiling_double_int(n3 + n2), worldBorder.maxX());
            if (n5 > worldBorder.maxZ() - n2) {
                float n13 = 0.0f;
                double n14 = max2;
                "".length();
                if (2 == 0) {
                    throw null;
                }
                while (n14 < min4) {
                    final double min5 = Math.min(1.0, min4 - n14);
                    final float n15 = (float)min5 * 0.5f;
                    worldRenderer.pos(n14, 256.0, worldBorder.maxZ()).tex(n6 + n13, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n14 + min5, 256.0, worldBorder.maxZ()).tex(n6 + n15 + n13, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n14 + min5, 0.0, worldBorder.maxZ()).tex(n6 + n15 + n13, n6 + 128.0f).endVertex();
                    worldRenderer.pos(n14, 0.0, worldBorder.maxZ()).tex(n6 + n13, n6 + 128.0f).endVertex();
                    ++n14;
                    n13 += 0.5f;
                }
            }
            if (n5 < worldBorder.minZ() + n2) {
                float n16 = 0.0f;
                double n17 = max2;
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (n17 < min4) {
                    final double min6 = Math.min(1.0, min4 - n17);
                    final float n18 = (float)min6 * 0.5f;
                    worldRenderer.pos(n17, 256.0, worldBorder.minZ()).tex(n6 + n16, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n17 + min6, 256.0, worldBorder.minZ()).tex(n6 + n18 + n16, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n17 + min6, 0.0, worldBorder.minZ()).tex(n6 + n18 + n16, n6 + 128.0f).endVertex();
                    worldRenderer.pos(n17, 0.0, worldBorder.minZ()).tex(n6 + n16, n6 + 128.0f).endVertex();
                    ++n17;
                    n16 += 0.5f;
                }
            }
            instance.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.enableCull();
            GlStateManager.disableAlpha();
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.disablePolygonOffset();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.depthMask(" ".length() != 0);
        }
    }
    
    public static void func_181563_a(final AxisAlignedBB axisAlignedBB, final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        worldRenderer.begin(" ".length(), DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
    }
    
    @Override
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int partialBlockDamage) {
        if (partialBlockDamage >= 0 && partialBlockDamage < (0x45 ^ 0x4F)) {
            DestroyBlockProgress destroyBlockProgress = this.damagedBlocks.get(n);
            if (destroyBlockProgress == null || destroyBlockProgress.getPosition().getX() != blockPos.getX() || destroyBlockProgress.getPosition().getY() != blockPos.getY() || destroyBlockProgress.getPosition().getZ() != blockPos.getZ()) {
                destroyBlockProgress = new DestroyBlockProgress(n, blockPos);
                this.damagedBlocks.put(n, destroyBlockProgress);
            }
            destroyBlockProgress.setPartialBlockDamage(partialBlockDamage);
            destroyBlockProgress.setCloudUpdateTick(this.cloudTickCounter);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            this.damagedBlocks.remove(n);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void cleanupDamagedBlocks(final Iterator iterator) {
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (this.cloudTickCounter - iterator.next().getCreationCloudUpdateTick() > 102 + 217 - 289 + 370) {
                iterator.remove();
            }
        }
    }
    
    public void resetClouds() {
        this.cloudRenderer.reset();
    }
    
    protected Vector3f getViewVector(final Entity entity, final double n) {
        float n2 = (float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * n);
        final float n3 = (float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n);
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == "  ".length()) {
            n2 += 180.0f;
        }
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        return new Vector3f(sin * n4, MathHelper.sin(-n2 * 0.017453292f), cos * n4);
    }
    
    @Override
    public void onEntityAdded(final Entity entity) {
        RandomMobs.entityLoaded(entity);
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.markBlocksForUpdate(n - " ".length(), n2 - " ".length(), n3 - " ".length(), n4 + " ".length(), n5 + " ".length(), n6 + " ".length());
    }
    
    public void createBindEntityOutlineFbs(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null) {
            this.entityOutlineShader.createBindFramebuffers(n, n2);
        }
    }
    
    public void updateChunks(final long n) {
        this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(n);
        if (this.chunksToUpdateForced.size() > 0) {
            final Iterator<RenderChunk> iterator = (Iterator<RenderChunk>)this.chunksToUpdateForced.iterator();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final RenderChunk renderChunk = iterator.next();
                if (!this.renderDispatcher.updateChunkLater(renderChunk)) {
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    renderChunk.setNeedsUpdate("".length() != 0);
                    iterator.remove();
                    this.chunksToUpdate.remove(renderChunk);
                    this.chunksToResortTransparency.remove(renderChunk);
                }
            }
        }
        if (this.chunksToResortTransparency.size() > 0) {
            final Iterator<RenderChunk> iterator2 = (Iterator<RenderChunk>)this.chunksToResortTransparency.iterator();
            if (iterator2.hasNext() && this.renderDispatcher.updateTransparencyLater(iterator2.next())) {
                iterator2.remove();
            }
        }
        int length = "".length();
        int updatesPerFrame = Config.getUpdatesPerFrame();
        final int n2 = updatesPerFrame * "  ".length();
        final Iterator<RenderChunk> iterator3 = (Iterator<RenderChunk>)this.chunksToUpdate.iterator();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final RenderChunk renderChunk2 = iterator3.next();
            if (!this.renderDispatcher.updateChunkLater(renderChunk2)) {
                "".length();
                if (3 == 1) {
                    throw null;
                }
                break;
            }
            else {
                renderChunk2.setNeedsUpdate("".length() != 0);
                iterator3.remove();
                if (renderChunk2.getCompiledChunk().isEmpty() && updatesPerFrame < n2) {
                    ++updatesPerFrame;
                }
                if (++length < updatesPerFrame) {
                    continue;
                }
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                break;
            }
        }
    }
    
    private void postRenderDamagedBlocks() {
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset(0.0f, 0.0f);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.popMatrix();
    }
    
    private void generateSky2() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.sky2VBO != null) {
            this.sky2VBO.deleteGlBuffers();
        }
        if (this.glSkyList2 >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList2);
            this.glSkyList2 = -" ".length();
        }
        if (this.vboEnabled) {
            this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, -16.0f, " ".length() != 0);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.sky2VBO.func_181722_a(worldRenderer.getByteBuffer());
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            GL11.glNewList(this.glSkyList2 = GLAllocation.generateDisplayLists(" ".length()), 2882 + 3253 - 2047 + 776);
            this.renderSky(worldRenderer, -16.0f, " ".length() != 0);
            instance.draw();
            GL11.glEndList();
        }
    }
    
    public void updateClouds() {
        this.cloudTickCounter += " ".length();
        if (this.cloudTickCounter % (0x21 ^ 0x35) == 0) {
            this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
        }
    }
    
    public void renderClouds(float n, final int n2) {
        if (!Config.isCloudsOff()) {
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
                final Object call = Reflector.call(this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object["".length()]);
                if (call != null) {
                    final Object o = call;
                    final ReflectorMethod iRenderHandler_render = Reflector.IRenderHandler_render;
                    final Object[] array = new Object["   ".length()];
                    array["".length()] = n;
                    array[" ".length()] = this.theWorld;
                    array["  ".length()] = this.mc;
                    Reflector.callVoid(o, iRenderHandler_render, array);
                    return;
                }
            }
            if (this.mc.theWorld.provider.isSurfaceWorld()) {
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(n, n2);
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else {
                    this.cloudRenderer.prepareToRender("".length() != 0, this.cloudTickCounter, n);
                    n = 0.0f;
                    GlStateManager.disableCull();
                    final float n3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * n);
                    " ".length();
                    " ".length();
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(293 + 313 + 114 + 50, 600 + 748 - 878 + 301, " ".length(), "".length());
                    if (this.cloudRenderer.shouldUpdateGlList()) {
                        this.cloudRenderer.startUpdateGlList();
                        final Vec3 cloudColour = this.theWorld.getCloudColour(n);
                        float n4 = (float)cloudColour.xCoord;
                        float n5 = (float)cloudColour.yCoord;
                        float n6 = (float)cloudColour.zCoord;
                        if (n2 != "  ".length()) {
                            final float n7 = (n4 * 30.0f + n5 * 59.0f + n6 * 11.0f) / 100.0f;
                            final float n8 = (n4 * 30.0f + n5 * 70.0f) / 100.0f;
                            final float n9 = (n4 * 30.0f + n6 * 70.0f) / 100.0f;
                            n4 = n7;
                            n5 = n8;
                            n6 = n9;
                        }
                        final double n10 = this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * n + (this.cloudTickCounter + n) * 0.029999999329447746;
                        final double n11 = this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * n;
                        final int floor_double = MathHelper.floor_double(n10 / 2048.0);
                        final int floor_double2 = MathHelper.floor_double(n11 / 2048.0);
                        final double n12 = n10 - floor_double * (343 + 1668 - 1581 + 1618);
                        final double n13 = n11 - floor_double2 * (1006 + 1948 - 2476 + 1570);
                        final float n14 = this.theWorld.provider.getCloudHeight() - n3 + 0.33f + this.mc.gameSettings.ofCloudsHeight * 128.0f;
                        final float n15 = (float)(n12 * 4.8828125E-4);
                        final float n16 = (float)(n13 * 4.8828125E-4);
                        worldRenderer.begin(0x6D ^ 0x6A, DefaultVertexFormats.POSITION_TEX_COLOR);
                        int i = -(160 + 173 - 186 + 109);
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        while (i < 61 + 193 + 2 + 0) {
                            int j = -(57 + 173 - 226 + 252);
                            "".length();
                            if (0 >= 3) {
                                throw null;
                            }
                            while (j < 249 + 21 - 125 + 111) {
                                worldRenderer.pos(i + "".length(), n14, j + (0x7B ^ 0x5B)).tex((i + "".length()) * 4.8828125E-4f + n15, (j + (0x40 ^ 0x60)) * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                                worldRenderer.pos(i + (0xE2 ^ 0xC2), n14, j + (0x97 ^ 0xB7)).tex((i + (0x15 ^ 0x35)) * 4.8828125E-4f + n15, (j + (0x4D ^ 0x6D)) * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                                worldRenderer.pos(i + (0x73 ^ 0x53), n14, j + "".length()).tex((i + (0x73 ^ 0x53)) * 4.8828125E-4f + n15, (j + "".length()) * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                                worldRenderer.pos(i + "".length(), n14, j + "".length()).tex((i + "".length()) * 4.8828125E-4f + n15, (j + "".length()) * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                                j += 32;
                            }
                            i += 32;
                        }
                        instance.draw();
                        this.cloudRenderer.endUpdateGlList();
                    }
                    this.cloudRenderer.renderGlList();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.disableBlend();
                    GlStateManager.enableCull();
                }
            }
        }
    }
    
    public void loadRenderers() {
        if (this.theWorld != null) {
            this.displayListEntitiesDirty = (" ".length() != 0);
            Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
            Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
            BlockModelRenderer.updateAoLightValue();
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            this.renderDistance = this.renderDistanceChunks * (0x3C ^ 0x2C);
            this.renderDistanceSq = this.renderDistance * this.renderDistance;
            final boolean vboEnabled = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (vboEnabled && !this.vboEnabled) {
                this.renderContainer = new RenderList();
                this.renderChunkFactory = new ListChunkFactory();
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else if (!vboEnabled && this.vboEnabled) {
                this.renderContainer = new VboRenderList();
                this.renderChunkFactory = new VboChunkFactory();
            }
            if (vboEnabled != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            final Set field_181024_n = this.field_181024_n;
            synchronized (this.field_181024_n) {
                this.field_181024_n.clear();
                // monitorexit(this.field_181024_n)
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
            if (this.theWorld != null) {
                final Entity renderViewEntity = this.mc.getRenderViewEntity();
                if (renderViewEntity != null) {
                    this.viewFrustum.updateChunkPositions(renderViewEntity.posX, renderViewEntity.posZ);
                }
            }
            this.renderEntitiesStartupCounter = "  ".length();
        }
    }
    
    public void drawBlockDamageTexture(final Tessellator tessellator, final WorldRenderer worldRenderer, final Entity entity, final float n) {
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        if (!this.damagedBlocks.isEmpty()) {
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.preRenderDamagedBlocks();
            worldRenderer.begin(0x51 ^ 0x56, DefaultVertexFormats.BLOCK);
            worldRenderer.setTranslation(-n2, -n3, -n4);
            worldRenderer.markDirty();
            final Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final DestroyBlockProgress destroyBlockProgress = iterator.next();
                final BlockPos position = destroyBlockProgress.getPosition();
                final double n5 = position.getX() - n2;
                final double n6 = position.getY() - n3;
                final double n7 = position.getZ() - n4;
                final Block block = this.theWorld.getBlockState(position).getBlock();
                int n10;
                if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
                    int n8;
                    if (!(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull)) {
                        n8 = "".length();
                        "".length();
                        if (2 < 2) {
                            throw null;
                        }
                    }
                    else {
                        n8 = " ".length();
                    }
                    int callBoolean = n8;
                    if (callBoolean == 0) {
                        final TileEntity tileEntity = this.theWorld.getTileEntity(position);
                        if (tileEntity != null) {
                            callBoolean = (Reflector.callBoolean(tileEntity, Reflector.ForgeTileEntity_canRenderBreaking, new Object["".length()]) ? 1 : 0);
                        }
                    }
                    int n9;
                    if (callBoolean != 0) {
                        n9 = "".length();
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n9 = " ".length();
                    }
                    n10 = n9;
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else {
                    int n11;
                    if (!(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull)) {
                        n11 = " ".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        n11 = "".length();
                    }
                    n10 = n11;
                }
                if (n10 != 0) {
                    if (n5 * n5 + n6 * n6 + n7 * n7 > 1024.0) {
                        iterator.remove();
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        final IBlockState blockState = this.theWorld.getBlockState(position);
                        if (blockState.getBlock().getMaterial() == Material.air) {
                            continue;
                        }
                        this.mc.getBlockRendererDispatcher().renderBlockDamage(blockState, position, this.destroyBlockIcons[destroyBlockProgress.getPartialBlockDamage()], this.theWorld);
                    }
                }
            }
            tessellator.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            this.postRenderDamagedBlocks();
        }
    }
    
    private RenderChunk func_181562_a(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos positionOffset16 = renderChunk.getPositionOffset16(enumFacing);
        if (positionOffset16.getY() >= 0 && positionOffset16.getY() < 180 + 237 - 299 + 138) {
            final int abs_int = MathHelper.abs_int(blockPos.getX() - positionOffset16.getX());
            final int abs_int2 = MathHelper.abs_int(blockPos.getZ() - positionOffset16.getZ());
            if (Config.isFogOff()) {
                if (abs_int > this.renderDistance || abs_int2 > this.renderDistance) {
                    return null;
                }
            }
            else if (abs_int * abs_int + abs_int2 * abs_int2 > this.renderDistanceSq) {
                return null;
            }
            return this.viewFrustum.getRenderChunk(positionOffset16);
        }
        return null;
    }
    
    protected boolean isRenderEntityOutlines() {
        if (this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.thePlayer != null && this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void preRenderDamagedBlocks() {
        GlStateManager.tryBlendFuncSeparate(427 + 359 - 703 + 691, 633 + 100 - 534 + 569, " ".length(), "".length());
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.doPolygonOffset(-3.0f, -3.0f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc(248 + 321 - 96 + 43, 0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
    }
    
    private boolean isPositionInRenderChunk(final BlockPos blockPos, final RenderChunk renderChunk) {
        final BlockPos position = renderChunk.getPosition();
        int n;
        if (MathHelper.abs_int(blockPos.getX() - position.getX()) > (0x1 ^ 0x11)) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (MathHelper.abs_int(blockPos.getY() - position.getY()) > (0x7D ^ 0x6D)) {
            n = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (MathHelper.abs_int(blockPos.getZ() - position.getZ()) <= (0x3B ^ 0x2B)) {
            n = " ".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private EntityFX spawnEntityFX(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        if (this.mc == null || this.mc.getRenderViewEntity() == null || this.mc.effectRenderer == null) {
            return null;
        }
        int n8 = this.mc.gameSettings.particleSetting;
        if (n8 == " ".length() && this.theWorld.rand.nextInt("   ".length()) == 0) {
            n8 = "  ".length();
        }
        final double n9 = this.mc.getRenderViewEntity().posX - n2;
        final double n10 = this.mc.getRenderViewEntity().posY - n3;
        final double n11 = this.mc.getRenderViewEntity().posZ - n4;
        if (n == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal()) {
            return null;
        }
        if (n == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()) {
            return null;
        }
        if (n == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()) {
            return null;
        }
        if (b) {
            return this.mc.effectRenderer.spawnEffectParticle(n, n2, n3, n4, n5, n6, n7, array);
        }
        double n12 = 256.0;
        if (n == EnumParticleTypes.CRIT.getParticleID()) {
            n12 = 38416.0;
        }
        if (n9 * n9 + n10 * n10 + n11 * n11 > n12) {
            return null;
        }
        if (n8 > " ".length()) {
            return null;
        }
        final EntityFX spawnEffectParticle = this.mc.effectRenderer.spawnEffectParticle(n, n2, n3, n4, n5, n6, n7, array);
        if (n == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
            CustomColorizer.updateWaterFX(spawnEffectParticle, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
            CustomColorizer.updateWaterFX(spawnEffectParticle, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.WATER_DROP.getParticleID()) {
            CustomColorizer.updateWaterFX(spawnEffectParticle, this.theWorld, n2, n3, n4);
        }
        if (n == EnumParticleTypes.TOWN_AURA.getParticleID()) {
            CustomColorizer.updateMyceliumFX(spawnEffectParticle);
        }
        if (n == EnumParticleTypes.PORTAL.getParticleID()) {
            CustomColorizer.updatePortalFX(spawnEffectParticle);
        }
        if (n == EnumParticleTypes.REDSTONE.getParticleID()) {
            CustomColorizer.updateReddustFX(spawnEffectParticle, this.theWorld, n2, n3, n4);
        }
        return spawnEffectParticle;
    }
    
    private void renderStars(final WorldRenderer worldRenderer) {
        final Random random = new Random(10842L);
        worldRenderer.begin(0xB4 ^ 0xB3, DefaultVertexFormats.POSITION);
        int i = "".length();
        "".length();
        if (4 == 0) {
            throw null;
        }
        while (i < 395 + 1067 - 1003 + 1041) {
            final double n = random.nextFloat() * 2.0f - 1.0f;
            final double n2 = random.nextFloat() * 2.0f - 1.0f;
            final double n3 = random.nextFloat() * 2.0f - 1.0f;
            final double n4 = 0.15f + random.nextFloat() * 0.1f;
            final double n5 = n * n + n2 * n2 + n3 * n3;
            if (n5 < 1.0 && n5 > 0.01) {
                final double n6 = 1.0 / Math.sqrt(n5);
                final double n7 = n * n6;
                final double n8 = n2 * n6;
                final double n9 = n3 * n6;
                final double n10 = n7 * 100.0;
                final double n11 = n8 * 100.0;
                final double n12 = n9 * 100.0;
                final double atan2 = Math.atan2(n7, n9);
                final double sin = Math.sin(atan2);
                final double cos = Math.cos(atan2);
                final double atan3 = Math.atan2(Math.sqrt(n7 * n7 + n9 * n9), n8);
                final double sin2 = Math.sin(atan3);
                final double cos2 = Math.cos(atan3);
                final double n13 = random.nextDouble() * 3.141592653589793 * 2.0;
                final double sin3 = Math.sin(n13);
                final double cos3 = Math.cos(n13);
                int j = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (j < (0x11 ^ 0x15)) {
                    final double n14 = ((j & "  ".length()) - " ".length()) * n4;
                    final double n15 = ((j + " ".length() & "  ".length()) - " ".length()) * n4;
                    final double n16 = n14 * cos3 - n15 * sin3;
                    final double n17 = n15 * cos3 + n14 * sin3;
                    final double n18 = n16 * sin2 + 0.0 * cos2;
                    final double n19 = 0.0 * sin2 - n16 * cos2;
                    worldRenderer.pos(n10 + (n19 * sin - n17 * cos), n11 + n18, n12 + (n17 * sin + n19 * cos)).endVertex();
                    ++j;
                }
            }
            ++i;
        }
    }
    
    private void updateDestroyBlockIcons() {
        final TextureMap textureMapBlocks = this.mc.getTextureMapBlocks();
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[i] = textureMapBlocks.getAtlasSprite(RenderGlobal.I[0x49 ^ 0x4F] + i);
            ++i;
        }
    }
    
    private void generateSky() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.skyVBO != null) {
            this.skyVBO.deleteGlBuffers();
        }
        if (this.glSkyList >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList);
            this.glSkyList = -" ".length();
        }
        if (this.vboEnabled) {
            this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, 16.0f, "".length() != 0);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.skyVBO.func_181722_a(worldRenderer.getByteBuffer());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            GL11.glNewList(this.glSkyList = GLAllocation.generateDisplayLists(" ".length()), 3383 + 4634 - 3358 + 205);
            this.renderSky(worldRenderer, 16.0f, "".length() != 0);
            instance.draw();
            GL11.glEndList();
        }
    }
    
    public void func_181023_a(final Collection collection, final Collection collection2) {
        final Set field_181024_n = this.field_181024_n;
        synchronized (this.field_181024_n) {
            this.field_181024_n.removeAll(collection);
            this.field_181024_n.addAll(collection2);
            // monitorexit(this.field_181024_n)
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        final Random rand = this.theWorld.rand;
        switch (n) {
            case 1000: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x31 ^ 0x16], 1.0f, 1.0f, "".length() != 0);
                "".length();
                if (1 < -1) {
                    throw null;
                }
                break;
            }
            case 1001: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0xA7 ^ 0x8F], 1.0f, 1.2f, "".length() != 0);
                "".length();
                if (3 == 4) {
                    throw null;
                }
                break;
            }
            case 1002: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x4B ^ 0x62], 1.0f, 1.2f, "".length() != 0);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                break;
            }
            case 1003: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x17 ^ 0x3D], 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 1004: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x11 ^ 0x3A], 0.5f, 2.6f + (rand.nextFloat() - rand.nextFloat()) * 0.8f, "".length() != 0);
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 1005: {
                if (Item.getItemById(n2) instanceof ItemRecord) {
                    this.theWorld.playRecord(blockPos, RenderGlobal.I[0xAD ^ 0x81] + ((ItemRecord)Item.getItemById(n2)).recordName);
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    this.theWorld.playRecord(blockPos, null);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 1006: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0xB1 ^ 0x9C], 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (1 < 0) {
                    throw null;
                }
                break;
            }
            case 1007: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x91 ^ 0xBF], 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 1008: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x8B ^ 0xA4], 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                break;
            }
            case 1009: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x19 ^ 0x29], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (4 == -1) {
                    throw null;
                }
                break;
            }
            case 1010: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x53 ^ 0x62], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (2 < -1) {
                    throw null;
                }
                break;
            }
            case 1011: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x54 ^ 0x66], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (false == true) {
                    throw null;
                }
                break;
            }
            case 1012: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x64 ^ 0x57], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (3 == 2) {
                    throw null;
                }
                break;
            }
            case 1014: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x99 ^ 0xAD], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (3 == 4) {
                    throw null;
                }
                break;
            }
            case 1015: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x38 ^ 0xD], 0.05f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 1016: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0xAA ^ 0x9C], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                break;
            }
            case 1017: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0xA6 ^ 0x91], 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, "".length() != 0);
                "".length();
                if (3 < 0) {
                    throw null;
                }
                break;
            }
            case 1020: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x35 ^ 0xD], 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (1 == 2) {
                    throw null;
                }
                break;
            }
            case 1021: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x41 ^ 0x78], 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            case 1022: {
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x6 ^ 0x3C], 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (false == true) {
                    throw null;
                }
                break;
            }
            case 2000: {
                final int n3 = n2 % "   ".length() - " ".length();
                final int n4 = n2 / "   ".length() % "   ".length() - " ".length();
                final double n5 = blockPos.getX() + n3 * 0.6 + 0.5;
                final double n6 = blockPos.getY() + 0.5;
                final double n7 = blockPos.getZ() + n4 * 0.6 + 0.5;
                int i = "".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
                while (i < (0xC9 ^ 0xC3)) {
                    final double n8 = rand.nextDouble() * 0.2 + 0.01;
                    this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n5 + n3 * 0.01 + (rand.nextDouble() - 0.5) * n4 * 0.5, n6 + (rand.nextDouble() - 0.5) * 0.5, n7 + n4 * 0.01 + (rand.nextDouble() - 0.5) * n3 * 0.5, n3 * n8 + rand.nextGaussian() * 0.01, -0.03 + rand.nextGaussian() * 0.01, n4 * n8 + rand.nextGaussian() * 0.01, new int["".length()]);
                    ++i;
                }
            }
            case 2001: {
                final Block blockById = Block.getBlockById(n2 & 2438 + 3699 - 4897 + 2855);
                if (blockById.getMaterial() != Material.air) {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(blockById.stepSound.getBreakSound()), (blockById.stepSound.getVolume() + 1.0f) / 2.0f, blockById.stepSound.getFrequency() * 0.8f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
                }
                this.mc.effectRenderer.addBlockDestroyEffects(blockPos, blockById.getStateFromMeta(n2 >> (0x7B ^ 0x77) & 133 + 197 - 107 + 32));
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                break;
            }
            case 2002: {
                final double n9 = blockPos.getX();
                final double n10 = blockPos.getY();
                final double n11 = blockPos.getZ();
                int j = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (j < (0xD ^ 0x5)) {
                    final EnumParticleTypes item_CRACK = EnumParticleTypes.ITEM_CRACK;
                    final double n12 = n9;
                    final double n13 = n10;
                    final double n14 = n11;
                    final double n15 = rand.nextGaussian() * 0.15;
                    final double n16 = rand.nextDouble() * 0.2;
                    final double n17 = rand.nextGaussian() * 0.15;
                    final int[] array = new int["  ".length()];
                    array["".length()] = Item.getIdFromItem(Items.potionitem);
                    array[" ".length()] = n2;
                    this.spawnParticle(item_CRACK, n12, n13, n14, n15, n16, n17, array);
                    ++j;
                }
                final int colorFromDamage = Items.potionitem.getColorFromDamage(n2);
                final float n18 = (colorFromDamage >> (0x55 ^ 0x45) & 226 + 155 - 260 + 134) / 255.0f;
                final float n19 = (colorFromDamage >> (0x26 ^ 0x2E) & 83 + 181 - 258 + 249) / 255.0f;
                final float n20 = (colorFromDamage >> "".length() & 146 + 240 - 147 + 16) / 255.0f;
                EnumParticleTypes enumParticleTypes = EnumParticleTypes.SPELL;
                if (Items.potionitem.isEffectInstant(n2)) {
                    enumParticleTypes = EnumParticleTypes.SPELL_INSTANT;
                }
                int k = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (k < (0x33 ^ 0x57)) {
                    final double n21 = rand.nextDouble() * 4.0;
                    final double n22 = rand.nextDouble() * 3.141592653589793 * 2.0;
                    final double n23 = Math.cos(n22) * n21;
                    final double n24 = 0.01 + rand.nextDouble() * 0.5;
                    final double n25 = Math.sin(n22) * n21;
                    final EntityFX spawnEntityFX = this.spawnEntityFX(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), n9 + n23 * 0.1, n10 + 0.3, n11 + n25 * 0.1, n23, n24, n25, new int["".length()]);
                    if (spawnEntityFX != null) {
                        final float n26 = 0.75f + rand.nextFloat() * 0.25f;
                        spawnEntityFX.setRBGColorF(n18 * n26, n19 * n26, n20 * n26);
                        spawnEntityFX.multiplyVelocity((float)n21);
                    }
                    ++k;
                }
                this.theWorld.playSoundAtPos(blockPos, RenderGlobal.I[0x3F ^ 0x4], 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, "".length() != 0);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 2003: {
                final double n27 = blockPos.getX() + 0.5;
                final double n28 = blockPos.getY();
                final double n29 = blockPos.getZ() + 0.5;
                int l = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (l < (0x1A ^ 0x12)) {
                    final EnumParticleTypes item_CRACK2 = EnumParticleTypes.ITEM_CRACK;
                    final double n30 = n27;
                    final double n31 = n28;
                    final double n32 = n29;
                    final double n33 = rand.nextGaussian() * 0.15;
                    final double n34 = rand.nextDouble() * 0.2;
                    final double n35 = rand.nextGaussian() * 0.15;
                    final int[] array2 = new int[" ".length()];
                    array2["".length()] = Item.getIdFromItem(Items.ender_eye);
                    this.spawnParticle(item_CRACK2, n30, n31, n32, n33, n34, n35, array2);
                    ++l;
                }
                double n36 = 0.0;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (n36 < 6.283185307179586) {
                    this.spawnParticle(EnumParticleTypes.PORTAL, n27 + Math.cos(n36) * 5.0, n28 - 0.4, n29 + Math.sin(n36) * 5.0, Math.cos(n36) * -5.0, 0.0, Math.sin(n36) * -5.0, new int["".length()]);
                    this.spawnParticle(EnumParticleTypes.PORTAL, n27 + Math.cos(n36) * 5.0, n28 - 0.4, n29 + Math.sin(n36) * 5.0, Math.cos(n36) * -7.0, 0.0, Math.sin(n36) * -7.0, new int["".length()]);
                    n36 += 0.15707963267948966;
                }
            }
            case 2004: {
                int length = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (length < (0xBE ^ 0xAA)) {
                    final double n37 = blockPos.getX() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n38 = blockPos.getY() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n39 = blockPos.getZ() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n37, n38, n39, 0.0, 0.0, 0.0, new int["".length()]);
                    this.theWorld.spawnParticle(EnumParticleTypes.FLAME, n37, n38, n39, 0.0, 0.0, 0.0, new int["".length()]);
                    ++length;
                }
            }
            case 2005: {
                ItemDye.spawnBonemealParticles(this.theWorld, blockPos, n2);
                break;
            }
        }
    }
    
    public void renderEntities(final Entity entity, final ICamera camera, final float n) {
        int n2 = "".length();
        if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
            n2 = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object["".length()]);
        }
        if (this.renderEntitiesStartupCounter > 0) {
            if (n2 > 0) {
                return;
            }
            this.renderEntitiesStartupCounter -= " ".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            final double n3 = entity.prevPosX + (entity.posX - entity.prevPosX) * n;
            final double n4 = entity.prevPosY + (entity.posY - entity.prevPosY) * n;
            final double n5 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * n;
            this.theWorld.theProfiler.startSection(RenderGlobal.I[0x89 ^ 0x82]);
            TileEntityRendererDispatcher.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRendererObj, this.mc.getRenderViewEntity(), n);
            this.renderManager.cacheActiveRenderInfo(this.theWorld, this.mc.fontRendererObj, this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, n);
            if (n2 == 0) {
                this.countEntitiesTotal = "".length();
                this.countEntitiesRendered = "".length();
                this.countEntitiesHidden = "".length();
                this.countTileEntitiesRendered = "".length();
            }
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final double staticPlayerX = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * n;
            final double staticPlayerY = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * n;
            final double staticPlayerZ = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * n;
            TileEntityRendererDispatcher.staticPlayerX = staticPlayerX;
            TileEntityRendererDispatcher.staticPlayerY = staticPlayerY;
            TileEntityRendererDispatcher.staticPlayerZ = staticPlayerZ;
            this.renderManager.setRenderPosition(staticPlayerX, staticPlayerY, staticPlayerZ);
            this.mc.entityRenderer.enableLightmap();
            this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0x5A ^ 0x56]);
            final List<Entity> loadedEntityList = this.theWorld.getLoadedEntityList();
            if (n2 == 0) {
                this.countEntitiesTotal = loadedEntityList.size();
            }
            if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
                GlStateManager.disableFog();
            }
            final boolean exists = Reflector.ForgeEntity_shouldRenderInPass.exists();
            final boolean exists2 = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < this.theWorld.weatherEffects.size()) {
                final Entity entity2 = this.theWorld.weatherEffects.get(i);
                Label_0572: {
                    if (exists) {
                        final Entity entity3 = entity2;
                        final ReflectorMethod forgeEntity_shouldRenderInPass = Reflector.ForgeEntity_shouldRenderInPass;
                        final Object[] array = new Object[" ".length()];
                        array["".length()] = n2;
                        if (!Reflector.callBoolean(entity3, forgeEntity_shouldRenderInPass, array)) {
                            break Label_0572;
                        }
                    }
                    this.countEntitiesRendered += " ".length();
                    if (entity2.isInRangeToRender3d(n3, n4, n5)) {
                        this.renderManager.renderEntitySimple(entity2, n);
                    }
                }
                ++i;
            }
            if (this.isRenderEntityOutlines()) {
                GlStateManager.depthFunc(187 + 113 - 153 + 372);
                GlStateManager.disableFog();
                this.entityOutlineFramebuffer.framebufferClear();
                this.entityOutlineFramebuffer.bindFramebuffer("".length() != 0);
                this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0xC9 ^ 0xC4]);
                RenderHelper.disableStandardItemLighting();
                this.renderManager.setRenderOutlines(" ".length() != 0);
                int j = "".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                while (j < loadedEntityList.size()) {
                    final Entity entity4 = loadedEntityList.get(j);
                    Label_0928: {
                        if (exists) {
                            final Entity entity5 = entity4;
                            final ReflectorMethod forgeEntity_shouldRenderInPass2 = Reflector.ForgeEntity_shouldRenderInPass;
                            final Object[] array2 = new Object[" ".length()];
                            array2["".length()] = n2;
                            if (!Reflector.callBoolean(entity5, forgeEntity_shouldRenderInPass2, array2)) {
                                break Label_0928;
                            }
                        }
                        int n6;
                        if (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping()) {
                            n6 = " ".length();
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        else {
                            n6 = "".length();
                        }
                        final int n7 = n6;
                        int n8;
                        if (entity4.isInRangeToRender3d(n3, n4, n5) && (entity4.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entity4.getEntityBoundingBox()) || entity4.riddenByEntity == this.mc.thePlayer) && entity4 instanceof EntityPlayer) {
                            n8 = " ".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            n8 = "".length();
                        }
                        final int n9 = n8;
                        if ((entity4 != this.mc.getRenderViewEntity() || this.mc.gameSettings.thirdPersonView != 0 || n7 != 0) && n9 != 0) {
                            this.renderManager.renderEntitySimple(entity4, n);
                        }
                    }
                    ++j;
                }
                this.renderManager.setRenderOutlines("".length() != 0);
                RenderHelper.enableStandardItemLighting();
                GlStateManager.depthMask("".length() != 0);
                this.entityOutlineShader.loadShaderGroup(n);
                GlStateManager.enableLighting();
                GlStateManager.depthMask(" ".length() != 0);
                this.mc.getFramebuffer().bindFramebuffer("".length() != 0);
                GlStateManager.enableFog();
                GlStateManager.enableBlend();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthFunc(494 + 74 - 302 + 249);
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
            }
            this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0x5 ^ 0xB]);
            final Iterator iterator = this.renderInfosEntities.iterator();
            final boolean fancyGraphics = this.mc.gameSettings.fancyGraphics;
            this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final ContainerLocalRenderInformation containerLocalRenderInformation = iterator.next();
                final ClassInheritanceMultiMap<Entity> classInheritanceMultiMap = this.theWorld.getChunkFromBlockCoords(containerLocalRenderInformation.renderChunk.getPosition()).getEntityLists()[containerLocalRenderInformation.renderChunk.getPosition().getY() / (0xB4 ^ 0xA4)];
                if (!classInheritanceMultiMap.isEmpty()) {
                    for (final Entity renderedEntity : classInheritanceMultiMap) {
                        if (exists) {
                            final Entity entity6 = renderedEntity;
                            final ReflectorMethod forgeEntity_shouldRenderInPass3 = Reflector.ForgeEntity_shouldRenderInPass;
                            final Object[] array3 = new Object[" ".length()];
                            array3["".length()] = n2;
                            if (!Reflector.callBoolean(entity6, forgeEntity_shouldRenderInPass3, array3)) {
                                continue;
                            }
                        }
                        int n10;
                        if (!this.renderManager.shouldRender(renderedEntity, camera, n3, n4, n5) && renderedEntity.riddenByEntity != this.mc.thePlayer) {
                            n10 = "".length();
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n10 = " ".length();
                        }
                        final int n11 = n10;
                        if (n11 == 0) {
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            int n12;
                            if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
                                n12 = (((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping() ? 1 : 0);
                                "".length();
                                if (2 == 0) {
                                    throw null;
                                }
                            }
                            else {
                                n12 = "".length();
                            }
                            final int n13 = n12;
                            if ((renderedEntity == this.mc.getRenderViewEntity() && this.mc.gameSettings.thirdPersonView == 0 && n13 == 0) || (renderedEntity.posY >= 0.0 && renderedEntity.posY < 256.0 && !this.theWorld.isBlockLoaded(new BlockPos(renderedEntity)))) {
                                continue;
                            }
                            this.countEntitiesRendered += " ".length();
                            if (renderedEntity.getClass() == EntityItemFrame.class) {
                                renderedEntity.renderDistanceWeight = 0.06;
                            }
                            this.renderedEntity = renderedEntity;
                            this.renderManager.renderEntitySimple(renderedEntity, n);
                            this.renderedEntity = null;
                        }
                        if (n11 == 0 && renderedEntity instanceof EntityWitherSkull) {
                            this.mc.getRenderManager().renderWitherSkull(renderedEntity, n);
                            "".length();
                            if (false) {
                                throw null;
                            }
                            continue;
                        }
                    }
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    continue;
                }
            }
            this.mc.gameSettings.fancyGraphics = fancyGraphics;
            final FontRenderer fontRenderer = TileEntityRendererDispatcher.instance.getFontRenderer();
            this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0x13 ^ 0x1C]);
            RenderHelper.enableStandardItemLighting();
            final Iterator iterator3 = this.renderInfosTileEntities.iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator3.hasNext()) {
                final List<TileEntity> tileEntities = iterator3.next().renderChunk.getCompiledChunk().getTileEntities();
                if (!tileEntities.isEmpty()) {
                    for (final TileEntity tileEntity : tileEntities) {
                        if (!exists2) {
                            "".length();
                            if (0 >= 2) {
                                throw null;
                            }
                        }
                        else {
                            final TileEntity tileEntity2 = tileEntity;
                            final ReflectorMethod forgeTileEntity_shouldRenderInPass = Reflector.ForgeTileEntity_shouldRenderInPass;
                            final Object[] array4 = new Object[" ".length()];
                            array4["".length()] = n2;
                            if (!Reflector.callBoolean(tileEntity2, forgeTileEntity_shouldRenderInPass, array4)) {
                                continue;
                            }
                            final AxisAlignedBB axisAlignedBB = (AxisAlignedBB)Reflector.call(tileEntity, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object["".length()]);
                            if (axisAlignedBB != null && !camera.isBoundingBoxInFrustum(axisAlignedBB)) {
                                continue;
                            }
                        }
                        if (tileEntity.getClass() == TileEntitySign.class && !Config.zoomMode) {
                            final EntityPlayerSP thePlayer = this.mc.thePlayer;
                            if (tileEntity.getDistanceSq(thePlayer.posX, thePlayer.posY, thePlayer.posZ) > 256.0) {
                                fontRenderer.enabled = ("".length() != 0);
                            }
                        }
                        TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, n, -" ".length());
                        this.countTileEntitiesRendered += " ".length();
                        fontRenderer.enabled = (" ".length() != 0);
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                    }
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    continue;
                }
            }
            final Set field_181024_n = this.field_181024_n;
            synchronized (this.field_181024_n) {
                final Iterator iterator5 = this.field_181024_n.iterator();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (iterator5.hasNext()) {
                    final TileEntity next = iterator5.next();
                    if (exists2) {
                        final TileEntity tileEntity3 = next;
                        final ReflectorMethod forgeTileEntity_shouldRenderInPass2 = Reflector.ForgeTileEntity_shouldRenderInPass;
                        final Object[] array5 = new Object[" ".length()];
                        array5["".length()] = n2;
                        if (!Reflector.callBoolean(tileEntity3, forgeTileEntity_shouldRenderInPass2, array5)) {
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            final AxisAlignedBB axisAlignedBB2 = (AxisAlignedBB)Reflector.call(next, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object["".length()]);
                            if (axisAlignedBB2 != null && !camera.isBoundingBoxInFrustum(axisAlignedBB2)) {
                                "".length();
                                if (3 >= 4) {
                                    throw null;
                                }
                                continue;
                            }
                        }
                    }
                    if (next.getClass() == TileEntitySign.class && !Config.zoomMode) {
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        if (next.getDistanceSq(thePlayer2.posX, thePlayer2.posY, thePlayer2.posZ) > 256.0) {
                            fontRenderer.enabled = ("".length() != 0);
                        }
                    }
                    TileEntityRendererDispatcher.instance.renderTileEntity(next, n, -" ".length());
                    fontRenderer.enabled = (" ".length() != 0);
                }
                // monitorexit(this.field_181024_n)
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            this.preRenderDamagedBlocks();
            final Iterator<DestroyBlockProgress> iterator6 = this.damagedBlocks.values().iterator();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (iterator6.hasNext()) {
                final DestroyBlockProgress next2 = iterator6.next();
                BlockPos blockPos = next2.getPosition();
                TileEntity tileEntity4 = this.theWorld.getTileEntity(blockPos);
                if (tileEntity4 instanceof TileEntityChest) {
                    final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity4;
                    if (tileEntityChest.adjacentChestXNeg != null) {
                        blockPos = blockPos.offset(EnumFacing.WEST);
                        tileEntity4 = this.theWorld.getTileEntity(blockPos);
                        "".length();
                        if (3 == 4) {
                            throw null;
                        }
                    }
                    else if (tileEntityChest.adjacentChestZNeg != null) {
                        blockPos = blockPos.offset(EnumFacing.NORTH);
                        tileEntity4 = this.theWorld.getTileEntity(blockPos);
                    }
                }
                final Block block = this.theWorld.getBlockState(blockPos).getBlock();
                int n14;
                if (exists2) {
                    n14 = "".length();
                    if (tileEntity4 != null) {
                        final TileEntityChest tileEntityChest2 = (TileEntityChest)tileEntity4;
                        final ReflectorMethod forgeTileEntity_shouldRenderInPass3 = Reflector.ForgeTileEntity_shouldRenderInPass;
                        final Object[] array6 = new Object[" ".length()];
                        array6["".length()] = n2;
                        if (Reflector.callBoolean(tileEntityChest2, forgeTileEntity_shouldRenderInPass3, array6) && Reflector.callBoolean(tileEntity4, Reflector.ForgeTileEntity_canRenderBreaking, new Object["".length()])) {
                            final AxisAlignedBB axisAlignedBB3 = (AxisAlignedBB)Reflector.call(tileEntity4, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object["".length()]);
                            if (axisAlignedBB3 != null) {
                                n14 = (camera.isBoundingBoxInFrustum(axisAlignedBB3) ? 1 : 0);
                                "".length();
                                if (0 == -1) {
                                    throw null;
                                }
                            }
                        }
                    }
                }
                else {
                    int n15;
                    if (tileEntity4 != null && (block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull)) {
                        n15 = " ".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n15 = "".length();
                    }
                    n14 = n15;
                }
                if (n14 != 0) {
                    TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity4, n, next2.getPartialBlockDamage());
                }
            }
            this.postRenderDamagedBlocks();
            this.mc.entityRenderer.disableLightmap();
            this.mc.mcProfiler.endSection();
        }
    }
    
    public void setWorldAndLoadRenderers(final WorldClient theWorld) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = -"".length();
        this.frustumUpdatePosChunkY = -"".length();
        this.frustumUpdatePosChunkZ = -"".length();
        this.renderManager.set(theWorld);
        if ((this.theWorld = theWorld) != null) {
            theWorld.addWorldAccess(this);
            this.loadRenderers();
        }
    }
    
    @Override
    public void playSound(final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    public void setDisplayListEntitiesDirty() {
        this.displayListEntitiesDirty = (" ".length() != 0);
    }
    
    @Override
    public void markBlockForUpdate(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - " ".length(), y - " ".length(), z - " ".length(), x + " ".length(), y + " ".length(), z + " ".length());
    }
    
    public void setupTerrain(final Entity entity, final double n, ICamera camera, final int frameIndex, final boolean b) {
        if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
            this.loadRenderers();
        }
        this.theWorld.theProfiler.startSection(RenderGlobal.I[0x96 ^ 0x8E]);
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
            this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
        }
        this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0xC ^ 0x15]);
        final double n5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        this.renderContainer.initialize(n5, n6, n7);
        this.theWorld.theProfiler.endStartSection(RenderGlobal.I[0xBD ^ 0xA7]);
        if (this.debugFixedClippingHelper != null) {
            final Frustum frustum = new Frustum(this.debugFixedClippingHelper);
            frustum.setPosition(this.debugTerrainFrustumPosition.field_181059_a, this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
            camera = frustum;
        }
        this.mc.mcProfiler.endStartSection(RenderGlobal.I[0x26 ^ 0x3D]);
        final BlockPos blockPos = new BlockPos(n5, n6 + entity.getEyeHeight(), n7);
        final RenderChunk renderChunk = this.viewFrustum.getRenderChunk(blockPos);
        final BlockPos blockPos2 = new BlockPos(MathHelper.floor_double(n5 / 16.0) * (0x4C ^ 0x5C), MathHelper.floor_double(n6 / 16.0) * (0x10 ^ 0x0), MathHelper.floor_double(n7 / 16.0) * (0x58 ^ 0x48));
        int displayListEntitiesDirty;
        if (!this.displayListEntitiesDirty && this.chunksToUpdate.isEmpty() && entity.posX == this.lastViewEntityX && entity.posY == this.lastViewEntityY && entity.posZ == this.lastViewEntityZ && entity.rotationPitch == this.lastViewEntityPitch && entity.rotationYaw == this.lastViewEntityYaw) {
            displayListEntitiesDirty = "".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            displayListEntitiesDirty = " ".length();
        }
        this.displayListEntitiesDirty = (displayListEntitiesDirty != 0);
        this.lastViewEntityX = entity.posX;
        this.lastViewEntityY = entity.posY;
        this.lastViewEntityZ = entity.posZ;
        this.lastViewEntityPitch = entity.rotationPitch;
        this.lastViewEntityYaw = entity.rotationYaw;
        int n8;
        if (this.debugFixedClippingHelper != null) {
            n8 = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        Lagometer.timerVisibility.start();
        if (n9 == 0 && this.displayListEntitiesDirty) {
            this.displayListEntitiesDirty = ("".length() != 0);
            this.renderInfos.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
            this.visibilityDeque.clear();
            final Deque visibilityDeque = this.visibilityDeque;
            int n10 = this.mc.renderChunksMany ? 1 : 0;
            if (renderChunk != null) {
                int n11 = "".length();
                final ContainerLocalRenderInformation containerLocalRenderInformation = new ContainerLocalRenderInformation(renderChunk, null, "".length(), null);
                final Set set_ALL_FACINGS = RenderGlobal.SET_ALL_FACINGS;
                if (set_ALL_FACINGS.size() == " ".length()) {
                    final Vector3f viewVector = this.getViewVector(entity, n);
                    set_ALL_FACINGS.remove(EnumFacing.getFacingFromVector(viewVector.x, viewVector.y, viewVector.z).getOpposite());
                }
                if (set_ALL_FACINGS.isEmpty()) {
                    n11 = " ".length();
                }
                if (n11 != 0 && !b) {
                    this.renderInfos.add(containerLocalRenderInformation);
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                else {
                    if (b && this.theWorld.getBlockState(blockPos).getBlock().isOpaqueCube()) {
                        n10 = "".length();
                    }
                    renderChunk.setFrameIndex(frameIndex);
                    visibilityDeque.add(containerLocalRenderInformation);
                    "".length();
                    if (!true) {
                        throw null;
                    }
                }
            }
            else {
                int n12;
                if (blockPos.getY() > 0) {
                    n12 = 143 + 81 - 10 + 34;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    n12 = (0xBD ^ 0xB5);
                }
                final int n13 = n12;
                int i = -this.renderDistanceChunks;
                "".length();
                if (false) {
                    throw null;
                }
                while (i <= this.renderDistanceChunks) {
                    int j = -this.renderDistanceChunks;
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                    while (j <= this.renderDistanceChunks) {
                        final RenderChunk renderChunk2 = this.viewFrustum.getRenderChunk(new BlockPos((i << (0x83 ^ 0x87)) + (0x30 ^ 0x38), n13, (j << (0x93 ^ 0x97)) + (0xB6 ^ 0xBE)));
                        if (renderChunk2 != null && camera.isBoundingBoxInFrustum(renderChunk2.boundingBox)) {
                            renderChunk2.setFrameIndex(frameIndex);
                            visibilityDeque.add(new ContainerLocalRenderInformation(renderChunk2, null, "".length(), null));
                        }
                        ++j;
                    }
                    ++i;
                }
            }
            final EnumFacing[] values = EnumFacing.VALUES;
            final int length = values.length;
            "".length();
            if (true != true) {
                throw null;
            }
            while (!visibilityDeque.isEmpty()) {
                final ContainerLocalRenderInformation containerLocalRenderInformation2 = visibilityDeque.poll();
                final RenderChunk renderChunk3 = containerLocalRenderInformation2.renderChunk;
                final EnumFacing facing = containerLocalRenderInformation2.facing;
                final BlockPos position = renderChunk3.getPosition();
                if (!renderChunk3.compiledChunk.isEmpty() || renderChunk3.isNeedsUpdate()) {
                    this.renderInfos.add(containerLocalRenderInformation2);
                }
                if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(position))) {
                    this.renderInfosEntities.add(containerLocalRenderInformation2);
                }
                if (renderChunk3.getCompiledChunk().getTileEntities().size() > 0) {
                    this.renderInfosTileEntities.add(containerLocalRenderInformation2);
                }
                int k = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (k < length) {
                    final EnumFacing enumFacing = values[k];
                    if ((n10 == 0 || !containerLocalRenderInformation2.setFacing.contains(enumFacing.getOpposite())) && (n10 == 0 || facing == null || renderChunk3.getCompiledChunk().isVisible(facing.getOpposite(), enumFacing))) {
                        final RenderChunk func_181562_a = this.func_181562_a(blockPos, renderChunk3, enumFacing);
                        if (func_181562_a != null && func_181562_a.setFrameIndex(frameIndex) && camera.isBoundingBoxInFrustum(func_181562_a.boundingBox)) {
                            final ContainerLocalRenderInformation containerLocalRenderInformation3 = new ContainerLocalRenderInformation(func_181562_a, enumFacing, containerLocalRenderInformation2.counter + " ".length(), null);
                            containerLocalRenderInformation3.setFacing.addAll(containerLocalRenderInformation2.setFacing);
                            containerLocalRenderInformation3.setFacing.add(enumFacing);
                            visibilityDeque.add(containerLocalRenderInformation3);
                        }
                    }
                    ++k;
                }
            }
        }
        if (this.debugFixTerrainFrustum) {
            this.fixTerrainFrustum(n5, n6, n7);
            this.debugFixTerrainFrustum = ("".length() != 0);
        }
        Lagometer.timerVisibility.end();
        this.renderDispatcher.clearChunkUpdates();
        final Set chunksToUpdate = this.chunksToUpdate;
        this.chunksToUpdate = Sets.newLinkedHashSet();
        final Iterator<ContainerLocalRenderInformation> iterator = (Iterator<ContainerLocalRenderInformation>)this.renderInfos.iterator();
        Lagometer.timerChunkUpdate.start();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ContainerLocalRenderInformation containerLocalRenderInformation4 = iterator.next();
            final RenderChunk renderChunk4 = containerLocalRenderInformation4.renderChunk;
            if (renderChunk4.isNeedsUpdate() || chunksToUpdate.contains(renderChunk4)) {
                this.displayListEntitiesDirty = (" ".length() != 0);
                if (this.isPositionInRenderChunk(blockPos2, containerLocalRenderInformation4.renderChunk)) {
                    if (!Config.isActing()) {
                        this.chunksToUpdateForced.add(renderChunk4);
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        this.mc.mcProfiler.startSection(RenderGlobal.I[0x19 ^ 0x5]);
                        this.renderDispatcher.updateChunkNow(renderChunk4);
                        renderChunk4.setNeedsUpdate("".length() != 0);
                        this.mc.mcProfiler.endSection();
                        "".length();
                        if (false == true) {
                            throw null;
                        }
                        continue;
                    }
                }
                else {
                    this.chunksToUpdate.add(renderChunk4);
                }
            }
        }
        Lagometer.timerChunkUpdate.end();
        this.chunksToUpdate.addAll(chunksToUpdate);
        this.mc.mcProfiler.endSection();
    }
    
    private void generateStars() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.starVBO != null) {
            this.starVBO.deleteGlBuffers();
        }
        if (this.starGLCallList >= 0) {
            GLAllocation.deleteDisplayLists(this.starGLCallList);
            this.starGLCallList = -" ".length();
        }
        if (this.vboEnabled) {
            this.starVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderStars(worldRenderer);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.starVBO.func_181722_a(worldRenderer.getByteBuffer());
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            this.starGLCallList = GLAllocation.generateDisplayLists(" ".length());
            GlStateManager.pushMatrix();
            GL11.glNewList(this.starGLCallList, 3615 + 3392 - 3582 + 1439);
            this.renderStars(worldRenderer);
            instance.draw();
            GL11.glEndList();
            GlStateManager.popMatrix();
        }
    }
    
    public String getDebugInfoRenders() {
        final int length = this.viewFrustum.renderChunks.length;
        int length2 = "".length();
        final Iterator<ContainerLocalRenderInformation> iterator = (Iterator<ContainerLocalRenderInformation>)this.renderInfos.iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final CompiledChunk compiledChunk = iterator.next().renderChunk.compiledChunk;
            if (compiledChunk != CompiledChunk.DUMMY && !compiledChunk.isEmpty()) {
                ++length2;
            }
        }
        final String s = RenderGlobal.I[0x19 ^ 0x9];
        final Object[] array = new Object[0x22 ^ 0x27];
        array["".length()] = length2;
        array[" ".length()] = length;
        final int length3 = "  ".length();
        String s2;
        if (this.mc.renderChunksMany) {
            s2 = RenderGlobal.I[0x41 ^ 0x50];
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            s2 = RenderGlobal.I[0x97 ^ 0x85];
        }
        array[length3] = s2;
        array["   ".length()] = this.renderDistanceChunks;
        array[0x1C ^ 0x18] = this.renderDispatcher.getDebugInfo();
        return String.format(s, array);
    }
    
    @Override
    public void playRecord(final String s, final BlockPos blockPos) {
        final ISound sound = this.mapSoundPositions.get(blockPos);
        if (sound != null) {
            this.mc.getSoundHandler().stopSound(sound);
            this.mapSoundPositions.remove(blockPos);
        }
        if (s != null) {
            final ItemRecord record = ItemRecord.getRecord(s);
            if (record != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(record.getRecordNameLocal());
            }
            ResourceLocation resourceLocation = null;
            if (Reflector.ForgeItemRecord_getRecordResource.exists() && record != null) {
                final ItemRecord itemRecord = record;
                final ReflectorMethod forgeItemRecord_getRecordResource = Reflector.ForgeItemRecord_getRecordResource;
                final Object[] array = new Object[" ".length()];
                array["".length()] = s;
                resourceLocation = (ResourceLocation)Reflector.call(itemRecord, forgeItemRecord_getRecordResource, array);
            }
            if (resourceLocation == null) {
                resourceLocation = new ResourceLocation(s);
            }
            final PositionedSoundRecord create = PositionedSoundRecord.create(resourceLocation, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.mapSoundPositions.put(blockPos, create);
            this.mc.getSoundHandler().playSound(create);
        }
    }
    
    @Override
    public void onEntityRemoved(final Entity entity) {
    }
    
    public String getDebugInfoEntities() {
        return RenderGlobal.I[0xD7 ^ 0xC4] + this.countEntitiesRendered + RenderGlobal.I[0x6 ^ 0x12] + this.countEntitiesTotal + RenderGlobal.I[0x19 ^ 0xC] + this.countEntitiesHidden + RenderGlobal.I[0x56 ^ 0x40] + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + RenderGlobal.I[0x70 ^ 0x67] + Config.getVersion();
    }
    
    public int getCountRenderers() {
        return this.viewFrustum.renderChunks.length;
    }
    
    public static void func_181561_a(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin("   ".length(), DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(" ".length(), DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.updateDestroyBlockIcons();
    }
    
    private void fixTerrainFrustum(final double field_181059_a, final double field_181060_b, final double field_181061_c) {
        this.debugFixedClippingHelper = new ClippingHelperImpl();
        ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
        final Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
        matrix4f.transpose();
        final Matrix4f matrix4f2 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
        matrix4f2.transpose();
        final Matrix4f matrix4f3 = new Matrix4f();
        Matrix4f.mul((org.lwjgl.util.vector.Matrix4f)matrix4f2, (org.lwjgl.util.vector.Matrix4f)matrix4f, (org.lwjgl.util.vector.Matrix4f)matrix4f3);
        matrix4f3.invert();
        this.debugTerrainFrustumPosition.field_181059_a = field_181059_a;
        this.debugTerrainFrustumPosition.field_181060_b = field_181060_b;
        this.debugTerrainFrustumPosition.field_181061_c = field_181061_c;
        this.debugTerrainMatrix["".length()] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[" ".length()] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix["  ".length()] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix["   ".length()] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[0x85 ^ 0x81] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[0x7F ^ 0x7A] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[0xB ^ 0xD] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[0x3E ^ 0x39] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < (0x66 ^ 0x6E)) {
            Matrix4f.transform((org.lwjgl.util.vector.Matrix4f)matrix4f3, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
            final Vector4f vector4f = this.debugTerrainMatrix[i];
            vector4f.x /= this.debugTerrainMatrix[i].w;
            final Vector4f vector4f2 = this.debugTerrainMatrix[i];
            vector4f2.y /= this.debugTerrainMatrix[i].w;
            final Vector4f vector4f3 = this.debugTerrainMatrix[i];
            vector4f3.z /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].w = 1.0f;
            ++i;
        }
    }
    
    static final class RenderGlobal$2
    {
        private static final String[] I;
        static final int[] field_178037_a;
        private static final String __OBFID;
        
        static {
            I();
            __OBFID = RenderGlobal$2.I["".length()];
            field_178037_a = new int[VertexFormatElement.EnumUsage.values().length];
            try {
                RenderGlobal$2.field_178037_a[VertexFormatElement.EnumUsage.POSITION.ordinal()] = " ".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                RenderGlobal$2.field_178037_a[VertexFormatElement.EnumUsage.UV.ordinal()] = "  ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                RenderGlobal$2.field_178037_a[VertexFormatElement.EnumUsage.COLOR.ordinal()] = "   ".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("-+;hu^WVmv[", "ngdXE");
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
                if (0 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    class ContainerLocalRenderInformation
    {
        final RenderChunk renderChunk;
        final int counter;
        private static final String[] I;
        private static final String __OBFID;
        final EnumFacing facing;
        final Set setFacing;
        final RenderGlobal this$0;
        
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
                if (2 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private ContainerLocalRenderInformation(final RenderGlobal this$0, final RenderChunk renderChunk, final EnumFacing facing, final int counter) {
            this.this$0 = this$0;
            this.setFacing = EnumSet.noneOf(EnumFacing.class);
            this.renderChunk = renderChunk;
            this.facing = facing;
            this.counter = counter;
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("0\u00028I]C~UL^G", "sNgym");
        }
        
        ContainerLocalRenderInformation(final RenderGlobal renderGlobal, final RenderChunk renderChunk, final EnumFacing enumFacing, final int n, final Object o) {
            this(renderGlobal, renderChunk, enumFacing, n);
        }
        
        static {
            I();
            __OBFID = ContainerLocalRenderInformation.I["".length()];
        }
    }
}
