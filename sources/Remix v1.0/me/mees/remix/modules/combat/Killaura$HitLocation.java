package me.mees.remix.modules.combat;

private enum HitLocation
{
    AUTO(0.0), 
    HEAD(1.0), 
    CHEST(1.5), 
    FEET(3.5);
    
    private double offset;
    
    private HitLocation(final double offset) {
        this.offset = offset;
    }
    
    public double getOffset() {
        return this.offset;
    }
}
