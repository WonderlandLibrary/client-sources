package xyz.mathax.mathaxclient.utils.render;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Identifier;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import xyz.mathax.mathaxclient.renderer.Texture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class IconTexture extends Texture {
    public IconTexture(Identifier identifier) {
        try {
            ByteBuffer data = TextureUtil.readResource(mc.getResourceManager().getResource(identifier).get().getInputStream());
            data.rewind();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer width = stack.mallocInt(1);
                IntBuffer height = stack.mallocInt(1);
                IntBuffer comp = stack.mallocInt(1);
                ByteBuffer image = STBImage.stbi_load_from_memory(data, width, height, comp, 3);
                upload(image);
                STBImage.stbi_image_free(image);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void upload(ByteBuffer data) {
        Runnable action = () -> upload(8, 8, data, Texture.Format.RGB, Texture.Filter.Nearest, Texture.Filter.Nearest, false);
        if (RenderSystem.isOnRenderThread()) {
            action.run();
        } else {
            RenderSystem.recordRenderCall(action::run);
        }
    }
}