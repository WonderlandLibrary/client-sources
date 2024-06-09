package com.client.glowclient;

import com.google.common.collect.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import org.apache.logging.log4j.*;
import java.util.*;
import com.client.glowclient.utils.*;

public class IC implements I
{
    private static Map<String, ra<?>> A;
    private static final Logger B;
    private static boolean b;
    
    @Override
    public <T extends e> List<ra<T>> D(final Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz must not be null!");
        }
        final ArrayList<ra<?>> list = (ArrayList<ra<?>>)new ArrayList<ra<T>>();
        for (final ra<?> ra : IC.A.values()) {
            if (clazz.isAssignableFrom(ra.A.getClass())) {
                list.add(ra);
            }
        }
        return (List<ra<T>>)list;
    }
    
    @Override
    public void M(final String s, final boolean b) {
        uc.K.setProperty(new StringBuilder().insert(0, "Extensions.").append(s).append(".enabled").toString(), Boolean.toString(b));
        uc.B();
    }
    
    @Override
    public String M(final String s) {
        if (!IC.A.containsKey(s)) {
            return null;
        }
        return IC.A.get(s).M();
    }
    
    public IC() {
        super();
    }
    
    @Override
    public boolean M(final String s) {
        return uc.K.getProperty(new StringBuilder().insert(0, "Extensions.").append(s).append(".enabled").toString(), "true").equals("true");
    }
    
    @Override
    public <T extends e> List<ra<T>> M(final Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz must not be null!");
        }
        final ArrayList<ra<T>> list = new ArrayList<ra<T>>();
        final Iterator<ra<?>> iterator = IC.A.values().iterator();
    Label_0036:
        while (true) {
            Iterator<ra<?>> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final ra<?> ra;
                if (!(ra = iterator.next()).M()) {
                    iterator2 = iterator;
                }
                else {
                    if (clazz.isAssignableFrom(ra.A.getClass())) {
                        list.add((ra<T>)ra);
                        continue Label_0036;
                    }
                    continue Label_0036;
                }
            }
            break;
        }
        return list;
    }
    
    private static void M() {
        final StackTraceElement[] stackTrace;
        final int length = (stackTrace = Thread.currentThread().getStackTrace()).length;
        int n;
        int i = n = 0;
        while (i < length) {
            final StackTraceElement stackTraceElement = stackTrace[n];
            final Logger b = IC.B;
            ++n;
            b.warn(stackTraceElement.toString());
            i = n;
        }
    }
    
    @Override
    public Map<String, ra<?>> M() {
        return (Map<String, ra<?>>)ImmutableMap.copyOf((Map)IC.A);
    }
    
    @Override
    public void M(final BlockPos blockPos, final TileEntity tileEntity) {
        if (!WA.A(blockPos.getX() << 16, blockPos.getZ() << 16)) {
            IC.B.warn(new StringBuilder().insert(0, "API attempted to call saveTileEntity when saving TileEntities is not allowed!  Pos: ").append(blockPos).append(", te: ").append(tileEntity).append(".  StackTrace: ").toString());
            M();
            return;
        }
        uc.M(blockPos, tileEntity);
    }
    
    static {
        B = LogManager.getLogger();
        IC.A = new HashMap<String, ra<?>>();
        IC.b = false;
        IC.B.debug("Setting instance");
        final IC ic = new IC();
        final String s = "Qf`a`qFmud}ozmf";
        final IC ic2 = ic;
        final String s2 = "@{d{ofiy";
        Oa.M(ic);
        IC.B.debug("Loading default WDL extensions");
        ic2.M(FontHelper.M(s2), "0", new NC());
        ic2.M(FontHelper.M(s), "1.0", new lA());
    }
    
    @Override
    public void M(final String s, final String s2, final e e) {
        if (s == null) {
            throw new IllegalArgumentException(new StringBuilder().insert(0, "id must not be null!  (mod=").append(e).append(", version=").append(s2).append(")").toString());
        }
        if (s2 == null) {
            throw new IllegalArgumentException(new StringBuilder().insert(0, "version must not be null!  (mod=").append(e).append(", id=").append(s2).append(")").toString());
        }
        if (e == null) {
            throw new IllegalArgumentException(new StringBuilder().insert(0, "mod must not be null!  (id=").append(s).append(", version=").append(s2).append(")").toString());
        }
        final ra<Object> ra = new ra<Object>(s, s2, e);
        if (IC.A.containsKey(s)) {
            throw new IllegalArgumentException(new StringBuilder().insert(0, "A mod by the name of '").append(s).append("' is already registered by ").append(IC.A.get(s)).append(" (tried to register ").append(ra).append(" over it)").toString());
        }
        if (e.M(VersionUtils.M())) {
            IC.A.put(s, ra);
            if (e instanceof G) {
                final Map<String, a> m = ((G)e).M();
                final iB ib = new iB(ra);
                final Iterator<Map.Entry<String, a>> iterator2;
                Iterator<Map.Entry<String, a>> iterator = iterator2 = m.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<String, a> entry = iterator2.next();
                    Qd.M(entry.getKey(), entry.getValue(), ib);
                    iterator = iterator2;
                }
            }
            if (!IC.b && (e instanceof M || e instanceof c)) {
                this.M("LegacyEntitySupport", "1.0", new wC());
            }
            return;
        }
        final String i;
        if ((i = e.M(VersionUtils.M())) != null) {
            throw new IllegalArgumentException(i);
        }
        throw new IllegalArgumentException(new StringBuilder().insert(0, "Environment for ").append(ra).append(" is incorrect!  Perhaps it is for a different version of WDL?  You are running ").append(VersionUtils.M()).append(".").toString());
    }
}
