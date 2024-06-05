package com.mojang.realmsclient.gui;

import net.minecraft.realms.*;

public interface GuiCallback
{
    void tick();
    
    void buttonClicked(final RealmsButton p0);
}
