package mathax.client.utils.player;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static mathax.client.MatHax.mc;

public class GotoUtil {
    private double y;

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) {
            MatHax.EVENT_BUS.unsubscribe(this);
            tickEventFuture.complete(null);
        }
        mc.player.setNoGravity(true);
        mc.player.setVelocity(Vec3d.ZERO);
        if (mc.player.getY() != this.y) {

            MoveToUtil.moveTo(mc.player.getX(), this.y, mc.player.getZ(), false, true);
            return;
        }
        if ((mc.player.getX() != xpos) || (mc.player.getZ() != zpos) ) {

            MoveToUtil.moveTo(xpos, y, zpos, false, true);
            return;
        }

        if (mc.player.getY() != ypos) {
            MoveToUtil.moveTo(xpos, ypos, zpos, false, true);
        }
        mc.player.setNoGravity(false);
        tickEventFuture.complete(null);

    }

    private static CompletableFuture<Void> tickEventFuture;

    private double xpos;
    private double ypos;
    private double zpos;

    public void moveto(double xpos, double ypos, double zpos) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.zpos = zpos;
        Vec3d goto1 = new Vec3d(xpos, ypos, zpos);

        this.y = CanTeleport.searchGoodYandTeleport(mc.player.getPos(), goto1);
        if (this.y == mc.player.getY()) {

            MoveToUtil.moveTo(xpos, ypos, zpos, false, true);
            return;
        }



        MatHax.EVENT_BUS.subscribe(this);
        tickEventFuture = new CompletableFuture<>();
        try {
            tickEventFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        MatHax.EVENT_BUS.unsubscribe(this);
    }


}
