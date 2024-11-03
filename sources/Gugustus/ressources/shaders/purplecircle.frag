#extension GL_OES_standard_derivatives : enable

#ifdef GL_ES
precision highp float;
#endif

uniform float time;
uniform vec2  resolution;
uniform float zoom;

#define PI 3.1415926535

mat2 rotate3d(float angle)
{
    return mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
}

void main()
{
    vec2 p = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);
    p = rotate3d((time * 2.0) * PI) * p;
    float t;
    if (sin(time) == 10.0)
        t = 0.075 / abs(1.0 - length(p));
    else
        t = 0.075 / abs(0.4/*sin(time)*/ - length(p));
    gl_FragColor = vec4(     ( 1. -exp( -vec3(t)  * vec3(0.13*(sin(time)+12.0), p.y*0.7, 3.0) )) , 1.0);
}
