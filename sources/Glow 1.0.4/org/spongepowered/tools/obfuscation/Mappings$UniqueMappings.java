package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.*;

static class UniqueMappings implements IMappingConsumer
{
    private final IMappingConsumer mappings;
    private final Map<ObfuscationType, Map<MappingField, MappingField>> fields;
    private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods;
    
    public UniqueMappings(final IMappingConsumer mappings) {
        super();
        this.fields = new HashMap<ObfuscationType, Map<MappingField, MappingField>>();
        this.methods = new HashMap<ObfuscationType, Map<MappingMethod, MappingMethod>>();
        this.mappings = mappings;
    }
    
    @Override
    public void clear() {
        this.clearMaps();
        this.mappings.clear();
    }
    
    protected void clearMaps() {
        this.fields.clear();
        this.methods.clear();
    }
    
    @Override
    public void addFieldMapping(final ObfuscationType obfuscationType, final MappingField mappingField, final MappingField mappingField2) {
        if (!this.checkForExistingMapping(obfuscationType, mappingField, mappingField2, this.fields)) {
            this.mappings.addFieldMapping(obfuscationType, mappingField, mappingField2);
        }
    }
    
    @Override
    public void addMethodMapping(final ObfuscationType obfuscationType, final MappingMethod mappingMethod, final MappingMethod mappingMethod2) {
        if (!this.checkForExistingMapping(obfuscationType, mappingMethod, mappingMethod2, this.methods)) {
            this.mappings.addMethodMapping(obfuscationType, mappingMethod, mappingMethod2);
        }
    }
    
    private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(final ObfuscationType obfuscationType, final TMapping tMapping, final TMapping tMapping2, final Map<ObfuscationType, Map<TMapping, TMapping>> map) throws MappingConflictException {
        Map<TMapping, TMapping> map2 = map.get(obfuscationType);
        if (map2 == null) {
            map2 = new HashMap<TMapping, TMapping>();
            map.put(obfuscationType, map2);
        }
        final IMapping<TMapping> mapping = map2.get(tMapping);
        if (mapping == null) {
            map2.put(tMapping, tMapping2);
            return false;
        }
        if (mapping.equals(tMapping2)) {
            return true;
        }
        throw new MappingConflictException(mapping, tMapping2);
    }
    
    @Override
    public MappingSet<MappingField> getFieldMappings(final ObfuscationType obfuscationType) {
        return this.mappings.getFieldMappings(obfuscationType);
    }
    
    @Override
    public MappingSet<MappingMethod> getMethodMappings(final ObfuscationType obfuscationType) {
        return this.mappings.getMethodMappings(obfuscationType);
    }
}
