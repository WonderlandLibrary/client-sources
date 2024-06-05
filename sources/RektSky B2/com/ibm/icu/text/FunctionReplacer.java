package com.ibm.icu.text;

class FunctionReplacer implements UnicodeReplacer
{
    private Transliterator translit;
    private UnicodeReplacer replacer;
    
    public FunctionReplacer(final Transliterator theTranslit, final UnicodeReplacer theReplacer) {
        this.translit = theTranslit;
        this.replacer = theReplacer;
    }
    
    @Override
    public int replace(final Replaceable text, final int start, int limit, final int[] cursor) {
        final int len = this.replacer.replace(text, start, limit, cursor);
        limit = start + len;
        limit = this.translit.transliterate(text, start, limit);
        return limit - start;
    }
    
    @Override
    public String toReplacerPattern(final boolean escapeUnprintable) {
        final StringBuilder rule = new StringBuilder("&");
        rule.append(this.translit.getID());
        rule.append("( ");
        rule.append(this.replacer.toReplacerPattern(escapeUnprintable));
        rule.append(" )");
        return rule.toString();
    }
    
    @Override
    public void addReplacementSetTo(final UnicodeSet toUnionTo) {
        toUnionTo.addAll(this.translit.getTargetSet());
    }
}
