package me.travis.wurstplus.gui.wurstplus.component;

import me.travis.wurstplus.gui.rgui.component.container.use.Frame;
import me.travis.wurstplus.gui.rgui.component.listen.RenderListener;
import me.travis.wurstplus.gui.rgui.component.use.Label;
import me.travis.wurstplus.gui.rgui.util.ContainerHelper;
import me.travis.wurstplus.gui.rgui.util.Docking;

public class ActiveModules extends Label {
//    public HashMap<Module, Integer> slide = new HashMap<>();

    public boolean sort_up = true;

    public ActiveModules() {
        super("");

        addRenderListener(new RenderListener() {
            @Override
            public void onPreRender() {
                Frame parentFrame = ContainerHelper.getFirstParent(Frame.class, ActiveModules.this);
                if (parentFrame == null) return;
                Docking docking = parentFrame.getDocking();
                if (docking.isTop()) sort_up = true;
                if (docking.isBottom()) sort_up = false;
            }

            @Override
            public void onPostRender() {}
        });
    }
};