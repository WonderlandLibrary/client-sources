package dev.excellent.impl.util.shader;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.impl.util.file.FileUtils;
import dev.excellent.impl.util.math.ScaleMath;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;
import lombok.Getter;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

@SuppressWarnings("FieldCanBeLocal")
public class ShaderLink implements IRenderAccess {
    @Getter
    private final int programID;

    public ShaderLink(String fragmentShaderLoc, String vertexShaderLoc) {
        if (!ini) {
            shadersInit();
        }
        int program = glCreateProgram();
        int status = cppInit(program, fragmentShaderLoc, vertexShaderLoc);
        if (status == 0) throw new IllegalStateException("Shader failed to link!");
        this.programID = program;
    }

    @Native
    private int cppInit(int program, String fragmentShaderLoc, String vertexShaderLoc) {
        String namespace = Excellent.getInst().getInfo().getNamespace();
        try {
            int fragmentShaderID;
            if ("clickgui1".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(clickGui1.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("clickgui2".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(clickGui2.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("mainmenu".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(mainmenu.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseUp".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseUp.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseDown".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseDown.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseUpBloom".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseUpBloom.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseDownBloom".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseDownBloom.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseUpGlow".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseUpGlow.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseDownGlow".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(kawaseDownGlow.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("test".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(test.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("roundedGradient".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(roundedGradient.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("roundedWithNoise".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(roundedWithNoise.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("roundedWithGlow".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(roundedWithGlow.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("roundedGradientWithGlow".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(roundedGradientWithGlow.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("rounded".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(rounded.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("fontRendererSubstring".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(fontRendererSubstring.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("glow".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(glow.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("colorpatch".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream(color_patch.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("kawaseUpF".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream("""
                                    #version 120
                                    uniform sampler2D image;
                                    uniform float offset;
                                    uniform vec2 resolution;
                                                
                                    void main()
                                    {
                                                
                                        vec2 uv = gl_TexCoord[0].xy / 2.0;
                                        vec2 halfpixel = resolution / 2.0;
                                        vec3 sum = texture2D(image, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset).rgb;
                                        sum += texture2D(image, uv + vec2(-halfpixel.x, halfpixel.y) * offset).rgb * 2.0;
                                        sum += texture2D(image, uv + vec2(0.0, halfpixel.y * 2.0) * offset).rgb;
                                        sum += texture2D(image, uv + vec2(halfpixel.x, halfpixel.y) * offset).rgb * 2.0;
                                        sum += texture2D(image, uv + vec2(halfpixel.x * 2.0, 0.0) * offset).rgb;
                                        sum += texture2D(image, uv + vec2(halfpixel.x, -halfpixel.y) * offset).rgb * 2.0;
                                        sum += texture2D(image, uv + vec2(0.0, -halfpixel.y * 2.0) * offset).rgb;
                                        sum += texture2D(image, uv + vec2(-halfpixel.x, -halfpixel.y) * offset).rgb * 2.0;
                                        gl_FragColor = vec4(sum / 12.0, 1);
                                    }
                        """.getBytes()), GL_FRAGMENT_SHADER);

            } else if ("kawaseDownF".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream("""
                                   #version 120
                                     
                                     uniform sampler2D image;
                                     uniform float offset;
                                     uniform vec2 resolution;
                                     
                                     void main()
                                     {
                                         vec2 uv = gl_TexCoord[0].xy * 2.0;
                                         vec2 halfpixel = resolution * 2.0;
                                         vec3 sum = texture2D(image, uv).rgb * 4.0;
                                         sum += texture2D(image, uv - halfpixel.xy * offset).rgb;
                                         sum += texture2D(image, uv + halfpixel.xy * offset).rgb;
                                         sum += texture2D(image, uv + vec2(halfpixel.x, -halfpixel.y) * offset).rgb;
                                         sum += texture2D(image, uv - vec2(halfpixel.x, -halfpixel.y) * offset).rgb;
                                         gl_FragColor = vec4(sum / 8.0, 1);
                                     }
                        """.getBytes()), GL_FRAGMENT_SHADER);
            } else if ("gradient".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream("""
                        #version 120
                                                
                        uniform vec2 location, rectSize;
                        uniform sampler2D tex;
                        uniform vec4 color1, color2, color3, color4;
                                                
                        #define NOISE .5/255.0
                                                
                        vec3 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){
                            vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);
                            //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/
                            color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));
                            return color;
                        }
                        void main() {
                            vec2 coords = (gl_FragCoord.xy - location) / rectSize;
                            float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;
                            gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4).rgb, texColorAlpha);
                        }""".getBytes()), GL_FRAGMENT_SHADER);
            } else if ("outline".equals(fragmentShaderLoc)) {
                fragmentShaderID = createShader(new ByteArrayInputStream("""
                        #version 120
                                     \s
                          uniform vec4 color;
                          uniform sampler2D textureIn, textureToCheck;
                          uniform vec2 texelSize, direction;
                          uniform float size;
                          
                          #define offset direction * texelSize
                          
                          void main() {
                              if (direction.y == 1) {
                                  if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;
                              }
                          
                              vec4 innerAlpha = texture2D(textureIn, gl_TexCoord[0].st);
                              innerAlpha *= innerAlpha.a;
                              for (float r = 1.0; r <= size; r ++) {
                                  vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);
                                  vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);
                                  colorCurrent1.rgb *= colorCurrent1.a;
                                  colorCurrent2.rgb *= colorCurrent2.a;
                                  innerAlpha += (colorCurrent1 + colorCurrent2) * r;
                              }
                              gl_FragColor = vec4(innerAlpha.rgb / innerAlpha.a, mix(innerAlpha.a, 1.0 - exp(-innerAlpha.a), step(0.0, direction.y)));
                          }""".getBytes()), GL_FRAGMENT_SHADER);
            } else {
                fragmentShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(namespace, String.format("shader/%s", fragmentShaderLoc))).getInputStream(), GL_FRAGMENT_SHADER);
            }
            glAttachShader(program, fragmentShaderID);

            //noinspection SwitchStatementWithTooFewBranches
            int vertexShaderID;
            if (vertexShaderLoc.equals("vertex")) {
                vertexShaderID = createShader(new ByteArrayInputStream(vertex.getBytes()), GL_VERTEX_SHADER);
            } else {
                vertexShaderID = createShader(mc.getResourceManager().getResource(new ResourceLocation(namespace, String.format("shader/%s", vertexShaderLoc))).getInputStream(), GL_VERTEX_SHADER);
            }
            glAttachShader(program, vertexShaderID);
        } catch (IOException ignored) {
        }

        glLinkProgram(program);
        int status = glGetProgrami(program, GL_LINK_STATUS);
        return status;
    }

    public static ShaderLink create(String loc) {
        return new ShaderLink(loc, "vertex");
    }

    public static ShaderLink create(String fragmentShaderLoc, String vertexShaderLoc) {
        return new ShaderLink(fragmentShaderLoc, vertexShaderLoc);
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

    public void setUniformf(String name, float... args) {
        int loc = glGetUniformLocation(programID, name);
        switch (args.length) {
            case 1 -> glUniform1f(loc, args[0]);
            case 2 -> glUniform2f(loc, args[0], args[1]);
            case 3 -> glUniform3f(loc, args[0], args[1], args[2]);
            case 4 -> glUniform4f(loc, args[0], args[1], args[2], args[3]);
        }
    }

    public void setUniformi(String name, int... args) {
        int loc = glGetUniformLocation(programID, name);
        switch (args.length) {
            case 1 -> glUniform1i(loc, args[0]);
            case 2 -> glUniform2i(loc, args[0], args[1]);
            case 3 -> glUniform3i(loc, args[0], args[1], args[2]);
            case 4 -> glUniform4i(loc, args[0], args[1], args[2], args[3]);
        }
    }

    public void setMat4fv(String name, FloatBuffer matrix) {
        int loc = glGetUniformLocation(programID, name);
        glUniformMatrix4fv(loc, false, matrix);
    }

    public void setMat4fv(String name, float[] matrix) {
        int loc = glGetUniformLocation(programID, name);
        glUniformMatrix4fv(loc, false, matrix);
    }

    public void setMat4fv(String name, MatrixStack matrix) {
        setMat4fv(name, matrix.getLast().getMatrix());
    }

    public void setMat4fv(String name, Matrix4f matrix) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        matrix.write(floatBuffer);
        setMat4fv(name, floatBuffer);
    }

    public static void drawQuads(double x, double y, double width, double height) {
        BUFFER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        BUFFER.pos(x, y + height, 0).tex(0, 1).endVertex();
        BUFFER.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        BUFFER.pos(x + width, y, 0).tex(1, 0).endVertex();
        BUFFER.pos(x, y, 0).tex(0, 0).endVertex();
        TESSELLATOR.draw();
    }

    public static void drawQuads(MatrixStack matrixStack, float x, float y, float width, float height) {
        Matrix4f matrix = matrixStack.getLast().getMatrix();

        BUFFER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        BUFFER.pos(matrix, x, y + height, 0).tex(0, 1).endVertex();
        BUFFER.pos(matrix, x + width, y + height, 0).tex(1, 1).endVertex();
        BUFFER.pos(matrix, x + width, y, 0).tex(1, 0).endVertex();
        BUFFER.pos(matrix, x, y, 0).tex(0, 0).endVertex();
        TESSELLATOR.draw();
    }

    public static void drawQuads() {
        Vector2d window = ScaleMath.getMouse(mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
        double width = window.x;
        double height = window.y;
        drawQuads(0, 0, width, height);
    }

    public static void drawQuads(double width, double height) {
        drawQuads(0, 0, width, height);
    }

    public static void drawScaledQuads() {
        drawQuads((mc.getMainWindow().getWidth() / mc.getMainWindow().getScaleFactor()), (mc.getMainWindow().getHeight() / mc.getMainWindow().getScaleFactor()));
    }

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);
        glShaderSource(shader, FileUtils.readInputStream(inputStream));
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.out.println(glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }

    @Protect(Protect.Type.ULTRA)
    @Native
    private static void shadersInit() {
        ini = true;
        color_patch = """
                #version 120
                                
                uniform sampler2D u_image;
                uniform vec4 u_color1, u_color2, u_color3, u_color4;
                #define NOISE 0.5/255.0
                                
                vec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){
                    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);
                    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/
                    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));
                    return color;
                }
                  
                void main() {
                    float alpha = texture2D(u_image, gl_TexCoord[0].st).a;
                    vec4 u_color = createGradient(gl_TexCoord[0].st, u_color1, u_color2, u_color3, u_color4);
                    gl_FragColor = vec4(u_color.rgb * 2, u_color.a * mix(0.0, alpha, step(0.0, alpha)));
                }
                """;
        vertex = """
                #version 120
                    
                void main() {
                    gl_TexCoord[0] = gl_MultiTexCoord0;
                    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
                }""";
        fontRendererSubstring = """
                #version 120
                uniform sampler2D texture;
                uniform vec4 color;
                uniform float end;
                            
                void main() {
                    vec2 texCoord = gl_TexCoord[0].xy;
                    vec2 fragCoord = gl_FragCoord.xy;
                    vec4 outColor = texture2D(texture, texCoord) * color;
                    float negative = 1 - smoothstep(0, 1, max((fragCoord.x - (end - 16)) / 16,0));
                    gl_FragColor = outColor * negative;
                }""";
        clickGui1 = """
                #ifdef GL_ES
                precision mediump float;
                #endif
                            
                #extension GL_OES_standard_derivatives : enable
                            
                uniform float red;
                uniform float green;
                uniform float blue;
                uniform float alpha;
                uniform float iTime;
                uniform vec2 iMouse;
                uniform vec2 iResolution;

                vec4 grade( void ) {
                	vec3 v = vec3(1,0,0);
                	vec3 d = vec3(1);
                	vec2 p =gl_FragCoord.xy / iResolution.xy;
                	vec3 col = vec3(abs((p.y-0.5)*-3.*cos(iTime)));
                	vec3 invrt =  1.0 - col;
                	return vec4(invrt,1.);
                            
                }
                            
                float rand(vec2 uv)
                {
                    return fract(sin(dot(uv, vec2(12.9898, 78.233))) * 43758.5453);
                }
                            
                vec2 uv2tri(vec2 uv)
                {
                    float sx = uv.x - uv.y / 2.0; // skewed x
                    float sxf = fract(sx);
                    float offs = step(fract(1.0 - uv.y), sxf);
                    return vec2(floor(sx) * 2.0 + sxf + offs, uv.y);
                }
                            
                float tri(vec2 uv)
                {
                    float sp = 1.2 + 3.3 * rand(floor(uv2tri(uv)));
                    return max(0.0, sin(sp * iTime));
                }
                            
                vec4 tri()
                {
                    vec2 uv = (gl_FragCoord.xy - iResolution.xy / 2.0) / iResolution.y;
                            
                    float t1 = iTime / 2.0;
                    float t2 = t1 + 0.5;
                            
                    float c1 = tri(uv * (2.0 + 4.0 * fract(t1)) + floor(t1));
                    float c2 = tri(uv * (2.0 + 4.0 * fract(t2)) + floor(t2));
                            
                    return vec4(mix(c1, c2, abs(1.0 - 2.0 * fract(t1))));
                }
                            
                void main()
                {
                	gl_FragColor = grade() * (tri()*vec4(red, green, blue, alpha));
                }""";
        clickGui2 = """
                uniform float red;
                uniform float green;
                uniform float blue;
                uniform float alpha;
                uniform float iTime;
                uniform vec2 iMouse;
                uniform vec2 iResolution;
                            
                #define TWO_PI 6.2831853
                             
                #define BIG_FLOAT 100000.
                            
                float h11(float seed) {
                    return fract(cos(seed*1179.30)*09384.7+173.812);
                }
                            
                float sdEquilateralTriangle(  in vec2 p, in float r )
                {
                    // https://www.shadertoy.com/view/Xl2yDW
                    const float k = sqrt(3.0);
                    p.x = abs(p.x) - r;
                    p.y = p.y + r/k;
                    if( p.x+k*p.y>0.0 ) p=vec2(p.x-k*p.y,-k*p.x-p.y)/2.0;
                    p.x -= clamp( p.x, -2.0*r, 0.0 );
                    return -length(p)*sign(p.y);
                }
                            
                mat2 rot(float th) {
                    return mat2(cos(th),sin(th),-sin(th),cos(th));
                }
                            
                float threeTrianglesSDF (vec2 p, float t) {
                   \s
                    float tri_dist = BIG_FLOAT;
                    vec2 uv_tri = p;
                   \s
                    float SHRINK_FACTOR = .38;
                   \s
                    t = mod(t,13.)-5.2;
                   \s
                    // LEFT TRI
                    uv_tri.x = p.x + .6; uv_tri.y = -p.y + .3;
                    uv_tri /= 1. - SHRINK_FACTOR*(smoothstep(0.,1.,t) - smoothstep(2.,3.,t));
                    uv_tri = rot(TWO_PI/3. * smoothstep(1.,2.,t)) * uv_tri;
                    tri_dist = min(tri_dist,sdEquilateralTriangle(uv_tri,.5));
                   \s
                    // MID TRI
                    uv_tri = p;
                    uv_tri /= 1. - SHRINK_FACTOR*(smoothstep(.5,1.5,t) - smoothstep(2.5,3.5,t));
                    uv_tri = rot(TWO_PI/3. * smoothstep(1.5,2.5,t)) * uv_tri;
                    tri_dist = min(tri_dist,sdEquilateralTriangle(uv_tri,.5));
                  \s
                    // RIGHT TRI
                    uv_tri.x = p.x - .6; uv_tri.y = -p.y + .3;
                    uv_tri /= 1. - SHRINK_FACTOR*(smoothstep(1.,2.,t) - smoothstep(3.,4.,t));
                    uv_tri = rot(TWO_PI/3. * smoothstep(2., 3., t)) * uv_tri;
                    tri_dist = min(tri_dist,sdEquilateralTriangle(uv_tri,.5));
                   \s
                    return tri_dist;
                }
                            
                float triangleBannerSDF(vec2 p, float t) {
                    float ix = floor(p.x/1.2);
                    p.x = mod(p.x,1.2) -.6;
                   \s
                    //t = mod(iTime,13.)-5.2;
                   \s
                    return threeTrianglesSDF( p , t - ix);
                }
                            
                vec4 col_strips(vec2 uv) {
                    float t = iTime;
                   \s
                   \s
                    uv = rot(.5)*uv;
                   \s
                    float row_margin = 1.2;
                    float iy = floor(uv.y/row_margin);
                    float time_factor = 1. + h11(iy);
                    uv.y = mod(uv.y,row_margin) - (row_margin/2.);
                    float tri_dist = triangleBannerSDF(uv , t*time_factor);
                   \s
                   \s
                    vec4 col = vec4(1.);
                    col *= 1. - smoothstep(0.,0.01,tri_dist);
                    col *= vec4(red,green,blue,alpha) * smoothstep(-.2,-.195,tri_dist);
                   \s
                    return col;
                }
                            
                            
                void main( void ) {
                    vec2 uv = gl_FragCoord.xy/iResolution.xy * 2. -1.;
                    uv *= 3.;
                    uv.x *= iResolution.x/iResolution.y;
                            
                   \s
                    gl_FragColor = col_strips(uv);
                }""";
        mainmenu = """
                uniform float red;
                uniform float green;
                uniform float blue;
                uniform float iTime;
                uniform vec2 iResolution;
                            
                float hash11(float p) {
                	vec3 p3  = fract(vec3(p) * vec3(.1031, .11369, .13787));
                    p3 += dot(p3, p3.yzx + 19.19);
                    return fract((p3.x + p3.y) * p3.z);
                }
                            
                // 1d smooth noise
                float snoise1d(float f) {
                    return
                        mix(
                            hash11(floor(f)),
                            hash11(floor(f+1.)),
                            smoothstep(0., 1., fract(f))
                        );
                }
                            
                /* star shape (2d distance estimate)
                   p = input coordinate
                   n = number of sides
                   r = radius
                   i = inset amount (0.0=basic polygon, 1.0=typical star
                */
                float StarDE(vec2 p, float n, float r, float i) {
                    float rep = floor(-atan(p.x, p.y)*(n/6.28)+.5) / (n/6.28);
                    float s, c;
                    p = mat2(c=cos(rep), s=-sin(rep), -s, c) * p;
                    float a = (i+1.) * 3.14 / n;
                    p = mat2(c=cos(a), s=-sin(a), -s, c) * vec2(-abs(p.x), p.y-r);
                    return length(max(vec2(0.), p));
                }
                            
                // StarDE, but with eyes
                float Starguy(vec2 p, float n, float r, float i, vec2 l) {
                            
                    // blink
                    float b = pow(abs(fract(.1*iTime-.1)-.5)*2., 72.);
                   \s
                    // eye look
                    vec2 p2 = p + l;
                   \s
                    return
                        max(
                            StarDE(p, n, r, i),
                            // eyes
                            -length(
                                vec2(
                                    min(0., -abs(abs(p2.x)-r*.2)+r*b*.1),
                                    min(0., -abs(p2.y)+r*(1.-b)*.1)
                                )
                            )+r*.13
                           \s
                        );
                }
                            
                void main( void ) {
                	vec2 p = (gl_FragCoord.xy-iResolution.xy/2.) / iResolution.y;
                	
                	// iTime
                	float t = .7 * iTime;
                	
                	// bob up and down
                	vec2 p2 = p;
                	p2.y=(gl_FragCoord.y-iResolution.y/1.25) / iResolution.y;
                	p2.y += .025 * sin(4.*t);
                	
                	// warping (pinned inversion)
                	p2 = p2 / dot(p2, p2) - .17 * vec2(sin(iTime), cos(4.*t));
                	p2 = p2 / dot(p2, p2);
                	
                	vec2 look = .01 * vec2(cos(.71*iTime*2.), sin(.44*iTime));
                	
                	// Starguy
                	float star = Starguy(p2, 5., 0.15, .85-length(p), look);
                	
                	// Starguy + radiation
                	vec3 col =
                		mix(
                		 vec3(1.0),
                		    vec3(0.0, 0.0, 0.0),
                		    clamp(star/.01, 0., 1.)
                		) + 4.5 * vec3(red, green, blue) * (1. - pow(star, .1));
                	
                	gl_FragColor = vec4(col, 1.0);
                }""";
        roundedGradient = """
                #version 120

                uniform vec2 location, size;
                uniform vec4 color1, color2, color3, color4;
                uniform float radius;

                #define NOISE 0.5/255.0

                float roundSDF(vec2 p, vec2 b, float r) {
                    return length(max(abs(p) - b, 0.0)) - r;
                }

                float noise(vec2 p) {
                    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
                }
                            
                vec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){
                    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);
                            
                    vec2 noiseCoords = coords * 10.0; // Увеличиваем масштаб шума
                    float n = noise(noiseCoords);
                    color += mix(NOISE, -NOISE, n);
                            
                    return color;
                }

                void main() {
                    if(radius > 0.0) {
                        vec2 st = gl_TexCoord[0].st;
                        vec2 halfSize = size * 0.5;
                                
                        float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(halfSize - (gl_TexCoord[0].st * size), halfSize - radius - 0.5, radius)));
                        vec4 gradient = createGradient(st, color1, color2, color3, color4);
                        gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);
                    } else {
                        vec2 st = gl_TexCoord[0].st;
                    
                        vec4 gradient = createGradient(st, color1, color2, color3, color4);
                        gl_FragColor = vec4(gradient.rgb, gradient.a  * 1.0);
                    }
                }""";
        rounded = """
                #version 120
                            
                uniform vec2 size;
                uniform float radius;
                uniform vec4 color;
                            
                void main(void)
                {
                    if (radius == 0.0) {
                        gl_FragColor = vec4(color.rgb, color.a);
                    } else {
                        gl_FragColor = vec4(color.rgb, color.a * smoothstep(1.0, 0.0, length(max((abs(gl_TexCoord[0].st - 0.5) + 0.5) * size - size + radius, 0.0)) - radius + 0.5));
                    }
                }""";
        kawaseUp = """
                #version 120

                uniform sampler2D inTexture, textureToCheck;
                uniform vec2 halfpixel, offset, iResolution;
                uniform float saturation;
                uniform int check;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);
                    sum.rgb *= saturation;
                    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;
                    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);
                    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;
                    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);
                    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;
                    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);
                    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;
                    
                    gl_FragColor = vec4(sum.rgb / 10.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));
                }
                """;

        kawaseDown = """
                #version 120

                uniform sampler2D inTexture;
                uniform vec2 offset, halfpixel, iResolution;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;
                    sum += texture2D(inTexture, uv - halfpixel.xy * offset);
                    sum += texture2D(inTexture, uv + halfpixel.xy * offset);
                    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
                    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);
                    
                    gl_FragColor = vec4(sum.rgb * .125, 1.0);
                }
                """;
        kawaseUpBloom = """
                #version 120

                uniform sampler2D inTexture, textureToCheck;
                uniform vec2 halfpixel, offset, iResolution;
                uniform float saturation;
                uniform int check;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);
                    sum.rgb *= saturation;
                    sum.rgb *= sum.a;
                    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);
                    smpl1.rgb *= smpl1.a;
                    sum += smpl1 * 2.0;
                    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);
                    smp2.rgb *= smp2.a;
                    sum += smp2;
                    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);
                    smp3.rgb *= smp3.a;
                    sum += smp3 * 2.0;
                    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);
                    smp4.rgb *= smp4.a;
                    sum += smp4;
                    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp5.rgb *= smp5.a;
                    sum += smp5 * 2.0;
                    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);
                    smp6.rgb *= smp6.a;
                    sum += smp6;
                    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);
                    smp7.rgb *= smp7.a;
                    sum += smp7 * 2.0;
                    vec4 result = sum / 12.0;
                    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));
                }""";

        kawaseDownBloom = """
                #version 120

                uniform sampler2D inTexture;
                uniform vec2 offset, halfpixel, iResolution;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);
                    sum.rgb *= sum.a;
                    sum *= 4.0;
                    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);
                    smp1.rgb *= smp1.a;
                    sum += smp1;
                    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);
                    smp2.rgb *= smp2.a;
                    sum += smp2;
                    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp3.rgb *= smp3.a;
                    sum += smp3;
                    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp4.rgb *= smp4.a;
                    sum += smp4;
                    vec4 result = sum / 8.0;
                    gl_FragColor = vec4(result.rgb / result.a, result.a);
                }""";
        kawaseUpGlow = """
                #version 120

                uniform sampler2D inTexture, textureToCheck;
                uniform vec2 halfpixel, offset, iResolution;
                uniform vec4 color;
                            
                uniform float saturation;
                uniform int check;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);
                    sum.rgb *= saturation;
                    sum.rgb *= sum.a;
                    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);
                    smpl1.rgb *= smpl1.a;
                    sum += smpl1 * 2.0;
                    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);
                    smp2.rgb *= smp2.a;
                    sum += smp2;
                    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);
                    smp3.rgb *= smp3.a;
                    sum += smp3 * 2.0;
                    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);
                    smp4.rgb *= smp4.a;
                    sum += smp4;
                    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp5.rgb *= smp5.a;
                    sum += smp5 * 2.0;
                    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);
                    smp6.rgb *= smp6.a;
                    sum += smp6;
                    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);
                    smp7.rgb *= smp7.a;
                    sum += smp7 * 2.0;
                    vec4 result = sum / 10.0;
                    gl_FragColor = vec4(color.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));
                }""";

        kawaseDownGlow = """
                #version 120

                uniform sampler2D inTexture;
                uniform vec2 offset, halfpixel, iResolution;
                uniform vec4 color;

                void main() {
                    vec2 uv = vec2(gl_FragCoord.xy / iResolution);
                    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);
                    sum.rgb *= sum.a;
                    sum *= 4.0;
                    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);
                    smp1.rgb *= smp1.a;
                    sum += smp1;
                    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);
                    smp2.rgb *= smp2.a;
                    sum += smp2;
                    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp3.rgb *= smp3.a;
                    sum += smp3;
                    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);
                    smp4.rgb *= smp4.a;
                    sum += smp4;
                    vec4 result = sum / 12.0;
                    gl_FragColor = vec4(color.rgb / result.a, result.a);
                }""";
        roundedWithNoise = """
                #version 120

                uniform vec2 resolution, start, size;
                uniform float round, alpha;

                float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                    vec2 d = abs(rect_center - pixel) - rect_size * 0.5;
                    return length(max(d,0.0));
                }
                float lum(vec3 col) {
                    return (col.r * 0.3) + (col.g * 0.59) + (col.b * 0.11);
                }
                float githubNoise(vec2 co){
                    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
                }
                vec3 n(vec2 xy) {
                    float l = lum(vec3(githubNoise(xy)));
                    return vec3(l);
                }

                float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                    return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                }

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                pos.y = resolution.y - pos.y;
                    float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                gl_FragColor = vec4(n(pos - start) * alpha, rr * alpha);
                }""";
        roundedWithGlow = """
                #version 130

                uniform vec4 color;
                uniform vec2 resolution, start, size;
                uniform float round, softness;

                float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                    vec2 d = abs(rect_center - pixel) - rect_size * 0.5;
                    return length(max(d,0.0));
                }

                float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                    return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                }

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                	pos.y = resolution.y - pos.y;
                    float rr = 1.0 - smoothstep(round / 5.0 - softness, round / 5.0 + softness, roundRect(pos, start, size, round));
                	gl_FragColor = vec4(color.rgb * color.a, rr * color.a);
                }""";
        roundedGradientWithGlow = """
                #version 120

                uniform vec3 color1, color2, color3, color4;
                uniform vec2 resolution, start, size;
                uniform float round, alpha, softness;

                float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                    vec2 d = abs(rect_center - pixel) - rect_size * 0.5;
                    return length(max(d,0.0));
                }

                float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                    return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                }

                vec3 createGradient(vec2 pos) {
                    vec2 norm = vec2(start.x - size.x * 0.5, start.y - size.y * 0.5);
                vec2 ctx = (pos - norm) / size;
                vec3 res = mix(mix(color1, color2, ctx.y), mix(color3, color4, ctx.y), ctx.x);
                return res;
                }

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                pos.y = resolution.y - pos.y;
                    float rr = 1.0 - smoothstep(round / 5.0 - softness, round / 5.0 + softness, roundRect(pos, start, size, round));
                gl_FragColor = vec4(createGradient(pos), rr * alpha);
                }""";
        glow = """
                #version 120

                uniform sampler2D texture;
                uniform vec2 texelSize;
                uniform vec3 color;

                void main() {
                    vec4 center = texture2D(texture, gl_TexCoord[0].xy);

                    if (center.a != 0) discard;

                    float alpha = 0;
                    vec4 endCol = vec4(0);
                    for (float x = -1; x <= 1; x++) {
                        for (float y = -1; y <= 1; y++) {
                            vec4 curColor = texture2D(texture, gl_TexCoord[0].xy + vec2(texelSize.x * x, texelSize.y * y));
                            if (curColor.a != 0) {
                                alpha += max(0, (2 - sqrt(x * x + y * y)));
                            }
                            curColor.rgb *= curColor.a;
                            endCol += curColor;
                        }
                    }
                    gl_FragColor = vec4(color, alpha);
                }""";
    }

    private static boolean ini;

    private static String vertex;
    private static String color_patch;
    private static String fontRendererSubstring;
    private static String clickGui1;
    private static String clickGui2;
    private static String mainmenu;
    private static String roundedGradient;
    private static String rounded;
    private static String kawaseUp;

    private static String kawaseDown;
    private static String kawaseUpBloom;

    private static String kawaseDownBloom;
    private static String kawaseUpGlow;

    private static String kawaseDownGlow;
    private static String roundedWithNoise;
    private static String roundedWithGlow;
    private static String roundedGradientWithGlow;
    private static String glow;
    private final String test = """
            """;
}