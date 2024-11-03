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
import dev.lvstrng.argon.utils.FontRenderer;
import dev.lvstrng.argon.utils.RandomUtil;
import dev.lvstrng.argon.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.RotationAxis;

import java.awt.*;

public final class TargetHUD extends Module implements Render2DListener, PacketSendListener {
    public static float opacity;
    private final IntSetting posX;
    private final IntSetting posY;
    private final BooleanSetting timeoutEnabled;
    public long lastUpdateTime;

    public TargetHUD() {
        super("Target HUD", "Gives you information about the enemy player", 0, Category.RENDER);
        this.posX = new IntSetting("X", 0.0, 1920.0, 500.0, 1.0);
        this.posY = new IntSetting("Y", 0.0, 1080.0, 500.0, 1.0);
        this.timeoutEnabled = new BooleanSetting("Timeout", true).setDescription("Target HUD will disappear after 10 seconds");
        this.lastUpdateTime = 0L;
        this.addSettings(new Setting[]{posX, posY, timeoutEnabled});
    }

    static MinecraftClient getMC(final TargetHUD targetHUD) {
        return targetHUD.mc;
    }

    @Override
    public void onEnable() {
        registerEventListeners();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        unregisterEventListeners();
        super.onDisable();
    }

    private void registerEventListeners() {
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
    }

    private void unregisterEventListeners() {
        this.eventBus.unregister(Render2DListener.class, this);
        this.eventBus.unregister(PacketSendListener.class, this);
    }

    @Override
    public void onRender2D(final Render2DEvent event) {
        final DrawContext drawContext = event.ctx;
        final int x = posX.getValueInt();
        final int y = posY.getValueInt();

        RenderUtil.startDrawing();

        final LivingEntity target = this.mc.player.getAttacking();

        if (!(target instanceof PlayerEntity playerEntity)) {
            opacity = RenderUtil.method412(opacity, 1.0f, 15.0f);
            RenderUtil.stopDrawing();
            return;
        }

        if (!playerEntity.isAlive()) return;

        float currentOpacity = opacity;
        boolean isTargetInvisible = isEntityInvisible();

        opacity = RenderUtil.method412(currentOpacity, (float) (isTargetInvisible ? 1 : 0), 15.0f);
        PlayerListEntry playerListEntry = this.mc.getNetworkHandler().getPlayerListEntry(playerEntity.getUuid());

        renderTargetHUD(drawContext, playerEntity, playerListEntry, x, y, isTargetInvisible);

        RenderUtil.stopDrawing();
    }

    private boolean isEntityInvisible() {
        final LivingEntity target = this.mc.player.getAttacking();
        return target instanceof PlayerEntity && target.isAlive();
    }

    private void renderTargetHUD(DrawContext drawContext, PlayerEntity playerEntity, PlayerListEntry playerListEntry, int x, int y, boolean isInvisible) {
        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.translate(x, y, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f * opacity));
        matrices.translate(-x, -y, 0.0f);

        renderBackground(drawContext, x, y);
        renderPlayerInfo(drawContext, playerEntity, playerListEntry, x, y, isInvisible);

        matrices.pop();
    }

    private void renderBackground(DrawContext drawContext, int x, int y) {
        RenderUtil.method416(drawContext.getMatrices(), new Color(0, 0, 0, 175), x, y, x + 340, y + 200, 5.0, 5.0, 5.0, 5.0, 10.0);
    }

    private void renderPlayerInfo(DrawContext drawContext, PlayerEntity playerEntity, PlayerListEntry playerListEntry, int x, int y, boolean isInvisible) {
        FontRenderer.drawText(playerEntity.getName().getString() + " - " + RandomUtil.method401(playerEntity.distanceTo(this.mc.player), 0.5) + " blocks", drawContext, x + 23, y + 5, Color.WHITE.getRGB());

        String type = (playerListEntry == null) ? "Type: Bot" : "Type: Player";
        Color typeColor = (playerListEntry == null) ? new Color(255, 80, 80, 255) : Color.WHITE;
        FontRenderer.drawText(type, drawContext, x + 5, y + 35, typeColor.getRGB());

        String healthInfo = "Health: " + Math.round(playerEntity.getHealth() + playerEntity.getAbsorptionAmount());
        FontRenderer.drawText(healthInfo, drawContext, x + 5, y + 65, Color.GREEN.getRGB());

        drawHealthBar(drawContext, playerEntity, x, y);

        FontRenderer.drawText("Invisible: " + isInvisible, drawContext, x + 5, y + 95, Color.WHITE.getRGB());
        FontRenderer.drawText("Ping: " + playerListEntry.getLatency(), drawContext, x + 5, y + 125, Color.WHITE.getRGB());
        PlayerSkinDrawer.draw(drawContext, playerListEntry.getSkinTextures().texture(), x + 3, y + 3, 20);

        if (playerEntity.hurtTime != 0) {
            FontRenderer.drawText("Damage Tick: " + playerEntity.hurtTime, drawContext, x + 125, y + 65, Color.WHITE.getRGB());
            drawDamageTickBar(drawContext, playerEntity, x, y);
        }
    }

    private void drawHealthBar(DrawContext drawContext, PlayerEntity playerEntity, int x, int y) {
        drawContext.fill(x, y + 200, x + 4, y + 200 - Math.min(Math.round((playerEntity.getHealth() + playerEntity.getAbsorptionAmount()) * 5.0f), 171), Color.GREEN.darker().getRGB());
    }

    private void drawDamageTickBar(DrawContext drawContext, PlayerEntity playerEntity, int x, int y) {
        int damageTick = playerEntity.hurtTime;
        drawContext.fill(x + 125, y + 80, x + 125 + damageTick * 15, y + 83, getDamageColor(damageTick).getRGB());
    }

    private Color getDamageColor(final int damageTick) {
        return switch (damageTick) {
            case 10 -> new Color(255, 0, 0, 255);
            case 9 -> new Color(255, 50, 0, 255);
            case 8 -> new Color(255, 100, 0, 255);
            case 7 -> new Color(255, 150, 0, 255);
            case 6 -> new Color(255, 255, 0, 255);
            case 5 -> new Color(200, 255, 0, 255);
            case 4 -> new Color(175, 255, 0, 255);
            case 3 -> new Color(100, 255, 0, 255);
            case 2 -> new Color(50, 255, 0, 255);
            case 1 -> new Color(0, 255, 0, 255);
            default -> throw new IllegalStateException("uv" + damageTick);
        };
    }

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        if (event.packet instanceof PlayerInteractEntityC2SPacket interact_packet)
            interact_packet.handle(new TargetHudAttackHandler(this));
    }
}
