#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;


vec3 palette(float t) {
    vec3 a = vec3(0.8,0.5,0.4);
    vec3 b = vec3(0.2,0.4,0.2);
    vec3 c = vec3(2.0,1.0,1.0);
    vec3 d = vec3(0.0,0.25,0.25);
    return a + b*cos( 6.28318*(c*t+d) );
}

void main() {
    vec2 uv = (gl_FragCoord.xy - mouse.xy) / resolution.y;
    float d = length(uv);
    vec3 tint = palette(time);
    d = smoothstep (0.015, 0.06, d);
    d = 1. - d;
    vec4 tint2 = vec4(tint, 0.5 * d);
    gl_FragColor = vec4(tint2 * d);
}