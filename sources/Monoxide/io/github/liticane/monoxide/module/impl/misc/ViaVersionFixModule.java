package io.github.liticane.monoxide.module.impl.misc;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

@ModuleData(name = "ViaVersionFix", description = "Fixes ViaVersion false flagging anticheats", category = ModuleCategory.MISCELLANEOUS)
public class ViaVersionFixModule extends Module {

    @Listen
    public void onPacket(PacketEvent event) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8))
            return;

        final Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            ((C08PacketPlayerBlockPlacement) packet).facingX /= 16;
            ((C08PacketPlayerBlockPlacement) packet).facingZ /= 16;
            ((C08PacketPlayerBlockPlacement) packet).facingY /= 16;
        }
    }

    @Listen
    public void onUpdateEvent(UpdateEvent event) {
        if (ViaLoadingBase.getInstance().getTargetVersion().isOlderThanOrEqualTo(ProtocolVersion.v1_8))
            return;

        if (mc.thePlayer.isBlocking() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            PacketWrapper useItem = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
            useItem.write(Type.VAR_INT, 1);
            PacketUtil.sendToServer(useItem, Protocol1_8To1_9.class, true, true);
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
        }
    }
}
