package dev.excellent.impl.util.test;

import dev.excellent.api.interfaces.game.IMinecraft;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class Shader implements IMinecraft {

    public static Shader KAWASE_DOWN;
    public static Shader KAWASE_UP;
    public static Shader DUAL_ROUNDED_GRADIENT_RECT;
    public static Shader DUAL_ROUNDED_RECT;
    public static Shader ROUNDED_RECT;
    public static Shader ROUNDED_RECT_WITH_GLOW;
    public static Shader BLURRED_ROUNDED_RECT;
    public static Shader ROUNDED_RECT_WITH_NOISE;
    public static Shader FILLED_CIRCLE;
    public static Shader CIRCLE_TEXTURE;
    public static Shader FONT_RENDERER_WIDTH;
    public static Shader FRAMES;
    public static Shader COLOR_MULTIPLIER;
    public static Shader BLUR;
    public static Shader BLUR_WITH_COLOR;
    public static Shader BLUR_GL_COLOR;
    public static Shader HAND_BLUR;
    public static Shader PROGRESS_CIRCLE;
    public static Shader GRADIENT_ROUNDED_RECT;
    public static Shader GRADIENT_ROUNDED_RECT_WITH_GLOW;
    public static Shader SOLO_COLOR;
    public static Shader ROUNDED_TEXTURE;

    public static void init() {
        KAWASE_DOWN = new Shader(
                """
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
                        """)
                .loadShader();
        KAWASE_UP = new Shader(
                """
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
                        }""")
                .loadShader();
        DUAL_ROUNDED_GRADIENT_RECT = new Shader("""
                #version 130

                uniform vec4 color1, color2, color3, color4;
                uniform vec4 secondColor;
                uniform vec2 resolution, start, size, swap;
                uniform float round, alpha;

                float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                    vec2 d = abs(rect_center - pixel) - rect_size * 0.5;
                    return length(max(d,0.0));
                }

                vec4 createGradient(vec2 pos) {
                    vec2 norm = vec2(start.x - size.x * 0.5, start.y - size.y * 0.5);
                	vec2 ctx = (pos - norm) / size;
                	vec4 res = mix(mix(color1, color2, ctx.y), mix(color3, color4, ctx.y), ctx.x);
                	return res;
                }

                float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                    return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                }

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                	pos.y = resolution.y - pos.y;
                    float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                	vec4 gradient = createGradient(pos);
                	gradient.a *= alpha;
                    vec4 result = mix(gradient, secondColor, clamp(min(pos.x - swap.x, pos.y - swap.y), 0, 1));
                	gl_FragColor = vec4(result.rgb, rr * result.a);
                }""").loadShader()
                .useContextMatrix(new String[]{"start", "swap"}, new String[]{"size", "round"});
        DUAL_ROUNDED_RECT = new Shader("""
                #version 130

                uniform vec4 firstColor, secondColor;
                uniform vec2 resolution, start, size, swap;
                uniform float rectRound;

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
                    float rr = 1.0 - smoothstep(rectRound / 5.0, rectRound / 5.0 + 2.0, roundRect(pos, start, size, rectRound));
                    vec4 result = mix(firstColor, secondColor, floor(clamp(min(pos.x - swap.x, pos.y - swap.y), 0, 1)));
                	gl_FragColor = vec4(result.rgb * result.a, rr * result.a);
                }""").loadShader()
                .useContextMatrix(new String[]{"start", "swap"}, new String[]{"size", "rectRound"});
        ROUNDED_RECT = new Shader("""
                #version 130

                uniform vec4 color;
                uniform vec2 resolution, start, size;
                uniform float round;

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
                    float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                	gl_FragColor = vec4(color.rgb * color.a, rr * color.a);
                }""").loadShader()
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round"});
        ROUNDED_RECT_WITH_GLOW = new Shader("""
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
                }""").loadShader()
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round", "softness"});
        BLURRED_ROUNDED_RECT = new Shader(
                """
                        #version 120

                        uniform sampler2D blurredTexture;
                        uniform vec2 resolution, start, size;
                        uniform float round;

                        float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                            vec2 d = abs(rect_center - pixel) - rect_size * 0.5;
                            return length(max(d,0.0));
                        }

                        float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                            return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                        }

                        void main() {
                            vec2 pos = gl_FragCoord.xy;
                            vec2 blurredPos = pos / resolution;
                            vec3 blurredColor = texture2D(blurredTexture, blurredPos).rgb;
                        pos.y = resolution.y - pos.y;
                            float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                        gl_FragColor = vec4(blurredColor, rr);
                        }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round"}).loadShader();
        ROUNDED_RECT_WITH_NOISE = new Shader(
                """
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
                        }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round"}).loadShader();
        FILLED_CIRCLE = new Shader(
                """
                        #version 130
                        uniform vec4 color;
                        uniform vec2 resolution;
                        uniform vec2 start;
                        uniform float width;

                        void main() {
                            vec2 pos = gl_FragCoord.xy;
                            pos.y = resolution.y - pos.y;
                            float dst = distance(pos, start);
                            float a = smoothstep(0, 2.5f, max(width - dst, 0));
                            gl_FragColor = color * a;
                        }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"width"}).loadShader();
        CIRCLE_TEXTURE = new Shader("""
                #version 130

                uniform sampler2D texture;
                uniform float rgbMultiplier;
                uniform float edge0;
                uniform float edge1;

                void main() {
                    vec2 texCoord = gl_TexCoord[0].xy;
                    vec4 color = texture2D(texture, texCoord);
                    float dst = length(vec2(0.5) - texCoord);
                    vec4 resultColor = color * smoothstep(edge0, edge1, 1 - dst);
                    resultColor.rgb *= rgbMultiplier;
                    gl_FragColor = resultColor;
                }""").loadShader();
        FONT_RENDERER_WIDTH = new Shader(
                """
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
                        }""")
                .loadShader();
        FRAMES = new Shader("""
                #version 130

                uniform sampler2D texture;
                uniform vec2 resolution;
                uniform vec2 data;

                void main() {
                    vec2 texCoord = gl_TexCoord[0].xy;
                    vec2 fragCoord = gl_FragCoord.xy;
                	fragCoord.y = resolution.y - fragCoord.y;
                    float dst = abs(data.x - fragCoord.y);
                    float negative = 1 - smoothstep(0, 1, max((dst - data.y) / 18, 0));
                    gl_FragColor = texture2D(texture, texCoord) * negative;
                }""").loadShader();
        COLOR_MULTIPLIER = new Shader("""
                #version 130

                uniform sampler2D texture;
                uniform vec4 inColor;

                void main() {
                    vec2 texPos = gl_TexCoord[0].xy;
                    gl_FragColor = texture2D(texture, texPos) * inColor;
                }""").loadShader();
        BLUR = new Shader("""
                uniform sampler2D blurredTexture;
                uniform vec2 resolution;

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                    vec2 blurredPos = pos / resolution;
                	pos.y = resolution.y - pos.y;
                	gl_FragColor = texture2D(blurredTexture, blurredPos);
                }""")
                .loadShader();
        BLUR_WITH_COLOR = new Shader(
                """
                        uniform sampler2D blurredTexture;
                        uniform vec2 resolution;
                        uniform vec4 c;
                        void main() {
                            vec2 pos = gl_FragCoord.xy;
                            vec2 blurredPos = pos / resolution;
                        	pos.y = resolution.y - pos.y;
                        	gl_FragColor = texture2D(blurredTexture, blurredPos) * c;
                        }""").loadShader();
        BLUR_GL_COLOR = new Shader("""
                uniform sampler2D blurredTexture;
                uniform vec2 resolution;

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                    vec2 blurredPos = pos / resolution;
                	pos.y = resolution.y - pos.y;
                	gl_FragColor =  gl_Color +  gl_Color * texture2D(blurredTexture, blurredPos);
                }""").loadShader();
        HAND_BLUR = new Shader("""
                uniform sampler2D originalTexture;
                uniform sampler2D blurredTexture;
                uniform vec4 multiplier;
                uniform vec2 viewOffset;
                uniform vec2 resolution;

                void main() {
                    vec2 pos = gl_FragCoord.xy + viewOffset;
                    vec4 srcColor = texture2D(originalTexture, gl_TexCoord[0].xy);
                    vec2 blurredPos = pos / resolution;
                	pos.y = resolution.y - pos.y;
                	gl_FragColor = texture2D(blurredTexture, blurredPos) * multiplier * srcColor.a;
                }""").loadShader();
        PROGRESS_CIRCLE = new Shader("""
                #version 120

                uniform sampler2D texture;
                uniform vec3 inColor;
                uniform float procent;

                void main() {
                    vec2 texCoord = gl_TexCoord[0].xy;
                    vec4 color = texture2D(texture, texCoord);
                    float alpha = clamp((color.r - procent) * 1000000, 0, 1);
                    gl_FragColor = vec4(inColor, alpha * color.a);
                }""").loadShader();
        GRADIENT_ROUNDED_RECT = new Shader("""
                #version 120

                uniform vec3 color1, color2, color3, color4;
                uniform vec2 resolution, start, size;
                uniform float round, alpha;

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
                    float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                gl_FragColor = vec4(createGradient(pos), rr * alpha);
                }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round"}).loadShader();
        GRADIENT_ROUNDED_RECT_WITH_GLOW = new Shader("""
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
                }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"softness", "size", "round"})
                .loadShader();
        SOLO_COLOR = new Shader(
                """
                        #version 120
                        uniform vec4 color;
                        void main() {
                            gl_FragColor = color;
                        }""")
                .loadShader();
        ROUNDED_TEXTURE = new Shader("""
                #version 130
                uniform sampler2D texture;
                uniform vec2 resolution, start, size;
                uniform float round;
                 uniform vec3 cb;\s
                uniform float alpha;

                float rect(vec2 pixel, vec2 rect_center, vec2 rect_size) {
                    vec2 d = abs(rect_center - pixel) - rect_size * 0.5;    return length(max(d, 0.0));
                }

                float roundRect(vec2 pixel, vec2 rect_center, vec2 rect_size, float rect_round) {
                    return rect(pixel, rect_center, rect_size - vec2(rect_round*2.0)) - rect_round;
                }

                void main() {
                    vec2 pos = gl_FragCoord.xy;
                    vec4 smpl = texture2D(texture, gl_TexCoord[0].xy);
                    pos.y = resolution.y - pos.y;
                    float rr = 1.0 - smoothstep(round / 5.0, round / 5.0 + 2.0, roundRect(pos, start, size, round));
                    gl_FragColor = vec4(smpl.rgb * cb * alpha, smpl.a * rr * alpha);
                }""")
                .useContextMatrix(new String[]{"start"}, new String[]{"size", "round"}).loadShader();
    }

    private final String fragment;
    private String[] positions;
    private String[] sizes;
    private int program;

    public Shader(String fragment) {
        this.fragment = fragment;
    }

    public Shader loadShader() {
        this.program = GL20.glCreateProgram();
        int frag = GL20.glCreateShader(35632);
        GL20.glShaderSource(frag, this.getSource());
        GL20.glCompileShader(frag);
        this.checkShader(frag);
        GL20.glAttachShader(this.program, frag);
        GL20.glLinkProgram(this.program);
        return this;
    }

    public Shader useContextMatrix(String[] positions, String[] sizes) {
        this.positions = positions;
        this.sizes = sizes;
        return this;
    }

    private String getSource() {
        return this.fragment;
    }

    private void checkShader(int shader) {
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            String s = GL20.glGetShaderInfoLog(shader, 256);
            System.out.println("Error [ " + s + " ]");
        }
    }

    public void initResolution(boolean scalePixel) {
        this.setFloatValue("resolution",
                scalePixel ? (1.0f / mc.getMainWindow().getFramebufferWidth()) : (mc.getMainWindow().getFramebufferWidth()),
                scalePixel ? (1.0f / mc.getMainWindow().getFramebufferHeight())
                        : (mc.getMainWindow().getFramebufferHeight()));
    }

    public void use() {
        GL20.glUseProgram(this.program);
    }

    public void release() {
        GL20.glUseProgram(0);
    }

    public void setFloatValue(String name, float... values) {
        int location = GL20.glGetUniformLocation(this.program, name);
        switch (values.length) {
            case 1: {
//                if (this.contains(this.sizes, name)) {
//                    values[0] = GLHelper.sizeX(values[0]);
//                }
                GL20.glUniform1f(location, values[0]);
                break;
            }
            case 2: {
//                if (this.contains(this.sizes, name)) {
//                    values[0] = GLHelper.sizeX(values[0]);
//                    values[1] = GLHelper.sizeY(values[1]);
//                }
//                if (this.contains(this.positions, name)) {
//                    values[0] = GLHelper.positionX(values[0]);
//                    values[1] = GLHelper.positionY(values[1]);
//                }
                GL20.glUniform2f(location, values[0], values[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(location, values[0], values[1], values[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(location, values[0], values[1], values[2], values[3]);
                break;
            }
        }
    }

    public void setFloatValue(String name, FloatBuffer buffer) {
        GL20.glUniform1fv(GL20.glGetUniformLocation(this.program, name), buffer);
    }

    public void setIntValue(String name, int... values) {
        int location = GL20.glGetUniformLocation(this.program, name);
        switch (values.length) {
            case 1: {
                GL20.glUniform1i(location, values[0]);
                break;
            }
            case 2: {
                GL20.glUniform2i(location, values[0], values[1]);
                break;
            }
            case 3: {
                GL20.glUniform3i(location, values[0], values[1], values[2]);
                break;
            }
            case 4: {
                GL20.glUniform4i(location, values[0], values[1], values[2], values[3]);
                break;
            }
        }
    }

    public boolean contains(String[] array, String name) {
        if (array == null) {
            return false;
        }

        for (String str : array) {
            if (str.equals(name)) {
                return true;
            }
        }

        return false;
    }

}