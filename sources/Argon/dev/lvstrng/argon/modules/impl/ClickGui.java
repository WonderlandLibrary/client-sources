package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.clickgui.ClickGUI;
import dev.lvstrng.argon.event.events.PacketReceiveEvent;
import dev.lvstrng.argon.event.listeners.PacketReceiveListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.AnimationType;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;

public final class ClickGui extends Module implements PacketReceiveListener {
    public static IntSetting colorRed;
    public static IntSetting colorGreen;
    public static IntSetting colorBlue;
    public static BooleanSetting breathingSetting;
    public static BooleanSetting rainbowSetting;
    public static BooleanSetting backgroundSetting;
    public static BooleanSetting preventCloseSetting;
    public static IntSetting roundnessSetting;
    public static EnumSetting animations;
    public static BooleanSetting msaaSetting;
    public static BooleanSetting customFontSetting;

    public ClickGui() {
        super("Click Gui", "Click Gui", 345, Category.CLIENT);

        colorRed = new IntSetting("Red", 0, 255, 255, 1).setDescription("The red colour of the GUI");
        colorGreen = new IntSetting("Green", 0, 255, 0, 1).setDescription("The green colour of the GUI");
        colorBlue = new IntSetting("Blue", 0, 255, 50, 1).setDescription("The blue colour of the GUI");
        breathingSetting = new BooleanSetting("Breathing", true).setDescription("Color breathing effect (only with rainbow off)");
        rainbowSetting = new BooleanSetting("Rainbow", true).setDescription("Enables LGBTQ mode");
        backgroundSetting = new BooleanSetting("Background", false).setDescription("Renders the background of the Click Gui");
        preventCloseSetting = new BooleanSetting("Prevent Close", true).setDescription("For servers with freeze plugins that don't let you open the GUI");
        roundnessSetting = new IntSetting("Roundness", 1, 10, 5, 1).setDescription("The roundness of the GUI");
        customFontSetting = new BooleanSetting("Custom Font", true).setDescription("Use a custom font for the GUI");
        animations = new EnumSetting("Animations", AnimationType.OFF, AnimationType.class).setDescription("The style of the animations");
        msaaSetting = new BooleanSetting("MSAA", true).setDescription("Anti Aliasing | This can impact performance if you're using tracers but gives them a smoother look");

        this.addSettings(new Setting[]{
                colorRed,
                colorGreen,
                colorBlue,
                breathingSetting,
                rainbowSetting,
                backgroundSetting,
                preventCloseSetting,
                roundnessSetting,
                customFontSetting,
                animations,
                msaaSetting
        });
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        Argon.INSTANCE.screen = this.mc.currentScreen;
        ClickGUI clickGui = Argon.INSTANCE.clickGUI;

        if (clickGui != null) {
            this.mc.setScreenAndRender(clickGui);

            if (this.mc.currentScreen instanceof InventoryScreen) {
                Argon.INSTANCE.guiOpen = true;
            }
        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PacketReceiveListener.class, this);

        if (this.mc.currentScreen instanceof ClickGUI) {
            closeClickGui();
        }

        super.onDisable();
    }

    private void closeClickGui() {
        Argon.INSTANCE.clickGUI.destroy();
        this.mc.setScreenAndRender(Argon.INSTANCE.screen);
        Argon.INSTANCE.clickGUI.removeComponents();
        Argon.INSTANCE.guiOpen = false;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (Argon.INSTANCE.guiOpen && event.packet instanceof OpenScreenS2CPacket && preventCloseSetting.getValue()) {
            event.cancelEvent();
        }
    }
}
