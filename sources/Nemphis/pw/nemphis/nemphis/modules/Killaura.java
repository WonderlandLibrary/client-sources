/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import pw.vertexcode.nemphis.events.EventPreMotion;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.COMBAT, color=-1618884, name="Killaura")
public class Killaura
extends ToggleableModule {
    private Value<Float> speed;
    private Value<Float> reach;
    private Value<Boolean> autoblock;
    private Value<Boolean> autoSword;
    private Value<Boolean> animals;
    private Value<Boolean> mobs;
    private Value<Boolean> players;
    private Value<Boolean> teams;
    private Value<Boolean> gommeBots;
    private Value<Boolean> aacBots;
    private Value<Boolean> spartanBots;
    private Value<Boolean> swing;
    private TimeHelper timeHelper;
    float[] rotations;
    boolean sswing;
    List<Class<? extends Entity>> entities;
    public static List<Entity> capEntities = new ArrayList<Entity>();
    private float lastYaw;
    private float lastPitch;

    public Killaura() {
        this.speed = new Value<Float>("speed", Float.valueOf(10.0f), this);
        this.reach = new Value<Float>("reach", Float.valueOf(4.8f), this);
        this.autoblock = new Value<Boolean>("autoblock", true, this);
        this.autoSword = new Value<Boolean>("autoSword", false, this);
        this.animals = new Value<Boolean>("animals", false, this);
        this.mobs = new Value<Boolean>("mobs", false, this);
        this.players = new Value<Boolean>("players", true, this);
        this.teams = new Value<Boolean>("teams", true, this);
        this.gommeBots = new Value<Boolean>("gommeBots", true, this);
        this.aacBots = new Value<Boolean>("aacBots", true, this);
        this.spartanBots = new Value<Boolean>("spartanBots", true, this);
        this.swing = new Value<Boolean>("swing", true, this);
        this.timeHelper = new TimeHelper();
        this.sswing = false;
        this.entities = new ArrayList<Class<? extends Entity>>();
        this.addValue(this.speed);
        this.addValue(this.reach);
        this.addValue(this.autoSword);
        this.addValue(this.autoblock);
        this.addValue(this.aacBots);
        this.addValue(this.players);
        this.addValue(this.spartanBots);
        this.addValue(this.gommeBots);
        this.addValue(this.animals);
        this.addValue(this.mobs);
        this.addValue(this.teams);
        this.addValue(this.swing);
    }

    @Override
    public void onEnabled() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        this.CheckEntity();
        if (this.timeHelper.hasReached(1000.0f / this.speed.getValue().floatValue())) {
            Random rand = new Random();
            ArrayList entity = new ArrayList();
            Entity thisOne = null;
            float closest = Float.MAX_VALUE;
            World world = Minecraft.getMinecraft().thePlayer.worldObj;
            int i = 0;
            while (i < world.loadedEntityList.size()) {
                if (((Entity)world.loadedEntityList.get(i)).getDistanceToEntity(Killaura.mc.thePlayer) < this.reach.getValue().floatValue() && (Entity)world.loadedEntityList.get(i) instanceof EntityLivingBase && ((Entity)world.loadedEntityList.get(i)).isEntityAlive()) {
                    for (Class<? extends Entity> ent : this.entities) {
                        EntityLivingBase e;
                        if (!world.loadedEntityList.get(i).getClass().isAssignableFrom(ent) || world.loadedEntityList.get(i) == Killaura.mc.thePlayer || Killaura.gommeBots(e = (EntityLivingBase)world.loadedEntityList.get(i))) continue;
                        if (e.isDead) {
                            return;
                        }
                        capEntities.add((Entity)world.loadedEntityList.get(i));
                        closest = e.getDistanceToEntity(Killaura.mc.thePlayer);
                        thisOne = e;
                        if (Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().objectMouseOver.entityHit == null || Minecraft.getMinecraft().objectMouseOver.entityHit.getDistanceToEntity(Killaura.mc.thePlayer) > this.reach.getValue().floatValue()) continue;
                        thisOne = Minecraft.getMinecraft().objectMouseOver.entityHit;
                    }
                }
                ++i;
            }
            if (thisOne != null) {
                if (this.autoblock.getValue().booleanValue()) {
                    if (!Killaura.mc.thePlayer.isBlocking() && Killaura.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        Killaura.mc.thePlayer.setItemInUse(Killaura.mc.thePlayer.getCurrentEquippedItem(), 100);
                    }
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(thisOne, C02PacketUseEntity.Action.ATTACK));
                    Killaura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotations[0], this.rotations[1], Killaura.mc.thePlayer.onGround));
                    Killaura.mc.thePlayer.swingItem();
                } else {
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(thisOne, C02PacketUseEntity.Action.ATTACK));
                    Killaura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotations[0], this.rotations[1], Killaura.mc.thePlayer.onGround));
                    Killaura.mc.thePlayer.swingItem();
                }
                this.timeHelper.resetTimer();
                this.rotations = this.getEntityRotations(thisOne, true);
                this.sswing = true;
            }
            capEntities.clear();
        } else {
            this.sswing = false;
        }
    }

    @EventListener
    public void on(EventPreMotion e) {
        if (this.swing.getValue().booleanValue() && this.sswing) {
            Killaura.mc.thePlayer.swingItem();
        }
    }

    public float[] getEntityRotations(Entity target, boolean random) {
        double posX = target.posX - Killaura.mc.thePlayer.posX;
        double posY = target.posY + (double)target.getEyeHeight() - (Killaura.mc.thePlayer.posY + (double)Killaura.mc.thePlayer.getEyeHeight() + 1.1);
        double posZ = target.posZ - Killaura.mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793 - 90.0);
        float pitch = (float)((- Math.atan2(posY, MathHelper.sqrt_double(posX * posX + posZ * posZ))) * 180.0 / 3.141592653589793);
        if (random) {
            yaw += (float)Killaura.getRandom(-8, 8);
            pitch += (float)Killaura.getRandom(-30, 10);
        }
        return new float[]{yaw, pitch};
    }

    public static int getRandom(int min, int max) {
        return min + new Random().nextInt(max - min + 1);
    }

    public static boolean aacBots(Entity e) {
        EntityPlayer player;
        if (e instanceof EntityPlayer && (player = (EntityPlayer)e).getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < 6.0f && (player.getEntityId() >= 1070000000 || player.getEntityId() <= -1)) {
            return true;
        }
        return false;
    }

    public static boolean gommeBots(Entity e) {
        EntityPlayer p;
        if (e instanceof EntityPlayer && !(p = (EntityPlayer)e).getDisplayName().getFormattedText().startsWith("\u00a7")) {
            return true;
        }
        return false;
    }

    public static boolean spartanBots(Entity e) {
        if (e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)e;
            if (mc.getNetHandler().func_175102_a(p.getUniqueID()).getResponseTime() < 5) {
                return true;
            }
        }
        return false;
    }

    public void CheckEntity() {
        if (this.players.getValue().booleanValue()) {
            if (!this.entities.contains(EntityOtherPlayerMP.class)) {
                this.entities.add(EntityOtherPlayerMP.class);
            }
        } else {
            this.entities.remove(EntityOtherPlayerMP.class);
        }
        if (this.animals.getValue().booleanValue()) {
            if (!this.entities.contains(EntityPig.class)) {
                this.entities.add(EntityPig.class);
                this.entities.add(EntitySheep.class);
                this.entities.add(EntityCow.class);
                this.entities.add(EntityChicken.class);
                this.entities.add(EntityWolf.class);
                this.entities.add(EntityBat.class);
                this.entities.add(EntityHorse.class);
                this.entities.add(EntityOcelot.class);
                this.entities.add(EntityRabbit.class);
                this.entities.add(EntitySquid.class);
                this.entities.add(EntityVillager.class);
            }
        } else if (this.entities.contains(EntityPig.class)) {
            this.entities.remove(EntityPig.class);
            this.entities.remove(EntitySheep.class);
            this.entities.remove(EntityCow.class);
            this.entities.remove(EntityChicken.class);
            this.entities.remove(EntityWolf.class);
            this.entities.remove(EntityBat.class);
            this.entities.remove(EntityHorse.class);
            this.entities.remove(EntityOcelot.class);
            this.entities.remove(EntityRabbit.class);
            this.entities.remove(EntitySquid.class);
            this.entities.remove(EntityVillager.class);
        }
        if (this.mobs.getValue().booleanValue()) {
            if (!this.entities.contains(EntityZombie.class)) {
                this.entities.add(EntityZombie.class);
                this.entities.add(EntityBlaze.class);
                this.entities.add(EntityCreeper.class);
                this.entities.add(EntityCaveSpider.class);
                this.entities.add(EntityEnderman.class);
                this.entities.add(EntityEndermite.class);
                this.entities.add(EntityGhast.class);
                this.entities.add(EntityGiantZombie.class);
                this.entities.add(EntityIronGolem.class);
                this.entities.add(EntityGuardian.class);
                this.entities.add(EntityMagmaCube.class);
                this.entities.add(EntityPigZombie.class);
                this.entities.add(EntitySilverfish.class);
                this.entities.add(EntitySkeleton.class);
                this.entities.add(EntitySlime.class);
                this.entities.add(EntitySnowman.class);
                this.entities.add(EntitySpider.class);
                this.entities.add(EntityWitch.class);
            }
        } else if (this.entities.contains(EntityZombie.class)) {
            this.entities.remove(EntityZombie.class);
            this.entities.remove(EntityBlaze.class);
            this.entities.remove(EntityCreeper.class);
            this.entities.remove(EntityCaveSpider.class);
            this.entities.remove(EntityEnderman.class);
            this.entities.remove(EntityEndermite.class);
            this.entities.remove(EntityGhast.class);
            this.entities.remove(EntityGiantZombie.class);
            this.entities.remove(EntityIronGolem.class);
            this.entities.remove(EntityGuardian.class);
            this.entities.remove(EntityMagmaCube.class);
            this.entities.remove(EntityPigZombie.class);
            this.entities.remove(EntitySilverfish.class);
            this.entities.remove(EntitySkeleton.class);
            this.entities.remove(EntitySlime.class);
            this.entities.remove(EntitySnowman.class);
            this.entities.remove(EntitySpider.class);
            this.entities.remove(EntityWitch.class);
        }
    }
}

