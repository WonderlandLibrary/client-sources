package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;

public static final class Map extends HashMap<String, InjectorGroupInfo>
{
    private static final long serialVersionUID = 1L;
    private static final InjectorGroupInfo NO_GROUP;
    
    public Map() {
        super();
    }
    
    @Override
    public InjectorGroupInfo get(final Object o) {
        return this.forName(o.toString());
    }
    
    public InjectorGroupInfo forName(final String s) {
        InjectorGroupInfo injectorGroupInfo = super.get(s);
        if (injectorGroupInfo == null) {
            injectorGroupInfo = new InjectorGroupInfo(s);
            this.put(s, injectorGroupInfo);
        }
        return injectorGroupInfo;
    }
    
    public InjectorGroupInfo parseGroup(final MethodNode methodNode, final String s) {
        return this.parseGroup(Annotations.getInvisible(methodNode, Group.class), s);
    }
    
    public InjectorGroupInfo parseGroup(final AnnotationNode annotationNode, final String s) {
        if (annotationNode == null) {
            return Map.NO_GROUP;
        }
        String s2 = Annotations.getValue(annotationNode, "name");
        if (s2 == null || s2.isEmpty()) {
            s2 = s;
        }
        final InjectorGroupInfo forName = this.forName(s2);
        final Integer n = Annotations.getValue(annotationNode, "min");
        if (n != null && n != -1) {
            forName.setMinRequired(n);
        }
        final Integer n2 = Annotations.getValue(annotationNode, "max");
        if (n2 != null && n2 != -1) {
            forName.setMaxAllowed(n2);
        }
        return forName;
    }
    
    public void validateAll() throws InjectionValidationException {
        final Iterator<InjectorGroupInfo> iterator = ((HashMap<K, InjectorGroupInfo>)this).values().iterator();
        while (iterator.hasNext()) {
            iterator.next().validate();
        }
    }
    
    @Override
    public Object get(final Object o) {
        return this.get(o);
    }
    
    static {
        NO_GROUP = new InjectorGroupInfo("NONE", true);
    }
}
