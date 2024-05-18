package host.kix.uzi.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Kix on 2/3/2017.
 */
public class Utility {

    private final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private final FontRenderer FONT_RENDERER = Minecraft.getMinecraft().fontRendererObj;
    private final EntityPlayerSP ENTITY_PLAYER = Minecraft.getMinecraft().thePlayer;

    public Minecraft getMinecraft() {
        return MINECRAFT;
    }

    public FontRenderer getFontRenderer() {
        return FONT_RENDERER;
    }

    public EntityPlayerSP getEntityPlayer() {
        return ENTITY_PLAYER;
    }
}
