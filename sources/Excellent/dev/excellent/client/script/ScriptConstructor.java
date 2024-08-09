package dev.excellent.client.script;

import dev.excellent.client.script.api.ScriptAction;
import dev.excellent.impl.util.time.TimerUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class ScriptConstructor {
    private final TimerUtil time;
    private final List<ScriptStep> scriptSteps;
    private int currentStepIndex;
    private boolean loop, interrupt;

    private ScriptConstructor() {
        this.scriptSteps = new LinkedList<>();
        this.time = TimerUtil.create();
        cleanup();
    }

    public static ScriptConstructor create() {
        return new ScriptConstructor();
    }

    public ScriptConstructor addStep(int delay, ScriptAction action) {
        return addStep(delay, action, () -> true, 0);
    }

    public ScriptConstructor addStep(int delay, ScriptAction action, BooleanSupplier condition) {
        return addStep(delay, action, condition, 0);
    }

    public ScriptConstructor addStep(int delay, ScriptAction action, int priority) {
        return addStep(delay, action, () -> true, priority);
    }

    public ScriptConstructor addStep(int delay, ScriptAction action, BooleanSupplier condition, int priority) {
        scriptSteps.add(new ScriptStep(delay, action, condition, priority));
        Collections.sort(scriptSteps);
        return this;
    }

    public void timeReset() {
        time.reset();
    }

    public void resetStep() {
        currentStepIndex = 0;
    }

    public ScriptConstructor cleanup() {
        scriptSteps.clear();
        timeReset();
        resetStep();
        return this;
    }

    private boolean shouldLoop() {
        return currentStepIndex >= scriptSteps.size() && loop;
    }

    public void update() {
        if (scriptSteps.isEmpty() || interrupt) return;
        scriptSteps.forEach(step -> {
            if (currentStepIndex < scriptSteps.size()) {
                ScriptStep currentStep = scriptSteps.get(currentStepIndex);
                if (currentStep.getCondition().getAsBoolean() && (time.hasReached(currentStep.getDelay()))) {
                    currentStep.getAction().perform();
                    currentStepIndex++;
                    time.reset();

                    if (shouldLoop()) resetStep();
                }
            }
        });
        currentStepIndex = Math.min(currentStepIndex, scriptSteps.size());
    }

    public boolean isFinished() {
        return currentStepIndex >= scriptSteps.size() && !loop && !interrupt;
    }

    public TimerUtil getTime() {
        return this.time;
    }

    public List<ScriptStep> getScriptSteps() {
        return this.scriptSteps;
    }

    public int getCurrentStepIndex() {
        return this.currentStepIndex;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public boolean isInterrupt() {
        return this.interrupt;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    private static class ScriptStep implements Comparable<ScriptStep> {
        private final int delay;
        private final ScriptAction action;
        private final BooleanSupplier condition;
        private final int priority;

        public ScriptStep(int delay, ScriptAction action, BooleanSupplier condition, int priority) {
            this.delay = delay;
            this.action = action;
            this.condition = condition;
            this.priority = priority;
        }

        @Override
        public int compareTo(ScriptStep otherStep) {
            return Integer.compare(otherStep.getPriority(), this.getPriority());
        }

        public int getDelay() {
            return this.delay;
        }

        public ScriptAction getAction() {
            return this.action;
        }

        public BooleanSupplier getCondition() {
            return this.condition;
        }

        public int getPriority() {
            return this.priority;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof ScriptStep other)) return false;
            if (!other.canEqual(this)) return false;
            if (this.getDelay() != other.getDelay()) return false;
            final Object this$action = this.getAction();
            final Object other$action = other.getAction();
            if (!Objects.equals(this$action, other$action)) return false;
            final Object this$condition = this.getCondition();
            final Object other$condition = other.getCondition();
            if (!Objects.equals(this$condition, other$condition))
                return false;
            return this.getPriority() == other.getPriority();
        }

        protected boolean canEqual(final Object other) {
            return other instanceof ScriptStep;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + this.getDelay();
            final Object $action = this.getAction();
            result = result * PRIME + ($action == null ? 43 : $action.hashCode());
            final Object $condition = this.getCondition();
            result = result * PRIME + ($condition == null ? 43 : $condition.hashCode());
            result = result * PRIME + this.getPriority();
            return result;
        }

        public String toString() {
            return "ScriptConstructor.ScriptStep(delay=" + this.getDelay() + ", action=" + this.getAction() + ", condition=" + this.getCondition() + ", priority=" + this.getPriority() + ")";
        }
    }
}
