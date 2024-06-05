package com.mojang.realmsclient.gui.screens;

import net.minecraft.realms.*;

public abstract class RealmsScreenWithCallback<T> extends RealmsScreen
{
    abstract void callback(final T p0);
}
