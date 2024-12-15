package com.alan.clients.ui.click.standard.components.popup;

import com.alan.clients.Client;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2f;

import java.awt.*;

public class PopUp implements Accessor {
    public Vector2f scale;

    public void draw() {
        final RiseClickGUI clickGUI = Client.INSTANCE.getClickGUI();

        final double x = clickGUI.position.x;
        final double y = clickGUI.position.y;
        final double width = clickGUI.scale.x;
        final double height = clickGUI.scale.y;

//        RenderUtil.rectangle(x, y, width, height, new Color(0, 0, 0, 100));

        if (scale == null) return;

        RenderUtil.dropShadow(60, (float) (x + width / 2 - scale.x / 2), (float) (y + height / 2 - scale.y / 2),
                scale.x, scale.y, 50, 34);

        RenderUtil.roundedRectangle(x + width / 2 - scale.x / 2, y + height / 2 - scale.y / 2, scale.x, scale.y,
                9, new Color(0, 0, 0, 230));

    }
}
