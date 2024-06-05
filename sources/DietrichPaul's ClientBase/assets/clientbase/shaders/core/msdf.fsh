#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float pxRange;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}

float screenPxRange() {
    vec2 unitRange = vec2(pxRange)/vec2(textureSize(Sampler0, 0));
    vec2 screenTexSize = vec2(1.0)/fwidth(texCoord0);
    return max(0.5*dot(unitRange, screenTexSize), 1.0);
}

void main() {
    vec4 msd = texture(Sampler0, texCoord0);
    float sd = median(msd.r, msd.g, msd.b);
    float screenPxDistance = screenPxRange() * (sd - 0.5);
    float opacity = clamp(screenPxDistance + 0.5, 0.0, 1.0);
    fragColor = vertexColor * vec4(1.0, 1.0, 1.0, opacity) * ColorModulator;
}
