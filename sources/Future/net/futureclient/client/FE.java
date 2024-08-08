package net.futureclient.client;

public enum FE
{
    private static final FE[] d;
    
    a {
        private static final FE[] d;
        public static final FE a;
        public static final FE D;
        public static final FE k;
    }, 
    D {
        private static final FE[] d;
        public static final FE a;
        public static final FE D;
        public static final FE k;
    }, 
    k {
        private static final FE[] d;
        public static final FE a;
        public static final FE D;
        public static final FE k;
    };
    
    static {
        d = new FE[] { FE.k, FE.D, FE.a };
    }
    
    public static FE[] values() {
        return FE.d.clone();
    }
    
    public static FE valueOf(final String s) {
        return Enum.<FE>valueOf(FE.class, s);
    }
}