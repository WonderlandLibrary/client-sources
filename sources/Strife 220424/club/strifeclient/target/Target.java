package club.strifeclient.target;

public abstract class Target {
    private final String targetName;

    public Target(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }
}
