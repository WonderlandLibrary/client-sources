package us.dev.direkt.module.internal.render;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorForge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.game.render.EventRenderNametag;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.internal.core.listeners.EntityPositionListener;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.direkt.util.client.PlayerUtils;
import us.dev.direkt.util.render.OGLStateManager;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Name Tags", category = ModCategory.RENDER)
public class NameTags extends ToggleableModule {

    @Exposed(description = "The display mode for an entity's health")
	private final Property<Mode> mode = new Property<>("HP Mode", Mode.PERCENT);

    @Exposed(description = "The nametag components to be shown")
	private final MultiProperty<Boolean> nametagOptions = new MultiProperty.Builder<Boolean>("Info")
			.put(new Property<>("Items", true))
			.put(new Property<>("Health", true))
			.put(new Property<>("Game Mode", false))
			.put(new Property<>("Air", false))
			.build();

	private Gui gui = new Gui();

	private ResourceLocation resource;

    private final ImmutableMap<String, String> cachedEnchantmentMap = new ImmutableMap.Builder<String, String>()
    		
    		// http://www.minecraftforum.net/forums/minecraft-discussion/creative-mode/365638-enchantment-id-list
    		
    		// Armor
            .put(Enchantment.getEnchantmentByID(0).getName(),  "p")
            .put(Enchantment.getEnchantmentByID(1).getName(),  "fp")
            .put(Enchantment.getEnchantmentByID(2).getName(),  "ff")
            .put(Enchantment.getEnchantmentByID(3).getName(),  "bp")
            .put(Enchantment.getEnchantmentByID(4).getName(),  "pp")
            .put(Enchantment.getEnchantmentByID(5).getName(),  "r")
            .put(Enchantment.getEnchantmentByID(6).getName(),  "aa")
            .put(Enchantment.getEnchantmentByID(7).getName(),  "t")
            .put(Enchantment.getEnchantmentByID(8).getName(),  "ds")
            .put(Enchantment.getEnchantmentByID(9).getName(),  "fw")
            
            // Weapons
            .put(Enchantment.getEnchantmentByID(16).getName(), "s")
            .put(Enchantment.getEnchantmentByID(17).getName(), "sm")
            .put(Enchantment.getEnchantmentByID(18).getName(), "boa")
            .put(Enchantment.getEnchantmentByID(19).getName(), "kb")
            .put(Enchantment.getEnchantmentByID(20).getName(), "fa")
            .put(Enchantment.getEnchantmentByID(21).getName(), "l")
            
            // Tools
            .put(Enchantment.getEnchantmentByID(32).getName(), "e")
            .put(Enchantment.getEnchantmentByID(33).getName(), "st")
            .put(Enchantment.getEnchantmentByID(35).getName(), "f")
            
            // Bows
            .put(Enchantment.getEnchantmentByID(48).getName(), "pow")
            .put(Enchantment.getEnchantmentByID(49).getName(), "pun")
            .put(Enchantment.getEnchantmentByID(50).getName(), "fl")
            .put(Enchantment.getEnchantmentByID(51).getName(), "inf")
                        
            // Fishing Rods
            .put(Enchantment.getEnchantmentByID(61).getName(), "lu")
            .put(Enchantment.getEnchantmentByID(62).getName(), "lots")
            
            // Everything
            .put(Enchantment.getEnchantmentByID(34).getName(), "un")
            .put(Enchantment.getEnchantmentByID(70).getName(), "m")
            .build();
	

	@Listener
	protected Link<EventRender2D> onRender2D = new Link<>(event -> {
		double playerX = RenderManager.renderPosX();
		double playerY = RenderManager.renderPosY();
		double playerZ = RenderManager.renderPosZ();
		float partialTicks = Wrapper.getTimer().renderPartialTicks;

		GlStateManager.pushMatrix();
		
		List<EntityPlayer> playerList = Wrapper.getLoadedPlayersNoNPCs();
		Collections.sort(playerList, PlayerUtils.getPlayerDistanceComparator());
		double guiScale = 2d / event.getScaledResolution().getScaleFactor();
		
		GlStateManager.scale(guiScale, guiScale, 1);
		
		for (EntityPlayer listEntity : playerList) {
			GlStateManager.enableBlend();
			if (listEntity != Wrapper.getPlayer()) {
				Vec3d point;
				if ((point = EntityPositionListener.getEntityUpperBounds().get(listEntity)) != null) {
					double X = playerX - this.interpolate(listEntity.posX, listEntity.lastTickPosX, partialTicks);
					double Y = playerY - this.interpolate(listEntity.posY, listEntity.lastTickPosY, partialTicks);
					double Z = playerZ - this.interpolate(listEntity.posZ, listEntity.lastTickPosZ, partialTicks);
					
					if (Wrapper.getGameSettings().thirdPersonView != 0) {
						double distance = 4;
						double rotation = Wrapper.getPlayer().rotationYaw;
						Vec3d position = new Vec3d(playerX, playerY, playerZ);
						Vec3d look = Wrapper.getPlayer().getLook(Wrapper.getTimer().renderPartialTicks);
						if (Wrapper.getGameSettings().thirdPersonView == 2) {
							look = new Vec3d(-look.xCoord, -look.yCoord, -look.zCoord);
						}
						RayTraceResult rayTrace = Wrapper.getWorld().rayTraceBlocks(new Vec3d(playerX + look.xCoord * distance, playerY + look.yCoord * distance, playerZ + look.zCoord * distance), position);
						if (rayTrace != null) {
							double rayTraceDistance = rayTrace.hitVec.distanceTo(position);
							if (rayTraceDistance < distance) {
								distance = rayTraceDistance;
							}
						}
						playerX -= look.xCoord * distance;
						playerY -= look.yCoord * distance;
						playerZ -= look.zCoord * distance;
					}
					
					double distance = Math.sqrt(X * X + Y * Y + Z * Z);
					double scale = 6.0 / distance;

					if (distance < 6 /*&& Wrapper.getGameSettings().thirdPersonView == 0*/) {
						point = new Vec3d(point.xCoord / scale, point.yCoord / scale, point.zCoord);
						GlStateManager.scale(scale, scale, 1);
					}

					if (point.zCoord >= 0 && point.zCoord < 1) {
						GlStateManager.pushMatrix();
						GlStateManager.translate(0, 0, 100 - (distance / 200f));
						float y = (float) ((point.yCoord / 2) - (Wrapper.getFontRenderer().FONT_HEIGHT / 2f));

						GL11.glColor4f(1, 1, 1, 1);
						this.renderTag(listEntity, point, y - 10);

						GL11.glColor4f(1, 1, 1, 1);
						List<ItemStack> items = Lists.newArrayList();
						if (nametagOptions.getValue("Items").getValue()) {
							ItemStack item = listEntity.getHeldItemMainhand();
							
							if (item != null)
								items.add(item);
							
							for (int i = 3; i > -1; i--) {
								item = listEntity.inventory.armorInventory[i];
								
								if (item != null)
									items.add(item);
							}
							
							item = listEntity.getHeldItemOffhand();
							
							if (item != null)
								items.add(item);
							
							this.renderItems(listEntity, items, point, y - 12);
						}

						GL11.glColor4f(1, 1, 1, 1);

						GlStateManager.popMatrix();
					}
					if (distance < 6 /*&& Wrapper.getGameSettings().thirdPersonView == 0*/) {
						GlStateManager.scale(1 / scale, 1 / scale, 1);
					}
				}
			}
		}

		GlStateManager.scale(1 / guiScale, 1 / guiScale, 1);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	});

	@Listener
	protected Link<EventRenderNametag> onRenderNameTags = new Link<>(event -> {
		if (event.getRenderEntity() instanceof EntityPlayer) {
			if (Wrapper.getLoadedPlayersNoNPCs().contains(event.getRenderEntity()))
				event.setCancelled(true);
		}
	});

	private void renderTag(EntityPlayer listEntity, Vec3d point, float y) {
		float x = (float) ((point.xCoord) / 2);
		int nameColor = 0xFFFFFF;
		
		String nameText = listEntity.getDisplayName().getFormattedText();
		
		nameText = nameText.replaceAll(ChatFormatting.OBFUSCATED.toString(), "");
		
		if (Direkt.getInstance().getFriendManager().isFriend(listEntity)) {
			nameText = Direkt.getInstance().getFriendManager().getAlias(listEntity.getName());
			nameColor = 0xFF00CCFF;
		}
		else if (listEntity.isSneaking()) {
			nameColor = 0xFFFC0000;
		}
		else if (listEntity.isInvisible()) {
			nameColor = 0x808080;
		}
		else if (!listEntity.canEntityBeSeen(Wrapper.getPlayer()) && (Wrapper.getPlayer().isSneaking())) {
			nameColor = 0xFF00FF00;
		}
		
		if(nameColor != 0xFFFFFF)
			nameText = ChatFormatting.stripFormatting(nameText);
		
		String gameType = "";

		if (Wrapper.getPlayer().connection.getPlayerInfo(listEntity.getUniqueID()) != null && nametagOptions.getValue("Game Mode").getValue())
			gameType = Wrapper.getPlayer().connection.getPlayerInfo(listEntity.getUniqueID()).getGameType().toString().replace("SURVIVAL", "[S]").replace("ADVENTURE", "[A]").replace("CREATIVE", "[C]").replace("SPECTATOR", "[SP]").replace("NOT_SET", "[?]") + " ";

		String healthText = "";
		
		switch (this.mode.getValue()) {
		case HEALTH:
			healthText = (" " + new DecimalFormat("0").format(listEntity.getHealth()));
			break;
		case PERCENT:
			healthText = ((" " + new DecimalFormat("0.0").format(listEntity.getHealth() * 5) + "%").replace(".0", ""));
			break;
		case HEARTS:
			healthText = " " + String.valueOf(Math.round(listEntity.getHealth()) / 2.0).replace(".0", "");
			break;
		}

		String airText = "";
		
		if (nametagOptions.getValue("Air").getValue() && listEntity.getAir() < 300) {
			airText = listEntity.getAir() > 0 ? " " + (new DecimalFormat("0.0").format((((float) listEntity.getAir() / 30))).replace(".0", "")) : " " + 0;
		}

		float totalWidth = (Wrapper.getFontRenderer().getStringWidth(gameType + nameText) / 2) + (nametagOptions.getValue("Health").getValue() ? Wrapper.getFontRenderer().getStringWidth(healthText) / 2 : 0) + (nametagOptions.getValue("Air").getValue() ? Wrapper.getFontRenderer().getStringWidth(airText) / 2 : 0) + 0.5F;

		GlStateManager.enableAlpha();
		OGLStateManager.drawBorderedRect((x - (totalWidth)) - 2.5F, (y - 2), (x + totalWidth) + 1, (y + Wrapper.getFontRenderer().FONT_HEIGHT), 0.5f, 0x70000000, 0xaf241c06);
		GlStateManager.disableAlpha();
		Wrapper.getFontRenderer().drawStringWithShadow(gameType + nameText, (x - (totalWidth) - 1), y, nameColor);

		if (nametagOptions.getValue("Health").getValue()) {
			Wrapper.getFontRenderer().drawStringWithShadow(healthText, (x - (totalWidth) + Wrapper.getFontRenderer().getStringWidth(gameType + nameText)), y, getHealthColor(listEntity));
		}

		if (nametagOptions.getValue("Air").getValue() && listEntity.getAir() < 300) {
			Wrapper.getFontRenderer().drawStringWithShadow(airText, (x - (totalWidth - (nametagOptions.getValue("Health").getValue() ? Wrapper.getFontRenderer().getStringWidth(healthText) / 2 : 0) * 2) + Wrapper.getFontRenderer().getStringWidth(gameType + nameText)), y, 0xFF00FFFF);
		}

		// GlStateManager.doPolygonOffset(1, 1000000);
		// GlStateManager.disablePolygonOffset();
	}

	private void renderItems(EntityPlayer listEntity, List<ItemStack> items, Vec3d point, float y) {
		if (items.size() > 0) {
			double ax = (point.xCoord - (items.size() * 16));
			double ay = (y * 2 - 34) - (2);
			
			GlStateManager.pushMatrix();
			GlStateManager.scale(1, 1, 1);
			RenderHelper.enableGUIStandardItemLighting();
			
			for (int i = 0; i < items.size(); i++) {
				double xx = roundInc(ax + (i * 32), 1);
				double yy = roundInc(ay, 1);

				GlStateManager.pushMatrix();
				GlStateManager.translate(xx / 2f, yy / 2f, 0);
				ItemStack stack = items.get(i);

				GlStateManager.depthMask(false);
				Wrapper.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(listEntity, stack, 0, 0);
				GlStateManager.depthMask(true);
				GlStateManager.enablePolygonOffset();
				GlStateManager.doPolygonOffset(1, 1000000);
				Wrapper.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(listEntity, stack, 0, 0);
				GlStateManager.doPolygonOffset(1, -1000000);
				GlStateManager.disablePolygonOffset();
				this.renderItemOverlayIntoGUI(Wrapper.getFontRenderer(), stack, 0, 0, stack.stackSize > 1 ? "" + stack.stackSize : "");
				
				GlStateManager.scale(0.5, 0.5, 0.5);
				// GlStateManager.disableDepth();
				GlStateManager.enableBlend();
				GlStateManager.disableLighting();

				NBTTagList enchants = stack.getEnchantmentTagList();
				

				if (stack.getItem() == Items.GOLDEN_APPLE && stack.getMetadata() == 1) {
					OGLStateManager.drawRect(-1, 14, Wrapper.getFontRenderer().getStringWidth("op") + 1, 24, 0x80241c06);
					Wrapper.getFontRenderer().drawStringWithShadow("op", 0, 15, 0xFFFF0000);
				}
				if (enchants != null) {
					int ency = 8;
					for (int index = 0; index < enchants.tagCount(); ++index) {
						short id = enchants.getCompoundTagAt(index).getShort("id");
						short level = enchants.getCompoundTagAt(index).getShort("lvl");
						Enchantment enc = Enchantment.getEnchantmentByID(id);

						if (enc != null) {
							String encName = this.cachedEnchantmentMap.get(enc.getName()) + level;
							OGLStateManager.drawRect(2, (ency * 2) - 2, Wrapper.getFontRenderer().getStringWidth(encName) + 4, (ency * 2) + 8, 0x80241c06);
							Wrapper.getFontRenderer().drawStringWithShadow(encName, 3, (ency) * 2 - 1, 0xFFffb900);
							ency -= 5;
						}
					}
				}
				GlStateManager.enableLighting();
				GlStateManager.disableBlend();
				// GlStateManager.enableDepth();
				GlStateManager.scale(2, 2, 2);

				GlStateManager.popMatrix();
			}
			
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}

	private static double roundInc(double val, double inc) {
		return (val * (1f / inc)) / (1f / inc);
	}

	private static int getHealthColor(EntityPlayer player) {
		int var8 = (int) Math.round(255.0D - (Double.valueOf(getHealth(player))) * 255.0D / (double) player.getMaxHealth());
		return 255 - var8 << 8 | var8 << 16;
	}

	private static String getHealth(EntityLivingBase entity) {
		return "" + (int) Math.ceil(entity.getHealth());
	}
	
	// TODO: move somewhere else?
    private double interpolate(final double now, final double then, final double percent) {
        return then + (now - then) * percent;
    }
    
	public enum Mode {
		PERCENT("Percent"), HEALTH("Health"), HEARTS("Hearts");

        final String name;
        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
	}
	
    /**
     * Renders the stack size and/or damage bar for the given ItemStack.
     */
    public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text)
    {
        if (stack != null)
        {
            if (stack.stackSize != 1 || text != null)
            {
                String s = text == null ? String.valueOf(stack.stackSize) : text;

                if (text == null && stack.stackSize < 1)
                {
                    s = TextFormatting.RED + String.valueOf(stack.stackSize);
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fr.drawStringWithShadow(s, (float)(xPosition + 19 - 2 - fr.getStringWidth(s)), (float)(yPosition + 6 + 3), 16777215);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

            if (ReflectorForge.isItemDamaged(stack))
            {
                int j = (int)Math.round(13.0D - (double)stack.getItemDamage() * 13.0D / (double)stack.getMaxDamage());
                int i = (int)Math.round(255.0D - (double)stack.getItemDamage() * 255.0D / (double)stack.getMaxDamage());

                if (Reflector.ForgeItem_getDurabilityForDisplay.exists())
                {
                    double d0 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, stack);
                    j = (int)Math.round(13.0D - d0 * 13.0D);
                    i = (int)Math.round(255.0D - d0 * 255.0D);
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer vertexbuffer = tessellator.getBuffer();
                this.draw(vertexbuffer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
                this.draw(vertexbuffer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
                this.draw(vertexbuffer, xPosition + 2, yPosition + 13, j, 1, 255 - i, i, 0, 255);
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    /**
     * Draw with the WorldRenderer
     */
    private void draw(VertexBuffer renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }
	
}