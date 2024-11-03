package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1.BukkitInventoryUpdateTask;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.ItemTransaction;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class BukkitInventoryQuickMoveProvider extends InventoryQuickMoveProvider {
   private final Map<UUID, BukkitInventoryUpdateTask> updateTasks = new ConcurrentHashMap<>();
   private final boolean supported = this.isSupported();
   private Class<?> windowClickPacketClass;
   private Object clickTypeEnum;
   private Method nmsItemMethod;
   private Method craftPlayerHandle;
   private Field connection;
   private Method packetMethod;

   public BukkitInventoryQuickMoveProvider() {
      this.setupReflection();
   }

   @Override
   public boolean registerQuickMoveAction(short windowId, short slotId, short actionId, UserConnection userConnection) {
      if (!this.supported) {
         return false;
      } else if (slotId < 0) {
         return false;
      } else {
         if (windowId == 0 && slotId >= 36 && slotId <= 45) {
            int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
            if (protocolId == ProtocolVersion.v1_8.getVersion()) {
               return false;
            }
         }

         ProtocolInfo info = userConnection.getProtocolInfo();
         UUID uuid = info.getUuid();
         BukkitInventoryUpdateTask updateTask = this.updateTasks.get(uuid);
         boolean registered = updateTask != null;
         if (!registered) {
            updateTask = new BukkitInventoryUpdateTask(this, uuid);
            this.updateTasks.put(uuid, updateTask);
         }

         updateTask.addItem(windowId, slotId, actionId);
         if (!registered && Via.getPlatform().isPluginEnabled()) {
            Via.getPlatform().runSync(updateTask);
         }

         return true;
      }
   }

   public Object buildWindowClickPacket(Player p, ItemTransaction storage) {
      if (!this.supported) {
         return null;
      } else {
         InventoryView inv = p.getOpenInventory();
         short slotId = storage.getSlotId();
         Inventory tinv = inv.getTopInventory();
         InventoryType tinvtype = tinv == null ? null : tinv.getType();
         if (tinvtype != null) {
            int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
            if (protocolId == ProtocolVersion.v1_8.getVersion() && tinvtype == InventoryType.BREWING && slotId >= 5 && slotId <= 40) {
               slotId--;
            }
         }

         ItemStack itemstack = null;
         if (slotId <= inv.countSlots()) {
            itemstack = inv.getItem(slotId);
         } else {
            String cause = "Too many inventory slots: slotId: "
               + slotId
               + " invSlotCount: "
               + inv.countSlots()
               + " invType: "
               + inv.getType()
               + " topInvType: "
               + tinvtype;
            Via.getPlatform()
               .getLogger()
               .severe("Failed to get an item to create a window click packet. Please report this issue to the ViaVersion Github: " + cause);
         }

         Object packet = null;

         try {
            packet = this.windowClickPacketClass.getDeclaredConstructor().newInstance();
            Object nmsItem = itemstack == null ? null : this.nmsItemMethod.invoke(null, itemstack);
            ReflectionUtil.set(packet, "a", Integer.valueOf(storage.getWindowId()));
            ReflectionUtil.set(packet, "slot", Integer.valueOf(slotId));
            ReflectionUtil.set(packet, "button", 0);
            ReflectionUtil.set(packet, "d", storage.getActionId());
            ReflectionUtil.set(packet, "item", nmsItem);
            int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
            if (protocolId == ProtocolVersion.v1_8.getVersion()) {
               ReflectionUtil.set(packet, "shift", 1);
            } else if (protocolId >= ProtocolVersion.v1_9.getVersion()) {
               ReflectionUtil.set(packet, "shift", this.clickTypeEnum);
            }
         } catch (Exception var11) {
            Via.getPlatform()
               .getLogger()
               .log(
                  Level.SEVERE,
                  "Failed to create a window click packet. Please report this issue to the ViaVersion Github: " + var11.getMessage(),
                  (Throwable)var11
               );
         }

         return packet;
      }
   }

   public boolean sendPacketToServer(Player p, Object packet) {
      if (packet == null) {
         return true;
      } else {
         try {
            Object entityPlayer = this.craftPlayerHandle.invoke(p);
            Object playerConnection = this.connection.get(entityPlayer);
            this.packetMethod.invoke(playerConnection, packet);
            return true;
         } catch (InvocationTargetException | IllegalAccessException var5) {
            var5.printStackTrace();
            return false;
         }
      }
   }

   public void onTaskExecuted(UUID uuid) {
      this.updateTasks.remove(uuid);
   }

   private void setupReflection() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.struct.gen.VarType.isGeneric()" because "newRet" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.getInferredExprType(InvocationExprent.java:634)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:966)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.toJava(AssignmentExprent.java:154)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.CatchStatement.toJava(CatchStatement.java:166)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.supported Z
      // 04: ifne 08
      // 07: return
      // 08: aload 0
      // 09: ldc_w "PacketPlayInWindowClick"
      // 0c: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.nms (Ljava/lang/String;)Ljava/lang/Class;
      // 0f: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.windowClickPacketClass Ljava/lang/Class;
      // 12: invokestatic com/viaversion/viaversion/api/Via.getAPI ()Lcom/viaversion/viaversion/api/ViaAPI;
      // 15: invokeinterface com/viaversion/viaversion/api/ViaAPI.getServerVersion ()Lcom/viaversion/viaversion/api/protocol/version/ServerProtocolVersion; 1
      // 1a: invokeinterface com/viaversion/viaversion/api/protocol/version/ServerProtocolVersion.lowestSupportedVersion ()I 1
      // 1f: istore 1
      // 20: iload 1
      // 21: getstatic com/viaversion/viaversion/api/protocol/version/ProtocolVersion.v1_9 Lcom/viaversion/viaversion/api/protocol/version/ProtocolVersion;
      // 24: invokevirtual com/viaversion/viaversion/api/protocol/version/ProtocolVersion.getVersion ()I
      // 27: if_icmplt 3d
      // 2a: ldc_w "InventoryClickType"
      // 2d: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.nms (Ljava/lang/String;)Ljava/lang/Class;
      // 30: astore 2
      // 31: aload 2
      // 32: invokevirtual java/lang/Class.getEnumConstants ()[Ljava/lang/Object;
      // 35: astore 3
      // 36: aload 0
      // 37: aload 3
      // 38: bipush 1
      // 39: aaload
      // 3a: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.clickTypeEnum Ljava/lang/Object;
      // 3d: ldc_w "inventory.CraftItemStack"
      // 40: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.obc (Ljava/lang/String;)Ljava/lang/Class;
      // 43: astore 2
      // 44: aload 0
      // 45: aload 2
      // 46: ldc_w "asNMSCopy"
      // 49: bipush 1
      // 4a: anewarray 216
      // 4d: dup
      // 4e: bipush 0
      // 4f: ldc org/bukkit/inventory/ItemStack
      // 51: aastore
      // 52: invokevirtual java/lang/Class.getDeclaredMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // 55: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.nmsItemMethod Ljava/lang/reflect/Method;
      // 58: goto 68
      // 5b: astore 1
      // 5c: new java/lang/RuntimeException
      // 5f: dup
      // 60: ldc_w "Couldn't find required inventory classes"
      // 63: aload 1
      // 64: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 67: athrow
      // 68: aload 0
      // 69: ldc_w "entity.CraftPlayer"
      // 6c: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.obc (Ljava/lang/String;)Ljava/lang/Class;
      // 6f: ldc_w "getHandle"
      // 72: bipush 0
      // 73: anewarray 216
      // 76: invokevirtual java/lang/Class.getDeclaredMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // 79: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.craftPlayerHandle Ljava/lang/reflect/Method;
      // 7c: goto 8c
      // 7f: astore 1
      // 80: new java/lang/RuntimeException
      // 83: dup
      // 84: ldc_w "Couldn't find CraftPlayer"
      // 87: aload 1
      // 88: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 8b: athrow
      // 8c: aload 0
      // 8d: ldc_w "EntityPlayer"
      // 90: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.nms (Ljava/lang/String;)Ljava/lang/Class;
      // 93: ldc_w "playerConnection"
      // 96: invokevirtual java/lang/Class.getDeclaredField (Ljava/lang/String;)Ljava/lang/reflect/Field;
      // 99: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.connection Ljava/lang/reflect/Field;
      // 9c: goto ac
      // 9f: astore 1
      // a0: new java/lang/RuntimeException
      // a3: dup
      // a4: ldc_w "Couldn't find Player Connection"
      // a7: aload 1
      // a8: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // ab: athrow
      // ac: aload 0
      // ad: ldc_w "PlayerConnection"
      // b0: invokestatic com/viaversion/viaversion/bukkit/util/NMSUtil.nms (Ljava/lang/String;)Ljava/lang/Class;
      // b3: ldc "a"
      // b5: bipush 1
      // b6: anewarray 216
      // b9: dup
      // ba: bipush 0
      // bb: aload 0
      // bc: getfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.windowClickPacketClass Ljava/lang/Class;
      // bf: aastore
      // c0: invokevirtual java/lang/Class.getDeclaredMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // c3: putfield com/viaversion/viaversion/bukkit/providers/BukkitInventoryQuickMoveProvider.packetMethod Ljava/lang/reflect/Method;
      // c6: goto d6
      // c9: astore 1
      // ca: new java/lang/RuntimeException
      // cd: dup
      // ce: ldc_w "Couldn't find CraftPlayer"
      // d1: aload 1
      // d2: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // d5: athrow
      // d6: return
   }

   private boolean isSupported() {
      int protocolId = Via.getAPI().getServerVersion().lowestSupportedVersion();
      return protocolId >= ProtocolVersion.v1_8.getVersion() && protocolId <= ProtocolVersion.v1_11_1.getVersion();
   }
}
