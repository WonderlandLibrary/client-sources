package arsenic.module.impl.combat;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventAttack;
import arsenic.event.impl.EventUpdate;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Criticals", category = ModuleCategory.Combat)
public class Criticals extends Module {
    public final EnumProperty<CritMode> critMode = new EnumProperty<>("Mode: ", CritMode.Offset);
    public final BooleanProperty visual = new BooleanProperty("Visual", false);
    int vulcanattack = 0;
    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        vulcanattack++;
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        if (visual.getValue()) {
            mc.thePlayer.onCriticalHit(event.getTarget());
        }
        if (critMode.getValue() == CritMode.Offset) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0625, z, true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.1E-5, z, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        }
        if (critMode.getValue() == CritMode.BlocksMC) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.05250000001304, z, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.00150000001304, z, false));
        }
        if (critMode.getValue() == CritMode.Vulcan) {
            if (vulcanattack == 10) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.16477328182606651, z, true));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.08307781780646721, z, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0030162615090425808, z, false));
                vulcanattack = 0;
            }
        }
    };
    @EventLink
    public final Listener<EventUpdate.Pre> preListener = event -> {
        if (critMode.getValue() == CritMode.NoGround) {
            event.setOnGround(false);
        }
    };

    public enum CritMode {
        Offset,
        BlocksMC,
        NoGround,
        Vulcan
    }
}
