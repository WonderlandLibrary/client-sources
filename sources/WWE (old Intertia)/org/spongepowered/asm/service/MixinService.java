package org.spongepowered.asm.service;

import java.util.*;
import org.apache.logging.log4j.*;

public final class MixinService
{
    private static final Logger logger;
    private static MixinService instance;
    private ServiceLoader<IMixinServiceBootstrap> bootstrapServiceLoader;
    private final Set<String> bootedServices;
    private ServiceLoader<IMixinService> serviceLoader;
    private IMixinService service;
    
    private MixinService() {
        super();
        this.bootedServices = new HashSet<String>();
        this.service = null;
        this.runBootServices();
    }
    
    private void runBootServices() {
        this.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, this.getClass().getClassLoader());
        for (final IMixinServiceBootstrap mixinServiceBootstrap : this.bootstrapServiceLoader) {
            try {
                mixinServiceBootstrap.bootstrap();
                this.bootedServices.add(mixinServiceBootstrap.getServiceClassName());
            }
            catch (Throwable t) {
                MixinService.logger.catching(t);
            }
        }
    }
    
    private static MixinService getInstance() {
        if (MixinService.instance == null) {
            MixinService.instance = new MixinService();
        }
        return MixinService.instance;
    }
    
    public static void boot() {
        getInstance();
    }
    
    public static IMixinService getService() {
        return getInstance().getServiceInstance();
    }
    
    private synchronized IMixinService getServiceInstance() {
        if (this.service == null) {
            this.service = this.initService();
            if (this.service == null) {
                throw new ServiceNotAvailableError("No mixin host service is available");
            }
        }
        return this.service;
    }
    
    private IMixinService initService() {
        this.serviceLoader = ServiceLoader.load(IMixinService.class, this.getClass().getClassLoader());
        final Iterator<IMixinService> iterator = this.serviceLoader.iterator();
        while (iterator.hasNext()) {
            try {
                final IMixinService mixinService = iterator.next();
                if (this.bootedServices.contains(mixinService.getClass().getName())) {
                    MixinService.logger.debug("MixinService [{}] was successfully booted in {}", new Object[] { mixinService.getName(), this.getClass().getClassLoader() });
                }
                if (mixinService.isValid()) {
                    return mixinService;
                }
                continue;
            }
            catch (ServiceConfigurationError serviceConfigurationError) {
                serviceConfigurationError.printStackTrace();
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
