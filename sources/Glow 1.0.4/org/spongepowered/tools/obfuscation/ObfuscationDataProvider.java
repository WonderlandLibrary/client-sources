package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class ObfuscationDataProvider implements IObfuscationDataProvider
{
    private final IMixinAnnotationProcessor ap;
    private final List<ObfuscationEnvironment> environments;
    
    public ObfuscationDataProvider(final IMixinAnnotationProcessor ap, final List<ObfuscationEnvironment> environments) {
        super();
        this.ap = ap;
        this.environments = environments;
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntryRecursive(final MemberInfo memberInfo) {
        MemberInfo move = memberInfo;
        final ObfuscationData<String> obfClass = this.getObfClass(move.owner);
        ObfuscationData<Object> obfuscationData = this.getObfEntry(move);
        try {
            while (obfuscationData.isEmpty()) {
                final TypeHandle typeHandle = this.ap.getTypeProvider().getTypeHandle(move.owner);
                if (typeHandle == null) {
                    return (ObfuscationData<T>)obfuscationData;
                }
                final TypeHandle superclass = typeHandle.getSuperclass();
                obfuscationData = this.getObfEntryUsing(move, superclass);
                if (!obfuscationData.isEmpty()) {
                    return applyParents(obfClass, (ObfuscationData<T>)obfuscationData);
                }
                final Iterator<TypeHandle> iterator = typeHandle.getInterfaces().iterator();
                while (iterator.hasNext()) {
                    obfuscationData = this.getObfEntryUsing(move, iterator.next());
                    if (!obfuscationData.isEmpty()) {
                        return applyParents(obfClass, (ObfuscationData<T>)obfuscationData);
                    }
                }
                if (superclass == null) {
                    break;
                }
                move = move.move(superclass.getName());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return (ObfuscationData<T>)this.getObfEntry(memberInfo);
        }
        return (ObfuscationData<T>)obfuscationData;
    }
    
    private <T> ObfuscationData<T> getObfEntryUsing(final MemberInfo memberInfo, final TypeHandle typeHandle) {
        return (typeHandle == null) ? new ObfuscationData<T>() : this.getObfEntry(memberInfo.move(typeHandle.getName()));
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntry(final MemberInfo memberInfo) {
        if (memberInfo.isField()) {
            return (ObfuscationData<T>)this.getObfField(memberInfo);
        }
        return (ObfuscationData<T>)this.getObfMethod(memberInfo.asMethodMapping());
    }
    
    @Override
    public <T> ObfuscationData<T> getObfEntry(final IMapping<T> mapping) {
        if (mapping != null) {
            if (mapping.getType() == IMapping.Type.FIELD) {
                return (ObfuscationData<T>)this.getObfField((MappingField)mapping);
            }
            if (mapping.getType() == IMapping.Type.METHOD) {
                return (ObfuscationData<T>)this.getObfMethod((MappingMethod)mapping);
            }
        }
        return new ObfuscationData<T>();
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethodRecursive(final MemberInfo memberInfo) {
        return this.getObfEntryRecursive(memberInfo);
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethod(final MemberInfo memberInfo) {
        return this.getRemappedMethod(memberInfo, memberInfo.isConstructor());
    }
    
    @Override
    public ObfuscationData<MappingMethod> getRemappedMethod(final MemberInfo memberInfo) {
        return this.getRemappedMethod(memberInfo, true);
    }
    
    private ObfuscationData<MappingMethod> getRemappedMethod(final MemberInfo memberInfo, final boolean b) {
        final ObfuscationData<MappingMethod> obfuscationData = new ObfuscationData<MappingMethod>();
        for (final ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            final MappingMethod obfMethod = obfuscationEnvironment.getObfMethod(memberInfo);
            if (obfMethod != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfMethod);
            }
        }
        if (!obfuscationData.isEmpty() || !b) {
            return obfuscationData;
        }
        return this.remapDescriptor(obfuscationData, memberInfo);
    }
    
    @Override
    public ObfuscationData<MappingMethod> getObfMethod(final MappingMethod mappingMethod) {
        return this.getRemappedMethod(mappingMethod, mappingMethod.isConstructor());
    }
    
    @Override
    public ObfuscationData<MappingMethod> getRemappedMethod(final MappingMethod mappingMethod) {
        return this.getRemappedMethod(mappingMethod, true);
    }
    
    private ObfuscationData<MappingMethod> getRemappedMethod(final MappingMethod mappingMethod, final boolean b) {
        final ObfuscationData<MappingMethod> obfuscationData = new ObfuscationData<MappingMethod>();
        for (final ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            final MappingMethod obfMethod = obfuscationEnvironment.getObfMethod(mappingMethod);
            if (obfMethod != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfMethod);
            }
        }
        if (!obfuscationData.isEmpty() || !b) {
            return obfuscationData;
        }
        return this.remapDescriptor(obfuscationData, new MemberInfo(mappingMethod));
    }
    
    public ObfuscationData<MappingMethod> remapDescriptor(final ObfuscationData<MappingMethod> obfuscationData, final MemberInfo memberInfo) {
        for (final ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            final MemberInfo remapDescriptor = obfuscationEnvironment.remapDescriptor(memberInfo);
            if (remapDescriptor != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), remapDescriptor.asMethodMapping());
            }
        }
        return obfuscationData;
    }
    
    @Override
    public ObfuscationData<MappingField> getObfFieldRecursive(final MemberInfo memberInfo) {
        return this.getObfEntryRecursive(memberInfo);
    }
    
    @Override
    public ObfuscationData<MappingField> getObfField(final MemberInfo memberInfo) {
        return this.getObfField(memberInfo.asFieldMapping());
    }
    
    @Override
    public ObfuscationData<MappingField> getObfField(final MappingField mappingField) {
        final ObfuscationData<MappingField> obfuscationData = new ObfuscationData<MappingField>();
        for (final ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingField mappingField2 = obfuscationEnvironment.getObfField(mappingField);
            if (mappingField2 != null) {
                if (mappingField2.getDesc() == null && mappingField.getDesc() != null) {
                    mappingField2 = mappingField2.transform(obfuscationEnvironment.remapDescriptor(mappingField.getDesc()));
                }
                obfuscationData.put(obfuscationEnvironment.getType(), mappingField2);
            }
        }
        return obfuscationData;
    }
    
    @Override
    public ObfuscationData<String> getObfClass(final TypeHandle typeHandle) {
        return this.getObfClass(typeHandle.getName());
    }
    
    @Override
    public ObfuscationData<String> getObfClass(final String s) {
        final ObfuscationData<String> obfuscationData = new ObfuscationData<String>(s);
        for (final ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            final String obfClass = obfuscationEnvironment.getObfClass(s);
            if (obfClass != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfClass);
            }
        }
        return obfuscationData;
    }
    
    private static <T> ObfuscationData<T> applyParents(final ObfuscationData<String> obfuscationData, final ObfuscationData<T> obfuscationData2) {
        for (final ObfuscationType obfuscationType : obfuscationData2) {
            obfuscationData2.put(obfuscationType, MemberInfo.fromMapping(obfuscationData2.get(obfuscationType)).move(obfuscationData.get(obfuscationType)).asMapping());
        }
        return obfuscationData2;
    }
}
