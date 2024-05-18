package org.spongepowered.asm.mixin;

import org.spongepowered.asm.mixin.extensibility.*;

static class TokenProviderWrapper implements Comparable<TokenProviderWrapper>
{
    private static int nextOrder;
    private final int priority;
    private final int order;
    private final IEnvironmentTokenProvider provider;
    private final MixinEnvironment environment;
    
    public TokenProviderWrapper(final IEnvironmentTokenProvider provider, final MixinEnvironment environment) {
        super();
        this.provider = provider;
        this.environment = environment;
        this.order = TokenProviderWrapper.nextOrder++;
        this.priority = provider.getPriority();
    }
    
    @Override
    public int compareTo(final TokenProviderWrapper tokenProviderWrapper) {
        if (tokenProviderWrapper == null) {
            return 0;
        }
        if (tokenProviderWrapper.priority == this.priority) {
            return tokenProviderWrapper.order - this.order;
        }
        return tokenProviderWrapper.priority - this.priority;
    }
    
    public IEnvironmentTokenProvider getProvider() {
        return this.provider;
    }
    
    Integer getToken(final String s) {
        return this.provider.getToken(s, this.environment);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((TokenProviderWrapper)o);
    }
    
    static {
        TokenProviderWrapper.nextOrder = 0;
    }
}
