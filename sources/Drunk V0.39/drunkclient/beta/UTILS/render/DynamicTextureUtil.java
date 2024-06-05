/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.UTILS.render;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class DynamicTextureUtil {
    private static final Map<String, DynamicTexture> IMAGES = new HashMap<String, DynamicTexture>();

    public static DynamicTexture addTexture(String name, BufferedImage image) {
        DynamicTexture dynamicTexture = new DynamicTexture(image);
        IMAGES.put(name, dynamicTexture);
        return dynamicTexture;
    }

    public static void bindTexture(DynamicTexture texture) {
        GL11.glBindTexture((int)3553, (int)texture.getGlTextureId());
    }

    public static void bindTexture(String name) {
        DynamicTextureUtil.bindTexture(IMAGES.get(name));
    }
}

