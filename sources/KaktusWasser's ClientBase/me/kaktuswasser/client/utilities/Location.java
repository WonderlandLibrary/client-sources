// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

public class Location
{
    public double x;
    public double y;
    public double z;
    
    public Location(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void offset(final double xoff, final double yoff, final double zoff) {
        this.x += xoff;
        this.y += yoff;
        this.z += zoff;
    }
    
    public void rotate(final float rot, final boolean x, final boolean y, final boolean z) {
        final double radians = Math.toRadians(rot);
        if (x) {
            this.y = this.y * Math.cos(radians) - this.z * Math.sin(radians);
            this.z = this.y * Math.sin(radians) + this.z * Math.cos(radians);
        }
        else if (y) {
            this.z = this.z * Math.cos(radians) - this.x * Math.sin(radians);
            this.x = this.z * Math.sin(radians) + this.x * Math.cos(radians);
        }
        else if (z) {
            this.x = this.x * Math.cos(radians) - this.y * Math.sin(radians);
            this.y = this.x * Math.sin(radians) + this.y * Math.cos(radians);
        }
    }
}
