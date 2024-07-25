package club.bluezenith.util.player;

import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.MinecraftInstance;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class PlayerUtil extends MinecraftInstance {
    private static final MillisTimer verus_timer = new MillisTimer();
    public static boolean damageNormal(float dist){
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        return true;
    }

    public static boolean damageStepping(float dist){
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist/2, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist/1.5, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist/1.3, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist/1.1, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + dist, mc.thePlayer.posZ, false));
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        return true;
    }

    public static boolean isVoidBelow() {
        if(mc.thePlayer.onGround) return false;
        for(double i = mc.thePlayer.posY; i > 0; i--) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if(!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir))
                return false;
            pos = null;
        }
        return true;
    }

    public static int damageEdit(UpdatePlayerEvent e, float dist, int progress){
        switch (progress){
            case 0:
                e.onGround = false;
                e.y = mc.thePlayer.posY + dist;
                return progress + 1;
            case 1:
                e.onGround = false;
                return progress + 1;
            case 2:
                e.onGround = true;
                return progress + 1;
        }
        return progress;
    }

    public static boolean damageVerus(UpdatePlayerEvent e, int jumps, int[] progress){
        if(progress[0] <= jumps){
            e.onGround = false;
            if(verus_timer.hasTicksPassed(12)){
                if(progress[0] < jumps){
                    mc.thePlayer.jump();
                }
                progress[0] += 1;
                verus_timer.reset();
            }
        }
        return progress[0] > jumps;
    }
}
