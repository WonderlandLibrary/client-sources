package zamorozka.ui;

public interface IPlayerSP {

    boolean isInLiquid();

    boolean isOnLiquid();

    boolean isMoving();

    void setInPortal(boolean inPortal);
}