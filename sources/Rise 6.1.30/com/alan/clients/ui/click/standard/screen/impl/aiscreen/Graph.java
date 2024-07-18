package com.alan.clients.ui.click.standard.screen.impl.aiscreen;

import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

import static com.alan.clients.util.render.RenderUtil.*;
import static org.lwjgl.opengl.GL11.*;

@Setter
@Getter
@AllArgsConstructor
public class Graph {
    private ArrayList<float[]> points;
    private Vector2f position;
    private Color color;

    public void onRender() {
        glPushMatrix();

        for (int i = 1; i <= 5; i++) {
            glLineWidth(i);
            start();
            color(ColorUtil.withAlpha(color, (int) ((color.getAlpha() / i) / 1.2)));
            glBegin(GL_LINE_STRIP);

            for (float[] point : points) {
                glVertex2d(position.getX() + point[0], position.getY() + point[1]);
            }

            glEnd();
            stop();
        }

        double width = 5.5;
        for (float[] point : points) {
            RenderUtil.roundedRectangle(position.getX() + point[0] - width / 2,
                    position.getY() + point[1] - width / 2, width, width, width / 2f,
                    color);
        }

        glPopMatrix();
    }

    public void onBloom() {
        glPushMatrix();

        double width = 5.5;

        for (float[] point : points) {
            RenderUtil.roundedRectangle(position.getX() + point[0] - width / 2,
                    position.getY() + point[1] - width / 2, width, width, width / 2f,
                    color);
        }

        glPopMatrix();
    }
}
