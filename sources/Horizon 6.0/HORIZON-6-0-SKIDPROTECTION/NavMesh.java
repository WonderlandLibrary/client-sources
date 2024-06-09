package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.ArrayList;

public class NavMesh
{
    private ArrayList HorizonCode_Horizon_È;
    
    public NavMesh() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public NavMesh(final ArrayList spaces) {
        (this.HorizonCode_Horizon_È = new ArrayList()).addAll(spaces);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public Space HorizonCode_Horizon_È(final int index) {
        return this.HorizonCode_Horizon_È.get(index);
    }
    
    public void HorizonCode_Horizon_È(final Space space) {
        this.HorizonCode_Horizon_È.add(space);
    }
    
    public Space HorizonCode_Horizon_È(final float x, final float y) {
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            final Space space = this.HorizonCode_Horizon_È(i);
            if (space.HorizonCode_Horizon_È(x, y)) {
                return space;
            }
        }
        return null;
    }
    
    public NavPath HorizonCode_Horizon_È(final float sx, final float sy, final float tx, final float ty, final boolean optimize) {
        final Space source = this.HorizonCode_Horizon_È(sx, sy);
        final Space target = this.HorizonCode_Horizon_È(tx, ty);
        if (source == null || target == null) {
            return null;
        }
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            this.HorizonCode_Horizon_È.get(i).Ó();
        }
        target.HorizonCode_Horizon_È(source, tx, ty, 0.0f);
        if (target.à() == Float.MAX_VALUE) {
            return null;
        }
        if (source.à() == Float.MAX_VALUE) {
            return null;
        }
        final NavPath path = new NavPath();
        path.HorizonCode_Horizon_È(new Link(sx, sy, null));
        if (source.HorizonCode_Horizon_È(target, path)) {
            path.HorizonCode_Horizon_È(new Link(tx, ty, null));
            if (optimize) {
                this.HorizonCode_Horizon_È(path);
            }
            return path;
        }
        return null;
    }
    
    private boolean HorizonCode_Horizon_È(final float x1, final float y1, final float x2, final float y2, final float step) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        final float len = (float)Math.sqrt(dx * dx + dy * dy);
        dx *= step;
        dx /= len;
        dy *= step;
        dy /= len;
        for (int steps = (int)(len / step), i = 0; i < steps; ++i) {
            final float x3 = x1 + dx * i;
            final float y3 = y1 + dy * i;
            if (this.HorizonCode_Horizon_È(x3, y3) == null) {
                return false;
            }
        }
        return true;
    }
    
    private void HorizonCode_Horizon_È(final NavPath path) {
        int pt = 0;
        while (pt < path.HorizonCode_Horizon_È() - 2) {
            final float sx = path.HorizonCode_Horizon_È(pt);
            final float sy = path.Â(pt);
            final float nx = path.HorizonCode_Horizon_È(pt + 2);
            final float ny = path.Â(pt + 2);
            if (this.HorizonCode_Horizon_È(sx, sy, nx, ny, 0.1f)) {
                path.Ý(pt + 1);
            }
            else {
                ++pt;
            }
        }
    }
}
