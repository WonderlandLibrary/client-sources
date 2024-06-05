package org.jsoup.nodes;

import java.io.*;

public class DataNode extends Node
{
    private static final String DATA_KEY = "data";
    
    public DataNode(final String data, final String baseUri) {
        super(baseUri);
        this.attributes.put("data", data);
    }
    
    @Override
    public String nodeName() {
        return "#data";
    }
    
    public String getWholeData() {
        return this.attributes.get("data");
    }
    
    public DataNode setWholeData(final String data) {
        this.attributes.put("data", data);
        return this;
    }
    
    @Override
    void outerHtmlHead(final Appendable accum, final int depth, final Document.OutputSettings out) throws IOException {
        accum.append(this.getWholeData());
    }
    
    @Override
    void outerHtmlTail(final Appendable accum, final int depth, final Document.OutputSettings out) {
    }
    
    @Override
    public String toString() {
        return this.outerHtml();
    }
    
    public static DataNode createFromEncoded(final String encodedData, final String baseUri) {
        final String data = Entities.unescape(encodedData);
        return new DataNode(data, baseUri);
    }
}
