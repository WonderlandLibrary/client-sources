package ru.FecuritySQ.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;

import java.io.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtil {
    private final int programID;
    public static Minecraft mc = Minecraft.getInstance();

    public ShaderUtil(String fragmentShaderLoc, String vertexShaderLoc) {
        int program = glCreateProgram();
        try {
            int fragmentShaderID;
            switch (fragmentShaderLoc) {
                case "roundedRect":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundedRect.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "background":
                    fragmentShaderID = createShader(new ByteArrayInputStream(background.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "roundedRectGradient":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundedRectGradient.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "roundedTexturedShader":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundedTexturedShader.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "roundRectOutline":
                    fragmentShaderID = createShader(new ByteArrayInputStream(roundRectOutline.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "circleFlex":
                    fragmentShaderID = createShader(new ByteArrayInputStream(circleShader.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "imageCircle":
                    fragmentShaderID = createShader(new ByteArrayInputStream(imageCircle.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "gray":
                    fragmentShaderID = createShader(new ByteArrayInputStream(gray.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                case "blur":
                    fragmentShaderID = createShader(new ByteArrayInputStream(blur.getBytes()), GL_FRAGMENT_SHADER);
                    break;
                default:
                    fragmentShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), GL_FRAGMENT_SHADER);
                    break;
            }
            glAttachShader(program, fragmentShaderID);


        } catch (IOException e) {
            e.printStackTrace();
        }

        glLinkProgram(program);
        int status = glGetProgrami(program, GL_LINK_STATUS);

        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }


    public ShaderUtil(String fragmentShaderLoc) {
        this(fragmentShaderLoc, "client/shaders/vertex.vsh");
    }


    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight(), true, false);
        }
        return framebuffer;
    }

    public void init() {
        glUseProgram(programID);
    }

    public void unload() {
        glUseProgram(0);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(programID, name);
    }

    public static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtil roundedTexturedShader) {
        roundedTexturedShader.setUniformf("location", (float) (x * Minecraft.getInstance().getMainWindow().getGuiScaleFactor()),
                (float) ((Minecraft.getInstance().getMainWindow().getHeight() - (height * Minecraft.getInstance().getMainWindow().getGuiScaleFactor())) - (y * Minecraft.getInstance().getMainWindow().getGuiScaleFactor())));
        roundedTexturedShader.setUniformf("rectSize", (float) (width * Minecraft.getInstance().getMainWindow().getGuiScaleFactor()), (float) (height * Minecraft.getInstance().getMainWindow().getGuiScaleFactor()));
        roundedTexturedShader.setUniformf("radius", (float) (radius * Minecraft.getInstance().getMainWindow().getGuiScaleFactor()));
    }

    public void setUniformf(String name, float... args) {
        int loc = glGetUniformLocation(programID, name);
        switch (args.length) {
            case 1:
                glUniform1f(loc, args[0]);
                break;
            case 2:
                glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }

    public void setUniformi(String name, int... args) {
        int loc = glGetUniformLocation(programID, name);
        if (args.length > 1) glUniform2i(loc, args[0], args[1]);
        else glUniform1i(loc, args[0]);
    }

    public static void drawQuads(float x, float y, float width, float height) {
        if (mc.gameSettings.ofFastRender) return;
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    public static void drawQuads() {
        if (mc.gameSettings.ofFastRender) return;

        float width = mc.getMainWindow().getScaledWidth();
        float height = mc.getMainWindow().getScaledHeight();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, 0);
        glEnd();
    }

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, readInputStream(inputStream));
        glCompileShader(shader);


        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.out.println(glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }

        return shader;
    }





    private final String roundedTexturedShader = "#version 120\n" +
            "\n" +
            "uniform vec2 location, rectSize;\n" +
            "uniform sampler2D textureIn;\n" +
            "uniform float radius, alpha;\n" +
            "\n" +
            "float roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n" +
            "    return length(max(abs(centerPos) -size, 0.)) - radius;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "void main() {\n" +
            "    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n" +
            "    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n" +
            "    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n" +
            "}";
    private final String background = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "#extension GL_OES_standard_derivatives : enable\n" +
            "\n" +
            "#define NUM_OCTAVES 10\n" +
            "\n" +
            "uniform float time;\n" +
            "uniform vec2 resolution;\n" +
            "\n" +
            "mat3 rotX(float a) {\n" +
            "\tfloat c = cos(a);\n" +
            "\tfloat s = sin(a);\n" +
            "\treturn mat3(\n" +
            "\t\t1, 0, 0,\n" +
            "\t\t0, c, -s,\n" +
            "\t\t0, s, c\n" +
            "\t);\n" +
            "}\n" +
            "mat3 rotY(float a) {\n" +
            "\tfloat c = cos(a);\n" +
            "\tfloat s = sin(a);\n" +
            "\treturn mat3(\n" +
            "\t\tc, 0, -s,\n" +
            "\t\t0, 1, 0,\n" +
            "\t\ts, 0, c\n" +
            "\t);\n" +
            "}\n" +
            "\n" +
            "float random(vec2 pos) {\n" +
            "\treturn fract(sin(dot(pos.xy, vec2(1399.9898, 78.233))) * 43758.5453123);\n" +
            "}\n" +
            "\n" +
            "float noise(vec2 pos) {\n" +
            "\tvec2 i = floor(pos);\n" +
            "\tvec2 f = fract(pos);\n" +
            "\tfloat a = random(i + vec2(0.0, 0.0));\n" +
            "\tfloat b = random(i + vec2(1.0, 0.0));\n" +
            "\tfloat c = random(i + vec2(0.0, 1.0));\n" +
            "\tfloat d = random(i + vec2(1.0, 1.0));\n" +
            "\tvec2 u = f * f * (3.0 - 2.0 * f);\n" +
            "\treturn mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;\n" +
            "}\n" +
            "\n" +
            "float fbm(vec2 pos) {\n" +
            "\tfloat v = 0.0;\n" +
            "\tfloat a = 0.5;\n" +
            "\tmat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));\n" +
            "\tfor (int i=0; i<NUM_OCTAVES; i++) {\n" +
            "\t\tv += a * noise(pos - vec2(random(pos)/50.0));\n" +
            "\t\tpos = rot * pos * 2.0 + vec2(1000.+ random(pos)/15.);\n" +
            "\t\ta *= 0.65;\n" +
            "\t}\n" +
            "\treturn v;\n" +
            "}\n" +
            "\n" +
            "void main(void) {\n" +
            "\tvec2 p = (gl_FragCoord.xy * 1.0 - resolution.xy) / min(resolution.x, resolution.y);\n" +
            "\n" +
            "\tfloat t = 0.0, d;\n" +
            "\n" +
            "\tfloat time2 = 0.6 * time / 2.0;\n" +
            "\n" +
            "\tvec2 q = vec2(0.0);\n" +
            "\tq.x = fbm(p + 0.30 * time2);\n" +
            "\tq.y = fbm(p + vec2(1.0));\n" +
            "\tvec2 r = vec2(0.0);\n" +
            "\tr.x = fbm(p + 1.0 * q + vec2(1.2, 3.2) + 0.135 * time2);\n" +
            "\tr.y = fbm(p + 1.0 * q + vec2(8.8, 2.8) + 0.126 * time2);\n" +
            "\tfloat f = fbm(p + r);\n" +
            "\tvec3 color = mix(\n" +
            "\t\tvec3(0.0, 0.0, 0.0),\n" +
            "\t\tvec3(.0, 0.534, 0.435),\n" +
            "\t\tclamp((f * f) * 1.0, 0.0, 0.0)\n" +
            "\t);\n" +
            "\n" +
            "\tcolor = mix(\n" +
            "\t\tcolor,\n" +
            "\t\tvec3(0.0, 0.0, 0.9),\n" +
            "\t\tclamp(length(q), 0.0, 1.0)\n" +
            "\t);\n" +
            "\n" +
            "\n" +
            "\tcolor = mix(\n" +
            "\t\tcolor,\n" +
            "\t\tvec3(0, 0.4, 1),\n" +
            "\t\tclamp(length(r.x), 0.0, 1.0)\n" +
            "\t);\n" +
            "\n" +
            "\tcolor = (f *f * f + 0.6 * f * f + 0.9 * f) * color;\n" +
            "\n" +
            "\tgl_FragColor = vec4(color, 1.0);\n" +
            "}";
    private final String roundedRectGradient = "#version 120\n" +
            "\n" +
            "uniform vec2 location, rectSize;\n" +
            "uniform vec4 color1, color2, color3, color4;\n" +
            "uniform float radius;\n" +
            "\n" +
            "#define NOISE .5/255.0\n" +
            "\n" +
            "float roundSDF(vec2 p, vec2 b, float r) {\n" +
            "    return length(max(abs(p) - b , 0.0)) - r;\n" +
            "}\n" +
            "\n" +
            "vec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n" +
            "    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n" +
            "    //Dithering the color\n" +
            "    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n" +
            "    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n" +
            "    return color;\n" +
            "}\n" +
            "\n" +
            "void main() {\n" +
            "    vec2 st = gl_TexCoord[0].st;\n" +
            "    vec2 halfSize = rectSize * .5;\n" +
            "    \n" +
            "    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n" +
            "    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n" +
            "}";

    private final String roundRectOutline = "#version 120\n" +
            "\n" +
            "uniform vec2 location, rectSize;\n" +
            "uniform vec4 color, outlineColor;\n" +
            "uniform float radius, outlineThickness;\n" +
            "\n" +
            "float roundedSDF(vec2 centerPos, vec2 size, float radius) {\n" +
            "    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n" +
            "}\n" +
            "\n" +
            "void main() {\n" +
            "    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n" +
            "\n" +
            "    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n" +
            "\n" +
            "    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n" +
            "    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n" +
            "\n" +
            "}";

    public static String circleShader = " #version 120\n" +
            "uniform sampler2D texture;\n" +
            "uniform float radius;\n" +
            "uniform float glow;\n" +
            "\n" +
            "void main() {\n" +
            "    vec2 texCoord = gl_TexCoord[0].xy;\n" +
            "\tvec4 pixel = texture2D(texture, texCoord);\n" +
            "\tfloat dst = length(vec2(0.5) - texCoord);\n" +
            "\tfloat f = smoothstep(radius, radius + glow, 1 - dst);\n" +
            "\tgl_FragColor = pixel * f;\n" +
            "}";

    public static String gray = "#version 120\n" +
            "uniform sampler2D textureIn;\n" +
            "void main() {\n" +
            "    vec4 original = texture2D(textureIn, gl_TexCoord[0].st); // получаем оригинальный цвет текстуры\n" +
            "    float d = (original.r + original.b + original.g) / 3; // вычисл¤ем ¤ркость\n" +
            "    gl_FragColor = vec4(d, d, d, 1);\n" +
            "}\n";

    public static String blur = "#version 120\n" +
            "\n" +
            "uniform sampler2D textureIn;\n" +
            "uniform vec2 texelSize, direction;\n" +
            "uniform float radius;\n" +
            "uniform float weights[256];\n" +
            "\n" +
            "#define offset texelSize * direction\n" +
            "\n" +
            "void main() {\n" +
            "    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n" +
            "    vec2 texCoord = gl_TexCoord[0].st;\n" +
            "    vec3 texel = vec3(0.0);\n" +
            "\n" +
            "    for (int i = 1; i <= 255; i++) {\n" +
            "        if (i > int(radius)) {\n" +
            "            break;\n" +
            "        }\n" +
            "\n" +
            "        texel = texture2D(textureIn, texCoord + float(i) * offset).rgb;\n" +
            "        blr += texel * weights[i];\n" +
            "        texel = texture2D(textureIn, texCoord - float(i) * offset).rgb;\n" +
            "        blr += texel * weights[i];\n" +
            "    }\n" +
            "\n" +
            "    gl_FragColor = vec4(blr, 1.0);\n" +
            "}";
    public static String imageCircle = "uniform vec2 resolution;\n" +
            "uniform sampler2D texture;\n" +
            "uniform float radius;\n" +
            "\n" +
            "void main() {\n" +
            "  vec2 uv = gl_TexCoord[0].xy;\n" +
            "  vec4 color = texture2D(texture, uv);\n" +
            "\n" +
            "  float aspect = resolution.x / resolution.y;\n" +
            "  float dx = radius / resolution.x;\n" +
            "  float dy = radius / resolution.y;\n" +
            "  float mx = max(dx, dy);\n" +
            "  vec2 duv = vec2(dx, dy) * aspect * mix(1.0, 1.0 / aspect, step(aspect, 1.0));\n" +
            "  vec4 texel = texture2D(texture, uv + duv * vec2(-1, -1));\n" +
            "  float r = length(vec2(0.5, 0.5) - uv);\n" +
            "  float f = smoothstep(mx, mx - 0.005, r);\n" +
            "  color = mix(color, texel, f);\n" +
            "\n" +
            "  gl_FragColor = color;\n" +
            "}";
    private final String roundedRect = "#version 120\n" +
            "\n" +
            "uniform vec2 location, rectSize;\n" +
            "uniform vec4 color;\n" +
            "uniform float radius;\n" +
            "uniform bool blur;\n" +
            "\n" +
            "float roundSDF(vec2 p, vec2 b, float r) {\n" +
            "    return length(max(abs(p) - b, 0.0)) - r;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "void main() {\n" +
            "    vec2 rectHalf = rectSize * .5;\n" +
            "    // Smooth the result (free antialiasing).\n" +
            "    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n" +
            "    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n" +
            "\n" +
            "}";



    public static void bindTexture(int texture) {
        GlStateManager.bindTexture(texture);
    }
    public static String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

}