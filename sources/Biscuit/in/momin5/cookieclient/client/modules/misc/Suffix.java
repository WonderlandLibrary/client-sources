package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;


public class Suffix extends Module {
    public Suffix(){
        super("ChatSuffix", Category.MISC);
    }

    @SubscribeEvent
    public void onChat(final ClientChatEvent event){

        if(isEnabled()) {
            for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "+", "\\", "@"))
            {
                if (event.getMessage().startsWith(s)) return;
            }
            event.setMessage(event.getMessage() + " " + "\u23D0" + toUnicode(" " + CookieClient.MOD_NAME));
        }
    }

    public String toUnicode(String s) {
        return s.toLowerCase()
                .replace("a", "\u1d00")
                .replace("b", "\u0299")
                .replace("c", "\u1d04")
                .replace("d", "\u1d05")
                .replace("e", "\u1d07")
                .replace("f", "\ua730")
                .replace("g", "\u0262")
                .replace("h", "\u029c")
                .replace("i", "\u026a")
                .replace("j", "\u1d0a")
                .replace("k", "\u1d0b")
                .replace("l", "\u029f")
                .replace("m", "\u1d0d")
                .replace("n", "\u0274")
                .replace("o", "\u1d0f")
                .replace("p", "\u1d18")
                .replace("q", "\u01eb")
                .replace("r", "\u0280")
                .replace("s", "\ua731")
                .replace("t", "\u1d1b")
                .replace("u", "\u1d1c")
                .replace("v", "\u1d20")
                .replace("w", "\u1d21")
                .replace("x", "\u02e3")
                .replace("y", "\u028f")
                .replace("z", "\u1d22");
    }


    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
    }

}
