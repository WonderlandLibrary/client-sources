// fire spider
#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

float distanceFunction(vec2 pos) {
    float a = atan(pos.x/pos.y);
    float f = 10.;
    float squiggle = sin( time+pos.x*16.0+f * a) *0.14;
    squiggle*=sin(time*2.37+a*8.0)*3.5;
    return length(pos) - .025 - squiggle*squiggle;
}

void main( void ) {
    vec2 p = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);

    p.x = dot(p,p);

    vec3 col = vec3(0.1,0.24,.5) * distanceFunction(p);
    col = smoothstep(0.01, 0.31, col);


    gl_FragColor = vec4(col.bgr, 1.0);
}