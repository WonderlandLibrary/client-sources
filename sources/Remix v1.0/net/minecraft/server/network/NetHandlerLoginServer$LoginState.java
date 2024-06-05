package net.minecraft.server.network;

enum LoginState
{
    HELLO("HELLO", 0), 
    KEY("KEY", 1), 
    AUTHENTICATING("AUTHENTICATING", 2), 
    READY_TO_ACCEPT("READY_TO_ACCEPT", 3), 
    ACCEPTED("ACCEPTED", 4);
    
    private static final LoginState[] $VALUES;
    
    private LoginState(final String p_i45297_1_, final int p_i45297_2_) {
    }
    
    static {
        $VALUES = new LoginState[] { LoginState.HELLO, LoginState.KEY, LoginState.AUTHENTICATING, LoginState.READY_TO_ACCEPT, LoginState.ACCEPTED };
    }
}
