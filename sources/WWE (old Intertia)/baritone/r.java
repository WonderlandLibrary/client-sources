package baritone;

import io.netty.buffer.*;
import java.nio.file.*;
import java.util.function.*;
import baritone.api.cache.*;
import java.util.*;

public final class r implements IContainerMemory
{
    private final Path a;
    private final Map<et, s> a;
    
    public r(final Path a) {
        super();
        this.a = new HashMap<et, s>();
        this.a = a;
        try {
            this.a(Files.readAllBytes(a));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.a.clear();
        }
    }
    
    private void a(final byte[] array) {
        gy gy;
        for (int int1 = (gy = new gy(Unpooled.wrappedBuffer(array))).readInt(), i = 0; i < int1; ++i) {
            final int int2 = gy.readInt();
            final int int3 = gy.readInt();
            final int int4 = gy.readInt();
            final s s;
            baritone.s.a(s = new s((byte)0)).addAll(a(gy));
            final s s2 = s;
            baritone.s.a(s2, baritone.s.a(s2).size());
            baritone.s.b(s, -1);
            if (!baritone.s.a(s).isEmpty()) {
                this.a.put(new et(int2, int3, int4), s);
            }
        }
    }
    
    public final synchronized void a() {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        gy a;
        (a = new gy(Unpooled.buffer(0, 0))).writeInt(this.a.size());
        for (final Map.Entry<et, s> entry : this.a.entrySet()) {
            a = a(entry.getValue().getContents(), new gy(new gy(new gy(a.writeInt(entry.getKey().p())).writeInt(entry.getKey().q())).writeInt(entry.getKey().r())));
        }
        Files.write(this.a, a.array(), new OpenOption[0]);
    }
    
    public final synchronized void a(final et et, final int n, final int n2) {
        final s s;
        baritone.s.b(s = this.a.computeIfAbsent(et, r::b), n);
        baritone.s.a(s, n2);
    }
    
    public final synchronized Optional<s> a(final int n) {
        return this.a.values().stream().filter(r::a).findFirst();
    }
    
    private synchronized s a(final et et) {
        return this.a.get(et);
    }
    
    @Override
    public final synchronized Map<et, IRememberedInventory> getRememberedInventories() {
        return new HashMap<et, IRememberedInventory>(this.a);
    }
    
    public static List<aip> a(final byte[] array) {
        return a(new gy(Unpooled.wrappedBuffer(array)));
    }
    
    private static List<aip> a(final gy gy) {
        final int int1 = gy.readInt();
        final ArrayList<aip> list = new ArrayList<aip>();
        for (int i = 0; i < int1; ++i) {
            list.add(gy.k());
        }
        return list;
    }
    
    public static byte[] a(final List<aip> list) {
        return a(list, new gy(Unpooled.buffer(0, 0))).array();
    }
    
    private static gy a(final List<aip> list, gy a) {
        a = new gy(a.writeInt(list.size()));
        final Iterator<aip> iterator = list.iterator();
        while (iterator.hasNext()) {
            a = a.a((aip)iterator.next());
        }
        return a;
    }
    
    @Override
    public final IRememberedInventory getInventoryByPos(final et et) {
        return this.a(et);
    }
    
    private static boolean a(final int n, final s s) {
        return s.a(s) == n;
    }
    
    private static s b(final et et) {
        return new s((byte)0);
    }
}
