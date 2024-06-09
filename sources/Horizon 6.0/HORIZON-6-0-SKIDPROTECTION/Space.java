package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.HashMap;

public class Space
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private float Ø­áŒŠá;
    private HashMap Âµá€;
    private ArrayList Ó;
    private float à;
    
    public Space(final float x, final float y, final float width, final float height) {
        this.Âµá€ = new HashMap();
        this.Ó = new ArrayList();
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        this.Ý = width;
        this.Ø­áŒŠá = height;
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public float Â() {
        return this.Ø­áŒŠá;
    }
    
    public float Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float Ø­áŒŠá() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final Space other) {
        if (this.Â(this.HorizonCode_Horizon_È, other.HorizonCode_Horizon_È + other.Ý) || this.Â(this.HorizonCode_Horizon_È + this.Ý, other.HorizonCode_Horizon_È)) {
            float linkx = this.HorizonCode_Horizon_È;
            if (this.HorizonCode_Horizon_È + this.Ý == other.HorizonCode_Horizon_È) {
                linkx = this.HorizonCode_Horizon_È + this.Ý;
            }
            final float top = Math.max(this.Â, other.Â);
            final float bottom = Math.min(this.Â + this.Ø­áŒŠá, other.Â + other.Ø­áŒŠá);
            final float linky = top + (bottom - top) / 2.0f;
            final Link link = new Link(linkx, linky, other);
            this.Âµá€.put(other, link);
            this.Ó.add(link);
        }
        if (this.Â(this.Â, other.Â + other.Ø­áŒŠá) || this.Â(this.Â + this.Ø­áŒŠá, other.Â)) {
            float linky2 = this.Â;
            if (this.Â + this.Ø­áŒŠá == other.Â) {
                linky2 = this.Â + this.Ø­áŒŠá;
            }
            final float left = Math.max(this.HorizonCode_Horizon_È, other.HorizonCode_Horizon_È);
            final float right = Math.min(this.HorizonCode_Horizon_È + this.Ý, other.HorizonCode_Horizon_È + other.Ý);
            final float linkx2 = left + (right - left) / 2.0f;
            final Link link = new Link(linkx2, linky2, other);
            this.Âµá€.put(other, link);
            this.Ó.add(link);
        }
    }
    
    private boolean Â(final float a, final float b) {
        return a == b;
    }
    
    public boolean Â(final Space other) {
        if (this.Â(this.HorizonCode_Horizon_È, other.HorizonCode_Horizon_È + other.Ý) || this.Â(this.HorizonCode_Horizon_È + this.Ý, other.HorizonCode_Horizon_È)) {
            if (this.Â >= other.Â && this.Â <= other.Â + other.Ø­áŒŠá) {
                return true;
            }
            if (this.Â + this.Ø­áŒŠá >= other.Â && this.Â + this.Ø­áŒŠá <= other.Â + other.Ø­áŒŠá) {
                return true;
            }
            if (other.Â >= this.Â && other.Â <= this.Â + this.Ø­áŒŠá) {
                return true;
            }
            if (other.Â + other.Ø­áŒŠá >= this.Â && other.Â + other.Ø­áŒŠá <= this.Â + this.Ø­áŒŠá) {
                return true;
            }
        }
        if (this.Â(this.Â, other.Â + other.Ø­áŒŠá) || this.Â(this.Â + this.Ø­áŒŠá, other.Â)) {
            if (this.HorizonCode_Horizon_È >= other.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È <= other.HorizonCode_Horizon_È + other.Ý) {
                return true;
            }
            if (this.HorizonCode_Horizon_È + this.Ý >= other.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È + this.Ý <= other.HorizonCode_Horizon_È + other.Ý) {
                return true;
            }
            if (other.HorizonCode_Horizon_È >= this.HorizonCode_Horizon_È && other.HorizonCode_Horizon_È <= this.HorizonCode_Horizon_È + this.Ý) {
                return true;
            }
            if (other.HorizonCode_Horizon_È + other.Ý >= this.HorizonCode_Horizon_È && other.HorizonCode_Horizon_È + other.Ý <= this.HorizonCode_Horizon_È + this.Ý) {
                return true;
            }
        }
        return false;
    }
    
    public Space Ý(final Space other) {
        final float minx = Math.min(this.HorizonCode_Horizon_È, other.HorizonCode_Horizon_È);
        final float miny = Math.min(this.Â, other.Â);
        float newwidth = this.Ý + other.Ý;
        float newheight = this.Ø­áŒŠá + other.Ø­áŒŠá;
        if (this.HorizonCode_Horizon_È == other.HorizonCode_Horizon_È) {
            newwidth = this.Ý;
        }
        else {
            newheight = this.Ø­áŒŠá;
        }
        return new Space(minx, miny, newwidth, newheight);
    }
    
    public boolean Ø­áŒŠá(final Space other) {
        return this.Â(other) && ((this.HorizonCode_Horizon_È == other.HorizonCode_Horizon_È && this.Ý == other.Ý) || (this.Â == other.Â && this.Ø­áŒŠá == other.Ø­áŒŠá));
    }
    
    public int Âµá€() {
        return this.Ó.size();
    }
    
    public Link HorizonCode_Horizon_È(final int index) {
        return this.Ó.get(index);
    }
    
    public boolean HorizonCode_Horizon_È(final float xp, final float yp) {
        return xp >= this.HorizonCode_Horizon_È && xp < this.HorizonCode_Horizon_È + this.Ý && yp >= this.Â && yp < this.Â + this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final Space target, final float sx, final float sy, final float cost) {
        if (cost >= this.à) {
            return;
        }
        this.à = cost;
        if (target == this) {
            return;
        }
        for (int i = 0; i < this.Âµá€(); ++i) {
            final Link link = this.HorizonCode_Horizon_È(i);
            final float extraCost = link.HorizonCode_Horizon_È(sx, sy);
            final float nextCost = cost + extraCost;
            link.Ý().HorizonCode_Horizon_È(target, link.HorizonCode_Horizon_È(), link.Â(), nextCost);
        }
    }
    
    public void Ó() {
        this.à = Float.MAX_VALUE;
    }
    
    public float à() {
        return this.à;
    }
    
    public boolean HorizonCode_Horizon_È(final Space target, final NavPath path) {
        if (target == this) {
            return true;
        }
        if (this.Âµá€.size() == 0) {
            return false;
        }
        Link bestLink = null;
        for (int i = 0; i < this.Âµá€(); ++i) {
            final Link link = this.HorizonCode_Horizon_È(i);
            if (bestLink == null || link.Ý().à() < bestLink.Ý().à()) {
                bestLink = link;
            }
        }
        path.HorizonCode_Horizon_È(bestLink);
        return bestLink.Ý().HorizonCode_Horizon_È(target, path);
    }
    
    @Override
    public String toString() {
        return "[Space " + this.HorizonCode_Horizon_È + "," + this.Â + " " + this.Ý + "," + this.Ø­áŒŠá + "]";
    }
}
