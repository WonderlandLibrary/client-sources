/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.Client;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.event.events.Render3DEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.modes.Mode;
import winter.module.modules.modes.aura.Single;
import winter.module.modules.modes.aura.Switch;
import winter.module.modules.modes.aura.Tick;
import winter.utils.friend.FriendUtil;
import winter.utils.other.Timer;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.ModeValue;
import winter.utils.value.types.NumberValue;

public class Aura
extends Module {
    public static ArrayList<EntityLivingBase> targetList;
    public static int delay;
    public static Entity target;
    public static Timer potTimer;
    public static NumberValue range;
    public static NumberValue brange;
    public static NumberValue ticks;
    public static BooleanValue dora;
    public static BooleanValue block;
    public static BooleanValue pl;
    public static BooleanValue an;
    public static BooleanValue mo;
    public static BooleanValue teams;
    public static BooleanValue box;
    public static BooleanValue invis;
    public static BooleanValue unblock;
    public static BooleanValue preach;
    public static Timer delayTimer;
    public static Timer fakeSwing;
    public static Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }

    public Aura() {
        super("Aura", Module.Category.Combat, -10511975);
        this.setBind(19);
        delayTimer = new Timer();
        fakeSwing = new Timer();
        potTimer = new Timer();
        delay = 0;
        this.addMode(new Switch(this, "Switch"));
        this.addMode(new Single(this, "Single"));
        this.addMode(new Tick(this, "Tick"));
        this.addValue(new ModeValue(this));
        range = new NumberValue("Range", 5.5, 1.0, 15.0, 0.1);
        this.addValue(range);
        brange = new NumberValue("Block-Range", 8.0, 1.0, 18.0, 0.1);
        this.addValue(brange);
        ticks = new NumberValue("Ticks", 2.0, 1.0, 20.0, 1.0);
        this.addValue(ticks);
        dora = new BooleanValue("Dura Swap", true);
        this.addValue(dora);
        block = new BooleanValue("Autoblock", true);
        this.addValue(block);
        pl = new BooleanValue("Players", true);
        this.addValue(pl);
        an = new BooleanValue("Animals", false);
        this.addValue(an);
        mo = new BooleanValue("Monsters", false);
        this.addValue(mo);
        invis = new BooleanValue("Ignore Invis", true);
        this.addValue(invis);
        teams = new BooleanValue("Hypixel Teams", false);
        this.addValue(teams);
        box = new BooleanValue("Target Boxes", false);
        this.addValue(box);
        unblock = new BooleanValue("Unblock Packet", true);
        this.addValue(unblock);
        preach = new BooleanValue("Packet Reach", false);
        this.addValue(preach);
        this.mode(" Switch");
        targetList = new ArrayList();
    }

    @Override
    public void onDisable() {
        Aura.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
        target = null;
        delay = 0;
        delayTimer.reset();
        fakeSwing.reset();
    }

    @Override
    public void onEnable() {
        targetList = new ArrayList();
        ((Mode)this.modes.get(this.modeInt)).enable();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        ((Mode)this.modes.get(this.modeInt)).onUpdate(event);
        this.findTargets();
        ArrayList<Entity> entToRemove = new ArrayList<Entity>();
        if (!targetList.isEmpty()) {
            int i2 = 0;
            while (i2 < targetList.size()) {
                if (!targetList.get(i2).isEntityAlive() || (double)Aura.mc.thePlayer.getDistanceToEntity(targetList.get(i2)) > range.getValue()) {
                    entToRemove.add(targetList.get(i2));
                }
                ++i2;
            }
            for (Entity e2 : entToRemove) {
                if (!targetList.contains(e2)) continue;
                targetList.remove(e2);
            }
        }
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        ((Mode)this.modes.get(this.modeInt)).onPacket(event);
    }

    @EventListener
    public void onRender(Render3DEvent event) {
        ((Mode)this.modes.get(this.modeInt)).onRender3D(event);
    }

    public static float getPitch(Entity ent) {
        double x2 = ent.posX - Aura.mc.thePlayer.posX;
        double y2 = ent.posY - Aura.mc.thePlayer.posY;
        double z2 = ent.posZ - Aura.mc.thePlayer.posZ;
        double pitch = Math.asin(y2 /= (double)Aura.mc.thePlayer.getDistanceToEntity(ent)) * 57.29577951308232;
        pitch = - pitch;
        return (float)pitch;
    }

    public static float getYaw(Entity ent) {
        double x2 = ent.posX - Aura.mc.thePlayer.posX;
        double y2 = ent.posY - Aura.mc.thePlayer.posY;
        double z2 = ent.posZ - Aura.mc.thePlayer.posZ;
        double yaw = Math.atan2(x2, z2) * 57.29577951308232;
        yaw = - yaw;
        return (float)yaw;
    }

    public static boolean crit() {
        if (Client.getManager().getMod("Criticals").isEnabled()) {
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Aura.mc.thePlayer.posX, Aura.mc.thePlayer.posY + 0.05, Aura.mc.thePlayer.posZ, false));
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Aura.mc.thePlayer.posX, Aura.mc.thePlayer.posY, Aura.mc.thePlayer.posZ, false));
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Aura.mc.thePlayer.posX, Aura.mc.thePlayer.posY + 0.012511, Aura.mc.thePlayer.posZ, false));
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Aura.mc.thePlayer.posX, Aura.mc.thePlayer.posY, Aura.mc.thePlayer.posZ, false));
        }
        return true;
    }

    public static void attack(Entity ent, boolean crit) {
        if (Aura.crit()) {
            float sharpLevel = EnchantmentHelper.func_152377_a(Aura.mc.thePlayer.getHeldItem(), ((EntityLivingBase)ent).getCreatureAttribute());
            boolean block = false;
            if (Aura.mc.thePlayer.isBlocking() && unblock.isEnabled()) {
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                block = true;
            }
            Aura.mc.thePlayer.swingItem();
            if (!preach.isEnabled()) {
                Aura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
            } else {
                double entx = ent.posX - 3.5 * Math.cos(Math.toRadians(Aura.getYaw(ent) + 90.0f));
                double entz = ent.posZ - 3.5 * Math.sin(Math.toRadians(Aura.getYaw(ent) + 90.0f));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(entx, ent.posY, entz, Aura.getYaw(ent), Aura.getPitch(ent), Aura.mc.thePlayer.onGround));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Aura.mc.thePlayer.posX, Aura.mc.thePlayer.posY, Aura.mc.thePlayer.posZ, Aura.mc.thePlayer.onGround));
            }
            if (Client.getManager().getMod("Criticals").isEnabled()) {
                Aura.mc.thePlayer.onCriticalHit(ent);
            }
            if (sharpLevel > 0.0f) {
                Aura.mc.thePlayer.onEnchantmentCritical(ent);
            }
            if (block) {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Aura.mc.thePlayer.inventory.getCurrentItem()));
                Aura.mc.thePlayer.setItemInUse(Aura.mc.thePlayer.inventory.getCurrentItem(), Aura.mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isntBot(Entity entity) {
        if (!Client.isEnabled("Antibot")) return true;
        if (entity instanceof EntityMob || entity instanceof EntityWither || entity instanceof EntityBat || entity instanceof EntityGolem || entity instanceof EntityDragon) {
            if (mo.isEnabled()) return true;
        }
        if (entity instanceof EntityAnimal && an.isEnabled()) {
            return true;
        }
        try {
            EntityPlayer p2;
            if (entity instanceof EntityPlayerSP) {
                return false;
            }
            if (entity.getEntityId() >= 1000000000) {
                return false;
            }
            if (entity.ticksExisted < 35) {
                return false;
            }
            if (!GuiPlayerTabOverlay.getPlayerList().contains(entity)) {
                return false;
            }
            if (entity.motionY == 0.0 && !entity.isCollidedVertically && entity.onGround && entity.posY % 1.0 != 0.0 && entity.posY % 0.5 != 0.0 && !Aura.mc.getCurrentServerData().serverIP.contains("mineplex")) {
                return false;
            }
            if (entity.motionY == 0.0 && !entity.isCollidedVertically && !entity.onGround && entity.posY % 1.0 != 0.0 && entity.posY % 0.5 != 0.0 && Aura.mc.getCurrentServerData().serverIP.contains("mineplex")) {
                return false;
            }
            Iterator<EntityPlayer> iterator = GuiPlayerTabOverlay.getPlayerList().iterator();
            do {
                if (iterator.hasNext()) continue;
                return true;
            } while ((p2 = iterator.next()) == null || !entity.getUniqueID().equals(p2.getUniqueID()) || entity.getEntityId() == p2.getEntityId());
            return false;
        }
        catch (Exception e2) {
            return false;
        }
    }

    public boolean isTeamed(Entity e2) {
        if (!teams.isEnabled()) {
            return false;
        }
        boolean team = false;
        if (Aura.mc.thePlayer.isOnSameTeam((EntityLivingBase)e2)) {
            team = true;
        }
        return team;
    }

    public boolean invis(Entity e2) {
        if (invis.isEnabled() && e2.isInvisible()) {
            return false;
        }
        return true;
    }

    public void findTargets() {
        double r2 = range.getValue();
        for (Object o : Aura.mc.theWorld.loadedEntityList) {
            EntityLivingBase ent;
            if (!(o instanceof EntityLivingBase) || (ent = (EntityLivingBase)o) == Aura.mc.thePlayer || (double)Aura.mc.thePlayer.getDistanceToEntity(ent) > r2 || (!(ent instanceof EntityPlayer) || !pl.isEnabled()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityWither) && !(ent instanceof EntityBat) && !(ent instanceof EntityGolem) && !(ent instanceof EntityDragon) || !mo.isEnabled()) && (!(ent instanceof EntityAnimal) || !an.isEnabled()) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || !this.invis(ent) || !Aura.isntBot(ent) || this.isTeamed(ent) || targetList.contains(ent)) continue;
            targetList.add(ent);
        }
    }

    public Entity findClosest() {
        double r2 = brange.getValue();
        Entity e2 = null;
        for (Object o : Aura.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent == Aura.mc.thePlayer || (double)Aura.mc.thePlayer.getDistanceToEntity(ent) > r2 || (!(ent instanceof EntityPlayer) || !pl.isEnabled()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityWither) && !(ent instanceof EntityBat) && !(ent instanceof EntityGolem) && !(ent instanceof EntityDragon) || !mo.isEnabled()) && (!(ent instanceof EntityAnimal) || !an.isEnabled()) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || !this.invis(ent) || !Aura.isntBot(ent) || this.isTeamed(ent)) continue;
            r2 = Aura.mc.thePlayer.getDistanceToEntity(ent);
            e2 = ent;
        }
        return e2;
    }
}

