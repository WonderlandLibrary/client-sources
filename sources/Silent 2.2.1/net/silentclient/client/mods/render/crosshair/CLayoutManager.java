package net.silentclient.client.mods.render.crosshair;

/**
 * @author refactoring
 */
import java.util.ArrayList;
import java.util.List;

public class CLayoutManager {

    private final List<boolean[][]> layouts = new ArrayList<>();

    public CLayoutManager() {
        init();
    }

    public void init() {
        addLayout(cDefault);
    }

    public boolean[][] getLayout(int index) {
        return layouts.get(index);
    }

    public void addLayout(boolean[][] layout) {
        layouts.add(layout);
    }

    final boolean[][] cDefault = new boolean[][]{
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, true, true, false, true, false, true, true, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false}
    };
}