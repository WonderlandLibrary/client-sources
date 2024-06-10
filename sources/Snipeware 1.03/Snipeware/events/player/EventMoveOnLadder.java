package Snipeware.events.player;

import Snipeware.events.Cancellable;

public class EventMoveOnLadder extends Cancellable
{
    private double ladderSpeed;
    
    public double getLadderSpeed() {
        return this.ladderSpeed;
    }
    
    public void setLadderSpeed(final double ladderSpeed) {
        this.ladderSpeed = ladderSpeed;
    }
    
    public EventMoveOnLadder(final double ladderSpeed) {
        this.ladderSpeed = ladderSpeed;
    }
}
