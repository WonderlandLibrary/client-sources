package ClassSub;

public class Class274 extends Class284<Float>
{
    
    
    public Class274(final Float n, final Float n2) {
        super(n, n2);
    }
    
    public Class274 setYaw(final Float x) {
        ((Class310<Float>)this).setX(x);
        return this;
    }
    
    public Class274 setPitch(final Float y) {
        ((Class310<Float>)this).setY(y);
        return this;
    }
    
    public Float getYaw() {
        return this.getX().floatValue();
    }
    
    public Float getPitch() {
        return this.getY().floatValue();
    }
    
    public Class274 constrantAngle() {
        this.setYaw(this.getYaw() % 360.0f);
        this.setPitch(this.getPitch() % 360.0f);
        while (this.getYaw() <= -180.0f) {
            this.setYaw(this.getYaw() + 360.0f);
        }
        while (this.getPitch() <= -180.0f) {
            this.setPitch(this.getPitch() + 360.0f);
        }
        while (this.getYaw() > 180.0f) {
            this.setYaw(this.getYaw() - 360.0f);
        }
        while (this.getPitch() > 180.0f) {
            this.setPitch(this.getPitch() - 360.0f);
        }
        return this;
    }
}
