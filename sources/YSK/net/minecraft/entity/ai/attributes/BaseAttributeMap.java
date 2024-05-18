package net.minecraft.entity.ai.attributes;

import java.util.*;
import net.minecraft.server.management.*;
import com.google.common.collect.*;

public abstract class BaseAttributeMap
{
    protected final Map<IAttribute, IAttributeInstance> attributes;
    private static final String[] I;
    protected final Map<String, IAttributeInstance> attributesByName;
    protected final Multimap<IAttribute, IAttribute> field_180377_c;
    
    public Collection<IAttributeInstance> getAllAttributes() {
        return this.attributesByName.values();
    }
    
    public IAttributeInstance registerAttribute(final IAttribute attribute) {
        if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName())) {
            throw new IllegalArgumentException(BaseAttributeMap.I["".length()]);
        }
        final IAttributeInstance func_180376_c = this.func_180376_c(attribute);
        this.attributesByName.put(attribute.getAttributeUnlocalizedName(), func_180376_c);
        this.attributes.put(attribute, func_180376_c);
        IAttribute attribute2 = attribute.func_180372_d();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (attribute2 != null) {
            this.field_180377_c.put((Object)attribute2, (Object)attribute);
            attribute2 = attribute2.func_180372_d();
        }
        return func_180376_c;
    }
    
    public void func_180794_a(final IAttributeInstance attributeInstance) {
    }
    
    public void applyAttributeModifiers(final Multimap<String, AttributeModifier> multimap) {
        final Iterator<Map.Entry<String, V>> iterator = multimap.entries().iterator();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, V> entry = iterator.next();
            final IAttributeInstance attributeInstanceByName = this.getAttributeInstanceByName(entry.getKey());
            if (attributeInstanceByName != null) {
                attributeInstanceByName.removeModifier((AttributeModifier)entry.getValue());
                attributeInstanceByName.applyModifier((AttributeModifier)entry.getValue());
            }
        }
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    protected abstract IAttributeInstance func_180376_c(final IAttribute p0);
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\b\u0016\u0017\u001d!+\u0017\u0017\nh \u0011C\u000e$;\u0007\u0002\u000b1i\u0010\u0006\b!:\u0016\u0006\u001d--C", "IbcoH");
    }
    
    public BaseAttributeMap() {
        this.attributes = (Map<IAttribute, IAttributeInstance>)Maps.newHashMap();
        this.attributesByName = new LowerStringMap<IAttributeInstance>();
        this.field_180377_c = (Multimap<IAttribute, IAttribute>)HashMultimap.create();
    }
    
    public IAttributeInstance getAttributeInstance(final IAttribute attribute) {
        return this.attributes.get(attribute);
    }
    
    public void removeAttributeModifiers(final Multimap<String, AttributeModifier> multimap) {
        final Iterator<Map.Entry<String, V>> iterator = multimap.entries().iterator();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, V> entry = iterator.next();
            final IAttributeInstance attributeInstanceByName = this.getAttributeInstanceByName(entry.getKey());
            if (attributeInstanceByName != null) {
                attributeInstanceByName.removeModifier((AttributeModifier)entry.getValue());
            }
        }
    }
    
    public IAttributeInstance getAttributeInstanceByName(final String s) {
        return this.attributesByName.get(s);
    }
}
