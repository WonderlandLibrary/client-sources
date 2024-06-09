package net.minecraft.client.triton.impl.modules.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.NametagRenderEvent;
import net.minecraft.client.triton.management.event.events.Render3DEvent;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.MathUtils;
import net.minecraft.client.triton.utils.minecraft.FontRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IChatComponent;

import org.lwjgl.opengl.GL11;

@Module.Mod(shown = false, displayName = "Nametags")
public class Tags extends Module {
	@Op(min = 1.0D, max = 20.0D, increment = 1.0D, name = "Distance")
	private double distance = 8.0D;
	@Op(min = 0.0D, max = 2.0D, increment = 0.1D, name = "Scale")
	private double scale = 0.1D;
	@Op(name = "Armor")
	private boolean armor = true;
	private Character formatChar = new Character('§');
	public static Map<EntityLivingBase, double[]> entityPositions = new HashMap();

	@EventTarget(3)
	private void onRender3DEvent(Render3DEvent event) {
		for (Object o : ClientUtils.mc().theWorld.loadedEntityList) {
			Entity ent = (Entity) o;
			if (ent != ClientUtils.mc().thePlayer) {
				if ((ent instanceof EntityPlayer)) {
					double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.getPartialTicks()
							- RenderManager.renderPosX;
					double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.getPartialTicks()
							- RenderManager.renderPosY + ent.height + 0.5D;
					double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.getPartialTicks()
							- RenderManager.renderPosZ;
					String str = ent.getDisplayName().getFormattedText();
					if (ent.isSneaking()) {
						str = str.replace(ent.getName(), this.formatChar + "4" + this.formatChar + "l" + ent.getName());
					}
					if (FriendManager.isFriend(ent.getName())) {
						str = str.replace(ent.getName(),
								this.formatChar + "b" + FriendManager.getAliasName(ent.getName()));
					}
					if (EnemyManager.isEnemy(ent.getName())) {
						str = str.replace(ent.getName(), this.formatChar + "c" + this.formatChar + "o"
								+ EnemyManager.getAliasName(ent.getName()));
					}
					String colorString = this.formatChar.toString();
					double health = MathUtils.roundToPlace(((EntityPlayer) ent).getHealth(), 1);
					if (health >= 12.0D) {
						colorString = colorString + "2";
					} else if (health >= 4.0D) {
						colorString = colorString + "6";
					} else {
						colorString = colorString + "4";
					}
					str = str + colorString + " \u2764" + health;

					float dist = ClientUtils.mc().thePlayer.getDistanceToEntity(ent);
					float scale = 0.02672F;
					float factor = (float) (dist <= this.distance ? this.distance * this.scale : dist * this.scale);
					scale *= factor;
					GlStateManager.pushMatrix();
					GlStateManager.disableDepth();
					GlStateManager.translate(posX, posY, posZ);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(-ClientUtils.mc().renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(ClientUtils.mc().renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					GlStateManager.scale(-scale, -scale, scale);

					GlStateManager.disableLighting();
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
					Tessellator tessellator = Tessellator.getInstance();
					WorldRenderer worldRenderer = tessellator.getWorldRenderer();
					GlStateManager.disableTexture2D();
					worldRenderer.startDrawingQuads();
					int stringWidth = ClientUtils.clientFont().getStringWidth(str) / 2;

					GL11.glColor3f(0.0F, 0.0F, 0.0F);
					GL11.glLineWidth(1.0E-6F);
					GL11.glBegin(3);
					GL11.glVertex2d(-stringWidth - 2, -0.8D);
					GL11.glVertex2d(-stringWidth - 2, 8.8D);
					GL11.glVertex2d(-stringWidth - 2, 8.8D);
					GL11.glVertex2d(stringWidth + 2, 8.8D);
					GL11.glVertex2d(stringWidth + 2, 8.8D);
					GL11.glVertex2d(stringWidth + 2, -0.8D);
					GL11.glVertex2d(stringWidth + 2, -0.8D);
					GL11.glVertex2d(-stringWidth - 2, -0.8D);
					GL11.glEnd();

					worldRenderer.setColorRGBA_I(0, 100);
					worldRenderer.addVertex(-stringWidth - 2, -0.8D, 0.0D);
					worldRenderer.addVertex(-stringWidth - 2, 8.8D, 0.0D);
					worldRenderer.addVertex(stringWidth + 2, 8.8D, 0.0D);
					worldRenderer.addVertex(stringWidth + 2, -0.8D, 0.0D);
					tessellator.draw();
					GlStateManager.enableTexture2D();

					ClientUtils.clientFont().drawString(str, -ClientUtils.clientFont().getStringWidth(str) / 2, 0.0D,
							-1);
					GlStateManager.enableLighting();
					GlStateManager.enableDepth();
					if ((this.armor) && (ClientUtils.player().getDistanceSqToEntity(ent) <= 1600.0D)) {
						int xOffset = 0;
						ItemStack[] arrayOfItemStack1;
						int j = (arrayOfItemStack1 = ((EntityPlayer) ent).inventory.armorInventory).length;
						for (int i = 0; i < j; i++) {
							ItemStack armourStack = arrayOfItemStack1[i];
							if (armourStack != null) {
								xOffset -= 8;
							}
						}
						if (((EntityPlayer) ent).getHeldItem() != null) {
							xOffset -= 8;
							Object renderStack = ((EntityPlayer) ent).getHeldItem().copy();
							if ((((ItemStack) renderStack).hasEffect())
									&& (((((ItemStack) renderStack).getItem() instanceof ItemTool))
											|| ((((ItemStack) renderStack).getItem() instanceof ItemArmor)))) {
								((ItemStack) renderStack).stackSize = 1;
							}
							renderItemStack((ItemStack) renderStack, xOffset, -20);
							xOffset += 16;
						}
						ItemStack[] arrayOfItemStack2;
						int k = (arrayOfItemStack2 = ((EntityPlayer) ent).inventory.armorInventory).length;
						for (j = 0; j < k; j++) {
							ItemStack armourStack = arrayOfItemStack2[j];
							if (armourStack != null) {
								ItemStack renderStack1 = armourStack.copy();
								if ((renderStack1.hasEffect()) && (((renderStack1.getItem() instanceof ItemTool))
										|| ((renderStack1.getItem() instanceof ItemArmor)))) {
									renderStack1.stackSize = 1;
								}
								renderItemStack(renderStack1, xOffset, -20);
								xOffset += 16;
							}
						}
					}
					GlStateManager.popMatrix();
				}
				GlStateManager.disableBlend();
			}
		}
	}

	@EventTarget
	private void onNametagRender(NametagRenderEvent event) {
		event.setCancelled(true);
	}

	public void renderItemStack(ItemStack stack, int x, int y) {
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.clear(256);
		ClientUtils.mc().getRenderItem().zLevel = -150.0f;
		ClientUtils.mc().getRenderItem().func_180450_b(stack, x, y);
		ClientUtils.mc().getRenderItem().func_175030_a(ClientUtils.mc().fontRendererObj, stack, x, y);
		ClientUtils.mc().getRenderItem().zLevel = 0.0f;
		GlStateManager.disableBlend();
		GlStateManager.scale(0.5, 0.5, 0.5);
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		this.renderEnchantText(stack, x, y);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0f, 2.0f, 2.0f);
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}

	public void renderEnchantText(ItemStack stack, int x, int y) {
		int encY = y - 24;
		if ((stack.getItem() instanceof ItemArmor)) {
			int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
			int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
			int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (pLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "p" + pLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (tLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "t" + tLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (uLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "u" + uLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (stack.getMaxDamage() - stack.getItemDamage() < stack.getMaxDamage()) {
				ClientUtils.clientFont().drawStringWithShadow((stack.getMaxDamage() - stack.getItemDamage()) + "",
						x * 2, encY + 4, 0xFFff9999);
			}
		}
		if ((stack.getItem() instanceof ItemBow)) {
			int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
			int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (sLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "d" + sLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (kLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "k" + kLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (fLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "f" + fLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (uLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "u" + uLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
		}
		if ((stack.getItem() instanceof ItemSword)) {
			int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
			int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
			int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
			int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
			if (sLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "s" + sLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (kLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "k" + kLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (fLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "f" + fLevel, x * 2, encY,
						16777215);
				encY += 7;
			}
			if (uLevel > 0) {
				ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "f" + "u" + uLevel, x * 2, encY,
						16777215);
			}
		}
	}
}
