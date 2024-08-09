package wtf.resolute.utiled.render;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import wtf.resolute.utiled.client.IMinecraft;

import java.io.ByteArrayInputStream;


public class ShaderUtils implements IMinecraft {

    public static ShaderUtils MAIN_MENU;

    private final int shaderID = GL20.glCreateProgram();

    public ShaderUtils(String string) {
        GL20.glAttachShader(this.shaderID, this.createShader(string));
        GL20.glLinkProgram(this.shaderID);
    }

    public static void init() {


        MAIN_MENU = new ShaderUtils(
         """
#extension GL_OES_standard_derivatives : enable

#ifdef GL_ES
precision mediump float;
#endif

#define repeat(i, n) for(int i = 0; i < n; i++)

uniform float time;

uniform vec2 resolution;

void main(void)
{
    vec2 uv = gl_FragCoord.xy / resolution.xy - .5;
    uv.y *= resolution.y / resolution.x;
    float mul = resolution.x / resolution.y;
    vec3 dir = vec3(uv * mul, 1.);
    float a2 = time * 20. + .5;
    float a1 = 1.0;
    mat2 rot1 = mat2(cos(a1), sin(a1), - sin(a1), cos(a1));
    mat2 rot2 = rot1;
    dir.xz *= rot1;
    dir.xy *= rot2;
    vec3 from = vec3(0., 0., 0.);
    from += vec3(.0025 * time, .03 * time, - 2.);
    from.xz *= rot1;
    from.xy *= rot2;
    float s = .1, fade = .07;
    vec3 v = vec3(0.4);
    repeat(r, 10) {
	vec3 p = from + s * dir * 1.5;
	p = abs(vec3(0.750) - mod(p, vec3(0.750 * 2.)));
	p.x += float(r * r) * 0.01;
	p.y += float(r) * 0.02;
	float pa, a = pa = 0.;
	repeat(i, 12) {
	    p = abs(p) / dot(p, p) - 0.340;
	    a += abs(length(p) - pa * 0.2);
	    pa = length(p);
	}
	a *= a * a * 2.;
	v += vec3(s * s , s , s * s) * a * 0.0017 * fade;
	fade *= 0.960;
	s += 0.110;
    }
    v = mix(vec3(length(v)), v, 0.8);
    gl_FragColor = vec4(v * 0.01, 1.);
}
                 """);

    }

    public void drawQuads(float x, float y, float z, float width, float height) {
        buffer.begin(9, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x,y,z).tex(0.0f, 0.0f).endVertex();
        buffer.pos(x, y + height,z).tex(0.0f, 1.0f).endVertex();
        buffer.pos(x + width, y + height,z).tex(1.0f, 1.0f).endVertex();
        buffer.pos(x + width, y,z).tex(1.0f, 0.0f).endVertex();
        tessellator.draw();
    }

    public void begin() {
        GL20.glUseProgram(this.shaderID);
    }

    public void end() {
        GL20.glUseProgram(0);
    }

    public void setUniform(String string, float ... fArray) {
        int n = GL20.glGetUniformLocation(this.shaderID, string);
        switch (fArray.length) {
            case 1: {
                ARBShaderObjects.glUniform1fARB(n, fArray[0]);
                break;
            }
            case 2: {
                ARBShaderObjects.glUniform2fARB(n, fArray[0], fArray[1]);
                break;
            }
            case 3: {
                ARBShaderObjects.glUniform3fARB(n, fArray[0], fArray[1], fArray[2]);
                break;
            }
            case 4: {
                ARBShaderObjects.glUniform4fARB(n, fArray[0], fArray[1], fArray[2], fArray[3]);
            }
        }
    }

    private int createShader(String string) {
        int gled = GL20.glCreateShader(35632);
        GL20.glShaderSource(gled, (CharSequence)FileUtil.readInputStream(new ByteArrayInputStream(string.getBytes())));
        GL20.glCompileShader(gled);
        return gled;
    }
}
