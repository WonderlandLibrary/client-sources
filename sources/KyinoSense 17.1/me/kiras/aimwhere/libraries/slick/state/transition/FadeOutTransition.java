/*
 * Decompiled with CFR 0.152.
 */
package me.kiras.aimwhere.libraries.slick.state.transition;

import me.kiras.aimwhere.libraries.slick.Color;
import me.kiras.aimwhere.libraries.slick.GameContainer;
import me.kiras.aimwhere.libraries.slick.Graphics;
import me.kiras.aimwhere.libraries.slick.state.GameState;
import me.kiras.aimwhere.libraries.slick.state.StateBasedGame;
import me.kiras.aimwhere.libraries.slick.state.transition.Transition;

public class FadeOutTransition
implements Transition {
    private Color color;
    private int fadeTime;

    public FadeOutTransition() {
        this(Color.black, 500);
    }

    public FadeOutTransition(Color color) {
        this(color, 500);
    }

    public FadeOutTransition(Color color, int fadeTime) {
        this.color = new Color(color);
        this.color.a = 0.0f;
        this.fadeTime = fadeTime;
    }

    @Override
    public boolean isComplete() {
        return this.color.a >= 1.0f;
    }

    @Override
    public void postRender(StateBasedGame game, GameContainer container, Graphics g) {
        Color old = g.getColor();
        g.setColor(this.color);
        g.fillRect(0.0f, 0.0f, container.getWidth() * 2, container.getHeight() * 2);
        g.setColor(old);
    }

    @Override
    public void update(StateBasedGame game, GameContainer container, int delta) {
        this.color.a += (float)delta * (1.0f / (float)this.fadeTime);
        if (this.color.a > 1.0f) {
            this.color.a = 1.0f;
        }
    }

    @Override
    public void preRender(StateBasedGame game, GameContainer container, Graphics g) {
    }

    @Override
    public void init(GameState firstState, GameState secondState) {
    }
}

