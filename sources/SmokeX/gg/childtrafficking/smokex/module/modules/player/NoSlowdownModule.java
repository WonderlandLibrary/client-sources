// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "NoSlowdown", renderName = "No Slowdown", description = "Prevents item slowdowns", aliases = { "noslow" }, category = ModuleCategory.PLAYER)
public final class NoSlowdownModule extends Module
{
    private final EnumProperty<Mode> modeEnumProperty;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventReceivePacket> sendPacketEvent;
    
    public NoSlowdownModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.NCP);
        this.updateEventListener = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString()));
            if (this.modeEnumProperty.getValue() == Mode.NCP) {
                this.setSuffix("NCP");
                final BlockPos blockPos = new BlockPos(-1.0, -1.0, -1.0);
                if (!event.isPre() && this.mc.thePlayer.isUsingItem()) {
                    this.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                }
            }
            return;
        });
        this.sendPacketEvent = (event -> {
            if (event.getPacket() instanceof S30PacketWindowItems && this.mc.thePlayer.isUsingItem()) {
                this.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                event.cancel();
            }
        });
    }
    
    private enum Mode
    {
        VANILLA, 
        NCP;
    }
}
