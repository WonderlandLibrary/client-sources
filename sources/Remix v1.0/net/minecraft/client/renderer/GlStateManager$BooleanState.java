package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;

static class BooleanState
{
    private final int capability;
    private boolean currentState;
    
    public BooleanState(final int p_i46267_1_) {
        this.currentState = false;
        this.capability = p_i46267_1_;
    }
    
    public void setDisabled() {
        this.setState(false);
    }
    
    public void setEnabled() {
        this.setState(true);
    }
    
    public void setState(final boolean p_179199_1_) {
        if (p_179199_1_ != this.currentState) {
            this.currentState = p_179199_1_;
            if (p_179199_1_) {
                GL11.glEnable(this.capability);
            }
            else {
                GL11.glDisable(this.capability);
            }
        }
    }
}
