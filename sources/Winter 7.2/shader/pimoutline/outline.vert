/*
    Credit to TheCyberBrick for this shader.
*/
#version 120

void main() {
  //Set texel coordinates for fragment shader
  gl_TexCoord[0] = gl_MultiTexCoord0;

  //Calculate and return vertex position
  gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
} 