package net.minecraft.client.network;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

public class NetworkPlayerInfo {
   private long field_178871_k = 0L;
   private boolean field_178864_d = false;
   private int responseTime;
   private long field_178868_l = 0L;
   private int field_178870_j = 0;
   private IChatComponent field_178872_h;
   private int field_178873_i = 0;
   private ResourceLocation field_178865_e;
   private final GameProfile field_178867_a;
   private String field_178863_g;
   private WorldSettings.GameType gameType;
   private static final String __OBFID = "CL_00000888";
   private long field_178869_m = 0L;
   private ResourceLocation field_178862_f;

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public void func_178843_c(long var1) {
      this.field_178869_m = var1;
   }

   static String access$2(NetworkPlayerInfo var0) {
      return var0.field_178863_g;
   }

   public ResourceLocation func_178837_g() {
      if (this.field_178865_e == null) {
         this.func_178841_j();
      }

      return (ResourceLocation)Objects.firstNonNull(this.field_178865_e, DefaultPlayerSkin.func_177334_a(this.field_178867_a.getId()));
   }

   public long func_178858_o() {
      return this.field_178868_l;
   }

   public int getResponseTime() {
      return this.responseTime;
   }

   public void func_178857_c(int var1) {
      this.field_178870_j = var1;
   }

   public int func_178835_l() {
      return this.field_178873_i;
   }

   public boolean func_178856_e() {
      return this.field_178865_e != null;
   }

   public void func_178844_b(long var1) {
      this.field_178868_l = var1;
   }

   protected void func_178838_a(int var1) {
      this.responseTime = var1;
   }

   public ResourceLocation func_178861_h() {
      if (this.field_178862_f == null) {
         this.func_178841_j();
      }

      return this.field_178862_f;
   }

   static void access$0(NetworkPlayerInfo var0, ResourceLocation var1) {
      var0.field_178865_e = var1;
   }

   public long func_178847_n() {
      return this.field_178871_k;
   }

   public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData var1) {
      this.field_178867_a = var1.func_179962_a();
      this.gameType = var1.func_179960_c();
      this.responseTime = var1.func_179963_b();
   }

   public IChatComponent func_178854_k() {
      return this.field_178872_h;
   }

   public long func_178855_p() {
      return this.field_178869_m;
   }

   public NetworkPlayerInfo(GameProfile var1) {
      this.field_178867_a = var1;
   }

   public String func_178851_f() {
      return this.field_178863_g == null ? DefaultPlayerSkin.func_177332_b(this.field_178867_a.getId()) : this.field_178863_g;
   }

   public ScorePlayerTeam func_178850_i() {
      return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.func_178845_a().getName());
   }

   public int func_178860_m() {
      return this.field_178870_j;
   }

   public void func_178846_a(long var1) {
      this.field_178871_k = var1;
   }

   protected void func_178839_a(WorldSettings.GameType var1) {
      this.gameType = var1;
   }

   public GameProfile func_178845_a() {
      return this.field_178867_a;
   }

   protected void func_178841_j() {
      synchronized(this) {
         if (!this.field_178864_d) {
            this.field_178864_d = true;
            Minecraft.getMinecraft().getSkinManager().func_152790_a(this.field_178867_a, new SkinManager.SkinAvailableCallback(this) {
               private static final String __OBFID = "CL_00002619";
               final NetworkPlayerInfo this$0;

               public void func_180521_a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3) {
                  switch(var1) {
                  case SKIN:
                     NetworkPlayerInfo.access$0(this.this$0, var2);
                     NetworkPlayerInfo.access$1(this.this$0, var3.getMetadata("model"));
                     if (NetworkPlayerInfo.access$2(this.this$0) == null) {
                        NetworkPlayerInfo.access$1(this.this$0, "default");
                     }
                     break;
                  case CAPE:
                     NetworkPlayerInfo.access$3(this.this$0, var2);
                  }

               }

               {
                  this.this$0 = var1;
               }
            }, true);
         }

      }
   }

   public void func_178836_b(int var1) {
      this.field_178873_i = var1;
   }

   static void access$1(NetworkPlayerInfo var0, String var1) {
      var0.field_178863_g = var1;
   }

   static void access$3(NetworkPlayerInfo var0, ResourceLocation var1) {
      var0.field_178862_f = var1;
   }

   public void func_178859_a(IChatComponent var1) {
      this.field_178872_h = var1;
   }

   static final class SwitchType {
      private static final String __OBFID = "CL_00002618";
      static final int[] field_178875_a = new int[Type.values().length];

      static {
         try {
            field_178875_a[Type.SKIN.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178875_a[Type.CAPE.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
