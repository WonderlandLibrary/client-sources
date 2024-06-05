package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiFall extends Module {
    public static Numbers<Double> distance = new Numbers<Double>("Distance", "Distance", 5.0, 4.0, 10.0, 1.0);
    private final Timer timer = new Timer();
    private boolean saveMe;
    //public static Option<Boolean> Void = new Option<Boolean>("Void", "Void", true);

    public AntiFall() {
        super("AntiFall", new String[]{"novoid", "antifall"}, ModuleType.Blatant);
        this.addValues(distance);
    }

    @EventHandler
    private void onUpdate(EventMove e) {
        if ((saveMe && Timer.delay(150)) || mc.thePlayer.isCollidedVertically) {
            saveMe = false;
            Timer.reset();
        }
        int dist = distance.getValue().intValue();
        if (mc.thePlayer.fallDistance > dist) {
            if (!isBlockUnder()) {
                if (!saveMe) {
                    saveMe = true;
                    Timer.reset();
                }
//				mc.thePlayer.motionY = 18F;
//				mc.thePlayer.fallDistance = 5F;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 32, mc.thePlayer.posZ, true));
            }
        }
    }

    private boolean isBlockUnder() {
        if (mc.thePlayer.posY < 0)
            return false;
        for (int off = 0; off < (int) mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = mc.thePlayer.boundingBox.offset(0, -off, 0);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
