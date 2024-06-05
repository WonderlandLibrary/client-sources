package org.jsoup.parser;

import java.util.*;
import org.jsoup.nodes.*;

public class Parser
{
    private static final int DEFAULT_MAX_ERRORS = 0;
    private TreeBuilder treeBuilder;
    private int maxErrors;
    private ParseErrorList errors;
    private ParseSettings settings;
    
    public Parser(final TreeBuilder treeBuilder) {
        this.maxErrors = 0;
        this.treeBuilder = treeBuilder;
        this.settings = treeBuilder.defaultSettings();
    }
    
    public Document parseInput(final String html, final String baseUri) {
        this.errors = (this.isTrackErrors() ? ParseErrorList.tracking(this.maxErrors) : ParseErrorList.noTracking());
        return this.treeBuilder.parse(html, baseUri, this.errors, this.settings);
    }
    
    public TreeBuilder getTreeBuilder() {
        return this.treeBuilder;
    }
    
    public Parser setTreeBuilder(final TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
        return this;
    }
    
    public boolean isTrackErrors() {
        return this.maxErrors > 0;
    }
    
    public Parser setTrackErrors(final int maxErrors) {
        this.maxErrors = maxErrors;
        return this;
    }
    
    public List<ParseError> getErrors() {
        return this.errors;
    }
    
    public Parser settings(final ParseSettings settings) {
        this.settings = settings;
        return this;
    }
    
    public ParseSettings settings() {
        return this.settings;
    }
    
    public static Document parse(final String html, final String baseUri) {
        final TreeBuilder treeBuilder = new HtmlTreeBuilder();
        return treeBuilder.parse(html, baseUri, ParseErrorList.noTracking(), treeBuilder.defaultSettings());
    }
    
    public static List<Node> parseFragment(final String fragmentHtml, final Element context, final String baseUri) {
        final HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
        return treeBuilder.parseFragment(fragmentHtml, context, baseUri, ParseErrorList.noTracking(), treeBuilder.defaultSettings());
    }
    
    public static List<Node> parseFragment(final String fragmentHtml, final Element context, final String baseUri, final ParseErrorList errorList) {
        final HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
        return treeBuilder.parseFragment(fragmentHtml, context, baseUri, errorList, treeBuilder.defaultSettings());
    }
    
    public static List<Node> parseXmlFragment(final String fragmentXml, final String baseUri) {
        final XmlTreeBuilder treeBuilder = new XmlTreeBuilder();
        return treeBuilder.parseFragment(fragmentXml, baseUri, ParseErrorList.noTracking(), treeBuilder.defaultSettings());
    }
    
    public static Document parseBodyFragment(final String bodyHtml, final String baseUri) {
        final Document doc = Document.createShell(baseUri);
        final Element body = doc.body();
        final List<Node> nodeList = parseFragment(bodyHtml, body, baseUri);
        final Node[] nodes = nodeList.toArray(new Node[nodeList.size()]);
        for (int i = nodes.length - 1; i > 0; --i) {
            nodes[i].remove();
        }
        for (final Node node : nodes) {
            body.appendChild(node);
        }
        return doc;
    }
    
    public static String unescapeEntities(final String string, final boolean inAttribute) {
        final Tokeniser tokeniser = new Tokeniser(new CharacterReader(string), ParseErrorList.noTracking());
        return tokeniser.unescapeEntities(inAttribute);
    }
    
    @Deprecated
    public static Document parseBodyFragmentRelaxed(final String bodyHtml, final String baseUri) {
        return parse(bodyHtml, baseUri);
    }
    
    public static Parser htmlParser() {
        return new Parser(new HtmlTreeBuilder());
    }
    
    public static Parser xmlParser() {
        return new Parser(new XmlTreeBuilder());
    }
}
