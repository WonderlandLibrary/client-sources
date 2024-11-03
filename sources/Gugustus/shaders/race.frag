#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;
// unfinished by gigatron ; road sin code from shader toyyy from mr matz;
vec3 road(vec3 pos)
{
	vec3 c1 = vec3(0.1,0.9,0.1);
	vec3 c2 = vec3(0.1,0.6,0.1);
	float a=time;
	float k=sin(.2*a);
	//pos.x *=
		pos.x-=.05*k*k*k*pos.y*pos.y;
	
	
	if(abs(pos.x) < 1.0)
	{
		c1 = vec3(0.9,0.1,0.1);
		c2 = vec3(0.9,0.9,0.9);
	}
	if(abs(pos.x) < .8)     //left and right line
	{
		c1 = vec3(0.5,0.5,0.5);
		c2 = vec3(0.5,0.5,0.5);
	}
	if(abs(pos.x) < 0.002)    //middle lines
	{
		c1 = vec3(0.5,0.5,0.5);
		c2 = vec3(0.9,0.9,0.9);
	}
	
	float t = time * 15.0;  //speed of the tesla car*10
	
	
	float v = pow(sin(0.),20.0);
	
	float rep = fract(pos.y+t);
	float blur =  dot(pos,pos)*0.005;
	vec3 ground = mix(c1,c2,smoothstep(0.25-blur*0.25,0.25+blur*0.25,rep)*smoothstep(0.75+blur*0.25,0.75-blur*0.25,rep));
	
	return ground;
}

vec3 sky(vec2 uv)
{
	return mix(vec3(1.0,1.0,1.0),vec3(0.1,0.7,1.0),uv.y);
}

vec3 car(vec2 uv)
{
	if (uv.y > -0.3)
		return vec3(0.);
	float carpos = (mouse.x * 2.0) - 11.0;
	if (abs(uv.x-carpos) < 0.15)
	{
		if (abs(carpos) > 0.4)
			return (vec3(1.,0.,0.));
		return vec3(1.);
	}
	return vec3(0.);
}

float insidecar(vec3 col)
{
	if (length(col) == .0)
		return .0;
	return 1.;
}

void main( void ) 
{
	vec2 res = resolution.xy/resolution.y;
	vec2 uv = gl_FragCoord.xy / resolution.y;
	uv -= res/2.0;
	

	vec3 pos = vec3(uv.x/abs(uv.y),1.0/abs(uv.y),step(0.0,uv.y)*2.0-1.0);
	
	vec3 color = vec3(0.0);
	
	color = mix(road(pos),sky(uv),step(.0,pos.z));
	
	vec3 carcolor = car(uv);
	
	color = mix(color, carcolor, insidecar(carcolor));
//	color = carcolor;
	
	gl_FragColor = vec4(color, 1.0 );

}