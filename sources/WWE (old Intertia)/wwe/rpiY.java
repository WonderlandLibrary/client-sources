package wwe;

import java.util.*;
import net.minecraft.util.*;

public class rpiY
{
    public ArrayList<LdXC> padc;
    
    public rpiY() {
        super();
        this.padc = new ArrayList<LdXC>();
    }
    
    public void kDXm(final String s, final String s2) {
        this.padc.add(new LdXC(s, s2));
    }
    
    public void bSEt(final String s) {
        for (final LdXC ldXC : this.padc) {
            if (ldXC.CjlP().equalsIgnoreCase(s) || ldXC.ugqf().equalsIgnoreCase(s)) {
                this.padc.remove(ldXC);
                if (rpiY.\u2005\u2008) {
                    throw null;
                }
                break;
            }
            else {
                if (rpiY.\u2005\u2008) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    public String mwBj(final String s) {
        if (this.UsVJ(s)) {
            for (final LdXC ldXC : this.padc) {
                if (ldXC.CjlP().equalsIgnoreCase(s)) {
                    return ldXC.ugqf();
                }
                if (rpiY.\u2005\u2008) {
                    throw null;
                }
            }
        }
        return null;
    }
    
    public boolean UsVJ(final String s) {
        boolean b = false;
        final Iterator<LdXC> iterator = this.padc.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().CjlP().equalsIgnoreCase(StringUtils.stripControlCodes(s))) {
                b = true;
                if (rpiY.\u2005\u2008) {
                    throw null;
                }
                break;
            }
            else {
                if (rpiY.\u2005\u2008) {
                    throw null;
                }
                continue;
            }
        }
        return b;
    }
}
