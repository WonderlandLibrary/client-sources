package waveycapes.utils;

public final class Quaternion
{
    public static final Quaternion ONE;
    private float i;
    private float j;
    private float k;
    private float r;
    
    public Quaternion(final float f, final float g, final float h, final float i) {
        super();
        this.i = f;
        this.j = g;
        this.k = h;
        this.r = i;
    }
    
    public Quaternion(final Vector3f vector3f, float f, final boolean bl) {
        super();
        if (bl) {
            f *= 0.017453292f;
        }
        final float g = sin(f / 2.0f);
        this.i = vector3f.x() * g;
        this.j = vector3f.y() * g;
        this.k = vector3f.z() * g;
        this.r = cos(f / 2.0f);
    }
    
    public Quaternion(float f, float g, float h, final boolean bl) {
        super();
        if (bl) {
            f *= 0.017453292f;
            g *= 0.017453292f;
            h *= 0.017453292f;
        }
        final float i = sin(0.5f * f);
        final float j = cos(0.5f * f);
        final float k = sin(0.5f * g);
        final float l = cos(0.5f * g);
        final float m = sin(0.5f * h);
        final float n = cos(0.5f * h);
        this.i = i * l * n + j * k * m;
        this.j = j * k * n - i * l * m;
        this.k = i * k * n + j * l * m;
        this.r = j * l * n - i * k * m;
    }
    
    public Quaternion(final Quaternion quaternion) {
        super();
        this.i = quaternion.i;
        this.j = quaternion.j;
        this.k = quaternion.k;
        this.r = quaternion.r;
    }
    
    public static Quaternion fromYXZ(final float f, final float g, final float h) {
        final Quaternion quaternion = Quaternion.ONE.copy();
        quaternion.mul(new Quaternion(0.0f, (float)Math.sin(f / 2.0f), 0.0f, (float)Math.cos(f / 2.0f)));
        quaternion.mul(new Quaternion((float)Math.sin(g / 2.0f), 0.0f, 0.0f, (float)Math.cos(g / 2.0f)));
        quaternion.mul(new Quaternion(0.0f, 0.0f, (float)Math.sin(h / 2.0f), (float)Math.cos(h / 2.0f)));
        return quaternion;
    }
    
    public static Quaternion fromXYZDegrees(final Vector3f vector3f) {
        return fromXYZ((float)Math.toRadians(vector3f.x()), (float)Math.toRadians(vector3f.y()), (float)Math.toRadians(vector3f.z()));
    }
    
    public static Quaternion fromXYZ(final Vector3f vector3f) {
        return fromXYZ(vector3f.x(), vector3f.y(), vector3f.z());
    }
    
    public static Quaternion fromXYZ(final float f, final float g, final float h) {
        final Quaternion quaternion = Quaternion.ONE.copy();
        quaternion.mul(new Quaternion((float)Math.sin(f / 2.0f), 0.0f, 0.0f, (float)Math.cos(f / 2.0f)));
        quaternion.mul(new Quaternion(0.0f, (float)Math.sin(g / 2.0f), 0.0f, (float)Math.cos(g / 2.0f)));
        quaternion.mul(new Quaternion(0.0f, 0.0f, (float)Math.sin(h / 2.0f), (float)Math.cos(h / 2.0f)));
        return quaternion;
    }
    
    public Vector3f toXYZ() {
        final float f = this.r() * this.r();
        final float g = this.i() * this.i();
        final float h = this.j() * this.j();
        final float i = this.k() * this.k();
        final float j = f + g + h + i;
        final float k = 2.0f * this.r() * this.i() - 2.0f * this.j() * this.k();
        final float l = (float)Math.asin(k / j);
        if (Math.abs(k) > 0.999f * j) {
            return new Vector3f(2.0f * (float)Math.atan2(this.i(), this.r()), l, 0.0f);
        }
        return new Vector3f((float)Math.atan2(2.0f * this.j() * this.k() + 2.0f * this.i() * this.r(), f - g - h + i), l, (float)Math.atan2(2.0f * this.i() * this.j() + 2.0f * this.r() * this.k(), f + g - h - i));
    }
    
    public Vector3f toXYZDegrees() {
        final Vector3f vector3f = this.toXYZ();
        return new Vector3f((float)Math.toDegrees(vector3f.x()), (float)Math.toDegrees(vector3f.y()), (float)Math.toDegrees(vector3f.z()));
    }
    
    public Vector3f toYXZ() {
        final float f = this.r() * this.r();
        final float g = this.i() * this.i();
        final float h = this.j() * this.j();
        final float i = this.k() * this.k();
        final float j = f + g + h + i;
        final float k = 2.0f * this.r() * this.i() - 2.0f * this.j() * this.k();
        final float l = (float)Math.asin(k / j);
        if (Math.abs(k) > 0.999f * j) {
            return new Vector3f(l, 2.0f * (float)Math.atan2(this.j(), this.r()), 0.0f);
        }
        return new Vector3f(l, (float)Math.atan2(2.0f * this.i() * this.k() + 2.0f * this.j() * this.r(), f - g - h + i), (float)Math.atan2(2.0f * this.i() * this.j() + 2.0f * this.r() * this.k(), f - g + h - i));
    }
    
    public Vector3f toYXZDegrees() {
        final Vector3f vector3f = this.toYXZ();
        return new Vector3f((float)Math.toDegrees(vector3f.x()), (float)Math.toDegrees(vector3f.y()), (float)Math.toDegrees(vector3f.z()));
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final Quaternion quaternion = (Quaternion)object;
        return Float.compare(quaternion.i, this.i) == 0 && Float.compare(quaternion.j, this.j) == 0 && Float.compare(quaternion.k, this.k) == 0 && Float.compare(quaternion.r, this.r) == 0;
    }
    
    @Override
    public int hashCode() {
        int i = Float.floatToIntBits(this.i);
        i = 31 * i + Float.floatToIntBits(this.j);
        i = 31 * i + Float.floatToIntBits(this.k);
        i = 31 * i + Float.floatToIntBits(this.r);
        return i;
    }
    
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Quaternion[").append(this.r()).append(" + ");
        stringBuilder.append(this.i()).append("i + ");
        stringBuilder.append(this.j()).append("j + ");
        stringBuilder.append(this.k()).append("k]");
        return stringBuilder.toString();
    }
    
    public float i() {
        return this.i;
    }
    
    public float j() {
        return this.j;
    }
    
    public float k() {
        return this.k;
    }
    
    public float r() {
        return this.r;
    }
    
    public void mul(final Quaternion quaternion) {
        final float f = this.i();
        final float g = this.j();
        final float h = this.k();
        final float i = this.r();
        final float j = quaternion.i();
        final float k = quaternion.j();
        final float l = quaternion.k();
        final float m = quaternion.r();
        this.i = i * j + f * m + g * l - h * k;
        this.j = i * k - f * l + g * m + h * j;
        this.k = i * l + f * k - g * j + h * m;
        this.r = i * m - f * j - g * k - h * l;
    }
    
    public void mul(final float f) {
        this.i *= f;
        this.j *= f;
        this.k *= f;
        this.r *= f;
    }
    
    public void conj() {
        this.i = -this.i;
        this.j = -this.j;
        this.k = -this.k;
    }
    
    public void set(final float f, final float g, final float h, final float i) {
        this.i = f;
        this.j = g;
        this.k = h;
        this.r = i;
    }
    
    private static float cos(final float f) {
        return (float)Math.cos(f);
    }
    
    private static float sin(final float f) {
        return (float)Math.sin(f);
    }
    
    public void normalize() {
        final float f = this.i() * this.i() + this.j() * this.j() + this.k() * this.k() + this.r() * this.r();
        if (f > 1.0E-6f) {
            final float g = Mth.fastInvSqrt(f);
            this.i *= g;
            this.j *= g;
            this.k *= g;
            this.r *= g;
        }
        else {
            this.i = 0.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.r = 0.0f;
        }
    }
    
    public void slerp(final Quaternion quaternion, final float f) {
        throw new UnsupportedOperationException();
    }
    
    public Quaternion copy() {
        return new Quaternion(this);
    }
    
    static {
        ONE = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    }
}
