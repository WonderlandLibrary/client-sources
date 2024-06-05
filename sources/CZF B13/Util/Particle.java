package gq.vapu.czfclient.Util;

import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Random;

public class Particle {
    public float x;
    public float y;
    public State state = State.ONE;
    public int[] pos = new int[2];
    private final Random random = new Random();
    public float radius = this.random.nextInt(2) + 1;
    private boolean grow;
    private final double max = 0.5;
    private final double min = 1.0;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        this.grow = this.radius < 2.0f;
        this.pos[0] = x;
        this.pos[1] = y;
    }

    public void drawScreen(int mouseX, int mouseY, int height) {
        Color white = new Color(255, 255, 255, 255);
        this.changeRadius();
        this.changePos(height);
        Gui.drawFilledCircle(this.x, this.y, this.radius, white);
        Gui.drawLine(this.x, this.y - 1.8f, this.x, this.y + 1.8f, white);
        Gui.drawLine(this.x - 0.2f, this.y - 1.8f, this.x, this.y - 1.8f, white);
        Gui.drawLine(this.x - 1.8f, this.y - 0.8f, this.x + 1.8f, this.y + 0.8f, white);
        Gui.drawLine(this.x - 1.8f, this.y + 0.8f, this.x + 1.8f, this.y - 0.8f, white);
    }

    private void changePos(int height) {
        this.x += this.random.nextFloat() - 0.5f;
        this.y = (float) ((double) this.y + 1.2);
        if (this.y > (float) height) {
            this.y = 0.0f;
        }
    }

    private void changeRadius() {
        if (this.grow) {
            this.radius = (float) ((double) this.radius + 0.1);
            if ((double) this.radius > this.max) {
                this.grow = !this.grow;
            }
        } else {
            this.radius = (float) ((double) this.radius - 0.1);
            if ((double) this.radius < this.min) {
                this.grow = !this.grow;
            }
        }
    }

    private enum State {
        ONE,
        TWO,
        THREE
    }
}
