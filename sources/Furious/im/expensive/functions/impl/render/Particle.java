package im.expensive.functions.impl.render;

import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.ThreadLocalRandom;

public class Particle {
public Vector3d pos;
private Vector3d end;
long time;
private long collisionTime;
private Vector3d velocity;
float alpha;

public Particle(EntityTrails var1) {
    this.collisionTime = -1L;
    this.pos = IMinecraft.mc.player.getPositionVec().add((double)(-ThreadLocalRandom.current().nextFloat()), (double)ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F), (double)(-ThreadLocalRandom.current().nextFloat()));
    this.end = this.pos.add((double)(-ThreadLocalRandom.current().nextFloat(-1.0F, 1.0F)), (double)(-ThreadLocalRandom.current().nextFloat()), (double)(-ThreadLocalRandom.current().nextFloat()));
    this.time = System.currentTimeMillis();
    this.velocity = new Vector3d(ThreadLocalRandom.current().nextDouble(-0.01, 0.01), ThreadLocalRandom.current().nextDouble(0.01, 0.02), ThreadLocalRandom.current().nextDouble(-0.01, 0.01));
    this.alpha = 1.0F;
}

public void update() {
    if (this.collisionTime != -1L) {
        this.alpha = MathUtil.fast(this.alpha, 1.0F, 2.0F);
        this.pos = MathUtil.fast(this.pos, this.end, 0.2F);
        long timeSinceCollision = System.currentTimeMillis() - this.collisionTime;
        this.alpha = Math.max(0.0F, 1.0F - (float)timeSinceCollision / 7000.0F);
    }

    this.velocity = this.velocity.add(0.0, -1.0E-4, 0.0);
    Vector3d newPos = this.pos.add(this.velocity);
    BlockPos particlePos = new BlockPos(newPos);
    BlockState blockState = IMinecraft.mc.world.getBlockState(particlePos);
    if (!blockState.isAir()) {
        if (this.collisionTime == -1L) {
            this.collisionTime = System.currentTimeMillis();
        }

        if (!IMinecraft.mc.world.getBlockState(new BlockPos(this.pos.x + this.velocity.x, this.pos.y, this.pos.z)).isAir()) {
            this.velocity = new Vector3d(0.0, this.velocity.y, this.velocity.z);
        }

        if (!IMinecraft.mc.world.getBlockState(new BlockPos(this.pos.x, this.pos.y + this.velocity.y, this.pos.z)).isAir()) {
            this.velocity = new Vector3d(this.velocity.x, -this.velocity.y * 0.5, this.velocity.z);
        }

        if (!IMinecraft.mc.world.getBlockState(new BlockPos(this.pos.x, this.pos.y, this.pos.z + this.velocity.z)).isAir()) {
            this.velocity = new Vector3d(this.velocity.x, this.velocity.y, 0.0);
        }

        this.pos = this.pos.add(this.velocity);
    } else {
        this.pos = newPos;
    }

}
}
