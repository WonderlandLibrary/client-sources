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

public class NoOverlay extends Module {
	
	public NoOverlay(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
}
