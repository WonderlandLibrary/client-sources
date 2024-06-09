package me.valk.agway.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.valk.Vital;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.other.EventTick;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.event.events.screen.EventRenderWorld;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.AimUtils;
import me.valk.utils.MathUtil;
import me.valk.utils.TimerUtils;
import me.valk.utils.angles.Angles;
import me.valk.utils.angles.AnglesUtils;
import me.valk.utils.angles.Vector3D;
import me.valk.utils.render.RenderUtil;
import me.valk.utils.value.BooleanValue;
import me.valk.utils.value.RestrictedValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class KillAuraMod extends Module {

	private List<EntityLivingBase> loaded = new ArrayList<>();
	public RestrictedValue<Integer> fov = new RestrictedValue<Integer>("FOV", 360, 1, 360);
	public RestrictedValue<Integer> APS = new RestrictedValue<Integer>("APS", 14, 1, 20);
	public RestrictedValue<Double> reach = new RestrictedValue<Double>("reach", 4.5d, 1d, 8d);
	public RestrictedValue<Integer> existed = new RestrictedValue<Integer>("existed", 0, 0, 500);
	public BooleanValue invisibles = new BooleanValue("invisibles", false);
	public BooleanValue hypixel = new BooleanValue("hypixel", false);
	public BooleanValue autoblock = new BooleanValue("autoblock", true);
	public BooleanValue teams = new BooleanValue("teams", false);
	public BooleanValue players = new BooleanValue("players", true);
	public BooleanValue animals = new BooleanValue("animals", true);
	public BooleanValue monsters = new BooleanValue("monsters", true);
	public static BooleanValue esp = new BooleanValue("esp", false);
	private List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
	private EntityLivingBase target;
	private TimerUtils time = new TimerUtils();
	private int random;

	public KillAuraMod() {
		super(new ModData("KillAura", Keyboard.KEY_R, new Color(255, 40, 40)), ModType.COMBAT);
		addValue(invisibles);
		addValue(autoblock);
		addValue(monsters);
		addValue(players);
		addValue(animals);
		addValue(existed);
		addValue(hypixel);
		addValue(reach);
		addValue(teams);
		addValue(APS);
		addValue(fov);
		addValue(esp);
	}

//I REMOVED THE AURA CUZ ITS PRIVATE SOWWY	
}
