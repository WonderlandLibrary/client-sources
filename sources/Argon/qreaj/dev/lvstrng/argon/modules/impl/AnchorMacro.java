package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.ItemUseEvent;
import dev.lvstrng.argon.event.listeners.ItemUseListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.BlockUtil;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.RandomUtil;
import dev.lvstrng.argon.utils.TargetUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public final class AnchorMacro extends Module implements TickListener, ItemUseListener {

    private final BooleanSetting whileUse;
    private final BooleanSetting stopOnKill;
    private final BooleanSetting clickSimulation;
    private final IntSetting switchDelay;
    private final IntSetting placeChance;
    private final IntSetting glowstoneDelay;
    private final IntSetting explodeDelay;
    private final IntSetting explodeSlot;
    private final BooleanSetting onlyOwn;
    private final Set<BlockPos> ownedBlocks;
    private int switchCounter;
    private int glowstoneCounter;
    private int explodeCounter;

    public AnchorMacro() {
        super("Anchor Macro", "Automatically blows up respawn anchors for you", 0, Category.MISC);
        this.whileUse = new BooleanSetting("While Use", true).setDescription("Triggers while using item or shield");
        this.stopOnKill = new BooleanSetting("Stop on Kill", false).setDescription("Prevents anchoring if enemies are nearby");
        this.clickSimulation = new BooleanSetting("Click Simulation", false).setDescription("Simulates clicking for CPS HUD");
        this.switchDelay = new IntSetting("Switch Delay", 0, 20, 0, 1);
        this.placeChance = new IntSetting("Place Chance", 0, 100, 100, 1).setDescription("Randomize the place chance");
        this.glowstoneDelay = new IntSetting("Glowstone Delay", 0, 20, 0, 1);
        this.explodeDelay = new IntSetting("Explode Delay", 0, 20, 0, 1);
        this.explodeSlot = new IntSetting("Explode Slot", 1, 9, 1, 1);
        this.onlyOwn = new BooleanSetting("Only Own", false);
        this.switchCounter = 0;
        this.glowstoneCounter = 0;
        this.explodeCounter = 0;
        this.ownedBlocks = new HashSet<>();
        this.addSettings(new Setting[]{whileUse, stopOnKill, clickSimulation, switchDelay, placeChance, glowstoneDelay, explodeDelay, explodeSlot, onlyOwn});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        this.switchCounter = 0;
        this.glowstoneCounter = 0;
        this.explodeCounter = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        this.eventBus.unregister(ItemUseListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.currentScreen != null) return;

        boolean isUsingShieldOrFood = this.mc.player.getMainHandStack().getItem().isFood() ||
                this.mc.player.getMainHandStack().getItem() instanceof ShieldItem ||
                this.mc.player.getOffHandStack().getItem().isFood() ||
                this.mc.player.getOffHandStack().getItem() instanceof ShieldItem;

        if (isUsingShieldOrFood && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 1) == 1 && !whileUse.getValue()) {
            return;
        }

        if (GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 1) == 1) {
            HitResult crosshairTarget = this.mc.crosshairTarget;
            if (crosshairTarget instanceof BlockHitResult blockHitResult) {
                BlockPos targetPos = blockHitResult.getBlockPos();

                if (mc.world.getBlockState(targetPos).getBlock().equals(Blocks.RESPAWN_ANCHOR)) {
                    if (onlyOwn.getValue() && !ownedBlocks.contains(targetPos)) {
                        return;
                    }

                    this.mc.options.useKey.setPressed(false);

                    if (BlockUtil.canExplodeAnchor(targetPos) && RandomUtil.getRandom(1, 100) <= placeChance.getValueInt()) {
                        handleGlowstoneUsage();
                    }

                    handleExplodeAnchor(targetPos);
                }
            }
        }
    }

    private void handleGlowstoneUsage() {
        if (!this.mc.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
            if (this.switchCounter != switchDelay.getValueInt()) {
                this.switchCounter++;
                return;
            }
            this.switchCounter = 0;
            for (int i = 0; i < 9; i++)
                if (mc.player.getInventory().getStack(i).equals(Items.GLOWSTONE))
                    mc.player.getInventory().selectedSlot = i;
        }

        if (this.mc.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
            if (this.glowstoneCounter != glowstoneDelay.getValueInt()) {
                this.glowstoneCounter++;
                return;
            }
            this.glowstoneCounter = 0;
            if (clickSimulation.getValue()) {
                Mouse.simulateClick(1);
            }
            TargetUtil.placeBlock((BlockHitResult) this.mc.crosshairTarget, true);
        }
    }

    private void handleExplodeAnchor(BlockPos blockPos) {
        int selectedSlot = explodeSlot.getValueInt() - 1;
        if (this.mc.player.getInventory().selectedSlot != selectedSlot) {
            if (this.switchCounter != switchDelay.getValueInt()) {
                this.switchCounter++;
                return;
            }
            this.switchCounter = 0;
            this.mc.player.getInventory().selectedSlot = selectedSlot;
        }

        if (this.mc.player.getInventory().selectedSlot == selectedSlot) {
            if (this.explodeCounter != explodeDelay.getValueInt()) {
                this.explodeCounter++;
                return;
            }
            this.explodeCounter = 0;
            if (clickSimulation.getValue()) {
                Mouse.simulateClick(1);
            }
            TargetUtil.placeBlock((BlockHitResult) this.mc.crosshairTarget, true);
            this.ownedBlocks.remove(blockPos);
        }
    }

    @Override
    public void onItemUse(ItemUseEvent event) {
        if (this.mc.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) {
            HitResult crosshairTarget = this.mc.crosshairTarget;
            if (crosshairTarget instanceof BlockHitResult blockHitResult) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                Direction side = blockHitResult.getSide();

                switch (side) {
                    case UP -> ownedBlocks.add(blockPos.add(0, 1, 0));
                    case DOWN -> ownedBlocks.add(blockPos.add(0, -1, 0));
                    case NORTH -> ownedBlocks.add(blockPos.add(0, 0, -1));
                    case SOUTH -> ownedBlocks.add(blockPos.add(0, 0, 1));
                    case EAST -> ownedBlocks.add(blockPos.add(1, 0, 0));
                    case WEST -> ownedBlocks.add(blockPos.add(-1, 0, 0));
                    default -> ownedBlocks.add(blockPos);
                }
            }
        }
    }
}
