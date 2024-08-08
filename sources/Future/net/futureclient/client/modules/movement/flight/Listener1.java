package net.futureclient.client.modules.movement.flight;

import net.futureclient.client.events.Event;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.futureclient.client.IG;
import net.futureclient.client.dA;
import net.futureclient.client.pg;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.ZC;
import net.futureclient.client.AA;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.modules.movement.Flight;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final Flight k;
    
    public Listener1(final Flight k) {
        this.k = k;
        super();
    }
    
    public void M(final EventMotion eventMotion) {
        Listener1 listener1 = null;
        Label_0729: {
            Label_0246: {
                switch (AA.k[((ZC.SB)Flight.M(this.k).M()).ordinal()]) {
                    case 1: {
                        Flight.getMinecraft32().player.motionX = 0.0;
                        Flight.getMinecraft9().player.motionY = 0.0;
                        Flight.getMinecraft30().player.motionZ = 0.0;
                        if (Flight.e(this.k).M()) {
                            final EntityPlayerSP player = Flight.getMinecraft17().player;
                            player.motionY -= Flight.B(this.k).B().doubleValue();
                        }
                        final NoSlow noSlow = (NoSlow)pg.M().M().M((Class)dA.class);
                        if (!Flight.getMinecraft8().inGameHasFocus && (noSlow == null || !noSlow.M() || !noSlow.inventoryMove.M())) {
                            break Label_0246;
                        }
                        if (Flight.getMinecraft27().gameSettings.keyBindJump.isKeyDown()) {
                            final EntityPlayerSP player2 = Flight.getMinecraft31().player;
                            player2.motionY += 1.3262473694E-314;
                        }
                        if (Flight.getMinecraft16().gameSettings.keyBindSneak.isKeyDown()) {
                            final EntityPlayerSP player3 = Flight.getMinecraft5().player;
                            player3.motionY -= 1.3262473694E-314;
                            listener1 = this;
                            break Label_0729;
                        }
                        break;
                    }
                    case 2:
                        switch (AA.D[eventMotion.M().ordinal()]) {
                            case 1:
                                if (Flight.getMinecraft4().player.onGround) {
                                    break;
                                }
                                if (!Flight.getMinecraft21().gameSettings.keyBindJump.isKeyDown()) {
                                    if ((!Flight.getMinecraft20().gameSettings.keyBindForward.isKeyDown() && !Flight.getMinecraft19().gameSettings.keyBindBack.isKeyDown() && !Flight.getMinecraft43().gameSettings.keyBindLeft.isKeyDown() && !Flight.getMinecraft41().gameSettings.keyBindRight.isKeyDown()) || Flight.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
                                        break;
                                    }
                                    Flight.e(this.k, Flight.e(this.k) + 1);
                                    if (Flight.e(this.k) >= 11) {
                                        Flight.getMinecraft12().player.jumpMovementFactor = 0.7f;
                                        Flight.getMinecraft18().player.jump();
                                        Flight.e(this.k, 0);
                                        break;
                                    }
                                    break;
                                }
                                else {
                                    if (Flight.getMinecraft25().gameSettings.keyBindSneak.isKeyDown()) {
                                        break;
                                    }
                                    Flight.e(this.k, Flight.e(this.k) + 1);
                                    if (Flight.e(this.k) >= 4) {
                                        Flight.getMinecraft14().player.jumpMovementFactor = 0.01f;
                                        Flight.getMinecraft1().player.jump();
                                        Flight.e(this.k, 0);
                                        break;
                                    }
                                    break;
                                }
                                break;
                        }
                        break;
                    case 3:
                        switch (AA.D[eventMotion.M().ordinal()]) {
                            case 1: {
                                Flight.getMinecraft42().player.motionY = 0.0;
                                EventMotion eventMotion2 = null;
                                Label_0686: {
                                    if (!IG.e(0.0).getMaterial().blocksMovement()) {
                                        final double n = 1.8748963413E-314;
                                        final double n2 = n + n * (1.0 + Flight.M(this.k).nextInt(1 + (Flight.M(this.k).nextBoolean() ? Flight.M(this.k).nextInt(34) : Flight.M(this.k).nextInt(43))));
                                        if (Flight.getMinecraft6().player.ticksExisted % 2 == 0) {
                                            eventMotion.M(Flight.getMinecraft11().player.posY + n2);
                                            eventMotion2 = eventMotion;
                                            break Label_0686;
                                        }
                                        eventMotion.M(Flight.getMinecraft34().player.posY);
                                    }
                                    eventMotion2 = eventMotion;
                                }
                                if (eventMotion2.B() != eventMotion.C()) {
                                    Flight.getMinecraft35().player.setPosition(Flight.getMinecraft7().player.posX, eventMotion.B(), Flight.getMinecraft46().player.posZ);
                                    break Label_0246;
                                }
                                break Label_0246;
                            }
                        }
                        break;
                }
            }
            listener1 = this;
        }
        if ((boolean)Flight.b(listener1.k).M() && Flight.M(this.k, Flight.M(this.k) + 1) >= 12 && !Flight.getMinecraft2().player.isPotionActive(MobEffects.LEVITATION) && !Flight.getMinecraft38().player.isElytraFlying()) {
            final WorldClient world = Flight.getMinecraft10().world;
            final EntityPlayerSP player4 = Flight.getMinecraft45().player;
            final AxisAlignedBB grow = Flight.getMinecraft23().player.getEntityBoundingBox().grow(0.0);
            final double n3 = 1.273197475E-314;
            final double n4 = 0.0;
            if (world.getCollisionBoxes((Entity)player4, grow.expand(n4, n3, n4)).isEmpty()) {
                final boolean b = true;
                eventMotion.M(eventMotion.B() - 1.155044749E-314);
                eventMotion.e(b);
                if (Flight.M(this.k) >= 22) {
                    Flight.M(this.k, 0);
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
}
