package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KillAuraP
        extends Module {
    public boolean criticals;
    private int ticks;
    private final List<EntityLivingBase> loaded = new ArrayList<EntityLivingBase>();
    private EntityLivingBase target;
    private int tpdelay;
    private final TimerUtil timer = new TimerUtil();

    public KillAuraP() {
        super("MultiAura", new String[]{"ma", "kam"}, ModuleType.Blatant);
    }

    @EventHandler
    public void onUpdate(EventTick event) {
        this.setColor(new Color(255, 50, 70).getRGB());
        ++this.ticks;
        ++this.tpdelay;
        if (this.ticks >= 20 - this.speed()) {
            this.ticks = 0;
            for (Object object : mc.theWorld.loadedEntityList) {
                EntityLivingBase entity;
                if (!(object instanceof EntityLivingBase) || (entity = (EntityLivingBase) object) instanceof EntityPlayerSP || mc.thePlayer.getDistanceToEntity(entity) > 10.0f || !entity.isEntityAlive())
                    continue;
                if (this.tpdelay >= 4) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                }
                if (mc.thePlayer.getDistanceToEntity(entity) >= 10.0f) continue;
                this.attack(entity);
            }
        }
    }

    public void attack(EntityLivingBase entity) {
        this.attack(entity, false);
    }

    public void attack(EntityLivingBase entity, boolean crit) {
        mc.thePlayer.swingItem();
        //float sharpLevel = EnchantmentHelper.getModifierForCreature(this.mc.thePlayer.getHeldItem(), entity.getCreatureAttribute());
        boolean vanillaCrit = mc.thePlayer.fallDistance > 0.0f && !mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isPotionActive(Potion.blindness) && mc.thePlayer.ridingEntity == null;
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            mc.thePlayer.onCriticalHit(entity);
        }
    }

    private int speed() {
        return 8;
    }
}