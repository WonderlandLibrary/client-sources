package best.actinium.module.impl.movement;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.BoundEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.player.MoveUtil;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
        name = "Flight",
        description = "Makes u flyyy",
        category = ModuleCategory.MOVEMENT
)
public class FlightModule extends Module {
    public ModeProperty mode = new ModeProperty("Mode", this, new String[] {"Vanilla","Club","Norules","Vulcan","Air Walk"}, "Vanilla");
    private NumberProperty boostTimer = new NumberProperty("Boost Timer",this,1,5,10,0.1)
            .setHidden(() -> !mode.is("Vulcan"));
    private BooleanProperty funny = new BooleanProperty("funny",this,false);
    private BooleanProperty blink = new BooleanProperty("Blink",this,true);
    public NumberProperty speed = new NumberProperty("What", this, 0.0, 2, 10, 0.01)
            .setHidden(() -> !mode.is("Vanilla"));
    public NumberProperty timer = new NumberProperty("Timer Speed", this, 0.0, 2, 10, 0.1);
    private int flags = 0;

    @Override
    public void onDisable() {
        flags = 0;
        mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Callback
    public void onMotion(MotionEvent event) {
        this.setSuffix(mode.getMode());

        if(!mode.is("Vulcan")) {
            mc.timer.timerSpeed = timer.getValue().floatValue();
        }

        switch (mode.getMode()) {
            case "Vanilla":
                mc.thePlayer.motionY = (mc.gameSettings.keyBindJump.isKeyDown() ? 1 : mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : 0);
                MoveUtil.strafe(speed.getValue().floatValue());
                break;
            case "Norules":
                mc.thePlayer.motionY = (mc.gameSettings.keyBindJump.isKeyDown() ? 0.5 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.5 : 0);
                MoveUtil.strafe(MoveUtil.baseSpeed());
                break;
            case "Club":
               // mc.thePlayer.motionY = -0.0980000019;
                //add the mmc fly duca gave u and make it better
                mc.thePlayer.motionY = 0;
                MoveUtil.strafe(0.15);
                PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getItemStack()));

             //   mc.gameSettings.keyBindJump.pressed = true;
                break;
            case "Vulcan":
                if(flags >= 3) {
                    mc.timer.timerSpeed = boostTimer.getValue().floatValue();
                }
                break;
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        switch (mode.getMode()) {
            case "Norules":
                if((funny.isEnabled() && !PlayerUtil.isInsideBlock()) || blink.isEnabled() && event.getType() == EventType.INCOMING) {
                    event.setCancelled(true);
                }
                break;
            case "Vulcan":
                if(event.getType() == EventType.INCOMING && event.getPacket() instanceof S08PacketPlayerPosLook) {
                    flags++;
                    event.setCancelled(true);
                }
                break;
        }
    }

    @Callback
    public void onBound(BoundEvent event) {
        final AxisAlignedBB axisAlignedBB = AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1, 5).offset(event.getBlockPosition().getX(), event.getBlockPosition().getY(), event.getBlockPosition().getZ());
        if(mode.is("Air Walk") || mode.is("Vulcan")) {
            event.setBoundingBox(axisAlignedBB);
        }
    }

}
