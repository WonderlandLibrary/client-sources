package waveycapes.utils;

import net.minecraft.util.*;

public final class Vector3f {
	public static Vector3f XN;
	public static Vector3f XP;
	public static Vector3f YN;
	public static Vector3f YP;
	public static Vector3f ZN;
	public static Vector3f ZP;
	public static Vector3f ZERO;
	private float x;
	private float y;
	private float z;

	public Vector3f() {
		super();
	}

	public Vector3f(final float f, final float g, final float h) {
		super();
		this.x = f;
		this.y = g;
		this.z = h;
	}

	public Vector3f(final Vector4f vector4f) {
		this(vector4f.x(), vector4f.y(), vector4f.z());
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		final Vector3f vector3f = (Vector3f) object;
		return Float.compare(vector3f.x, this.x) == 0 && Float.compare(vector3f.y, this.y) == 0
				&& Float.compare(vector3f.z, this.z) == 0;
	}

	@Override
	public int hashCode() {
		int i = Float.floatToIntBits(this.x);
		i = 31 * i + Float.floatToIntBits(this.y);
		i = 31 * i + Float.floatToIntBits(this.z);
		return i;
	}

	public float x() {
		return this.x;
	}

	public float y() {
		return this.y;
	}

	public float z() {
		return this.z;
	}

	public void mul(final float f) {
		this.x *= f;
		this.y *= f;
		this.z *= f;
	}

	public void mul(final float f, final float g, final float h) {
		this.x *= f;
		this.y *= g;
		this.z *= h;
	}

	public void clamp(final Vector3f vector3f, final Vector3f vector3f2) {
		this.x = MathHelper.clamp_float(this.x, vector3f.x(), vector3f2.x());
		this.y = MathHelper.clamp_float(this.y, vector3f.x(), vector3f2.y());
		this.z = MathHelper.clamp_float(this.z, vector3f.z(), vector3f2.z());
	}

	public void clamp(final float f, final float g) {
		this.x = MathHelper.clamp_float(this.x, f, g);
		this.y = MathHelper.clamp_float(this.y, f, g);
		this.z = MathHelper.clamp_float(this.z, f, g);
	}

	public void set(final float f, final float g, final float h) {
		this.x = f;
		this.y = g;
		this.z = h;
	}

	public void load(final Vector3f vector3f) {
		this.x = vector3f.x;
		this.y = vector3f.y;
		this.z = vector3f.z;
	}

	public void add(final float f, final float g, final float h) {
		this.x += f;
		this.y += g;
		this.z += h;
	}

	public void add(final Vector3f vector3f) {
		this.x += vector3f.x;
		this.y += vector3f.y;
		this.z += vector3f.z;
	}

	public void sub(final Vector3f vector3f) {
		this.x -= vector3f.x;
		this.y -= vector3f.y;
		this.z -= vector3f.z;
	}

	public float dot(final Vector3f vector3f) {
		return this.x * vector3f.x + this.y * vector3f.y + this.z * vector3f.z;
	}

	public boolean normalize() {
		final float f = this.x * this.x + this.y * this.y + this.z * this.z;
		if (f < 1.0E-5) {
			return false;
		}
		final float g = Mth.fastInvSqrt(f);
		this.x *= g;
		this.y *= g;
		this.z *= g;
		return true;
	}

	public void cross(final Vector3f vector3f) {
		final float f = this.x;
		final float g = this.y;
		final float h = this.z;
		final float i = vector3f.x();
		final float j = vector3f.y();
		final float k = vector3f.z();
		this.x = g * k - h * j;
		this.y = h * i - f * k;
		this.z = f * j - g * i;
	}

	public void transform(final Quaternion quaternion) {
		final Quaternion quaternion2 = new Quaternion(quaternion);
		quaternion2.mul(new Quaternion(this.x(), this.y(), this.z(), 0.0f));
		final Quaternion quaternion3 = new Quaternion(quaternion);
		quaternion3.conj();
		quaternion2.mul(quaternion3);
		this.set(quaternion2.i(), quaternion2.j(), quaternion2.k());
	}

	public void lerp(final Vector3f vector3f, final float f) {
		final float g = 1.0f - f;
		this.x = this.x * g + vector3f.x * f;
		this.y = this.y * g + vector3f.y * f;
		this.z = this.z * g + vector3f.z * f;
	}

	public Quaternion rotation(final float f) {
		return new Quaternion(this, f, false);
	}

	public Quaternion rotationDegrees(final float f) {
		return new Quaternion(this, f, true);
	}

	public Vector3f copy() {
		return new Vector3f(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + ", " + this.z + "]";
	}

	static {
		Vector3f.XN = new Vector3f(-1.0f, 0.0f, 0.0f);
		Vector3f.XP = new Vector3f(1.0f, 0.0f, 0.0f);
		Vector3f.YN = new Vector3f(0.0f, -1.0f, 0.0f);
		Vector3f.YP = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f.ZN = new Vector3f(0.0f, 0.0f, -1.0f);
		Vector3f.ZP = new Vector3f(0.0f, 0.0f, 1.0f);
		Vector3f.ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
	}
}
