#version 110

uniform float radius;
varying vec2 pos;
uniform vec2 cpos;

void main()
{
    float len = length(pos - cpos);
    float a = 1.0 - smoothstep(radius - 1.0, radius, len);
    gl_FragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);
}