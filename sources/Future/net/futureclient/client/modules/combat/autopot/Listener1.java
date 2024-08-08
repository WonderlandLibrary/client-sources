package net.futureclient.client.modules.combat.autopot;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.hf;
import net.futureclient.client.of;
import net.futureclient.client.modules.combat.AutoTotem;
import net.futureclient.client.db;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AutoPot;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final AutoPot k;
    
    public Listener1(final AutoPot k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        this.k.e(String.format("AutoPot §7[§F%s§7]", this.k.b()));
        final Speed speed = (Speed)pg.M().M().M((Class)db.class);
        if (((AutoTotem)pg.M().M().M((Class)of.class)).k) {
            return;
        }
        switch (hf.k[eventMotion.M().ordinal()]) {
            case 1: {
                Listener1 listener1 = null;
                Label_0794: {
                    if (this.k.e() != -1 && AutoPot.getMinecraft19().player.getHealth() <= AutoPot.M(this.k).B().floatValue() && this.k.K.e(AutoPot.b(this.k).B().floatValue() * 1000.0f)) {
                        if (AutoPot.getMinecraft27().player.collidedVertically && speed != null && !speed.M() && !AutoPot.M(this.k).M()) {
                            this.k.K.e();
                            AutoPot.getMinecraft1().player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(AutoPot.getMinecraft15().player.posX, AutoPot.getMinecraft21().player.posY, AutoPot.getMinecraft36().player.posZ, AutoPot.getMinecraft23().player.rotationYaw, -90.0f, true));
                            this.k.M(this.k.e(), AutoPot.e(this.k).B().intValue() - 1);
                            AutoPot.getMinecraft8().player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoPot.e(this.k).B().intValue() - 1));
                            if (this.k.19Fix.M()) {
                                AutoPot.getMinecraft11().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                            }
                            else {
                                AutoPot.getMinecraft30().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                            }
                            AutoPot.getMinecraft12().player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoPot.getMinecraft33().player.inventory.currentItem));
                            AutoPot.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoPot.getMinecraft41().player.posX, AutoPot.getMinecraft43().player.posY + 1.4429571377E-314, AutoPot.getMinecraft25().player.posZ, true));
                            AutoPot.getMinecraft10().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoPot.getMinecraft32().player.posX, AutoPot.getMinecraft9().player.posY + 0.0, AutoPot.getMinecraft20().player.posZ, true));
                            AutoPot.getMinecraft37().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoPot.getMinecraft35().player.posX, AutoPot.getMinecraft26().player.posY + 1.0, AutoPot.getMinecraft31().player.posZ, true));
                            AutoPot.getMinecraft14().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoPot.getMinecraft22().player.posX, AutoPot.getMinecraft6().player.posY + 3.395193264E-315, AutoPot.getMinecraft29().player.posZ, true));
                            AutoPot.getMinecraft4().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AutoPot.getMinecraft18().player.posX, AutoPot.getMinecraft3().player.posY + 5.0927899E-315, AutoPot.getMinecraft40().player.posZ, true));
                            AutoPot.b(this.k, AutoPot.getMinecraft13().player.posX);
                            listener1 = this;
                            AutoPot.e(this.k, AutoPot.getMinecraft7().player.posY + 5.0927899E-315);
                            AutoPot.M(this.k, AutoPot.getMinecraft24().player.posZ);
                            AutoPot.M(this.k, 5);
                            break Label_0794;
                        }
                        eventMotion.e(90.0f);
                        this.k.j = true;
                        this.k.K.e();
                    }
                    listener1 = this;
                }
                if (AutoPot.e(listener1.k) >= 0) {
                    eventMotion.M(true);
                }
                if (AutoPot.e(this.k) == 0) {
                    AutoPot.getMinecraft39().player.motionZ = 0.0;
                    AutoPot.getMinecraft16().player.motionX = 0.0;
                    AutoPot.getMinecraft38().player.setPositionAndUpdate(AutoPot.e(this.k), AutoPot.b(this.k), AutoPot.M(this.k));
                    AutoPot.getMinecraft28().player.motionY = 5.941588215E-315;
                }
                AutoPot.M(this.k);
            }
            case 2:
                if (this.k.j) {
                    this.k.M(this.k.e(), AutoPot.e(this.k).B().intValue() - 1);
                    AutoPot.getMinecraft34().player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoPot.e(this.k).B().intValue() - 1));
                    if (this.k.19Fix.M()) {
                        AutoPot.getMinecraft17().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    }
                    else {
                        AutoPot.getMinecraft5().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    }
                    AutoPot.getMinecraft2().player.connection.sendPacket((Packet)new CPacketHeldItemChange(AutoPot.getMinecraft42().player.inventory.currentItem));
                    this.k.K.e();
                    this.k.j = false;
                    break;
                }
                break;
        }
    }
}
