package net.shoreline.client.impl.command;

import net.minecraft.entity.Entity;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.mixin.accessor.AccessorEntity;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.Arrays;

/**
 * @author linus
 * @since 1.0
 */
public class VanishCommand extends Command {
    //
    Argument<String> dismountArgument = new StringArgument("Dismount/Remount", "Desync or resync the entity", "dismount", "remount");
    //
    private Entity mount;

    /**
     *
     */
    public VanishCommand() {
        super("Vanish", "Desyncs the riding entity");
    }

    @Override
    public void onCommandInput() {
        String dismount = dismountArgument.getValue();
        if (dismount.equalsIgnoreCase("dismount")) {
            if (mc.player.isRiding() && mc.player.getVehicle() != null) {
                if (mount != null) {
                    ChatUtil.error("Entity vanished, must remount before mounting!");
                    return;
                }
                mount = mc.player.getVehicle();
                mc.player.dismountVehicle();
                mc.world.removeEntity(mount.getId(), Entity.RemovalReason.DISCARDED);
            }
        } else if (dismount.equalsIgnoreCase("remount")) {
            if (mount == null) {
                ChatUtil.error("No vanished entity!");
                return;
            }
            //
            ((AccessorEntity) mount).hookUnsetRemoved();
            mc.world.addEntity(mount);
            mc.player.startRiding(mount, true);
            mount = null;
        }
    }
}
