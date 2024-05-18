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

import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl.BreezilyFix;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl.CombatFix;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl.ErrorCorrectedFix;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.impl.SilentMoveFix;

public enum StrafeMode {

    NONE("None", false, null),
    STRICT("Strict", true, null),
    SILENT("Silent", true, new SilentMoveFix()),
    ERROR_CORRECTED("ErrorCorrected", true, new ErrorCorrectedFix(1)),
    SEMI_CORRECTED("SemiCorrected", true, new ErrorCorrectedFix(1.0/4.0)),
    BREEZILY("Breezily", true, new BreezilyFix()),
    COMBAT("Combat", true, new CombatFix());

    private String name;
    private boolean fixYaw;
    private CorrectMovement correctMovement;

    StrafeMode(String name, boolean fixYaw, CorrectMovement correctMovement) {
        this.name = name;
        this.fixYaw = fixYaw;
        this.correctMovement = correctMovement;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isFixYaw() {
        return fixYaw;
    }

    public CorrectMovement getCorrectMovement() {
        return correctMovement;
    }
}
