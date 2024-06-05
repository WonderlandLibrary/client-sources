package ClassSub;

import java.util.*;

private class Class93
{
    private Class148 loc;
    private Class93 parent;
    private ArrayList<Class148> path;
    private double squareDistanceToFromTarget;
    private double cost;
    private double totalCost;
    final Class171 this$0;
    
    
    public Class93(final Class171 this$0, final Class148 loc, final Class93 parent, final ArrayList<Class148> path, final double squareDistanceToFromTarget, final double cost, final double totalCost) {
        this.this$0 = this$0;
        this.loc = null;
        this.parent = null;
        this.loc = loc;
        this.parent = parent;
        this.path = path;
        this.squareDistanceToFromTarget = squareDistanceToFromTarget;
        this.cost = cost;
        this.totalCost = totalCost;
    }
    
    public Class148 getLoc() {
        return this.loc;
    }
    
    public Class93 getParent() {
        return this.parent;
    }
    
    public ArrayList<Class148> getPath() {
        return this.path;
    }
    
    public double getSquareDistanceToFromTarget() {
        return this.squareDistanceToFromTarget;
    }
    
    public double getCost() {
        return this.cost;
    }
    
    public void setLoc(final Class148 loc) {
        this.loc = loc;
    }
    
    public void setParent(final Class93 parent) {
        this.parent = parent;
    }
    
    public void setPath(final ArrayList<Class148> path) {
        this.path = path;
    }
    
    public void setSquareDistanceToFromTarget(final double squareDistanceToFromTarget) {
        this.squareDistanceToFromTarget = squareDistanceToFromTarget;
    }
    
    public void setCost(final double cost) {
        this.cost = cost;
    }
    
    public double getTotalCost() {
        return this.totalCost;
    }
    
    public void setTotalCost(final double totalCost) {
        this.totalCost = totalCost;
    }
}
