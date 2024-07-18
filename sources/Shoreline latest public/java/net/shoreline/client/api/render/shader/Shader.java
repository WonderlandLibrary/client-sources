package net.shoreline.client.api.render.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static org.lwjgl.opengl.GL20C.*;


public class Shader {
    private final int id;

    public Shader(String shaderName, int shaderMode) {
        id = GlStateManager.glCreateShader(shaderMode);
        try {
            GlStateManager.glShaderSource(id, loadShader(shaderName));
            GlStateManager.glCompileShader(id);

            if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                throw new RuntimeException(
                        "Shader compilation error!\n" +
                                glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH))
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> loadShader(String name) throws IOException {
        InputStream stream = Shader.class
                .getClassLoader()
                .getResourceAsStream("assets/shoreline/shader/" + name);
        if (stream == null) {
            throw new IOException("Shader with name " + name + " not found");
        }

        return IOUtils.readLines(stream, Charset.defaultCharset()).stream()
                .map(line -> line + "\n")
                .toList();
    }

    public int getId() {
        return id;
    }
}
