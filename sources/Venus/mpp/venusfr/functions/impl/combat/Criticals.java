/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventMotion;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Criticals", type=Category.Combat)
public class Criticals
extends Function {
    private final Minecraft mc = Minecraft.getInstance();
    private final ModeSetting mode = new ModeSetting("Type", "Vanilla", "Vanilla", "FunTime", "ReallyWorld");
    private final SliderSetting jumpheight = new SliderSetting("\u0412\u044b\u0441\u043e\u0442\u0430", 0.15f, 0.05f, 1.0f, 0.05f).setVisible(this::lambda$new$0);
    private KillAura killAura;

    public Criticals(KillAura killAura) {
        this.addSettings(this.mode, this.jumpheight);
        this.killAura = killAura;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Subscribe
    public void onPlayerUpdate(EventUpdate eventUpdate) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mode.is("Vanilla")) {
            return;
        }
        if (this.mode.is("FunTime") && this.killAura.isState() && this.killAura.getTarget() != null && this.mc.player.isOnGround() && this.mc.player.fallDistance == 0.0f) {
            this.mc.player.jump();
        }
    }

    @Subscribe
    public void onPlayerAttack(EventMotion eventMotion) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mode.is("Vanilla")) {
            if (this.killAura.isState() && this.killAura.getTarget() != null && this.mc.player.isOnGround()) {
                this.mc.player.motion = new Vector3d(0.0, ((Float)this.jumpheight.get()).floatValue(), 0.0);
            }
        } else if (this.mode.is("FunTime")) {
            if (this.killAura.isState() && this.killAura.getTarget() != null && this.mc.player.isOnGround() && this.mc.player.fallDistance > 0.0f && !this.mc.player.isInWater()) {
                this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY() + 0.1, this.mc.player.getPosZ(), false));
                this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY(), this.mc.player.getPosZ(), false));
                this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY() + 0.01, this.mc.player.getPosZ(), false));
                this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY(), this.mc.player.getPosZ(), false));
                this.mc.player.swingArm(Hand.MAIN_HAND);
            }
        } else if (this.mode.is("ReallyWorld") && this.killAura.isState() && this.killAura.getTarget() != null) {
            ClientPlayerEntity clientPlayerEntity = this.mc.player;
            if (clientPlayerEntity.isOnGround()) {
                this.mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY() + 1.0E-8, clientPlayerEntity.getPosZ(), clientPlayerEntity.rotationYaw, clientPlayerEntity.rotationPitch, false));
                this.mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY() - 1.0E-9, clientPlayerEntity.getPosZ(), clientPlayerEntity.rotationYaw, clientPlayerEntity.rotationPitch, false));
                if (!clientPlayerEntity.isHandActive()) {
                    this.mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                    this.mc.playerController.onStoppedUsingItem(clientPlayerEntity);
                }
            } else {
                this.mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(clientPlayerEntity.getPosX(), clientPlayerEntity.getPosY() - 1.0E-9, clientPlayerEntity.getPosZ(), clientPlayerEntity.rotationYaw, clientPlayerEntity.rotationPitch, false));
                if (!clientPlayerEntity.isHandActive()) {
                    this.mc.getConnection().sendPacket(new CPlayerTryUseItemPacket(Hand.OFF_HAND));
                    this.mc.playerController.onStoppedUsingItem(clientPlayerEntity);
                }
            }
        }
    }

    private Boolean lambda$new$0() {
        return this.mode.is("Vanilla");
    }
}

