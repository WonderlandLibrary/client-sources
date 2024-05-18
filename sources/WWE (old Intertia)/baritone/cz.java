package baritone;

import baritone.api.utils.input.*;

public class cz extends bub
{
    private final ct a;
    
    public cz(final ct a) {
        super();
        this.a = a;
    }
    
    public void a() {
        this.a = 0.0f;
        this.b = 0.0f;
        this.g = this.a.isInputForcedDown(Input.JUMP);
        final boolean inputForcedDown = this.a.isInputForcedDown(Input.MOVE_FORWARD);
        this.c = inputForcedDown;
        if (inputForcedDown) {
            ++this.b;
        }
        final boolean inputForcedDown2 = this.a.isInputForcedDown(Input.MOVE_BACK);
        this.d = inputForcedDown2;
        if (inputForcedDown2) {
            --this.b;
        }
        final boolean inputForcedDown3 = this.a.isInputForcedDown(Input.MOVE_LEFT);
        this.e = inputForcedDown3;
        if (inputForcedDown3) {
            ++this.a;
        }
        final boolean inputForcedDown4 = this.a.isInputForcedDown(Input.MOVE_RIGHT);
        this.f = inputForcedDown4;
        if (inputForcedDown4) {
            --this.a;
        }
        final boolean inputForcedDown5 = this.a.isInputForcedDown(Input.SNEAK);
        this.h = inputForcedDown5;
        if (inputForcedDown5) {
            this.a *= (float)0.3;
            this.b *= (float)0.3;
        }
    }
}
