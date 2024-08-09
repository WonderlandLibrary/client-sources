#version 120

uniform vec2 size;
uniform vec4 color1, color2, color3, color4;
uniform vec2 smoothness;
uniform float value;
uniform vec4 round;

#define NOISE .5/255.0

float dstfn(vec2 p, vec2 b, vec4 r) {
    r.xy = (p.x>0.0)?r.xy : r.zw;
    r.x  = (p.y>0.0)?r.x  : r.y;

    vec2 l = abs(p) - b + r.x;
    return min(max(l.x, l.y), 0.0) + length(max(l, .0f)) - r.x;
}

vec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4){
    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);
    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));
    return color;
}

void main() {
    vec2 halfSize = size * .5;

    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., dstfn(halfSize - (gl_TexCoord[0].st * size), halfSize - 1., round))) * color1.a;
    gl_FragColor = vec4(createGradient(gl_TexCoord[0].st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);
}