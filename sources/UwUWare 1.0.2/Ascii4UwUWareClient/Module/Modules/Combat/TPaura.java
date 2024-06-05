package Ascii4UwUWareClient.Module.Modules.Combat;


import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventTick;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Manager.FriendManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Misc.Teams;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TPaura extends Module {
    private int ticks;
    private EntityLivingBase target;
    public List<Entity> targets = new ArrayList();
    private int tpdelay;
    public boolean criticals;
    private TimerUtil timer = new TimerUtil();

    public TPaura() {
        super("TPAura", new String[]{"tpaura"}, ModuleType.Combat);
    }

    private List<Entity> loadTargets() {
        return this.mc.theWorld.loadedEntityList.stream().filter(e -> (double)this.mc.thePlayer.getDistanceToEntity((Entity)e) <= 30 && this.qualifies((Entity)e)).collect(Collectors.toList());
    }

    private boolean qualifies(Entity e) {
        if (e == this.mc.thePlayer) {
            return false;
        }
        AntiBot ab = (AntiBot)Client.instance.getModuleManager().getModuleByClass(AntiBot.class);
        if (ab.isServerBot((EntityPlayer) e)) {
            return false;
        }
        if (!e.isEntityAlive()) {
            return false;
        }
        if (FriendManager.isFriend(e.getName())) {
            return false;
        }
        if (e instanceof EntityPlayer && !Teams.isOnSameTeam(e)) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void onUpdate(EventTick event) {
        this.setColor(new Color(255, 50, 70).getRGB());
        ++this.ticks;
        ++this.tpdelay;
        if (this.ticks >= 20 - this.speed()) {
            this.ticks = 0;
            this.targets = this.loadTargets();
            this.targets.sort((o1, o2) -> (int) (o1.getDistanceToEntity(o2) - o2.getDistanceToEntity(o1)));
            for (Entity loaded : this.targets) {
                EntityLivingBase entity;
                entity = (EntityLivingBase)loaded;
                if (this.tpdelay >= 4) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                }
                this.attack(entity);
            }
        }
    }

    public void attack(EntityLivingBase entity) {
        this.attack(entity, false);
    }

    public void attack(EntityLivingBase entity, boolean crit) {
        this.mc.thePlayer.swingItem();
        float sharpLevel = EnchantmentHelper.func_152377_a(this.mc.thePlayer.getHeldItem(), entity.getCreatureAttribute());
        boolean vanillaCrit = this.mc.thePlayer.fallDistance > 0.0f && !this.mc.thePlayer.onGround && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isPotionActive(Potion.blindness) && this.mc.thePlayer.ridingEntity == null;
        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            this.mc.thePlayer.onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            this.mc.thePlayer.onEnchantmentCritical(entity);
        }
    }

    private int speed() {
        return 8;
    }
}
