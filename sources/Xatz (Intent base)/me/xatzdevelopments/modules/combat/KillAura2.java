package me.xatzdevelopments.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.EventType;
import me.xatzdevelopments.events.listeners.EventAttack;
import me.xatzdevelopments.events.listeners.EventModeChanged;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventTick;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.ui.TargetHUDMaker;
import me.xatzdevelopments.util.MoveUtils;
import me.xatzdevelopments.util.PlayerUtils;
import me.xatzdevelopments.util.RandomUtils;
import me.xatzdevelopments.util.Timer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class KillAura2 extends Module {
    public Timer timer = new Timer();
    float severYaw = 0.0f;
    float severPitch = 0.0f;
    public static float nextRotationYaw;
    public static float nextRotationPitch;
    public static boolean isWithInRange;
    float lastRotationYaw;
    float lastRotationPitch;
    float randomness;
    int BlockedTicks = 0;
    float randomYaw = 0.0f;
    float randomPitch = 0.0f;
    public boolean isattacking = false;
	public static EntityLivingBase target;
	public static String health = "";
	public static double maxHealth = 0;
	public static String name = "";
	public static String distance = "";
	public static double health2 = 0;
	public int healthcolor = -1;
	EntityLivingBase idk = null;
    public NumberSetting Range = new NumberSetting("Range", 3.0, 3.0, 7.0, 0.1);
    public NumberSetting AvgAPS = new NumberSetting("AvgAPS", 14.0, 1.0, 20.0, 1.0);
    public BooleanSetting SmoothRot = new BooleanSetting("SmoothRot", false);
    public BooleanSetting TargetDead = new BooleanSetting("Target Dead", true);
    public BooleanSetting AllowSprint = new BooleanSetting("AllowSprint", true);
    public ModeSetting Rots = new ModeSetting("Rots", "KeepRot","KeepRot", "Snap", "None");
    public ModeSetting RandomRotation = new ModeSetting("Random", "None", "None", "Always", "OnMove");
    public ModeSetting AutoBlock = new ModeSetting("AutoBlock", "None", "None", "Legit", "Keep", "Full", "InAir");
    public BooleanSetting Raycast = new BooleanSetting("Raycast", true);
    public BooleanSetting RotationStrafe = new BooleanSetting("Rotation Strafe", false);
    public BooleanSetting Silent = new BooleanSetting("Silent", true);
    public BooleanSetting FixGDC = new BooleanSetting("FixGDC", false);
    public BooleanSetting DeadZone = new BooleanSetting("Dead Zone", false);
    public BooleanSetting Players = new BooleanSetting("Players", true);
    public BooleanSetting NonPlayers = new BooleanSetting("NonPlayers", true);
    public BooleanSetting IgnoreTeammates = new BooleanSetting("Ignore Teams", false);
    List<EntityLivingBase> targets = null;
    
    public KillAura2() {
        super("KillAura", Keyboard.KEY_NONE, Category.COMBAT, "Kills entites for you.");
        this.addSettings(this.Range, this.AllowSprint, this.SmoothRot, this.TargetDead, this.AvgAPS, this.Rots, this.RandomRotation, this.AutoBlock, this.Players, this.NonPlayers, this.IgnoreTeammates, this.Raycast, this.Silent, this.RotationStrafe, this.FixGDC, this.DeadZone);
        this.addonText = "R:" + this.Range.getValue() + " A:" + this.AvgAPS.getValue();
    }
    
    @Override
    public void onEnable() {
    	this.addonText = "R:" + this.Range.getValue() + " A:" + this.AvgAPS.getValue();
        final float rotationYaw = this.mc.thePlayer.rotationYaw;
        this.lastRotationYaw = rotationYaw;
        this.severYaw = rotationYaw;
        this.severPitch = this.mc.thePlayer.rotationPitch;
        this.BlockedTicks = 0;
    }
    
    @Override
    public void onDisable() {
        this.isWithInRange = false;
    }
    
    @Override
    public void onEvent(final Event e) {
    	if(e instanceof EventModeChanged) {
    		this.addonText = "R:" + this.Range.getValue() + " A:" + this.AvgAPS.getValue();
    	}
        if (this.mc.currentScreen != null || Xatz.getModuleByName("Scaffold").isEnabled()) {
            return;
        }
        if (e instanceof EventTick && e.isPre()) {
            if (this.target == null) {
                return;
            }
            this.lastRotationYaw = this.nextRotationYaw;
            this.lastRotationPitch = this.nextRotationPitch;
            this.nextRotationYaw = this.getRotations(this.target)[0];
            this.nextRotationPitch = this.getRotations(this.target)[1];
            if (this.DeadZone.isEnabled()) {
                if (Math.abs(this.nextRotationYaw - this.lastRotationYaw) < 7.0 + Math.random() * 3.0) {
                    this.nextRotationYaw = this.lastRotationYaw;
                }
                if (Math.abs(this.nextRotationPitch - this.lastRotationPitch) < 20.0f) {
                    this.nextRotationPitch = this.lastRotationPitch;
                }
            }
            if (this.RandomRotation.getMode() == "Always" || (this.RandomRotation.getMode() == "OnMove" && MoveUtils.isMoving())) {
                this.randomYaw += this.Random(8);
                this.randomPitch += this.Random(8);
                if (this.mc.thePlayer.ticksExisted % 4 == 0) {
                    this.randomYaw = this.Random(1);
                    this.randomPitch = this.Random(1);
                }
                this.nextRotationYaw += this.randomYaw;
                this.nextRotationPitch += this.randomPitch;
            }
            if (this.SmoothRot.isEnabled()) {
                this.nextRotationYaw = (this.lastRotationYaw * 2.0f + this.nextRotationYaw) / 3.0f;
                this.nextRotationPitch = (this.lastRotationPitch * 2.0f + this.nextRotationPitch) / 3.0f;
            }
            if (this.FixGDC.isEnabled()) {
                final float f = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                final float gcd = f * f * f * 1.2f;
                this.nextRotationYaw -= this.nextRotationYaw % gcd;
                this.nextRotationPitch -= this.nextRotationPitch % gcd;
            }
        }
        if (e instanceof EventMotion) {
            ++this.BlockedTicks;
            if (this.mc.thePlayer.ticksExisted == 1) {
                this.toggled = false;
            }
            if (e.isPre()) {
                final EventMotion event = (EventMotion)e;
                this.targets = (List<EntityLivingBase>)this.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
                this.targets = this.targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < this.Range.getValue() && entity != this.mc.thePlayer && !entity.isInvisible()).collect(Collectors.toList());
                this.isWithInRange = false;
                this.targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(this.mc.thePlayer)));
				List<EntityLivingBase> target2 = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
				target2 = target2.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 8 && entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());
				target2.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
                if (!this.targets.isEmpty()) {
                    this.target = this.targets.get(0);
                    idk = target;
                    if ((this.target.getHealth() <= 0.0f && this.TargetDead.isEnabled()) || this.target.getHealth() > 0.0f) {
                        if (this.Raycast.isEnabled() && !this.raytrace(this.target)) {
                            return;
                        }
                        if (!this.canAttack(this.target)) {
                            return;
                        }
                        if (!this.AllowSprint.isEnabled()) {
                            this.mc.thePlayer.setSprinting(false);
                        }
                        this.isWithInRange = true;
                        if (this.Rots.getMode() == "KeepRot" && this.Rots.getMode() != "None" && this.Rots.getMode() != "CubeCraft") {
                            if (this.Silent.isEnabled()) {
                                event.setYaw(this.nextRotationYaw);
                                event.setPitch(this.nextRotationPitch);
                            }
                            else {
                                this.mc.thePlayer.rotationYaw = this.nextRotationYaw;
                                this.mc.thePlayer.rotationPitch = this.nextRotationPitch;
                            }
                        }
                        if (this.AutoBlock.getMode() == "Keep" && PlayerUtils.isHoldingSword()) {
                            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
                        }
                        this.severYaw = this.nextRotationYaw;
                        this.severPitch = this.nextRotationPitch;
                        this.mc.thePlayer.renderYawOffset = this.nextRotationYaw;
                        this.mc.thePlayer.rotationYawHead = this.nextRotationYaw;
                        if (timer.hasTimeElapsed((long)(1000.0 / this.AvgAPS.getValue() + RandomUtils.RandomBetween(-50.0, 15.0)), true) && !this.mc.thePlayer.isUsingItem()) {
                            if (this.Rots.getMode() == "Snap") {
                                event.setYaw(this.nextRotationYaw);
                                event.setPitch(this.nextRotationPitch);
                            }
                            if (PlayerUtils.isHoldingSword() && (this.AutoBlock.getMode() == "Keep" || this.AutoBlock.getMode() == "Legit")) {
                                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            final EventAttack e2 = new EventAttack(this.target);
                            e2.setType(EventType.PRE);
                            Xatz.onEvent(e2);
                            this.isattacking = true;
                            this.mc.thePlayer.swingItem();
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                            if (PlayerUtils.isHoldingSword() && (this.AutoBlock.getMode() == "Keep" || (this.BlockedTicks == 2 && this.AutoBlock.getMode() == "Legit"))) {
                                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
                            }
                            if (this.mc.thePlayer.fallDistance > 0.0f) {
                                this.mc.thePlayer.onCriticalHit(this.target);
                            }
                        }
                        else {
                            this.BlockedTicks = 0;
                            if (this.AutoBlock.getMode() == "InAir" && PlayerUtils.isHoldingSword() && !this.mc.thePlayer.onGround) {
                                this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
                            }
                        }
                        if (this.AutoBlock.getMode() == "Full" || this.AutoBlock.getMode() == "Legit") {
                            this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), 71626);
                        }
                    }
                }
                else {
                    this.severYaw = this.mc.thePlayer.rotationYaw;
                    this.severPitch = this.mc.thePlayer.rotationPitch;
                }
				if(this.isattacking) {
			        if (this.target.getHealth() > 8 && this.target.getHealth() < 10) {
			            healthcolor = 0xff0ac713;
			        }
			        else if (this.target.getHealth() > 5 && this.target.getHealth() < 8) {
			            healthcolor = 0xff85c70a;
			        }
			        else if (this.target.getHealth() > 3 && this.target.getHealth() < 5) {
			            healthcolor = 0xffc7980a;
			        }
			        else {
			            healthcolor = 0xffc7590a;
			        }
		        }else if(!this.isattacking) {
		        	healthcolor = -1;


		        }
		    }
			if(!targets.isEmpty()) {
				this.health = "" + String.valueOf((float)((int)target.getHealth()) / 2.0F);
				this.name = "" + target.getName();
				this.health2 = target.getHealth();
				this.distance = "Distance: " + (double)Math.round(mc.thePlayer.getDistanceToEntity(target) * 100) / 100;
			} else {
				this.target = null;
				this.health = "0.0";
				this.name = "Unknown";
				this.distance = "Distance: 0.0";
				this.target = null;
				this.maxHealth =0;
				this.health2 = 0.0;
				this.isattacking = false;
				healthcolor = -1;
				idk = null;
			}
				TargetHUDMaker thud = new TargetHUDMaker();
				thud.animationStopwatch.reset();
				//mc.fontRendererObj.drawString(KillAura.health, thud.x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, mc.displayHeight / 4 + 15 + 16.0F, -1);
		        mc.fontRendererObj.drawString(KillAura.name, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 2.0F, -1);
            }
        
		if(e instanceof EventRenderGUI) {
			if(Xatz.getModuleByName("TargetHUD2").isEnabled()) {
				TargetHUDMaker thud = new TargetHUDMaker();
				double healthBarWidth = health2 * 3;
				float width = 140.0F;
	            float height = 40.0F;
	            float xOffset = 40.0F;
	            float x = mc.displayWidth / 2.0F - 70.0F;
	            float y = mc.displayHeight / 2.0F + 80.0F;
	            double hpPercentage = (health2 / maxHealth);
	            double hpPercentage2;
	            hpPercentage2 = MathHelper.clamp_double(hpPercentage, 0.0D, 1.0D);
	            double hpWidth = 92.0D * hpPercentage2;
	            //int healthColor = ColorUtils.getHealthColor((float)this.health2, (float)this.maxHealth).getRGB();
	            String healthStr = String.valueOf((float)((int)this.maxHealth) / 2.0F);
			/*TargetHUDMaker thud = new TargetHUDMaker();
			final Color COLOR = new Color(0, 0, 0, 180);
			Gui.drawRect((double)thud.x, (double)mc.displayHeight / 4 + 15, (double)(thud.x + 140.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F), COLOR.getRGB());
	        Gui.drawRect((double)(thud.x + 40.0F), (double)(mc.displayHeight / 4 + 15 + 45.0F), (double)(thud.x + 40.0F) + thud.healthBarWidth, (double)(mc.displayHeight / 4 + 15 + 25.0F), thud.healthColor);
			//mc.fontRendererObj.drawString(KillAura.health, thud.x2 - 1f + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, mc.displayHeight / 4 + 15 + 16.0F, -1);
			//Gui.drawRect((double)thud.x, (double)mc.displayHeight / 4 + 15, (double)(thud.x + 140.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F), COLOR.getRGB());
	        Gui.drawRect((double)(thud.x + 40.0F), (double)(mc.displayHeight / 4 + 15 + 45.0F), (double)(thud.x + 40.0F + KillAura.health2) + thud.healthBarWidth, (double)(mc.displayHeight / 4 + 15 - 40), -1);
	        mc.fontRendererObj.drawString(KillAura.name, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 2.0F, this.healthcolor);
	        mc.fontRendererObj.drawString(KillAura.health, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 16.0F, this.healthcolor);
	        mc.fontRendererObj.drawString(KillAura.distance, thud.x2 + 40.0F, mc.displayHeight / 4 + 15 + 30.0F, this.healthcolor);*/

	            	//GuiInventory.drawEntityOnScreen((int)(thud.x + 13.333333F), (int)(thud.y + 30.0F), 20, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer);
	            //}
	            if(this.isattacking) {
	            	Gui.drawRect((double)mc.displayWidth / 4 - 95 - 5, (double)mc.displayHeight / 4 + 15, (double)(mc.displayWidth / 4 - 55 + 125.0F), (double)(mc.displayHeight / 4 + 15 + 40.0F + 10), 0xEE111111);
		            Gui.drawRect((double)(mc.displayWidth / 4 - 55 - 5), (double)(mc.displayHeight / 4 + 15 + 15.0F), (double)(mc.displayWidth / 4 - 55 - 5.0F) + healthBarWidth, (double)(mc.displayHeight / 4 + 15 + 27.0F), healthcolor);
		            mc.fontRendererObj.drawStringWithShadow(this.health, /*thud.x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(this.health) / 2.0F*/ mc.displayWidth / 4 - 55 + 50, mc.displayHeight / 4 + 15 + 16.0F, -1);
		            mc.fontRendererObj.drawStringWithShadow(this.name, /*thud.x + 40.0F*/ mc.displayWidth / 4 - 55, mc.displayHeight / 4 + 15 + 2.0F, -1);
		            if(idk != null) {
		            GuiInventory.drawEntityOnScreen((int)(mc.displayWidth / 4 - 76), (int)(mc.displayHeight / 4 + 60.0F), 20, idk.rotationYaw, idk.rotationPitch, idk);
		            }
	            //GuiInventory.drawEntityOnScreen((int)(x + 13.333333F), (int)(y + 40.0F), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
	            }
				//mc.fontRendererObj.drawString(this.name, 165, 50, -1);
				//mc.fontRendererObj.drawString(this.health, 165, 40, -1);
				//TargetHUDMaker hud = new TargetHUDMaker();
				//hud.makeTargetHUD();
			}
			if(Xatz.getModuleByName("SuperHeroFX").isEnabled()) {
				if(this.isattacking && idk.hurtTime > 0) {
					Xatz.fxManager.addTextFx(idk, "test");
				}
				if(!this.isattacking) {
					Xatz.fxManager.resetFxList();
				}
			}
		}
    }
    
    public float[] getRotations(final Entity target) {
        final double var4 = target.posX - this.mc.thePlayer.posX;
        final double var5 = target.posZ - this.mc.thePlayer.posZ;
        final double var6 = target.posY + 0.3 + target.getEyeHeight() / 1.3 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        final double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float yaw = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793));
        final float interpolatedAngle = MathHelper.cos((float)(var4 - 1.0 + (var5 - (var6 - 1.0)) * this.mc.timer.renderPartialTicks));
        return new float[] { this.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw), this.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch) };
    }
    
    public boolean raytrace(final EntityLivingBase entity) {
        final EntitySnowball entitySnowball = new EntitySnowball(this.mc.theWorld);
        entitySnowball.posX = entity.posX;
        entitySnowball.posY = entity.posY + entity.getEyeHeight() / 2.0f;
        entitySnowball.posZ = entity.posZ;
        return this.mc.thePlayer.canEntityBeSeen(entitySnowball);
    }
    
    private boolean canAttack(final EntityLivingBase player) {
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !this.Players.isEnabled()) {
                return false;
            }
            if (player instanceof EntityAnimal && !this.NonPlayers.isEnabled()) {
                return false;
            }
            if (player instanceof EntityMob && !this.NonPlayers.isEnabled()) {
                return false;
            }
            if (player instanceof EntityVillager && !this.NonPlayers.isEnabled()) {
                return false;
            }
        }
        return !this.mc.thePlayer.isOnSameTeam(player) || !this.IgnoreTeammates.isEnabled();
    }
    
    public float Random(final int multiplyer) {
        return (float)((Math.random() - 0.5) * multiplyer);
    }
    
    public float getMoveYaw(final float yaw) {
        float moveYaw = yaw;
        if (this.mc.thePlayer.moveForward != 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {
            moveYaw += ((this.mc.thePlayer.moveForward > 0.0f) ? 0 : 180);
        }
        else if (this.mc.thePlayer.moveForward != 0.0f && this.mc.thePlayer.moveStrafing != 0.0f) {
            if (this.mc.thePlayer.moveForward > 0.0f) {
                moveYaw += ((this.mc.thePlayer.moveStrafing > 0.0f) ? -45 : 45);
            }
            else {
                moveYaw -= ((this.mc.thePlayer.moveStrafing > 0.0f) ? -45 : 45);
            }
            moveYaw += ((this.mc.thePlayer.moveForward > 0.0f) ? 0 : 180);
        }
        else if (this.mc.thePlayer.moveStrafing != 0.0f && this.mc.thePlayer.moveForward == 0.0f) {
            moveYaw += ((this.mc.thePlayer.moveStrafing > 0.0f) ? -90 : 90);
        }
        return moveYaw;
    }
    
    private void drawCircle(final Entity entity, final float partialTicks, final double rad) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(3);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - this.mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - this.mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - this.mc.getRenderManager().viewerPosZ;
        final float r = 0.003921569f;
        final float g = 0.003921569f;
        final float b = 0.003921569f;
        final double pix2 = 6.283185307179586;
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor3f((float)(i * 100), (float)(i * 100), (float)(i * 100));
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586 / 45.0), y, z + rad * Math.sin(i * 6.283185307179586 / 45.0));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
}
