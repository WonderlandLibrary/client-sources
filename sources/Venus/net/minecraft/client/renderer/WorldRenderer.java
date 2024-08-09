/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.MatrixApplyingVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.venusfr;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.ClientChunkProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderTimeManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Tuple3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.IWeatherParticleRenderHandler;
import net.minecraftforge.client.IWeatherRenderHandler;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lagometer;
import net.optifine.SmartAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.ChunkVisibility;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderStateManager;
import net.optifine.render.RenderUtils;
import net.optifine.render.VboRegion;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.shaders.ShadowUtils;
import net.optifine.util.BiomeUtils;
import net.optifine.util.ChunkUtils;
import net.optifine.util.MathUtils;
import net.optifine.util.PairInt;
import net.optifine.util.RenderChunkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class WorldRenderer
implements IResourceManagerReloadListener,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation FORCEFIELD_TEXTURES = new ResourceLocation("textures/misc/forcefield.png");
    private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
    public static final Direction[] FACINGS = Direction.values();
    private final Minecraft mc;
    private final TextureManager textureManager;
    private final EntityRendererManager renderManager;
    private final RenderTypeBuffers renderTypeTextures;
    private ClientWorld world;
    private Set<ChunkRenderDispatcher.ChunkRender> chunksToUpdate = new ObjectLinkedOpenHashSet<ChunkRenderDispatcher.ChunkRender>();
    private ObjectList<LocalRenderInformationContainer> renderInfos = new ObjectArrayList<LocalRenderInformationContainer>(69696);
    private final Set<TileEntity> setTileEntities = Sets.newHashSet();
    private ViewFrustum viewFrustum;
    private final VertexFormat skyVertexFormat = DefaultVertexFormats.POSITION;
    @Nullable
    private VertexBuffer starVBO;
    @Nullable
    private VertexBuffer skyVBO;
    @Nullable
    private VertexBuffer sky2VBO;
    private boolean cloudsNeedUpdate = true;
    @Nullable
    private VertexBuffer cloudsVBO;
    private final RenderTimeManager renderTimeManager = new RenderTimeManager(100);
    private int ticks;
    private final Int2ObjectMap<DestroyBlockProgress> damagedBlocks = new Int2ObjectOpenHashMap<DestroyBlockProgress>();
    private final Long2ObjectMap<SortedSet<DestroyBlockProgress>> damageProgress = new Long2ObjectOpenHashMap<SortedSet<DestroyBlockProgress>>();
    private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
    @Nullable
    private Framebuffer entityOutlineFramebuffer;
    @Nullable
    private ShaderGroup entityOutlineShader;
    @Nullable
    private Framebuffer field_239222_F_;
    @Nullable
    private Framebuffer field_239223_G_;
    @Nullable
    private Framebuffer field_239224_H_;
    @Nullable
    private Framebuffer field_239225_I_;
    @Nullable
    private Framebuffer field_239226_J_;
    @Nullable
    private ShaderGroup field_239227_K_;
    private double frustumUpdatePosX = Double.MIN_VALUE;
    private double frustumUpdatePosY = Double.MIN_VALUE;
    private double frustumUpdatePosZ = Double.MIN_VALUE;
    private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
    private double lastViewEntityX = Double.MIN_VALUE;
    private double lastViewEntityY = Double.MIN_VALUE;
    private double lastViewEntityZ = Double.MIN_VALUE;
    private double lastViewEntityPitch = Double.MIN_VALUE;
    private double lastViewEntityYaw = Double.MIN_VALUE;
    private int cloudsCheckX = Integer.MIN_VALUE;
    private int cloudsCheckY = Integer.MIN_VALUE;
    private int cloudsCheckZ = Integer.MIN_VALUE;
    private Vector3d cloudsCheckColor = Vector3d.ZERO;
    private CloudOption cloudOption;
    private ChunkRenderDispatcher renderDispatcher;
    private final VertexFormat blockVertexFormat = DefaultVertexFormats.BLOCK;
    private int renderDistanceChunks = -1;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    private boolean debugFixTerrainFrustum;
    @Nullable
    private ClippingHelper debugFixedClippingHelper;
    private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
    private final Tuple3d debugTerrainFrustumPosition = new Tuple3d(0.0, 0.0, 0.0);
    private double prevRenderSortX;
    private double prevRenderSortY;
    private double prevRenderSortZ;
    private boolean displayListEntitiesDirty = true;
    private int frameId;
    private int rainSoundTime;
    private final float[] rainSizeX = new float[1024];
    private final float[] rainSizeZ = new float[1024];
    public Entity renderedEntity;
    public Set chunksToResortTransparency = new LinkedHashSet();
    public Set chunksToUpdateForced = new LinkedHashSet();
    private Set<ChunkRenderDispatcher.ChunkRender> chunksToUpdatePrev = new ObjectLinkedOpenHashSet<ChunkRenderDispatcher.ChunkRender>();
    private Deque visibilityDeque = new ArrayDeque();
    private List<LocalRenderInformationContainer> renderInfosEntities = new ArrayList<LocalRenderInformationContainer>(1024);
    private List<LocalRenderInformationContainer> renderInfosTileEntities = new ArrayList<LocalRenderInformationContainer>(1024);
    private ObjectList renderInfosNormal = new ObjectArrayList(1024);
    private List renderInfosEntitiesNormal = new ArrayList(1024);
    private List renderInfosTileEntitiesNormal = new ArrayList(1024);
    private ObjectList renderInfosShadow = new ObjectArrayList(1024);
    private List renderInfosEntitiesShadow = new ArrayList(1024);
    private List renderInfosTileEntitiesShadow = new ArrayList(1024);
    private int renderDistance = 0;
    private int renderDistanceSq = 0;
    private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet<Direction>(Arrays.asList(Direction.VALUES)));
    private int countTileEntitiesRendered;
    private int countLoadedChunksPrev = 0;
    private RenderEnv renderEnv = new RenderEnv(Blocks.AIR.getDefaultState(), new BlockPos(0, 0, 0));
    public boolean renderOverlayDamaged = false;
    public boolean renderOverlayEyes = false;
    private boolean firstWorldLoad = false;
    private static int renderEntitiesCounter = 0;
    public int loadVisibleChunksCounter = -1;
    public static final int loadVisibleChunksMessageId = 201435902;
    private static boolean ambientOcclusion = false;
    private Map<String, List<Entity>> mapEntityLists = new HashMap<String, List<Entity>>();
    private Map<RenderType, Map> mapRegionLayers = new LinkedHashMap<RenderType, Map>();
    public static ClippingHelper frustum;
    private WorldEvent render = new WorldEvent(new MatrixStack(), 1.0f);

    public WorldRenderer(Minecraft minecraft, RenderTypeBuffers renderTypeBuffers) {
        this.mc = minecraft;
        this.renderManager = minecraft.getRenderManager();
        this.renderTypeTextures = renderTypeBuffers;
        this.textureManager = minecraft.getTextureManager();
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                float f = j - 16;
                float f2 = i - 16;
                float f3 = MathHelper.sqrt(f * f + f2 * f2);
                this.rainSizeX[i << 5 | j] = -f2 / f3;
                this.rainSizeZ[i << 5 | j] = f / f3;
            }
        }
        this.generateStars();
        this.generateSky();
        this.generateSky2();
    }

    private void renderRainSnow(LightTexture lightTexture, float f, double d, double d2, double d3) {
        IWeatherRenderHandler iWeatherRenderHandler;
        if (Reflector.ForgeDimensionRenderInfo_getWeatherRenderHandler.exists() && (iWeatherRenderHandler = (IWeatherRenderHandler)Reflector.call(this.world.func_239132_a_(), Reflector.ForgeDimensionRenderInfo_getWeatherRenderHandler, new Object[0])) != null) {
            iWeatherRenderHandler.render(this.ticks, f, this.world, this.mc, lightTexture, d, d2, d3);
            return;
        }
        float f2 = this.mc.world.getRainStrength(f);
        if (!(f2 <= 0.0f)) {
            if (Config.isRainOff()) {
                return;
            }
            lightTexture.enableLightmap();
            ClientWorld clientWorld = this.mc.world;
            int n = MathHelper.floor(d);
            int n2 = MathHelper.floor(d2);
            int n3 = MathHelper.floor(d3);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.enableAlphaTest();
            RenderSystem.disableCull();
            RenderSystem.normal3f(0.0f, 1.0f, 0.0f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableDepthTest();
            int n4 = 5;
            if (Config.isRainFancy()) {
                n4 = 10;
            }
            RenderSystem.depthMask(Minecraft.isFabulousGraphicsEnabled());
            int n5 = -1;
            float f3 = (float)this.ticks + f;
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = n3 - n4; i <= n3 + n4; ++i) {
                for (int j = n - n4; j <= n + n4; ++j) {
                    float f4;
                    float f5;
                    int n6 = (i - n3 + 16) * 32 + j - n + 16;
                    double d4 = (double)this.rainSizeX[n6] * 0.5;
                    double d5 = (double)this.rainSizeZ[n6] * 0.5;
                    mutable.setPos(j, 0, i);
                    Biome biome = clientWorld.getBiome(mutable);
                    if (biome.getPrecipitation() == Biome.RainType.NONE) continue;
                    int n7 = clientWorld.getHeight(Heightmap.Type.MOTION_BLOCKING, mutable).getY();
                    int n8 = n2 - n4;
                    int n9 = n2 + n4;
                    if (n8 < n7) {
                        n8 = n7;
                    }
                    if (n9 < n7) {
                        n9 = n7;
                    }
                    int n10 = n7;
                    if (n7 < n2) {
                        n10 = n2;
                    }
                    if (n8 == n9) continue;
                    Random random2 = new Random(j * j * 3121 + j * 45238971 ^ i * i * 418711 + i * 13761);
                    mutable.setPos(j, n8, i);
                    float f6 = biome.getTemperature(mutable);
                    if (f6 >= 0.15f) {
                        if (n5 != 0) {
                            if (n5 >= 0) {
                                tessellator.draw();
                            }
                            n5 = 0;
                            this.mc.getTextureManager().bindTexture(RAIN_TEXTURES);
                            bufferBuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }
                        int n11 = this.ticks + j * j * 3121 + j * 45238971 + i * i * 418711 + i * 13761 & 0x1F;
                        f5 = -((float)n11 + f) / 32.0f * (3.0f + random2.nextFloat());
                        double d6 = (double)((float)j + 0.5f) - d;
                        double d7 = (double)((float)i + 0.5f) - d3;
                        float f7 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float)n4;
                        f4 = ((1.0f - f7 * f7) * 0.5f + 0.5f) * f2;
                        mutable.setPos(j, n10, i);
                        int n12 = WorldRenderer.getCombinedLight(clientWorld, mutable);
                        bufferBuilder.pos((double)j - d - d4 + 0.5, (double)n9 - d2, (double)i - d3 - d5 + 0.5).tex(0.0f, (float)n8 * 0.25f + f5).color(1.0f, 1.0f, 1.0f, f4).lightmap(n12).endVertex();
                        bufferBuilder.pos((double)j - d + d4 + 0.5, (double)n9 - d2, (double)i - d3 + d5 + 0.5).tex(1.0f, (float)n8 * 0.25f + f5).color(1.0f, 1.0f, 1.0f, f4).lightmap(n12).endVertex();
                        bufferBuilder.pos((double)j - d + d4 + 0.5, (double)n8 - d2, (double)i - d3 + d5 + 0.5).tex(1.0f, (float)n9 * 0.25f + f5).color(1.0f, 1.0f, 1.0f, f4).lightmap(n12).endVertex();
                        bufferBuilder.pos((double)j - d - d4 + 0.5, (double)n8 - d2, (double)i - d3 - d5 + 0.5).tex(0.0f, (float)n9 * 0.25f + f5).color(1.0f, 1.0f, 1.0f, f4).lightmap(n12).endVertex();
                        continue;
                    }
                    if (n5 != 1) {
                        if (n5 >= 0) {
                            tessellator.draw();
                        }
                        n5 = 1;
                        this.mc.getTextureManager().bindTexture(SNOW_TEXTURES);
                        bufferBuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                    }
                    float f8 = -((float)(this.ticks & 0x1FF) + f) / 512.0f;
                    f5 = (float)(random2.nextDouble() + (double)f3 * 0.01 * (double)((float)random2.nextGaussian()));
                    float f9 = (float)(random2.nextDouble() + (double)(f3 * (float)random2.nextGaussian()) * 0.001);
                    double d8 = (double)((float)j + 0.5f) - d;
                    double d9 = (double)((float)i + 0.5f) - d3;
                    f4 = MathHelper.sqrt(d8 * d8 + d9 * d9) / (float)n4;
                    float f10 = ((1.0f - f4 * f4) * 0.3f + 0.5f) * f2;
                    mutable.setPos(j, n10, i);
                    int n13 = WorldRenderer.getCombinedLight(clientWorld, mutable);
                    int n14 = n13 >> 16 & 0xFFFF;
                    int n15 = (n13 & 0xFFFF) * 3;
                    int n16 = (n14 * 3 + 240) / 4;
                    int n17 = (n15 * 3 + 240) / 4;
                    bufferBuilder.pos((double)j - d - d4 + 0.5, (double)n9 - d2, (double)i - d3 - d5 + 0.5).tex(0.0f + f5, (float)n8 * 0.25f + f8 + f9).color(1.0f, 1.0f, 1.0f, f10).lightmap(n17, n16).endVertex();
                    bufferBuilder.pos((double)j - d + d4 + 0.5, (double)n9 - d2, (double)i - d3 + d5 + 0.5).tex(1.0f + f5, (float)n8 * 0.25f + f8 + f9).color(1.0f, 1.0f, 1.0f, f10).lightmap(n17, n16).endVertex();
                    bufferBuilder.pos((double)j - d + d4 + 0.5, (double)n8 - d2, (double)i - d3 + d5 + 0.5).tex(1.0f + f5, (float)n9 * 0.25f + f8 + f9).color(1.0f, 1.0f, 1.0f, f10).lightmap(n17, n16).endVertex();
                    bufferBuilder.pos((double)j - d - d4 + 0.5, (double)n8 - d2, (double)i - d3 - d5 + 0.5).tex(0.0f + f5, (float)n9 * 0.25f + f8 + f9).color(1.0f, 1.0f, 1.0f, f10).lightmap(n17, n16).endVertex();
                }
            }
            if (n5 >= 0) {
                tessellator.draw();
            }
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.disableAlphaTest();
            lightTexture.disableLightmap();
        }
    }

    public void addRainParticles(ActiveRenderInfo activeRenderInfo) {
        IWeatherParticleRenderHandler iWeatherParticleRenderHandler;
        if (Reflector.ForgeDimensionRenderInfo_getWeatherParticleRenderHandler.exists() && (iWeatherParticleRenderHandler = (IWeatherParticleRenderHandler)Reflector.call(this.world.func_239132_a_(), Reflector.ForgeDimensionRenderInfo_getWeatherParticleRenderHandler, new Object[0])) != null) {
            iWeatherParticleRenderHandler.render(this.ticks, this.world, this.mc, activeRenderInfo);
            return;
        }
        float f = this.mc.world.getRainStrength(1.0f) / (Minecraft.isFancyGraphicsEnabled() ? 1.0f : 2.0f);
        if (!Config.isRainFancy()) {
            f /= 2.0f;
        }
        if (!(f <= 0.0f) && Config.isRainSplash()) {
            Random random2 = new Random((long)this.ticks * 312987231L);
            ClientWorld clientWorld = this.mc.world;
            BlockPos blockPos = new BlockPos(activeRenderInfo.getProjectedView());
            Vector3i vector3i = null;
            int n = (int)(100.0f * f * f) / (this.mc.gameSettings.particles == ParticleStatus.DECREASED ? 2 : 1);
            for (int i = 0; i < n; ++i) {
                int n2 = random2.nextInt(21) - 10;
                int n3 = random2.nextInt(21) - 10;
                BlockPos blockPos2 = clientWorld.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos.add(n2, 0, n3)).down();
                Biome biome = clientWorld.getBiome(blockPos2);
                if (blockPos2.getY() <= 0 || blockPos2.getY() > blockPos.getY() + 10 || blockPos2.getY() < blockPos.getY() - 10 || biome.getPrecipitation() != Biome.RainType.RAIN || !(biome.getTemperature(blockPos2) >= 0.15f)) continue;
                vector3i = blockPos2;
                if (this.mc.gameSettings.particles == ParticleStatus.MINIMAL) break;
                double d = random2.nextDouble();
                double d2 = random2.nextDouble();
                BlockState blockState = clientWorld.getBlockState(blockPos2);
                FluidState fluidState = clientWorld.getFluidState(blockPos2);
                VoxelShape voxelShape = blockState.getCollisionShape(clientWorld, blockPos2);
                double d3 = voxelShape.max(Direction.Axis.Y, d, d2);
                double d4 = fluidState.getActualHeight(clientWorld, blockPos2);
                double d5 = Math.max(d3, d4);
                BasicParticleType basicParticleType = !fluidState.isTagged(FluidTags.LAVA) && !blockState.isIn(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLit(blockState) ? ParticleTypes.RAIN : ParticleTypes.SMOKE;
                this.mc.world.addParticle(basicParticleType, (double)blockPos2.getX() + d, (double)blockPos2.getY() + d5, (double)blockPos2.getZ() + d2, 0.0, 0.0, 0.0);
            }
            if (vector3i != null && random2.nextInt(3) < this.rainSoundTime++) {
                this.rainSoundTime = 0;
                if (vector3i.getY() > blockPos.getY() + 1 && clientWorld.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > MathHelper.floor(blockPos.getY())) {
                    this.mc.world.playSound((BlockPos)vector3i, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1f, 0.5f, true);
                } else {
                    this.mc.world.playSound((BlockPos)vector3i, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2f, 1.0f, true);
                }
            }
        }
    }

    @Override
    public void close() {
        if (this.entityOutlineShader != null) {
            this.entityOutlineShader.close();
        }
        if (this.field_239227_K_ != null) {
            this.field_239227_K_.close();
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.textureManager.bindTexture(FORCEFIELD_TEXTURES);
        RenderSystem.texParameter(3553, 10242, 10497);
        RenderSystem.texParameter(3553, 10243, 10497);
        RenderSystem.bindTexture(0);
        this.makeEntityOutlineShader();
        if (Minecraft.isFabulousGraphicsEnabled()) {
            this.func_239233_v_();
        }
    }

    public void makeEntityOutlineShader() {
        if (this.entityOutlineShader != null) {
            this.entityOutlineShader.close();
        }
        ResourceLocation resourceLocation = new ResourceLocation("shaders/post/entity_outline.json");
        try {
            this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourceLocation);
            this.entityOutlineShader.createBindFramebuffers(this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
            this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
        } catch (IOException iOException) {
            LOGGER.warn("Failed to load shader: {}", (Object)resourceLocation, (Object)iOException);
            this.entityOutlineShader = null;
            this.entityOutlineFramebuffer = null;
        } catch (JsonSyntaxException jsonSyntaxException) {
            LOGGER.warn("Failed to parse shader: {}", (Object)resourceLocation, (Object)jsonSyntaxException);
            this.entityOutlineShader = null;
            this.entityOutlineFramebuffer = null;
        }
    }

    private void func_239233_v_() {
        this.func_239234_w_();
        ResourceLocation resourceLocation = new ResourceLocation("shaders/post/transparency.json");
        try {
            ShaderGroup shaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourceLocation);
            shaderGroup.createBindFramebuffers(this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
            Framebuffer framebuffer = shaderGroup.getFramebufferRaw("translucent");
            Framebuffer framebuffer2 = shaderGroup.getFramebufferRaw("itemEntity");
            Framebuffer framebuffer3 = shaderGroup.getFramebufferRaw("particles");
            Framebuffer framebuffer4 = shaderGroup.getFramebufferRaw("weather");
            Framebuffer framebuffer5 = shaderGroup.getFramebufferRaw("clouds");
            this.field_239227_K_ = shaderGroup;
            this.field_239222_F_ = framebuffer;
            this.field_239223_G_ = framebuffer2;
            this.field_239224_H_ = framebuffer3;
            this.field_239225_I_ = framebuffer4;
            this.field_239226_J_ = framebuffer5;
        } catch (Exception exception) {
            String string = exception instanceof JsonSyntaxException ? "parse" : "load";
            String string2 = "Failed to " + string + " shader: " + resourceLocation;
            ShaderException shaderException = new ShaderException(string2, exception);
            if (this.mc.getResourcePackList().func_232621_d_().size() > 1) {
                StringTextComponent stringTextComponent;
                try {
                    stringTextComponent = new StringTextComponent(this.mc.getResourceManager().getResource(resourceLocation).getPackName());
                } catch (IOException iOException) {
                    stringTextComponent = null;
                }
                this.mc.gameSettings.graphicFanciness = GraphicsFanciness.FANCY;
                this.mc.throwResourcePackLoadError(shaderException, stringTextComponent);
            }
            CrashReport crashReport = this.mc.addGraphicsAndWorldToCrashReport(new CrashReport(string2, shaderException));
            this.mc.gameSettings.graphicFanciness = GraphicsFanciness.FANCY;
            this.mc.gameSettings.saveOptions();
            LOGGER.fatal(string2, (Throwable)shaderException);
            this.mc.freeMemory();
            Minecraft.displayCrashReport(crashReport);
        }
    }

    private void func_239234_w_() {
        if (this.field_239227_K_ != null) {
            this.field_239227_K_.close();
            this.field_239222_F_.deleteFramebuffer();
            this.field_239223_G_.deleteFramebuffer();
            this.field_239224_H_.deleteFramebuffer();
            this.field_239225_I_.deleteFramebuffer();
            this.field_239226_J_.deleteFramebuffer();
            this.field_239227_K_ = null;
            this.field_239222_F_ = null;
            this.field_239223_G_ = null;
            this.field_239224_H_ = null;
            this.field_239225_I_ = null;
            this.field_239226_J_ = null;
        }
    }

    public void renderEntityOutlineFramebuffer() {
        if (this.isRenderEntityOutlines()) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight(), true);
            RenderSystem.disableBlend();
        }
    }

    public boolean isRenderEntityOutlines() {
        if (!Config.isShaders() && !Config.isAntialiasing()) {
            return this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.player != null;
        }
        return true;
    }

    private void generateSky2() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (this.sky2VBO != null) {
            this.sky2VBO.close();
        }
        this.sky2VBO = new VertexBuffer(this.skyVertexFormat);
        this.renderSky(bufferBuilder, -16.0f, false);
        bufferBuilder.finishDrawing();
        this.sky2VBO.upload(bufferBuilder);
    }

    private void generateSky() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (this.skyVBO != null) {
            this.skyVBO.close();
        }
        this.skyVBO = new VertexBuffer(this.skyVertexFormat);
        this.renderSky(bufferBuilder, 16.0f, true);
        bufferBuilder.finishDrawing();
        this.skyVBO.upload(bufferBuilder);
    }

    private void renderSky(BufferBuilder bufferBuilder, float f, boolean bl) {
        int n = 64;
        int n2 = 6;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        int n3 = (this.renderDistance / 64 + 1) * 64 + 64;
        for (int i = -n3; i <= n3; i += 64) {
            for (int j = -n3; j <= n3; j += 64) {
                float f2 = i;
                float f3 = i + 64;
                if (bl) {
                    f3 = i;
                    f2 = i + 64;
                }
                bufferBuilder.pos(f2, f, j).endVertex();
                bufferBuilder.pos(f3, f, j).endVertex();
                bufferBuilder.pos(f3, f, j + 64).endVertex();
                bufferBuilder.pos(f2, f, j + 64).endVertex();
            }
        }
    }

    private void generateStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (this.starVBO != null) {
            this.starVBO.close();
        }
        this.starVBO = new VertexBuffer(this.skyVertexFormat);
        this.renderStars(bufferBuilder);
        bufferBuilder.finishDrawing();
        this.starVBO.upload(bufferBuilder);
    }

    private void renderStars(BufferBuilder bufferBuilder) {
        Random random2 = new Random(10842L);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        for (int i = 0; i < 1500; ++i) {
            double d = random2.nextFloat() * 2.0f - 1.0f;
            double d2 = random2.nextFloat() * 2.0f - 1.0f;
            double d3 = random2.nextFloat() * 2.0f - 1.0f;
            double d4 = 0.15f + random2.nextFloat() * 0.1f;
            double d5 = d * d + d2 * d2 + d3 * d3;
            if (!(d5 < 1.0) || !(d5 > 0.01)) continue;
            d5 = 1.0 / Math.sqrt(d5);
            double d6 = (d *= d5) * 100.0;
            double d7 = (d2 *= d5) * 100.0;
            double d8 = (d3 *= d5) * 100.0;
            double d9 = Math.atan2(d, d3);
            double d10 = Math.sin(d9);
            double d11 = Math.cos(d9);
            double d12 = Math.atan2(Math.sqrt(d * d + d3 * d3), d2);
            double d13 = Math.sin(d12);
            double d14 = Math.cos(d12);
            double d15 = random2.nextDouble() * Math.PI * 2.0;
            double d16 = Math.sin(d15);
            double d17 = Math.cos(d15);
            for (int j = 0; j < 4; ++j) {
                double d18 = 0.0;
                double d19 = (double)((j & 2) - 1) * d4;
                double d20 = (double)((j + 1 & 2) - 1) * d4;
                double d21 = 0.0;
                double d22 = d19 * d17 - d20 * d16;
                double d23 = d20 * d17 + d19 * d16;
                double d24 = d22 * d13 + 0.0 * d14;
                double d25 = 0.0 * d13 - d22 * d14;
                double d26 = d25 * d10 - d23 * d11;
                double d27 = d23 * d10 + d25 * d11;
                bufferBuilder.pos(d6 + d26, d7 + d24, d8 + d27).endVertex();
            }
        }
    }

    public void setWorldAndLoadRenderers(@Nullable ClientWorld clientWorld) {
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.renderManager.setWorld(clientWorld);
        this.world = clientWorld;
        if (Config.isDynamicLights()) {
            DynamicLights.clear();
        }
        ChunkVisibility.reset();
        this.renderEnv.reset(null, null);
        BiomeUtils.onWorldChanged(this.world);
        Shaders.checkWorldChanged(this.world);
        if (clientWorld != null) {
            this.loadRenderers();
        } else {
            this.chunksToUpdate.clear();
            this.chunksToUpdatePrev.clear();
            this.clearRenderInfos();
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
                this.viewFrustum = null;
            }
            if (this.renderDispatcher != null) {
                this.renderDispatcher.stopWorkerThreads();
            }
            this.renderDispatcher = null;
            this.setTileEntities.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadRenderers() {
        if (this.world != null) {
            if (Minecraft.isFabulousGraphicsEnabled()) {
                this.func_239233_v_();
            } else {
                this.func_239234_w_();
            }
            this.world.clearColorCaches();
            if (this.renderDispatcher == null) {
                this.renderDispatcher = new ChunkRenderDispatcher(this.world, this, Util.getServerExecutor(), this.mc.isJava64bit(), this.renderTypeTextures.getFixedBuilder());
            } else {
                this.renderDispatcher.setWorld(this.world);
            }
            this.displayListEntitiesDirty = true;
            this.cloudsNeedUpdate = true;
            RenderTypeLookup.setFancyGraphics(Config.isTreesFancy());
            BlockModelRenderer.updateAoLightValue();
            if (Config.isDynamicLights()) {
                DynamicLights.clear();
            }
            SmartAnimations.update();
            ambientOcclusion = Minecraft.isAmbientOcclusionEnabled();
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            this.renderDistance = this.renderDistanceChunks * 16;
            this.renderDistanceSq = this.renderDistance * this.renderDistance;
            this.generateStars();
            this.generateSky();
            this.generateSky2();
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            Object object = this.setTileEntities;
            synchronized (object) {
                this.setTileEntities.clear();
            }
            this.viewFrustum = new ViewFrustum(this.renderDispatcher, this.world, this.mc.gameSettings.renderDistanceChunks, this);
            if (this.world != null && (object = this.mc.getRenderViewEntity()) != null) {
                this.viewFrustum.updateChunkPositions(((Entity)object).getPosX(), ((Entity)object).getPosZ());
            }
        }
        if (this.mc.player == null) {
            this.firstWorldLoad = true;
        }
    }

    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }

    public void createBindEntityOutlineFbs(int n, int n2) {
        this.setDisplayListEntitiesDirty();
        if (this.entityOutlineShader != null) {
            this.entityOutlineShader.createBindFramebuffers(n, n2);
        }
        if (this.field_239227_K_ != null) {
            this.field_239227_K_.createBindFramebuffers(n, n2);
        }
    }

    public String getDebugInfoRenders() {
        int n = this.viewFrustum.renderChunks.length;
        int n2 = this.getRenderedChunks();
        return String.format("C: %d/%d %sD: %d, %s", n2, n, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher == null ? "null" : this.renderDispatcher.getDebugInfo());
    }

    protected int getRenderedChunks() {
        int n = 0;
        for (LocalRenderInformationContainer localRenderInformationContainer : this.renderInfos) {
            if (localRenderInformationContainer.renderChunk.getCompiledChunk().isEmpty()) continue;
            ++n;
        }
        return n;
    }

    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.world.getCountLoadedEntities() + ", B: " + this.countEntitiesHidden + ", " + Config.getVersionDebug();
    }

    public void setupTerrain(ActiveRenderInfo activeRenderInfo, ClippingHelper clippingHelper, boolean bl, int n, boolean bl2) {
        boolean bl3;
        Object object2;
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
            this.loadRenderers();
        }
        this.world.getProfiler().startSection("camera");
        double d = this.mc.player.getPosX() - this.frustumUpdatePosX;
        double d2 = this.mc.player.getPosY() - this.frustumUpdatePosY;
        double d3 = this.mc.player.getPosZ() - this.frustumUpdatePosZ;
        if (this.frustumUpdatePosChunkX != this.mc.player.chunkCoordX || this.frustumUpdatePosChunkY != this.mc.player.chunkCoordY || this.frustumUpdatePosChunkZ != this.mc.player.chunkCoordZ || d * d + d2 * d2 + d3 * d3 > 16.0) {
            this.frustumUpdatePosX = this.mc.player.getPosX();
            this.frustumUpdatePosY = this.mc.player.getPosY();
            this.frustumUpdatePosZ = this.mc.player.getPosZ();
            this.frustumUpdatePosChunkX = this.mc.player.chunkCoordX;
            this.frustumUpdatePosChunkY = this.mc.player.chunkCoordY;
            this.frustumUpdatePosChunkZ = this.mc.player.chunkCoordZ;
            this.viewFrustum.updateChunkPositions(this.mc.player.getPosX(), this.mc.player.getPosZ());
        }
        if (Config.isDynamicLights()) {
            DynamicLights.update(this);
        }
        this.renderDispatcher.setRenderPosition(vector3d);
        this.world.getProfiler().endStartSection("cull");
        this.mc.getProfiler().endStartSection("culling");
        BlockPos blockPos = activeRenderInfo.getBlockPos();
        ChunkRenderDispatcher.ChunkRender chunkRender = this.viewFrustum.getRenderChunk(blockPos);
        int n2 = 16;
        BlockPos blockPos2 = new BlockPos(MathHelper.floor(vector3d.x / 16.0) * 16, MathHelper.floor(vector3d.y / 16.0) * 16, MathHelper.floor(vector3d.z / 16.0) * 16);
        float f = activeRenderInfo.getPitch();
        float f2 = activeRenderInfo.getYaw();
        this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || vector3d.x != this.lastViewEntityX || vector3d.y != this.lastViewEntityY || vector3d.z != this.lastViewEntityZ || (double)f != this.lastViewEntityPitch || (double)f2 != this.lastViewEntityYaw;
        this.lastViewEntityX = vector3d.x;
        this.lastViewEntityY = vector3d.y;
        this.lastViewEntityZ = vector3d.z;
        this.lastViewEntityPitch = f;
        this.lastViewEntityYaw = f2;
        this.mc.getProfiler().endStartSection("update");
        Lagometer.timerVisibility.start();
        int n3 = this.getCountLoadedChunks();
        if (n3 != this.countLoadedChunksPrev) {
            this.countLoadedChunksPrev = n3;
            this.displayListEntitiesDirty = true;
        }
        Entity entity2 = activeRenderInfo.getRenderViewEntity();
        int n4 = 256;
        if (!ChunkVisibility.isFinished()) {
            this.displayListEntitiesDirty = true;
        }
        if (!bl && this.displayListEntitiesDirty && Config.isIntegratedServerRunning() && !Shaders.isShadowPass) {
            n4 = ChunkVisibility.getMaxChunkY(this.world, entity2, this.renderDistanceChunks);
        }
        ChunkRenderDispatcher.ChunkRender chunkRender2 = this.viewFrustum.getRenderChunk(new BlockPos(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ()));
        if (Shaders.isShadowPass) {
            this.renderInfos = this.renderInfosShadow;
            this.renderInfosEntities = this.renderInfosEntitiesShadow;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
            if (!bl && this.displayListEntitiesDirty) {
                this.clearRenderInfos();
                if (chunkRender2 != null && chunkRender2.getPosition().getY() > n4) {
                    this.renderInfosEntities.add(chunkRender2.getRenderInfo());
                }
                object2 = ShadowUtils.makeShadowChunkIterator(this.world, 0.0, entity2, this.renderDistanceChunks, this.viewFrustum);
                while (object2.hasNext()) {
                    ChunkRenderDispatcher.ChunkRender chunkRender3 = (ChunkRenderDispatcher.ChunkRender)object2.next();
                    if (chunkRender3 == null || chunkRender3.getPosition().getY() > n4) continue;
                    LocalRenderInformationContainer object7 = chunkRender3.getRenderInfo();
                    if (!chunkRender3.compiledChunk.get().isEmpty()) {
                        this.renderInfos.add(object7);
                    }
                    if (ChunkUtils.hasEntities(chunkRender3.getChunk())) {
                        this.renderInfosEntities.add(object7);
                    }
                    if (chunkRender3.getCompiledChunk().getTileEntities().size() <= 0) continue;
                    this.renderInfosTileEntities.add(object7);
                }
            }
        } else {
            this.renderInfos = this.renderInfosNormal;
            this.renderInfosEntities = this.renderInfosEntitiesNormal;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
        }
        if (!bl && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
            Object object;
            Object object3;
            Object object4;
            Object object5;
            this.displayListEntitiesDirty = false;
            this.clearRenderInfos();
            this.visibilityDeque.clear();
            object2 = this.visibilityDeque;
            Entity.setRenderDistanceWeight(MathHelper.clamp((double)this.mc.gameSettings.renderDistanceChunks / 8.0, 1.0, 2.5) * (double)this.mc.gameSettings.entityDistanceScaling);
            boolean bl4 = this.mc.renderChunksMany;
            BlockPos blockPos3 = activeRenderInfo.getBlockPos();
            int n5 = blockPos3.getY();
            int n6 = n5 >> 4 << 4;
            if (n6 > n4 && n6 > (n4 += 16) && n4 < 256) {
                if (chunkRender2 != null) {
                    this.renderInfosEntities.add(chunkRender2.getRenderInfo());
                }
                Vector3d vector3d2 = new Vector3d(blockPos3.getX(), n4, blockPos3.getZ());
                object5 = new Vector3d(vector3d2.getX(), vector3d2.getY(), vector3d2.getZ());
                object4 = activeRenderInfo.getViewVector();
                object3 = new Vector3f(((Vector3f)object4).getX(), 0.0f, ((Vector3f)object4).getZ());
                if (!((Vector3f)object3).normalize()) {
                    object3 = new Vector3f(1.0f, 0.0f, 0.0f);
                }
                double d4 = ((Vector3f)object3).getX() * 16.0f;
                double d5 = ((Vector3f)object3).getZ() * 16.0f;
                double d6 = this.renderDistanceChunks * 16;
                double d7 = d6 * d6;
                while (((Vector3d)object5).squareDistanceTo(vector3d2) < d7 && (object = this.viewFrustum.getRenderChunk(new BlockPos((Vector3d)object5))) != null) {
                    if (clippingHelper.isBoundingBoxInFrustum(((ChunkRenderDispatcher.ChunkRender)object).boundingBox)) {
                        ((ChunkRenderDispatcher.ChunkRender)object).setFrameIndex(n);
                        object2.add(new LocalRenderInformationContainer((ChunkRenderDispatcher.ChunkRender)object, null, 0));
                        break;
                    }
                    object5 = ((Vector3d)object5).add(d4, 0.0, d5);
                }
            }
            if (object2.isEmpty()) {
                if (chunkRender != null && chunkRender.getPosition().getY() <= n4) {
                    if (bl2 && this.world.getBlockState(blockPos).isOpaqueCube(this.world, blockPos)) {
                        bl4 = false;
                    }
                    chunkRender.setFrameIndex(n);
                    object2.add(new LocalRenderInformationContainer(chunkRender, null, 0));
                } else {
                    int n7;
                    int n8 = n7 = blockPos2.getY() > 0 ? Math.min(n4, 248) : 8;
                    if (chunkRender2 != null) {
                        this.renderInfosEntities.add(chunkRender2.getRenderInfo());
                    }
                    int n9 = MathHelper.floor(vector3d.x / 16.0) * 16;
                    int n10 = MathHelper.floor(vector3d.z / 16.0) * 16;
                    object3 = Lists.newArrayList();
                    for (int i = -this.renderDistanceChunks; i <= this.renderDistanceChunks; ++i) {
                        for (int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; ++j) {
                            ChunkRenderDispatcher.ChunkRender chunkRender3 = this.viewFrustum.getRenderChunk(new BlockPos(n9 + (i << 4) + 8, n7, n10 + (j << 4) + 8));
                            if (chunkRender3 == null || !clippingHelper.isBoundingBoxInFrustum(chunkRender3.boundingBox)) continue;
                            chunkRender3.setFrameIndex(n);
                            LocalRenderInformationContainer localRenderInformationContainer = chunkRender3.getRenderInfo();
                            localRenderInformationContainer.initialize(null, 0, 0);
                            object3.add(localRenderInformationContainer);
                        }
                    }
                    object3.sort(Comparator.comparingDouble(arg_0 -> WorldRenderer.lambda$setupTerrain$0(blockPos, arg_0)));
                    object2.addAll(object3);
                }
            }
            this.mc.getProfiler().startSection("iteration");
            bl3 = Config.isFogOn();
            while (!object2.isEmpty()) {
                Direction[] directionArray;
                object5 = (LocalRenderInformationContainer)object2.poll();
                object4 = ((LocalRenderInformationContainer)object5).renderChunk;
                object3 = ((LocalRenderInformationContainer)object5).facing;
                ChunkRenderDispatcher.CompiledChunk compiledChunk = ((ChunkRenderDispatcher.ChunkRender)object4).compiledChunk.get();
                if (!compiledChunk.isEmpty() || ((ChunkRenderDispatcher.ChunkRender)object4).needsUpdate()) {
                    this.renderInfos.add(object5);
                }
                if (ChunkUtils.hasEntities(((ChunkRenderDispatcher.ChunkRender)object4).getChunk())) {
                    this.renderInfosEntities.add((LocalRenderInformationContainer)object5);
                }
                if (compiledChunk.getTileEntities().size() > 0) {
                    this.renderInfosTileEntities.add((LocalRenderInformationContainer)object5);
                }
                for (Direction direction : directionArray = bl4 ? ChunkVisibility.getFacingsNotOpposite(((LocalRenderInformationContainer)object5).setFacing) : Direction.VALUES) {
                    ChunkRenderDispatcher.ChunkRender chunkRender4;
                    if (bl4 && object3 != null && !compiledChunk.isVisible(((Direction)object3).getOpposite(), direction) || (chunkRender4 = this.getRenderChunkOffset(blockPos2, (ChunkRenderDispatcher.ChunkRender)object4, direction, bl3, n4)) == null || !chunkRender4.setFrameIndex(n) || !clippingHelper.isBoundingBoxInFrustum(chunkRender4.boundingBox)) continue;
                    int n11 = ((LocalRenderInformationContainer)object5).setFacing | 1 << direction.ordinal();
                    object = chunkRender4.getRenderInfo();
                    ((LocalRenderInformationContainer)object).initialize(direction, n11, ((LocalRenderInformationContainer)object5).counter + 1);
                    object2.add(object);
                }
            }
            this.mc.getProfiler().endSection();
        }
        Lagometer.timerVisibility.end();
        if (Shaders.isShadowPass) {
            Shaders.mcProfilerEndSection();
        } else {
            this.mc.getProfiler().endStartSection("rebuildNear");
            object2 = this.chunksToUpdate;
            this.chunksToUpdate = this.chunksToUpdatePrev;
            this.chunksToUpdatePrev = object2;
            this.chunksToUpdate.clear();
            Lagometer.timerChunkUpdate.start();
            for (LocalRenderInformationContainer localRenderInformationContainer : this.renderInfos) {
                ChunkRenderDispatcher.ChunkRender chunkRender5 = localRenderInformationContainer.renderChunk;
                if (!chunkRender5.needsUpdate() && !object2.contains(chunkRender5)) continue;
                this.displayListEntitiesDirty = true;
                BlockPos blockPos4 = chunkRender5.getPosition();
                boolean bl4 = bl3 = (double)MathUtils.distanceSq(blockPos2, blockPos4.getX() + 8, blockPos4.getY() + 8, blockPos4.getZ() + 8) < 768.0;
                if (!chunkRender5.needsImmediateUpdate() && !bl3) {
                    this.chunksToUpdate.add(chunkRender5);
                    continue;
                }
                if (!chunkRender5.isPlayerUpdate()) {
                    this.chunksToUpdateForced.add(chunkRender5);
                    continue;
                }
                this.mc.getProfiler().startSection("build near");
                this.renderDispatcher.rebuildChunk(chunkRender5);
                chunkRender5.clearNeedsUpdate();
                this.mc.getProfiler().endSection();
            }
            Lagometer.timerChunkUpdate.end();
            this.chunksToUpdate.addAll((Collection<ChunkRenderDispatcher.ChunkRender>)object2);
            this.mc.getProfiler().endSection();
        }
    }

    @Nullable
    private ChunkRenderDispatcher.ChunkRender getRenderChunkOffset(BlockPos blockPos, ChunkRenderDispatcher.ChunkRender chunkRender, Direction direction, boolean bl, int n) {
        ChunkRenderDispatcher.ChunkRender chunkRender2 = chunkRender.getRenderChunkNeighbour(direction);
        if (chunkRender2 == null) {
            return null;
        }
        if (chunkRender2.getPosition().getY() > n) {
            return null;
        }
        if (bl) {
            int n2;
            BlockPos blockPos2 = chunkRender2.getPosition();
            int n3 = blockPos.getX() - blockPos2.getX();
            int n4 = n3 * n3 + (n2 = blockPos.getZ() - blockPos2.getZ()) * n2;
            if (n4 > this.renderDistanceSq) {
                return null;
            }
        }
        return chunkRender2;
    }

    private void captureFrustum(Matrix4f matrix4f, Matrix4f matrix4f2, double d, double d2, double d3, ClippingHelper clippingHelper) {
        this.debugFixedClippingHelper = clippingHelper;
        Matrix4f matrix4f3 = matrix4f2.copy();
        matrix4f3.mul(matrix4f);
        matrix4f3.invert();
        this.debugTerrainFrustumPosition.x = d;
        this.debugTerrainFrustumPosition.y = d2;
        this.debugTerrainFrustumPosition.z = d3;
        this.debugTerrainMatrix[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < 8; ++i) {
            this.debugTerrainMatrix[i].transform(matrix4f3);
            this.debugTerrainMatrix[i].perspectiveDivide();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void updateCameraAndRender(MatrixStack matrixStack, float f, long l, boolean bl, ActiveRenderInfo activeRenderInfo, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f) {
        int n;
        int n2;
        Object object;
        boolean bl2;
        ClippingHelper clippingHelper;
        boolean bl3;
        TileEntityRendererDispatcher.instance.prepare(this.world, this.mc.getTextureManager(), this.mc.fontRenderer, activeRenderInfo, this.mc.objectMouseOver);
        this.renderManager.cacheActiveRenderInfo(this.world, activeRenderInfo, this.mc.pointedEntity);
        IProfiler iProfiler = this.world.getProfiler();
        iProfiler.endStartSection("light_updates");
        this.mc.world.getChunkProvider().getLightManager().tick(Integer.MAX_VALUE, true, false);
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        double d = vector3d.getX();
        double d2 = vector3d.getY();
        double d3 = vector3d.getZ();
        Matrix4f matrix4f2 = matrixStack.getLast().getMatrix();
        iProfiler.endStartSection("culling");
        boolean bl4 = bl3 = this.debugFixedClippingHelper != null;
        if (bl3) {
            clippingHelper = this.debugFixedClippingHelper;
            clippingHelper.setCameraPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
        } else {
            clippingHelper = new ClippingHelper(matrix4f2, matrix4f);
            clippingHelper.setCameraPosition(d, d2, d3);
        }
        frustum = new ClippingHelper(matrix4f2, matrix4f);
        this.mc.getProfiler().endStartSection("captureFrustum");
        if (this.debugFixTerrainFrustum) {
            this.captureFrustum(matrix4f2, matrix4f, vector3d.x, vector3d.y, vector3d.z, bl3 ? new ClippingHelper(matrix4f2, matrix4f) : clippingHelper);
            this.debugFixTerrainFrustum = false;
        }
        iProfiler.endStartSection("clear");
        if (Config.isShaders()) {
            Shaders.setViewport(0, 0, this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
        } else {
            RenderSystem.viewport(0, 0, this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight());
        }
        FogRenderer.updateFogColor(activeRenderInfo, f, this.mc.world, this.mc.gameSettings.renderDistanceChunks, gameRenderer.getBossColorModifier(f));
        RenderSystem.clear(16640, Minecraft.IS_RUNNING_ON_MAC);
        boolean bl5 = Config.isShaders();
        if (bl5) {
            Shaders.clearRenderBuffer();
            Shaders.setCamera(matrixStack, activeRenderInfo, f);
            Shaders.renderPrepare();
        }
        clippingHelper.disabled = Config.isShaders() && !Shaders.isFrustumCulling();
        float f2 = gameRenderer.getFarPlaneDistance();
        boolean bl6 = bl2 = this.mc.world.func_239132_a_().func_230493_a_(MathHelper.floor(d), MathHelper.floor(d2)) || this.mc.ingameGUI.getBossOverlay().shouldCreateFog();
        if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
            FogRenderer.setupFog(activeRenderInfo, FogRenderer.FogType.FOG_SKY, f2, bl2, f);
            iProfiler.endStartSection("sky");
            if (bl5) {
                Shaders.beginSky();
            }
            this.renderSky(matrixStack, f);
            if (bl5) {
                Shaders.endSky();
            }
        } else {
            GlStateManager.disableBlend();
        }
        iProfiler.endStartSection("fog");
        FogRenderer.setupFog(activeRenderInfo, FogRenderer.FogType.FOG_TERRAIN, Math.max(f2 - 16.0f, 32.0f), bl2, f);
        iProfiler.endStartSection("terrain_setup");
        this.checkLoadVisibleChunks(activeRenderInfo, clippingHelper, this.mc.player.isSpectator());
        this.setupTerrain(activeRenderInfo, clippingHelper, bl3, this.frameId++, this.mc.player.isSpectator());
        iProfiler.endStartSection("updatechunks");
        int n3 = 30;
        int n4 = this.mc.gameSettings.framerateLimit;
        long l2 = 33333333L;
        long l3 = (double)n4 == AbstractOption.FRAMERATE_LIMIT.getMaxValue() ? 0L : (long)(1000000000 / n4);
        long l4 = Util.nanoTime() - l;
        long l5 = this.renderTimeManager.nextValue(l4);
        long l6 = l5 * 3L / 2L;
        long l7 = MathHelper.clamp(l6, l3, 33333333L);
        Lagometer.timerChunkUpload.start();
        this.updateChunks(l + l7);
        Lagometer.timerChunkUpload.end();
        iProfiler.endStartSection("terrain");
        Lagometer.timerTerrain.start();
        if (this.mc.gameSettings.ofSmoothFps) {
            this.mc.getProfiler().endStartSection("finish");
            GL11.glFinish();
            this.mc.getProfiler().endStartSection("terrain");
        }
        if (Config.isFogOff() && FogRenderer.fogStandard) {
            GlStateManager.setFogAllowed(false);
        }
        this.renderBlockLayer(RenderType.getSolid(), matrixStack, d, d2, d3);
        this.mc.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, this.mc.gameSettings.mipmapLevels > 0);
        this.renderBlockLayer(RenderType.getCutoutMipped(), matrixStack, d, d2, d3);
        this.mc.getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        this.renderBlockLayer(RenderType.getCutout(), matrixStack, d, d2, d3);
        if (bl5) {
            ShadersRender.endTerrain();
        }
        Lagometer.timerTerrain.end();
        if (this.world.func_239132_a_().func_239217_c_()) {
            RenderHelper.setupDiffuseGuiLighting(matrixStack.getLast().getMatrix());
        } else {
            RenderHelper.setupLevelDiffuseLighting(matrixStack.getLast().getMatrix());
        }
        if (bl5) {
            Shaders.beginEntities();
        }
        ItemFrameRenderer.updateItemRenderDistance();
        iProfiler.endStartSection("entities");
        ++renderEntitiesCounter;
        this.countEntitiesRendered = 0;
        this.countEntitiesHidden = 0;
        this.countTileEntitiesRendered = 0;
        if (this.field_239223_G_ != null) {
            this.field_239223_G_.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
            this.field_239223_G_.func_237506_a_(this.mc.getFramebuffer());
            this.mc.getFramebuffer().bindFramebuffer(true);
        }
        if (this.field_239225_I_ != null) {
            this.field_239225_I_.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        }
        if (this.isRenderEntityOutlines()) {
            this.entityOutlineFramebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
            this.mc.getFramebuffer().bindFramebuffer(true);
        }
        boolean bl7 = false;
        IRenderTypeBuffer.Impl impl = this.renderTypeTextures.getBufferSource();
        if (Config.isFastRender()) {
            RenderStateManager.enableCache();
        }
        for (LocalRenderInformationContainer object52 : this.renderInfosEntities) {
            object = object52.renderChunk;
            Chunk chunk = ((ChunkRenderDispatcher.ChunkRender)object).getChunk();
            for (Entity entity2 : chunk.getEntityLists()[((ChunkRenderDispatcher.ChunkRender)object).getPosition().getY() / 16]) {
                int n5 = n2 = entity2 == this.mc.player && !this.mc.player.isSpectator() ? 1 : 0;
                if (!this.renderManager.shouldRender(entity2, clippingHelper, d, d2, d3) && !entity2.isRidingOrBeingRiddenBy(this.mc.player) || entity2 == activeRenderInfo.getRenderViewEntity() && !activeRenderInfo.isThirdPerson() && (!(activeRenderInfo.getRenderViewEntity() instanceof LivingEntity) || !((LivingEntity)activeRenderInfo.getRenderViewEntity()).isSleeping()) || entity2 instanceof ClientPlayerEntity && activeRenderInfo.getRenderViewEntity() != entity2 && n2 == 0) continue;
                String string = entity2.getClass().getName();
                List<Entity> list = this.mapEntityLists.get(string);
                if (list == null) {
                    list = new ArrayList<Entity>();
                    this.mapEntityLists.put(string, list);
                }
                list.add(entity2);
            }
        }
        for (List list : this.mapEntityLists.values()) {
            for (Entity entity3 : list) {
                void var44_52;
                ++this.countEntitiesRendered;
                if (entity3.ticksExisted == 0) {
                    entity3.lastTickPosX = entity3.getPosX();
                    entity3.lastTickPosY = entity3.getPosY();
                    entity3.lastTickPosZ = entity3.getPosZ();
                }
                if (this.isRenderEntityOutlines() && this.mc.isEntityGlowing(entity3)) {
                    OutlineLayerBuffer outlineLayerBuffer;
                    bl7 = true;
                    OutlineLayerBuffer outlineLayerBuffer2 = outlineLayerBuffer = this.renderTypeTextures.getOutlineBufferSource();
                    n2 = entity3.getTeamColor();
                    int n6 = 255;
                    int n7 = n2 >> 16 & 0xFF;
                    int n8 = n2 >> 8 & 0xFF;
                    int n9 = n2 & 0xFF;
                    n = ColorUtils.getColor(1);
                    int n10 = n >> 16 & 0xFF;
                    int n11 = n >> 8 & 0xFF;
                    int n12 = n >> 0 & 0xFF;
                    outlineLayerBuffer.setColor(n10, n11, n12, 255);
                } else {
                    IRenderTypeBuffer.Impl impl2 = impl;
                }
                this.renderedEntity = entity3;
                if (bl5) {
                    Shaders.nextEntity(entity3);
                }
                this.renderEntity(entity3, d, d2, d3, f, matrixStack, (IRenderTypeBuffer)var44_52);
                this.renderedEntity = null;
            }
            list.clear();
        }
        this.checkMatrixStack(matrixStack);
        impl.finish(RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        impl.finish(RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        impl.finish(RenderType.getEntityCutoutNoCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        impl.finish(RenderType.getEntitySmoothCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE));
        if (bl5) {
            Shaders.endEntities();
            Shaders.beginBlockEntities();
        }
        iProfiler.endStartSection("blockentities");
        SignTileEntityRenderer.updateTextRenderDistance();
        boolean bl8 = Reflector.IForgeTileEntity_getRenderBoundingBox.exists();
        ClippingHelper clippingHelper2 = clippingHelper;
        for (LocalRenderInformationContainer localRenderInformationContainer : this.renderInfosTileEntities) {
            List<TileEntity> list = localRenderInformationContainer.renderChunk.getCompiledChunk().getTileEntities();
            if (list.isEmpty()) continue;
            for (TileEntity tileEntity : list) {
                AxisAlignedBB axisAlignedBB;
                if (bl8 && (axisAlignedBB = (AxisAlignedBB)Reflector.call(tileEntity, Reflector.IForgeTileEntity_getRenderBoundingBox, new Object[0])) != null && !clippingHelper2.isBoundingBoxInFrustum(axisAlignedBB)) continue;
                if (bl5) {
                    Shaders.nextBlockEntity(tileEntity);
                }
                BlockPos blockPos = tileEntity.getPos();
                IRenderTypeBuffer iRenderTypeBuffer = impl;
                matrixStack.push();
                matrixStack.translate((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
                SortedSet sortedSet = (SortedSet)this.damageProgress.get(blockPos.toLong());
                if (sortedSet != null && !sortedSet.isEmpty() && (n = ((DestroyBlockProgress)sortedSet.last()).getPartialBlockDamage()) >= 0) {
                    MatrixStack.Entry entry = matrixStack.getLast();
                    MatrixApplyingVertexBuilder matrixApplyingVertexBuilder = new MatrixApplyingVertexBuilder(this.renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.DESTROY_RENDER_TYPES.get(n)), entry.getMatrix(), entry.getNormal());
                    iRenderTypeBuffer = arg_0 -> WorldRenderer.lambda$updateCameraAndRender$1(impl, matrixApplyingVertexBuilder, arg_0);
                }
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, f, matrixStack, iRenderTypeBuffer);
                matrixStack.pop();
                ++this.countTileEntitiesRendered;
            }
        }
        object = this.setTileEntities;
        synchronized (object) {
            for (TileEntity tileEntity : this.setTileEntities) {
                AxisAlignedBB axisAlignedBB;
                if (bl8 && (axisAlignedBB = (AxisAlignedBB)Reflector.call(tileEntity, Reflector.IForgeTileEntity_getRenderBoundingBox, new Object[0])) != null && !clippingHelper2.isBoundingBoxInFrustum(axisAlignedBB)) continue;
                if (bl5) {
                    Shaders.nextBlockEntity(tileEntity);
                }
                BlockPos blockPos = tileEntity.getPos();
                matrixStack.push();
                matrixStack.translate((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, f, matrixStack, impl);
                matrixStack.pop();
                ++this.countTileEntitiesRendered;
            }
        }
        this.checkMatrixStack(matrixStack);
        impl.finish(RenderType.getSolid());
        impl.finish(Atlases.getSolidBlockType());
        impl.finish(Atlases.getCutoutBlockType());
        impl.finish(Atlases.getBedType());
        impl.finish(Atlases.getShulkerBoxType());
        impl.finish(Atlases.getSignType());
        impl.finish(Atlases.getChestType());
        impl.finish(Atlases.getBannerType());
        this.renderTypeTextures.getOutlineBufferSource().finish();
        if (Config.isFastRender()) {
            RenderStateManager.disableCache();
        }
        if (bl7) {
            this.entityOutlineShader.render(f);
            this.mc.getFramebuffer().bindFramebuffer(true);
        }
        if (bl5) {
            Shaders.endBlockEntities();
        }
        this.renderOverlayDamaged = true;
        iProfiler.endStartSection("destroyProgress");
        for (Long2ObjectMap.Entry entry : this.damageProgress.long2ObjectEntrySet()) {
            SortedSet sortedSet;
            double d4;
            double d5;
            BlockPos blockPos = BlockPos.fromLong(entry.getLongKey());
            double d6 = (double)blockPos.getX() - d;
            if (d6 * d6 + (d5 = (double)blockPos.getY() - d2) * d5 + (d4 = (double)blockPos.getZ() - d3) * d4 > 1024.0 || (sortedSet = (SortedSet)entry.getValue()) == null || sortedSet.isEmpty()) continue;
            int n13 = ((DestroyBlockProgress)sortedSet.last()).getPartialBlockDamage();
            matrixStack.push();
            matrixStack.translate((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
            MatrixStack.Entry entry2 = matrixStack.getLast();
            MatrixApplyingVertexBuilder matrixApplyingVertexBuilder = new MatrixApplyingVertexBuilder(this.renderTypeTextures.getCrumblingBufferSource().getBuffer(ModelBakery.DESTROY_RENDER_TYPES.get(n13)), entry2.getMatrix(), entry2.getNormal());
            this.mc.getBlockRendererDispatcher().renderBlockDamage(this.world.getBlockState(blockPos), blockPos, this.world, matrixStack, matrixApplyingVertexBuilder);
            matrixStack.pop();
        }
        this.renderOverlayDamaged = false;
        RenderUtils.flushRenderBuffers();
        --renderEntitiesCounter;
        this.checkMatrixStack(matrixStack);
        object = this.mc.objectMouseOver;
        if (bl && object != null && ((RayTraceResult)object).getType() == RayTraceResult.Type.BLOCK) {
            void var45_70;
            iProfiler.endStartSection("outline");
            BlockPos blockPos = ((BlockRayTraceResult)object).getPos();
            BlockState blockState = this.world.getBlockState(blockPos);
            if (bl5) {
                ShadersRender.beginOutline();
            }
            if (Reflector.IForgeBlockState_isAir2.exists() && Reflector.ForgeHooksClient_onDrawBlockHighlight.exists()) {
                boolean bl9 = !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, this, activeRenderInfo, object, Float.valueOf(f), matrixStack, impl) && !Reflector.callBoolean(blockState, Reflector.IForgeBlockState_isAir2, this.world, blockPos) && this.world.getWorldBorder().contains(blockPos);
            } else {
                boolean bl10;
                boolean bl11 = bl10 = !blockState.isAir() && this.world.getWorldBorder().contains(blockPos);
            }
            if (var45_70 != false) {
                IVertexBuilder iVertexBuilder = impl.getBuffer(RenderType.getLines());
                this.drawSelectionBox(matrixStack, iVertexBuilder, activeRenderInfo.getRenderViewEntity(), d, d2, d3, blockPos, blockState);
            }
            if (bl5) {
                impl.finish(RenderType.getLines());
                ShadersRender.endOutline();
            }
        } else if (object != null && ((RayTraceResult)object).getType() == RayTraceResult.Type.ENTITY) {
            Reflector.ForgeHooksClient_onDrawBlockHighlight.call(this, activeRenderInfo, object, Float.valueOf(f), matrixStack, impl);
        }
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
        boolean bl12 = GlStateManager.isFogEnabled();
        GlStateManager.disableFog();
        if (bl5) {
            ShadersRender.beginDebug();
        }
        this.mc.debugRenderer.render(matrixStack, impl, d, d2, d3);
        RenderSystem.popMatrix();
        impl.finish(Atlases.getTranslucentCullBlockType());
        impl.finish(Atlases.getBannerType());
        impl.finish(Atlases.getShieldType());
        impl.finish(RenderType.getArmorGlint());
        impl.finish(RenderType.getArmorEntityGlint());
        impl.finish(RenderType.getGlint());
        impl.finish(RenderType.getGlintDirect());
        impl.finish(RenderType.getGlintTranslucent());
        impl.finish(RenderType.getEntityGlint());
        impl.finish(RenderType.getEntityGlintDirect());
        impl.finish(RenderType.getWaterMask());
        this.renderTypeTextures.getCrumblingBufferSource().finish();
        GlStateManager.setFogEnabled(bl12);
        if (bl5) {
            impl.finish();
            ShadersRender.endDebug();
            Shaders.preRenderHand();
            ShadersRender.renderHand0(gameRenderer, matrixStack, activeRenderInfo, f);
            Shaders.preWater();
        }
        if (this.field_239227_K_ != null) {
            impl.finish(RenderType.getLines());
            impl.finish();
            this.field_239222_F_.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
            this.field_239222_F_.func_237506_a_(this.mc.getFramebuffer());
            iProfiler.endStartSection("translucent");
            this.renderBlockLayer(RenderType.getTranslucent(), matrixStack, d, d2, d3);
            iProfiler.endStartSection("string");
            this.renderBlockLayer(RenderType.getTripwire(), matrixStack, d, d2, d3);
            this.field_239224_H_.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
            this.field_239224_H_.func_237506_a_(this.mc.getFramebuffer());
            RenderState.field_239237_T_.setupRenderState();
            iProfiler.endStartSection("particles");
            this.mc.particles.renderParticles(matrixStack, impl, lightTexture, activeRenderInfo, f, clippingHelper);
            RenderState.field_239237_T_.clearRenderState();
        } else {
            iProfiler.endStartSection("translucent");
            if (bl5) {
                Shaders.beginWater();
            }
            this.renderBlockLayer(RenderType.getTranslucent(), matrixStack, d, d2, d3);
            if (bl5) {
                Shaders.endWater();
            }
            impl.finish(RenderType.getLines());
            impl.finish();
            iProfiler.endStartSection("string");
            this.renderBlockLayer(RenderType.getTripwire(), matrixStack, d, d2, d3);
            iProfiler.endStartSection("particles");
            if (bl5) {
                Shaders.beginParticles();
            }
            this.mc.particles.renderParticles(matrixStack, impl, lightTexture, activeRenderInfo, f, clippingHelper);
            if (bl5) {
                Shaders.endParticles();
            }
        }
        GlStateManager.setFogAllowed(true);
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
        this.render.setPartialTicks(f);
        this.render.setStack(matrixStack);
        venusfr.getInstance().getEventBus().post(this.render);
        if (this.mc.gameSettings.getCloudOption() != CloudOption.OFF) {
            if (this.field_239227_K_ != null) {
                this.field_239226_J_.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
                RenderState.field_239239_V_.setupRenderState();
                iProfiler.endStartSection("clouds");
                this.renderClouds(matrixStack, f, d, d2, d3);
                RenderState.field_239239_V_.clearRenderState();
            } else {
                iProfiler.endStartSection("clouds");
                this.renderClouds(matrixStack, f, d, d2, d3);
            }
        }
        if (this.field_239227_K_ != null) {
            RenderState.field_239238_U_.setupRenderState();
            iProfiler.endStartSection("weather");
            this.renderRainSnow(lightTexture, f, d, d2, d3);
            this.renderWorldBorder(activeRenderInfo);
            RenderState.field_239238_U_.clearRenderState();
            this.field_239227_K_.render(f);
            this.mc.getFramebuffer().bindFramebuffer(true);
        } else {
            RenderSystem.depthMask(false);
            if (Config.isShaders()) {
                GlStateManager.depthMask(Shaders.isRainDepth());
            }
            iProfiler.endStartSection("weather");
            if (bl5) {
                Shaders.beginWeather();
            }
            this.renderRainSnow(lightTexture, f, d, d2, d3);
            if (bl5) {
                Shaders.endWeather();
            }
            this.renderWorldBorder(activeRenderInfo);
            RenderSystem.depthMask(true);
        }
        this.renderDebug(activeRenderInfo);
        RenderSystem.shadeModel(7424);
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        FogRenderer.resetFog();
    }

    public void checkMatrixStack(MatrixStack matrixStack) {
        if (!matrixStack.clear()) {
            throw new IllegalStateException("Pose stack not empty");
        }
    }

    public void renderEntity(Entity entity2, double d, double d2, double d3, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer) {
        double d4 = MathHelper.lerp((double)f, entity2.lastTickPosX, entity2.getPosX());
        double d5 = MathHelper.lerp((double)f, entity2.lastTickPosY, entity2.getPosY());
        double d6 = MathHelper.lerp((double)f, entity2.lastTickPosZ, entity2.getPosZ());
        float f2 = MathHelper.lerp(f, entity2.prevRotationYaw, entity2.rotationYaw);
        this.renderManager.renderEntityStatic(entity2, d4 - d, d5 - d2, d6 - d3, f2, f, matrixStack, iRenderTypeBuffer, this.renderManager.getPackedLight(entity2, f));
    }

    public void renderBlockLayer(RenderType renderType, MatrixStack matrixStack, double d, double d2, double d3) {
        Object object;
        Object object2;
        renderType.setupRenderState();
        boolean bl = Config.isShaders();
        if (renderType == RenderType.getTranslucent() && !Shaders.isShadowPass) {
            this.mc.getProfiler().startSection("translucent_sort");
            double d4 = d - this.prevRenderSortX;
            double d5 = d2 - this.prevRenderSortY;
            double d6 = d3 - this.prevRenderSortZ;
            if (d4 * d4 + d5 * d5 + d6 * d6 > 1.0) {
                this.prevRenderSortX = d;
                this.prevRenderSortY = d2;
                this.prevRenderSortZ = d3;
                int n = 0;
                this.chunksToResortTransparency.clear();
                object2 = this.renderInfos.iterator();
                while (object2.hasNext()) {
                    object = (LocalRenderInformationContainer)object2.next();
                    if (n >= 15 || !((LocalRenderInformationContainer)object).renderChunk.getCompiledChunk().isLayerStarted(renderType)) continue;
                    this.chunksToResortTransparency.add(((LocalRenderInformationContainer)object).renderChunk);
                    ++n;
                }
            }
            this.mc.getProfiler().endSection();
        }
        this.mc.getProfiler().startSection("filterempty");
        if (bl) {
            ShadersRender.preRenderChunkLayer(renderType);
        }
        boolean bl2 = SmartAnimations.isActive();
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
        this.mc.getProfiler().endStartSection(() -> WorldRenderer.lambda$renderBlockLayer$2(renderType));
        boolean bl3 = renderType != RenderType.getTranslucent();
        ListIterator listIterator2 = this.renderInfos.listIterator(bl3 ? 0 : this.renderInfos.size());
        if (Config.isRenderRegions()) {
            Object object3;
            Object object4;
            int n = Integer.MIN_VALUE;
            int n2 = Integer.MIN_VALUE;
            Object object5 = null;
            Map map = this.mapRegionLayers.computeIfAbsent(renderType, WorldRenderer::lambda$renderBlockLayer$3);
            object2 = null;
            object = null;
            while (!(!bl3 ? !listIterator2.hasPrevious() : !listIterator2.hasNext())) {
                Object object6;
                Object object7;
                Object object8 = object7 = bl3 ? (LocalRenderInformationContainer)listIterator2.next() : (LocalRenderInformationContainer)listIterator2.previous();
                ChunkRenderDispatcher.ChunkRender object9 = ((LocalRenderInformationContainer)object7).renderChunk;
                if (object9.getCompiledChunk().isLayerEmpty(renderType)) continue;
                object4 = object9.getVertexBuffer(renderType);
                object3 = ((VertexBuffer)object4).getVboRegion();
                if (object9.regionX != n || object9.regionZ != n2) {
                    object6 = PairInt.of(object9.regionX, object9.regionZ);
                    object2 = map.computeIfAbsent(object6, WorldRenderer::lambda$renderBlockLayer$4);
                    n = object9.regionX;
                    n2 = object9.regionZ;
                    object5 = null;
                }
                if (object3 != object5) {
                    object = object2.computeIfAbsent(object3, WorldRenderer::lambda$renderBlockLayer$5);
                    object5 = object3;
                }
                object.add(object4);
                if (!SmartAnimations.isActive() || (object6 = object9.getCompiledChunk().getAnimatedSprites(renderType)) == null) continue;
                SmartAnimations.spritesRendered((BitSet)object6);
            }
            for (Map.Entry entry : map.entrySet()) {
                object4 = (PairInt)entry.getKey();
                object3 = (Map)entry.getValue();
                for (Map.Entry entry2 : object3.entrySet()) {
                    VboRegion vboRegion = (VboRegion)entry2.getKey();
                    List list = (List)entry2.getValue();
                    for (VertexBuffer vertexBuffer : list) {
                        vertexBuffer.draw(7);
                    }
                    this.drawRegion(((PairInt)object4).getLeft(), 0, ((PairInt)object4).getRight(), d, d2, d3, vboRegion);
                    list.clear();
                }
            }
        } else {
            while (!(!bl3 ? !listIterator2.hasPrevious() : !listIterator2.hasNext())) {
                LocalRenderInformationContainer localRenderInformationContainer;
                LocalRenderInformationContainer localRenderInformationContainer2 = localRenderInformationContainer = bl3 ? (LocalRenderInformationContainer)listIterator2.next() : (LocalRenderInformationContainer)listIterator2.previous();
                ChunkRenderDispatcher.ChunkRender chunkRender = localRenderInformationContainer.renderChunk;
                if (chunkRender.getCompiledChunk().isLayerEmpty(renderType)) continue;
                VertexBuffer vertexBuffer = chunkRender.getVertexBuffer(renderType);
                GlStateManager.pushMatrix();
                BlockPos blockPos = chunkRender.getPosition();
                GlStateManager.translated((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
                vertexBuffer.bindBuffer();
                DefaultVertexFormats.BLOCK.setupBufferState(0L);
                GlStateManager.lockClientState();
                if (bl) {
                    ShadersRender.setupArrayPointersVbo();
                }
                vertexBuffer.draw(7);
                GlStateManager.popMatrix();
                if (!bl2 || (object2 = chunkRender.getCompiledChunk().getAnimatedSprites(renderType)) == null) continue;
                SmartAnimations.spritesRendered((BitSet)object2);
            }
        }
        GlStateManager.unlockClientState();
        RenderSystem.popMatrix();
        if (Config.isMultiTexture()) {
            this.mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        }
        VertexBuffer.unbindBuffer();
        RenderSystem.clearCurrentColor();
        DefaultVertexFormats.BLOCK.clearBufferState();
        this.mc.getProfiler().endSection();
        if (bl) {
            ShadersRender.postRenderChunkLayer(renderType);
        }
        renderType.clearRenderState();
    }

    private void drawRegion(int n, int n2, int n3, double d, double d2, double d3, VboRegion vboRegion) {
        GlStateManager.pushMatrix();
        GlStateManager.translated((double)n - d, (double)n2 - d2, (double)n3 - d3);
        vboRegion.finishDraw();
        GlStateManager.lockClientState();
        GlStateManager.popMatrix();
    }

    private void renderDebug(ActiveRenderInfo activeRenderInfo) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (this.mc.debugWireframe || this.mc.debugChunkPath) {
            double d = activeRenderInfo.getProjectedView().getX();
            double d2 = activeRenderInfo.getProjectedView().getY();
            double d3 = activeRenderInfo.getProjectedView().getZ();
            RenderSystem.depthMask(true);
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableTexture();
            for (LocalRenderInformationContainer localRenderInformationContainer : this.renderInfos) {
                int n;
                ChunkRenderDispatcher.ChunkRender chunkRender = localRenderInformationContainer.renderChunk;
                RenderSystem.pushMatrix();
                BlockPos blockPos = chunkRender.getPosition();
                RenderSystem.translated((double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3);
                if (this.mc.debugWireframe) {
                    bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
                    RenderSystem.lineWidth(10.0f);
                    n = localRenderInformationContainer.counter == 0 ? 0 : MathHelper.hsvToRGB((float)localRenderInformationContainer.counter / 50.0f, 0.9f, 0.9f);
                    int n2 = n >> 16 & 0xFF;
                    int n3 = n >> 8 & 0xFF;
                    int n4 = n & 0xFF;
                    Direction direction = localRenderInformationContainer.facing;
                    if (direction != null) {
                        bufferBuilder.pos(8.0, 8.0, 8.0).color(n2, n3, n4, 255).endVertex();
                        bufferBuilder.pos(8 - 16 * direction.getXOffset(), 8 - 16 * direction.getYOffset(), 8 - 16 * direction.getZOffset()).color(n2, n3, n4, 255).endVertex();
                    }
                    tessellator.draw();
                    RenderSystem.lineWidth(1.0f);
                }
                if (this.mc.debugChunkPath && !chunkRender.getCompiledChunk().isEmpty()) {
                    bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
                    RenderSystem.lineWidth(10.0f);
                    n = 0;
                    for (Direction direction : FACINGS) {
                        for (Direction direction2 : FACINGS) {
                            boolean bl = chunkRender.getCompiledChunk().isVisible(direction, direction2);
                            if (bl) continue;
                            ++n;
                            bufferBuilder.pos(8 + 8 * direction.getXOffset(), 8 + 8 * direction.getYOffset(), 8 + 8 * direction.getZOffset()).color(1, 0, 0, 1).endVertex();
                            bufferBuilder.pos(8 + 8 * direction2.getXOffset(), 8 + 8 * direction2.getYOffset(), 8 + 8 * direction2.getZOffset()).color(1, 0, 0, 1).endVertex();
                        }
                    }
                    tessellator.draw();
                    RenderSystem.lineWidth(1.0f);
                    if (n > 0) {
                        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                        float f = 0.5f;
                        float f2 = 0.2f;
                        bufferBuilder.pos(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 15.5, 0.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 15.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(15.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        bufferBuilder.pos(0.5, 0.5, 15.5).color(0.9f, 0.9f, 0.0f, 0.2f).endVertex();
                        tessellator.draw();
                    }
                }
                RenderSystem.popMatrix();
            }
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableTexture();
        }
        if (this.debugFixedClippingHelper != null) {
            RenderSystem.disableCull();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.lineWidth(10.0f);
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(this.debugTerrainFrustumPosition.x - activeRenderInfo.getProjectedView().x), (float)(this.debugTerrainFrustumPosition.y - activeRenderInfo.getProjectedView().y), (float)(this.debugTerrainFrustumPosition.z - activeRenderInfo.getProjectedView().z));
            RenderSystem.depthMask(true);
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            this.addDebugQuad(bufferBuilder, 0, 1, 2, 3, 0, 1, 1);
            this.addDebugQuad(bufferBuilder, 4, 5, 6, 7, 1, 0, 0);
            this.addDebugQuad(bufferBuilder, 0, 1, 5, 4, 1, 1, 0);
            this.addDebugQuad(bufferBuilder, 2, 3, 7, 6, 0, 0, 1);
            this.addDebugQuad(bufferBuilder, 0, 4, 7, 3, 0, 1, 0);
            this.addDebugQuad(bufferBuilder, 1, 5, 6, 2, 1, 0, 1);
            tessellator.draw();
            RenderSystem.depthMask(false);
            bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.addDebugVertex(bufferBuilder, 0);
            this.addDebugVertex(bufferBuilder, 1);
            this.addDebugVertex(bufferBuilder, 1);
            this.addDebugVertex(bufferBuilder, 2);
            this.addDebugVertex(bufferBuilder, 2);
            this.addDebugVertex(bufferBuilder, 3);
            this.addDebugVertex(bufferBuilder, 3);
            this.addDebugVertex(bufferBuilder, 0);
            this.addDebugVertex(bufferBuilder, 4);
            this.addDebugVertex(bufferBuilder, 5);
            this.addDebugVertex(bufferBuilder, 5);
            this.addDebugVertex(bufferBuilder, 6);
            this.addDebugVertex(bufferBuilder, 6);
            this.addDebugVertex(bufferBuilder, 7);
            this.addDebugVertex(bufferBuilder, 7);
            this.addDebugVertex(bufferBuilder, 4);
            this.addDebugVertex(bufferBuilder, 0);
            this.addDebugVertex(bufferBuilder, 4);
            this.addDebugVertex(bufferBuilder, 1);
            this.addDebugVertex(bufferBuilder, 5);
            this.addDebugVertex(bufferBuilder, 2);
            this.addDebugVertex(bufferBuilder, 6);
            this.addDebugVertex(bufferBuilder, 3);
            this.addDebugVertex(bufferBuilder, 7);
            tessellator.draw();
            RenderSystem.popMatrix();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableTexture();
            RenderSystem.lineWidth(1.0f);
        }
    }

    private void addDebugVertex(IVertexBuilder iVertexBuilder, int n) {
        iVertexBuilder.pos(this.debugTerrainMatrix[n].getX(), this.debugTerrainMatrix[n].getY(), this.debugTerrainMatrix[n].getZ()).endVertex();
    }

    private void addDebugQuad(IVertexBuilder iVertexBuilder, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        float f = 0.25f;
        iVertexBuilder.pos(this.debugTerrainMatrix[n].getX(), this.debugTerrainMatrix[n].getY(), this.debugTerrainMatrix[n].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).endVertex();
        iVertexBuilder.pos(this.debugTerrainMatrix[n2].getX(), this.debugTerrainMatrix[n2].getY(), this.debugTerrainMatrix[n2].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).endVertex();
        iVertexBuilder.pos(this.debugTerrainMatrix[n3].getX(), this.debugTerrainMatrix[n3].getY(), this.debugTerrainMatrix[n3].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).endVertex();
        iVertexBuilder.pos(this.debugTerrainMatrix[n4].getX(), this.debugTerrainMatrix[n4].getY(), this.debugTerrainMatrix[n4].getZ()).color((float)n5, (float)n6, (float)n7, 0.25f).endVertex();
    }

    public void tick() {
        ++this.ticks;
        if (this.ticks % 20 == 0) {
            Iterator iterator2 = this.damagedBlocks.values().iterator();
            while (iterator2.hasNext()) {
                DestroyBlockProgress destroyBlockProgress = (DestroyBlockProgress)iterator2.next();
                int n = destroyBlockProgress.getCreationCloudUpdateTick();
                if (this.ticks - n <= 400) continue;
                iterator2.remove();
                this.removeDamageProgress(destroyBlockProgress);
            }
        }
        if (Config.isRenderRegions() && this.ticks % 20 == 0) {
            this.mapRegionLayers.clear();
        }
    }

    private void removeDamageProgress(DestroyBlockProgress destroyBlockProgress) {
        long l = destroyBlockProgress.getPosition().toLong();
        Set set = (Set)this.damageProgress.get(l);
        set.remove(destroyBlockProgress);
        if (set.isEmpty()) {
            this.damageProgress.remove(l);
        }
    }

    private void renderSkyEnd(MatrixStack matrixStack) {
        if (Config.isSkyEnabled()) {
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            this.textureManager.bindTexture(END_SKY_TEXTURES);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            for (int i = 0; i < 6; ++i) {
                matrixStack.push();
                if (i == 1) {
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
                }
                if (i == 2) {
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f));
                }
                if (i == 3) {
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0f));
                }
                if (i == 4) {
                    matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
                }
                if (i == 5) {
                    matrixStack.rotate(Vector3f.ZP.rotationDegrees(-90.0f));
                }
                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int n = 40;
                int n2 = 40;
                int n3 = 40;
                if (Config.isCustomColors()) {
                    Vector3d vector3d = new Vector3d((double)n / 255.0, (double)n2 / 255.0, (double)n3 / 255.0);
                    vector3d = CustomColors.getWorldSkyColor(vector3d, this.world, this.mc.getRenderViewEntity(), 0.0f);
                    n = (int)(vector3d.x * 255.0);
                    n2 = (int)(vector3d.y * 255.0);
                    n3 = (int)(vector3d.z * 255.0);
                }
                bufferBuilder.pos(matrix4f, -100.0f, -100.0f, -100.0f).tex(0.0f, 0.0f).color(n, n2, n3, 255).endVertex();
                bufferBuilder.pos(matrix4f, -100.0f, -100.0f, 100.0f).tex(0.0f, 16.0f).color(n, n2, n3, 255).endVertex();
                bufferBuilder.pos(matrix4f, 100.0f, -100.0f, 100.0f).tex(16.0f, 16.0f).color(n, n2, n3, 255).endVertex();
                bufferBuilder.pos(matrix4f, 100.0f, -100.0f, -100.0f).tex(16.0f, 0.0f).color(n, n2, n3, 255).endVertex();
                tessellator.draw();
                matrixStack.pop();
            }
            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
        }
    }

    public void renderSky(MatrixStack matrixStack, float f) {
        ISkyRenderHandler iSkyRenderHandler;
        if (Reflector.ForgeDimensionRenderInfo_getSkyRenderHandler.exists() && (iSkyRenderHandler = (ISkyRenderHandler)Reflector.call(this.world.func_239132_a_(), Reflector.ForgeDimensionRenderInfo_getSkyRenderHandler, new Object[0])) != null) {
            iSkyRenderHandler.render(this.ticks, f, matrixStack, this.world, this.mc);
            return;
        }
        if (this.mc.world.func_239132_a_().func_241683_c_() == DimensionRenderInfo.FogType.END) {
            this.renderSkyEnd(matrixStack);
        } else if (this.mc.world.func_239132_a_().func_241683_c_() == DimensionRenderInfo.FogType.NORMAL) {
            float f2;
            float f3;
            float f4;
            int n;
            int n2;
            float f5;
            float f6;
            float f7;
            RenderSystem.disableTexture();
            boolean bl = Config.isShaders();
            if (bl) {
                Shaders.disableTexture2D();
            }
            Vector3d vector3d = this.world.getSkyColor(this.mc.gameRenderer.getActiveRenderInfo().getBlockPos(), f);
            vector3d = CustomColors.getSkyColor(vector3d, this.mc.world, this.mc.getRenderViewEntity().getPosX(), this.mc.getRenderViewEntity().getPosY() + 1.0, this.mc.getRenderViewEntity().getPosZ());
            if (bl) {
                Shaders.setSkyColor(vector3d);
            }
            float f8 = (float)vector3d.x;
            float f9 = (float)vector3d.y;
            float f10 = (float)vector3d.z;
            FogRenderer.applyFog();
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.depthMask(false);
            RenderSystem.enableFog();
            if (bl) {
                Shaders.enableFog();
            }
            RenderSystem.color3f(f8, f9, f10);
            if (bl) {
                Shaders.preSkyList(matrixStack);
            }
            if (Config.isSkyEnabled()) {
                this.skyVBO.bindBuffer();
                this.skyVertexFormat.setupBufferState(0L);
                this.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
                VertexBuffer.unbindBuffer();
                this.skyVertexFormat.clearBufferState();
            }
            RenderSystem.disableFog();
            if (bl) {
                Shaders.disableFog();
            }
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float[] fArray = this.world.func_239132_a_().func_230492_a_(this.world.func_242415_f(f), f);
            if (fArray != null && Config.isSunMoonEnabled()) {
                RenderSystem.disableTexture();
                if (bl) {
                    Shaders.disableTexture2D();
                }
                if (bl) {
                    Shaders.setRenderStage(RenderStage.SUNSET);
                }
                RenderSystem.shadeModel(7425);
                matrixStack.push();
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0f));
                f7 = MathHelper.sin(this.world.getCelestialAngleRadians(f)) < 0.0f ? 180.0f : 0.0f;
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(f7));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0f));
                float f11 = fArray[0];
                f6 = fArray[1];
                f5 = fArray[2];
                Matrix4f matrix4f = matrixStack.getLast().getMatrix();
                bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                bufferBuilder.pos(matrix4f, 0.0f, 100.0f, 0.0f).color(f11, f6, f5, fArray[3]).endVertex();
                n2 = 16;
                for (n = 0; n <= 16; ++n) {
                    f4 = (float)n * ((float)Math.PI * 2) / 16.0f;
                    f3 = MathHelper.sin(f4);
                    f2 = MathHelper.cos(f4);
                    bufferBuilder.pos(matrix4f, f3 * 120.0f, f2 * 120.0f, -f2 * 40.0f * fArray[3]).color(fArray[0], fArray[1], fArray[2], 0.0f).endVertex();
                }
                bufferBuilder.finishDrawing();
                WorldVertexBufferUploader.draw(bufferBuilder);
                matrixStack.pop();
                RenderSystem.shadeModel(7424);
            }
            RenderSystem.enableTexture();
            if (bl) {
                Shaders.enableTexture2D();
            }
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            matrixStack.push();
            f7 = 1.0f - this.world.getRainStrength(f);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, f7);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0f));
            CustomSky.renderSky(this.world, this.textureManager, matrixStack, f);
            if (bl) {
                Shaders.preCelestialRotate(matrixStack);
            }
            matrixStack.rotate(Vector3f.XP.rotationDegrees(this.world.func_242415_f(f) * 360.0f));
            if (bl) {
                Shaders.postCelestialRotate(matrixStack);
            }
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            f6 = 30.0f;
            if (Config.isSunTexture()) {
                if (bl) {
                    Shaders.setRenderStage(RenderStage.SUN);
                }
                this.textureManager.bindTexture(SUN_TEXTURES);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferBuilder.pos(matrix4f, -f6, 100.0f, -f6).tex(0.0f, 0.0f).endVertex();
                bufferBuilder.pos(matrix4f, f6, 100.0f, -f6).tex(1.0f, 0.0f).endVertex();
                bufferBuilder.pos(matrix4f, f6, 100.0f, f6).tex(1.0f, 1.0f).endVertex();
                bufferBuilder.pos(matrix4f, -f6, 100.0f, f6).tex(0.0f, 1.0f).endVertex();
                bufferBuilder.finishDrawing();
                WorldVertexBufferUploader.draw(bufferBuilder);
            }
            f6 = 20.0f;
            if (Config.isMoonTexture()) {
                if (bl) {
                    Shaders.setRenderStage(RenderStage.MOON);
                }
                this.textureManager.bindTexture(MOON_PHASES_TEXTURES);
                int n3 = this.world.getMoonPhase();
                int n4 = n3 % 4;
                n2 = n3 / 4 % 2;
                float f12 = (float)(n4 + 0) / 4.0f;
                f4 = (float)(n2 + 0) / 2.0f;
                f3 = (float)(n4 + 1) / 4.0f;
                f2 = (float)(n2 + 1) / 2.0f;
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                bufferBuilder.pos(matrix4f, -f6, -100.0f, f6).tex(f3, f2).endVertex();
                bufferBuilder.pos(matrix4f, f6, -100.0f, f6).tex(f12, f2).endVertex();
                bufferBuilder.pos(matrix4f, f6, -100.0f, -f6).tex(f12, f4).endVertex();
                bufferBuilder.pos(matrix4f, -f6, -100.0f, -f6).tex(f3, f4).endVertex();
                bufferBuilder.finishDrawing();
                WorldVertexBufferUploader.draw(bufferBuilder);
            }
            RenderSystem.disableTexture();
            if (bl) {
                Shaders.disableTexture2D();
            }
            if ((f5 = this.world.getStarBrightness(f) * f7) > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.world)) {
                if (bl) {
                    Shaders.setRenderStage(RenderStage.STARS);
                }
                RenderSystem.color4f(f5, f5, f5, f5);
                this.starVBO.bindBuffer();
                this.skyVertexFormat.setupBufferState(0L);
                this.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
                VertexBuffer.unbindBuffer();
                this.skyVertexFormat.clearBufferState();
            }
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableFog();
            if (bl) {
                Shaders.enableFog();
            }
            matrixStack.pop();
            RenderSystem.disableTexture();
            if (bl) {
                Shaders.disableTexture2D();
            }
            RenderSystem.color3f(0.0f, 0.0f, 0.0f);
            double d = this.mc.player.getEyePosition((float)f).y - this.world.getWorldInfo().getVoidFogHeight();
            n = 0;
            if (d < 0.0) {
                if (bl) {
                    Shaders.setRenderStage(RenderStage.VOID);
                }
                matrixStack.push();
                matrixStack.translate(0.0, 12.0, 0.0);
                this.sky2VBO.bindBuffer();
                this.skyVertexFormat.setupBufferState(0L);
                this.sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
                VertexBuffer.unbindBuffer();
                this.skyVertexFormat.clearBufferState();
                matrixStack.pop();
                n = 1;
            }
            if (this.world.func_239132_a_().func_239216_b_()) {
                RenderSystem.color3f(f8 * 0.2f + 0.04f, f9 * 0.2f + 0.04f, f10 * 0.6f + 0.1f);
            } else {
                RenderSystem.color3f(f8, f9, f10);
            }
            RenderSystem.enableTexture();
            RenderSystem.depthMask(true);
            RenderSystem.disableFog();
        }
    }

    public void renderClouds(MatrixStack matrixStack, float f, double d, double d2, double d3) {
        if (!Config.isCloudsOff()) {
            ICloudRenderHandler iCloudRenderHandler;
            if (Reflector.ForgeDimensionRenderInfo_getCloudRenderHandler.exists() && (iCloudRenderHandler = (ICloudRenderHandler)Reflector.call(this.world.func_239132_a_(), Reflector.ForgeDimensionRenderInfo_getCloudRenderHandler, new Object[0])) != null) {
                iCloudRenderHandler.render(this.ticks, f, matrixStack, this.world, this.mc, d, d2, d3);
                return;
            }
            float f2 = this.world.func_239132_a_().func_239213_a_();
            if (!Float.isNaN(f2)) {
                if (Config.isShaders()) {
                    Shaders.beginClouds();
                }
                RenderSystem.disableCull();
                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableDepthTest();
                RenderSystem.defaultAlphaFunc();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.enableFog();
                RenderSystem.depthMask(true);
                float f3 = 12.0f;
                float f4 = 4.0f;
                double d4 = 2.0E-4;
                double d5 = ((float)this.ticks + f) * 0.03f;
                double d6 = (d + d5) / 12.0;
                double d7 = f2 - (float)d2 + 0.33f;
                double d8 = d3 / 12.0 + (double)0.33f;
                d6 -= (double)(MathHelper.floor(d6 / 2048.0) * 2048);
                d8 -= (double)(MathHelper.floor(d8 / 2048.0) * 2048);
                float f5 = (float)(d6 - (double)MathHelper.floor(d6));
                float f6 = (float)((d7 += this.mc.gameSettings.ofCloudsHeight * 128.0) / 4.0 - (double)MathHelper.floor(d7 / 4.0)) * 4.0f;
                float f7 = (float)(d8 - (double)MathHelper.floor(d8));
                Vector3d vector3d = this.world.getCloudColor(f);
                int n = (int)Math.floor(d6);
                int n2 = (int)Math.floor(d7 / 4.0);
                int n3 = (int)Math.floor(d8);
                if (n != this.cloudsCheckX || n2 != this.cloudsCheckY || n3 != this.cloudsCheckZ || this.mc.gameSettings.getCloudOption() != this.cloudOption || this.cloudsCheckColor.squareDistanceTo(vector3d) > 2.0E-4) {
                    this.cloudsCheckX = n;
                    this.cloudsCheckY = n2;
                    this.cloudsCheckZ = n3;
                    this.cloudsCheckColor = vector3d;
                    this.cloudOption = this.mc.gameSettings.getCloudOption();
                    this.cloudsNeedUpdate = true;
                }
                if (this.cloudsNeedUpdate) {
                    this.cloudsNeedUpdate = false;
                    BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                    if (this.cloudsVBO != null) {
                        this.cloudsVBO.close();
                    }
                    this.cloudsVBO = new VertexBuffer(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
                    this.drawClouds(bufferBuilder, d6, d7, d8, vector3d);
                    bufferBuilder.finishDrawing();
                    this.cloudsVBO.upload(bufferBuilder);
                }
                this.textureManager.bindTexture(CLOUDS_TEXTURES);
                matrixStack.push();
                matrixStack.scale(12.0f, 1.0f, 12.0f);
                matrixStack.translate(-f5, f6, -f7);
                if (this.cloudsVBO != null) {
                    int n4;
                    this.cloudsVBO.bindBuffer();
                    DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.setupBufferState(0L);
                    for (int i = n4 = this.cloudOption == CloudOption.FANCY ? 0 : 1; i < 2; ++i) {
                        if (i == 0) {
                            RenderSystem.colorMask(false, false, false, false);
                        } else {
                            RenderSystem.colorMask(true, true, true, true);
                        }
                        this.cloudsVBO.draw(matrixStack.getLast().getMatrix(), 7);
                    }
                    VertexBuffer.unbindBuffer();
                    DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.clearBufferState();
                }
                matrixStack.pop();
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableAlphaTest();
                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                RenderSystem.disableFog();
                if (Config.isShaders()) {
                    Shaders.endClouds();
                }
            }
        }
    }

    private void drawClouds(BufferBuilder bufferBuilder, double d, double d2, double d3, Vector3d vector3d) {
        float f = 4.0f;
        float f2 = 0.00390625f;
        int n = 8;
        int n2 = 4;
        float f3 = 9.765625E-4f;
        float f4 = (float)MathHelper.floor(d) * 0.00390625f;
        float f5 = (float)MathHelper.floor(d3) * 0.00390625f;
        float f6 = (float)vector3d.x;
        float f7 = (float)vector3d.y;
        float f8 = (float)vector3d.z;
        float f9 = f6 * 0.9f;
        float f10 = f7 * 0.9f;
        float f11 = f8 * 0.9f;
        float f12 = f6 * 0.7f;
        float f13 = f7 * 0.7f;
        float f14 = f8 * 0.7f;
        float f15 = f6 * 0.8f;
        float f16 = f7 * 0.8f;
        float f17 = f8 * 0.8f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        float f18 = (float)Math.floor(d2 / 4.0) * 4.0f;
        if (Config.isCloudsFancy()) {
            for (int i = -3; i <= 4; ++i) {
                for (int j = -3; j <= 4; ++j) {
                    int n3;
                    float f19 = i * 8;
                    float f20 = j * 8;
                    if (f18 > -5.0f) {
                        bufferBuilder.pos(f19 + 0.0f, f18 + 0.0f, f20 + 8.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f12, f13, f14, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 0.0f, f20 + 8.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f12, f13, f14, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 0.0f, f20 + 0.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f12, f13, f14, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 0.0f, f18 + 0.0f, f20 + 0.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f12, f13, f14, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    }
                    if (f18 <= 5.0f) {
                        bufferBuilder.pos(f19 + 0.0f, f18 + 4.0f - 9.765625E-4f, f20 + 8.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 4.0f - 9.765625E-4f, f20 + 8.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 4.0f - 9.765625E-4f, f20 + 0.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        bufferBuilder.pos(f19 + 0.0f, f18 + 4.0f - 9.765625E-4f, f20 + 0.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                    }
                    if (i > -1) {
                        for (n3 = 0; n3 < 8; ++n3) {
                            bufferBuilder.pos(f19 + (float)n3 + 0.0f, f18 + 0.0f, f20 + 8.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 0.0f, f18 + 4.0f, f20 + 8.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 0.0f, f18 + 4.0f, f20 + 0.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 0.0f, f18 + 0.0f, f20 + 0.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                        }
                    }
                    if (i <= 1) {
                        for (n3 = 0; n3 < 8; ++n3) {
                            bufferBuilder.pos(f19 + (float)n3 + 1.0f - 9.765625E-4f, f18 + 0.0f, f20 + 8.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 1.0f - 9.765625E-4f, f18 + 4.0f, f20 + 8.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 8.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 1.0f - 9.765625E-4f, f18 + 4.0f, f20 + 0.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            bufferBuilder.pos(f19 + (float)n3 + 1.0f - 9.765625E-4f, f18 + 0.0f, f20 + 0.0f).tex((f19 + (float)n3 + 0.5f) * 0.00390625f + f4, (f20 + 0.0f) * 0.00390625f + f5).color(f9, f10, f11, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                        }
                    }
                    if (j > -1) {
                        for (n3 = 0; n3 < 8; ++n3) {
                            bufferBuilder.pos(f19 + 0.0f, f18 + 4.0f, f20 + (float)n3 + 0.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bufferBuilder.pos(f19 + 8.0f, f18 + 4.0f, f20 + (float)n3 + 0.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bufferBuilder.pos(f19 + 8.0f, f18 + 0.0f, f20 + (float)n3 + 0.0f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            bufferBuilder.pos(f19 + 0.0f, f18 + 0.0f, f20 + (float)n3 + 0.0f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                        }
                    }
                    if (j > 1) continue;
                    for (n3 = 0; n3 < 8; ++n3) {
                        bufferBuilder.pos(f19 + 0.0f, f18 + 4.0f, f20 + (float)n3 + 1.0f - 9.765625E-4f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 4.0f, f20 + (float)n3 + 1.0f - 9.765625E-4f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                        bufferBuilder.pos(f19 + 8.0f, f18 + 0.0f, f20 + (float)n3 + 1.0f - 9.765625E-4f).tex((f19 + 8.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                        bufferBuilder.pos(f19 + 0.0f, f18 + 0.0f, f20 + (float)n3 + 1.0f - 9.765625E-4f).tex((f19 + 0.0f) * 0.00390625f + f4, (f20 + (float)n3 + 0.5f) * 0.00390625f + f5).color(f15, f16, f17, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                    }
                }
            }
        } else {
            boolean bl = true;
            int n4 = 32;
            for (int i = -32; i < 32; i += 32) {
                for (int j = -32; j < 32; j += 32) {
                    bufferBuilder.pos(i + 0, f18, j + 32).tex((float)(i + 0) * 0.00390625f + f4, (float)(j + 32) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    bufferBuilder.pos(i + 32, f18, j + 32).tex((float)(i + 32) * 0.00390625f + f4, (float)(j + 32) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    bufferBuilder.pos(i + 32, f18, j + 0).tex((float)(i + 32) * 0.00390625f + f4, (float)(j + 0) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    bufferBuilder.pos(i + 0, f18, j + 0).tex((float)(i + 0) * 0.00390625f + f4, (float)(j + 0) * 0.00390625f + f5).color(f6, f7, f8, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                }
            }
        }
    }

    public void updateChunks(long l) {
        ChunkRenderDispatcher.ChunkRender chunkRender;
        Iterator iterator2;
        l = (long)((double)l + 1.0E8);
        this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads();
        long l2 = Util.nanoTime();
        boolean bl = false;
        if (this.chunksToUpdateForced.size() > 0) {
            iterator2 = this.chunksToUpdateForced.iterator();
            while (iterator2.hasNext() && this.renderDispatcher.updateChunkLater(chunkRender = (ChunkRenderDispatcher.ChunkRender)iterator2.next())) {
                chunkRender.clearNeedsUpdate();
                iterator2.remove();
                this.chunksToUpdate.remove(chunkRender);
                this.chunksToResortTransparency.remove(chunkRender);
            }
        }
        if (this.chunksToResortTransparency.size() > 0 && (iterator2 = this.chunksToResortTransparency.iterator()).hasNext() && this.renderDispatcher.updateTransparencyLater(chunkRender = (ChunkRenderDispatcher.ChunkRender)iterator2.next())) {
            iterator2.remove();
        }
        double d = 0.0;
        int n = Config.getUpdatesPerFrame();
        if (!this.chunksToUpdate.isEmpty()) {
            Iterator<ChunkRenderDispatcher.ChunkRender> iterator3 = this.chunksToUpdate.iterator();
            while (iterator3.hasNext()) {
                double d2;
                ChunkRenderDispatcher.ChunkRender chunkRender2 = iterator3.next();
                boolean bl2 = chunkRender2.isChunkRegionEmpty();
                boolean bl3 = !chunkRender2.needsImmediateUpdate() && !bl2 ? this.renderDispatcher.updateChunkLater(chunkRender2) : this.renderDispatcher.updateChunkNow(chunkRender2);
                if (!bl3) break;
                chunkRender2.clearNeedsUpdate();
                iterator3.remove();
                if (bl2 || !((d += (d2 = 2.0 * RenderChunkUtils.getRelativeBufferSize(chunkRender2))) > (double)n)) continue;
                break;
            }
        }
    }

    private void renderWorldBorder(ActiveRenderInfo activeRenderInfo) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        WorldBorder worldBorder = this.world.getWorldBorder();
        double d = this.mc.gameSettings.renderDistanceChunks * 16;
        if (!(activeRenderInfo.getProjectedView().x < worldBorder.maxX() - d && activeRenderInfo.getProjectedView().x > worldBorder.minX() + d && activeRenderInfo.getProjectedView().z < worldBorder.maxZ() - d && activeRenderInfo.getProjectedView().z > worldBorder.minZ() + d)) {
            float f;
            double d2;
            double d3;
            float f2;
            if (Config.isShaders()) {
                Shaders.pushProgram();
                Shaders.useProgram(Shaders.ProgramTexturedLit);
                Shaders.setRenderStage(RenderStage.WORLD_BORDER);
            }
            double d4 = 1.0 - worldBorder.getClosestDistance(activeRenderInfo.getProjectedView().x, activeRenderInfo.getProjectedView().z) / d;
            d4 = Math.pow(d4, 4.0);
            double d5 = activeRenderInfo.getProjectedView().x;
            double d6 = activeRenderInfo.getProjectedView().y;
            double d7 = activeRenderInfo.getProjectedView().z;
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            this.textureManager.bindTexture(FORCEFIELD_TEXTURES);
            RenderSystem.depthMask(Minecraft.isFabulousGraphicsEnabled());
            RenderSystem.pushMatrix();
            int n = worldBorder.getStatus().getColor();
            float f3 = (float)(n >> 16 & 0xFF) / 255.0f;
            float f4 = (float)(n >> 8 & 0xFF) / 255.0f;
            float f5 = (float)(n & 0xFF) / 255.0f;
            RenderSystem.color4f(f3, f4, f5, (float)d4);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableAlphaTest();
            RenderSystem.disableCull();
            float f6 = (float)(Util.milliTime() % 3000L) / 3000.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            float f9 = 128.0f;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            double d8 = Math.max((double)MathHelper.floor(d7 - d), worldBorder.minZ());
            double d9 = Math.min((double)MathHelper.ceil(d7 + d), worldBorder.maxZ());
            if (d5 > worldBorder.maxX() - d) {
                f2 = 0.0f;
                d3 = d8;
                while (d3 < d9) {
                    d2 = Math.min(1.0, d9 - d3);
                    f = (float)d2 * 0.5f;
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.maxX(), 256, d3, f6 + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.maxX(), 256, d3 + d2, f6 + f + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.maxX(), 0, d3 + d2, f6 + f + f2, f6 + 128.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.maxX(), 0, d3, f6 + f2, f6 + 128.0f);
                    d3 += 1.0;
                    f2 += 0.5f;
                }
            }
            if (d5 < worldBorder.minX() + d) {
                f2 = 0.0f;
                d3 = d8;
                while (d3 < d9) {
                    d2 = Math.min(1.0, d9 - d3);
                    f = (float)d2 * 0.5f;
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.minX(), 256, d3, f6 + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.minX(), 256, d3 + d2, f6 + f + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.minX(), 0, d3 + d2, f6 + f + f2, f6 + 128.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, worldBorder.minX(), 0, d3, f6 + f2, f6 + 128.0f);
                    d3 += 1.0;
                    f2 += 0.5f;
                }
            }
            d8 = Math.max((double)MathHelper.floor(d5 - d), worldBorder.minX());
            d9 = Math.min((double)MathHelper.ceil(d5 + d), worldBorder.maxX());
            if (d7 > worldBorder.maxZ() - d) {
                f2 = 0.0f;
                d3 = d8;
                while (d3 < d9) {
                    d2 = Math.min(1.0, d9 - d3);
                    f = (float)d2 * 0.5f;
                    this.addVertex(bufferBuilder, d5, d6, d7, d3, 256, worldBorder.maxZ(), f6 + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3 + d2, 256, worldBorder.maxZ(), f6 + f + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3 + d2, 0, worldBorder.maxZ(), f6 + f + f2, f6 + 128.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3, 0, worldBorder.maxZ(), f6 + f2, f6 + 128.0f);
                    d3 += 1.0;
                    f2 += 0.5f;
                }
            }
            if (d7 < worldBorder.minZ() + d) {
                f2 = 0.0f;
                d3 = d8;
                while (d3 < d9) {
                    d2 = Math.min(1.0, d9 - d3);
                    f = (float)d2 * 0.5f;
                    this.addVertex(bufferBuilder, d5, d6, d7, d3, 256, worldBorder.minZ(), f6 + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3 + d2, 256, worldBorder.minZ(), f6 + f + f2, f6 + 0.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3 + d2, 0, worldBorder.minZ(), f6 + f + f2, f6 + 128.0f);
                    this.addVertex(bufferBuilder, d5, d6, d7, d3, 0, worldBorder.minZ(), f6 + f2, f6 + 128.0f);
                    d3 += 1.0;
                    f2 += 0.5f;
                }
            }
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            RenderSystem.enableCull();
            RenderSystem.disableAlphaTest();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.enableAlphaTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
            RenderSystem.depthMask(true);
            if (Config.isShaders()) {
                Shaders.popProgram();
                Shaders.setRenderStage(RenderStage.NONE);
            }
        }
    }

    private void addVertex(BufferBuilder bufferBuilder, double d, double d2, double d3, double d4, int n, double d5, float f, float f2) {
        bufferBuilder.pos(d4 - d, (double)n - d2, d5 - d3).tex(f, f2).endVertex();
    }

    private void drawSelectionBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, Entity entity2, double d, double d2, double d3, BlockPos blockPos, BlockState blockState) {
        if (!Config.isCustomEntityModels() || !CustomEntityModels.isCustomModel(blockState)) {
            WorldRenderer.drawShape(matrixStack, iVertexBuilder, blockState.getShape(this.world, blockPos, ISelectionContext.forEntity(entity2)), (double)blockPos.getX() - d, (double)blockPos.getY() - d2, (double)blockPos.getZ() - d3, 0.0f, 0.0f, 0.0f, 0.4f);
        }
    }

    public static void drawVoxelShapeParts(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, VoxelShape voxelShape, double d, double d2, double d3, float f, float f2, float f3, float f4) {
        List<AxisAlignedBB> list = voxelShape.toBoundingBoxList();
        int n = MathHelper.ceil((double)list.size() / 3.0);
        for (int i = 0; i < list.size(); ++i) {
            AxisAlignedBB axisAlignedBB = list.get(i);
            float f5 = ((float)i % (float)n + 1.0f) / (float)n;
            float f6 = i / n;
            float f7 = f5 * (float)(f6 == 0.0f ? 1 : 0);
            float f8 = f5 * (float)(f6 == 1.0f ? 1 : 0);
            float f9 = f5 * (float)(f6 == 2.0f ? 1 : 0);
            WorldRenderer.drawShape(matrixStack, iVertexBuilder, VoxelShapes.create(axisAlignedBB.offset(0.0, 0.0, 0.0)), d, d2, d3, f7, f8, f9, 1.0f);
        }
    }

    private static void drawShape(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, VoxelShape voxelShape, double d, double d2, double d3, float f, float f2, float f3, float f4) {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        voxelShape.forEachEdge((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> WorldRenderer.lambda$drawShape$6(iVertexBuilder, matrix4f, d, d2, d3, f, f2, f3, f4, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
    }

    public static void drawBoundingBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, f, f2, f3, f4, f, f2, f3);
    }

    public static void drawBoundingBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4) {
        WorldRenderer.drawBoundingBox(matrixStack, iVertexBuilder, d, d2, d3, d4, d5, d6, f, f2, f3, f4, f, f2, f3);
    }

    public static void drawBoundingBox(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        float f8 = (float)d;
        float f9 = (float)d2;
        float f10 = (float)d3;
        float f11 = (float)d4;
        float f12 = (float)d5;
        float f13 = (float)d6;
        iVertexBuilder.pos(matrix4f, f8, f9, f10).color(f, f6, f7, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f10).color(f, f6, f7, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f9, f10).color(f5, f2, f7, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f10).color(f5, f2, f7, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f9, f10).color(f5, f6, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f9, f13).color(f5, f6, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f9, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f9, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f8, f12, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f9, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f13).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f10).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, f11, f12, f13).color(f, f2, f3, f4).endVertex();
    }

    public static void addChainedFilledBoxVertices(BufferBuilder bufferBuilder, double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4) {
        bufferBuilder.pos(d, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d2, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d3).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d6).color(f, f2, f3, f4).endVertex();
        bufferBuilder.pos(d4, d5, d6).color(f, f2, f3, f4).endVertex();
    }

    public void notifyBlockUpdate(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, BlockState blockState2, int n) {
        this.notifyBlockUpdate(blockPos, (n & 8) != 0);
    }

    private void notifyBlockUpdate(BlockPos blockPos, boolean bl) {
        for (int i = blockPos.getZ() - 1; i <= blockPos.getZ() + 1; ++i) {
            for (int j = blockPos.getX() - 1; j <= blockPos.getX() + 1; ++j) {
                for (int k = blockPos.getY() - 1; k <= blockPos.getY() + 1; ++k) {
                    this.markForRerender(j >> 4, k >> 4, i >> 4, bl);
                }
            }
        }
    }

    public void markBlockRangeForRenderUpdate(int n, int n2, int n3, int n4, int n5, int n6) {
        for (int i = n3 - 1; i <= n6 + 1; ++i) {
            for (int j = n - 1; j <= n4 + 1; ++j) {
                for (int k = n2 - 1; k <= n5 + 1; ++k) {
                    this.markForRerender(j >> 4, k >> 4, i >> 4);
                }
            }
        }
    }

    public void markBlockRangeForRenderUpdate(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
        if (this.mc.getModelManager().needsRenderUpdate(blockState, blockState2)) {
            this.markBlockRangeForRenderUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }

    public void markSurroundingsForRerender(int n, int n2, int n3) {
        for (int i = n3 - 1; i <= n3 + 1; ++i) {
            for (int j = n - 1; j <= n + 1; ++j) {
                for (int k = n2 - 1; k <= n2 + 1; ++k) {
                    this.markForRerender(j, k, i);
                }
            }
        }
    }

    public void markForRerender(int n, int n2, int n3) {
        this.markForRerender(n, n2, n3, true);
    }

    private void markForRerender(int n, int n2, int n3, boolean bl) {
        this.viewFrustum.markForRerender(n, n2, n3, bl);
    }

    public void playRecord(@Nullable SoundEvent soundEvent, BlockPos blockPos) {
        this.playRecord(soundEvent, blockPos, soundEvent == null ? null : MusicDiscItem.getBySound(soundEvent));
    }

    public void playRecord(@Nullable SoundEvent soundEvent, BlockPos blockPos, @Nullable MusicDiscItem musicDiscItem) {
        ISound iSound = this.mapSoundPositions.get(blockPos);
        if (iSound != null) {
            this.mc.getSoundHandler().stop(iSound);
            this.mapSoundPositions.remove(blockPos);
        }
        if (soundEvent != null) {
            MusicDiscItem musicDiscItem2 = MusicDiscItem.getBySound(soundEvent);
            if (Reflector.MinecraftForgeClient.exists()) {
                musicDiscItem2 = musicDiscItem;
            }
            if (musicDiscItem2 != null) {
                this.mc.ingameGUI.func_238451_a_(musicDiscItem2.getDescription());
            }
            SimpleSound simpleSound = SimpleSound.record(soundEvent, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.mapSoundPositions.put(blockPos, simpleSound);
            this.mc.getSoundHandler().play(simpleSound);
        }
        this.setPartying(this.world, blockPos, soundEvent != null);
    }

    private void setPartying(World world, BlockPos blockPos, boolean bl) {
        for (LivingEntity livingEntity : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos).grow(3.0))) {
            livingEntity.setPartying(blockPos, bl);
        }
    }

    public void addParticle(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
        this.addParticle(iParticleData, bl, false, d, d2, d3, d4, d5, d6);
    }

    public void addParticle(IParticleData iParticleData, boolean bl, boolean bl2, double d, double d2, double d3, double d4, double d5, double d6) {
        try {
            this.addParticleUnchecked(iParticleData, bl, bl2, d, d2, d3, d4, d5, d6);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Particle being added");
            crashReportCategory.addDetail("ID", Registry.PARTICLE_TYPE.getKey(iParticleData.getType()));
            crashReportCategory.addDetail("Parameters", iParticleData.getParameters());
            crashReportCategory.addDetail("Position", () -> WorldRenderer.lambda$addParticle$7(d, d2, d3));
            throw new ReportedException(crashReport);
        }
    }

    private <T extends IParticleData> void addParticleUnchecked(T t, double d, double d2, double d3, double d4, double d5, double d6) {
        this.addParticle(t, t.getType().getAlwaysShow(), d, d2, d3, d4, d5, d6);
    }

    @Nullable
    private Particle addParticleUnchecked(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
        return this.addParticleUnchecked(iParticleData, bl, false, d, d2, d3, d4, d5, d6);
    }

    @Nullable
    private Particle addParticleUnchecked(IParticleData iParticleData, boolean bl, boolean bl2, double d, double d2, double d3, double d4, double d5, double d6) {
        ActiveRenderInfo activeRenderInfo = this.mc.gameRenderer.getActiveRenderInfo();
        if (this.mc != null && activeRenderInfo.isValid() && this.mc.particles != null) {
            ParticleStatus particleStatus = this.calculateParticleLevel(bl2);
            if (iParticleData == ParticleTypes.EXPLOSION_EMITTER && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (iParticleData == ParticleTypes.EXPLOSION && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (iParticleData == ParticleTypes.POOF && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (iParticleData == ParticleTypes.UNDERWATER && !Config.isWaterParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.SMOKE && !Config.isAnimatedSmoke()) {
                return null;
            }
            if (iParticleData == ParticleTypes.LARGE_SMOKE && !Config.isAnimatedSmoke()) {
                return null;
            }
            if (iParticleData == ParticleTypes.ENTITY_EFFECT && !Config.isPotionParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.AMBIENT_ENTITY_EFFECT && !Config.isPotionParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.EFFECT && !Config.isPotionParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.INSTANT_EFFECT && !Config.isPotionParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.WITCH && !Config.isPotionParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.PORTAL && !Config.isPortalParticles()) {
                return null;
            }
            if (iParticleData == ParticleTypes.FLAME && !Config.isAnimatedFlame()) {
                return null;
            }
            if (iParticleData == ParticleTypes.SOUL_FIRE_FLAME && !Config.isAnimatedFlame()) {
                return null;
            }
            if (iParticleData == ParticleTypes.DUST && !Config.isAnimatedRedstone()) {
                return null;
            }
            if (iParticleData == ParticleTypes.DRIPPING_WATER && !Config.isDrippingWaterLava()) {
                return null;
            }
            if (iParticleData == ParticleTypes.DRIPPING_LAVA && !Config.isDrippingWaterLava()) {
                return null;
            }
            if (iParticleData == ParticleTypes.FIREWORK && !Config.isFireworkParticles()) {
                return null;
            }
            if (!bl) {
                double d7 = 1024.0;
                if (iParticleData == ParticleTypes.CRIT) {
                    d7 = 38416.0;
                }
                if (activeRenderInfo.getProjectedView().squareDistanceTo(d, d2, d3) > d7) {
                    return null;
                }
                if (particleStatus == ParticleStatus.MINIMAL) {
                    return null;
                }
            }
            Particle particle = this.mc.particles.addParticle(iParticleData, d, d2, d3, d4, d5, d6);
            if (iParticleData == ParticleTypes.BUBBLE) {
                CustomColors.updateWaterFX(particle, this.world, d, d2, d3, this.renderEnv);
            }
            if (iParticleData == ParticleTypes.SPLASH) {
                CustomColors.updateWaterFX(particle, this.world, d, d2, d3, this.renderEnv);
            }
            if (iParticleData == ParticleTypes.RAIN) {
                CustomColors.updateWaterFX(particle, this.world, d, d2, d3, this.renderEnv);
            }
            if (iParticleData == ParticleTypes.MYCELIUM) {
                CustomColors.updateMyceliumFX(particle);
            }
            if (iParticleData == ParticleTypes.PORTAL) {
                CustomColors.updatePortalFX(particle);
            }
            if (iParticleData == ParticleTypes.DUST) {
                CustomColors.updateReddustFX(particle, this.world, d, d2, d3);
            }
            return particle;
        }
        return null;
    }

    private ParticleStatus calculateParticleLevel(boolean bl) {
        ParticleStatus particleStatus = this.mc.gameSettings.particles;
        if (bl && particleStatus == ParticleStatus.MINIMAL && this.world.rand.nextInt(10) == 0) {
            particleStatus = ParticleStatus.DECREASED;
        }
        if (particleStatus == ParticleStatus.DECREASED && this.world.rand.nextInt(3) == 0) {
            particleStatus = ParticleStatus.MINIMAL;
        }
        return particleStatus;
    }

    public void deleteAllDisplayLists() {
    }

    public void broadcastSound(int n, BlockPos blockPos, int n2) {
        switch (n) {
            case 1023: 
            case 1028: 
            case 1038: {
                ActiveRenderInfo activeRenderInfo = this.mc.gameRenderer.getActiveRenderInfo();
                if (!activeRenderInfo.isValid()) break;
                double d = (double)blockPos.getX() - activeRenderInfo.getProjectedView().x;
                double d2 = (double)blockPos.getY() - activeRenderInfo.getProjectedView().y;
                double d3 = (double)blockPos.getZ() - activeRenderInfo.getProjectedView().z;
                double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
                double d5 = activeRenderInfo.getProjectedView().x;
                double d6 = activeRenderInfo.getProjectedView().y;
                double d7 = activeRenderInfo.getProjectedView().z;
                if (d4 > 0.0) {
                    d5 += d / d4 * 2.0;
                    d6 += d2 / d4 * 2.0;
                    d7 += d3 / d4 * 2.0;
                }
                if (n == 1023) {
                    this.world.playSound(d5, d6, d7, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0f, 1.0f, true);
                    break;
                }
                if (n == 1038) {
                    this.world.playSound(d5, d6, d7, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.HOSTILE, 1.0f, 1.0f, true);
                    break;
                }
                this.world.playSound(d5, d6, d7, SoundEvents.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.HOSTILE, 5.0f, 1.0f, true);
            }
        }
    }

    public void playEvent(PlayerEntity playerEntity, int n, BlockPos blockPos, int n2) {
        Random random2 = this.world.rand;
        switch (n) {
            case 1000: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                break;
            }
            case 1001: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0f, 1.2f, true);
                break;
            }
            case 1002: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0f, 1.2f, true);
                break;
            }
            case 1003: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 1.0f, 1.2f, true);
                break;
            }
            case 1004: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_FIREWORK_ROCKET_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.2f, true);
                break;
            }
            case 1005: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1006: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1007: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1008: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1009: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (random2.nextFloat() - random2.nextFloat()) * 0.8f, true);
                break;
            }
            case 1010: {
                if (Item.getItemById(n2) instanceof MusicDiscItem) {
                    if (Reflector.MinecraftForgeClient.exists()) {
                        this.playRecord(((MusicDiscItem)Item.getItemById(n2)).getSound(), blockPos, (MusicDiscItem)Item.getItemById(n2));
                        break;
                    }
                    this.playRecord(((MusicDiscItem)Item.getItemById(n2)).getSound(), blockPos);
                    break;
                }
                this.playRecord(null, blockPos);
                break;
            }
            case 1011: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1012: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1013: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1014: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1015: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1016: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1017: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 10.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1018: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1019: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1020: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1021: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1022: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1024: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1025: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1026: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1027: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1029: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1030: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1031: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3f, this.world.rand.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1032: {
                this.mc.getSoundHandler().play(SimpleSound.ambientWithoutAttenuation(SoundEvents.BLOCK_PORTAL_TRAVEL, random2.nextFloat() * 0.4f + 0.8f, 0.25f));
                break;
            }
            case 1033: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                break;
            }
            case 1034: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                break;
            }
            case 1035: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                break;
            }
            case 1036: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1037: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1039: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.HOSTILE, 0.3f, this.world.rand.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1040: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, SoundCategory.NEUTRAL, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1041: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, SoundCategory.NEUTRAL, 2.0f, (random2.nextFloat() - random2.nextFloat()) * 0.2f + 1.0f, true);
                break;
            }
            case 1042: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0f, this.world.rand.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1043: {
                this.world.playSound(blockPos, SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.BLOCKS, 1.0f, this.world.rand.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1044: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.BLOCKS, 1.0f, this.world.rand.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 1500: {
                ComposterBlock.playEvent(this.world, blockPos, n2 > 0);
                break;
            }
            case 1501: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (random2.nextFloat() - random2.nextFloat()) * 0.8f, true);
                for (int i = 0; i < 8; ++i) {
                    this.world.addParticle(ParticleTypes.LARGE_SMOKE, (double)blockPos.getX() + random2.nextDouble(), (double)blockPos.getY() + 1.2, (double)blockPos.getZ() + random2.nextDouble(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1502: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5f, 2.6f + (random2.nextFloat() - random2.nextFloat()) * 0.8f, true);
                for (int i = 0; i < 5; ++i) {
                    double d = (double)blockPos.getX() + random2.nextDouble() * 0.6 + 0.2;
                    double d2 = (double)blockPos.getY() + random2.nextDouble() * 0.6 + 0.2;
                    double d3 = (double)blockPos.getZ() + random2.nextDouble() * 0.6 + 0.2;
                    this.world.addParticle(ParticleTypes.SMOKE, d, d2, d3, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 1503: {
                this.world.playSound(blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                for (int i = 0; i < 16; ++i) {
                    double d = (double)blockPos.getX() + (5.0 + random2.nextDouble() * 6.0) / 16.0;
                    double d4 = (double)blockPos.getY() + 0.8125;
                    double d5 = (double)blockPos.getZ() + (5.0 + random2.nextDouble() * 6.0) / 16.0;
                    this.world.addParticle(ParticleTypes.SMOKE, d, d4, d5, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2000: {
                Direction direction = Direction.byIndex(n2);
                int n3 = direction.getXOffset();
                int n4 = direction.getYOffset();
                int n5 = direction.getZOffset();
                double d = (double)blockPos.getX() + (double)n3 * 0.6 + 0.5;
                double d6 = (double)blockPos.getY() + (double)n4 * 0.6 + 0.5;
                double d7 = (double)blockPos.getZ() + (double)n5 * 0.6 + 0.5;
                for (int i = 0; i < 10; ++i) {
                    double d8 = random2.nextDouble() * 0.2 + 0.01;
                    double d9 = d + (double)n3 * 0.01 + (random2.nextDouble() - 0.5) * (double)n5 * 0.5;
                    double d10 = d6 + (double)n4 * 0.01 + (random2.nextDouble() - 0.5) * (double)n4 * 0.5;
                    double d11 = d7 + (double)n5 * 0.01 + (random2.nextDouble() - 0.5) * (double)n3 * 0.5;
                    double d12 = (double)n3 * d8 + random2.nextGaussian() * 0.01;
                    double d13 = (double)n4 * d8 + random2.nextGaussian() * 0.01;
                    double d14 = (double)n5 * d8 + random2.nextGaussian() * 0.01;
                    this.addParticleUnchecked(ParticleTypes.SMOKE, d9, d10, d11, d12, d13, d14);
                }
                break;
            }
            case 2001: {
                BlockState blockState = Block.getStateById(n2);
                if (!ReflectorForge.isAir(blockState, this.world, blockPos)) {
                    SoundType soundType = blockState.getSoundType();
                    if (Reflector.IForgeBlockState_getSoundType3.exists()) {
                        soundType = (SoundType)Reflector.call(blockState, Reflector.IForgeBlockState_getSoundType3, this.world, blockPos, null);
                    }
                    this.world.playSound(blockPos, soundType.getBreakSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0f) / 2.0f, soundType.getPitch() * 0.8f, true);
                }
                this.mc.particles.addBlockDestroyEffects(blockPos, blockState);
                break;
            }
            case 2002: 
            case 2007: {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockPos);
                for (int i = 0; i < 8; ++i) {
                    this.addParticleUnchecked(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), vector3d.x, vector3d.y, vector3d.z, random2.nextGaussian() * 0.15, random2.nextDouble() * 0.2, random2.nextGaussian() * 0.15);
                }
                float f = (float)(n2 >> 16 & 0xFF) / 255.0f;
                float f2 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                float f3 = (float)(n2 >> 0 & 0xFF) / 255.0f;
                BasicParticleType basicParticleType = n == 2007 ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;
                for (int i = 0; i < 100; ++i) {
                    double d = random2.nextDouble() * 4.0;
                    double d15 = random2.nextDouble() * Math.PI * 2.0;
                    double d16 = Math.cos(d15) * d;
                    double d17 = 0.01 + random2.nextDouble() * 0.5;
                    double d18 = Math.sin(d15) * d;
                    Particle particle = this.addParticleUnchecked(basicParticleType, basicParticleType.getType().getAlwaysShow(), vector3d.x + d16 * 0.1, vector3d.y + 0.3, vector3d.z + d18 * 0.1, d16, d17, d18);
                    if (particle == null) continue;
                    float f4 = 0.75f + random2.nextFloat() * 0.25f;
                    particle.setColor(f * f4, f2 * f4, f3 * f4);
                    particle.multiplyVelocity((float)d);
                }
                this.world.playSound(blockPos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 2003: {
                double d = (double)blockPos.getX() + 0.5;
                double d19 = blockPos.getY();
                double d20 = (double)blockPos.getZ() + 0.5;
                for (int i = 0; i < 8; ++i) {
                    this.addParticleUnchecked(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), d, d19, d20, random2.nextGaussian() * 0.15, random2.nextDouble() * 0.2, random2.nextGaussian() * 0.15);
                }
                for (double d21 = 0.0; d21 < Math.PI * 2; d21 += 0.15707963267948966) {
                    this.addParticleUnchecked(ParticleTypes.PORTAL, d + Math.cos(d21) * 5.0, d19 - 0.4, d20 + Math.sin(d21) * 5.0, Math.cos(d21) * -5.0, 0.0, Math.sin(d21) * -5.0);
                    this.addParticleUnchecked(ParticleTypes.PORTAL, d + Math.cos(d21) * 5.0, d19 - 0.4, d20 + Math.sin(d21) * 5.0, Math.cos(d21) * -7.0, 0.0, Math.sin(d21) * -7.0);
                }
                break;
            }
            case 2004: {
                for (int i = 0; i < 20; ++i) {
                    double d = (double)blockPos.getX() + 0.5 + (random2.nextDouble() - 0.5) * 2.0;
                    double d22 = (double)blockPos.getY() + 0.5 + (random2.nextDouble() - 0.5) * 2.0;
                    double d23 = (double)blockPos.getZ() + 0.5 + (random2.nextDouble() - 0.5) * 2.0;
                    this.world.addParticle(ParticleTypes.SMOKE, d, d22, d23, 0.0, 0.0, 0.0);
                    this.world.addParticle(ParticleTypes.FLAME, d, d22, d23, 0.0, 0.0, 0.0);
                }
                break;
            }
            case 2005: {
                BoneMealItem.spawnBonemealParticles(this.world, blockPos, n2);
                break;
            }
            case 2006: {
                for (int i = 0; i < 200; ++i) {
                    float f = random2.nextFloat() * 4.0f;
                    float f5 = random2.nextFloat() * ((float)Math.PI * 2);
                    double d = MathHelper.cos(f5) * f;
                    double d24 = 0.01 + random2.nextDouble() * 0.5;
                    double d25 = MathHelper.sin(f5) * f;
                    Particle particle = this.addParticleUnchecked(ParticleTypes.DRAGON_BREATH, false, (double)blockPos.getX() + d * 0.1, (double)blockPos.getY() + 0.3, (double)blockPos.getZ() + d25 * 0.1, d, d24, d25);
                    if (particle == null) continue;
                    particle.multiplyVelocity(f);
                }
                if (n2 != 1) break;
                this.world.playSound(blockPos, SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 1.0f, random2.nextFloat() * 0.1f + 0.9f, true);
                break;
            }
            case 2008: {
                this.world.addParticle(ParticleTypes.EXPLOSION, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
                break;
            }
            case 2009: {
                for (int i = 0; i < 8; ++i) {
                    this.world.addParticle(ParticleTypes.CLOUD, (double)blockPos.getX() + random2.nextDouble(), (double)blockPos.getY() + 1.2, (double)blockPos.getZ() + random2.nextDouble(), 0.0, 0.0, 0.0);
                }
                break;
            }
            case 3000: {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, true, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
                this.world.playSound(blockPos, SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.BLOCKS, 10.0f, (1.0f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2f) * 0.7f, true);
                break;
            }
            case 3001: {
                this.world.playSound(blockPos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 64.0f, 0.8f + this.world.rand.nextFloat() * 0.3f, true);
            }
        }
    }

    public void sendBlockBreakProgress(int n, BlockPos blockPos, int n2) {
        if (n2 >= 0 && n2 < 10) {
            DestroyBlockProgress destroyBlockProgress = (DestroyBlockProgress)this.damagedBlocks.get(n);
            if (destroyBlockProgress != null) {
                this.removeDamageProgress(destroyBlockProgress);
            }
            if (destroyBlockProgress == null || destroyBlockProgress.getPosition().getX() != blockPos.getX() || destroyBlockProgress.getPosition().getY() != blockPos.getY() || destroyBlockProgress.getPosition().getZ() != blockPos.getZ()) {
                destroyBlockProgress = new DestroyBlockProgress(n, blockPos);
                this.damagedBlocks.put(n, destroyBlockProgress);
            }
            destroyBlockProgress.setPartialBlockDamage(n2);
            destroyBlockProgress.setCloudUpdateTick(this.ticks);
            this.damageProgress.computeIfAbsent(destroyBlockProgress.getPosition().toLong(), WorldRenderer::lambda$sendBlockBreakProgress$8).add(destroyBlockProgress);
        } else {
            DestroyBlockProgress destroyBlockProgress = (DestroyBlockProgress)this.damagedBlocks.remove(n);
            if (destroyBlockProgress != null) {
                this.removeDamageProgress(destroyBlockProgress);
            }
        }
    }

    public boolean hasNoChunkUpdates() {
        return this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasNoChunkUpdates();
    }

    public void setDisplayListEntitiesDirty() {
        this.displayListEntitiesDirty = true;
        this.cloudsNeedUpdate = true;
    }

    public int getCountRenderers() {
        return this.viewFrustum.renderChunks.length;
    }

    public int getCountActiveRenderers() {
        return this.renderInfos.size();
    }

    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }

    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }

    public int getCountLoadedChunks() {
        if (this.world == null) {
            return 1;
        }
        ClientChunkProvider clientChunkProvider = this.world.getChunkProvider();
        return clientChunkProvider == null ? 0 : clientChunkProvider.getLoadedChunksCount();
    }

    public int getCountChunksToUpdate() {
        return this.chunksToUpdate.size();
    }

    public ChunkRenderDispatcher.ChunkRender getRenderChunk(BlockPos blockPos) {
        return this.viewFrustum.getRenderChunk(blockPos);
    }

    public ClientWorld getWorld() {
        return this.world;
    }

    private void clearRenderInfos() {
        if (renderEntitiesCounter > 0) {
            this.renderInfos = new ObjectArrayList<LocalRenderInformationContainer>(this.renderInfos.size() + 16);
            this.renderInfosEntities = new ArrayList<LocalRenderInformationContainer>(this.renderInfosEntities.size() + 16);
            this.renderInfosTileEntities = new ArrayList<LocalRenderInformationContainer>(this.renderInfosTileEntities.size() + 16);
        } else {
            this.renderInfos.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
        }
    }

    public void onPlayerPositionSet() {
        if (this.firstWorldLoad) {
            this.loadRenderers();
            this.firstWorldLoad = false;
        }
    }

    public void pauseChunkUpdates() {
        if (this.renderDispatcher != null) {
            this.renderDispatcher.pauseChunkUpdates();
        }
    }

    public void resumeChunkUpdates() {
        if (this.renderDispatcher != null) {
            this.renderDispatcher.resumeChunkUpdates();
        }
    }

    public int getFrameCount() {
        return this.frameId;
    }

    public int getNextFrameCount() {
        return ++this.frameId;
    }

    public RenderTypeBuffers getRenderTypeTextures() {
        return this.renderTypeTextures;
    }

    public List<LocalRenderInformationContainer> getRenderInfosEntities() {
        return this.renderInfosEntities;
    }

    public List<LocalRenderInformationContainer> getRenderInfosTileEntities() {
        return this.renderInfosTileEntities;
    }

    private void checkLoadVisibleChunks(ActiveRenderInfo activeRenderInfo, ClippingHelper clippingHelper, boolean bl) {
        if (this.loadVisibleChunksCounter == 0) {
            this.loadAllVisibleChunks(activeRenderInfo, clippingHelper, bl);
            this.mc.ingameGUI.getChatGUI().deleteChatLine(201435902);
        }
        if (this.loadVisibleChunksCounter > -1) {
            --this.loadVisibleChunksCounter;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadAllVisibleChunks(ActiveRenderInfo activeRenderInfo, ClippingHelper clippingHelper, boolean bl) {
        int n = this.mc.gameSettings.ofChunkUpdates;
        boolean bl2 = this.mc.gameSettings.ofLazyChunkLoading;
        try {
            this.mc.gameSettings.ofChunkUpdates = 1000;
            this.mc.gameSettings.ofLazyChunkLoading = false;
            WorldRenderer worldRenderer = Config.getRenderGlobal();
            int n2 = worldRenderer.getCountLoadedChunks();
            long l = System.currentTimeMillis();
            Config.dbg("Loading visible chunks");
            long l2 = System.currentTimeMillis() + 5000L;
            int n3 = 0;
            boolean bl3 = false;
            do {
                bl3 = false;
                for (int i = 0; i < 100; ++i) {
                    worldRenderer.setDisplayListEntitiesDirty();
                    worldRenderer.setupTerrain(activeRenderInfo, clippingHelper, false, this.frameId++, bl);
                    if (!worldRenderer.hasNoChunkUpdates()) {
                        bl3 = true;
                    }
                    n3 += worldRenderer.getCountChunksToUpdate();
                    while (!worldRenderer.hasNoChunkUpdates()) {
                        worldRenderer.updateChunks(System.nanoTime() + 1000000000L);
                    }
                    n3 -= worldRenderer.getCountChunksToUpdate();
                    if (!bl3) break;
                }
                if (worldRenderer.getCountLoadedChunks() != n2) {
                    bl3 = true;
                    n2 = worldRenderer.getCountLoadedChunks();
                }
                if (System.currentTimeMillis() <= l2) continue;
                Config.log("Chunks loaded: " + n3);
                l2 = System.currentTimeMillis() + 5000L;
            } while (bl3);
            Config.log("Chunks loaded: " + n3);
            Config.log("Finished loading visible chunks");
            ChunkRenderDispatcher.renderChunksUpdated = 0;
        } finally {
            this.mc.gameSettings.ofChunkUpdates = n;
            this.mc.gameSettings.ofLazyChunkLoading = bl2;
        }
    }

    public IResourceType getResourceType() {
        return VanillaResourceType.MODELS;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateTileEntities(Collection<TileEntity> collection, Collection<TileEntity> collection2) {
        Set<TileEntity> set = this.setTileEntities;
        synchronized (set) {
            this.setTileEntities.removeAll(collection);
            this.setTileEntities.addAll(collection2);
        }
    }

    public static int getCombinedLight(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, iBlockDisplayReader.getBlockState(blockPos), blockPos);
    }

    public static int getPackedLightmapCoords(IBlockDisplayReader iBlockDisplayReader, BlockState blockState, BlockPos blockPos) {
        int n;
        if (blockState.isEmissiveRendering(iBlockDisplayReader, blockPos)) {
            return 1;
        }
        int n2 = iBlockDisplayReader.getLightFor(LightType.SKY, blockPos);
        int n3 = iBlockDisplayReader.getLightFor(LightType.BLOCK, blockPos);
        if (n3 < (n = blockState.getLightValue(iBlockDisplayReader, blockPos))) {
            n3 = n;
        }
        int n4 = n2 << 20 | n3 << 4;
        if (Config.isDynamicLights() && iBlockDisplayReader instanceof IBlockReader && (!ambientOcclusion || !blockState.isOpaqueCube(iBlockDisplayReader, blockPos))) {
            n4 = DynamicLights.getCombinedLight(blockPos, n4);
        }
        return n4;
    }

    @Nullable
    public Framebuffer getEntityOutlineFramebuffer() {
        return this.entityOutlineFramebuffer;
    }

    @Nullable
    public Framebuffer func_239228_q_() {
        return this.field_239222_F_;
    }

    @Nullable
    public Framebuffer func_239229_r_() {
        return this.field_239223_G_;
    }

    @Nullable
    public Framebuffer func_239230_s_() {
        return this.field_239224_H_;
    }

    @Nullable
    public Framebuffer func_239231_t_() {
        return this.field_239225_I_;
    }

    @Nullable
    public Framebuffer func_239232_u_() {
        return this.field_239226_J_;
    }

    private static SortedSet lambda$sendBlockBreakProgress$8(long l) {
        return Sets.newTreeSet();
    }

    private static String lambda$addParticle$7(double d, double d2, double d3) throws Exception {
        return CrashReportCategory.getCoordinateInfo(d, d2, d3);
    }

    private static void lambda$drawShape$6(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, double d, double d2, double d3, float f, float f2, float f3, float f4, double d4, double d5, double d6, double d7, double d8, double d9) {
        iVertexBuilder.pos(matrix4f, (float)(d4 + d), (float)(d5 + d2), (float)(d6 + d3)).color(f, f2, f3, f4).endVertex();
        iVertexBuilder.pos(matrix4f, (float)(d7 + d), (float)(d8 + d2), (float)(d9 + d3)).color(f, f2, f3, f4).endVertex();
    }

    private static List lambda$renderBlockLayer$5(VboRegion vboRegion) {
        return new ArrayList();
    }

    private static Map lambda$renderBlockLayer$4(PairInt pairInt) {
        return new LinkedHashMap(8);
    }

    private static Map lambda$renderBlockLayer$3(RenderType renderType) {
        return new LinkedHashMap(16);
    }

    private static String lambda$renderBlockLayer$2(RenderType renderType) {
        return "render_" + renderType;
    }

    private static IVertexBuilder lambda$updateCameraAndRender$1(IRenderTypeBuffer.Impl impl, IVertexBuilder iVertexBuilder, RenderType renderType) {
        IVertexBuilder iVertexBuilder2 = impl.getBuffer(renderType);
        return renderType.isUseDelegate() ? VertexBuilderUtils.newDelegate(iVertexBuilder, iVertexBuilder2) : iVertexBuilder2;
    }

    private static double lambda$setupTerrain$0(BlockPos blockPos, LocalRenderInformationContainer localRenderInformationContainer) {
        return blockPos.distanceSq(localRenderInformationContainer.renderChunk.getPosition().add(8, 8, 8));
    }

    public static class ShaderException
    extends RuntimeException {
        public ShaderException(String string, Throwable throwable) {
            super(string, throwable);
        }
    }

    public static class LocalRenderInformationContainer {
        public final ChunkRenderDispatcher.ChunkRender renderChunk;
        private Direction facing;
        private int setFacing;
        private int counter;

        public LocalRenderInformationContainer(ChunkRenderDispatcher.ChunkRender chunkRender, @Nullable Direction direction, int n) {
            this.renderChunk = chunkRender;
            this.facing = direction;
            this.setFacing = n;
        }

        public void setDirection(byte by, Direction direction) {
            this.setFacing = this.setFacing | by | 1 << this.facing.ordinal();
        }

        public boolean hasDirection(Direction direction) {
            return (this.setFacing & 1 << direction.ordinal()) > 0;
        }

        private void initialize(Direction direction, int n, int n2) {
            this.facing = direction;
            this.setFacing = n;
            this.counter = n2;
        }
    }
}

