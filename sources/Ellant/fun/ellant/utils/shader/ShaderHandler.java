/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.utils.shader;

import fun.ellant.utils.client.IMinecraft;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL20;

public class ShaderHandler
        implements IMinecraft {
    private final int programID;
    private String shaderMainMenu = "#ifdef GL_ES\nprecision highp float;\n#endif\n\n#extension GL_OES_standard_derivatives : enable\n\n#define NUM_OCTAVES 6\n\nuniform float time;\nuniform vec2 resolution;\n\nmat3 rotX(float a) {\n    float c = cos(a);\n    float s = sin(a);\n    return mat3(\n        1, 0, 0,\n        0, c, -s,\n        0, s, c\n    );\n}\n\nmat3 rotY(float a) {\n    float c = cos(a);\n    float s = sin(a);\n    return mat3(\n        c, 0, -s,\n        0, 1, 0,\n        s, 0, c\n    );\n}\n\nfloat random(vec2 pos) {\n    return fract(sin(dot(pos.xy, vec2(12.9898, 78.233))) * 43758.5453123);\n}\n\nfloat noise(vec2 pos) {\n    vec2 i = floor(pos);\n    vec2 f = fract(pos);\n    float a = random(i + vec2(0.0, 0.0));\n    float b = random(i + vec2(1.0, 0.0));\n    float c = random(i + vec2(0.0, 1.0));\n    float d = random(i + vec2(1.0, 1.0));\n    vec2 u = f * f * (3.0 - 2.0 * f);\n    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;\n}\n\nfloat fbm(vec2 pos) {\n    float v = 0.0;\n    float a = 0.5;\n    vec2 shift = vec2(100.0);\n    mat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));\n    for (int i = 0; i < NUM_OCTAVES; i++) {\n        float dir = mod(float(i), 2.0) > 0.5 ? 1.0 : -1.0;\n        v += a * noise(pos - 0.05 * dir * time);\n\n        pos = rot * pos * 2.0 + shift;\n        a *= 0.5;\n    }\n    return v;\n}\n\nvoid main(void) {\n    vec2 p = (gl_FragCoord.xy * 2.0 - resolution.xy) / min(resolution.x, resolution.y);\n    p -= vec2(12.0, 0.0);\n\n    float t = 0.0, d;\n\n    float time2 = 1.0;\n\n    vec2 q = vec2(0.0);\n    q.x = fbm(p + 0.00 * time2);\n    q.y = fbm(p + vec2(1.0));\n    vec2 r = vec2(0.0);\n    r.x = fbm(p + 1.0 * q + vec2(1.7, 9.2) + 0.15 * time2);\n    r.y = fbm(p + 1.0 * q + vec2(8.3, 2.8) + 0.126 * time2);\n    float f = fbm(p + r);\n    \n    // hornidev\n    vec3 color = mix(\n        vec3(0.3, 0.3, 0.6),\n        vec3(0.7, 0.7, 0.7),\n        clamp((f * f) * 4.0, 0.0, 1.0)\n    );\n\n    color = mix(\n        color,\n        vec3(0.7, 0.7, 0.7),\n        clamp(length(q), 0.0, 1.0)\n    );\n\n    color = mix(\n        color,\n        vec3(0.4, 0.4, 0.4),\n        clamp(length(r.x), 0.0, 1.0)\n    );\n\n    color = (f * f * f + 0.9 * f * f + 0.8 * f) * color;\n\n    gl_FragColor = vec4(color * 0.7, color.r);\n}\n";

    public ShaderHandler(String fragmentShaderLoc, String vertexShaderLoc) {
        int program = GL20.glCreateProgram();
        try {
            GL20.glAttachShader(program, switch (fragmentShaderLoc) {
                case "shaderMainMenu" -> this.createShader(new ByteArrayInputStream(this.shaderMainMenu.getBytes()), 35632);
                default -> this.createShader(mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 35632);
            });
            int vertexShaderID = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 35633);
            GL20.glAttachShader(program, vertexShaderID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glLinkProgram(program);
        int status2 = GL20.glGetProgrami(program, 35714);
        if (status2 == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public ShaderHandler(String fragmentShaderLoc) {
        this(fragmentShaderLoc, new ResourceLocation("minecraft", "expensive/shader/vertex.vsh").getPath());
    }

    public void setUniformf(String name, float ... args2) {
        int loc = GL20.glGetUniformLocation(this.programID, name);
        switch (args2.length) {
            case 1: {
                GL20.glUniform1f(loc, args2[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(loc, args2[0], args2[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(loc, args2[0], args2[1], args2[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(loc, args2[0], args2[1], args2[2], args2[3]);
            }
        }
    }

    public void setUniformi(String name, int ... args2) {
        int loc = GL20.glGetUniformLocation(this.programID, name);
        if (args2.length > 1) {
            GL20.glUniform2i(loc, args2[0], args2[1]);
        } else {
            GL20.glUniform1i(loc, args2[0]);
        }
    }

    public static void drawQuads(float x, float y, float width, float height) {
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(x, y);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(x, y + height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(x + width, y + height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(x + width, y);
        GL20.glEnd();
    }

    public static void drawQuads() {
        float width = mc.getMainWindow().getScaledWidth();
        float height = mc.getMainWindow().getScaledHeight();
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(0.0f, 0.0f);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(0.0f, height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(width, height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(width, 0.0f);
        GL20.glEnd();
    }

    public int createShader(InputStream inputStream, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, (CharSequence)ShaderHandler.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

