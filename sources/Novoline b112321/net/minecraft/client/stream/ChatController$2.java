package net.minecraft.client.stream;

import net.minecraft.client.stream.ChatController$EnumChannelState;
import net.minecraft.client.stream.ChatController$EnumEmoticonMode;
import tv.twitch.chat.ChatEvent;

// $FF: synthetic class
class ChatController$2 {
   static final int[] $SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode;
   static final int[] $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState;
   static final int[] $SwitchMap$tv$twitch$chat$ChatEvent = new int[ChatEvent.values().length];

   static {
      try {
         $SwitchMap$tv$twitch$chat$ChatEvent[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = 1;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$tv$twitch$chat$ChatEvent[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = 2;
      } catch (NoSuchFieldError var9) {
         ;
      }

      $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState = new int[ChatController$EnumChannelState.values().length];

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[ChatController$EnumChannelState.Connected.ordinal()] = 1;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[ChatController$EnumChannelState.Connecting.ordinal()] = 2;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[ChatController$EnumChannelState.Created.ordinal()] = 3;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[ChatController$EnumChannelState.Disconnected.ordinal()] = 4;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[ChatController$EnumChannelState.Disconnecting.ordinal()] = 5;
      } catch (NoSuchFieldError var4) {
         ;
      }

      $SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode = new int[ChatController$EnumEmoticonMode.values().length];

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode[ChatController$EnumEmoticonMode.None.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode[ChatController$EnumEmoticonMode.Url.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode[ChatController$EnumEmoticonMode.TextureAtlas.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
