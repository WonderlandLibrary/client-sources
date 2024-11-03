package net.augustus.modules.misc;

import net.augustus.events.EventReadPacket;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.utils.EventHandler;
import net.lenni0451.eventapi.manager.EventManager;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostDisabler extends Module {


   public BooleanValue debug = new BooleanValue(8522, "Debug", this, false);

   public static boolean postValue = true;
   private static boolean lastResult = false;
   public static List<Packet<INetHandler>> storedPackets = new CopyOnWriteArrayList<>();
   public static ConcurrentLinkedDeque<Integer> pingPackets = new ConcurrentLinkedDeque<>();
   static PostDisabler INSTANCE;
   private boolean shouldProcess = false;

   public PostDisabler() {
      super("PostDis", Color.red, Categorys.MISC);


      PostDisabler.INSTANCE = this;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      storedPackets.clear();
      pingPackets.clear();
      lastResult = false;
      postValue = true;
      shouldProcess = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      storedPackets.clear();
      pingPackets.clear();
      lastResult = false;
      postValue = true;
      shouldProcess = false;
   }

   @EventTarget
   public void onUpdate(final EventUpdate eventUpdate) {

      if (this.debug.getBoolean())
         mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("StoredPackets : " + storedPackets));
      if (!getGrimPost()) {
         processPackets();
      }
      if (shouldProcess) {
         processPackets();
         shouldProcess = false;
      }



   }

   public static boolean getGrimPost() {
      final boolean result = PostDisabler.INSTANCE != null && PostDisabler.INSTANCE.isToggled() && PostDisabler.mc.thePlayer != null && PostDisabler.mc.thePlayer.isEntityAlive() && PostDisabler.mc.thePlayer.ticksExisted >= 10 && postValue && !(PostDisabler.mc.currentScreen instanceof GuiDownloadTerrain);
      if (PostDisabler.lastResult && !result) {
         PostDisabler.lastResult = false;
         PostDisabler.mc.addScheduledTask(PostDisabler::processPackets);
      }
      return PostDisabler.lastResult = result;
   }

   public static boolean grimPostDelay(final Packet<?> packet) {
      if (!postValue) return false;
      if (PostDisabler.mc.thePlayer == null) {
         return false;
      }
      if (PostDisabler.mc.currentScreen instanceof GuiDownloadTerrain) {
         return false;
      }
      if (packet instanceof S00PacketServerInfo) {
         return false;
      }
      if (packet instanceof S01PacketEncryptionRequest) {
         return false;
      }
      if (packet instanceof S38PacketPlayerListItem) {
         return false;
      }
      if (packet instanceof S00PacketDisconnect) {
         return false;
      }
      if (packet instanceof S40PacketDisconnect) {
         return false;
      }
      if (packet instanceof S21PacketChunkData) {
         return false;
      }
      if (packet instanceof S01PacketPong) {
         return false;
      }
      if (packet instanceof S44PacketWorldBorder) {
         return false;
      }
      if (packet instanceof S01PacketJoinGame) {
         return false;
      }
      if (packet instanceof S19PacketEntityHeadLook) {
         return false;
      }
      if (packet instanceof S3EPacketTeams) {
         return false;
      }
      if (packet instanceof S02PacketChat) {
         return false;
      }
      if (packet instanceof S2FPacketSetSlot) {
         return false;
      }
      if (packet instanceof S1CPacketEntityMetadata) {
         return false;
      }
      if (packet instanceof S20PacketEntityProperties) {
         return false;
      }
      if (packet instanceof S35PacketUpdateTileEntity) {
         return false;
      }
      if (packet instanceof S03PacketTimeUpdate) {
         return false;
      }
      if (packet instanceof S47PacketPlayerListHeaderFooter) {
         return false;
      }
      if (packet instanceof S12PacketEntityVelocity) {
         final S12PacketEntityVelocity sPacketEntityVelocity = (S12PacketEntityVelocity) packet;
         return sPacketEntityVelocity.getEntityID() == PostDisabler.mc.thePlayer.getEntityId();
      }
      return packet instanceof S27PacketExplosion || packet instanceof S32PacketConfirmTransaction || packet instanceof S08PacketPlayerPosLook || packet instanceof S18PacketEntityTeleport || packet instanceof S19PacketEntityStatus || packet instanceof S04PacketEntityEquipment || packet instanceof S23PacketBlockChange || packet instanceof S22PacketMultiBlockChange || packet instanceof S13PacketDestroyEntities || packet instanceof S00PacketKeepAlive || packet instanceof S06PacketUpdateHealth || packet instanceof S14PacketEntity || packet instanceof S0FPacketSpawnMob || packet instanceof S2DPacketOpenWindow || packet instanceof S30PacketWindowItems || packet instanceof S3FPacketCustomPayload || packet instanceof S2EPacketCloseWindow;
   }

   public static void processPackets() {
      if (!PostDisabler.storedPackets.isEmpty()) {
         for (final Packet<INetHandler> packet : PostDisabler.storedPackets) {
            final EventReadPacket eventReadPacket = new EventReadPacket(packet, PostDisabler.mc.getNetHandler(), EnumPacketDirection.CLIENTBOUND);
            EventHandler.call(eventReadPacket);
            if (eventReadPacket.isCanceled()) {
               continue;
            }
            if (packet == null) continue;

            packet.processPacket(PostDisabler.mc.getNetHandler());
         }
         PostDisabler.storedPackets.clear();

      }
   }

   public static void fixC0F(final C0FPacketConfirmTransaction packet) {
      final int id = packet.getUid();
      if (id >= 0 || PostDisabler.pingPackets.isEmpty()) {

         mc.thePlayer.sendQueue.addToSendQueueDirect(packet);
      } else {
         do {
            final int current = PostDisabler.pingPackets.getFirst();
            mc.thePlayer.sendQueue.addToSendQueueDirect(new C0FPacketConfirmTransaction(packet.getWindowId(), (short) current, true));
            PostDisabler.pingPackets.pollFirst();
            if (current == id) {
               break;
            }
         } while (!PostDisabler.pingPackets.isEmpty());
      }
   }
   public void netWorkManagerHook(Packet packet , INetHandler packetListener , EnumPacketDirection direction) {

         if (getGrimPost()) {
            try {


               if (packet instanceof S3FPacketCustomPayload) {
                  EventReadPacket eventReadPacket = new EventReadPacket(packet, packetListener, direction);
                  EventManager.call(eventReadPacket);
                  if (eventReadPacket.isCanceled()) {
                     return;
                  }
                  packet.processPacket(packetListener);
               } else if (grimPostDelay(packet)) {
                  mc.addScheduledTask(() -> PostDisabler.storedPackets.add(packet));
               } else {
                  EventReadPacket eventReadPacket = new EventReadPacket(packet, packetListener, direction);
                  EventHandler.call(eventReadPacket);
                  if (eventReadPacket.isCanceled()) {
                     return;
                  }
                  packet.processPacket(packetListener);
               }


            } catch (Exception ignored) {
            }
         }

   }


}
