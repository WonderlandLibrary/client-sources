/**
 * 
 */
package cafe.kagu.kagu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

/**
 * @author lavaflowglow
 *
 */
public class SoundUtils {
	
	/**
	 * @return A new PositionedSoundRecord for the click sound effect
	 */
	public static ISound getClickSound() {
		Minecraft mc = Minecraft.getMinecraft();
		return new PositionedSoundRecord(new ResourceLocation("kagusounds:kagu.click"), 1, 1, false, 1, ISound.AttenuationType.NONE, mc.thePlayer == null ? 0 : (float)mc.thePlayer.posX, mc.thePlayer == null ? 0 : (float)mc.thePlayer.posY, mc.thePlayer == null ? 0 : (float)mc.thePlayer.posZ);
	}
	
}
