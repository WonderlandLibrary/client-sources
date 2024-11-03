varying float x;
varying float y;
uniform float width;
uniform float height;
uniform int kernel_size = 3;
uniform sampler2D txtr;

void main() {
    float dx = 1.0/width;
    float dy = 1.0/height;

    int sz = kernel_size;
    vec4 sum = vec4(0,0,0,0);
    float wt = 0.0;
    for ( int i = -sz; i <= sz; i++ ) {
        for ( int j = -sz; j <= sz; j++ ) {
            float dist = float(i*i+j*j);
            float w = pow(2.0,-dist/float(sz*sz));
            sum += texture(txtr,vec2(x+float(i)*dx,y+float(j)*dy),0.0)*w;
            wt += w;
        }
    }

    sum = sum*1.0/wt;
    gl_FragColor = vec4(sum.x,sum.y,sum.z,1);
}