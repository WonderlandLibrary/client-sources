/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.app;

import imgui.app.Configuration;
import imgui.app.Window;

public abstract class Application
extends Window {
    protected void configure(Configuration configuration) {
    }

    protected void preRun() {
    }

    protected void postRun() {
    }

    public static void launch(Application application) {
        Application.initialize(application);
        application.preRun();
        application.run();
        application.postRun();
        application.dispose();
    }

    private static void initialize(Application application) {
        Configuration configuration = new Configuration();
        application.configure(configuration);
        application.init(configuration);
    }
}

