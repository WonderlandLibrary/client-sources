package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

static class MixinAppender extends AbstractAppender
{
    protected MixinAppender(final String s, final Filter filter, final Layout<? extends Serializable> layout) {
        super(s, filter, (Layout)layout);
    }
    
    public void append(final LogEvent logEvent) {
        if (logEvent.getLevel() == Level.DEBUG && "Validating minecraft".equals(logEvent.getMessage().getFormat())) {
            MixinEnvironment.gotoPhase(Phase.INIT);
        }
    }
}
