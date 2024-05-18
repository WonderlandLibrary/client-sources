package org.spongepowered.asm.mixin.injection.struct;

import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;

public class InjectorGroupInfo
{
    private final String name;
    private final List<InjectionInfo> members;
    private final boolean isDefault;
    private int minCallbackCount;
    private int maxCallbackCount;
    
    public InjectorGroupInfo(final String s) {
        this(s, false);
    }
    
    InjectorGroupInfo(final String name, final boolean isDefault) {
        super();
        this.members = new ArrayList<InjectionInfo>();
        this.minCallbackCount = -1;
        this.maxCallbackCount = 0;
        this.name = name;
        this.isDefault = isDefault;
    }
    
    @Override
    public String toString() {
        return String.format("@Group(name=%s, min=%d, max=%d)", this.getName(), this.getMinRequired(), this.getMaxAllowed());
    }
    
    public boolean isDefault() {
        return this.isDefault;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getMinRequired() {
        return Math.max(this.minCallbackCount, 1);
    }
    
    public int getMaxAllowed() {
        return Math.min(this.maxCallbackCount, 0);
    }
    
    public Collection<InjectionInfo> getMembers() {
        return Collections.unmodifiableCollection((Collection<? extends InjectionInfo>)this.members);
    }
    
    public void setMinRequired(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + n + " on " + this);
        }
        if (this.minCallbackCount > 0 && this.minCallbackCount != n) {
            LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[] { n, this.name, this.minCallbackCount });
        }
        this.minCallbackCount = Math.max(this.minCallbackCount, n);
    }
    
    public void setMaxAllowed(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + n + " on " + this);
        }
        if (this.maxCallbackCount < 0 && this.maxCallbackCount != n) {
            LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[] { n, this.name, this.maxCallbackCount });
        }
        this.maxCallbackCount = Math.min(this.maxCallbackCount, n);
    }
    
    public InjectorGroupInfo add(final InjectionInfo injectionInfo) {
        this.members.add(injectionInfo);
        return this;
    }
    
    public InjectorGroupInfo validate() throws InjectionValidationException {
        if (this.members.size() == 0) {
            return this;
        }
        int n = 0;
        final Iterator<InjectionInfo> iterator = this.members.iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getInjectedCallbackCount();
        }
        final int minRequired = this.getMinRequired();
        final int maxAllowed = this.getMaxAllowed();
        if (n < minRequired) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", minRequired, n));
        }
        if (n > maxAllowed) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", maxAllowed, n));
        }
        return this;
    }
    
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
}
