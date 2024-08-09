package fun.ellant.utils.rubizer.player.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPlayerPacket;

public class CPlayerPacketUtils {

    Minecraft mc = Minecraft.getInstance();
    public void Position(float x, float y, float z, boolean onGround) {
        // CPlayerPacket.PositionPacket positionPacket = new CPlayerPacket.PositionPacket(x, y, z, onGround)));
        // mc.getConnection().sendPacket(positionPacket);

    }
    public void ActionSwimming(boolean state) {
        CPlayerPacket.PositionPacket positionPacket = new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), state);
        mc.getConnection().sendPacket(positionPacket);
    }
    // Метод для поиска первого пустого слота в инвентаре

}
