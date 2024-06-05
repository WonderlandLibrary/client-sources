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
package de.dietrichpaul.clientbase.feature.hack.movement;

import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.ClientBase;

public class SprintHack extends Hack implements KeyPressedStateListener {

    public SprintHack() {
        super("Sprint", HackCategory.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(KeyPressedStateListener.class, this);
    }

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding == mc.options.sprintKey) event.pressed = true;
    }
}
