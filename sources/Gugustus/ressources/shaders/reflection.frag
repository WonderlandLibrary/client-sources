


#extension GL_OES_standard_derivatives : enable

precision highp float;

uniform float time;
#define time time*.66
uniform vec2 mouse;
uniform vec2 resolution;


#define rot(a) mat2(cos(a), -sin(a), sin(a), cos(a))

float rbox( vec3 p, vec3 b, float r )
{
  vec3 q = abs(p) - b;
  return length(max(q,0.0)) + min(max(q.x,max(q.y,q.z)),0.0) - r;
}

// Dave Hoshkin 
float hash13(vec3 p3)
{
	p3  = fract(p3 * .1031);
    p3 += dot(p3, p3.zyx + 31.32);
    return fract((p3.x + p3.y) * p3.z);
}

vec3 hash33(vec3 p3)
{
	p3 = fract(p3 * vec3(.1031, .1030, .0973));
    p3 += dot(p3, p3.yxz+33.33);
    return fract((p3.xxy + p3.yxx)*p3.zyx);

}


#define b vec3(2.8, 2., 2.)

vec3 getCell(vec3 p)
{
    return floor(p / b);
}

vec3 getCellCoord(vec3 p)
{
    return mod(p, b) - b*.5;
}

float map(vec3 p){
    vec3 id = getCell(p);
    p = getCellCoord(p);
    
    float rnd = 1.*hash13(id*663.) - 1.;
    
    //p.xz *= rot(rnd*time*.3 + rnd);
    //p.xy *= rot(rnd*time*.3 + rnd);
    //p.yz *= rot(p.x*(5.+rnd*10.));

    return rbox(p, vec3(0.33, 0.90, .90), .0);
}

vec3 normal( in vec3 pos ){
    vec2 e = vec2(0.002, -0.002);
    return normalize(
        e.xyy * map(pos + e.xyy) + 
        e.yyx * map(pos + e.yyx) + 
        e.yxy * map(pos + e.yxy) + 
        e.xxx * map(pos + e.xxx));
}

vec3 color(vec3 ro, vec3 rd, vec3 n, float t){
    vec3 p = ro + rd*t;
    vec3 lp = ro + vec3(.0, .0, 1.7);
    
    //if(iMouse.z>0.) lp.z += m.y*14.;
    
    vec3 ld = normalize(lp-p);
    float dd = length(p - lp);
    float dif = max(dot(n, ld), .1);
    float fal = 1.5 / dd;
    float spec = pow(max(dot( reflect(-ld, n), -rd), 0.), 31.);

    vec3 id = getCell(p);

    bool l1, l2 = false;
    if (mod(id.y, 2.0) == 0.0) l1 = true;
    if (mod(id.z, 2.0) == 0.0) { l1 = !l1; } 
    if (l1 == true) { vec3 objCol = vec3(0.05, 0.05, 0.2); return objCol; }
    //if (mod(id.y, 2.0) == 0.0 && mod(id.z, 2.0) == 0.0 && mod(id.z, 20.0) == 0.0) { vec3 objCol = vec3(0.0, 0.0, 0.333); return objCol; }
    vec3 objCol = hash33(id);
       
    objCol *= (dif + .2);
    objCol += spec * 0.6;
    objCol *= fal;
    
    return objCol;
}


void main( void ){
    vec2 uv = vec2(gl_FragCoord.xy - 0.5*resolution.xy)/resolution.y;
    vec3 rd = normalize(vec3(uv, 0.8));
    vec3 ro = vec3(0., 7.0, 0.1);
    rd.xy*=rot(-time*0.5 + .5);
    ro.zy += time*5.;
    ro.x += cos(time)*.25;
    
    int nH = 0;
    float d = 0.0, t = 0.0; //, ns = 0.;
    vec3 p, n, col = vec3(0);
    
    for(int i = 0; i < 240; i++)
    {
    	d = map(ro + rd*t); 
        
        if(nH >= 8 || t >= 23.) break;
        
        if(abs(d) < .001){
            p = ro + rd*t;
            n = normal(p);
            
            if(d > 0.0 && nH <= 0) rd = refract(rd, n, 1./1.053);  // use the inverse of the RI here... your welcome. ;)
            
            col += color(ro, rd, n, t) * 1.11 * (n*0.5+0.5);
            
            nH++;
            t += .1;
        }
        t += abs(d) *0.6;
          }
	#define c col
	#define fogCol vec3(0., 0.015, 0.02)
		float fog = 0.0005;
		fog *= 1.; // fog strength
       	        c = mix(c, fogCol, 1.0 - (1.0) / (1.0 + t*t*t*fog));;
    //col /= float(nH)*.133;
    //col *= smoothstep(.5, .3, ns * .01);
    col = 1.-exp(-col);
    gl_FragColor = vec4(col, 1.0);
}///ändrom3da4






