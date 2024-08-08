#version 120

uniform sampler2D textureIn;
uniform vec2 halfPixel, offset, multiplier, resolution;

void main() {
    vec2 loc = vec2(gl_FragCoord.xy / resolution);
    vec4 col = texture2D(textureIn, loc + vec2(-halfPixel.x * 2.0, 0.0) * offset);

    col.rgb *= col.a;

    vec4 tex1 =  texture2D(textureIn, loc + vec2(-halfPixel.x, halfPixel.y) * offset);
    tex1.rgb *= tex1.a;
    col += tex1 * 2.0;

    vec4 tex2 = texture2D(textureIn, loc + vec2(0.0, halfPixel.y * 2.0) * offset);
    tex2.rgb *= tex2.a;
    col += tex2;

    vec4 tex3 = texture2D(textureIn, loc + vec2(halfPixel.x, halfPixel.y) * offset);
    tex3.rgb *= tex3.a;
    col += tex3 * 2.0;

    vec4 tex4 = texture2D(textureIn, loc + vec2(halfPixel.x * 2.0, 0.0) * offset);
    tex4.rgb *= tex4.a;
    col += tex4;

    vec4 tex5 = texture2D(textureIn, loc + vec2(halfPixel.x, -halfPixel.y) * offset);
    tex5.rgb *= tex5.a;
    col += tex5 * 2.0;

    vec4 tex6 = texture2D(textureIn, loc + vec2(0.0, -halfPixel.y * 2.0) * offset);
    tex6.rgb *= tex6.a;
    col += tex6;

    vec4 tex7 = texture2D(textureIn, loc + vec2(-halfPixel.x, -halfPixel.y) * offset);
    tex7.rgb *= tex7.a;
    col += tex7 * 2.0;

    vec4 result = col / 12.0;
    gl_FragColor = vec4(result.rgb / result.a, clamp(result.a * multiplier, 0.0, 1.0));
}