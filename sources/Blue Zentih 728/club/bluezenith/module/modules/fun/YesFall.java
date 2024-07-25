package club.bluezenith.module.modules.fun;

import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.events.Listener;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@SuppressWarnings("unused")
public class YesFall extends Module {
    // lmao thx levzzz for the idea
    public YesFall(){
        super("YesFall", ModuleCategory.FUN);
        this.displayName = "Yes Fall";
    }

    //private final ListValue choices = new ListValue("test", true, "sex1", "sex2", "sex3");

    @Listener
    public void onMove(UpdatePlayerEvent e){
        if(Math.round(mc.thePlayer.fallDistance) % 3 == 0){
            // rip i couldn't make it :((( :cry:
            //i am gaming :sunglasses:
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.fallDistance, mc.thePlayer.posZ, false));
        }
    }
}
