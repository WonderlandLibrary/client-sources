package dev.darkmoon.client.utility.render.shaderRound;

import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

public class ShaderShell {
    public static ShaderShell BLUR_SHADER, MENU_SHADER, CIRCLE_TEXTURE_SHADER, CIRCLE_SHADER, SCROLL_SHADER,
            FONTRENDERER_SUBSTRING, ROUNDED_RECT, CHAMS_SHADER;
    private int shaderID;

    public ShaderShell(String shaderName, int type) {
        parseShaderFromFile(shaderName, type);
    }

    public ShaderShell(String shaderName) {
        this(shaderName, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    }

    public static void init() {
        BLUR_SHADER = new ShaderShell("#version 120\r\n" +
                "\r\n" +
                "uniform sampler2D textureIn, mainTexture;\r\n" +
                "uniform vec2 texelSize, direction;\r\n" +
                "uniform float radius;\r\n" +
                "uniform float weights[256];\r\n" +
                "\r\n" +
                "#define offset texelSize * direction\r\n" +
                "\r\n" +
                "void main() {\r\n" +
                "    vec4 clr = texture2D(mainTexture, gl_TexCoord[0].xy);\r\n" +
                "	if (clr.a >= 0.1) {\r\n" +
                "        vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\r\n" +
                "        for (float f = 1.0; f <= radius; f++) {\r\n" +
                "            blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\r\n" +
                "            blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\r\n" +
                "        }\r\n" +
                "        gl_FragColor = vec4(blr, 1.0);\r\n" +
                "	} else {\r\n" +
                "	    gl_FragColor = clr; \r\n" +
                "	}\r\n" +
                "}\r\n" +
                "");
        MENU_SHADER = new ShaderShell(
                "#version 120PIDORASuniform sampler2D texture;PIDORASuniform sampler2D textureBG;PIDORASuniform vec3 color;PIDORASuniform vec2 oneTexel;PIDORASuniform float alpha;PIDORASPIDORASvoid main() {PIDORAS    vec2 pos = gl_TexCoord[0];PIDORAS    vec4 tc = texture2D(texture, pos);PIDORAS	vec4 tbg = texture2D(textureBG, pos);PIDORAS	vec4 c = vec4(vec3(tbg.r + tbg.g + tbg.b) / 3, 1);PIDORAS	c.r = c.r * (1 + color.r * alpha);PIDORAS	c.g = c.g * (1 + color.g * alpha);PIDORAS	c.b = c.b * (1 + color.b * alpha);PIDORAS	gl_FragColor = mix(c, tc, tc.a * alpha);PIDORAS}PIDORAS");
        CIRCLE_SHADER = new ShaderShell(
                "#version 120PIDORASuniform vec4 color;PIDORASuniform vec2 resolution;PIDORASuniform vec2 start;PIDORASuniform vec2 end;PIDORASuniform float glow;PIDORASuniform float radius;PIDORASPIDORASvoid main() {PIDORAS    vec2 pos = gl_FragCoord.xy;PIDORAS	pos.y = resolution.y - pos.y;PIDORAS	pos = (pos - start) / (end - start);PIDORAS	float len = 1 - length(pos - vec2(0.5));PIDORAS	float smo = smoothstep(radius, radius + glow, len);PIDORAS	gl_FragColor = vec4(smo * color);PIDORAS}PIDORAS");
        CIRCLE_TEXTURE_SHADER = new ShaderShell(
                "#version 120PIDORASuniform sampler2D texture;PIDORASuniform float radius;PIDORASuniform float glow;PIDORASPIDORASvoid main() {PIDORAS    vec2 texCoord = gl_TexCoord[0];PIDORAS	vec4 pixel = texture2D(texture, texCoord);PIDORAS	float dst = length(vec2(0.5) - texCoord);PIDORAS	float f = smoothstep(radius, radius + glow, 1 - dst);PIDORAS	gl_FragColor = pixel * f;PIDORAS}PIDORAS");
        SCROLL_SHADER = new ShaderShell(
                "#version 120PIDORASuniform sampler2D texture;PIDORASuniform sampler2D textureBG;PIDORASuniform vec2 resolution;PIDORASuniform int maxY;PIDORASuniform int minY;PIDORASPIDORASvoid main() {PIDORAS    vec2 pos = gl_TexCoord[0];PIDORAS	vec2 rpos = vec2(gl_FragCoord.x, resolution.y - gl_FragCoord.y);PIDORAS	vec4 tbg = texture2D(textureBG, pos);PIDORAS    vec4 tc = texture2D(texture, pos);PIDORAS	if (tc.a != 0) {PIDORAS	    if (rpos.y > maxY - 40) {PIDORAS		    tc.a = tc.a * smoothstep(0, 0.5, length(vec2(gl_FragCoord.x, maxY) - rpos) / 40);PIDORAS	    }PIDORAS	    if (rpos.y < minY + 40) {PIDORAS		    tc.a = tc.a * smoothstep(0, 0.5, length(vec2(gl_FragCoord.x, minY) - rpos) / 40);PIDORAS	    }PIDORAS	}PIDORAS	gl_FragColor = mix(tbg, tc, tc.a);PIDORAS}PIDORAS");
        FONTRENDERER_SUBSTRING = new ShaderShell(
                "#version 120PIDORASuniform sampler2D font;PIDORASuniform vec4 inColor;PIDORASuniform float width;PIDORASuniform float maxWidth;PIDORASPIDORASvoid main() {PIDORAS	float f = clamp(smoothstep(0.5, 1, 1 - (gl_FragCoord.x - maxWidth) / width), 0, 1);PIDORAS    vec2 pos = gl_TexCoord[0].xy;PIDORAS	vec4 color = texture2D(font, pos);PIDORAS	if(color.a > 0) {PIDORAS	   color.a = color.a * f;PIDORAS	}PIDORAS	gl_FragColor = color * inColor;PIDORAS}PIDORAS");
        ROUNDED_RECT = new ShaderShell(
                "#version 120PIDORASuniform vec4 color;PIDORASuniform vec2 resolution;PIDORASuniform vec2 center;PIDORASuniform vec2 dst;PIDORASuniform float radius;PIDORASPIDORASfloat rect(vec2 pos, vec2 center, vec2 size) {  PIDORAS    return length(max(abs(center - pos) - (size / 2), 0));PIDORAS}PIDORASPIDORASvoid main() {PIDORAS    vec2 pos = gl_FragCoord.xy;PIDORAS	pos.y = resolution.y - pos.y;PIDORAS	gl_FragColor = vec4(vec3(color), (1 - rect(pos, center, dst) / radius) * color.a);PIDORAS}PIDORAS");
    }

    public void attach() {
        if (Display.isActive())
            ARBShaderObjects.glUseProgramObjectARB(shaderID);
    }

    public void set1I(String name, int value0) {
        if (Display.isActive())
            ARBShaderObjects.glUniform1iARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, name), value0);
    }

    public void set1F(String name, float value0) {
        if (Display.isActive())
            ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, name), value0);
    }

    public void set2F(String name, float value0, float value1) {
        if (Display.isActive())
            ARBShaderObjects.glUniform2fARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, name), value0, value1);
    }

    public void set3F(String name, float value0, float value1, float value2) {
        if (Display.isActive())
            ARBShaderObjects.glUniform3fARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, name), value0, value1,
                    value2);
    }

    public void set4F(String name, float value0, float value1, float value2, float value3) {
        if (Display.isActive())
            ARBShaderObjects.glUniform4fARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, name), value0, value1,
                    value2, value3);
    }

    public void detach() {
        if (Display.isActive())
            ARBShaderObjects.glUseProgramObjectARB(0);
    }

    private void parseShaderFromFile(String source, int i) {
        try {
            localInit(source.replace("PIDORAS", "\n").replace("gl_TexCoord[0];", "gl_TexCoord[0].xy;"), i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupUniforms(float offset) {
        Minecraft mc = Minecraft.getMinecraft();
        set1I("textureIn", 2);
        set1I("mainTexture", 0);
        set2F("texelSize", 1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
        set2F("direction", 1f, 1f);
        set1F("radius", offset);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= offset; i++) {
            weightBuffer.put(calculateGaussianValue(i, offset / 2));
        }
        weightBuffer.rewind();
        ARBShaderObjects.glUniform1ARB(ARBShaderObjects.glGetUniformLocationARB(shaderID, "weights"), weightBuffer);
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    void localInit(String str, int i) {
        int shaderProgram = ARBShaderObjects.glCreateProgramObjectARB();
        if (shaderProgram == 0) {
            System.out.println("PC Issued");
            System.exit(0);
            return;
        }
        int shader = ARBShaderObjects.glCreateShaderObjectARB(i);
        ARBShaderObjects.glShaderSourceARB(shader, str);
        ARBShaderObjects.glCompileShaderARB(shader);
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            String s = GL20.glGetShaderInfoLog(shader, 500);
            System.out.println("Error [ " + s + " ]");
        } else {
            System.out.println("Ok");
        }
        ARBShaderObjects.glAttachObjectARB(shaderProgram, shader);
        ARBShaderObjects.glLinkProgramARB(shaderProgram);
        this.shaderID = shaderProgram;
    }
}
