#version 120

uniform vec2 u_size;
uniform float u_radius;
uniform vec4 u_first_color;
uniform vec4 u_second_color;
uniform vec4 u_third_color;
uniform float u_time;
uniform int u_direction;
uniform vec4 u_edges;

void main() {
    vec2 tex_coord = gl_TexCoord[0].st;
    float newTexCoord = u_direction > 0.0 ? tex_coord.y : tex_coord.x;

    float blendFactor = smoothstep(0.0, 1.0, sin(3.0 * (newTexCoord + u_time)));

    vec4 color = mix(mix(u_first_color, u_second_color, 0.5 + 0.5 * sin(3.0 * (newTexCoord + u_time))),
    mix(u_second_color, u_third_color, 0.5 + 0.5 * sin(3.0 * (newTexCoord + u_time))),
    blendFactor);

    if (tex_coord.x < 0.5 && tex_coord.y > 0.5 && u_edges.x == 0.0 ||
    tex_coord.x > 0.5 && tex_coord.y > 0.5 && u_edges.y == 0.0 ||
    tex_coord.x > 0.5 && tex_coord.y < 0.5 && u_edges.z == 0.0 ||
    tex_coord.x < 0.5 && tex_coord.y < 0.5 && u_edges.w == 0.0) {
        gl_FragColor = color;
    } else {
        gl_FragColor = vec4(color.rgb, color.a * smoothstep(1.0, 0.0, length(max((abs(tex_coord - 0.5) + 0.5) * u_size - u_size + u_radius, 0.0)) - u_radius + 0.5));
    }
}
