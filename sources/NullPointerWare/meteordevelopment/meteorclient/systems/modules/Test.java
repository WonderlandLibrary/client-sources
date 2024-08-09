package meteordevelopment.meteorclient.systems.modules;

import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;

public class Test extends Module {

    public Test() {
        super(Categories.Misc, "Test", "a module for...testing");
    }

    @Override
    public void onActivate() {
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre event) {

    }


    @EventHandler
    private void onTickPost(final TickEvent.Post event) {

    }

    @EventHandler
    private void onMovePacket(final SendMovementPacketsEvent event) {

    }



}
