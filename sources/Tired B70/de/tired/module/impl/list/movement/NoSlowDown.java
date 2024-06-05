package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.SlowDownEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleAnnotation(name = "NoSlowDown", category = ModuleCategory.MOVEMENT, clickG = "Disable slowdown while blocking or eating")
public class NoSlowDown extends Module {

    public ModeSetting packetMode = new ModeSetting("packetMode", this, new String[]{"ItemChange", "PlayerDigging"});
    public BooleanSetting justAir = new BooleanSetting("justAir", this, true);

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        if (MC.thePlayer.onGround && justAir.getValue()) return;
        if (MC.thePlayer.isBlocking()) {
            switch (packetMode.getValue()) {
                case "ItemChange":
                    sendPacketUnlogged(new C09PacketHeldItemChange(MC.thePlayer.inventory.currentItem));
                    break;
                case "PlayerDigging":
                    sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
            }

        }
    }

    @EventTarget
    public void onEvent(SlowDownEvent e) {
        if (MC.thePlayer.onGround && justAir.getValue()) return;
        if (MC.thePlayer.isBlocking()) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
