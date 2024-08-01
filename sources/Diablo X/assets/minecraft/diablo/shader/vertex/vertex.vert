#version 120

varying vec2 fragCoord;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;

    fragCoord = gl_TexCoord[0].xy;
}