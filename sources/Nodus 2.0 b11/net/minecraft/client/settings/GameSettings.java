/*    1:     */ package net.minecraft.client.settings;
/*    2:     */ 
/*    3:     */ import com.google.common.collect.Maps;
/*    4:     */ import com.google.gson.Gson;
/*    5:     */ import java.io.BufferedReader;
/*    6:     */ import java.io.File;
/*    7:     */ import java.io.FileReader;
/*    8:     */ import java.io.FileWriter;
/*    9:     */ import java.io.PrintWriter;
/*   10:     */ import java.lang.reflect.ParameterizedType;
/*   11:     */ import java.lang.reflect.Type;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.Arrays;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import net.minecraft.client.Minecraft;
/*   17:     */ import net.minecraft.client.audio.SoundCategory;
/*   18:     */ import net.minecraft.client.audio.SoundHandler;
/*   19:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   20:     */ import net.minecraft.client.gui.FontRenderer;
/*   21:     */ import net.minecraft.client.gui.GuiIngame;
/*   22:     */ import net.minecraft.client.gui.GuiNewChat;
/*   23:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   24:     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   25:     */ import net.minecraft.client.renderer.RenderGlobal;
/*   26:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   27:     */ import net.minecraft.client.resources.I18n;
/*   28:     */ import net.minecraft.client.resources.LanguageManager;
/*   29:     */ import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
/*   30:     */ import net.minecraft.init.Blocks;
/*   31:     */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*   32:     */ import net.minecraft.src.BlockUtils;
/*   33:     */ import net.minecraft.src.Config;
/*   34:     */ import net.minecraft.src.CustomColorizer;
/*   35:     */ import net.minecraft.src.CustomSky;
/*   36:     */ import net.minecraft.src.NaturalTextures;
/*   37:     */ import net.minecraft.src.RandomMobs;
/*   38:     */ import net.minecraft.src.Reflector;
/*   39:     */ import net.minecraft.src.ReflectorClass;
/*   40:     */ import net.minecraft.src.TextureUtils;
/*   41:     */ import net.minecraft.src.WrUpdaterSmooth;
/*   42:     */ import net.minecraft.src.WrUpdaterThreaded;
/*   43:     */ import net.minecraft.src.WrUpdates;
/*   44:     */ import net.minecraft.util.MathHelper;
/*   45:     */ import net.minecraft.world.EnumDifficulty;
/*   46:     */ import net.minecraft.world.chunk.Chunk;
/*   47:     */ import net.minecraft.world.chunk.EmptyChunk;
/*   48:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   49:     */ import net.minecraft.world.chunk.NibbleArray;
/*   50:     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*   51:     */ import org.apache.commons.lang3.ArrayUtils;
/*   52:     */ import org.apache.logging.log4j.LogManager;
/*   53:     */ import org.apache.logging.log4j.Logger;
/*   54:     */ import org.lwjgl.input.Keyboard;
/*   55:     */ import org.lwjgl.input.Mouse;
/*   56:     */ import org.lwjgl.opengl.Display;
/*   57:     */ 
/*   58:     */ public class GameSettings
/*   59:     */ {
/*   60:  55 */   private static final Logger logger = ;
/*   61:  56 */   private static final Gson gson = new Gson();
/*   62:  57 */   private static final ParameterizedType typeListString = new ParameterizedType()
/*   63:     */   {
/*   64:     */     private static final String __OBFID = "CL_00000651";
/*   65:     */     
/*   66:     */     public Type[] getActualTypeArguments()
/*   67:     */     {
/*   68:  62 */       return new Type[] { String.class };
/*   69:     */     }
/*   70:     */     
/*   71:     */     public Type getRawType()
/*   72:     */     {
/*   73:  66 */       return List.class;
/*   74:     */     }
/*   75:     */     
/*   76:     */     public Type getOwnerType()
/*   77:     */     {
/*   78:  70 */       return null;
/*   79:     */     }
/*   80:     */   };
/*   81:  75 */   private static final String[] GUISCALES = { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
/*   82:  76 */   private static final String[] PARTICLES = { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
/*   83:  77 */   private static final String[] AMBIENT_OCCLUSIONS = { "options.ao.off", "options.ao.min", "options.ao.max" };
/*   84:  78 */   public float mouseSensitivity = 0.5F;
/*   85:     */   public boolean invertMouse;
/*   86:  80 */   public int renderDistanceChunks = -1;
/*   87:  81 */   public boolean viewBobbing = true;
/*   88:     */   public boolean anaglyph;
/*   89:     */   public boolean advancedOpengl;
/*   90:  86 */   public boolean fboEnable = true;
/*   91:  87 */   public int limitFramerate = 120;
/*   92:  88 */   public boolean fancyGraphics = true;
/*   93:  91 */   public int ambientOcclusion = 2;
/*   94:  92 */   public int ofFogType = 1;
/*   95:  93 */   public float ofFogStart = 0.8F;
/*   96:  94 */   public int ofMipmapType = 0;
/*   97:  95 */   public boolean ofLoadFar = false;
/*   98:  96 */   public int ofPreloadedChunks = 0;
/*   99:  97 */   public boolean ofOcclusionFancy = false;
/*  100:  98 */   public boolean ofSmoothFps = false;
/*  101:  99 */   public boolean ofSmoothWorld = Config.isSingleProcessor();
/*  102: 100 */   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
/*  103: 101 */   public float ofAoLevel = 1.0F;
/*  104: 102 */   public int ofAaLevel = 0;
/*  105: 103 */   public int ofClouds = 0;
/*  106: 104 */   public float ofCloudsHeight = 0.0F;
/*  107: 105 */   public int ofTrees = 0;
/*  108: 106 */   public int ofGrass = 0;
/*  109: 107 */   public int ofRain = 0;
/*  110: 108 */   public int ofWater = 0;
/*  111: 109 */   public int ofDroppedItems = 0;
/*  112: 110 */   public int ofBetterGrass = 3;
/*  113: 111 */   public int ofAutoSaveTicks = 4000;
/*  114: 112 */   public boolean ofLagometer = false;
/*  115: 113 */   public boolean ofProfiler = false;
/*  116: 114 */   public boolean ofWeather = true;
/*  117: 115 */   public boolean ofSky = true;
/*  118: 116 */   public boolean ofStars = true;
/*  119: 117 */   public boolean ofSunMoon = true;
/*  120: 118 */   public int ofChunkUpdates = 1;
/*  121: 119 */   public int ofChunkLoading = 0;
/*  122: 120 */   public boolean ofChunkUpdatesDynamic = false;
/*  123: 121 */   public int ofTime = 0;
/*  124: 122 */   public boolean ofClearWater = false;
/*  125: 123 */   public boolean ofDepthFog = true;
/*  126: 124 */   public boolean ofBetterSnow = false;
/*  127: 125 */   public String ofFullscreenMode = "Default";
/*  128: 126 */   public boolean ofSwampColors = true;
/*  129: 127 */   public boolean ofRandomMobs = true;
/*  130: 128 */   public boolean ofSmoothBiomes = true;
/*  131: 129 */   public boolean ofCustomFonts = true;
/*  132: 130 */   public boolean ofCustomColors = true;
/*  133: 131 */   public boolean ofCustomSky = true;
/*  134: 132 */   public boolean ofShowCapes = true;
/*  135: 133 */   public int ofConnectedTextures = 2;
/*  136: 134 */   public boolean ofNaturalTextures = false;
/*  137: 135 */   public boolean ofFastMath = false;
/*  138: 136 */   public boolean ofFastRender = true;
/*  139: 137 */   public int ofTranslucentBlocks = 2;
/*  140: 138 */   public int ofAnimatedWater = 0;
/*  141: 139 */   public int ofAnimatedLava = 0;
/*  142: 140 */   public boolean ofAnimatedFire = true;
/*  143: 141 */   public boolean ofAnimatedPortal = true;
/*  144: 142 */   public boolean ofAnimatedRedstone = true;
/*  145: 143 */   public boolean ofAnimatedExplosion = true;
/*  146: 144 */   public boolean ofAnimatedFlame = true;
/*  147: 145 */   public boolean ofAnimatedSmoke = true;
/*  148: 146 */   public boolean ofVoidParticles = true;
/*  149: 147 */   public boolean ofWaterParticles = true;
/*  150: 148 */   public boolean ofRainSplash = true;
/*  151: 149 */   public boolean ofPortalParticles = true;
/*  152: 150 */   public boolean ofPotionParticles = true;
/*  153: 151 */   public boolean ofDrippingWaterLava = true;
/*  154: 152 */   public boolean ofAnimatedTerrain = true;
/*  155: 153 */   public boolean ofAnimatedItems = true;
/*  156: 154 */   public boolean ofAnimatedTextures = true;
/*  157:     */   public static final int DEFAULT = 0;
/*  158:     */   public static final int FAST = 1;
/*  159:     */   public static final int FANCY = 2;
/*  160:     */   public static final int OFF = 3;
/*  161:     */   public static final int ANIM_ON = 0;
/*  162:     */   public static final int ANIM_GENERATED = 1;
/*  163:     */   public static final int ANIM_OFF = 2;
/*  164:     */   public static final int CL_DEFAULT = 0;
/*  165:     */   public static final int CL_SMOOTH = 1;
/*  166:     */   public static final int CL_THREADED = 2;
/*  167:     */   public static final String DEFAULT_STR = "Default";
/*  168:     */   public KeyBinding ofKeyBindZoom;
/*  169:     */   private File optionsFileOF;
/*  170: 170 */   public boolean clouds = true;
/*  171: 171 */   public List resourcePacks = new ArrayList();
/*  172:     */   public EntityPlayer.EnumChatVisibility chatVisibility;
/*  173:     */   public boolean chatColours;
/*  174:     */   public boolean chatLinks;
/*  175:     */   public boolean chatLinksPrompt;
/*  176:     */   public float chatOpacity;
/*  177:     */   public boolean serverTextures;
/*  178:     */   public boolean snooperEnabled;
/*  179:     */   public boolean fullScreen;
/*  180:     */   public boolean enableVsync;
/*  181:     */   public boolean hideServerAddress;
/*  182:     */   public boolean advancedItemTooltips;
/*  183:     */   public boolean pauseOnLostFocus;
/*  184:     */   public boolean showCape;
/*  185:     */   public boolean touchscreen;
/*  186:     */   public int overrideWidth;
/*  187:     */   public int overrideHeight;
/*  188:     */   public boolean heldItemTooltips;
/*  189:     */   public float chatScale;
/*  190:     */   public float chatWidth;
/*  191:     */   public float chatHeightUnfocused;
/*  192:     */   public float chatHeightFocused;
/*  193:     */   public boolean showInventoryAchievementHint;
/*  194:     */   public int mipmapLevels;
/*  195:     */   public int anisotropicFiltering;
/*  196:     */   private Map mapSoundLevels;
/*  197:     */   public KeyBinding keyBindForward;
/*  198:     */   public KeyBinding keyBindLeft;
/*  199:     */   public KeyBinding keyBindBack;
/*  200:     */   public KeyBinding keyBindRight;
/*  201:     */   public KeyBinding keyBindJump;
/*  202:     */   public KeyBinding keyBindSneak;
/*  203:     */   public KeyBinding keyBindInventory;
/*  204:     */   public KeyBinding keyBindUseItem;
/*  205:     */   public KeyBinding keyBindDrop;
/*  206:     */   public KeyBinding keyBindAttack;
/*  207:     */   public KeyBinding keyBindPickBlock;
/*  208:     */   public KeyBinding keyBindSprint;
/*  209:     */   public KeyBinding keyBindChat;
/*  210:     */   public KeyBinding keyBindPlayerList;
/*  211:     */   public KeyBinding keyBindCommand;
/*  212:     */   public KeyBinding keyBindScreenshot;
/*  213:     */   public KeyBinding keyBindTogglePerspective;
/*  214:     */   public KeyBinding keyBindSmoothCamera;
/*  215:     */   public KeyBinding[] keyBindsHotbar;
/*  216:     */   public KeyBinding[] keyBindings;
/*  217:     */   protected Minecraft mc;
/*  218:     */   private File optionsFile;
/*  219:     */   public EnumDifficulty difficulty;
/*  220:     */   public boolean hideGUI;
/*  221:     */   public int thirdPersonView;
/*  222:     */   public boolean showDebugInfo;
/*  223:     */   public boolean showDebugProfilerChart;
/*  224:     */   public String lastServer;
/*  225:     */   public boolean noclip;
/*  226:     */   public boolean smoothCamera;
/*  227:     */   public boolean debugCamEnable;
/*  228:     */   public float noclipRate;
/*  229:     */   public float debugCamRate;
/*  230:     */   public float fovSetting;
/*  231:     */   public float gammaSetting;
/*  232:     */   public float saturation;
/*  233:     */   public int guiScale;
/*  234:     */   public int particleSetting;
/*  235:     */   public String language;
/*  236:     */   public boolean forceUnicodeFont;
/*  237:     */   private static final String __OBFID = "CL_00000650";
/*  238:     */   
/*  239:     */   public GameSettings(Minecraft par1Minecraft, File par2File)
/*  240:     */   {
/*  241: 267 */     this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
/*  242: 268 */     this.chatColours = true;
/*  243: 269 */     this.chatLinks = true;
/*  244: 270 */     this.chatLinksPrompt = true;
/*  245: 271 */     this.chatOpacity = 1.0F;
/*  246: 272 */     this.serverTextures = true;
/*  247: 273 */     this.snooperEnabled = true;
/*  248: 274 */     this.enableVsync = true;
/*  249: 275 */     this.pauseOnLostFocus = true;
/*  250: 276 */     this.showCape = true;
/*  251: 277 */     this.heldItemTooltips = true;
/*  252: 278 */     this.chatScale = 1.0F;
/*  253: 279 */     this.chatWidth = 1.0F;
/*  254: 280 */     this.chatHeightUnfocused = 0.443662F;
/*  255: 281 */     this.chatHeightFocused = 1.0F;
/*  256: 282 */     this.showInventoryAchievementHint = true;
/*  257: 283 */     this.mipmapLevels = 4;
/*  258: 284 */     this.anisotropicFiltering = 1;
/*  259: 285 */     this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
/*  260: 286 */     this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
/*  261: 287 */     this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
/*  262: 288 */     this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
/*  263: 289 */     this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
/*  264: 290 */     this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
/*  265: 291 */     this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
/*  266: 292 */     this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
/*  267: 293 */     this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
/*  268: 294 */     this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
/*  269: 295 */     this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
/*  270: 296 */     this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
/*  271: 297 */     this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
/*  272: 298 */     this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
/*  273: 299 */     this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
/*  274: 300 */     this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
/*  275: 301 */     this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
/*  276: 302 */     this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
/*  277: 303 */     this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
/*  278: 304 */     this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
/*  279: 305 */     this.keyBindings = ((KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint }, this.keyBindsHotbar));
/*  280: 306 */     this.difficulty = EnumDifficulty.NORMAL;
/*  281: 307 */     this.lastServer = "";
/*  282: 308 */     this.noclipRate = 1.0F;
/*  283: 309 */     this.debugCamRate = 1.0F;
/*  284: 310 */     this.language = "en_US";
/*  285: 311 */     this.forceUnicodeFont = false;
/*  286: 312 */     this.mc = par1Minecraft;
/*  287: 313 */     this.optionsFile = new File(par2File, "options.txt");
/*  288: 314 */     this.optionsFileOF = new File(par2File, "optionsof.txt");
/*  289: 315 */     this.limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
/*  290: 316 */     this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
/*  291: 317 */     this.keyBindings = ((KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom));
/*  292: 318 */     Options.RENDER_DISTANCE.setValueMax(32.0F);
/*  293: 319 */     this.renderDistanceChunks = (par1Minecraft.isJava64bit() ? 12 : 8);
/*  294: 320 */     loadOptions();
/*  295: 321 */     Config.initGameSettings(this);
/*  296:     */   }
/*  297:     */   
/*  298:     */   public GameSettings()
/*  299:     */   {
/*  300: 326 */     this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
/*  301: 327 */     this.chatColours = true;
/*  302: 328 */     this.chatLinks = true;
/*  303: 329 */     this.chatLinksPrompt = true;
/*  304: 330 */     this.chatOpacity = 1.0F;
/*  305: 331 */     this.serverTextures = true;
/*  306: 332 */     this.snooperEnabled = true;
/*  307: 333 */     this.enableVsync = true;
/*  308: 334 */     this.pauseOnLostFocus = true;
/*  309: 335 */     this.showCape = true;
/*  310: 336 */     this.heldItemTooltips = true;
/*  311: 337 */     this.chatScale = 1.0F;
/*  312: 338 */     this.chatWidth = 1.0F;
/*  313: 339 */     this.chatHeightUnfocused = 0.443662F;
/*  314: 340 */     this.chatHeightFocused = 1.0F;
/*  315: 341 */     this.showInventoryAchievementHint = true;
/*  316: 342 */     this.mipmapLevels = 4;
/*  317: 343 */     this.anisotropicFiltering = 1;
/*  318: 344 */     this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
/*  319: 345 */     this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
/*  320: 346 */     this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
/*  321: 347 */     this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
/*  322: 348 */     this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
/*  323: 349 */     this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
/*  324: 350 */     this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
/*  325: 351 */     this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
/*  326: 352 */     this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
/*  327: 353 */     this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
/*  328: 354 */     this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
/*  329: 355 */     this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
/*  330: 356 */     this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
/*  331: 357 */     this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
/*  332: 358 */     this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
/*  333: 359 */     this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
/*  334: 360 */     this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
/*  335: 361 */     this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
/*  336: 362 */     this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
/*  337: 363 */     this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
/*  338: 364 */     this.keyBindings = ((KeyBinding[])ArrayUtils.addAll(new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint }, this.keyBindsHotbar));
/*  339: 365 */     this.difficulty = EnumDifficulty.NORMAL;
/*  340: 366 */     this.lastServer = "";
/*  341: 367 */     this.noclipRate = 1.0F;
/*  342: 368 */     this.debugCamRate = 1.0F;
/*  343: 369 */     this.language = "en_US";
/*  344: 370 */     this.forceUnicodeFont = false;
/*  345: 371 */     this.limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
/*  346: 372 */     this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
/*  347: 373 */     this.keyBindings = ((KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom));
/*  348:     */   }
/*  349:     */   
/*  350:     */   public static String getKeyDisplayString(int par0)
/*  351:     */   {
/*  352: 381 */     return par0 < 0 ? I18n.format("key.mouseButton", new Object[] { Integer.valueOf(par0 + 101) }) : Keyboard.getKeyName(par0);
/*  353:     */   }
/*  354:     */   
/*  355:     */   public static boolean isKeyDown(KeyBinding par0KeyBinding)
/*  356:     */   {
/*  357: 389 */     return par0KeyBinding.getKeyCode() < 0 ? Mouse.isButtonDown(par0KeyBinding.getKeyCode() + 100) : par0KeyBinding.getKeyCode() == 0 ? false : Keyboard.isKeyDown(par0KeyBinding.getKeyCode());
/*  358:     */   }
/*  359:     */   
/*  360:     */   public void setKeyCodeSave(KeyBinding p_151440_1_, int p_151440_2_)
/*  361:     */   {
/*  362: 394 */     p_151440_1_.setKeyCode(p_151440_2_);
/*  363: 395 */     saveOptions();
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void setOptionFloatValue(Options par1EnumOptions, float par2)
/*  367:     */   {
/*  368: 403 */     if (par1EnumOptions == Options.CLOUD_HEIGHT) {
/*  369: 405 */       this.ofCloudsHeight = par2;
/*  370:     */     }
/*  371: 408 */     if (par1EnumOptions == Options.AO_LEVEL)
/*  372:     */     {
/*  373: 410 */       this.ofAoLevel = par2;
/*  374: 411 */       this.mc.renderGlobal.loadRenderers();
/*  375:     */     }
/*  376: 414 */     if (par1EnumOptions == Options.SENSITIVITY) {
/*  377: 416 */       this.mouseSensitivity = par2;
/*  378:     */     }
/*  379: 419 */     if (par1EnumOptions == Options.FOV) {
/*  380: 421 */       this.fovSetting = par2;
/*  381:     */     }
/*  382: 424 */     if (par1EnumOptions == Options.GAMMA) {
/*  383: 426 */       this.gammaSetting = par2;
/*  384:     */     }
/*  385: 429 */     if (par1EnumOptions == Options.FRAMERATE_LIMIT)
/*  386:     */     {
/*  387: 431 */       this.limitFramerate = ((int)par2);
/*  388: 432 */       this.enableVsync = false;
/*  389: 434 */       if (this.limitFramerate <= 0)
/*  390:     */       {
/*  391: 436 */         this.limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
/*  392: 437 */         this.enableVsync = true;
/*  393:     */       }
/*  394: 440 */       updateVSync();
/*  395:     */     }
/*  396: 443 */     if (par1EnumOptions == Options.CHAT_OPACITY)
/*  397:     */     {
/*  398: 445 */       this.chatOpacity = par2;
/*  399: 446 */       this.mc.ingameGUI.getChatGUI().func_146245_b();
/*  400:     */     }
/*  401: 449 */     if (par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED)
/*  402:     */     {
/*  403: 451 */       this.chatHeightFocused = par2;
/*  404: 452 */       this.mc.ingameGUI.getChatGUI().func_146245_b();
/*  405:     */     }
/*  406: 455 */     if (par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED)
/*  407:     */     {
/*  408: 457 */       this.chatHeightUnfocused = par2;
/*  409: 458 */       this.mc.ingameGUI.getChatGUI().func_146245_b();
/*  410:     */     }
/*  411: 461 */     if (par1EnumOptions == Options.CHAT_WIDTH)
/*  412:     */     {
/*  413: 463 */       this.chatWidth = par2;
/*  414: 464 */       this.mc.ingameGUI.getChatGUI().func_146245_b();
/*  415:     */     }
/*  416: 467 */     if (par1EnumOptions == Options.CHAT_SCALE)
/*  417:     */     {
/*  418: 469 */       this.chatScale = par2;
/*  419: 470 */       this.mc.ingameGUI.getChatGUI().func_146245_b();
/*  420:     */     }
/*  421: 475 */     if (par1EnumOptions == Options.ANISOTROPIC_FILTERING)
/*  422:     */     {
/*  423: 477 */       int var3 = this.anisotropicFiltering;
/*  424: 478 */       this.anisotropicFiltering = ((int)par2);
/*  425: 480 */       if (var3 != par2)
/*  426:     */       {
/*  427: 482 */         this.mc.getTextureMapBlocks().func_147632_b(this.anisotropicFiltering);
/*  428: 483 */         this.mc.scheduleResourcesRefresh();
/*  429:     */       }
/*  430:     */     }
/*  431: 487 */     if (par1EnumOptions == Options.MIPMAP_LEVELS)
/*  432:     */     {
/*  433: 489 */       int var3 = this.mipmapLevels;
/*  434: 490 */       this.mipmapLevels = ((int)par2);
/*  435: 492 */       if (var3 != par2)
/*  436:     */       {
/*  437: 494 */         this.mc.getTextureMapBlocks().func_147633_a(this.mipmapLevels);
/*  438: 495 */         this.mc.scheduleResourcesRefresh();
/*  439:     */       }
/*  440:     */     }
/*  441: 499 */     if (par1EnumOptions == Options.RENDER_DISTANCE) {
/*  442: 501 */       this.renderDistanceChunks = ((int)par2);
/*  443:     */     }
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void setOptionValue(Options par1EnumOptions, int par2)
/*  447:     */   {
/*  448: 510 */     if (par1EnumOptions == Options.FOG_FANCY) {
/*  449: 512 */       switch (this.ofFogType)
/*  450:     */       {
/*  451:     */       case 1: 
/*  452: 515 */         this.ofFogType = 2;
/*  453: 517 */         if (!Config.isFancyFogAvailable()) {
/*  454: 519 */           this.ofFogType = 3;
/*  455:     */         }
/*  456: 522 */         break;
/*  457:     */       case 2: 
/*  458: 525 */         this.ofFogType = 3;
/*  459: 526 */         break;
/*  460:     */       case 3: 
/*  461: 529 */         this.ofFogType = 1;
/*  462: 530 */         break;
/*  463:     */       default: 
/*  464: 533 */         this.ofFogType = 1;
/*  465:     */       }
/*  466:     */     }
/*  467: 537 */     if (par1EnumOptions == Options.FOG_START)
/*  468:     */     {
/*  469: 539 */       this.ofFogStart += 0.2F;
/*  470: 541 */       if (this.ofFogStart > 0.81F) {
/*  471: 543 */         this.ofFogStart = 0.2F;
/*  472:     */       }
/*  473:     */     }
/*  474: 547 */     if (par1EnumOptions == Options.MIPMAP_TYPE)
/*  475:     */     {
/*  476: 549 */       this.ofMipmapType += 1;
/*  477: 551 */       if (this.ofMipmapType > 3) {
/*  478: 553 */         this.ofMipmapType = 0;
/*  479:     */       }
/*  480: 556 */       TextureUtils.refreshBlockTextures();
/*  481:     */     }
/*  482: 559 */     if (par1EnumOptions == Options.LOAD_FAR)
/*  483:     */     {
/*  484: 561 */       this.ofLoadFar = (!this.ofLoadFar);
/*  485: 562 */       this.mc.renderGlobal.loadRenderers();
/*  486:     */     }
/*  487: 565 */     if (par1EnumOptions == Options.PRELOADED_CHUNKS)
/*  488:     */     {
/*  489: 567 */       this.ofPreloadedChunks += 2;
/*  490: 569 */       if (this.ofPreloadedChunks > 8) {
/*  491: 571 */         this.ofPreloadedChunks = 0;
/*  492:     */       }
/*  493: 574 */       this.mc.renderGlobal.loadRenderers();
/*  494:     */     }
/*  495: 577 */     if (par1EnumOptions == Options.SMOOTH_FPS) {
/*  496: 579 */       this.ofSmoothFps = (!this.ofSmoothFps);
/*  497:     */     }
/*  498: 582 */     if (par1EnumOptions == Options.SMOOTH_WORLD)
/*  499:     */     {
/*  500: 584 */       this.ofSmoothWorld = (!this.ofSmoothWorld);
/*  501: 585 */       Config.updateAvailableProcessors();
/*  502: 587 */       if (!Config.isSingleProcessor()) {
/*  503: 589 */         this.ofSmoothWorld = false;
/*  504:     */       }
/*  505: 592 */       Config.updateThreadPriorities();
/*  506:     */     }
/*  507: 595 */     if (par1EnumOptions == Options.CLOUDS)
/*  508:     */     {
/*  509: 597 */       this.ofClouds += 1;
/*  510: 599 */       if (this.ofClouds > 3) {
/*  511: 601 */         this.ofClouds = 0;
/*  512:     */       }
/*  513:     */     }
/*  514: 605 */     if (par1EnumOptions == Options.TREES)
/*  515:     */     {
/*  516: 607 */       this.ofTrees += 1;
/*  517: 609 */       if (this.ofTrees > 2) {
/*  518: 611 */         this.ofTrees = 0;
/*  519:     */       }
/*  520: 614 */       this.mc.renderGlobal.loadRenderers();
/*  521:     */     }
/*  522: 617 */     if (par1EnumOptions == Options.GRASS)
/*  523:     */     {
/*  524: 619 */       this.ofGrass += 1;
/*  525: 621 */       if (this.ofGrass > 2) {
/*  526: 623 */         this.ofGrass = 0;
/*  527:     */       }
/*  528: 626 */       net.minecraft.client.renderer.RenderBlocks.fancyGrass = Config.isGrassFancy();
/*  529: 627 */       this.mc.renderGlobal.loadRenderers();
/*  530:     */     }
/*  531: 630 */     if (par1EnumOptions == Options.DROPPED_ITEMS)
/*  532:     */     {
/*  533: 632 */       this.ofDroppedItems += 1;
/*  534: 634 */       if (this.ofDroppedItems > 2) {
/*  535: 636 */         this.ofDroppedItems = 0;
/*  536:     */       }
/*  537:     */     }
/*  538: 640 */     if (par1EnumOptions == Options.RAIN)
/*  539:     */     {
/*  540: 642 */       this.ofRain += 1;
/*  541: 644 */       if (this.ofRain > 3) {
/*  542: 646 */         this.ofRain = 0;
/*  543:     */       }
/*  544:     */     }
/*  545: 650 */     if (par1EnumOptions == Options.WATER)
/*  546:     */     {
/*  547: 652 */       this.ofWater += 1;
/*  548: 654 */       if (this.ofWater > 2) {
/*  549: 656 */         this.ofWater = 0;
/*  550:     */       }
/*  551:     */     }
/*  552: 660 */     if (par1EnumOptions == Options.ANIMATED_WATER)
/*  553:     */     {
/*  554: 662 */       this.ofAnimatedWater += 1;
/*  555: 664 */       if (this.ofAnimatedWater > 2) {
/*  556: 666 */         this.ofAnimatedWater = 0;
/*  557:     */       }
/*  558:     */     }
/*  559: 670 */     if (par1EnumOptions == Options.ANIMATED_LAVA)
/*  560:     */     {
/*  561: 672 */       this.ofAnimatedLava += 1;
/*  562: 674 */       if (this.ofAnimatedLava > 2) {
/*  563: 676 */         this.ofAnimatedLava = 0;
/*  564:     */       }
/*  565:     */     }
/*  566: 680 */     if (par1EnumOptions == Options.ANIMATED_FIRE) {
/*  567: 682 */       this.ofAnimatedFire = (!this.ofAnimatedFire);
/*  568:     */     }
/*  569: 685 */     if (par1EnumOptions == Options.ANIMATED_PORTAL) {
/*  570: 687 */       this.ofAnimatedPortal = (!this.ofAnimatedPortal);
/*  571:     */     }
/*  572: 690 */     if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
/*  573: 692 */       this.ofAnimatedRedstone = (!this.ofAnimatedRedstone);
/*  574:     */     }
/*  575: 695 */     if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
/*  576: 697 */       this.ofAnimatedExplosion = (!this.ofAnimatedExplosion);
/*  577:     */     }
/*  578: 700 */     if (par1EnumOptions == Options.ANIMATED_FLAME) {
/*  579: 702 */       this.ofAnimatedFlame = (!this.ofAnimatedFlame);
/*  580:     */     }
/*  581: 705 */     if (par1EnumOptions == Options.ANIMATED_SMOKE) {
/*  582: 707 */       this.ofAnimatedSmoke = (!this.ofAnimatedSmoke);
/*  583:     */     }
/*  584: 710 */     if (par1EnumOptions == Options.VOID_PARTICLES) {
/*  585: 712 */       this.ofVoidParticles = (!this.ofVoidParticles);
/*  586:     */     }
/*  587: 715 */     if (par1EnumOptions == Options.WATER_PARTICLES) {
/*  588: 717 */       this.ofWaterParticles = (!this.ofWaterParticles);
/*  589:     */     }
/*  590: 720 */     if (par1EnumOptions == Options.PORTAL_PARTICLES) {
/*  591: 722 */       this.ofPortalParticles = (!this.ofPortalParticles);
/*  592:     */     }
/*  593: 725 */     if (par1EnumOptions == Options.POTION_PARTICLES) {
/*  594: 727 */       this.ofPotionParticles = (!this.ofPotionParticles);
/*  595:     */     }
/*  596: 730 */     if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
/*  597: 732 */       this.ofDrippingWaterLava = (!this.ofDrippingWaterLava);
/*  598:     */     }
/*  599: 735 */     if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
/*  600: 737 */       this.ofAnimatedTerrain = (!this.ofAnimatedTerrain);
/*  601:     */     }
/*  602: 740 */     if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
/*  603: 742 */       this.ofAnimatedTextures = (!this.ofAnimatedTextures);
/*  604:     */     }
/*  605: 745 */     if (par1EnumOptions == Options.ANIMATED_ITEMS) {
/*  606: 747 */       this.ofAnimatedItems = (!this.ofAnimatedItems);
/*  607:     */     }
/*  608: 750 */     if (par1EnumOptions == Options.RAIN_SPLASH) {
/*  609: 752 */       this.ofRainSplash = (!this.ofRainSplash);
/*  610:     */     }
/*  611: 755 */     if (par1EnumOptions == Options.LAGOMETER) {
/*  612: 757 */       this.ofLagometer = (!this.ofLagometer);
/*  613:     */     }
/*  614: 760 */     if (par1EnumOptions == Options.AUTOSAVE_TICKS)
/*  615:     */     {
/*  616: 762 */       this.ofAutoSaveTicks *= 10;
/*  617: 764 */       if (this.ofAutoSaveTicks > 40000) {
/*  618: 766 */         this.ofAutoSaveTicks = 40;
/*  619:     */       }
/*  620:     */     }
/*  621: 770 */     if (par1EnumOptions == Options.BETTER_GRASS)
/*  622:     */     {
/*  623: 772 */       this.ofBetterGrass += 1;
/*  624: 774 */       if (this.ofBetterGrass > 3) {
/*  625: 776 */         this.ofBetterGrass = 1;
/*  626:     */       }
/*  627: 779 */       this.mc.renderGlobal.loadRenderers();
/*  628:     */     }
/*  629: 782 */     if (par1EnumOptions == Options.CONNECTED_TEXTURES)
/*  630:     */     {
/*  631: 784 */       this.ofConnectedTextures += 1;
/*  632: 786 */       if (this.ofConnectedTextures > 3) {
/*  633: 788 */         this.ofConnectedTextures = 1;
/*  634:     */       }
/*  635: 791 */       this.mc.renderGlobal.loadRenderers();
/*  636:     */     }
/*  637: 794 */     if (par1EnumOptions == Options.WEATHER) {
/*  638: 796 */       this.ofWeather = (!this.ofWeather);
/*  639:     */     }
/*  640: 799 */     if (par1EnumOptions == Options.SKY) {
/*  641: 801 */       this.ofSky = (!this.ofSky);
/*  642:     */     }
/*  643: 804 */     if (par1EnumOptions == Options.STARS) {
/*  644: 806 */       this.ofStars = (!this.ofStars);
/*  645:     */     }
/*  646: 809 */     if (par1EnumOptions == Options.SUN_MOON) {
/*  647: 811 */       this.ofSunMoon = (!this.ofSunMoon);
/*  648:     */     }
/*  649: 814 */     if (par1EnumOptions == Options.CHUNK_UPDATES)
/*  650:     */     {
/*  651: 816 */       this.ofChunkUpdates += 1;
/*  652: 818 */       if (this.ofChunkUpdates > 5) {
/*  653: 820 */         this.ofChunkUpdates = 1;
/*  654:     */       }
/*  655:     */     }
/*  656: 824 */     if (par1EnumOptions == Options.CHUNK_LOADING)
/*  657:     */     {
/*  658: 826 */       this.ofChunkLoading += 1;
/*  659: 828 */       if (this.ofChunkLoading > 2) {
/*  660: 830 */         this.ofChunkLoading = 0;
/*  661:     */       }
/*  662: 833 */       updateChunkLoading();
/*  663:     */     }
/*  664: 836 */     if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC) {
/*  665: 838 */       this.ofChunkUpdatesDynamic = (!this.ofChunkUpdatesDynamic);
/*  666:     */     }
/*  667: 841 */     if (par1EnumOptions == Options.TIME)
/*  668:     */     {
/*  669: 843 */       this.ofTime += 1;
/*  670: 845 */       if (this.ofTime > 3) {
/*  671: 847 */         this.ofTime = 0;
/*  672:     */       }
/*  673:     */     }
/*  674: 851 */     if (par1EnumOptions == Options.CLEAR_WATER)
/*  675:     */     {
/*  676: 853 */       this.ofClearWater = (!this.ofClearWater);
/*  677: 854 */       updateWaterOpacity();
/*  678:     */     }
/*  679: 857 */     if (par1EnumOptions == Options.DEPTH_FOG) {
/*  680: 859 */       this.ofDepthFog = (!this.ofDepthFog);
/*  681:     */     }
/*  682: 862 */     if (par1EnumOptions == Options.AA_LEVEL) {
/*  683: 864 */       this.ofAaLevel = 0;
/*  684:     */     }
/*  685: 867 */     if (par1EnumOptions == Options.PROFILER) {
/*  686: 869 */       this.ofProfiler = (!this.ofProfiler);
/*  687:     */     }
/*  688: 872 */     if (par1EnumOptions == Options.BETTER_SNOW)
/*  689:     */     {
/*  690: 874 */       this.ofBetterSnow = (!this.ofBetterSnow);
/*  691: 875 */       this.mc.renderGlobal.loadRenderers();
/*  692:     */     }
/*  693: 878 */     if (par1EnumOptions == Options.SWAMP_COLORS)
/*  694:     */     {
/*  695: 880 */       this.ofSwampColors = (!this.ofSwampColors);
/*  696: 881 */       CustomColorizer.updateUseDefaultColorMultiplier();
/*  697: 882 */       this.mc.renderGlobal.loadRenderers();
/*  698:     */     }
/*  699: 885 */     if (par1EnumOptions == Options.RANDOM_MOBS)
/*  700:     */     {
/*  701: 887 */       this.ofRandomMobs = (!this.ofRandomMobs);
/*  702: 888 */       RandomMobs.resetTextures();
/*  703:     */     }
/*  704: 891 */     if (par1EnumOptions == Options.SMOOTH_BIOMES)
/*  705:     */     {
/*  706: 893 */       this.ofSmoothBiomes = (!this.ofSmoothBiomes);
/*  707: 894 */       CustomColorizer.updateUseDefaultColorMultiplier();
/*  708: 895 */       this.mc.renderGlobal.loadRenderers();
/*  709:     */     }
/*  710: 898 */     if (par1EnumOptions == Options.CUSTOM_FONTS)
/*  711:     */     {
/*  712: 900 */       this.ofCustomFonts = (!this.ofCustomFonts);
/*  713: 901 */       this.mc.fontRenderer.onResourceManagerReload(Config.getResourceManager());
/*  714: 902 */       this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
/*  715:     */     }
/*  716: 905 */     if (par1EnumOptions == Options.CUSTOM_COLORS)
/*  717:     */     {
/*  718: 907 */       this.ofCustomColors = (!this.ofCustomColors);
/*  719: 908 */       CustomColorizer.update();
/*  720: 909 */       this.mc.renderGlobal.loadRenderers();
/*  721:     */     }
/*  722: 912 */     if (par1EnumOptions == Options.CUSTOM_SKY)
/*  723:     */     {
/*  724: 914 */       this.ofCustomSky = (!this.ofCustomSky);
/*  725: 915 */       CustomSky.update();
/*  726:     */     }
/*  727: 918 */     if (par1EnumOptions == Options.SHOW_CAPES)
/*  728:     */     {
/*  729: 920 */       this.ofShowCapes = (!this.ofShowCapes);
/*  730: 921 */       this.mc.renderGlobal.updateCapes();
/*  731:     */     }
/*  732: 924 */     if (par1EnumOptions == Options.NATURAL_TEXTURES)
/*  733:     */     {
/*  734: 926 */       this.ofNaturalTextures = (!this.ofNaturalTextures);
/*  735: 927 */       NaturalTextures.update();
/*  736: 928 */       this.mc.renderGlobal.loadRenderers();
/*  737:     */     }
/*  738: 931 */     if (par1EnumOptions == Options.FAST_MATH)
/*  739:     */     {
/*  740: 933 */       this.ofFastMath = (!this.ofFastMath);
/*  741: 934 */       MathHelper.fastMath = this.ofFastMath;
/*  742:     */     }
/*  743: 937 */     if (par1EnumOptions == Options.FAST_RENDER)
/*  744:     */     {
/*  745: 939 */       this.ofFastRender = (!this.ofFastRender);
/*  746: 940 */       this.mc.renderGlobal.loadRenderers();
/*  747:     */     }
/*  748: 943 */     if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS)
/*  749:     */     {
/*  750: 945 */       if (this.ofTranslucentBlocks == 1) {
/*  751: 947 */         this.ofTranslucentBlocks = 2;
/*  752:     */       } else {
/*  753: 951 */         this.ofTranslucentBlocks = 1;
/*  754:     */       }
/*  755: 954 */       this.mc.renderGlobal.loadRenderers();
/*  756:     */     }
/*  757: 957 */     if (par1EnumOptions == Options.LAZY_CHUNK_LOADING)
/*  758:     */     {
/*  759: 959 */       this.ofLazyChunkLoading = (!this.ofLazyChunkLoading);
/*  760: 960 */       Config.updateAvailableProcessors();
/*  761: 962 */       if (!Config.isSingleProcessor()) {
/*  762: 964 */         this.ofLazyChunkLoading = false;
/*  763:     */       }
/*  764: 967 */       this.mc.renderGlobal.loadRenderers();
/*  765:     */     }
/*  766: 970 */     if (par1EnumOptions == Options.FULLSCREEN_MODE)
/*  767:     */     {
/*  768: 972 */       List modeList = Arrays.asList(Config.getFullscreenModes());
/*  769: 974 */       if (this.ofFullscreenMode.equals("Default"))
/*  770:     */       {
/*  771: 976 */         this.ofFullscreenMode = ((String)modeList.get(0));
/*  772:     */       }
/*  773:     */       else
/*  774:     */       {
/*  775: 980 */         int index = modeList.indexOf(this.ofFullscreenMode);
/*  776: 982 */         if (index < 0)
/*  777:     */         {
/*  778: 984 */           this.ofFullscreenMode = "Default";
/*  779:     */         }
/*  780:     */         else
/*  781:     */         {
/*  782: 988 */           index++;
/*  783: 990 */           if (index >= modeList.size()) {
/*  784: 992 */             this.ofFullscreenMode = "Default";
/*  785:     */           } else {
/*  786: 996 */             this.ofFullscreenMode = ((String)modeList.get(index));
/*  787:     */           }
/*  788:     */         }
/*  789:     */       }
/*  790:     */     }
/*  791:1002 */     if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
/*  792:1004 */       this.heldItemTooltips = (!this.heldItemTooltips);
/*  793:     */     }
/*  794:1007 */     if (par1EnumOptions == Options.INVERT_MOUSE) {
/*  795:1009 */       this.invertMouse = (!this.invertMouse);
/*  796:     */     }
/*  797:1012 */     if (par1EnumOptions == Options.GUI_SCALE) {
/*  798:1014 */       this.guiScale = (this.guiScale + par2 & 0x3);
/*  799:     */     }
/*  800:1017 */     if (par1EnumOptions == Options.PARTICLES) {
/*  801:1019 */       this.particleSetting = ((this.particleSetting + par2) % 3);
/*  802:     */     }
/*  803:1022 */     if (par1EnumOptions == Options.VIEW_BOBBING) {
/*  804:1024 */       this.viewBobbing = (!this.viewBobbing);
/*  805:     */     }
/*  806:1027 */     if (par1EnumOptions == Options.RENDER_CLOUDS) {
/*  807:1029 */       this.clouds = (!this.clouds);
/*  808:     */     }
/*  809:1032 */     if (par1EnumOptions == Options.FORCE_UNICODE_FONT)
/*  810:     */     {
/*  811:1034 */       this.forceUnicodeFont = (!this.forceUnicodeFont);
/*  812:1035 */       this.mc.fontRenderer.setUnicodeFlag((this.mc.getLanguageManager().isCurrentLocaleUnicode()) || (this.forceUnicodeFont));
/*  813:     */     }
/*  814:1038 */     if (par1EnumOptions == Options.ADVANCED_OPENGL)
/*  815:     */     {
/*  816:1040 */       if (!Config.isOcclusionAvailable())
/*  817:     */       {
/*  818:1042 */         this.ofOcclusionFancy = false;
/*  819:1043 */         this.advancedOpengl = false;
/*  820:     */       }
/*  821:1045 */       else if (!this.advancedOpengl)
/*  822:     */       {
/*  823:1047 */         this.advancedOpengl = true;
/*  824:1048 */         this.ofOcclusionFancy = false;
/*  825:     */       }
/*  826:1050 */       else if (!this.ofOcclusionFancy)
/*  827:     */       {
/*  828:1052 */         this.ofOcclusionFancy = true;
/*  829:     */       }
/*  830:     */       else
/*  831:     */       {
/*  832:1056 */         this.ofOcclusionFancy = false;
/*  833:1057 */         this.advancedOpengl = false;
/*  834:     */       }
/*  835:1060 */       this.mc.renderGlobal.setAllRenderersVisible();
/*  836:     */     }
/*  837:1063 */     if (par1EnumOptions == Options.FBO_ENABLE) {
/*  838:1065 */       this.fboEnable = (!this.fboEnable);
/*  839:     */     }
/*  840:1068 */     if (par1EnumOptions == Options.ANAGLYPH)
/*  841:     */     {
/*  842:1070 */       this.anaglyph = (!this.anaglyph);
/*  843:1071 */       this.mc.refreshResources();
/*  844:     */     }
/*  845:1074 */     if (par1EnumOptions == Options.DIFFICULTY) {
/*  846:1076 */       this.difficulty = EnumDifficulty.getDifficultyEnum(this.difficulty.getDifficultyId() + par2 & 0x3);
/*  847:     */     }
/*  848:1079 */     if (par1EnumOptions == Options.GRAPHICS)
/*  849:     */     {
/*  850:1081 */       this.fancyGraphics = (!this.fancyGraphics);
/*  851:1082 */       this.mc.renderGlobal.loadRenderers();
/*  852:     */     }
/*  853:1085 */     if (par1EnumOptions == Options.AMBIENT_OCCLUSION)
/*  854:     */     {
/*  855:1087 */       this.ambientOcclusion = ((this.ambientOcclusion + par2) % 3);
/*  856:1088 */       this.mc.renderGlobal.loadRenderers();
/*  857:     */     }
/*  858:1091 */     if (par1EnumOptions == Options.CHAT_VISIBILITY) {
/*  859:1093 */       this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + par2) % 3);
/*  860:     */     }
/*  861:1096 */     if (par1EnumOptions == Options.CHAT_COLOR) {
/*  862:1098 */       this.chatColours = (!this.chatColours);
/*  863:     */     }
/*  864:1101 */     if (par1EnumOptions == Options.CHAT_LINKS) {
/*  865:1103 */       this.chatLinks = (!this.chatLinks);
/*  866:     */     }
/*  867:1106 */     if (par1EnumOptions == Options.CHAT_LINKS_PROMPT) {
/*  868:1108 */       this.chatLinksPrompt = (!this.chatLinksPrompt);
/*  869:     */     }
/*  870:1111 */     if (par1EnumOptions == Options.USE_SERVER_TEXTURES) {
/*  871:1113 */       this.serverTextures = (!this.serverTextures);
/*  872:     */     }
/*  873:1116 */     if (par1EnumOptions == Options.SNOOPER_ENABLED) {
/*  874:1118 */       this.snooperEnabled = (!this.snooperEnabled);
/*  875:     */     }
/*  876:1121 */     if (par1EnumOptions == Options.SHOW_CAPE) {
/*  877:1123 */       this.showCape = (!this.showCape);
/*  878:     */     }
/*  879:1126 */     if (par1EnumOptions == Options.TOUCHSCREEN) {
/*  880:1128 */       this.touchscreen = (!this.touchscreen);
/*  881:     */     }
/*  882:1131 */     if (par1EnumOptions == Options.USE_FULLSCREEN)
/*  883:     */     {
/*  884:1133 */       this.fullScreen = (!this.fullScreen);
/*  885:1135 */       if (this.mc.isFullScreen() != this.fullScreen) {
/*  886:1137 */         this.mc.toggleFullscreen();
/*  887:     */       }
/*  888:     */     }
/*  889:1141 */     if (par1EnumOptions == Options.ENABLE_VSYNC)
/*  890:     */     {
/*  891:1143 */       this.enableVsync = (!this.enableVsync);
/*  892:1144 */       Display.setVSyncEnabled(this.enableVsync);
/*  893:     */     }
/*  894:1147 */     saveOptions();
/*  895:     */   }
/*  896:     */   
/*  897:     */   public float getOptionFloatValue(Options par1EnumOptions)
/*  898:     */   {
/*  899:1152 */     return par1EnumOptions == Options.RENDER_DISTANCE ? this.renderDistanceChunks : par1EnumOptions == Options.MIPMAP_LEVELS ? this.mipmapLevels : par1EnumOptions == Options.ANISOTROPIC_FILTERING ? this.anisotropicFiltering : par1EnumOptions == Options.FRAMERATE_LIMIT ? this.limitFramerate : par1EnumOptions == Options.CHAT_WIDTH ? this.chatWidth : par1EnumOptions == Options.CHAT_SCALE ? this.chatScale : par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED ? this.chatHeightUnfocused : par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED ? this.chatHeightFocused : par1EnumOptions == Options.CHAT_OPACITY ? this.chatOpacity : par1EnumOptions == Options.SENSITIVITY ? this.mouseSensitivity : par1EnumOptions == Options.SATURATION ? this.saturation : par1EnumOptions == Options.GAMMA ? this.gammaSetting : par1EnumOptions == Options.FOV ? this.fovSetting : par1EnumOptions == Options.FRAMERATE_LIMIT ? this.limitFramerate : (this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax()) && (this.enableVsync) ? 0.0F : par1EnumOptions == Options.AO_LEVEL ? this.ofAoLevel : par1EnumOptions == Options.CLOUD_HEIGHT ? this.ofCloudsHeight : 0.0F;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public boolean getOptionOrdinalValue(Options par1EnumOptions)
/*  903:     */   {
/*  904:1157 */     switch (SwitchOptions.optionIds[par1EnumOptions.ordinal()])
/*  905:     */     {
/*  906:     */     case 1: 
/*  907:1160 */       return this.invertMouse;
/*  908:     */     case 2: 
/*  909:1163 */       return this.viewBobbing;
/*  910:     */     case 3: 
/*  911:1166 */       return this.anaglyph;
/*  912:     */     case 4: 
/*  913:1169 */       return this.advancedOpengl;
/*  914:     */     case 5: 
/*  915:1172 */       return this.fboEnable;
/*  916:     */     case 6: 
/*  917:1175 */       return this.clouds;
/*  918:     */     case 7: 
/*  919:1178 */       return this.chatColours;
/*  920:     */     case 8: 
/*  921:1181 */       return this.chatLinks;
/*  922:     */     case 9: 
/*  923:1184 */       return this.chatLinksPrompt;
/*  924:     */     case 10: 
/*  925:1187 */       return this.serverTextures;
/*  926:     */     case 11: 
/*  927:1190 */       return this.snooperEnabled;
/*  928:     */     case 12: 
/*  929:1193 */       return this.fullScreen;
/*  930:     */     case 13: 
/*  931:1196 */       return this.enableVsync;
/*  932:     */     case 14: 
/*  933:1199 */       return this.showCape;
/*  934:     */     case 15: 
/*  935:1202 */       return this.touchscreen;
/*  936:     */     case 16: 
/*  937:1205 */       return this.forceUnicodeFont;
/*  938:     */     }
/*  939:1208 */     return false;
/*  940:     */   }
/*  941:     */   
/*  942:     */   private static String getTranslation(String[] par0ArrayOfStr, int par1)
/*  943:     */   {
/*  944:1218 */     if ((par1 < 0) || (par1 >= par0ArrayOfStr.length)) {
/*  945:1220 */       par1 = 0;
/*  946:     */     }
/*  947:1223 */     return I18n.format(par0ArrayOfStr[par1], new Object[0]);
/*  948:     */   }
/*  949:     */   
/*  950:     */   public String getKeyBinding(Options par1EnumOptions)
/*  951:     */   {
/*  952:1232 */     String var2 = I18n.format(par1EnumOptions.getEnumString(), new Object[0]) + ": ";
/*  953:1234 */     if (var2 == null) {
/*  954:1236 */       var2 = par1EnumOptions.getEnumString();
/*  955:     */     }
/*  956:1239 */     if (par1EnumOptions == Options.RENDER_DISTANCE)
/*  957:     */     {
/*  958:1241 */       int var33 = (int)getOptionFloatValue(par1EnumOptions);
/*  959:1242 */       String var41 = "Tiny";
/*  960:1243 */       byte baseDist = 2;
/*  961:1245 */       if (var33 >= 4)
/*  962:     */       {
/*  963:1247 */         var41 = "Short";
/*  964:1248 */         baseDist = 4;
/*  965:     */       }
/*  966:1251 */       if (var33 >= 8)
/*  967:     */       {
/*  968:1253 */         var41 = "Normal";
/*  969:1254 */         baseDist = 8;
/*  970:     */       }
/*  971:1257 */       if (var33 >= 16)
/*  972:     */       {
/*  973:1259 */         var41 = "Far";
/*  974:1260 */         baseDist = 16;
/*  975:     */       }
/*  976:1263 */       if (var33 >= 32)
/*  977:     */       {
/*  978:1265 */         var41 = "Extreme";
/*  979:1266 */         baseDist = 32;
/*  980:     */       }
/*  981:1269 */       int diff = this.renderDistanceChunks - baseDist;
/*  982:1270 */       String descr = var41;
/*  983:1272 */       if (diff > 0) {
/*  984:1274 */         descr = var41 + "+";
/*  985:     */       }
/*  986:1277 */       return var2 + var33 + " " + descr;
/*  987:     */     }
/*  988:1279 */     if (par1EnumOptions == Options.ADVANCED_OPENGL) {
/*  989:1281 */       return var2 + "Fast";
/*  990:     */     }
/*  991:1283 */     if (par1EnumOptions == Options.FOG_FANCY)
/*  992:     */     {
/*  993:1285 */       switch (this.ofFogType)
/*  994:     */       {
/*  995:     */       case 1: 
/*  996:1288 */         return var2 + "Fast";
/*  997:     */       case 2: 
/*  998:1291 */         return var2 + "Fancy";
/*  999:     */       case 3: 
/* 1000:1294 */         return var2 + "OFF";
/* 1001:     */       }
/* 1002:1297 */       return var2 + "OFF";
/* 1003:     */     }
/* 1004:1300 */     if (par1EnumOptions == Options.FOG_START) {
/* 1005:1302 */       return var2 + this.ofFogStart;
/* 1006:     */     }
/* 1007:1304 */     if (par1EnumOptions == Options.MIPMAP_TYPE)
/* 1008:     */     {
/* 1009:1306 */       switch (this.ofMipmapType)
/* 1010:     */       {
/* 1011:     */       case 0: 
/* 1012:1309 */         return var2 + "Nearest";
/* 1013:     */       case 1: 
/* 1014:1312 */         return var2 + "Linear";
/* 1015:     */       case 2: 
/* 1016:1315 */         return var2 + "Bilinear";
/* 1017:     */       case 3: 
/* 1018:1318 */         return var2 + "Trilinear";
/* 1019:     */       }
/* 1020:1321 */       return var2 + "Nearest";
/* 1021:     */     }
/* 1022:1324 */     if (par1EnumOptions == Options.LOAD_FAR) {
/* 1023:1326 */       return var2 + "OFF";
/* 1024:     */     }
/* 1025:1328 */     if (par1EnumOptions == Options.PRELOADED_CHUNKS) {
/* 1026:1330 */       return var2 + this.ofPreloadedChunks;
/* 1027:     */     }
/* 1028:1332 */     if (par1EnumOptions == Options.SMOOTH_FPS) {
/* 1029:1334 */       return var2 + "OFF";
/* 1030:     */     }
/* 1031:1336 */     if (par1EnumOptions == Options.SMOOTH_WORLD) {
/* 1032:1338 */       return var2 + "OFF";
/* 1033:     */     }
/* 1034:1340 */     if (par1EnumOptions == Options.CLOUDS)
/* 1035:     */     {
/* 1036:1342 */       switch (this.ofClouds)
/* 1037:     */       {
/* 1038:     */       case 1: 
/* 1039:1345 */         return var2 + "Fast";
/* 1040:     */       case 2: 
/* 1041:1348 */         return var2 + "Fancy";
/* 1042:     */       case 3: 
/* 1043:1351 */         return var2 + "OFF";
/* 1044:     */       }
/* 1045:1354 */       return var2 + "Default";
/* 1046:     */     }
/* 1047:1357 */     if (par1EnumOptions == Options.TREES)
/* 1048:     */     {
/* 1049:1359 */       switch (this.ofTrees)
/* 1050:     */       {
/* 1051:     */       case 1: 
/* 1052:1362 */         return var2 + "Fast";
/* 1053:     */       case 2: 
/* 1054:1365 */         return var2 + "Fancy";
/* 1055:     */       }
/* 1056:1368 */       return var2 + "Default";
/* 1057:     */     }
/* 1058:1371 */     if (par1EnumOptions == Options.GRASS)
/* 1059:     */     {
/* 1060:1373 */       switch (this.ofGrass)
/* 1061:     */       {
/* 1062:     */       case 1: 
/* 1063:1376 */         return var2 + "Fast";
/* 1064:     */       case 2: 
/* 1065:1379 */         return var2 + "Fancy";
/* 1066:     */       }
/* 1067:1382 */       return var2 + "Default";
/* 1068:     */     }
/* 1069:1385 */     if (par1EnumOptions == Options.DROPPED_ITEMS)
/* 1070:     */     {
/* 1071:1387 */       switch (this.ofDroppedItems)
/* 1072:     */       {
/* 1073:     */       case 1: 
/* 1074:1390 */         return var2 + "Fast";
/* 1075:     */       case 2: 
/* 1076:1393 */         return var2 + "Fancy";
/* 1077:     */       }
/* 1078:1396 */       return var2 + "Default";
/* 1079:     */     }
/* 1080:1399 */     if (par1EnumOptions == Options.RAIN)
/* 1081:     */     {
/* 1082:1401 */       switch (this.ofRain)
/* 1083:     */       {
/* 1084:     */       case 1: 
/* 1085:1404 */         return var2 + "Fast";
/* 1086:     */       case 2: 
/* 1087:1407 */         return var2 + "Fancy";
/* 1088:     */       case 3: 
/* 1089:1410 */         return var2 + "OFF";
/* 1090:     */       }
/* 1091:1413 */       return var2 + "Default";
/* 1092:     */     }
/* 1093:1416 */     if (par1EnumOptions == Options.WATER)
/* 1094:     */     {
/* 1095:1418 */       switch (this.ofWater)
/* 1096:     */       {
/* 1097:     */       case 1: 
/* 1098:1421 */         return var2 + "Fast";
/* 1099:     */       case 2: 
/* 1100:1424 */         return var2 + "Fancy";
/* 1101:     */       case 3: 
/* 1102:1427 */         return var2 + "OFF";
/* 1103:     */       }
/* 1104:1430 */       return var2 + "Default";
/* 1105:     */     }
/* 1106:1433 */     if (par1EnumOptions == Options.ANIMATED_WATER)
/* 1107:     */     {
/* 1108:1435 */       switch (this.ofAnimatedWater)
/* 1109:     */       {
/* 1110:     */       case 1: 
/* 1111:1438 */         return var2 + "Dynamic";
/* 1112:     */       case 2: 
/* 1113:1441 */         return var2 + "OFF";
/* 1114:     */       }
/* 1115:1444 */       return var2 + "ON";
/* 1116:     */     }
/* 1117:1447 */     if (par1EnumOptions == Options.ANIMATED_LAVA)
/* 1118:     */     {
/* 1119:1449 */       switch (this.ofAnimatedLava)
/* 1120:     */       {
/* 1121:     */       case 1: 
/* 1122:1452 */         return var2 + "Dynamic";
/* 1123:     */       case 2: 
/* 1124:1455 */         return var2 + "OFF";
/* 1125:     */       }
/* 1126:1458 */       return var2 + "ON";
/* 1127:     */     }
/* 1128:1461 */     if (par1EnumOptions == Options.ANIMATED_FIRE) {
/* 1129:1463 */       return var2 + "OFF";
/* 1130:     */     }
/* 1131:1465 */     if (par1EnumOptions == Options.ANIMATED_PORTAL) {
/* 1132:1467 */       return var2 + "OFF";
/* 1133:     */     }
/* 1134:1469 */     if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
/* 1135:1471 */       return var2 + "OFF";
/* 1136:     */     }
/* 1137:1473 */     if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
/* 1138:1475 */       return var2 + "OFF";
/* 1139:     */     }
/* 1140:1477 */     if (par1EnumOptions == Options.ANIMATED_FLAME) {
/* 1141:1479 */       return var2 + "OFF";
/* 1142:     */     }
/* 1143:1481 */     if (par1EnumOptions == Options.ANIMATED_SMOKE) {
/* 1144:1483 */       return var2 + "OFF";
/* 1145:     */     }
/* 1146:1485 */     if (par1EnumOptions == Options.VOID_PARTICLES) {
/* 1147:1487 */       return var2 + "OFF";
/* 1148:     */     }
/* 1149:1489 */     if (par1EnumOptions == Options.WATER_PARTICLES) {
/* 1150:1491 */       return var2 + "OFF";
/* 1151:     */     }
/* 1152:1493 */     if (par1EnumOptions == Options.PORTAL_PARTICLES) {
/* 1153:1495 */       return var2 + "OFF";
/* 1154:     */     }
/* 1155:1497 */     if (par1EnumOptions == Options.POTION_PARTICLES) {
/* 1156:1499 */       return var2 + "OFF";
/* 1157:     */     }
/* 1158:1501 */     if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
/* 1159:1503 */       return var2 + "OFF";
/* 1160:     */     }
/* 1161:1505 */     if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
/* 1162:1507 */       return var2 + "OFF";
/* 1163:     */     }
/* 1164:1509 */     if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
/* 1165:1511 */       return var2 + "OFF";
/* 1166:     */     }
/* 1167:1513 */     if (par1EnumOptions == Options.ANIMATED_ITEMS) {
/* 1168:1515 */       return var2 + "OFF";
/* 1169:     */     }
/* 1170:1517 */     if (par1EnumOptions == Options.RAIN_SPLASH) {
/* 1171:1519 */       return var2 + "OFF";
/* 1172:     */     }
/* 1173:1521 */     if (par1EnumOptions == Options.LAGOMETER) {
/* 1174:1523 */       return var2 + "OFF";
/* 1175:     */     }
/* 1176:1525 */     if (par1EnumOptions == Options.AUTOSAVE_TICKS) {
/* 1177:1527 */       return var2 + "30min";
/* 1178:     */     }
/* 1179:1529 */     if (par1EnumOptions == Options.BETTER_GRASS)
/* 1180:     */     {
/* 1181:1531 */       switch (this.ofBetterGrass)
/* 1182:     */       {
/* 1183:     */       case 1: 
/* 1184:1534 */         return var2 + "Fast";
/* 1185:     */       case 2: 
/* 1186:1537 */         return var2 + "Fancy";
/* 1187:     */       }
/* 1188:1540 */       return var2 + "OFF";
/* 1189:     */     }
/* 1190:1543 */     if (par1EnumOptions == Options.CONNECTED_TEXTURES)
/* 1191:     */     {
/* 1192:1545 */       switch (this.ofConnectedTextures)
/* 1193:     */       {
/* 1194:     */       case 1: 
/* 1195:1548 */         return var2 + "Fast";
/* 1196:     */       case 2: 
/* 1197:1551 */         return var2 + "Fancy";
/* 1198:     */       }
/* 1199:1554 */       return var2 + "OFF";
/* 1200:     */     }
/* 1201:1557 */     if (par1EnumOptions == Options.WEATHER) {
/* 1202:1559 */       return var2 + "OFF";
/* 1203:     */     }
/* 1204:1561 */     if (par1EnumOptions == Options.SKY) {
/* 1205:1563 */       return var2 + "OFF";
/* 1206:     */     }
/* 1207:1565 */     if (par1EnumOptions == Options.STARS) {
/* 1208:1567 */       return var2 + "OFF";
/* 1209:     */     }
/* 1210:1569 */     if (par1EnumOptions == Options.SUN_MOON) {
/* 1211:1571 */       return var2 + "OFF";
/* 1212:     */     }
/* 1213:1573 */     if (par1EnumOptions == Options.CHUNK_UPDATES) {
/* 1214:1575 */       return var2 + this.ofChunkUpdates;
/* 1215:     */     }
/* 1216:1577 */     if (par1EnumOptions == Options.CHUNK_LOADING) {
/* 1217:1579 */       return var2 + "Default";
/* 1218:     */     }
/* 1219:1581 */     if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC) {
/* 1220:1583 */       return var2 + "OFF";
/* 1221:     */     }
/* 1222:1585 */     if (par1EnumOptions == Options.TIME) {
/* 1223:1587 */       return var2 + "Default";
/* 1224:     */     }
/* 1225:1589 */     if (par1EnumOptions == Options.CLEAR_WATER) {
/* 1226:1591 */       return var2 + "OFF";
/* 1227:     */     }
/* 1228:1593 */     if (par1EnumOptions == Options.DEPTH_FOG) {
/* 1229:1595 */       return var2 + "OFF";
/* 1230:     */     }
/* 1231:1597 */     if (par1EnumOptions == Options.AA_LEVEL) {
/* 1232:1599 */       return var2 + this.ofAaLevel;
/* 1233:     */     }
/* 1234:1601 */     if (par1EnumOptions == Options.PROFILER) {
/* 1235:1603 */       return var2 + "OFF";
/* 1236:     */     }
/* 1237:1605 */     if (par1EnumOptions == Options.BETTER_SNOW) {
/* 1238:1607 */       return var2 + "OFF";
/* 1239:     */     }
/* 1240:1609 */     if (par1EnumOptions == Options.SWAMP_COLORS) {
/* 1241:1611 */       return var2 + "OFF";
/* 1242:     */     }
/* 1243:1613 */     if (par1EnumOptions == Options.RANDOM_MOBS) {
/* 1244:1615 */       return var2 + "OFF";
/* 1245:     */     }
/* 1246:1617 */     if (par1EnumOptions == Options.SMOOTH_BIOMES) {
/* 1247:1619 */       return var2 + "OFF";
/* 1248:     */     }
/* 1249:1621 */     if (par1EnumOptions == Options.CUSTOM_FONTS) {
/* 1250:1623 */       return var2 + "OFF";
/* 1251:     */     }
/* 1252:1625 */     if (par1EnumOptions == Options.CUSTOM_COLORS) {
/* 1253:1627 */       return var2 + "OFF";
/* 1254:     */     }
/* 1255:1629 */     if (par1EnumOptions == Options.CUSTOM_SKY) {
/* 1256:1631 */       return var2 + "OFF";
/* 1257:     */     }
/* 1258:1633 */     if (par1EnumOptions == Options.SHOW_CAPES) {
/* 1259:1635 */       return var2 + "OFF";
/* 1260:     */     }
/* 1261:1637 */     if (par1EnumOptions == Options.NATURAL_TEXTURES) {
/* 1262:1639 */       return var2 + "OFF";
/* 1263:     */     }
/* 1264:1641 */     if (par1EnumOptions == Options.FAST_MATH) {
/* 1265:1643 */       return var2 + "OFF";
/* 1266:     */     }
/* 1267:1645 */     if (par1EnumOptions == Options.FAST_RENDER) {
/* 1268:1647 */       return var2 + "OFF";
/* 1269:     */     }
/* 1270:1649 */     if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS) {
/* 1271:1651 */       return var2 + "Fancy";
/* 1272:     */     }
/* 1273:1653 */     if (par1EnumOptions == Options.LAZY_CHUNK_LOADING) {
/* 1274:1655 */       return var2 + "OFF";
/* 1275:     */     }
/* 1276:1657 */     if (par1EnumOptions == Options.FULLSCREEN_MODE) {
/* 1277:1659 */       return var2 + this.ofFullscreenMode;
/* 1278:     */     }
/* 1279:1661 */     if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
/* 1280:1663 */       return var2 + "OFF";
/* 1281:     */     }
/* 1282:1669 */     if (par1EnumOptions == Options.FRAMERATE_LIMIT)
/* 1283:     */     {
/* 1284:1671 */       float var32 = getOptionFloatValue(par1EnumOptions);
/* 1285:1672 */       return var2 + (int)var32 + " fps";
/* 1286:     */     }
/* 1287:1674 */     if (par1EnumOptions.getEnumFloat())
/* 1288:     */     {
/* 1289:1676 */       float var32 = getOptionFloatValue(par1EnumOptions);
/* 1290:1677 */       float var4 = par1EnumOptions.normalizeValue(var32);
/* 1291:1678 */       return var2 + (int)(var4 * 100.0F) + "%";
/* 1292:     */     }
/* 1293:1680 */     if (par1EnumOptions.getEnumBoolean())
/* 1294:     */     {
/* 1295:1682 */       boolean var31 = getOptionOrdinalValue(par1EnumOptions);
/* 1296:1683 */       return var2 + I18n.format("options.off", new Object[0]);
/* 1297:     */     }
/* 1298:1685 */     if (par1EnumOptions == Options.DIFFICULTY) {
/* 1299:1687 */       return var2 + I18n.format(this.difficulty.getDifficultyResourceKey(), new Object[0]);
/* 1300:     */     }
/* 1301:1689 */     if (par1EnumOptions == Options.GUI_SCALE) {
/* 1302:1691 */       return var2 + getTranslation(GUISCALES, this.guiScale);
/* 1303:     */     }
/* 1304:1693 */     if (par1EnumOptions == Options.CHAT_VISIBILITY) {
/* 1305:1695 */       return var2 + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
/* 1306:     */     }
/* 1307:1697 */     if (par1EnumOptions == Options.PARTICLES) {
/* 1308:1699 */       return var2 + getTranslation(PARTICLES, this.particleSetting);
/* 1309:     */     }
/* 1310:1701 */     if (par1EnumOptions == Options.AMBIENT_OCCLUSION) {
/* 1311:1703 */       return var2 + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
/* 1312:     */     }
/* 1313:1705 */     if (par1EnumOptions == Options.GRAPHICS)
/* 1314:     */     {
/* 1315:1707 */       if (this.fancyGraphics) {
/* 1316:1709 */         return var2 + I18n.format("options.graphics.fancy", new Object[0]);
/* 1317:     */       }
/* 1318:1713 */       String var3 = "options.graphics.fast";
/* 1319:1714 */       return var2 + I18n.format("options.graphics.fast", new Object[0]);
/* 1320:     */     }
/* 1321:1719 */     return var2;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public void loadOptions()
/* 1325:     */   {
/* 1326:     */     try
/* 1327:     */     {
/* 1328:1731 */       if (!this.optionsFile.exists()) {
/* 1329:1733 */         return;
/* 1330:     */       }
/* 1331:1736 */       BufferedReader var9 = new BufferedReader(new FileReader(this.optionsFile));
/* 1332:1737 */       String var2 = "";
/* 1333:1738 */       this.mapSoundLevels.clear();
/* 1334:1740 */       while ((var2 = var9.readLine()) != null) {
/* 1335:     */         try
/* 1336:     */         {
/* 1337:1744 */           String[] var8 = var2.split(":");
/* 1338:1746 */           if (var8[0].equals("mouseSensitivity")) {
/* 1339:1748 */             this.mouseSensitivity = parseFloat(var8[1]);
/* 1340:     */           }
/* 1341:1751 */           if (var8[0].equals("fov")) {
/* 1342:1753 */             this.fovSetting = parseFloat(var8[1]);
/* 1343:     */           }
/* 1344:1756 */           if (var8[0].equals("gamma")) {
/* 1345:1758 */             this.gammaSetting = parseFloat(var8[1]);
/* 1346:     */           }
/* 1347:1761 */           if (var8[0].equals("saturation")) {
/* 1348:1763 */             this.saturation = parseFloat(var8[1]);
/* 1349:     */           }
/* 1350:1766 */           if (var8[0].equals("invertYMouse")) {
/* 1351:1768 */             this.invertMouse = var8[1].equals("true");
/* 1352:     */           }
/* 1353:1771 */           if (var8[0].equals("renderDistance")) {
/* 1354:1773 */             this.renderDistanceChunks = Integer.parseInt(var8[1]);
/* 1355:     */           }
/* 1356:1776 */           if (var8[0].equals("guiScale")) {
/* 1357:1778 */             this.guiScale = Integer.parseInt(var8[1]);
/* 1358:     */           }
/* 1359:1781 */           if (var8[0].equals("particles")) {
/* 1360:1783 */             this.particleSetting = Integer.parseInt(var8[1]);
/* 1361:     */           }
/* 1362:1786 */           if (var8[0].equals("bobView")) {
/* 1363:1788 */             this.viewBobbing = var8[1].equals("true");
/* 1364:     */           }
/* 1365:1791 */           if (var8[0].equals("anaglyph3d")) {
/* 1366:1793 */             this.anaglyph = var8[1].equals("true");
/* 1367:     */           }
/* 1368:1796 */           if (var8[0].equals("advancedOpengl")) {
/* 1369:1798 */             this.advancedOpengl = var8[1].equals("true");
/* 1370:     */           }
/* 1371:1801 */           if (var8[0].equals("maxFps"))
/* 1372:     */           {
/* 1373:1803 */             this.limitFramerate = Integer.parseInt(var8[1]);
/* 1374:1804 */             this.enableVsync = false;
/* 1375:1806 */             if (this.limitFramerate <= 0)
/* 1376:     */             {
/* 1377:1808 */               this.limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
/* 1378:1809 */               this.enableVsync = true;
/* 1379:     */             }
/* 1380:1812 */             updateVSync();
/* 1381:     */           }
/* 1382:1815 */           if (var8[0].equals("fboEnable")) {
/* 1383:1817 */             this.fboEnable = var8[1].equals("true");
/* 1384:     */           }
/* 1385:1820 */           if (var8[0].equals("difficulty")) {
/* 1386:1822 */             this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var8[1]));
/* 1387:     */           }
/* 1388:1825 */           if (var8[0].equals("fancyGraphics")) {
/* 1389:1827 */             this.fancyGraphics = var8[1].equals("true");
/* 1390:     */           }
/* 1391:1830 */           if (var8[0].equals("ao")) {
/* 1392:1832 */             if (var8[1].equals("true")) {
/* 1393:1834 */               this.ambientOcclusion = 2;
/* 1394:1836 */             } else if (var8[1].equals("false")) {
/* 1395:1838 */               this.ambientOcclusion = 0;
/* 1396:     */             } else {
/* 1397:1842 */               this.ambientOcclusion = Integer.parseInt(var8[1]);
/* 1398:     */             }
/* 1399:     */           }
/* 1400:1846 */           if (var8[0].equals("clouds")) {
/* 1401:1848 */             this.clouds = var8[1].equals("true");
/* 1402:     */           }
/* 1403:1851 */           if (var8[0].equals("resourcePacks"))
/* 1404:     */           {
/* 1405:1853 */             this.resourcePacks = ((List)gson.fromJson(var2.substring(var2.indexOf(':') + 1), typeListString));
/* 1406:1855 */             if (this.resourcePacks == null) {
/* 1407:1857 */               this.resourcePacks = new ArrayList();
/* 1408:     */             }
/* 1409:     */           }
/* 1410:1861 */           if ((var8[0].equals("lastServer")) && (var8.length >= 2)) {
/* 1411:1863 */             this.lastServer = var2.substring(var2.indexOf(':') + 1);
/* 1412:     */           }
/* 1413:1866 */           if ((var8[0].equals("lang")) && (var8.length >= 2)) {
/* 1414:1868 */             this.language = var8[1];
/* 1415:     */           }
/* 1416:1871 */           if (var8[0].equals("chatVisibility")) {
/* 1417:1873 */             this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var8[1]));
/* 1418:     */           }
/* 1419:1876 */           if (var8[0].equals("chatColors")) {
/* 1420:1878 */             this.chatColours = var8[1].equals("true");
/* 1421:     */           }
/* 1422:1881 */           if (var8[0].equals("chatLinks")) {
/* 1423:1883 */             this.chatLinks = var8[1].equals("true");
/* 1424:     */           }
/* 1425:1886 */           if (var8[0].equals("chatLinksPrompt")) {
/* 1426:1888 */             this.chatLinksPrompt = var8[1].equals("true");
/* 1427:     */           }
/* 1428:1891 */           if (var8[0].equals("chatOpacity")) {
/* 1429:1893 */             this.chatOpacity = parseFloat(var8[1]);
/* 1430:     */           }
/* 1431:1896 */           if (var8[0].equals("serverTextures")) {
/* 1432:1898 */             this.serverTextures = var8[1].equals("true");
/* 1433:     */           }
/* 1434:1901 */           if (var8[0].equals("snooperEnabled")) {
/* 1435:1903 */             this.snooperEnabled = var8[1].equals("true");
/* 1436:     */           }
/* 1437:1906 */           if (var8[0].equals("fullscreen")) {
/* 1438:1908 */             this.fullScreen = var8[1].equals("true");
/* 1439:     */           }
/* 1440:1911 */           if (var8[0].equals("enableVsync"))
/* 1441:     */           {
/* 1442:1913 */             this.enableVsync = var8[1].equals("true");
/* 1443:1914 */             updateVSync();
/* 1444:     */           }
/* 1445:1917 */           if (var8[0].equals("hideServerAddress")) {
/* 1446:1919 */             this.hideServerAddress = var8[1].equals("true");
/* 1447:     */           }
/* 1448:1922 */           if (var8[0].equals("advancedItemTooltips")) {
/* 1449:1924 */             this.advancedItemTooltips = var8[1].equals("true");
/* 1450:     */           }
/* 1451:1927 */           if (var8[0].equals("pauseOnLostFocus")) {
/* 1452:1929 */             this.pauseOnLostFocus = var8[1].equals("true");
/* 1453:     */           }
/* 1454:1932 */           if (var8[0].equals("showCape")) {
/* 1455:1934 */             this.showCape = var8[1].equals("true");
/* 1456:     */           }
/* 1457:1937 */           if (var8[0].equals("touchscreen")) {
/* 1458:1939 */             this.touchscreen = var8[1].equals("true");
/* 1459:     */           }
/* 1460:1942 */           if (var8[0].equals("overrideHeight")) {
/* 1461:1944 */             this.overrideHeight = Integer.parseInt(var8[1]);
/* 1462:     */           }
/* 1463:1947 */           if (var8[0].equals("overrideWidth")) {
/* 1464:1949 */             this.overrideWidth = Integer.parseInt(var8[1]);
/* 1465:     */           }
/* 1466:1952 */           if (var8[0].equals("heldItemTooltips")) {
/* 1467:1954 */             this.heldItemTooltips = var8[1].equals("true");
/* 1468:     */           }
/* 1469:1957 */           if (var8[0].equals("chatHeightFocused")) {
/* 1470:1959 */             this.chatHeightFocused = parseFloat(var8[1]);
/* 1471:     */           }
/* 1472:1962 */           if (var8[0].equals("chatHeightUnfocused")) {
/* 1473:1964 */             this.chatHeightUnfocused = parseFloat(var8[1]);
/* 1474:     */           }
/* 1475:1967 */           if (var8[0].equals("chatScale")) {
/* 1476:1969 */             this.chatScale = parseFloat(var8[1]);
/* 1477:     */           }
/* 1478:1972 */           if (var8[0].equals("chatWidth")) {
/* 1479:1974 */             this.chatWidth = parseFloat(var8[1]);
/* 1480:     */           }
/* 1481:1977 */           if (var8[0].equals("showInventoryAchievementHint")) {
/* 1482:1979 */             this.showInventoryAchievementHint = var8[1].equals("true");
/* 1483:     */           }
/* 1484:1982 */           if (var8[0].equals("mipmapLevels")) {
/* 1485:1984 */             this.mipmapLevels = Integer.parseInt(var8[1]);
/* 1486:     */           }
/* 1487:1987 */           if (var8[0].equals("anisotropicFiltering")) {
/* 1488:1989 */             this.anisotropicFiltering = Integer.parseInt(var8[1]);
/* 1489:     */           }
/* 1490:1992 */           if (var8[0].equals("forceUnicodeFont")) {
/* 1491:1994 */             this.forceUnicodeFont = var8[1].equals("true");
/* 1492:     */           }
/* 1493:1997 */           KeyBinding[] var4 = this.keyBindings;
/* 1494:1998 */           int var5 = var4.length;
/* 1495:2001 */           for (int var6 = 0; var6 < var5; var6++)
/* 1496:     */           {
/* 1497:2003 */             KeyBinding var10 = var4[var6];
/* 1498:2005 */             if (var8[0].equals("key_" + var10.getKeyDescription())) {
/* 1499:2007 */               var10.setKeyCode(Integer.parseInt(var8[1]));
/* 1500:     */             }
/* 1501:     */           }
/* 1502:2011 */           SoundCategory[] var111 = SoundCategory.values();
/* 1503:2012 */           var5 = var111.length;
/* 1504:2014 */           for (var6 = 0; var6 < var5; var6++)
/* 1505:     */           {
/* 1506:2016 */             SoundCategory var11 = var111[var6];
/* 1507:2018 */             if (var8[0].equals("soundCategory_" + var11.getCategoryName())) {
/* 1508:2020 */               this.mapSoundLevels.put(var11, Float.valueOf(parseFloat(var8[1])));
/* 1509:     */             }
/* 1510:     */           }
/* 1511:     */         }
/* 1512:     */         catch (Exception var91)
/* 1513:     */         {
/* 1514:2026 */           logger.warn("Skipping bad option: " + var2);
/* 1515:2027 */           var91.printStackTrace();
/* 1516:     */         }
/* 1517:     */       }
/* 1518:2031 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 1519:2032 */       var9.close();
/* 1520:     */     }
/* 1521:     */     catch (Exception var101)
/* 1522:     */     {
/* 1523:2036 */       logger.error("Failed to load options", var101);
/* 1524:     */     }
/* 1525:2039 */     loadOfOptions();
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   private float parseFloat(String par1Str)
/* 1529:     */   {
/* 1530:2047 */     return par1Str.equals("false") ? 0.0F : par1Str.equals("true") ? 1.0F : Float.parseFloat(par1Str);
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   public void saveOptions()
/* 1534:     */   {
/* 1535:2055 */     if (Reflector.FMLClientHandler.exists())
/* 1536:     */     {
/* 1537:2057 */       Object var6 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/* 1538:2059 */       if ((var6 != null) && (Reflector.callBoolean(var6, Reflector.FMLClientHandler_isLoading, new Object[0]))) {
/* 1539:2061 */         return;
/* 1540:     */       }
/* 1541:     */     }
/* 1542:     */     try
/* 1543:     */     {
/* 1544:2067 */       PrintWriter var81 = new PrintWriter(new FileWriter(this.optionsFile));
/* 1545:2068 */       var81.println("invertYMouse:" + this.invertMouse);
/* 1546:2069 */       var81.println("mouseSensitivity:" + this.mouseSensitivity);
/* 1547:2070 */       var81.println("fov:" + this.fovSetting);
/* 1548:2071 */       var81.println("gamma:" + this.gammaSetting);
/* 1549:2072 */       var81.println("saturation:" + this.saturation);
/* 1550:2073 */       var81.println("renderDistance:" + Config.limit(this.renderDistanceChunks, 2, 16));
/* 1551:2074 */       var81.println("guiScale:" + this.guiScale);
/* 1552:2075 */       var81.println("particles:" + this.particleSetting);
/* 1553:2076 */       var81.println("bobView:" + this.viewBobbing);
/* 1554:2077 */       var81.println("anaglyph3d:" + this.anaglyph);
/* 1555:2078 */       var81.println("advancedOpengl:" + this.advancedOpengl);
/* 1556:2079 */       var81.println("maxFps:" + this.limitFramerate);
/* 1557:2080 */       var81.println("fboEnable:" + this.fboEnable);
/* 1558:2081 */       var81.println("difficulty:" + this.difficulty.getDifficultyId());
/* 1559:2082 */       var81.println("fancyGraphics:" + this.fancyGraphics);
/* 1560:2083 */       var81.println("ao:" + this.ambientOcclusion);
/* 1561:2084 */       var81.println("clouds:" + this.clouds);
/* 1562:2085 */       var81.println("resourcePacks:" + gson.toJson(this.resourcePacks));
/* 1563:2086 */       var81.println("lastServer:" + this.lastServer);
/* 1564:2087 */       var81.println("lang:" + this.language);
/* 1565:2088 */       var81.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
/* 1566:2089 */       var81.println("chatColors:" + this.chatColours);
/* 1567:2090 */       var81.println("chatLinks:" + this.chatLinks);
/* 1568:2091 */       var81.println("chatLinksPrompt:" + this.chatLinksPrompt);
/* 1569:2092 */       var81.println("chatOpacity:" + this.chatOpacity);
/* 1570:2093 */       var81.println("serverTextures:" + this.serverTextures);
/* 1571:2094 */       var81.println("snooperEnabled:" + this.snooperEnabled);
/* 1572:2095 */       var81.println("fullscreen:" + this.fullScreen);
/* 1573:2096 */       var81.println("enableVsync:" + this.enableVsync);
/* 1574:2097 */       var81.println("hideServerAddress:" + this.hideServerAddress);
/* 1575:2098 */       var81.println("advancedItemTooltips:" + this.advancedItemTooltips);
/* 1576:2099 */       var81.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
/* 1577:2100 */       var81.println("showCape:" + this.showCape);
/* 1578:2101 */       var81.println("touchscreen:" + this.touchscreen);
/* 1579:2102 */       var81.println("overrideWidth:" + this.overrideWidth);
/* 1580:2103 */       var81.println("overrideHeight:" + this.overrideHeight);
/* 1581:2104 */       var81.println("heldItemTooltips:" + this.heldItemTooltips);
/* 1582:2105 */       var81.println("chatHeightFocused:" + this.chatHeightFocused);
/* 1583:2106 */       var81.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
/* 1584:2107 */       var81.println("chatScale:" + this.chatScale);
/* 1585:2108 */       var81.println("chatWidth:" + this.chatWidth);
/* 1586:2109 */       var81.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
/* 1587:2110 */       var81.println("mipmapLevels:" + this.mipmapLevels);
/* 1588:2111 */       var81.println("anisotropicFiltering:" + this.anisotropicFiltering);
/* 1589:2112 */       var81.println("forceUnicodeFont:" + this.forceUnicodeFont);
/* 1590:2113 */       KeyBinding[] var2 = this.keyBindings;
/* 1591:2114 */       int var3 = var2.length;
/* 1592:2117 */       for (int var4 = 0; var4 < var3; var4++)
/* 1593:     */       {
/* 1594:2119 */         KeyBinding var7 = var2[var4];
/* 1595:2120 */         var81.println("key_" + var7.getKeyDescription() + ":" + var7.getKeyCode());
/* 1596:     */       }
/* 1597:2123 */       SoundCategory[] var9 = SoundCategory.values();
/* 1598:2124 */       var3 = var9.length;
/* 1599:2126 */       for (var4 = 0; var4 < var3; var4++)
/* 1600:     */       {
/* 1601:2128 */         SoundCategory var8 = var9[var4];
/* 1602:2129 */         var81.println("soundCategory_" + var8.getCategoryName() + ":" + getSoundLevel(var8));
/* 1603:     */       }
/* 1604:2132 */       var81.close();
/* 1605:     */     }
/* 1606:     */     catch (Exception var71)
/* 1607:     */     {
/* 1608:2136 */       logger.error("Failed to save options", var71);
/* 1609:     */     }
/* 1610:2139 */     saveOfOptions();
/* 1611:2140 */     sendSettingsToServer();
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public float getSoundLevel(SoundCategory p_151438_1_)
/* 1615:     */   {
/* 1616:2145 */     return this.mapSoundLevels.containsKey(p_151438_1_) ? ((Float)this.mapSoundLevels.get(p_151438_1_)).floatValue() : 1.0F;
/* 1617:     */   }
/* 1618:     */   
/* 1619:     */   public void setSoundLevel(SoundCategory p_151439_1_, float p_151439_2_)
/* 1620:     */   {
/* 1621:2150 */     this.mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
/* 1622:2151 */     this.mapSoundLevels.put(p_151439_1_, Float.valueOf(p_151439_2_));
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public void sendSettingsToServer()
/* 1626:     */   {
/* 1627:2159 */     if (this.mc.thePlayer != null) {
/* 1628:2161 */       this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, this.difficulty, this.showCape));
/* 1629:     */     }
/* 1630:     */   }
/* 1631:     */   
/* 1632:     */   public boolean shouldRenderClouds()
/* 1633:     */   {
/* 1634:2170 */     return (this.renderDistanceChunks >= 4) && (this.clouds);
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public void loadOfOptions()
/* 1638:     */   {
/* 1639:     */     try
/* 1640:     */     {
/* 1641:2177 */       File exception = this.optionsFileOF;
/* 1642:2179 */       if (!exception.exists()) {
/* 1643:2181 */         exception = this.optionsFile;
/* 1644:     */       }
/* 1645:2184 */       if (!exception.exists()) {
/* 1646:2186 */         return;
/* 1647:     */       }
/* 1648:2189 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
/* 1649:2190 */       String s = "";
/* 1650:2192 */       while ((s = bufferedreader.readLine()) != null) {
/* 1651:     */         try
/* 1652:     */         {
/* 1653:2196 */           String[] exception1 = s.split(":");
/* 1654:2198 */           if ((exception1[0].equals("ofRenderDistanceChunks")) && (exception1.length >= 2))
/* 1655:     */           {
/* 1656:2200 */             this.renderDistanceChunks = Integer.valueOf(exception1[1]).intValue();
/* 1657:2201 */             this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
/* 1658:     */           }
/* 1659:2204 */           if ((exception1[0].equals("ofFogType")) && (exception1.length >= 2))
/* 1660:     */           {
/* 1661:2206 */             this.ofFogType = Integer.valueOf(exception1[1]).intValue();
/* 1662:2207 */             this.ofFogType = Config.limit(this.ofFogType, 1, 3);
/* 1663:     */           }
/* 1664:2210 */           if ((exception1[0].equals("ofFogStart")) && (exception1.length >= 2))
/* 1665:     */           {
/* 1666:2212 */             this.ofFogStart = Float.valueOf(exception1[1]).floatValue();
/* 1667:2214 */             if (this.ofFogStart < 0.2F) {
/* 1668:2216 */               this.ofFogStart = 0.2F;
/* 1669:     */             }
/* 1670:2219 */             if (this.ofFogStart > 0.81F) {
/* 1671:2221 */               this.ofFogStart = 0.8F;
/* 1672:     */             }
/* 1673:     */           }
/* 1674:2225 */           if ((exception1[0].equals("ofMipmapType")) && (exception1.length >= 2))
/* 1675:     */           {
/* 1676:2227 */             this.ofMipmapType = Integer.valueOf(exception1[1]).intValue();
/* 1677:2228 */             this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
/* 1678:     */           }
/* 1679:2231 */           if ((exception1[0].equals("ofLoadFar")) && (exception1.length >= 2)) {
/* 1680:2233 */             this.ofLoadFar = Boolean.valueOf(exception1[1]).booleanValue();
/* 1681:     */           }
/* 1682:2236 */           if ((exception1[0].equals("ofPreloadedChunks")) && (exception1.length >= 2))
/* 1683:     */           {
/* 1684:2238 */             this.ofPreloadedChunks = Integer.valueOf(exception1[1]).intValue();
/* 1685:2240 */             if (this.ofPreloadedChunks < 0) {
/* 1686:2242 */               this.ofPreloadedChunks = 0;
/* 1687:     */             }
/* 1688:2245 */             if (this.ofPreloadedChunks > 8) {
/* 1689:2247 */               this.ofPreloadedChunks = 8;
/* 1690:     */             }
/* 1691:     */           }
/* 1692:2251 */           if ((exception1[0].equals("ofOcclusionFancy")) && (exception1.length >= 2)) {
/* 1693:2253 */             this.ofOcclusionFancy = Boolean.valueOf(exception1[1]).booleanValue();
/* 1694:     */           }
/* 1695:2256 */           if ((exception1[0].equals("ofSmoothFps")) && (exception1.length >= 2)) {
/* 1696:2258 */             this.ofSmoothFps = Boolean.valueOf(exception1[1]).booleanValue();
/* 1697:     */           }
/* 1698:2261 */           if ((exception1[0].equals("ofSmoothWorld")) && (exception1.length >= 2)) {
/* 1699:2263 */             this.ofSmoothWorld = Boolean.valueOf(exception1[1]).booleanValue();
/* 1700:     */           }
/* 1701:2266 */           if ((exception1[0].equals("ofAoLevel")) && (exception1.length >= 2))
/* 1702:     */           {
/* 1703:2268 */             this.ofAoLevel = Float.valueOf(exception1[1]).floatValue();
/* 1704:2269 */             this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
/* 1705:     */           }
/* 1706:2272 */           if ((exception1[0].equals("ofClouds")) && (exception1.length >= 2))
/* 1707:     */           {
/* 1708:2274 */             this.ofClouds = Integer.valueOf(exception1[1]).intValue();
/* 1709:2275 */             this.ofClouds = Config.limit(this.ofClouds, 0, 3);
/* 1710:     */           }
/* 1711:2278 */           if ((exception1[0].equals("ofCloudsHeight")) && (exception1.length >= 2))
/* 1712:     */           {
/* 1713:2280 */             this.ofCloudsHeight = Float.valueOf(exception1[1]).floatValue();
/* 1714:2281 */             this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
/* 1715:     */           }
/* 1716:2284 */           if ((exception1[0].equals("ofTrees")) && (exception1.length >= 2))
/* 1717:     */           {
/* 1718:2286 */             this.ofTrees = Integer.valueOf(exception1[1]).intValue();
/* 1719:2287 */             this.ofTrees = Config.limit(this.ofTrees, 0, 2);
/* 1720:     */           }
/* 1721:2290 */           if ((exception1[0].equals("ofGrass")) && (exception1.length >= 2))
/* 1722:     */           {
/* 1723:2292 */             this.ofGrass = Integer.valueOf(exception1[1]).intValue();
/* 1724:2293 */             this.ofGrass = Config.limit(this.ofGrass, 0, 2);
/* 1725:     */           }
/* 1726:2296 */           if ((exception1[0].equals("ofDroppedItems")) && (exception1.length >= 2))
/* 1727:     */           {
/* 1728:2298 */             this.ofDroppedItems = Integer.valueOf(exception1[1]).intValue();
/* 1729:2299 */             this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
/* 1730:     */           }
/* 1731:2302 */           if ((exception1[0].equals("ofRain")) && (exception1.length >= 2))
/* 1732:     */           {
/* 1733:2304 */             this.ofRain = Integer.valueOf(exception1[1]).intValue();
/* 1734:2305 */             this.ofRain = Config.limit(this.ofRain, 0, 3);
/* 1735:     */           }
/* 1736:2308 */           if ((exception1[0].equals("ofWater")) && (exception1.length >= 2))
/* 1737:     */           {
/* 1738:2310 */             this.ofWater = Integer.valueOf(exception1[1]).intValue();
/* 1739:2311 */             this.ofWater = Config.limit(this.ofWater, 0, 3);
/* 1740:     */           }
/* 1741:2314 */           if ((exception1[0].equals("ofAnimatedWater")) && (exception1.length >= 2))
/* 1742:     */           {
/* 1743:2316 */             this.ofAnimatedWater = Integer.valueOf(exception1[1]).intValue();
/* 1744:2317 */             this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
/* 1745:     */           }
/* 1746:2320 */           if ((exception1[0].equals("ofAnimatedLava")) && (exception1.length >= 2))
/* 1747:     */           {
/* 1748:2322 */             this.ofAnimatedLava = Integer.valueOf(exception1[1]).intValue();
/* 1749:2323 */             this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
/* 1750:     */           }
/* 1751:2326 */           if ((exception1[0].equals("ofAnimatedFire")) && (exception1.length >= 2)) {
/* 1752:2328 */             this.ofAnimatedFire = Boolean.valueOf(exception1[1]).booleanValue();
/* 1753:     */           }
/* 1754:2331 */           if ((exception1[0].equals("ofAnimatedPortal")) && (exception1.length >= 2)) {
/* 1755:2333 */             this.ofAnimatedPortal = Boolean.valueOf(exception1[1]).booleanValue();
/* 1756:     */           }
/* 1757:2336 */           if ((exception1[0].equals("ofAnimatedRedstone")) && (exception1.length >= 2)) {
/* 1758:2338 */             this.ofAnimatedRedstone = Boolean.valueOf(exception1[1]).booleanValue();
/* 1759:     */           }
/* 1760:2341 */           if ((exception1[0].equals("ofAnimatedExplosion")) && (exception1.length >= 2)) {
/* 1761:2343 */             this.ofAnimatedExplosion = Boolean.valueOf(exception1[1]).booleanValue();
/* 1762:     */           }
/* 1763:2346 */           if ((exception1[0].equals("ofAnimatedFlame")) && (exception1.length >= 2)) {
/* 1764:2348 */             this.ofAnimatedFlame = Boolean.valueOf(exception1[1]).booleanValue();
/* 1765:     */           }
/* 1766:2351 */           if ((exception1[0].equals("ofAnimatedSmoke")) && (exception1.length >= 2)) {
/* 1767:2353 */             this.ofAnimatedSmoke = Boolean.valueOf(exception1[1]).booleanValue();
/* 1768:     */           }
/* 1769:2356 */           if ((exception1[0].equals("ofVoidParticles")) && (exception1.length >= 2)) {
/* 1770:2358 */             this.ofVoidParticles = Boolean.valueOf(exception1[1]).booleanValue();
/* 1771:     */           }
/* 1772:2361 */           if ((exception1[0].equals("ofWaterParticles")) && (exception1.length >= 2)) {
/* 1773:2363 */             this.ofWaterParticles = Boolean.valueOf(exception1[1]).booleanValue();
/* 1774:     */           }
/* 1775:2366 */           if ((exception1[0].equals("ofPortalParticles")) && (exception1.length >= 2)) {
/* 1776:2368 */             this.ofPortalParticles = Boolean.valueOf(exception1[1]).booleanValue();
/* 1777:     */           }
/* 1778:2371 */           if ((exception1[0].equals("ofPotionParticles")) && (exception1.length >= 2)) {
/* 1779:2373 */             this.ofPotionParticles = Boolean.valueOf(exception1[1]).booleanValue();
/* 1780:     */           }
/* 1781:2376 */           if ((exception1[0].equals("ofDrippingWaterLava")) && (exception1.length >= 2)) {
/* 1782:2378 */             this.ofDrippingWaterLava = Boolean.valueOf(exception1[1]).booleanValue();
/* 1783:     */           }
/* 1784:2381 */           if ((exception1[0].equals("ofAnimatedTerrain")) && (exception1.length >= 2)) {
/* 1785:2383 */             this.ofAnimatedTerrain = Boolean.valueOf(exception1[1]).booleanValue();
/* 1786:     */           }
/* 1787:2386 */           if ((exception1[0].equals("ofAnimatedTextures")) && (exception1.length >= 2)) {
/* 1788:2388 */             this.ofAnimatedTextures = Boolean.valueOf(exception1[1]).booleanValue();
/* 1789:     */           }
/* 1790:2391 */           if ((exception1[0].equals("ofAnimatedItems")) && (exception1.length >= 2)) {
/* 1791:2393 */             this.ofAnimatedItems = Boolean.valueOf(exception1[1]).booleanValue();
/* 1792:     */           }
/* 1793:2396 */           if ((exception1[0].equals("ofRainSplash")) && (exception1.length >= 2)) {
/* 1794:2398 */             this.ofRainSplash = Boolean.valueOf(exception1[1]).booleanValue();
/* 1795:     */           }
/* 1796:2401 */           if ((exception1[0].equals("ofLagometer")) && (exception1.length >= 2)) {
/* 1797:2403 */             this.ofLagometer = Boolean.valueOf(exception1[1]).booleanValue();
/* 1798:     */           }
/* 1799:2406 */           if ((exception1[0].equals("ofAutoSaveTicks")) && (exception1.length >= 2))
/* 1800:     */           {
/* 1801:2408 */             this.ofAutoSaveTicks = Integer.valueOf(exception1[1]).intValue();
/* 1802:2409 */             this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
/* 1803:     */           }
/* 1804:2412 */           if ((exception1[0].equals("ofBetterGrass")) && (exception1.length >= 2))
/* 1805:     */           {
/* 1806:2414 */             this.ofBetterGrass = Integer.valueOf(exception1[1]).intValue();
/* 1807:2415 */             this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
/* 1808:     */           }
/* 1809:2418 */           if ((exception1[0].equals("ofConnectedTextures")) && (exception1.length >= 2))
/* 1810:     */           {
/* 1811:2420 */             this.ofConnectedTextures = Integer.valueOf(exception1[1]).intValue();
/* 1812:2421 */             this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
/* 1813:     */           }
/* 1814:2424 */           if ((exception1[0].equals("ofWeather")) && (exception1.length >= 2)) {
/* 1815:2426 */             this.ofWeather = Boolean.valueOf(exception1[1]).booleanValue();
/* 1816:     */           }
/* 1817:2429 */           if ((exception1[0].equals("ofSky")) && (exception1.length >= 2)) {
/* 1818:2431 */             this.ofSky = Boolean.valueOf(exception1[1]).booleanValue();
/* 1819:     */           }
/* 1820:2434 */           if ((exception1[0].equals("ofStars")) && (exception1.length >= 2)) {
/* 1821:2436 */             this.ofStars = Boolean.valueOf(exception1[1]).booleanValue();
/* 1822:     */           }
/* 1823:2439 */           if ((exception1[0].equals("ofSunMoon")) && (exception1.length >= 2)) {
/* 1824:2441 */             this.ofSunMoon = Boolean.valueOf(exception1[1]).booleanValue();
/* 1825:     */           }
/* 1826:2444 */           if ((exception1[0].equals("ofChunkUpdates")) && (exception1.length >= 2))
/* 1827:     */           {
/* 1828:2446 */             this.ofChunkUpdates = Integer.valueOf(exception1[1]).intValue();
/* 1829:2447 */             this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
/* 1830:     */           }
/* 1831:2450 */           if ((exception1[0].equals("ofChunkLoading")) && (exception1.length >= 2))
/* 1832:     */           {
/* 1833:2452 */             this.ofChunkLoading = Integer.valueOf(exception1[1]).intValue();
/* 1834:2453 */             this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
/* 1835:2454 */             updateChunkLoading();
/* 1836:     */           }
/* 1837:2457 */           if ((exception1[0].equals("ofChunkUpdatesDynamic")) && (exception1.length >= 2)) {
/* 1838:2459 */             this.ofChunkUpdatesDynamic = Boolean.valueOf(exception1[1]).booleanValue();
/* 1839:     */           }
/* 1840:2462 */           if ((exception1[0].equals("ofTime")) && (exception1.length >= 2))
/* 1841:     */           {
/* 1842:2464 */             this.ofTime = Integer.valueOf(exception1[1]).intValue();
/* 1843:2465 */             this.ofTime = Config.limit(this.ofTime, 0, 3);
/* 1844:     */           }
/* 1845:2468 */           if ((exception1[0].equals("ofClearWater")) && (exception1.length >= 2))
/* 1846:     */           {
/* 1847:2470 */             this.ofClearWater = Boolean.valueOf(exception1[1]).booleanValue();
/* 1848:2471 */             updateWaterOpacity();
/* 1849:     */           }
/* 1850:2474 */           if ((exception1[0].equals("ofDepthFog")) && (exception1.length >= 2)) {
/* 1851:2476 */             this.ofDepthFog = Boolean.valueOf(exception1[1]).booleanValue();
/* 1852:     */           }
/* 1853:2479 */           if ((exception1[0].equals("ofAaLevel")) && (exception1.length >= 2))
/* 1854:     */           {
/* 1855:2481 */             this.ofAaLevel = Integer.valueOf(exception1[1]).intValue();
/* 1856:2482 */             this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/* 1857:     */           }
/* 1858:2485 */           if ((exception1[0].equals("ofProfiler")) && (exception1.length >= 2)) {
/* 1859:2487 */             this.ofProfiler = Boolean.valueOf(exception1[1]).booleanValue();
/* 1860:     */           }
/* 1861:2490 */           if ((exception1[0].equals("ofBetterSnow")) && (exception1.length >= 2)) {
/* 1862:2492 */             this.ofBetterSnow = Boolean.valueOf(exception1[1]).booleanValue();
/* 1863:     */           }
/* 1864:2495 */           if ((exception1[0].equals("ofSwampColors")) && (exception1.length >= 2)) {
/* 1865:2497 */             this.ofSwampColors = Boolean.valueOf(exception1[1]).booleanValue();
/* 1866:     */           }
/* 1867:2500 */           if ((exception1[0].equals("ofRandomMobs")) && (exception1.length >= 2)) {
/* 1868:2502 */             this.ofRandomMobs = Boolean.valueOf(exception1[1]).booleanValue();
/* 1869:     */           }
/* 1870:2505 */           if ((exception1[0].equals("ofSmoothBiomes")) && (exception1.length >= 2)) {
/* 1871:2507 */             this.ofSmoothBiomes = Boolean.valueOf(exception1[1]).booleanValue();
/* 1872:     */           }
/* 1873:2510 */           if ((exception1[0].equals("ofCustomFonts")) && (exception1.length >= 2)) {
/* 1874:2512 */             this.ofCustomFonts = Boolean.valueOf(exception1[1]).booleanValue();
/* 1875:     */           }
/* 1876:2515 */           if ((exception1[0].equals("ofCustomColors")) && (exception1.length >= 2)) {
/* 1877:2517 */             this.ofCustomColors = Boolean.valueOf(exception1[1]).booleanValue();
/* 1878:     */           }
/* 1879:2520 */           if ((exception1[0].equals("ofCustomSky")) && (exception1.length >= 2)) {
/* 1880:2522 */             this.ofCustomSky = Boolean.valueOf(exception1[1]).booleanValue();
/* 1881:     */           }
/* 1882:2525 */           if ((exception1[0].equals("ofShowCapes")) && (exception1.length >= 2)) {
/* 1883:2527 */             this.ofShowCapes = Boolean.valueOf(exception1[1]).booleanValue();
/* 1884:     */           }
/* 1885:2530 */           if ((exception1[0].equals("ofNaturalTextures")) && (exception1.length >= 2)) {
/* 1886:2532 */             this.ofNaturalTextures = Boolean.valueOf(exception1[1]).booleanValue();
/* 1887:     */           }
/* 1888:2535 */           if ((exception1[0].equals("ofLazyChunkLoading")) && (exception1.length >= 2)) {
/* 1889:2537 */             this.ofLazyChunkLoading = Boolean.valueOf(exception1[1]).booleanValue();
/* 1890:     */           }
/* 1891:2540 */           if ((exception1[0].equals("ofFullscreenMode")) && (exception1.length >= 2)) {
/* 1892:2542 */             this.ofFullscreenMode = exception1[1];
/* 1893:     */           }
/* 1894:2545 */           if ((exception1[0].equals("ofFastMath")) && (exception1.length >= 2))
/* 1895:     */           {
/* 1896:2547 */             this.ofFastMath = Boolean.valueOf(exception1[1]).booleanValue();
/* 1897:2548 */             MathHelper.fastMath = this.ofFastMath;
/* 1898:     */           }
/* 1899:2551 */           if ((exception1[0].equals("ofFastRender")) && (exception1.length >= 2)) {
/* 1900:2553 */             this.ofFastRender = Boolean.valueOf(exception1[1]).booleanValue();
/* 1901:     */           }
/* 1902:2556 */           if ((exception1[0].equals("ofTranslucentBlocks")) && (exception1.length >= 2))
/* 1903:     */           {
/* 1904:2558 */             this.ofTranslucentBlocks = Integer.valueOf(exception1[1]).intValue();
/* 1905:2559 */             this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 1, 2);
/* 1906:     */           }
/* 1907:     */         }
/* 1908:     */         catch (Exception var5)
/* 1909:     */         {
/* 1910:2564 */           Config.dbg("Skipping bad option: " + s);
/* 1911:2565 */           var5.printStackTrace();
/* 1912:     */         }
/* 1913:     */       }
/* 1914:2569 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 1915:2570 */       bufferedreader.close();
/* 1916:     */     }
/* 1917:     */     catch (Exception var6)
/* 1918:     */     {
/* 1919:2574 */       Config.warn("Failed to load options");
/* 1920:2575 */       var6.printStackTrace();
/* 1921:     */     }
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public void saveOfOptions()
/* 1925:     */   {
/* 1926:     */     try
/* 1927:     */     {
/* 1928:2583 */       PrintWriter exception = new PrintWriter(new FileWriter(this.optionsFileOF));
/* 1929:2584 */       exception.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
/* 1930:2585 */       exception.println("ofFogType:" + this.ofFogType);
/* 1931:2586 */       exception.println("ofFogStart:" + this.ofFogStart);
/* 1932:2587 */       exception.println("ofMipmapType:" + this.ofMipmapType);
/* 1933:2588 */       exception.println("ofLoadFar:" + this.ofLoadFar);
/* 1934:2589 */       exception.println("ofPreloadedChunks:" + this.ofPreloadedChunks);
/* 1935:2590 */       exception.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
/* 1936:2591 */       exception.println("ofSmoothFps:" + this.ofSmoothFps);
/* 1937:2592 */       exception.println("ofSmoothWorld:" + this.ofSmoothWorld);
/* 1938:2593 */       exception.println("ofAoLevel:" + this.ofAoLevel);
/* 1939:2594 */       exception.println("ofClouds:" + this.ofClouds);
/* 1940:2595 */       exception.println("ofCloudsHeight:" + this.ofCloudsHeight);
/* 1941:2596 */       exception.println("ofTrees:" + this.ofTrees);
/* 1942:2597 */       exception.println("ofGrass:" + this.ofGrass);
/* 1943:2598 */       exception.println("ofDroppedItems:" + this.ofDroppedItems);
/* 1944:2599 */       exception.println("ofRain:" + this.ofRain);
/* 1945:2600 */       exception.println("ofWater:" + this.ofWater);
/* 1946:2601 */       exception.println("ofAnimatedWater:" + this.ofAnimatedWater);
/* 1947:2602 */       exception.println("ofAnimatedLava:" + this.ofAnimatedLava);
/* 1948:2603 */       exception.println("ofAnimatedFire:" + this.ofAnimatedFire);
/* 1949:2604 */       exception.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
/* 1950:2605 */       exception.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
/* 1951:2606 */       exception.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
/* 1952:2607 */       exception.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
/* 1953:2608 */       exception.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
/* 1954:2609 */       exception.println("ofVoidParticles:" + this.ofVoidParticles);
/* 1955:2610 */       exception.println("ofWaterParticles:" + this.ofWaterParticles);
/* 1956:2611 */       exception.println("ofPortalParticles:" + this.ofPortalParticles);
/* 1957:2612 */       exception.println("ofPotionParticles:" + this.ofPotionParticles);
/* 1958:2613 */       exception.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
/* 1959:2614 */       exception.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
/* 1960:2615 */       exception.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
/* 1961:2616 */       exception.println("ofAnimatedItems:" + this.ofAnimatedItems);
/* 1962:2617 */       exception.println("ofRainSplash:" + this.ofRainSplash);
/* 1963:2618 */       exception.println("ofLagometer:" + this.ofLagometer);
/* 1964:2619 */       exception.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
/* 1965:2620 */       exception.println("ofBetterGrass:" + this.ofBetterGrass);
/* 1966:2621 */       exception.println("ofConnectedTextures:" + this.ofConnectedTextures);
/* 1967:2622 */       exception.println("ofWeather:" + this.ofWeather);
/* 1968:2623 */       exception.println("ofSky:" + this.ofSky);
/* 1969:2624 */       exception.println("ofStars:" + this.ofStars);
/* 1970:2625 */       exception.println("ofSunMoon:" + this.ofSunMoon);
/* 1971:2626 */       exception.println("ofChunkUpdates:" + this.ofChunkUpdates);
/* 1972:2627 */       exception.println("ofChunkLoading:" + this.ofChunkLoading);
/* 1973:2628 */       exception.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
/* 1974:2629 */       exception.println("ofTime:" + this.ofTime);
/* 1975:2630 */       exception.println("ofClearWater:" + this.ofClearWater);
/* 1976:2631 */       exception.println("ofDepthFog:" + this.ofDepthFog);
/* 1977:2632 */       exception.println("ofAaLevel:" + this.ofAaLevel);
/* 1978:2633 */       exception.println("ofProfiler:" + this.ofProfiler);
/* 1979:2634 */       exception.println("ofBetterSnow:" + this.ofBetterSnow);
/* 1980:2635 */       exception.println("ofSwampColors:" + this.ofSwampColors);
/* 1981:2636 */       exception.println("ofRandomMobs:" + this.ofRandomMobs);
/* 1982:2637 */       exception.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
/* 1983:2638 */       exception.println("ofCustomFonts:" + this.ofCustomFonts);
/* 1984:2639 */       exception.println("ofCustomColors:" + this.ofCustomColors);
/* 1985:2640 */       exception.println("ofCustomSky:" + this.ofCustomSky);
/* 1986:2641 */       exception.println("ofShowCapes:" + this.ofShowCapes);
/* 1987:2642 */       exception.println("ofNaturalTextures:" + this.ofNaturalTextures);
/* 1988:2643 */       exception.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
/* 1989:2644 */       exception.println("ofFullscreenMode:" + this.ofFullscreenMode);
/* 1990:2645 */       exception.println("ofFastMath:" + this.ofFastMath);
/* 1991:2646 */       exception.println("ofFastRender:" + this.ofFastRender);
/* 1992:2647 */       exception.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
/* 1993:2648 */       exception.close();
/* 1994:     */     }
/* 1995:     */     catch (Exception var2)
/* 1996:     */     {
/* 1997:2652 */       Config.warn("Failed to save options");
/* 1998:2653 */       var2.printStackTrace();
/* 1999:     */     }
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   public void resetSettings()
/* 2003:     */   {
/* 2004:2659 */     this.renderDistanceChunks = 8;
/* 2005:2660 */     this.viewBobbing = true;
/* 2006:2661 */     this.anaglyph = false;
/* 2007:2662 */     this.advancedOpengl = false;
/* 2008:2663 */     this.limitFramerate = ((int)Options.FRAMERATE_LIMIT.getValueMax());
/* 2009:2664 */     this.enableVsync = false;
/* 2010:2665 */     updateVSync();
/* 2011:2666 */     this.mipmapLevels = 4;
/* 2012:2667 */     this.anisotropicFiltering = 1;
/* 2013:2668 */     this.fancyGraphics = true;
/* 2014:2669 */     this.ambientOcclusion = 2;
/* 2015:2670 */     this.clouds = true;
/* 2016:2671 */     this.fovSetting = 0.0F;
/* 2017:2672 */     this.gammaSetting = 0.0F;
/* 2018:2673 */     this.guiScale = 0;
/* 2019:2674 */     this.particleSetting = 0;
/* 2020:2675 */     this.heldItemTooltips = true;
/* 2021:2676 */     this.ofFogType = 1;
/* 2022:2677 */     this.ofFogStart = 0.8F;
/* 2023:2678 */     this.ofMipmapType = 0;
/* 2024:2679 */     this.ofLoadFar = false;
/* 2025:2680 */     this.ofPreloadedChunks = 0;
/* 2026:2681 */     this.ofOcclusionFancy = false;
/* 2027:2682 */     this.ofSmoothFps = false;
/* 2028:2683 */     Config.updateAvailableProcessors();
/* 2029:2684 */     this.ofSmoothWorld = Config.isSingleProcessor();
/* 2030:2685 */     this.ofLazyChunkLoading = Config.isSingleProcessor();
/* 2031:2686 */     this.ofFastMath = false;
/* 2032:2687 */     this.ofFastRender = true;
/* 2033:2688 */     this.ofTranslucentBlocks = 2;
/* 2034:2689 */     this.ofAoLevel = 1.0F;
/* 2035:2690 */     this.ofAaLevel = 0;
/* 2036:2691 */     this.ofClouds = 0;
/* 2037:2692 */     this.ofCloudsHeight = 0.0F;
/* 2038:2693 */     this.ofTrees = 0;
/* 2039:2694 */     this.ofGrass = 0;
/* 2040:2695 */     this.ofRain = 0;
/* 2041:2696 */     this.ofWater = 0;
/* 2042:2697 */     this.ofBetterGrass = 3;
/* 2043:2698 */     this.ofAutoSaveTicks = 4000;
/* 2044:2699 */     this.ofLagometer = false;
/* 2045:2700 */     this.ofProfiler = false;
/* 2046:2701 */     this.ofWeather = true;
/* 2047:2702 */     this.ofSky = true;
/* 2048:2703 */     this.ofStars = true;
/* 2049:2704 */     this.ofSunMoon = true;
/* 2050:2705 */     this.ofChunkUpdates = 1;
/* 2051:2706 */     this.ofChunkLoading = 0;
/* 2052:2707 */     this.ofChunkUpdatesDynamic = false;
/* 2053:2708 */     this.ofTime = 0;
/* 2054:2709 */     this.ofClearWater = false;
/* 2055:2710 */     this.ofDepthFog = true;
/* 2056:2711 */     this.ofBetterSnow = false;
/* 2057:2712 */     this.ofFullscreenMode = "Default";
/* 2058:2713 */     this.ofSwampColors = true;
/* 2059:2714 */     this.ofRandomMobs = true;
/* 2060:2715 */     this.ofSmoothBiomes = true;
/* 2061:2716 */     this.ofCustomFonts = true;
/* 2062:2717 */     this.ofCustomColors = true;
/* 2063:2718 */     this.ofCustomSky = true;
/* 2064:2719 */     this.ofShowCapes = true;
/* 2065:2720 */     this.ofConnectedTextures = 2;
/* 2066:2721 */     this.ofNaturalTextures = false;
/* 2067:2722 */     this.ofAnimatedWater = 0;
/* 2068:2723 */     this.ofAnimatedLava = 0;
/* 2069:2724 */     this.ofAnimatedFire = true;
/* 2070:2725 */     this.ofAnimatedPortal = true;
/* 2071:2726 */     this.ofAnimatedRedstone = true;
/* 2072:2727 */     this.ofAnimatedExplosion = true;
/* 2073:2728 */     this.ofAnimatedFlame = true;
/* 2074:2729 */     this.ofAnimatedSmoke = true;
/* 2075:2730 */     this.ofVoidParticles = true;
/* 2076:2731 */     this.ofWaterParticles = true;
/* 2077:2732 */     this.ofRainSplash = true;
/* 2078:2733 */     this.ofPortalParticles = true;
/* 2079:2734 */     this.ofPotionParticles = true;
/* 2080:2735 */     this.ofDrippingWaterLava = true;
/* 2081:2736 */     this.ofAnimatedTerrain = true;
/* 2082:2737 */     this.ofAnimatedItems = true;
/* 2083:2738 */     this.ofAnimatedTextures = true;
/* 2084:2739 */     this.mc.renderGlobal.updateCapes();
/* 2085:2740 */     updateWaterOpacity();
/* 2086:2741 */     this.mc.renderGlobal.setAllRenderersVisible();
/* 2087:2742 */     this.mc.refreshResources();
/* 2088:2743 */     saveOptions();
/* 2089:     */   }
/* 2090:     */   
/* 2091:     */   public void updateVSync()
/* 2092:     */   {
/* 2093:2748 */     Display.setVSyncEnabled(this.enableVsync);
/* 2094:     */   }
/* 2095:     */   
/* 2096:     */   private void updateWaterOpacity()
/* 2097:     */   {
/* 2098:2753 */     if (this.mc.getIntegratedServer() != null) {
/* 2099:2755 */       Config.waterOpacityChanged = true;
/* 2100:     */     }
/* 2101:2758 */     byte opacity = 3;
/* 2102:2760 */     if (this.ofClearWater) {
/* 2103:2762 */       opacity = 1;
/* 2104:     */     }
/* 2105:2765 */     BlockUtils.setLightOpacity(Blocks.water, opacity);
/* 2106:2766 */     BlockUtils.setLightOpacity(Blocks.flowing_water, opacity);
/* 2107:2768 */     if (this.mc.theWorld != null)
/* 2108:     */     {
/* 2109:2770 */       IChunkProvider cp = this.mc.theWorld.getChunkProvider();
/* 2110:2772 */       if (cp != null)
/* 2111:     */       {
/* 2112:2774 */         for (int x = -512; x < 512; x++) {
/* 2113:2776 */           for (int z = -512; z < 512; z++) {
/* 2114:2778 */             if (cp.chunkExists(x, z))
/* 2115:     */             {
/* 2116:2780 */               Chunk c = cp.provideChunk(x, z);
/* 2117:2782 */               if ((c != null) && (!(c instanceof EmptyChunk)))
/* 2118:     */               {
/* 2119:2784 */                 ExtendedBlockStorage[] ebss = c.getBlockStorageArray();
/* 2120:2786 */                 for (int i = 0; i < ebss.length; i++)
/* 2121:     */                 {
/* 2122:2788 */                   ExtendedBlockStorage ebs = ebss[i];
/* 2123:2790 */                   if (ebs != null)
/* 2124:     */                   {
/* 2125:2792 */                     NibbleArray na = ebs.getSkylightArray();
/* 2126:2794 */                     if (na != null)
/* 2127:     */                     {
/* 2128:2796 */                       byte[] data = na.data;
/* 2129:2798 */                       for (int d = 0; d < data.length; d++) {
/* 2130:2800 */                         data[d] = 0;
/* 2131:     */                       }
/* 2132:     */                     }
/* 2133:     */                   }
/* 2134:     */                 }
/* 2135:2806 */                 c.generateSkylightMap();
/* 2136:     */               }
/* 2137:     */             }
/* 2138:     */           }
/* 2139:     */         }
/* 2140:2812 */         this.mc.renderGlobal.loadRenderers();
/* 2141:     */       }
/* 2142:     */     }
/* 2143:     */   }
/* 2144:     */   
/* 2145:     */   public void updateChunkLoading()
/* 2146:     */   {
/* 2147:2819 */     switch (this.ofChunkLoading)
/* 2148:     */     {
/* 2149:     */     case 1: 
/* 2150:2822 */       WrUpdates.setWrUpdater(new WrUpdaterSmooth());
/* 2151:2823 */       break;
/* 2152:     */     case 2: 
/* 2153:2826 */       WrUpdates.setWrUpdater(new WrUpdaterThreaded());
/* 2154:2827 */       break;
/* 2155:     */     default: 
/* 2156:2830 */       WrUpdates.setWrUpdater(null);
/* 2157:     */     }
/* 2158:2833 */     if (this.mc.renderGlobal != null) {
/* 2159:2835 */       this.mc.renderGlobal.loadRenderers();
/* 2160:     */     }
/* 2161:     */   }
/* 2162:     */   
/* 2163:     */   public void setAllAnimations(boolean flag)
/* 2164:     */   {
/* 2165:2841 */     int animVal = flag ? 0 : 2;
/* 2166:2842 */     this.ofAnimatedWater = animVal;
/* 2167:2843 */     this.ofAnimatedLava = animVal;
/* 2168:2844 */     this.ofAnimatedFire = flag;
/* 2169:2845 */     this.ofAnimatedPortal = flag;
/* 2170:2846 */     this.ofAnimatedRedstone = flag;
/* 2171:2847 */     this.ofAnimatedExplosion = flag;
/* 2172:2848 */     this.ofAnimatedFlame = flag;
/* 2173:2849 */     this.ofAnimatedSmoke = flag;
/* 2174:2850 */     this.ofVoidParticles = flag;
/* 2175:2851 */     this.ofWaterParticles = flag;
/* 2176:2852 */     this.ofRainSplash = flag;
/* 2177:2853 */     this.ofPortalParticles = flag;
/* 2178:2854 */     this.ofPotionParticles = flag;
/* 2179:2855 */     this.particleSetting = (flag ? 0 : 2);
/* 2180:2856 */     this.ofDrippingWaterLava = flag;
/* 2181:2857 */     this.ofAnimatedTerrain = flag;
/* 2182:2858 */     this.ofAnimatedItems = flag;
/* 2183:2859 */     this.ofAnimatedTextures = flag;
/* 2184:     */   }
/* 2185:     */   
/* 2186:     */   static final class SwitchOptions
/* 2187:     */   {
/* 2188:2864 */     static final int[] optionIds = new int[GameSettings.Options.values().length];
/* 2189:     */     private static final String __OBFID = "CL_00000652";
/* 2190:     */     
/* 2191:     */     static
/* 2192:     */     {
/* 2193:     */       try
/* 2194:     */       {
/* 2195:2871 */         optionIds[GameSettings.Options.INVERT_MOUSE.ordinal()] = 1;
/* 2196:     */       }
/* 2197:     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 2198:     */       try
/* 2199:     */       {
/* 2200:2880 */         optionIds[GameSettings.Options.VIEW_BOBBING.ordinal()] = 2;
/* 2201:     */       }
/* 2202:     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 2203:     */       try
/* 2204:     */       {
/* 2205:2889 */         optionIds[GameSettings.Options.ANAGLYPH.ordinal()] = 3;
/* 2206:     */       }
/* 2207:     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/* 2208:     */       try
/* 2209:     */       {
/* 2210:2898 */         optionIds[GameSettings.Options.ADVANCED_OPENGL.ordinal()] = 4;
/* 2211:     */       }
/* 2212:     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/* 2213:     */       try
/* 2214:     */       {
/* 2215:2907 */         optionIds[GameSettings.Options.FBO_ENABLE.ordinal()] = 5;
/* 2216:     */       }
/* 2217:     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/* 2218:     */       try
/* 2219:     */       {
/* 2220:2916 */         optionIds[GameSettings.Options.RENDER_CLOUDS.ordinal()] = 6;
/* 2221:     */       }
/* 2222:     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/* 2223:     */       try
/* 2224:     */       {
/* 2225:2925 */         optionIds[GameSettings.Options.CHAT_COLOR.ordinal()] = 7;
/* 2226:     */       }
/* 2227:     */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/* 2228:     */       try
/* 2229:     */       {
/* 2230:2934 */         optionIds[GameSettings.Options.CHAT_LINKS.ordinal()] = 8;
/* 2231:     */       }
/* 2232:     */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/* 2233:     */       try
/* 2234:     */       {
/* 2235:2943 */         optionIds[GameSettings.Options.CHAT_LINKS_PROMPT.ordinal()] = 9;
/* 2236:     */       }
/* 2237:     */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/* 2238:     */       try
/* 2239:     */       {
/* 2240:2952 */         optionIds[GameSettings.Options.USE_SERVER_TEXTURES.ordinal()] = 10;
/* 2241:     */       }
/* 2242:     */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/* 2243:     */       try
/* 2244:     */       {
/* 2245:2961 */         optionIds[GameSettings.Options.SNOOPER_ENABLED.ordinal()] = 11;
/* 2246:     */       }
/* 2247:     */       catch (NoSuchFieldError localNoSuchFieldError11) {}
/* 2248:     */       try
/* 2249:     */       {
/* 2250:2970 */         optionIds[GameSettings.Options.USE_FULLSCREEN.ordinal()] = 12;
/* 2251:     */       }
/* 2252:     */       catch (NoSuchFieldError localNoSuchFieldError12) {}
/* 2253:     */       try
/* 2254:     */       {
/* 2255:2979 */         optionIds[GameSettings.Options.ENABLE_VSYNC.ordinal()] = 13;
/* 2256:     */       }
/* 2257:     */       catch (NoSuchFieldError localNoSuchFieldError13) {}
/* 2258:     */       try
/* 2259:     */       {
/* 2260:2988 */         optionIds[GameSettings.Options.SHOW_CAPE.ordinal()] = 14;
/* 2261:     */       }
/* 2262:     */       catch (NoSuchFieldError localNoSuchFieldError14) {}
/* 2263:     */       try
/* 2264:     */       {
/* 2265:2997 */         optionIds[GameSettings.Options.TOUCHSCREEN.ordinal()] = 15;
/* 2266:     */       }
/* 2267:     */       catch (NoSuchFieldError localNoSuchFieldError15) {}
/* 2268:     */       try
/* 2269:     */       {
/* 2270:3006 */         optionIds[GameSettings.Options.FORCE_UNICODE_FONT.ordinal()] = 16;
/* 2271:     */       }
/* 2272:     */       catch (NoSuchFieldError localNoSuchFieldError16) {}
/* 2273:     */     }
/* 2274:     */   }
/* 2275:     */   
/* 2276:     */   public static enum Options
/* 2277:     */   {
/* 2278:3017 */     INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true),  SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false),  FOV("FOV", 2, "FOV", 2, "options.fov", true, false),  GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false),  SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false),  RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),  VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true),  ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true),  ADVANCED_OPENGL("ADVANCED_OPENGL", 8, "ADVANCED_OPENGL", 8, "options.advancedOpengl", false, true),  FRAMERATE_LIMIT("FRAMERATE_LIMIT", 9, "FRAMERATE_LIMIT", 9, "options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),  FBO_ENABLE("FBO_ENABLE", 10, "FBO_ENABLE", 10, "options.fboEnable", false, true),  DIFFICULTY("DIFFICULTY", 11, "DIFFICULTY", 11, "options.difficulty", false, false),  GRAPHICS("GRAPHICS", 12, "GRAPHICS", 12, "options.graphics", false, false),  AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 13, "AMBIENT_OCCLUSION", 13, "options.ao", false, false),  GUI_SCALE("GUI_SCALE", 14, "GUI_SCALE", 14, "options.guiScale", false, false),  RENDER_CLOUDS("RENDER_CLOUDS", 15, "RENDER_CLOUDS", 15, "options.renderClouds", false, true),  PARTICLES("PARTICLES", 16, "PARTICLES", 16, "options.particles", false, false),  CHAT_VISIBILITY("CHAT_VISIBILITY", 17, "CHAT_VISIBILITY", 17, "options.chat.visibility", false, false),  CHAT_COLOR("CHAT_COLOR", 18, "CHAT_COLOR", 18, "options.chat.color", false, true),  CHAT_LINKS("CHAT_LINKS", 19, "CHAT_LINKS", 19, "options.chat.links", false, true),  CHAT_OPACITY("CHAT_OPACITY", 20, "CHAT_OPACITY", 20, "options.chat.opacity", true, false),  CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 21, "CHAT_LINKS_PROMPT", 21, "options.chat.links.prompt", false, true),  USE_SERVER_TEXTURES("USE_SERVER_TEXTURES", 22, "USE_SERVER_TEXTURES", 22, "options.serverTextures", false, true),  SNOOPER_ENABLED("SNOOPER_ENABLED", 23, "SNOOPER_ENABLED", 23, "options.snooper", false, true),  USE_FULLSCREEN("USE_FULLSCREEN", 24, "USE_FULLSCREEN", 24, "options.fullscreen", false, true),  ENABLE_VSYNC("ENABLE_VSYNC", 25, "ENABLE_VSYNC", 25, "options.vsync", false, true),  SHOW_CAPE("SHOW_CAPE", 26, "SHOW_CAPE", 26, "options.showCape", false, true),  TOUCHSCREEN("TOUCHSCREEN", 27, "TOUCHSCREEN", 27, "options.touchscreen", false, true),  CHAT_SCALE("CHAT_SCALE", 28, "CHAT_SCALE", 28, "options.chat.scale", true, false),  CHAT_WIDTH("CHAT_WIDTH", 29, "CHAT_WIDTH", 29, "options.chat.width", true, false),  CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 30, "CHAT_HEIGHT_FOCUSED", 30, "options.chat.height.focused", true, false),  CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 31, "CHAT_HEIGHT_UNFOCUSED", 31, "options.chat.height.unfocused", true, false),  MIPMAP_LEVELS("MIPMAP_LEVELS", 32, "MIPMAP_LEVELS", 32, "options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),  ANISOTROPIC_FILTERING("ANISOTROPIC_FILTERING", 33, "ANISOTROPIC_FILTERING", 33, "options.anisotropicFiltering", true, false, 1.0F, 16.0F, 0.0F, null, null),  FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 34, "FORCE_UNICODE_FONT", 34, "options.forceUnicodeFont", false, true),  FOG_FANCY("FOG_FANCY", 35, "FOG", 101, "Fog", false, false),  FOG_START("FOG_START", 36, "", 10, "Fog Start", false, false),  MIPMAP_TYPE("MIPMAP_TYPE", 37, "", 10, "Mipmap Type", false, false),  LOAD_FAR("LOAD_FAR", 38, "", 10, "Load Far", false, false),  PRELOADED_CHUNKS("PRELOADED_CHUNKS", 39, "", 10, "Preloaded Chunks", false, false),  SMOOTH_FPS("SMOOTH_FPS", 40, "", 10, "Smooth FPS", false, false),  CLOUDS("CLOUDS", 41, "", 999, "Clouds", false, false),  CLOUD_HEIGHT("CLOUD_HEIGHT", 42, "", 999, "Cloud Height", true, false),  TREES("TREES", 43, "", 999, "Trees", false, false),  GRASS("GRASS", 44, "", 999, "Grass", false, false),  RAIN("RAIN", 45, "", 999, "Rain & Snow", false, false),  WATER("WATER", 46, "", 999, "Water", false, false),  ANIMATED_WATER("ANIMATED_WATER", 47, "", 999, "Water Animated", false, false),  ANIMATED_LAVA("ANIMATED_LAVA", 48, "", 999, "Lava Animated", false, false),  ANIMATED_FIRE("ANIMATED_FIRE", 49, "", 999, "Fire Animated", false, false),  ANIMATED_PORTAL("ANIMATED_PORTAL", 50, "", 999, "Portal Animated", false, false),  AO_LEVEL("AO_LEVEL", 51, "", 999, "Smooth Lighting Level", true, false),  LAGOMETER("LAGOMETER", 52, "", 999, "Lagometer", false, false),  AUTOSAVE_TICKS("AUTOSAVE_TICKS", 53, "", 999, "Autosave", false, false),  BETTER_GRASS("BETTER_GRASS", 54, "", 999, "Better Grass", false, false),  ANIMATED_REDSTONE("ANIMATED_REDSTONE", 55, "", 999, "Redstone Animated", false, false),  ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 56, "", 999, "Explosion Animated", false, false),  ANIMATED_FLAME("ANIMATED_FLAME", 57, "", 999, "Flame Animated", false, false),  ANIMATED_SMOKE("ANIMATED_SMOKE", 58, "", 999, "Smoke Animated", false, false),  WEATHER("WEATHER", 59, "", 999, "Weather", false, false),  SKY("SKY", 60, "", 999, "Sky", false, false),  STARS("STARS", 61, "", 999, "Stars", false, false),  SUN_MOON("SUN_MOON", 62, "", 999, "Sun & Moon", false, false),  CHUNK_UPDATES("CHUNK_UPDATES", 63, "", 999, "Chunk Updates", false, false),  CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 64, "", 999, "Dynamic Updates", false, false),  TIME("TIME", 65, "", 999, "Time", false, false),  CLEAR_WATER("CLEAR_WATER", 66, "", 999, "Clear Water", false, false),  SMOOTH_WORLD("SMOOTH_WORLD", 67, "", 999, "Smooth World", false, false),  DEPTH_FOG("DEPTH_FOG", 68, "", 999, "Depth Fog", false, false),  VOID_PARTICLES("VOID_PARTICLES", 69, "", 999, "Void Particles", false, false),  WATER_PARTICLES("WATER_PARTICLES", 70, "", 999, "Water Particles", false, false),  RAIN_SPLASH("RAIN_SPLASH", 71, "", 999, "Rain Splash", false, false),  PORTAL_PARTICLES("PORTAL_PARTICLES", 72, "", 999, "Portal Particles", false, false),  POTION_PARTICLES("POTION_PARTICLES", 73, "", 999, "Potion Particles", false, false),  PROFILER("PROFILER", 74, "", 999, "Debug Profiler", false, false),  DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 75, "", 999, "Dripping Water/Lava", false, false),  BETTER_SNOW("BETTER_SNOW", 76, "", 999, "Better Snow", false, false),  FULLSCREEN_MODE("FULLSCREEN_MODE", 77, "", 999, "Fullscreen Mode", false, false),  ANIMATED_TERRAIN("ANIMATED_TERRAIN", 78, "", 999, "Terrain Animated", false, false),  ANIMATED_ITEMS("ANIMATED_ITEMS", 79, "", 999, "Items Animated", false, false),  SWAMP_COLORS("SWAMP_COLORS", 80, "", 999, "Swamp Colors", false, false),  RANDOM_MOBS("RANDOM_MOBS", 81, "", 999, "Random Mobs", false, false),  SMOOTH_BIOMES("SMOOTH_BIOMES", 82, "", 999, "Smooth Biomes", false, false),  CUSTOM_FONTS("CUSTOM_FONTS", 83, "", 999, "Custom Fonts", false, false),  CUSTOM_COLORS("CUSTOM_COLORS", 84, "", 999, "Custom Colors", false, false),  SHOW_CAPES("SHOW_CAPES", 85, "", 999, "Show Capes", false, false),  CONNECTED_TEXTURES("CONNECTED_TEXTURES", 86, "", 999, "Connected Textures", false, false),  AA_LEVEL("AA_LEVEL", 87, "", 999, "Antialiasing", false, false),  ANIMATED_TEXTURES("ANIMATED_TEXTURES", 88, "", 999, "Textures Animated", false, false),  NATURAL_TEXTURES("NATURAL_TEXTURES", 89, "", 999, "Natural Textures", false, false),  CHUNK_LOADING("CHUNK_LOADING", 90, "", 999, "Chunk Loading", false, false),  HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 91, "", 999, "Held Item Tooltips", false, false),  DROPPED_ITEMS("DROPPED_ITEMS", 92, "", 999, "Dropped Items", false, false),  LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 93, "", 999, "Lazy Chunk Loading", false, false),  CUSTOM_SKY("CUSTOM_SKY", 94, "", 999, "Custom Sky", false, false),  FAST_MATH("FAST_MATH", 95, "", 999, "Fast Math", false, false),  FAST_RENDER("FAST_RENDER", 96, "", 999, "Fast Render", false, false),  TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 97, "", 999, "Translucent Blocks", false, false);
/* 2279:     */     
/* 2280:     */     private final boolean enumFloat;
/* 2281:     */     private final boolean enumBoolean;
/* 2282:     */     private final String enumString;
/* 2283:     */     private final float valueStep;
/* 2284:     */     private float valueMin;
/* 2285:     */     private float valueMax;
/* 2286:3128 */     private static final Options[] $VALUES = { INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, FBO_ENABLE, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, USE_SERVER_TEXTURES, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, SHOW_CAPE, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, ANISOTROPIC_FILTERING, FORCE_UNICODE_FONT };
/* 2287:     */     private static final String __OBFID = "CL_00000653";
/* 2288:3131 */     private static final Options[] $VALUES$ = { INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, SATURATION, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, FBO_ENABLE, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, USE_SERVER_TEXTURES, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, SHOW_CAPE, TOUCHSCREEN, CHAT_SCALE, CHAT_WIDTH, CHAT_HEIGHT_FOCUSED, CHAT_HEIGHT_UNFOCUSED, MIPMAP_LEVELS, ANISOTROPIC_FILTERING, FORCE_UNICODE_FONT, FOG_FANCY, FOG_START, MIPMAP_TYPE, LOAD_FAR, PRELOADED_CHUNKS, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, GRASS, RAIN, WATER, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, DEPTH_FOG, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, POTION_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, ANIMATED_ITEMS, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, AA_LEVEL, ANIMATED_TEXTURES, NATURAL_TEXTURES, CHUNK_LOADING, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING, CUSTOM_SKY, FAST_MATH, FAST_RENDER, TRANSLUCENT_BLOCKS };
/* 2289:     */     
/* 2290:     */     public static Options getEnumOptions(int par0)
/* 2291:     */     {
/* 2292:3135 */       Options[] var1 = values();
/* 2293:3136 */       int var2 = var1.length;
/* 2294:3138 */       for (int var3 = 0; var3 < var2; var3++)
/* 2295:     */       {
/* 2296:3140 */         Options var4 = var1[var3];
/* 2297:3142 */         if (var4.returnEnumOrdinal() == par0) {
/* 2298:3144 */           return var4;
/* 2299:     */         }
/* 2300:     */       }
/* 2301:3148 */       return null;
/* 2302:     */     }
/* 2303:     */     
/* 2304:     */     private Options(String var1, int var2, String par1Str, int par2, String par3Str, boolean par4, boolean par5)
/* 2305:     */     {
/* 2306:3153 */       this(var1, var2, par1Str, par2, par3Str, par4, par5, 0.0F, 1.0F, 0.0F);
/* 2307:     */     }
/* 2308:     */     
/* 2309:     */     private Options(String var1, int var2, String p_i45004_1_, int p_i45004_2_, String p_i45004_3_, boolean p_i45004_4_, boolean p_i45004_5_, float p_i45004_6_, float p_i45004_7_, float p_i45004_8_)
/* 2310:     */     {
/* 2311:3158 */       this.enumString = p_i45004_3_;
/* 2312:3159 */       this.enumFloat = p_i45004_4_;
/* 2313:3160 */       this.enumBoolean = p_i45004_5_;
/* 2314:3161 */       this.valueMin = p_i45004_6_;
/* 2315:3162 */       this.valueMax = p_i45004_7_;
/* 2316:3163 */       this.valueStep = p_i45004_8_;
/* 2317:     */     }
/* 2318:     */     
/* 2319:     */     public boolean getEnumFloat()
/* 2320:     */     {
/* 2321:3168 */       return this.enumFloat;
/* 2322:     */     }
/* 2323:     */     
/* 2324:     */     public boolean getEnumBoolean()
/* 2325:     */     {
/* 2326:3173 */       return this.enumBoolean;
/* 2327:     */     }
/* 2328:     */     
/* 2329:     */     public int returnEnumOrdinal()
/* 2330:     */     {
/* 2331:3178 */       return ordinal();
/* 2332:     */     }
/* 2333:     */     
/* 2334:     */     public String getEnumString()
/* 2335:     */     {
/* 2336:3183 */       return this.enumString;
/* 2337:     */     }
/* 2338:     */     
/* 2339:     */     public float getValueMax()
/* 2340:     */     {
/* 2341:3188 */       return this.valueMax;
/* 2342:     */     }
/* 2343:     */     
/* 2344:     */     public void setValueMax(float p_148263_1_)
/* 2345:     */     {
/* 2346:3193 */       this.valueMax = p_148263_1_;
/* 2347:     */     }
/* 2348:     */     
/* 2349:     */     public float normalizeValue(float p_148266_1_)
/* 2350:     */     {
/* 2351:3198 */       return MathHelper.clamp_float((snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/* 2352:     */     }
/* 2353:     */     
/* 2354:     */     public float denormalizeValue(float p_148262_1_)
/* 2355:     */     {
/* 2356:3203 */       return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
/* 2357:     */     }
/* 2358:     */     
/* 2359:     */     public float snapToStepClamp(float p_148268_1_)
/* 2360:     */     {
/* 2361:3208 */       p_148268_1_ = snapToStep(p_148268_1_);
/* 2362:3209 */       return MathHelper.clamp_float(p_148268_1_, this.valueMin, this.valueMax);
/* 2363:     */     }
/* 2364:     */     
/* 2365:     */     protected float snapToStep(float p_148264_1_)
/* 2366:     */     {
/* 2367:3214 */       if (this.valueStep > 0.0F) {
/* 2368:3216 */         p_148264_1_ = this.valueStep * Math.round(p_148264_1_ / this.valueStep);
/* 2369:     */       }
/* 2370:3219 */       return p_148264_1_;
/* 2371:     */     }
/* 2372:     */     
/* 2373:     */     private Options(String var1, int var2, String p_i45005_1_, int p_i45005_2_, String p_i45005_3_, boolean p_i45005_4_, boolean p_i45005_5_, float p_i45005_6_, float p_i45005_7_, float p_i45005_8_, Object p_i45005_9_)
/* 2374:     */     {
/* 2375:3224 */       this(var1, var2, p_i45005_1_, p_i45005_2_, p_i45005_3_, p_i45005_4_, p_i45005_5_, p_i45005_6_, p_i45005_7_, p_i45005_8_);
/* 2376:     */     }
/* 2377:     */     
/* 2378:     */     private Options(String x0, int x1, String x2, int x3, String x4, boolean x5, boolean x6, float x7, float x8, float x9, Object x10, Object x11)
/* 2379:     */     {
/* 2380:3229 */       this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10);
/* 2381:     */     }
/* 2382:     */   }
/* 2383:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.settings.GameSettings
 * JD-Core Version:    0.7.0.1
 */