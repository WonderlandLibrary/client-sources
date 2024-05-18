package paulscode.sound;

public class Vector3D
{
    public float x;
    public float y;
    public float z;
    
    public Vector3D() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    
    public Vector3D(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D clone() {
        return new Vector3D(this.x, this.y, this.z);
    }
    
    public Vector3D cross(final Vector3D vector3D, final Vector3D vector3D2) {
        return new Vector3D(vector3D.y * vector3D2.z - vector3D2.y * vector3D.z, vector3D.z * vector3D2.x - vector3D2.z * vector3D.x, vector3D.x * vector3D2.y - vector3D2.x * vector3D.y);
    }
    
    public Vector3D cross(final Vector3D vector3D) {
        return new Vector3D(this.y * vector3D.z - vector3D.y * this.z, this.z * vector3D.x - vector3D.z * this.x, this.x * vector3D.y - vector3D.x * this.y);
    }
    
    public float dot(final Vector3D vector3D, final Vector3D vector3D2) {
        return vector3D.x * vector3D2.x + vector3D.y * vector3D2.y + vector3D.z * vector3D2.z;
    }
    
    public float dot(final Vector3D vector3D) {
        return this.x * vector3D.x + this.y * vector3D.y + this.z * vector3D.z;
    }
    
    public Vector3D add(final Vector3D vector3D, final Vector3D vector3D2) {
        return new Vector3D(vector3D.x + vector3D2.x, vector3D.y + vector3D2.y, vector3D.z + vector3D2.z);
    }
    
    public Vector3D add(final Vector3D vector3D) {
        return new Vector3D(this.x + vector3D.x, this.y + vector3D.y, this.z + vector3D.z);
    }
    
    public Vector3D subtract(final Vector3D vector3D, final Vector3D vector3D2) {
        return new Vector3D(vector3D.x - vector3D2.x, vector3D.y - vector3D2.y, vector3D.z - vector3D2.z);
    }
    
    public Vector3D subtract(final Vector3D vector3D) {
        return new Vector3D(this.x - vector3D.x, this.y - vector3D.y, this.z - vector3D.z);
    }
    
    public float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public void normalize() {
        final double sqrt = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x /= (float)sqrt;
        this.y /= (float)sqrt;
        this.z /= (float)sqrt;
    }
    
    public String toString() {
        return "Vector3D (" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
