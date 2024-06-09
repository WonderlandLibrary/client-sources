package com.client.glowclient.utils;

public class KeybindHelper
{
    public static final BetterKeybind keyJump;
    public static final BetterKeybind keySprint;
    public static final BetterKeybind keyLeft;
    public static final BetterKeybind keyAttack;
    public static final BetterKeybind keyForward;
    public static final BetterKeybind keyUseItem;
    public static final BetterKeybind keyRight;
    public static final BetterKeybind keyBack;
    public static final BetterKeybind keySneak;
    
    public KeybindHelper() {
        super();
    }
    
    static {
        keyForward = new BetterKeybind(Wrapper.mc.gameSettings.keyBindForward);
        keyBack = new BetterKeybind(Wrapper.mc.gameSettings.keyBindBack);
        keyLeft = new BetterKeybind(Wrapper.mc.gameSettings.keyBindLeft);
        keyRight = new BetterKeybind(Wrapper.mc.gameSettings.keyBindRight);
        keyJump = new BetterKeybind(Wrapper.mc.gameSettings.keyBindJump);
        keySprint = new BetterKeybind(Wrapper.mc.gameSettings.keyBindSprint);
        keySneak = new BetterKeybind(Wrapper.mc.gameSettings.keyBindSneak);
        keyAttack = new BetterKeybind(Wrapper.mc.gameSettings.keyBindAttack);
        keyUseItem = new BetterKeybind(Wrapper.mc.gameSettings.keyBindUseItem);
    }
}
