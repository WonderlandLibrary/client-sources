package club.bluezenith.module.modules.player;

import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.events.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen extends Module {
    private final IntegerValue speed = new IntegerValue("Speed", 10, 0, 100, 1, true, null).setIndex(1);
    public Regen() {
        super("Regen", ModuleCategory.PLAYER);
    }
    @Listener
    public void onUpdate(@SuppressWarnings("unused") UpdateEvent e){
        if(mc.thePlayer.getFoodStats().getFoodLevel() > 19){
            if(mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth() || mc.thePlayer.hurtTime >= 7){
                for (int i = 0; i < speed.get(); i++) {
                    PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                }
            }
        }
    }
}
