package vestige.shaders;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import vestige.util.IMinecraft;

public class ShaderUtil {
   private final int programID;
   private final String kawaseUpGlow;
   private final String glowShader;
   private final String chams;
   private final String roundRectTexture;
   private final String roundRectOutline;
   private final String kawaseUpBloom;
   private final String kawaseDownBloom;
   private final String kawaseUp;
   private final String kawaseDown;
   private final String gradientMask;
   private final String mask;
   private final String gradient;
   private final String roundedRectGradient;
   private final String roundedRect;

   public ShaderUtil(String fragmentShaderLoc, String vertexShaderLoc) {
      this.kawaseUpGlow = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform bool check;\nuniform float lastPass;\nuniform float exposure;\n\nvoid main() {\n    if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, 1.0 - exp(-result.a * exposure), step(0.0, lastPass)));\n}";
      this.glowShader = "#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform vec3 color;\nuniform bool avoidTexture;\nuniform float exposure, radius;\nuniform float weights[256];\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y == 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerColor = texture2D(textureIn, gl_TexCoord[0].st);\n    innerColor.rgb *= innerColor.a;\n    innerColor *= weights[0];\n    for (float r = 1.0; r <= radius; r++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerColor += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n    }\n\n    gl_FragColor = vec4(innerColor.rgb / innerColor.a, mix(innerColor.a, 1.0 - exp(-innerColor.a * exposure), step(0.0, direction.y)));\n}\n";
      this.chams = "#version 120\n\nuniform sampler2D textureIn;\nuniform vec4 color;\nvoid main() {\n    float alpha = texture2D(textureIn, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(color.rgb, color.a * mix(0.0, alpha, step(0.0, alpha)));\n}\n";
      this.roundRectTexture = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}";
      this.roundRectOutline = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n\n}";
      this.kawaseUpBloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n}";
      this.kawaseDownBloom = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);\n    sum.rgb *= sum.a;\n    sum *= 4.0;\n    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);\n    smp1.rgb *= smp1.a;\n    sum += smp1;\n    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3;\n    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 result = sum / 8.0;\n    gl_FragColor = vec4(result.rgb / result.a, result.a);\n}";
      this.kawaseUp = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;\n\n    gl_FragColor = vec4(sum.rgb /12.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));\n}\n";
      this.kawaseDown = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;\n    sum += texture2D(inTexture, uv - halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    gl_FragColor = vec4(sum.rgb * .125, 1.0);\n}\n";
      this.gradientMask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec3 color1, color2, color3, color4;\nuniform float alpha;\n\n#define NOISE .5/255.0\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4), texColorAlpha * alpha);\n}";
      this.mask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D u_texture, u_texture2;\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(u_texture, gl_TexCoord[0].st).a;\n    vec3 tex2Color = texture2D(u_texture2, gl_TexCoord[0].st).rgb;\n    gl_FragColor = vec4(tex2Color, texColorAlpha);\n}";
      this.gradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec4 color1, color2, color3, color4;\n#define NOISE .5/255.0\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    gl_FragColor = createGradient(coords, color1, color2, color3, color4);\n}";
      this.roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n   // use the bottom leftColor as the alpha\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}";
      this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
      int program = GL20.glCreateProgram();

      int fragmentShaderID;
      try {
         byte var6 = -1;
         switch(fragmentShaderLoc.hashCode()) {
         case -1432945623:
            if (fragmentShaderLoc.equals("roundRectTexture")) {
               var6 = 3;
            }
            break;
         case -1121374384:
            if (fragmentShaderLoc.equals("roundRectOutline")) {
               var6 = 4;
            }
            break;
         case -493757311:
            if (fragmentShaderLoc.equals("roundedRectGradient")) {
               var6 = 13;
            }
            break;
         case -200552017:
            if (fragmentShaderLoc.equals("kawaseDownBloom")) {
               var6 = 6;
            }
            break;
         case -72859087:
            if (fragmentShaderLoc.equals("roundedRect")) {
               var6 = 12;
            }
            break;
         case 3175821:
            if (fragmentShaderLoc.equals("glow")) {
               var6 = 1;
            }
            break;
         case 3344108:
            if (fragmentShaderLoc.equals("mask")) {
               var6 = 10;
            }
            break;
         case 89650992:
            if (fragmentShaderLoc.equals("gradient")) {
               var6 = 11;
            }
            break;
         case 94623554:
            if (fragmentShaderLoc.equals("chams")) {
               var6 = 2;
            }
            break;
         case 491608636:
            if (fragmentShaderLoc.equals("gradientMask")) {
               var6 = 9;
            }
            break;
         case 1190632237:
            if (fragmentShaderLoc.equals("kawaseUp")) {
               var6 = 7;
            }
            break;
         case 1261448918:
            if (fragmentShaderLoc.equals("kawaseUpBloom")) {
               var6 = 5;
            }
            break;
         case 1735775412:
            if (fragmentShaderLoc.equals("kawaseDown")) {
               var6 = 8;
            }
            break;
         case 2119050842:
            if (fragmentShaderLoc.equals("kawaseUpGlow")) {
               var6 = 0;
            }
         }

         switch(var6) {
         case 0:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform bool check;\nuniform float lastPass;\nuniform float exposure;\n\nvoid main() {\n    if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, 1.0 - exp(-result.a * exposure), step(0.0, lastPass)));\n}".getBytes()), 35632);
            break;
         case 1:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform vec3 color;\nuniform bool avoidTexture;\nuniform float exposure, radius;\nuniform float weights[256];\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y == 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerColor = texture2D(textureIn, gl_TexCoord[0].st);\n    innerColor.rgb *= innerColor.a;\n    innerColor *= weights[0];\n    for (float r = 1.0; r <= radius; r++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerColor += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n    }\n\n    gl_FragColor = vec4(innerColor.rgb / innerColor.a, mix(innerColor.a, 1.0 - exp(-innerColor.a * exposure), step(0.0, direction.y)));\n}\n".getBytes()), 35632);
            break;
         case 2:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D textureIn;\nuniform vec4 color;\nvoid main() {\n    float alpha = texture2D(textureIn, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(color.rgb, color.a * mix(0.0, alpha, step(0.0, alpha)));\n}\n".getBytes()), 35632);
            break;
         case 3:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}".getBytes()), 35632);
            break;
         case 4:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n\n}".getBytes()), 35632);
            break;
         case 5:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n}".getBytes()), 35632);
            break;
         case 6:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);\n    sum.rgb *= sum.a;\n    sum *= 4.0;\n    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);\n    smp1.rgb *= smp1.a;\n    sum += smp1;\n    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3;\n    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 result = sum / 8.0;\n    gl_FragColor = vec4(result.rgb / result.a, result.a);\n}".getBytes()), 35632);
            break;
         case 7:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;\n\n    gl_FragColor = vec4(sum.rgb /12.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));\n}\n".getBytes()), 35632);
            break;
         case 8:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;\n    sum += texture2D(inTexture, uv - halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    gl_FragColor = vec4(sum.rgb * .125, 1.0);\n}\n".getBytes()), 35632);
            break;
         case 9:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec3 color1, color2, color3, color4;\nuniform float alpha;\n\n#define NOISE .5/255.0\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4), texColorAlpha * alpha);\n}".getBytes()), 35632);
            break;
         case 10:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D u_texture, u_texture2;\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(u_texture, gl_TexCoord[0].st).a;\n    vec3 tex2Color = texture2D(u_texture2, gl_TexCoord[0].st).rgb;\n    gl_FragColor = vec4(tex2Color, texColorAlpha);\n}".getBytes()), 35632);
            break;
         case 11:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec4 color1, color2, color3, color4;\n#define NOISE .5/255.0\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    gl_FragColor = createGradient(coords, color1, color2, color3, color4);\n}".getBytes()), 35632);
            break;
         case 12:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}".getBytes()), 35632);
            break;
         case 13:
            fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n   // use the bottom leftColor as the alpha\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}".getBytes()), 35632);
            break;
         default:
            fragmentShaderID = this.createShader(IMinecraft.mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 35632);
         }

         GL20.glAttachShader(program, fragmentShaderID);
         int vertexShaderID = this.createShader(IMinecraft.mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 35633);
         GL20.glAttachShader(program, vertexShaderID);
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      GL20.glLinkProgram(program);
      fragmentShaderID = GL20.glGetProgrami(program, 35714);
      if (fragmentShaderID == 0) {
         throw new IllegalStateException("Shader failed to link!");
      } else {
         this.programID = program;
      }
   }

   public ShaderUtil(String fragmentShadersrc, boolean notUsed) {
      this.kawaseUpGlow = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform bool check;\nuniform float lastPass;\nuniform float exposure;\n\nvoid main() {\n    if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, 1.0 - exp(-result.a * exposure), step(0.0, lastPass)));\n}";
      this.glowShader = "#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform vec3 color;\nuniform bool avoidTexture;\nuniform float exposure, radius;\nuniform float weights[256];\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y == 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerColor = texture2D(textureIn, gl_TexCoord[0].st);\n    innerColor.rgb *= innerColor.a;\n    innerColor *= weights[0];\n    for (float r = 1.0; r <= radius; r++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerColor += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n    }\n\n    gl_FragColor = vec4(innerColor.rgb / innerColor.a, mix(innerColor.a, 1.0 - exp(-innerColor.a * exposure), step(0.0, direction.y)));\n}\n";
      this.chams = "#version 120\n\nuniform sampler2D textureIn;\nuniform vec4 color;\nvoid main() {\n    float alpha = texture2D(textureIn, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(color.rgb, color.a * mix(0.0, alpha, step(0.0, alpha)));\n}\n";
      this.roundRectTexture = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}";
      this.roundRectOutline = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n\n}";
      this.kawaseUpBloom = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum.rgb *= sum.a;\n    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n    smpl1.rgb *= smpl1.a;\n    sum += smpl1 * 2.0;\n    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3 * 2.0;\n    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp5.rgb *= smp5.a;\n    sum += smp5 * 2.0;\n    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    smp6.rgb *= smp6.a;\n    sum += smp6;\n    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n    smp7.rgb *= smp7.a;\n    sum += smp7 * 2.0;\n    vec4 result = sum / 12.0;\n    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n}";
      this.kawaseDownBloom = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st);\n    sum.rgb *= sum.a;\n    sum *= 4.0;\n    vec4 smp1 = texture2D(inTexture, uv - halfpixel.xy * offset);\n    smp1.rgb *= smp1.a;\n    sum += smp1;\n    vec4 smp2 = texture2D(inTexture, uv + halfpixel.xy * offset);\n    smp2.rgb *= smp2.a;\n    sum += smp2;\n    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp3.rgb *= smp3.a;\n    sum += smp3;\n    vec4 smp4 = texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    smp4.rgb *= smp4.a;\n    sum += smp4;\n    vec4 result = sum / 8.0;\n    gl_FragColor = vec4(result.rgb / result.a, result.a);\n}";
      this.kawaseUp = "#version 120\n\nuniform sampler2D inTexture, textureToCheck;\nuniform vec2 halfpixel, offset, iResolution;\nuniform int check;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset) * 2.0;\n    sum += texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n    sum += texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset) * 2.0;\n\n    gl_FragColor = vec4(sum.rgb /12.0, mix(1.0, texture2D(textureToCheck, gl_TexCoord[0].st).a, check));\n}\n";
      this.kawaseDown = "#version 120\n\nuniform sampler2D inTexture;\nuniform vec2 offset, halfpixel, iResolution;\n\nvoid main() {\n    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n    vec4 sum = texture2D(inTexture, gl_TexCoord[0].st) * 4.0;\n    sum += texture2D(inTexture, uv - halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + halfpixel.xy * offset);\n    sum += texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n    sum += texture2D(inTexture, uv - vec2(halfpixel.x, -halfpixel.y) * offset);\n    gl_FragColor = vec4(sum.rgb * .125, 1.0);\n}\n";
      this.gradientMask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec3 color1, color2, color3, color4;\nuniform float alpha;\n\n#define NOISE .5/255.0\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    //Dithering the color from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898,78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(tex, gl_TexCoord[0].st).a;\n    gl_FragColor = vec4(createGradient(coords, color1, color2, color3, color4), texColorAlpha * alpha);\n}";
      this.mask = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D u_texture, u_texture2;\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    float texColorAlpha = texture2D(u_texture, gl_TexCoord[0].st).a;\n    vec3 tex2Color = texture2D(u_texture2, gl_TexCoord[0].st).rgb;\n    gl_FragColor = vec4(tex2Color, texColorAlpha);\n}";
      this.gradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D tex;\nuniform vec4 color1, color2, color3, color4;\n#define NOISE .5/255.0\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 coords = (gl_FragCoord.xy - location) / rectSize;\n    gl_FragColor = createGradient(coords, color1, color2, color3, color4);\n}";
      this.roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4){\n    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);\n    //Dithering the color\n    // from https://shader-tutorial.dev/advanced/color-banding-dithering/\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    \n   // use the bottom leftColor as the alpha\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius)));\n    vec4 gradient = createGradient(st, color1, color2, color3, color4);    gl_FragColor = vec4(gradient.rgb, gradient.a * smoothedAlpha);\n}";
      this.roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\nuniform bool blur;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    // Smooth the result (free antialiasing).\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n\n}";
      int program = GL20.glCreateProgram();
      int fragmentShaderID = this.createShader(new ByteArrayInputStream(fragmentShadersrc.getBytes()), 35632);
      boolean var5 = false;

      int vertexShaderID;
      try {
         vertexShaderID = this.createShader(IMinecraft.mc.getResourceManager().getResource(new ResourceLocation("Maple/Shaders/vertex.vsh")).getInputStream(), 35633);
      } catch (IOException var7) {
         throw new RuntimeException(var7);
      }

      GL20.glAttachShader(program, fragmentShaderID);
      GL20.glAttachShader(program, vertexShaderID);
      GL20.glLinkProgram(program);
      int status = GL20.glGetProgrami(program, 35714);
      if (status == 0) {
         throw new IllegalStateException("Shader failed to link!");
      } else {
         this.programID = program;
      }
   }

   public ShaderUtil(String fragmentShaderLoc) {
      this(fragmentShaderLoc, "flap/shaders/vertex.vsh");
   }

   public void init() {
      GL20.glUseProgram(this.programID);
   }

   public void unload() {
      GL20.glUseProgram(0);
   }

   public int getUniform(String name) {
      return GL20.glGetUniformLocation(this.programID, name);
   }

   public void setUniformf(String name, float... args) {
      int loc = GL20.glGetUniformLocation(this.programID, name);
      switch(args.length) {
      case 1:
         GL20.glUniform1f(loc, args[0]);
         break;
      case 2:
         GL20.glUniform2f(loc, args[0], args[1]);
         break;
      case 3:
         GL20.glUniform3f(loc, args[0], args[1], args[2]);
         break;
      case 4:
         GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
      }

   }

   public void setUniformi(String name, int... args) {
      int loc = GL20.glGetUniformLocation(this.programID, name);
      if (args.length > 1) {
         GL20.glUniform2i(loc, args[0], args[1]);
      } else {
         GL20.glUniform1i(loc, args[0]);
      }

   }

   public static void drawQuads(float x, float y, float width, float height) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(x + width, y + height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(x + width, y);
      GL11.glEnd();
   }

   public static void drawQuads() {
      ScaledResolution sr = new ScaledResolution(IMinecraft.mc);
      float width = (float)sr.getScaledWidth_double();
      float height = (float)sr.getScaledHeight_double();
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(width, height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(width, 0.0F);
      GL11.glEnd();
   }

   public static void drawQuads(float width, float height) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(width, height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(width, 0.0F);
      GL11.glEnd();
   }

   private int createShader(InputStream inputStream, int shaderType) {
      int shader = GL20.glCreateShader(shaderType);
      GL20.glShaderSource(shader, this.readInputStream(inputStream));
      GL20.glCompileShader(shader);
      if (GL20.glGetShaderi(shader, 35713) == 0) {
         System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
         throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
      } else {
         return shader;
      }
   }

   @NotNull
   private String readInputStream(InputStream inputStream) {
      StringBuilder stringBuilder = new StringBuilder();

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

         String line;
         while((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      String var10000 = stringBuilder.toString();
      if (var10000 == null) {
         $$$reportNull$$$0(0);
      }

      return var10000;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "vestige/shaders/ShaderUtil", "readInputStream"));
   }
}
