package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.handlers.CmdHandler;
import com.klintos.twelve.handlers.ModHandler;
import com.klintos.twelve.handlers.friend.FriendHandler;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.mod.ModCategory;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.mod.events.EventPostAttack;
import com.klintos.twelve.mod.events.EventPostUpdate;
import com.klintos.twelve.mod.events.EventPreAttack;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.value.ValueBoolean;
import com.klintos.twelve.mod.value.ValueDouble;
import com.klintos.twelve.utils.PlayerUtils;
import com.klintos.twelve.utils.TimerUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Aura
extends Mod {
    private EntityLivingBase target = null;
    private TimerUtil timer = new TimerUtil();
    public ValueDouble hitRange = new ValueDouble("Range", 3.9, 2.0, 6.0, 1);
    public ValueDouble hitSpeed;
    public ValueDouble smoothSpeed;
    public ValueBoolean mobs;
    public ValueBoolean players;
    public ValueBoolean legit;
    private float prePitch;
    private float preYaw;
    public static String exclude;
    public static boolean autoblock;
    Random rand = new Random();

    public Aura() {
        super("Aura", 37, ModCategory.COMBAT);
        this.addValue(this.hitRange);
        this.hitSpeed = new ValueDouble("Speed", 6.0, 2.0, 12.0, 1);
        this.addValue(this.hitSpeed);
        this.smoothSpeed = new ValueDouble("Aim Speed", 2.0, 1.0, 5.0, 1);
        this.addValue(this.smoothSpeed);
        this.legit = new ValueBoolean("Legit", false);
        this.addValue(this.legit);
        this.players = new ValueBoolean("Players", true);
        this.addValue(this.players);
        this.mobs = new ValueBoolean("Mobs", true);
        this.addValue(this.mobs);
        Twelve.getInstance().getCmdHandler().addCmd(new Cmd("aura", "Change aura settings.", "aura <APS/Range/AutoBlock/Legit/Exclude>"){

            @Override
            public void runCmd(String msg, String[] args) {
                try {
                    if (args[1].equalsIgnoreCase("block") || args[1].equalsIgnoreCase("autoblock") || args[1].equalsIgnoreCase("ab")) {
                        Aura.autoblock = !Aura.autoblock;
                        this.addMessage("AutoBlock now toggled \u00a7c" + (Aura.autoblock ? "on" : "off") + "\u00a7f.");
                    } else if (args[1].equalsIgnoreCase("legit")) {
                        Aura.this.legit.setValue(!Aura.this.legit.getValue());
                        this.addMessage("LegitAura now toggled \u00a7c" + (Aura.this.legit.getValue() ? "on" : "off") + "\u00a7f.");
                    } else if (args[1].equalsIgnoreCase("aps") || args[1].equalsIgnoreCase("speed")) {
                        double aps = Float.parseFloat(args[2]);
                        if (aps >= 4.0 && aps <= 20.0) {
                            Aura.this.hitSpeed.setValue(aps);
                            this.addMessage("Aura APS now changed to \u00a7c" + aps + "\u00a7f.");
                        } else {
                            this.addMessage("Aura APS cannot be lower than \u00a7c4\u00a7f or higher than \u00a7c20\u00a7f.");
                        }
                    } else if (args[1].equalsIgnoreCase("range")) {
                        double range = Float.parseFloat(args[2]);
                        if (range >= 1.0 && range <= 8.0) {
                            Aura.this.hitRange.setValue(range);
                            this.addMessage("Aura range now changed to \u00a7c" + range + "\u00a7f blocks.");
                        } else {
                            this.addMessage("Aura range cannot be less than \u00a7c1\u00a7f or more than \u00a7c8\u00a7f.");
                        }
                    } else if (args[1].equalsIgnoreCase("exclude")) {
                        if (args[2].length() > 1) {
                            this.addMessage("Exclusions are colour codes in the name tags, 0-9, a-f.");
                        } else {
                            Aura.exclude = args[2].replace(" ", "");
                            this.addMessage("Aura now excluding players with \u00a7" + Aura.exclude + "this \u00a7fcolour nametag.");
                        }
                    } else {
                        this.runHelp();
                    }
                }
                catch (Exception e) {
                    this.runHelp();
                }
            }
        });
    }

    @EventTarget
    public void onPreUpdate(EventPreUpdate event) {
        this.prePitch = Aura.mc.thePlayer.rotationPitch;
        this.preYaw = Aura.mc.thePlayer.rotationYaw;
        this.legit.setValue(Twelve.getInstance().ghost ? true : this.legit.getValue());
        ArrayList<EntityLivingBase> attackableWithinFOV = this.getAttackableWithinFOV(45);
        EntityLivingBase entityLivingBase = this.target = this.legit.getValue() ? this.getClosestEntityToCursor(45.0f) : attackableWithinFOV.get(attackableWithinFOV.size() / 2);
        if (this.target != null) {
            double speed = Math.abs(this.smoothSpeed.getValue() - this.smoothSpeed.getMax());
            speed = speed < 1.0 ? 1.0 : speed;
            Aura.mc.thePlayer.rotationYaw = (float)((double)Aura.mc.thePlayer.rotationYaw + (double)Aura.getYawChangeToEntity(this.target) / speed);
            Aura.mc.thePlayer.rotationPitch = (float)((double)Aura.mc.thePlayer.rotationPitch + (double)Aura.getPitchChangeToEntity(this.target) / speed);
            if (autoblock && (double)Aura.mc.thePlayer.getDistanceToEntity(this.target) <= this.hitRange.getValue() && Aura.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                Aura.mc.thePlayer.getCurrentEquippedItem().useItemRightClick(Aura.mc.theWorld, Aura.mc.thePlayer);
            }
        }
    }

    @EventTarget
    public void onPostUpdate(EventPostUpdate event) {
        if (this.target != null && this.shouldAttack(this.target) && this.timer.delay(1000.0 / this.genRandom(this.hitSpeed.getValue() - 2.0, this.hitSpeed.getValue() + 2.0)) && this.isFacingEntity(this.legit.getValue() ? 20 : 45)) {
            if (this.legit.getValue()) {
                this.attackEntity(this.target);
            } else {
                for (EntityLivingBase entity : this.getAttackableWithinFOV(45)) {
                    this.attackEntity(entity);
                }
            }
        }
        if (!this.legit.getValue()) {
            Aura.mc.thePlayer.rotationPitch = this.prePitch;
            Aura.mc.thePlayer.rotationYaw = this.preYaw;
        }
    }

    @EventTarget
    public void onPreAttack(EventPreAttack event) {
        if (Aura.mc.thePlayer.isBlocking() && autoblock && !Twelve.getInstance().getModHandler().getMod("NoSlowdown").getEnabled()) {
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @EventTarget
    public void onPostAttack(EventPostAttack event) {
        if (Aura.mc.thePlayer.isBlocking() && autoblock && !Twelve.getInstance().getModHandler().getMod("NoSlowdown").getEnabled()) {
            Aura.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, -1, Aura.mc.thePlayer.inventory.getCurrentItem(), -1.0f, -1.0f, -1.0f));
        }
    }

    private EntityLivingBase getClosestEntity() {
        EntityLivingBase closest = null;
        for (Object o : Aura.mc.theWorld.loadedEntityList) {
            EntityLivingBase entity;
            if (!(o instanceof EntityLivingBase) || !this.shouldAttack(entity = (EntityLivingBase)o)) continue;
            if (closest == null) {
                closest = entity;
            }
            if (Aura.mc.thePlayer.getDistanceToEntity(entity) >= Aura.mc.thePlayer.getDistanceToEntity(closest)) continue;
            closest = entity;
        }
        return closest;
    }

    private ArrayList<EntityLivingBase> getAttackableWithinFOV(int fov) {
        ArrayList<EntityLivingBase> attackable = new ArrayList<EntityLivingBase>();
        float distance = fov;
        for (Object ob : Aura.mc.theWorld.loadedEntityList) {
        	if (ob instanceof Entity) {
        		Entity entity = (Entity) ob;
	            float curDistance;
	            EntityLivingBase living;
	            if (!(entity instanceof EntityLivingBase) || !this.shouldAttack(living = (EntityLivingBase)entity)) continue;
	            float yaw = Aura.getYawChangeToEntity(living);
	            float pitch = Aura.getPitchChangeToEntity(living);
	            if (yaw > (float)fov || pitch > (float)fov || (curDistance = (yaw + pitch) / 2.0f) > distance) continue;
	            distance = curDistance;
	            attackable.add(living);
        	}
        }
        return attackable;
    }

    private void attackEntity(Entity entity) {
        Aura.mc.gameSettings.keyBindUseItem.pressed = false;
        boolean wasSprinting = Aura.mc.thePlayer.isSprinting();
        Aura.mc.thePlayer.inventory.currentItem = PlayerUtils.getBestWeapon(entity);
        Aura.mc.thePlayer.setSprinting(false);
        Aura.mc.thePlayer.swingItem();
        Aura.mc.playerController.attackEntity(Aura.mc.thePlayer, entity);
        this.target = null;
    }

    private boolean shouldAttack(Entity e) {
        if (e.getEntityId() != -1 && this.isEntityCorrectType(e) && !(e instanceof EntityPlayerSP) && ((EntityLivingBase)e).deathTime <= 0 && Aura.mc.thePlayer.canEntityBeSeen(e) && (double)Aura.mc.thePlayer.getDistanceToEntity(e) <= this.hitRange.getValue() && !e.getDisplayName().getFormattedText().startsWith("\u00a7" + exclude) && !Twelve.getInstance().getFriendHandler().isFriend(e.getName())) {
            return true;
        }
        return false;
    }

    private boolean isEntityCorrectType(Entity e) {
        if (this.players.getValue() && this.mobs.getValue()) {
            return e instanceof EntityLivingBase;
        }
        if (this.mobs.getValue()) {
            if (!(e instanceof EntityMob || e instanceof EntityAnimal || e instanceof EntitySquid)) {
                return false;
            }
            return true;
        }
        if (this.players.getValue()) {
            return e instanceof EntityPlayer;
        }
        return false;
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - Aura.mc.thePlayer.posX;
        double deltaZ = entity.posZ - Aura.mc.thePlayer.posZ;
        double deltaY = entity.posY - 2.2 + (double)entity.getEyeHeight() - Aura.mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = - Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return - MathHelper.wrapAngleTo180_float(Aura.mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - Aura.mc.thePlayer.posX;
        double deltaZ = entity.posZ - Aura.mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(- Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(- Aura.mc.thePlayer.rotationYaw - (float)yawToEntity);
    }

    public double genRandom(double d, double e) {
        return d + Math.random() * (e - d + 1.0);
    }

    public EntityLivingBase getClosestEntityToCursor(float angle) {
        float distance = angle;
        EntityLivingBase tempEntity = null;
        for (Object ob : Aura.mc.theWorld.loadedEntityList) {
        	if (ob instanceof Entity) {
        		Entity entity = (Entity) ob;
	            float curDistance;
	            EntityLivingBase living;
	            if (!(entity instanceof EntityLivingBase) || !this.shouldAttack(living = (EntityLivingBase)entity)) continue;
	            float yaw = Aura.getYawChangeToEntity(living);
	            float pitch = Aura.getPitchChangeToEntity(living);
	            if (yaw > angle || pitch > angle || (curDistance = (yaw + pitch) / 2.0f) > distance) continue;
	            distance = curDistance;
	            tempEntity = living;
        	}
        }
        return tempEntity;
    }

    public boolean isFacingEntity(int angle) {
        double x2 = this.target.posX - Aura.mc.thePlayer.posX;
        double z2 = this.target.posZ - Aura.mc.thePlayer.posZ;
        float f = (float)(Math.atan2(z2, x2) * 180.0 / 3.141592653589793) - 90.0f;
        if ((f = this.getDistanceBetweenAngles(f, Aura.mc.thePlayer.rotationYaw % 360.0f)) <= (float)angle) {
            return true;
        }
        return false;
    }

    public float getDistanceBetweenAngles(float ang1, float ang2) {
        return Math.abs(((ang1 - ang2 + 180.0f) % 360.0f + 360.0f) % 360.0f - 180.0f);
    }

}

