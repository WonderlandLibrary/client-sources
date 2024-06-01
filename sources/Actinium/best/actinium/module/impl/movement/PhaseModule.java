package best.actinium.module.impl.movement;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.BoundEvent;
import best.actinium.event.impl.move.EventPushBlock;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.ModeProperty;
import best.actinium.util.IAccess;
import best.actinium.util.player.MoveUtil;
import best.actinium.util.player.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
//shit
@ModuleInfo(
        name = "Phase",
        description = "Makes u phase in blocks",
        category = ModuleCategory.MOVEMENT
)
public class PhaseModule extends Module {
    public ModeProperty mode = new ModeProperty("Phase Mode/Exploitt", this, new String[] {"Aris","Invalid","Vulcan"}, "Vanilla");
    int delay;

    @Callback
    public void onMotion(UpdateEvent event) {
        this.setSuffix(mode.getMode());
        switch (mode.getMode()) {
            case "Aris":
                double multiplier = 0.3;
                final double mx = Math.cos(Math.toRadians(IAccess.mc.thePlayer.rotationYaw + 90.0f));
                final double mz = Math.sin(Math.toRadians(IAccess.mc.thePlayer.rotationYaw + 90.0f));
                final double x = IAccess.mc.thePlayer.movementInput.moveForward * multiplier * mx + IAccess.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
                final double z = IAccess.mc.thePlayer.movementInput.moveForward * multiplier * mz - IAccess.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
                if(IAccess.mc.gameSettings.keyBindSneak.isKeyDown()){
                    double X = IAccess.mc.thePlayer.posX; double Y = IAccess.mc.thePlayer.posY; double Z = IAccess.mc.thePlayer.posZ;
                    if(IAccess.mc.thePlayer.posY == (int) IAccess.mc.thePlayer.posY){
                        IAccess.mc.thePlayer.setPosition(X, Y - 0.3, Z);
                    }
                }

                if (PlayerUtil.isInsideBlock() && MoveUtil.isMoving()) {
                    double speed = 0.02;
                    float direction = (float) Math.toRadians(MoveUtil.getthePlayerDirection());
                    double deltaX = -speed * Math.sin(direction);
                    double deltaZ = speed * Math.cos(direction);

                    IAccess.mc.thePlayer.setPosition(IAccess.mc.thePlayer.posX + deltaX, IAccess.mc.thePlayer.posY, IAccess.mc.thePlayer.posZ + deltaZ);
                }


                if (IAccess.mc.thePlayer.isCollidedHorizontally && !IAccess.mc.thePlayer.isOnLadder() && !PlayerUtil.isInsideBlock()) {
                    IAccess.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(IAccess.mc.thePlayer.posX + x, IAccess.mc.thePlayer.posY, IAccess.mc.thePlayer.posZ + z, false));
                    final double posX2 = IAccess.mc.thePlayer.posX;
                    final double posY2 = IAccess.mc.thePlayer.posY;
                    IAccess.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX2, posY2 - (PlayerUtil.isOnLiquid() ? 9000.0 : 0.09), IAccess.mc.thePlayer.posZ, false));
                    IAccess.mc.thePlayer.setPosition(IAccess.mc.thePlayer.posX + x, IAccess.mc.thePlayer.posY, IAccess.mc.thePlayer.posZ + z);
                    break;
                }
                break;
        }
    }

    @Callback
    public void onBound(BoundEvent event) {
        switch (mode.getMode()) {
            case "Aris":
                if ((event.getBoundingBox() != null) && (event.getBoundingBox().maxY > IAccess.mc.thePlayer.boundingBox.minY)) {
                    event.setCancelled(true);
                }
                break;
        }
    }

    @Callback
    public void onPsuh(EventPushBlock event) {
        event.setCancelled(true);
    }

    @Callback
    public void onPacket(PacketEvent event) {
        switch (mode.getMode()) {
            case "Invalid":
                IAccess.mc.thePlayer.setPosition(IAccess.mc.thePlayer.posX, IAccess.mc.thePlayer.posY - 2, IAccess.mc.thePlayer.posZ);
                if (IAccess.mc.thePlayer.isCollidedHorizontally && event.getType() == EventType.OUTGOING) {
                    if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                        event.setCancelled(true);
                    }
                }
                break;
            case "Aris":
                if (event.getType() == EventType.OUTGOING) {
                    if (PlayerUtil.isInsideBlock()) {
                        return;
                    }
                    final double multiplier = 0.2;
                    final double mx = Math.cos(Math.toRadians(IAccess.mc.thePlayer.rotationYaw + 90.0f));
                    final double mz = Math.sin(Math.toRadians(IAccess.mc.thePlayer.rotationYaw + 90.0f));
                    final double x = IAccess.mc.thePlayer.movementInput.moveForward * multiplier * mx + IAccess.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
                    final double z = IAccess.mc.thePlayer.movementInput.moveForward * multiplier * mz - IAccess.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
                    if (IAccess.mc.thePlayer.isCollidedHorizontally && event.getPacket() instanceof C03PacketPlayer) {
                        delay++;
                        final C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
                        if (this.delay >= 5) {
                            player.x += x;
                            player.z += z;
                            --player.y;
                            this.delay = 0;
                        }
                    }
                }
                break;
        }
    }
}
