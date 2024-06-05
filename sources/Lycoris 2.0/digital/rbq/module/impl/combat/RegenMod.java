/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.combat;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;

@Label(value="Regen")
@Category(value=ModuleCategory.COMBAT)
public final class RegenMod
extends Module {
    public static final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.POTION);
    public static final DoubleOption packets = new DoubleOption("Packets", 10.0, 1.0, 1000.0, 1.0);

    public RegenMod() {
        this.setMode(mode);
        this.addOptions(mode, packets);
    }

    @Listener(value=TickEvent.class)
    public final void onTick() {
        switch ((Mode)((Object)mode.getValue())) {
            case PACKET: {
                this.packetRegen(((Double)packets.getValue()).intValue());
                break;
            }
            case POTION: {
                if (!RegenMod.mc.thePlayer.isPotionActive(Potion.regeneration)) break;
                this.packetRegen((int)((Double)packets.getValue() / 2.0 * (double)RegenMod.mc.thePlayer.getActivePotionEffect(Potion.regeneration).getAmplifier()));
            }
        }
    }

    private void packetRegen(int packets) {
        if (RegenMod.mc.thePlayer.onGround) {
            for (int i = 0; i < packets; ++i) {
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
            }
        }
    }

    private static enum Mode {
        PACKET,
        POTION;

    }
}

