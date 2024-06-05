/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public final class FontUtils {
    public static Font getFontFromTTF(ResourceLocation loc, float fontSize, int fontType) {
        Font output;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream());
            output = output.deriveFont(fontSize);
        }
        catch (Exception e) {
            return null;
        }
        return output;
    }
}

