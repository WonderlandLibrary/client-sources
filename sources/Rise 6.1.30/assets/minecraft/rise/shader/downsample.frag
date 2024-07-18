#version 330 core

out vec4 fragColor;

uniform sampler2D mainTexture;
uniform vec2 textureOffset, pixelStep, screenResolution;

#define halfPixel (pixelStep * textureOffset)

void main() {
    vec2 oTexCoord = gl_FragCoord.xy / screenResolution;
    vec4 color = texture2D(mainTexture, oTexCoord) * 4.0;

    color += texture2D(mainTexture, oTexCoord + halfPixel * textureOffset);
    color += texture2D(mainTexture, oTexCoord - halfPixel * textureOffset);
    color += texture2D(mainTexture, oTexCoord + vec2(halfPixel.x, -halfPixel.y) * textureOffset);
    color += texture2D(mainTexture, oTexCoord - vec2(halfPixel.x, -halfPixel.y) * textureOffset);

    color /= 8.0;

    fragColor = color;
}