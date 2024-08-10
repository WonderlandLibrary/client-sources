package cc.slack.features.modules.impl.ghost;

import cc.slack.events.State;
import cc.slack.events.impl.network.DisconnectEvent;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.Vec3;

@ModuleInfo(
        name = "PearlAntiVoid",
        category = Category.GHOST
)
public class PearlAntiVoid extends Module {

    private int overVoidTicks;
    private Vec3 position;
    private Vec3 motion;
    private boolean wasVoid;
    private boolean setBack;
    boolean shouldStuck;
    double x;
    double y;
    double z;
    boolean wait;
    private final NumberValue<Integer> fall = new NumberValue<>("Min fall distance", 5, 0, 10, 1);

    public PearlAntiVoid() {
        addSettings(fall);
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        mc.thePlayer.isDead = false;
    }

    @Listen
    public void onDisconnect (DisconnectEvent event) {
        mc.timer.timerSpeed = 1.0f;
        mc.thePlayer.isDead = false;
        toggle();
    }

    @Listen
    public void onPacket(final PacketEvent e) {
        Packet<?> p = e.getPacket();
            if (e.getDirection() == PacketDirection.OUTGOING) {
                if (!mc.thePlayer.onGround && shouldStuck && p instanceof C03PacketPlayer
                        && !(p instanceof C03PacketPlayer.C05PacketPlayerLook)
                        && !(p instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                    e.cancel();
                }
                if (p instanceof C08PacketPlayerBlockPlacement && wait) {
                    shouldStuck = false;
                    mc.timer.timerSpeed = 0.2f;
                    wait = false;
                }
            }
            if (e.getDirection() == PacketDirection.INCOMING) {
                if (p instanceof S08PacketPlayerPosLook) {
                    final S08PacketPlayerPosLook wrapper = (S08PacketPlayerPosLook) p;
                    x = wrapper.getX();
                    y = wrapper.getY();
                    z = wrapper.getZ();
                    mc.timer.timerSpeed = 0.2f;
                }
            }

            if (e.getPacket() instanceof S40PacketDisconnect) {
                toggle();
                mc.timer.timerSpeed = 1.0f;
                mc.thePlayer.isDead = false;
            }
    }

    @Listen
    public void onMotion(final MotionEvent e) {
        try {
            if (e.getState() != State.POST) {
                    if (mc.thePlayer.getHeldItem() == null) {
                        mc.timer.timerSpeed = 1.0f;
                    }

                    if (mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) {
                        wait = true;
                    }

                    if (shouldStuck && !mc.thePlayer.onGround) {
                        mc.thePlayer.motionX = 0.0;
                        mc.thePlayer.motionY = 0.0;
                        mc.thePlayer.motionZ = 0.0;
                        mc.thePlayer.setPositionAndRotation(x, y, z, mc.thePlayer.rotationYaw,
                                mc.thePlayer.rotationPitch);
                    }
                    final boolean overVoid = !mc.thePlayer.onGround && !PlayerUtil.isBlockUnderP(30);
                    if (!overVoid) {
                        shouldStuck = false;
                        x = mc.thePlayer.posX;
                        y = mc.thePlayer.posY;
                        z = mc.thePlayer.posZ;
                        mc.timer.timerSpeed = 1.0f;
                    }
                    if (overVoid) {
                        ++overVoidTicks;
                    } else if (mc.thePlayer.onGround) {
                        overVoidTicks = 0;
                    }
                    if (overVoid && position != null && motion != null
                            && overVoidTicks < 30.0 + fall.getValue() * 20.0) {
                        if (!setBack) {
                            wasVoid = true;
                            if (mc.thePlayer.fallDistance > fall.getValue()) {
                                mc.thePlayer.fallDistance = 0.0f;
                                setBack = true;
                                shouldStuck = true;
                                x = mc.thePlayer.posX;
                                y = mc.thePlayer.posY;
                                z = mc.thePlayer.posZ;
                            }
                        }
                    } else {
                        if (shouldStuck) {
                            toggle();
                        }
                        shouldStuck = false;
                        mc.timer.timerSpeed = 1.0f;
                        setBack = false;
                        if (wasVoid) {
                            wasVoid = false;
                        }
                        motion = new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
                        position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

}
