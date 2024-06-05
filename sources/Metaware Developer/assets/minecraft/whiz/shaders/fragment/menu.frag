/*
 * Original shader from: https://www.shadertoy.com/view/fljXWt
 */

#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 resolution;
uniform vec2 mouse;

#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.1415926535897932384626433832795

const float wave_amplitude = 0.076;
const float period = 2.*PI;

float wave_phase() {
	return time;
}

float square(vec2 st) {
	vec2 bl = step(vec2(0.), st);       // bottom-left
	vec2 tr = step(vec2(0.),1.0-st);   // top-right
	return bl.x * bl.y * tr.x * tr.y;
}

vec4 frame(vec2 st) {
	float tushka = square(st*mat2((1./.48), 0., 0., (1./.69)));

	mat2 sector_mat = mat2(1./.16, 0., 0., 1./.22);
	float sectors[4];
	sectors[0] = square(st * sector_mat + (1./.16)*vec2(0.000,-0.280));
	sectors[1] = square(st * sector_mat + (1./.16)*vec2(0.000,-0.060));
	sectors[2] = square(st * sector_mat + (1./.16)*vec2(-0.240,-0.280));
	sectors[3] = square(st * sector_mat + (1./.16)*vec2(-0.240,-0.060));
	vec3 sector_colors[4];
	sector_colors[0] = vec3(0.941, 0.439, 0.404) * sectors[0];
	sector_colors[1] = vec3(0.435, 0.682, 0.843) * sectors[1];
	sector_colors[2] = vec3(0.659, 0.808, 0.506) * sectors[2];
	sector_colors[3] = vec3(0.996, 0.859, 0.114) * sectors[3];

	return vec4(vec3(sector_colors[0] + sector_colors[1] +
	sector_colors[2] + sector_colors[3]), tushka);
}

vec4 trail_piece(vec2 st, vec2 index, float scale) {
	scale = index.x * 0.082 + 0.452;

	vec3 color;
	if (index.y > 0.9 && index.y < 2.1 ) {
		color = vec3(0.435, 0.682, 0.843);
		scale *= .8;
	} else if (index.y > 3.9 && index.y < 5.1) {
		color = vec3(0.941, 0.439, 0.404);
		scale *= .8;
	} else {
		color = vec3(0., 0., 0.);
	}

	float scale1 = 1./scale;
	float shift = - (1.-scale) / (2. * scale);
	vec2 st2 = vec2(vec3(st, 1.) * mat3(scale1, 0., shift, 0., scale1, shift, 0., 0., 1.));
	float mask = square(st2);

	return vec4( color, mask );
}

vec4 trail(vec2 st) {
	// actually 1/width, 1/height
	const float piece_height = 7. / .69;
	const float piece_width = 6. / .54;

	// make distance between smaller segments slightly lower
	st.x = 1.2760 * pow(st.x, 3.0) - 1.4624 * st.x*st.x + 1.4154 * st.x;

	float x_at_cell = floor(st.x*piece_width)/piece_width;
	float x_at_cell_center = x_at_cell + 0.016;
	float incline = cos(0.5*period + wave_phase()) * wave_amplitude;

	float offset = sin(x_at_cell_center*period + wave_phase())* wave_amplitude +
	incline*(st.x-x_at_cell)*5.452;

	float mask = step(offset, st.y) * (1.-step(.69+offset, st.y)) * step(0., st.x);

	vec2 cell_coord = vec2((st.x - x_at_cell) * piece_width,
	fract((st.y-offset) * piece_height));
	vec2 cell_index = vec2(x_at_cell * piece_width,
	floor((st.y-offset) * piece_height));

	vec4 pieces = trail_piece(cell_coord, cell_index, 0.752);

	return vec4(vec3(pieces), pieces.a * mask);
}

vec4 logo(vec2 st) {
	if (st.x <= .54) {
		return trail(st);
	} else {
		vec2 st2 = st + vec2(0., -sin(st.x*period + wave_phase())*wave_amplitude);
		return frame(st2 + vec2(-.54, 0));
	}
}


void main(void)
{
	vec2 st = gl_FragCoord.xy/resolution.xy;
	st.x *= resolution.x/resolution.y;

	st += vec2(.0);
	st *= 1.472;
	st += vec2(-0.7,-0.68);
	float rot = PI*-0.124;
	st *= mat2(cos(rot), sin(rot), -sin(rot), cos(rot));
	vec3 color = vec3(1.);

	vec4 logo_ = logo(st);
	gl_FragColor = mix(vec4(0.,.5,.5,1.000), logo_, logo_.a);
}

