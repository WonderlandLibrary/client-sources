package net.silentclient.client.mods.hud;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackDisplayMod extends HudMod {

	private ResourceLocation defaultIcon;

	public PackDisplayMod() {
		super("Pack Display", ModCategory.MODS, "silentclient/icons/mods/packdisplay.png", false);
	}
	
	@Override
	public void setup() {
		super.setupWithAfterValue();
		this.addBooleanSetting("Pack Icon", this, true);
		ArrayList<String> options = new ArrayList<>();
		
		options.add("First Pack");
		options.add("Last Pack");

		this.addModeSetting("Pack Order", this, "First Pack", options);

		ArrayList<String> options2 = new ArrayList<>();

		options2.add("Full Text");
		options2.add("White Text Only");

		this.addModeSetting("Pack Title Replacement", this, "White Text Only", options2);
	}

	@Override
	public String getText() {
		String order = Client.getInstance().getSettingsManager().getSettingByName(this, "Pack Order").getValString();
		String replacement = Client.getInstance().getSettingsManager().getSettingByName(this, "Pack Title Replacement").getValString();


		List<ResourcePackRepository.Entry> rps = Lists.reverse(this.mc.getResourcePackRepository().getRepositoryEntries());
		int count = rps.size();
		String name = "";
		if(count != 0) {
			ResourcePackRepository.Entry entry = rps.get(order == "First Pack" ? 0 : count - 1);
			name = entry.getResourcePackName().replace(".zip", "").trim();
		} else {
			name = mc.getResourcePackRepository().rprDefaultResourcePack.getPackName();
		}

		if(replacement.equals("Full Text")) {
			return EnumChatFormatting.getTextWithoutFormattingCodes(name);
		}

		return name;
	}

	@Override
	public void preRender() {
		super.preRender();
		if(!showIcon()) {
			return;
		}
		String order = Client.getInstance().getSettingsManager().getSettingByName(this, "Pack Order").getValString();
		List<ResourcePackRepository.Entry> rps = Lists.reverse(this.mc.getResourcePackRepository().getRepositoryEntries());
		int count = rps.size();
		if(count != 0) {
			ResourcePackRepository.Entry entry = rps.get(order == "First Pack" ? 0 : count - 1);
			entry.bindTexturePackIcon(Minecraft.getMinecraft().getTextureManager());
			GlStateManager.disableLighting();
			entry.bindTexturePackIcon(mc.getTextureManager());
			RenderUtil.drawImage(0, 0, getIconHeight(), getIconHeight());
		} else {
			if(defaultIcon == null) {
				try {
					defaultIcon = mc.getTextureManager().getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.mc.getResourcePackRepository().rprDefaultResourcePack.getPackImage()));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			GlStateManager.disableLighting();
			RenderUtil.drawImage(defaultIcon, 0, 0, getIconHeight(), getIconHeight(), false);
		}
	}

	@Override
	public int getHeight() {
		if(!showIcon()) {
			return super.getHeight();
		}
		return getIconHeight();
	}

	public int getIconHeight() {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();

		return (fancyFont ? 12 : this.font.FONT_HEIGHT) + (fancyFont ? 5 : 8);
	}

	@Override
	public int getWidth() {
		if(!showIcon()) {
			return super.getWidth();
		}
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();

		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);

		return font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) + (showIcon() ? this.getIconHeight() : 0) + 1;
	}

	private boolean showIcon() {
		return Client.getInstance().getSettingsManager().getSettingByName(this, "Pack Icon").getValBoolean();
	}

	@Override
	public int getX() {
		if(showIcon()) {
			return getIconHeight();
		}
		return super.getX();
	}

	@Override
	public int getRealWidth() {
		if(!showIcon()) {
			return super.getRealWidth();
		}
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		boolean brackets = Client.getInstance().getSettingsManager().getSettingByName(this, "Brackets").getValBoolean();

		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);

		return font.getStringWidth((brackets ? "[" : "") + getText() + (brackets ? "]" : "")) + 2;
	}

	@Override
	public int getTextX() {
		if(!showIcon()) {
			return super.getTextX();
		}

		return this.getX() + 1;
	}

	@Override
	public int getTextY() {
		if(!showIcon()) {
			return super.getTextY();
		}
		boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
		if(!background) {
			return this.getHeight() / 3;
		}
		return super.getTextY();
	}
}