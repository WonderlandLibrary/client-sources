
#extension GL_OES_standard_derivatives : enable 

#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 resolution;
uniform vec2 mouse;

float cosNoise(in vec2 pos){
	return 0.7*(sin(pos.x) + sin(pos.y));
}

const mat2 m2 = mat2(0.8, -0.6, 0.6, 0.8); 

float map(in vec3 pos){
    vec2 q = pos.xz * .6;
    float h = sin(time*.2)*4.;
        
    float s = 1.5;
    for(int i = 0; i<10; i++){
   	   h += s*cosNoise(q);
       s *= .55;
       q = m2 * (q) * 2.;
    }
    
    h*=.3;
	float mf = max(fract(h-1.),fract(-h-1.))-.5;
	float mf2 = (cos(2.*h*3.14)+1.)*.5;
    return pos.y - mix(ceil(h)*2.,h,mf2);
}


vec3 calcNormal(in vec3 pos){
	vec3 nor;
    vec2 e = vec2(0.01,0.00001);
    nor.x = map(pos + e.xyy) - map(pos - e.xyy);
    nor.y = map(pos + e.yxy) - map(pos - e.yxy);
    nor.z = map(pos + e.yyx) - map(pos - e.yyx);
    return normalize(nor);
}

float calcShadow(in vec3 ro, in vec3 rd){
    float res = 1.0;
    float t = 0.1;
    for (int i = 0; i <16; i++){
    	vec3 pos = ro + t*rd;
        float h = map(pos);
       res = min( res, max(h,0.0)*1.01/t );
        if(res< 1.0) break;
        t+=h*0.1;
    }
    return res;
}

void main()
{
    vec2 p = gl_FragCoord.xy / resolution.xy;
    vec2 q = -1.0 + 2.0 * p;
    q.x *= 1.5; 
    
    vec3 ro = vec3(100.0,  19.0, - time);
    vec3 rd = normalize( vec3(q.x, q.y - 1.5, -1.0));
    
    vec3 col = vec3(0.4, 0.8, 1.0);
    float tmax = 50.0;
    float t = 0.0;
    
    for(int i=0; i<32; i++){
    	vec3 pos = ro + rd*t;
        float h = map(pos);
        if(h < 0.1 || t > tmax) break;
        
        t += h*0.25;
    }
    
    vec3 light = normalize(vec3(0.1,0.1,-0.9));
    
    if(t < tmax){
        vec3 pos = ro + t*rd;
        vec3 nor = calcNormal(pos);
        float sha = calcShadow(pos + nor *0.1,light);

        float dif = clamp (dot(nor,light), 0.0,1.0);
        vec3 lig = vec3(1.0, 1.0,0.0)*dif *sha;
        lig += vec3(0.1,0.1,0.1)*nor.y *1.0;
        
        
        vec3 mate = vec3(0.2, 0.6, 0.5); 
        mate = mix(mate, vec3(0.3,0.5,0.5), smoothstep(1.7,0.9,nor.y));
    	col = lig * mate;
        
        float fog = exp(-0.00002 * t  * t * t );
        col += ((1.0 - fog) * vec3(0.1,0.8,1.0));
    }
    
    gl_FragColor = vec4(sqrt(col),1.0);
}