package fr.dog.anticheat.check;

import fr.dog.anticheat.Check;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import fr.dog.module.impl.player.Anticheat;
import net.minecraft.util.BlockPos;


public class Autoblock extends Check {
    public Autoblock(Anticheat anticheat) {
        super("Autoblock", anticheat);
    }

    @Override
    public void onPacket(Packet packet) {
        if (!anticheat.isEnabled() || !anticheat.autoblock.getValue()) {
            return;
        }

        if (packet instanceof S0BPacketAnimation) {
            S0BPacketAnimation animation = (S0BPacketAnimation) packet;

            Entity e = Minecraft.getMinecraft().theWorld.getEntityByID(animation.getEntityID());

            if (e instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) e;
                if (player.blockTicks > 5) {
                    flagPlayer(player, 1);
                }
            }
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onBlockMod(BlockPos pos, IBlockState state) {

    }
}
