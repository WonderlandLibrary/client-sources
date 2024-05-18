package net.minecraft.client;

import net.minecraft.client.particle.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.profiler.*;
import net.minecraft.server.integrated.*;
import net.minecraft.client.resources.model.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.server.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.client.main.*;
import com.mojang.authlib.yggdrasil.*;
import javax.imageio.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import org.apache.commons.io.*;
import net.minecraft.client.renderer.vertex.*;
import com.mojang.authlib.properties.*;
import net.minecraft.client.stream.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.multiplayer.*;
import org.lwjgl.opengl.*;
import org.apache.commons.lang3.*;
import java.util.concurrent.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.nio.*;
import java.awt.image.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.login.client.*;
import net.minecraft.world.storage.*;
import net.minecraft.crash.*;
import java.net.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import com.google.common.collect.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class Minecraft implements IThreadListener, IPlayerUsage
{
    private int joinPlayerCounter;
    public EntityRenderer entityRenderer;
    private final File fileResourcepacks;
    public EffectRenderer effectRenderer;
    private RenderItem renderItem;
    private final Queue<FutureTask<?>> scheduledTasks;
    private ISaveFormat saveLoader;
    boolean running;
    public static byte[] memoryReserve;
    private String serverName;
    private final File fileAssets;
    private MusicTicker mcMusicTicker;
    private final Session session;
    private SoundHandler mcSoundHandler;
    private final String launchedVersion;
    private boolean hasCrashed;
    private static final Logger logger;
    public LoadingScreenRenderer loadingScreen;
    private ItemRenderer itemRenderer;
    private boolean isGamePaused;
    private static int debugFPS;
    public final FrameTimer field_181542_y;
    private long field_175615_aJ;
    long systemTime;
    private final Proxy proxy;
    long field_181543_z;
    public boolean field_175614_C;
    int fpsCounter;
    private TextureMap textureMapBlocks;
    public EntityPlayerSP thePlayer;
    private Framebuffer framebufferMc;
    private static final List<DisplayMode> macDisplayModes;
    public final File mcDataDir;
    private ServerData currentServerData;
    private SkinManager skinManager;
    private RenderManager renderManager;
    private boolean integratedServerIsRunning;
    private int tempDisplayHeight;
    public PlayerControllerMP playerController;
    private boolean fullscreen;
    public GameSettings gameSettings;
    private int leftClickCounter;
    private final boolean jvm64bit;
    private IStream stream;
    public boolean renderChunksMany;
    private PlayerUsageSnooper usageSnooper;
    private CrashReport crashReporter;
    private boolean enableGLErrorChecking;
    private LanguageManager mcLanguageManager;
    private NetworkManager myNetworkManager;
    public boolean inGameHasFocus;
    private IReloadableResourceManager mcResourceManager;
    public GuiAchievement guiAchievement;
    private String debugProfilerName;
    private final PropertyMap twitchDetails;
    private TextureManager renderEngine;
    public boolean skipRenderWorld;
    public MovingObjectPosition objectMouseOver;
    public FontRenderer fontRendererObj;
    private final List<IResourcePack> defaultResourcePacks;
    private static final String[] I;
    private Timer timer;
    public final Profiler mcProfiler;
    private final Thread mcThread;
    public WorldClient theWorld;
    private final boolean isDemo;
    private int serverPort;
    long prevFrameTime;
    private long debugCrashKeyPressTime;
    private static int[] $SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
    private boolean field_181541_X;
    private static Minecraft theMinecraft;
    private int tempDisplayWidth;
    private IntegratedServer theIntegratedServer;
    public RenderGlobal renderGlobal;
    public String debug;
    public boolean field_175611_D;
    private final DefaultResourcePack mcDefaultResourcePack;
    private ResourceLocation mojangLogo;
    public int displayWidth;
    public GuiScreen currentScreen;
    private final IMetadataSerializer metadataSerializer_;
    public Entity pointedEntity;
    private ModelManager modelManager;
    public GuiIngame ingameGUI;
    private final PropertyMap field_181038_N;
    public boolean field_175613_B;
    private Entity renderViewEntity;
    public MouseHelper mouseHelper;
    private BlockRendererDispatcher blockRenderDispatcher;
    public int displayHeight;
    private int rightClickDelayTimer;
    public FontRenderer standardGalacticFontRenderer;
    long debugUpdateTime;
    private ResourcePackRepository mcResourcePackRepository;
    private static final ResourceLocation locationMojangPng;
    private final MinecraftSessionService sessionService;
    public static final boolean isRunningOnMac;
    private static int[] $SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType;
    
    public static boolean isGuiEnabled() {
        if (Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.hideGUI) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat(Minecraft.I[102 + 35 - 96 + 105], Minecraft.debugFPS);
        playerUsageSnooper.addClientStat(Minecraft.I[96 + 42 - 118 + 127], this.gameSettings.enableVsync);
        playerUsageSnooper.addClientStat(Minecraft.I[91 + 128 - 169 + 98], Display.getDisplayMode().getFrequency());
        final String s = Minecraft.I[32 + 39 - 16 + 94];
        String s2;
        if (this.fullscreen) {
            s2 = Minecraft.I[60 + 48 - 94 + 136];
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            s2 = Minecraft.I[54 + 121 - 135 + 111];
        }
        playerUsageSnooper.addClientStat(s, s2);
        playerUsageSnooper.addClientStat(Minecraft.I[3 + 127 - 7 + 29], (MinecraftServer.getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
        playerUsageSnooper.addClientStat(Minecraft.I[29 + 26 + 49 + 49], this.func_181538_aA());
        String s3;
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            s3 = Minecraft.I[119 + 100 - 153 + 88];
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            s3 = Minecraft.I[103 + 108 - 102 + 46];
        }
        playerUsageSnooper.addClientStat(Minecraft.I[120 + 34 - 82 + 84], s3);
        playerUsageSnooper.addClientStat(Minecraft.I[127 + 56 - 153 + 127], this.mcResourcePackRepository.getRepositoryEntries().size());
        int length = "".length();
        final Iterator<ResourcePackRepository.Entry> iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            playerUsageSnooper.addClientStat(Minecraft.I[152 + 104 - 169 + 71] + length++ + Minecraft.I[132 + 138 - 237 + 126], iterator.next().getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerUsageSnooper.addClientStat(Minecraft.I[140 + 36 - 68 + 52], this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        if (Thread.currentThread() == this.mcThread) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static boolean isJvm64bit() {
        final String[] array = new String["   ".length()];
        array["".length()] = Minecraft.I[0x55 ^ 0x48];
        array[" ".length()] = Minecraft.I[0x3C ^ 0x22];
        array["  ".length()] = Minecraft.I[0x7 ^ 0x18];
        final String[] array2;
        final int length = (array2 = array).length;
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < length) {
            final String property = System.getProperty(array2[i]);
            if (property != null && property.contains(Minecraft.I[0x79 ^ 0x59])) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }
    
    public SkinManager getSkinManager() {
        return this.skinManager;
    }
    
    private void checkGLError(final String s) {
        if (this.enableGLErrorChecking) {
            final int glGetError = GL11.glGetError();
            if (glGetError != 0) {
                final String gluErrorString = GLU.gluErrorString(glGetError);
                Minecraft.logger.error(Minecraft.I[0x87 ^ 0xAB]);
                Minecraft.logger.error(Minecraft.I[0x5A ^ 0x77] + s);
                Minecraft.logger.error(String.valueOf(glGetError) + Minecraft.I[0x51 ^ 0x7F] + gluErrorString);
            }
        }
    }
    
    private void resize(final int n, final int n2) {
        this.displayWidth = Math.max(" ".length(), n);
        this.displayHeight = Math.max(" ".length(), n2);
        if (this.currentScreen != null) {
            final ScaledResolution scaledResolution = new ScaledResolution(this);
            this.currentScreen.onResize(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper(Minecraft.I[19 + 86 + 42 + 19], GL11.glGetString(4574 + 412 + 2836 + 116));
        playerUsageSnooper.addStatToSnooper(Minecraft.I[93 + 17 + 55 + 2], GL11.glGetString(4305 + 3712 - 3085 + 3004));
        playerUsageSnooper.addStatToSnooper(Minecraft.I[104 + 62 - 104 + 106], ClientBrandRetriever.getClientModName());
        playerUsageSnooper.addStatToSnooper(Minecraft.I[121 + 2 - 34 + 80], this.launchedVersion);
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[41 + 113 - 73 + 89], capabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[97 + 15 - 25 + 84], capabilities.GL_ARB_base_instance);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[5 + 169 - 105 + 103], capabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[2 + 89 - 21 + 103], capabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[51 + 42 - 37 + 118], capabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[47 + 64 - 42 + 106], capabilities.GL_ARB_compatibility);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[58 + 137 - 144 + 125], capabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[62 + 39 - 16 + 92], capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[169 + 62 - 56 + 3], capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[105 + 110 - 211 + 175], capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[10 + 153 - 143 + 160], capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[49 + 73 - 6 + 65], capabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[40 + 53 + 75 + 14], capabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[130 + 126 - 182 + 109], capabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[59 + 31 - 56 + 150], capabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[170 + 182 - 200 + 33], capabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[106 + 27 - 103 + 156], capabilities.GL_ARB_depth_texture);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[99 + 2 + 86 + 0], capabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[151 + 154 - 296 + 179], capabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[63 + 69 + 14 + 43], capabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[61 + 31 + 91 + 7], capabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[89 + 152 - 142 + 92], capabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[93 + 4 - 18 + 113], capabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[144 + 44 - 112 + 117], capabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[32 + 63 + 63 + 36], capabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[128 + 119 - 200 + 148], capabilities.GL_ARB_fragment_program);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[22 + 171 - 23 + 26], capabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[153 + 108 - 119 + 55], capabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[185 + 164 - 253 + 102], capabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[60 + 93 + 25 + 21], capabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[158 + 180 - 196 + 58], capabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[112 + 131 - 67 + 25], capabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[4 + 146 - 13 + 65], capabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[153 + 20 - 115 + 145], capabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[28 + 39 - 64 + 201], capabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[10 + 138 - 93 + 150], capabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[32 + 120 - 127 + 181], capabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[177 + 168 - 232 + 94], capabilities.GL_ARB_multisample);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[65 + 191 - 251 + 203], capabilities.GL_ARB_multitexture);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[125 + 86 - 120 + 118], capabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[67 + 9 + 29 + 105], capabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[3 + 180 + 7 + 21], capabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[152 + 146 - 170 + 84], capabilities.GL_ARB_shader_objects);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[2 + 81 - 62 + 192], capabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[87 + 12 - 17 + 132], capabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[74 + 30 + 100 + 11], capabilities.GL_ARB_shadow);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[193 + 45 - 209 + 187], capabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[171 + 27 - 157 + 176], capabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[40 + 110 - 56 + 124], capabilities.GL_ARB_sync);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[112 + 39 - 27 + 95], capabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[196 + 18 - 107 + 113], capabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[177 + 202 - 337 + 179], capabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[159 + 115 - 271 + 219], capabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[24 + 58 + 23 + 118], capabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[94 + 139 - 103 + 94], capabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[181 + 43 - 186 + 187], capabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[143 + 187 - 270 + 166], capabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[219 + 32 - 154 + 130], capabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[86 + 223 - 234 + 153], capabilities.GL_ARB_vertex_program);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[187 + 160 - 335 + 217], capabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[108 + 191 - 128 + 59], capabilities.GL_EXT_bindable_uniform);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[83 + 224 - 100 + 24], capabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[180 + 21 - 19 + 50], capabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[165 + 1 - 137 + 204], capabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[226 + 5 - 117 + 120], capabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[150 + 77 - 150 + 158], capabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[43 + 72 + 19 + 102], capabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[114 + 160 - 39 + 2], capabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[44 + 174 - 182 + 202], capabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[139 + 157 - 132 + 75], capabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[185 + 138 - 258 + 175], capabilities.GL_EXT_gpu_program_parameters);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[157 + 43 - 121 + 162], capabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[128 + 144 - 185 + 155], capabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[204 + 106 - 285 + 218], capabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[194 + 9 + 8 + 33], capabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[103 + 98 + 20 + 24], capabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[106 + 45 - 106 + 201], capabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[3 + 204 - 3 + 43], capabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[110 + 68 + 33 + 37], capabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[202 + 105 - 240 + 182], capabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[83 + 181 - 114 + 100], capabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[192 + 47 - 5 + 17], capabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[44 + 176 + 4 + 28], capabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[112 + 147 - 31 + 25], capabilities.GL_EXT_texture_3d);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[200 + 214 - 249 + 89], capabilities.GL_EXT_texture_array);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[86 + 81 - 92 + 180], capabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[115 + 8 - 121 + 254], capabilities.GL_EXT_texture_integer);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[66 + 250 - 291 + 232], capabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[134 + 132 - 234 + 226], capabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[44 + 213 - 242 + 244], capabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[20 + 113 + 51 + 76], capabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.addStatToSnooper(Minecraft.I[21 + 44 + 54 + 142], GL11.glGetInteger(12656 + 34042 - 15322 + 4282));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[243 + 176 - 212 + 55], GL11.glGetInteger(29498 + 26975 - 53863 + 33047));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[225 + 16 - 240 + 262], GL11.glGetInteger(30598 + 837 - 18581 + 22067));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[214 + 93 - 289 + 246], GL11.glGetInteger(33026 + 31070 - 50126 + 21690));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[171 + 57 - 64 + 101], GL11.glGetInteger(19083 + 15008 - 30367 + 31206));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[4 + 47 - 18 + 233], GL11.glGetInteger(17174 + 19952 - 9517 + 7462));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(Minecraft.I[77 + 234 - 192 + 148], getGLMaximumTextureSize());
    }
    
    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }
    
    public void toggleFullscreen() {
        try {
            int fullscreen;
            if (this.fullscreen) {
                fullscreen = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                fullscreen = " ".length();
            }
            this.fullscreen = (fullscreen != 0);
            this.gameSettings.fullScreen = this.fullscreen;
            if (this.fullscreen) {
                this.updateDisplayMode();
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = " ".length();
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = " ".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            else {
                Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;
                if (this.displayWidth <= 0) {
                    this.displayWidth = " ".length();
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = " ".length();
                }
            }
            if (this.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                this.updateFramebufferSize();
            }
            Display.setFullscreen(this.fullscreen);
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            this.updateDisplay();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        catch (Exception ex) {
            Minecraft.logger.error(Minecraft.I[0x24 ^ 0x44], (Throwable)ex);
        }
    }
    
    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.blockRenderDispatcher;
    }
    
    private void registerMetadataSerializers() {
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }
    
    public Session getSession() {
        return this.session;
    }
    
    public void setDimensionAndSpawnPlayer(final int dimension) {
        this.theWorld.setInitialSpawnLocation();
        this.theWorld.removeAllEntities();
        int entityId = "".length();
        String clientBrand = null;
        if (this.thePlayer != null) {
            entityId = this.thePlayer.getEntityId();
            this.theWorld.removeEntity(this.thePlayer);
            clientBrand = this.thePlayer.getClientBrand();
        }
        this.renderViewEntity = null;
        final EntityPlayerSP thePlayer = this.thePlayer;
        final PlayerControllerMP playerController = this.playerController;
        final WorldClient theWorld = this.theWorld;
        StatFileWriter statFileWriter;
        if (this.thePlayer == null) {
            statFileWriter = new StatFileWriter();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            statFileWriter = this.thePlayer.getStatFileWriter();
        }
        this.thePlayer = playerController.func_178892_a(theWorld, statFileWriter);
        this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(thePlayer.getDataWatcher().getAllWatched());
        this.thePlayer.dimension = dimension;
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        this.thePlayer.setClientBrand(clientBrand);
        this.theWorld.spawnEntityInWorld(this.thePlayer);
        this.playerController.flipPlayer(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.setEntityId(entityId);
        this.playerController.setPlayerCapabilities(this.thePlayer);
        this.thePlayer.setReducedDebug(thePlayer.hasReducedDebug());
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }
    
    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }
    
    public void shutdown() {
        this.running = ("".length() != 0);
    }
    
    public IStream getTwitchStream() {
        return this.stream;
    }
    
    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }
    
    public void shutdownMinecraftApplet() {
        try {
            this.stream.shutdownStream();
            Minecraft.logger.info(Minecraft.I[0xE9 ^ 0xC6]);
            try {
                this.loadWorld(null);
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            catch (Throwable t) {}
            this.mcSoundHandler.unloadSounds();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        finally {
            Display.destroy();
            if (!this.hasCrashed) {
                System.exit("".length());
            }
        }
        Display.destroy();
        if (!this.hasCrashed) {
            System.exit("".length());
        }
        I();
    }
    
    public Minecraft(final GameConfiguration gameConfiguration) {
        this.enableGLErrorChecking = (" ".length() != 0);
        this.field_181541_X = ("".length() != 0);
        this.timer = new Timer(20.0f);
        this.usageSnooper = new PlayerUsageSnooper(Minecraft.I[" ".length()], this, MinecraftServer.getCurrentTimeMillis());
        this.systemTime = getSystemTime();
        this.field_181542_y = new FrameTimer();
        this.field_181543_z = System.nanoTime();
        this.mcProfiler = new Profiler();
        this.debugCrashKeyPressTime = -1L;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = (List<IResourcePack>)Lists.newArrayList();
        this.scheduledTasks = (Queue<FutureTask<?>>)Queues.newArrayDeque();
        this.field_175615_aJ = 0L;
        this.mcThread = Thread.currentThread();
        this.running = (" ".length() != 0);
        this.debug = Minecraft.I["  ".length()];
        this.field_175613_B = ("".length() != 0);
        this.field_175614_C = ("".length() != 0);
        this.field_175611_D = ("".length() != 0);
        this.renderChunksMany = (" ".length() != 0);
        this.debugUpdateTime = getSystemTime();
        this.prevFrameTime = -1L;
        this.debugProfilerName = Minecraft.I["   ".length()];
        Minecraft.theMinecraft = this;
        this.mcDataDir = gameConfiguration.folderInfo.mcDataDir;
        this.fileAssets = gameConfiguration.folderInfo.assetsDir;
        this.fileResourcepacks = gameConfiguration.folderInfo.resourcePacksDir;
        this.launchedVersion = gameConfiguration.gameInfo.version;
        this.twitchDetails = gameConfiguration.userInfo.userProperties;
        this.field_181038_N = gameConfiguration.userInfo.field_181172_c;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(gameConfiguration.folderInfo.assetsDir, gameConfiguration.folderInfo.assetIndex).getResourceMap());
        Proxy proxy;
        if (gameConfiguration.userInfo.proxy == null) {
            proxy = Proxy.NO_PROXY;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            proxy = gameConfiguration.userInfo.proxy;
        }
        this.proxy = proxy;
        this.sessionService = new YggdrasilAuthenticationService(gameConfiguration.userInfo.proxy, UUID.randomUUID().toString()).createMinecraftSessionService();
        this.session = gameConfiguration.userInfo.session;
        Minecraft.logger.info(Minecraft.I[0x98 ^ 0x9C] + this.session.getUsername());
        Minecraft.logger.info(Minecraft.I[0x2 ^ 0x7] + this.session.getSessionID() + Minecraft.I[0x3 ^ 0x5]);
        this.isDemo = gameConfiguration.gameInfo.isDemo;
        int displayWidth;
        if (gameConfiguration.displayInfo.width > 0) {
            displayWidth = gameConfiguration.displayInfo.width;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            displayWidth = " ".length();
        }
        this.displayWidth = displayWidth;
        int displayHeight;
        if (gameConfiguration.displayInfo.height > 0) {
            displayHeight = gameConfiguration.displayInfo.height;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            displayHeight = " ".length();
        }
        this.displayHeight = displayHeight;
        this.tempDisplayWidth = gameConfiguration.displayInfo.width;
        this.tempDisplayHeight = gameConfiguration.displayInfo.height;
        this.fullscreen = gameConfiguration.displayInfo.fullscreen;
        this.jvm64bit = isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (gameConfiguration.serverInfo.serverName != null) {
            this.serverName = gameConfiguration.serverInfo.serverName;
            this.serverPort = gameConfiguration.serverInfo.serverPort;
        }
        ImageIO.setUseCache("".length() != 0);
        Bootstrap.register();
    }
    
    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }
    
    static LanguageManager access$3(final Minecraft minecraft) {
        return minecraft.mcLanguageManager;
    }
    
    private void middleClickMouse() {
        if (this.objectMouseOver != null) {
            final boolean isCreativeMode = this.thePlayer.capabilities.isCreativeMode;
            int n = "".length();
            int n2 = "".length();
            TileEntity tileEntity = null;
            Item item;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                final Block block = this.theWorld.getBlockState(blockPos).getBlock();
                if (block.getMaterial() == Material.air) {
                    return;
                }
                item = block.getItem(this.theWorld, blockPos);
                if (item == null) {
                    return;
                }
                if (isCreativeMode && GuiScreen.isCtrlKeyDown()) {
                    tileEntity = this.theWorld.getTileEntity(blockPos);
                }
                Block blockFromItem;
                if (item instanceof ItemBlock && !block.isFlowerPot()) {
                    blockFromItem = Block.getBlockFromItem(item);
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                else {
                    blockFromItem = block;
                }
                n = blockFromItem.getDamageValue(this.theWorld, blockPos);
                n2 = (item.getHasSubtypes() ? 1 : 0);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !isCreativeMode) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    item = Items.painting;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    item = Items.lead;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    final ItemStack displayedItem = ((EntityItemFrame)this.objectMouseOver.entityHit).getDisplayedItem();
                    if (displayedItem == null) {
                        item = Items.item_frame;
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                    else {
                        item = displayedItem.getItem();
                        n = displayedItem.getMetadata();
                        n2 = " ".length();
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    switch ($SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType()[((EntityMinecart)this.objectMouseOver.entityHit).getMinecartType().ordinal()]) {
                        case 3: {
                            item = Items.furnace_minecart;
                            "".length();
                            if (-1 >= 4) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            item = Items.chest_minecart;
                            "".length();
                            if (3 < 2) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            item = Items.tnt_minecart;
                            "".length();
                            if (2 <= 1) {
                                throw null;
                            }
                            break;
                        }
                        case 6: {
                            item = Items.hopper_minecart;
                            "".length();
                            if (-1 == 4) {
                                throw null;
                            }
                            break;
                        }
                        case 7: {
                            item = Items.command_block_minecart;
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break;
                        }
                        default: {
                            item = Items.minecart;
                            "".length();
                            if (2 < -1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    item = Items.boat;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                    item = Items.armor_stand;
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                }
                else {
                    item = Items.spawn_egg;
                    n = EntityList.getEntityID(this.objectMouseOver.entityHit);
                    n2 = " ".length();
                    if (!EntityList.entityEggs.containsKey(n)) {
                        return;
                    }
                }
            }
            final InventoryPlayer inventory = this.thePlayer.inventory;
            if (tileEntity == null) {
                inventory.setCurrentItem(item, n, n2 != 0, isCreativeMode);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                inventory.setInventorySlotContents(inventory.currentItem, this.func_181036_a(item, n, tileEntity));
            }
            if (isCreativeMode) {
                this.playerController.sendSlotPacket(inventory.getStackInSlot(inventory.currentItem), this.thePlayer.inventoryContainer.inventorySlots.size() - (0x80 ^ 0x89) + inventory.currentItem);
            }
        }
    }
    
    private void setInitialDisplayMode() throws LWJGLException {
        if (this.fullscreen) {
            Display.setFullscreen((boolean)(" ".length() != 0));
            final DisplayMode displayMode = Display.getDisplayMode();
            this.displayWidth = Math.max(" ".length(), displayMode.getWidth());
            this.displayHeight = Math.max(" ".length(), displayMode.getHeight());
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
    }
    
    private void drawSplashScreen(final TextureManager textureManager) throws LWJGLException {
        final ScaledResolution scaledResolution = new ScaledResolution(this);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, " ".length() != 0);
        framebuffer.bindFramebuffer("".length() != 0);
        GlStateManager.matrixMode(2280 + 4008 - 2295 + 1896);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5190 + 177 - 4145 + 4666);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        InputStream inputStream = null;
        Label_0274: {
            try {
                inputStream = this.mcDefaultResourcePack.getInputStream(Minecraft.locationMojangPng);
                textureManager.bindTexture(this.mojangLogo = textureManager.getDynamicTextureLocation(Minecraft.I[0x92 ^ 0xB8], new DynamicTexture(ImageIO.read(inputStream))));
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            catch (IOException ex) {
                Minecraft.logger.error(Minecraft.I[0x42 ^ 0x69] + Minecraft.locationMojangPng, (Throwable)ex);
                IOUtils.closeQuietly(inputStream);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                break Label_0274;
            }
            finally {
                IOUtils.closeQuietly(inputStream);
            }
            IOUtils.closeQuietly(inputStream);
        }
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x7 ^ 0x0, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, this.displayHeight, 0.0).tex(0.0, 0.0).color(63 + 63 + 126 + 3, 73 + 218 - 131 + 95, 138 + 140 - 75 + 52, 158 + 127 - 273 + 243).endVertex();
        worldRenderer.pos(this.displayWidth, this.displayHeight, 0.0).tex(0.0, 0.0).color(3 + 190 - 109 + 171, 179 + 23 - 185 + 238, 121 + 19 + 73 + 42, 112 + 144 - 126 + 125).endVertex();
        worldRenderer.pos(this.displayWidth, 0.0, 0.0).tex(0.0, 0.0).color(148 + 49 - 22 + 80, 186 + 153 - 192 + 108, 11 + 80 + 51 + 113, 136 + 139 - 203 + 183).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(213 + 183 - 214 + 73, 150 + 19 - 86 + 172, 64 + 21 + 99 + 71, 215 + 79 - 252 + 213).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n = 74 + 251 - 312 + 243;
        final int n2 = 218 + 4 + 6 + 28;
        this.func_181536_a((scaledResolution.getScaledWidth() - n) / "  ".length(), (scaledResolution.getScaledHeight() - n2) / "  ".length(), "".length(), "".length(), n, n2, 14 + 61 + 139 + 41, 161 + 171 - 146 + 69, 226 + 230 - 407 + 206, 39 + 163 + 43 + 10);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(456 + 104 - 280 + 236, 0.1f);
        this.updateDisplay();
    }
    
    public PropertyMap func_181037_M() {
        if (this.field_181038_N.isEmpty()) {
            this.field_181038_N.putAll((Multimap)this.getSessionService().fillProfileProperties(this.session.getProfile(), (boolean)("".length() != 0)).getProperties());
        }
        return this.field_181038_N;
    }
    
    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                this.mcSoundHandler.pauseSounds();
            }
        }
    }
    
    private void initStream() {
        try {
            this.stream = new TwitchStream(this, (Property)Iterables.getFirst((Iterable)this.twitchDetails.get((Object)Minecraft.I[0x95 ^ 0x83]), (Object)null));
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (Throwable t) {
            this.stream = new NullStream(t);
            Minecraft.logger.error(Minecraft.I[0x64 ^ 0x73]);
        }
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    public static boolean isFancyGraphicsEnabled() {
        if (Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public RenderItem getRenderItem() {
        return this.renderItem;
    }
    
    public void run() {
        this.running = (" ".length() != 0);
        try {
            this.startGame();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, Minecraft.I[0x7A ^ 0x7D]);
            crashReport.makeCategory(Minecraft.I[0x32 ^ 0x3A]);
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashReport));
            return;
        }
        try {
            do {
                Label_0132: {
                    if (this.hasCrashed) {
                        if (this.crashReporter != null) {
                            break Label_0132;
                        }
                    }
                    try {
                        this.runGameLoop();
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                        continue;
                    }
                    catch (OutOfMemoryError outOfMemoryError) {
                        this.freeMemory();
                        this.displayGuiScreen(new GuiMemoryErrorScreen());
                        I();
                        "".length();
                        if (0 == 2) {
                            throw null;
                        }
                        continue;
                    }
                }
                this.displayCrashReport(this.crashReporter);
            } while (this.running);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        catch (MinecraftError minecraftError) {
            this.shutdownMinecraftApplet();
            "".length();
            if (3 == 0) {
                throw null;
            }
            return;
        }
        catch (ReportedException ex) {
            this.addGraphicsAndWorldToCrashReport(ex.getCrashReport());
            this.freeMemory();
            Minecraft.logger.fatal(Minecraft.I[0x5E ^ 0x57], (Throwable)ex);
            this.displayCrashReport(ex.getCrashReport());
            this.shutdownMinecraftApplet();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            return;
        }
        catch (Throwable t2) {
            final CrashReport addGraphicsAndWorldToCrashReport = this.addGraphicsAndWorldToCrashReport(new CrashReport(Minecraft.I[0x39 ^ 0x33], t2));
            this.freeMemory();
            Minecraft.logger.fatal(Minecraft.I[0x63 ^ 0x68], t2);
            this.displayCrashReport(addGraphicsAndWorldToCrashReport);
            this.shutdownMinecraftApplet();
            "".length();
            if (4 < 3) {
                throw null;
            }
            return;
        }
        finally {
            this.shutdownMinecraftApplet();
        }
        this.shutdownMinecraftApplet();
    }
    
    public boolean isSingleplayer() {
        if (this.integratedServerIsRunning && this.theIntegratedServer != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isFullScreen() {
        return this.fullscreen;
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void runGameLoop() throws IOException {
        final long nanoTime = System.nanoTime();
        this.mcProfiler.startSection(Minecraft.I[0xF5 ^ 0xC5]);
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        if (this.isGamePaused && this.theWorld != null) {
            final float renderPartialTicks = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = renderPartialTicks;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.timer.updateTimer();
        }
        this.mcProfiler.startSection(Minecraft.I[0x22 ^ 0x13]);
        synchronized (this.scheduledTasks) {
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (!this.scheduledTasks.isEmpty()) {
                Util.func_181617_a(this.scheduledTasks.poll(), Minecraft.logger);
            }
            // monitorexit(this.scheduledTasks)
            "".length();
            if (true != true) {
                throw null;
            }
        }
        this.mcProfiler.endSection();
        final long nanoTime2 = System.nanoTime();
        this.mcProfiler.startSection(Minecraft.I[0x19 ^ 0x2B]);
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < this.timer.elapsedTicks) {
            this.runTick();
            ++i;
        }
        this.mcProfiler.endStartSection(Minecraft.I[0x6F ^ 0x5C]);
        final long n = System.nanoTime() - nanoTime2;
        this.checkGLError(Minecraft.I[0x12 ^ 0x26]);
        this.mcProfiler.endStartSection(Minecraft.I[0x8D ^ 0xB8]);
        this.mcSoundHandler.setListener(this.thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection(Minecraft.I[0x27 ^ 0x11]);
        GlStateManager.pushMatrix();
        GlStateManager.clear(5305 + 10906 - 5196 + 5625);
        this.framebufferMc.bindFramebuffer(" ".length() != 0);
        this.mcProfiler.startSection(Minecraft.I[0x1B ^ 0x2C]);
        GlStateManager.enableTexture2D();
        if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = "".length();
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection(Minecraft.I[0xA8 ^ 0x90]);
            this.entityRenderer.func_181560_a(this.timer.renderPartialTicks, nanoTime);
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = (" ".length() != 0);
            this.displayDebugInfo(n);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            this.mcProfiler.profilingEnabled = ("".length() != 0);
            this.prevFrameTime = System.nanoTime();
        }
        this.guiAchievement.updateAchievementWindow();
        this.framebufferMc.unbindFramebuffer();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.entityRenderer.renderStreamIndicator(this.timer.renderPartialTicks);
        GlStateManager.popMatrix();
        this.mcProfiler.startSection(Minecraft.I[0x3D ^ 0x4]);
        this.updateDisplay();
        Thread.yield();
        this.mcProfiler.startSection(Minecraft.I[0x50 ^ 0x6A]);
        this.mcProfiler.startSection(Minecraft.I[0xB9 ^ 0x82]);
        this.stream.func_152935_j();
        this.mcProfiler.endStartSection(Minecraft.I[0xB0 ^ 0x8C]);
        this.stream.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError(Minecraft.I[0xFC ^ 0xC1]);
        this.fpsCounter += " ".length();
        int isGamePaused;
        if (this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic()) {
            isGamePaused = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            isGamePaused = "".length();
        }
        this.isGamePaused = (isGamePaused != 0);
        final long nanoTime3 = System.nanoTime();
        this.field_181542_y.func_181747_a(nanoTime3 - this.field_181543_z);
        this.field_181543_z = nanoTime3;
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (getSystemTime() >= this.debugUpdateTime + 1000L) {
            Minecraft.debugFPS = this.fpsCounter;
            final String s = Minecraft.I[0xA7 ^ 0x99];
            final Object[] array = new Object[0x9F ^ 0x97];
            array["".length()] = Minecraft.debugFPS;
            array[" ".length()] = RenderChunk.renderChunksUpdated;
            final int length = "  ".length();
            String s2;
            if (RenderChunk.renderChunksUpdated != " ".length()) {
                s2 = Minecraft.I[0x3C ^ 0x3];
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else {
                s2 = Minecraft.I[0x59 ^ 0x19];
            }
            array[length] = s2;
            final int length2 = "   ".length();
            Serializable value;
            if (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) {
                value = Minecraft.I[0x5E ^ 0x1F];
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                value = this.gameSettings.limitFramerate;
            }
            array[length2] = value;
            final int n2 = 0xA1 ^ 0xA5;
            String s3;
            if (this.gameSettings.enableVsync) {
                s3 = Minecraft.I[0x36 ^ 0x74];
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                s3 = Minecraft.I[0xDB ^ 0x98];
            }
            array[n2] = s3;
            final int n3 = 0x7D ^ 0x78;
            String s4;
            if (this.gameSettings.fancyGraphics) {
                s4 = Minecraft.I[0xC9 ^ 0x8D];
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                s4 = Minecraft.I[0xEF ^ 0xAA];
            }
            array[n3] = s4;
            final int n4 = 0x64 ^ 0x62;
            String s5;
            if (this.gameSettings.clouds == 0) {
                s5 = Minecraft.I[0x3 ^ 0x45];
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else if (this.gameSettings.clouds == " ".length()) {
                s5 = Minecraft.I[0x68 ^ 0x2F];
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                s5 = Minecraft.I[0x54 ^ 0x1C];
            }
            array[n4] = s5;
            final int n5 = 0x89 ^ 0x8E;
            String s6;
            if (OpenGlHelper.useVbo()) {
                s6 = Minecraft.I[0x3B ^ 0x72];
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            else {
                s6 = Minecraft.I[0x6C ^ 0x26];
            }
            array[n5] = s6;
            this.debug = String.format(s, array);
            RenderChunk.renderChunksUpdated = "".length();
            this.debugUpdateTime += 1000L;
            this.fpsCounter = "".length();
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.startSnooper();
            }
        }
        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection(Minecraft.I[0x68 ^ 0x23]);
            Display.sync(this.getLimitFramerate());
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
    }
    
    public void runTick() throws IOException {
        if (this.rightClickDelayTimer > 0) {
            this.rightClickDelayTimer -= " ".length();
        }
        this.mcProfiler.startSection(Minecraft.I[0x3 ^ 0x62]);
        if (!this.isGamePaused) {
            this.ingameGUI.updateTick();
        }
        this.mcProfiler.endSection();
        this.entityRenderer.getMouseOver(1.0f);
        this.mcProfiler.startSection(Minecraft.I[0xF4 ^ 0x96]);
        if (!this.isGamePaused && this.theWorld != null) {
            this.playerController.updateController();
        }
        this.mcProfiler.endStartSection(Minecraft.I[0x42 ^ 0x21]);
        if (!this.isGamePaused) {
            this.renderEngine.tick();
        }
        if (this.currentScreen == null && this.thePlayer != null) {
            if (this.thePlayer.getHealth() <= 0.0f) {
                this.displayGuiScreen(null);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
                this.displayGuiScreen(new GuiSleepMP());
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
        }
        else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.leftClickCounter = 4141 + 27 + 955 + 4877;
        }
        if (this.currentScreen != null) {
            try {
                this.currentScreen.handleInput();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, Minecraft.I[0x11 ^ 0x75]);
                crashReport.makeCategory(Minecraft.I[0x46 ^ 0x23]).addCrashSectionCallable(Minecraft.I[0x4 ^ 0x62], new Callable<String>(this) {
                    final Minecraft this$0;
                    
                    @Override
                    public String call() throws Exception {
                        return this.this$0.currentScreen.getClass().getCanonicalName();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                            if (0 >= 1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                });
                throw new ReportedException(crashReport);
            }
            if (this.currentScreen != null) {
                try {
                    this.currentScreen.updateScreen();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                catch (Throwable t2) {
                    final CrashReport crashReport2 = CrashReport.makeCrashReport(t2, Minecraft.I[0x7B ^ 0x1C]);
                    crashReport2.makeCategory(Minecraft.I[0x4E ^ 0x26]).addCrashSectionCallable(Minecraft.I[0x5D ^ 0x34], new Callable<String>(this) {
                        final Minecraft this$0;
                        
                        @Override
                        public String call() throws Exception {
                            return this.this$0.currentScreen.getClass().getCanonicalName();
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
                                if (2 == 1) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public Object call() throws Exception {
                            return this.call();
                        }
                    });
                    throw new ReportedException(crashReport2);
                }
            }
        }
        if (this.currentScreen == null || this.currentScreen.allowUserInput) {
            this.mcProfiler.endStartSection(Minecraft.I[0x78 ^ 0x12]);
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (Mouse.next()) {
                final int eventButton = Mouse.getEventButton();
                KeyBinding.setKeyBindState(eventButton - (0xC4 ^ 0xA0), Mouse.getEventButtonState());
                if (Mouse.getEventButtonState()) {
                    if (this.thePlayer.isSpectator() && eventButton == "  ".length()) {
                        this.ingameGUI.getSpectatorGui().func_175261_b();
                        "".length();
                        if (2 < 2) {
                            throw null;
                        }
                    }
                    else {
                        KeyBinding.onTick(eventButton - (0xD4 ^ 0xB0));
                    }
                }
                if (getSystemTime() - this.systemTime <= 200L) {
                    final int eventDWheel = Mouse.getEventDWheel();
                    if (eventDWheel != 0) {
                        if (this.thePlayer.isSpectator()) {
                            int length;
                            if (eventDWheel < 0) {
                                length = -" ".length();
                                "".length();
                                if (3 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                length = " ".length();
                            }
                            final int n = length;
                            if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
                                this.ingameGUI.getSpectatorGui().func_175259_b(-n);
                                "".length();
                                if (2 == 0) {
                                    throw null;
                                }
                            }
                            else {
                                this.thePlayer.capabilities.setFlySpeed(MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + n * 0.005f, 0.0f, 0.2f));
                                "".length();
                                if (3 <= 1) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            this.thePlayer.inventory.changeCurrentItem(eventDWheel);
                        }
                    }
                    if (this.currentScreen == null) {
                        if (this.inGameHasFocus || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        this.setIngameFocus();
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        if (this.currentScreen == null) {
                            continue;
                        }
                        this.currentScreen.handleMouseInput();
                    }
                }
            }
            if (this.leftClickCounter > 0) {
                this.leftClickCounter -= " ".length();
            }
            this.mcProfiler.endStartSection(Minecraft.I[0x9 ^ 0x62]);
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (Keyboard.next()) {
                int eventKey;
                if (Keyboard.getEventKey() == 0) {
                    eventKey = Keyboard.getEventCharacter() + (0 + 211 - 167 + 212);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    eventKey = Keyboard.getEventKey();
                }
                final int n2 = eventKey;
                KeyBinding.setKeyBindState(n2, Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    KeyBinding.onTick(n2);
                }
                if (this.debugCrashKeyPressTime > 0L) {
                    if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                        throw new ReportedException(new CrashReport(Minecraft.I[0x41 ^ 0x2D], new Throwable()));
                    }
                    if (!Keyboard.isKeyDown(0x7B ^ 0x55) || !Keyboard.isKeyDown(0xAF ^ 0x92)) {
                        this.debugCrashKeyPressTime = -1L;
                        "".length();
                        if (-1 == 4) {
                            throw null;
                        }
                    }
                }
                else if (Keyboard.isKeyDown(0x8A ^ 0xA4) && Keyboard.isKeyDown(0x71 ^ 0x4C)) {
                    this.debugCrashKeyPressTime = getSystemTime();
                }
                this.dispatchKeypresses();
                if (Keyboard.getEventKeyState()) {
                    if (n2 == (0xB3 ^ 0x8D) && this.entityRenderer != null) {
                        this.entityRenderer.switchUseShader();
                    }
                    if (this.currentScreen != null) {
                        this.currentScreen.handleKeyboardInput();
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        if (n2 == " ".length()) {
                            this.displayInGameMenu();
                        }
                        if (n2 == (0x95 ^ 0xB5) && Keyboard.isKeyDown(0x44 ^ 0x79) && this.ingameGUI != null) {
                            this.ingameGUI.getChatGUI().clearChatMessages();
                        }
                        if (n2 == (0x94 ^ 0x8B) && Keyboard.isKeyDown(0x14 ^ 0x29)) {
                            this.refreshResources();
                        }
                        if (n2 != (0xD2 ^ 0xC3) || Keyboard.isKeyDown(0x60 ^ 0x5D)) {}
                        if (n2 != (0x8D ^ 0x9F) || Keyboard.isKeyDown(0x9D ^ 0xA0)) {}
                        if (n2 != (0x89 ^ 0xA6) || Keyboard.isKeyDown(0x24 ^ 0x19)) {}
                        if (n2 != (0x6A ^ 0x4C) || Keyboard.isKeyDown(0x91 ^ 0xAC)) {}
                        if (n2 != (0x37 ^ 0x21) || Keyboard.isKeyDown(0x67 ^ 0x5A)) {}
                        if (n2 == (0xAB ^ 0xBF) && Keyboard.isKeyDown(0x66 ^ 0x5B)) {
                            this.refreshResources();
                        }
                        if (n2 == (0x79 ^ 0x58) && Keyboard.isKeyDown(0x89 ^ 0xB4)) {
                            final GameSettings gameSettings = this.gameSettings;
                            final GameSettings.Options render_DISTANCE = GameSettings.Options.RENDER_DISTANCE;
                            int length2;
                            if (GuiScreen.isShiftKeyDown()) {
                                length2 = -" ".length();
                                "".length();
                                if (3 != 3) {
                                    throw null;
                                }
                            }
                            else {
                                length2 = " ".length();
                            }
                            gameSettings.setOptionValue(render_DISTANCE, length2);
                        }
                        if (n2 == (0x39 ^ 0x27) && Keyboard.isKeyDown(0xFB ^ 0xC6)) {
                            this.renderGlobal.loadRenderers();
                        }
                        if (n2 == (0x7B ^ 0x58) && Keyboard.isKeyDown(0xFE ^ 0xC3)) {
                            final GameSettings gameSettings2 = this.gameSettings;
                            int advancedItemTooltips;
                            if (this.gameSettings.advancedItemTooltips) {
                                advancedItemTooltips = "".length();
                                "".length();
                                if (-1 == 0) {
                                    throw null;
                                }
                            }
                            else {
                                advancedItemTooltips = " ".length();
                            }
                            gameSettings2.advancedItemTooltips = (advancedItemTooltips != 0);
                            this.gameSettings.saveOptions();
                        }
                        if (n2 == (0xBE ^ 0x8E) && Keyboard.isKeyDown(0x89 ^ 0xB4)) {
                            final RenderManager renderManager = this.renderManager;
                            int debugBoundingBox;
                            if (this.renderManager.isDebugBoundingBox()) {
                                debugBoundingBox = "".length();
                                "".length();
                                if (1 < 1) {
                                    throw null;
                                }
                            }
                            else {
                                debugBoundingBox = " ".length();
                            }
                            renderManager.setDebugBoundingBox(debugBoundingBox != 0);
                        }
                        if (n2 == (0x2C ^ 0x35) && Keyboard.isKeyDown(0x55 ^ 0x68)) {
                            final GameSettings gameSettings3 = this.gameSettings;
                            int pauseOnLostFocus;
                            if (this.gameSettings.pauseOnLostFocus) {
                                pauseOnLostFocus = "".length();
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                            else {
                                pauseOnLostFocus = " ".length();
                            }
                            gameSettings3.pauseOnLostFocus = (pauseOnLostFocus != 0);
                            this.gameSettings.saveOptions();
                        }
                        if (n2 == (0xBD ^ 0x86)) {
                            final GameSettings gameSettings4 = this.gameSettings;
                            int hideGUI;
                            if (this.gameSettings.hideGUI) {
                                hideGUI = "".length();
                                "".length();
                                if (3 < 3) {
                                    throw null;
                                }
                            }
                            else {
                                hideGUI = " ".length();
                            }
                            gameSettings4.hideGUI = (hideGUI != 0);
                        }
                        if (n2 == (0x87 ^ 0xBA)) {
                            final GameSettings gameSettings5 = this.gameSettings;
                            int showDebugInfo;
                            if (this.gameSettings.showDebugInfo) {
                                showDebugInfo = "".length();
                                "".length();
                                if (4 <= 0) {
                                    throw null;
                                }
                            }
                            else {
                                showDebugInfo = " ".length();
                            }
                            gameSettings5.showDebugInfo = (showDebugInfo != 0);
                            this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                            this.gameSettings.field_181657_aC = GuiScreen.isAltKeyDown();
                        }
                        if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                            final GameSettings gameSettings6 = this.gameSettings;
                            gameSettings6.thirdPersonView += " ".length();
                            if (this.gameSettings.thirdPersonView > "  ".length()) {
                                this.gameSettings.thirdPersonView = "".length();
                            }
                            if (this.gameSettings.thirdPersonView == 0) {
                                this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                            else if (this.gameSettings.thirdPersonView == " ".length()) {
                                this.entityRenderer.loadEntityShader(null);
                            }
                            this.renderGlobal.setDisplayListEntitiesDirty();
                        }
                        if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                            final GameSettings gameSettings7 = this.gameSettings;
                            int smoothCamera;
                            if (this.gameSettings.smoothCamera) {
                                smoothCamera = "".length();
                                "".length();
                                if (1 < 0) {
                                    throw null;
                                }
                            }
                            else {
                                smoothCamera = " ".length();
                            }
                            gameSettings7.smoothCamera = (smoothCamera != 0);
                        }
                    }
                    if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) {
                        continue;
                    }
                    if (n2 == (0xB8 ^ 0xB3)) {
                        this.updateDebugProfilerName("".length());
                    }
                    int i = "".length();
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                    while (i < (0x34 ^ 0x3D)) {
                        if (n2 == "  ".length() + i) {
                            this.updateDebugProfilerName(i + " ".length());
                        }
                        ++i;
                    }
                }
            }
            int j = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (j < (0xE ^ 0x7)) {
                if (this.gameSettings.keyBindsHotbar[j].isPressed()) {
                    if (this.thePlayer.isSpectator()) {
                        this.ingameGUI.getSpectatorGui().func_175260_a(j);
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    else {
                        this.thePlayer.inventory.currentItem = j;
                    }
                }
                ++j;
            }
            int n3;
            if (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                n3 = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (this.gameSettings.keyBindInventory.isPressed()) {
                if (this.playerController.isRidingHorse()) {
                    this.thePlayer.sendHorseInventory();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    continue;
                }
                else {
                    this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    this.displayGuiScreen(new GuiInventory(this.thePlayer));
                }
            }
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (this.gameSettings.keyBindDrop.isPressed()) {
                if (!this.thePlayer.isSpectator()) {
                    this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
                }
            }
            "".length();
            if (false) {
                throw null;
            }
            while (this.gameSettings.keyBindChat.isPressed() && n4 != 0) {
                this.displayGuiScreen(new GuiChat());
            }
            if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && n4 != 0) {
                this.displayGuiScreen(new GuiChat(Minecraft.I[0xD4 ^ 0xB9]));
            }
            if (this.thePlayer.isUsingItem()) {
                if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
                    this.playerController.onStoppedUsingItem(this.thePlayer);
                }
                while (this.gameSettings.keyBindAttack.isPressed()) {}
                while (this.gameSettings.keyBindUseItem.isPressed()) {}
                while (this.gameSettings.keyBindPickBlock.isPressed()) {}
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                while (this.gameSettings.keyBindAttack.isPressed()) {
                    this.clickMouse();
                }
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                    this.rightClickMouse();
                }
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                    this.middleClickMouse();
                }
            }
            if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
                this.rightClickMouse();
            }
            int n5;
            if (this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus) {
                n5 = " ".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            this.sendClickBlockToController(n5 != 0);
        }
        if (this.theWorld != null) {
            if (this.thePlayer != null) {
                this.joinPlayerCounter += " ".length();
                if (this.joinPlayerCounter == (0x8A ^ 0x94)) {
                    this.joinPlayerCounter = "".length();
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }
            this.mcProfiler.endStartSection(Minecraft.I[0x2B ^ 0x45]);
            if (!this.isGamePaused) {
                this.entityRenderer.updateRenderer();
            }
            this.mcProfiler.endStartSection(Minecraft.I[0xFA ^ 0x95]);
            if (!this.isGamePaused) {
                this.renderGlobal.updateClouds();
            }
            this.mcProfiler.endStartSection(Minecraft.I[0xDC ^ 0xAC]);
            if (!this.isGamePaused) {
                if (this.theWorld.getLastLightningBolt() > 0) {
                    this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - " ".length());
                }
                this.theWorld.updateEntities();
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
        }
        else if (this.entityRenderer.isShaderActive()) {
            this.entityRenderer.func_181022_b();
        }
        if (!this.isGamePaused) {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }
        if (this.theWorld != null) {
            if (!this.isGamePaused) {
                final WorldClient theWorld = this.theWorld;
                int n6;
                if (this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL) {
                    n6 = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                }
                theWorld.setAllowedSpawnTypes(n6 != 0, " ".length() != 0);
                try {
                    this.theWorld.tick();
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                catch (Throwable t3) {
                    final CrashReport crashReport3 = CrashReport.makeCrashReport(t3, Minecraft.I[0x7E ^ 0xF]);
                    if (this.theWorld == null) {
                        crashReport3.makeCategory(Minecraft.I[0x71 ^ 0x3]).addCrashSection(Minecraft.I[0xB5 ^ 0xC6], Minecraft.I[0x28 ^ 0x5C]);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        this.theWorld.addWorldInfoToCrashReport(crashReport3);
                    }
                    throw new ReportedException(crashReport3);
                }
            }
            this.mcProfiler.endStartSection(Minecraft.I[0x5F ^ 0x2A]);
            if (!this.isGamePaused && this.theWorld != null) {
                this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }
            this.mcProfiler.endStartSection(Minecraft.I[0xB3 ^ 0xC5]);
            if (!this.isGamePaused) {
                this.effectRenderer.updateEffects();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else if (this.myNetworkManager != null) {
            this.mcProfiler.endStartSection(Minecraft.I[0x17 ^ 0x60]);
            this.myNetworkManager.processReceivedPackets();
        }
        this.mcProfiler.endSection();
        this.systemTime = getSystemTime();
    }
    
    private static void I() {
        System.gc();
    }
    
    public boolean isUnicode() {
        if (!this.mcLanguageManager.isCurrentLocaleUnicode() && !this.gameSettings.forceUnicodeFont) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private void updateDebugProfilerName(int n) {
        final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (profilingData != null && !profilingData.isEmpty()) {
            final Profiler.Result result = profilingData.remove("".length());
            if (n == 0) {
                if (result.field_76331_c.length() > 0) {
                    final int lastIndex = this.debugProfilerName.lastIndexOf(Minecraft.I[0xF2 ^ 0xBF]);
                    if (lastIndex >= 0) {
                        this.debugProfilerName = this.debugProfilerName.substring("".length(), lastIndex);
                        "".length();
                        if (-1 == 2) {
                            throw null;
                        }
                    }
                }
            }
            else if (--n < profilingData.size() && !profilingData.get(n).field_76331_c.equals(Minecraft.I[0x1 ^ 0x4F])) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = String.valueOf(this.debugProfilerName) + Minecraft.I[0x34 ^ 0x7B];
                }
                this.debugProfilerName = String.valueOf(this.debugProfilerName) + profilingData.get(n).field_76331_c;
            }
        }
    }
    
    public void refreshResources() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        final Iterator<ResourcePackRepository.Entry> iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getResourcePack());
        }
        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add(this.mcResourcePackRepository.getResourcePackInstance());
        }
        try {
            this.mcResourceManager.reloadResources(arrayList);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (RuntimeException ex) {
            Minecraft.logger.info(Minecraft.I[0x7C ^ 0x55], (Throwable)ex);
            arrayList.clear();
            arrayList.addAll(this.defaultResourcePacks);
            this.mcResourcePackRepository.setRepositories(Collections.emptyList());
            this.mcResourceManager.reloadResources(arrayList);
            this.gameSettings.resourcePacks.clear();
            this.gameSettings.field_183018_l.clear();
            this.gameSettings.saveOptions();
        }
        this.mcLanguageManager.parseLanguageMetadata(arrayList);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }
    
    private void createDisplay() throws LWJGLException {
        Display.setResizable((boolean)(" ".length() != 0));
        Display.setTitle(Minecraft.I[0x51 ^ 0x49]);
        try {
            Display.create(new PixelFormat().withDepthBits(0x3F ^ 0x27));
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        catch (LWJGLException ex) {
            Minecraft.logger.error(Minecraft.I[0x72 ^ 0x6B], (Throwable)ex);
            try {
                Thread.sleep(1000L);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (InterruptedException ex2) {}
            if (this.fullscreen) {
                this.updateDisplayMode();
            }
            Display.create();
        }
    }
    
    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public PropertyMap getTwitchDetails() {
        return this.twitchDetails;
    }
    
    public static boolean isAmbientOcclusionEnabled() {
        if (Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }
    
    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }
    
    public void func_181537_a(final boolean field_181541_X) {
        this.field_181541_X = field_181541_X;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.theMinecraft;
    }
    
    private void startGame() throws IOException, LWJGLException {
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
        this.startTimerHackThread();
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        Minecraft.logger.info(Minecraft.I[0x0 ^ 0xC] + Sys.getVersion());
        this.setWindowIcon();
        this.setInitialDisplayMode();
        this.createDisplay();
        OpenGlHelper.initializeTextures();
        (this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, " ".length() != 0)).setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.registerMetadataSerializers();
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, Minecraft.I[0x9B ^ 0x96]), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.drawSplashScreen(this.renderEngine);
        this.initStream();
        this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, Minecraft.I[0x8A ^ 0x84]), this.sessionService);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, Minecraft.I[0xB9 ^ 0xB6]));
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(this);
        this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation(Minecraft.I[0x93 ^ 0x83]), this.renderEngine, "".length() != 0);
        if (this.gameSettings.language != null) {
            this.fontRendererObj.setUnicodeFlag(this.isUnicode());
            this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation(Minecraft.I[0xAF ^ 0xBE]), this.renderEngine, "".length() != 0);
        this.mcResourceManager.registerReloadListener(this.fontRendererObj);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat(this) {
            private static final String[] I;
            final Minecraft this$0;
            
            static {
                I();
            }
            
            @Override
            public String formatString(final String s) {
                try {
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = GameSettings.getKeyDisplayString(this.this$0.gameSettings.keyBindInventory.getKeyCode());
                    return String.format(s, array);
                }
                catch (Exception ex) {
                    return Minecraft$1.I["".length()] + ex.getLocalizedMessage();
                }
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u001d3\u0001\u0018=ba", "XAswO");
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
                    if (2 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.mouseHelper = new MouseHelper();
        this.checkGLError(Minecraft.I[0x29 ^ 0x3B]);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(6604 + 4641 - 8334 + 4514);
        GlStateManager.clearDepth(1.0);
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(416 + 27 + 64 + 8);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(184 + 221 + 96 + 15, 0.1f);
        GlStateManager.cullFace(1014 + 499 - 976 + 492);
        GlStateManager.matrixMode(80 + 3735 + 476 + 1598);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(101 + 2644 + 699 + 2444);
        this.checkGLError(Minecraft.I[0x4E ^ 0x5D]);
        (this.textureMapBlocks = new TextureMap(Minecraft.I[0x16 ^ 0x2])).setMipmapLevels(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final TextureMap textureMapBlocks = this.textureMapBlocks;
        final int length = "".length();
        int n;
        if (this.gameSettings.mipmapLevels > 0) {
            n = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        textureMapBlocks.setBlurMipmapDirect(length != 0, n != 0);
        this.modelManager = new ModelManager(this.textureMapBlocks);
        this.mcResourceManager.registerReloadListener(this.modelManager);
        this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
        this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
        this.itemRenderer = new ItemRenderer(this);
        this.mcResourceManager.registerReloadListener(this.renderItem);
        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
        this.renderGlobal = new RenderGlobal(this);
        this.mcResourceManager.registerReloadListener(this.renderGlobal);
        this.guiAchievement = new GuiAchievement(this);
        GlStateManager.viewport("".length(), "".length(), this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        this.checkGLError(Minecraft.I[0x85 ^ 0x90]);
        this.ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            this.displayGuiScreen(new GuiMainMenu());
        }
        this.renderEngine.deleteTexture(this.mojangLogo);
        this.mojangLogo = null;
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        try {
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (OpenGLException ex) {
            this.gameSettings.enableVsync = ("".length() != 0);
            this.gameSettings.saveOptions();
        }
        this.renderGlobal.makeEntityOutlineShader();
    }
    
    public void freeMemory() {
        try {
            Minecraft.memoryReserve = new byte["".length()];
            this.renderGlobal.deleteAllDisplayLists();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (Throwable t) {}
        try {
            I();
            this.loadWorld(null);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (Throwable t2) {}
        I();
    }
    
    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = (" ".length() != 0);
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 1325 + 6953 - 6880 + 8602;
        }
    }
    
    public boolean isGamePaused() {
        return this.isGamePaused;
    }
    
    public void loadWorld(final WorldClient worldClient, final String s) {
        if (worldClient == null) {
            final NetHandlerPlayClient netHandler = this.getNetHandler();
            if (netHandler != null) {
                netHandler.cleanup();
            }
            if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
                this.theIntegratedServer.initiateShutdown();
                this.theIntegratedServer.setStaticInstance();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.clearAchievements();
            this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
        }
        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(s);
            this.loadingScreen.displayLoadingString(Minecraft.I[75 + 83 - 105 + 74]);
        }
        if (worldClient == null && this.theWorld != null) {
            this.mcResourcePackRepository.func_148529_f();
            this.ingameGUI.func_181029_i();
            this.setServerData(null);
            this.integratedServerIsRunning = ("".length() != 0);
        }
        this.mcSoundHandler.stopSounds();
        if ((this.theWorld = worldClient) != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(worldClient);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(worldClient);
            }
            if (this.thePlayer == null) {
                this.thePlayer = this.playerController.func_178892_a(worldClient, new StatFileWriter());
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.preparePlayerToSpawn();
            worldClient.spawnEntityInWorld(this.thePlayer);
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(this.thePlayer);
            this.renderViewEntity = this.thePlayer;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            this.saveLoader.flushCache();
            this.thePlayer = null;
        }
        I();
        this.systemTime = 0L;
    }
    
    @Override
    public ListenableFuture<Object> addScheduledTask(final Runnable runnable) {
        Validate.notNull((Object)runnable);
        return this.addScheduledTask(Executors.callable(runnable));
    }
    
    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }
    
    private static void l() {
        (I = new String[157 + 136 - 145 + 127])["".length()] = I("\u0013\"\u0013\u001d$\u0015\"\u0018F6\u0012.D\u001d8\u0013+\u000eF<\b-\n\u00076I7\u0005\u000e", "gGkiQ");
        Minecraft.I[" ".length()] = I("\u0013)\u0010\u0010%\u0004", "pEyuK");
        Minecraft.I["  ".length()] = I("", "UEpqg");
        Minecraft.I["   ".length()] = I("\u00068+;", "tWDOc");
        Minecraft.I[0x57 ^ 0x53] = I("$!\u000e\u0015\u0002\u0019#Z\u0014\u0018\u00126@A", "wDzak");
        Minecraft.I[0x5A ^ 0x5F] = I("k\n\t8)*6\u0002k\u0013\u0007y\u00058z", "CYlKZ");
        Minecraft.I[0x56 ^ 0x50] = I("D", "mQmvM");
        Minecraft.I[0x75 ^ 0x72] = I("!4*\u0004\"\t6*\n\"\u0006=c\u0017*\u0005?", "hZCpK");
        Minecraft.I[0xA4 ^ 0xAC] = I("+&\"\u0015\u0000\u0003$\"\u001b\b\u0016!$\u000f", "bHKai");
        Minecraft.I[0xAA ^ 0xA3] = I("\u00177(\u001e\u000017<Q\u0017=1=\u0001\u0006,=6Q\u0006- 7\u0006\u001cd", "ERXqr");
        Minecraft.I[0x42 ^ 0x48] = I("'\u0014+,&\u0017\u0019:12R\u001f<&9\u0000", "rzNTV");
        Minecraft.I[0xCD ^ 0xC6] = I("\"=%1(\u0018!#1<W6/7=\u0007'>;6W'?&7\u0000=v", "wSWTX");
        Minecraft.I[0xCD ^ 0xC1] = I("(6(2\u0016D7\u0007\u0007)\r\u000e\fOz", "dabuZ");
        Minecraft.I[0x24 ^ 0x29] = I("8\u00021\u0010\u001c9J1\u0003\n$\u00121\u0005\u001cf\u0017\"\u0005\u00128", "KgCfy");
        Minecraft.I[0x89 ^ 0x87] = I(")/\u0004'\u0012", "ZDmIa");
        Minecraft.I[0x50 ^ 0x5F] = I("\u001b\r0\b1", "hlFmB");
        Minecraft.I[0x55 ^ 0x45] = I(":\u00009\u00190<\u00002B#!\u000b5B$=\u0006(\u0004k>\u000b&", "NeAmE");
        Minecraft.I[0xA5 ^ 0xB4] = I("!\u00023\u001a\u0016'\u00028A\u0005:\t?A\u0002&\u0004\"\u0007<&\u0000*@\u0013;\u0000", "UgKnc");
        Minecraft.I[0x84 ^ 0x96] = I("1\u0006=z \u0015\u0015*.&\u0011", "atXZS");
        Minecraft.I[0x3F ^ 0x2C] = I("\u001a\f\u0017!\u0017<\b", "IxvSc");
        Minecraft.I[0x13 ^ 0x7] = I("<(>\u0019\u001a:(5", "HMFmo");
        Minecraft.I[0xB6 ^ 0xA3] = I(">-\u0000$o\u001d6\u0012\";\u001b2", "nBsPO");
        Minecraft.I[0x21 ^ 0x37] = I(">=\u001b#!\"\u0015\u00134!/9\u0001\b6%!\u00179", "JJrWB");
        Minecraft.I[0x20 ^ 0x37] = I("\u0019\"?\u0001)4j>M$4$>\u0004,6$0\bm.:#\u0019.2m9\u0019??,'", "ZMJmM");
        Minecraft.I[0x65 ^ 0x7D] = I("\u0017\"($\b(* 5Kke~oS", "ZKFAk");
        Minecraft.I[0xBC ^ 0xA5] = I(",.1'\t\u0001f0k\u001e\n5d;\u0004\u0017$(k\u000b\u00003)*\u0019", "oADKm");
        Minecraft.I[0xBC ^ 0xA6] = I(":66\u001e!|<:\u001f<\fdo\bce{)\u001e5", "SUYpR");
        Minecraft.I[0x1E ^ 0x5] = I("1\t\u000b\u0017\u001cw\u0003\u0007\u0016\u0001\u0007YV\u0001\\jD\u0014\u0017\b", "Xjdyo");
        Minecraft.I[0x5E ^ 0x42] = I("\u000f<\u001a\u001f2\"t\u001bS%)'O\u001a5#=", "LSosV");
        Minecraft.I[0x4D ^ 0x50] = I("\u001b\u00059O\u0018\u001a\u0013?O\u001d\t\u00046O\u0014\u0007\u00142\r", "hpWay");
        Minecraft.I[0x65 ^ 0x7B] = I("\u0012!\u0006|'\u0013#E$#_,\u0002&#\u001e*\u000e", "qNkRN");
        Minecraft.I[0xA6 ^ 0xB9] = I("\t\u001dO\f\u001c\u0005\u0006", "fnamn");
        Minecraft.I[0x62 ^ 0x42] = I("Np", "xDfZg");
        Minecraft.I[0xB7 ^ 0x96] = I("\u0006?*\u0011\u0003r>&\u0017\u001ar\"/\u0006\u001432", "RVGtq");
        Minecraft.I[0xAE ^ 0x8C] = I("5\u00045\u0003\u001b{\u00041\u0000\u001c$\u0002'", "VvTps");
        Minecraft.I[0x5E ^ 0x7D] = I("4\u00027\u0010\u000ez", "WpVcf");
        Minecraft.I[0xBB ^ 0x9F] = I("\t\r\u0017\b@=9C\u0015\t/<&_\u0000\u001dZ\u001d\u0002", "ptnqm");
        Minecraft.I[0x80 ^ 0xA5] = I("w\u0017\u000e#\u00114\u0000L>\f.", "ZtbJt");
        Minecraft.I[0x16 ^ 0x30] = I("`\u0001V\u0018qc\u0006\u001657c\"\u00059!+$\u0013yr\u00003\u0016+:c3\u0012(=15W+35$\u0013x&,{W{\u0012b\u0001Tx", "CAwXR");
        Minecraft.I[0x61 ^ 0x46] = I("E'i\u0002FF )/\u0000F\u0004:#\u0016\u000e\u0002,cE%\u0015)1\rF\u0015-2\n\u0014\u0013h1\u0004\u0010\u0002,b\u0011\t]ha%G'kb", "fgHBe");
        Minecraft.I[0x7C ^ 0x54] = I("G\"I\u0005@D%\u0017(\u0006D\u0001\u0004$\u0010\f\u0007\u0012dC'\u0010\u00176\u000bD\u0010\u00135\f\u0016\u0016V&\f\u0011\u000e\u0012e\r\u000b\u0016V'\u0006D\u0011\u00173\u0006\u0000LVf#[\"U", "dbvEc");
        Minecraft.I[0xA8 ^ 0x81] = I("\r#6-.:b&84!0c92'6 \"/ %oj4+/,</ %c+*\"b\"95'%-/\"n0&9);0 /6/!(9", "NBCJF");
        Minecraft.I[0x24 ^ 0xE] = I("\u0015\u001d\n+", "yrmDi");
        Minecraft.I[0x91 ^ 0xBA] = I("3<\u000b\u000b'\u0003r\u001e\u0006k\n=\u000b\rk\n=\r\u0006qF", "fRjiK");
        Minecraft.I[0x62 ^ 0x4E] = I("NWuimNWuimM3\u001aj\u000b?&\u0019\u0018nNWuimNWuim", "mtVJN");
        Minecraft.I[0x1C ^ 0x31] = I("+z", "kZKjD");
        Minecraft.I[0x25 ^ 0xB] = I("JG", "pgTmz");
        Minecraft.I[0x5E ^ 0x71] = I("\u0017\u00117\u001a6-\u000b?K", "DeXjF");
        Minecraft.I[0x43 ^ 0x73] = I("\"&\u0018\u001e", "PIwjZ");
        Minecraft.I[0x73 ^ 0x42] = I("\u001a\u0004 \u0007.\u001c\u000b-\u0006\u000f\u0011\u0002+\u0017>\b\u0005$\u00079", "igHbJ");
        Minecraft.I[0x70 ^ 0x42] = I("?&)\u0005", "KOJnn");
        Minecraft.I[0x9E ^ 0xAD] = I("<\u0004\u0003\u0015\u0017\"\u0012\u000357>\u0004\t5\u0001", "LvfGr");
        Minecraft.I[0x4E ^ 0x7A] = I("\b=+G*=!*\u0002*", "XONgX");
        Minecraft.I[0xF1 ^ 0xC4] = I("\u00108,+3", "cWYEW");
        Minecraft.I[0x23 ^ 0x15] = I("5\u0002,\u000b\u001c5", "GgBoy");
        Minecraft.I[0x97 ^ 0xA0] = I("\t*8<!\f:", "mCKLM");
        Minecraft.I[0x22 ^ 0x1A] = I("\n4\u0002.\u0002\b;\u000b.\"\b'", "mUoKP");
        Minecraft.I[0xB6 ^ 0x8F] = I("\b\u001d\n\r", "zreyY");
        Minecraft.I[0x10 ^ 0x2A] = I("\u000b\u001f\u0016\u000e/\u0015", "xkdkN");
        Minecraft.I[0x3A ^ 0x1] = I("\f\u0005'\u001b\u0013\u001c", "yuCzg");
        Minecraft.I[0x43 ^ 0x7F] = I("\u0001-\u00178&\u0006", "rXuUO");
        Minecraft.I[0x84 ^ 0xB9] = I(";\u0016*\u0013t\u0019\u001c7\u00031\u0019", "kyYgT");
        Minecraft.I[0x3C ^ 0x2] = I("`)D436mLw'e.\f'-.m\u0011\"'$9\u0001w0lm0hc`>A!f6h\u0017w0", "EMdRC");
        Minecraft.I[0x75 ^ 0x4A] = I("\u001d", "nMAnC");
        Minecraft.I[0x57 ^ 0x17] = I("", "Cktww");
        Minecraft.I[0x86 ^ 0xC7] = I(" +\u000f", "IEimC");
        Minecraft.I[0x1 ^ 0x43] = I("M!\u00063\u0007\u000e", "mWuJi");
        Minecraft.I[0xF2 ^ 0xB1] = I("", "sawDd");
        Minecraft.I[0x5E ^ 0x1A] = I("", "gnfss");
        Minecraft.I[0x10 ^ 0x55] = I("g7 \u0005\u0005", "GQAvq");
        Minecraft.I[0x42 ^ 0x4] = I("", "SHrlO");
        Minecraft.I[0xDF ^ 0x98] = I("K\u0003.2\u000eF\u0006#.\u000f\u000f\u0016", "keOAz");
        Minecraft.I[0x58 ^ 0x10] = I("b\u00120>\n;Y2<\u00067\u0010\"", "BtQPi");
        Minecraft.I[0xF5 ^ 0xBC] = I("z?\u0000\u001e", "ZIbqj");
        Minecraft.I[0xF2 ^ 0xB8] = I("", "mOpRU");
        Minecraft.I[0x48 ^ 0x3] = I("\t<\u0003\u0019\r\u0002%\u0004*\u0013\u000e%\u0004", "oLpud");
        Minecraft.I[0xF0 ^ 0xBC] = I("*\f\u0005\u0007>/\u001c)\u0002\"*\u0004\u0002\u0012", "NevwR");
        Minecraft.I[0x22 ^ 0x6F] = I("o", "ARasv");
        Minecraft.I[0x62 ^ 0x2C] = I("!%8\u0013\u00017\"-\n\u00010", "TKKcd");
        Minecraft.I[0xC9 ^ 0x86] = I("Z", "tvOKI");
        Minecraft.I[0xEE ^ 0xBE] = I("TIv`CG", "wjFNs");
        Minecraft.I[0xF3 ^ 0xA2] = I("", "fXXDM");
        Minecraft.I[0x29 ^ 0x7B] = I("3\u001b89\n%\u001c- \n\"", "FuKIo");
        Minecraft.I[0x1B ^ 0x48] = I("\f\\(f", "WluFZ");
        Minecraft.I[0x7B ^ 0x2F] = I("\u0018=\".H", "Jrmzh");
        Minecraft.I[0x95 ^ 0xC0] = I("r", "RnOsq");
        Minecraft.I[0x5C ^ 0xA] = I("v", "SsCTw");
        Minecraft.I[0x17 ^ 0x40] = I("", "tFlAL");
        Minecraft.I[0xE5 ^ 0xBD] = I("6\n=\u001e7 \r(\u00077'", "CdNnR");
        Minecraft.I[0x19 ^ 0x40] = I("9E1E", "bzlet");
        Minecraft.I[0x37 ^ 0x6D] = I("\u0003", "XNmpG");
        Minecraft.I[0x98 ^ 0xC3] = I("\u0016k", "KKSwu");
        Minecraft.I[0x5A ^ 0x6] = I("v", "SJGLT");
        Minecraft.I[0x37 ^ 0x6A] = I("u", "PZfrY");
        Minecraft.I[0x41 ^ 0x1F] = I(";\u0010/\u0001P\u0007\u00007\u0018\u0002\u001b\u0000'M\u0011\u0006Ed\u0005\u0019\u00017&\u001e\u0005\u0019\u0011dAP\u0001\r*\u001eP\u0006\r,\u0018\u001c\u0011\u000bd\u0019P\u001d\u00043\u001d\u0015\u001bD", "ueCmp");
        Minecraft.I[0x18 ^ 0x47] = I("\u00149\u0003*P()\u001b3\u00024)\u000bf\u0011)lH.\u0019.\u001e\n5\u000568HjP.$\u00065P)$\u00003\u001c>\"H2P2-\u001f6\u00154m", "ZLoFp");
        Minecraft.I[0xA ^ 0x6A] = I("\u001a\u00162\u0002\u00027^3N\u00126\u001e \u0002\u0003y\u001f2\u0002\n*\u001a5\u000b\u00037", "YyGnf");
        Minecraft.I[0x72 ^ 0x13] = I("(/\u000b", "OZbpt");
        Minecraft.I[0xD3 ^ 0xB1] = I("\u0014\u0017\u0019\u0016\t\u001c\u0012\u0011", "svtsD");
        Minecraft.I[0x2B ^ 0x48] = I("\u0003\u001f\u000e\u0002#\u0005\u001f\u0005", "wzvvV");
        Minecraft.I[0x25 ^ 0x41] = I("\u0005 03;9>3r<3\"17!p5\"7!$#", "PPTRO");
        Minecraft.I[0x76 ^ 0x13] = I("%,*\u000e\u001a\u0010/(K\n\u00078)\u000e\u0017", "dJLky");
        Minecraft.I[0xCC ^ 0xAA] = I("!56\u001c\f\u001cv*\u0018\u0004\u0017", "rVDyi");
        Minecraft.I[0x73 ^ 0x14] = I("\u0012\u0007\u000b&\u0001(\tH>\u000b4\u000b\r#", "FnhMh");
        Minecraft.I[0x5B ^ 0x33] = I("\u0015\u0015)\u0013\u0017 \u0016+V\u00077\u0001*\u0013\u001a", "TsOvt");
        Minecraft.I[0xD6 ^ 0xBF] = I("\u000b-3\")6n/&!=", "XNAGL");
        Minecraft.I[0x56 ^ 0x3C] = I("\u0007\u000e#+\u0001", "jaVXd");
        Minecraft.I[0xAF ^ 0xC4] = I(";\u0014\n,$1\u0003\u0017", "PqsNK");
        Minecraft.I[0xAA ^ 0xC6] = I("/\b&\f\u0003\u000e\u00051Y\u0016\u0010\u0000/\u001e\u0007\u0010\f,Y\u0006\u0007\u000b=\u001eB\u0001\u001b)\n\n", "biHyb");
        Minecraft.I[0xC5 ^ 0xA8] = I("{", "TAWPr");
        Minecraft.I[0xD1 ^ 0xBF] = I("\u0015'&\u0000;\u0017(/\u0000\u001b\u00174", "rFKei");
        Minecraft.I[0xEE ^ 0x81] = I("\u0000/$45>/<5<\u001e/ ", "lJRQY");
        Minecraft.I[0x2A ^ 0x5A] = I("\u001b\u0003\u00006\u000b", "wfvSg");
        Minecraft.I[0x49 ^ 0x38] = I("\u0014/\u0002.:%>\u000e%j89A<%#;\u0005k>84\n", "QWaKJ");
        Minecraft.I[0xD5 ^ 0xA7] = I("(\u000e \u00134\u001d\r\"V;\f\u001e#\u001a", "ihFvW");
        Minecraft.I[0x32 ^ 0x41] = I("\u00117\u001d2\u001e$(", "AErPr");
        Minecraft.I[0xE5 ^ 0x91] = I("\u0004\u001f\u0012\u0013\u001fh\u0013\u0017V\u001d=\u0016\bW", "Hzdvs");
        Minecraft.I[0xF9 ^ 0x8C] = I("\u0004\n'\u00156\u0011\u0001\u001a\u00114\u000e", "edNxW");
        Minecraft.I[0xDF ^ 0xA9] = I("\u001b\u00181\u001a1\b\u0015&\u001d", "kyCnX");
        Minecraft.I[0xEA ^ 0x9D] = I("\b\u0015\u0016\u0003\u0002\u0016\u0017;\b\u0005\u0016\u0015\u001b\u0013\u0002\u0017\u001e", "xpxgk");
        Minecraft.I[0x12 ^ 0x6A] = I("\u001b\u00064\u001d\u0018!\u001c2O\u0005&\u00060\b\u001e)\u00060\u000bL;\u0017'\u0019\t:", "HrUol");
        Minecraft.I[0x4B ^ 0x32] = I("\u001c0\u0013\u0018-&*\u0015J0!0\u0017\r+.0\u0017\u000ey<!\u0000\u001c<=", "ODrjY");
        Minecraft.I[0xD9 ^ 0xA3] = I("\u0001&9\u001c-m\n\u000b", "MCOyA");
        Minecraft.I[0xB ^ 0x70] = I("!\u0002\u001f\u0010-M)\b\u0018$", "mgiuA");
        Minecraft.I[0x48 ^ 0x34] = I("\u000b=,\u0000[\n7#\u0011\u001c\b?\u000e\u0010\u0003\u00034", "fXBuu");
        Minecraft.I[0xFD ^ 0x80] = I("", "XaMEf");
        Minecraft.I[0xE6 ^ 0x98] = I("", "EufNm");
        Minecraft.I[29 + 118 - 69 + 49] = I("", "LEeYr");
        Minecraft.I[115 + 86 - 133 + 60] = I("\u001b\u0014)\u0000\u0016", "TcGed");
        Minecraft.I[9 + 20 + 22 + 78] = I("%\u0005\u0001\b$", "jromV");
        Minecraft.I[120 + 122 - 151 + 39] = I("\t-;-4\u00151 $*", "ZFNAX");
        Minecraft.I[45 + 40 + 26 + 20] = I("#'?:\u0012$%$0\r\u0018\u001f1>", "aKPYy");
        Minecraft.I[48 + 108 - 55 + 31] = I("KS\u0005!#J", "cxKcw");
        Minecraft.I[102 + 28 - 52 + 55] = I("\n\"\u001f\u0004", "FMmaY");
        Minecraft.I[52 + 91 - 137 + 128] = I("\u0003\u001b\u0001?\u001d\u0006\u000b", "grrOq");
        Minecraft.I[88 + 74 - 45 + 18] = I("\u00008%\u0006\u0001$<4H4)+#\u0001\r\"", "LYPhb");
        Minecraft.I[32 + 115 - 135 + 124] = I("\u0014\u0003,\u001e#", "XTfYo");
        Minecraft.I[101 + 68 - 64 + 32] = I("$\t,$\u000f'", "kyIJH");
        Minecraft.I[136 + 115 - 120 + 7] = I("\b\tI)6?6", "OEijW");
        Minecraft.I[3 + 73 - 14 + 77] = I("\u00139.\u001b(f\u001c\u0005:<", "FJGuO");
        Minecraft.I[94 + 129 - 200 + 117] = I("\u0001\u0010d\u0000!,\u0007!)", "HcDMN");
        Minecraft.I[8 + 28 + 9 + 96] = I("\u0006\b\b\u0004", "RqxaO");
        Minecraft.I[19 + 73 + 30 + 20] = I("\u0013\u00025;\u00163\u0004#t3 \u0004-'", "AgFTc");
        Minecraft.I[139 + 86 - 186 + 104] = I("\b\u001f;4\u001d%\u001ei\n\u0019%\r<'\u001f.", "KjIFx");
        Minecraft.I[68 + 1 + 47 + 28] = I("&\u001d\u0015#\u0004\u001a\n\be=\u0019\u001c\u00131\u0004\u0019\u0001", "vozEm");
        Minecraft.I[38 + 7 - 5 + 105] = I("5;\r", "vkXSY");
        Minecraft.I[95 + 52 - 8 + 7] = I("!? ", "GOSho");
        Minecraft.I[106 + 37 - 6 + 10] = I("9\u00175 !\u0010\u0001\"/ #\u0001(", "OdLNB");
        Minecraft.I[9 + 29 + 10 + 100] = I("\u00079\u0002\u001a\u0001\u0002).\f\u001f\u0006!\u0004\u000f\u0003\u0000)", "cPqjm");
        Minecraft.I[13 + 43 + 83 + 10] = I("\u001e*%\u001e8\u001b:\t\u001a-\n&", "zCVnT");
        Minecraft.I[147 + 22 - 43 + 24] = I("\u0017?\u0007!+\u00128\u000e(6", "qJkMX");
        Minecraft.I[97 + 123 - 137 + 68] = I("\u0014\u001f\u0003\u0007\b\u0014\u0013\t", "cvmcg");
        Minecraft.I[110 + 98 - 190 + 134] = I("66\u0002\u000f\u001b-.\t", "DClPo");
        Minecraft.I[109 + 95 - 170 + 119] = I("/\u0006 (\u001f\"\u0007\r;\u00198\u001a=4", "LsRZz");
        Minecraft.I[107 + 46 - 99 + 100] = I("9*\u0001\r50", "UCuyY");
        Minecraft.I[111 + 54 - 44 + 34] = I("\u0014\u000e ", "vgGUT");
        Minecraft.I[49 + 61 - 105 + 151] = I("\u0006%4\u0013+\r%5\t9", "cKPzJ");
        Minecraft.I[153 + 0 - 67 + 71] = I("\u0011-\u0000 \u0017\u0011+\u0016\u0010\u0012\u0002+\u0018<", "cHsOb");
        Minecraft.I[153 + 140 - 226 + 91] = I("!\u001d&,?!\u001b0\u001c:2\u001b>\u0018", "SxUCJ");
        Minecraft.I[64 + 113 - 36 + 18] = I("?", "bcTIl");
        Minecraft.I[130 + 30 - 84 + 84] = I("\u0012\r)=8\u0004\u0011\u0019\")\u0013\u0017(7:", "acFRH");
        Minecraft.I[27 + 137 - 27 + 24] = I("\u000b#\u0006\"0\r+*:8\r", "cLuVY");
        Minecraft.I[160 + 109 - 259 + 152] = I("#\u001e<\f>5\u0007>\n+5\u0005", "PwRkR");
        Minecraft.I[57 + 119 - 60 + 47] = I(">5$.\u0000 >\u001a;\b ", "NYEWi");
        Minecraft.I[18 + 128 - 84 + 102] = I("%\u0007:3:8\u001e7>6:", "HrVGS");
        Minecraft.I[87 + 2 + 54 + 22] = I("\u0015\u00199\u0013)\u001c3*-+\u001f", "zlMLF");
        Minecraft.I[107 + 150 - 117 + 26] = I("\u001e\u0018#\u001d\u0004\u001d70\u0016\u0011\u0002\u0001)\u001d", "qhFsc");
        Minecraft.I[97 + 38 - 10 + 42] = I(">5+\u0002\u0002=\u001a8\t\u000b5*<", "QENle");
        Minecraft.I[160 + 123 - 229 + 114] = I("\":/\u0004:5\t$\u00135/2", "AVFaT");
        Minecraft.I[30 + 11 - 14 + 142] = I("?$;*\u000b; *\u001b\u001e67=-\u0007=", "SENDh");
        Minecraft.I[15 + 59 + 32 + 64] = I("\u00139;9\u0017\u0004&?\u001b$6\n\u0005(\u0004\u0015,\u0017\u0005\u0019\u0012\n\u0005(\u0004\u0015,\u0017\u0007", "tUdZv");
        Minecraft.I[151 + 103 - 179 + 96] = I("\u0012\u001a\u001d0\b\u0005\u0005\u0019\u0012;7) 2\u001a\u0010)+=\u001a\u0001\u0017,0\f(", "uvBSi");
        Minecraft.I[163 + 60 - 204 + 153] = I("!\u0003=\u001276\u001c90\u0004\u00040\u0000\u001d3(\u000b=\u0017#(\f=\u0014.2\n\f\u00153\"2", "FobqV");
        Minecraft.I[55 + 5 - 39 + 152] = I("$\u001b\u0011&\n3\u0004\u0015\u00049\u0001(-)\u000e\"\u0005\u0011'\u001e%\u0011+74,\u0015$ \b7*", "CwNEk");
        Minecraft.I[171 + 54 - 142 + 91] = I("393\u0013\u0010$&71#\u0016\n\u000f\u001f\u001d;'3\u0012\u000423\t\u0002.29\u0003\u0011\u0005\t", "TUlpq");
        Minecraft.I[73 + 58 - 113 + 157] = I("+>\u001b),<!\u001f\u000b\u001f\u000e\r'% <30#/%>->4\u0011", "LRDJM");
        Minecraft.I[108 + 135 - 224 + 157] = I("\u000b\u00009!#\u001c\u001f=\u0003\u0010.3\u0005-/\u001c\u001e\u000311\t\b96'\u0014\u0018\u00130'3\u001c\u000f:'\u00003\u00156-\u001e\r\u0001'\u001f", "llfBB");
        Minecraft.I[119 + 167 - 210 + 101] = I("\u0001\u0019\u0014-0\u0016\u0006\u0010\u000f\u0003$*(!<\u0016\u0000?+\u000e\u0015\u001d**4\u0014(", "fuKNQ");
        Minecraft.I[16 + 13 + 64 + 85] = I("\u0015\u001a&5\u001b\u0002\u0005\"\u0017(0)\u001a9\n\u000b)\u001b#\u001c\u0014\u0013\u000b\u000b", "rvyVz");
        Minecraft.I[117 + 48 - 107 + 121] = I("*;&\u000f(=$\"-\u001b\u000f\b\u001a\u000394\b\u0010\u0001(*2$", "MWylI");
        Minecraft.I[176 + 88 - 118 + 34] = I("\u0013.\u00163\r\u00041\u0012\u0011>6\u001d-5\u001c\u0000*\u00162\u0019\u0012$,\"3\u0012.&1\u0018)", "tBIPl");
        Minecraft.I[8 + 26 - 22 + 169] = I("6\u001b\b5\u0006!\u0004\f\u00175\u0013(49\n!\u0002#38\"\u001f62\u0002#*", "QwWVg");
        Minecraft.I[92 + 35 - 69 + 124] = I(" ;2\u001b77$69\u0004\u0005\b\u000e\u0017&>\b\u000f\r0!2\u001f%", "GWmxV");
        Minecraft.I[126 + 61 - 72 + 68] = I("!/7:\f603\u0018?\u0004\u001c\u000b6\u001d?\u001c\u00014\f!&5", "FChYm");
        Minecraft.I[73 + 171 - 137 + 77] = I("\u0011)\u0006\u000b\u0011\u00066\u0002)\"4\u001a=\r\u0000\u0002-\u0006\n\u0005\u0010#<\u001a/\u0010)6\t\u0004+", "vEYhp");
        Minecraft.I[177 + 45 - 94 + 57] = I("3\u0015\u001c\u0004\u0012$\n\u0018&!\u0016&'\u0002\u0003 \u0011\u001c\u0004\u001f5\u00143:", "TyCgs");
        Minecraft.I[60 + 79 - 129 + 176] = I("\"\u001a\u0015\u0016\"5\u0005\u00114\u0011\u0007).\u001031\u001e\u0015\u0001&=\u0002?\u0007&\u0018", "EvJuC");
        Minecraft.I[168 + 167 - 290 + 142] = I("\u0013/\b$0\u00040\f\u0006\u00036\u001c350\u0003\u001c527\u0012&%4\f", "tCWGQ");
        Minecraft.I[56 + 113 + 15 + 4] = I("%\u0000\u0013\u001262\u001f\u00170\u0005\u00003(\u0003653.\u00041$\t>\u0002\b \u0000)\u001f3\u001f", "BlLqW");
        Minecraft.I[164 + 117 - 93 + 1] = I("\u001e\u0015;\u0006\u0012\t\n?$!;&\u0000\u0017\u0012\u000e&\u0001\t\u0016\u0014\u001c\n\u0011\u0000&\u001b\u0005\u0016\u0016&\u000f\u0001\u0017\u0007\u001c\u00019", "yydes");
        Minecraft.I[51 + 130 - 1 + 10] = I("\u0005\r/\u000f\b\u0012\u0012+-; >\u0014\u001e\b\u0015>\u0019\u0002\r\u000b\u0013\u0015\u000f\u001d?", "bapli");
        Minecraft.I[73 + 25 - 20 + 113] = I("\u001d\u0006\u0018\u00106\n\u0019\u001c2\u000585#\u00016\r5.\u001d$\u000e\u000b)\u00102\u001e7", "zjGsW");
        Minecraft.I[93 + 168 - 209 + 140] = I("7\u0007\u001e:$ \u0018\u001a\u0018\u0017\u00124$!5<\u0002\"01\u000f\n5-79\t\u001e5*3\n50*>6", "PkAYE");
        Minecraft.I[88 + 94 - 171 + 182] = I(">-\n$\u000b)2\u000e\u00068\u001b\u001e0?\u001a5(6.\u001e\u00064;.\f638\u0018\u00066\"43\u00036/\b", "YAUGj");
        Minecraft.I[92 + 35 - 15 + 82] = I("\u000f\n'&\f\u0018\u0015#\u0004?*9\u001e7\f\u000f\u000b\u001d+\u00197\n\u0019<\b\u001a9\u000e,\b\u001f\u0016\u00177\u00195", "hfxEm");
        Minecraft.I[182 + 154 - 183 + 42] = I(" \u00042 ,7\u001b6\u0002\u001f\u00057\u000b1, \u0005\b-9\u0018\u0018\u001f,*5\t\u0000\u001e", "GhmCM");
        Minecraft.I[7 + 15 + 107 + 67] = I("\u0001\u0006<\u0006)\u0016\u00198$\u001a$5\u0005\u0017)\u0001\u0007\u0006\u000b<9\u0019\u000b\u0004,\u0003\u0018>", "fjceH");
        Minecraft.I[181 + 94 - 125 + 47] = I("\u00016'\u0015\n\u0016)#79$\u0005\u001e\u0004\n\u00017\u001d\u0018\u001f9*\n\u0019\f\u0014;\u0015)\u0018\u000e;\u001c\u0019\u001c;", "fZxvk");
        Minecraft.I[135 + 98 - 229 + 194] = I("\u0003\r\u0012)\f\u0014\u0012\u0016\u000b?&>+8\f\t\u0004/?\u000b\u0002\u0004?\u0015\u0002\u0006\u000b()\u00199", "daMJm");
        Minecraft.I[103 + 111 - 98 + 83] = I(",$>4';;:\u0016\u0014\t\u0017\u0007%'&-\u0003\" --\u0013\b5\u0019\u000f#\n", "KHaWF");
        Minecraft.I[96 + 133 - 49 + 20] = I(".$\u000f%\u00179;\u000b\u0007$\u000b\u00177#\u0019$-$4\u000f\u0016;8'\u0012,:d\u001b", "IHPFv");
        Minecraft.I[42 + 147 - 92 + 104] = I("&\u0005\n2\u00001\u001a\u000e\u00103\u000362!\u0014\u001e\u001a=0\u0005$\u001b`\f", "AiUQa");
        Minecraft.I[12 + 43 + 14 + 133] = I("\u001e#\u0015\u0015\u0013\t<\u00117 ;\u0010\"\u0017\u001e\u001f\u0010,\u001a\u001d\u0018;\u0015\u0006\u001b\u0001*&+", "yOJvr");
        Minecraft.I[157 + 8 - 57 + 95] = I("\u000f'\u0014\u00018\u00188\u0010#\u000b*\u0014#\u00035\u000e\u0014-\u000e6\t?\u0014\u0014<\u001a?.\u001a\u0004", "hKKbY");
        Minecraft.I[70 + 132 - 12 + 14] = I(",\u0001<\u0019\r;\u001e8;>\t2\n\u0014\u001f?\f\r\u0019\t/2\u0002\b\u001e*\u0014\u0010'", "Kmczl");
        Minecraft.I[38 + 124 - 48 + 91] = I("-\u0003\r\u001a#:\u001c\t8\u0010\b0?\u00182\u0015\r'\u001f$/\u001d\r\u0018.#\b<\u0014'$\u001b\u000f", "JoRyB");
        Minecraft.I[73 + 51 + 44 + 38] = I("#\u001f\u0016,\n4\u0000\u0012\u000e9\u0006,$.\u001b\u001b\u0011<)\r!\u0001\u0016=\n*\u0014,\u0012", "DsIOk");
        Minecraft.I[187 + 161 - 289 + 148] = I("0\u0003\u001b;4'\u001c\u001f\u0019\u0007\u00150)-9#\u0006798'\u0003!\u0005", "WoDXU");
        Minecraft.I[174 + 45 - 96 + 85] = I("0(\u0011\u0000''7\u0015\"\u0014\u0015\u001b#\u0016*#-:\u0006>#1<\u0006\u001b", "WDNcF");
        Minecraft.I[98 + 91 - 42 + 62] = I("*%\n\f\f=:\u000e.?\u000f\u0016:\f\u000e!<&\u0006\u0002#\u0016$\u001a\b?0g2", "MIUom");
        Minecraft.I[100 + 146 - 238 + 202] = I("$ \u0006+13?\u0002\t\u0002\u0001\u0013)!(& \u0006*%%*<:\u000f,.3-37\u0011", "CLYHP");
        Minecraft.I[139 + 53 - 73 + 92] = I("%%\u000b\u0007\"2:\u000f%\u0011\u0000\u0016'\u0001\"/%1\u00170\u001d*!\u0006&\u001d$5\u0014\u001e", "BITdC");
        Minecraft.I[65 + 166 - 125 + 106] = I(")=\r\u00142>\"\t6\u0001\f\u000e!\u001f2*4 (<,;7\u0014'=\f", "NQRwS");
        Minecraft.I[204 + 89 - 222 + 142] = I("\u0010/,3+\u00070(\u0011\u00185\u001c\u00008+\u0013&\u0001\u000f9\u0003&\u001d3#\u001b\u001c\u0016(:\u00181\u0007\r", "wCsPJ");
        Minecraft.I[65 + 192 - 51 + 8] = I("*\u000f\u0010'-=\u0010\u0014\u0005\u001e\u000f<<,-)\u0006=\u001b8(\u001b;1>(<#+(\u0010", "McODL");
        Minecraft.I[138 + 11 - 16 + 82] = I(".$;\u000599;?'\n\u000b\u0017\u0017\u000e9-'\u0013;", "IHdfX");
        Minecraft.I[9 + 196 - 27 + 38] = I("3<\u001c,\u000e$#\u0018\u000e=\u0016\u000f0'\u000e0?4\u0010\u000e92**\u0001 \r", "TPCOo");
        Minecraft.I[1 + 133 - 7 + 90] = I("\u0014-=\u00141\u0003296\u00021\u001e\u0011\u00035\u001d\"\u000b\u001b\u000f\u0007$\u001a\u0003%\u0001(\f\u0010\r", "sAbwP");
        Minecraft.I[55 + 204 - 185 + 144] = I("7\u000b%\"\b \u0014!\u0000;\u00128\t8\u00073:", "PgzAi");
        Minecraft.I[179 + 121 - 281 + 200] = I("\t\t\u0015*\r\u001e\u0016\u0011\b>,:>,\u001f\u001d\u0000&%\r\u001a\f%'3\u001d\r+-\t\u001c8", "neJIl");
        Minecraft.I[97 + 140 - 70 + 53] = I(",\u000b-\n&;\u0014)(\u0015\t8\u0006\f??\u0012\u0000\f\u0018)\b\u0000\r\"98\u0011\u0005&&\u0017/", "KgriG");
        Minecraft.I[50 + 203 - 187 + 155] = I("\u0014>\u0012\u0019\u0000\u0003!\u0016;31\r9\u001f\u0019\u0007'?\u001f>\u0011'+\u001c\u0004\u0001\r\"\u0018\u000b\u001619'", "sRMza");
        Minecraft.I[149 + 72 - 117 + 118] = I(".'*\u0002298. \u0001\u000b\u0014\u0001\u0004+=>\u0007\u0004\f*>\u0017\u0004\f$*\u0005<", "IKuaS");
        Minecraft.I[190 + 72 - 116 + 77] = I("$\n\u0006\u000b\u00103\u0015\u0002)#\u00019-\r\t7\u0013+\r. \u0013;\r..\u0007)7\u00101\u00148\u0011,", "CfYhq");
        Minecraft.I[184 + 34 - 74 + 80] = I(")\u0000\u0018'+>\u001f\u001c\u0005\u0018\f33!2:\u00195!\u0015 \u0003)\u001b:!\u001b\"6\u0015!\n\u00180=!1", "NlGDJ");
        Minecraft.I[0 + 31 + 107 + 87] = I("&$5\u0017\r1;15>\u0003\u0017\u001f\u001a\u0005''\u0018\u00193#=\f\u0012\t3\u0017\u0005\u0016\u0006$+\u001e)", "AHjtl");
        Minecraft.I[77 + 170 - 172 + 151] = I("#\u000f\u00145.4\u0010\u0010\u0017\u001d\u0006<=3=0\u00063\t-(\u0006%2\u0012", "DcKVO");
        Minecraft.I[0 + 64 - 6 + 169] = I("\b:++\u0002\u001f%/\t1-\t\u0002-\u0011\u001b3\f\u0017\u0001\u001a0\u0012-\u001109\u0016\"\u0006\f\")", "oVtHc");
        Minecraft.I[101 + 168 - 173 + 132] = I("\u0014*\u001c\u001b\r\u00035\u00189>1\u00195\u001d\u001e\u0007#;'\u001c\u0001)$\n\r\u001e\u001b", "sFCxl");
        Minecraft.I[122 + 9 + 69 + 29] = I("1\r35\u0005&\u00127\u00176\u0014>\u001a3\u0016\"\u0004\u0014\t\u0017>\u0000\b3\u0016\u000b", "ValVd");
        Minecraft.I[53 + 27 + 75 + 75] = I("\f\u00025\u000e*\u001b\u001d1(\u0013?1\b\u0004%\u000f\u000f\b\u0001.4\u001b\u0004\u0004-\u0004\u001c\u00070", "knjmK");
        Minecraft.I[73 + 27 - 30 + 161] = I("+\r2.;<\u00126\b\u0002\u0018>\u000f!?\"\u00052(+9\u0000\u0019$5\">\u001e(*-\u0013\f9?\u0011", "LamMZ");
        Minecraft.I[28 + 164 - 183 + 223] = I("=)\u0011$\u000f*6\u0015\u00026\u000e\u001a,+\u000b4!\u0011!\u001b4&\u00114\u000b*$<&\u001a?\u0018", "ZENGn");
        Minecraft.I[121 + 125 - 104 + 91] = I("$'4\"3380\u0004\n\u0017\u0014\t-7-/4,;-&\n9\u000f", "CKkAR");
        Minecraft.I[61 + 222 - 84 + 35] = I("\u0015\u0019)/1\u0002\u0006-\t\b&*\u0014 5\u001c\u0011)?%\u0010\u0001\u0004-3\u0006(", "ruvLP");
        Minecraft.I[49 + 20 + 7 + 159] = I("?)\u0011\r\u0010(6\u0015+)\f\u001a*\u001c\u0010/\u001a'\u0000\u0002,$ \r\u0014<\u0018", "XENnq");
        Minecraft.I[26 + 37 + 127 + 46] = I("\u000b\u0016\u001c\u00052\u001c\t\u0018#\u000b8%%\u00142\u0001\u001f!\u00135\n\u001f19>\u0019\u00167\u000f \r\u00173\n61", "lzCfS");
        Minecraft.I[210 + 116 - 292 + 203] = I("\u0010\u000b7\u0001\u0004\u0007\u00143'=#8\u000e\u0010\u0004\u001a\u0002\n\u0017\u0003\u0011\u0002\u001a=\n\u0015\r\r\u0001\u0011*", "wghbe");
        Minecraft.I[228 + 143 - 226 + 93] = I("=-0\t&*24/\u001f\u000e\u001e\t\u0018&7$\r\u001f!<$\u001d54\b\u0006-7", "ZAojG");
        Minecraft.I[217 + 59 - 258 + 221] = I("\u000b9\u000b\u0015&\u001c&\u000f3\u001f8\n3\u0013(\u00010 \u0004>3&<\u0017#\t'`+", "lUTvG");
        Minecraft.I[118 + 16 + 105 + 1] = I("!/\u0007\u0000960\u0003&\u0000\u0012\u001c?\u0013-\u00193*\f?4\"5<('19\u000e=2&*\u0010\u0005", "FCXcX");
        Minecraft.I[182 + 213 - 226 + 72] = I("\u001267+\"\u0005)3\r\u001b!\u0005\u000f86*)\u0000)'\u0010(\\\u0015", "uZhHC");
        Minecraft.I[52 + 227 - 272 + 235] = I(">;\u0016/\u0007)$\u0012\t>\r\b$9\n->\u0016(\u00148 \u0016-\u0014+60?;", "YWILf");
        Minecraft.I[137 + 17 - 94 + 183] = I("\u0013$0:\u000e\u0004;4\u001c7 \u0017\u001f8\f\u001f-\u000b\u0006\u000b\u00118\u001b10\u0007<\n7\f\u001d$2", "tHoYo");
        Minecraft.I[7 + 197 + 13 + 27] = I("*>\u0018\u0016\t=!\u001c00\u0019\r7\u0014\u0004(&3\u0010\f\u0012&\"\r\u001c8 \"(", "MRGuh");
        Minecraft.I[31 + 150 + 19 + 45] = I("\"\u000f1\u001a\u00075\u00105<>\u0011<\u001c\u001c\u0015&\u0002\u0002\u001c9+\f\u001c\u0014\u0007)>", "Ecnyf");
        Minecraft.I[97 + 219 - 105 + 35] = I(")9>\u000e\u000b>&:(2\u001a\n\u0012\b\u001a/'\u0000\u0019\u000f\u0011&\t\f\u000e+'>\u0002\b$0\u0002\u0019\u0019\u0013", "NUamj");
        Minecraft.I[114 + 241 - 194 + 86] = I("\u000f&\u0018\u0006\u0013\u00189\u001c *<\u00154\r\u0013\f/5:\u001b\u0005+ \u0000-\u0004%&\u0001-\u001b>(\u0017\u00175", "hJGer");
        Minecraft.I[170 + 40 - 16 + 54] = I("\u001d*.4\"\n5*\u0012\u001b.\u0019\u0002?\"\u001e)\u0006\b%\u000f(\u0012$\u001e", "zFqWC");
        Minecraft.I[204 + 44 - 157 + 158] = I("\u0000\u000206\u0000\u0017\u001d4\u0010931\u001c=\u0000\u0015\u000b\u000b\n\u0015\u0002\u0016\u001b \u0013\u00021\u001f4\r\u0002\u001a\u001b0<", "gnoUa");
        Minecraft.I[84 + 240 - 76 + 2] = I("2\"\u0015(5%=\u0011\u000e\f\u0001\u00119?1;-#'\u000b6\"/*&\n:+,\t", "UNJKT");
        Minecraft.I[201 + 103 - 75 + 22] = I("\u0017\u0007,\u000e0\u0000\u0018((\t$4\u0000\u00194\u001e\b\u001a\u0001\u000e\u0004\u001c\u001c2\"\u0019\u000f\u00160", "pksmQ");
        Minecraft.I[126 + 54 - 178 + 250] = I("\u0010\u0016\n*\r\u0007\t\u000e\f4#%&=\t\u0019\u0019<%3\u0000\b491", "wzUIl");
        Minecraft.I[0 + 39 + 189 + 25] = I("3?&!6$ \"\u0007\u000f\u0000\f\r'/ &\u000b'\bg7$", "TSyBW");
        Minecraft.I[179 + 189 - 199 + 85] = I(".&9:$99=\u001c\u001d\u001d\u0015\u0012<==?\u0014<\u001a(8\u00148<\u0014", "IJfYE");
        Minecraft.I[225 + 73 - 128 + 85] = I("#\u000194\u00074\u001e=\u0012>\u00102\u00122\u001e0\u0018\u001429&\u0018\u00001\u000362\t5\f!\u000e\u0012\n", "DmfWf");
        Minecraft.I[31 + 209 - 166 + 182] = I("#+\u000f\u001a/44\u000b<\u0016\u0010\u0018$\u001c602\"\u001c\u0011-)$\u001c)!5\r", "DGPyN");
        Minecraft.I[229 + 150 - 221 + 99] = I("4\u0003>\u0017(#\u001c:1\u0011\u00070\u0015\u00111'\u001a\u0013\u0011\u0016?\u0000\u0005++:\u000e\u0012)", "SoatI");
        Minecraft.I[135 + 243 - 359 + 239] = I("\u000f'1\n\u0016\u001885,/<\u0014\u001a\f\u000f\u001c>\u001c\f(\u001b\u0019)+*", "hKniw");
        Minecraft.I[127 + 176 - 139 + 95] = I("?\u0007\u001a\u00002(\u0018\u001e&\u000b\f43\u0006!,\u000e=< 0\n!\u0006!\u0005", "XkEcS");
        Minecraft.I[81 + 31 + 108 + 40] = I("\u0011\u001684\u0018\u0006\t<\u0012!\"%\u00112\u000b\u0002\u001f\u001f\b\u000e\u0013\u0013\u0000?\r\u001f\u0014\u0000\n", "vzgWy");
        Minecraft.I[252 + 119 - 238 + 128] = I(">\u001d;\u0013\u0016)\u0002?\u0017\u001b\u0006\u001c\u0005\b(/\u0014\u0016\u0004\u0012!.\u0011\u001e\u001e?\u001e\u0016\u001d\u0004\u0004", "Yqdpw");
        Minecraft.I[171 + 90 - 174 + 175] = I("6 )\b\u0018!?-\f\u0015\u000e!\u0017\u0013&7>\u0017\f\u00144\"\u00024\f?%\u0010\u0004\u000b<?+", "QLvky");
        Minecraft.I[5 + 65 + 110 + 83] = I("\u000b.\u0015\u00107\u001c1\u0011\u0014:3/+\u000b\t\u001a'8\u00073\u0014\u001d+\u0007\"\u001e+(\u0000\u000b", "lBJsV");
        Minecraft.I[78 + 165 - 18 + 39] = I("=\u0015\u0017\u0011\u001b*\n\u0013\u0015\u0016\u0005\u0014)\n%,\u001c:\u0006\u001f\"&<\u0017\u0002.\f:\u0017%3\u0014)\u0015\u001f\u0005\f&\u001b\u000e)$", "ZyHrz");
        Minecraft.I[68 + 176 - 60 + 81] = I("\u000b:'\u00063\u001c%#\u0002>3;\u0019\u001d\r\u00183\u0000\u0011'\u001e3'\f?\r1\u001d:'\u0002?\f\u0016\u000f", "lVxeR");
        Minecraft.I[82 + 192 - 82 + 74] = I("\u0002\u001c*:\u0003\u0015\u0003.>\u000e:\u001d\u0014!=\u0011\u0015\r-\u0017\u0017\u0015*0\u000f\u0004\u0017\u0010\u0006\u0017\u000b\u0019\u0001*?", "epuYb");
        Minecraft.I[162 + 206 - 288 + 187] = I("\u0001\r\u0010\u0015\u0014\u001e>;\u001d\r\u0012\u0014=\u001d*\u0015\b5\u001d", "faOxu");
        Minecraft.I[34 + 7 + 84 + 143] = I("';04(9a!>'2&0<\u0016';##=", "TOBQI");
        Minecraft.I[43 + 33 + 141 + 52] = I("", "VHVMm");
        Minecraft.I[206 + 60 - 237 + 241] = I("\u0000\u0007\u0019B\u0019+\t\t\u001bK:\u0007M\u0011\u001f/\u001a\u0019B\u0018:\u001a\b\u0003\u0006'\u0006\nB\u0012+\u001cL", "Nhmbk");
        Minecraft.I[46 + 270 - 239 + 194] = I("\u0013N\u001d\u0013!.\u0000\"\u001b)?N\u0005\t*9\r1\u0017*", "KcPzO");
        Minecraft.I[32 + 172 - 169 + 237] = I("\u0011{'\u000b-,5\u0018\u0003%={?7\n\r", "IVjbC");
        Minecraft.I[159 + 80 - 198 + 232] = I("\u001be\u0014\u000b\u0019&++\u0003\u00117e\u000f\u0007\u00050!6\f", "CHYbw");
        Minecraft.I[101 + 39 - 138 + 272] = I("dI|FW", "UgDho");
    }
    
    public SoundHandler getSoundHandler() {
        return this.mcSoundHandler;
    }
    
    public void setRenderViewEntity(final Entity renderViewEntity) {
        this.renderViewEntity = renderViewEntity;
        this.entityRenderer.loadEntityShader(renderViewEntity);
    }
    
    public FrameTimer func_181539_aj() {
        return this.field_181542_y;
    }
    
    public void displayCrashReport(final CrashReport crashReport) {
        final File file = new File(new File(getMinecraft().mcDataDir, Minecraft.I[0x5D ^ 0x7F]), Minecraft.I[0x32 ^ 0x11] + new SimpleDateFormat(Minecraft.I[0x33 ^ 0x17]).format(new Date()) + Minecraft.I[0x1F ^ 0x3A]);
        Bootstrap.printToSYSOUT(crashReport.getCompleteReport());
        if (crashReport.getFile() != null) {
            Bootstrap.printToSYSOUT(Minecraft.I[0x80 ^ 0xA6] + crashReport.getFile());
            System.exit(-" ".length());
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (crashReport.saveToFile(file)) {
            Bootstrap.printToSYSOUT(Minecraft.I[0xF ^ 0x28] + file.getAbsolutePath());
            System.exit(-" ".length());
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            Bootstrap.printToSYSOUT(Minecraft.I[0x48 ^ 0x60]);
            System.exit(-"  ".length());
        }
    }
    
    public void dispatchKeypresses() {
        int n;
        if (Keyboard.getEventKey() == 0) {
            n = Keyboard.getEventCharacter();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n = Keyboard.getEventKey();
        }
        final int n2 = n;
        if (n2 != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)) {
            if (Keyboard.getEventKeyState()) {
                if (n2 == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        this.getTwitchStream().stopBroadcasting();
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                    else if (this.getTwitchStream().isReadyToBroadcast()) {
                        this.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                            final Minecraft this$0;
                            
                            @Override
                            public void confirmClicked(final boolean b, final int n) {
                                if (b) {
                                    this.this$0.getTwitchStream().func_152930_t();
                                }
                                this.this$0.displayGuiScreen(null);
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
                                    if (3 <= 2) {
                                        throw null;
                                    }
                                }
                                return sb.toString();
                            }
                        }, I18n.format(Minecraft.I[20 + 32 + 69 + 147], new Object["".length()]), Minecraft.I[267 + 178 - 422 + 246], "".length()));
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                    }
                    else if (this.getTwitchStream().func_152928_D() && this.getTwitchStream().func_152936_l()) {
                        if (this.theWorld != null) {
                            this.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Minecraft.I[140 + 239 - 178 + 69]));
                            "".length();
                            if (2 == -1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        GuiStreamUnavailable.func_152321_a(this.currentScreen);
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                    }
                }
                else if (n2 == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        if (this.getTwitchStream().isPaused()) {
                            this.getTwitchStream().unpause();
                            "".length();
                            if (3 <= 2) {
                                throw null;
                            }
                        }
                        else {
                            this.getTwitchStream().pause();
                            "".length();
                            if (3 < 3) {
                                throw null;
                            }
                        }
                    }
                }
                else if (n2 == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        this.getTwitchStream().requestCommercial();
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                    }
                }
                else if (n2 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.muteMicrophone(" ".length() != 0);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else if (n2 == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (n2 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
            }
            else if (n2 == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                this.stream.muteMicrophone("".length() != 0);
            }
        }
    }
    
    private ItemStack func_181036_a(final Item item, final int n, final TileEntity tileEntity) {
        final ItemStack itemStack = new ItemStack(item, " ".length(), n);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        if (item == Items.skull && nbtTagCompound.hasKey(Minecraft.I[20 + 20 - 31 + 119])) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(Minecraft.I[93 + 128 - 158 + 66]);
            final NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setTag(Minecraft.I[74 + 45 - 59 + 70], compoundTag);
            itemStack.setTagCompound(tagCompound);
            return itemStack;
        }
        itemStack.setTagInfo(Minecraft.I[58 + 119 - 107 + 61], nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagString(Minecraft.I[121 + 111 - 127 + 27]));
        nbtTagCompound2.setTag(Minecraft.I[130 + 49 - 120 + 74], list);
        itemStack.setTagInfo(Minecraft.I[5 + 102 - 104 + 131], nbtTagCompound2);
        return itemStack;
    }
    
    private ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        final int[] rgb = read.getRGB("".length(), "".length(), read.getWidth(), read.getHeight(), null, "".length(), read.getWidth());
        final ByteBuffer allocate = ByteBuffer.allocate((0x67 ^ 0x63) * rgb.length);
        final int[] array;
        final int length = (array = rgb).length;
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < length) {
            final int n = array[i];
            allocate.putInt(n << (0xB7 ^ 0xBF) | (n >> (0x9A ^ 0x82) & 213 + 201 - 209 + 50));
            ++i;
        }
        allocate.flip();
        return allocate;
    }
    
    public final boolean isDemo() {
        return this.isDemo;
    }
    
    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }
    
    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }
    
    private void updateFramebufferSize() {
        this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType() {
        final int[] $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType = Minecraft.$SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType;
        if ($switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType != null) {
            return $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType;
        }
        final int[] $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType2 = new int[MovingObjectPosition.MovingObjectType.values().length];
        try {
            $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType2[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = "  ".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType2[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = "   ".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType2[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = " ".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return Minecraft.$SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType = $switch_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType2;
    }
    
    public Framebuffer getFramebuffer() {
        return this.framebufferMc;
    }
    
    public TextureManager getTextureManager() {
        return this.renderEngine;
    }
    
    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }
    
    private void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            InputStream inputStreamAssets = null;
            InputStream inputStreamAssets2 = null;
            try {
                inputStreamAssets = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation(Minecraft.I[0x2E ^ 0x34]));
                inputStreamAssets2 = this.mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation(Minecraft.I[0x2C ^ 0x37]));
                if (inputStreamAssets != null && inputStreamAssets2 != null) {
                    final ByteBuffer[] icon = new ByteBuffer["  ".length()];
                    icon["".length()] = this.readImageToBuffer(inputStreamAssets);
                    icon[" ".length()] = this.readImageToBuffer(inputStreamAssets2);
                    Display.setIcon(icon);
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
            }
            catch (IOException ex) {
                Minecraft.logger.error(Minecraft.I[0x1E ^ 0x2], (Throwable)ex);
                IOUtils.closeQuietly(inputStreamAssets);
                IOUtils.closeQuietly(inputStreamAssets2);
                "".length();
                if (true != true) {
                    throw null;
                }
                return;
            }
            finally {
                IOUtils.closeQuietly(inputStreamAssets);
                IOUtils.closeQuietly(inputStreamAssets2);
            }
            IOUtils.closeQuietly(inputStreamAssets);
            IOUtils.closeQuietly(inputStreamAssets2);
        }
    }
    
    public String getVersion() {
        return this.launchedVersion;
    }
    
    public void setServerData(final ServerData currentServerData) {
        this.currentServerData = currentServerData;
    }
    
    private void rightClickMouse() {
        if (!this.playerController.func_181040_m()) {
            this.rightClickDelayTimer = (0xA ^ 0xE);
            int n = " ".length();
            final ItemStack currentItem = this.thePlayer.inventory.getCurrentItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.warn(Minecraft.I[0xFF ^ 0xA0]);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
            else {
                switch ($SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType()[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 3: {
                        if (this.playerController.func_178894_a(this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                            n = "".length();
                            "".length();
                            if (1 < -1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            if (!this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
                                break;
                            }
                            n = "".length();
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                            break;
                        }
                        break;
                    }
                    case 2: {
                        final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                        if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
                            break;
                        }
                        int n2;
                        if (currentItem != null) {
                            n2 = currentItem.stackSize;
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        else {
                            n2 = "".length();
                        }
                        final int n3 = n2;
                        if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, currentItem, blockPos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                            n = "".length();
                            this.thePlayer.swingItem();
                        }
                        if (currentItem == null) {
                            return;
                        }
                        if (currentItem.stackSize == 0) {
                            this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            if (currentItem.stackSize != n3 || this.playerController.isInCreativeMode()) {
                                this.entityRenderer.itemRenderer.resetEquippedProgress();
                                break;
                            }
                            break;
                        }
                        break;
                    }
                }
            }
            if (n != 0) {
                final ItemStack currentItem2 = this.thePlayer.inventory.getCurrentItem();
                if (currentItem2 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, currentItem2)) {
                    this.entityRenderer.itemRenderer.resetEquippedProgress2();
                }
            }
        }
    }
    
    public RenderManager getRenderManager() {
        return this.renderManager;
    }
    
    public <V> ListenableFuture<V> addScheduledTask(final Callable<V> callable) {
        Validate.notNull((Object)callable);
        if (!this.isCallingFromMinecraftThread()) {
            final ListenableFutureTask create = ListenableFutureTask.create((Callable)callable);
            synchronized (this.scheduledTasks) {
                this.scheduledTasks.add((FutureTask<?>)create);
                // monitorexit(this.scheduledTasks)
                return (ListenableFuture<V>)create;
            }
        }
        try {
            return (ListenableFuture<V>)Futures.immediateFuture((Object)callable.call());
        }
        catch (Exception ex) {
            return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(ex);
        }
    }
    
    private void updateDisplayMode() throws LWJGLException {
        final HashSet hashSet = Sets.newHashSet();
        Collections.addAll(hashSet, Display.getAvailableDisplayModes());
        DisplayMode desktopDisplayMode = Display.getDesktopDisplayMode();
        if (!hashSet.contains(desktopDisplayMode) && Util.getOSType() == Util.EnumOS.OSX) {
            final Iterator<DisplayMode> iterator = Minecraft.macDisplayModes.iterator();
            "".length();
            if (2 < 2) {
                throw null;
            }
        Label_0284:
            while (iterator.hasNext()) {
                final DisplayMode displayMode = iterator.next();
                int n = " ".length();
                final Iterator<DisplayMode> iterator2 = (Iterator<DisplayMode>)hashSet.iterator();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final DisplayMode displayMode2 = iterator2.next();
                    if (displayMode2.getBitsPerPixel() == (0x5D ^ 0x7D) && displayMode2.getWidth() == displayMode.getWidth() && displayMode2.getHeight() == displayMode.getHeight()) {
                        n = "".length();
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                        break;
                    }
                }
                if (n != 0) {
                    continue;
                }
                for (final DisplayMode displayMode3 : hashSet) {
                    if (displayMode3.getBitsPerPixel() == (0x12 ^ 0x32) && displayMode3.getWidth() == displayMode.getWidth() / "  ".length() && displayMode3.getHeight() == displayMode.getHeight() / "  ".length()) {
                        desktopDisplayMode = displayMode3;
                        continue Label_0284;
                    }
                }
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        Display.setDisplayMode(desktopDisplayMode);
        this.displayWidth = desktopDisplayMode.getWidth();
        this.displayHeight = desktopDisplayMode.getHeight();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType() {
        final int[] $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = Minecraft.$SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
        if ($switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType != null) {
            return $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType;
        }
        final int[] $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2 = new int[EntityMinecart.EnumMinecartType.values().length];
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = "  ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = (0x93 ^ 0x94);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = "   ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = (0x47 ^ 0x41);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.RIDEABLE.ordinal()] = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.SPAWNER.ordinal()] = (0x2C ^ 0x29);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2[EntityMinecart.EnumMinecartType.TNT.ordinal()] = (0x3D ^ 0x39);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        return Minecraft.$SWITCH_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType = $switch_TABLE$net$minecraft$entity$item$EntityMinecart$EnumMinecartType2;
    }
    
    public boolean func_181540_al() {
        return this.field_181541_X;
    }
    
    public void displayGuiScreen(GuiScreen currentScreen) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (currentScreen == null && this.theWorld == null) {
            currentScreen = new GuiMainMenu();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (currentScreen == null && this.thePlayer.getHealth() <= 0.0f) {
            currentScreen = new GuiGameOver();
        }
        if (currentScreen instanceof GuiMainMenu) {
            this.gameSettings.showDebugInfo = ("".length() != 0);
            this.ingameGUI.getChatGUI().clearChatMessages();
        }
        if ((this.currentScreen = currentScreen) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution scaledResolution = new ScaledResolution(this);
            currentScreen.setWorldAndResolution(this, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            this.skipRenderWorld = ("".length() != 0);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.mcSoundHandler.resumeSounds();
            this.setIngameFocus();
        }
    }
    
    public boolean isJava64bit() {
        return this.jvm64bit;
    }
    
    public void updateDisplay() {
        this.mcProfiler.startSection(Minecraft.I[0x7D ^ 0x31]);
        Display.update();
        this.mcProfiler.endSection();
        this.checkWindowResize();
    }
    
    protected void checkWindowResize() {
        if (!this.fullscreen && Display.wasResized()) {
            final int displayWidth = this.displayWidth;
            final int displayHeight = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != displayWidth || this.displayHeight != displayHeight) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = " ".length();
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = " ".length();
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }
    
    private String func_181538_aA() {
        String s;
        if (this.theIntegratedServer != null) {
            if (this.theIntegratedServer.getPublic()) {
                s = Minecraft.I[82 + 101 - 166 + 144];
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                s = Minecraft.I[83 + 21 - 67 + 125];
                "".length();
                if (!true) {
                    throw null;
                }
            }
        }
        else if (this.currentServerData != null) {
            if (this.currentServerData.func_181041_d()) {
                s = Minecraft.I[60 + 7 + 58 + 38];
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                s = Minecraft.I[87 + 93 - 20 + 4];
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
        }
        else {
            s = Minecraft.I[131 + 112 - 119 + 41];
        }
        return s;
    }
    
    public static void stopIntegratedServer() {
        if (Minecraft.theMinecraft != null) {
            final IntegratedServer integratedServer = Minecraft.theMinecraft.getIntegratedServer();
            if (integratedServer != null) {
                integratedServer.stopServer();
            }
        }
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    private void startTimerHackThread() {
        final Thread thread = new Thread(this, Minecraft.I[0x8F ^ 0xAE]) {
            final Minecraft this$0;
            
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (this.this$0.running) {
                    try {
                        Thread.sleep(2147483647L);
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                        continue;
                    }
                    catch (InterruptedException ex) {}
                }
            }
        };
        thread.setDaemon(" ".length() != 0);
        thread.start();
    }
    
    public ListenableFuture<Object> scheduleResourcesRefresh() {
        return this.addScheduledTask(new Runnable(this) {
            final Minecraft this$0;
            
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
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                this.this$0.refreshResources();
            }
        });
    }
    
    static String access$2(final Minecraft minecraft) {
        return minecraft.launchedVersion;
    }
    
    public void launchIntegratedServer(final String s, final String s2, WorldSettings worldSettings) {
        this.loadWorld(null);
        I();
        final ISaveHandler saveLoader = this.saveLoader.getSaveLoader(s, "".length() != 0);
        WorldInfo loadWorldInfo = saveLoader.loadWorldInfo();
        if (loadWorldInfo == null && worldSettings != null) {
            loadWorldInfo = new WorldInfo(worldSettings, s);
            saveLoader.saveWorldInfo(loadWorldInfo);
        }
        if (worldSettings == null) {
            worldSettings = new WorldSettings(loadWorldInfo);
        }
        try {
            (this.theIntegratedServer = new IntegratedServer(this, s, s2, worldSettings)).startServerThread();
            this.integratedServerIsRunning = (" ".length() != 0);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, Minecraft.I[0x72 ^ 0xA]);
            final CrashReportCategory category = crashReport.makeCategory(Minecraft.I[0x27 ^ 0x5E]);
            category.addCrashSection(Minecraft.I[0x4E ^ 0x34], s);
            category.addCrashSection(Minecraft.I[0x22 ^ 0x59], s2);
            throw new ReportedException(crashReport);
        }
        this.loadingScreen.displaySavingString(I18n.format(Minecraft.I[0x6C ^ 0x10], new Object["".length()]));
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            final String userMessage = this.theIntegratedServer.getUserMessage();
            if (userMessage != null) {
                this.loadingScreen.displayLoadingString(I18n.format(userMessage, new Object["".length()]));
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                this.loadingScreen.displayLoadingString(Minecraft.I[0x44 ^ 0x39]);
            }
            try {
                Thread.sleep(200L);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                continue;
            }
            catch (InterruptedException ex) {}
        }
        this.displayGuiScreen(null);
        final SocketAddress addLocalEndpoint = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        final NetworkManager provideLocalClient = NetworkManager.provideLocalClient(addLocalEndpoint);
        provideLocalClient.setNetHandler(new NetHandlerLoginClient(provideLocalClient, this, null));
        provideLocalClient.sendPacket(new C00Handshake(0xC ^ 0x23, addLocalEndpoint.toString(), "".length(), EnumConnectionState.LOGIN));
        provideLocalClient.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
        this.myNetworkManager = provideLocalClient;
    }
    
    public MusicTicker func_181535_r() {
        return this.mcMusicTicker;
    }
    
    static {
        l();
        logger = LogManager.getLogger();
        locationMojangPng = new ResourceLocation(Minecraft.I["".length()]);
        int isRunningOnMac2;
        if (Util.getOSType() == Util.EnumOS.OSX) {
            isRunningOnMac2 = " ".length();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            isRunningOnMac2 = "".length();
        }
        isRunningOnMac = (isRunningOnMac2 != 0);
        Minecraft.memoryReserve = new byte[584766 + 6353573 - 750911 + 4298332];
        final DisplayMode[] array = new DisplayMode["  ".length()];
        array["".length()] = new DisplayMode(2055 + 1049 - 1379 + 835, 685 + 1317 - 1131 + 729);
        array[" ".length()] = new DisplayMode(2809 + 1948 - 4205 + 2328, 824 + 1684 - 1876 + 1168);
        macDisplayModes = Lists.newArrayList((Object[])array);
    }
    
    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            KeyBinding.unPressAllKeys();
            this.inGameHasFocus = ("".length() != 0);
            this.mouseHelper.ungrabMouseCursor();
        }
    }
    
    private void sendClickBlockToController(final boolean b) {
        if (!b) {
            this.leftClickCounter = "".length();
        }
        if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem()) {
            if (b && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockPos, this.objectMouseOver.sideHit)) {
                    this.effectRenderer.addBlockHitEffects(blockPos, this.objectMouseOver.sideHit);
                    this.thePlayer.swingItem();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
            else {
                this.playerController.resetBlockRemoving();
            }
        }
    }
    
    public static int getDebugFPS() {
        return Minecraft.debugFPS;
    }
    
    public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport crashReport) {
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[27 + 118 - 17 + 7], new Callable<String>(this) {
            final Minecraft this$0;
            
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
                    if (2 != 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                return Minecraft.access$2(this.this$0);
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[123 + 91 - 174 + 96], new Callable<String>(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return Sys.getVersion();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (-1 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[73 + 136 - 110 + 38], new Callable<String>(this) {
            final Minecraft this$0;
            private static final String[] I;
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("y\u000e4n\u000f<;\u000b'\u00167i", "YIxNy");
                Minecraft$7.I[" ".length()] = I("[m", "wMntQ");
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (-1 == 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() {
                return String.valueOf(GL11.glGetString(2016 + 6370 - 7188 + 6739)) + Minecraft$7.I["".length()] + GL11.glGetString(3467 + 1124 - 2829 + 6176) + Minecraft$7.I[" ".length()] + GL11.glGetString(7693 + 1365 - 8838 + 7716);
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[114 + 41 - 151 + 134], new Callable<String>(this) {
            final Minecraft this$0;
            
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
                    if (4 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return OpenGlHelper.getLogText();
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[20 + 101 - 52 + 70], new Callable<String>(this) {
            private static final String[] I;
            final Minecraft this$0;
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (0 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() {
                String s;
                if (this.this$0.gameSettings.useVbo) {
                    s = Minecraft$9.I["".length()];
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else {
                    s = Minecraft$9.I[" ".length()];
                }
                return s;
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("?,\u001e", "fImPS");
                Minecraft$9.I[" ".length()] = I("4\u001f", "zpChE");
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[139 + 6 - 17 + 12], new Callable<String>(this) {
            final Minecraft this$0;
            private static final String[] I;
            
            @Override
            public String call() throws Exception {
                final String clientModName = ClientBrandRetriever.getClientModName();
                String string;
                if (!clientModName.equals(Minecraft$10.I["".length()])) {
                    string = Minecraft$10.I[" ".length()] + clientModName + Minecraft$10.I["  ".length()];
                    "".length();
                    if (!true) {
                        throw null;
                    }
                }
                else if (Minecraft.class.getSigners() == null) {
                    string = Minecraft$10.I["   ".length()];
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    string = Minecraft$10.I[0x4D ^ 0x49];
                }
                return string;
            }
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (4 <= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0x11 ^ 0x14])["".length()] = I("\u001d =\"#\u0007 ", "kASKO");
                Minecraft$10.I[" ".length()] = I("3\u0004\u0014\u0006\u001b\u001e\u0015\u0017\u0003\fLA1\u0003\u001c\u0012\u000f\u0006O\u0017\u0005\u0000\u001c\u000bU\u0014\t\u0013\u0001\u0012\u0012\u0005R\u001b\u001aWF", "warou");
                Minecraft$10.I["  ".length()] = I("_", "xccEm");
                Minecraft$10.I["   ".length()] = I("\u0001\u0016\u0015<X;\u001a\f \u0014.HG\u000f\u0019%S\u0014,\u001f9\u0012\u00130\n2S\u000e+\u000e6\u001f\u000e!\u0019#\u0016\u0003", "WsgEx");
                Minecraft$10.I[0x24 ^ 0x20] = I("\u001f?5 \u0000-!#b\u000f 9tb+.?z1\b(#;6\u0014=(z0\u0004\",3,\u0012o,4&A,!3'\u000f;m80\u0000!)z+\u0012o846\u000e:.2'\u0005a", "OMZBa");
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[4 + 81 + 27 + 29], new Callable<String>(this) {
            private static final String[] I;
            final Minecraft this$0;
            
            @Override
            public String call() throws Exception {
                return Minecraft$11.I["".length()];
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
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0015&\r\r\u001c\"jL\u0005\u0013&\u0015\u0007\u0004\u001b3$\u0010F\u0006.>M", "VJdhr");
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[91 + 132 - 170 + 89], new Callable<String>(this) {
            private static final String[] I;
            final Minecraft this$0;
            
            static {
                I();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                final StringBuilder sb = new StringBuilder();
                final Iterator<Object> iterator = this.this$0.gameSettings.resourcePacks.iterator();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Object next = iterator.next();
                    if (sb.length() > 0) {
                        sb.append(Minecraft$12.I["".length()]);
                    }
                    sb.append(next);
                    if (this.this$0.gameSettings.field_183018_l.contains(next)) {
                        sb.append(Minecraft$12.I[" ".length()]);
                    }
                }
                return sb.toString();
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
                    if (2 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("ek", "IKAkX");
                Minecraft$12.I[" ".length()] = I("IB.\u000b2\u0006\u00077\u0004%\u0000\b+\u0000x", "ijGeQ");
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[42 + 3 + 18 + 80], new Callable<String>(this) {
            final Minecraft this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return Minecraft.access$3(this.this$0).getCurrentLanguage().toString();
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
                    if (3 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[77 + 20 - 67 + 114], new Callable<String>(this) {
            final Minecraft this$0;
            private static final String[] I;
            
            @Override
            public String call() throws Exception {
                String nameOfLastSection;
                if (this.this$0.mcProfiler.profilingEnabled) {
                    nameOfLastSection = this.this$0.mcProfiler.getNameOfLastSection();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    nameOfLastSection = Minecraft$14.I["".length()];
                }
                return nameOfLastSection;
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0017M&LP=\u000b\u0014\r\u001a5\u0007\u0003E", "Ybglx");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (3 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReport.getCategory().addCrashSectionCallable(Minecraft.I[40 + 68 + 14 + 23], new Callable<String>(this) {
            final Minecraft this$0;
            
            @Override
            public String call() {
                return OpenGlHelper.func_183029_j();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (2 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        if (this.theWorld != null) {
            this.theWorld.addWorldInfoToCrashReport(crashReport);
        }
        return crashReport;
    }
    
    private void clickMouse() {
        if (this.leftClickCounter <= 0) {
            this.thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.error(Minecraft.I[0x9A ^ 0xC4]);
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = (0x54 ^ 0x5E);
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
            }
            else {
                switch ($SWITCH_TABLE$net$minecraft$util$MovingObjectPosition$MovingObjectType()[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 3: {
                        this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                        return;
                    }
                    case 2: {
                        final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                        if (this.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
                            break;
                        }
                        this.playerController.clickBlock(blockPos, this.objectMouseOver.sideHit);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        return;
                    }
                }
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = (0x44 ^ 0x4E);
                }
            }
        }
    }
    
    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }
    
    public MusicTicker.MusicType getAmbientMusicType() {
        MusicTicker.MusicType musicType;
        if (this.thePlayer != null) {
            if (this.thePlayer.worldObj.provider instanceof WorldProviderHell) {
                musicType = MusicTicker.MusicType.NETHER;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else if (this.thePlayer.worldObj.provider instanceof WorldProviderEnd) {
                if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
                    musicType = MusicTicker.MusicType.END_BOSS;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    musicType = MusicTicker.MusicType.END;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            else if (this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) {
                musicType = MusicTicker.MusicType.CREATIVE;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                musicType = MusicTicker.MusicType.GAME;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            musicType = MusicTicker.MusicType.MENU;
        }
        return musicType;
    }
    
    public void func_181536_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final int n9, final int n10) {
        final float n11 = 0.00390625f;
        final float n12 = 0.00390625f;
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.begin(0x72 ^ 0x75, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(n, n2 + n6, 0.0).tex(n3 * n11, (n4 + n6) * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, 0.0).tex((n3 + n5) * n11, (n4 + n6) * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n + n5, n2, 0.0).tex((n3 + n5) * n11, n4 * n12).color(n7, n8, n9, n10).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n11, n4 * n12).color(n7, n8, n9, n10).endVertex();
        Tessellator.getInstance().draw();
    }
    
    public NetHandlerPlayClient getNetHandler() {
        NetHandlerPlayClient sendQueue;
        if (this.thePlayer != null) {
            sendQueue = this.thePlayer.sendQueue;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            sendQueue = null;
        }
        return sendQueue;
    }
    
    public void crashed(final CrashReport crashReporter) {
        this.hasCrashed = (" ".length() != 0);
        this.crashReporter = crashReporter;
    }
    
    public static int getGLMaximumTextureSize() {
        int i = 2431 + 12863 - 13807 + 14897;
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (i > 0) {
            GL11.glTexImage2D(9543 + 11830 + 1559 + 9936, "".length(), 3811 + 5068 - 7175 + 4704, i, i, "".length(), 4006 + 4561 - 4381 + 2222, 2528 + 3612 - 2854 + 1835, (ByteBuffer)null);
            if (GL11.glGetTexLevelParameteri(31184 + 9748 - 31366 + 23302, "".length(), 2135 + 3286 - 5293 + 3968) != 0) {
                return i;
            }
            i >>= " ".length();
        }
        return -" ".length();
    }
    
    public void loadWorld(final WorldClient worldClient) {
        this.loadWorld(worldClient, Minecraft.I[0x1E ^ 0x60]);
    }
    
    public int getLimitFramerate() {
        int limitFramerate;
        if (this.theWorld == null && this.currentScreen != null) {
            limitFramerate = (0x3F ^ 0x21);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            limitFramerate = this.gameSettings.limitFramerate;
        }
        return limitFramerate;
    }
    
    public boolean isFramerateLimitBelowMax() {
        if (this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static Map<String, String> getSessionInfo() {
        final HashMap hashMap = Maps.newHashMap();
        hashMap.put(Minecraft.I[140 + 21 - 114 + 224], getMinecraft().getSession().getUsername());
        hashMap.put(Minecraft.I[209 + 130 - 239 + 172], getMinecraft().getSession().getPlayerID());
        hashMap.put(Minecraft.I[113 + 227 - 82 + 15], Minecraft.I[72 + 135 - 166 + 233]);
        return (Map<String, String>)hashMap;
    }
    
    private void displayDebugInfo(final long n) {
        if (this.mcProfiler.profilingEnabled) {
            final List profilingData = this.mcProfiler.getProfilingData(this.debugProfilerName);
            final Profiler.Result result = profilingData.remove("".length());
            GlStateManager.clear(249 + 247 - 450 + 210);
            GlStateManager.matrixMode(2876 + 1124 - 3933 + 5822);
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(3050 + 3827 - 5132 + 4143);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.disableTexture2D();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            final int n2 = 92 + 86 - 114 + 96;
            final int n3 = this.displayWidth - n2 - (0x27 ^ 0x2D);
            final int n4 = this.displayHeight - n2 * "  ".length();
            GlStateManager.enableBlend();
            worldRenderer.begin(0x25 ^ 0x22, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(n3 - n2 * 1.1f, n4 - n2 * 0.6f - 16.0f, 0.0).color(69 + 27 - 0 + 104, "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(n3 - n2 * 1.1f, n4 + n2 * "  ".length(), 0.0).color(87 + 71 - 120 + 162, "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(n3 + n2 * 1.1f, n4 + n2 * "  ".length(), 0.0).color(172 + 140 - 240 + 128, "".length(), "".length(), "".length()).endVertex();
            worldRenderer.pos(n3 + n2 * 1.1f, n4 - n2 * 0.6f - 16.0f, 0.0).color(170 + 190 - 342 + 182, "".length(), "".length(), "".length()).endVertex();
            instance.draw();
            GlStateManager.disableBlend();
            double n5 = 0.0;
            int i = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (i < profilingData.size()) {
                final Profiler.Result result2 = profilingData.get(i);
                final int n6 = MathHelper.floor_double(result2.field_76332_a / 4.0) + " ".length();
                worldRenderer.begin(0x99 ^ 0x9F, DefaultVertexFormats.POSITION_COLOR);
                final int func_76329_a = result2.func_76329_a();
                final int n7 = func_76329_a >> (0x4D ^ 0x5D) & 231 + 12 - 162 + 174;
                final int n8 = func_76329_a >> (0x61 ^ 0x69) & 163 + 21 - 94 + 165;
                final int n9 = func_76329_a & 252 + 15 - 214 + 202;
                worldRenderer.pos(n3, n4, 0.0).color(n7, n8, n9, 154 + 56 - 107 + 152).endVertex();
                int j = n6;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (j >= 0) {
                    final float n10 = (float)((n5 + result2.field_76332_a * j / n6) * 3.141592653589793 * 2.0 / 100.0);
                    worldRenderer.pos(n3 + MathHelper.sin(n10) * n2, n4 - MathHelper.cos(n10) * n2 * 0.5f, 0.0).color(n7, n8, n9, 248 + 241 - 474 + 240).endVertex();
                    --j;
                }
                instance.draw();
                worldRenderer.begin(0x71 ^ 0x74, DefaultVertexFormats.POSITION_COLOR);
                int k = n6;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k >= 0) {
                    final float n11 = (float)((n5 + result2.field_76332_a * k / n6) * 3.141592653589793 * 2.0 / 100.0);
                    final float n12 = MathHelper.sin(n11) * n2;
                    final float n13 = MathHelper.cos(n11) * n2 * 0.5f;
                    worldRenderer.pos(n3 + n12, n4 - n13, 0.0).color(n7 >> " ".length(), n8 >> " ".length(), n9 >> " ".length(), 127 + 99 - 211 + 240).endVertex();
                    worldRenderer.pos(n3 + n12, n4 - n13 + 10.0f, 0.0).color(n7 >> " ".length(), n8 >> " ".length(), n9 >> " ".length(), 188 + 126 - 303 + 244).endVertex();
                    --k;
                }
                instance.draw();
                n5 += result2.field_76332_a;
                ++i;
            }
            final DecimalFormat decimalFormat = new DecimalFormat(Minecraft.I[0xC ^ 0x5C]);
            GlStateManager.enableTexture2D();
            String string = Minecraft.I[0x70 ^ 0x21];
            if (!result.field_76331_c.equals(Minecraft.I[0x68 ^ 0x3A])) {
                string = String.valueOf(string) + Minecraft.I[0xCD ^ 0x9E];
            }
            String s;
            if (result.field_76331_c.length() == 0) {
                s = String.valueOf(string) + Minecraft.I[0xD3 ^ 0x87];
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                s = String.valueOf(string) + result.field_76331_c + Minecraft.I[0x4C ^ 0x19];
            }
            final int n14 = 343471 + 4751261 - 1769015 + 13451498;
            this.fontRendererObj.drawStringWithShadow(s, n3 - n2, n4 - n2 / "  ".length() - (0x64 ^ 0x74), n14);
            final FontRenderer fontRendererObj = this.fontRendererObj;
            final String string2 = String.valueOf(decimalFormat.format(result.field_76330_b)) + Minecraft.I[0xE6 ^ 0xB0];
            fontRendererObj.drawStringWithShadow(string2, n3 + n2 - this.fontRendererObj.getStringWidth(string2), n4 - n2 / "  ".length() - (0x47 ^ 0x57), n14);
            int l = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (l < profilingData.size()) {
                final Profiler.Result result3 = profilingData.get(l);
                final String s2 = Minecraft.I[0x7F ^ 0x28];
                String s3;
                if (result3.field_76331_c.equals(Minecraft.I[0xC5 ^ 0x9D])) {
                    s3 = String.valueOf(s2) + Minecraft.I[0x1A ^ 0x43];
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    s3 = String.valueOf(s2) + Minecraft.I[0xE1 ^ 0xBB] + (l + " ".length()) + Minecraft.I[0x9E ^ 0xC5];
                }
                this.fontRendererObj.drawStringWithShadow(String.valueOf(s3) + result3.field_76331_c, n3 - n2, n4 + n2 / "  ".length() + l * (0x65 ^ 0x6D) + (0x74 ^ 0x60), result3.func_76329_a());
                final FontRenderer fontRendererObj2 = this.fontRendererObj;
                final String string3 = String.valueOf(decimalFormat.format(result3.field_76332_a)) + Minecraft.I[0xDF ^ 0x83];
                fontRendererObj2.drawStringWithShadow(string3, n3 + n2 - (0x67 ^ 0x55) - this.fontRendererObj.getStringWidth(string3), n4 + n2 / "  ".length() + l * (0xA3 ^ 0xAB) + (0x83 ^ 0x97), result3.func_76329_a());
                final FontRenderer fontRendererObj3 = this.fontRendererObj;
                final String string4 = String.valueOf(decimalFormat.format(result3.field_76330_b)) + Minecraft.I[0x2E ^ 0x73];
                fontRendererObj3.drawStringWithShadow(string4, n3 + n2 - this.fontRendererObj.getStringWidth(string4), n4 + n2 / "  ".length() + l * (0x71 ^ 0x79) + (0x44 ^ 0x50), result3.func_76329_a());
                ++l;
            }
        }
    }
}
