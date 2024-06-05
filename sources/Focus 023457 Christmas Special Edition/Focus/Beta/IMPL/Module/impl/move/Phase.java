package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.world.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Module {

    public Mode<Enum> modes = new Mode<>("Modes", "Modes", Modes.values(), Modes.BlocksMC);

    public Phase(){
        super("Phase", new String[0], Type.MOVE, "Allows you to walks through blocks");
        this.addValues(modes);
    }

    @Override
    public void onEnable() {
        if(modes.getModeAsString().equalsIgnoreCase("BlocksMC")){
            this.mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 4, mc.thePlayer.posZ);
            this.setEnabled(false);
        }
        if(modes.getModeAsString().equalsIgnoreCase("AAC4")){
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.00000001, mc.thePlayer.posZ, false));
        }
        super.onEnable();
    }

    enum Modes{
        BlocksMC, AAC4
    }
}
