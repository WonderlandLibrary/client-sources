package host.kix.uzi.module.addons.theme;

import java.util.ArrayList;
import java.util.List;

public class Theme {

    private List<ObjectRenderer> render = new ArrayList<ObjectRenderer>();
    private String label;

    public Theme(String label) {
        this.label = label;
    }

    protected void addRenderers(ObjectRenderer... objectRenderers) {
        for (ObjectRenderer object : objectRenderers) {
            render.add(object);
        }
    }

    public String getLabel() {
        return label;
    }

    public void dispatch(HudObject object) {
        for (ObjectRenderer renderer : render) {
            if (renderer.getObjectClass().getName().equalsIgnoreCase(object.getClass().getName())) {
                renderer.render(object);
            }
        }

    }
}
