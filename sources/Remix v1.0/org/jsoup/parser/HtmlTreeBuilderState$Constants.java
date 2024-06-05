package org.jsoup.parser;

private static final class Constants
{
    private static final String[] InBodyStartToHead;
    private static final String[] InBodyStartPClosers;
    private static final String[] Headings;
    private static final String[] InBodyStartPreListing;
    private static final String[] InBodyStartLiBreakers;
    private static final String[] DdDt;
    private static final String[] Formatters;
    private static final String[] InBodyStartApplets;
    private static final String[] InBodyStartEmptyFormatters;
    private static final String[] InBodyStartMedia;
    private static final String[] InBodyStartInputAttribs;
    private static final String[] InBodyStartOptions;
    private static final String[] InBodyStartRuby;
    private static final String[] InBodyStartDrop;
    private static final String[] InBodyEndClosers;
    private static final String[] InBodyEndAdoptionFormatters;
    private static final String[] InBodyEndTableFosters;
    
    static {
        InBodyStartToHead = new String[] { "base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "title" };
        InBodyStartPClosers = new String[] { "address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul" };
        Headings = new String[] { "h1", "h2", "h3", "h4", "h5", "h6" };
        InBodyStartPreListing = new String[] { "pre", "listing" };
        InBodyStartLiBreakers = new String[] { "address", "div", "p" };
        DdDt = new String[] { "dd", "dt" };
        Formatters = new String[] { "b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u" };
        InBodyStartApplets = new String[] { "applet", "marquee", "object" };
        InBodyStartEmptyFormatters = new String[] { "area", "br", "embed", "img", "keygen", "wbr" };
        InBodyStartMedia = new String[] { "param", "source", "track" };
        InBodyStartInputAttribs = new String[] { "name", "action", "prompt" };
        InBodyStartOptions = new String[] { "optgroup", "option" };
        InBodyStartRuby = new String[] { "rp", "rt" };
        InBodyStartDrop = new String[] { "caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr" };
        InBodyEndClosers = new String[] { "address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul" };
        InBodyEndAdoptionFormatters = new String[] { "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u" };
        InBodyEndTableFosters = new String[] { "table", "tbody", "tfoot", "thead", "tr" };
    }
}
