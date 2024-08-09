package ez.h.features.visual;

import ez.h.features.*;

public class FireFlyes extends Feature
{
    public FireFlyes() {
        super("FireFlyes", "\u0420\u0438\u0441\u0443\u0435\u0442 \u0441\u0432\u0435\u0442\u043b\u044f\u0447\u043a\u043e\u0432 \u0432 \u043c\u0438\u0440\u0435.", Category.VISUAL);
    }
    
    static class FireFly extends btf
    {
        public float z;
        public float p;
        public float q;
        public float x;
        public float y;
        
        public void render() {
        }
        
        public FireFly(final amu amu, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final float n7) {
            super(amu, n, n2, n3, 0.0, 0.0, 0.0);
            this.x = this.x;
            this.y = this.y;
            this.z = this.z;
            this.p = this.p;
            this.q = this.q;
        }
    }
}
