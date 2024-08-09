package dev.excellent.api.interfaces.client;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.api.interfaces.game.IWindow;

public interface IAccess extends IMinecraft, IWindow, IMouse, ITheme {
    Excellent excellent = Excellent.getInst();
}