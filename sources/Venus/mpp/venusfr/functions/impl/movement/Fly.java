/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTeleportPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="Fly", type=Category.Movement)
public class Fly
extends Function {
    private final ModeSetting mode = new ModeSetting("\u041c\u043e\u0434", "Vanilla", "Vanilla", "Matrix Jump", "Matrix Glide", "GrimAC", "HolyWorld");
    private final SliderSetting horizontal = new SliderSetting("\u041f\u043e \u0433\u043e\u0440\u0438\u0437\u043e\u043d\u0442\u0430\u043b\u0438", 0.5f, 0.0f, 5.0f, 0.1f);
    private final SliderSetting vertical = new SliderSetting("\u041f\u043e \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u0438", 0.5f, 0.0f, 5.0f, 0.1f);
    public Entity vehicle;

    public Fly() {
        this.addSettings(this.mode, this.horizontal, this.vertical);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (Fly.mc.player == null || Fly.mc.world == null) {
            return;
        }
        block0 : switch (this.mode.getIndex()) {
            case 0: {
                this.updatePlayerMotion();
                break;
            }
            case 1: {
                if (Fly.mc.player.isOnGround()) {
                    Fly.mc.player.jump();
                    break;
                }
                MoveUtils.setMotion(Math.min(((Float)this.horizontal.get()).floatValue(), 1.97f));
                Fly.mc.player.motion.y = ((Float)this.vertical.get()).floatValue();
                break;
            }
            case 2: {
                Fly.mc.player.motion = Vector3d.ZERO;
                MoveUtils.setMotion(((Float)this.horizontal.get()).floatValue());
                Fly.mc.player.setMotion(Fly.mc.player.getMotion().x, -0.003, Fly.mc.player.getMotion().z);
                break;
            }
            case 3: {
                for (Entity entity2 : Fly.mc.world.getAllEntities()) {
                    if (!(entity2 instanceof BoatEntity) || !(Fly.mc.player.getDistance(entity2) <= 2.0f)) continue;
                    MoveUtils.setMotion(1.2f);
                    Fly.mc.player.motion.y = 1.0;
                    break block0;
                }
                break;
            }
            case 4: {
                float f = 0.065f;
                Minecraft.getInstance();
                ClientPlayerEntity clientPlayerEntity = Fly.mc.player;
                if (clientPlayerEntity == null || !clientPlayerEntity.isAlive()) break;
                clientPlayerEntity.setMotion(clientPlayerEntity.getMotion().x, clientPlayerEntity.getMotion().y + (double)0.065f, clientPlayerEntity.getMotion().z);
            }
        }
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        if (Fly.mc.player == null || Fly.mc.world == null) {
            return;
        }
        switch (this.mode.getIndex()) {
            case 1: {
                IPacket<?> iPacket = eventPacket.getPacket();
                if (!(iPacket instanceof SPlayerPositionLookPacket)) break;
                SPlayerPositionLookPacket sPlayerPositionLookPacket = (SPlayerPositionLookPacket)iPacket;
                if (Fly.mc.player == null) {
                    this.toggle();
                }
                Fly.mc.player.setPosition(sPlayerPositionLookPacket.getX(), sPlayerPositionLookPacket.getY(), sPlayerPositionLookPacket.getZ());
                Fly.mc.player.connection.sendPacket(new CConfirmTeleportPacket(sPlayerPositionLookPacket.getTeleportId()));
                eventPacket.cancel();
                this.toggle();
                break;
            }
            case 3: {
                IPacket<?> iPacket = eventPacket.getPacket();
                if (!(iPacket instanceof SPlayerPositionLookPacket)) break;
                SPlayerPositionLookPacket sPlayerPositionLookPacket = (SPlayerPositionLookPacket)iPacket;
                this.toggle();
            }
        }
    }

    private void updatePlayerMotion() {
        double d = Fly.mc.player.getMotion().x;
        double d2 = this.getMotionY();
        double d3 = Fly.mc.player.getMotion().z;
        MoveUtils.setMotion(((Float)this.horizontal.get()).floatValue());
        Fly.mc.player.motion.y = d2;
    }

    private double getMotionY() {
        return Fly.mc.gameSettings.keyBindSneak.pressed ? (double)(-((Float)this.vertical.get()).floatValue()) : (Fly.mc.gameSettings.keyBindJump.pressed ? (double)((Float)this.vertical.get()).floatValue() : 0.0);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Fly.mc.player.abilities.isFlying = false;
    }
}

