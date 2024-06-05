package net;

import java.io.File;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.optifine.Config;
import net.optifine.GlVersion;

public class awt {
   private static boolean b;

   public static void e(String var0) {
      Config.log(var0);
   }

   public static void a(String var0) {
      Config.warn(var0);
   }

   public static boolean n() {
      return Config.be();
   }

   public static boolean ah() {
      return Config.isShaders();
   }

   public static boolean P() {
      return Config.isMultiTexture();
   }

   public static boolean p() {
      return Config.isCustomItems();
   }

   public static boolean Y() {
      return Config.isCustomColors();
   }

   public static boolean u() {
      return Config.ai();
   }

   public static GameSettings aP() {
      return Config.getGameSettings();
   }

   public static String k() {
      return Config.getVersion();
   }

   public static void c(String var0) {
      Config.dbg(var0);
   }

   public static int V() {
      return Config.getChunkViewDistance();
   }

   public static int ab() {
      return Config.getMipmapLevels();
   }

   public static int G() {
      return Config.getAnisotropicFilterLevel();
   }

   public static int S() {
      return Config.getAntialiasingLevel();
   }

   public static int x() {
      return Config.getAvailableProcessors();
   }

   public static IResourcePack[] az() {
      return Config.getResourcePacks();
   }

   public static DefaultResourcePack at() {
      return Config.getDefaultResourcePack();
   }

   public static InputStream d(ResourceLocation var0) {
      return Config.getResourceStream(var0);
   }

   public static boolean a(String var0, boolean var1) {
      return Config.parseBoolean(var0, var1);
   }

   public static Object[] a(Object[] var0, Object[] var1) {
      return Config.addObjectsToArray(var0, var1);
   }

   public static TextureManager aF() {
      return Config.getTextureManager();
   }

   public static Minecraft c() {
      return Config.getMinecraft();
   }

   public static String d(String var0) {
      return Config.c(var0);
   }

   public static String a(Object[] var0) {
      return Config.a(var0);
   }

   public static boolean e(ResourceLocation var0) {
      return Config.hasResource(var0);
   }

   public static void a() {
      Config.Y();
   }

   public static IResourceManager aS() {
      return Config.getResourceManager();
   }

   public static boolean a(Object var0, Object var1) {
      return Config.equals(var0, var1);
   }

   public static String[] c(String var0, String var1) {
      return Config.tokenize(var0, var1);
   }

   public static int a(String var0, int var1) {
      return Config.parseInt(var0, var1);
   }

   public static boolean B() {
      return Config.isSingleProcessor();
   }

   public static void a(GameSettings var0) {
      Config.initGameSettings(var0);
   }

   public static void b(String var0, String var1) {
      Config.showGuiMessage(var0, var1);
   }

   public static int a(int var0, int var1, int var2) {
      return Config.limit(var0, var1, var2);
   }

   public static boolean J() {
      return Config.isFancyFogAvailable();
   }

   public static void U() {
      Config.updateThreadPriorities();
   }

   public static void an() {
      Config.updateAvailableProcessors();
   }

   public static String[] aH() {
      return Config.getDisplayModeNames();
   }

   public static float a(float var0, float var1, float var2) {
      return Config.limit(var0, var1, var2);
   }

   public static boolean o() {
      return Config.aO();
   }

   public static boolean ae() {
      return Config.al();
   }

   public static boolean E() {
      return Config.isShowCapes();
   }

   public static float a(String var0, float var1) {
      return Config.parseFloat(var0, var1);
   }

   public static boolean H() {
      return Config.isAntialiasing();
   }

   public static boolean r() {
      return Config.isAnisotropicFiltering();
   }

   public static Object[] a(Object[] var0, Object var1, int var2) {
      return Config.addObjectToArray(var0, var1, var2);
   }

   public static void a(File var0, String var1) {
      Config.writeFile(var0, var1);
   }

   public static void aJ() {
      Config.initDisplay();
   }

   public static boolean g() {
      return Config.isRandomMobs();
   }

   public static DynamicTexture a(DynamicTexture var0) {
      return Config.getMojangLogoTexture(var0);
   }

   public static String aA() {
      return Config.getResourcePackNames();
   }

   public static boolean ag() {
      return Config.isCustomSky();
   }

   public static String a(int[] var0) {
      return Config.a(var0);
   }

   public static void a(InputStream var0) {
      Config.readAll(var0);
   }

   public static Object[] a(Object[] var0, Object var1) {
      return Config.addObjectToArray(var0, var1);
   }

   public static boolean D() {
      return Config.isDynamicFov();
   }

   public static boolean aU() {
      return Config.isFogFancy();
   }

   public static boolean aV() {
      return Config.ad();
   }

   public static void ai() {
      Config.drawFps();
   }

   public static boolean R() {
      return Config.isSkyEnabled();
   }

   public static boolean au() {
      return Config.isSunMoonEnabled();
   }

   public static boolean b() {
      return Config.isStarsEnabled();
   }

   public static boolean ay() {
      return Config.isCloudsOff();
   }

   public static boolean ap() {
      return Config.as();
   }

   public static boolean as() {
      return Config.isRainSplash();
   }

   public static boolean aL() {
      return Config.aV();
   }

   public static boolean av() {
      return Config.isClearWater();
   }

   public static float j() {
      return Config.getFogStart();
   }

   public static boolean v() {
      return Config.isSmoothWorld();
   }

   public static void a(long var0) {
      Config.sleep(var0);
   }

   public static int aG() {
      return Config.getBitsOs();
   }

   public static int q() {
      return Config.getBitsJre();
   }

   public static void a(boolean var0) {
      Config.setNotify64BitJava(var0);
   }

   public static void N() {
      Config.checkDisplayMode();
   }

   public static boolean W() {
      return Config.isNotify64BitJava();
   }

   public static boolean F() {
      return Config.a7();
   }

   public static InputStream a(IResourceManager var0, ResourceLocation var1) {
      return Config.getResourceStream(var0, var1);
   }

   public static boolean aX() {
      return Config.isCustomFonts();
   }

   public static boolean b(IResourceManager var0, ResourceLocation var1) {
      return Config.hasResource(var0, var1);
   }

   public static boolean aW() {
      return Config.y();
   }

   public static ModelManager ad() {
      return Config.getModelManager();
   }

   public static IResourcePack c(ResourceLocation var0) {
      return Config.getDefiningResourcePack(var0);
   }

   public static String[] I() {
      return Config.getOpenGlExtensions();
   }

   public static int a(int var0) {
      return Config.intHash(var0);
   }

   public static boolean aj() {
      return Config.isSwampColors();
   }

   public static boolean al() {
      return Config.isSmoothBiomes();
   }

   public static float a(float var0) {
      return Config.limitTo1(var0);
   }

   public static double a(double var0, double var2, double var4) {
      return Config.limit(var0, var2, var4);
   }

   public static boolean aM() {
      return Config.aQ();
   }

   public static boolean K() {
      return Config.isWeatherEnabled();
   }

   public static boolean ax() {
      return Config.bd();
   }

   public static boolean M() {
      return Config.a_();
   }

   public static boolean aT() {
      return Config.isAntialiasingConfigured();
   }

   public static int a(BlockPos var0, int var1) {
      return Config.getRandom(var0, var1);
   }

   public static boolean aI() {
      return Config.B();
   }

   public static boolean O() {
      return Config.k();
   }

   public static boolean b(ResourceLocation var0) {
      return Config.isFromDefaultResourcePack(var0);
   }

   public static String b(InputStream var0) {
      return Config.readInputStream(var0);
   }

   public static int a(String var0, String var1) {
      return Config.compareRelease(var0, var1);
   }

   public static void b(String var0) {
      Config.setNewRelease(var0);
   }

   public static int[] a(int[] var0, int var1) {
      return Config.addIntToArray(var0, var1);
   }

   public static String[] c(InputStream var0) {
      return Config.readLines(var0);
   }

   public static int ar() {
      return Config.getMinecraftVersionInt();
   }

   public static GlVersion aY() {
      return Config.getGlVersion();
   }

   public static GlVersion aq() {
      return Config.getGlslVersion();
   }

   public static boolean aN() {
      return Config.O();
   }

   public static boolean e() {
      return Config.aT();
   }

   public static boolean aK() {
      return Config.E();
   }

   public static String aE() {
      return Config.getVersionDebug();
   }

   public static boolean Q() {
      return Config.isSunTexture();
   }

   public static boolean Z() {
      return Config.isMoonTexture();
   }

   public static boolean s() {
      return Config.isCloudsFancy();
   }

   public static int f() {
      return Config.getUpdatesPerFrame();
   }

   public static boolean ac() {
      return Config.isAnimatedExplosion();
   }

   public static boolean X() {
      return Config.isWaterParticles();
   }

   public static boolean A() {
      return Config.isVoidParticles();
   }

   public static boolean ao() {
      return Config.isAnimatedSmoke();
   }

   public static boolean aw() {
      return Config.isPotionParticles();
   }

   public static boolean ak() {
      return Config.isAnimatedPortal();
   }

   public static boolean t() {
      return Config.isAnimatedFlame();
   }

   public static boolean d() {
      return Config.isAnimatedRedstone();
   }

   public static boolean m() {
      return Config.isDrippingWaterLava();
   }

   public static boolean aD() {
      return Config.isFireworkParticles();
   }

   public static boolean T() {
      return Config.isMinecraftThread();
   }

   public static float l() {
      return Config.getAmbientOcclusionLevel();
   }

   public static boolean L() {
      return Config.T();
   }

   public static boolean C() {
      return Config.isBetterSnow();
   }

   public static boolean y() {
      return Config.isNaturalTextures();
   }

   public static boolean i() {
      return Config.isAnimatedTextures();
   }

   public static boolean af() {
      return Config.isAnimatedTerrain();
   }

   public static boolean aa() {
      return Config.isAnimatedFire();
   }

   public static boolean h() {
      return Config.i();
   }

   public static boolean aR() {
      return Config.a();
   }

   public static TextureMap aQ() {
      return Config.getTextureMap();
   }

   public static IResource a(ResourceLocation var0) {
      return Config.getResource(var0);
   }

   public static boolean w() {
      return Config.isDynamicHandLight();
   }

   public static int aC() {
      return Config.getMipmapType();
   }

   public static WorldServer am() {
      return Config.ag();
   }

   public static RenderGlobal aO() {
      return Config.getRenderGlobal();
   }

   public static boolean aB() {
      return Config.isVignetteEnabled();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean aZ() {
      return b;
   }

   public static boolean z() {
      boolean var0 = aZ();
      return true;
   }

   static {
      if(!z()) {
         b(true);
      }

   }
}
