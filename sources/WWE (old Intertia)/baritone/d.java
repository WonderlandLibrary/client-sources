package baritone;

import baritone.api.behavior.*;
import baritone.api.utils.*;
import baritone.api.event.events.*;

public final class d extends b implements ILookBehavior
{
    public Rotation a;
    private boolean a;
    private float a;
    
    public d(final a a) {
        super(a);
    }
    
    @Override
    public final void updateTarget(final Rotation a, final boolean b) {
        this.a = a;
        this.a = !a.a().freeLook.value;
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (this.a == null) {
            return;
        }
        final boolean b = baritone.a.a().antiCheatCompatibility.value && !this.a;
        switch (e.a[playerUpdateEvent.getState().ordinal()]) {
            case 1: {
                if (this.a) {
                    this.a.player().v = this.a.getYaw();
                    final float w = this.a.player().w;
                    this.a.player().w = this.a.getPitch();
                    this.a = null;
                }
                if (b) {
                    this.a = this.a.player().v;
                    this.a.player().v = this.a.getYaw();
                    return;
                }
                break;
            }
            case 2: {
                if (b) {
                    this.a.player().v = this.a;
                    this.a = null;
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public final void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
        if (this.a != null) {
            rotationMoveEvent.setYaw(this.a.getYaw());
            if (!baritone.a.a().antiCheatCompatibility.value && rotationMoveEvent.getType() == RotationMoveEvent$Type.MOTION_UPDATE && !this.a) {
                this.a = null;
            }
        }
    }
}
