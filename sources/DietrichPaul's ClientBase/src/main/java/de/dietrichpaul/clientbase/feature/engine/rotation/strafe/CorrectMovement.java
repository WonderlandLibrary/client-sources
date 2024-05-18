/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.rotation.strafe;

import de.dietrichpaul.clientbase.event.StrafeInputListener;
import net.minecraft.client.MinecraftClient;

public abstract class CorrectMovement {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public abstract void edit(float serverYaw, StrafeInputListener.StrafeInputEvent event);

    public void reset() {
    }
}
