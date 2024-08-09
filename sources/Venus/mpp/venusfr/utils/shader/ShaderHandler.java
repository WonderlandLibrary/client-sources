/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL20;

public class ShaderHandler
implements IMinecraft {
    private final int programID;
    private String shaderMainMenu = "#ifdef GL_ES\nprecision mediump float;\n#endif\n\n// glslsandbox uniforms\nuniform float time;\nuniform vec2 resolution;\n\n// shadertoy emulation\nfloat iTime = 0.0;\n#define iResolution resolution\n\n// Protect glslsandbox uniform names\n#define time stemu_time\n\n#define ALL_COLORS 1\n\nfloat time;\n\nmat2 rot(float a) {\n  float ca=cos(a);\n  float sa=sin(a);\n  return mat2(ca,sa,-sa,ca);  \n}\n\nfloat box(vec3 p, vec3 s) {\n  p=abs(p)-s;\n  return max(p.x, max(p.y,p.z));\n}\n\nvec3 fr(vec3 p, float t) {\n\n  //float s = 1.0 - exp(-fract(time*1.0))*0.8;\n  float s = 0.7 - smoothstep(0.0,1.0,abs(fract(time*0.1)-0.5)*2.0)*0.3;\n  for(int i=0; i<5; ++i) {\n    \n    float t2=t+float(i);\n    p.xy *= rot(t2);\n    p.yz *= rot(t2*.7);\n    \n    float dist = 10.0;\n    p=(fract(p/dist-.5)-.5)*dist;\n    p=abs(p);\n    p-=s;\n    \n  }\n  \n  return p;\n}\n\nfloat at = 0.0;\nfloat at2 = 0.0;\nfloat at3 = 0.0;\nfloat map(vec3 p) {\n  \n  \n  vec3 bp=p;\n  \n  p.xy *= rot((p.z*0.023+time*0.1)*0.3);\n  p.yz *= rot((p.x*0.087)*0.4);\n  \n  float t=time*0.5;\n  vec3 p2 = fr(p, t * 0.2);\n  vec3 p3 = fr(p+vec3(5,0,0), t * 0.23);\n  \n  float d1 = box(p2, vec3(1,1.3,4));\n  float d2 = box(p3, vec3(3,0.7,0.4));\n  \n  float d = max(abs(d1), abs(d2))-0.2;\n  float dist = 1.0;\n  vec3 p4=(fract(p2/dist-.5)-.5)*dist;\n  float d3 = box(p4, vec3(0.4));\n  //d = max(d, -d3);\n  d = d - d3*0.4;\n  \n  //d = max(d, length(bp)-15);\n  \n  \n  //float f=p.z + time*4;\n  //p.x += sin(f*0.05)*6;\n  //p.y += sin(f*0.12)*4;\n  //d = max(d, -length(p.xy)+10);\n  \n  at += 0.13/(0.13+abs(d));\n  \n  float d5 = box(bp, vec3(4));\n  \n  float dist2 = 8.0;\n  vec3 p5=bp;\n  p5.z = abs(p5.z)-13.0;\n  p5.x=(fract(p5.x/dist2-.5)-.5)*dist2;\n  float d6 = length(p5.xz)-1.0;\n  \n  at2 += 0.2/(0.15+abs(d5));\n  at3 += 0.2/(0.5+abs(d6));\n  \n  return d;\n}\n\nvoid cam(inout vec3 p) {\n  \n  float t=time*0.1;\n  p.yz *= rot(t);\n  p.zx *= rot(t*1.2);\n}\n\nfloat rnd(vec2 uv) {  \n  return fract(dot(sin(uv*752.322+uv.yx*653.842),vec2(254.652)));\n}\n\nvoid mainImage(out vec4 fragColor, in vec2 fragCoord)\n{\n  time = iTime+0.6;\n  vec2 uv = vec2(gl_FragCoord.x / resolution.x, gl_FragCoord.y / resolution.y);\n  uv -= 0.5;\n  uv /= vec2(resolution.y / resolution.x, 1);\n  \n  float factor = 0.9 + 0.1*rnd(uv);\n  //factor = 1;\n\n  vec3 s=vec3(0,0,-15);\n  vec3 r=normalize(vec3(-uv, 1));\n  \n  cam(s);\n  cam(r);\n  \n  vec3 p=s;\n  int i=0;\n  \n  for(int i=0; i<80; ++i) {\n    float d=abs(map(p));\n    d = abs(max(d, -length(p-s)+6.0));\n    d *= factor;\n    if(d<0.001) {\n      d = 0.1;\n      //break;\n    }\n    p+=r*d;\n  }\n  \n  vec3 col=vec3(0);\n  //col += pow(1-i/101.0,8);\n  \n  vec3 sky = mix(vec3(1.0,0.5,0.3), vec3(0.2,1.5,0.7), pow(abs(r.z),8.0));\n  sky = mix(sky, vec3(0.4,0.5,1.7), pow(abs(r.y),8.0));\n  \n  //col += at*0.002 * sky;\n  col += pow(at2*0.008, 1.0) * sky;\n  col += pow(at3*0.072, 2.0) * sky * vec3(0.7,0.3,1.0) * 2.0;\n  \n  col *= 1.2-length(uv);\n  \n  col = 1.0-exp(-col*15.0);\n  col = pow(col, vec3(1.2));\n  col *= 1.2;\n  //col += 0.2*sky;\n  \n  //col = vec3(rnd(uv));\n  \n  fragColor = vec4(col, 1);\n  #undef time\n}\n\nvoid main(void)\n{\n    iTime = time;\n    mainImage(gl_FragColor, gl_FragCoord.xy);\n}\n\n\n\n\n";

    public ShaderHandler(String string, String string2) {
        int n = GL20.glCreateProgram();
        try {
            GL20.glAttachShader(n, switch (string) {
                case "shaderMainMenu" -> this.createShader(new ByteArrayInputStream(this.shaderMainMenu.getBytes()), 35632);
                default -> this.createShader(mc.getResourceManager().getResource(new ResourceLocation(string)).getInputStream(), 35632);
            });
            int n2 = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(string2)).getInputStream(), 35633);
            GL20.glAttachShader(n, n2);
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        GL20.glLinkProgram(n);
        int n3 = GL20.glGetProgrami(n, 35714);
        if (n3 == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = n;
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public ShaderHandler(String string) {
        this(string, new ResourceLocation("minecraft", "venusfr/shader/vertex.vsh").getPath());
    }

    public void setUniformf(String string, float ... fArray) {
        int n = GL20.glGetUniformLocation(this.programID, string);
        switch (fArray.length) {
            case 1: {
                GL20.glUniform1f(n, fArray[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(n, fArray[0], fArray[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(n, fArray[0], fArray[1], fArray[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(n, fArray[0], fArray[1], fArray[2], fArray[3]);
            }
        }
    }

    public void setUniformi(String string, int ... nArray) {
        int n = GL20.glGetUniformLocation(this.programID, string);
        if (nArray.length > 1) {
            GL20.glUniform2i(n, nArray[0], nArray[1]);
        } else {
            GL20.glUniform1i(n, nArray[0]);
        }
    }

    public static void drawQuads(float f, float f2, float f3, float f4) {
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(f, f2);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(f, f2 + f4);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(f + f3, f2 + f4);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(f + f3, f2);
        GL20.glEnd();
    }

    public static void drawQuads() {
        float f = mc.getMainWindow().getScaledWidth();
        float f2 = mc.getMainWindow().getScaledHeight();
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(0.0f, 0.0f);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(0.0f, f2);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(f, f2);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(f, 0.0f);
        GL20.glEnd();
    }

    public int createShader(InputStream inputStream, int n) {
        int n2 = GL20.glCreateShader(n);
        GL20.glShaderSource(n2, (CharSequence)ShaderHandler.readInputStream(inputStream));
        GL20.glCompileShader(n2);
        if (GL20.glGetShaderi(n2, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(n2, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", n));
        }
        return n2;
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String string;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string).append('\n');
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

