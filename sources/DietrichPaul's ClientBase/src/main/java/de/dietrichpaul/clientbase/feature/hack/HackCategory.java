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
package de.dietrichpaul.clientbase.feature.hack;

public enum HackCategory {

    COMBAT("Combat"), MOVEMENT("Movement"), RENDER("Render"), WORLD("World");

    private final String name;

    HackCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
