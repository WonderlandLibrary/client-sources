package net.futureclient.client;

import java.util.Iterator;
import java.util.StringJoiner;

public class jD extends XB
{
    public jD() {
        super(new String[] { "Toggle", "t" });
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final String s = array[0];
        final Ga m;
        if ((m = pg.M().M().M(s)) == null) {
            final StringJoiner stringJoiner = new StringJoiner(", ");
            final Iterator<Ga> iterator = (Iterator<Ga>)pg.M().M().M().iterator();
            while (iterator.hasNext()) {
                final String[] i;
                final int length = (i = iterator.next().M()).length;
                int j = 0;
                int n = 0;
                while (j < length) {
                    final String s2;
                    if ((s2 = i[n]).contains(s)) {
                        stringJoiner.add(String.format("&e%s&7", s2));
                    }
                    j = ++n;
                }
            }
            if (stringJoiner.length() < 1) {
                return "That module does not exist.";
            }
            return String.format("Did you mean: %s?", stringJoiner.toString());
        }
        else {
            if (!(m instanceof l)) {
                return "That module is not toggleable.";
            }
            final Ea ea = (Ea)m;
            final String s3 = "b@gG(T _\"Wg\u00164\u0015p\u001d";
            ea.M();
            final String k = X.M(s3);
            final Object[] array2 = new Object[2];
            final String[] l = ea.M();
            final int n2 = 0;
            array2[n2] = l[n2];
            array2[1] = (ea.M() ? "&aon" : "&coff");
            return String.format(k, array2);
        }
    }
    
    @Override
    public String M() {
        return "&e[module]";
    }
}
