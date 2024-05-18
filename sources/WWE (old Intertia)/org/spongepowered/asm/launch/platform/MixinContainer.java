package org.spongepowered.asm.launch.platform;

import java.net.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.util.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.service.*;

public class MixinContainer
{
    private static final List<String> agentClasses;
    private final Logger logger;
    private final URI uri;
    private final List<IMixinPlatformAgent> agents;
    
    public MixinContainer(final MixinPlatformManager mixinPlatformManager, final URI uri) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.agents = new ArrayList<IMixinPlatformAgent>();
        this.uri = uri;
        for (final String s : MixinContainer.agentClasses) {
            try {
                final Class<?> forName = Class.forName(s);
                final Constructor<?> declaredConstructor = forName.getDeclaredConstructor(MixinPlatformManager.class, URI.class);
                this.logger.debug("Instancing new {} for {}", new Object[] { forName.getSimpleName(), this.uri });
                this.agents.add((IMixinPlatformAgent)declaredConstructor.newInstance(mixinPlatformManager, uri));
            }
            catch (Exception ex) {
                this.logger.catching((Throwable)ex);
            }
        }
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    public Collection<String> getPhaseProviders() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<IMixinPlatformAgent> iterator = this.agents.iterator();
        while (iterator.hasNext()) {
            final String phaseProvider = iterator.next().getPhaseProvider();
            if (phaseProvider != null) {
                list.add(phaseProvider);
            }
        }
        return list;
    }
    
    public void prepare() {
        for (final IMixinPlatformAgent mixinPlatformAgent : this.agents) {
            this.logger.debug("Processing prepare() for {}", new Object[] { mixinPlatformAgent });
            mixinPlatformAgent.prepare();
        }
    }
    
    public void initPrimaryContainer() {
        for (final IMixinPlatformAgent mixinPlatformAgent : this.agents) {
            this.logger.debug("Processing launch tasks for {}", new Object[] { mixinPlatformAgent });
            mixinPlatformAgent.initPrimaryContainer();
        }
    }
    
    public void inject() {
        for (final IMixinPlatformAgent mixinPlatformAgent : this.agents) {
            this.logger.debug("Processing inject() for {}", new Object[] { mixinPlatformAgent });
            mixinPlatformAgent.inject();
        }
    }
    
    public String getLaunchTarget() {
        final Iterator<IMixinPlatformAgent> iterator = this.agents.iterator();
        while (iterator.hasNext()) {
            final String launchTarget = iterator.next().getLaunchTarget();
            if (launchTarget != null) {
                return launchTarget;
            }
        }
        return null;
    }
    
    static {
        GlobalProperties.put("mixin.agents", agentClasses = new ArrayList<String>());
        final Iterator<String> iterator = MixinService.getService().getPlatformAgents().iterator();
        while (iterator.hasNext()) {
            MixinContainer.agentClasses.add(iterator.next());
        }
        MixinContainer.agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
    }
}
