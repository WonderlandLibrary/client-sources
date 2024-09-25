package eze.modules.combat;

import eze.modules.*;
import eze.util.*;
import eze.settings.*;
import eze.modules.player.*;
import eze.events.*;
import eze.events.listeners.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.gui.*;

public class Killaura extends Module
{
    public Random random;
    public Timer timer;
    public NumberSetting aps;
    public NumberSetting HitChance;
    public NumberSetting range;
    public BooleanSetting noSwing;
    public BooleanSetting DisableOnDeath;
    public ModeSetting Rotation;
    public BooleanSetting OnlyPlayers;
    public BooleanSetting HitInvisible;
    public BooleanSetting Autoblock;
    public ModeSetting criticals;
    public static boolean HasTarget;
    public int hit;
    
    static {
        Killaura.HasTarget = false;
    }
    
    public Killaura() {
        super("Killaura", 19, Category.COMBAT);
        this.random = new Random();
        this.timer = new Timer();
        this.aps = new NumberSetting("APS", 10.0, 1.0, 20.0, 0.5);
        this.HitChance = new NumberSetting("Hit chance", 99.0, 1.0, 100.0, 2.0);
        this.range = new NumberSetting("Range", 4.0, 1.0, 6.0, 0.1);
        this.noSwing = new BooleanSetting("No Swing", false);
        this.DisableOnDeath = new BooleanSetting("Disable on death", true);
        this.Rotation = new ModeSetting("Rotation", "None", new String[] { "None", "Basic", "Redesky", "Basic Legit", "OldVerus" });
        this.OnlyPlayers = new BooleanSetting("Only Players", true);
        this.HitInvisible = new BooleanSetting("Hit invisibles", false);
        this.Autoblock = new BooleanSetting("Autoblock", true);
        this.criticals = new ModeSetting("Criticals", "None", new String[] { "None", "Redesky" });
        this.addSettings(this.aps, this.range, this.noSwing, this.Rotation, this.DisableOnDeath, this.OnlyPlayers, this.HitInvisible, this.Autoblock, this.HitChance);
    }
    
    @Override
    public void onEnable() {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.aps.setValue(10.5);
                this.range.setValue(4.0);
                if (!this.Rotation.is("Basic")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isMineplex) {
                this.aps.setValue(11.5);
                this.range.setValue(3.3);
                if (!this.Rotation.is("Basic")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isRedesky) {
                this.aps.setValue(12.5);
                this.range.setValue(4.8);
                if (!this.Rotation.is("Redesky")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isOldVerus) {
                this.aps.setValue(10.5);
                this.range.setValue(3.2);
                if (!this.Rotation.is("OldVerus")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    public void OnEvent(final Event e) {
        final boolean b = e instanceof EventRenderGUI;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
                this.aps.setValue(10.5);
                this.range.setValue(4.0);
                if (!this.Rotation.is("Basic")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isMineplex) {
                this.aps.setValue(11.5);
                this.range.setValue(3.3);
                if (!this.Rotation.is("Basic")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isRedesky) {
                this.aps.setValue(12.5);
                this.range.setValue(4.8);
                if (!this.Rotation.is("Redesky")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
            if (AutoSetting.isOldVerus) {
                this.aps.setValue(10.5);
                this.range.setValue(3.2);
                if (!this.Rotation.is("OldVerus")) {
                    this.Rotation.cycle();
                }
                this.OnlyPlayers.enabled = true;
                this.HitInvisible.enabled = false;
                this.Autoblock.enabled = true;
            }
        }
        if (e instanceof EventMotion && e.isPre()) {
            final EventMotion event = (EventMotion)e;
            List<EntityLivingBase> targets = (List<EntityLivingBase>)this.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            if (this.HitInvisible.isEnabled()) {
                if (AutoSetting.isMineplex) {
                    targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && entity != this.mc.thePlayer && entity.getHealth() > 0.0f && !entity.isDead).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
                }
                else {
                    targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && entity != this.mc.thePlayer && !entity.isDead).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
                }
            }
            else {
                if (AutoSetting.isMineplex) {
                    targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !entity.isInvisible() && !entity.isInvisibleToPlayer(this.mc.thePlayer) && entity != this.mc.thePlayer && entity.getHealth() > 0.0f && !entity.isDead).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
                }
                targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.range.getValue() && !entity.isInvisible() && !entity.isInvisibleToPlayer(this.mc.thePlayer) && entity != this.mc.thePlayer && !entity.isDead).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
            }
            targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(this.mc.thePlayer)));
            if (this.OnlyPlayers.isEnabled()) {
                targets = targets.stream().filter(EntityPlayer.class::isInstance).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
            }
            if (!targets.isEmpty()) {
                final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                final FontRenderer fr = this.mc.fontRendererObj;
                final EntityLivingBase target = targets.get(0);
                if (e instanceof EventRenderGUI) {
                    System.out.println("aaaaaaaaaaaaaaaaa");
                    fr.drawString(String.valueOf(target.getName()) + target.getHealth(), 300.0, 300.0, -1);
                }
                Killaura.HasTarget = true;
                if (this.Rotation.is("Basic Legit")) {
                    this.mc.thePlayer.rotationYaw = this.getRotations(target)[0] + this.random.nextInt(20) - 10.0f;
                    this.mc.thePlayer.rotationPitch = this.getRotations(target)[1] + this.random.nextInt(20) - 10.0f;
                }
                if (this.Rotation.is("Basic")) {
                    event.setYaw(this.getRotations(target)[0] + this.random.nextInt(20) - 10.0f);
                    event.setPitch(this.getRotations(target)[1] + this.random.nextInt(20) - 10.0f);
                }
                if (this.Rotation.is("Redesky")) {
                    this.mc.thePlayer.rotationYaw = this.getRotations(target)[0] + this.random.nextInt(20) - 10.0f;
                    this.mc.thePlayer.rotationPitch = this.getRotations(target)[1] + this.random.nextInt(20) - 10.0f;
                }
                if (this.Rotation.is("OldVerus")) {
                    event.setYaw(this.getRotations(target)[0] + this.random.nextInt(26) - 13.0f);
                    event.setPitch(this.getRotations(target)[1] + this.random.nextInt(26) - 13.0f);
                    if (!targets.isEmpty()) {
                        this.mc.thePlayer.setSprinting(false);
                    }
                }
                if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0f)) {
                    this.toggled = false;
                }
                if (this.timer.hasTimeElapsed((long)((long)(1000.0 / this.aps.getValue()) / this.mc.timer.timerSpeed), true)) {
                    if (this.noSwing.isEnabled()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                    else {
                        this.mc.thePlayer.swingItem();
                        this.mc.thePlayer.clearItemInUse();
                    }
                    if (this.criticals.is("Redesky") && this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.0031311231111, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.onGround = false;
                    }
                    this.hit = this.random.nextInt(100) - 1;
                    if (this.hit <= this.HitChance.getValue()) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    }
                }
                if (this.Autoblock.isEnabled()) {
                    this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), 20);
                }
            }
            else {
                Killaura.HasTarget = true;
            }
        }
    }
    
    public float[] getRotations(final Entity e) {
        final double deltaX = e.posX + (e.posX - e.lastTickPosX) - this.mc.thePlayer.posX;
        final double deltaY = e.posY - 3.5 + e.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
        final double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - this.mc.thePlayer.posZ;
        final double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        final float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[] { yaw, pitch };
    }
}
