package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.client.Minecraft;

public class FakeFPS extends Module {
    public static Numbers<Double> FPS = new Numbers<>("FPS", "FPS", 0.0, 0.0, 3279.0, 10.0);

    private FPSThread thread = new FPSThread();

    public FakeFPS() {
        super("FakeFPS", new String[]{}, ModuleType.Player);
        this.addValues(FPS);
        this.setRemoved(true);
    }

    @Override
    public void onEnable() {
        // 线程死了就重新创建一个实例，死掉的不能再启动了
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new FPSThread();
        }
        thread.start();
    }

    @Override
    public void onDisable() {
        thread.interrupt();
    }

    static class FPSThread extends Thread {
        @Override
        public void run() {
            while (true)
                Minecraft.debugFPS = (int) FPS.getValue().doubleValue();
        }
    }

}