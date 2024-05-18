package com.darkcart.xdolf.mods.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;

public class announcer extends Module
{
	
	int timer = 0;
	  int timerhit = 0;
	  int jumptimer = 0;
	  int htimer = 0;
	  Float prevh = Float.valueOf(0.0F);
	  int antimer = 0;
	  Float fuzzy = Float.valueOf(0.0F);
	
	public announcer()
	{
		super("announcer", "Broken", "Announces in chat what you are doing.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	Timer walked;
	Minecraft mc = Minecraft.getMinecraft();
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(this.isEnabled()){
			
		Float health = Float.valueOf(mc.player.getHealth() / 2.0F);
	    
	    String hitem = mc.player.getHeldItemMainhand().getUnlocalizedName();
	    EntityLivingBase lastattacker = mc.player.getLastAttacker();
	      this.timer += 1;
	      this.timerhit += 1;
	      this.jumptimer += 1;
	      this.antimer += 1;
	      if ((Mouse.isButtonDown(Mouse.getEventButton())) && 
	        (this.timerhit >= 150))
	      {
	        mc.player.sendChatMessage(">I just used attack with a " + hitem + "!");
	        this.timerhit = 0;
	      }
	      if (Keyboard.isKeyDown(20)) {
	        mc.player.sendChatMessage(">I just opened chat!");
	      }
	      if (Keyboard.isKeyDown(53)) {
	        mc.player.sendChatMessage(">I just opened chat and wrote a slash!");
	      }
	      if (Keyboard.isKeyDown(16)) {
	        mc.player.sendChatMessage(">I just dropped an item!");
	      }
	      if (this.timer >= 300)
	      {
	        Double distance = Double.valueOf(mc.player.distanceWalkedModified / 0.6D);
	        int distancerounded = distance.intValue();
	        mc.player.sendChatMessage("> I walked " + distancerounded + " blocks today!");
	        distance = Double.valueOf(0.0D);
	        distancerounded = 0;
	        this.timer = 0;
	      }
	      if (mc.gameSettings.keyBindFullscreen.isPressed()) {
	        mc.player.sendChatMessage(">I just toggled fullscreen!");
	      }
	      if (mc.gameSettings.keyBindInventory.isPressed()) {
	        mc.player.sendChatMessage(">I just opened my Inventory!");
	      }
	      if ((mc.gameSettings.keyBindJump.isPressed()) && 
	        (this.jumptimer >= 150))
	      {
	        mc.player.sendChatMessage(">I just jumped!");
	        this.jumptimer = 0;
	      }
	      if (mc.gameSettings.keyBindLeft.isPressed()) {
	        mc.player.sendChatMessage(">I just moved to my left!");
	      }
	      if (mc.gameSettings.keyBindPickBlock.isPressed()) {
	        mc.player.sendChatMessage(">I just used pick block!");
	      }
	      if (mc.gameSettings.keyBindRight.isPressed()) {
	        mc.player.sendChatMessage(">I just moved to my right!");
	      }
	      if (mc.gameSettings.keyBindScreenshot.isPressed()) {
	        mc.player.sendChatMessage(">I just took a screenshot!");
	      }
	      if (mc.gameSettings.keyBindSneak.isPressed()) {
	        mc.player.sendChatMessage(">I just got sneaky ;)!");
	      }
	      if (mc.gameSettings.keyBindSprint.isPressed()) {
	        mc.player.sendChatMessage(">I just began sprinting!");
	      }
	      if (mc.gameSettings.keyBindSwapHands.isPressed()) {
	        mc.player.sendChatMessage(">I just swapped hands!");
	      }
	      if (Keyboard.isKeyDown(63)) {
	        mc.player.sendChatMessage(">I just changed perspectives!");
	      }
	      if (mc.gameSettings.keyBindUseItem.isPressed()) {
	        mc.player.sendChatMessage(">I just used an item!");
	      }
	      if (Keyboard.isKeyDown(1)) {
	        mc.player.sendChatMessage(">I just paused my game!");
	      }
	      if (mc.player.hitByEntity(lastattacker)) {
	        mc.player.sendChatMessage(">I just got hit by an entity!");
	      }
	      if (mc.player.getHealth() != 20.0F)
	      {
	        this.htimer += 1;
	        if (this.htimer >= 500)
	        {
	          this.prevh = Float.valueOf(mc.player.getHealth());
	          this.htimer = 0;
	        }
	      }
	      if (health.floatValue() - 10.0F != 0.0F) {
	        if ((this.antimer >= 200) && (this.fuzzy.floatValue() != health.floatValue() - 10.0F) && (this.fuzzy.floatValue() >= health.floatValue() - 10.0F))
	        {
	          mc.player.sendChatMessage("> I just took " + (health.floatValue() - 10.0F) + "  hearts of damage");
	          this.fuzzy = Float.valueOf(health.floatValue() - 10.0F);
	          this.antimer = 0;
	        }
	        else
	        {
	          this.fuzzy = Float.valueOf(health.floatValue() - 10.0F);
	            }
	          }
	        }
	      }
	    }