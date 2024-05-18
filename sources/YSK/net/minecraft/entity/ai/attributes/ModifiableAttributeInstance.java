package net.minecraft.entity.ai.attributes;

import java.util.*;
import com.google.common.collect.*;

public class ModifiableAttributeInstance implements IAttributeInstance
{
    private double baseValue;
    private static final String[] I;
    private boolean needsUpdate;
    private final Map<UUID, AttributeModifier> mapByUUID;
    private double cachedValue;
    private final Map<String, Set<AttributeModifier>> mapByName;
    private final Map<Integer, Set<AttributeModifier>> mapByOperation;
    private final BaseAttributeMap attributeMap;
    private final IAttribute genericAttribute;
    
    private Collection<AttributeModifier> func_180375_b(final int n) {
        final HashSet hashSet = Sets.newHashSet((Iterable)this.getModifiersByOperation(n));
        IAttribute attribute = this.genericAttribute.func_180372_d();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (attribute != null) {
            final IAttributeInstance attributeInstance = this.attributeMap.getAttributeInstance(attribute);
            if (attributeInstance != null) {
                hashSet.addAll(attributeInstance.getModifiersByOperation(n));
            }
            attribute = attribute.func_180372_d();
        }
        return (Collection<AttributeModifier>)hashSet;
    }
    
    @Override
    public Collection<AttributeModifier> getModifiersByOperation(final int n) {
        return this.mapByOperation.get(n);
    }
    
    @Override
    public double getBaseValue() {
        return this.baseValue;
    }
    
    @Override
    public void removeAllModifiers() {
        final Collection<AttributeModifier> func_111122_c = this.func_111122_c();
        if (func_111122_c != null) {
            final Iterator iterator = Lists.newArrayList((Iterable)func_111122_c).iterator();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.removeModifier(iterator.next());
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private double computeValue() {
        double baseValue = this.getBaseValue();
        final Iterator<AttributeModifier> iterator = this.func_180375_b("".length()).iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            baseValue += iterator.next().getAmount();
        }
        double n = baseValue;
        final Iterator<AttributeModifier> iterator2 = this.func_180375_b(" ".length()).iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            n += baseValue * iterator2.next().getAmount();
        }
        final Iterator<AttributeModifier> iterator3 = this.func_180375_b("  ".length()).iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator3.hasNext()) {
            n *= 1.0 + iterator3.next().getAmount();
        }
        return this.genericAttribute.clampValue(n);
    }
    
    protected void flagForUpdate() {
        this.needsUpdate = (" ".length() != 0);
        this.attributeMap.func_180794_a(this);
    }
    
    @Override
    public IAttribute getAttribute() {
        return this.genericAttribute;
    }
    
    public ModifiableAttributeInstance(final BaseAttributeMap attributeMap, final IAttribute genericAttribute) {
        this.mapByOperation = (Map<Integer, Set<AttributeModifier>>)Maps.newHashMap();
        this.mapByName = (Map<String, Set<AttributeModifier>>)Maps.newHashMap();
        this.mapByUUID = (Map<UUID, AttributeModifier>)Maps.newHashMap();
        this.needsUpdate = (" ".length() != 0);
        this.attributeMap = attributeMap;
        this.genericAttribute = genericAttribute;
        this.baseValue = genericAttribute.getDefaultValue();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < "   ".length()) {
            this.mapByOperation.put(i, Sets.newHashSet());
            ++i;
        }
    }
    
    @Override
    public AttributeModifier getModifier(final UUID uuid) {
        return this.mapByUUID.get(uuid);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(")\u0002>\u0006\u0000\r\b(O\u000f\u0017M;\u0003\u0014\u0001\f>\u0016F\u0005\u001d*\u0003\u000f\u0001\tz\u0000\bD\u00192\u0006\u0015D\f.\u001b\u0014\r\u000f/\u001b\u0003E", "dmZof");
    }
    
    @Override
    public boolean hasModifier(final AttributeModifier attributeModifier) {
        if (this.mapByUUID.get(attributeModifier.getID()) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void setBaseValue(final double baseValue) {
        if (baseValue != this.getBaseValue()) {
            this.baseValue = baseValue;
            this.flagForUpdate();
        }
    }
    
    @Override
    public void applyModifier(final AttributeModifier attributeModifier) {
        if (this.getModifier(attributeModifier.getID()) != null) {
            throw new IllegalArgumentException(ModifiableAttributeInstance.I["".length()]);
        }
        Set<AttributeModifier> hashSet = this.mapByName.get(attributeModifier.getName());
        if (hashSet == null) {
            hashSet = (Set<AttributeModifier>)Sets.newHashSet();
            this.mapByName.put(attributeModifier.getName(), hashSet);
        }
        this.mapByOperation.get(attributeModifier.getOperation()).add(attributeModifier);
        hashSet.add(attributeModifier);
        this.mapByUUID.put(attributeModifier.getID(), attributeModifier);
        this.flagForUpdate();
    }
    
    @Override
    public void removeModifier(final AttributeModifier attributeModifier) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < "   ".length()) {
            this.mapByOperation.get(i).remove(attributeModifier);
            ++i;
        }
        final Set<AttributeModifier> set = this.mapByName.get(attributeModifier.getName());
        if (set != null) {
            set.remove(attributeModifier);
            if (set.isEmpty()) {
                this.mapByName.remove(attributeModifier.getName());
            }
        }
        this.mapByUUID.remove(attributeModifier.getID());
        this.flagForUpdate();
    }
    
    static {
        I();
    }
    
    @Override
    public double getAttributeValue() {
        if (this.needsUpdate) {
            this.cachedValue = this.computeValue();
            this.needsUpdate = ("".length() != 0);
        }
        return this.cachedValue;
    }
    
    @Override
    public Collection<AttributeModifier> func_111122_c() {
        final HashSet hashSet = Sets.newHashSet();
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < "   ".length()) {
            hashSet.addAll(this.getModifiersByOperation(i));
            ++i;
        }
        return (Collection<AttributeModifier>)hashSet;
    }
}
