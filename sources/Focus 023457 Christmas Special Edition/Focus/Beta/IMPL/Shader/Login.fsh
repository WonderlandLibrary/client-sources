#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

const int num_balls = 70; // change this, but be careful
const float coordinate_scale = 1000.;
const float structure_size = 1.20; // Size from 0 to 1, max size = 1
const float glow_decay = 1.40;
const float trail_len = 151.0;
#define pi 3.14159265358
const float speed = 0.079;
const float rot_speed = speed*10.;
const float starting_pt = -506.0; // This is a good number

vec4 draw_ball(float i, float j, float size) {
    float balls = float(num_balls);
    float dt = starting_pt + time * speed;
    // Map coordinates to 0-1
    vec2 coord = gl_FragCoord.xy/resolution.xy;
    //map coordinates to -coord_scale -> +coord_scale
    coord = coord *coordinate_scale-coordinate_scale/2.;
    coord -= vec2(coord.x/2.,coord.y/2.);

    //Controls motion of balls
    float spacing = (2.*pi)/balls;

    float x =  (sin(dt*i*spacing)*100. - cos(dt*j*spacing)) * structure_size;
    float y =  (cos(dt*j*spacing)*100. - sin(dt*i*spacing)) * structure_size;
    y *= ((j - dt)/-dt) + sin(i*spacing + dt*sin((dt - sin((dt - cos(dt*sin(dt/140.)))*10.))/100.));
    x *= ((i - dt)/dt)  + sin(i*spacing - dt*cos((dt - cos((dt - sin(dt*cos(dt/140.)))*10.))/100.));
    //Correct aspect ratio
    coord.x *= resolution.x/resolution.y;
    vec2 pos = vec2(x,y);
    mat2 rot = mat2(cos(dt*rot_speed), -sin(dt*rot_speed), sin(dt*rot_speed), cos(dt*rot_speed));
    pos *= rot;
    float dist = length(coord - pos);

    //Controls how quickly brightness falls off
    float intensity = pow(size/dist, glow_decay);

    vec4 color = vec4(vec3(1.0) * abs(sin(vec3(time*0.25,time/2.,time/3.))), 1.0);
    return color * intensity;
}

void main( void ) {

    vec4 col = vec4(0.0);

    for (int i = 0; i < num_balls; i++) {
        vec2 pt = vec2(float(i),float(i));
        col += draw_ball(float(i),float(i), 1.2-distance(pt,vec2(0.))/coordinate_scale);
    }

    gl_FragColor = col;
}