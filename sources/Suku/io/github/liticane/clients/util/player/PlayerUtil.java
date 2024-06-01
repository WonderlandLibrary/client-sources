package io.github.liticane.clients.util.player;

import io.github.liticane.clients.util.interfaces.IMethods;
public class PlayerUtil implements IMethods {
    public static boolean isMathGround() {return mc.player.posY % 0.015625 == 0;}

}
