package wtf.shiyeno.util.render.shader;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import wtf.shiyeno.util.IMinecraft;
import wtf.shiyeno.util.misc.FileUtil;

import java.io.ByteArrayInputStream;

public class ShaderUtils implements IMinecraft {

    public static ShaderUtils MAIN_MENU;

    private final int shaderID = GL20.glCreateProgram();

    public ShaderUtils(String string) {
        GL20.glAttachShader(this.shaderID, this.createShader(string));
        GL20.glLinkProgram(this.shaderID);
    }

    public static void init() {
        MAIN_MENU = new ShaderUtils("""
                #ifdef GL_ES
                precision highp float;
                #endif
                                
                #extension GL_OES_standard_derivatives : enable
                                
                #define NUM_OCTAVES 6
                                
                uniform float time;
                uniform vec2 resolution;
                                
                mat3 rotX(float a) {
                    float c = cos(a);
                    float s = sin(a);
                    return mat3(
                        1, 0, 0,
                        0, c, -s,
                        0, s, c
                    );
                }
                                
                mat3 rotY(float a) {
                    float c = cos(a);
                    float s = sin(a);
                    return mat3(
                        c, 0, -s,
                        0, 1, 0,
                        s, 0, c
                    );
                }
                                
                float random(vec2 pos) {
                    return fract(sin(dot(pos.xy, vec2(13.9898, 78.233))) * 43758.5453123);
                }
                                
                float noise(vec2 pos) {
                    vec2 i = floor(pos);
                    vec2 f = fract(pos);
                    float a = random(i + vec2(0.0, 0.0));
                    float b = random(i + vec2(1.0, 0.0));
                    float c = random(i + vec2(0.0, 1.0));
                    float d = random(i + vec2(1.0, 1.0));
                    vec2 u = f * f * (3.0 - 2.0 * f);
                    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
                }
                                
                float fbm(vec2 pos) {
                    float v = 0.0;
                    float a = 0.5;
                    vec2 shift = vec2(100.0);
                    mat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));
                    for (int i = 0; i < NUM_OCTAVES; i++) {
                        float dir = mod(float(i), 2.0) > 0.5 ? 1.0 : -1.0;
                        v += a * noise(pos - 0.05 * dir * time);
                                
                        pos = rot * pos * 2.0 + shift;
                        a *= 0.5;
                    }
                    return v;
                }
                                
                void main(void) {
                    vec2 p = (gl_FragCoord.xy * 3.0 - resolution.xy) / min(resolution.x, resolution.y);
                    p -= vec2(12.0, 0.0);
                                
                    float t = 0.0, d;
                                
                    float time2 = 1.0;
                                
                    vec2 q = vec2(0.0);
                    q.x = fbm(p + 0.00 * time2);
                    q.y = fbm(p + vec2(1.0));
                    vec2 r = vec2(0.0);
                    r.x = fbm(p + 1.0 * q + vec2(1.7, 1.2) + 0.15 * time2);
                    r.y = fbm(p + 1.0 * q + vec2(8.3, 2.8) + 0.126 * time2);
                    float f = fbm(p + r);
                   \s
                    // DS: hornidev
                    vec3 color = mix(
                        vec3(1.0, 1.0, 2.0),
                        vec3(1.0, 1.0, 1.0),
                        clamp((f * f) * 5.5, 1.2, 15.5)
                    );
                                
                    color = mix(
                        color,
                        vec3(1.0, 1.0, 1.0),
                        clamp(length(q), 2.0, 2.0)
                    );
                                
                    color = mix(
                        color,
                        vec3(0.3, 0.2, 1.0),
                        clamp(length(r.x), 0.0, 5.0)
                    );
                                
                    color = (f * f * f * 1.0 + 0.5 * 1.7 * 0.0 + 0.9 * f) * color;
                                
                    vec2 uv = gl_FragCoord.xy / resolution.xy;
                    float alpha = 50.0 - max(pow(100.0 * distance(uv.x, -1.0), 0.0), pow(2.0 * distance(uv.y, 0.5), 5.0));
                    gl_FragColor = vec4(color * 100.0, color.r);
                    gl_FragColor = vec4(color, alpha * color.r);
                }
                                
                """);

    }

    public void drawQuads(float x, float y, float width, float height) {
        buffer.begin(9, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y, 0).tex(0.0f, 0.0f).endVertex();
        buffer.pos(x, y + height, 0).tex(0.0f, 1.0f).endVertex();
        buffer.pos(x + width, y + height, 0).tex(1.0f, 1.0f).endVertex();
        buffer.pos(x + width, y, 0).tex(1.0f, 0.0f).endVertex();
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
        GL20.glShaderSource(gled, (CharSequence) FileUtil.readInputStream(new ByteArrayInputStream(string.getBytes())));
        GL20.glCompileShader(gled);
        return gled;
    }
}