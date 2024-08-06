package club.strifeclient.config;

import club.strifeclient.util.callback.ReturnVariableCallback;
import club.strifeclient.util.callback.VariableCallback;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLParser {
    private TransformerFactory transformerFactory;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Transformer transformer;

    public void setupParser() throws ParserConfigurationException, TransformerConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        transformerFactory = TransformerFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        transformerFactory.setAttribute("indent-number", 2);
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    }

    public void readFile(String path, VariableCallback<Document> callback) throws IOException, SAXException {
        Document document = documentBuilder.parse(new File(path));
        document.getDocumentElement().normalize();
        callback.callback(document);
    }

    public void writeFile(String path, ReturnVariableCallback<Document> callback) throws TransformerException {
        Document document = documentBuilder.newDocument();
        callback.callback(document);
        DOMSource domSource = new DOMSource(callback.getCallback());
        StreamResult streamResult = new StreamResult(new File(path));
        transformer.transform(domSource, streamResult);
    }

    public void dispose() {
        transformerFactory = null;
        documentBuilderFactory = null;
        documentBuilder = null;
        transformer = null;
        System.gc();
    }
}
