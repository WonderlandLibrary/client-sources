package HORIZON-6-0-SKIDPROTECTION;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class AStarPathFinder implements PathFinder, PathFindingContext
{
    private ArrayList HorizonCode_Horizon_È;
    private Â Â;
    private TileBasedMap Ý;
    private int Ø­áŒŠá;
    private HorizonCode_Horizon_È[][] Âµá€;
    private boolean Ó;
    private AStarHeuristic à;
    private HorizonCode_Horizon_È Ø;
    private Mover áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    
    public AStarPathFinder(final TileBasedMap map, final int maxSearchDistance, final boolean allowDiagMovement) {
        this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
    }
    
    public AStarPathFinder(final TileBasedMap map, final int maxSearchDistance, final boolean allowDiagMovement, final AStarHeuristic heuristic) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = new Â((Â)null);
        this.à = heuristic;
        this.Ý = map;
        this.Ø­áŒŠá = maxSearchDistance;
        this.Ó = allowDiagMovement;
        this.Âµá€ = new HorizonCode_Horizon_È[map.Â()][map.HorizonCode_Horizon_È()];
        for (int x = 0; x < map.Â(); ++x) {
            for (int y = 0; y < map.HorizonCode_Horizon_È(); ++y) {
                this.Âµá€[x][y] = new HorizonCode_Horizon_È(x, y);
            }
        }
    }
    
    @Override
    public Path_460141958 HorizonCode_Horizon_È(final Mover mover, final int sx, final int sy, final int tx, final int ty) {
        this.Ø = null;
        this.áŒŠÆ = mover;
        this.áˆºÑ¢Õ = tx;
        this.ÂµÈ = ty;
        this.á = 0;
        if (this.Ý.HorizonCode_Horizon_È(this, tx, ty)) {
            return null;
        }
        for (int x = 0; x < this.Ý.Â(); ++x) {
            for (int y = 0; y < this.Ý.HorizonCode_Horizon_È(); ++y) {
                this.Âµá€[x][y].Ý();
            }
        }
        AStarPathFinder.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Âµá€[sx][sy], 0.0f);
        AStarPathFinder.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Âµá€[sx][sy], 0);
        this.HorizonCode_Horizon_È.clear();
        this.Â.Â();
        this.HorizonCode_Horizon_È(this.Âµá€[sx][sy]);
        AStarPathFinder.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Âµá€[tx][ty], null);
        int maxDepth = 0;
        while (maxDepth < this.Ø­áŒŠá && this.Â.Ý() != 0) {
            int lx = sx;
            int ly = sy;
            if (this.Ø != null) {
                lx = this.Ø.Â;
                ly = this.Ø.Ý;
            }
            this.Ø = this.Ý();
            this.á = this.Ø.à;
            if (this.Ø == this.Âµá€[tx][ty] && this.Â(mover, lx, ly, tx, ty)) {
                break;
            }
            this.Ý(this.Ø);
            this.Ø­áŒŠá(this.Ø);
            for (int x2 = -1; x2 < 2; ++x2) {
                for (int y2 = -1; y2 < 2; ++y2) {
                    if (x2 != 0 || y2 != 0) {
                        if (this.Ó || x2 == 0 || y2 == 0) {
                            final int xp = x2 + this.Ø.Â;
                            final int yp = y2 + this.Ø.Ý;
                            if (this.Â(mover, this.Ø.Â, this.Ø.Ý, xp, yp)) {
                                final float nextStepCost = this.Ø.Ø­áŒŠá + this.Ý(mover, this.Ø.Â, this.Ø.Ý, xp, yp);
                                final HorizonCode_Horizon_È neighbour = this.Âµá€[xp][yp];
                                this.Ý.HorizonCode_Horizon_È(xp, yp);
                                if (nextStepCost < neighbour.Ø­áŒŠá) {
                                    if (this.Â(neighbour)) {
                                        this.Ý(neighbour);
                                    }
                                    if (this.Âµá€(neighbour)) {
                                        this.Ó(neighbour);
                                    }
                                }
                                if (!this.Â(neighbour) && !this.Âµá€(neighbour)) {
                                    AStarPathFinder.HorizonCode_Horizon_È.HorizonCode_Horizon_È(neighbour, nextStepCost);
                                    AStarPathFinder.HorizonCode_Horizon_È.Â(neighbour, this.Ø­áŒŠá(mover, xp, yp, tx, ty));
                                    maxDepth = Math.max(maxDepth, neighbour.HorizonCode_Horizon_È(this.Ø));
                                    this.HorizonCode_Horizon_È(neighbour);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.Âµá€[tx][ty].Âµá€ == null) {
            return null;
        }
        final Path_460141958 path = new Path_460141958();
        for (HorizonCode_Horizon_È target = this.Âµá€[tx][ty]; target != this.Âµá€[sx][sy]; target = target.Âµá€) {
            path.Â(target.Â, target.Ý);
        }
        path.Â(sx, sy);
        return path;
    }
    
    public int HorizonCode_Horizon_È() {
        if (this.Ø == null) {
            return -1;
        }
        return this.Ø.Â;
    }
    
    public int Â() {
        if (this.Ø == null) {
            return -1;
        }
        return this.Ø.Ý;
    }
    
    protected HorizonCode_Horizon_È Ý() {
        return (HorizonCode_Horizon_È)this.Â.HorizonCode_Horizon_È();
    }
    
    protected void HorizonCode_Horizon_È(final HorizonCode_Horizon_È node) {
        node.HorizonCode_Horizon_È(true);
        this.Â.HorizonCode_Horizon_È(node);
    }
    
    protected boolean Â(final HorizonCode_Horizon_È node) {
        return node.HorizonCode_Horizon_È();
    }
    
    protected void Ý(final HorizonCode_Horizon_È node) {
        node.HorizonCode_Horizon_È(false);
        this.Â.Â(node);
    }
    
    protected void Ø­áŒŠá(final HorizonCode_Horizon_È node) {
        node.Â(true);
        this.HorizonCode_Horizon_È.add(node);
    }
    
    protected boolean Âµá€(final HorizonCode_Horizon_È node) {
        return node.Â();
    }
    
    protected void Ó(final HorizonCode_Horizon_È node) {
        node.Â(false);
        this.HorizonCode_Horizon_È.remove(node);
    }
    
    protected boolean Â(final Mover mover, final int sx, final int sy, final int x, final int y) {
        boolean invalid = x < 0 || y < 0 || x >= this.Ý.Â() || y >= this.Ý.HorizonCode_Horizon_È();
        if (!invalid && (sx != x || sy != y)) {
            this.áŒŠÆ = mover;
            this.áˆºÑ¢Õ = sx;
            this.ÂµÈ = sy;
            invalid = this.Ý.HorizonCode_Horizon_È(this, x, y);
        }
        return !invalid;
    }
    
    public float Ý(final Mover mover, final int sx, final int sy, final int tx, final int ty) {
        this.áŒŠÆ = mover;
        this.áˆºÑ¢Õ = sx;
        this.ÂµÈ = sy;
        return this.Ý.Â(this, tx, ty);
    }
    
    public float Ø­áŒŠá(final Mover mover, final int x, final int y, final int tx, final int ty) {
        return this.à.HorizonCode_Horizon_È(this.Ý, mover, x, y, tx, ty);
    }
    
    @Override
    public Mover Ó() {
        return this.áŒŠÆ;
    }
    
    @Override
    public int à() {
        return this.á;
    }
    
    @Override
    public int Ø() {
        return this.áˆºÑ¢Õ;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.ÂµÈ;
    }
    
    private class Â
    {
        private List Â;
        
        private Â() {
            this.Â = new LinkedList();
        }
        
        public Object HorizonCode_Horizon_È() {
            return this.Â.get(0);
        }
        
        public void Â() {
            this.Â.clear();
        }
        
        public void HorizonCode_Horizon_È(final Object o) {
            for (int i = 0; i < this.Â.size(); ++i) {
                if (this.Â.get(i).compareTo(o) > 0) {
                    this.Â.add(i, o);
                    break;
                }
            }
            if (!this.Â.contains(o)) {
                this.Â.add(o);
            }
        }
        
        public void Â(final Object o) {
            this.Â.remove(o);
        }
        
        public int Ý() {
            return this.Â.size();
        }
        
        public boolean Ý(final Object o) {
            return this.Â.contains(o);
        }
        
        @Override
        public String toString() {
            String temp = "{";
            for (int i = 0; i < this.Ý(); ++i) {
                temp = String.valueOf(temp) + this.Â.get(i).toString() + ",";
            }
            temp = String.valueOf(temp) + "}";
            return temp;
        }
    }
    
    private class HorizonCode_Horizon_È implements Comparable
    {
        private int Â;
        private int Ý;
        private float Ø­áŒŠá;
        private HorizonCode_Horizon_È Âµá€;
        private float Ó;
        private int à;
        private boolean Ø;
        private boolean áŒŠÆ;
        
        public HorizonCode_Horizon_È(final int x, final int y) {
            this.Â = x;
            this.Ý = y;
        }
        
        public int HorizonCode_Horizon_È(final HorizonCode_Horizon_È parent) {
            this.à = parent.à + 1;
            this.Âµá€ = parent;
            return this.à;
        }
        
        @Override
        public int compareTo(final Object other) {
            final HorizonCode_Horizon_È o = (HorizonCode_Horizon_È)other;
            final float f = this.Ó + this.Ø­áŒŠá;
            final float of = o.Ó + o.Ø­áŒŠá;
            if (f < of) {
                return -1;
            }
            if (f > of) {
                return 1;
            }
            return 0;
        }
        
        public void HorizonCode_Horizon_È(final boolean open) {
            this.Ø = open;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ø;
        }
        
        public void Â(final boolean closed) {
            this.áŒŠÆ = closed;
        }
        
        public boolean Â() {
            return this.áŒŠÆ;
        }
        
        public void Ý() {
            this.áŒŠÆ = false;
            this.Ø = false;
            this.Ø­áŒŠá = 0.0f;
            this.à = 0;
        }
        
        @Override
        public String toString() {
            return "[Node " + this.Â + "," + this.Ý + "]";
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final float ø­áŒŠá) {
            horizonCode_Horizon_È.Ø­áŒŠá = ø­áŒŠá;
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final int à) {
            horizonCode_Horizon_È.à = à;
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final HorizonCode_Horizon_È âµá€) {
            horizonCode_Horizon_È.Âµá€ = âµá€;
        }
        
        static /* synthetic */ void Â(final HorizonCode_Horizon_È horizonCode_Horizon_È, final float ó) {
            horizonCode_Horizon_È.Ó = ó;
        }
    }
}
