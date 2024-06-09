package us.dev.direkt.module.internal.core.autocheat.check;

/**
 * @author Foundry
 */
public class CheckStatus {
    private final boolean passed;
    private final Check check;

    public CheckStatus(Check check, boolean passed) {
        this.check = check;
        this.passed = passed;
    }

    public Check getCheck() {
        return this.check;
    }

    public boolean hasPassed() {
        return this.passed;
    }
}
