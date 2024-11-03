/*
 * Original shader from: https://www.shadertoy.com/view/cdyXWG
 */

#ifdef GL_ES
precision highp float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //
// ----------------------------------------------------------------------------------------
//	"Toon Cloud" by Antoine Clappier - March 2015
//
//	Licensed under:
//  A Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
//	http://creativecommons.org/licenses/by-nc-sa/4.0/
// ----------------------------------------------------------------------------------------

#define TAU 6.28318530718


const vec3 BackColor	= vec3(0.6, 0.4, 0.8);
const vec3 CloudColor	= vec3(0.9,0.30,0.7);


float Func(float pX)
{
    return 0.6*(0.5*sin(0.1*pX) + 0.5*sin(0.553*pX) + 0.7*sin(1.2*pX));
}


float FuncR(float pX)
{
    return 0.5 + 0.25*(1.0 + sin(mod(40.0*pX, TAU)));
}


float Layer(vec2 pQ, float pT)
{
    vec2 Qt = 3.5*pQ;
    pT *= 0.5;
    Qt.x += pT;

    float Xi = floor(Qt.x);
    float Xf = Qt.x - Xi -0.5;

    vec2 C;
    float Yi;
    float D = 1.0 - step(Qt.y,  Func(Qt.x));

    // Disk:
    Yi = Func(Xi + 0.5);
    C = vec2(Xf, Qt.y - Yi );
    D =  min(D, length(C) - FuncR(Xi+ pT/80.0));

    // Previous disk:
    Yi = Func(Xi+1.0 + 0.5);
    C = vec2(Xf-1.0, Qt.y - Yi );
    D =  min(D, length(C) - FuncR(Xi+1.0+ pT/80.0));

    // Next Disk:
    Yi = Func(Xi-1.0 + 0.5);
    C = vec2(Xf+1.0, Qt.y - Yi );
    D =  min(D, length(C) - FuncR(Xi-1.0+ pT/80.0));

    return min(1.0, D);
}


mat2 r2(float r){ return mat2(cos(r),sin(r),-sin(r),cos(r));}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Setup:
    vec2 UV = 2.0*(fragCoord.xy - iResolution.xy/2.0) / min(iResolution.x, iResolution.y);
    vec2 op=UV;//op
    float bb=smoothstep(0.,1.,clamp(cos(op.y*.075-time*.75),-.25,.25)*2.+.5); //bb
    UV.xy*=r2(sin(UV.y*.1+bb*2.)+time*.1); //rot
    // Render:
    vec3 Color= BackColor;

    for(float J=0.0; J<=1.0; J+=0.2)
    {
        // Cloud Layer:
        float Lt =  iTime*(0.5  + 1.0*J)*(1.0 + 0.1*sin(226.0*J)) + 17.0*J;
        vec2 Lp = vec2(0.0, 0.3+1.5*( J - 0.5));
        float L = Layer(UV + Lp, Lt);

        // Blur and color:
        float Blur = 1.0*(0.5*abs(2.0 - 5.0*J))/(11.0 - 5.0*J);

        float V = mix( 0.0, 1.0, 1.0 - smoothstep( 0.0, 0.01 +0.2*Blur, L ) );
        vec3 Lc=  mix( CloudColor, vec3(1.0), J);

        Color =mix(Color, Lc,  V);
    }

    fragColor = vec4(Color, 1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}