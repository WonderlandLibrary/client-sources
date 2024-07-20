/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;

public class ChestStealer
extends Module {
    final TimerHelper timerLoot = new TimerHelper();
    final TimerHelper timeSeen = new TimerHelper();
    private Random RANDOM = new Random();
    Settings Delay;
    Settings SilentWindow;
    Settings IgnoreCustomItems;
    Settings RandomSlots;
    Settings ChestAura;
    private ArrayList<ItemStackWithSlot> stacks = new ArrayList();
    private boolean hasLootProcess;
    private boolean lastSlot;
    List<BlockPos> opennedChestPoses = new ArrayList<BlockPos>();
    BlockPos targetChestPos;
    BlockPos lastOpennedChest;
    boolean hasFlagOnOpen = false;

    public ChestStealer() {
        super("ChestStealer", 0, Module.Category.PLAYER);
        this.Delay = new Settings("Delay", 70.0f, 500.0f, 0.0f, this);
        this.settings.add(this.Delay);
        this.SilentWindow = new Settings("SilentWindow", true, (Module)this);
        this.settings.add(this.SilentWindow);
        this.IgnoreCustomItems = new Settings("IgnoreCustomItems", true, (Module)this);
        this.settings.add(this.IgnoreCustomItems);
        this.RandomSlots = new Settings("RandomSlots", true, (Module)this);
        this.settings.add(this.RandomSlots);
        this.ChestAura = new Settings("ChestAura", false, (Module)this);
        this.settings.add(this.ChestAura);
        this.RANDOM.setSeed(1234567890L);
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByInt((int)this.Delay.fValue);
    }

    private boolean itemHasCustom(ItemStack stack) {
        return stack.hasDisplayName();
    }

    private ArrayList<ItemStackWithSlot> getItemStackListFromContainer(ContainerChest container) {
        this.stacks.clear();
        if (container != null && container.getLowerChestInventory() != null) {
            for (int index = 0; index < container.getLowerChestInventory().getSizeInventory(); ++index) {
                if (!((Slot)container.inventorySlots.get(index)).getHasStack()) continue;
                this.stacks.add(new ItemStackWithSlot(((Slot)container.inventorySlots.get(index)).getStack(), index));
            }
        }
        return this.stacks;
    }

    private boolean hasCustomItemInContainer(ArrayList<ItemStackWithSlot> itemStacks) {
        return itemStacks.stream().anyMatch(stackWS -> this.itemHasCustom(stackWS.getStack()));
    }

    private boolean hasEmptySlotInInventory() {
        boolean hasAir = false;
        for (int slotNum = 0; slotNum < 36; ++slotNum) {
            if (!(Minecraft.player.inventory.getStackInSlot(slotNum).getItem() instanceof ItemAir)) continue;
            hasAir = true;
        }
        return hasAir;
    }

    private boolean lootSlotsFromListStacks(ArrayList<ItemStackWithSlot> itemStacks, ContainerChest container, boolean randomSlots, TimerHelper delayController, long delay) {
        if (container == null || container.getLowerChestInventory() == null || itemStacks.isEmpty()) {
            return false;
        }
        int currectSlot = randomSlots && itemStacks.size() > 2 ? MathUtils.clamp(this.RANDOM.nextInt(itemStacks.size()), 0, itemStacks.size()) : 0;
        ItemStackWithSlot currentISWS = itemStacks.get(currectSlot);
        if (itemStacks.get(currectSlot) != null) {
            ItemStack stackInSlot = currentISWS.getStack();
            int slot = currentISWS.getSlot();
            if (delayController.hasReached(delay)) {
                if (this.getHasLootAction(stackInSlot, slot, container)) {
                    delayController.reset();
                    itemStacks.remove(currectSlot);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean getHasLootAction(ItemStack stackIn, int slotIn, ContainerChest chestIn) {
        ChestStealer.mc.playerController.windowClick(chestIn.windowId, slotIn, 1, ClickType.QUICK_MOVE, Minecraft.player);
        return true;
    }

    @Override
    public void onUpdate() {
        Container opennedContainer = Minecraft.player.openContainer;
        if (opennedContainer != null && opennedContainer instanceof ContainerChest) {
            ContainerChest chestContainer = (ContainerChest)opennedContainer;
            ArrayList<ItemStackWithSlot> iswsList = this.getItemStackListFromContainer(chestContainer);
            ArrayList iswsListFiltered = (ArrayList)iswsList.stream().filter(isws -> !this.IgnoreCustomItems.bValue || !this.itemHasCustom(isws.getStack())).collect(Collectors.toList());
            if (!this.timeSeen.hasReached(100.0)) {
                return;
            }
            if (this.hasEmptySlotInInventory() && !iswsListFiltered.isEmpty()) {
                if (this.lootSlotsFromListStacks(iswsListFiltered, chestContainer, this.RandomSlots.bValue, this.timerLoot, (int)this.Delay.fValue)) {
                    this.hasLootProcess = true;
                    if (this.SilentWindow.bValue && ChestStealer.mc.currentScreen instanceof GuiContainer && !(ChestStealer.mc.currentScreen instanceof GuiInventory) && this.hasLootProcess) {
                        ChestStealer.mc.currentScreen = null;
                        mc.setIngameFocus();
                    }
                }
            } else if (this.hasLootProcess || this.timeSeen.hasReached(200.0)) {
                this.hasLootProcess = false;
                Minecraft.player.closeScreen();
                ChestStealer.mc.currentScreen = null;
                this.timeSeen.reset();
            }
        } else {
            this.timerLoot.reset();
            this.timeSeen.reset();
        }
    }

    List<BlockPos> allChestPoses(double inRange) {
        return ChestStealer.mc.world.getLoadedTileEntityList().stream().filter(tile -> tile instanceof TileEntityChest).filter(tile -> Minecraft.player.getDistanceAtEye((double)tile.getX() + 0.5, (double)tile.getY() + 0.5, (double)tile.getZ() + 0.5) < inRange).map(TileEntity::getPos).filter(pos -> !this.opennedChestPoses.stream().anyMatch(blackPos -> blackPos.equals(pos))).filter(pos -> {
            RayTraceResult result = ChestStealer.mc.world.rayTraceBlocks(Minecraft.player.getPositionEyes(1.0f), new Vec3d((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5), false, true, false);
            return result != null && result.getBlockPos() != null && result.getBlockPos().equals(pos);
        }).collect(Collectors.toList());
    }

    void nullateAll() {
        this.lastOpennedChest = null;
        this.hasFlagOnOpen = false;
        this.targetChestPos = null;
        this.opennedChestPoses.clear();
    }

    @Override
    public void onToggled(boolean actived) {
        this.nullateAll();
        super.onToggled(actived);
    }

    @EventTarget
    public void onUpdateEntitySelf(EventPlayerMotionUpdate event) {
        if (this.ChestAura.bValue) {
            if (Minecraft.player.ticksExisted < 4) {
                this.nullateAll();
                return;
            }
            if (this.lastOpennedChest != null && this.hasFlagOnOpen) {
                for (int i = 0; i < this.opennedChestPoses.size(); ++i) {
                    if (this.opennedChestPoses.get(i) == null || !this.opennedChestPoses.get(i).equals(this.lastOpennedChest)) continue;
                    this.opennedChestPoses.remove(i);
                }
                this.hasFlagOnOpen = false;
            }
            if (!this.hasEmptySlotInInventory()) {
                return;
            }
            List<BlockPos> targetChests = this.allChestPoses(4.6);
            this.targetChestPos = !targetChests.isEmpty() && (!HitAura.get.actived || HitAura.TARGET_ROTS != null) ? targetChests.get(0) : null;
            if (this.targetChestPos != null && ChestStealer.mc.currentScreen == null && !this.hasLootProcess) {
                BlockPos pos;
                float[] rotate = RotationUtil.getNeededFacing(new Vec3d(this.targetChestPos).addVector(0.5, 0.5, 0.5), true, Minecraft.player, false);
                event.setYaw(rotate[0]);
                event.setPitch(rotate[1]);
                float prevYaw = Minecraft.player.rotationYaw;
                float prevPitch = Minecraft.player.rotationPitch;
                Minecraft.player.rotationYaw = rotate[0];
                Minecraft.player.rotationPitch = rotate[1];
                Minecraft.player.renderYawOffset = rotate[0];
                Minecraft.player.rotationYawHead = rotate[0];
                Minecraft.player.rotationPitchHead = rotate[1];
                ChestStealer.mc.entityRenderer.getMouseOver(mc.getRenderPartialTicks());
                RayTraceResult result = ChestStealer.mc.objectMouseOver;
                Minecraft.player.rotationYaw = prevYaw;
                Minecraft.player.rotationPitch = prevPitch;
                ChestStealer.mc.entityRenderer.getMouseOver(mc.getRenderPartialTicks());
                if (result != null && result.getBlockPos() != null && (pos = result.getBlockPos()).equals(this.targetChestPos)) {
                    boolean canClick;
                    boolean bl = canClick = result.hitVec != null && result.sideHit != null;
                    if (canClick) {
                        boolean clientIsSneaking = Minecraft.player.isSneaking();
                        if (clientIsSneaking) {
                            mc.getConnection().sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        if (ChestStealer.mc.playerController.processRightClickBlock(Minecraft.player, ChestStealer.mc.world, pos, result.sideHit, result.hitVec, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
                            this.opennedChestPoses.add(pos);
                            this.lastOpennedChest = pos;
                            this.targetChestPos = null;
                        }
                        if (clientIsSneaking) {
                            mc.getConnection().sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                        }
                    }
                }
            }
        } else {
            this.nullateAll();
        }
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketCloseWindow) {
            SPacketCloseWindow close = (SPacketCloseWindow)packet;
            if (Minecraft.player.openContainer != null) {
                if (close.windowId == Minecraft.player.openContainer.windowId) {
                    this.hasFlagOnOpen = true;
                }
            }
        }
    }

    private class ItemStackWithSlot {
        private ItemStack stack;
        private int slot;

        public ItemStackWithSlot(ItemStack stack, int slot) {
            this.stack = stack;
            this.slot = slot;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public int getSlot() {
            return this.slot;
        }
    }
}

