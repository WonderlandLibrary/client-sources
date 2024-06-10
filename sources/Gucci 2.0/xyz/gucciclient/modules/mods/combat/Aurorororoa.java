package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.utils.Timer;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;

public class Aurorororoa extends Module
{
    private BooleanValue player;
    private BooleanValue mob;
    private BooleanValue animal;
    private BooleanValue Switch;
    private BooleanValue invis;
    private NumberValue reach;
    private NumberValue ticksexisted;
    private NumberValue maxcps;
    private NumberValue mincps;
    private EntityLivingBase entity;
    private ArrayList<Entity> entities;
    private Timer timer;
    private Random rand;
    
    public Aurorororoa() {
        super(Modules.KillAura.name(), 0, Category.COMBAT);
        this.player = new BooleanValue("Players", true);
        this.mob = new BooleanValue("Mobs", false);
        this.animal = new BooleanValue("Animals", false);
        this.Switch = new BooleanValue("Switch", false);
        this.invis = new BooleanValue("Invis", true);
        this.reach = new NumberValue("Reach", 4.25, 3.0, 8.0);
        this.ticksexisted = new NumberValue("Ticks existed", 5.0, 1.0, 300.0);
        this.maxcps = new NumberValue("MaxCPS", 12.0, 1.0, 20.0);
        this.mincps = new NumberValue("MinCPS", 6.0, 1.0, 20.0);
        this.entities = new ArrayList<Entity>();
        this.timer = new Timer();
        this.rand = new Random();
        this.addValue(this.reach);
        this.addValue(this.maxcps);
        this.addValue(this.mincps);
        this.addValue(this.ticksexisted);
        this.addBoolean(this.invis);
        this.addBoolean(this.player);
        this.addBoolean(this.mob);
        this.addBoolean(this.animal);
        this.addBoolean(this.Switch);
    }
    
    @Override
    public void onEnable() {
        this.entities.clear();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent event) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (Wrapper.getWorld() == null) {
            return;
        }
        this.AddValidTargetsToArrayList();
        this.Attack();
    }
    
    private long getDelay() {
        return (long)(this.maxcps.getValue() + this.rand.nextDouble() * (this.mincps.getValue() - this.maxcps.getValue()));
    }
    
    public boolean isTargetValid(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!entity.isEntityAlive()) {
            return false;
        }
        if (!this.mc.thePlayer.canEntityBeSeen(entity)) {
            return false;
        }
        final float range = (float)this.reach.getValue();
        return this.mc.thePlayer.getDistanceToEntity(entity) <= range;
    }
    
    public void Attack() {
        for (int i = 0; i < this.entities.size(); ++i) {
            final Entity entity = this.entities.get(i);
            if (entity.isDead) {
                this.entities.remove(entity);
            }
            if (this.isTargetValid(entity)) {
                if (this.timer.hasReached((double)(1000L / this.getDelay()))) {
                    this.Attack(entity);
                    if (this.Switch.getState()) {
                        this.entities.remove(i);
                    }
                    this.getDelay();
                    this.timer.reset();
                }
            }
        }
    }
    
    public boolean isValid(final Entity entity) {
        return entity != null && entity.isEntityAlive() && entity != this.mc.thePlayer && ((entity instanceof EntityPlayer && this.player.getState()) || (entity instanceof EntityAnimal && this.animal.getState()) || entity instanceof EntityMob || entity instanceof EntityBat || entity instanceof EntityGolem || (entity instanceof EntityDragon && this.mob.getState())) && entity.getDistanceToEntity((Entity)this.mc.thePlayer) <= this.reach.getValue() && (!entity.isInvisible() || this.invis.getState()) && entity.ticksExisted > this.ticksexisted.getValue();
    }
    
    public void AddValidTargetsToArrayList() {
        for (int entities = 0; entities < Wrapper.getMinecraft().theWorld.loadedEntityList.size(); ++entities) {
            final Entity entity = Wrapper.getMinecraft().theWorld.getLoadedEntityList().get(entities);
            if (this.isValid(entity)) {
                this.entities.add(entity);
            }
            else {
                this.entities.remove(entity);
            }
        }
    }
    
    private float[] getAngles(final EntityLivingBase entityLiving) {
        final double difX = entityLiving.posX - Wrapper.getPlayer().posX;
        final double difY = entityLiving.posY - Wrapper.getPlayer().posY + entityLiving.getEyeHeight() / 1.4f;
        final double difZ = entityLiving.posZ - Wrapper.getPlayer().posZ;
        final double hypo = Wrapper.getPlayer().getDistanceToEntity((Entity)entityLiving);
        final float yaw = (float)Math.toDegrees(Math.atan2(difZ, difX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(difY, hypo)));
        return new float[] { yaw, pitch };
    }
    
    public float getDistanceBetweenAngles(final float angle1, final float angle2) {
        return Math.abs(angle1 % 360.0f - angle2 % 360.0f) % 360.0f;
    }
    
    public void Attack(final Entity entity) {
        if (this.mincps.getValue() > this.maxcps.getValue()) {
            this.mincps.setValue(this.maxcps.getValue());
        }
        if (!this.entities.contains(entity)) {
            return;
        }
        Wrapper.getPlayer().swingItem();
        Wrapper.getPlayerController().attackEntity((EntityPlayer)Wrapper.getPlayer(), entity);
    }
}
