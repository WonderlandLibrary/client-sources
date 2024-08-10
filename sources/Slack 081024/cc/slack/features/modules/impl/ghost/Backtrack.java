// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.network.PingSpoofUtil;
import cc.slack.utils.network.TimedPacket;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;

@ModuleInfo(
        name = "Backtrack",
        category = Category.GHOST
)
public class Backtrack extends Module {

    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", 4, 1, 40, 1);
    private final NumberValue<Integer> backtrackTime = new NumberValue<>("Backtrack ticks", 20, 10, 60, 1);

    private int ticksSinceAttack = 0;
    private int backtrackTicks = 0;
    private boolean enabled = false;
    public EntityPlayer player;

    private int comboCounter = 0;
    private boolean sentHit = false;
    
    private Vec3 realPos = new Vec3(0, 0, 0);
    private Entity target;

    public Backtrack() {
        addSettings(maxDelay, backtrackTime);
    }

    @Override
    public void onEnable() {
        ticksSinceAttack = 0;
        backtrackTicks = 0;
        enabled = false;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) {
            enabled = false;
            PingSpoofUtil.disable();
        }

        if (enabled) {
            if (ticksSinceAttack < maxDelay.getValue() * 3) {
                ticksSinceAttack ++;
            }
        }
        if (backtrackTicks > 0) {
            backtrackTicks --;
        } else {
            if (enabled) {
                PingSpoofUtil.disable();
                enabled = false;
            }
        }
    }

    @Listen
    public void onPacket(PacketEvent event) {
        final Packet packet = event.getPacket();

        if (event.getDirection() == PacketDirection.OUTGOING) {
            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity wrapper = (C02PacketUseEntity) packet;
                if (wrapper.getEntityFromWorld(mc.theWorld) instanceof EntityPlayer && wrapper.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (mc.thePlayer.ticksSinceLastDamage > 20 && comboCounter > 1) {
                        if (backtrackTicks == 0) ticksSinceAttack = 0;
                        backtrackTicks = backtrackTime.getValue();
                        if (!enabled) {
                            realPos = wrapper.getEntityFromWorld(mc.theWorld).getPositionVector();
                            target =  wrapper.getEntityFromWorld(mc.theWorld);
                        } else {
                            if ( wrapper.getEntityFromWorld(mc.theWorld).getUniqueID() != target.getUniqueID()) {
                                realPos = wrapper.getEntityFromWorld(mc.theWorld).getPositionVector();
                                target =  wrapper.getEntityFromWorld(mc.theWorld);
                                ticksSinceAttack = 0;
                            }
                        }
                        enabled = true;
                        PingSpoofUtil.enableInbound(true, ticksSinceAttack * 17);
                        PingSpoofUtil.enableOutbound(true, ticksSinceAttack * 10);

                        player = (EntityPlayer) wrapper.getEntityFromWorld(mc.theWorld);
                    }
                    if (((C02PacketUseEntity) packet).getEntityFromWorld(mc.theWorld).hurtResistantTime == 0 && !sentHit) {
                        sentHit = true;
                        comboCounter += 1;
                    } else if (((C02PacketUseEntity) packet).getEntityFromWorld(mc.theWorld).hurtResistantTime > 2) {
                        sentHit = false;
                    }
                }
            }
        } else {
            if (packet instanceof S12PacketEntityVelocity) {
                if (((S12PacketEntityVelocity) packet).getEntityID() == mc.thePlayer.getEntityId())
                    ticksSinceAttack /= 3;
            } else if (packet instanceof S14PacketEntity && enabled) {
                if (((S14PacketEntity) packet).getEntity(mc.theWorld).getUniqueID() == target.getUniqueID()) {
                    realPos.xCoord += ((S14PacketEntity) packet).getPosX() / 32D;
                    realPos.yCoord += ((S14PacketEntity) packet).getPosY() / 32D;
                    realPos.zCoord += ((S14PacketEntity) packet).getPosZ() / 32D;
                }
            }
        }
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (event.state != RenderEvent.State.RENDER_3D) return;

        if (enabled) {
            RenderUtil.drawFilledAABB(mc.thePlayer.getEntityBoundingBox().offset(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ).offset(realPos.xCoord, realPos.yCoord, realPos.zCoord), new Color(255, 255, 255, 80).getRGB());
        }
    }


    @Override
    public void onDisable() {
        PingSpoofUtil.disable(true, true);
    }
}
