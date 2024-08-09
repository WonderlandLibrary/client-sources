package wtf.shiyeno.modules.impl.combat;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CKeepAlivePacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.render.RenderUtil.Render3D;
import wtf.shiyeno.util.render.animation.AnimationMath;

@FunctionAnnotation(
        name = "BackTrack",
        type = Type.Combat
)
public class BackTrack extends Function {
    private final CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList();
    private PlayerEntity target;
    private final SliderSetting range = new SliderSetting("Дистанция", 5.2F, 3.0F, 6.5F, 0.1F);
    private final SliderSetting timeout = new SliderSetting("Таймаут", 5000.0F, 100.0F, 10000.0F, 100.0F);
    private final BooleanOption draw = new BooleanOption("Рисовать позицию игрока", true);
    public TimerUtil timerUtil = new TimerUtil();

    public BackTrack() {
        this.addSettings(new Setting[]{this.range, this.timeout, this.draw});
    }

    private void findTarget(CUseEntityPacket packet) {
        if (mc.world != null && mc.player != null) {
            Entity var3 = packet.getEntityFromWorld(mc.world);
            PlayerEntity player;
            if (var3 instanceof PlayerEntity) {
                player = (PlayerEntity)var3;
                if (player != this.target) {
                    Iterator var5 = this.packets.iterator();

                    while(var5.hasNext()) {
                        IPacket packetss = (IPacket)var5.next();
                        mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packetss);
                    }

                    this.packets.clear();
                    this.target = player;
                    this.createPositions();
                }
            }

            var3 = packet.getEntityFromWorld(mc.world);
            if (var3 instanceof PlayerEntity) {
                player = (PlayerEntity)var3;
                if (player == this.target) {
                    this.timerUtil.reset();
                }
            }
        }

    }

    private void createPositions() {
        this.target.realX = this.target.getPosX();
        this.target.realY = this.target.getPosY();
        this.target.realZ = this.target.getPosZ();
    }

    private void onPacket(EventPacket e) {
        if (e.isSendPacket()) {
            this.onSendPacket(e);
        }

        if (e.isReceivePacket()) {
            this.onReceivePacket(e);
        }

    }

    private void onSendPacket(EventPacket e) {
        if (this.target != null) {
            if (mc.player != null && mc.world != null && !mc.isSingleplayer() && !mc.player.getShouldBeDead()) {
                if (e.getPacket() instanceof CKeepAlivePacket || e.getPacket() instanceof CConfirmTransactionPacket) {
                    this.packets.add(e.getPacket());
                    e.setCancel(true);
                }
            } else {
                this.toggle();
            }
        }

        IPacket var3 = e.getPacket();
        if (var3 instanceof CUseEntityPacket packet) {
            this.findTarget(packet);
        }

    }

    private void onReceivePacket(EventPacket e) {
        if (this.target != null) {
            IPacket var3 = e.getPacket();
            if (var3 instanceof SEntityVelocityPacket) {
                SEntityVelocityPacket packet = (SEntityVelocityPacket)var3;
                this.handleVelocityPacket(e);
            }

            if (this.target != null) {
                var3 = e.getPacket();
                if (var3 instanceof SPlayerPositionLookPacket) {
                    SPlayerPositionLookPacket packet = (SPlayerPositionLookPacket)var3;
                    this.handleFlagPacket(e);
                    return;
                }

                var3 = e.getPacket();
                if (var3 instanceof SEntityPacket) {
                    SEntityPacket packet = (SEntityPacket)var3;
                    this.onUpdatePosition(e);
                }

                var3 = e.getPacket();
                if (var3 instanceof SEntityTeleportPacket) {
                    SEntityTeleportPacket packet = (SEntityTeleportPacket)var3;
                    this.onTeleportPosition(e);
                }
            }
        }

    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            this.onPacket(e);
        }

        if (event instanceof EventRender e) {
            this.handleRenderEvent(e);
        }

        if (event instanceof EventMotion e) {
            this.handleMotionEvent(e);
        }

    }

    private void handleMotionEvent(EventMotion e) {
        Iterator var2;
        IPacket packet;
        if (this.target != null && !this.target.isAlive()) {
            this.target.func_242277_a(new Vector3d(this.target.realX, this.target.realY, this.target.realZ));
            this.target.setRawPosition(this.target.realX, this.target.realY, this.target.realZ);
            this.target.realXI = 0.0;
            this.target.realYI = 0.0;
            this.target.realZI = 0.0;
            this.target = null;
            var2 = this.packets.iterator();

            while(var2.hasNext()) {
                packet = (IPacket)var2.next();
                mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packet);
            }

            this.packets.clear();
            this.timerUtil.reset();
        } else if (this.timerUtil.hasTimeElapsed((long)this.timeout.getValue().intValue()) && this.target != null) {
            this.target.func_242277_a(new Vector3d(this.target.realX, this.target.realY, this.target.realZ));
            this.target.setRawPosition(this.target.realX, this.target.realY, this.target.realZ);
            this.target.realXI = 0.0;
            this.target.realYI = 0.0;
            this.target.realZI = 0.0;
            this.target = null;
            var2 = this.packets.iterator();

            while(var2.hasNext()) {
                packet = (IPacket)var2.next();
                mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packet);
            }

            this.packets.clear();
            this.timerUtil.reset();
        } else {
            if (this.target != null) {
                if (!this.target.isAlive()) {
                    this.target = null;
                    return;
                }

                if (mc.player.getPositionVec().distanceTo(new Vector3d(this.target.realX, this.target.realY, this.target.realZ)) >= (double)this.range.getValue().floatValue()) {
                    this.target.func_242277_a(new Vector3d(this.target.realX, this.target.realY, this.target.realZ));
                    this.target.setRawPosition(this.target.realX, this.target.realY, this.target.realZ);
                    this.target.realXI = 0.0;
                    this.target.realYI = 0.0;
                    this.target.realZI = 0.0;
                    this.target = null;
                    var2 = this.packets.iterator();

                    while(var2.hasNext()) {
                        packet = (IPacket)var2.next();
                        mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packet);
                    }

                    this.packets.clear();
                }
            }

        }
    }

    private void handleRenderEvent(EventRender e) {
        if (e.isRender3D() && this.target != null && this.draw.get()) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            this.target.realXI = AnimationMath.lerp(this.target.realXI, this.target.realX - this.target.getPosX(), 10.0);
            this.target.realYI = AnimationMath.lerp(this.target.realYI, this.target.realY - this.target.getPosY(), 10.0);
            this.target.realZI = AnimationMath.lerp(this.target.realZI, this.target.realZ - this.target.getPosZ(), 10.0);
            Render3D.drawBox(this.target.getBoundingBox().offset(this.target.realXI, this.target.realYI, this.target.realZI).offset(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z), -1);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void handleVelocityPacket(EventPacket e) {
        SEntityVelocityPacket packet = (SEntityVelocityPacket)e.getPacket();
        if (packet.getEntityID() == mc.player.getEntityId()) {
            this.target.func_242277_a(new Vector3d(this.target.realX, this.target.realY, this.target.realZ));
            this.target.setRawPosition(this.target.realX, this.target.realY, this.target.realZ);
            this.target.realXI = 0.0;
            this.target.realYI = 0.0;
            this.target.realZI = 0.0;
            this.target = null;
            Iterator var3 = this.packets.iterator();

            while(var3.hasNext()) {
                IPacket packetss = (IPacket)var3.next();
                mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packetss);
            }

            this.packets.clear();
            this.timerUtil.reset();
        }
    }

    private void handleFlagPacket(EventPacket e) {
        this.target.func_242277_a(new Vector3d(this.target.realX, this.target.realY, this.target.realZ));
        this.target.setRawPosition(this.target.realX, this.target.realY, this.target.realZ);
        this.target.realXI = 0.0;
        this.target.realYI = 0.0;
        this.target.realZI = 0.0;
        this.target = null;
        Iterator var2 = this.packets.iterator();

        while(var2.hasNext()) {
            IPacket packetss = (IPacket)var2.next();
            mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packetss);
        }

        this.packets.clear();
        this.timerUtil.reset();
    }

    private void onTeleportPosition(EventPacket e) {
        SEntityTeleportPacket packet = (SEntityTeleportPacket)e.getPacket();
        if (packet.getEntityId() == this.target.getEntityId()) {
            this.target.realX = packet.getX();
            this.target.realY = packet.getY();
            this.target.realZ = packet.getZ();
            e.setCancel(true);
        }
    }

    private void onUpdatePosition(EventPacket e) {
        SEntityPacket packet = (SEntityPacket)e.getPacket();
        if (packet.entityId == this.target.getEntityId()) {
            PlayerEntity var10000 = this.target;
            var10000.realX += (double)packet.posX / 4096.0;
            var10000 = this.target;
            var10000.realY += (double)packet.posY / 4096.0;
            var10000 = this.target;
            var10000.realZ += (double)packet.posZ / 4096.0;
            e.setCancel(true);
        }

    }
}