/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import java.io.IOException;

import javax.imageio.ImageIO;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.play.client.C01PacketChatMessage;

/**
 * @author DistastefulBannock
 *
 */
public class CommandSpoofSkin extends Command {

	public CommandSpoofSkin() {
		super("spoofSkin", "Put file in kagu dir named skin.png, run this command to refresh the file", refresh);
	}

	private static ActionRequirement refresh = new ActionRequirement((CommandAction) args -> {
		if (FileManager.SKIN_OVERRIDE.exists()) {
			try {
				if (Kagu.getSkinOverride() != null)
					Minecraft.getMinecraft().getTextureManager().deleteTexture(Kagu.getSkinOverride());
				Kagu.setSkinOverride(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(
						"SkinOverride:3", new DynamicTexture(ImageIO.read(FileManager.SKIN_OVERRIDE))));
				ChatUtils.addChatMessage("Your skin has been set");
			} catch (IOException e) {
				ChatUtils.addChatMessage("Something went wrong while loading the skin");
				e.printStackTrace();
			}
		}else {
			ChatUtils.addChatMessage("Could not find the skin file in " + FileManager.SKIN_OVERRIDE.getAbsolutePath());
			Kagu.setSkinOverride(null);
		}
		return true;
	});

}
