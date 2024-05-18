package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.gui.*;

public final class Chat extends ModBase
{
    public Chat() {
        super("Chat", "0", false, ".t chat");
        this.setDescription("Attiva/Disattiva la Morbid Chat.");
    }
    
    @Override
    public void onEnable() {
        MorbidWrapper.mcObj().w.e = new awh(MorbidWrapper.mcObj());
        MorbidWrapper.mcObj().w.b().a();
    }
    
    @Override
    public void onDisable() {
        MorbidWrapper.mcObj().w.e = new MorbidChat(MorbidWrapper.mcObj());
        MorbidWrapper.mcObj().w.b().a();
    }
}
