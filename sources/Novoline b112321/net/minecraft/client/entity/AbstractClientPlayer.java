package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import java.io.File;
import net.Uv;
import net.aE2;
import net.aTu;
import net.aeQ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;
import optifine.CapeUtils;
import optifine.Config;
import optifine.Reflector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractClientPlayer extends EntityPlayer {
   private NetworkPlayerInfo bN;
   private ResourceLocation locationOfCape = null;
   private String bM;

   public AbstractClientPlayer(World var1, GameProfile var2) {
      super(var1, var2);
      this.bM = var2.getName();
      if(this.bM != null && !this.bM.isEmpty()) {
         this.bM = StringUtils.stripControlCodes(this.bM);
      }

      CapeUtils.downloadCape(this);
      aE2.a(this);
   }

   @NotNull
   public static aeQ a(ResourceLocation var0, String var1) {
      Uv var2 = Minecraft.getMinecraft().ab();
      ITextureObject var3 = var2.b(var0);
      aeQ var4 = new aeQ((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[]{StringUtils.stripControlCodes(var1)}), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(var1)), new aTu());
      var2.a((ResourceLocation)var0, (ITextureObject)var4);
      return (aeQ)var4;
   }

   public boolean hasPlayerInfo() {
      return this.getPlayerInfo() != null;
   }

   public boolean isSpectator() {
      NetworkPlayerInfo var1 = NetHandlerPlayClient.a(this.getGameProfile().getId());
      return var1.getGameType() == WorldSettings$GameType.SPECTATOR;
   }

   public boolean hasSkin() {
      NetworkPlayerInfo var1 = this.getPlayerInfo();
      return var1.hasLocationSkin();
   }

   public void a(NetworkPlayerInfo var1) {
      this.bN = var1;
   }

   public ResourceLocation getLocationSkin() {
      NetworkPlayerInfo var1 = this.getPlayerInfo();
      return DefaultPlayerSkin.getDefaultSkin(this.z());
   }

   public ResourceLocation getLocationCape() {
      if(!Config.isShowCapes()) {
         return null;
      } else if(this.locationOfCape != null) {
         return this.locationOfCape;
      } else {
         NetworkPlayerInfo var1 = this.getPlayerInfo();
         return null;
      }
   }

   protected NetworkPlayerInfo getPlayerInfo() {
      if(this.bN == null) {
         this.bN = NetHandlerPlayClient.a(this.z());
      }

      return this.bN;
   }

   @NotNull
   public static ResourceLocation getLocationSkin(String var0) {
      return new ResourceLocation("skins/" + StringUtils.stripControlCodes(var0));
   }

   public String n() {
      NetworkPlayerInfo var1 = this.getPlayerInfo();
      return DefaultPlayerSkin.getSkinType(this.z());
   }

   public float getFovModifier() {
      float var1 = 1.0F;
      if(this.capabilities.e()) {
         var1 *= 1.1F;
      }

      IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      var1 = (float)((double)var1 * ((var2.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
      if(this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(var1) || Float.isInfinite(var1)) {
         var1 = 1.0F;
      }

      if(this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
         int var3 = this.getItemInUseDuration();
         float var4 = (float)var3 / 20.0F;
         if(var4 > 1.0F) {
            var4 = 1.0F;
         } else {
            var4 = var4 * var4;
         }

         var1 *= 1.0F - var4 * 0.15F;
      }

      return Reflector.ac.d()?Reflector.g(Reflector.ac, new Object[]{this, Float.valueOf(var1)}):var1;
   }

   public String getNameClear() {
      return this.bM;
   }

   public ResourceLocation getLocationOfCape() {
      return this.locationOfCape;
   }

   public void setLocationOfCape(@Nullable ResourceLocation var1) {
      this.locationOfCape = var1;
   }
}
