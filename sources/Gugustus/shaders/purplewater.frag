/*
 * "Seascape" by Alexander Alekseev aka TDM - 2014
 * License Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

const int NUM_STEPS = 8;
const float PI      = 3.141592;
const float EPSILON = 1e-3;

#define EPSILON_NRM (0.1 / resolution.x)
#define SEA_TIME (1.0 + time * SEA_SPEED)

// sea
const int ITER_GEOMETRY = 1;
const int ITER_FRAGMENT = 2;
const float SEA_HEIGHT = 0.1;
const float SEA_CHOPPY = 4.0;
const float SEA_SPEED = 2.8;
const float SEA_FREQ = 1.3;
const vec3 SEA_BASE = vec3(1.1, .19, 1.22);
const vec3 SEA_WATER_COLOR = vec3(1.8,1.9,2.6);
const mat2 octave_m = mat2(2.6, 1.2, -1.2, 1.6);

// Get random value.
float hash(vec2 p)
{
    float h = dot(p, vec2(127.1, 311.7));
    return fract(sin(h) * 43758.5453123);
}

// Get Noise.
float noise(in vec2 p)
{
    vec2 i = floor(p);
    vec2 f = fract(p);

    // u = -2.0f^3 + 3.0f^2
    vec2 u = f * f * (3.0 - 2.0 * f);

    // Get each grid vertices.
    // +---+---+
    // | a | b |
    // +---+---+
    // | c | d |
    // +---+---+
    float a = hash(i + vec2(0.0,0.0));
    float b = hash(i + vec2(1.0,0.0));
    float c = hash(i + vec2(0.0,1.0));
    float d = hash(i + vec2(1.0,1.0));

    // Interpolate grid parameters with x and y.
    float result = mix(mix(a, b, u.x),
    mix(c, d, u.x), u.y);

    // Normalized to '-1 - 1'.
    return (2.0 * result) - 1.0;
}

// lighting
float diffuse(vec3 n, vec3 l, float p)
{
    return pow(dot(n, l) * 0.4 + 0.6, p);
}

float specular(vec3 n, vec3 l, vec3 e, float s)
{
    float nrm = (s + 8.0) / (PI * 8.0);
    return pow(max(dot(reflect(e, n), l), 0.0), s) * nrm;
}

// Get sky color by 'eye' position.
// So, The color changed smoothly by eye level.
vec3 getSkyColor(vec3 e)
{
    e.y = max(e.y, 0.7);
    float r = pow(.0 - e.y, 2.0);
    float g = 1.0 - e.y;
    float b = 0.6 + (1.0 - e.y) * 0.4;
    return vec3(r, g, b);
}

// Get sea wave octave.
float sea_octave(vec2 uv, float choppy)
{
    uv += noise(uv);
    vec2 wv = 1.0 - abs(sin(uv));
    vec2 swv = abs(cos(uv));
    wv = mix(wv, swv, wv);
    return pow(1.0 - pow(wv.x * wv.y, 0.85), choppy);
}

// p is ray position.
float map(vec3 p)
{
    float freq = SEA_FREQ; // => 0.16
    float amp = SEA_HEIGHT; // => 0.6
    float choppy = SEA_CHOPPY; // => 4.0

    // XZ plane.
    vec2 uv = p.xz;

    float d, h = 0.0;

    // ITER_GEOMETRY => 3
    for (int i = 0; i < ITER_GEOMETRY; i++)
    {
        d = sea_octave((uv + SEA_TIME) * freq, choppy);
        d += sea_octave((uv - SEA_TIME) * freq, choppy);
        h += d * amp;
        uv *= octave_m;
        freq *= 2.0;
        amp *= 0.2;
        choppy = mix(choppy, 1.0, 0.2);
    }

    return p.y - h;
}

// p is ray position.
// This function calculate detail map with more iteration count.
float map_detailed(vec3 p)
{
    float freq = SEA_FREQ;
    float amp = SEA_HEIGHT;
    float choppy = SEA_CHOPPY;

    vec2 uv = p.xz;

    float d, h = 0.0;

    // ITER_FRAGMENT = 5
    for (int i = 0; i < ITER_FRAGMENT; i++)
    {
        d = sea_octave((uv + SEA_TIME) * freq, choppy);
        d += sea_octave((uv - SEA_TIME) * freq, choppy);
        h += d * amp;
        uv *= octave_m;
        freq *= 2.0;
        amp *= 0.2;
        choppy = mix(choppy, 1.0, 0.2);
    }

    return p.y - h;
}

// p = ray position
// n = surface normal
// l = light
// eye = eye
// dist = ray marching distance
vec3 getSeaColor(vec3 p, vec3 n, vec3 l, vec3 eye, vec3 dist)
{
    float fresnel = clamp(1.0 - dot(n, -eye), 0.0, 1.0);
    fresnel = pow(fresnel, 3.0) * 0.65;

    vec3 reflected = getSkyColor(reflect(eye, n));
    vec3 refracted = SEA_BASE + diffuse(n, l, 80.0) * SEA_WATER_COLOR * 0.12;

    vec3 color = mix(refracted, reflected, fresnel);

    float atten = max(1.0 - dot(dist, dist) * 0.001, 0.0);
    color += SEA_WATER_COLOR * (p.y - SEA_HEIGHT) * 0.18 * atten;

    color += vec3(specular(n, l, eye,60.0));

    return color;
}

// tracing
vec3 getNormal(vec3 p, float eps)
{
    vec3 n;
    n.y = map_detailed(p);
    n.x = map_detailed(vec3(p.x + eps, p.y, p.z)) - n.y;
    n.z = map_detailed(vec3(p.x, p.y, p.z + eps)) - n.y;
    n.y = eps;
    return normalize(n);
}

// Get Height Map
float heightMapTracing(vec3 ori, vec3 dir, out vec3 p)
{
    float tm = 0.0;

    float tx = 1000.0;

    // Calculate 1000m far distance map.
    float hx = map(ori + dir * tx);

    // if hx over 0.0 is that ray on the sky. right?
    if(hx > 0.0)
    {
        p = vec3(0.0);
        return tx;
    }

    float hm = map(ori + dir * tm);
    float tmid = 0.0;

    // NUM_STEPS = 8
    for (int i = 0; i < NUM_STEPS; i++)
    {
        // Normalized by max distance (hx is max far position), is it correct?
        float f = hm / (hm - hx);

        tmid = mix(tm, tx, f);
        p = ori + dir * tmid;

        float hmid = map(p);

        if (hmid < 0.0)
        {
            tx = tmid;
            hx = hmid;
        }
        else
        {
            tm = tmid;
            hm = hmid;
        }
    }

    return tmid;
}

// main
void main(void)
{
    vec2 uv = gl_FragCoord.xy / resolution.xy;
    uv = uv * 1.8 - 1.0;

    const vec3 light = normalize(vec3(0.0, 1.0, 0.8));

    // ray
    vec3 ori = vec3(0.0, 3.5, 5.0);
    vec3 dir = normalize(vec3(uv.xy, -2.0));

    // tracing
    vec3 p;
    heightMapTracing(ori, dir, p);

    vec3 dist = p - ori;
    vec3 n = getNormal(p, dot(dist, dist) * EPSILON_NRM);

    // color
    vec3 sky = getSkyColor(dir);
    vec3 sea = getSeaColor(p, n, light, dir, dist);



    // This is coefficient for smooth blending sky and sea with
    float t = pow(smoothstep(0.0, -0.05, dir.y), 0.025);
    vec3 color = mix(sky, sea, t);

    // post
    gl_FragColor = vec4(color, 1.0);
}