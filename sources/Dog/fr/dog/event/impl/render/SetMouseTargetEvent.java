package fr.dog.event.impl.render;

import fr.dog.event.Event;
import net.minecraft.util.MovingObjectPosition;

public class SetMouseTargetEvent extends Event {

    public boolean hasBeenTouchedLikeLiticaneMyFemboyFuwwyOngOrLikeMasterMeeMyFemboyBottom = false;
    public MovingObjectPosition position;

    public SetMouseTargetEvent(){

    }


    public void setMove(MovingObjectPosition position){
        this.position = position;
        hasBeenTouchedLikeLiticaneMyFemboyFuwwyOngOrLikeMasterMeeMyFemboyBottom = true;
    }


}
