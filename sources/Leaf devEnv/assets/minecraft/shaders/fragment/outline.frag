#version 120

uniform sampler2D texture;
uniform vec2 texelSize;
uniform float radius;
uniform bool filled;
uniform float opacity;

void main() {
    float closest = radius * radius ;
    vec4 color = vec4(0, 0, 0, 0);

    vec4 current = texture2D(texture, gl_TexCoord[0].st);
    if (current.a == 0.0F) {
        for (float x = -radius; x <= radius; x += radius) {
            for (float y = -radius; y <= radius; y += radius) {
                vec4 currCol = texture2D(texture, gl_TexCoord[0].st + vec2(x * texelSize.x, y * texelSize.y));
                if (currCol.a != 0.0F) {
                    float dist = (x * x + y * y);
                    if (closest >= dist) {
                        color = currCol;
                    }
                }
            }
        }
    } else if (filled) {
        color = vec4(current.r, current.g, current.b, opacity);
    }

    gl_FragColor = color;
}
