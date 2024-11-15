package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.AttackEvent;
import dev.lvstrng.argon.event.events.BreakBlockEvent;
import dev.lvstrng.argon.event.listeners.AttackListener;
import dev.lvstrng.argon.event.listeners.BreakBlockListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.HitResult;

public final class NoMissDelay extends Module implements AttackListener, BreakBlockListener {
    private final BooleanSetting onlyWeaponSetting;
    private final BooleanSetting stopAirHitsSetting;
    private final BooleanSetting stopBlockHitsSetting;

    public NoMissDelay() {
        super("No Miss Delay", "Doesn't let you miss your sword/axe hits", 0, Category.COMBAT);
        this.onlyWeaponSetting = new BooleanSetting("Only weapon", true);
        this.stopAirHitsSetting = new BooleanSetting("Air", true).setDescription("Whether to stop hits directed to the air");
        this.stopBlockHitsSetting = new BooleanSetting("Blocks", false).setDescription("Whether to stop hits directed to blocks");
        this.addSettings(new Setting[]{this.onlyWeaponSetting, this.stopAirHitsSetting, this.stopBlockHitsSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.eventBus.registerPriorityListener(BreakBlockListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(AttackListener.class, this);
        this.eventBus.unregister(BreakBlockListener.class, this);
        super.onDisable();
    }

    @Override
    public void onAttack(final AttackEvent event) {
        if (onlyWeaponSetting.getValue() && !(this.mc.player.getMainHandStack().getItem() instanceof SwordItem) && !(this.mc.player.getMainHandStack().getItem() instanceof AxeItem))
            return;

        switch (this.mc.crosshairTarget.getType()) {
            case MISS:
                if (stopAirHitsSetting.getValue()) event.cancelEvent();
                break;
            case BLOCK:
                if (stopBlockHitsSetting.getValue()) event.cancelEvent();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBlockBreak(final BreakBlockEvent event) {
        if (onlyWeaponSetting.getValue() && !(this.mc.player.getMainHandStack().getItem() instanceof SwordItem) && !(this.mc.player.getMainHandStack().getItem() instanceof AxeItem))
            return;
        if (this.mc.crosshairTarget.getType() == HitResult.Type.BLOCK && stopBlockHitsSetting.getValue())
            event.cancelEvent();
    }
}