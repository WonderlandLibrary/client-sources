/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.world;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.AuraMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.utils.Stopwatch;

@Label(value="Chest Aura")
@Category(value=ModuleCategory.WORLD)
@Aliases(value={"chestaura", "cheststealer"})
public class ChestAura
extends Module {
    private static final int REMOVE_SIZE = 128;
    public final DoubleOption range = new DoubleOption("Range", 4.0, 3.0, 6.0, 0.05);
    public final BoolOption autoOpen = new BoolOption("Auto Open", true);
    public final BoolOption autoSteal = new BoolOption("Auto Steal", true);
    public final DoubleOption delay = new DoubleOption("Delay", 150.0, 0.0, 500.0, 5.0);
    private final Set<TileEntity> openedChests = new HashSet<TileEntity>();
    private final Stopwatch openStopwatch = new Stopwatch();
    private final Stopwatch itemStealStopwatch = new Stopwatch();
    private AuraMod aura;

    public ChestAura() {
        this.addOptions(this.range, this.autoOpen, this.autoSteal, this.delay);
    }

    @Override
    public void onEnabled() {
        if (this.aura == null) {
            this.aura = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
        }
        this.openStopwatch.reset();
        this.itemStealStopwatch.reset();
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        boolean auraSafe;
        boolean bl = auraSafe = this.aura.getTarget() == null || !this.aura.isEnabled();
        if (auraSafe) {
            GuiChest chest;
            Minecraft minecraft = mc;
            EntityPlayerSP player = minecraft.thePlayer;
            WorldClient world = minecraft.theWorld;
            PlayerControllerMP controller = minecraft.playerController;
            boolean pre = event.isPre();
            if (pre && minecraft.currentScreen instanceof GuiChest && this.autoSteal.getValue().booleanValue() && this.isValidChest(chest = (GuiChest)minecraft.currentScreen)) {
                if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                    player.closeScreen();
                    this.itemStealStopwatch.reset();
                    this.openStopwatch.reset();
                    return;
                }
                for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
                    ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                    if (stack == null || !this.isValidItem(stack) || !this.itemStealStopwatch.elapsed(((Double)this.delay.getValue()).longValue())) continue;
                    controller.windowClick(chest.inventorySlots.windowId, index, 0, 1, player);
                    this.itemStealStopwatch.reset();
                }
            }
            if (!event.isPre() && minecraft.currentScreen == null && this.autoOpen.getValue().booleanValue()) {
                List loadedTileEntityList = world.loadedTileEntityList;
                int loadedTileEntityListSize = loadedTileEntityList.size();
                for (int i = 0; i < loadedTileEntityListSize; ++i) {
                    TileEntity tile = (TileEntity)loadedTileEntityList.get(i);
                    BlockPos pos = tile.getPos();
                    if (!(tile instanceof TileEntityChest) || !(this.getDistanceToBlockPos(pos) <= (Double)this.range.getValue()) || this.openedChests.contains(tile) || !this.openStopwatch.elapsed(500L) || !controller.onPlayerRightClick(player, world, player.getHeldItem(), pos, EnumFacing.DOWN, this.getVec(tile.getPos()))) continue;
                    minecraft.getNetHandler().addToSendQueueSilent(new C0APacketAnimation());
                    this.andAndEnsureSetSize(this.openedChests, tile);
                    this.openStopwatch.reset();
                    return;
                }
            }
        }
    }

    @Listener(value=ReceivePacketEvent.class)
    public final void onReceivePacket(ReceivePacketEvent event) {
        if (event.getPacket() instanceof S18PacketEntityTeleport) {
            // empty if block
        }
    }

    private void andAndEnsureSetSize(Set<TileEntity> set, TileEntity chest) {
        if (set.size() > 128) {
            set.clear();
        }
        set.add(chest);
    }

    private Vec3 getVec(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    private double getDistanceToBlockPos(BlockPos pos) {
        return ChestAura.mc.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
    }

    private boolean isValidChest(GuiChest chest) {
        return chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("LOW");
    }

    private boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
    }

    private boolean isChestEmpty(GuiChest chest) {
        for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack == null || !this.isValidItem(stack)) continue;
            return false;
        }
        return true;
    }

    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            ItemStack stack = ChestAura.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null) continue;
            return false;
        }
        return true;
    }
}

