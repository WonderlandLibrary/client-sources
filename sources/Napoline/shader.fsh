#version 150

#define GLSLSANDBOX

#ifdef GL_ES
precision mediump float;
#endif

float DERRect(in vec3 z, vec4 radii){return length(max(abs(z)-radii.xyz,0.0))-radii.w;}
float DERect(in vec2 z, vec2 r){return max(abs(z.x)-r.x,abs(z.y)-r.y);}
float DEEiffie(in vec3 z){
	float d1=DERect(z.yz,vec2(0.25,0.9));//I
	float d2=min(DERect(z.xz,vec2(0.25,0.9)),min(DERect(z.xz+vec2(0.25,0.7),vec2(0.5,0.2)),DERect(z.xz+vec2(0.25,0.0),vec2(0.5,0.2))));//F
	float d3=min(DERect(z.xy,vec2(0.25,0.9)),min(DERect(z.xy+vec2(0.25,0.7),vec2(0.5,0.2)),min(DERect(z.xy+vec2(0.25,0.0),vec2(0.5,0.2)),DERect(z.xy+vec2(0.25,-0.7),vec2(0.5,0.2)))));//E
	return min(d1,min(d2,d3));
}

float DE(in vec3 z){
	return max(DERRect(z,vec4(0.95,0.95,0.95,0.05)),-DEEiffie(z));
}
float sinNoise3d(in vec3 p){
	float s=0.5,r=0.0;
	for(int i=0;i<3;i++){
		p+=p+sin(p.yzx*0.8+sin(p.zxy*0.9));
		s*=0.5;
		r+=sin(p.z+1.5*sin(p.y+1.3*sin(p.x)))*s;
	}
	return r;
}
float volLiteMask(vec3 rd){
	vec3 ar=abs(rd);
	vec2 pt;
	float d=100.0;
	if(ar.x>ar.y && ar.x>ar.z)pt=rd.yz/ar.x;
	else{
		if(ar.y>ar.z)pt=rd.xz/ar.y;
		else {
			pt=rd.xy/ar.z;
			d=DERect(pt+vec2(0.25,-0.7),vec2(0.5,0.2));
		}
		d=min(d,min(DERect(pt+vec2(0.25,0.7),vec2(0.5,0.2)),DERect(pt+vec2(0.25,0.0),vec2(0.5,0.2))));
	}
	d=min(d,DERect(pt,vec2(0.25,0.9)));
	return (d<0.0)?1.0:0.0;
}
float rand(vec2 c){return fract(sin(c.x+2.4*sin(c.y))*34.1234);}
mat3 lookat(vec3 fw){
	fw=normalize(fw);vec3 rt=normalize(cross(-fw,vec3(0.0,1.0,0.0)));return mat3(rt,cross(rt,fw),fw);
}
vec4 scene(vec3 ro, vec3 rd) {
	float t=0.0,d=0.0;
	for(int i=0;i<48;i++){
		t+=d=DE(ro+rd*t);
		if(t>10.0 || d<0.01)break;
	}
	float lt=pow(dot(rd,normalize(-ro)),10.0);
	float t2=0.2*rand(gl_FragCoord.xy);
	vec3 sum=vec3(0.0);
	for(int i=0;i<48;i++){
		t2+=0.2+t2*t2*0.01;
		//if((t2>t && d<0.2) || t2>100.0)break;
        if(t2>t && d<0.2)break;
        //t2=min(t2,10.0);
        if(t2>9.0)t2-=0.75+0.25*sin(float(i*2));
		vec3 vr=normalize(ro+rd*t2);
		if(vr==vr)sum+=(vr*0.5+0.5)*volLiteMask(vr)*(0.1+0.2*sinNoise3d((ro+rd*t2)));
	}
	vec3 col=clamp(lt*sum,0.0,1.0);
	return vec4(col,t);
}
void AnimateCamera(in vec2 uv, in float tym, inout vec3 ro, inout vec3 rd){
	ro=vec3(cos(tym),sin(tym*0.7),sin(tym))*4.0;
	rd=lookat(vec3(sin(tym*0.8),cos(tym*0.3),0.0)-ro)*normalize(vec3(uv,1.0));

    	int y_ = int(gl_FragCoord.y);
    	bool f = int(y_ / 2) * 2  == y_;
	if (f) {rd.x += 0.0; ro.x += 0.1; }
}



uniform float time;
uniform vec2 resolution;
void main(){
	vec2 uv=(1.0*gl_FragCoord.xy-resolution.xy)/(resolution.y/1.5);
	vec3 ro=vec3(0.0, 0.0, 0.0),rd=vec3(0.0);
	AnimateCamera(uv, time*0.4, ro, rd);
	vec4 scn=scene(ro,rd);
	gl_FragColor= vec4(scn.rgb,1.0)*1.9;
}
