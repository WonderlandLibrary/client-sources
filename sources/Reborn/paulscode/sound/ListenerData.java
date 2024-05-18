package paulscode.sound;

public class ListenerData
{
    public Vector3D position;
    public Vector3D lookAt;
    public Vector3D up;
    public Vector3D velocity;
    public float angle;
    
    public ListenerData() {
        this.angle = 0.0f;
        this.position = new Vector3D(0.0f, 0.0f, 0.0f);
        this.lookAt = new Vector3D(0.0f, 0.0f, -1.0f);
        this.up = new Vector3D(0.0f, 1.0f, 0.0f);
        this.velocity = new Vector3D(0.0f, 0.0f, 0.0f);
        this.angle = 0.0f;
    }
    
    public ListenerData(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float angle) {
        this.angle = 0.0f;
        this.position = new Vector3D(n, n2, n3);
        this.lookAt = new Vector3D(n4, n5, n6);
        this.up = new Vector3D(n7, n8, n9);
        this.velocity = new Vector3D(0.0f, 0.0f, 0.0f);
        this.angle = angle;
    }
    
    public ListenerData(final Vector3D vector3D, final Vector3D vector3D2, final Vector3D vector3D3, final float angle) {
        this.angle = 0.0f;
        this.position = vector3D.clone();
        this.lookAt = vector3D2.clone();
        this.up = vector3D3.clone();
        this.velocity = new Vector3D(0.0f, 0.0f, 0.0f);
        this.angle = angle;
    }
    
    public void setData(final float x, final float y, final float z, final float x2, final float y2, final float z2, final float x3, final float y3, final float z3, final float angle) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
        this.lookAt.x = x2;
        this.lookAt.y = y2;
        this.lookAt.z = z2;
        this.up.x = x3;
        this.up.y = y3;
        this.up.z = z3;
        this.angle = angle;
    }
    
    public void setData(final Vector3D vector3D, final Vector3D vector3D2, final Vector3D vector3D3, final float angle) {
        this.position.x = vector3D.x;
        this.position.y = vector3D.y;
        this.position.z = vector3D.z;
        this.lookAt.x = vector3D2.x;
        this.lookAt.y = vector3D2.y;
        this.lookAt.z = vector3D2.z;
        this.up.x = vector3D3.x;
        this.up.y = vector3D3.y;
        this.up.z = vector3D3.z;
        this.angle = angle;
    }
    
    public void setData(final ListenerData listenerData) {
        this.position.x = listenerData.position.x;
        this.position.y = listenerData.position.y;
        this.position.z = listenerData.position.z;
        this.lookAt.x = listenerData.lookAt.x;
        this.lookAt.y = listenerData.lookAt.y;
        this.lookAt.z = listenerData.lookAt.z;
        this.up.x = listenerData.up.x;
        this.up.y = listenerData.up.y;
        this.up.z = listenerData.up.z;
        this.angle = listenerData.angle;
    }
    
    public void setPosition(final float x, final float y, final float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    
    public void setPosition(final Vector3D vector3D) {
        this.position.x = vector3D.x;
        this.position.y = vector3D.y;
        this.position.z = vector3D.z;
    }
    
    public void setOrientation(final float x, final float y, final float z, final float x2, final float y2, final float z2) {
        this.lookAt.x = x;
        this.lookAt.y = y;
        this.lookAt.z = z;
        this.up.x = x2;
        this.up.y = y2;
        this.up.z = z2;
    }
    
    public void setOrientation(final Vector3D vector3D, final Vector3D vector3D2) {
        this.lookAt.x = vector3D.x;
        this.lookAt.y = vector3D.y;
        this.lookAt.z = vector3D.z;
        this.up.x = vector3D2.x;
        this.up.y = vector3D2.y;
        this.up.z = vector3D2.z;
    }
    
    public void setVelocity(final Vector3D vector3D) {
        this.velocity.x = vector3D.x;
        this.velocity.y = vector3D.y;
        this.velocity.z = vector3D.z;
    }
    
    public void setVelocity(final float x, final float y, final float z) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }
    
    public void setAngle(final float angle) {
        this.angle = angle;
        this.lookAt.x = -1.0f * (float)Math.sin(this.angle);
        this.lookAt.z = -1.0f * (float)Math.cos(this.angle);
    }
}
