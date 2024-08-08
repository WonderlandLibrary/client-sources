package net.futureclient.client;

public enum uf
{
    d {
        public static final uf d;
        private static final uf[] a;
        public static final uf D;
        public static final uf k;
    };
    
    private static final uf[] a;
    
    D {
        public static final uf d;
        private static final uf[] a;
        public static final uf D;
        public static final uf k;
    }, 
    k {
        public static final uf d;
        private static final uf[] a;
        public static final uf D;
        public static final uf k;
    };
    
    static {
        a = new uf[] { uf.D, uf.d, uf.k };
    }
    
    public static uf[] values() {
        return uf.a.clone();
    }
    
    public static uf valueOf(final String s) {
        return Enum.<uf>valueOf(uf.class, s);
    }
}