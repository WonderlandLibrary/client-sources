package lol.point.returnclient.module.managers.impl;

import lol.point.Return;
import lol.point.returnclient.events.impl.render.EventRender3D;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.managers.Manager;
import lol.point.returnclient.util.render.CrazyGLUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class TargetManager extends Manager {

    public Object parent;

    public TargetManager(Object parent) {
        Return.BUS.subscribe(this);
        this.parent = parent;
    }

    public List<EntityPlayer> friends = new ArrayList<>();
    public EntityLivingBase target;

    private KillAura killAura;

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        if (killAura == null) {
            killAura = Return.INSTANCE.moduleManager.getByClass(KillAura.class);
        }

        if (!killAura.enabled) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase entity) {
                target = entity;
            } else {
                target = null;
            }
        }

        if (target != null) {
            if (target.isDead) {
                target = null;
            }

            if (friends.contains(target)) {
                target = null;
            }

            if (mc.thePlayer.isDead) {
                target = null;
            }
        }
    });

    @Subscribe
    private final Listener<EventRender3D> onRender3D = new Listener<>(eventRender3D -> {
        if (Return.INSTANCE.moduleManager.targetManager.target != null) {
            CrazyGLUtil.drawCircle(Return.INSTANCE.moduleManager.targetManager.target, 0.65);
        }
    });
}
