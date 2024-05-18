package com.darkcart.xdolf.mods.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class StorageESP extends Module {

	public StorageESP() {
		super("StorageESP", "Creates an ESP box around blocks which can be used to store items.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for (Object o : Wrapper.getWorld().loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					TileEntityChest chest = (TileEntityChest) o;
					this.drawChestESP(chest, chest.getPos().getX(), chest.getPos().getY(), chest.getPos().getZ());
				}
				if (o instanceof TileEntityEnderChest) {
					TileEntityEnderChest enderChest = (TileEntityEnderChest) o;
					this.drawEnderChestESP(enderChest, enderChest.getPos().getX(), enderChest.getPos().getY(), enderChest.getPos().getZ());
				}
				if (o instanceof TileEntityFurnace) {
					TileEntityFurnace furnace = (TileEntityFurnace) o;
					RenderUtils.blockESP(furnace.getPos(), Color.white, 1.0, 1.0);
				}
				if (o instanceof TileEntityDispenser) {
					TileEntityDispenser dispenser = (TileEntityDispenser) o;
					RenderUtils.blockESP(dispenser.getPos(), Color.white, 1.0, 1.0);
				}
				if (o instanceof TileEntityDropper) {
					TileEntityDropper dropper = (TileEntityDropper) o;
					RenderUtils.blockESP(dropper.getPos(), Color.white, 1.0, 1.0);
				}
				if (o instanceof TileEntityHopper) {
					TileEntityHopper hopper = (TileEntityHopper) o;
					RenderUtils.blockESP(hopper.getPos(), Color.white, 1.0, 1.0);
				}
				if (o instanceof TileEntityShulkerBox) {
					TileEntityShulkerBox shulker = (TileEntityShulkerBox) o;
					RenderUtils.blockESP(shulker.getPos(), Color.yellow, 1.0, 1.0);
				}
				
			}
			for (Entity e: Wrapper.getWorld().loadedEntityList) {
				if (e instanceof EntityMinecartChest) {
					RenderUtils.blockESP(e.getPosition(), Color.green, 1.0, 1.0);
				}
				if (e instanceof EntityMinecartFurnace || e instanceof EntityMinecartHopper) {
					RenderUtils.blockESP(e.getPosition(), Color.white, 1.0, 1.0);
				}
			}
		}
	}

	
	public void drawChestESP(TileEntityChest chest, double x, double y, double z) {
		if(isEnabled()) {
			boolean isAdjacent = chest.adjacentChestChecked;
			if(chest.adjacentChestXPos != null)
			{
				if(chest.getChestType() == BlockChest.Type.TRAP) { //if is trapped chest
					RenderUtils.blockESP(chest.getPos(), Color.red, 1.0, 2.0);
				}else{
					RenderUtils.blockESP(chest.getPos(), Color.green, 1.0, 2.0);
				}
			}

			if(chest.adjacentChestZPos != null)
			{
				if(chest.getChestType() == BlockChest.Type.TRAP) { //if is trapped chest
					RenderUtils.blockESP(chest.getPos(), Color.red, 2.0, 1.0);
				}else{
					RenderUtils.blockESP(chest.getPos(), Color.green, 2.0, 1.0);
				}
			}
			if(chest.adjacentChestZNeg == null && chest.adjacentChestXNeg == null && chest.adjacentChestXPos == null && chest.adjacentChestZPos == null)
			{
				if(chest.getChestType() == BlockChest.Type.TRAP) { //if is trapped chest
					RenderUtils.blockESP(chest.getPos(), Color.red, 1.0, 1.0);
				}else{
					RenderUtils.blockESP(chest.getPos(), 
							Color.green, 
							1.0, 
							1.0);
				}
			}
		}
	}
	
	public void drawEnderChestESP(TileEntityEnderChest enderChest, double x, double y, double z) {
		if(isEnabled()) {
			RenderUtils.blockESP(enderChest.getPos(), Color.magenta, 1.0, 1.0);
		}
	}
}
