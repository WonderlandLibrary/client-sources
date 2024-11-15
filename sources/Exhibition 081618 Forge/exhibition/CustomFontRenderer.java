package exhibition;

import exhibition.module.impl.other.MonikaMode;
import exhibition.module.impl.other.NameProtect;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class CustomFontRenderer extends FontRenderer {
	private String replaceString;
	private String monikamodeString;
	private NameProtect ModuleNameProtect;
	private MonikaMode ModuleMonikaMode;
	public CustomFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
		super(gameSettingsIn, location, textureManagerIn, unicode);
	}
	
	@Override
	public int drawString(String text, float x, float y, int color, boolean dropShadow)	{
		ModuleNameProtect = (NameProtect) Client.getModuleManager().get(NameProtect.class);
		ModuleMonikaMode = (MonikaMode) Client.getModuleManager().get(MonikaMode.class);
		replaceString = text.replace(Wrapper.getMinecraft().getSession().getUsername(), ModuleNameProtect.isEnabled() ? "§c§l"+ModuleNameProtect.nameString+"§r" : Wrapper.getMinecraft().getSession().getUsername());
		Boolean isddlcmode = ModuleMonikaMode.isEnabled() && Wrapper.getWorld() != null;
		if(isddlcmode) {
			for(final NetworkPlayerInfo playerInfo : Wrapper.getMinecraft().getNetHandler().getPlayerInfoMap()) {
				monikamodeString = replaceString.replace(playerInfo.getGameProfile().getName(),ModuleMonikaMode.getRandomMonika());
			}
		}
		return super.drawString(isddlcmode ? monikamodeString :replaceString,x,y,color,dropShadow);
    }
}
