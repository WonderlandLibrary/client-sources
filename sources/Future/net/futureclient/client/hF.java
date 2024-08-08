package net.futureclient.client;

private enum hF
{
    private static final hF[] a;
    
    D {
        private static final hF[] a;
        public static final hF D;
        public static final hF k;
    }, 
    k {
        private static final hF[] a;
        public static final hF D;
        public static final hF k;
    };
    
    static {
        a = new hF[] { hF.k, hF.D };
    }
    
    public static hF[] values() {
        return hF.a.clone();
    }
    
    public static hF valueOf(final String s) {
        return Enum.<hF>valueOf(hF.class, s);
    }
}