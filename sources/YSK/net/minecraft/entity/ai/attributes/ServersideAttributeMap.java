package net.minecraft.entity.ai.attributes;

import com.google.common.collect.*;
import net.minecraft.server.management.*;
import java.util.*;

public class ServersideAttributeMap extends BaseAttributeMap
{
    protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap;
    private final Set<IAttributeInstance> attributeInstanceSet;
    
    @Override
    public void func_180794_a(final IAttributeInstance attributeInstance) {
        if (attributeInstance.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(attributeInstance);
        }
        final Iterator<IAttribute> iterator = this.field_180377_c.get((Object)attributeInstance.getAttribute()).iterator();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModifiableAttributeInstance attributeInstance2 = this.getAttributeInstance((IAttribute)iterator.next());
            if (attributeInstance2 != null) {
                attributeInstance2.flagForUpdate();
            }
        }
    }
    
    @Override
    public IAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return this.getAttributeInstance(attribute);
    }
    
    @Override
    public IAttributeInstance getAttributeInstanceByName(final String s) {
        return this.getAttributeInstanceByName(s);
    }
    
    public ServersideAttributeMap() {
        this.attributeInstanceSet = (Set<IAttributeInstance>)Sets.newHashSet();
        this.descriptionToAttributeInstanceMap = new LowerStringMap<IAttributeInstance>();
    }
    
    @Override
    protected IAttributeInstance func_180376_c(final IAttribute attribute) {
        return new ModifiableAttributeInstance(this, attribute);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IAttributeInstance registerAttribute(final IAttribute attribute) {
        final IAttributeInstance registerAttribute = super.registerAttribute(attribute);
        if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), registerAttribute);
        }
        return registerAttribute;
    }
    
    public Collection<IAttributeInstance> getWatchedAttributes() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<IAttributeInstance> iterator = this.getAllAttributes().iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IAttributeInstance attributeInstance = iterator.next();
            if (attributeInstance.getAttribute().getShouldWatch()) {
                hashSet.add(attributeInstance);
            }
        }
        return (Collection<IAttributeInstance>)hashSet;
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
    }
    
    public Set<IAttributeInstance> getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstanceByName(final String s) {
        IAttributeInstance attributeInstanceByName = super.getAttributeInstanceByName(s);
        if (attributeInstanceByName == null) {
            attributeInstanceByName = this.descriptionToAttributeInstanceMap.get(s);
        }
        return (ModifiableAttributeInstance)attributeInstanceByName;
    }
}
