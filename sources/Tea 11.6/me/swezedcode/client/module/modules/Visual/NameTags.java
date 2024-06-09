package me.swezedcode.client.module.modules.Visual;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FriendManager;
import me.swezedcode.client.manager.managers.more.Friend;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.Wrapper;
import me.swezedcode.client.utils.events.EventRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;

public class NameTags extends Module {

	public NameTags() {
		super("NameTags", Keyboard.KEY_NONE, 0xFFFC804E, ModCategory.Visual);
		if(!MemeNames.enabled) {
			setDisplayName("NameTags");
		}else{
			setDisplayName("RenderABigScaledTagOverANiggersHead");
		}
	}

	private static Map<Integer, Boolean> field542;

	static {
		NameTags.field542 = new HashMap<Integer, Boolean>();
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
	}

	@EventListener
	public void EventRender(final EventRender event) {
		for (Object theEntity : mc.theWorld.loadedEntityList) {
			if ((((Entity) theEntity).isInvisible()) && (theEntity != Minecraft.thePlayer)) {
				Minecraft.theWorld.removeEntity((Entity) theEntity);
			}
		}
		for (final Object o : Wrapper.mc.theWorld.playerEntities) {
			final EntityPlayer entityPlayer = (EntityPlayer) o;
			if (entityPlayer.isEntityAlive()) {
				final EntityPlayer entityPlayer2 = entityPlayer;
				final Minecraft mc = NameTags.mc;
				if (entityPlayer2 == Minecraft.thePlayer) {
					continue;
				}
				final double posX = entityPlayer.posX;
				NameTags.mc.getRenderManager();
				final double nPosX = posX - RenderManager.renderPosX;
				final double posY = entityPlayer.posY;
				NameTags.mc.getRenderManager();
				final double nPosY = posY - RenderManager.renderPosY;
				final double nPosZ = entityPlayer.posZ;
				NameTags.mc.getRenderManager();
				this.d(entityPlayer, entityPlayer.getDisplayName().getFormattedText(), nPosX, nPosY,
						nPosZ - RenderManager.renderPosZ);
			}
		}
	}

	public static void e(final float n, final float n2, final float n3, final float n4, final float n5, final int n6,
			final int n7) {
		c(n, n2, n3, n4, n7);
		final float n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n6 >> 24
				& 0xFF) / 255.0f;
		final float n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n6 >> 16
				& 0xFF) / 255.0f;
		final float n101L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n6 >> 8
				& 0xFF) / 255.0f;
		final float n111L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n6
				& 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(
				n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n101L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n111L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L);
		GL11.glLineWidth(n5);
		GL11.glBegin(1);
		GL11.glVertex2d((double) n, (double) n2);
		GL11.glVertex2d((double) n, (double) n4);
		GL11.glVertex2d((double) n3, (double) n4);
		GL11.glVertex2d((double) n3, (double) n2);
		GL11.glVertex2d((double) n, (double) n2);
		GL11.glVertex2d((double) n3, (double) n2);
		GL11.glVertex2d((double) n, (double) n4);
		GL11.glVertex2d((double) n3, (double) n4);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void c(final float n, final float n2, final float n3, final float n4, final int n5) {
		final float n61L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n5 >> 24
				& 0xFF) / 255.0f;
		final float n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n5 >> 16
				& 0xFF) / 255.0f;
		final float n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n5 >> 8
				& 0xFF) / 255.0f;
		final float n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = (n5
				& 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(
				n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n61L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L);
		GL11.glBegin(7);
		GL11.glVertex2d((double) n3, (double) n2);
		GL11.glVertex2d((double) n, (double) n2);
		GL11.glVertex2d((double) n, (double) n4);
		GL11.glVertex2d((double) n3, (double) n4);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	private void d(final EntityPlayer entityPlayer, String s, final double n, double n2, final double n3) {
		n2 += (entityPlayer.isSneaking() ? 0.5 : 0.7);
		final Minecraft mc = NameTags.mc;
		float n4 = Minecraft.thePlayer.getDistanceToEntity(entityPlayer) / 4.0f;
		if (n4 < 1.6f) {
			n4 = 1.6f;
		}
		int n5 = 16777215;
		if (entityPlayer.isInvisible()) {
			n5 = 16756480;
		} else if (entityPlayer.isSneaking()) {
			n5 = 11468800;
		}
		if(Manager.getManager().getFriendManager().isFriend(entityPlayer.getName())) {
			n5 = 0xFF2EFFEA;
		}
		if(Manager.getManager().getFriendManager().isFriend(entityPlayer.getName())) {
			for(Friend friends : Manager.getManager().getFriendManager().getFriends()) {
				s = friends.getAlias();
			}
		}
		final double n6 = Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount()) / 2.0;
		EnumChatFormatting enumChatFormatting;
		if (n6 > 10.0) {
			enumChatFormatting = EnumChatFormatting.DARK_GREEN;
		} else if (n6 > 7.5) {
			enumChatFormatting = EnumChatFormatting.GREEN;
		} else if (n6 > 5.0) {
			enumChatFormatting = EnumChatFormatting.YELLOW;
		} else if (n6 > 2.5) {
			enumChatFormatting = EnumChatFormatting.GOLD;
		} else {
			enumChatFormatting = EnumChatFormatting.RED;
		}
		if (Math.floor(n6) == n6) {
			s = String.valueOf(String.valueOf(s)) + " " + enumChatFormatting + (int) Math.floor(n6);
		} else {
			s = String.valueOf(String.valueOf(s)) + " " + enumChatFormatting + n6;
		}
		final float n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = n4
				* 2.0f / 100.0f;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) n, (float) n2 + 1.4f, (float) n3);
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		NameTags.mc.getRenderManager();
		GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
		NameTags.mc.getRenderManager();
		GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
		GL11.glScalef(
				-n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				-n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				n71L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L);
		method768(2896, false);
		method768(2929, false);
		final int n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = NameTags.mc.fontRendererObj
				.getStringWidth(s) / 2;
		method768(3042, true);
		GL11.glBlendFunc(770, 771);
		e(-n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
				- 2, -(NameTags.mc.fontRendererObj.FONT_HEIGHT + 1F),
				n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
						+ 2,
				2.0f, 1.0f, -16777216, 0x90000000);
		NameTags.mc.fontRendererObj.func_175063_a(s,
				-n81L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
				-(NameTags.mc.fontRendererObj.FONT_HEIGHT - 1), n5);
		GL11.glPushMatrix();
		int n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = 0;
		ItemStack[] armorInventory;
		for (int length = (armorInventory = entityPlayer.inventory.armorInventory).length, i = 0; i < length; ++i) {
			if (Objects.nonNull(armorInventory[i])) {
				n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L -= 8;
			}
		}
		if (Objects.nonNull(entityPlayer.getHeldItem())) {
			n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L -= 8;
			final ItemStack c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = entityPlayer
					.getHeldItem().copy();
			if (c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
					.hasEffect()
					&& (c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
							.getItem() instanceof ItemTool
							|| c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
									.getItem() instanceof ItemArmor)) {
				c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L.stackSize = 1;
			}
			this.e(c1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
					n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
					-26);
			n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L += 16;
		}
		ItemStack[] aI21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L;
		for (int length2 = (aI21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = entityPlayer.inventory.armorInventory).length, j = 0; j < length2; ++j) {
			final ItemStack iS1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = aI21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L[j];
			if (Objects.nonNull(
					iS1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L)) {
				final ItemStack copy2 = iS1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L
						.copy();
				if (copy2.hasEffect()
						&& (copy2.getItem() instanceof ItemTool || copy2.getItem() instanceof ItemArmor)) {
					copy2.stackSize = 1;
				}
				this.e(copy2,
						n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						-26);
				n91L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L += 16;
			}
		}
		GL11.glPopMatrix();
		method1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}

	private void e(final ItemStack itemStack, final int n, final int n2) {
		GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GlStateManager.clear(256);
		RenderHelper.enableStandardItemLighting();
		NameTags.mc.getRenderItem().zLevel = -150.0f;
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		NameTags.mc.getRenderItem().func_180450_b(itemStack, n, n2);
		NameTags.mc.getRenderItem().func_175030_a(NameTags.mc.fontRendererObj, itemStack, n, n2);
		NameTags.mc.getRenderItem().zLevel = 0.0f;
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.disableDepth();
		this.f(itemStack, n, n2);
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0f, 2.0f, 2.0f);
		GL11.glPopMatrix();
	}

	private void f(final ItemStack itemStack, final int n, final int n2) {
		int n3 = n2 - 24;
		if (itemStack.getItem() instanceof ItemArmor) {
			final int eL1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.field_180310_c.effectId, itemStack);
			final int eL21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack);
			final int eL31L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
			if (eL1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"p" + eL1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
			if (eL21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"t" + eL21L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
			if (eL31L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"u" + eL31L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
		}
		if (itemStack.getItem() instanceof ItemBow) {
			final int eL141L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
			final int eL151L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
			final int eL161L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);
			final int eL171L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L = EnchantmentHelper
					.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
			if (eL141L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"p" + eL141L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
			if (eL151L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"k" + eL151L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
			if (eL161L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"f" + eL161L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
			if (eL171L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L > 0) {
				NameTags.mc.fontRendererObj.drawString(
						"u" + eL171L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L,
						n * 2, n3, 16777215);
				n3 += 8;
			}
		}
		if (itemStack.getItem() instanceof ItemSword) {
			final int enchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId,
					itemStack);
			final int enchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId,
					itemStack);
			final int enchantmentLevel10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId,
					itemStack);
			final int enchantmentLevel11 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId,
					itemStack);
			if (enchantmentLevel8 > 0) {
				NameTags.mc.fontRendererObj.drawString("s" + enchantmentLevel8, n * 2, n3, 16777215);
				n3 += 8;
			}
			if (enchantmentLevel9 > 0) {
				NameTags.mc.fontRendererObj.drawString("k" + enchantmentLevel9, n * 2, n3, 16777215);
				n3 += 8;
			}
			if (enchantmentLevel10 > 0) {
				NameTags.mc.fontRendererObj.drawString("f" + enchantmentLevel10, n * 2, n3, 16777215);
				n3 += 8;
			}
			if (enchantmentLevel11 > 0) {
				NameTags.mc.fontRendererObj.drawString("u" + enchantmentLevel11, n * 2, n3, 16777215);
			}
		}
	}

	public static void method768(final int n, final boolean b) {
		NameTags.field542.put(n, GL11.glGetBoolean(n));
		if (b) {
			GL11.glEnable(n);
		} else {
			GL11.glDisable(n);
		}
	}

	public static void method769(final int n) {
		final Boolean b = NameTags.field542.get(n);
		if (b != null) {
			if (b) {
				GL11.glEnable(n);
			} else {
				GL11.glDisable(n);
			}
		}
	}

	public static void method1L1L1LL1L1L1L1L1L1L1LL1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1L1L1L1L1LL1L1L1L1L1L1LL1L1L1L1L1L1L1LL1L1L1L1L() {
		final Iterator<Integer> iterator = NameTags.field542.keySet().iterator();
		while (iterator.hasNext()) {
			method769(iterator.next());
		}
	}

	@EventListener
	public void onRenderNormalNametag(
			final me.swezedcode.client.utils.events.EventNameRender e1L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L1) {
		if (e1L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L1.entity instanceof EntityPlayer) {
			e1L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L11L1L1L1LL1L1L1L1L1
					.setCanceled(true);
		}
	}
}
