package org.jsoup.select;

import org.jsoup.nodes.*;

public interface NodeVisitor
{
    void head(final Node p0, final int p1);
    
    void tail(final Node p0, final int p1);
}
