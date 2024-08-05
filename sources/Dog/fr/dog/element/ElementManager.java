package fr.dog.element;

import fr.dog.util.render.RenderUtil;

import java.util.ArrayList;

public class ElementManager {
    private final ArrayList<Element> elements = new ArrayList<>();

    public final void addElement(Element element) {
        elements.add(element);
    }

    public final void drawAll(){

        elements.forEach(element -> {
            final float x = (float) element.animation.getValue() * element.getPlacement().getXFactor();
            final float y = (float) element.animation.getValue() * element.getPlacement().getYFactor();

            RenderUtil.translate(element::draw,x,y);

            if(element.isFinished()) elements.remove(element);
        });

    }


}
