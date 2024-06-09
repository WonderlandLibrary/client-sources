#version 120

// Sampler, contains the texture of the Entity FBO
uniform sampler2D DiffuseSamper;

// Contains the size of one texel
uniform vec2 TexelSize;

// The width of the outline.
uniform int width;

// The color of the outline.
uniform vec3 color;

bool solid(){
    // Color of the texel at this fragment
    vec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st);

    // Returns false if the fragment is full alpha.
    if(centerCol.a > 0.0F){
        return false;
    }

    // Loops through a square with the length of 2width
    for(int x = -width; x <= width; x++){
        for(int y = -width; y <= width; y++){
            // The color at the offset texel.
            vec4 col = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(x*TexelSize.x, y*TexelSize.y));

            // Returns true if there is a solid fragment.
            if(col.a > 0){
                return true;
            }
        }
    }
    // Return false if nothing is found.
    return false;
}

void main(){
    if(solid()){
        // Sets the color to outline color.
        gl_FragColor = vec4(color, 1);
    }else{
        // Sets the color to no alpha.
        gl_FragColor = vec4(0, 0, 0, 0);
    }
}
