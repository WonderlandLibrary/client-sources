#version 120

uniform vec2 u_size;
uniform float u_radius;
uniform vec4 u_color;
uniform vec4 u_edges;

void main(void)
{
    vec2 tex_coord = gl_TexCoord[0].st;

    // fast implementation of custom corners
    // can probably be better, but this is good enough for now
    if (tex_coord.x < 0.5 && tex_coord.y > 0.5 && u_edges.x == 0.0 ||
        tex_coord.x > 0.5 && tex_coord.y > 0.5 && u_edges.y == 0.0 ||
        tex_coord.x > 0.5 && tex_coord.y < 0.5 && u_edges.z == 0.0 ||
        tex_coord.x < 0.5 && tex_coord.y < 0.5 && u_edges.w == 0.0) {
        gl_FragColor = u_color;
    } else {
        gl_FragColor = vec4(u_color.rgb, u_color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));
    }
}