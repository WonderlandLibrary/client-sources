package zamorozka.modules.WORLD;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class VehicleOneHit extends Module {

	public VehicleOneHit() {
		super("VehicleOneHit", 0, Category.WORLD);
	}
	
	@EventTarget
    public void onAttack(AttackEvent event) {
        if (event.getTargetEntity() instanceof EntityBoat || event.getTargetEntity() instanceof EntityMinecart) {
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketAnimation());
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
            	mc.player.connection.sendPacket(new CPacketUseEntity(event.getTargetEntity(), CPacketUseEntity.Action.ATTACK));
        }
	}
}