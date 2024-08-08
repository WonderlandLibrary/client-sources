package net.futureclient.client.modules.movement.speed;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.futureclient.client.ZG;
import net.futureclient.client.Uh;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.futureclient.client.db;
import net.futureclient.client.pb;
import net.futureclient.client.IG;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener2 extends n<fg>
{
    public final Speed k;
    
    public Listener2(final Speed k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
    
    @Override
    public void M(final fg fg) {
        final Freecam freecam;
        if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
            return;
        }
        if (!this.k.speedInWater.M() && (IG.e() || IG.M(true) || Speed.getMinecraft126().player.isOnLadder() || Speed.getMinecraft18().player.isEntityInsideOpaqueBlock())) {
            Speed.M(this.k, 0.0);
            Speed.M(this.k, true);
            return;
        }
        if (Speed.M(this.k)) {
            Speed.M(this.k, 0.0);
            Speed.M(this.k, false);
            return;
        }
        switch (pb.D[this.k.mode.M().ordinal()]) {
            case 1: {
                if (!Speed.M(this.k).e(100L)) {
                    return;
                }
                if (this.k.useTimer.M()) {
                    ((ITimer)((IMinecraft)Speed.getMinecraft10()).getTimer()).setTimerSpeed(1.0888f);
                }
                Listener2 listener2 = null;
                Label_0565: {
                    if (!Speed.getMinecraft110().player.collidedHorizontally) {
                        if (Uh.M(Speed.getMinecraft146().player.posY - (int)Speed.getMinecraft134().player.posY, 3) == Uh.M(1.273197475E-314, 3)) {
                            fg.b(Speed.getMinecraft120().player.motionY = 5.0927899E-315 + ZG.M());
                            listener2 = this;
                            break Label_0565;
                        }
                        if (Uh.M(Speed.getMinecraft58().player.posY - (int)Speed.getMinecraft9().player.posY, 3) == Uh.M(1.9522361275E-314, 3)) {
                            fg.b(Speed.getMinecraft30().player.motionY = 5.941588215E-315 + ZG.M());
                            listener2 = this;
                            break Label_0565;
                        }
                        if (Uh.M(Speed.getMinecraft154().player.posY - (int)Speed.getMinecraft60().player.posY, 3) == Uh.M(0.0, 3)) {
                            fg.b(Speed.getMinecraft62().player.motionY = 1.273197475E-314 - ZG.M());
                            listener2 = this;
                            break Label_0565;
                        }
                        if (Uh.M(Speed.getMinecraft72().player.posY - (int)Speed.getMinecraft101().player.posY, 3) == Uh.M(1.273197475E-314, 3)) {
                            fg.b(Speed.getMinecraft68().player.motionY = 2.54639495E-315 + ZG.M());
                            listener2 = this;
                            break Label_0565;
                        }
                        if (Uh.M(Speed.getMinecraft4().player.posY - (int)Speed.getMinecraft16().player.posY, 3) == Uh.M(1.358077306E-314, 3)) {
                            fg.b(Speed.getMinecraft99().player.motionY = 1.273197475E-314 + ZG.M());
                        }
                    }
                    listener2 = this;
                }
                fg fg2;
                if (Speed.B(listener2.k) == 1 && (Speed.getMinecraft86().player.moveForward != 0.0f || Speed.getMinecraft147().player.moveStrafing != 0.0f)) {
                    Speed.M(this.k, 1.273197475E-314 * ZG.e() - 5.941588215E-315);
                    fg2 = fg;
                }
                else if (Speed.B(this.k) == 2 && (Speed.getMinecraft43().player.moveForward != 0.0f || Speed.getMinecraft112().player.moveStrafing != 0.0f)) {
                    fg.b(Speed.getMinecraft19().player.motionY = 3.59890486E-315 + ZG.M());
                    Speed.M(this.k, Speed.M(this.k) * (Speed.B(this.k) ? 9.676300807E-315 : 9.16702182E-315));
                    fg2 = fg;
                }
                else if (Speed.B(this.k) == 3) {
                    Speed.M(this.k, Speed.e(this.k) - 6.790386532E-315 * (Speed.e(this.k) - ZG.e()));
                    Speed.b(this.k, !Speed.B(this.k));
                    fg2 = fg;
                }
                else {
                    final WorldClient world = Speed.getMinecraft148().world;
                    final EntityPlayerSP player = Speed.getMinecraft88().player;
                    final AxisAlignedBB entityBoundingBox = Speed.getMinecraft127().player.getEntityBoundingBox();
                    final double motionY = Speed.getMinecraft159().player.motionY;
                    final double n = 0.0;
                    if ((world.getCollisionBoxes((Entity)player, entityBoundingBox.offset(n, motionY, n)).size() > 0 || Speed.getMinecraft102().player.collidedVertically) && Speed.B(this.k) > 0) {
                        Speed.i(this.k, (Speed.getMinecraft3().player.moveForward != 0.0f || Speed.getMinecraft7().player.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    Speed.M(this.k, Speed.e(this.k) - Speed.e(this.k) / 0.0);
                    fg2 = fg;
                }
                ZG.M(fg2, Speed.M(this.k, Math.max(Speed.M(this.k), ZG.e())));
                if (Speed.getMinecraft114().player.moveForward != 0.0f || Speed.getMinecraft82().player.moveStrafing != 0.0f) {
                    Speed.g(this.k);
                    return;
                }
                break;
            }
            case 2:
                if (Speed.getMinecraft107().player.onGround || Speed.K(this.k) == 0.0) {
                    fg fg3 = null;
                    Label_1246: {
                        if ((!Speed.getMinecraft135().player.collidedHorizontally && Speed.getMinecraft104().player.moveForward != 0.0f) || Speed.getMinecraft27().player.moveStrafing != 0.0f) {
                            if (Speed.K(this.k) == 0.0) {
                                Speed.M(this.k, Speed.M(this.k) * 1.9352601614E-314);
                                Speed.C(this.k, 3);
                                fg3 = fg;
                                break Label_1246;
                            }
                            if (Speed.K(this.k) == 0.0) {
                                fg3 = fg;
                                Speed.C(this.k, 2);
                                Speed.M(this.k, Speed.e(this.k) - 6.790386532E-315 * (Speed.e(this.k) - ZG.e()));
                                break Label_1246;
                            }
                            final WorldClient world2 = Speed.getMinecraft125().world;
                            final EntityPlayerSP player2 = Speed.getMinecraft106().player;
                            final AxisAlignedBB entityBoundingBox2 = Speed.getMinecraft64().player.getEntityBoundingBox();
                            final double n2 = 1.273197475E-314;
                            final double n3 = 0.0;
                            final AxisAlignedBB offset = entityBoundingBox2.offset(n3, n2, n3);
                            final double n4 = 0.0;
                            final double n5 = 0.0;
                            if (world2.getCollisionBoxes((Entity)player2, offset.grow(n5, n4, n5)).size() > 0 || Speed.getMinecraft23().player.collidedVertically) {
                                Speed.C(this.k, 1);
                            }
                        }
                        fg3 = fg;
                    }
                    ZG.M(fg3, Speed.M(this.k, Math.max(Speed.M(this.k), ZG.e())));
                    return;
                }
                break;
            case 3: {
                if (!Speed.M(this.k).e(100L)) {
                    Speed.M(this.k, 1);
                    return;
                }
                if (Speed.getMinecraft70().player.moveForward == 0.0f && Speed.getMinecraft133().player.moveStrafing == 0.0f) {
                    Speed.M(this.k, ZG.e());
                }
                if (Uh.M(Speed.getMinecraft142().player.posY - (int)Speed.getMinecraft122().player.posY, 3) == Uh.M(1.273197475E-314, 3)) {
                    fg.b(Speed.getMinecraft29().player.motionY = 5.0927899E-315 + ZG.M());
                }
                else if (Uh.M(Speed.getMinecraft108().player.posY - (int)Speed.getMinecraft117().player.posY, 3) == Uh.M(1.9522361275E-314, 3)) {
                    fg.b(Speed.getMinecraft28().player.motionY = 5.941588215E-315 + ZG.M());
                }
                else if (Uh.M(Speed.getMinecraft61().player.posY - (int)Speed.getMinecraft69().player.posY, 3) == Uh.M(0.0, 3)) {
                    fg.b(Speed.getMinecraft85().player.motionY = 1.273197475E-314 + ZG.M());
                }
                final WorldClient world3 = Speed.getMinecraft74().world;
                final EntityPlayerSP player3 = Speed.getMinecraft152().player;
                final AxisAlignedBB entityBoundingBox3 = Speed.getMinecraft92().player.getEntityBoundingBox();
                final double n6 = 2.54639495E-315;
                final double n7 = 0.0;
                if (world3.getCollisionBoxes((Entity)player3, entityBoundingBox3.offset(n7, n6, n7)).size() > 0 && Uh.M(Speed.getMinecraft160().player.posY - (int)Speed.getMinecraft67().player.posY, 3) == Uh.M(1.273197475E-314, 3)) {
                    fg.b(Speed.getMinecraft89().player.motionY = 2.54639495E-315 + ZG.M());
                }
                Listener2 listener3;
                if (Speed.e(this.k) != 1 || !Speed.getMinecraft45().player.collidedVertically || (Speed.getMinecraft158().player.moveForward == 0.0f && Speed.getMinecraft80().player.moveStrafing == 0.0f)) {
                    if (Speed.e(this.k) != 2 || !Speed.getMinecraft121().player.collidedVertically || (Speed.getMinecraft91().player.moveForward == 0.0f && Speed.getMinecraft53().player.moveStrafing == 0.0f)) {
                        if (Speed.e(this.k) == 3) {
                            final double n8 = 6.790386532E-315 * (Speed.e(this.k) - ZG.e());
                            final Speed k = this.k;
                            listener3 = this;
                            Speed.M(k, Speed.e(this.k) - n8);
                        }
                        else {
                            final WorldClient world4 = Speed.getMinecraft40().world;
                            final EntityPlayerSP player4 = Speed.getMinecraft84().player;
                            final AxisAlignedBB entityBoundingBox4 = Speed.getMinecraft5().player.getEntityBoundingBox();
                            final double motionY2 = Speed.getMinecraft57().player.motionY;
                            final double n9 = 0.0;
                            Listener2 listener4 = null;
                            Label_1911: {
                                if ((world4.getCollisionBoxes((Entity)player4, entityBoundingBox4.offset(n9, motionY2, n9)).size() > 0 || Speed.getMinecraft79().player.collidedVertically) && Speed.e(this.k) > 0) {
                                    if (1.273197475E-314 * ZG.e() - 5.941588215E-315 > Speed.M(this.k)) {
                                        Speed.M(this.k, 0);
                                        listener4 = this;
                                        break Label_1911;
                                    }
                                    Speed.M(this.k, (Speed.getMinecraft156().player.moveForward != 0.0f || Speed.getMinecraft46().player.moveStrafing != 0.0f) ? 1 : 0);
                                }
                                listener4 = this;
                            }
                            Speed.M(listener4.k, Speed.e(this.k) - Speed.e(this.k) / 0.0);
                            listener3 = this;
                        }
                    }
                    else {
                        fg.b(Speed.getMinecraft140().player.motionY = 1.273197475E-314 + ZG.M());
                        final Speed i = this.k;
                        listener3 = this;
                        Speed.M(i, Speed.M(this.k) * 1.9352601614E-314);
                    }
                }
                else {
                    listener3 = this;
                    Speed.M(this.k, 0.0 * ZG.e() - 5.941588215E-315);
                }
                if (Speed.e(listener3.k) > 8) {
                    Speed.M(this.k, ZG.e());
                }
                Speed.M(this.k, Math.max(Speed.M(this.k), ZG.e()));
                if (Speed.e(this.k) > 0) {
                    ZG.M(fg, Speed.M(this.k));
                }
                if (Speed.getMinecraft115().player.moveForward != 0.0f || Speed.getMinecraft42().player.moveStrafing != 0.0f) {
                    Speed.C(this.k);
                    return;
                }
                break;
            }
            case 4: {
                if (!Speed.M(this.k).e(100L)) {
                    Speed.B(this.k, 4);
                    return;
                }
                if (Uh.M(Speed.getMinecraft54().player.posY - (int)Speed.getMinecraft31().player.posY, 3) == Uh.M(1.918284195E-314, 3)) {
                    final EntityPlayerSP player5 = Speed.getMinecraft36().player;
                    player5.motionY -= 5.941588215E-315 + ZG.M();
                    fg.b(fg.e() - (1.7179677924E-314 + ZG.M()));
                    final EntityPlayerSP player6 = Speed.getMinecraft137().player;
                    player6.posY -= 1.7179677924E-314 + ZG.M();
                }
                Listener2 listener5;
                if (Speed.b(this.k) != 0.0 || (Speed.getMinecraft144().player.moveForward == 0.0f && Speed.getMinecraft15().player.moveStrafing == 0.0f)) {
                    if (Speed.b(this.k) == 0.0) {
                        final double n10 = 6.790386532E-315 * (Speed.e(this.k) - ZG.e());
                        final Speed j = this.k;
                        listener5 = this;
                        Speed.M(j, Speed.e(this.k) - n10);
                    }
                    else {
                        final WorldClient world5 = Speed.getMinecraft157().world;
                        final EntityPlayerSP player7 = Speed.getMinecraft8().player;
                        final AxisAlignedBB entityBoundingBox5 = Speed.getMinecraft48().player.getEntityBoundingBox();
                        final double motionY3 = Speed.getMinecraft13().player.motionY;
                        final double n11 = 0.0;
                        if (world5.getCollisionBoxes((Entity)player7, entityBoundingBox5.offset(n11, motionY3, n11)).size() > 0 || Speed.getMinecraft20().player.collidedVertically) {
                            Speed.B(this.k, 1);
                        }
                        listener5 = this;
                        Speed.M(this.k, Speed.e(this.k) - Speed.e(this.k) / 0.0);
                    }
                }
                else {
                    fg.b(Speed.getMinecraft78().player.motionY = 1.273197475E-314 + ZG.M());
                    final Speed l = this.k;
                    listener5 = this;
                    Speed.M(l, Speed.M(this.k) * 1.9352601614E-314);
                }
                Speed.M(listener5.k, Math.max(Speed.M(this.k), ZG.e()));
                ZG.M(fg, Speed.M(this.k));
                Speed.h(this.k);
            }
            case 5: {
                if (Speed.getMinecraft71().player.moveForward == 0.0f && Speed.getMinecraft138().player.moveStrafing == 0.0f) {
                    return;
                }
                if (this.k.useTimer.M()) {
                    ((ITimer)((IMinecraft)Speed.getMinecraft56()).getTimer()).setTimerSpeed(1.0888f);
                }
                fg fg4;
                if (Speed.B(this.k) == 1 && (Speed.getMinecraft17().player.moveForward != 0.0f || Speed.getMinecraft123().player.moveStrafing != 0.0f)) {
                    Speed.M(this.k, 1.273197475E-314 * ZG.e() - 5.941588215E-315);
                    fg4 = fg;
                }
                else if (Speed.B(this.k) == 2 && (Speed.getMinecraft21().player.moveForward != 0.0f || Speed.getMinecraft14().player.moveStrafing != 0.0f)) {
                    fg.b(Speed.getMinecraft119().player.motionY = 3.59890486E-315 + ZG.M());
                    Speed.M(this.k, Speed.M(this.k) * (Speed.B(this.k) ? 1.4769090705E-314 : 1.1034378113E-314));
                    fg4 = fg;
                }
                else if (Speed.B(this.k) == 3) {
                    Speed.M(this.k, Speed.e(this.k) - 6.790386532E-315 * (Speed.e(this.k) - ZG.e()));
                    Speed.b(this.k, !Speed.B(this.k));
                    fg4 = fg;
                }
                else {
                    final WorldClient world6 = Speed.getMinecraft35().world;
                    final EntityPlayerSP player8 = Speed.getMinecraft1().player;
                    final AxisAlignedBB entityBoundingBox6 = Speed.getMinecraft118().player.getEntityBoundingBox();
                    final double motionY4 = Speed.getMinecraft94().player.motionY;
                    final double n12 = 0.0;
                    if ((world6.getCollisionBoxes((Entity)player8, entityBoundingBox6.offset(n12, motionY4, n12)).size() > 0 || Speed.getMinecraft39().player.collidedVertically) && Speed.B(this.k) > 0) {
                        Speed.i(this.k, (Speed.getMinecraft32().player.moveForward != 0.0f || Speed.getMinecraft136().player.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    Speed.M(this.k, Speed.e(this.k) - Speed.e(this.k) / 0.0);
                    fg4 = fg;
                }
                ZG.M(fg4, Speed.M(this.k, Math.max(Speed.M(this.k), ZG.e())));
                if (Speed.getMinecraft12().player.moveForward != 0.0f || Speed.getMinecraft151().player.moveStrafing != 0.0f) {
                    Speed.g(this.k);
                    return;
                }
                break;
            }
            case 6: {
                if (!Speed.M(this.k).e(100L)) {
                    Speed.e(this.k, 1);
                    return;
                }
                if (Speed.getMinecraft96().player.moveForward == 0.0f && Speed.getMinecraft44().player.moveStrafing == 0.0f) {
                    Speed.M(this.k, ZG.e());
                }
                Listener2 listener6;
                if (Speed.i(this.k) == 1 && Speed.getMinecraft65().player.collidedVertically && (Speed.getMinecraft77().player.moveForward != 0.0f || Speed.getMinecraft150().player.moveStrafing != 0.0f)) {
                    listener6 = this;
                    Speed.M(this.k, 0.0 + ZG.e() - 5.941588215E-315);
                }
                else if (Speed.i(this.k) == 2 && Speed.getMinecraft59().player.collidedVertically && (Speed.getMinecraft63().player.moveForward != 0.0f || Speed.getMinecraft66().player.moveStrafing != 0.0f)) {
                    fg.b(Speed.getMinecraft131().player.motionY = 1.273197475E-314 + ZG.M());
                    final Speed m = this.k;
                    listener6 = this;
                    Speed.M(m, Speed.M(this.k) * 1.9352601614E-314);
                }
                else if (Speed.i(this.k) == 3) {
                    final double n13 = 6.790386532E-315 * (Speed.e(this.k) - ZG.e());
                    final Speed k2 = this.k;
                    listener6 = this;
                    Speed.M(k2, Speed.e(this.k) - n13);
                }
                else {
                    final WorldClient world7 = Speed.getMinecraft111().world;
                    final EntityPlayerSP player9 = Speed.getMinecraft38().player;
                    final AxisAlignedBB entityBoundingBox7 = Speed.getMinecraft34().player.getEntityBoundingBox();
                    final double motionY5 = Speed.getMinecraft109().player.motionY;
                    final double n14 = 0.0;
                    Listener2 listener7 = null;
                    Label_3448: {
                        if ((world7.getCollisionBoxes((Entity)player9, entityBoundingBox7.offset(n14, motionY5, n14)).size() > 0 || Speed.getMinecraft113().player.collidedVertically) && Speed.i(this.k) > 0) {
                            if (1.273197475E-314 * ZG.e() - 5.941588215E-315 > Speed.M(this.k)) {
                                Speed.e(this.k, 0);
                                listener7 = this;
                                break Label_3448;
                            }
                            Speed.e(this.k, (Speed.getMinecraft33().player.moveForward != 0.0f || Speed.getMinecraft124().player.moveStrafing != 0.0f) ? 1 : 0);
                        }
                        listener7 = this;
                    }
                    Speed.M(listener7.k, Speed.e(this.k) - Speed.e(this.k) / 0.0);
                    listener6 = this;
                }
                Speed.M(listener6.k, Math.max(Speed.M(this.k), ZG.e()));
                if (Speed.i(this.k) > 0) {
                    ZG.M(fg, Speed.M(this.k));
                }
                if (Speed.getMinecraft143().player.moveForward != 0.0f || Speed.getMinecraft153().player.moveStrafing != 0.0f) {
                    Speed.M(this.k);
                    break;
                }
                break;
            }
        }
    }
}
