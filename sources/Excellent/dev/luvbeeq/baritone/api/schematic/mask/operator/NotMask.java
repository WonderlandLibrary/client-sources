package dev.luvbeeq.baritone.api.schematic.mask.operator;

import dev.luvbeeq.baritone.api.schematic.mask.AbstractMask;
import dev.luvbeeq.baritone.api.schematic.mask.Mask;
import dev.luvbeeq.baritone.api.schematic.mask.StaticMask;
import net.minecraft.block.BlockState;

/**
 * @author Brady
 */
public final class NotMask extends AbstractMask {

    private final Mask source;

    public NotMask(Mask source) {
        super(source.widthX(), source.heightY(), source.lengthZ());
        this.source = source;
    }

    @Override
    public boolean partOfMask(int x, int y, int z, BlockState currentState) {
        return !this.source.partOfMask(x, y, z, currentState);
    }

    public static final class Static extends AbstractMask implements StaticMask {

        private final StaticMask source;

        public Static(StaticMask source) {
            super(source.widthX(), source.heightY(), source.lengthZ());
            this.source = source;
        }

        @Override
        public boolean partOfMask(int x, int y, int z) {
            return !this.source.partOfMask(x, y, z);
        }
    }
}
