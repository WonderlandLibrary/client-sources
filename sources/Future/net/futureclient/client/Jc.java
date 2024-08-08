package net.futureclient.client;

import java.io.File;

public class Jc extends XB
{
    public final Hd k;
    
    public Jc(final Hd k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M() {
        return "&e[name]";
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length < 1) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final int length = array.length;
        int i = 0;
        int n = 0;
        while (i < length) {
            sb.append(array[n]);
            final StringBuilder sb2 = sb;
            ++n;
            sb2.append(AE.M("e"));
            i = n;
        }
        final String trim = sb.toString().trim();
        final File file;
        if ((file = new File(Hd.M(this.k), new StringBuilder().insert(0, trim).append(trim.endsWith(".txt") ? "" : AE.M("H1\u001e1")).toString())).exists()) {
            this.k.M(false);
            Hd.M(this.k, file.getPath());
            this.k.M(true);
            return new StringBuilder().insert(0, "The spammer file ").append(trim).append(AE.M("e\u0011$\u0015e\n*\u0007!\u0003!F6\u0013&\u0005 \u00156\u00000\n)\u001fk")).toString();
        }
        return new StringBuilder().insert(0, "File ").append(trim).append(AE.M("H1\u001e1F!\t \u0015e\b*\u0012e\u0003=\u000f6\u0012k")).toString();
    }
}
