package com.google.common.html;

import com.google.common.annotations.*;
import com.google.common.escape.*;

@Beta
@GwtCompatible
public final class HtmlEscapers
{
    private static final Escaper HTML_ESCAPER;
    
    public static Escaper htmlEscaper() {
        return HtmlEscapers.HTML_ESCAPER;
    }
    
    private HtmlEscapers() {
    }
    
    static {
        HTML_ESCAPER = Escapers.builder().addEscape('\"', "&quot;").addEscape('\'', "&#39;").addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;").build();
    }
}
