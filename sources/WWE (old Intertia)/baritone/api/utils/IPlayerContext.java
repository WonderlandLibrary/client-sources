package baritone.api.utils;

import baritone.api.cache.*;
import java.util.*;

public interface IPlayerContext
{
    bud player();
    
    IPlayerController playerController();
    
    amu world();
    
    IWorldData worldData();
    
    bhc objectMouseOver();
    
    default BetterBlockPos playerFeet() {
        final BetterBlockPos betterBlockPos = new BetterBlockPos(this.player().p, this.player().q + 0.1251, this.player().r);
        if (this.world().o((et)betterBlockPos).u() instanceof arf) {
            return betterBlockPos.up();
        }
        return betterBlockPos;
    }
    
    default bhe playerFeetAsVec() {
        return new bhe(this.player().p, this.player().q, this.player().r);
    }
    
    default bhe playerHead() {
        return new bhe(this.player().p, this.player().q + this.player().by(), this.player().r);
    }
    
    default Rotation playerRotations() {
        return new Rotation(this.player().v, this.player().w);
    }
    
    default Optional<et> getSelectedBlock() {
        if (this.objectMouseOver() != null && this.objectMouseOver().a == bhc$a.b) {
            return Optional.of(this.objectMouseOver().a());
        }
        return Optional.empty();
    }
    
    default Optional<vg> getSelectedEntity() {
        if (this.objectMouseOver() != null && this.objectMouseOver().a == bhc$a.c) {
            return Optional.of(this.objectMouseOver().d);
        }
        return Optional.empty();
    }
}
