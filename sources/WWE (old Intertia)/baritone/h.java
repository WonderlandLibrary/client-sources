package baritone;

import java.io.*;
import java.nio.file.*;
import java.util.function.*;
import java.util.*;

public final class h
{
    private static final Map<Path, h> a;
    private final Path a;
    private List<aip> a;
    
    private h(final Path a) {
        super();
        this.a = a;
        System.out.println("Echest storing in ".concat(String.valueOf(a)));
        try {
            this.a = r.a(Files.readAllBytes(a));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("CANNOT read echest =( =(");
            this.a = null;
        }
    }
    
    public final synchronized void a() {
        System.out.println("Saving");
        if (this.a != null) {
            try {
                this.a.getParent().toFile().mkdir();
                Files.write(this.a, r.a(this.a), new OpenOption[0]);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("CANNOT save echest =( =(");
            }
        }
    }
    
    private static synchronized h b(final Path path, final UUID uuid) {
        return h.a.computeIfAbsent(path.resolve("echests").resolve(uuid.toString()), h::new);
    }
    
    static List a(final h h, final List a) {
        return h.a = (List<aip>)a;
    }
    
    static List a(final h h) {
        return h.a;
    }
    
    static h a(final Path path, final UUID uuid) {
        return b(path, uuid);
    }
    
    static {
        a = new HashMap<Path, h>();
    }
}
