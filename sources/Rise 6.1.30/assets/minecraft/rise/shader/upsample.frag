#version 330 core

out vec4 fragColor;

uniform sampler2D mainTexture, secondaryTexture;
uniform vec2 textureOffset, pixelStep, screenResolution;
uniform int mixture;

#define pixel (pixelStep * textureOffset)

void main() {

    vec2 position = vec2(gl_FragCoord.xy / screenResolution);

    vec4 value = texture2D(mainTexture, position + vec2(-pixel.x * 2.0, 0.0));
    value += texture2D(mainTexture, position + vec2(-pixel.x, pixel.y) * textureOffset) * 2.0;
    value += texture2D(mainTexture, position + vec2(0.0, pixel.y * 2.0));
    value += texture2D(mainTexture, position + vec2(pixel.x, pixel.y) * textureOffset) * 2.0;
    value += texture2D(mainTexture, position + vec2(pixel.x * 2.0, 0.0));
    value += texture2D(mainTexture, position + vec2(pixel.x, -pixel.y) * textureOffset) * 2.0;
    value += texture2D(mainTexture, position + vec2(0.0, -pixel.y * 2.0));
    value += texture2D(mainTexture, position + vec2(-pixel.x, -pixel.y)) * 2.0;

    value /= 12.0;

    float alpha = texture2D(secondaryTexture, position).a;

    fragColor = vec4(value.rgb, mix(1.0, alpha, mixture));
}