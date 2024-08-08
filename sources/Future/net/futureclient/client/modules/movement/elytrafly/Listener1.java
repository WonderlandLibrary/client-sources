package net.futureclient.client.modules.movement.elytrafly;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.futureclient.client.ZG;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.Fb;
import net.futureclient.client.lb;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.ElytraFly;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener1 extends n<fg>
{
    public final ElytraFly k;
    
    public Listener1(final ElytraFly k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
    
    @Override
    public void M(final fg fg) {
        if (ElytraFly.getMinecraft2().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
            return;
        }
        final Freecam freecam;
        if ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) != null && freecam.M()) {
            return;
        }
        switch (lb.D[this.k.mode.M().ordinal()]) {
            case 1:
                if (ElytraFly.getMinecraft17().player.isElytraFlying() && this.k.stopInWater.M() && ElytraFly.getMinecraft38().player.isInWater()) {
                    ElytraFly.getMinecraft32().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly.getMinecraft36().player, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (ElytraFly.getMinecraft37().gameSettings.keyBindJump.isKeyDown() && ElytraFly.getMinecraft25().player.isElytraFlying()) {
                    fg.b(0.0);
                }
                if (ElytraFly.getMinecraft4().player.isElytraFlying() && !ElytraFly.getMinecraft18().inGameHasFocus) {
                    KeyBinding.setKeyBindState(ElytraFly.getMinecraft30().gameSettings.keyBindJump.getKeyCode(), true);
                }
                if (ElytraFly.getMinecraft12().inGameHasFocus && this.k.instantFly.M() && Keyboard.isKeyDown(57) && !ElytraFly.getMinecraft41().player.isElytraFlying() && ElytraFly.M(this.k).e(1000L)) {
                    ElytraFly.getMinecraft33().player.setJumping(false);
                    ElytraFly.getMinecraft().player.setSprinting(true);
                    ElytraFly.getMinecraft42().player.jump();
                    ElytraFly.getMinecraft1().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly.getMinecraft15().player, CPacketEntityAction.Action.START_FALL_FLYING));
                    ElytraFly.M(this.k).e();
                    return;
                }
                break;
            case 2:
                if (!ElytraFly.getMinecraft43().player.onGround || !this.k.K.M()) {
                    if (this.k.E.M()) {
                        if (ElytraFly.M(this.k)) {
                            ElytraFly.M(this.k, 1.0);
                            ElytraFly.M(this.k, false);
                        }
                        if (ElytraFly.M(this.k) < ElytraFly.M(this.k).B().doubleValue()) {
                            ElytraFly.M(this.k, ElytraFly.M(this.k) + 1.273197475E-314);
                        }
                        if (ElytraFly.M(this.k) - 1.273197475E-314 > ElytraFly.M(this.k).B().doubleValue()) {
                            ElytraFly.M(this.k, ElytraFly.M(this.k) - 1.273197475E-314);
                        }
                    }
                    fg fg2 = null;
                    Label_0716: {
                        if (!ZG.M() && !ElytraFly.getMinecraft44().player.collided && this.k.antiKick.M()) {
                            if (ElytraFly.M(this.k).e(1000L)) {
                                fg2 = fg;
                                ElytraFly.M(this.k, true);
                                ElytraFly.M(this.k, ElytraFly.M(this.k) + 1);
                                final EntityPlayerSP player = ElytraFly.getMinecraft27().player;
                                player.motionX += 1.9522361275E-314 * Math.sin(Math.toRadians(ElytraFly.M(this.k) * 4));
                                final EntityPlayerSP player2 = ElytraFly.getMinecraft21().player;
                                player2.motionZ += 1.9522361275E-314 * Math.cos(Math.toRadians(ElytraFly.M(this.k) * 4));
                                break Label_0716;
                            }
                        }
                        else {
                            ElytraFly.M(this.k).e();
                            ElytraFly.M(this.k, false);
                        }
                        fg2 = fg;
                    }
                    fg2.e(fg.M() * (ElytraFly.M(this.k) ? 0.0 : (this.k.E.M() ? ElytraFly.M(this.k) : ElytraFly.M(this.k).B().doubleValue())));
                    fg.M(fg.b() * (ElytraFly.M(this.k) ? 0.0 : (this.k.E.M() ? ElytraFly.M(this.k) : ElytraFly.M(this.k).B().doubleValue())));
                    return;
                }
                break;
            case 3:
                if (ElytraFly.getMinecraft40().player.isElytraFlying() && this.k.stopInWater.M() && ElytraFly.getMinecraft22().player.isInWater()) {
                    ElytraFly.getMinecraft24().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly.getMinecraft47().player, CPacketEntityAction.Action.START_FALL_FLYING));
                    return;
                }
                if (Keyboard.isKeyDown(57) && ElytraFly.getMinecraft31().player.isElytraFlying()) {
                    final float n = ElytraFly.getMinecraft48().player.rotationYaw * 0.017453292f;
                    final EntityPlayerSP player3 = ElytraFly.getMinecraft29().player;
                    player3.motionX -= MathHelper.sin(n) * 0.15f;
                    final EntityPlayerSP player4 = ElytraFly.getMinecraft11().player;
                    player4.motionZ += MathHelper.cos(n) * 0.15f;
                    return;
                }
                break;
            case 4:
                if (!ElytraFly.getMinecraft19().player.isElytraFlying()) {
                    break;
                }
                if (!ElytraFly.getMinecraft7().gameSettings.keyBindForward.isKeyDown() && !ElytraFly.getMinecraft14().gameSettings.keyBindSprint.isKeyDown() && !ElytraFly.getMinecraft28().gameSettings.keyBindSneak.isKeyDown()) {
                    ElytraFly.getMinecraft3().player.motionX = 0.0;
                    ElytraFly.getMinecraft34().player.motionY = 0.0;
                    ElytraFly.getMinecraft46().player.motionZ = 0.0;
                    break;
                }
                if (ElytraFly.getMinecraft26().gameSettings.keyBindForward.isKeyDown() && ElytraFly.getMinecraft39().player.prevRotationPitch > 0.0f) {
                    final float n2 = (float)Math.toRadians(ElytraFly.getMinecraft6().player.rotationYaw);
                    ElytraFly.getMinecraft45().player.motionX = MathHelper.sin(n2) * -ElytraFly.M(this.k).B().floatValue();
                    ElytraFly.getMinecraft16().player.motionZ = MathHelper.cos(n2) * ElytraFly.M(this.k).B().floatValue();
                    return;
                }
                break;
        }
    }
}
