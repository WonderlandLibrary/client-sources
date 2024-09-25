/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;

public final class ScoreComponent
extends BaseComponent {
    private String name;
    private String objective;
    private String value = "";

    public ScoreComponent(String name, String objective) {
        this.setName(name);
        this.setObjective(objective);
    }

    public ScoreComponent(ScoreComponent original) {
        super(original);
        this.setName(original.getName());
        this.setObjective(original.getObjective());
        this.setValue(original.getValue());
    }

    @Override
    public ScoreComponent duplicate() {
        return new ScoreComponent(this);
    }

    @Override
    protected void toPlainText(StringBuilder builder) {
        builder.append(this.value);
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(StringBuilder builder) {
        this.addFormat(builder);
        builder.append(this.value);
        super.toLegacyText(builder);
    }

    public String getName() {
        return this.name;
    }

    public String getObjective() {
        return this.objective;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ScoreComponent(name=" + this.getName() + ", objective=" + this.getObjective() + ", value=" + this.getValue() + ")";
    }

    public ScoreComponent(String name, String objective, String value) {
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScoreComponent)) {
            return false;
        }
        ScoreComponent other = (ScoreComponent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$objective = this.getObjective();
        String other$objective = other.getObjective();
        if (this$objective == null ? other$objective != null : !this$objective.equals(other$objective)) {
            return false;
        }
        String this$value = this.getValue();
        String other$value = other.getValue();
        return !(this$value == null ? other$value != null : !this$value.equals(other$value));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof ScoreComponent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $objective = this.getObjective();
        result = result * 59 + ($objective == null ? 43 : $objective.hashCode());
        String $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        return result;
    }
}

