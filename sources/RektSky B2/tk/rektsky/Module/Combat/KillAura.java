package tk.rektsky.Module.Combat;

import tk.rektsky.Module.*;
import java.util.*;
import net.minecraft.entity.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Utils.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Utils.Entity.*;
import net.minecraft.entity.player.*;

public class KillAura extends Module
{
    int ticks;
    public ArrayList<Entity> attackedEntity;
    public DoubleSetting rangeSetting;
    public IntSetting delaySetting;
    public ListSetting modeSetting;
    public BooleanSetting blockAnimationSetting;
    public BooleanSetting blockSetting;
    public BooleanSetting targetHudSetting;
    public boolean attackPost;
    boolean attacked;
    public EntityLivingBase t;
    public boolean isAttacking;
    
    public KillAura() {
        super("KillAura", "Auto Attack Entities", 0, Category.COMBAT);
        this.ticks = 0;
        this.attackedEntity = new ArrayList<Entity>();
        this.rangeSetting = new DoubleSetting("Range", 2.0, 10.0, 6.0);
        this.delaySetting = new IntSetting("Delay", 1, 10, 1);
        this.modeSetting = new ListSetting("Mode", new String[] { "Closest" }, "Closest");
        this.blockAnimationSetting = new BooleanSetting("BlockAnimation", true);
        this.blockSetting = new BooleanSetting("DoBlock", true);
        this.targetHudSetting = new BooleanSetting("TargetHUD", true);
        this.t = null;
        this.isAttacking = false;
        this.registerSetting(this.rangeSetting);
        this.registerSetting(this.delaySetting);
        this.registerSetting(this.modeSetting);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        this.isAttacking = false;
        RotationUtil.doReset = !this.isAttacking;
        this.isAttacking = false;
        this.t = null;
        this.attackPost = false;
    }
    
    @Override
    public void onDisable() {
        this.isAttacking = false;
        RotationUtil.doReset = true;
        this.isAttacking = false;
        this.t = null;
        this.attackPost = false;
    }
    
    @Override
    public String getSuffix() {
        return this.modeSetting.getValue();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof WorldTickEvent) {
            ++this.ticks;
            if (this.modeSetting.getValue().equals("Closest")) {
                final ArrayList<EntityLivingBase> targets = EntityUtil.getEntities();
                final EntityLivingBase target = EntityUtil.getClosest(targets, this.rangeSetting.getValue());
                if (target != null) {
                    RotationUtil.faceBlock(target.posX, target.posY + 1.3, target.posZ);
                    if (this.ticks % this.delaySetting.getValue() == 0 && target.ticksExisted > 2) {
                        this.mc.thePlayer.swingItem();
                        this.mc.playerController.attackEntity(this.mc.thePlayer, target);
                        this.t = target;
                        this.isAttacking = true;
                        RotationUtil.doReset = false;
                    }
                }
                else {
                    this.isAttacking = false;
                    this.t = null;
                    this.attackPost = false;
                    RotationUtil.doReset = true;
                }
            }
        }
    }
}
