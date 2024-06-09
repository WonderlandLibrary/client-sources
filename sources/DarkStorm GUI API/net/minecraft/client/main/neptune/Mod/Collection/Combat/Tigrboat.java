package net.minecraft.client.main.neptune.Mod.Collection.Combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventTick;
import net.minecraft.client.main.neptune.Events.EventUpdate;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.NumValue;
import net.minecraft.client.main.neptune.Utils.CombatUtils;
import net.minecraft.client.main.neptune.Utils.EntityUtils;
import net.minecraft.client.main.neptune.Utils.RotationUtils;
import net.minecraft.client.main.neptune.Utils.TimeMeme;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;

public class Tigrboat extends Mod {
    
    int delay;
	private NumValue speed = new NumValue("FF Delay", 5, 0, 15, ValueDisplay.INTEGER);
	public static NumValue fov = new NumValue("FF FOV", 60, 0, 360, ValueDisplay.INTEGER);
	public static NumValue range = new NumValue("FF Range", 4, 0, 7, ValueDisplay.INTEGER);
	private TimeMeme timer;
	int random;
	
	public Tigrboat() {
		super("Forcefield", Category.HACKS);
		this.timer = new TimeMeme();
		this.setBind(Keyboard.KEY_R);
	}
	
	@Override
	public void onEnable() {
		Memeager.register(this);
		this.timer.reset();
	}

	@Override
	public void onDisable() {
		Memeager.unregister(this);
	}

	@Memetarget
	public void onTick(EventTick event) {
		this.setRenderName(String.format("%s", "Forcefield"));
	}
	
	@Memetarget
	public void onUpdate(EventUpdate event) {
        final List list = mc.theWorld.playerEntities;
        final Tigrboat this$0 = Tigrboat.this;
        if(mc.gameSettings.keyBindAttack.pressed) {
	        ++this$0.delay;
	        for (int i = 0; i < list.size(); ++i) {
	            final MovingObjectPosition omo = mc.objectMouseOver;
	            if (omo == null || omo.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
	                final EntityPlayer entityPlayer = (EntityPlayer) list.get(i);
	                if (entityPlayer != mc.thePlayer) {
	                    final float f = mc.thePlayer.getDistanceToEntity(entityPlayer);
	                    if (f < this.range.getValue() && mc.thePlayer.canEntityBeSeen(entityPlayer) && this.delay >= this.speed.getValue() + this.random) {
	                        if (!Neptune.getWinter().friendUtils.isFriend(entityPlayer.getGameProfile().getName()) && !entityPlayer.isInvisible()) {
	                            mc.thePlayer.swingItem();
	                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entityPlayer, C02PacketUseEntity.Action.ATTACK));
	                            mc.thePlayer.setSprinting(false);
					    		float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(),
					    				mc.thePlayer.getCreatureAttribute());
					    		boolean vanillaCrit = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f
					    				&& !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder()
					    				&& !Minecraft.getMinecraft().thePlayer.isInWater()
					    				&& !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness)
					    				&& Minecraft.getMinecraft().thePlayer.ridingEntity == null;
			            		if (vanillaCrit) {
			            			Minecraft.getMinecraft().thePlayer.onCriticalHit(entityPlayer);
			            		}
			            		if (sharpLevel > 0.0f) {
			            			Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entityPlayer);
			            		}
	                            this.delay = 0;
	                        }
	                    }
	                }
	            }
                else if(this.delay >= this.speed.getValue() + this.random) {
                	mc.thePlayer.swingItem();
                	this.delay = 0;
                }
	        }
		}
        if(this.timer.hasPassed(150)) {
        	this.random = 1;
        }
        if(this.timer.hasPassed(550)) {
        	this.random = 2;
        }
        if(this.timer.hasPassed(850)) {
        	this.random = -2;
        }
        if(this.timer.hasPassed(1350)) {
        	this.random = 4;
        	this.timer.reset();
        }
	}
}
