/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.HpackUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;

final class HpackHuffmanDecoder {
    private static final Http2Exception EOS_DECODED = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - EOS Decoded", new Object[0]), HpackHuffmanDecoder.class, "decode(..)");
    private static final Http2Exception INVALID_PADDING = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - Invalid Padding", new Object[0]), HpackHuffmanDecoder.class, "decode(..)");
    private static final Node ROOT = HpackHuffmanDecoder.buildTree(HpackUtil.HUFFMAN_CODES, HpackUtil.HUFFMAN_CODE_LENGTHS);
    private final DecoderProcessor processor;

    HpackHuffmanDecoder(int n) {
        this.processor = new DecoderProcessor(n);
    }

    public AsciiString decode(ByteBuf byteBuf, int n) throws Http2Exception {
        this.processor.reset();
        byteBuf.forEachByte(byteBuf.readerIndex(), n, this.processor);
        byteBuf.skipBytes(n);
        return this.processor.end();
    }

    private static Node buildTree(int[] nArray, byte[] byArray) {
        Node node = new Node();
        for (int i = 0; i < nArray.length; ++i) {
            HpackHuffmanDecoder.insert(node, i, nArray[i], byArray[i]);
        }
        return node;
    }

    private static void insert(Node node, int n, int n2, byte by) {
        Node node2 = node;
        while (by > 8) {
            if (Node.access$000(node2)) {
                throw new IllegalStateException("invalid Huffman code: prefix not unique");
            }
            by = (byte)(by - 8);
            int n3 = n2 >>> by & 0xFF;
            if (Node.access$100(node2)[n3] == null) {
                Node.access$100((Node)node2)[n3] = new Node();
            }
            node2 = Node.access$100(node2)[n3];
        }
        Node node3 = new Node(n, by);
        int n4 = 8 - by;
        int n5 = n2 << n4 & 0xFF;
        int n6 = 1 << n4;
        for (int i = n5; i < n5 + n6; ++i) {
            Node.access$100((Node)node2)[i] = node3;
        }
    }

    static Node access$200() {
        return ROOT;
    }

    static Http2Exception access$500() {
        return EOS_DECODED;
    }

    static Http2Exception access$600() {
        return INVALID_PADDING;
    }

    private static final class DecoderProcessor
    implements ByteProcessor {
        private final int initialCapacity;
        private byte[] bytes;
        private int index;
        private Node node;
        private int current;
        private int currentBits;
        private int symbolBits;

        DecoderProcessor(int n) {
            this.initialCapacity = ObjectUtil.checkPositive(n, "initialCapacity");
        }

        void reset() {
            this.node = HpackHuffmanDecoder.access$200();
            this.current = 0;
            this.currentBits = 0;
            this.symbolBits = 0;
            this.bytes = new byte[this.initialCapacity];
            this.index = 0;
        }

        @Override
        public boolean process(byte by) throws Http2Exception {
            this.current = this.current << 8 | by & 0xFF;
            this.currentBits += 8;
            this.symbolBits += 8;
            do {
                this.node = Node.access$100(this.node)[this.current >>> this.currentBits - 8 & 0xFF];
                this.currentBits -= Node.access$300(this.node);
                if (!Node.access$000(this.node)) continue;
                if (Node.access$400(this.node) == 256) {
                    throw HpackHuffmanDecoder.access$500();
                }
                this.append(Node.access$400(this.node));
                this.node = HpackHuffmanDecoder.access$200();
                this.symbolBits = this.currentBits;
            } while (this.currentBits >= 8);
            return false;
        }

        AsciiString end() throws Http2Exception {
            while (this.currentBits > 0) {
                this.node = Node.access$100(this.node)[this.current << 8 - this.currentBits & 0xFF];
                if (!Node.access$000(this.node) || Node.access$300(this.node) > this.currentBits) break;
                if (Node.access$400(this.node) == 256) {
                    throw HpackHuffmanDecoder.access$500();
                }
                this.currentBits -= Node.access$300(this.node);
                this.append(Node.access$400(this.node));
                this.node = HpackHuffmanDecoder.access$200();
                this.symbolBits = this.currentBits;
            }
            int n = (1 << this.symbolBits) - 1;
            if (this.symbolBits > 7 || (this.current & n) != n) {
                throw HpackHuffmanDecoder.access$600();
            }
            return new AsciiString(this.bytes, 0, this.index, false);
        }

        private void append(int n) {
            if (this.bytes.length == this.index) {
                int n2 = this.bytes.length >= 1024 ? this.bytes.length + this.initialCapacity : this.bytes.length << 1;
                byte[] byArray = new byte[n2];
                System.arraycopy(this.bytes, 0, byArray, 0, this.bytes.length);
                this.bytes = byArray;
            }
            this.bytes[this.index++] = (byte)n;
        }
    }

    private static final class Node {
        private final int symbol;
        private final int bits;
        private final Node[] children;
        static final boolean $assertionsDisabled = !HpackHuffmanDecoder.class.desiredAssertionStatus();

        Node() {
            this.symbol = 0;
            this.bits = 8;
            this.children = new Node[256];
        }

        Node(int n, int n2) {
            if (!($assertionsDisabled || n2 > 0 && n2 <= 8)) {
                throw new AssertionError();
            }
            this.symbol = n;
            this.bits = n2;
            this.children = null;
        }

        private boolean isTerminal() {
            return this.children == null;
        }

        static boolean access$000(Node node) {
            return node.isTerminal();
        }

        static Node[] access$100(Node node) {
            return node.children;
        }

        static int access$300(Node node) {
            return node.bits;
        }

        static int access$400(Node node) {
            return node.symbol;
        }
    }
}

