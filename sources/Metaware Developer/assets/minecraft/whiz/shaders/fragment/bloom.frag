#version 120

uniform sampler2D originalTexture;
uniform float shadowAlpha;
uniform vec2 texelSize, direction;
uniform const float radius = 10;

float gauss(float x, float sigma) {
    return .4 * exp(-.5 * x * x / (sigma * sigma)) / sigma;
}

void main() {
    //    if (direction.y == 1)
    //        if(texture2D(checkedTexture, gl_TexCoord[0].st).a != 0.0) return;

    float alpha = 0.0;
    for(float i = -radius; i <= radius; i++) {
        alpha += texture2D(originalTexture, gl_TexCoord[0].st + i * texelSize * direction).a * gauss(i, (radius * shadowAlpha) / 2);
    }
    gl_FragColor = vec4(0.0,0.0,0.0, alpha);
}