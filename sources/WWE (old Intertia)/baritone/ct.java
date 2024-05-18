package baritone;

import baritone.api.utils.*;
import baritone.api.utils.input.*;
import java.util.*;
import baritone.api.event.events.*;
import baritone.api.*;

public final class ct extends b implements IInputOverrideHandler
{
    private final Map<Input, Boolean> a;
    public final cl a;
    private final cm a;
    
    public ct(final a a) {
        super(a);
        this.a = new HashMap<Input, Boolean>();
        this.a = new cl(a.getPlayerContext());
        this.a = new cm(a.getPlayerContext());
    }
    
    @Override
    public final boolean isInputForcedDown(final Input input) {
        return input != null && this.a.getOrDefault(input, Boolean.FALSE);
    }
    
    @Override
    public final void setInputForceState(final Input input, final boolean b) {
        this.a.put(input, b);
    }
    
    @Override
    public final void clearAllKeys() {
        this.a.clear();
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            return;
        }
        if (this.isInputForcedDown(Input.CLICK_LEFT)) {
            this.setInputForceState(Input.CLICK_RIGHT, false);
        }
        final cl a = this.a;
        final boolean inputForcedDown = this.isInputForcedDown(Input.CLICK_LEFT);
        final cl cl = a;
        final bhc objectMouseOver;
        final boolean b = (objectMouseOver = a.a.objectMouseOver()) != null && objectMouseOver.a == bhc$a.b;
        if (inputForcedDown) {}
        if (cl.a) {
            cl.a();
            cl.a = false;
        }
        final cm a2 = this.a;
        final boolean inputForcedDown2 = this.isInputForcedDown(Input.CLICK_RIGHT);
        final cm cm = a2;
        if (a2.a > 0) {
            final cm cm2 = cm;
            --cm2.a;
        }
        else {
            final bhc objectMouseOver2 = cm.a.objectMouseOver();
            if (inputForcedDown2 && !cm.a.player().L() && objectMouseOver2 != null && objectMouseOver2.a() != null && objectMouseOver2.a == bhc$a.b) {
                cm.a = baritone.a.a().rightClickSpeed.value;
                ub[] values;
                for (int length = (values = ub.values()).length, i = 0; i < length; ++i) {
                    final ub ub = values[i];
                    if (cm.a.playerController().processRightClickBlock(cm.a.player(), cm.a.world(), objectMouseOver2.a(), objectMouseOver2.b, objectMouseOver2.c, ub) == ud.a) {
                        cm.a.player().a(ub);
                        break;
                    }
                    if (!cm.a.player().b(ub).b() && cm.a.playerController().processRightClick(cm.a.player(), cm.a.world(), ub) == ud.a) {
                        break;
                    }
                }
            }
        }
        if (this.a.a.isPathing() || this.a != BaritoneAPI.getProvider().getPrimaryBaritone()) {
            if (this.a.player().e.getClass() != cz.class) {
                this.a.player().e = new cz(this);
            }
        }
        else if (this.a.player().e.getClass() == cz.class) {
            this.a.player().e = (bub)new buc(bib.z().t);
        }
    }
}
