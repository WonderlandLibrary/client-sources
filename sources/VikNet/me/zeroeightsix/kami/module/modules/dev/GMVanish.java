package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketVehicleMove;

import java.util.Objects;

/**
 * Created by TimG on 16 June 2019
 * Last Updated 16 June 2019 by hub
 */
@Module.Info(name = "GMVanish", category = Module.Category.HIDDEN, description = "Godmode Vanish")
public class GMVanish extends Module {

    private Entity entity;

    @Override
    public void onEnable() {
        if (mc.player == null || mc.player.getRidingEntity() == null) {
            this.disable();
            return;
        }
        entity = mc.player.getRidingEntity();
        mc.player.dismountRidingEntity();
        mc.world.removeEntity(entity);
    }

    @Override
    public void onUpdate() {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (mc.player.getRidingEntity() == null) {
            this.disable();
            return;
        }
        if (entity != null) {
            entity.posX = mc.player.posX;
            entity.posY = mc.player.posY;
            entity.posZ = mc.player.posZ;
            try {
                Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketVehicleMove(entity));
            } catch (Exception e) {
                System.out.println("ERROR: Dude we kinda have a problem here:");
                e.printStackTrace();
            }
        }
    }
}
