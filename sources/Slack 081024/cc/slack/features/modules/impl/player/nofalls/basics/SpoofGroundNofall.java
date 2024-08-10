// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player.nofalls.basics;


import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;

public class SpoofGroundNofall implements INoFall {

    @Override
    public void onMotion(MotionEvent event) {
        event.setGround(true);
    }

    public String toString() {
        return "Spoof Ground";
    }
}
