package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.AttackEvent;
import dev.lvstrng.argon.event.events.BreakBlockEvent;
import dev.lvstrng.argon.event.events.ItemUseEvent;
import dev.lvstrng.argon.event.listeners.AttackListener;
import dev.lvstrng.argon.event.listeners.BreakBlockListener;
import dev.lvstrng.argon.event.listeners.ItemUseListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.utils.BlockUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public final class Prevent extends Module implements ItemUseListener, AttackListener, BreakBlockListener {
    private final BooleanSetting disableDoubleGlowstone;
    private final BooleanSetting limitGlowstoneUse;
    private final BooleanSetting restrictAnchorPlacement;
    private final BooleanSetting preventObsidianBreaking;
    private final BooleanSetting blockEChestClicks;

    public Prevent() {
        super("Prevent", "Prevents you from certain actions", 0, Category.MISC);
        this.disableDoubleGlowstone = new BooleanSetting("Disable Double Glowstone", false).setDescription("Prevents charging the anchor again if it's already charged");
        this.limitGlowstoneUse = new BooleanSetting("Limit Glowstone Use", false).setDescription("Allows right-clicking with glowstone only when aiming at an anchor");
        this.restrictAnchorPlacement = new BooleanSetting("Restrict Anchor Placement", false).setDescription("Prevents placing an anchor next to another unless charged");
        this.preventObsidianBreaking = new BooleanSetting("Prevent Obsidian Breaking", false).setDescription("Enables faster crystal placement by blocking left clicks on obsidian");
        this.blockEChestClicks = new BooleanSetting("Block E-Chest Clicks", false).setDescription("Prevents clicking on ender chests with PvP items");
        this.addSettings(new Setting[]{
                this.disableDoubleGlowstone,
                this.limitGlowstoneUse,
                this.restrictAnchorPlacement,
                this.preventObsidianBreaking,
                this.blockEChestClicks
        });
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(BreakBlockListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(BreakBlockListener.class, this);
        this.eventBus.unregister(AttackListener.class, this);
        this.eventBus.unregister(ItemUseListener.class, this);
        super.onDisable();
    }

    @Override
    public void onAttack(final AttackEvent event) {
        final HitResult crosshairTarget = this.mc.crosshairTarget;
        if (!(crosshairTarget instanceof BlockHitResult blockHit)) {
            return;
        }

        boolean isObsidian = BlockUtil.isBlockType(blockHit.getBlockPos(), Blocks.OBSIDIAN);

        if (isObsidian && (this.preventObsidianBreaking.getValue() || this.mc.player.isHolding(Items.END_CRYSTAL))) {
            event.cancelEvent();
        }
    }

    @Override
    public void onBlockBreak(final BreakBlockEvent event) {
        final HitResult crosshairTarget = this.mc.crosshairTarget;
        if (!(crosshairTarget instanceof BlockHitResult blockHit)) {
            return;
        }

        boolean isObsidian = BlockUtil.isBlockType(blockHit.getBlockPos(), Blocks.OBSIDIAN);

        if (isObsidian && (this.preventObsidianBreaking.getValue() || this.mc.player.isHolding(Items.END_CRYSTAL))) {
            event.cancelEvent();
        }
    }

    @Override
    public void onItemUse(final ItemUseEvent event) {
        final HitResult crosshairTarget = this.mc.crosshairTarget;
        if (!(crosshairTarget instanceof BlockHitResult blockHit)) return;

        boolean isAnchor = BlockUtil.canExplodeAnchor(blockHit.getBlockPos());
        boolean isGlowstone = this.mc.player.isHolding(Items.GLOWSTONE);
        boolean isRespawnAnchor = BlockUtil.isBlockType(blockHit.getBlockPos(), Blocks.RESPAWN_ANCHOR);
        boolean isEnderChest = BlockUtil.isBlockType(blockHit.getBlockPos(), Blocks.ENDER_CHEST);
        boolean isCrystal = this.mc.player.isHolding(Items.END_CRYSTAL);
        boolean isObsidian = this.mc.player.isHolding(Items.OBSIDIAN);
        boolean isPlayerHoldingSword = this.mc.player.getMainHandStack().getItem() instanceof SwordItem;

        if (isAnchor && this.disableDoubleGlowstone.getValue() && isGlowstone) event.cancelEvent();
        else if (isGlowstone && this.limitGlowstoneUse.getValue() && isGlowstone) event.cancelEvent();
        else if (isRespawnAnchor && this.restrictAnchorPlacement.getValue() && isRespawnAnchor) event.cancelEvent();
        else if (isEnderChest && this.blockEChestClicks.getValue()) event.cancelEvent();
        else if (!isPlayerHoldingSword && isCrystal) event.cancelEvent();
        else if (!isPlayerHoldingSword && isObsidian) event.cancelEvent();
    }
}
