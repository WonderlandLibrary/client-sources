package Focus.Beta.UTILS.render;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DynamicTextureUtil {

    private static final Map<String, DynamicTexture> IMAGES = new HashMap<>();

    public static DynamicTexture addTexture(String name, BufferedImage image) {
        DynamicTexture dynamicTexture = new DynamicTexture(image);
        IMAGES.put(name, dynamicTexture);
        return dynamicTexture;
    }

    public static void bindTexture(DynamicTexture texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getGlTextureId());
    }

    public static void bindTexture(String name) {
        bindTexture(IMAGES.get(name));
    }

}
