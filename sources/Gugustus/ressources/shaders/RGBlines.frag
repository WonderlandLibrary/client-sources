/*
 * Original shader from: https://www.shadertoy.com/view/WdscR4
 */

#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //

#define PI 3.1415926535
#define TAU 6.2831853071

float line(vec2 A, vec2 B, vec2 C, float thickness) {
	vec2 AB = B-A;
    vec2 AC = C-A;

    float t = dot(AC, AB) / dot(AB, AB);
    t = min(1.0, max(0.0, t));
    
    vec2 Q = A + t * AB;
    
    float dist = length(Q-C);
    return smoothstep(-0.01, -dist, -thickness) + smoothstep(0.0, dist, thickness);
}

void mainImage( out vec4 fragColor, in vec2 fragCoord ) {
    vec2 uv = (fragCoord-.5*iResolution.xy)/iResolution.y;

    vec3 color = vec3(0.0);
    
    for (int i = 0; i < 20; ++i) {
        float r = 0.5 - sin(iTime + float(i) * 0.5 * PI) * 0.1;
        float angle = iTime * 0.2 + float(i+1) * 0.1 * PI;

        vec2 dir = vec2(cos(angle), sin(angle)) * r;

        vec2 A = -dir * 0.5;
        vec2 B = vec2(0.0);

        float t = iTime * 0.5 + float(i) * 0.1 * TAU;
        vec3 rgb = vec3(
        	sin(t		  ) * 0.5 + 0.5,
            sin(t + PI/2.0) * 0.5 + 0.5,
            sin(t + PI	  ) * 0.5 + 0.5
        );

        color += line(A, B, uv, 0.008) * rgb;        
    }

    fragColor = vec4(1.  - exp2( -vec3(color)), 1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}