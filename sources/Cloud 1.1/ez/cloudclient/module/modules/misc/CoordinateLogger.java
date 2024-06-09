package ez.cloudclient.module.modules.misc;

import ez.cloudclient.module.Module;
import ez.cloudclient.util.MessageUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CoordinateLogger extends Module {

    private BlockPos l;

    public CoordinateLogger() {
        super("CoordinateLogger", Category.MISC, "Sends you your death coordinates when you respawn.");
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent e) {
        if (e.getEntity() == mc.player) {
            setDeathLocation(mc.player.getPosition());
        }
    }

    @SubscribeEvent
    public void onSpawn(PlayerEvent.PlayerRespawnEvent e) {
        if (e.player == mc.player) {
            if (getDeathLocation() != null) {
                MessageUtil.sendMessage("You died at " + l.toString(), MessageUtil.Color.GREEN);
            }
        }
    }

    public BlockPos getDeathLocation() {
        return l;
    }

    public void setDeathLocation(BlockPos loc) {
        l = loc;
    }

}
