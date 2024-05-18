package com.darkcart.xdolf.commands;

import java.util.ArrayList;

import com.darkcart.xdolf.Wrapper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CmdPlayerInfo extends Command {

	public CmdPlayerInfo() {
		super("info");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			String name = args[0];
			for (int x = 0; x < Wrapper.getWorld().loadedEntityList.size(); x++)
			{
				if (Wrapper.getWorld().loadedEntityList.get(x) instanceof EntityPlayer)
				{
					EntityPlayer ent = (EntityPlayer)Wrapper.getWorld().loadedEntityList.get(x);
					if (ent.getName().equalsIgnoreCase(name))
					{
						Wrapper.addChatMessage("Username: " + ent.getName());
						boolean checkHealth = ent.getHealth() == 20;
						Wrapper.addChatMessage("Health: " + (checkHealth ? "20" : ((double)Math.round(((ent).getHealth() * 100) / 100)) + "/20"));
						Wrapper.addChatMessage("Distance: " + Math.abs(ent.getDistanceToEntity(Wrapper.getPlayer())));
						if (ent.inventory.armorItemInSlot(3) != null)
						{
							ArrayList var15 = new ArrayList();
							var15.add(ent.inventory.armorItemInSlot(3).getDisplayName());
						
							NBTTagList helm = ent.inventory.armorItemInSlot(3).getEnchantmentTagList();
							for (int var4 = 0; var4 < helm.tagCount(); ++var4)
							{
								//helm
								short var5 = ((NBTTagCompound)helm.getCompoundTagAt(var4)).getShort("id");
								short var6 = ((NBTTagCompound)helm.getCompoundTagAt(var4)).getShort("lvl");
								if (Enchantment.getEnchantmentByID(var5) != null)
								{
									var15.add(Enchantment.getEnchantmentByID(var5).getTranslatedName(var6));
								}
							}
							int var15Size = var15.size() - 1;
							Wrapper.addChatMessage("\247f" + var15);
						}
								
						if (ent.inventory.armorItemInSlot(2) != null)
						{
							ArrayList var16 = new ArrayList();
							var16.add(ent.inventory.armorItemInSlot(2).getDisplayName());
								
							NBTTagList chest = ent.inventory.armorItemInSlot(2).getEnchantmentTagList();
							for (int var4 = 0; var4 < chest.tagCount(); ++var4)
							{
				    			//chest
								short var7 = ((NBTTagCompound)chest.getCompoundTagAt(var4)).getShort("id");
								short var8 = ((NBTTagCompound)chest.getCompoundTagAt(var4)).getShort("lvl");
								if (Enchantment.getEnchantmentByID(var7) != null)
								{
									var16.add(Enchantment.getEnchantmentByID(var7).getTranslatedName(var8));
								}
							}
							int var15Size = var16.size() - 1;
							Wrapper.addChatMessage("\247f" + var16);
						}
							
						if (ent.inventory.armorItemInSlot(1) != null)
						{
							ArrayList var18 = new ArrayList();
							var18.add(ent.inventory.armorItemInSlot(1).getDisplayName());
							
							NBTTagList legs = ent.inventory.armorItemInSlot(1).getEnchantmentTagList();
							for (int var4 = 0; var4 < legs.tagCount(); ++var4)
							{
								//legs
								short var11 = ((NBTTagCompound)legs.getCompoundTagAt(var4)).getShort("id");
								short var12 = ((NBTTagCompound)legs.getCompoundTagAt(var4)).getShort("lvl");
								if (Enchantment.getEnchantmentByID(var11) != null)
								{
									var18.add(Enchantment.getEnchantmentByID(var11).getTranslatedName(var12));
								}
							}
							int var15Size = var18.size() - 1;
			    			Wrapper.addChatMessage("\247f" + var18);
						}
							
						if (ent.inventory.armorItemInSlot(0) != null)
						{
							ArrayList var18 = new ArrayList();
							var18.add(ent.inventory.armorItemInSlot(0).getDisplayName());
							
							NBTTagList legs = ent.inventory.armorItemInSlot(0).getEnchantmentTagList();
							for (int var4 = 0; var4 < legs.tagCount(); ++var4)
							{
								//boots
								short var13 = ((NBTTagCompound)legs.getCompoundTagAt(var4)).getShort("id");
								short var14 = ((NBTTagCompound)legs.getCompoundTagAt(var4)).getShort("lvl");
								if (Enchantment.getEnchantmentByID(var13) != null)
								{
									var18.add(Enchantment.getEnchantmentByID(var13).getTranslatedName(var14));
								}
							}
							int var15Size = var18.size() - 1;
			    			Wrapper.addChatMessage("\247f" + var18);
						}
							
						if (ent.inventory.getCurrentItem().isItemEnchanted())
						{
							ArrayList var19 = new ArrayList();
							var19.add(ent.inventory.getCurrentItem().getDisplayName());
							
							NBTTagList currentItem = ent.inventory.getCurrentItem().getEnchantmentTagList();
							for (int var4 = 0; var4 < currentItem.tagCount(); ++var4)
							{
								//currentItem
								short var13 = ((NBTTagCompound)currentItem.getCompoundTagAt(var4)).getShort("id");
								short var14 = ((NBTTagCompound)currentItem.getCompoundTagAt(var4)).getShort("lvl");
								if (Enchantment.getEnchantmentByID(var13) != null)
								{
									var19.add(Enchantment.getEnchantmentByID(var13).getTranslatedName(var14));
								}
							}
							int var15Size = var19.size() - 1;
							Wrapper.addChatMessage("\247f" + var19);
						}
					}
				}
			}
		}catch(Exception ex) {}
	}

	@Override
	public String getDescription() {
		return "Display Player information. e.g. armour enchants.";
	}

	@Override
	public String getSyntax() {
		return "info <player>";
	}

}
