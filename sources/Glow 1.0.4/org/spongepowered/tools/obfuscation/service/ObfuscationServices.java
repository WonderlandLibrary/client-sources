package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.tools.*;
import org.spongepowered.tools.obfuscation.*;
import java.util.*;

public final class ObfuscationServices
{
    private static ObfuscationServices instance;
    private final ServiceLoader<IObfuscationService> serviceLoader;
    private final Set<IObfuscationService> services;
    
    private ObfuscationServices() {
        super();
        this.services = new HashSet<IObfuscationService>();
        this.serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
    }
    
    public static ObfuscationServices getInstance() {
        if (ObfuscationServices.instance == null) {
            ObfuscationServices.instance = new ObfuscationServices();
        }
        return ObfuscationServices.instance;
    }
    
    public void initProviders(final IMixinAnnotationProcessor mixinAnnotationProcessor) {
        try {
            for (final IObfuscationService obfuscationService : this.serviceLoader) {
                if (!this.services.contains(obfuscationService)) {
                    this.services.add(obfuscationService);
                    final String simpleName = obfuscationService.getClass().getSimpleName();
                    final Collection<ObfuscationTypeDescriptor> obfuscationTypes = obfuscationService.getObfuscationTypes();
                    if (obfuscationTypes == null) {
                        continue;
                    }
                    for (final ObfuscationTypeDescriptor obfuscationTypeDescriptor : obfuscationTypes) {
                        try {
                            mixinAnnotationProcessor.printMessage(Diagnostic.Kind.NOTE, simpleName + " supports type: \"" + ObfuscationType.create(obfuscationTypeDescriptor, mixinAnnotationProcessor) + "\"");
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (ServiceConfigurationError serviceConfigurationError) {
            mixinAnnotationProcessor.printMessage(Diagnostic.Kind.ERROR, serviceConfigurationError.getClass().getSimpleName() + ": " + serviceConfigurationError.getMessage());
            serviceConfigurationError.printStackTrace();
        }
    }
    
    public Set<String> getSupportedOptions() {
        final HashSet<Object> set = (HashSet<Object>)new HashSet<String>();
        final Iterator<IObfuscationService> iterator = this.serviceLoader.iterator();
        while (iterator.hasNext()) {
            final Set<String> supportedOptions = iterator.next().getSupportedOptions();
            if (supportedOptions != null) {
                set.addAll(supportedOptions);
            }
        }
        return (Set<String>)set;
    }
    
    public IObfuscationService getService(final Class<? extends IObfuscationService> clazz) {
        for (final IObfuscationService obfuscationService : this.serviceLoader) {
            if (clazz.getName().equals(obfuscationService.getClass().getName())) {
                return obfuscationService;
            }
        }
        return null;
    }
}
