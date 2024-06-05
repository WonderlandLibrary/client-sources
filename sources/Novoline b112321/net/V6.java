package net;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.Color;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.Br;
import net.CI;
import net.D7;
import net.JP;
import net.Ql;
import net.a6_;
import net.aHc;
import net.aIB;
import net.aSQ;
import net.aTJ;
import net.afq;
import net.aio;
import net.alO;
import net.asQ;
import net.azi;
import net.d3;
import net.gZ;
import net.zZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.SkinManager$SkinAvailableCallback;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class V6 {
   private static final GameProfile j = new GameProfile(new UUID(0L, 0L), "Ловушка джокера");
   static final asQ a = new asQ(j, (ResourceLocation)null);
   private final aHc d;
   private final JP e;
   private asQ p;
   private afq A;
   private long u;
   private String E;
   private double i;
   private boolean v;
   private final d3 r = new d3();
   private float h = 255.0F;
   private float D = 0.0F;
   private long z;
   private static final byte c = 0;
   private static final byte w = 1;
   private static final byte o = 2;
   private static final byte g = 3;
   private byte l = 0;
   long B;
   private static final float q = 0.71F;
   private static final int x = 24;
   static final float t = 5.0F;
   private static final int b = 100;
   private static final int f = 10;
   private static final int y = (new Color(0, 0, 0, 75)).getRGB();
   private static final int k = (new Color(0, 0, 0, 100)).getRGB();
   private static final int n = -7961722;
   private static final int s = (new Color(198, 198, 198)).getRGB();
   private static final int m = 7245117;
   private static final int C = 10369337;

   public V6(@NotNull JP var1, @NotNull asQ var2, @Nullable afq var3, @NotNull aHc var4, Long var5, String var6, double var7, boolean var9) {
      this.d = var4;
      this.e = var1;
      this.p = var2;
      this.A = var3;
      this.u = var5.longValue();
      this.i = var7;
      this.E = var6;
      this.v = var9;
   }

   protected boolean a(float var1, float var2, int var3, int var4) {
      int[] var5 = CI.b();
      if(!this.b(var1, var2, var3, var4)) {
         return false;
      } else {
         if(Minecraft.getSystemTime() - this.B < 250L) {
            this.e(true);
         }

         this.j();
         this.B = Minecraft.getSystemTime();
         return true;
      }
   }

   public void a(float var1, int var2, int var3, int var4) {
      int[] var5 = CI.b();
      a6_.a(15.0F, (float)var2, var1, 36.0F, 5.0F, !this.e()?y:k);
      if(this.l() && this.h > 0.0F) {
         a6_.a(15.0F, (float)var2, this.D, 36.0F, 5.0F, (int)Math.max(0.0F, this.h) << 24 | (this.b()?7245117:10369337));
         this.a(var1, var3, var4);
      }

      this.a(this.p, var2);
      D7.a.b((this.v?EnumChatFormatting.STRIKETHROUGH:"") + this.p.getName(), 53.0F, (float)(var2 + 3), s);
      aTJ.a.b("Email: " + this.e.a(), 53.0F, (float)(var2 + 15), s);
      String var6 = this.e.c();
      if(StringUtils.isNotBlank(var6)) {
         aTJ.a.b("Password:", 53.0F, (float)(var2 + 24), s);
         aTJ.a.b((new String(new char[var6.length()])).replace('\u0000', '*'), (float)(zZ.a.a("Password:") + 57), (float)(var2 + 25), s);
      }

      if(this.d.g() == this) {
         D7.a.b("Logged", var1 - 35.0F, (float)var2 + 18.0F - 5.0F, (new Color(255, 255, 255, 50)).getRGB());
      }

      if(this.u != -1L && this.u - System.currentTimeMillis() < 0L) {
         this.u = 0L;
      }

      if(this.u != 0L) {
         if(this.u != -1L) {
            int var8 = (int)((this.u - System.currentTimeMillis()) / 1000L);
            String var9 = var8 > 86400?var8 / 86400 + "d ":"";
            var8 = !var9.equals("")?var8 % 86400:var8;
            String var10 = var8 > 3600?var8 / 3600 + "h ":"";
            var8 = !var10.equals("")?var8 % 3600:var8;
            String var11 = var8 > 60?var8 / 60 + "m ":"";
            (new StringBuilder()).append(var9).append(var10).append(var11).toString();
         }

         String var7 = "Permed";
         D7.a.b(var7, var1 - (float)D7.a.a(var7) - (float)(this.d.g() == this?45:0) + 5.0F, (float)var2 + 18.0F - 5.0F, s);
      }

   }

   private void a(@NotNull asQ var1, int var2) {
      Minecraft var3 = Minecraft.getMinecraft();
      var3.ab().a(var1.getLocationSkin());
      aSQ.a(18, var2 + 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
   }

   private void a(float var1, int var2, int var3) {
      CI.b();
      float var5 = this.d.m();
      if(this.r.a(10.0D) && this.h > 0.0F) {
         this.h -= var5;
         this.r.b();
      }

      if(this.D < var1) {
         this.D = Math.min(this.D + this.d.p(), var1);
      }

   }

   public CompletableFuture e(boolean var1) {
      CompletableFuture var2 = CompletableFuture.supplyAsync(this::lambda$logIn$0, ForkJoinPool.commonPool());
      return var2.whenCompleteAsync(this::lambda$logIn$1);
   }

   private void a(byte var1, boolean var2) {
      CI.b();
      byte var4 = (byte)(1 << var1);
      this.l = (byte)(this.l & ~var4);
      this.l = (byte)(this.l & ~var4 | 1 << var1 & var4);
   }

   private boolean a(byte var1) {
      CI.b();
      byte var3 = (byte)(1 << var1);
      return (this.l & var3) == var3;
   }

   public void o() {
      this.a((byte)1, false);
      this.a((byte)2, false);
   }

   private void a(boolean var1) {
      this.a((byte)1, true);
      this.a((byte)2, var1);
   }

   public boolean b() {
      int[] var1 = CI.b();
      return this.l() && this.a((byte)2);
   }

   public boolean k() {
      int[] var1 = CI.b();
      return this.l() && !this.a((byte)2);
   }

   public boolean l() {
      return this.a((byte)1);
   }

   void b(boolean var1) {
      this.a((byte)0, var1);
   }

   public boolean e() {
      return this.a((byte)0);
   }

   public boolean n() {
      return this.a((byte)3);
   }

   private void d(boolean var1) {
      this.a((byte)3, var1);
   }

   public void a(long var1) {
      this.u = var1;
   }

   public long a() {
      return this.u;
   }

   public boolean d() {
      return this.v;
   }

   public void a(@NotNull GameProfile var1) {
      this.a((GameProfile)var1, (ResourceLocation)null);
      Minecraft var2 = Minecraft.getMinecraft();
      var1.getProperties().clear();
      var1.getProperties().putAll(var2.M());
      MinecraftProfileTexture var3 = (MinecraftProfileTexture)var2.getSessionService().getTextures(var1, false).get(Type.SKIN);
      var2.addScheduledTask(this::lambda$setGameProfile$3);
   }

   void a(@NotNull GameProfile var1, @Nullable ResourceLocation var2) {
      Minecraft var3 = Minecraft.getMinecraft();
      this.p = new asQ(var1, var2);
      aIB.a(var3.getRenderManager(), this.p.worldObj, var3.fontRendererObj, this.p, this.p, var3.gameSettings, 0.0F);
   }

   public void j() {
      CI.b();
      V6 var2 = (V6)this.d.k().stream().filter(V6::e).findAny().orElse((Object)null);
      if(var2 != null) {
         var2.b(false);
      }

      this.b(true);
      this.d.a(var2, this, (Integer)null);
   }

   public void a(int var1, int var2) {
      if(this.p != null) {
         Minecraft var3 = Minecraft.getMinecraft();
         int var4 = this.d.width;
         int var5 = this.d.height;
         boolean var6 = true;
         float var7 = (float)var5 / 3.0F * 0.71F;
         int var8 = var4 - 175;
         int var9 = 25 + var5 - 25 - 24 - 90 - 24;
         int var10 = var4 - 175 - var1;
         float var11 = (float)var5 / 2.0F + this.p.height * var7 - this.p.height * var7 * (this.p.getEyeHeight() / this.p.height) - (float)var2;
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableColorMaterial();
         GlStateManager.pushMatrix();
         GL11.glEnable(2929);
         GlStateManager.translate((float)var8, (float)var9, 50.0F);
         GL11.glScalef(-var7, var7, var7);
         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.enableStandardItemLighting();
         GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
         float var12 = (float)Math.atan((double)((float)var10 / 40.0F));
         float var13 = -((float)Math.atan((double)(var11 / 40.0F)));
         GlStateManager.rotate(var13 * 20.0F, 1.0F, 0.0F, 0.0F);
         this.p.renderYawOffset = var12 * 20.0F;
         this.p.rotationYaw = var12 * 40.0F;
         this.p.rotationPitch = var13 * 20.0F;
         this.p.rotationYawHead = this.p.rotationYaw;
         this.p.prevRotationYawHead = this.p.rotationYaw;
         GlStateManager.translate(0.0F, 0.0F, 0.0F);

         try {
            RenderManager var14 = var3.getRenderManager();
            var14.setPlayerViewY(180.0F);
            var14.setRenderShadow(false);
            aIB.a(var14, this.p, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
            var14.setRenderShadow(true);
         } catch (Exception var15) {
            var15.printStackTrace();
         }

         GL11.glDisable(2929);
         GlStateManager.popMatrix();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.disableTexture2D();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      }

   }

   private boolean b(float var1, float var2, int var3, int var4) {
      int[] var5 = CI.b();
      return var3 >= 15 && (float)var3 <= var1 + 15.0F && (float)var4 >= var2 && (float)var4 <= var2 + 36.0F;
   }

   @NotNull
   public static V6 a(aHc var0, @NotNull NBTTagCompound var1) {
      CI.b();
      String var3 = Ql.a(var1.getString("login"), "login");
      String var4 = var1.b("password", (String)null);
      afq var5 = aio.a(var1.a("hypixel", (NBTTagCompound)null));
      var5 = var5 != null?var5:afq.b();
      NBTTagCompound var6 = var1.a("profile", (NBTTagCompound)null);
      GameProfile var7 = NBTUtil.readGameProfileFromNBT(var6);
      asQ var8 = new asQ((GameProfile)Objects.requireNonNull(var7), (ResourceLocation)null);
      Long var9 = Long.valueOf(Long.parseLong(var1.b("unbanDate", (String)null)));
      String var10 = "NONE";
      double var11 = 1.0D;
      if(var1.hasKey("networkLevel")) {
         var11 = Double.parseDouble(var1.b("networkLevel", (String)null));
      }

      if(var1.hasKey("rank")) {
         var10 = var1.b("rank", (String)null);
      }

      boolean var13 = false;
      if(var1.hasKey("invalid")) {
         var13 = var1.getBoolean("invalid");
      }

      JP var14 = new JP(var3, var4);
      return new V6(var14, var8, var5, var0, var9, var10, var11, var13);
   }

   public NBTBase m() {
      CI.b();
      NBTTagCompound var2 = new NBTTagCompound();
      var2.setString("unbanDate", String.valueOf(this.u));
      var2.setString("rank", this.E);
      var2.setString("networkLevel", String.valueOf(this.i));
      var2.setString("login", this.e.a());
      var2.setBoolean("invalid", this.v);
      if(this.e.c() != null) {
         var2.setString("password", this.e.c());
      }

      if(this.A != null) {
         var2.setTag("hypixel", this.A.g());
      }

      var2.setTag("profile", NBTUtil.writeGameProfile(new NBTTagCompound(), this.p.getGameProfile()));
      return var2;
   }

   public String toString() {
      CI.b();
      StringBuilder var2 = new StringBuilder("Username: " + this.p.getGameProfile().getName());
      if(this.A != null) {
         String var3 = this.A.e();
         int var4 = this.A.d();
         if(var4 > 1) {
            var2.append(" | ").append(var4).append(" Lvl");
         }

         if(var3 != null && !var3.equalsIgnoreCase("default")) {
            var2.append(" | ").append(var3);
         }
      }

      return var2.toString();
   }

   public JP i() {
      return this.e;
   }

   public double g() {
      return this.i;
   }

   public String f() {
      return this.E;
   }

   public void c(boolean var1) {
      this.v = var1;
   }

   public asQ c() {
      return this.p;
   }

   public afq h() {
      return this.A;
   }

   private ResourceLocation lambda$setGameProfile$3(Minecraft var1, MinecraftProfileTexture var2, GameProfile var3) throws Exception {
      return var1.V().a(var2, Type.SKIN, this::lambda$null$2);
   }

   private void lambda$null$2(GameProfile var1, Type var2, ResourceLocation var3, MinecraftProfileTexture var4) {
      this.a(var1, var3);
   }

   private void lambda$logIn$1(Session var1, Throwable var2) {
      int[] var3 = CI.b();
      gZ.v().warn("An error occurred while logging in!", var2);
   }

   private Session lambda$logIn$0() {
      CI.b();
      Session var2 = null;
      if(!this.n() && !this.b()) {
         this.d(true);
         var2 = (new alO(this.e, new Br(this))).a();
         this.d(false);
         this.h = 255.0F;
         this.D = 0.0F;
      } else {
         if(this.n()) {
            if(System.currentTimeMillis() <= this.z + 150L) {
               return var2;
            }

            gZ.g().t().a("Already trying logging in!", azi.ERROR);
            this.z = System.currentTimeMillis();
         }

         if(this.b() && System.currentTimeMillis() > this.z + 150L) {
            gZ.g().t().a("Already logged in!", azi.ERROR);
            this.z = System.currentTimeMillis();
         }
      }

      return var2;
   }

   static aHc b(V6 var0) {
      return var0.d;
   }

   static void a(V6 var0, boolean var1) {
      var0.a(var1);
   }

   static afq a(V6 var0) {
      return var0.A;
   }

   static String a(V6 var0, String var1) {
      return var0.E = var1;
   }

   static double a(V6 var0, double var1) {
      return var0.i = var1;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
