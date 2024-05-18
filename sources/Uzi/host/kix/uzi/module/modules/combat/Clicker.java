package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.events.TickEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Stopwatch;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Mouse;

import java.util.Random;

/**
 * Created by Kix on 6/10/2017.
 * Made for the Uzi Universal project.
 */
public class Clicker extends Module {

    private Value<Integer> cps = new Value<Integer>("CPS", 6, 1, 12);
    private Stopwatch stopwatch = new Stopwatch();
    private Random random = new Random();

    public Clicker() {
        super("Clicker", 0, Category.COMBAT);
        add(cps);
    }

    @SubscribeEvent
    public void tick(TickEvent event) {
        if ((mc.currentScreen == null) &&
                (Mouse.isButtonDown(0))) {
            if (stopwatch.hasCompleted(1000L / cps.getValue())) {
                KeyBinding.setKeyBindState(-100, true);
                KeyBinding.onTick(-100);
                stopwatch.reset();
            } else {
                KeyBinding.setKeyBindState(-100, false);
            }
        }
    }

    @SubscribeEvent
    public void packet(RecievePacketEvent event) {
        if ((event.getPacket() instanceof C02PacketUseEntity)) {
            C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity) event.getPacket();
            if ((c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) &&
                    ((c02PacketUseEntity.getEntityFromWorld(mc.theWorld) instanceof EntityPlayer))) {
                EntityPlayer entityPlayer = (EntityPlayer) c02PacketUseEntity.getEntityFromWorld(mc.theWorld);
                if (Uzi.getInstance().getFriendManager().get(entityPlayer.getName()).isPresent()) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
