package com.kilo.mod.all;

import java.awt.Color;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Render;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class StorageESP extends Module {
	
	public StorageESP(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Chests", "Show chests and trapped chests", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Ender Chests", "Show ender chests", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Dispensers", "Show dispensers and droppers", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Furnaces", "Show furnaces", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Hoppers", "Show hoppers", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Tracers", "Show tracer line to storages", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
	}
	
	public void render3D() {
		boolean chests = Util.makeBoolean(getOptionValue("chests"));
		boolean enderChests = Util.makeBoolean(getOptionValue("ender chests"));
		boolean dispensers = Util.makeBoolean(getOptionValue("dispensers"));
		boolean furnaces = Util.makeBoolean(getOptionValue("furnaces"));
		boolean hoppers = Util.makeBoolean(getOptionValue("hoppers"));
		boolean tracers = Util.makeBoolean(getOptionValue("tracers"));
		
		for(int i = 0; i < mc.theWorld.loadedTileEntityList.size(); i++) {
			TileEntity e = (TileEntity)mc.theWorld.loadedTileEntityList.get(i);
			
			int r = -1;
			int g = r;
			int b = r;
			int a = r;
			
			if (e instanceof TileEntityChest){
				if (chests) {
					r = Util.makeInteger(getSubOptionValue("chests", "red"));
					g = Util.makeInteger(getSubOptionValue("chests", "green"));
					b = Util.makeInteger(getSubOptionValue("chests", "blue"));
					a = Util.makeInteger(getSubOptionValue("chests", "opacity"));
				}
			} else if (e instanceof TileEntityEnderChest) {
				if (enderChests) {
					r = Util.makeInteger(getSubOptionValue("ender chests", "red"));
					g = Util.makeInteger(getSubOptionValue("ender chests", "green"));
					b = Util.makeInteger(getSubOptionValue("ender chests", "blue"));
					a = Util.makeInteger(getSubOptionValue("ender chests", "opacity"));
				}
			} else if (e instanceof TileEntityDispenser) {
				if (dispensers) {
					r = Util.makeInteger(getSubOptionValue("dispensers", "red"));
					g = Util.makeInteger(getSubOptionValue("dispensers", "green"));
					b = Util.makeInteger(getSubOptionValue("dispensers", "blue"));
					a = Util.makeInteger(getSubOptionValue("dispensers", "opacity"));
				}
			} else if (e instanceof TileEntityFurnace) {
				if (furnaces) {
					r = Util.makeInteger(getSubOptionValue("furnaces", "red"));
					g = Util.makeInteger(getSubOptionValue("furnaces", "green"));
					b = Util.makeInteger(getSubOptionValue("furnaces", "blue"));
					a = Util.makeInteger(getSubOptionValue("furnaces", "opacity"));
				}
			} else if (e instanceof TileEntityHopper) {
				if (hoppers) {
					r = Util.makeInteger(getSubOptionValue("hoppers", "red"));
					g = Util.makeInteger(getSubOptionValue("hoppers", "green"));
					b = Util.makeInteger(getSubOptionValue("hoppers", "blue"));
					a = Util.makeInteger(getSubOptionValue("hoppers", "opacity"));
				}
			}
			if (r != -1) {
				if (tracers) {
					double[] p = RenderUtil.entityWorldPos(mc.thePlayer);
					Vec3 from = new Vec3(p[0], p[1]+mc.thePlayer.getEyeHeight(), p[2]);
					Vec3 to = new Vec3(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()).addVector(0.5f, 0.5f, 0.5f);
					Render.line(from, to, new Color(r, g, b, (a/2)+128).getRGB(), 1f);
				}
				
				Render.blockBox(e.getPos(), new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB(), new boolean[] {
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.NORTH)).getBlock().getClass() != e.getBlockType().getClass(), 
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.SOUTH)).getBlock().getClass() != e.getBlockType().getClass(), 
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.EAST)).getBlock().getClass() != e.getBlockType().getClass(), 
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.WEST)).getBlock().getClass() != e.getBlockType().getClass(), 
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.UP)).getBlock().getClass() != e.getBlockType().getClass(), 
					mc.theWorld.getBlockState(e.getPos().offset(EnumFacing.DOWN)).getBlock().getClass() != e.getBlockType().getClass()
				});
			}
		}
	}
}
