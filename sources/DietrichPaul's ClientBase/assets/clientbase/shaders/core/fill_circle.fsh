#version 150

in vec4 vertexColor;
in vec2 vertexPos;

uniform float Radius;
uniform vec2 Center;
uniform vec4 ColorModulator;

out vec4 fragColor;

/*
(x - a)² + (y - b)² <= r² | sqrt
sqrt((x - a)² + (y - b)²) <= r | -r
sqrt((x - a)² + (y - b)²) - r <= 0
distance(xy, ab) - r <= 0
*/
void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }

    float edge = clamp(distance(vertexPos, Center) - Radius, 0.0, 1.0);
    fragColor = color * ColorModulator * vec4(1.0, 1.0, 1.0, 1.0 - edge);
}
