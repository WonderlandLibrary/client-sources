/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.BowAimbot;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class AWP
extends Module {
    public static AWP get;
    private final AnimationUtils usingProgress = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private float indicatorScale = 0.0f;
    private float radiusPlus = 0.0f;
    Settings MatrixElytra;
    Settings Packets;
    Settings Massage;
    final TimerHelper wait = new TimerHelper();

    public AWP() {
        super("AWP", 0, Module.Category.COMBAT);
        this.MatrixElytra = new Settings("MatrixElytra", false, (Module)this);
        this.settings.add(this.MatrixElytra);
        this.Packets = new Settings("Packets", 45.0f, 100.0f, 10.0f, this, () -> !this.MatrixElytra.bValue);
        this.settings.add(this.Packets);
        this.Massage = new Settings("Massage", true, (Module)this);
        this.settings.add(this.Massage);
    }

    private final float getCurrentLongUseDamage(float packetsCount) {
        return 2.24f + packetsCount * 0.092159994f;
    }

    @EventTarget
    public void onSend(EventSendPacket event) {
        Packet packet = event.getPacket();
        if (!(packet instanceof CPacketPlayerDigging)) {
            return;
        }
        CPacketPlayerDigging packet2 = (CPacketPlayerDigging)packet;
        if (packet2 != null && packet2.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && this.correctUseMod() && this.wait.hasReached(100.0)) {
            this.damageMultiply(this.hasTautString(this.getTautPercent()), (int)this.Packets.fValue, this.Massage.bValue);
            this.wait.reset();
        }
    }

    private final boolean correctUseMod() {
        return Minecraft.player.isBowing() && this.actived;
    }

    private final void drawIndicator(float scaling, ScaledResolution sr) {
        this.usingProgress.to = this.getTautPercent();
        float progress = MathUtils.clamp(this.usingProgress.getAnim(), 0.05f, 1.0f);
        float plusRad = this.radiusPlus;
        float width = 100.0f - 50.0f * plusRad;
        float height = 4.0f;
        float extendY = 30.0f;
        float x = (float)(sr.getScaledWidth() / 2) - width / 2.0f;
        float x2 = (float)(sr.getScaledWidth() / 2) + width / 2.0f;
        float x3 = (float)(sr.getScaledWidth() / 2) - width / 2.0f + width * progress;
        float y = (float)(sr.getScaledHeight() / 2) + 30.0f;
        float y2 = y + 4.0f;
        float alphed = scaling * scaling;
        int colorShadow = ColorUtils.getColor(5, (int)(plusRad * 255.0f), 14, (int)((90.0f + plusRad * 45.0f) * alphed));
        int colorLeft = ColorUtils.getOverallColorFrom(ColorUtils.getColor(255, 110, 70, (int)(140.0f * alphed)), ColorUtils.swapAlpha(colorShadow, alphed * 80.0f), plusRad);
        int colorRight = ColorUtils.getOverallColorFrom(ColorUtils.getColor(140, 255, 255, (int)(120.0f * alphed)), ColorUtils.swapAlpha(colorShadow, alphed * 95.0f), plusRad);
        GlStateManager.pushMatrix();
        RenderUtils.customScaledObject2D(x, y, width, 4.0f, scaling);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x3, y2, 2.0f, 0.0f, colorLeft, colorRight, colorRight, colorLeft, false, true, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 2.0f, 2.0f + plusRad * 3.25f, colorShadow, colorShadow, colorShadow, colorShadow, false, false, true);
        GlStateManager.popMatrix();
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        if (this.correctUseMod() && this.indicatorScale != 1.0f || !this.correctUseMod() && this.indicatorScale != 0.0f) {
            this.indicatorScale = MathUtils.harp(this.indicatorScale, this.correctUseMod() ? 1.0f : 0.0f, (float)Minecraft.frameTime * 0.005f);
        }
        if (this.indicatorScale == 0.0f) {
            return;
        }
        this.radiusPlus = MathUtils.harp(this.radiusPlus, this.hasTautString(this.getTautPercent()) && (double)this.usingProgress.getAnim() > 0.995 ? 1.0f : 0.0f, (float)Minecraft.frameTime * 0.01f);
        this.drawIndicator(this.indicatorScale, sr);
    }

    private final float getTautPercent() {
        return (float)Minecraft.player.getItemInUseMaxCount() / this.getCurrentLongUseDamage(this.MatrixElytra.bValue ? 26.0f : this.Packets.fValue) >= 1.0f ? 1.0f : (float)Minecraft.player.getItemInUseMaxCount() / this.getCurrentLongUseDamage(this.MatrixElytra.bValue ? 26.0f : this.Packets.fValue);
    }

    private final boolean hasTautString(float used) {
        return used == 1.0f;
    }

    private final void damageMultiply(boolean successfully, int packetsCount, boolean sendFakeMassage) {
        if (!successfully) {
            if (sendFakeMassage) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.name + "\u00a7r\u00a77]: \u041d\u0435 \u043c\u043e\u0433\u0443 \u0443\u0432\u0435\u043b\u0438\u0447\u0438\u0442\u044c \u0443\u0440\u043e\u043d \u043b\u0443\u043a\u0430.", false);
            }
            return;
        }
        float yaw = BowAimbot.get.getTarget() != null ? BowAimbot.getVirt()[0] : Minecraft.player.rotationYaw;
        float pitch = BowAimbot.get.getTarget() != null ? BowAimbot.getVirt()[1] : Minecraft.player.rotationPitch;
        Clip.goClip(0.0, 0.0, false);
        if (this.MatrixElytra.bValue) {
            if (ElytraBoost.canElytra()) {
                ElytraBoost.equipElytra();
                ElytraBoost.badPacket();
                Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
                Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
                Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
                ElytraBoost.badPacket();
                ElytraBoost.dequipElytra();
                Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
                for (int packet = 0; packet < 26; ++packet) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.2, Minecraft.player.posZ, false));
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                }
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, 4.2f, false));
            } else {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.name + "\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u043d\u0435\u0442 \u044d\u043b\u0438\u0442\u0440\u044b \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435.", false);
            }
        } else {
            Minecraft.player.connection.preSendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
            for (int packet = 0; packet < packetsCount / 2; ++packet) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + (double)0.002f, Minecraft.player.posZ, false));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - (double)0.002f, Minecraft.player.posZ, true));
            }
            Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, (float)MathUtils.clamp((double)pitch * 1.0001, -89.9, 89.9), false));
        }
        if (sendFakeMassage) {
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.name + "\u00a7r\u00a77]: \u0423\u0432\u0435\u043b\u0438\u0447\u0432\u0430\u044e \u0443\u0440\u043e\u043d \u043b\u0443\u043a\u0430.", false);
        }
        this.usingProgress.setAnim(0.0f);
    }
}

