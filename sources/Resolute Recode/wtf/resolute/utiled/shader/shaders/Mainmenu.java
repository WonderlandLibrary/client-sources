package wtf.resolute.utiled.shader.shaders;

import wtf.resolute.utiled.shader.IShader;

public class Mainmenu implements IShader {

    @Override
    public String glsl() {
        return """
                 #extension GL_OES_standard_derivatives : enable
                  
                  #ifdef GL_ES
                  precision mediump float;
                  #endif
                  
                  #define repeat(i, n) for(int i = 0; i < n; i++)
                  
                  uniform float time;
                  uniform float w;
                  uniform float h;
                  
                  void main(void)
                  {
                      vec2 uv = gl_FragCoord.xy / vec2(w,h) - .5;
                      uv.y *= h / w;
                      float mul = w / h;
                      vec3 dir = vec3(uv * mul, 1.);
                      float a2 = time * 20. + .5;
                      float a1 = 1.0;
                      mat2 rot1 = mat2(cos(a1), sin(a1), - sin(a1), cos(a1));
                      mat2 rot2 = rot1;
                      dir.xz *= rot1;
                      dir.xy *= rot2;
                      vec3 from = vec3(0., 0., 0.);
                      from += vec3(.0025 * time, .03 * time, - 2.);
                      from.xz *= rot1;
                      from.xy *= rot2;
                      float s = .1, fade = .07;
                      vec3 v = vec3(0.4);
                      repeat(r, 10) {
                  	vec3 p = from + s * dir * 1.5;
                  	p = abs(vec3(0.750) - mod(p, vec3(0.750 * 2.)));
                  	p.x += float(r * r) * 0.01;
                  	p.y += float(r) * 0.02;
                  	float pa, a = pa = 0.;
                  	repeat(i, 12) {
                  	    p = abs(p) / dot(p, p) - 0.340;
                  	    a += abs(length(p) - pa * 0.2);
                  	    pa = length(p);
                  	}
                  	a *= a * a * 2.;
                  	v += vec3(s * s , s , s * s) * a * 0.0017 * fade;
                  	fade *= 0.960;
                  	s += 0.110;
                      }
                      v = mix(vec3(length(v)), v, 0.9);
                      gl_FragColor = vec4(v * 0.01, 1.);
                  
                }""";
    }

}
