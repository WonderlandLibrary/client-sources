#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 resolution;
uniform float time;
uniform vec2 mouse;

const int   complexity      = 130;                  // More points of color.
float mouse_factor          = 3.01*sin(0.01*time);  // Makes it more/less jumpy.
const float mouse_offset    = 100.0;                // Drives complexity in the amount of curls/cuves.  Zero is a single whirlpool.
const float fluid_speed     = 10.0;                 // Drives speed, higher number will make it slower.
const float color_intensity = 0.1;
uniform sampler2D sTexture;
const float Pi = 3.14159;

void main()
{

    vec2 p=(2.0*gl_FragCoord.xy-resolution)/max(resolution.x,resolution.y);
    for(int i=1;i<complexity;i++) {
        vec2 newp=p;
        newp.x+=0.5/float(i)*sin(float(i)*p.y+time/fluid_speed+0.9*float(i))+mouse_offset;
        newp.y+=0.5/float(i)*sin(float(i)*p.x+time/fluid_speed+0.5*float(i+10))-mouse_offset;
        p=newp;
    }

    vec3 col=vec3(color_intensity*sin(3.0*p.x)+color_intensity + 0.3,color_intensity*sin(3.0*p.y)+color_intensity + 0.2,sin(p.x+p.y) + 2.);
    col *= 0.3;

    gl_FragColor = vec4(col, 1.0);
    gl_FragColor = vec4(gl_FragColor.xyz, 1.);
}