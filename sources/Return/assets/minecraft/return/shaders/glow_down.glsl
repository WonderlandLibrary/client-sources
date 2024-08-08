#version 120

uniform sampler2D textureIn;
uniform vec2 halfPixel, offset, resolution;

void main() {
    vec2 loc = vec2(gl_FragCoord.xy / resolution);
    vec4 col = texture2D(textureIn, gl_TexCoord[0].st);

    col.rgb *= col.a;
    col *= 4.0;

    vec4 tex1 = texture2D(textureIn, loc - halfPixel.xy * offset);
    tex1.rgb *= tex1.a;
    col += tex1;

    vec4 tex2 = texture2D(textureIn, loc + halfPixel.xy * offset);
    tex2.rgb *= tex2.a;
    col += tex2;

    vec4 tex3 = texture2D(textureIn, loc + vec2(halfPixel.x, -halfPixel.y) * offset);
    tex3.rgb *= tex3.a;
    col += tex3;

    vec4 tex4 = texture2D(textureIn, loc - vec2(halfPixel.x, -halfPixel.y) * offset);
    tex4.rgb *= tex4.a;
    col += tex4;

    vec4 result = col / 8.0;
    gl_FragColor = vec4(result.rgb / result.a, result.a);
}