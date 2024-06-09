package com.client.glowclient;

import net.minecraft.entity.*;
import java.util.*;

public class wC implements f, j
{
    @Override
    public String k() {
        return null;
    }
    
    @Override
    public String k(final String s) {
        return null;
    }
    
    @Override
    public String M() {
        final StringBuilder sb;
        (sb = new StringBuilder()).append("One or more extensions is using the deprecated entity API (IEntityAdder or ISpecialEntityHandler).  This extension is automatically added to provide compatability.");
        sb.append("\nImplementations of ISpecialEntityHandler:");
        Iterator<ra<c>> iterator2;
        final Iterator<ra<c>> iterator = iterator2 = Oa.D(c.class).iterator();
        while (iterator2.hasNext()) {
            final ra<c> ra = iterator.next();
            iterator2 = iterator;
            sb.append('\n').append(ra);
        }
        sb.append("\nImplementations of IEntityAdder:");
        Iterator<ra<M>> iterator4;
        final Iterator<ra<M>> iterator3 = iterator4 = Oa.D(M.class).iterator();
        while (iterator4.hasNext()) {
            final ra<M> ra2 = iterator3.next();
            iterator4 = iterator3;
            sb.append('\n').append(ra2);
        }
        return sb.toString();
    }
    
    @Override
    public String D() {
        return "Legacy entity API support";
    }
    
    @Override
    public String A(final String s) {
        final Iterator<ra<c>> iterator = Oa.M(c.class).iterator();
        while (iterator.hasNext()) {
            final ra<c> ra;
            if ((ra = iterator.next()).A.M().containsKey((Object)s)) {
                return ra.A.D(s);
            }
        }
        final Iterator<ra<M>> iterator2 = Oa.M(M.class).iterator();
        while (iterator2.hasNext()) {
            final ra<M> ra2;
            if ((ra2 = iterator2.next()).A.M().contains(s)) {
                return ra2.A.D(s);
            }
        }
        return null;
    }
    
    @Override
    public String[] M() {
        return null;
    }
    
    @Override
    public int M(final String s, final Entity entity) {
        final Iterator<ra<c>> iterator = Oa.M(c.class).iterator();
        while (iterator.hasNext()) {
            final ra<c> ra;
            if ((ra = iterator.next()).A.M().containsKey((Object)s)) {
                return ra.A.M(s);
            }
        }
        final Iterator<ra<M>> iterator2 = Oa.M(M.class).iterator();
        while (iterator2.hasNext()) {
            final ra<M> ra2;
            if ((ra2 = iterator2.next()).A.M().contains(s)) {
                return ra2.A.M(s);
            }
        }
        return -1;
    }
    
    @Override
    public String A() {
        return null;
    }
    
    @Override
    public String M(final Entity entity) {
        final String entityString = EntityList.getEntityString(entity);
        final Iterator<ra<c>> iterator = Oa.M(c.class).iterator();
        while (iterator.hasNext()) {
            final ra<c> ra;
            if ((ra = iterator.next()).A.M().containsKey((Object)entityString)) {
                return ra.A.M(entity);
            }
        }
        final Iterator<ra<M>> iterator2 = Oa.M(M.class).iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().A.M().contains(entityString)) {
                return entityString;
            }
        }
        return null;
    }
    
    @Override
    public String M(final String s) {
        return null;
    }
    
    @Override
    public String D(final String s) {
        return null;
    }
    
    @Override
    public boolean D(final String s) {
        return true;
    }
    
    public wC() {
        super();
    }
    
    @Override
    public Set<String> M() {
        final HashSet<Object> set = (HashSet<Object>)new HashSet<String>();
        final Iterator<ra<c>> iterator2;
        Iterator<ra<c>> iterator = iterator2 = Oa.M(c.class).iterator();
        while (iterator.hasNext()) {
            set.addAll(iterator2.next().A.M().values());
            iterator = iterator2;
        }
        final Iterator<ra> iterator4;
        Iterator<ra> iterator3 = iterator4 = (Iterator<ra>)Oa.M(M.class).iterator();
        while (iterator3.hasNext()) {
            set.addAll(((M)iterator4.next().A).M());
            iterator3 = iterator4;
        }
        return (Set<String>)set;
    }
    
    @Override
    public boolean M(final String s) {
        return true;
    }
}
