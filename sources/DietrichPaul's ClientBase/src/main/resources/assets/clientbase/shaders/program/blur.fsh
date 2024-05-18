#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

out vec4 fragColor;

void main() {
    vec3 colorSum = vec3(0.0);
    float sum = 0.0;
    for (float r = -Radius; r <= Radius; r += 1.0) {
        float weight = 1.0 - abs(r / Radius);
        colorSum += texture(DiffuseSampler, texCoord + BlurDir * oneTexel * r).rgb * weight;
        sum += weight;
    }
    fragColor = vec4(colorSum / sum, 1.0);
}
