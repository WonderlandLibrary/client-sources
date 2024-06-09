package me.protocol_client.modules;

import java.text.DecimalFormat;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class NameTags extends Module {

	public NameTags() {
		super("Name Tags", "nametags", Keyboard.KEY_NONE, Category.RENDER, new String[] { "dsdfsdfsdfsdghgh" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	public static float					mode			= 0.0F;
	private final static DecimalFormat	decimalFormat	= new DecimalFormat("#.#");
	private RenderItem					itemRenderer	= null;

	@EventTarget
	public void render(Render3DEvent event) {
		if ((Wrapper.getWorld() != null) && (itemRenderer == null)) {
			try {
				itemRenderer = new RenderItem(Wrapper.mc().renderEngine, Wrapper.mc().modelManager);
			} catch (NullPointerException localNullPointerException) {
			}
		}
		for (Object o : mc.theWorld.playerEntities) {
			EntityPlayer p = (EntityPlayer) o;
			if ((p != mc.func_175606_aa()) && (p.isEntityAlive())) {
				double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
				double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
				double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
				if (!p.getName().startsWith("Body #")) {
					renderNametag(p, pX, pY, pZ);
				}
			}
			if ((p instanceof EntityPlayerSP && Wrapper.mc().gameSettings.thirdPersonView != 0 && Wrapper.mc().gameSettings.thirdPersonView != 3) && (p.isEntityAlive())) {
				double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
				double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
				double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
				if (!p.getName().startsWith("Body #")) {
					renderNametag(p, pX, pY, pZ);
				}
			}
		}
	}

	public static void renderNametag(EntityPlayer player, double x, double y, double z) {
		GL11.glPushMatrix();
		FontRenderer var13 = mc.fontRendererObj;
		String name = getNametagName(player);

		float distance = mc.thePlayer.getDistanceToEntity(player);
		float var15 = (distance / 5 <= 2 ? 2.0F : distance / 5) * 2.5f;
		float var14 = 0.016666668F * getNametagSize(player);

		GL11.glTranslated((float) x, (float) y + 2.5D, (float) z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-var14, -var14, var14);

		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GL11.glDisable(2929);
		int width = var13.getStringWidth(name) / 2;
		Wrapper.drawBorderRect(-width - 2, 0, width + 1, 9.5f, 0x60000000, 0x60000000, 0.2f);
		Gui.drawString(Wrapper.fr(), name, -width, 1, getNametagColor(player));
		int xOffset = 0;
		for (ItemStack armourStack : player.inventory.armorInventory) {
			if (armourStack != null) {
				xOffset -= 8;
			}
		}
		Object renderStack;
		if (player.getHeldItem() != null) {
			xOffset -= 8;
			renderStack = player.getHeldItem().copy();
			if ((((ItemStack) renderStack).hasEffect()) && (((((ItemStack) renderStack).getItem() instanceof ItemTool)) || ((((ItemStack) renderStack).getItem() instanceof ItemArmor)))) {
				((ItemStack) renderStack).stackSize = 1;
			}
			rendertheitemsandshit((ItemStack) renderStack, xOffset, -26);
			xOffset += 16;
		}
		for (ItemStack armourStack : player.inventory.armorInventory) {
			if (armourStack != null) {
				ItemStack renderStack1 = armourStack.copy();
				if ((renderStack1.hasEffect()) && (((renderStack1.getItem() instanceof ItemTool)) || ((renderStack1.getItem() instanceof ItemArmor)))) {
					renderStack1.stackSize = 1;
				}
				rendertheitemsandshit(renderStack1, xOffset, -26);
				xOffset += 16;
			}
		}

		GL11.glEnable(2929);
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopMatrix();
	}

	public static String getHealthColor(int health) {
		if (health > 15) {
			return "2";
		}
		if (health > 10) {
			return "e";
		}
		if (health > 5) {
			return "6";
		}
		return "4";
	}

	public static int getNametagColor(EntityLivingBase player) {
		if (Protocol.getFriendManager().isFriend(player.getName())) {
			return 0xff00ffff;
		}
		if (player.isInvisible()) {
			return 0xffffaa00;
		}
		if (player.isSneaking()) {
			return 0xffff4040;
		}
		return -1;
	}

	public static String getNametagName(EntityLivingBase player) {
		String name = Protocol.getFriendManager().isFriend(player.getName()) ? Protocol.getFriendManager().getAlias(player.getName()) : player.getDisplayName().getFormattedText().replace(player.getName(), "") + player.getName();
		double health = (int) (player.getHealth() * 5);
		String healthstring = health + "%";
		return "§r" + name + " §" + getHealthColor((int) player.getHealth()) + healthstring.replace(".0", "");
	}

	public static float getNametagSize(EntityLivingBase player) {
		// return mc.thePlayer.getDistanceToEntity(player) / 4.0F <= 2.0F ? 2.0F
		// : mc.thePlayer.getDistanceToEntity(player) / 4.0F;
		Entity ent = mc.thePlayer;
		float dist = ent.getDistanceToEntity(player) / 2.0F;
		float size = dist <= 2.0F ? 2.2F : dist;
		return size;
	}

	public static void rendertheitemsandshit(ItemStack stack, int x, int y) {
		GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GlStateManager.clear(256);
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		mc.getRenderItem().zLevel = -100.0F;
		GlStateManager.scale(1, 1, 0.01f);
		mc.getRenderItem().memes(stack, x, y + 8);
		mc.getRenderItem().func_175030_a(mc.fontRendererObj, stack, x, y + 8);
		mc.getRenderItem().zLevel = 0.0F;
		GlStateManager.scale(1, 1, 1);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.disableDepth();
		renderEnchantText(stack, x, y);
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		GL11.glPopMatrix();
	}

	public static void renderEnchantText(ItemStack stack, int x, int y) {
		int encY = y - 24;
		if (stack.getItem() instanceof ItemArmor) {
			Gui.drawString(Wrapper.fr(), (stack.getMaxDamage() - stack.getItemDamage()) + "", x * 2 + 8, y + 15, -1);
		}
		NBTTagList enchants = stack.getEnchantmentTagList();
		if (enchants != null) {
			if(enchants.tagCount() >= 5){
				Gui.drawString(Wrapper.fr(), "God", x * 2, encY + 15, 0xffff4040);
				return;
			}
			for (int index = 0; index < enchants.tagCount(); ++index) {
				short id = enchants.getCompoundTagAt(index).getShort("id");
				short level = enchants.getCompoundTagAt(index).getShort("lvl");
				Enchantment enc = Enchantment.func_180306_c(id);
				if (enc != null) {
					String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
					encName = encName + level;
					GL11.glPushMatrix();
					GL11.glScalef(0.9f, 0.9f, 0);
					Gui.drawString(mc.fontRendererObj, encName, x * 2, encY + 4, -1);
					GL11.glScalef(1f, 1f, 1);
					GL11.glPopMatrix();
					encY += 8;
				}
			}
		}
	}
}
