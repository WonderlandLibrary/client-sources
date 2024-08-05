package fr.dog.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum Placement {

    //enum
    TOP(1,0),
    BOTTOM(-1,0),
    LEFT(0,1),
    RIGHT(0,-1);

    //var
    @Setter
    private float offset;
    private final int xFactor, yFactor;

}
