package de.tired.api.guis.guipainting.features;

import de.tired.api.util.math.Vec;

public class Positions {

    public Vec startPos, endPos, endPosStatic;

    public int thickness;

    public Positions(Vec startPos, Vec endPos, Vec endPosStatic,  int thickness) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.endPosStatic = endPosStatic;
        this.thickness = thickness;
    }

}
