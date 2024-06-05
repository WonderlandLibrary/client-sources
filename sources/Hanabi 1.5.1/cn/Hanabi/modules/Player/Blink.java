package cn.Hanabi.modules.Player;

import ClassSub.*;
import net.minecraft.network.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.types.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import javax.vecmath.*;
import cn.Hanabi.injection.interfaces.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;

public class Blink extends Mod
{
    Class205 time;
    ArrayList<Packet> packets;
    
    
    public Blink() {
        super("Blink", Category.PLAYER);
        this.time = new Class205();
        this.packets = new ArrayList<Packet>();
        this.setState(false);
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        if (eventPacket.getEventType() == EventType.SEND) {
            if (eventPacket.getPacket() instanceof C03PacketPlayer) {
                this.packets.add(eventPacket.getPacket());
                eventPacket.setCancelled(true);
            }
            else if (eventPacket.getPacket() instanceof C08PacketPlayerBlockPlacement || eventPacket.getPacket() instanceof C07PacketPlayerDigging || eventPacket.getPacket() instanceof C09PacketHeldItemChange || eventPacket.getPacket() instanceof C02PacketUseEntity) {
                this.packets.add(eventPacket.getPacket());
                eventPacket.setCancelled(true);
            }
        }
        if (eventPacket.getEventType() == EventType.RECIEVE && eventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
            eventPacket.setCancelled(true);
        }
    }
    
    private void addPosition() {
        final Vector3f vector3f = new Vector3f((float)Blink.mc.thePlayer.posX, (float)Blink.mc.thePlayer.posY, (float)Blink.mc.thePlayer.posZ);
        if (Blink.mc.thePlayer.movementInput.moveForward != 0.0f || ((IKeyBinding)Blink.mc.gameSettings.keyBindJump).getPress() || Blink.mc.thePlayer.movementInput.moveStrafe != 0.0f) {}
    }
    
    public void onEnable() {
        EventManager.register(this);
        if (Blink.mc.thePlayer != null && Blink.mc.theWorld != null) {
            final double posX = Blink.mc.thePlayer.posX;
            final double posY = Blink.mc.thePlayer.posY;
            final double posZ = Blink.mc.thePlayer.posZ;
            final float rotationYaw = Blink.mc.thePlayer.rotationYaw;
            final float rotationPitch = Blink.mc.thePlayer.rotationPitch;
            final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)Blink.mc.theWorld, Blink.mc.thePlayer.getGameProfile());
            entityOtherPlayerMP.inventory = Blink.mc.thePlayer.inventory;
            entityOtherPlayerMP.inventoryContainer = Blink.mc.thePlayer.inventoryContainer;
            entityOtherPlayerMP.setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
            entityOtherPlayerMP.rotationYawHead = Blink.mc.thePlayer.rotationYawHead;
            Blink.mc.theWorld.addEntityToWorld(-1, (Entity)entityOtherPlayerMP);
        }
        this.packets.clear();
    }
    
    public void onDisable() {
        EventManager.unregister(this);
        Blink.mc.theWorld.removeEntityFromWorld(-1);
        final Iterator<Packet> iterator = this.packets.iterator();
        while (iterator.hasNext()) {
            Blink.mc.thePlayer.sendQueue.addToSendQueue((Packet)iterator.next());
            this.time.reset();
        }
        this.packets.clear();
    }
}
