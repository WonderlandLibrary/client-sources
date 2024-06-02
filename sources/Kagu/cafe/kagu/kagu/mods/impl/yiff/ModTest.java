/**
 * 
 */
package cafe.kagu.kagu.mods.impl.yiff;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.vecmath.Vector3d;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;

import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.managers.NetworkManager;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ColorSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.KeybindSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.settings.impl.SlotSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.MiscUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.PlayerUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.UiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.MathHelper;

/**
 * @author lavaflowglow
 *
 */
public class ModTest extends Module {

	public ModTest() {
		super("Test", Category.EXPLOIT);
		slotSetting1.setInvalidSlots(slotSetting2);
		slotSetting2.setInvalidSlots(slotSetting1);
		setSettings(booleanSetting, decimalSetting1, decimalSetting2, decimalSetting3, modeSetting1, modeSetting2,
				modeSetting3, integerSetting1, integerSetting2, integerSetting3, keybindSetting, slotSetting1,
				slotSetting2, colorSetting1, colorSetting2);
	}

	private BooleanSetting booleanSetting = new BooleanSetting("Boolean setting", false);
	private DoubleSetting decimalSetting1 = new DoubleSetting("Decimal setting 1", 1, 0, 10, 0.25),
			decimalSetting2 = new DoubleSetting("Decimal setting 2", 1, -10, 10, 0.25),
			decimalSetting3 = new DoubleSetting("Decimal setting 3", 10, 10, 20, 0.25);
	private IntegerSetting integerSetting1 = new IntegerSetting("Integer setting 1", 1, 0, 10, 1),
			integerSetting2 = new IntegerSetting("Integer setting 2", 1, -10, 10, 1),
			integerSetting3 = new IntegerSetting("Integer setting 3", 10, 10, 20, 1);
	private ModeSetting modeSetting1 = new ModeSetting("Mode setting 1", "Test 1", "Test 1", "Test 2", "Test 3"),
			modeSetting2 = new ModeSetting("Mode setting 2", "Test 1", "Test 1"),
			modeSetting3 = new ModeSetting("Mode setting 3", "Test 1");
	private KeybindSetting keybindSetting = new KeybindSetting("Keybind", Keyboard.KEY_W);
	private SlotSetting slotSetting1 = new SlotSetting("Slot 1", 1), slotSetting2 = new SlotSetting("Slot 2", 2);
	private ColorSetting colorSetting1 = new ColorSetting("Color 1", 0xffd58cff),
			colorSetting2 = new ColorSetting("Color 2", 0xffa5e0fe);

	private float[] lastRotations = new float[] { 0, 0 };
	private int ticks = 0;
	private Logger logger = LogManager.getLogger();
	private Gson gson = new Gson();
	
	@Override
	public void onEnable() {
		EntityPlayerSP thePlayer = mc.thePlayer;
//		lastRotations[0] = thePlayer.rotationYaw;
//		lastRotations[1] = thePlayer.rotationPitch;
//		ticks = 0;
//		float yaw = RotationUtils.getStrafeYaw() + 90;
//		thePlayer.setPosition(thePlayer.posX + Math.cos(Math.toRadians(yaw)) * 2, thePlayer.posY - 0.9I, thePlayer.posZ + Math.sin(Math.toRadians(yaw)) * 2);
//		toggle();
//		thePlayer.offsetPosition(0, -5, 0);
//		toggle();
//		ChatUtils.addChatMessage(mc.theWorld.getBlockState(thePlayer.getPosition()).getBlock().getLightOpacity());
	}

	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		
//		for (int i = 0; i < 10; i++)
//			try {
//				NetworkManager.getInstance().sendGet(new HttpGet("https://aimware.net/"));
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		
	};
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
//		if (e.isPre()) {
//			logger.info("S: " + e.getPacket());
//		}
		if (e.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)e.getPacket()).getAction() == Action.ATTACK) {
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.START_SPRINTING));
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.STOP_SPRINTING));
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, net.minecraft.network.play.client.C0BPacketEntityAction.Action.START_SPRINTING));
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ, true));
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ, false));
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
//		if (e.isPre()) {
//			logger.info("R: " + e.getPacket());
//		}
	};
	
}
