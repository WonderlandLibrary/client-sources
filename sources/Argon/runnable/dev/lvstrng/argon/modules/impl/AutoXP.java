package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.ItemUseEvent;
import dev.lvstrng.argon.event.listeners.ItemUseListener;
import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.Mouse;
import dev.lvstrng.argon.utils.RandomUtil;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public final class AutoXP extends Module implements TickListener, ItemUseListener {
    private final IntSetting delaySetting;
    private final IntSetting chanceSetting;
    private final BooleanSetting clickSimulationSetting;
    private int delayCounter;

    public AutoXP() {
        super("Auto XP", "Automatically throws XP bottles for you", 0, Category.MISC);
        this.delaySetting = new IntSetting("Delay", 0.0, 20.0, 0.0, 1.0);
        this.chanceSetting = new IntSetting("Chance", 0.0, 100.0, 100.0, 1.0).setDescription("Randomization");
        this.clickSimulationSetting = new BooleanSetting("Click Simulation", false).setDescription("Makes the CPS hud think you're legit");
        this.addSettings(new Setting[]{this.delaySetting, this.chanceSetting, this.clickSimulationSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        this.delayCounter = 0;
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
        boolean isDelaying = this.delayCounter != 0;
        int randomValue = RandomUtil.getRandom(1, (int) chanceSetting.getValue());
        if (this.mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE) return;
        if (GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != 1) return;
        if (isDelaying) --this.delayCounter;
        if (!isDelaying && randomValue <= this.chanceSetting.getValueInt()) {
            if (this.clickSimulationSetting.getValue()) Mouse.simulateClick(GLFW.GLFW_MOUSE_BUTTON_2);
            ActionResult interactItem = this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
            if (interactItem.isAccepted() && interactItem.shouldSwingHand()) this.mc.player.swingHand(Hand.MAIN_HAND);
            this.delayCounter = this.delaySetting.getValueInt();
        }
    }

    @Override
    public void onItemUse(ItemUseEvent event) {
        if (this.mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE)
            event.cancelEvent();
    }
}