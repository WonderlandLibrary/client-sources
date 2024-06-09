package com.kilo.mod.all;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.RenderUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class Creatives extends Module {
	
	private final Timer timer = new Timer();
	
	private final int[] helmets = new int[] {298, 302, 306, 310, 314};
	private final int[] chestplates = new int[] {299, 303, 307, 311, 315};
	private final int[] leggings = new int[] {300, 304, 308, 312, 316};
	private final int[] boots = new int[] {301, 305, 309, 313, 317};

	private float rainbowHue, woolHue, bannerHue;
	private final int[] woolColors = new int[] {14, 1, 4, 5, 3, 9, 11, 10, 2, 6};
	private final int[] bannerColors = new int[] {1, 14, 11, 10, 12, 6, 4, 5, 13, 9};
	
	public Creatives(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		List<ModuleSubOption> rso = new ArrayList<ModuleSubOption>();
		rso.add(new ModuleSubOption("Transition Speed", "Speed of the rainbow transition", Interactable.TYPE.SLIDER, 0, new float[] {0, 10}, true));
		addOption("Rainbow Armor", "Override all armor with rainbow armor", Interactable.TYPE.CHECKBOX, false, null, false, rso);

		List<ModuleSubOption> hso = new ArrayList<ModuleSubOption>();
		hso.add(new ModuleSubOption("Switch Speed", "Speed to switch blocks", Interactable.TYPE.SLIDER, 0, new float[] {0, 10}, true));
		addOption("Block Hat", "Wear a random block as a hat", Interactable.TYPE.CHECKBOX, null, null, false, hso);

		List<ModuleSubOption> wso = new ArrayList<ModuleSubOption>();
		wso.add(new ModuleSubOption("Switch Speed", "Speed to switch wool colors", Interactable.TYPE.SLIDER, 0, new float[] {0, 10}, true));
		addOption("Wool Hat", "Wear colored wool as a hat", Interactable.TYPE.CHECKBOX, null, null, false, wso);

		List<ModuleSubOption> bso = new ArrayList<ModuleSubOption>();
		bso.add(new ModuleSubOption("Switch Speed", "Speed to switch banner colors", Interactable.TYPE.SLIDER, 0, new float[] {0, 10}, true));
		addOption("Banner Hat", "Wear a colored banner as a hat", Interactable.TYPE.CHECKBOX, null, null, false, bso);
	}
	
	public void onPlayerPreUpdate() {
		if (!mc.thePlayer.capabilities.isCreativeMode) { return; }
		
		boolean rainbowArmor = Util.makeBoolean(getOptionValue("rainbow armor"));
		boolean blockHat = Util.makeBoolean(getOptionValue("block hat"));
		boolean woolHat = Util.makeBoolean(getOptionValue("wool hat"));
		boolean bannerHat = Util.makeBoolean(getOptionValue("banner hat"));
		
		if (rainbowArmor) {
			rainbowArmor();
		}
		
		if (blockHat) {
			blockHat();
			return;
		}
		
		if (woolHat) {
			woolHat();
			return;
		}
		
		if (bannerHat) {
			bannerHat();
			return;
		}
	}
	
	public void rainbowArmor() {
		float rainbowSpeed = Util.makeFloat(getSubOptionValue("rainbow armor", "transition speed"));
		
		rainbowHue+= rainbowSpeed;
		if (rainbowHue > 255) {
			rainbowHue = 0;
		}
		
		try {
			ItemStack helmet = new ItemStack(Item.getItemById(helmets[0]));
			ItemStack chest = new ItemStack(Item.getItemById(chestplates[0]));
			ItemStack leg = new ItemStack(Item.getItemById(leggings[0]));
			ItemStack boot = new ItemStack(Item.getItemById(boots[0]));

			int c = new Color(Color.HSBtoRGB(rainbowHue/255f, 1f, 1f)).getRGB();
			String cc = Integer.toHexString(c);
			
			c = (int) Long.parseLong(cc.substring(2, cc.length()), 16);
			
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagCompound display = new NBTTagCompound();
			display.setInteger("color", c);
			
			tag.setTag("display", display);
			
			helmet.setTagCompound(tag);
			chest.setTagCompound(tag);
			leg.setTagCompound(tag);
			boot.setTagCompound(tag);
			
			if (!Util.makeBoolean(getOptionValue("block hat")) && !Util.makeBoolean(getOptionValue("wool hat"))) {
				mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(5, helmet));
			}
			mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(6, chest));
			mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(7, leg));
			mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(8, boot));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void blockHat() {
		float blockSpeed = Util.makeFloat(getSubOptionValue("block hat", "switch speed"));
		
		if (!timer.isTime(blockSpeed/10f)) {
			return;
		}
		
		try {
			Random rand = new Random();
			
			int i = rand.nextInt(Block.blockRegistry.getKeys().size());
			Object obj = Block.blockRegistry.getKeys().toArray()[i];
			
			Item item = Item.getItemFromBlock((Block)Block.blockRegistry.getObject(obj));
			
			if (item == null) {
				return;
			}
			
			ItemStack block = new ItemStack(item);
			ItemStack old = mc.thePlayer.getCurrentArmor(3);
			
			if (old != null) {
				if (block.getDisplayName().equalsIgnoreCase(old.getDisplayName())) {
					return;
				}
			}
			
			mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(5, block));
			timer.reset();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void woolHat() {
		float blockSpeed = Util.makeFloat(getSubOptionValue("wool hat", "switch speed"));
		
		try {
			woolHue+= blockSpeed/10;
			
			if (woolHue > woolColors.length) {
				woolHue = 0;
			}
			
			Item item = Item.getItemById(35);
			
			ItemStack block = new ItemStack(item, 1, woolColors[(int)Math.floor(woolHue)]);
			
			mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(5, block));
			
			timer.reset();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void bannerHat() {
		float blockSpeed = Util.makeFloat(getSubOptionValue("banner hat", "switch speed"));
		
		try {
			bannerHue+= blockSpeed/10;
			
			if (bannerHue > bannerColors.length-1) {
				bannerHue = 0;
			}
			
			Item item = Item.getItemById(425);
			
			if (item != null) {
				ItemStack block = new ItemStack(item, 1, bannerColors[(int)Math.floor(bannerHue)]);
				
				mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(5, block));
				
				timer.reset();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
