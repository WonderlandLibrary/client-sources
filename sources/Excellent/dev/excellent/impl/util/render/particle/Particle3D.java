package dev.excellent.impl.util.render.particle;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.player.BlockUtils;
import dev.excellent.impl.util.time.TimerUtil;
import lombok.Getter;
import net.minecraft.block.*;
import org.joml.Vector3d;

@Getter
public class Particle3D implements IAccess {
    private final int index;
    private final int color;
    private final TimerUtil time = TimerUtil.create();
    private final Animation animation = new Animation(Easing.LINEAR, 500);

    public final Vector3d position;
    private final Vector3d delta;

    public Particle3D(final Vector3d position, final int index, int color) {
        this.position = position;
        this.color = color;
        this.delta = new Vector3d((Math.random() * 0.5 - 0.25) * 0.01, (Math.random() * 0.25) * 0.01, (Math.random() * 0.5 - 0.25) * 0.01);
        this.index = index;
        this.time.reset();
    }

    public Particle3D(final Vector3d position, final Vector3d velocity, final int index, int color) {
        this.position = position;
        this.delta = new Vector3d(velocity.x * 0.01, velocity.y * 0.01, velocity.z * 0.01);
        this.index = index;
        this.color = color;
        this.time.reset();
    }

    public void update() {
        final Block block1 = BlockUtils.getBlock(this.position.x, this.position.y, this.position.z + this.delta.z);
        if (isValidBlock(block1))
            this.delta.z *= -0.8;

        final Block block2 = BlockUtils.getBlock(this.position.x, this.position.y + this.delta.y, this.position.z);
        if (isValidBlock(block2)) {
            this.delta.x *= 0.999F;
            this.delta.z *= 0.999F;

            this.delta.y *= -0.7;
        }

        final Block block3 = BlockUtils.getBlock(this.position.x + this.delta.x, this.position.y, this.position.z);
        if (isValidBlock(block3))
            this.delta.x *= -0.8;

        this.updateWithoutPhysics();
    }

    private boolean isValidBlock(Block block) {
        return !(block instanceof AirBlock)
                && !(block instanceof BushBlock)
                && !(block instanceof AbstractButtonBlock)
                && !(block instanceof TorchBlock)
                && !(block instanceof LeverBlock)
                && !(block instanceof AbstractPressurePlateBlock)
                && !(block instanceof CarpetBlock)
                && !(block instanceof FlowingFluidBlock);
    }

    public void updateWithoutPhysics() {
        this.position.x += this.delta.x;
        this.position.y += this.delta.y;
        this.position.z += this.delta.z;
        this.delta.x /= 0.999999F;
        this.delta.y -= 0.00005F;
        this.delta.z /= 0.999999F;
    }
}