package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

public class RoundedOutGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120
                // объявление переменных
                uniform vec2 size; // размер прямоугольника
                uniform vec4 round; // коэффициенты скругления углов
                uniform vec2 smoothness; // плавность перехода от цвета к прозрачности
                uniform float value; // значение, используемое для расчета расстояния до границы
                uniform vec4 color; // цвет прямоугольника
                uniform vec4 outlineColor; // цвет обводки
                uniform vec4 outlineColor1; // цвет обводки
                uniform vec4 outlineColor2; // цвет обводки
                uniform vec4 outlineColor3; // цвет обводки
                uniform float outline; // цвет обводки
                #define NOISE .5/255.0
                // функция для расчета расстояния до границы
                float test(vec2 vec_1, vec2 vec_2, vec4 vec_4) {
                    vec_4.xy = (vec_1.x > 0.0) ? vec_4.xy : vec_4.zw;
                    vec_4.x = (vec_1.y > 0.0) ? vec_4.x : vec_4.y;
                    vec2 coords = abs(vec_1) - vec_2 + vec_4.x;
                    return min(max(coords.x, coords.y), 0.0) + length(max(coords, vec2(0.0f))) - vec_4.x;
                }

                vec4 createGradient(vec2 coords, vec4 color1, vec4 color2, vec4 color3, vec4 color4) {
                    vec4 color = mix(mix(color1, color2, coords.y), mix(color3, color4, coords.y), coords.x);
                    //Dithering the color
                    // from https://shader-tutorial.dev/advanced/color-banding-dithering/
                    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));
                    return color;
                }

                void main() {
                    vec2 st = gl_TexCoord[0].st * size; // координаты текущего пикселя
                    vec2 halfSize = 0.5 * size; // половина размера прямоугольника
                    float sa = 1.0 - smoothstep(smoothness.x, smoothness.y, test(halfSize - st, halfSize - value, round));
                    float outline = 1.0 - smoothstep(smoothness.x, smoothness.y, test(halfSize - st, halfSize - value - outline, round));
                    float outlin1 = 1.0 - smoothstep(smoothness.x, smoothness.y, test(halfSize - st, halfSize - value - 1, round));

                    // рассчитываем прозрачность в зависимости от расстояния до границы
                    vec4 finalColor = mix(vec4(color.rgb, 0.0), vec4(color.rgb, color.a), sa); // устанавливаем цвет прямоугольника с прозрачностью sa

                    // если sa и outline равны, то это обводка
                    if (sa > outline) {
                        vec4 color = createGradient(gl_TexCoord[0].st, outlineColor, outlineColor1,outlineColor2,outlineColor3);
                        finalColor = vec4(color.r,color.g,color.b,outlin1); // присваиваем обводке ее цвет
                    }

                    gl_FragColor = finalColor;
                }
                       """;
    }

}
