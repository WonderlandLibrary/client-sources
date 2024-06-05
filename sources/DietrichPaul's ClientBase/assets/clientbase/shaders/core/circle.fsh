#version 150

in vec4 vertexColor;
in vec2 vertexPos;

uniform float LineWidth;
uniform float Radius;
uniform vec2 Center;
uniform vec4 ColorModulator;

out vec4 fragColor;

/*
selbe idee wie bei fill_circle
aber begrenz durch min/max radius als faktor
*/
void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }

    float minRadius = Radius - LineWidth / 2.0;
    float maxRadius = Radius + LineWidth / 2.0;
    float innen = clamp(distance(vertexPos, Center) - minRadius, 0.0, 1.0); // im kreis < 0, außer kreis > 0
    float aussen = 1.0 - clamp(distance(vertexPos, Center) - maxRadius, 0.0, 1.0); // im kreis > 0, außer kreis < 0
    float edge = innen * aussen; // unter minRadius < 0, über maxRadius < 0, zwischen > 0
    fragColor = color * ColorModulator * vec4(1.0, 1.0, 1.0, edge);
}
