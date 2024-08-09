package net.minecraft.client.gui.overlay;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.lighting.WorldLightManager;
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

public class DebugOverlayGui extends AbstractGui
{
    private static final Map<Heightmap.Type, String> HEIGHTMAP_NAMES = Util.make(new EnumMap<>(Heightmap.Type.class), (p_lambda$static$0_0_) ->
    {
        p_lambda$static$0_0_.put(Heightmap.Type.WORLD_SURFACE_WG, "SW");
        p_lambda$static$0_0_.put(Heightmap.Type.WORLD_SURFACE, "S");
        p_lambda$static$0_0_.put(Heightmap.Type.OCEAN_FLOOR_WG, "OW");
        p_lambda$static$0_0_.put(Heightmap.Type.OCEAN_FLOOR, "O");
        p_lambda$static$0_0_.put(Heightmap.Type.MOTION_BLOCKING, "M");
        p_lambda$static$0_0_.put(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, "ML");
    });
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

    public DebugOverlayGui(Minecraft mc)
    {
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
    }

    public void resetChunk()
    {
        this.futureChunk = null;
        this.chunk = null;
    }

    public void render(MatrixStack p_194818_1_)
    {
        this.mc.getProfiler().startSection("debug");
        RenderSystem.pushMatrix();
        Entity entity = this.mc.getRenderViewEntity();
        this.rayTraceBlock = entity.pick(20.0D, 0.0F, false);
        this.rayTraceFluid = entity.pick(20.0D, 0.0F, true);
        this.renderDebugInfoLeft(p_194818_1_);
        this.renderDebugInfoRight(p_194818_1_);
        RenderSystem.popMatrix();

        if (this.mc.gameSettings.showLagometer)
        {
            int i = this.mc.getMainWindow().getScaledWidth();
            this.func_238509_a_(p_194818_1_, this.mc.getFrameTimer(), 0, i / 2, true);
            IntegratedServer integratedserver = this.mc.getIntegratedServer();

            if (integratedserver != null)
            {
                this.func_238509_a_(p_194818_1_, integratedserver.getFrameTimer(), i - Math.min(i / 2, 240), i / 2, false);
            }
        }

        this.mc.getProfiler().endSection();
    }

    protected void renderDebugInfoLeft(MatrixStack p_230024_1_)
    {
        List<String> list = this.debugInfoLeft;

        if (list == null || System.currentTimeMillis() > this.updateInfoLeftTimeMs)
        {
            list = this.getDebugInfoLeft();

            this.debugInfoLeft = list;
            this.updateInfoLeftTimeMs = System.currentTimeMillis() + 100L;
        }

        GuiPoint[] aguipoint = new GuiPoint[list.size()];
        GuiRect[] aguirect = new GuiRect[list.size()];

        for (int i = 0; i < list.size(); ++i)
        {
            String s = list.get(i);

            if (!Strings.isNullOrEmpty(s))
            {
                int j = 9;
                int k = this.fontRenderer.getStringWidth(s);
                int l = 2;
                int i1 = 2 + j * i;
                aguirect[i] = new GuiRect(1, i1 - 1, 2 + k + 1, i1 + j - 1);
                aguipoint[i] = new GuiPoint(2, i1);
            }
        }

        GuiUtils.fill(p_230024_1_.getLast().getMatrix(), aguirect, -1873784752);
        this.fontRenderer.renderStrings(list, aguipoint, 14737632, p_230024_1_.getLast().getMatrix(), false, this.fontRenderer.getBidiFlag());
    }

    protected void renderDebugInfoRight(MatrixStack p_230025_1_)
    {
        List<String> list = this.debugInfoRight;

        if (list == null || System.currentTimeMillis() > this.updateInfoRightTimeMs)
        {
            list = this.getDebugInfoRight();
            this.debugInfoRight = list;
            this.updateInfoRightTimeMs = System.currentTimeMillis() + 100L;
        }

        GuiPoint[] aguipoint = new GuiPoint[list.size()];
        GuiRect[] aguirect = new GuiRect[list.size()];

        for (int i = 0; i < list.size(); ++i)
        {
            String s = list.get(i);

            if (!Strings.isNullOrEmpty(s))
            {
                int j = 9;
                int k = this.fontRenderer.getStringWidth(s);
                int l = this.mc.getMainWindow().getScaledWidth() - 2 - k;
                int i1 = 2 + j * i;
                aguirect[i] = new GuiRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1);
                aguipoint[i] = new GuiPoint(l, i1);
            }
        }

        GuiUtils.fill(p_230025_1_.getLast().getMatrix(), aguirect, -1873784752);
        this.fontRenderer.renderStrings(list, aguipoint, 14737632, p_230025_1_.getLast().getMatrix(), false, this.fontRenderer.getBidiFlag());
    }

    protected List<String> getDebugInfoLeft()
    {
        List<String> list = this.getInfoLeft();
        return list;
    }

    protected List<String> getInfoLeft()
    {
        IntegratedServer integratedserver = this.mc.getIntegratedServer();
        NetworkManager networkmanager = this.mc.getConnection().getNetworkManager();
        float f = networkmanager.getPacketsSent();
        float f1 = networkmanager.getPacketsReceived();
        String s;

        if (integratedserver != null)
        {
            s = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", integratedserver.getTickTime(), f, f1);
        }
        else
        {
            s = String.format("\"%s\" server, %.0f tx, %.0f rx", this.mc.player.getServerBrand(), f, f1);
        }

        BlockPos blockpos = this.mc.getRenderViewEntity().getPosition();

        if (this.mc.isReducedDebug())
        {
            return Lists.newArrayList("Minecraft " + SharedConstants.getVersion().getName() + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, s);
        }
        else
        {
            Entity entity = this.mc.getRenderViewEntity();
            Direction direction = entity.getHorizontalFacing();
            String s1;

            switch (direction)
            {
                case NORTH:
                    s1 = "-Z";
                    break;

                case SOUTH:
                    s1 = "+Z";
                    break;

                case WEST:
                    s1 = "-X";
                    break;

                case EAST:
                    s1 = "+X";
                    break;

                default:
                    s1 = "Invalid";
            }

            ChunkPos chunkpos = new ChunkPos(blockpos);

            if (!Objects.equals(this.chunkPos, chunkpos))
            {
                this.chunkPos = chunkpos;
                this.resetChunk();
            }

            World world = this.getWorld();
            LongSet longset = (LongSet)(world instanceof ServerWorld ? ((ServerWorld)world).getForcedChunks() : LongSets.EMPTY_SET);
            List<String> list = Lists.newArrayList("FecuritySQ" + " (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType()) + ")", this.mc.debug, s);

            list.add("Location: " + this.mc.world.getDimensionKey().getLocation());
            list.add("");
            list.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.mc.getRenderViewEntity().getPosX(), this.mc.getRenderViewEntity().getPosY(), this.mc.getRenderViewEntity().getPosZ()));
            list.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", direction, s1, MathHelper.wrapDegrees(entity.rotationYaw), MathHelper.wrapDegrees(entity.rotationPitch)));

            if (this.mc.world != null)
            {
                if (this.mc.world.isBlockLoaded(blockpos))
                {
                    Chunk chunk = this.getChunk();

                    if (chunk.isEmpty())
                    {
                        list.add("Waiting for chunk...");
                    }
                    else
                    {
                        Chunk chunk1 = this.getServerChunk();

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256)
                        {
                            list.add("Biome: " + this.mc.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(this.mc.world.getBiome(blockpos)));
                            long i1 = 0L;
                            float f2 = 0.0F;

                            if (chunk1 != null)
                            {
                                f2 = world.getMoonFactor();
                                i1 = chunk1.getInhabitedTime();
                            }
                         }
                    }
                }
                else
                {
                    list.add("Outside of world...");
                }
            }
            else
            {
                list.add("Outside of world...");
            }

            ShaderGroup shadergroup = this.mc.gameRenderer.getShaderGroup();

            if (shadergroup != null)
            {
                list.add("Shader: " + shadergroup.getShaderGroupName());
            }
            return list;
        }
    }

    @Nullable
    private ServerWorld func_238515_d_()
    {
        IntegratedServer integratedserver = this.mc.getIntegratedServer();
        return integratedserver != null ? integratedserver.getWorld(this.mc.world.getDimensionKey()) : null;
    }

    @Nullable
    private String getServerChunkStats()
    {
        ServerWorld serverworld = this.func_238515_d_();
        return serverworld != null ? serverworld.getProviderName() : null;
    }

    private World getWorld()
    {
        return DataFixUtils.orElse(Optional.ofNullable(this.mc.getIntegratedServer()).flatMap((p_lambda$getWorld$2_1_) ->
        {
            return Optional.ofNullable(p_lambda$getWorld$2_1_.getWorld(this.mc.world.getDimensionKey()));
        }), this.mc.world);
    }

    @Nullable
    private Chunk getServerChunk()
    {
        if (this.futureChunk == null)
        {
            ServerWorld serverworld = this.func_238515_d_();

            if (serverworld != null)
            {
                this.futureChunk = serverworld.getChunkProvider().func_217232_b(this.chunkPos.x, this.chunkPos.z, ChunkStatus.FULL, false).thenApply((p_lambda$getServerChunk$5_0_) ->
                {
                    return p_lambda$getServerChunk$5_0_.map((p_lambda$null$3_0_) -> {
                        return (Chunk)p_lambda$null$3_0_;
                    }, (p_lambda$null$4_0_) -> {
                        return null;
                    });
                });
            }

            if (this.futureChunk == null)
            {
                this.futureChunk = CompletableFuture.completedFuture(this.getChunk());
            }
        }

        return this.futureChunk.getNow((Chunk)null);
    }

    private Chunk getChunk()
    {
        if (this.chunk == null)
        {
            this.chunk = this.mc.world.getChunk(this.chunkPos.x, this.chunkPos.z);
        }

        return this.chunk;
    }

    protected List<String> getDebugInfoRight()
    {
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;
        List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i)), "", String.format("CPU: %s", PlatformDescriptors.getCpuInfo()), "", String.format("Display: %dx%d (%s)", Minecraft.getInstance().getMainWindow().getFramebufferWidth(), Minecraft.getInstance().getMainWindow().getFramebufferHeight(), PlatformDescriptors.getGlVendor()), PlatformDescriptors.getGlRenderer(), PlatformDescriptors.getGlVersion());
        return list;
    }

    private String getPropertyString(Entry < Property<?>, Comparable<? >> entryIn)
    {
        Property<?> property = entryIn.getKey();
        Comparable<?> comparable = entryIn.getValue();
        String s = Util.getValueName(property, comparable);

        if (Boolean.TRUE.equals(comparable))
        {
            s = TextFormatting.GREEN + s;
        }
        else if (Boolean.FALSE.equals(comparable))
        {
            s = TextFormatting.RED + s;
        }

        return property.getName() + ": " + s;
    }

    private void func_238509_a_(MatrixStack p_238509_1_, FrameTimer p_238509_2_, int p_238509_3_, int p_238509_4_, boolean p_238509_5_)
    {
        if (!p_238509_5_)
        {
            int i = (int)(512.0D / this.mc.getMainWindow().getGuiScaleFactor());
            p_238509_3_ = Math.max(p_238509_3_, i);
            p_238509_4_ = this.mc.getMainWindow().getScaledWidth() - p_238509_3_;
            RenderSystem.disableDepthTest();
            int j = p_238509_2_.getLastIndex();
            int k = p_238509_2_.getIndex();
            long[] along = p_238509_2_.getFrames();
            int l = p_238509_3_;
            int i1 = Math.max(0, along.length - p_238509_4_);
            int j1 = along.length - i1;
            int k1 = p_238509_2_.parseIndex(j + i1);
            long l1 = 0L;
            int i2 = Integer.MAX_VALUE;
            int j2 = Integer.MIN_VALUE;

            for (int k2 = 0; k2 < j1; ++k2)
            {
                int l2 = (int)(along[p_238509_2_.parseIndex(k1 + k2)] / 1000000L);
                i2 = Math.min(i2, l2);
                j2 = Math.max(j2, l2);
                l1 += (long)l2;
            }

            int l4 = this.mc.getMainWindow().getScaledHeight();
            fill(p_238509_1_, p_238509_3_, l4 - 60, p_238509_3_ + j1, l4, -1873784752);
            BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
            RenderSystem.enableBlend();
            RenderSystem.disableTexture();
            RenderSystem.defaultBlendFunc();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);

            for (Matrix4f matrix4f = TransformationMatrix.identity().getMatrix(); k1 != k; k1 = p_238509_2_.parseIndex(k1 + 1))
            {
                int i3 = p_238509_2_.getLineHeight(along[k1], p_238509_5_ ? 30 : 60, p_238509_5_ ? 60 : 20);
                int j3 = p_238509_5_ ? 100 : 60;
                int k3 = this.getFrameColor(MathHelper.clamp(i3, 0, j3), 0, j3 / 2, j3);
                int l3 = k3 >> 24 & 255;
                int i4 = k3 >> 16 & 255;
                int j4 = k3 >> 8 & 255;
                int k4 = k3 & 255;
                bufferbuilder.pos(matrix4f, (float)(l + 1), (float)l4, 0.0F).color(i4, j4, k4, l3).endVertex();
                bufferbuilder.pos(matrix4f, (float)(l + 1), (float)(l4 - i3 + 1), 0.0F).color(i4, j4, k4, l3).endVertex();
                bufferbuilder.pos(matrix4f, (float)l, (float)(l4 - i3 + 1), 0.0F).color(i4, j4, k4, l3).endVertex();
                bufferbuilder.pos(matrix4f, (float)l, (float)l4, 0.0F).color(i4, j4, k4, l3).endVertex();
                ++l;
            }

            bufferbuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferbuilder);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();

            if (p_238509_5_)
            {
                fill(p_238509_1_, p_238509_3_ + 1, l4 - 30 + 1, p_238509_3_ + 14, l4 - 30 + 10, -1873784752);
                this.fontRenderer.drawString(p_238509_1_, "60 FPS", (float)(p_238509_3_ + 2), (float)(l4 - 30 + 2), 14737632);
                this.hLine(p_238509_1_, p_238509_3_, p_238509_3_ + j1 - 1, l4 - 30, -1);
                fill(p_238509_1_, p_238509_3_ + 1, l4 - 60 + 1, p_238509_3_ + 14, l4 - 60 + 10, -1873784752);
                this.fontRenderer.drawString(p_238509_1_, "30 FPS", (float)(p_238509_3_ + 2), (float)(l4 - 60 + 2), 14737632);
                this.hLine(p_238509_1_, p_238509_3_, p_238509_3_ + j1 - 1, l4 - 60, -1);
            }
            else
            {
                fill(p_238509_1_, p_238509_3_ + 1, l4 - 60 + 1, p_238509_3_ + 14, l4 - 60 + 10, -1873784752);
                this.fontRenderer.drawString(p_238509_1_, "20 TPS", (float)(p_238509_3_ + 2), (float)(l4 - 60 + 2), 14737632);
                this.hLine(p_238509_1_, p_238509_3_, p_238509_3_ + j1 - 1, l4 - 60, -1);
            }

            this.hLine(p_238509_1_, p_238509_3_, p_238509_3_ + j1 - 1, l4 - 1, -1);
            this.vLine(p_238509_1_, p_238509_3_, l4 - 60, l4, -1);
            this.vLine(p_238509_1_, p_238509_3_ + j1 - 1, l4 - 60, l4, -1);

            if (p_238509_5_ && this.mc.gameSettings.framerateLimit > 0 && this.mc.gameSettings.framerateLimit <= 250)
            {
                this.hLine(p_238509_1_, p_238509_3_, p_238509_3_ + j1 - 1, l4 - 1 - (int)(1800.0D / (double)this.mc.gameSettings.framerateLimit), -16711681);
            }

            String s = i2 + " ms min";
            String s1 = l1 / (long)j1 + " ms avg";
            String s2 = j2 + " ms max";
            this.fontRenderer.drawStringWithShadow(p_238509_1_, s, (float)(p_238509_3_ + 2), (float)(l4 - 60 - 9), 14737632);
            this.fontRenderer.drawStringWithShadow(p_238509_1_, s1, (float)(p_238509_3_ + j1 / 2 - this.fontRenderer.getStringWidth(s1) / 2), (float)(l4 - 60 - 9), 14737632);
            this.fontRenderer.drawStringWithShadow(p_238509_1_, s2, (float)(p_238509_3_ + j1 - this.fontRenderer.getStringWidth(s2)), (float)(l4 - 60 - 9), 14737632);
            RenderSystem.enableDepthTest();
        }
    }

    private int getFrameColor(int height, int heightMin, int heightMid, int heightMax)
    {
        return height < heightMid ? this.blendColors(-16711936, -256, (float)height / (float)heightMid) : this.blendColors(-256, -65536, (float)(height - heightMid) / (float)(heightMax - heightMid));
    }

    private int blendColors(int col1, int col2, float factor)
    {
        int i = col1 >> 24 & 255;
        int j = col1 >> 16 & 255;
        int k = col1 >> 8 & 255;
        int l = col1 & 255;
        int i1 = col2 >> 24 & 255;
        int j1 = col2 >> 16 & 255;
        int k1 = col2 >> 8 & 255;
        int l1 = col2 & 255;
        int i2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)i, (float)i1), 0, 255);
        int j2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)j, (float)j1), 0, 255);
        int k2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)k, (float)k1), 0, 255);
        int l2 = MathHelper.clamp((int)MathHelper.lerp(factor, (float)l, (float)l1), 0, 255);
        return i2 << 24 | j2 << 16 | k2 << 8 | l2;
    }

    private static long bytesToMb(long bytes)
    {
        return bytes / 1024L / 1024L;
    }
}
