package me.mees.remix.modules.combat;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.killaura.*;
import me.satisfactory.base.utils.aura.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.item.*;
import me.satisfactory.base.utils.*;
import optifine.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import me.satisfactory.base.*;
import net.minecraft.network.*;
import java.util.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;
import me.satisfactory.base.relations.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public class Killaura extends Module
{
    public static EntityLivingBase target;
    public TimerUtil timer;
    public TimerUtil switchdelay;
    public TimerUtil randomTimer;
    public int TimerUtil;
    public double lastReportedCPS;
    private int currentTarget;
    private EntityLivingBase en22222;
    private ArrayList<EntityLivingBase> loaded;
    private boolean behindBlock;
    private boolean isBlocking;
    int truee;
    int falsee;
    
    public Killaura() {
        super("Killaura", 0, Category.COMBAT);
        this.timer = new TimerUtil();
        this.switchdelay = new TimerUtil();
        this.randomTimer = new TimerUtil();
        this.TimerUtil = 5;
        this.loaded = new ArrayList<EntityLivingBase>();
        this.truee = 0;
        this.falsee = 0;
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Single");
        options.add("Switch");
        options.add("Tick");
        this.addSetting(new Setting("Killaura Mode", this, "Single", options));
        this.addSetting(new Setting("Min APS", this, 8.0, 1.0, 20.0, true, 1.0));
        this.addSetting(new Setting("Max APS", this, 13.0, 1.0, 20.0, true, 1.0));
        this.addSetting(new Setting("Range", this, 4.3, 1.0, 6.0, false, 0.1));
        this.addSetting(new Setting("Swing Range", this, 4.3, 0.0, 6.0, false, 0.1));
        this.addSetting(new Setting("Switch Delay", this, 80.0, 1.0, 500.0, true, 1.0));
        this.addSetting(new Setting("rayTrace", this, true));
        this.addSetting(new Setting("Players", this, true));
        this.addSetting(new Setting("Animals", this, false));
        this.addSetting(new Setting("AutoBlock", this, false));
        this.addSetting(new Setting("Mobs", this, false));
        this.addSetting(new Setting("AAC", this, false));
        this.addSetting(new Setting("Invisibles", this, false));
        this.addSetting(new Setting("Dura", this, false));
        this.addSetting(new Setting("Teams", this, false));
        this.addSetting(new Setting("No Rotate", this, false));
        this.addSetting(new Setting("Stop Sprint", this, false));
        this.addMode(new Single(this));
        this.addMode(new Switch(this));
        this.addMode(new Tick(this));
    }
    
    @Override
    public void onDisable() {
        this.falsee = 0;
        this.truee = 0;
        super.onDisable();
    }
    
    @Subscriber
    public void onMotion(final EventMotion event) {
        this.behindBlock = false;
        Killaura.target = this.getBestEntity();
        if (Killaura.target != null) {
            final float yaw = this.getEntityRotations(Killaura.target)[0];
            float pitch = this.getEntityRotations(Killaura.target)[1];
            if (pitch > 90.0f) {
                pitch = 90.0f;
            }
            if (this.getSettingByModule(this, "rayTrace").booleanValue()) {
                if (AuraUtils.getMouseOver(yaw, pitch, Killaura.target) instanceof Entity) {
                    this.behindBlock = false;
                    final Entity rayEn = AuraUtils.getMouseOver(yaw, pitch, Killaura.target);
                    if (rayEn != null && rayEn instanceof EntityLivingBase) {
                        this.en22222 = (EntityLivingBase)rayEn;
                        if (this.en22222 != Killaura.target) {}
                    }
                }
                else {
                    this.behindBlock = true;
                }
            }
            if (!this.qualifies(Killaura.target)) {
                return;
            }
            event.setYaw(yaw);
            event.setPitch(pitch);
            if (this.getSettingByModule(this, "Autoblock").booleanValue() && this.canBlock() && !Killaura.mc.thePlayer.isUsingItem()) {
                Killaura.mc.playerController.sendUseItem(Killaura.mc.thePlayer, Killaura.mc.theWorld, Killaura.mc.thePlayer.getCurrentEquippedItem());
                this.isBlocking = true;
            }
            else {
                this.isBlocking = false;
            }
        }
    }
    
    private boolean canBlock() {
        if (Killaura.mc == null && Killaura.mc.thePlayer.getHeldItem() == null) {
            return false;
        }
        if (Killaura.mc.thePlayer.isBlocking() || (Killaura.mc.thePlayer.isUsingItem() && Killaura.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
            return true;
        }
        try {
            if (Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && this.getSettingByModule(this, "Autoblock").booleanValue()) {
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public float[] getEntityRotations(final Entity target) {
        final double xDiff = target.posX - Killaura.mc.thePlayer.posX + (this.getSettingByModule(this, "AAC").booleanValue() ? MiscellaneousUtil.random(-target.width / 2.0f, target.width / 4.0f) : 0.0f);
        final double yDiff = target.posY - Killaura.mc.thePlayer.posY;
        final double zDiff = target.posZ - Killaura.mc.thePlayer.posZ + (this.getSettingByModule(this, "AAC").booleanValue() ? MiscellaneousUtil.random(-target.width / 2.0f, target.width / 4.0f) : 0.0f);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / 0.0 - (Killaura.mc.thePlayer.posY + Killaura.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        if (yDiff > -0.2 && yDiff < 0.2) {
            pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.CHEST.getOffset() - (Killaura.mc.thePlayer.posY + Killaura.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        else if (yDiff > -0.2) {
            pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.FEET.getOffset() - (Killaura.mc.thePlayer.posY + Killaura.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        else if (yDiff < 0.3) {
            pitch = (float)(-Math.atan2(target.posY + target.getEyeHeight() / HitLocation.HEAD.getOffset() - (Killaura.mc.thePlayer.posY + Killaura.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        return new float[] { yaw, pitch };
    }
    
    public void attack() {
        if (!this.qualifies(Killaura.target)) {
            return;
        }
        if (this.getDistanceSq(Killaura.target) <= MathUtils.square(this.range())) {
            final float sharpLevel = EnchantmentHelper.func_152377_a(Killaura.mc.thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            final boolean vanillaCrit = Killaura.mc.thePlayer.fallDistance > 0.0f && !Killaura.mc.thePlayer.onGround && !Killaura.mc.thePlayer.isOnLadder() && !Killaura.mc.thePlayer.isInWater() && !Killaura.mc.thePlayer.isPotionActive(Potion.blindness) && Killaura.mc.thePlayer.ridingEntity == null;
            if (Base.INSTANCE.getModuleManager().getModByName("Criticals").isEnabled() || vanillaCrit) {
                Killaura.mc.thePlayer.onCriticalHit(Killaura.target);
            }
            if (sharpLevel > 0.0f) {
                Killaura.mc.thePlayer.onEnchantmentCritical(Killaura.target);
            }
        }
        final EntityLivingBase entityBefore = Killaura.target;
        if (this.en22222 != null) {
            Killaura.target = this.en22222;
        }
        if (this.getDistanceSq(Killaura.target) <= MathUtils.square(this.swingRange())) {
            Killaura.mc.thePlayer.swingItem();
        }
        if (this.getSettingByModule(this, "Stop Sprint").booleanValue() && Killaura.mc.thePlayer.isSprinting()) {
            Killaura.mc.thePlayer.setSprinting(false);
            MiscellaneousUtil.setSpeed(0.08);
        }
        if (this.getDistanceSq(Killaura.target) <= MathUtils.square(this.range())) {
            Killaura.mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(Killaura.target, C02PacketUseEntity.Action.ATTACK));
        }
        this.randomTimer.reset();
        this.en22222 = null;
        Killaura.target = entityBefore;
    }
    
    public void changeTarget() {
        if (this.loaded.size() == 1) {
            return;
        }
        ++this.currentTarget;
    }
    
    private double getDistanceSq(final Entity target) {
        return Killaura.mc.thePlayer.getDistanceSq(target.posX, target.posY, target.posZ);
    }
    
    public EntityLivingBase getBestEntity() {
        if (this.loaded != null) {
            this.loaded.clear();
        }
        for (final Object object : Killaura.mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase)object;
                if (!this.qualifies(e)) {
                    continue;
                }
                this.loaded.add(e);
            }
        }
        if (this.loaded.isEmpty()) {
            return null;
        }
        final double dist1;
        final double dist2;
        this.loaded.sort((o1, o2) -> {
            dist1 = this.getDistanceSq(o1);
            dist2 = this.getDistanceSq(o2);
            return Double.compare(dist1, dist2);
        });
        if (this.currentTarget >= this.loaded.size()) {
            this.currentTarget = 0;
        }
        return this.loaded.get(this.currentTarget);
    }
    
    @Subscriber
    public void onPacket(final EventSendPacket packet) {
        if (Killaura.target != null && this.randomTimer.hasTimeElapsed((double)(long)this.lastReportedCPS) && this.canBlock() && !Base.INSTANCE.getModuleManager().getModByName("Scaffold").isEnabled() && packet.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            packet.setPacket(null);
            packet.setCancelled(true);
        }
    }
    
    private double swingRange() {
        return this.findSettingByName("Swing Range").doubleValue();
    }
    
    private double range() {
        return this.findSettingByName("Range").doubleValue();
    }
    
    public boolean qualifies(final Entity e2) {
        if (this.getDistanceSq(e2) > MathUtils.square(this.range()) && this.getDistanceSq(e2) > MathUtils.square(this.swingRange())) {
            return false;
        }
        if (e2 instanceof EntityPlayerSP) {
            return false;
        }
        if (this.getSettingByModule(this, "rayTrace").booleanValue() && !this.behindBlock && e2 == this.en22222) {
            return true;
        }
        if (this.getSettingByModule(this, "rayTrace").booleanValue() && this.behindBlock && e2 == this.en22222) {
            return false;
        }
        if (e2 instanceof EntityLivingBase) {
            Base.INSTANCE.getFriendManager();
            if (!FriendManager.isFriend(e2.getName()) && (e2.isInvisible() || this.getSettingByModule(this, "Invisibles").booleanValue()) && ((EntityLivingBase)e2).getHealth() > 0.0f && ((EntityLivingBase)e2).deathTime == 0 && e2 != Killaura.mc.thePlayer && (!(e2 instanceof EntityPlayer) || !FriendManager.isFriend(e2.getName())) && (!(e2 instanceof EntityPlayer) || !FriendManager.isFriend(e2.getName())) && (!this.getSettingByModule(this, "Teams").booleanValue() || !AuraUtils.isOnTeam((EntityLivingBase)e2))) {
                return (e2 instanceof EntityPlayer && this.getSettingByModule(this, "Players").booleanValue()) || ((e2 instanceof EntityMob || e2 instanceof EntitySlime || e2 instanceof EntityVillager || e2 instanceof EntityGhast) && this.getSettingByModule(this, "Mobs").booleanValue()) || ((e2 instanceof EntityAnimal || e2 instanceof EntitySquid || e2 instanceof EntityBat) && this.getSettingByModule(this, "Animals").booleanValue());
            }
        }
        return false;
    }
    
    private enum HitLocation
    {
        AUTO(0.0), 
        HEAD(1.0), 
        CHEST(1.5), 
        FEET(3.5);
        
        private double offset;
        
        private HitLocation(final double offset) {
            this.offset = offset;
        }
        
        public double getOffset() {
            return this.offset;
        }
    }
}
