package me.teus.eclipse.modules;

public enum Category {

    COMBAT("Combat", 40, 50),
    MOVEMENT("Movement", 150, 50),
    PLAYER("Player", 260, 50),
    VISUALS("Visuals", 370, 50),
    MISC("Misc", 480, 50);

    private String name;
    private int x, y;
    private boolean open = true;

    Category(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return x;
    }

    public void setPosX(int x) {
        this.x = x;
    }

    public int getPosY() {
        return y;
    }

    public void setPosY(int y) {
        this.y = y;
    }

    public boolean isCatOpen() {
        return open;
    }

    public void setCatOpen(boolean open) {
        this.open = open;
    }
}
