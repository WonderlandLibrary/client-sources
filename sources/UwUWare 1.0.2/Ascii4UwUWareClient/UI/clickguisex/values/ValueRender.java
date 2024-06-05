package Ascii4UwUWareClient.UI.clickguisex.values;

import Ascii4UwUWareClient.API.Value.Value;

public class ValueRender {
    public Value value;
    public float x, y;

    public ValueRender(Value v, float x, float y) {
        this.value = v;
        this.x = x;
        this.y = y;
    }

    public void onRender() {

    }

    public void onClicked(float x, float y, int clickType) {

    }

    public void onMouseMove(float x, float y, int clickType) {


    }

    public float getX() {
        return x;
    }

    public Value getValue() {
        return value;
    }

    public float getY() {
        return y;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public static boolean isHovered(float x, float y, float x1, float y1, float mouseX, float mouseY) {
        if (mouseX > x && mouseY > y && mouseX < x1 && mouseY < y1) {
            return true;
        }
        return false;
    }

}
