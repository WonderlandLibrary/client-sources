#version 330 core

uniform sampler2D textureSampler;
uniform float screenWidth;

in vec2 TexCoords;
out vec4 FragColor;

const float offsets[5] = float[](0.0, 1.0, -1.0, 2.0, -2.0);

void main() {
    vec2 texelSize = vec2(1.0 / screenWidth, 0.0);
    vec4 sum = vec4(0.0);
    
    for (int i = 0; i < 5; ++i) {
        sum += texture(textureSampler, TexCoords + vec2(offsets[i] * texelSize.x, 0.0));
    }

    FragColor = sum / 5.0;
}