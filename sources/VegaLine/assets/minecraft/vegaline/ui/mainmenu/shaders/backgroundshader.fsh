/*
 * Original shader from: https://www.shadertoy.com/view/WdKXWt
 */

#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
float iTime = 0.0;
#define iResolution resolution

// Protect glslsandbox uniform names
#define time        stemu_time

// --------[ Original ShaderToy begins here ]---------- //
/*
Shader coded live on twitch (https://www.twitch.tv/nusan_fx)
The shader was made using Bonzomatic.
You can find the original shader here: http://lezanu.fr/LiveCode/DropOfDistortion.glsl
*/

#define ALL_COLORS 1

float time;

float sph(vec3 p, float r) {
  return length(p)-r;
}

float cyl(vec2 p, float r) {
  return length(p)-r;
}

mat2 rot(float a) {
  float ca=cos(a);
  float sa=sin(a);
  return mat2(ca,sa,-sa,ca);
}

float smin(float a, float b, float h) {
  float k=clamp((a-b)/h*0.5+0.5,0.0,1.0);
  return mix(a,b,k) - k*(1.0-k)*h;
}

vec3 smin(vec3 a, vec3 b, float h) {
  vec3 k=clamp((a-b)/h*0.5+0.5,0.0,1.0);
  return mix(a,b,k) - k*(1.0-k)*h;
}

float tick(float t, float d) {
  t/=d;
  float g=fract(t);
  g=smoothstep(0.0,1.0,g);
  g=pow(g,10.0);
  return (floor(t) + g)*d;
}

// Kaleidoscopic iterated function system
vec3 kifs(vec3 p, float t) {
  
  for(int i=0; i<3; ++i) {
    
    float t1 = tick(t + float(i), 0.4 + float(i)*0.1) + t*0.3;
    p.xz *= rot(t1);
    p.yz *= rot(t1*0.7);
    
    //p = abs(p);
    // symmetry on all axis with a smooth transition
    p = smin(p, -p, -1.0);
    p -= vec3(1.2,0.5,1.6);
    
  }
  
  return p;
}

float map(vec3 p) {
  
  vec3 bp=p;
  
  float t0 = time*0.7;
  float t1 = tick(t0,1.3)*0.3 + t0*0.1;
  p.xy *= rot(t1);
  p.yz *= rot(t1*1.3);
  
  vec3 p2 = kifs(p, t0*0.3);
  vec3 p3 = kifs(p+vec3(1,0,0.3), t0*0.2);
  
  float d = sph(p2, 1.0);
  float d2 = cyl(p3.xz, 1.1);
  
  // substract d2 to d1 with a smooth transition
  d = smin(d, -d2, -1.0);
  
  // remove whats above and below a depth plane to keep only a slice
  d = smin(d, -bp.z-4.0, -0.3);
  d = smin(d, bp.z-4.0, -0.3);
  
  return d;
}


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
  time = iTime+0.6;
    
  vec2 uv = vec2(fragCoord.x / iResolution.x, fragCoord.y / iResolution.y);
  uv -= 0.5;
  uv /= vec2(iResolution.y / iResolution.x, 1);
  
  vec2 buv = uv;
  
  uv *= rot(time*0.1);

  vec3 s=vec3(0,0,-17);
  float fov = 1.0;//1.3+sin(tick(time,2.3) + time*0.4)*0.4;
  vec3 r=normalize(vec3(-uv, fov));
  
  vec3 p=s;
  float at=0.0;
  bool inside = false;
  // main raymarching loop
  for(int i=0; i<100; ++i) {
    float d=map(p);
    if(d<0.01) {
      inside=true;
      break;
    }
    if(d>100.0) {
      break;
    }
    p+=r*d;
    at+=0.9/(3.0+abs(d));
  }
  
  // if we hit a surface
  if(inside) {
    vec2 off=vec2(0.01,0);
    vec3 n=normalize(map(p)-vec3(map(p-off.xyy), map(p-off.yxy), map(p-off.yyx)));
    // refract the ray direction
    r = refract(r, n, 0.7);
  }
  
  float depth = length(p-s);
  
  // project the surface position on the screen to get new UVs but with the refraction applyed
  float backscale = 0.9+sin(tick(time,2.3)*0.3 + time*0.1)*0.4;
  vec2 uv2 = p.xy * backscale * fov / (depth * r.z);
  
  
  vec2 grid = step(0.5,fract(uv2*7.0));
  vec2 grid2 = abs(fract(uv2*12.0)-0.5)*2.0;
  vec2 grid3 = abs(fract(uv2*12.0*rot(0.77))-0.5)*2.0;
    
  float val = min(grid.x, grid.y) + 1.0 - max(grid.x,grid.y);
  
  float prog = time * 0.3;
  float preanim = floor(mod(prog-0.5, 6.0));
  // transitions types
  if(preanim == 0.0) {
    prog += at*0.02;
  } else if(preanim==1.0) {
    prog -= length(uv)*0.2;
  } else if(preanim==2.0) {
    prog -= sin(atan(uv.y,uv.x)*5.0)*0.1;
  } else if(preanim==3.0) {
    prog -= min(abs(uv.x),abs(uv.y))*0.2;
  } else if(preanim==4.0) {
    prog -= length(uv)*0.1+sin(atan(uv.y,uv.x)*7.0)*0.05;
  } else {
    prog -= atan(buv.x,-buv.y)*0.05;
  }
  float anim = mod(prog, 6.0);
  float scene = floor(anim);
  
  float wantinverse = floor(mod(prog/6.0,2.0));
    
  // background types
  
  if(scene == 1.0 ) {
    val = step(min(grid2.x, grid2.y), 0.04);
  }
  
  if(scene == 2.0) {
    float di = fract(uv2.x*10.0-time*0.8)-0.5;
    val = step(fract(uv2.y*10.0 + abs(di)*2.0),0.2);
  }
  
  if(scene == 3.0) {
    val = step(sin(min(grid3.x, grid3.y)*16.0), -0.3);
  }
  
  vec2 gridSize = vec2(7,10)*1.3;
  float ds = length((fract(uv2*gridSize)-0.5)*gridSize.y/gridSize);
  float ds2 = length((fract(uv2*gridSize+0.5)-0.5)*gridSize.y/gridSize);
  
  if(scene == 4.0) {
    val = step(abs(min(ds,ds2)-0.25), 0.025);
  }
  
  if(scene == 5.0) {
    val = step(abs(ds-ds2)-0.13,0.0);
  }
    
  if(inside && wantinverse<0.5) {
    val = 1.0-val;
  }
  
  vec3 col=vec3(0);
  
  col += (val + 0.1);
  
  col *= at*0.08;
  
  // if on the second part, invert everything to get a white background
  if(wantinverse>0.5) {
    col = pow((1.0-col),vec3(12));
  }
  
#if ALL_COLORS
  // rotate all the colors
  float t3 = scene*1.3 + time*0.05;
  vec3 col2 = vec3(1,0.5,0.3);
  col2.xz *= rot(t3);
  col2.zy *= rot(t3*1.4);
  col2=abs(col2);
  col2=mix(col2, vec3(1), 0.4 + wantinverse*0.2);
  
  col *= col2;
#endif
  
  col *= pow(max(0.0,1.3-length(uv)),0.8);
  
  fragColor = vec4(col, 1);
}
// --------[ Original ShaderToy ends here ]---------- //

#undef time

void main(void)
{
    iTime = time;
    mainImage(gl_FragColor, gl_FragCoord.xy);
}