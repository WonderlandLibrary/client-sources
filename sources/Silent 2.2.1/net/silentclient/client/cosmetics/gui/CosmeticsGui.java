package net.silentclient.client.cosmetics.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.SelectedButtonTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.settings.CosmeticsMod;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.ScrollHelper;
import net.silentclient.client.utils.Sounds;
import net.silentclient.client.utils.types.PlayerResponse.Account.Cosmetics.CosmeticItem;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class CosmeticsGui extends SilentScreen {
	public static String selectedCategory = "capes";
	private int rotate = 0;
	private ScrollHelper scrollHelper = new ScrollHelper();
	
	@Override
	public void initGui() {
		rotate = 144;
		defaultCursor = false;
		if(mc.thePlayer == null) {
			Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
		} else {
			MenuBlurUtils.loadBlur();
		}
		this.silentInputs.add(new Input("Search"));
		CosmeticsGui.selectedCategory = "capes";
		Client.getInstance().updateUserInformation();
		int categoryOffsetY = 25;
		int addX = 190;
		int addY = 110;

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int height = addY * 2;
		int tabId = 1;

		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Capes", false, selectedCategory == "capes" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		categoryOffsetY +=23;
		tabId++;

		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Wings", false, selectedCategory == "wings" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		categoryOffsetY +=23;
		tabId++;

		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Bandanas", false, selectedCategory == "bandanas" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		categoryOffsetY +=23;
		tabId++;

		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Hats", false, selectedCategory == "hats" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		categoryOffsetY +=23;
		tabId++;
		
		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Shields", false, selectedCategory == "shields" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		categoryOffsetY +=23;
		tabId++;

		this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 18, "Icons", false, selectedCategory == "icons" ? new SelectedButtonTheme() : new DefaultButtonTheme()));
		tabId++;

		this.buttonList.add(new Button(tabId, x + 5, (y + height) - 24, 75, 18, "Store"));

		this.buttonList.add(new Button(88, x + 380 - 140, (y + height) - 24, 135, 18, "Outfits"));
	}
	
	@Override
	public void onGuiClosed() {
		if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Background Blur").getValBoolean()) {
			Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
		}
		super.onGuiClosed();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button instanceof Button) {
			switch (button.id) {
				case 1:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "capes";
					rotate = 144;
					scrollHelper.resetScroll();
					break;
				case 2:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "wings";
					rotate = 144;
					scrollHelper.resetScroll();
					break;
				case 3:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "bandanas";
					rotate = 340;
					scrollHelper.resetScroll();
					break;
				case 4:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "hats";
					rotate = 340;
					scrollHelper.resetScroll();
					break;
				case 5:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "shields";
					rotate = 340;
					scrollHelper.resetScroll();
					break;
				case 6:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = "icons";
					scrollHelper.resetScroll();
					break;
			}

			this.silentInputs.get(0).setValue("");
		}

		if(button.id == 7) {
			try {
				Class<?> oclass = Class.forName("java.awt.Desktop");
				Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
				oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/")});
			} catch (Throwable err) {
				err.printStackTrace();
			}
		}

		if(button.id == 88) {
			mc.displayGuiScreen(new OutfitsGui(this));
		}
	}

	private ArrayList<CosmeticItem> getItems() {
		ArrayList<CosmeticItem> items;
		ArrayList<CosmeticItem> searchItems = new ArrayList<>();

		if(selectedCategory == "capes") {
			items = Client.getInstance().getCosmetics().getMyCapes();
		} else if(selectedCategory == "wings") {
			items = Client.getInstance().getCosmetics().getMyWings();
		} else if(selectedCategory == "bandanas") {
			items = Client.getInstance().getCosmetics().getMyBandanas();
		} else if(selectedCategory == "hats") {
			items = Client.getInstance().getCosmetics().getMyHats();
		} else if(selectedCategory == "shields") {
			items = Client.getInstance().getCosmetics().getMyShields();
		} else {
			items = Client.getInstance().getCosmetics().getMyIcons();
		}

		if(this.silentInputs.get(0).getValue().trim().equals("")) {
			return items;
		} else {
			for(CosmeticItem item : items) {
				if(item.getName().trim().toLowerCase().contains(this.silentInputs.get(0).getValue().toLowerCase().trim())) {
					searchItems.add(item);
				}
			}
			return searchItems;
		}
	}
	
	private ArrayList<Number> getFavoriteItems() {
		ArrayList<Number> items;

		if(selectedCategory == "capes") {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().capes;
		} else if(selectedCategory == "wings") {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().wings;
		} else if(selectedCategory == "bandanas") {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().bandanas;
		} else if(selectedCategory == "hats") {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().hats;
		} else if(selectedCategory == "shields") {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().shields;
		} else {
			items = Client.getInstance().getAccount().getFavoriteCosmetics().icons;
		}

		return items;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
		if(mc.thePlayer == null) {
			GlStateManager.disableAlpha();
			Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
			GlStateManager.enableAlpha();
			if(Client.getInstance().getGlobalSettings().isLite()) {
				this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
			} else {
				this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
			}
		} else {
			MenuBlurUtils.renderBackground(this);
		}

		int addX = 190;
		int addY = 110;

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int width = addX * 2;
		int height = addY * 2;

		int modOffsetY = 25;
		int modIndex = 1;

		//Draw background
		RenderUtil.drawRoundedRect(x, y, width, height, 10, Theme.backgroundColor().getRGB());
		
        ArrayList<CosmeticItem> items = null;
        ArrayList<Number> selected_item = new ArrayList<>();
        
        boolean can_show = true;
        String error_text = "";
		items = getItems();
		selected_item.clear();
        if(selectedCategory == "capes") {
        	selected_item.add(Client.getInstance().getAccount().getSelectedCape());
        	can_show = Client.getInstance().getAccount().customCape() ? false : Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Capes").getValBoolean();
        	error_text = Client.getInstance().getAccount().customCape() ? "Please disable Custom Cape in Silent+ settings" : Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Capes").getValBoolean() ? "" : "Please enable Capes in the cosmetics settings";
        } else if(selectedCategory == "wings") {
        	selected_item.add(Client.getInstance().getAccount().getSelectedWings());
        	can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings").getValBoolean();
        	error_text = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings").getValBoolean() ? "" : "Please enable Wings in the cosmetics settings";
        } else if(selectedCategory == "bandanas") {
        	selected_item.add(Client.getInstance().getAccount().getSelectedBandana());
        	can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Bandanas").getValBoolean();
        	error_text = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Bandanas").getValBoolean() ? "" : "Please enable Bandanas in the cosmetics settings";
        } else if(selectedCategory == "hats") {
			selected_item.add(Client.getInstance().getAccount().getSelectedHat());
			selected_item.add(Client.getInstance().getAccount().getSelectedMask());
			selected_item.add(Client.getInstance().getAccount().getSelectedNeck());
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Hats").getValBoolean();
			error_text = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Hats").getValBoolean() ? "" : "Please enable Hats in the cosmetics settings";
		} else if(selectedCategory == "shields") {
			selected_item.add(Client.getInstance().getAccount().getSelectedShield());
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Shields").getValBoolean();
			error_text = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Shields").getValBoolean() ? "" : "Please enable Shields in the cosmetics settings";
		} else {
        	selected_item.add(Client.getInstance().getAccount().getSelectedIcon());
        	can_show = Client.getInstance().getAccount().plusIcon() ? false : true;
        	error_text = Client.getInstance().getAccount().plusIcon() ? "Please disable Plus Nametag Icon in Silent+ settings" : "";
        }
        
		if(can_show) {
			scrollHelper.setStep(5);
			scrollHelper.setElementsHeight((items != null ? items.size() : 0) * 35);
			scrollHelper.setMaxScroll(height - 25);
			scrollHelper.setSpeed(200);
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_SCISSOR_TEST);
			ScaledResolution r = new ScaledResolution(mc);
	        int s = r.getScaleFactor();
	        int translatedY = r.getScaledHeight() - (y + 25) - (height - 25);
	        GL11.glScissor(x * s, translatedY * s, width * s, (height - 25) * s);
			if(items != null) {
				for(CosmeticItem m : items) {
					RenderUtil.drawRoundedOutline(x + 100, y + modOffsetY + scrollHelper.getScroll(), 135, 28, 10, 2, selected_item.contains(m.getId()) ? new Color(32, 252, 3).getRGB() : new Color(252, 3, 3).getRGB());
					if(MouseUtils.isInside(mouseX, mouseY, x + 100, y + modOffsetY + scrollHelper.getScroll(), 135, 28)) {
						cursorType = MouseCursorHandler.CursorType.POINTER;
					}
					GL11.glDisable((int) 2929);
					GL11.glEnable((int) 3042);
					GL11.glDepthMask((boolean) false);
					OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
					GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, 1f);
					GL11.glDepthMask((boolean) true);
					GL11.glDisable((int) 3042);
					GL11.glEnable((int) 2929);
					Client.getInstance().getSilentFontRenderer().drawString(m.getName(), x + 110, y + modOffsetY + 10 + scrollHelper.getScroll() - 3, 14, SilentFontRenderer.FontType.TITLE, 100);
					boolean favorite = false;
					for(Number i : getFavoriteItems()) {
						if(i.intValue() == m.getId()) {
							favorite = true;
						}
					}
					RenderUtil.drawImage(favorite ? new ResourceLocation("silentclient/icons/star.png") : new ResourceLocation("silentclient/icons/star_outline.png"), x + 100 + 120, y + modOffsetY + scrollHelper.getScroll() + 8, 12, 12, false);
					
					modOffsetY+= 35;
					modIndex += 1;
				}
			}
			GL11.glDisable(GL11.GL_SCISSOR_TEST);
			GL11.glPopMatrix();
			RenderUtil.drawImage(new ResourceLocation("silentclient/transparent.png"), x, y, 30, 30, false);
			if(mc.thePlayer != null && selectedCategory != "icons") {
				drawEntityOnScreen(x + 310, y + 175, 60, 1, 1, mc.thePlayer, rotate);
                boolean drag = MouseUtils.isInside(mouseX, mouseY, x + 265, y + 50, 90, 150) && Mouse.isButtonDown(0);
				if (drag) {
                    double diff = 360 - 0;
                    double mouse = MathHelper.clamp_double((mouseX - (x + 265)) / 90D, 0, 1);
                    double newVal = 0 + mouse * diff;
                    rotate = (int) newVal;
                }
			}
		}
		
		RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), x + 5, y + 5, 77, 15);
		Client.getInstance().getSilentFontRenderer().drawString("Cosmetics", x + 100, (int) (y + 5), 14, SilentFontRenderer.FontType.TITLE);
		
		super.drawScreen(mouseX, mouseY, partialTicks);

		if(mc.thePlayer == null && can_show && selectedCategory != "icons") {
			Client.getInstance().getSilentFontRenderer().drawString("Preview not available", x + 100 + 160, y + 100 - 3, 14, SilentFontRenderer.FontType.TITLE);
		}
		if(!can_show) {
			Client.getInstance().getSilentFontRenderer().drawString(error_text, x + ((width - 90) / 2) - 15, y + (height / 3) + 20 - 3, 14, SilentFontRenderer.FontType.TITLE);
		}
		if(selectedCategory == "icons" && can_show) {
			RenderUtil.drawImage(Client.getInstance().getAccount().getSelectedIcon() != 0 && !Client.getInstance().getAccount().plusIcon() ? Client.getInstance().getCosmetics().getIconById(Client.getInstance().getAccount().getSelectedIcon()).getLocation() : new ResourceLocation(Client.getInstance().getAccount().plusIcon() ? "silentclient/icons/plus_icon.png" : "silentclient/icons/player_icon.png"), x + 285, y + ((height / 2) - 25), 50, 50, false);
		}

		Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);

		if(can_show) {
			this.silentInputs.get(0).render(mouseX, mouseY, x + width - 140, y + 2, 135);
		}
	}

	public void updateScreen()
	{
		Client.backgroundPanorama.tickPanorama();
	}
	
	public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent, int rotate)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(rotate, 0.0F, 1.0F, 0.0F);
        
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0, 1.0F, 0.0F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int addX = 190;
		int addY = 110;

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int width = addX * 2;

		int modOffsetY = 25;
		
		ArrayList<CosmeticItem> items = getItems();

		boolean can_show = true;

		if(selectedCategory == "capes") {
			can_show = Client.getInstance().getAccount().customCape() ? false : Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Capes").getValBoolean();
		} else if(selectedCategory == "wings") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings").getValBoolean();
		} else if(selectedCategory == "bandanas") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Bandanas").getValBoolean();
		} else if(selectedCategory == "hats") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Hats").getValBoolean();
		}else if(selectedCategory == "shields") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Shields").getValBoolean();
		} else {
			can_show = Client.getInstance().getAccount().plusIcon() ? false : true;
		}
        
        if(items != null && can_show) {
			this.silentInputs.get(0).onClick(mouseX, mouseY, x + width - 140, y + 2, 135);
			for(CosmeticItem m : items) {
				if(MouseUtils.isInside(mouseX, mouseY, x + 100 + 120, y + modOffsetY + scrollHelper.getScroll() + 8, 12, 12)) {
					Client.getInstance().getAccount().updateFavorite(m.getId(), selectedCategory);
				} else if(MouseUtils.isInside(mouseX, mouseY,x + 100, y + modOffsetY + scrollHelper.getScroll(), 135, 28) && mouseButton == 0) {
					Sounds.playButtonSound();
					if(selectedCategory == "capes") {
						Client.getInstance().getAccount().setSelectedCape(m.getId());
					} else if(selectedCategory == "wings") {
						Client.getInstance().getAccount().setSelectedWings(m.getId());
					} else if(selectedCategory == "bandanas") {
						Client.getInstance().getAccount().setSelectedBandana(m.getId());
					} else if(selectedCategory == "hats") {
						Client.getInstance().getAccount().setSelectedHat(m.getId(), m.getModel().equals("gold_chain") ? "neck" : (m.getModel().equals("facemask") || m.getModel().equals("wichtiger_glasses")) ? "mask" : "hat");
					} else if(selectedCategory == "shields") {
						Client.getInstance().getAccount().setSelectedShield(m.getId());
					} else {
						Client.getInstance().getAccount().setSelectedIcon(m.getId());
					}
				}

				modOffsetY+= 35;
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		boolean can_show = true;


		if(selectedCategory == "capes") {
			can_show = Client.getInstance().getAccount().customCape() ? false : Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Capes").getValBoolean();
		} else if(selectedCategory == "wings") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings").getValBoolean();
		} else if(selectedCategory == "bandanas") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Bandanas").getValBoolean();
		} else if(selectedCategory == "hats") {
			can_show = Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Hats").getValBoolean();
		} else {
			can_show = Client.getInstance().getAccount().plusIcon() ? false : true;
		}
		if(can_show) {
			this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
			if(this.silentInputs.get(0).isFocused()) {
				this.scrollHelper.resetScroll();
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
