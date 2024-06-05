package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class BoundingBox implements MCUtil {
    private double blair$;
    private double counts$;
    private double scanning$;

    public BoundingBox(AxisAlignedBB axisAlignedBB) {
        this(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0);
    }

    public BoundingBox(double d, double d2, double d3) {
        this.scanning$ = d;
        this.counts$ = d2;
        this.blair$ = d3;
    }

    public BoundingBox(Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }

    public BoundingBox(BoundingBox blockPos) {
        this(blockPos._talented(), blockPos._adelaide(), blockPos._produce());
    }

    public String toString() {
        return "Location[" + this._involve() + "];";
    }

    public float _company(Entity entity) {
        return MathHelper.sqrt((float) (this.scanning$ - entity.posX) * (float) (this.scanning$ - entity.posX) + (float) (this.counts$ - entity.posY) * (float) (this.counts$ - entity.posY) + (float) (this.blair$ - entity.posZ) * (float) (this.blair$ - entity.posZ));
    }

    public double _talented() {
        return this.scanning$;
    }

    public double _adelaide() {
        return this.counts$;
    }

    public double _produce() {
        return this.blair$;
    }

    public BoundingBox _exclude() {
        return new BoundingBox(this);
    }

    public Block _maria() {
        return mc.world.getBlock((int) this.scanning$, (int) this.counts$, (int) this.blair$);
    }

    public BoundingBox offset(double d, double d2, double d3) {
        this.scanning$ += d;
        this.counts$ += d2;
        this.blair$ += d3;
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        return this._exclude();
    }

    public AxisAlignedBB _wrist() {
        return AxisAlignedBB.fromBounds(this.scanning$, this.counts$, this.blair$, this.scanning$ + 1.0, this.counts$ + 1.0, this.blair$ + 1.0);
    }

    public BoundingBox _blind(AxisAlignedBB axisAlignedBB) {
        return this.offset(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0);
    }

    public BoundingBox _glucose(Entity entity) {
        return this.offset(entity.posX, entity.posY, entity.posZ);
    }

    public String _involve() {
        return this.scanning$ + ", " + this.counts$ + ", " + this.blair$;
    }

    public BoundingBox _talking(BoundingBox blockPos) {
        return this.offset(blockPos._talented(), blockPos._adelaide(), blockPos._produce());
    }
}