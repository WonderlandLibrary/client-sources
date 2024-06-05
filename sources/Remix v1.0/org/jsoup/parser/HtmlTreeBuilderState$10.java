package org.jsoup.parser;

import org.jsoup.helper.*;
import java.util.*;

enum HtmlTreeBuilderState$10
{
    @Override
    boolean process(final Token t, final HtmlTreeBuilder tb) {
        switch (t.type) {
            case Character: {
                final Token.Character c = t.asCharacter();
                if (c.getData().equals(HtmlTreeBuilderState.access$400())) {
                    tb.error(this);
                    return false;
                }
                tb.getPendingTableCharacters().add(c.getData());
                return true;
            }
            default: {
                if (tb.getPendingTableCharacters().size() > 0) {
                    for (final String character : tb.getPendingTableCharacters()) {
                        if (!HtmlTreeBuilderState.access$2200(character)) {
                            tb.error(this);
                            if (StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                tb.setFosterInserts(true);
                                tb.process(new Token.Character().data(character), HtmlTreeBuilderState$10.InBody);
                                tb.setFosterInserts(false);
                            }
                            else {
                                tb.process(new Token.Character().data(character), HtmlTreeBuilderState$10.InBody);
                            }
                        }
                        else {
                            tb.insert(new Token.Character().data(character));
                        }
                    }
                    tb.newPendingTableCharacters();
                }
                tb.transition(tb.originalState());
                return tb.process(t);
            }
        }
    }
}