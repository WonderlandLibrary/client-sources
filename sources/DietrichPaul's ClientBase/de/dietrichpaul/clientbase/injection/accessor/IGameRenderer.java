package de.dietrichpaul.clientbase.injection.accessor;

public interface IGameRenderer {

    void setRange(double range);

    void setCustomRaytrace(boolean customRaytrace);

    boolean isCustomRaytrace();

}
