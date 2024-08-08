package me.xatzdevelopments.xatz.client.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import io.netty.util.internal.ThreadLocalRandom;
import me.xatzdevelopments.xatz.client.Unused.inEvents.inEventType;
import me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners.inEventAttack;
import me.xatzdevelopments.xatz.client.events.EventMotion;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.client.tools.Helper;
import me.xatzdevelopments.xatz.client.tools.TimeTracker;
import me.xatzdevelopments.xatz.client.tools.Utils;
import me.xatzdevelopments.xatz.gui.UnicodeFontRenderer;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Movement.MoveUtils2;
import net.java.games.input.Mouse;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.src.MathUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;


public class KillauraSecksEdition extends Module {

	public KillauraSecksEdition(String name, int defaultKeyCode, Category cat, String description) {
		super(name, defaultKeyCode, cat, description);
		// TODO Auto-generated constructor stub
	}
}

