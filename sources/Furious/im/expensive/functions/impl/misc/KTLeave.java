package im.expensive.functions.impl.misc;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.Hand;

@FunctionRegister(name = "KTLeave", type = Category.Miscellaneous)
public class KTLeave extends Function {

    @Override
    public boolean onEnable() {
        super.onEnable();
        mc.getConnection().sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
        for (int i = 0; i < 90; i++) {
            mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY() - 1E-13, mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, false));
            for (int j = -0; j < 1; j++) {
                mc.getConnection().sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY() + 1E-33, mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, false));
                mc.getConnection().sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() - 1E-10,mc.player.getPosZ(), false));
            }
        }
        mc.player.connection.sendPacket(new CPlayerPacket(mc.player.isOnGround()));
        mc.getConnection().sendPacket(new CAnimateHandPacket(Hand.OFF_HAND));
        return false;
    }
}
