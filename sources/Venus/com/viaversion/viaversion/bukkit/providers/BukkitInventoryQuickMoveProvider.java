/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
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

public class BukkitInventoryQuickMoveProvider
extends InventoryQuickMoveProvider {
    private final Map<UUID, BukkitInventoryUpdateTask> updateTasks = new ConcurrentHashMap<UUID, BukkitInventoryUpdateTask>();
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
    public boolean registerQuickMoveAction(short s, short s2, short s3, UserConnection userConnection) {
        boolean bl;
        int n;
        if (!this.supported) {
            return true;
        }
        if (s2 < 0) {
            return true;
        }
        if (s == 0 && s2 >= 36 && s2 <= 45 && (n = Via.getAPI().getServerVersion().lowestSupportedVersion()) == ProtocolVersion.v1_8.getVersion()) {
            return true;
        }
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        UUID uUID = protocolInfo.getUuid();
        BukkitInventoryUpdateTask bukkitInventoryUpdateTask = this.updateTasks.get(uUID);
        boolean bl2 = bl = bukkitInventoryUpdateTask != null;
        if (!bl) {
            bukkitInventoryUpdateTask = new BukkitInventoryUpdateTask(this, uUID);
            this.updateTasks.put(uUID, bukkitInventoryUpdateTask);
        }
        bukkitInventoryUpdateTask.addItem(s, s2, s3);
        if (!bl && Via.getPlatform().isPluginEnabled()) {
            Via.getPlatform().runSync(bukkitInventoryUpdateTask);
        }
        return false;
    }

    public Object buildWindowClickPacket(Player player, ItemTransaction itemTransaction) {
        String string;
        int n;
        InventoryType inventoryType;
        if (!this.supported) {
            return null;
        }
        InventoryView inventoryView = player.getOpenInventory();
        short s = itemTransaction.getSlotId();
        Inventory inventory = inventoryView.getTopInventory();
        InventoryType inventoryType2 = inventoryType = inventory == null ? null : inventory.getType();
        if (inventoryType != null && (n = Via.getAPI().getServerVersion().lowestSupportedVersion()) == ProtocolVersion.v1_8.getVersion() && inventoryType == InventoryType.BREWING && s >= 5 && s <= 40) {
            s = (short)(s - 1);
        }
        ItemStack itemStack = null;
        if (s <= inventoryView.countSlots()) {
            itemStack = inventoryView.getItem((int)s);
        } else {
            string = "Too many inventory slots: slotId: " + s + " invSlotCount: " + inventoryView.countSlots() + " invType: " + inventoryView.getType() + " topInvType: " + inventoryType;
            Via.getPlatform().getLogger().severe("Failed to get an item to create a window click packet. Please report this issue to the ViaVersion Github: " + string);
        }
        string = null;
        try {
            string = (String)this.windowClickPacketClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Object object = itemStack == null ? null : this.nmsItemMethod.invoke(null, itemStack);
            ReflectionUtil.set(string, "a", itemTransaction.getWindowId());
            ReflectionUtil.set(string, "slot", s);
            ReflectionUtil.set(string, "button", 0);
            ReflectionUtil.set(string, "d", itemTransaction.getActionId());
            ReflectionUtil.set(string, "item", object);
            int n2 = Via.getAPI().getServerVersion().lowestSupportedVersion();
            if (n2 == ProtocolVersion.v1_8.getVersion()) {
                ReflectionUtil.set(string, "shift", 1);
            } else if (n2 >= ProtocolVersion.v1_9.getVersion()) {
                ReflectionUtil.set(string, "shift", this.clickTypeEnum);
            }
        } catch (Exception exception) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to create a window click packet. Please report this issue to the ViaVersion Github: " + exception.getMessage(), exception);
        }
        return string;
    }

    public boolean sendPacketToServer(Player player, Object object) {
        if (object == null) {
            return false;
        }
        try {
            Object object2 = this.craftPlayerHandle.invoke(player, new Object[0]);
            Object object3 = this.connection.get(object2);
            this.packetMethod.invoke(object3, object);
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return true;
        }
        return false;
    }

    public void onTaskExecuted(UUID uUID) {
        this.updateTasks.remove(uUID);
    }

    private void setupReflection() {
        if (!this.supported) {
            return;
        }
        try {
            Class<?> clazz;
            this.windowClickPacketClass = NMSUtil.nms("PacketPlayInWindowClick");
            int n = Via.getAPI().getServerVersion().lowestSupportedVersion();
            if (n >= ProtocolVersion.v1_9.getVersion()) {
                clazz = NMSUtil.nms("InventoryClickType");
                ?[] objArray = clazz.getEnumConstants();
                this.clickTypeEnum = objArray[0];
            }
            clazz = NMSUtil.obc("inventory.CraftItemStack");
            this.nmsItemMethod = clazz.getDeclaredMethod("asNMSCopy", ItemStack.class);
        } catch (Exception exception) {
            throw new RuntimeException("Couldn't find required inventory classes", exception);
        }
        try {
            this.craftPlayerHandle = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            throw new RuntimeException("Couldn't find CraftPlayer", reflectiveOperationException);
        }
        try {
            this.connection = NMSUtil.nms("EntityPlayer").getDeclaredField("playerConnection");
        } catch (ClassNotFoundException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException("Couldn't find Player Connection", reflectiveOperationException);
        }
        try {
            this.packetMethod = NMSUtil.nms("PlayerConnection").getDeclaredMethod("a", this.windowClickPacketClass);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            throw new RuntimeException("Couldn't find CraftPlayer", reflectiveOperationException);
        }
    }

    private boolean isSupported() {
        int n = Via.getAPI().getServerVersion().lowestSupportedVersion();
        return n < ProtocolVersion.v1_8.getVersion() || n > ProtocolVersion.v1_11_1.getVersion();
    }
}

