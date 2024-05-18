package net.minecraft.client.gui.spectator;

import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PlayerMenuObject implements ISpectatorMenuObject
{
    private final ResourceLocation resourceLocation;
    private final GameProfile profile;
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(this.profile.getName());
    }
    
    public PlayerMenuObject(final GameProfile profile) {
        this.profile = profile;
        AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation = AbstractClientPlayer.getLocationSkin(profile.getName()), profile.getName());
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n2 / 255.0f);
        Gui.drawScaledCustomSizeModalRect("  ".length(), "  ".length(), 8.0f, 8.0f, 0x77 ^ 0x7F, 0x6B ^ 0x63, 0x4D ^ 0x41, 0x78 ^ 0x74, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect("  ".length(), "  ".length(), 40.0f, 8.0f, 0x59 ^ 0x51, 0x69 ^ 0x61, 0x1 ^ 0xD, 0xA5 ^ 0xA9, 64.0f, 64.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return " ".length() != 0;
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.profile.getId()));
    }
}
