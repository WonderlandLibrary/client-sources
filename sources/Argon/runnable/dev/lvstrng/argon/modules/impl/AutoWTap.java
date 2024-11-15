package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.PacketSendEvent;
import dev.lvstrng.argon.event.events.Render2DEvent;
import dev.lvstrng.argon.event.listeners.PacketSendListener;
import dev.lvstrng.argon.event.listeners.Render2DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.Timer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.lwjgl.glfw.GLFW;

public final class AutoWTap extends Module implements PacketSendListener, Render2DListener {
    public final Timer wTapTimer;
    public final Timer actionTimer;
    private final IntSetting delaySetting;
    private final BooleanSetting inAirSetting;
    public boolean isWTapped;
    public boolean isWTapEnabled;

    public AutoWTap() {
        super("Auto WTap", "Automatically W Taps for you so the opponent takes more knockback", 0, Category.COMBAT);
        this.delaySetting = new IntSetting("Delay", 0.0, 1000.0, 250.0, 1.0);
        this.inAirSetting = new BooleanSetting("In Air", false).setDescription("Whether it should W tap in air");
        this.wTapTimer = new Timer();
        this.actionTimer = new Timer();
        this.addSettings(new Setting[]{this.delaySetting, this.inAirSetting});
    }

    static MinecraftClient getMinecraft(AutoWTap autoWTap) {
        return autoWTap.mc;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PacketSendListener.class, this);
        this.eventBus.unregister(Render2DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        if (this.getKeybind() != 0 && GLFW.glfwGetKey(this.mc.getWindow().getHandle(), this.getKeybind()) == GLFW.GLFW_PRESS) {
            this.isWTapEnabled = false;
            this.isWTapped = false;
            return;
        }
        if (!this.inAirSetting.getValue() && !this.mc.player.isOnGround()) return;
        if (this.isWTapped && this.actionTimer.hasPassedDelay(1.0f)) {
            this.mc.options.forwardKey.setPressed(false);
            this.wTapTimer.reset();
            this.isWTapEnabled = true;
            this.isWTapped = false;
        }
        if (!this.isWTapEnabled || !this.wTapTimer.hasPassedDelay((float) this.delaySetting.getValueLong())) {
            return;
        }
        this.mc.options.forwardKey.setPressed(true);
        this.isWTapEnabled = false;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.packet instanceof PlayerInteractEntityC2SPacket playerPacket) {
            playerPacket.handle(new WTapAttackHandler(this));
        }
    }
}