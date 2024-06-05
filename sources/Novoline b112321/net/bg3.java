package net;

import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketVehicleMove;

public class bg3 {
   private static int b;

   public static void a(INetHandlerPlayServer var0, C08PacketPlayerBlockPlacement var1) {
      var0.processPlayerBlockPlacement(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0APacketAnimation var1) {
      var0.handleAnimation(var1);
   }

   public static void a(INetHandlerPlayServer var0, C13PacketPlayerAbilities var1) {
      var0.processPlayerAbilities(var1);
   }

   public static void a(INetHandlerPlayServer var0, C10PacketCreativeInventoryAction var1) {
      var0.processCreativeInventoryAction(var1);
   }

   public static void a(INetHandlerPlayServer var0, C07PacketPlayerDigging var1) {
      var0.processPlayerDigging(var1);
   }

   public static void a(INetHandlerPlayServer var0, C14PacketTabComplete var1) {
      var0.processTabComplete(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0DPacketCloseWindow var1) {
      var0.processCloseWindow(var1);
   }

   public static void a(INetHandlerPlayServer var0, C03PacketPlayer var1) {
      var0.processPlayer(var1);
   }

   public static void a(INetHandlerPlayServer var0, C15PacketClientSettings var1) {
      var0.processClientSettings(var1);
   }

   public static void a(INetHandlerPlayServer var0, C17PacketCustomPayload var1) {
      var0.processVanilla250Packet(var1);
   }

   public static void a(INetHandlerPlayServer var0, CPacketVehicleMove var1) {
      var0.processVehicleMove(var1);
   }

   public static void a(INetHandlerPlayServer var0, C19PacketResourcePackStatus var1) {
      var0.handleResourcePackStatus(var1);
   }

   public static void a(INetHandlerPlayServer var0, C01PacketChatMessage var1) {
      var0.processChatMessage(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0BPacketEntityAction var1) {
      var0.processEntityAction(var1);
   }

   public static void a(INetHandlerPlayServer var0, C12PacketUpdateSign var1) {
      var0.processUpdateSign(var1);
   }

   public static void a(INetHandlerPlayServer var0, C00PacketKeepAlive var1) {
      var0.processKeepAlive(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0EPacketClickWindow var1) {
      var0.processClickWindow(var1);
   }

   public static void a(INetHandlerPlayServer var0, C18PacketSpectate var1) {
      var0.handleSpectate(var1);
   }

   public static void a(INetHandlerPlayServer var0, C11PacketEnchantItem var1) {
      var0.processEnchantItem(var1);
   }

   public static void a(INetHandlerPlayServer var0, C09PacketHeldItemChange var1) {
      var0.processHeldItemChange(var1);
   }

   public static void a(INetHandlerPlayServer var0, C02PacketUseEntity var1) {
      var0.processUseEntity(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0FPacketConfirmTransaction var1) {
      var0.processConfirmTransaction(var1);
   }

   public static void a(INetHandlerPlayServer var0, C16PacketClientStatus var1) {
      var0.processClientStatus(var1);
   }

   public static void a(INetHandlerPlayServer var0, C0CPacketInput var1) {
      var0.processInput(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 65;
   }

   static {
      if(b() == 0) {
         b(71);
      }

   }
}
