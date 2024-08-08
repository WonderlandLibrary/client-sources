package net.futureclient.client;

import net.minecraft.util.text.TextFormatting;

public enum Wh
{
    j("Generated", TextFormatting.BLUE) {
        public static final Wh j;
        private static final Wh[] K;
        private TextFormatting M;
        public static final Wh d;
        public static final Wh a;
        public static final Wh D;
        private String k;
        
        {
            this.k = k;
            this.M = m;
        }
    };
    
    private static final Wh[] K;
    private TextFormatting M;
    
    d("Premium", TextFormatting.GREEN) {
        public static final Wh j;
        private static final Wh[] K;
        private TextFormatting M;
        public static final Wh d;
        public static final Wh a;
        public static final Wh D;
        private String k;
        
        {
            this.k = k;
            this.M = m;
        }
    }, 
    a("Premium", TextFormatting.GREEN) {
        public static final Wh j;
        private static final Wh[] K;
        private TextFormatting M;
        public static final Wh d;
        public static final Wh a;
        public static final Wh D;
        private String k;
        
        {
            this.k = k;
            this.M = m;
        }
    }, 
    D("Non-Premium", TextFormatting.YELLOW) {
        public static final Wh j;
        private static final Wh[] K;
        private TextFormatting M;
        public static final Wh d;
        public static final Wh a;
        public static final Wh D;
        private String k;
        
        {
            this.k = k;
            this.M = m;
        }
    };
    
    private String k;
    
    {
        this.k = k;
        this.M = m;
    }
    
    static {
        K = new Wh[] { Wh.D, Wh.a, Wh.d, Wh.j };
    }
    
    public static Wh[] values() {
        return Wh.K.clone();
    }
    
    public static Wh valueOf(final String s) {
        return Enum.<Wh>valueOf(Wh.class, s);
    }
    
    public String M() {
        return this.k;
    }
    
    public TextFormatting M() {
        return this.M;
    }
}