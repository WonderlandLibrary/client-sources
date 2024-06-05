#version 150

uniform vec2 resolution;

in vec2 texCoord;
out vec4 fragColor;

void main()
{
    vec2 xy = texCoord.xy / resolution.xy;
    fragColor = vec4(2.0, xy.x, 0.4, 1.0);
}