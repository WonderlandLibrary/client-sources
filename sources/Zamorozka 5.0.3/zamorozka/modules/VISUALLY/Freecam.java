package zamorozka.modules.VISUALLY;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.EntityFakePlayer;
import zamorozka.ui.FreecamEntity;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.RenderUtils;

public class Freecam extends Module {
	
	public Freecam() {
		super("FreeCam", 0, Category.VISUALLY);
	}

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("FreecamSpeed", this, 1, 0.1, 2, true));
	}

	private EntityOtherPlayerMP fakePlayer = null;
	private double oldX;
	private double oldY;
	private double oldZ;

	public void onEnable() {
		oldX = Minecraft.getMinecraft().player.posX;
		oldY = Minecraft.getMinecraft().player.posY;
		oldZ = Minecraft.getMinecraft().player.posZ;
		Minecraft.getMinecraft().player.noClip = true;
		EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getGameProfile());
		fakePlayer.copyLocationAndAnglesFrom(Minecraft.getMinecraft().player);
		fakePlayer.posY -= 0;
		fakePlayer.rotationYawHead = Minecraft.getMinecraft().player.rotationYawHead;
		Minecraft.getMinecraft().world.addEntityToWorld(-69, fakePlayer);
	}

	public void onUpdate() {
		if (!this.getState())
			return;
		double xz = Zamorozka.settingsManager.getSettingByName("FreecamSpeed").getValDouble();
		Minecraft.getMinecraft().player.motionY = 0;

		if (Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
			Minecraft.getMinecraft().player.motionY = 0.9;
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed) {
			Minecraft.getMinecraft().player.motionY = -0.9;
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindForward.pressed) {
			MovementUtilis.setMotion(xz);
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindBack.pressed) {
			MovementUtilis.setMotion(xz);
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindLeft.pressed) {
			MovementUtilis.setMotion(xz);
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindRight.pressed) {
			MovementUtilis.setMotion(xz);
		}
	}

	public void onDisable() {
		Minecraft.getMinecraft().player.noClip = false;
		Minecraft.getMinecraft().player.setPositionAndRotation(oldX, oldY, oldZ, Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch);
		Minecraft.getMinecraft().world.removeEntityFromWorld(-69);
		fakePlayer = null;
	}
}
