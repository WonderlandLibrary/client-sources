package com.minus.commands;

import com.minus.utils.ChatUtils;
import com.minus.utils.MinecraftInterface;
import lombok.Getter;

@Getter
public class Command implements MinecraftInterface {
    private final CommandInfo commandInfo;
    public Command(){
        if (this.getClass().isAnnotationPresent(CommandInfo.class)){
            this.commandInfo = this.getClass().getAnnotation(CommandInfo.class);
        }
        else{
            throw new RuntimeException("Stupid ahh nigga dont initialized @CommandInfo");
        }
    }

    public void onCall(String[] args){

    };


}

