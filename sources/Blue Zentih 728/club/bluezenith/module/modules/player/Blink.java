package club.bluezenith.module.modules.player;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;

import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings({"UnstableApiUsage", "NonAsciiCharacters"})
public class Blink extends Module {
    private final LinkedBlockingQueue<Packet<?>> ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ = new LinkedBlockingQueue<>();
    public Blink() {
        super("Blink", ModuleCategory.PLAYER);
    }

    @Override
    public void onDisable(){
        while(!ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ.isEmpty()){
            try {
                PacketUtil.sendSilent(ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Listener
    public void onPacket(PacketEvent e){
        if(e.direction == EnumPacketDirection.CLIENTBOUND && mc.thePlayer != null && mc.thePlayer.ticksExisted > 10 && mc.theWorld.isRemote){
            ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ.add(e.packet);
            e.cancel();
        }
    }

    @Override
    public void onEnable() {
        ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ.clear();
    }
}
