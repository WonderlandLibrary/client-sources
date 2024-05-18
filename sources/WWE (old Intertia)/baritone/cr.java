package baritone;

import java.util.function.*;

public interface cr
{
    public static final cr a;
    public static final hh a;
    public static final bib a = bib.z();
    
    default void a(final String s) {
        if (!baritone.a.a().chatDebug.value) {
            return;
        }
        this.b(s);
    }
    
    default void b(final String s) {
        final hh f;
        (f = cr.a.f()).b().a(a.h);
        f.a((hh)new ho(" ".concat(String.valueOf(s))));
        bib.z().a(cr::a);
    }
    
    default void a(final hh hh) {
        baritone.a.a().logger.value.accept(hh);
    }
    
    default static {
        a = new cs();
        a = (hh)new ho(String.format("%s[%sBaritone%s]%s", a.f, a.n, a.f, a.h));
    }
}
