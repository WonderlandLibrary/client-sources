#version 120

uniform vec2 location, rectSize;
uniform vec4 color;
uniform float radius;
uniform bool blur;

float roundSDF(vec2 a, vec2 b, float c) {
	return length(max(abs(a) - b, 0.0)) - c;
}

// Class from SMok Client by SleepyFish
void main() {
	vec2 rectHalf = rectSize * .5;
	
	gl_FragColor = vec4(color.rgb, (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a);
}