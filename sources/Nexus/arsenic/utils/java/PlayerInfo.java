package arsenic.utils.java;

public class PlayerInfo {


    public boolean isServerSprintState() {
        return serverSprintState;
    }

    public float getLastReportedYaw() {
        return lastReportedYaw;
    }

    public float getLastReportedPitch() {
        return lastReportedPitch;
    }

    public PlayerInfo(float lastReportedYaw, float lastReportedPitch, boolean serverSprintState) {
        this.serverSprintState = serverSprintState;
        this.lastReportedYaw = lastReportedYaw;
        this.lastReportedPitch = lastReportedPitch;
    }

    boolean serverSprintState;
    float lastReportedYaw;
    float lastReportedPitch;

}
