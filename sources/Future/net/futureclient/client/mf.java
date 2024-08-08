package net.futureclient.client;

import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.futureclient.client.events.EventPacket;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.futureclient.client.events.EventUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public final class mf extends XB
{
    private iD M;
    private NE d;
    private rF a;
    private Entity D;
    private QE k;
    
    public mf() {
        super(new String[] { "\u0017<kvuxFgbh`m", "\u0007&nddi" });
    }
    
    public static Minecraft B(final mf mf) {
        return mf.k;
    }
    
    public static Minecraft C(final mf mf) {
        return mf.k;
    }
    
    public static Minecraft b(final mf mf) {
        return mf.k;
    }
    
    public static Minecraft e(final mf mf) {
        return mf.k;
    }
    
    public static Minecraft i(final mf mf) {
        return mf.k;
    }
    
    @Override
    public String M() {
        return "'d]b\u000e\u0014cairqy~imowl5=tcpf\t ";
    }
    
    public static Minecraft M(final mf mf) {
        return mf.k;
    }
    
    public static Entity M(final mf mf, final Entity d) {
        return mf.D = d;
    }
    
    @Override
    public String M(final String[] array) {
        if (this.d == null && this.M == null && this.a == null && this.k == null) {
            pg.M().M().M((n)(this.d = new n<lF>(this, null) {
                public final mf k;
                
                public NE(final mf mf, final df df) {
                    this(mf);
                }
                
                private NE(final mf k) {
                    this.k = k;
                    super();
                }
                
                public void M(final EventUpdate eventUpdate) {
                    if (mf.M(this.k) != null) {
                        if (mf.e(this.k).player.isRiding()) {
                            mf.M(this.k, null);
                            return;
                        }
                        mf.M(this.k).setPosition(mf.h(this.k).player.posX, mf.M(this.k).player.posY, mf.C(this.k).player.posZ);
                        mf.i(this.k).player.connection.sendPacket((Packet)new CPacketVehicleMove(mf.M(this.k)));
                    }
                }
                
                public void M(final Event event) {
                    this.M((EventUpdate)event);
                }
            }));
            pg.M().M().M((n)(this.M = new n<fF>(this, null) {
                public final mf k;
                
                private iD(final mf k) {
                    this.k = k;
                    super();
                }
                
                public iD(final mf mf, final df df) {
                    this(mf);
                }
                
                public void M(final EventWorld eventWorld) {
                    mf.M(this.k, null);
                }
                
                public void M(final Event event) {
                    this.M((EventWorld)event);
                }
            }));
            pg.M().M().M((n)(this.a = new n<Ag>(this, null) {
                public final mf k;
                
                private rF(final mf k) {
                    this.k = k;
                    super();
                }
                
                public rF(final mf mf, final df df) {
                    this(mf);
                }
                
                @Override
                public void M(final Ag ag) {
                    if ((ag.M() instanceof CPacketPlayer.Position || ag.M() instanceof CPacketPlayer.PositionRotation) && mf.M(this.k) != null) {
                        ag.M(true);
                    }
                }
                
                public void M(final Event event) {
                    this.M((Ag)event);
                }
            }));
            pg.M().M().M((n)(this.k = new n<we>(this, null) {
                public final mf k;
                
                public QE(final mf mf, final df df) {
                    this(mf);
                }
                
                private QE(final mf k) {
                    this.k = k;
                    super();
                }
                
                public void M(final EventPacket eventPacket) {
                    if (mf.M(this.k) != null) {
                        if (eventPacket.M() instanceof SPacketSetPassengers) {
                            final SPacketSetPassengers sPacketSetPassengers;
                            if ((sPacketSetPassengers = (SPacketSetPassengers)eventPacket.M()).getEntityId() == mf.M(this.k).getEntityId()) {
                                final int[] passengerIds;
                                final int length = (passengerIds = sPacketSetPassengers.getPassengerIds()).length;
                                int i = 0;
                                int n = 0;
                                while (i < length) {
                                    if (passengerIds[n] == mf.b(this.k).player.getEntityId()) {
                                        return;
                                    }
                                    i = ++n;
                                }
                                mf.M(this.k).isDead = false;
                                mf.B(this.k).world.spawnEntity(mf.M(this.k));
                                mf.M(this.k, null);
                            }
                        }
                        else if (eventPacket.M() instanceof SPacketDestroyEntities) {
                            final int[] entityIDs;
                            final int length2 = (entityIDs = ((SPacketDestroyEntities)eventPacket.M()).getEntityIDs()).length;
                            int j = 0;
                            int n2 = 0;
                            while (j < length2) {
                                if (entityIDs[n2] != mf.M(this.k).getEntityId()) {
                                    return;
                                }
                                j = ++n2;
                            }
                            mf.M(this.k, null);
                        }
                    }
                }
                
                public void M(final Event event) {
                    this.M((EventPacket)event);
                }
            }));
        }
        if (array.length == 1) {
            final String s;
            if ((s = array[0]).equalsIgnoreCase("YT0.rhd~")) {
                final Entity ridingEntity;
                if ((ridingEntity = this.k.player.getRidingEntity()) == null) {
                    return "Cyc!`tcG\taz<nlaebg cla$hrjw\u0004S";
                }
                this.D = ridingEntity;
                this.k.player.dismountRidingEntity();
                this.k.world.removeEntity(ridingEntity);
                return "@im|\u007f~*k<xTN.,hs~$";
            }
            else if (s.equalsIgnoreCase("3cklv\u0013\t")) {
                if (this.D == null) {
                    return "Nkq&n`wJ\u000fyx9m`im|\u007f~*k<xTN.,hs~$";
                }
                this.D.isDead = false;
                this.k.world.spawnEntity(this.D);
                this.k.player.startRiding(this.D, true);
                return "Zjwoid c\"3$kivm\tS";
            }
            else if (s.equalsIgnoreCase("1xno~")) {
                if (this.D == null) {
                    return "Puo6~`wc&\t\bz.zswfih a\"f(2kivm\tS";
                }
                this.D = null;
                return "\u0011&nx~$";
            }
        }
        return null;
    }
    
    public static Entity M(final mf mf) {
        return mf.D;
    }
    
    public static Minecraft h(final mf mf) {
        return mf.k;
    }
}
