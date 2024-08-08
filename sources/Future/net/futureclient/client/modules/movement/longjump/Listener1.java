package net.futureclient.client.modules.movement.longjump;

import net.futureclient.client.events.Event;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.futureclient.client.ZG;
import net.futureclient.client.Uh;
import net.futureclient.client.hb;
import net.futureclient.client.EB;
import net.futureclient.client.modules.movement.LongJump;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener1 extends n<fg>
{
    public final LongJump k;
    
    public Listener1(final LongJump k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final fg fg) {
        switch (EB.D[((hb.wc)LongJump.M(this.k).M()).ordinal()]) {
            case 1: {
                if (LongJump.getMinecraft1().player.moveStrafing <= 0.0f && LongJump.getMinecraft35().player.moveForward <= 0.0f) {
                    LongJump.e(this.k, 1);
                }
                if (Uh.M(LongJump.getMinecraft105().player.posY - (int)LongJump.getMinecraft136().player.posY, 3) == Uh.M(7.129905855E-315, 3)) {
                    final EntityPlayerSP player = LongJump.getMinecraft13().player;
                    player.motionY -= 1.9522361275E-314;
                    fg.D -= 1.9522361275E-314;
                }
                Listener1 listener1;
                if (LongJump.M(this.k) == 1 && (LongJump.getMinecraft63().player.moveForward != 0.0f || LongJump.getMinecraft76().player.moveStrafing != 0.0f)) {
                    listener1 = this;
                    LongJump.e(this.k, 2);
                    LongJump.b(this.k, LongJump.M(this.k).B().doubleValue() * ZG.e() - 5.941588215E-315);
                }
                else if (LongJump.M(this.k) == 2) {
                    LongJump.e(this.k, 3);
                    LongJump.getMinecraft84().player.motionY = 1.9013082286E-314;
                    fg.D = 1.9013082286E-314;
                    final LongJump k = this.k;
                    listener1 = this;
                    LongJump.b(k, LongJump.M(this.k) * 8.296494266E-315);
                }
                else if (LongJump.M(this.k) == 3) {
                    LongJump.e(this.k, 4);
                    listener1 = this;
                    LongJump.M(this.k, 6.790386532E-315 * (LongJump.b(this.k) - ZG.e()));
                    LongJump.b(this.k, LongJump.b(this.k) - LongJump.e(this.k));
                }
                else {
                    final WorldClient world = LongJump.getMinecraft42().world;
                    final EntityPlayerSP player2 = LongJump.getMinecraft114().player;
                    final AxisAlignedBB entityBoundingBox = LongJump.getMinecraft86().player.getEntityBoundingBox();
                    final double motionY = LongJump.getMinecraft64().player.motionY;
                    final double n = 0.0;
                    if (world.getCollisionBoxes((Entity)player2, entityBoundingBox.offset(n, motionY, n)).size() > 0 || LongJump.getMinecraft109().player.collidedVertically) {
                        LongJump.e(this.k, 1);
                    }
                    listener1 = this;
                    LongJump.b(this.k, LongJump.b(this.k) - LongJump.b(this.k) / 0.0);
                }
                LongJump.b(listener1.k, Math.max(LongJump.M(this.k), ZG.e()));
                final MovementInput movementInput = LongJump.getMinecraft110().player.movementInput;
                float moveForward = movementInput.moveForward;
                float moveStrafe = movementInput.moveStrafe;
                float rotationYaw = LongJump.getMinecraft12().player.rotationYaw;
                float n2 = 0.0f;
                Label_0634: {
                    if (moveForward == 0.0f && moveStrafe == 0.0f) {
                        n2 = rotationYaw;
                        final double m = 0.0;
                        fg.k = 0.0;
                        fg.M = m;
                    }
                    else {
                        if (moveForward != 0.0f) {
                            float n3;
                            if (moveStrafe >= 1.0f) {
                                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                                moveStrafe = 0.0f;
                                n3 = moveForward;
                            }
                            else {
                                if (moveStrafe <= -1.0f) {
                                    rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                                    moveStrafe = 0.0f;
                                }
                                n3 = moveForward;
                            }
                            if (n3 > 0.0f) {
                                moveForward = 1.0f;
                                n2 = rotationYaw;
                                break Label_0634;
                            }
                            if (moveForward < 0.0f) {
                                moveForward = -1.0f;
                            }
                        }
                        n2 = rotationYaw;
                    }
                }
                final double cos = Math.cos(Math.toRadians(n2 + 90.0f));
                final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0f));
                final float n4 = moveForward;
                fg.k = moveForward * LongJump.M(this.k) * cos + moveStrafe * LongJump.M(this.k) * sin;
                fg.M = n4 * LongJump.M(this.k) * sin - moveStrafe * LongJump.M(this.k) * cos;
            }
            case 2: {
                if (LongJump.e(this.k)) {
                    if (LongJump.getMinecraft98().player.onGround) {
                        LongJump.M(this.k).e();
                    }
                    if (Uh.M(LongJump.getMinecraft148().player.posY - (int)LongJump.getMinecraft36().player.posY, 3) == Uh.M(1.358077306E-314, 3)) {
                        LongJump.getMinecraft30().player.motionY = 0.0;
                    }
                    if (LongJump.getMinecraft77().player.moveStrafing <= 0.0f && LongJump.getMinecraft39().player.moveForward <= 0.0f) {
                        LongJump.B(this.k, 1);
                    }
                    if (Uh.M(LongJump.getMinecraft26().player.posY - (int)LongJump.getMinecraft128().player.posY, 3) == Uh.M(7.129905855E-315, 3)) {
                        LongJump.getMinecraft23().player.motionY = 0.0;
                    }
                    Listener1 listener2 = null;
                    Label_1204: {
                        Listener1 listener3;
                        if (LongJump.B(this.k) == 1) {
                            if (LongJump.getMinecraft113().player.moveForward != 0.0f || LongJump.getMinecraft106().player.moveStrafing != 0.0f) {
                                listener2 = this;
                                LongJump.B(this.k, 2);
                                LongJump.b(this.k, 3.395193264E-315 * ZG.e() - 5.941588215E-315);
                                break Label_1204;
                            }
                            listener3 = this;
                        }
                        else {
                            listener3 = this;
                        }
                        if (LongJump.B(listener3.k) == 2) {
                            LongJump.B(this.k, 3);
                            if (!LongJump.M(this.k).M()) {
                                LongJump.getMinecraft130().player.motionY = 1.9013082286E-314;
                            }
                            fg.D = 1.9013082286E-314;
                            final LongJump i = this.k;
                            listener2 = this;
                            LongJump.b(i, LongJump.M(this.k) * 8.296494266E-315);
                        }
                        else if (LongJump.B(this.k) == 3) {
                            LongJump.B(this.k, 4);
                            final double n5 = 6.790386532E-315 * (LongJump.b(this.k) - ZG.e());
                            final LongJump j = this.k;
                            listener2 = this;
                            LongJump.b(j, LongJump.b(this.k) - n5);
                        }
                        else {
                            final WorldClient world2 = LongJump.getMinecraft129().world;
                            final EntityPlayerSP player3 = LongJump.getMinecraft6().player;
                            final AxisAlignedBB entityBoundingBox2 = LongJump.getMinecraft149().player.getEntityBoundingBox();
                            final double motionY2 = LongJump.getMinecraft151().player.motionY;
                            final double n6 = 0.0;
                            Listener1 listener4;
                            if (world2.getCollisionBoxes((Entity)player3, entityBoundingBox2.offset(n6, motionY2, n6)).size() <= 0 && !LongJump.getMinecraft().player.collidedVertically) {
                                listener4 = this;
                            }
                            else {
                                listener4 = this;
                                LongJump.B(this.k, 1);
                            }
                            LongJump.b(listener4.k, LongJump.b(this.k) - LongJump.b(this.k) / 0.0);
                            listener2 = this;
                        }
                    }
                    LongJump.b(listener2.k, Math.max(LongJump.M(this.k), ZG.e()));
                    float moveForward2 = LongJump.getMinecraft141().player.movementInput.moveForward;
                    float moveStrafe2 = LongJump.getMinecraft157().player.movementInput.moveStrafe;
                    float rotationYaw2 = LongJump.getMinecraft38().player.rotationYaw;
                    float n7 = 0.0f;
                    Label_1385: {
                        if (moveForward2 == 0.0f && moveStrafe2 == 0.0f) {
                            n7 = rotationYaw2;
                            final double l = 0.0;
                            fg.k = 0.0;
                            fg.M = l;
                        }
                        else {
                            if (moveForward2 != 0.0f) {
                                float n8;
                                if (moveStrafe2 >= 1.0f) {
                                    rotationYaw2 += ((moveForward2 > 0.0f) ? -45 : 45);
                                    moveStrafe2 = 0.0f;
                                    n8 = moveForward2;
                                }
                                else {
                                    if (moveStrafe2 <= -1.0f) {
                                        rotationYaw2 += ((moveForward2 > 0.0f) ? 45 : -45);
                                        moveStrafe2 = 0.0f;
                                    }
                                    n8 = moveForward2;
                                }
                                if (n8 > 0.0f) {
                                    moveForward2 = 1.0f;
                                    n7 = rotationYaw2;
                                    break Label_1385;
                                }
                                if (moveForward2 < 0.0f) {
                                    moveForward2 = -1.0f;
                                }
                            }
                            n7 = rotationYaw2;
                        }
                    }
                    final double cos2 = Math.cos(Math.toRadians(n7 + 90.0f));
                    final double sin2 = Math.sin(Math.toRadians(rotationYaw2 + 90.0f));
                    final float n9 = moveForward2;
                    fg.k = moveForward2 * LongJump.M(this.k) * cos2 + moveStrafe2 * LongJump.M(this.k) * sin2;
                    fg.M = n9 * LongJump.M(this.k) * sin2 - moveStrafe2 * LongJump.M(this.k) * cos2;
                    if (n9 == 0.0f && moveStrafe2 == 0.0f) {
                        final double m2 = 0.0;
                        fg.k = 0.0;
                        fg.M = m2;
                    }
                    else if (moveForward2 != 0.0f) {
                        float n11;
                        if (moveStrafe2 >= 1.0f) {
                            final float n10 = rotationYaw2 + ((moveForward2 > 0.0f) ? -45 : 45);
                            n11 = moveForward2;
                        }
                        else {
                            if (moveStrafe2 <= -1.0f) {
                                final float n12 = rotationYaw2 + ((moveForward2 > 0.0f) ? 45 : -45);
                            }
                            n11 = moveForward2;
                        }
                        if (n11 <= 0.0f) {
                            if (moveForward2 < 0.0f) {}
                        }
                    }
                }
                Listener1 listener5;
                if (LongJump.getMinecraft91().player.onGround) {
                    listener5 = this;
                    LongJump.b(this.k);
                }
                else {
                    if (!LongJump.getMinecraft52().player.onGround && LongJump.i(this.k) != 0) {
                        LongJump.C(this.k);
                    }
                    listener5 = this;
                }
                if (LongJump.M(listener5.k).M()) {
                    if (LongJump.M(this.k).e(35L)) {
                        LongJump.e(this.k, true);
                    }
                    LongJump.M(this.k).e(2200L);
                    if (LongJump.M(this.k).e(2490L)) {
                        LongJump.e(this.k, false);
                        LongJump.M(this.k, false);
                        final EntityPlayerSP player4 = LongJump.getMinecraft152().player;
                        player4.motionX *= 0.0;
                        final EntityPlayerSP player5 = LongJump.getMinecraft10().player;
                        player5.motionZ *= 0.0;
                    }
                    if (LongJump.M(this.k).e(2820L)) {
                        LongJump.M(this.k, true);
                        final EntityPlayerSP player6 = LongJump.getMinecraft161().player;
                        player6.motionX *= 0.0;
                        final EntityPlayerSP player7 = LongJump.getMinecraft156().player;
                        player7.motionZ *= 0.0;
                        LongJump.M(this.k).e();
                        return;
                    }
                    break;
                }
                else {
                    if (LongJump.M(this.k).M()) {
                        break;
                    }
                    if (LongJump.M(this.k).e(480L)) {
                        final EntityPlayerSP player8 = LongJump.getMinecraft123().player;
                        player8.motionX *= 0.0;
                        final EntityPlayerSP player9 = LongJump.getMinecraft33().player;
                        player9.motionZ *= 0.0;
                        LongJump.M(this.k, false);
                    }
                    if (LongJump.M(this.k).e(550L)) {
                        final EntityPlayerSP player10 = LongJump.getMinecraft75().player;
                        player10.motionX *= 0.0;
                        final EntityPlayerSP player11 = LongJump.getMinecraft87().player;
                        player11.motionZ *= 0.0;
                        LongJump.M(this.k, false);
                    }
                    if (LongJump.M(this.k).e(650L)) {
                        final EntityPlayerSP player12 = LongJump.getMinecraft25().player;
                        player12.motionX *= 0.0;
                        final EntityPlayerSP player13 = LongJump.getMinecraft143().player;
                        player13.motionZ *= 0.0;
                        LongJump.M(this.k, false);
                    }
                    if (LongJump.M(this.k).e(750L)) {
                        final EntityPlayerSP player14 = LongJump.getMinecraft85().player;
                        player14.motionX *= 0.0;
                        final EntityPlayerSP player15 = LongJump.getMinecraft140().player;
                        player15.motionZ *= 0.0;
                        LongJump.M(this.k, false);
                    }
                    if (LongJump.M(this.k).e(780L)) {
                        final EntityPlayerSP player16 = LongJump.getMinecraft21().player;
                        player16.motionX *= 0.0;
                        final EntityPlayerSP player17 = LongJump.getMinecraft46().player;
                        player17.motionZ *= 0.0;
                        LongJump.M(this.k, true);
                        LongJump.M(this.k).e();
                        break;
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
}
