package zamorozka.modules.COMBAT;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import de.Hero.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.AntiBot2;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.MathUtil;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingTools;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.Wrapper;

public class PearlNotifier extends Module {

	EntityLivingBase target = KillAura.target;

	public PearlNotifier() {
		super("PearlESP", Keyboard.KEY_NONE, Category.VISUALLY);
	}

	@EventTarget
	public void onRender(RenderEvent3D event) {
		/*
		 * ƒŒƒ≈À¿“‹!!!
		 */
		for (Entity e : mc.world.loadedEntityList) {
			if (!(e instanceof EntityEnderPearl))
				continue;
			if (e != null) {
				float pTicks = mc.timer.renderPartialTicks;
				double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pTicks - mc.getRenderManager().viewerPosX;
				double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY;
				double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pTicks - mc.getRenderManager().viewerPosZ;
				RenderingUtils.startDrawing();
				RenderUtils2.drawEntityESP(e, Zamorozka.getClientColors());
				RenderingTools.drawTracer(e, Zamorozka.getClientColors());
				RenderingUtils.stopDrawing();
				ChatUtils.printChatprefix("Pearl Coord: " + "X: "+ e.getPosition().getX() + " " + "Y: " +(int) e.getPosition().getY() + " " + "Z: " + (int) e.getPosition().getZ());
			}
		}
	}
}