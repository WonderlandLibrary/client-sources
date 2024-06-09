package host.kix.uzi.module.modules.render;

import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myche on 2/25/2017.
 */
public class Xray extends Module {

    private static List<Integer> blocks = new ArrayList<>();

    public Xray() {
        super("Xray", Keyboard.KEY_X, Category.RENDER);
        blocks.add(16);
        blocks.add(56);
        blocks.add(14);
        blocks.add(15);
        blocks.add(129);
        blocks.add(73);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
    }

    public static List<Integer> getBlocks() {
        return blocks;
    }
}
