package cc.novoline.gui.screen.alt.repository;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.alt.login.AltLoginThread;
import cc.novoline.gui.screen.alt.repository.Alt$1;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfile;
import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfileFactory;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_12;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_20;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_12;
import cc.novoline.utils.java.Checks;
import cc.novoline.utils.minecraft.FakeEntityPlayer;
import cc.novoline.utils.notifications.NotificationType;
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
import net.CI;
import net.aIB;
import net.aSQ;
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

public class Alt {
   private static final GameProfile j = new GameProfile(new UUID(0L, 0L), "Ловушка джокера");
   static final FakeEntityPlayer FAKE_ENTITY_PLAYER = new FakeEntityPlayer(j, (ResourceLocation)null);
   private final AltRepositoryGUI repository;
   private final AltCredential credential;
   private FakeEntityPlayer player;
   private HypixelProfile hypixelProfile;
   private long unbanDate;
   private String rank;
   private double networkLevel;
   private boolean invalid;
   private final Timer timer = new Timer();
   private float alpha = 255.0F;
   private float animationX = 0.0F;
   private long lastTimeAlreadyLogged;
   private static final byte SELECTED_POSITION = 0;
   private static final byte AUTHORIZED_POSITION = 1;
   private static final byte LOGGED_POSITION = 2;
   private static final byte LOGGING_IN_POSITION = 3;
   private byte state = 0;
   long lastClickTime;
   private static final float MODEL_SCALE_FACTOR = 0.71F;
   private static final int MODEL_BOTTOM_MARGIN = 24;
   static final float FHD_ANIMATION_STEP = 5.0F;
   private static final int UPDATES_PER_SECOND = 100;
   private static final int UPDATE_MILLIS_DELAY = 10;
   private static final int DEFAULT_COLOR = (new Color(0, 0, 0, 75)).getRGB();
   private static final int SELECTED_COLOR = (new Color(0, 0, 0, 100)).getRGB();
   private static final int TEXT_DEFAULT_COLOR = -7961722;
   private static final int TEXT_SELECTED_COLOR = (new Color(198, 198, 198)).getRGB();
   private static final int SUCCESS_LOGIN_COLOR = 7245117;
   private static final int FAILED_LOGIN_COLOR = 10369337;

   public Alt(@NotNull AltCredential var1, @NotNull FakeEntityPlayer var2, @Nullable HypixelProfile var3, @NotNull AltRepositoryGUI var4, Long var5, String var6, double var7, boolean var9) {
      this.repository = var4;
      this.credential = var1;
      this.player = var2;
      this.hypixelProfile = var3;
      this.unbanDate = var5.longValue();
      this.networkLevel = var7;
      this.rank = var6;
      this.invalid = var9;
   }

   protected boolean mouseClicked(float var1, float var2, int var3, int var4) {
      int[] var5 = CI.b();
      if(!this.isHovered(var1, var2, var3, var4)) {
         return false;
      } else {
         if(Minecraft.getSystemTime() - this.lastClickTime < 250L) {
            this.logIn(true);
         }

         this.select();
         this.lastClickTime = Minecraft.getSystemTime();
         return true;
      }
   }

   public void drawAlt(float var1, int var2, int var3, int var4) {
      int[] var5 = CI.b();
      RenderUtils.drawRoundedRect(15.0F, (float)var2, var1, 36.0F, 5.0F, !this.isSelected()?DEFAULT_COLOR:SELECTED_COLOR);
      if(this.triedAuthorizing() && this.alpha > 0.0F) {
         RenderUtils.drawRoundedRect(15.0F, (float)var2, this.animationX, 36.0F, 5.0F, (int)Math.max(0.0F, this.alpha) << 24 | (this.isLoginSuccessful()?7245117:10369337));
         this.renderAltBox(var1, var3, var4);
      }

      this.drawSkull(this.player, var2);
      Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.drawString((this.invalid?EnumChatFormatting.STRIKETHROUGH:"") + this.player.getName(), 53.0F, (float)(var2 + 3), TEXT_SELECTED_COLOR);
      Fonts$SFBOLD$SFBOLD_12.SFBOLD_12.drawString("Email: " + this.credential.getLogin(), 53.0F, (float)(var2 + 15), TEXT_SELECTED_COLOR);
      String var6 = this.credential.getPassword();
      if(StringUtils.isNotBlank(var6)) {
         Fonts$SFBOLD$SFBOLD_12.SFBOLD_12.drawString("Password:", 53.0F, (float)(var2 + 24), TEXT_SELECTED_COLOR);
         Fonts$SFBOLD$SFBOLD_12.SFBOLD_12.drawString((new String(new char[var6.length()])).replace('\u0000', '*'), (float)(Fonts$SFTHIN$SFTHIN_12.SFTHIN_12.stringWidth("Password:") + 57), (float)(var2 + 25), TEXT_SELECTED_COLOR);
      }

      if(this.repository.getCurrentAlt() == this) {
         Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.drawString("Logged", var1 - 35.0F, (float)var2 + 18.0F - 5.0F, (new Color(255, 255, 255, 50)).getRGB());
      }

      if(this.unbanDate != -1L && this.unbanDate - System.currentTimeMillis() < 0L) {
         this.unbanDate = 0L;
      }

      if(this.unbanDate != 0L) {
         if(this.unbanDate != -1L) {
            int var8 = (int)((this.unbanDate - System.currentTimeMillis()) / 1000L);
            String var9 = var8 > 86400?var8 / 86400 + "d ":"";
            var8 = !var9.equals("")?var8 % 86400:var8;
            String var10 = var8 > 3600?var8 / 3600 + "h ":"";
            var8 = !var10.equals("")?var8 % 3600:var8;
            String var11 = var8 > 60?var8 / 60 + "m ":"";
            (new StringBuilder()).append(var9).append(var10).append(var11).toString();
         }

         String var7 = "Permed";
         Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.drawString(var7, var1 - (float)Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.stringWidth(var7) - (float)(this.repository.getCurrentAlt() == this?45:0) + 5.0F, (float)var2 + 18.0F - 5.0F, TEXT_SELECTED_COLOR);
      }

   }

   private void drawSkull(@NotNull FakeEntityPlayer var1, int var2) {
      Minecraft var3 = Minecraft.getInstance();
      var3.getTextureManager().bindTexture(var1.getLocationSkin());
      aSQ.a(18, var2 + 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
   }

   private void renderAltBox(float var1, int var2, int var3) {
      CI.b();
      float var5 = this.repository.getAltBoxAlphaStep();
      if(this.timer.delay(10.0D) && this.alpha > 0.0F) {
         this.alpha -= var5;
         this.timer.reset();
      }

      if(this.animationX < var1) {
         this.animationX = Math.min(this.animationX + this.repository.getAltBoxAnimationStep(), var1);
      }

   }

   public CompletableFuture logIn(boolean var1) {
      CompletableFuture var2 = CompletableFuture.supplyAsync(this::lambda$logIn$0, ForkJoinPool.commonPool());
      return var2.whenCompleteAsync(this::lambda$logIn$1);
   }

   private void modifyState(byte var1, boolean var2) {
      CI.b();
      byte var4 = (byte)(1 << var1);
      this.state = (byte)(this.state & ~var4);
      this.state = (byte)(this.state & ~var4 | 1 << var1 & var4);
   }

   private boolean state(byte var1) {
      CI.b();
      byte var3 = (byte)(1 << var1);
      return (this.state & var3) == var3;
   }

   public void resetLogged() {
      this.modifyState((byte)1, false);
      this.modifyState((byte)2, false);
   }

   private void setLoginProperty(boolean var1) {
      this.modifyState((byte)1, true);
      this.modifyState((byte)2, var1);
   }

   public boolean isLoginSuccessful() {
      int[] var1 = CI.b();
      return this.triedAuthorizing() && this.state((byte)2);
   }

   public boolean isLoginUnsuccessful() {
      int[] var1 = CI.b();
      return this.triedAuthorizing() && !this.state((byte)2);
   }

   public boolean triedAuthorizing() {
      return this.state((byte)1);
   }

   void setSelectedProperty(boolean var1) {
      this.modifyState((byte)0, var1);
   }

   public boolean isSelected() {
      return this.state((byte)0);
   }

   public boolean isLoggingIn() {
      return this.state((byte)3);
   }

   private void setLoggingIn(boolean var1) {
      this.modifyState((byte)3, var1);
   }

   public void setUnbanDate(long var1) {
      this.unbanDate = var1;
   }

   public long getUnbanDate() {
      return this.unbanDate;
   }

   public boolean isInvalid() {
      return this.invalid;
   }

   public void setGameProfile(@NotNull GameProfile var1) {
      this.setupPlayer(var1, (ResourceLocation)null);
      Minecraft var2 = Minecraft.getInstance();
      var1.getProperties().clear();
      var1.getProperties().putAll(var2.fillSessionProfileProperties());
      MinecraftProfileTexture var3 = (MinecraftProfileTexture)var2.getSessionService().getTextures(var1, false).get(Type.SKIN);
      var2.addScheduledTask(this::lambda$setGameProfile$3);
   }

   void setupPlayer(@NotNull GameProfile var1, @Nullable ResourceLocation var2) {
      Minecraft var3 = Minecraft.getInstance();
      this.player = new FakeEntityPlayer(var1, var2);
      aIB.a(var3.getRenderManager(), this.player.worldObj, var3.fontRendererObj, this.player, this.player, var3.gameSettings, 0.0F);
   }

   public void select() {
      CI.b();
      Alt var2 = (Alt)this.repository.getAlts().stream().filter(Alt::isSelected).findAny().orElse((Object)null);
      if(var2 != null) {
         var2.setSelectedProperty(false);
      }

      this.setSelectedProperty(true);
      this.repository.selectAlt(var2, this, (Integer)null);
   }

   public void drawEntity(int var1, int var2) {
      if(this.player != null) {
         Minecraft var3 = Minecraft.getInstance();
         int var4 = this.repository.width;
         int var5 = this.repository.height;
         boolean var6 = true;
         float var7 = (float)var5 / 3.0F * 0.71F;
         int var8 = var4 - 175;
         int var9 = 25 + var5 - 25 - 24 - 90 - 24;
         int var10 = var4 - 175 - var1;
         float var11 = (float)var5 / 2.0F + this.player.height * var7 - this.player.height * var7 * (this.player.getEyeHeight() / this.player.height) - (float)var2;
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
         this.player.renderYawOffset = var12 * 20.0F;
         this.player.rotationYaw = var12 * 40.0F;
         this.player.rotationPitch = var13 * 20.0F;
         this.player.rotationYawHead = this.player.rotationYaw;
         this.player.prevRotationYawHead = this.player.rotationYaw;
         GlStateManager.translate(0.0F, 0.0F, 0.0F);

         try {
            RenderManager var14 = var3.getRenderManager();
            var14.setPlayerViewY(180.0F);
            var14.setRenderShadow(false);
            aIB.a(var14, this.player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
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

   private boolean isHovered(float var1, float var2, int var3, int var4) {
      int[] var5 = CI.b();
      return var3 >= 15 && (float)var3 <= var1 + 15.0F && (float)var4 >= var2 && (float)var4 <= var2 + 36.0F;
   }

   @NotNull
   public static Alt fromNBT(AltRepositoryGUI var0, @NotNull NBTTagCompound var1) {
      CI.b();
      String var3 = Checks.notBlank(var1.getString("login"), "login");
      String var4 = var1.getString("password", (String)null);
      HypixelProfile var5 = HypixelProfileFactory.fromNBT(var1.getCompoundTag("hypixel", (NBTTagCompound)null));
      var5 = var5 != null?var5:HypixelProfile.empty();
      NBTTagCompound var6 = var1.getCompoundTag("profile", (NBTTagCompound)null);
      GameProfile var7 = NBTUtil.readGameProfileFromNBT(var6);
      FakeEntityPlayer var8 = new FakeEntityPlayer((GameProfile)Objects.requireNonNull(var7), (ResourceLocation)null);
      Long var9 = Long.valueOf(Long.parseLong(var1.getString("unbanDate", (String)null)));
      String var10 = "NONE";
      double var11 = 1.0D;
      if(var1.hasKey("networkLevel")) {
         var11 = Double.parseDouble(var1.getString("networkLevel", (String)null));
      }

      if(var1.hasKey("rank")) {
         var10 = var1.getString("rank", (String)null);
      }

      boolean var13 = false;
      if(var1.hasKey("invalid")) {
         var13 = var1.getBoolean("invalid");
      }

      AltCredential var14 = new AltCredential(var3, var4);
      return new Alt(var14, var8, var5, var0, var9, var10, var11, var13);
   }

   public NBTBase asNBTCompound() {
      CI.b();
      NBTTagCompound var2 = new NBTTagCompound();
      var2.setString("unbanDate", String.valueOf(this.unbanDate));
      var2.setString("rank", this.rank);
      var2.setString("networkLevel", String.valueOf(this.networkLevel));
      var2.setString("login", this.credential.getLogin());
      var2.setBoolean("invalid", this.invalid);
      if(this.credential.getPassword() != null) {
         var2.setString("password", this.credential.getPassword());
      }

      if(this.hypixelProfile != null) {
         var2.setTag("hypixel", this.hypixelProfile.asNBTCompound());
      }

      var2.setTag("profile", NBTUtil.writeGameProfile(new NBTTagCompound(), this.player.getGameProfile()));
      return var2;
   }

   public String toString() {
      CI.b();
      StringBuilder var2 = new StringBuilder("Username: " + this.player.getGameProfile().getName());
      if(this.hypixelProfile != null) {
         String var3 = this.hypixelProfile.getRank();
         int var4 = this.hypixelProfile.getLevel();
         if(var4 > 1) {
            var2.append(" | ").append(var4).append(" Lvl");
         }

         if(var3 != null && !var3.equalsIgnoreCase("default")) {
            var2.append(" | ").append(var3);
         }
      }

      return var2.toString();
   }

   public AltCredential getCredential() {
      return this.credential;
   }

   public double getNetworkLevel() {
      return this.networkLevel;
   }

   public String getRank() {
      return this.rank;
   }

   public void setInvalid(boolean var1) {
      this.invalid = var1;
   }

   public FakeEntityPlayer getPlayer() {
      return this.player;
   }

   public HypixelProfile getHypixelProfile() {
      return this.hypixelProfile;
   }

   private ResourceLocation lambda$setGameProfile$3(Minecraft var1, MinecraftProfileTexture var2, GameProfile var3) throws Exception {
      return var1.V().a(var2, Type.SKIN, this::lambda$null$2);
   }

   private void lambda$null$2(GameProfile var1, Type var2, ResourceLocation var3, MinecraftProfileTexture var4) {
      this.setupPlayer(var1, var3);
   }

   private void lambda$logIn$1(Session var1, Throwable var2) {
      int[] var3 = CI.b();
      Novoline.getLogger().warn("An error occurred while logging in!", var2);
   }

   private Session lambda$logIn$0() {
      CI.b();
      Session var2 = null;
      if(!this.isLoggingIn() && !this.isLoginSuccessful()) {
         this.setLoggingIn(true);
         var2 = (new AltLoginThread(this.credential, new Alt$1(this))).run();
         this.setLoggingIn(false);
         this.alpha = 255.0F;
         this.animationX = 0.0F;
      } else {
         if(this.isLoggingIn()) {
            if(System.currentTimeMillis() <= this.lastTimeAlreadyLogged + 150L) {
               return var2;
            }

            Novoline.getInstance().getNotificationManager().pop("Already trying logging in!", NotificationType.ERROR);
            this.lastTimeAlreadyLogged = System.currentTimeMillis();
         }

         if(this.isLoginSuccessful() && System.currentTimeMillis() > this.lastTimeAlreadyLogged + 150L) {
            Novoline.getInstance().getNotificationManager().pop("Already logged in!", NotificationType.ERROR);
            this.lastTimeAlreadyLogged = System.currentTimeMillis();
         }
      }

      return var2;
   }

   static AltRepositoryGUI access$000(Alt var0) {
      return var0.repository;
   }

   static void access$100(Alt var0, boolean var1) {
      var0.setLoginProperty(var1);
   }

   static HypixelProfile access$200(Alt var0) {
      return var0.hypixelProfile;
   }

   static String access$302(Alt var0, String var1) {
      return var0.rank = var1;
   }

   static double access$402(Alt var0, double var1) {
      return var0.networkLevel = var1;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
