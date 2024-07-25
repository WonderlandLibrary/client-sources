package club.bluezenith.module.modules.combat;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AttackEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class SprintReset extends Module {
    public SprintReset() {
        super("WTap", ModuleCategory.COMBAT, "WTap");
    }

    private boolean sex = false;

    @Listener
    public void onAttack(AttackEvent event) {
        if (!(event.target instanceof EntityLivingBase)) return;

        final EntityLivingBase target = (EntityLivingBase) event.target;

        if(mc.thePlayer.isSprinting() && event.isPre() && target.hurtTime < 1) {
            PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
            PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
        }
    }
}
