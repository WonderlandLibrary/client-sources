package net.futureclient.client.modules.miscellaneous.antiafk;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.dd;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Freecam;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.IE;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.Display;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AntiAFK;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AntiAFK k;
    
    public Listener1(final AntiAFK k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (!Display.isActive()) {
            if (AntiAFK.M(this.k).M(AntiAFK.M(this.k).B().intValue() * 1000)) {
                if (AntiAFK.c(this.k).M()) {
                    AntiAFK.getMinecraft14().player.swingArm(EnumHand.MAIN_HAND);
                }
                if (AntiAFK.g(this.k).M()) {
                    final float rotationPitch = AntiAFK.M(this.k).nextBoolean() ? ((float)AntiAFK.M(this.k).nextInt(90)) : ((float)(-AntiAFK.M(this.k).nextInt(90)));
                    final EntityPlayerSP player = AntiAFK.getMinecraft3().player;
                    player.rotationYaw += (float)(rotationPitch + (AntiAFK.M(this.k).nextBoolean() ? (Math.random() * 0.0) : (-(Math.random() * 0.0))));
                    AntiAFK.getMinecraft55().player.rotationPitch = rotationPitch;
                }
                if (AntiAFK.B(this.k).M()) {
                    ((IMinecraft)AntiAFK.getMinecraft6()).clickMouse(IE.RD.D);
                }
                if (AntiAFK.b(this.k).M()) {
                    AntiAFK.getMinecraft29().player.connection.sendPacket((Packet)new CPacketPlayer.Position(AntiAFK.getMinecraft36().player.posX, AntiAFK.getMinecraft53().player.posY - Math.random(), AntiAFK.getMinecraft38().player.posZ, AntiAFK.getMinecraft46().player.onGround));
                }
                if (AntiAFK.C(this.k).M()) {
                    AntiAFK.getMinecraft1().player.sendChatMessage(new StringBuilder().insert(0, "/").append(Long.toHexString(Double.doubleToLongBits(Math.random()))).toString());
                }
                if (AntiAFK.h(this.k).M()) {
                    AntiAFK.getMinecraft26().player.connection.sendPacket((Packet)new CPacketTabComplete(new StringBuilder().insert(0, "/").append(Long.toHexString(Double.doubleToLongBits(Math.random()))).toString(), (BlockPos)null, false));
                }
                final Freecam freecam = (Freecam)pg.M().M().M((Class)dd.class);
                if (AntiAFK.e(this.k).M() && freecam != null && !freecam.M()) {
                    AntiAFK.getMinecraft42().player.jump();
                }
                if (AntiAFK.i(this.k).M() && freecam != null && !freecam.M() && AntiAFK.getMinecraft31().player.getRidingEntity() == null && !AntiAFK.getMinecraft54().player.isElytraFlying()) {
                    KeyBinding.setKeyBindState(AntiAFK.getMinecraft19().gameSettings.keyBindSneak.getKeyCode(), true);
                }
            }
            else if (AntiAFK.e(this.k).M((AntiAFK.M(this.k).B().intValue() + 2) * 1000) && AntiAFK.getMinecraft33().player.getRidingEntity() == null && !AntiAFK.getMinecraft().player.isElytraFlying()) {
                KeyBinding.setKeyBindState(AntiAFK.getMinecraft13().gameSettings.keyBindSneak.getKeyCode(), false);
            }
            if (AntiAFK.K(this.k).M()) {
                if (AntiAFK.e(this.k) > 7) {
                    AntiAFK.M(this.k, -AntiAFK.M(this.k).nextInt(20));
                }
                Listener1 listener1 = null;
                switch (AntiAFK.e(this.k)) {
                    case 0:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft23().gameSettings.keyBindForward.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft43().gameSettings.keyBindBack.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft47().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft45().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                    case 1:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft8().gameSettings.keyBindForward.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft48().gameSettings.keyBindBack.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft34().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft18().gameSettings.keyBindRight.getKeyCode(), true);
                        listener1 = this;
                        break;
                    case 2:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft41().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft21().gameSettings.keyBindBack.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft37().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft52().gameSettings.keyBindRight.getKeyCode(), true);
                        listener1 = this;
                        break;
                    case 3:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft24().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft25().gameSettings.keyBindBack.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft28().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft35().gameSettings.keyBindRight.getKeyCode(), true);
                        listener1 = this;
                        break;
                    case 4:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft5().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft44().gameSettings.keyBindBack.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft20().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft40().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                    case 5:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft4().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft27().gameSettings.keyBindBack.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft11().gameSettings.keyBindLeft.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft51().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                    case 6:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft39().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft30().gameSettings.keyBindBack.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft22().gameSettings.keyBindLeft.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft10().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                    case 7:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft16().gameSettings.keyBindForward.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft32().gameSettings.keyBindBack.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft15().gameSettings.keyBindLeft.getKeyCode(), true);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft56().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                    default:
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft2().gameSettings.keyBindForward.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft49().gameSettings.keyBindBack.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft12().gameSettings.keyBindLeft.getKeyCode(), false);
                        KeyBinding.setKeyBindState(AntiAFK.getMinecraft9().gameSettings.keyBindRight.getKeyCode(), false);
                        listener1 = this;
                        break;
                }
                if (AntiAFK.M(listener1.k).e(AntiAFK.M(this.k).nextInt(1000)) && (AntiAFK.getMinecraft17().player.motionX == 0.0 || AntiAFK.getMinecraft50().player.motionZ == 0.0)) {
                    AntiAFK.M(this.k);
                    AntiAFK.M(this.k).e();
                }
            }
        }
    }
}
