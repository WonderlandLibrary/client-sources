package org.spongepowered.tools.obfuscation;

import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.struct.*;
import java.util.*;

static class AnnotatedElementInjectionPoint extends AnnotatedElement<ExecutableElement>
{
    private final AnnotationHandle at;
    private Map<String, String> args;
    private final InjectorRemap state;
    
    public AnnotatedElementInjectionPoint(final ExecutableElement executableElement, final AnnotationHandle annotationHandle, final AnnotationHandle at, final InjectorRemap state) {
        super(executableElement, annotationHandle);
        this.at = at;
        this.state = state;
    }
    
    public boolean shouldRemap() {
        return this.at.getBoolean("remap", this.state.shouldRemap());
    }
    
    public AnnotationHandle getAt() {
        return this.at;
    }
    
    public String getAtArg(final String s) {
        if (this.args == null) {
            this.args = new HashMap<String, String>();
            for (final String s2 : this.at.getList("args")) {
                if (s2 == null) {
                    continue;
                }
                final int index = s2.indexOf(61);
                if (index > -1) {
                    this.args.put(s2.substring(0, index), s2.substring(index + 1));
                }
                else {
                    this.args.put(s2, "");
                }
            }
        }
        return this.args.get(s);
    }
    
    public void notifyRemapped() {
        this.state.notifyRemapped();
    }
}
