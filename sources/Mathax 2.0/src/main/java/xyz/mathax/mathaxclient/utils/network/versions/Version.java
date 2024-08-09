package xyz.mathax.mathaxclient.utils.network.versions;

import xyz.mathax.mathaxclient.MatHax;

public class Version {
    private final String string;

    private final int[] numbers;

    public Version(String string) {
        this.string = string;
        this.numbers = new int[3];

        String[] split = string.split("\\.");
        if (split.length != 3) {
            throw new IllegalArgumentException("[" + MatHax.NAME + "] Version string needs to have 3 numbers.");
        }

        for (int i = 0; i < 3; i++) {
            try {
                numbers[i] = Integer.parseInt(split[i]);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("[" + MatHax.NAME + "] Failed to parse version string.");
            }
        }
    }

    public boolean isHigherThan(Version version) {
        for (int i = 0; i < 3; i++) {
            if (numbers[i] > version.numbers[i]) {
                return true;
            }

            if (numbers[i] < version.numbers[i]) {
                return false;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return string;
    }
}
