package us.dev.direkt.command.internal.movement;


import java.util.Optional;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author BFCE
 */
public class TP extends Command {

    private boolean offGround;

    public TP() {
        super(Direkt.getInstance().getCommandManager(), "tp");
    }

    @Executes
    public void run(Double offset, Optional<Double> yoff) {
    	double xD = Wrapper.moveLooking(0)[0];
    	double zD = Wrapper.moveLooking(0)[1];
    	Wrapper.getPlayer().setPosition(Wrapper.getPlayer().posX + (xD * offset), Wrapper.getPlayer().posY + yoff.get(), Wrapper.getPlayer().posZ + (zD * offset));
    	}
}
