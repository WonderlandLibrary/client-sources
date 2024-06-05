#version 150

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform vec4 ColorModulator;
uniform float Antialias;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 shape = texture(Sampler0, texCoord0);
    if (shape.a == 0.0) {
        discard;
    }

    vec4 blurred = texture(Sampler1, texCoord0);
    if (Antialias == 0 || shape.a == 1.0) {
        fragColor = blurred;
        return;
    }
    blurred.a = shape.a / Antialias;
    fragColor = blurred;
}
