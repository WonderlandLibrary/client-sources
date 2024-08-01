#ifdef GL_ES
precision highp float;
#endif

uniform float time;
uniform vec2 resolution;

#define iTime time
#define iResolution resolution


#define PI 3.14159
#define    TAU 6.28318
precision highp float;

uniform vec2 mouse;

float horizon;

float shift_right(float v, float amt) {
    v = floor(v) + 0.5;
    return floor(v * exp2(-amt));
}

float shift_left(float v, float amt) {
    return floor(v * exp2(amt) + 0.5);
}

float mask_last(float v, float bits) {
    return mod(v, shift_left(1.0, bits));
}
float extract_bits(float v, float from, float n) {
    return mask_last(shift_right(v, from), n);
}
float tex(float val, float j) {
    float sign = val > 0.0 ? 0.0 : 1.0;
    val = abs(val);
    float exponent = floor(log2(val));
    float biased_exponent = exponent + 63.0;
    float mantissa = ((val * exp2(-exponent)) - 1.0) * 8388608.0;

    float index = j * 2.0;
    if (j == 0.0) {
        return sign + mod(biased_exponent, 2.0) * 2.0;
    } else if (j < 4.){
        index -= 1.;
        return extract_bits(biased_exponent, index, 2.0);
    } else {
        index -= 7.;
        return extract_bits(mantissa, index, 2.0);
    }
}

float ch(mat4 m, float c, vec2 uv) {
    if (max(abs(uv.x), abs(uv.y)) < .5) {
        float i = floor(uv.x * 16.) + 8.;
        float j = floor(uv.y * 16.) + 8.;
        vec4 v = m[3];
        if (i < 8.) {
            v = (i < 4.) ? m[0] : m[1];
        } else if (i < 12.) {
            v = m[2];
        }
        i = mod(i, 4.);
        if (i < 2.) {
            if (i==0.) return tex(v.x, j);
            return tex(v.y, j);
        } else {
            if (i==2.) return tex(v.z, j);
            return tex(v.w, j);
        }
    }
    return c;
}

vec3 ground(vec2 uv) {


    float fov = 0.55;
    float scaling = 0.06;

    vec3 p = vec3(uv.x, fov, uv.y - horizon);
    vec2 s = vec2(p.x/p.z-5.*time, p.y/p.z-time) * scaling;

    float color;
    color = 0.0;
    return vec3(color);
}

vec3 copper(in vec3 c) {
    vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0, 4.0, 2.0), 6.0)-3.0)-1.0, 0.0, 1.0);
    rgb = rgb*rgb*(3.0-2.0*rgb);
    return c.z * mix(vec3(1.0), rgb, c.y) * vec3(1.0, 1.0, 2.5);
}

float N21(vec2 p){
    p = fract(p*vec2(234.334, 125.64));
    p+=dot(p, p+25.34);
    return fract(p.x*p.y);
}

vec2 N22(vec2 p){
    float n = N21(p);
    return vec2(n, N21(p+n));
}

vec2 starPos(vec2 id, vec2 offs){
    vec2 n = N22(id+offs);
    return offs+(n)*.8;
}

float starLayer(vec2 uv) {
    if (uv.y < 0.) return 0.;

    vec2 id = floor(uv);
    vec2 fd = fract(uv)-.5;
    vec2 p[9];
    p[0] = starPos(id, vec2(-1, -1));
    p[1] = starPos(id, vec2(0, -1));
    p[2] = starPos(id, vec2(1, -1));
    p[3] = starPos(id, vec2(-1, 0));
    p[4] = starPos(id, vec2(0, 0));
    p[5] = starPos(id, vec2(1, 0));
    p[6] = starPos(id, vec2(-1, 1));
    p[7] = starPos(id, vec2(0, 1));
    p[8] = starPos(id, vec2(1, 1));
    float m = 0.;
    for (int i=0;i<9;i++) {
        vec2 d = (p[i] - fd);
        m += smoothstep(.07, .02, abs(d.x)+abs(d.y));
    }

    return m;
}

vec3 stars(vec2 uv) {
    vec3 col = vec3(0.);

    float m = 0.;
    float t = time*.1;

    for (float i=0.;i<.5;i+=1./5.) {
        float z = fract(i);
        float scale = mix(30., .55, z);
        float fade = smoothstep(0., .6, z);
        float border = horizon;
        if (uv.y >border) m += starLayer(uv*scale+vec2(i*113.+time, z*scale*.5))*fade;
    }

    return vec3(m);
}




    #define CHAR_W (17./16.)
    #define _(cc) col = ch(cc, col, uv); uv.x -= CHAR_W;
    #define SP uv.x -= CHAR_W;

const float text_width = 18. * CHAR_W;

float scroll(vec2 uv) {
    float zoom = 4.0;
    uv *= zoom;

    uv.x = mod(uv.x +4.*time, text_width) - .5;
    uv.y -= 2.0*horizon;
    //uv.y += sin(uv.x + time * 4.) * .5;
    //uv.y += floor(uv.x*1.) *0.15 ;

    float col = 0.;



    return pow(col, 3.)*1.66*(sin(time)*0.5+0.7);
}
vec3 punk(vec2 uv)
{
    vec3 col = vec3(.55, 0.35, 1.225);// Drop Colour
    uv.x += sin(0.2+uv.y*0.8)*0.5;
    uv.x = uv.x*150.0;// H-Count
    float dx = fract(uv.x);
    uv.x = floor(uv.x);
    float t =  iTime*0.4;
    uv.y *= 0.15;// stretch
    float o=sin(uv.x*21005.4);// offset
    float s=cos(uv.x*15.1)*.2 +.1;// speed
    float trail = mix(95.0, 35.0, s);// trail length
    float yv = fract(uv.y + t*s + o) * trail;
    yv = 1.0/yv;
    yv = smoothstep(0.0, 1.0, yv*yv);
    yv = sin(yv*PI)*(s*5.0);
    float d2 = sin(dx*PI);
    yv *= d2*d2;
    col = col*yv;
    return col;
}

void main(void)
{
    vec2 ppp = (-iResolution.xy + 2.*(gl_FragCoord.xy))/iResolution.y;
    ppp.x *= 0.8+sin(ppp.y*0.1+time)*.4;
    vec3 bbk = punk(sin(ppp.xy));
    vec2 uv = (gl_FragCoord.xy/resolution.xy) -.0;
    uv.x*=resolution.x/resolution.y;
    vec3 col = ground(uv);
    col += stars(uv);
    if (uv.y > -0.4)
    {
        vec3 copper = copper(vec3(uv.y*4.-time*.4, .3, .4));
        float text = scroll(uv)+scroll(uv*vec2(2., -1.)-vec2(0., .68))*.5;
        col = text > 0. ? vec3(.5)*text*copper : col;
    }

    gl_FragColor = vec4(col+bbk, 100.0);

}