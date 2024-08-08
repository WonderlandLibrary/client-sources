// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraft.block.BlockWeb;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.Item;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.util.EntityUtil;
import net.minecraft.item.ItemSword;
import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import me.perry.mcdonalds.features.Feature;
import me.perry.mcdonalds.event.events.PacketEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import me.perry.mcdonalds.event.events.ProcessRightClickBlockEvent;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.util.InventoryUtil;
import java.util.Queue;
import me.perry.mcdonalds.features.modules.Module;

public class Offhand extends Module
{
    private static Offhand instance;
    private final Queue<InventoryUtil.Task> taskList;
    private final Timer timer;
    private final Timer secondTimer;
    public Setting<Boolean> crystal;
    public Setting<Float> crystalHealth;
    public Setting<Float> crystalHoleHealth;
    public Setting<Boolean> gapple;
    public Setting<Boolean> armorCheck;
    public Setting<Integer> actions;
    public Mode2 currentMode;
    public int totems;
    public int crystals;
    public int gapples;
    public int lastTotemSlot;
    public int lastGappleSlot;
    public int lastCrystalSlot;
    public int lastObbySlot;
    public int lastWebSlot;
    public boolean holdingCrystal;
    public boolean holdingTotem;
    public boolean holdingGapple;
    public boolean didSwitchThisTick;
    private boolean second;
    private boolean switchedForHealthReason;
    
    public Offhand() {
        super("OffhandCrystal", "Allows you to switch up your Offhand.", Category.COMBAT, true, false, false);
        this.taskList = new ConcurrentLinkedQueue<InventoryUtil.Task>();
        this.timer = new Timer();
        this.secondTimer = new Timer();
        this.crystal = (Setting<Boolean>)this.register(new Setting("Crystal", (T)true));
        this.crystalHealth = (Setting<Float>)this.register(new Setting("CrystalHP", (T)13.0f, (T)0.1f, (T)36.0f));
        this.crystalHoleHealth = (Setting<Float>)this.register(new Setting("CrystalHoleHP", (T)3.5f, (T)0.1f, (T)36.0f));
        this.gapple = (Setting<Boolean>)this.register(new Setting("Gapple", (T)true));
        this.armorCheck = (Setting<Boolean>)this.register(new Setting("ArmorCheck", (T)true));
        this.actions = (Setting<Integer>)this.register(new Setting("Packets", (T)4, (T)1, (T)4));
        this.currentMode = Mode2.TOTEMS;
        this.totems = 0;
        this.crystals = 0;
        this.gapples = 0;
        this.lastTotemSlot = -1;
        this.lastGappleSlot = -1;
        this.lastCrystalSlot = -1;
        this.lastObbySlot = -1;
        this.lastWebSlot = -1;
        this.holdingCrystal = false;
        this.holdingTotem = false;
        this.holdingGapple = false;
        this.didSwitchThisTick = false;
        this.second = false;
        this.switchedForHealthReason = false;
        Offhand.instance = this;
    }
    
    public static Offhand getInstance() {
        if (Offhand.instance == null) {
            Offhand.instance = new Offhand();
        }
        return Offhand.instance;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final ProcessRightClickBlockEvent event) {
        if (event.hand == EnumHand.MAIN_HAND && event.stack.getItem() == Items.END_CRYSTAL && Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Offhand.mc.objectMouseOver != null && event.pos == Offhand.mc.objectMouseOver.getBlockPos()) {
            event.setCanceled(true);
            Offhand.mc.player.setActiveHand(EnumHand.OFF_HAND);
            Offhand.mc.playerController.processRightClick((EntityPlayer)Offhand.mc.player, (World)Offhand.mc.world, EnumHand.OFF_HAND);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(50L)) {
            if (Offhand.mc.player != null && Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Offhand.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && Mouse.isButtonDown(1)) {
                Offhand.mc.player.setActiveHand(EnumHand.OFF_HAND);
                Offhand.mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
            }
        }
        else if (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Offhand.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            Offhand.mc.gameSettings.keyBindUseItem.pressed = false;
        }
        if (nullCheck()) {
            return;
        }
        this.doOffhand();
        if (this.secondTimer.passedMs(50L) && this.second) {
            this.second = false;
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (!Feature.fullNullCheck() && Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && Offhand.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && Offhand.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet2 = event.getPacket();
                if (packet2.getHand() == EnumHand.MAIN_HAND) {
                    if (this.timer.passedMs(50L)) {
                        Offhand.mc.player.setActiveHand(EnumHand.OFF_HAND);
                        Offhand.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    }
                    event.setCanceled(true);
                }
            }
            else {
                final CPacketPlayerTryUseItem packet3;
                if (event.getPacket() instanceof CPacketPlayerTryUseItem && (packet3 = event.getPacket()).getHand() == EnumHand.OFF_HAND && !this.timer.passedMs(50L)) {
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            return "Crystals";
        }
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            return "Totems";
        }
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            return "Gapples";
        }
        return null;
    }
    
    public void doOffhand() {
        this.didSwitchThisTick = false;
        this.holdingCrystal = (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
        this.holdingTotem = (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING);
        this.holdingGapple = (Offhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE);
        this.totems = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingTotem) {
            this.totems += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.crystals = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingCrystal) {
            this.crystals += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.gapples = Offhand.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::func_190916_E).sum();
        if (this.holdingGapple) {
            this.gapples += Offhand.mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::func_190916_E).sum();
        }
        this.doSwitch();
    }
    
    public void doSwitch() {
        this.currentMode = Mode2.TOTEMS;
        if (this.gapple.getValue() && Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && Offhand.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            this.currentMode = Mode2.GAPPLES;
        }
        else if (this.currentMode != Mode2.CRYSTALS && this.crystal.getValue() && ((EntityUtil.isSafe((Entity)Offhand.mc.player) && EntityUtil.getHealth((Entity)Offhand.mc.player, true) > this.crystalHoleHealth.getValue()) || EntityUtil.getHealth((Entity)Offhand.mc.player, true) > this.crystalHealth.getValue())) {
            this.currentMode = Mode2.CRYSTALS;
        }
        if (this.currentMode == Mode2.CRYSTALS && this.crystals == 0) {
            this.setMode(Mode2.TOTEMS);
        }
        if (this.currentMode == Mode2.CRYSTALS && ((!EntityUtil.isSafe((Entity)Offhand.mc.player) && EntityUtil.getHealth((Entity)Offhand.mc.player, true) <= this.crystalHealth.getValue()) || EntityUtil.getHealth((Entity)Offhand.mc.player, true) <= this.crystalHoleHealth.getValue())) {
            if (this.currentMode == Mode2.CRYSTALS) {
                this.switchedForHealthReason = true;
            }
            this.setMode(Mode2.TOTEMS);
        }
        if (this.switchedForHealthReason && ((EntityUtil.isSafe((Entity)Offhand.mc.player) && EntityUtil.getHealth((Entity)Offhand.mc.player, true) > this.crystalHoleHealth.getValue()) || EntityUtil.getHealth((Entity)Offhand.mc.player, true) > this.crystalHealth.getValue())) {
            this.setMode(Mode2.CRYSTALS);
            this.switchedForHealthReason = false;
        }
        if (this.currentMode == Mode2.CRYSTALS && this.armorCheck.getValue() && (Offhand.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.AIR || Offhand.mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.AIR || Offhand.mc.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.AIR || Offhand.mc.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.AIR)) {
            this.setMode(Mode2.TOTEMS);
        }
        if (Offhand.mc.currentScreen instanceof GuiContainer && !(Offhand.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        final Item currentOffhandItem = Offhand.mc.player.getHeldItemOffhand().getItem();
        switch (this.currentMode) {
            case TOTEMS: {
                if (this.totems <= 0) {
                    break;
                }
                if (this.holdingTotem) {
                    break;
                }
                this.lastTotemSlot = InventoryUtil.findItemInventorySlot(Items.TOTEM_OF_UNDYING, false);
                final int lastSlot = this.getLastSlot(currentOffhandItem, this.lastTotemSlot);
                this.putItemInOffhand(this.lastTotemSlot, lastSlot);
                break;
            }
            case GAPPLES: {
                if (this.gapples <= 0) {
                    break;
                }
                if (this.holdingGapple) {
                    break;
                }
                this.lastGappleSlot = InventoryUtil.findItemInventorySlot(Items.GOLDEN_APPLE, false);
                final int lastSlot = this.getLastSlot(currentOffhandItem, this.lastGappleSlot);
                this.putItemInOffhand(this.lastGappleSlot, lastSlot);
                break;
            }
            default: {
                if (this.crystals <= 0) {
                    break;
                }
                if (this.holdingCrystal) {
                    break;
                }
                this.lastCrystalSlot = InventoryUtil.findItemInventorySlot(Items.END_CRYSTAL, false);
                final int lastSlot = this.getLastSlot(currentOffhandItem, this.lastCrystalSlot);
                this.putItemInOffhand(this.lastCrystalSlot, lastSlot);
                break;
            }
        }
        for (int i = 0; i < this.actions.getValue(); ++i) {
            final InventoryUtil.Task task = this.taskList.poll();
            if (task != null) {
                task.run();
                if (task.isSwitching()) {
                    this.didSwitchThisTick = true;
                }
            }
        }
    }
    
    private int getLastSlot(final Item item, final int slotIn) {
        if (item == Items.END_CRYSTAL) {
            return this.lastCrystalSlot;
        }
        if (item == Items.GOLDEN_APPLE) {
            return this.lastGappleSlot;
        }
        if (item == Items.TOTEM_OF_UNDYING) {
            return this.lastTotemSlot;
        }
        if (InventoryUtil.isBlock(item, BlockObsidian.class)) {
            return this.lastObbySlot;
        }
        if (InventoryUtil.isBlock(item, BlockWeb.class)) {
            return this.lastWebSlot;
        }
        if (item == Items.AIR) {
            return -1;
        }
        return slotIn;
    }
    
    private void putItemInOffhand(final int slotIn, final int slotOut) {
        if (slotIn != -1 && this.taskList.isEmpty()) {
            this.taskList.add(new InventoryUtil.Task(slotIn));
            this.taskList.add(new InventoryUtil.Task(45));
            this.taskList.add(new InventoryUtil.Task(slotOut));
            this.taskList.add(new InventoryUtil.Task());
        }
    }
    
    public void setMode(final Mode2 mode) {
        this.currentMode = ((this.currentMode == mode) ? Mode2.TOTEMS : mode);
    }
    
    public enum Mode2
    {
        TOTEMS, 
        GAPPLES, 
        CRYSTALS;
    }
}
