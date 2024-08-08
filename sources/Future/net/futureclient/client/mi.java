package net.futureclient.client;

public class mi extends JG implements a
{
    public final double D;
    private double k;
    
    public mi() {
        final double k = 1.273197475E-314;
        final double d = 1.273197475E-314;
        super();
        this.D = d;
        this.k = k;
    }
    
    public mi(double k) {
        final double n = k;
        final double d = 1.273197475E-314;
        super();
        this.D = d;
        if (n > 0.0) {
            k = 0.0;
        }
        this.k = k;
    }
    
    @Override
    public double M(final String s, final String s2) {
        final double m = super.M(s, s2);
        return m + this.k * this.M(s, s2) * (1.0 - m);
    }
    
    private int M(final String s, final String s2) {
        String s3;
        String s4;
        if (s.length() > s2.length()) {
            s3 = s.toLowerCase();
            s4 = s2.toLowerCase();
        }
        else {
            s3 = s2.toLowerCase();
            s4 = s.toLowerCase();
        }
        int n = 0;
        int i = 0;
        int n2 = 0;
        while (true) {
            while (i < s4.length()) {
                if (s4.charAt(n2) != s3.charAt(n2)) {
                    final int n3 = n;
                    if (n3 > 4) {
                        return 4;
                    }
                    return n;
                }
                else {
                    ++n;
                    i = ++n2;
                }
            }
            final int n3 = n;
            continue;
        }
    }
}
