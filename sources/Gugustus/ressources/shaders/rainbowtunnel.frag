#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

vec3 rotXY(vec3 p, vec2 rad) {
    vec2 s = sin(rad + time * 0.5);
    vec2 c = cos(rad + time * 0.5);
    
    mat3 m = mat3(
        c.y, s.x, -s.y,
        -s.x * s.y, c.x, c.y * s.x,
        c.x * s.y, s.x, c.x * c.y
    );
    return m * p;
}

float squareDistance(vec2 ip) {
    ip = mod(ip, 2.0) - 1.0;
    return max(abs(ip.x), abs(ip.y));
}

float smoothEdge(float edge, float margin, float x) {
    return smoothstep(edge - margin, edge + margin, x);
}

vec3 hueToRgb(float hue) {
    hue = fract(hue); // Wrap around 1.0
    float r = abs(hue * 6.0 - 3.0) - 1.0;
    float g = 2.0 - abs(hue * 6.0 - 2.0);
    float b = 2.0 - abs(hue * 6.0 - 4.0);
    return clamp(vec3(r, g, b), 0.0, 1.0);
}

void main(void) {
    const float PI = 3.1415926535;
    vec3 rgb;

    vec2 nsc = (gl_FragCoord.xy - resolution * 0.5) / resolution.yy * 2.0;
    vec3 dir = normalize(vec3(nsc, -2.0));
    dir = rotXY(dir, vec2((mouse.yx - 0.5) * PI * 0.35));
    vec2 uv = vec2(atan(dir.y, dir.x) / (PI * 2.0) + 0.5, dir.z / length(dir.xy));

    vec2 pos = uv * vec2(1.0, 0.2) - vec2(time * 0.01, time * 0.3);
    float d = squareDistance(pos * 16.0);
    
    // Apply chaos with time-based distortion
    float chaos = sin(time + length(pos) * 10.0) * 0.5 + 0.5;
    float edgeFactor = smoothEdge(0.1, 0.05 + chaos * 0.05, d);

    // Rainbow Color based on position along the tube
    float hue = uv.y + time * 0.1;
    vec3 rainbowColor = hueToRgb(hue);

    // Increase the visibility of rainbow colors
    rgb = mix(rainbowColor, vec3(0.0), edgeFactor * (0.5 + chaos * 0.5));

    gl_FragColor = vec4(rgb, 1.0);
}
