/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.aalto.AsyncByteArrayFeeder
 *  com.fasterxml.aalto.AsyncXMLInputFactory
 *  com.fasterxml.aalto.AsyncXMLStreamReader
 *  com.fasterxml.aalto.stax.InputFactoryImpl
 */
package io.netty.handler.codec.xml;

import com.fasterxml.aalto.AsyncByteArrayFeeder;
import com.fasterxml.aalto.AsyncXMLInputFactory;
import com.fasterxml.aalto.AsyncXMLStreamReader;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.xml.XmlAttribute;
import io.netty.handler.codec.xml.XmlCdata;
import io.netty.handler.codec.xml.XmlCharacters;
import io.netty.handler.codec.xml.XmlComment;
import io.netty.handler.codec.xml.XmlDTD;
import io.netty.handler.codec.xml.XmlDocumentEnd;
import io.netty.handler.codec.xml.XmlDocumentStart;
import io.netty.handler.codec.xml.XmlElementEnd;
import io.netty.handler.codec.xml.XmlElementStart;
import io.netty.handler.codec.xml.XmlEntityReference;
import io.netty.handler.codec.xml.XmlNamespace;
import io.netty.handler.codec.xml.XmlProcessingInstruction;
import io.netty.handler.codec.xml.XmlSpace;
import java.util.List;
import javax.xml.stream.XMLStreamException;

public class XmlDecoder
extends ByteToMessageDecoder {
    private static final AsyncXMLInputFactory XML_INPUT_FACTORY = new InputFactoryImpl();
    private static final XmlDocumentEnd XML_DOCUMENT_END = XmlDocumentEnd.INSTANCE;
    private final AsyncXMLStreamReader<AsyncByteArrayFeeder> streamReader = XML_INPUT_FACTORY.createAsyncForByteArray();
    private final AsyncByteArrayFeeder streamFeeder = (AsyncByteArrayFeeder)this.streamReader.getInputFeeder();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] byArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byArray);
        try {
            this.streamFeeder.feedInput(byArray, 0, byArray.length);
        } catch (XMLStreamException xMLStreamException) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            throw xMLStreamException;
        }
        while (!this.streamFeeder.needMoreInput()) {
            int n = this.streamReader.next();
            switch (n) {
                case 7: {
                    list.add(new XmlDocumentStart(this.streamReader.getEncoding(), this.streamReader.getVersion(), this.streamReader.isStandalone(), this.streamReader.getCharacterEncodingScheme()));
                    break;
                }
                case 8: {
                    list.add(XML_DOCUMENT_END);
                    break;
                }
                case 1: {
                    Object object;
                    int n2;
                    XmlElementStart xmlElementStart = new XmlElementStart(this.streamReader.getLocalName(), this.streamReader.getName().getNamespaceURI(), this.streamReader.getPrefix());
                    for (n2 = 0; n2 < this.streamReader.getAttributeCount(); ++n2) {
                        object = new XmlAttribute(this.streamReader.getAttributeType(n2), this.streamReader.getAttributeLocalName(n2), this.streamReader.getAttributePrefix(n2), this.streamReader.getAttributeNamespace(n2), this.streamReader.getAttributeValue(n2));
                        xmlElementStart.attributes().add((XmlAttribute)object);
                    }
                    for (n2 = 0; n2 < this.streamReader.getNamespaceCount(); ++n2) {
                        object = new XmlNamespace(this.streamReader.getNamespacePrefix(n2), this.streamReader.getNamespaceURI(n2));
                        xmlElementStart.namespaces().add((XmlNamespace)object);
                    }
                    list.add(xmlElementStart);
                    break;
                }
                case 2: {
                    XmlElementEnd xmlElementEnd = new XmlElementEnd(this.streamReader.getLocalName(), this.streamReader.getName().getNamespaceURI(), this.streamReader.getPrefix());
                    for (int i = 0; i < this.streamReader.getNamespaceCount(); ++i) {
                        XmlNamespace xmlNamespace = new XmlNamespace(this.streamReader.getNamespacePrefix(i), this.streamReader.getNamespaceURI(i));
                        xmlElementEnd.namespaces().add(xmlNamespace);
                    }
                    list.add(xmlElementEnd);
                    break;
                }
                case 3: {
                    list.add(new XmlProcessingInstruction(this.streamReader.getPIData(), this.streamReader.getPITarget()));
                    break;
                }
                case 4: {
                    list.add(new XmlCharacters(this.streamReader.getText()));
                    break;
                }
                case 5: {
                    list.add(new XmlComment(this.streamReader.getText()));
                    break;
                }
                case 6: {
                    list.add(new XmlSpace(this.streamReader.getText()));
                    break;
                }
                case 9: {
                    list.add(new XmlEntityReference(this.streamReader.getLocalName(), this.streamReader.getText()));
                    break;
                }
                case 11: {
                    list.add(new XmlDTD(this.streamReader.getText()));
                    break;
                }
                case 12: {
                    list.add(new XmlCdata(this.streamReader.getText()));
                }
            }
        }
    }
}

