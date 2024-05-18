package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.collect.*;
import java.util.*;

public final class Extensions
{
    private final MixinTransformer transformer;
    private final List<IExtension> extensions;
    private final Map<Class<? extends IExtension>, IExtension> extensionMap;
    private final List<IClassGenerator> generators;
    private final List<IClassGenerator> generatorsView;
    private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap;
    private List<IExtension> activeExtensions;
    
    public Extensions(final MixinTransformer transformer) {
        super();
        this.extensions = new ArrayList<IExtension>();
        this.extensionMap = new HashMap<Class<? extends IExtension>, IExtension>();
        this.generators = new ArrayList<IClassGenerator>();
        this.generatorsView = Collections.unmodifiableList((List<? extends IClassGenerator>)this.generators);
        this.generatorMap = new HashMap<Class<? extends IClassGenerator>, IClassGenerator>();
        this.activeExtensions = Collections.emptyList();
        this.transformer = transformer;
    }
    
    public MixinTransformer getTransformer() {
        return this.transformer;
    }
    
    public void add(final IExtension extension) {
        this.extensions.add(extension);
        this.extensionMap.put(extension.getClass(), extension);
    }
    
    public List<IExtension> getExtensions() {
        return Collections.unmodifiableList((List<? extends IExtension>)this.extensions);
    }
    
    public List<IExtension> getActiveExtensions() {
        return this.activeExtensions;
    }
    
    public <T extends IExtension> T getExtension(final Class<? extends IExtension> clazz) {
        return lookup((Class<? extends T>)clazz, (Map<Class<? extends T>, T>)this.extensionMap, (List<T>)this.extensions);
    }
    
    public void select(final MixinEnvironment mixinEnvironment) {
        final ImmutableList.Builder builder = ImmutableList.builder();
        for (final IExtension element : this.extensions) {
            if (element.checkActive(mixinEnvironment)) {
                builder.add(element);
            }
        }
        this.activeExtensions = builder.build();
    }
    
    public void preApply(final ITargetClassContext targetClassContext) {
        final Iterator<IExtension> iterator = this.activeExtensions.iterator();
        while (iterator.hasNext()) {
            iterator.next().preApply(targetClassContext);
        }
    }
    
    public void postApply(final ITargetClassContext targetClassContext) {
        final Iterator<IExtension> iterator = this.activeExtensions.iterator();
        while (iterator.hasNext()) {
            iterator.next().postApply(targetClassContext);
        }
    }
    
    public void export(final MixinEnvironment mixinEnvironment, final String s, final boolean b, final byte[] array) {
        final Iterator<IExtension> iterator = this.activeExtensions.iterator();
        while (iterator.hasNext()) {
            iterator.next().export(mixinEnvironment, s, b, array);
        }
    }
    
    public void add(final IClassGenerator classGenerator) {
        this.generators.add(classGenerator);
        this.generatorMap.put(classGenerator.getClass(), classGenerator);
    }
    
    public List<IClassGenerator> getGenerators() {
        return this.generatorsView;
    }
    
    public <T extends IClassGenerator> T getGenerator(final Class<? extends IClassGenerator> clazz) {
        return lookup((Class<? extends T>)clazz, (Map<Class<? extends T>, T>)this.generatorMap, (List<T>)this.generators);
    }
    
    private static <T> T lookup(final Class<? extends T> clazz, final Map<Class<? extends T>, T> map, final List<T> list) {
        T value = map.get(clazz);
        if (value == null) {
            for (final T next : list) {
                if (clazz.isAssignableFrom(next.getClass())) {
                    value = next;
                    break;
                }
            }
            if (value == null) {
                throw new IllegalArgumentException("Extension for <" + clazz.getName() + "> could not be found");
            }
            map.put(clazz, value);
        }
        return value;
    }
}
