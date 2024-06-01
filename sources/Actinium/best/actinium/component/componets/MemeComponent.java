package best.actinium.component.componets;

import best.actinium.component.Component;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.render.ColorUtil;
import best.actinium.util.render.RenderUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Random;

public class MemeComponent extends Component {
    private TimerUtil t = new TimerUtil(), frames = new TimerUtil();
    private boolean active = true, shouldShow = false, pick = false;
    private String location, test;
    private int frameCount = 0;

    @Callback
    public void onPacket(PacketEvent event) {
        if(!active) {
            return;
        }

        if(event.getPacket() instanceof C01PacketChatMessage) {
            final C01PacketChatMessage wrapper = (C01PacketChatMessage) event.getPacket();
            if(wrapper.getMessage().contains("show meme")) {
                shouldShow = true;
                pick = false;
                t.reset();
            }
        }

        // if(event.getPacket() instanceof S02PacketChat) {
        //            final S02PacketChat wrapper = (S02PacketChat) event.getPacket();
        //            if(wrapper.getChatComponent().getFormattedText().contains("show meme")) {
        //                shouldShow = true;
        //                t.reset();
        //            }
        //        }

        if(t.hasTimeElapsed(2000)) {
            shouldShow = false;
        }
    }

    @Callback
    public void onRender(Render2DEvent event) {
        if(!active) {
            return;
        }
        //todo: make like animated shit and make it play audio too
        //shitytytyy ass frames
        if(frames.finished(40)) {
            frameCount++;
            if (frameCount >= 14) {
                frameCount = 1;
            }

            test = "actinium/meme/aqua/frame_" + frameCount + "_delay-0.05s.png";
            frames.reset();
        }

        String[] strings =  {
                test,
                "actinium/meme/test 2.png"
        };

        //        if(!pick) {
        //            location = strings[new Random().nextInt(strings.length)];
        //            pick = true;
        //        }
        if(shouldShow) {
            RenderUtil.image(new ResourceLocation(test), 3, 3, 200, 200, ColorUtil.withAlpha(Color.white, 255));
        }
    }
}
