package net.minecraft.client.network;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

// $FF: synthetic class
class NetworkPlayerInfo$2 {
   static final int[] $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type = new int[Type.values().length];

   static {
      try {
         $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[Type.SKIN.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[Type.CAPE.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
