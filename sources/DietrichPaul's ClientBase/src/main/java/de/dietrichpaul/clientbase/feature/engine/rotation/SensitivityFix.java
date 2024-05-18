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
package de.dietrichpaul.clientbase.feature.engine.rotation;

public enum SensitivityFix {

    NONE("None"),
    TICK_BASED("TickBased"),    // 20FPS
    APPROXIMATE("Approximate"), // fps [20;60]
    REAL("Real");               // mouse deltas are being simulated for real fps

    private final String name;

    SensitivityFix(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
