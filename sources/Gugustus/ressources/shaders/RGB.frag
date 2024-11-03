#extension GL_OES_standard_derivatives : enable

precision highp float;

#define PI 3.1415926

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}



void main( void ) {
	vec2 uv = gl_FragCoord.xy / resolution;
	vec2 pos = uv * 2.0 - 1.0;
	pos.x *= resolution.x / resolution.y;	
	
	float l = length(pos);
	
	pos = mat2(cos(l), sin(l), -sin(l), cos(l)) * pos;
	
	float angle = atan(pos.y, pos.x);
	angle = (1.0 * -time - angle);
	
	float sgdsgdsgd = mod((angle / (2.0 * PI)) * 3.0, 1.0);
	sgdsgdsgd = smoothstep(1.0,0.4,sgdsgdsgd);
	
	vec3 c = hsv2rgb(vec3((angle-42.5) / (2.0 * PI), smoothstep(3.0,0.1,l * 1.5), 1.0));
	
	gl_FragColor = vec4(c, sgdsgdsgd)-l*0.8;

}