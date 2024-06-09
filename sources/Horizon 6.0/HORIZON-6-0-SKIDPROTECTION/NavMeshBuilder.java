package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class NavMeshBuilder implements PathFindingContext
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    
    public NavMeshBuilder() {
        this.Ý = 0.2f;
    }
    
    public NavMesh HorizonCode_Horizon_È(final TileBasedMap map) {
        return this.HorizonCode_Horizon_È(map, true);
    }
    
    public NavMesh HorizonCode_Horizon_È(final TileBasedMap map, final boolean tileBased) {
        this.Ø­áŒŠá = tileBased;
        final ArrayList spaces = new ArrayList();
        if (tileBased) {
            for (int x = 0; x < map.Â(); ++x) {
                for (int y = 0; y < map.HorizonCode_Horizon_È(); ++y) {
                    if (!map.HorizonCode_Horizon_È(this, x, y)) {
                        spaces.add(new Space(x, y, 1.0f, 1.0f));
                    }
                }
            }
        }
        else {
            final Space space = new Space(0.0f, 0.0f, map.Â(), map.HorizonCode_Horizon_È());
            this.HorizonCode_Horizon_È(map, space, spaces);
        }
        while (this.HorizonCode_Horizon_È(spaces)) {}
        this.Â(spaces);
        return new NavMesh(spaces);
    }
    
    private boolean HorizonCode_Horizon_È(final ArrayList spaces) {
        for (int source = 0; source < spaces.size(); ++source) {
            final Space a = spaces.get(source);
            for (int target = source + 1; target < spaces.size(); ++target) {
                final Space b = spaces.get(target);
                if (a.Ø­áŒŠá(b)) {
                    spaces.remove(a);
                    spaces.remove(b);
                    spaces.add(a.Ý(b));
                    return true;
                }
            }
        }
        return false;
    }
    
    private void Â(final ArrayList spaces) {
        for (int source = 0; source < spaces.size(); ++source) {
            final Space a = spaces.get(source);
            for (int target = source + 1; target < spaces.size(); ++target) {
                final Space b = spaces.get(target);
                if (a.Â(b)) {
                    a.HorizonCode_Horizon_È(b);
                    b.HorizonCode_Horizon_È(a);
                }
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final TileBasedMap map, final Space space) {
        if (this.Ø­áŒŠá) {
            return true;
        }
        float x = 0.0f;
        boolean donex = false;
        while (x < space.HorizonCode_Horizon_È()) {
            float y = 0.0f;
            boolean doney = false;
            while (y < space.Â()) {
                this.HorizonCode_Horizon_È = (int)(space.Ý() + x);
                this.Â = (int)(space.Ø­áŒŠá() + y);
                if (map.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È, this.Â)) {
                    return false;
                }
                y += 0.1f;
                if (y <= space.Â() || doney) {
                    continue;
                }
                y = space.Â();
                doney = true;
            }
            x += 0.1f;
            if (x > space.HorizonCode_Horizon_È() && !donex) {
                x = space.HorizonCode_Horizon_È();
                donex = true;
            }
        }
        return true;
    }
    
    private void HorizonCode_Horizon_È(final TileBasedMap map, final Space space, final ArrayList spaces) {
        if (!this.HorizonCode_Horizon_È(map, space)) {
            final float width2 = space.HorizonCode_Horizon_È() / 2.0f;
            final float height2 = space.Â() / 2.0f;
            if (width2 < this.Ý && height2 < this.Ý) {
                return;
            }
            this.HorizonCode_Horizon_È(map, new Space(space.Ý(), space.Ø­áŒŠá(), width2, height2), spaces);
            this.HorizonCode_Horizon_È(map, new Space(space.Ý(), space.Ø­áŒŠá() + height2, width2, height2), spaces);
            this.HorizonCode_Horizon_È(map, new Space(space.Ý() + width2, space.Ø­áŒŠá(), width2, height2), spaces);
            this.HorizonCode_Horizon_È(map, new Space(space.Ý() + width2, space.Ø­áŒŠá() + height2, width2, height2), spaces);
        }
        else {
            spaces.add(space);
        }
    }
    
    @Override
    public Mover Ó() {
        return null;
    }
    
    @Override
    public int à() {
        return 0;
    }
    
    @Override
    public int Ø() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Â;
    }
}
