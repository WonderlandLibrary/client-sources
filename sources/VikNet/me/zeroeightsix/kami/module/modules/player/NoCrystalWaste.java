package me.zeroeightsix.kami.module.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

//Made by Viktisen
//Thanks to 2q1/kami for helping

@Module.Info(name = "NoCrystalWaste", description = "Avoids accidental offhand crystal placement",category = Module.Category.PLAYER)
public class NoCrystalWaste extends Module {

    private Setting<Boolean> Chorus = register(Settings.b("Chorus", false));
    private Setting<Boolean> EmptyHand = register(Settings.b("EmptyHand", false));

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            if (Chorus.getValue())
                if (mc.player.getHeldItemMainhand().getItem() == Items.CHORUS_FRUIT) event.cancel();
        }
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            if (EmptyHand.getValue())
                if (mc.player.itemStackMainHand == ItemStack.EMPTY) event.cancel();
        }
    });
}