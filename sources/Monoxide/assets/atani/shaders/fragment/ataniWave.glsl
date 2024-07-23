/*
 * Original shader from: https://www.shadertoy.com/view/ddKSDd
 */

#ifdef GL_ES
precision lowp float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //
// Simple hash function
float hash(float n) {
    return fract(sin(n) * 43758.5453);
}

// 2D noise function
float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(mix(hash(i.x + hash(i.y)), hash(i.x + 1.0 + hash(i.y)), u.x),
    mix(hash(i.x + hash(i.y + 1.0)), hash(i.x + 1.0 + hash(i.y + 1.0)), u.x), u.y);
}

// Aurora layer function
vec3 auroraLayer(vec2 uv, float speed, float intensity, vec3 color) {
    float t = iTime * speed;
    vec2 scaleXY = vec2(2.0, 2.0);
    vec2 movement = vec2(2.0, -2.0);
    vec2 p = uv * scaleXY + t * movement;
    float n = noise(p + noise(color.xy + p + t));
    float aurora = smoothstep(0.0, 0.2, n - uv.y) * (1.0 - smoothstep(0.0, 0.6, n - uv.y));

    return aurora * intensity * color;
}


// Main image function
void mainImage(out vec4 fragColor, in vec2 fragCoord) {
    vec2 uv = fragCoord / iResolution.xy;
    uv.x *= iResolution.x / iResolution.y;

    // Create multiple aurora layers with varying colors, speeds, and intensities
    vec3 color = vec3(0.0);

    color += auroraLayer(uv, 0.05, 0.3, vec3(0.5, 0.2, 0.3));
    color += auroraLayer(uv, 0.1, 0.4, vec3(0.1, 0.1, 0.8));
    color += auroraLayer(uv, 0.15, 0.3, vec3(0.1, 0.8, 0.8));
    color += auroraLayer(uv, 0.07, 0.2, vec3(0.8, 0.2, 0.8));

    vec3 skyColor1 = vec3(0.2, 0.0, 0.4);
    vec3 skyColor2 = vec3(0.15, 0.2, 0.35);
    // Add a gradient to simulate the night sky
    color += skyColor2 * (1.0 - smoothstep(0.0, 2.0, uv.y));
    color += skyColor1 * (1.0 - smoothstep(0.0, 1.0, uv.y));

    fragColor = vec4(color, 1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}