/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.overlay;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.venusfr;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.util.Direction;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.util.GuiPoint;
import net.optifine.util.GuiRect;
import net.optifine.util.GuiUtils;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.NativeMemory;

public class DebugOverlayGui
extends AbstractGui {
    private static final Map<Heightmap.Type, String> HEIGHTMAP_NAMES = Util.make(new EnumMap(Heightmap.Type.class), DebugOverlayGui::lambda$static$0);
    private final Minecraft mc;
    private final FontRenderer fontRenderer;
    private RayTraceResult rayTraceBlock;
    private RayTraceResult rayTraceFluid;
    @Nullable
    private ChunkPos chunkPos;
    @Nullable
    private Chunk chunk;
    @Nullable
    private CompletableFuture<Chunk> futureChunk;
    private String debugOF = null;
    private List<String> debugInfoLeft = null;
    private List<String> debugInfoRight = null;
    private long updateInfoLeftTimeMs = 0L;
    private long updateInfoRightTimeMs = 0L;

    public DebugOverlayGui(Minecraft minecraft) {
        this.mc = minecraft;
        this.fontRenderer = minecraft.fontRenderer;
    }

    public void resetChunk() {
        this.futureChunk = null;
        this.chunk = null;
    }

    public void render(MatrixStack matrixStack) {
        this.mc.getProfiler().startSection("debug");
        RenderSystem.pushMatrix();
        Entity entity2 = this.mc.getRenderViewEntity();
        this.rayTraceBlock = entity2.pick(20.0, 0.0f, true);
        this.rayTraceFluid = entity2.pick(20.0, 0.0f, false);
        this.renderDebugInfoLeft(matrixStack);
        this.renderDebugInfoRight(matrixStack);
        RenderSystem.popMatrix();
        if (this.mc.gameSettings.showLagometer) {
            int n = this.mc.getMainWindow().getScaledWidth();
            this.func_238509_a_(matrixStack, this.mc.getFrameTimer(), 0, n / 2, false);
            IntegratedServer integratedServer = this.mc.getIntegratedServer();
            if (integratedServer != null) {
                this.func_238509_a_(matrixStack, integratedServer.getFrameTimer(), n - Math.min(n / 2, 240), n / 2, true);
            }
        }
        this.mc.getProfiler().endSection();
    }

    protected void renderDebugInfoLeft(MatrixStack matrixStack) {
        List<String> list = this.debugInfoLeft;
        if (list == null || System.currentTimeMillis() > this.updateInfoLeftTimeMs) {
            list = this.getDebugInfoLeft();
            list.add("");
            boolean bl = this.mc.getIntegratedServer() != null;
            list.add("Debug: Pie [shift]: " + (this.mc.gameSettings.showDebugProfilerChart ? "visible" : "hidden") + (bl ? " FPS + TPS" : " FPS") + " [alt]: " + (this.mc.gameSettings.showLagometer ? "visible" : "hidden"));
            list.add("For help: press F3 + Q");
            this.debugInfoLeft = list;
            this.updateInfoLeftTimeMs = System.currentTimeMillis() + 100L;
        }
        GuiPoint[] guiPointArray = new GuiPoint[list.size()];
        GuiRect[] guiRectArray = new GuiRect[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (Strings.isNullOrEmpty(string)) continue;
            int n = 9;
            int n2 = this.fontRenderer.getStringWidth(string);
            int n3 = 2;
            int n4 = 2 + n * i;
            guiRectArray[i] = new GuiRect(1, n4 - 1, 2 + n2 + 1, n4 + n - 1);
            guiPointArray[i] = new GuiPoint(2, n4);
        }
        GuiUtils.fill(matrixStack.getLast().getMatrix(), guiRectArray, -1873784752);
        this.fontRenderer.renderStrings(list, guiPointArray, 0xE0E0E0, matrixStack.getLast().getMatrix(), false, this.fontRenderer.getBidiFlag());
    }

    protected void renderDebugInfoRight(MatrixStack matrixStack) {
        List<String> list = this.debugInfoRight;
        if (list == null || System.currentTimeMillis() > this.updateInfoRightTimeMs) {
            this.debugInfoRight = list = this.getDebugInfoRight();
            this.updateInfoRightTimeMs = System.currentTimeMillis() + 100L;
        }
        GuiPoint[] guiPointArray = new GuiPoint[list.size()];
        GuiRect[] guiRectArray = new GuiRect[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (Strings.isNullOrEmpty(string)) continue;
            int n = 9;
            int n2 = this.fontRenderer.getStringWidth(string);
            int n3 = this.mc.getMainWindow().getScaledWidth() - 2 - n2;
            int n4 = 2 + n * i;
            guiRectArray[i] = new GuiRect(n3 - 1, n4 - 1, n3 + n2 + 1, n4 + n - 1);
            guiPointArray[i] = new GuiPoint(n3, n4);
        }
        GuiUtils.fill(matrixStack.getLast().getMatrix(), guiRectArray, -1873784752);
        this.fontRenderer.renderStrings(list, guiPointArray, 0xE0E0E0, matrixStack.getLast().getMatrix(), false, this.fontRenderer.getBidiFlag());
    }

    protected List<String> getDebugInfoLeft() {
        int n;
        Object object;
        if (this.mc.debug != this.debugOF) {
            object = new StringBuffer(this.mc.debug);
            int n2 = Config.getChunkUpdates();
            int n3 = this.mc.debug.indexOf("T: ");
            if (n3 >= 0) {
                ((StringBuffer)object).insert(n3, "(" + n2 + " chunk updates) ");
            }
            int n4 = Config.getFpsMin();
            n = this.mc.debug.indexOf(" fps ");
            if (n >= 0) {
                ((StringBuffer)object).replace(0, n + 4, Config.getFpsString());
            }
            if (Config.isSmoothFps()) {
                ((StringBuffer)object).append(" sf");
            }
            if (Config.isFastRender()) {
                ((StringBuffer)object).append(" fr");
            }
            if (Config.isAnisotropicFiltering()) {
                ((StringBuffer)object).append(" af");
            }
            if (Config.isAntialiasing()) {
                ((StringBuffer)object).append(" aa");
            }
            if (Config.isRenderRegions()) {
                ((StringBuffer)object).append(" reg");
            }
            if (Config.isShaders()) {
                ((StringBuffer)object).append(" sh");
            }
            this.debugOF = this.mc.debug = ((StringBuffer)object).toString();
        }
        object = this.getInfoLeft();
        StringBuilder stringBuilder = new StringBuilder();
        AtlasTexture atlasTexture = Config.getTextureMap();
        stringBuilder.append(", A: ");
        if (SmartAnimations.isActive()) {
            stringBuilder.append(atlasTexture.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
            stringBuilder.append("/");
        }
        stringBuilder.append(atlasTexture.getCountAnimations() + TextureAnimations.getCountAnimations());
        String string = stringBuilder.toString();
        for (n = 0; n < object.size(); ++n) {
            Object object2 = (String)object.get(n);
            if (object2 == null || !((String)object2).startsWith("P: ")) continue;
            object2 = (String)object2 + string;
            object.set(n, object2);
            break;
        }
        return object;
    }

    protected List<String> getInfoLeft() {
        ShaderGroup shaderGroup;
        int n;
        IBlockReader iBlockReader;
        World world;
        IntegratedServer integratedServer = this.mc.getIntegratedServer();
        NetworkManager networkManager = this.mc.getConnection().getNetworkManager();
        float f = networkManager.getPacketsSent();
        float f2 = networkManager.getPacketsReceived();
        String string = integratedServer != null ? String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", Float.valueOf(integratedServer.getTickTime()), Float.valueOf(f), Float.valueOf(f2)) : String.format("\"%s\" server, %.0f tx, %.0f rx", this.mc.player.getServerBrand(), Float.valueOf(f), Float.valueOf(f2));
        BlockPos blockPos = this.mc.getRenderViewEntity().getPosition();
        if (this.mc.isReducedDebug()) {
            return Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, string, this.mc.worldRenderer.getDebugInfoRenders(), this.mc.worldRenderer.getDebugInfoEntities(), "P: " + this.mc.particles.getStatistics() + ". T: " + this.mc.world.getCountLoadedEntities(), this.mc.world.getProviderName(), "", String.format("Chunk-relative: %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF));
        }
        Entity entity2 = this.mc.getRenderViewEntity();
        Direction direction = entity2.getHorizontalFacing();
        String string2 = switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1 -> "Towards negative Z";
            case 2 -> "Towards positive Z";
            case 3 -> "Towards negative X";
            case 4 -> "Towards positive X";
            default -> "Invalid";
        };
        ChunkPos chunkPos = new ChunkPos(blockPos);
        if (!Objects.equals(this.chunkPos, chunkPos)) {
            this.chunkPos = chunkPos;
            this.resetChunk();
        }
        LongSets.EmptySet emptySet = (world = this.getWorld()) instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET;
        ArrayList<String> arrayList = Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + (String)("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType()) + ")", this.mc.debug, string, this.mc.worldRenderer.getDebugInfoRenders(), this.mc.worldRenderer.getDebugInfoEntities(), "P: " + this.mc.particles.getStatistics() + ". T: " + this.mc.world.getCountLoadedEntities(), this.mc.world.getProviderName());
        String string3 = this.getServerChunkStats();
        if (string3 != null) {
            arrayList.add(string3);
        }
        arrayList.add(this.mc.world.getDimensionKey().getLocation() + " FC: " + emptySet.size());
        arrayList.add("");
        arrayList.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.mc.getRenderViewEntity().getPosX(), this.mc.getRenderViewEntity().getPosY(), this.mc.getRenderViewEntity().getPosZ()));
        arrayList.add(String.format("Block: %d %d %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        arrayList.add(String.format("Chunk: %d %d %d in %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF, blockPos.getX() >> 4, blockPos.getY() >> 4, blockPos.getZ() >> 4));
        arrayList.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, string2, Float.valueOf(MathHelper.wrapDegrees(entity2.rotationYaw)), Float.valueOf(MathHelper.wrapDegrees(entity2.rotationPitch))));
        if (this.mc.world != null) {
            if (this.mc.world.isBlockLoaded(blockPos)) {
                iBlockReader = this.getChunk();
                if (((Chunk)iBlockReader).isEmpty()) {
                    arrayList.add("Waiting for chunk...");
                } else {
                    Object object;
                    int n2 = this.mc.world.getChunkProvider().getLightManager().getLightSubtracted(blockPos, 0);
                    int n3 = this.mc.world.getLightFor(LightType.SKY, blockPos);
                    n = this.mc.world.getLightFor(LightType.BLOCK, blockPos);
                    arrayList.add("Client Light: " + n2 + " (" + n3 + " sky, " + n + " block)");
                    Chunk chunk = this.getServerChunk();
                    if (chunk != null) {
                        object = world.getChunkProvider().getLightManager();
                        arrayList.add("Server Light: (" + ((WorldLightManager)object).getLightEngine(LightType.SKY).getLightFor(blockPos) + " sky, " + ((WorldLightManager)object).getLightEngine(LightType.BLOCK).getLightFor(blockPos) + " block)");
                    } else {
                        arrayList.add("Server Light: (?? sky, ?? block)");
                    }
                    object = new StringBuilder("CH");
                    for (Heightmap.Type object2 : Heightmap.Type.values()) {
                        if (!object2.isUsageClient()) continue;
                        ((StringBuilder)object).append(" ").append(HEIGHTMAP_NAMES.get(object2)).append(": ").append(((Chunk)iBlockReader).getTopBlockY(object2, blockPos.getX(), blockPos.getZ()));
                    }
                    arrayList.add(((StringBuilder)object).toString());
                    ((StringBuilder)object).setLength(0);
                    ((StringBuilder)object).append("SH");
                    for (Heightmap.Type type : Heightmap.Type.values()) {
                        if (!type.isUsageNotWorldgen()) continue;
                        ((StringBuilder)object).append(" ").append(HEIGHTMAP_NAMES.get(type)).append(": ");
                        if (chunk != null) {
                            ((StringBuilder)object).append(chunk.getTopBlockY(type, blockPos.getX(), blockPos.getZ()));
                            continue;
                        }
                        ((StringBuilder)object).append("??");
                    }
                    arrayList.add(((StringBuilder)object).toString());
                    if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
                        arrayList.add("Biome: " + this.mc.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(this.mc.world.getBiome(blockPos)));
                        long l = 0L;
                        float f3 = 0.0f;
                        if (chunk != null) {
                            f3 = world.getMoonFactor();
                            l = chunk.getInhabitedTime();
                        }
                        DifficultyInstance difficultyInstance = new DifficultyInstance(world.getDifficulty(), world.getDayTime(), l, f3);
                        arrayList.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", Float.valueOf(difficultyInstance.getAdditionalDifficulty()), Float.valueOf(difficultyInstance.getClampedAdditionalDifficulty()), this.mc.world.getDayTime() / 24000L));
                    }
                }
            } else {
                arrayList.add("Outside of world...");
            }
        } else {
            arrayList.add("Outside of world...");
        }
        iBlockReader = this.func_238515_d_();
        if (iBlockReader != null) {
            WorldEntitySpawner.EntityDensityManager entityDensityManager = ((ServerWorld)iBlockReader).getChunkProvider().func_241101_k_();
            if (entityDensityManager != null) {
                Object2IntMap<EntityClassification> object2IntMap = entityDensityManager.func_234995_b_();
                n = entityDensityManager.func_234988_a_();
                arrayList.add("SC: " + n + ", " + Stream.of(EntityClassification.values()).map(arg_0 -> DebugOverlayGui.lambda$getInfoLeft$1(object2IntMap, arg_0)).collect(Collectors.joining(", ")));
            } else {
                arrayList.add("SC: N/A");
            }
        }
        if ((shaderGroup = this.mc.gameRenderer.getShaderGroup()) != null) {
            arrayList.add("Shader: " + shaderGroup.getShaderGroupName());
        }
        arrayList.add(this.mc.getSoundHandler().getDebugString() + String.format(" (Mood %d%%)", Math.round(this.mc.player.getDarknessAmbience() * 100.0f)));
        return arrayList;
    }

    @Nullable
    private ServerWorld func_238515_d_() {
        IntegratedServer integratedServer = this.mc.getIntegratedServer();
        return integratedServer != null ? integratedServer.getWorld(this.mc.world.getDimensionKey()) : null;
    }

    @Nullable
    private String getServerChunkStats() {
        ServerWorld serverWorld = this.func_238515_d_();
        return serverWorld != null ? serverWorld.getProviderName() : null;
    }

    private World getWorld() {
        return DataFixUtils.orElse(Optional.ofNullable(this.mc.getIntegratedServer()).flatMap(this::lambda$getWorld$2), this.mc.world);
    }

    @Nullable
    private Chunk getServerChunk() {
        if (this.futureChunk == null) {
            ServerWorld serverWorld = this.func_238515_d_();
            if (serverWorld != null) {
                this.futureChunk = serverWorld.getChunkProvider().func_217232_b(this.chunkPos.x, this.chunkPos.z, ChunkStatus.FULL, true).thenApply(DebugOverlayGui::lambda$getServerChunk$5);
            }
            if (this.futureChunk == null) {
                this.futureChunk = CompletableFuture.completedFuture(this.getChunk());
            }
        }
        return this.futureChunk.getNow(null);
    }

    private Chunk getChunk() {
        if (this.chunk == null) {
            this.chunk = this.mc.world.getChunk(this.chunkPos.x, this.chunkPos.z);
        }
        return this.chunk;
    }

    protected List<String> getDebugInfoRight() {
        Collection<ResourceLocation> collection;
        Object object;
        Iterator iterator2;
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        long l = Runtime.getRuntime().maxMemory();
        long l2 = Runtime.getRuntime().totalMemory();
        long l3 = Runtime.getRuntime().freeMemory();
        long l4 = l2 - l3;
        ArrayList<String> arrayList = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l4 * 100L / l, DebugOverlayGui.bytesToMb(l4), DebugOverlayGui.bytesToMb(l)), String.format("Allocated: % 2d%% %03dMB", l2 * 100L / l, DebugOverlayGui.bytesToMb(l2)), "", String.format("CPU: %s", PlatformDescriptors.getCpuInfo()), "", String.format("Display: %dx%d (%s)", Minecraft.getInstance().getMainWindow().getFramebufferWidth(), Minecraft.getInstance().getMainWindow().getFramebufferHeight(), PlatformDescriptors.getGlVendor()), PlatformDescriptors.getGlRenderer(), PlatformDescriptors.getGlVersion());
        long l5 = NativeMemory.getBufferAllocated();
        long l6 = NativeMemory.getBufferMaximum();
        long l7 = NativeMemory.getImageAllocated();
        String string = "Native: " + DebugOverlayGui.bytesToMb(l5) + "/" + DebugOverlayGui.bytesToMb(l6) + "+" + DebugOverlayGui.bytesToMb(l7) + "MB";
        arrayList.add(3, string);
        arrayList.set(4, "Allocation: " + MemoryMonitor.getAllocationRateAvgMb() + "MB/s");
        if (Reflector.BrandingControl_getBrandings.exists()) {
            arrayList.add("");
            iterator2 = ((Set)Reflector.call(Reflector.BrandingControl_getBrandings, true, false)).iterator();
            while (iterator2.hasNext()) {
                object = (String)iterator2.next();
                if (((String)object).startsWith("Minecraft ")) continue;
                arrayList.add((String)object);
            }
        }
        if (this.mc.isReducedDebug()) {
            return arrayList;
        }
        if (this.rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
            iterator2 = ((BlockRayTraceResult)this.rayTraceBlock).getPos();
            object = this.mc.world.getBlockState((BlockPos)((Object)iterator2));
            arrayList.add("");
            arrayList.add(TextFormatting.UNDERLINE + "Targeted Block: " + ((Vector3i)((Object)iterator2)).getX() + ", " + ((Vector3i)((Object)iterator2)).getY() + ", " + ((Vector3i)((Object)iterator2)).getZ());
            arrayList.add(String.valueOf(Registry.BLOCK.getKey(((AbstractBlock.AbstractBlockState)object).getBlock())));
            for (Iterator<ResourceLocation> iterator3 : ((StateHolder)object).getValues().entrySet()) {
                arrayList.add(this.getPropertyString((Map.Entry<Property<?>, Comparable<?>>)((Object)iterator3)));
            }
            collection = Reflector.IForgeBlock_getTags.exists() ? (Collection)Reflector.call(((AbstractBlock.AbstractBlockState)object).getBlock(), Reflector.IForgeBlock_getTags, new Object[0]) : this.mc.getConnection().getTags().getBlockTags().getOwningTags(((AbstractBlock.AbstractBlockState)object).getBlock());
            for (ResourceLocation resourceLocation : collection) {
                arrayList.add("#" + resourceLocation);
            }
        }
        if (this.rayTraceFluid.getType() == RayTraceResult.Type.BLOCK) {
            iterator2 = ((BlockRayTraceResult)this.rayTraceFluid).getPos();
            object = this.mc.world.getFluidState((BlockPos)((Object)iterator2));
            arrayList.add("");
            arrayList.add(TextFormatting.UNDERLINE + "Targeted Fluid: " + ((Vector3i)((Object)iterator2)).getX() + ", " + ((Vector3i)((Object)iterator2)).getY() + ", " + ((Vector3i)((Object)iterator2)).getZ());
            arrayList.add(String.valueOf(Registry.FLUID.getKey(((FluidState)object).getFluid())));
            for (Iterator<ResourceLocation> iterator3 : ((StateHolder)object).getValues().entrySet()) {
                arrayList.add(this.getPropertyString((Map.Entry<Property<?>, Comparable<?>>)((Object)iterator3)));
            }
            collection = Reflector.ForgeFluid_getTags.exists() ? (Collection<ResourceLocation>)Reflector.call(((FluidState)object).getFluid(), Reflector.ForgeFluid_getTags, new Object[0]) : this.mc.getConnection().getTags().getFluidTags().getOwningTags(((FluidState)object).getFluid());
            for (ResourceLocation resourceLocation : collection) {
                arrayList.add("#" + resourceLocation);
            }
        }
        if ((iterator2 = this.mc.pointedEntity) != null) {
            arrayList.add("");
            arrayList.add(TextFormatting.UNDERLINE + "Targeted Entity");
            arrayList.add(String.valueOf(Registry.ENTITY_TYPE.getKey(((Entity)((Object)iterator2)).getType())));
            if (Reflector.ForgeEntityType_getTags.exists()) {
                object = (Collection)Reflector.call(((Entity)((Object)iterator2)).getType(), Reflector.ForgeEntityType_getTags, new Object[0]);
                object.forEach(arg_0 -> DebugOverlayGui.lambda$getDebugInfoRight$6(arrayList, arg_0));
            }
        }
        return arrayList;
    }

    private String getPropertyString(Map.Entry<Property<?>, Comparable<?>> entry) {
        Property<?> property = entry.getKey();
        Comparable<?> comparable = entry.getValue();
        Object object = Util.getValueName(property, comparable);
        if (Boolean.TRUE.equals(comparable)) {
            object = TextFormatting.GREEN + (String)object;
        } else if (Boolean.FALSE.equals(comparable)) {
            object = TextFormatting.RED + (String)object;
        }
        return property.getName() + ": " + (String)object;
    }

    private void func_238509_a_(MatrixStack matrixStack, FrameTimer frameTimer, int n, int n2, boolean bl) {
        if (!bl) {
            int n3;
            int n4 = (int)(512.0 / this.mc.getMainWindow().getGuiScaleFactor());
            n = Math.max(n, n4);
            n2 = this.mc.getMainWindow().getScaledWidth() - n;
            RenderSystem.disableDepthTest();
            int n5 = frameTimer.getLastIndex();
            int n6 = frameTimer.getIndex();
            long[] lArray = frameTimer.getFrames();
            int n7 = n;
            int n8 = Math.max(0, lArray.length - n2);
            int n9 = lArray.length - n8;
            int n10 = frameTimer.parseIndex(n5 + n8);
            long l = 0L;
            int n11 = Integer.MAX_VALUE;
            int n12 = Integer.MIN_VALUE;
            for (n3 = 0; n3 < n9; ++n3) {
                int n13 = (int)(lArray[frameTimer.parseIndex(n10 + n3)] / 1000000L);
                n11 = Math.min(n11, n13);
                n12 = Math.max(n12, n13);
                l += (long)n13;
            }
            n3 = this.mc.getMainWindow().getScaledHeight();
            DebugOverlayGui.fill(matrixStack, n, n3 - 60, n + n9, n3, -1873784752);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.enableBlend();
            RenderSystem.disableTexture();
            RenderSystem.defaultBlendFunc();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            Object object = TransformationMatrix.identity().getMatrix();
            while (n10 != n6) {
                int n14 = frameTimer.getLineHeight(lArray[n10], bl ? 30 : 60, bl ? 60 : 20);
                int n15 = bl ? 100 : 60;
                int n16 = this.getFrameColor(MathHelper.clamp(n14, 0, n15), 0, n15 / 2, n15);
                int n17 = n16 >> 24 & 0xFF;
                int n18 = n16 >> 16 & 0xFF;
                int n19 = n16 >> 8 & 0xFF;
                int n20 = n16 & 0xFF;
                bufferBuilder.pos((Matrix4f)object, n7 + 1, n3, 0.0f).color(n18, n19, n20, n17).endVertex();
                bufferBuilder.pos((Matrix4f)object, n7 + 1, n3 - n14 + 1, 0.0f).color(n18, n19, n20, n17).endVertex();
                bufferBuilder.pos((Matrix4f)object, n7, n3 - n14 + 1, 0.0f).color(n18, n19, n20, n17).endVertex();
                bufferBuilder.pos((Matrix4f)object, n7, n3, 0.0f).color(n18, n19, n20, n17).endVertex();
                ++n7;
                n10 = frameTimer.parseIndex(n10 + 1);
            }
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            if (bl) {
                DebugOverlayGui.fill(matrixStack, n + 1, n3 - 30 + 1, n + 14, n3 - 30 + 10, -1873784752);
                this.fontRenderer.drawString(matrixStack, "60 FPS", n + 2, n3 - 30 + 2, 0xE0E0E0);
                this.hLine(matrixStack, n, n + n9 - 1, n3 - 30, -1);
                DebugOverlayGui.fill(matrixStack, n + 1, n3 - 60 + 1, n + 14, n3 - 60 + 10, -1873784752);
                this.fontRenderer.drawString(matrixStack, "30 FPS", n + 2, n3 - 60 + 2, 0xE0E0E0);
                this.hLine(matrixStack, n, n + n9 - 1, n3 - 60, -1);
            } else {
                DebugOverlayGui.fill(matrixStack, n + 1, n3 - 60 + 1, n + 14, n3 - 60 + 10, -1873784752);
                this.fontRenderer.drawString(matrixStack, "20 TPS", n + 2, n3 - 60 + 2, 0xE0E0E0);
                this.hLine(matrixStack, n, n + n9 - 1, n3 - 60, -1);
            }
            this.hLine(matrixStack, n, n + n9 - 1, n3 - 1, -1);
            this.vLine(matrixStack, n, n3 - 60, n3, -1);
            this.vLine(matrixStack, n + n9 - 1, n3 - 60, n3, -1);
            if (bl && this.mc.gameSettings.framerateLimit > 0 && this.mc.gameSettings.framerateLimit <= 250) {
                this.hLine(matrixStack, n, n + n9 - 1, n3 - 1 - (int)(1800.0 / (double)this.mc.gameSettings.framerateLimit), -16711681);
            }
            object = n11 + " ms min";
            String string = l / (long)n9 + " ms avg";
            String string2 = n12 + " ms max";
            this.fontRenderer.drawStringWithShadow(matrixStack, (String)object, n + 2, n3 - 60 - 9, 0xE0E0E0);
            this.fontRenderer.drawStringWithShadow(matrixStack, string, n + n9 / 2 - this.fontRenderer.getStringWidth(string) / 2, n3 - 60 - 9, 0xE0E0E0);
            this.fontRenderer.drawStringWithShadow(matrixStack, string2, n + n9 - this.fontRenderer.getStringWidth(string2), n3 - 60 - 9, 0xE0E0E0);
            RenderSystem.enableDepthTest();
        }
    }

    private int getFrameColor(int n, int n2, int n3, int n4) {
        return n < n3 ? this.blendColors(-16711936, -256, (float)n / (float)n3) : this.blendColors(-256, -65536, (float)(n - n3) / (float)(n4 - n3));
    }

    private int blendColors(int n, int n2, float f) {
        int n3 = n >> 24 & 0xFF;
        int n4 = n >> 16 & 0xFF;
        int n5 = n >> 8 & 0xFF;
        int n6 = n & 0xFF;
        int n7 = n2 >> 24 & 0xFF;
        int n8 = n2 >> 16 & 0xFF;
        int n9 = n2 >> 8 & 0xFF;
        int n10 = n2 & 0xFF;
        int n11 = MathHelper.clamp((int)MathHelper.lerp(f, n3, n7), 0, 255);
        int n12 = MathHelper.clamp((int)MathHelper.lerp(f, n4, n8), 0, 255);
        int n13 = MathHelper.clamp((int)MathHelper.lerp(f, n5, n9), 0, 255);
        int n14 = MathHelper.clamp((int)MathHelper.lerp(f, n6, n10), 0, 255);
        return n11 << 24 | n12 << 16 | n13 << 8 | n14;
    }

    public static long bytesToMb(long l) {
        return l / 1024L / 1024L;
    }

    private static void lambda$getDebugInfoRight$6(List list, ResourceLocation resourceLocation) {
        list.add("#" + resourceLocation);
    }

    private static Chunk lambda$getServerChunk$5(Either either) {
        return either.map(DebugOverlayGui::lambda$getServerChunk$3, DebugOverlayGui::lambda$getServerChunk$4);
    }

    private static Chunk lambda$getServerChunk$4(ChunkHolder.IChunkLoadingError iChunkLoadingError) {
        return null;
    }

    private static Chunk lambda$getServerChunk$3(IChunk iChunk) {
        return (Chunk)iChunk;
    }

    private Optional lambda$getWorld$2(IntegratedServer integratedServer) {
        return Optional.ofNullable(integratedServer.getWorld(this.mc.world.getDimensionKey()));
    }

    private static String lambda$getInfoLeft$1(Object2IntMap object2IntMap, EntityClassification entityClassification) {
        return Character.toUpperCase(entityClassification.getName().charAt(0)) + ": " + object2IntMap.getInt(entityClassification);
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(Heightmap.Type.WORLD_SURFACE_WG, "SW");
        enumMap.put(Heightmap.Type.WORLD_SURFACE, "S");
        enumMap.put(Heightmap.Type.OCEAN_FLOOR_WG, "OW");
        enumMap.put(Heightmap.Type.OCEAN_FLOOR, "O");
        enumMap.put(Heightmap.Type.MOTION_BLOCKING, "M");
        enumMap.put(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, "ML");
    }
}

