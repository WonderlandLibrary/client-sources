package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.mixin.accessor.AccessorEntity;
import net.shoreline.client.util.chat.ChatUtil;
import net.minecraft.entity.Entity;
import net.shoreline.client.util.Globals;

import java.util.Arrays;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class VanishCommand extends Command
{
    //
    Argument<String> dismountArgument = new StringArgument("Dismount/Remount",
            "Desync or resync the entity", Arrays.asList("dismount", "remount"));
    //
    private Entity mount;

    /**
     *
     */
    public VanishCommand()
    {
        super("Vanish", "Desyncs the riding entity");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput()
    {
        String dismount = dismountArgument.getValue();
        if (dismount.equalsIgnoreCase("dismount"))
        {
            if (Globals.mc.player.isRiding() && Globals.mc.player.getVehicle() != null)
            {
                if (mount != null)
                {
                    ChatUtil.error("Entity vanished, must remount!");
                    return;
                }
                mount = Globals.mc.player.getVehicle();
                Globals.mc.player.dismountVehicle();
                Globals.mc.world.removeEntity(mount.getId(), Entity.RemovalReason.DISCARDED);
            }
        }
        else if (dismount.equalsIgnoreCase("remount"))
        {
            if (mount == null)
            {
                ChatUtil.error("No vanished entity!");
                return;
            }
            //
            ((AccessorEntity) mount).hookUnsetRemoved();
            Globals.mc.world.addEntity(mount.getId(), mount);
            Globals.mc.player.startRiding(mount, true);
            mount = null;
        }
    }
}
