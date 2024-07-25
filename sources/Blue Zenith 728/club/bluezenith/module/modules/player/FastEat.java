package club.bluezenith.module.modules.player;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;

@SuppressWarnings("unused")
public class FastEat extends Module {
    private final ModeValue mode = new ModeValue("Mode", "Packet", true, null, "Packet", "Timer").setIndex(1);
    private final BooleanValue groundCheck = new BooleanValue("Ground Check", true, true, null).setIndex(2);
    private final FloatValue timer = new FloatValue("Timer", 2f, 1.1f, 10f, 0.05f, true, () -> mode.is("Timer")).setIndex(4);
    private final IntegerValue packet = new IntegerValue("Packet amount", 5, 1, 30, 1, true, () -> mode.is("Packet")).setIndex(5);

    public FastEat() {
        super("FastEat", ModuleCategory.PLAYER, "fastuse");
    }

    private boolean sentPackets = false;
    private boolean usedTimer = false;

    @Listener
    public void onUpdate(UpdateEvent e) {
        if(!mc.thePlayer.isEating()) {
            sentPackets = false;
            if(usedTimer) {
                mc.timer.timerSpeed = 1F;
                usedTimer = false;
            }
        } else if(!groundCheck.get() || mc.thePlayer.onGround) {
            boolean canFastEat = false;
            if(mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUse() != null) {
                final Item item = mc.thePlayer.getItemInUse().getItem();
                if(item instanceof ItemFood || item instanceof ItemPotion)
                    canFastEat = true;
            }
            if(canFastEat) switch (mode.get()) {
                case "Packet":
                    if(sentPackets) return;
                    for (int i = 0; i < packet.get(); i++) {
                       PacketUtil.sendSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                    }
                    sentPackets = true;
                break;

                case "Timer":
                    mc.timer.timerSpeed = timer.get();
                    usedTimer = true;
                break;
            }
        }
    }

    @Override
    public String getTag() {
        if(mode.get().equals("Packet")) {
            if(packet.get() > 25) return "§7Instant";
            else return "§7Packet " + packet.get();
        }
        else return "§7Timer";
    }
}



