package src.Wiksi.functions.impl.render;

import java.util.LinkedList;
import java.util.Queue;

public class AIModel {
    private final int historySize;
    private final Queue<Float> speedHistory;
    private float aimSpeed;

    public AIModel(int historySize, float initialAimSpeed) {
        this.historySize = historySize;
        this.speedHistory = new LinkedList<>();
        this.aimSpeed = initialAimSpeed;
    }

    public float adjustAimSpeed(float aimChange, float aimSpeedFactor) {
        float adjustedSpeed = aimSpeed;

        if (!speedHistory.isEmpty()) {
            float averageSpeed = 0;
            for (float speed : speedHistory) {
                averageSpeed += speed;
            }
            averageSpeed /= speedHistory.size();
            adjustedSpeed = averageSpeed;
        }

        adjustedSpeed += aimChange * aimSpeedFactor;
        if (speedHistory.size() >= historySize) {
            speedHistory.poll();
        }
        speedHistory.add(adjustedSpeed);

        return adjustedSpeed;
    }

    public void updateSpeed(float newSpeed) {
        if (speedHistory.size() >= historySize) {
            speedHistory.poll();
        }
        speedHistory.add(newSpeed);
    }
}