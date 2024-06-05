package me.superskidder.lune.modules.world;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.cloud.IRC;
import net.minecraft.client.Minecraft;

public class IRCThread extends Thread {
    @Override
    public void run(){
        while(true){
            IRC.handleInput();
        }
    }
}
