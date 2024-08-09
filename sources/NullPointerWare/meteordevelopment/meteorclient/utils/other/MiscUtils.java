/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.utils.other;

public final class MiscUtils {

    public static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number >= lowerBound && number <= upperBound;
    }

}
