package wwe.modules.combat;

import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import wwe.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import wwe.modules.exploits.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;

public class KillAura extends qMIe
{
    public static EntityLivingBase oYbE;
    public sbfc Xijd;
    public ArrayList<EntityLivingBase> AWXn;
    public ModeValue mode;
    public ModeValue priority;
    public ModeValue bodyPart;
    public ModeValue eatting;
    public ModeValue event;
    public ModeValue rotationType;
    public BooleanValue players;
    public BooleanValue animals;
    public BooleanValue mobs;
    public BooleanValue 19Delay;
    public BooleanValue aimbot;
    public BooleanValue invisibles;
    public BooleanValue walls;
    public NumberValue range;
    public NumberValue cps;
    public NumberValue hitchance;
    
    public KillAura() {
        super("Kill Aura", "Automatically hits entities", 37, Category.COMBAT);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Single");
        list.add("Multi");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Single", list);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Closest");
        list2.add("Health");
        list2.add("Random");
        list2.add("None");
        this.priority = new ModeValue("Priority", this, "Which target to hit", "Closest", list2);
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("Head");
        list3.add("Chest");
        list3.add("Pants");
        list3.add("Boots");
        this.bodyPart = new ModeValue("Body Part", this, "What body part to attack", "Chest", list3);
        final ArrayList<String> list4 = new ArrayList<String>();
        list4.add("Stop");
        list4.add("Stop eatting");
        list4.add("Ignore");
        this.eatting = new ModeValue("Eatting", this, "Eatting modes", "Stop", list4);
        final ArrayList<String> list5 = new ArrayList<String>();
        list5.add("Pre");
        list5.add("Post");
        this.event = new ModeValue("Event", this, "What event to attack the crystal on", "Pre", list5);
        final ArrayList<String> list6 = new ArrayList<String>();
        list6.add("Snap");
        list6.add("Stay");
        this.rotationType = new ModeValue("Rotation Type", this, "What type of rotation to do", "Snap", list6);
        this.players = new BooleanValue("Players", this, "Target players", true);
        this.animals = new BooleanValue("Animals", this, "Target friendly animals", false);
        this.mobs = new BooleanValue("Mobs", this, "Target hostile mobs", false);
        this.19Delay = new BooleanValue("1.9+ Delay", this, "Use 1.9+ delay", true);
        this.aimbot = new BooleanValue("Aimbot", this, "Look at the entity", false);
        this.invisibles = new BooleanValue("Invisibles", this, "Target invisible entities", false);
        this.walls = new BooleanValue("Walls", this, "Attack threw walls", true);
        this.range = new NumberValue("Range", this, "Range to attack entities", 6.0, 0.0, 6.0);
        this.cps = new NumberValue("CPS", this, "Clicks per second", 7.0, 0.1, 20.0);
        this.hitchance = new NumberValue("Hit chance", this, "What percentage of hits actually hit", 100.0, 0.0, 100.0);
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        KillAura.oYbE = null;
        super.qBmy();
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Single")) {
            this.owgW("Kill Aura " + TextFormatting.DARK_GRAY + "Single");
            if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                throw null;
            }
        }
        else if (this.mode.OnGi().equalsIgnoreCase("Multi")) {
            this.owgW("Kill Aura " + TextFormatting.DARK_GRAY + "Multi");
        }
        if (this.eatting.OnGi().equalsIgnoreCase("Stop") && Wrapper.mc.player.isHandActive()) {
            return;
        }
        KillAura.oYbE = null;
        if (this.priority.OnGi().equalsIgnoreCase("Closest")) {
            double doubleValue = this.range.OnGi();
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity instanceof EntityLivingBase) {
                    final EntityLivingBase oYbE = (EntityLivingBase)entity;
                    if (FIwW.Bvtk(oYbE, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi()) && Wrapper.mc.player.getDistance((Entity)oYbE) < doubleValue) {
                        doubleValue = Wrapper.mc.player.getDistance((Entity)oYbE);
                        KillAura.oYbE = oYbE;
                    }
                }
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
        }
        if (this.priority.OnGi().equalsIgnoreCase("Health")) {
            float health = -1.0f;
            for (final Entity entity2 : Wrapper.mc.world.loadedEntityList) {
                if (entity2 instanceof EntityLivingBase) {
                    final EntityLivingBase oYbE2 = (EntityLivingBase)entity2;
                    if (FIwW.Bvtk(oYbE2, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi()) && (oYbE2.getHealth() <= health || health == -1.0f)) {
                        health = oYbE2.getHealth();
                        KillAura.oYbE = oYbE2;
                    }
                }
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
        }
        if (this.priority.OnGi().equalsIgnoreCase("Random")) {
            this.AWXn = new ArrayList<EntityLivingBase>();
            for (final Entity entity3 : Wrapper.mc.world.loadedEntityList) {
                if (entity3 instanceof EntityLivingBase) {
                    final EntityLivingBase entityLivingBase = (EntityLivingBase)entity3;
                    if (FIwW.Bvtk(entityLivingBase, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi())) {
                        this.AWXn.add(entityLivingBase);
                    }
                }
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
            KillAura.oYbE = this.AWXn.get(new Random().nextInt(this.AWXn.size()));
        }
        if (this.priority.OnGi().equalsIgnoreCase("None")) {
            for (final Entity entity4 : Wrapper.mc.world.loadedEntityList) {
                if (entity4 instanceof EntityLivingBase) {
                    final EntityLivingBase oYbE3 = (EntityLivingBase)entity4;
                    if (FIwW.Bvtk(oYbE3, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi())) {
                        KillAura.oYbE = oYbE3;
                    }
                }
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
        }
        if (KillAura.oYbE == null || !FIwW.Bvtk(KillAura.oYbE, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi())) {
            return;
        }
        if (this.eatting.OnGi().equalsIgnoreCase("Stop eatting") && Wrapper.mc.player.isHandActive()) {
            Wrapper.mc.player.stopActiveHand();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
        }
        if (this.rotationType.OnGi().equalsIgnoreCase("Stay")) {
            omqH.prFd = this.aADM((Entity)KillAura.oYbE, Float.MAX_VALUE, Float.MAX_VALUE)[0];
            omqH.OjyD = this.aADM((Entity)KillAura.oYbE, Float.MAX_VALUE, Float.MAX_VALUE)[1];
        }
        if (Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
            if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                throw null;
            }
        }
        if (this.19Delay.OnGi()) {}
        if (!this.19Delay.OnGi()) {
            this.Xijd.gCgh();
            if (this.Xijd.QOXG(1000.0 / this.cps.OnGi() - new Random().nextInt(4)) && this.rotationType.OnGi().equalsIgnoreCase("Snap")) {
                omqH.prFd = this.aADM((Entity)KillAura.oYbE, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                omqH.OjyD = this.aADM((Entity)KillAura.oYbE, Float.MAX_VALUE, Float.MAX_VALUE)[1];
            }
        }
        if (this.event.OnGi().equalsIgnoreCase("Pre")) {
            this.ioqL();
        }
    }
    
    @oqrr
    public void uEyI(final ILBH ilbh) {
        if (!FIwW.Bvtk(KillAura.oYbE, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi()) || !this.event.OnGi().equalsIgnoreCase("Post")) {
            return;
        }
        this.ioqL();
    }
    
    public void ioqL() {
        if (this.mode.OnGi().equalsIgnoreCase("Multi")) {
            boolean b;
            if (Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
                b = true;
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            final boolean b2 = b;
            if (this.19Delay.OnGi() && b2) {
                if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)KillAura.oYbE));
                    if (Wrapper.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                        Wrapper.mc.player.attackTargetEntityWithCurrentItem((Entity)KillAura.oYbE);
                    }
                }
                OffHand.LInL();
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
            else if (!this.19Delay.OnGi()) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(1000.0 / this.cps.OnGi() - new Random().nextInt(4))) {
                    if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)KillAura.oYbE));
                    }
                    OffHand.LInL();
                    Wrapper.mc.player.resetCooldown();
                }
            }
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity instanceof EntityLivingBase && FIwW.Bvtk((EntityLivingBase)entity, this.players.OnGi(), this.animals.OnGi(), this.mobs.OnGi(), this.invisibles.OnGi(), this.walls.OnGi(), this.range.OnGi()) && entity != KillAura.oYbE) {
                    if (this.19Delay.OnGi() && b2) {
                        if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                            if (Wrapper.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                                Wrapper.mc.player.attackTargetEntityWithCurrentItem(entity);
                            }
                        }
                        OffHand.LInL();
                        if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                            throw null;
                        }
                    }
                    else if (!this.19Delay.OnGi()) {
                        this.Xijd.gCgh();
                        if (this.Xijd.QOXG(1000.0 / this.cps.OnGi() - new Random().nextInt(4))) {
                            if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                            }
                            OffHand.LInL();
                            Wrapper.mc.player.resetCooldown();
                        }
                    }
                }
                if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                    throw null;
                }
            }
            if (this.Xijd.QOXG(1000.0 / this.cps.OnGi() - new Random().nextInt(4))) {
                this.Xijd.BBwj();
            }
            if (this.19Delay.OnGi()) {}
        }
        if (this.19Delay.OnGi() && Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
            if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, (Entity)KillAura.oYbE);
            }
            OffHand.LInL();
            Wrapper.mc.player.resetCooldown();
            if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                throw null;
            }
        }
        else if (!this.19Delay.OnGi()) {
            this.Xijd.gCgh();
            if (this.Xijd.QOXG(1000.0 / this.cps.OnGi() - new Random().nextInt(4))) {
                if (new Random().nextInt(100) < this.hitchance.OnGi()) {
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)KillAura.oYbE));
                }
                OffHand.LInL();
                Wrapper.mc.player.resetCooldown();
                this.Xijd.BBwj();
            }
        }
    }
    
    public void YjnP() {
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
    }
    
    public float[] aADM(final Entity entity, final float n, final float n2) {
        float n3 = 0.0f;
        if (this.bodyPart.OnGi().equalsIgnoreCase("Head")) {
            n3 = 0.0f;
        }
        if (this.bodyPart.OnGi().equalsIgnoreCase("Chest")) {
            n3 = 0.5f;
        }
        if (this.bodyPart.OnGi().equalsIgnoreCase("Pants")) {
            n3 = 1.2f;
        }
        if (this.bodyPart.OnGi().equalsIgnoreCase("Boots")) {
            n3 = 1.6f;
        }
        final double n4 = entity.posX - Wrapper.mc.player.posX;
        final double n5 = entity.posZ - Wrapper.mc.player.posZ;
        double n6;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n6 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
            if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                throw null;
            }
        }
        else {
            n6 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        }
        final double n7 = MathHelper.sqrt(n4 * n4 + n5 * n5);
        final float n8 = (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f;
        final double n9 = n6;
        float n10;
        if (entity instanceof EntityPlayer) {
            n10 = n3;
            if (KillAura.\u200a\u2003\u2001\u2008\u200b\u2009\u200f\u200b\u2004) {
                throw null;
            }
        }
        else {
            n10 = 0.0f;
        }
        final float[] array = { qxzm(Wrapper.mc.player.rotationYaw, n8, n), qxzm(Wrapper.mc.player.rotationPitch, (float)(-(Math.atan2(n9 - n10, n7) * 180.0 / 3.141592653589793)), n2) };
        Wrapper.mc.player.rotationYawHead = array[0];
        if (this.aimbot.OnGi()) {
            Wrapper.mc.player.rotationYaw = array[0];
            Wrapper.mc.player.rotationPitch = array[1];
        }
        return array;
    }
    
    public static float qxzm(final float n, final float n2, final float n3) {
        float wrapDegrees = MathHelper.wrapDegrees(n2 - n);
        if (wrapDegrees > n3) {
            wrapDegrees = n3;
        }
        if (wrapDegrees < -n3) {
            wrapDegrees = -n3;
        }
        return n + wrapDegrees;
    }
}
