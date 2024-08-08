package net.futureclient.client;

public enum nB
{
    d {
        public static final nB d;
        public static final nB a;
        public static final nB D;
        private static final nB[] k;
    }, 
    a {
        public static final nB d;
        public static final nB a;
        public static final nB D;
        private static final nB[] k;
    }, 
    D {
        public static final nB d;
        public static final nB a;
        public static final nB D;
        private static final nB[] k;
    };
    
    private static final nB[] k;
    
    static {
        k = new nB[] { nB.d, nB.a, nB.D };
    }
    
    public static nB[] values() {
        return nB.k.clone();
    }
    
    public static nB valueOf(final String s) {
        return Enum.<nB>valueOf(nB.class, s);
    }
}