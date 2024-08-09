/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mpp.venusfr.events.AttackEvent;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.TickEvent;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.render.RenderUtils;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Backtrack", type=Category.Combat)
public class Backtrack
extends Function {
    private final BindSetting skip = new BindSetting("\u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c", 0);
    private final SliderSetting range = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 3.0f, 3.0f, 6.0f, 0.1f);
    private final SliderSetting delay = new SliderSetting("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430", 500.0f, 100.0f, 1000.0f, 50.0f);
    private final List<PacketData> queue = new LinkedList<PacketData>();
    private Entity target;
    private Vector3d realPos;
    private Vector3d interpolatedrealPos;

    public Backtrack() {
        this.addSettings(this.skip, this.range, this.delay);
    }

    @Subscribe
    private void onKey(EventKey eventKey) {
        if (eventKey.isKeyDown((Integer)this.skip.get())) {
            this.reset();
        }
    }

    @Subscribe
    private void onAttack(AttackEvent attackEvent) {
        if (attackEvent.entity == this.target || attackEvent.entity.isInvulnerable()) {
            return;
        }
        this.target = attackEvent.entity;
        this.interpolatedrealPos = this.realPos = this.target.getPositionVec();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        Object object;
        if (eventPacket.isSend() || !this.shouldLagging() || mc.isSingleplayer()) {
            return;
        }
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SPlaySoundEffectPacket || iPacket instanceof SEntityStatusPacket) {
            return;
        }
        if (iPacket instanceof SPlayerPositionLookPacket || iPacket instanceof SDisconnectPacket) {
            this.reset();
            return;
        }
        if (iPacket instanceof SEntityTeleportPacket && ((SEntityTeleportPacket)(object = (SEntityTeleportPacket)iPacket)).getEntityId() == this.target.getEntityId()) {
            this.realPos = new Vector3d(((SEntityTeleportPacket)object).getX(), ((SEntityTeleportPacket)object).getY(), ((SEntityTeleportPacket)object).getZ());
        }
        if (iPacket instanceof SEntityPacket) {
            object = (SEntityPacket)iPacket;
            if (((SEntityPacket)object).entityId == this.target.getEntityId()) {
                this.realPos = this.realPos.add(new Vector3d((double)((SEntityPacket)object).posX / 4096.0, (double)((SEntityPacket)object).posY / 4096.0, (double)((SEntityPacket)object).posZ / 4096.0));
            }
        }
        eventPacket.cancel();
        object = this.queue;
        synchronized (object) {
            if (eventPacket.isReceive()) {
                this.queue.add(new PacketData(iPacket, System.currentTimeMillis()));
            }
        }
    }

    @Subscribe
    private void onTick(TickEvent tickEvent) {
        if (this.queue.isEmpty() && this.isTargetNull() || mc.isSingleplayer()) {
            return;
        }
        if (this.shouldLagging()) {
            this.handle(true);
        } else {
            this.reset();
        }
    }

    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        if (this.realPos == null || mc.isSingleplayer()) {
            return;
        }
        double d = this.target.getWidth() / 2.0f;
        if (this.interpolatedrealPos == null || this.realPos.distanceTo(this.interpolatedrealPos) >= 2.0) {
            this.interpolatedrealPos = this.realPos;
        }
        this.interpolatedrealPos = MathUtil.fast(this.interpolatedrealPos, this.realPos, 15.0f);
        GL11.glPushMatrix();
        Vector3d vector3d = Backtrack.mc.getRenderManager().info.getProjectedView();
        GL11.glTranslated(-vector3d.x, -vector3d.y, -vector3d.z);
        int n = 0;
        Entity entity2 = this.target;
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity2;
            n = livingEntity.hurtTime;
        }
        RenderUtils.drawBox(new AxisAlignedBB(this.interpolatedrealPos.getX() - d, this.interpolatedrealPos.getY(), this.interpolatedrealPos.getZ() - d, this.interpolatedrealPos.getX() + d, this.interpolatedrealPos.getY() + (double)this.target.getHeight(), this.interpolatedrealPos.getZ() + d), ColorUtils.interpolate(-1, ColorUtils.getColor(0), 1.0f - (float)n / 9.0f));
        GL11.glPopMatrix();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handle(boolean bl) {
        List<PacketData> list = this.queue;
        synchronized (list) {
            Iterator<PacketData> iterator2 = this.queue.iterator();
            while (iterator2.hasNext()) {
                PacketData packetData = iterator2.next();
                double d = Backtrack.mc.player.getPositionVec().distanceTo(this.realPos) / (double)((Float)this.range.get()).floatValue();
                if (!bl && (double)packetData.timestamp() + (double)((Float)this.delay.get()).longValue() * d > (double)System.currentTimeMillis()) break;
                try {
                    NetworkManager.processPacket(packetData.packet(), Backtrack.mc.player.connection);
                } catch (ThreadQuickExitException threadQuickExitException) {
                    // empty catch block
                }
                iterator2.remove();
            }
        }
    }

    private boolean isTargetNull() {
        return this.target == null && this.realPos == null;
    }

    private boolean shouldLagging() {
        return this.target != null && this.target.isAlive() && !this.target.isInvulnerable() && Backtrack.mc.player.getPositionVec().distanceTo(this.realPos) <= (double)((Float)this.range.get()).floatValue();
    }

    private void reset() {
        this.handle(false);
        this.target = null;
        this.realPos = null;
        this.interpolatedrealPos = null;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.isSingleplayer()) {
            return;
        }
        this.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.isSingleplayer()) {
            return;
        }
        this.reset();
    }

    private record PacketData(IPacket<?> packet, long timestamp) {
    }
}

