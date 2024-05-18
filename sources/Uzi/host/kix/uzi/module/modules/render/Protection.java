package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.RenderStringEvent;
import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * Created by myche on 3/1/2017.
 */
public class Protection extends Module {

    public Protection() {
        super("Protection", Keyboard.KEY_NONE, Category.RENDER);
    }

    @SubscribeEvent
    public void renderString(RenderStringEvent e){
        Uzi.getInstance().getFriendManager().getContents().stream().filter(friend -> e.getString().contains(friend.getUsername())).forEach(friend -> e.setString(e.getString().replaceAll(friend.getUsername(), "\2473" + friend.getAlias())));
    }

}
