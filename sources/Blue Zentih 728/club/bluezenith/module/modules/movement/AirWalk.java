package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.util.AxisAlignedBB;

@SuppressWarnings("unused")
public class AirWalk extends Module {
    public AirWalk() {
        super("AirWalk", ModuleCategory.MOVEMENT, "AirWalk");
    }
    // this is an excuse to have 2 fly
    private final FloatValue speed = new FloatValue("Speed", 0.28f, 0f, 2f, 0.05f).setIndex(1);

    @Listener
    public void onBlockBB(CollisionEvent e) {
        if (player == null) return;

        // im sorry natasha
        if (e.pos.getY() < mc.thePlayer.posY && !mc.thePlayer.isSneaking()) {
            e.boundingBox = AxisAlignedBB.fromBounds(-3, -2,  -3, 3, 2, 3).offset(e.pos.getX(), e.pos.getY(), e.pos.getZ());
            if(speed.get() != 0){
                MovementUtil.setSpeed(speed.get());
            }
        }
    }
}
