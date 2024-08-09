package dev.luvbeeq.shader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractShader {
    private static final long START_TIME = System.currentTimeMillis();

    protected ShaderInstance shaderInstance;
    protected ShaderDefault timeUniform;

    public void initialize() {
        Minecraft.getInstance().deferTask(this::reloadShaders);

    }

    public void bind() {
        if (shaderInstance != null) {
            timeUniform.set((System.currentTimeMillis() - START_TIME) / 1000.0f);
            shaderInstance.apply();
        }
    }

    public void unbind() {
        if (shaderInstance != null) {
            shaderInstance.clear();
        }
    }

    private void reloadShaders() {
        if (shaderInstance != null) {
            shaderInstance.close();
            shaderInstance = null;
        }

        try {
            shaderInstance = new ShaderInstance(getShaderName(), shader().getJsonSet(), shader().getFragmentShader(), shader().getVertexShader());
            handleShaderLoad();
        } catch (final IOException ignored) {
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Shader {
        DEPTH_SHADER(new ByteArrayInputStream(depth_json.getBytes()), new ByteArrayInputStream(depth_fragment.getBytes()), new ByteArrayInputStream(depth_vertex.getBytes()));

        private final InputStream jsonSet;
        private final InputStream fragmentShader;
        private final InputStream vertexShader;
    }

    public abstract String getShaderName();

    public abstract Shader shader();

    public void handleShaderLoad() {
        timeUniform = shaderInstance.safeGetUniform("time");
    }

    private static final String depth_json = """
            {
              "blend": {
                "func": "add",
                "srcrgb": "one",
                "dstrgb": "one"
              },
              "vertex": "exc:depth_shader",
              "fragment": "exc:depth_shader",
              "samplers": [
                { "name": "depthTex" },
                { "name": "blur" },
                 { "name": "minecraft" }
              ],
              "uniforms": [
                { "name": "near",     "type": "float",     "count": 1,  "values": [ 1.0 ] },
                { "name": "distance",     "type": "float",     "count": 1,  "values": [ 1.0 ] },
                { "name": "resolution",     "type": "float",     "count": 2,  "values": [ 1.0 ] },
                { "name": "color1",     "type": "float",     "count": 4,  "values": [ 1.0, 1.0, 1.0, 1.0 ] },
                { "name": "color2",     "type": "float",     "count": 4,  "values": [ 1.0, 1.0, 1.0, 1.0 ] },
                { "name": "color3",     "type": "float",     "count": 4,  "values": [ 1.0, 1.0, 1.0, 1.0 ] },
                { "name": "color4",     "type": "float",     "count": 4,  "values": [ 1.0, 1.0, 1.0, 1.0 ] },
                { "name": "clientColor",     "type": "float",     "count": 1,  "values": [ 1.0 ] },
                { "name": "saturation",     "type": "float",     "count": 1,  "values": [ 1.0 ] },
                { "name": "far",     "type": "float",     "count": 1,  "values": [ 1.0 ] }
              ]
            }""";
    private static final String depth_fragment = """
            #version 130
                        
            #define clipping far
            uniform sampler2D depthTex, minecraft, blur;
            uniform float near;
            uniform float distance;
            uniform float far;
            uniform float clientColor;
            uniform float saturation;
            uniform vec2 resolution;
            uniform vec4 color1, color2, color3, color4;
              
            varying vec2 texCoord;
              
            float getDepth(vec2 coord) {
                return 2.0 * near * far / (far + near - (2.0 * texture2D(depthTex, coord).x - 1.0) * (far - near)) / clipping;
            }
              
            #define NOISE .5/255.0
                                                
            vec3 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){
                vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);
                color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));
                return color;
            }
                        
            void main() {
                float linearDepth = getDepth(texCoord);
               
                float smoothness = smoothstep(distance, distance + 0.1, linearDepth);
                vec3 minecraftColor = texture2D(minecraft, texCoord).rgb;
                vec3 blurColor = texture2D(blur, texCoord).rgb;
                vec3 finalColor = vec3(1.0, 1.0, 1.0);
                if (clientColor == 1.0){
                    finalColor = mix(minecraftColor, mix(createGradient(texCoord, color1, color2, color3, color4).rgb,blurColor,saturation), smoothness);
                } else {
                    finalColor = mix(minecraftColor, blurColor, smoothness);
                }
                if (linearDepth > distance) {
                    gl_FragColor = vec4(finalColor, 1);
                } else {
                    gl_FragColor = vec4(texture2D(minecraft, texCoord).rgb, 1);
                }
            }""";
    private static final String depth_vertex = """
            #version 130
                        
            varying vec2 texCoord;
                        
            void main() {
                texCoord = gl_MultiTexCoord0.st;
                gl_Position = ftransform();
            }""";
}
