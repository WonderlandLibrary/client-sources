#version 120
#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

void main() {
    vec2 tex_coord = gl_TexCoord[0].st;
    vec4 pixel_color = texture2D(u_diffuse_sampler, tex_coord);
    if (pixel_color.a == 0.0) discard;

    gl_FragColor = vec4(pixel_color.r, pixel_color.g, pixel_color.b, pixel_color.a);
}