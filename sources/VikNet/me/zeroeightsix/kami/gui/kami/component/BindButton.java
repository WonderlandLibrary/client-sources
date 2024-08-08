package me.zeroeightsix.kami.gui.kami.component;

import me.zeroeightsix.kami.gui.rgui.component.listen.KeyListener;
import me.zeroeightsix.kami.gui.rgui.component.listen.MouseListener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.util.Bind;
import org.lwjgl.input.Keyboard;

/**
 * Created by 086 on 8/08/2017.
 * Last Updated 28 June 2019 by hub
 */
class BindButton extends EnumButton {

    private static String[] lookingFor = new String[]{"_"};
    private static String[] none = new String[]{"NONE"};
    private boolean waiting = false;
    private Module m;

    BindButton(String name, Module m) {
        super(name, none);
        this.m = m;

        Bind bind = m.getBind();
        modes = new String[]{bind.toString()};

        addKeyListener(new KeyListener() {
            @Override
            public void onKeyDown(KeyEvent event) {
                if (!waiting) {
                    return;
                }
                int key = event.getKey();

                if (key == Keyboard.KEY_BACK) {
                    m.getBind().setKey(-1);
                    modes = new String[]{m.getBind().toString()};
                    waiting = false;
                } else {
                    m.getBind().setKey(key);
                    modes = new String[]{m.getBind().toString()};
                    waiting = false;
                }
            }

            @Override
            public void onKeyUp(KeyEvent event) {
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void onMouseDown(MouseButtonEvent event) {
                setModes(lookingFor);
                waiting = true;
            }

            @Override
            public void onMouseRelease(MouseButtonEvent event) {

            }

            @Override
            public void onMouseDrag(MouseButtonEvent event) {

            }

            @Override
            public void onMouseMove(MouseMoveEvent event) {

            }

            @Override
            public void onScroll(MouseScrollEvent event) {

            }
        });
    }

}
