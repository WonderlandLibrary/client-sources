package net.futureclient.client.modules.miscellaneous.notifications;

import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.ZG;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.FC;
import java.awt.TrayIcon;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.EC;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.AutoWalk;
import org.lwjgl.opengl.Display;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Notifications;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Notifications k;
    
    public Listener1(final Notifications k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (Display.isActive()) {
            Notifications.B(false);
            Notifications.e(false);
            Notifications.M(false);
            Notifications.C(false);
            Notifications.b(false);
            return;
        }
        Listener1 listener1 = null;
        Label_1061: {
            final AutoWalk autoWalk;
            if ((autoWalk = (AutoWalk)pg.M().M().M((Class)EC.class)) != null && autoWalk.M() && this.k.stuck.M()) {
                final EnumFacing horizontalFacing = Notifications.getMinecraft16().player.getHorizontalFacing();
                if (Notifications.getMinecraft12().player.getRidingEntity() == null) {
                    if (horizontalFacing.equals((Object)EnumFacing.NORTH) && Notifications.getMinecraft2().player.motionZ == 0.0) {
                        if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                            Notifications.e();
                            if (Notifications.b() > 1) {
                                Notifications.M(0);
                                pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                                if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                    Notifications.M(true);
                                    listener1 = this;
                                    break Label_1061;
                                }
                            }
                        }
                    }
                    else if (horizontalFacing.equals((Object)EnumFacing.SOUTH) && Notifications.getMinecraft5().player.motionX == 0.0 && Notifications.getMinecraft8().player.motionZ == 0.0) {
                        if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                            Notifications.e();
                            if (Notifications.b() > 1) {
                                Notifications.M(0);
                                pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                                if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                    Notifications.M(true);
                                    listener1 = this;
                                    break Label_1061;
                                }
                            }
                        }
                    }
                    else if (horizontalFacing.equals((Object)EnumFacing.EAST) && Notifications.getMinecraft4().player.motionX == 0.0) {
                        if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                            Notifications.e();
                            if (Notifications.b() > 1) {
                                Notifications.M(0);
                                pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                                if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                    Notifications.M(true);
                                    listener1 = this;
                                    break Label_1061;
                                }
                            }
                        }
                    }
                    else {
                        if (!horizontalFacing.equals((Object)EnumFacing.WEST) || Notifications.getMinecraft15().player.motionX != 0.0) {
                            Notifications.M(false);
                            Notifications.M(0);
                            listener1 = this;
                            break Label_1061;
                        }
                        if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                            Notifications.e();
                            if (Notifications.b() > 1) {
                                Notifications.M(0);
                                pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                                if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                    Notifications.M(true);
                                    listener1 = this;
                                    break Label_1061;
                                }
                            }
                        }
                    }
                }
                else if (horizontalFacing.equals((Object)EnumFacing.NORTH) && Notifications.getMinecraft7().player.getRidingEntity().motionZ == 0.0) {
                    if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                        Notifications.e();
                        if (Notifications.b() > 1) {
                            Notifications.M(0);
                            pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                            if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                Notifications.M(true);
                                listener1 = this;
                                break Label_1061;
                            }
                        }
                    }
                }
                else if (horizontalFacing.equals((Object)EnumFacing.SOUTH) && Notifications.getMinecraft10().player.getRidingEntity().motionX == 0.0 && Notifications.getMinecraft6().player.getRidingEntity().motionZ == 0.0) {
                    if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                        Notifications.e();
                        if (Notifications.b() > 1) {
                            Notifications.M(0);
                            pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                            if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                Notifications.M(true);
                                listener1 = this;
                                break Label_1061;
                            }
                        }
                    }
                }
                else if (horizontalFacing.equals((Object)EnumFacing.EAST) && Notifications.getMinecraft14().player.getRidingEntity().motionX == 0.0) {
                    if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                        Notifications.e();
                        if (Notifications.b() > 1) {
                            Notifications.M(0);
                            pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                            if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                Notifications.M(true);
                                listener1 = this;
                                break Label_1061;
                            }
                        }
                    }
                }
                else if (horizontalFacing.equals((Object)EnumFacing.WEST) && Notifications.getMinecraft9().player.getRidingEntity().motionX == 0.0) {
                    if (!Notifications.h() && Notifications.i(this.k).M(2500L)) {
                        Notifications.e();
                        if (Notifications.b() > 1) {
                            Notifications.M(0);
                            pg.M().M().k.displayMessage("No longer moving", "AutoWalk has detected that you are stuck.", TrayIcon.MessageType.NONE);
                            if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                                Notifications.M(true);
                                listener1 = this;
                                break Label_1061;
                            }
                        }
                    }
                }
                else {
                    Notifications.M(false);
                    Notifications.M(0);
                }
            }
            listener1 = this;
        }
        Listener1 listener2 = null;
        Label_1169: {
            if (listener1.k.damage.M() && Notifications.getMinecraft17().player.hurtTime != 0) {
                if (!Notifications.i() && Notifications.h(this.k).M(10000L)) {
                    pg.M().M().k.displayMessage("Damage received", "You have just taken damage.", TrayIcon.MessageType.NONE);
                    if (((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                        Notifications.C(true);
                        listener2 = this;
                        break Label_1169;
                    }
                }
            }
            else {
                Notifications.C(false);
            }
            listener2 = this;
        }
        if (listener2.k.nearby.M()) {
            final Iterator<Entity> iterator = (Iterator<Entity>)Notifications.getMinecraft1().world.playerEntities.iterator();
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer;
                if (ZG.b((Entity)(entityPlayer = (EntityPlayer)iterator.next())) && !Notifications.M(this.k).contains(entityPlayer) && (this.k.showFriends.M() || !pg.M().M().M(entityPlayer.getName())) && !entityPlayer.getName().equals(Notifications.getMinecraft13().player.getName()) && !(entityPlayer instanceof EntityPlayerSP)) {
                    if (Notifications.C() || !Notifications.B(this.k).M(10000L)) {
                        continue;
                    }
                    Notifications.M(this.k).add(entityPlayer);
                    pg.M().M().k.displayMessage("Player in range", new StringBuilder().insert(0, entityPlayer.getName()).append(" came into render distance.").toString(), TrayIcon.MessageType.NONE);
                    if (!((FC.XC)Notifications.M(this.k).M()).equals((Object)FC.XC.a)) {
                        continue;
                    }
                    Notifications.b(true);
                }
                else {
                    Notifications.b(false);
                }
            }
        }
    }
}
