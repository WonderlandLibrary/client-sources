#version 120

uniform sampler2D textureIn, textureToCheck;
uniform vec2 texelSize, direction;
uniform vec3 color;
uniform float exposure, radius;
uniform float weights[256];

#define offset direction * texelSize

void main() {

    float innerAlpha = texture2D(textureIn, gl_TexCoord[0].st).a * weights[0];

    for (float r = 1.0; r <= radius; r ++) {
        innerAlpha += texture2D(textureIn, gl_TexCoord[0].st + offset * r).a * weights[int(r)];
        innerAlpha += texture2D(textureIn, gl_TexCoord[0].st - offset * r).a * weights[int(r)];
    }

    gl_FragColor = vec4(color, mix(innerAlpha, 1.0 - exp(-innerAlpha * exposure), step(0.0, direction.y)));
}