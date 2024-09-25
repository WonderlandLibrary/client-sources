package none.module.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import none.Client;
import none.event.Event;
import none.event.EventSystem;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventNametagRender;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Antibot;
import none.module.modules.combat.AuraTeams;
import none.module.modules.world.Murder;
import none.utils.RenderingUtil;
import none.utils.RotationUtils;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;

public class NameTags extends Module {

	public NameTags() {
		super("NameTags", "NameTags", Category.RENDER, Keyboard.KEY_NONE);
	}

	public BooleanValue Health = new BooleanValue("Health", true);
	public BooleanValue Armor = new BooleanValue("Armor", false);
	public BooleanValue Invisible = new BooleanValue("Invisible", false);

	private boolean hideInvisibles;
	private double gradualFOVModifier;
	private Character formatChar = new Character('\247');
	public static Map<EntityLivingBase, double[]> entityPositions = new HashMap();

	@Override
	@RegisterEvent(events = { EventNametagRender.class, Event3D.class, Event2D.class })
	public void onEvent(Event event) {

		if (mc.thePlayer.ticksExisted <= 1) {
			entityPositions.clear();
		}

		Event enr = EventSystem.getInstance(EventNametagRender.class);
		if (!isEnabled()) {
			enr.setCancelled(false);
			return;
		} else {
			enr.setCancelled(true);
		}

		if (event instanceof Event3D) {
			try {
				updatePositions();
			} catch (Exception e) {

			}
		}

		if (event instanceof Event2D) {
			Event2D er = (Event2D) event;
			GlStateManager.pushMatrix();
			ScaledResolution scaledRes = new ScaledResolution(mc);

			for (Entity ent : entityPositions.keySet()) {
				if (ent != mc.thePlayer && Invisible.getObject() || !ent.isInvisible()) {

					GlStateManager.pushMatrix();
					if ((ent instanceof EntityPlayer)) {
						String str = ent.getDisplayName().getFormattedText();
						String name = ent.getName();
						EntityPlayer entityplayer = (EntityPlayer) ent;

						int renderColor = Colors.getColor(255, 255, 255, 200);

						if (Client.instance.moduleManager.auraTeams.isEnabled()) {
							if (AuraTeams.player.contains(entityplayer)) {
								str = ChatFormatting.GREEN + "AuraTeams";
							}
						}

						if (Client.nameList.contains(entityplayer.getName())) {
							str = "NoneTK" + ChatFormatting.WHITE + ":" + ChatFormatting.DARK_BLUE + "Dev";
							renderColor = ClientColor.rainbow(100);
						} else if (entityplayer.getGameProfile().getName().equalsIgnoreCase("HaKu_V3")) {
							str = ent.getDisplayName().getFormattedText() + ChatFormatting.WHITE + ":"
									+ ChatFormatting.BLACK + "Mr.NoName";
						} else if (entityplayer.getGameProfile().getName().equalsIgnoreCase("Sirasora")) {
							str = ent.getDisplayName().getFormattedText() + ChatFormatting.WHITE + ":"
									+ ChatFormatting.BLUE + "TheSmokey";
						} else if (entityplayer.getGameProfile().getName().equalsIgnoreCase("ZeezaGamer")) {
							str = ent.getDisplayName().getFormattedText() + ChatFormatting.WHITE + ":"
									+ ChatFormatting.RED + "l" + ChatFormatting.GOLD + "n" + ChatFormatting.YELLOW + "W"
									+ ChatFormatting.GREEN + "T" + ChatFormatting.BLUE + "r" + ChatFormatting.DARK_BLUE
									+ "u" + ChatFormatting.DARK_PURPLE + "e";
						} else if (entityplayer.getGameProfile().getName().equalsIgnoreCase("xLinkLeto_CHx")) {
							str = ent.getDisplayName().getFormattedText() + ChatFormatting.WHITE + ":"
									+ ChatFormatting.RED + "l" + ChatFormatting.GOLD + "n" + ChatFormatting.YELLOW + "W"
									+ ChatFormatting.GREEN + "T" + ChatFormatting.BLUE + "r" + ChatFormatting.DARK_BLUE
									+ "u" + ChatFormatting.DARK_PURPLE + "e";
						} else if (Antibot.getInvalid().contains(entityplayer)) {
							str = ChatFormatting.RED + "B" + ChatFormatting.GREEN + "o" + ChatFormatting.BLUE + "t"
									+ ChatFormatting.BLACK + "s";
						} else if (Client.instance.moduleManager.murder.isEnabled()
								&& entityplayer.isMurderer) {
							str = ChatFormatting.DARK_RED + "Murder";
						} else if (FriendManager.isFriend(name) && !(ent instanceof EntityPlayerSP)) {
							str = ChatFormatting.BLUE + "Friend";
						}

						double[] renderPositions = entityPositions.get(ent);
						if ((renderPositions[3] < 0.0D) || (renderPositions[3] >= 1.0D)) {
							GlStateManager.popMatrix();
							continue;
						}
						TTFFontRenderer font = Client.fm.getFont("JIGR 19");
						GlStateManager.translate(renderPositions[0] / scaledRes.getScaleFactor(),
								renderPositions[1] / scaledRes.getScaleFactor(), 0.0D);
						scale();
						String healthInfo = (int) ((EntityLivingBase) ent).getHealth() + "";
						GlStateManager.translate(0.0D, -2.5D, 0.0D);
						float strWidth = font.getStringWidth(str);
						float strWidth2 = font.getStringWidth(healthInfo);
						RenderingUtil.rectangle(-strWidth / 2 - 1, -10.0D, strWidth / 2 + 1, 0,
								Colors.getColor(0, 130));
						int x3 = ((int) (renderPositions[0] + -strWidth / 2 - 3) / 2) - 26;
						int x4 = ((int) (renderPositions[0] + strWidth / 2 + 3) / 2) + 20;
						int y1 = ((int) (renderPositions[1] + -30) / 2);
						int y2 = ((int) (renderPositions[1] + 11) / 2);
						int mouseY = (scaledRes.getScaledHeight() / 2);
						int mouseX = (scaledRes.getScaledWidth() / 2);
						font.drawStringWithShadow(str, -strWidth / 2, -7.0F, renderColor);
						boolean healthOption = !Health.getObject();
						boolean armor = !Armor.getObject();
						boolean hovered = x3 < mouseX && mouseX < x4 && y1 < mouseY && mouseY < y2;
						if (!healthOption || hovered) {
							float health = ((EntityPlayer) ent).getHealth();
							float[] fractions = new float[] { 0f, 0.5f, 1f };
							Color[] colors = new Color[] { Color.RED, Color.YELLOW, Color.GREEN };
							float progress = (health * 5) * 0.01f;
							Color customColor = Esp.blendColors(fractions, colors, progress).brighter();
							try {
								RenderingUtil.rectangle(strWidth / 2 + 2, -10.0D, strWidth / 2 + 1 + strWidth2, 0,
										Colors.getColor(0, 130));
								font.drawStringWithShadow(healthInfo, strWidth / 2 + 2, (int) -7.0D,
										customColor.getRGB());
							} catch (Exception e) {

							}
						}
						if (hovered || !armor) {
							List<ItemStack> itemsToRender = new ArrayList<>();
							for (int i = 0; i < 5; i++) {
								ItemStack stack = ((EntityPlayer) ent).getEquipmentInSlot(i);
								if (stack != null) {
									itemsToRender.add(stack);
								}
							}
							int x = -(itemsToRender.size() * 9);
							for (ItemStack stack : itemsToRender) {
								RenderHelper.enableGUIStandardItemLighting();
								mc.getRenderItem().renderItemIntoGUI(stack, x, -27);
								mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, -27);
								x += 20;
								RenderHelper.disableStandardItemLighting();
							}
						}
					}
					GlStateManager.popMatrix();
				}
			}
			GlStateManager.popMatrix();
		}
	}

	private String getColor(int level) {
		if (level == 1) {

		} else if (level == 2) {
			return "\247a";
		} else if (level == 3) {
			return "\2473";
		} else if (level == 4) {
			return "\2474";
		} else if (level >= 5) {
			return "\2476";
		}
		return "\247f";
	}

	private void drawEnchantTag(String text, int x, int y) {
		GlStateManager.pushMatrix();
		GlStateManager.disableDepth();
		x = (int) (x * 1.75D);
		y -= 4;
		GL11.glScalef(0.57F, 0.57F, 0.57F);
		mc.fontRendererObj.drawStringWithShadow(text, x, -30 - y, Colors.getColor(255));
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

	private void scale() {
		float scale = 1;
		scale *= ((mc.currentScreen == null) && (GameSettings.isKeyDown(mc.gameSettings.ofKeyBindZoom)) ? 2 : 1);
		GlStateManager.scale(scale, scale, scale);
	}

	private void updatePositions() {
		entityPositions.clear();
		float pTicks = mc.timer.renderPartialTicks;
		for (Object o : mc.theWorld.loadedEntityList) {
			Entity ent = (Entity) o;
			if ((ent != mc.thePlayer) && ((ent instanceof EntityPlayer))
					&& ((!ent.isInvisible()) || (!this.hideInvisibles))) {
				double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - mc.getRenderManager().viewerPosX;
				double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY;
				double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - mc.getRenderManager().viewerPosZ;
				y += ent.height + 0.2D;
				if ((convertTo2D(x, y, z)[2] >= 0.0D) && (convertTo2D(x, y, z)[2] < 1.0D)) {
					entityPositions.put((EntityPlayer) ent,
							new double[] { convertTo2D(x, y, z)[0], convertTo2D(x, y, z)[1],
									Math.abs(convertTo2D(x, y + 1.0D, z, ent)[1] - convertTo2D(x, y, z, ent)[1]),
									convertTo2D(x, y, z)[2] });
				}
			}
		}
	}

	private double[] convertTo2D(double x, double y, double z, Entity ent) {
		float pTicks = mc.timer.renderPartialTicks;
		float prevYaw = mc.thePlayer.rotationYaw;
		float prevPrevYaw = mc.thePlayer.prevRotationYaw;
		float[] rotations = RotationUtils.getRotationFromPosition(
				ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks,
				ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks,
				ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - 1.6D);
		mc.getRenderViewEntity().rotationYaw = (mc.getRenderViewEntity().prevRotationYaw = rotations[0]);
		mc.entityRenderer.setupCameraTransform(pTicks, 0);
		double[] convertedPoints = convertTo2D(x, y, z);
		mc.getRenderViewEntity().rotationYaw = prevYaw;
		mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
		mc.entityRenderer.setupCameraTransform(pTicks, 0);
		return convertedPoints;
	}

	private double[] convertTo2D(double x, double y, double z) {
		FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(2982, modelView);
		GL11.glGetFloat(2983, projection);
		GL11.glGetInteger(2978, viewport);
		boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
		if (result) {
			return new double[] { screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2) };
		}
		return null;
	}

}
