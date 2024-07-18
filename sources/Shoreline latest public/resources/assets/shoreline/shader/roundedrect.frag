#version 120

uniform float radius;
uniform float softness;
uniform vec2 size;
uniform vec4 color;

float roundedBoxSDF(vec2 cpos, vec2 size, float r)
{
    return length(max(abs(cpos) - size + r, 0.0)) - r;
}

void main()
{
    vec2 half = size * 0.5;
    float distance = roundedBoxSDF(half - (gl_TexCoord[0].st * size),
                                   half, radius);
    float a = 1.0 - smoothstep(0.0, softness * 2.0, distance);
    gl_FragColor = vec4(color.rgb, color.a * a);
}