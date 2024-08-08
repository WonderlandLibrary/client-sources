package me.napoleon.napoline.modules.world;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.junk.cloud.IRC;
import net.minecraft.client.Minecraft;

public class IRCThread extends Thread {
    @Override
    public void run(){
        while(true){
            IRC.handleInput();
        }
    }
}
