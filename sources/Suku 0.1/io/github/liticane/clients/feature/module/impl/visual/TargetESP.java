package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.Render3DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.module.impl.combat.Aura;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.render.RenderUtil;

import java.awt.*;

@Module.Info(name = "TargetESP", category = Module.Category.VISUAL)
public class TargetESP extends Module {
    private BooleanProperty aura = new BooleanProperty("Aura ESP",this,false);
    private BooleanProperty backtrack = new BooleanProperty("Back Track ESP",this,false);
    public StringProperty auraesp = new StringProperty("Aura ESP Mode",this,"Box","Box","Circle","Fake Player");
    public StringProperty backtrackesp = new StringProperty("Back Track ESP Mode",this,"Fake Player","Fake Player","Box");

    @SubscribeEvent
    private final EventListener<Render3DEvent> onRender3d = e -> {
        if(aura.isToggled() && Aura.target != null) {
            switch (auraesp.getMode()) {
                case "Box":
                RenderUtil.renderBoundingBox(Aura.target, Color.white, 0.7f);
                break;
                //
            }
        }
        if(backtrack.isToggled()) {
            switch (backtrackesp.getMode()) {
                //Todo: make it like actually do something
            }
        }
        shows();
    };
    private void shows() {
        auraesp.setVisible(() -> aura.isToggled());
        backtrackesp.setVisible(() -> backtrack.isToggled());
    }

}
