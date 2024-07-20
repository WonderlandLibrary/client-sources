/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;

public class StormHVHHelper
extends Module {
    public static StormHVHHelper get;
    Settings AutoDuel;
    Settings AutoResellDuel;
    Settings OnlySneakDuel;
    Settings SmartDuel;
    Settings DuelType;
    Settings AutoKitSelect;
    Settings KitSelect;
    Settings NoPlayersOnSpawn;
    Settings NoSoundsIssue;
    private static final BlockPos pos;
    boolean goSword = false;
    boolean goDuel = false;
    TimerHelper waitResell = new TimerHelper();

    public StormHVHHelper() {
        super("StormHVHHelper", 0, Module.Category.MISC);
        this.AutoDuel = new Settings("AutoDuel", true, (Module)this);
        this.settings.add(this.AutoDuel);
        this.AutoResellDuel = new Settings("AutoResellDuel", true, (Module)this, () -> this.AutoDuel.bValue);
        this.settings.add(this.AutoResellDuel);
        this.OnlySneakDuel = new Settings("OnlySneakDuel", true, (Module)this, () -> this.AutoDuel.bValue);
        this.settings.add(this.OnlySneakDuel);
        this.SmartDuel = new Settings("SmartDuel", true, (Module)this, () -> this.AutoDuel.bValue);
        this.settings.add(this.SmartDuel);
        String[] kits = new String[]{"Sunrise", "Jetmine", "Totems", "NoTotems", "Crystals", "Thorns", "Web", "Axe", "Normal", "NoDebuff"};
        this.DuelType = new Settings("DuelType", kits[4], this, kits, () -> this.AutoDuel.bValue);
        this.settings.add(this.DuelType);
        this.AutoKitSelect = new Settings("AutoKitSelect", true, (Module)this);
        this.settings.add(this.AutoKitSelect);
        this.KitSelect = new Settings("KitSelect", "Duped", this, new String[]{"Duped", "Standart"}, () -> this.AutoKitSelect.bValue);
        this.settings.add(this.KitSelect);
        this.NoPlayersOnSpawn = new Settings("NoPlayersOnSpawn", true, (Module)this);
        this.settings.add(this.NoPlayersOnSpawn);
        this.NoSoundsIssue = new Settings("NoSoundsIssue", true, (Module)this);
        this.settings.add(this.NoSoundsIssue);
        get = this;
    }

    public static boolean noRenderPlayersInWorld() {
        return get != null && StormHVHHelper.get.actived && StormHVHHelper.get.NoPlayersOnSpawn.bValue;
    }

    private boolean canSelectKit(boolean dupe) {
        return Minecraft.player.inventory.getStackInSlot(dupe ? 0 : 8).getItem() == Items.ENCHANTED_BOOK;
    }

    private void selectKitAuto(boolean dupe) {
        int slot;
        int n = slot = dupe ? 0 : 8;
        if (this.canSelectKit(dupe)) {
            if (Minecraft.player.inventory.currentItem != slot) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            }
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            if (Minecraft.player.inventory.currentItem != slot) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
            }
        }
    }

    private boolean isAutoDuel() {
        return this.AutoDuel.bValue;
    }

    private boolean canClickSword() {
        return Minecraft.player.inventory.getStackInSlot(0).getItem() == Items.IRON_SWORD;
    }

    private void clickSword() {
        if (this.canClickSword()) {
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(0));
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
        }
    }

    private boolean canClickRedstone() {
        return Minecraft.player.inventory.getStackInSlot(8).getItem() == Items.REDSTONE;
    }

    private void clickRedstone() {
        if (this.canClickRedstone()) {
            if (Minecraft.player.inventory.currentItem != 8) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(8));
            }
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            if (Minecraft.player.inventory.currentItem != 8) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
            }
        }
    }

    private boolean canClickSlot(String duelType, boolean smart) {
        return this.getSlotByDuel(duelType, smart) != -1;
    }

    private int getSlotByDuel(String duelType, boolean smart) {
        int slot = -1;
        int smartSlot = -1;
        for (int index = 0; index < Minecraft.player.openContainer.inventorySlots.size(); ++index) {
            ItemStack stack;
            Item item;
            if (!Minecraft.player.openContainer.inventorySlots.get(index).getHasStack() || !((item = (stack = Minecraft.player.openContainer.inventorySlots.get(index).getStack()).getItem()) instanceof ItemSkull) || stack.stackSize < 2) continue;
            smartSlot = index;
        }
        if (smart && smartSlot != -1) {
            slot = smartSlot;
        } else {
            switch (duelType) {
                case "Sunrise": {
                    slot = 11;
                    break;
                }
                case "Jetmine": {
                    slot = 12;
                    break;
                }
                case "Totems": {
                    slot = 13;
                    break;
                }
                case "NoTotems": {
                    slot = 14;
                    break;
                }
                case "Crystals": {
                    slot = 15;
                    break;
                }
                case "Thorns": {
                    slot = 20;
                    break;
                }
                case "Web": {
                    slot = 21;
                    break;
                }
                case "Axe": {
                    slot = 22;
                    break;
                }
                case "Normal": {
                    slot = 23;
                    break;
                }
                case "NoDebuff": {
                    slot = 24;
                }
            }
        }
        return slot;
    }

    private void clickSlot(int slot) {
        StormHVHHelper.mc.playerController.windowClick(Minecraft.player.openContainer.windowId, slot, 0, ClickType.PICKUP, Minecraft.player);
        Minecraft.player.closeScreen();
        StormHVHHelper.mc.currentScreen = null;
    }

    private boolean isInSelectDuelMenu() {
        boolean hasGlass = false;
        int countHead = 0;
        if (StormHVHHelper.mc.currentScreen instanceof GuiContainer && !(StormHVHHelper.mc.currentScreen instanceof GuiInventory) && Minecraft.player.openContainer instanceof ContainerChest && Minecraft.player.openContainer.inventorySlots.size() == 90) {
            for (int index = 0; index < Minecraft.player.openContainer.inventorySlots.size(); ++index) {
                if (!Minecraft.player.openContainer.inventorySlots.get(index).getHasStack()) continue;
                ItemStack stack = Minecraft.player.openContainer.inventorySlots.get(index).getStack();
                Item item = stack.getItem();
                if (index == 1 && item == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
                    hasGlass = true;
                }
                if (!(item instanceof ItemSkull)) continue;
                ++countHead;
            }
        }
        return countHead == 10 && hasGlass;
    }

    public static boolean isInStormServer() {
        return !mc.isSingleplayer() && mc.getCurrentServerData() != null && StormHVHHelper.mc.getCurrentServerData().serverIP != null && StormHVHHelper.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.stormhvh.su");
    }

    public static boolean isInStormSpawn() {
        return StormHVHHelper.mc.world.getBlockState(pos).getBlock() == Blocks.NOTEBLOCK;
    }

    @Override
    public void onUpdate() {
        if (StormHVHHelper.isInStormServer() && StormHVHHelper.isInStormSpawn() && StormHVHHelper.noRenderPlayersInWorld() && StormHVHHelper.mc.world != null) {
            for (Entity entity : StormHVHHelper.mc.world.getLoadedEntityList()) {
                EntityOtherPlayerMP mp;
                if (!(entity instanceof EntityOtherPlayerMP) || (mp = (EntityOtherPlayerMP)entity) == FreeCam.fakePlayer || mp.getEntityId() == 462462998 || Client.friendManager.isFriend(mp.getName())) continue;
                StormHVHHelper.mc.world.removeEntityFromWorld(mp.getEntityId());
            }
            StormHVHHelper.mc.world.playerEntities.clear();
        }
        if (this.AutoKitSelect.bValue && StormHVHHelper.isInStormServer() && !StormHVHHelper.isInStormSpawn() && StormHVHHelper.mc.currentScreen == null && this.canSelectKit(this.KitSelect.currentMode.equalsIgnoreCase("Duped"))) {
            this.selectKitAuto(this.KitSelect.currentMode.equalsIgnoreCase("Duped"));
        }
        if (this.isAutoDuel() && StormHVHHelper.isInStormServer() && StormHVHHelper.isInStormSpawn() && this.canClickSword() && StormHVHHelper.mc.currentScreen == null && (!this.OnlySneakDuel.bValue || Minecraft.player.isSneaking())) {
            this.goSword = true;
        }
        if (this.goSword) {
            this.clickSword();
            this.goDuel = true;
            this.goSword = false;
        }
        if (this.goDuel && this.isInSelectDuelMenu() && this.canClickSlot(this.DuelType.currentMode, this.SmartDuel.bValue)) {
            this.clickSlot(this.getSlotByDuel(this.DuelType.currentMode, this.SmartDuel.bValue));
        }
        if (this.AutoResellDuel.bValue) {
            if (Minecraft.player.inventory.getStackInSlot(8).getItem() == Items.REDSTONE) {
                if (this.waitResell.hasReached(200.0)) {
                    this.clickRedstone();
                    this.waitResell.reset();
                }
            } else {
                this.waitResell.reset();
            }
        }
        if (!(this.isInSelectDuelMenu() || !(Minecraft.player.inventory.getStackInSlot(8).getItem() instanceof ItemRedstone) && StormHVHHelper.isInStormSpawn())) {
            this.goDuel = false;
        }
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        if ((event.getPacket() instanceof SPacketSoundEffect || event.getPacket() instanceof SPacketChat) && this.NoSoundsIssue.bValue) {
            String msg;
            SPacketChat chat;
            ResourceLocation resourceSound;
            SPacketSoundEffect effect;
            Object sound;
            Packet packet = event.getPacket();
            if (packet instanceof SPacketSoundEffect && (sound = (effect = (SPacketSoundEffect)packet).getSound()) != null && (resourceSound = ((SoundEvent)sound).getSoundName()) != null && resourceSound.toString().equalsIgnoreCase("minecraft:entity.player.levelup")) {
                event.cancel();
            }
            if ((sound = event.getPacket()) instanceof SPacketChat && (chat = (SPacketChat)sound).getChatComponent() != null && (msg = chat.getChatComponent().getUnformattedText()) != null && msg.equalsIgnoreCase("[!] \u0412 \u0431\u043e\u0439!")) {
                event.cancel();
            }
        }
    }

    static {
        pos = new BlockPos(0, 67, -10);
    }
}

