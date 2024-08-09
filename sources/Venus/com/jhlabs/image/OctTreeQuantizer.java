/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Quantizer;
import java.io.PrintStream;
import java.util.Vector;

public class OctTreeQuantizer
implements Quantizer {
    static final int MAX_LEVEL = 5;
    private int nodes = 0;
    private OctTreeNode root;
    private int reduceColors;
    private int maximumColors;
    private int colors = 0;
    private Vector[] colorList;

    public OctTreeQuantizer() {
        this.setup(256);
        this.colorList = new Vector[6];
        for (int i = 0; i < 6; ++i) {
            this.colorList[i] = new Vector();
        }
        this.root = new OctTreeNode(this);
    }

    @Override
    public void setup(int n) {
        this.maximumColors = n;
        this.reduceColors = Math.max(512, n * 2);
    }

    @Override
    public void addPixels(int[] nArray, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            this.insertColor(nArray[i + n]);
            if (this.colors <= this.reduceColors) continue;
            this.reduceTree(this.reduceColors);
        }
    }

    @Override
    public int getIndexForColor(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        OctTreeNode octTreeNode = this.root;
        for (int i = 0; i <= 5; ++i) {
            OctTreeNode octTreeNode2;
            int n5 = 128 >> i;
            int n6 = 0;
            if ((n2 & n5) != 0) {
                n6 += 4;
            }
            if ((n3 & n5) != 0) {
                n6 += 2;
            }
            if ((n4 & n5) != 0) {
                ++n6;
            }
            if ((octTreeNode2 = octTreeNode.leaf[n6]) == null) {
                return octTreeNode.index;
            }
            if (octTreeNode2.isLeaf) {
                return octTreeNode2.index;
            }
            octTreeNode = octTreeNode2;
        }
        System.out.println("getIndexForColor failed");
        return 1;
    }

    private void insertColor(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        OctTreeNode octTreeNode = this.root;
        for (int i = 0; i <= 5; ++i) {
            OctTreeNode octTreeNode2;
            int n5 = 128 >> i;
            int n6 = 0;
            if ((n2 & n5) != 0) {
                n6 += 4;
            }
            if ((n3 & n5) != 0) {
                n6 += 2;
            }
            if ((n4 & n5) != 0) {
                ++n6;
            }
            if ((octTreeNode2 = octTreeNode.leaf[n6]) == null) {
                ++octTreeNode.children;
                octTreeNode2 = new OctTreeNode(this);
                octTreeNode2.parent = octTreeNode;
                octTreeNode.leaf[n6] = octTreeNode2;
                octTreeNode.isLeaf = false;
                ++this.nodes;
                this.colorList[i].addElement(octTreeNode2);
                if (i == 5) {
                    octTreeNode2.isLeaf = true;
                    octTreeNode2.count = 1;
                    octTreeNode2.totalRed = n2;
                    octTreeNode2.totalGreen = n3;
                    octTreeNode2.totalBlue = n4;
                    octTreeNode2.level = i;
                    ++this.colors;
                    return;
                }
                octTreeNode = octTreeNode2;
                continue;
            }
            if (octTreeNode2.isLeaf) {
                ++octTreeNode2.count;
                octTreeNode2.totalRed += n2;
                octTreeNode2.totalGreen += n3;
                octTreeNode2.totalBlue += n4;
                return;
            }
            octTreeNode = octTreeNode2;
        }
        System.out.println("insertColor failed");
    }

    private void reduceTree(int n) {
        for (int i = 4; i >= 0; --i) {
            Vector vector = this.colorList[i];
            if (vector == null || vector.size() <= 0) continue;
            for (int j = 0; j < vector.size(); ++j) {
                OctTreeNode octTreeNode = (OctTreeNode)vector.elementAt(j);
                if (octTreeNode.children <= 0) continue;
                for (int k = 0; k < 8; ++k) {
                    OctTreeNode octTreeNode2 = octTreeNode.leaf[k];
                    if (octTreeNode2 == null) continue;
                    if (!octTreeNode2.isLeaf) {
                        System.out.println("not a leaf!");
                    }
                    octTreeNode.count += octTreeNode2.count;
                    octTreeNode.totalRed += octTreeNode2.totalRed;
                    octTreeNode.totalGreen += octTreeNode2.totalGreen;
                    octTreeNode.totalBlue += octTreeNode2.totalBlue;
                    octTreeNode.leaf[k] = null;
                    --octTreeNode.children;
                    --this.colors;
                    --this.nodes;
                    this.colorList[i + 1].removeElement(octTreeNode2);
                }
                octTreeNode.isLeaf = true;
                ++this.colors;
                if (this.colors > n) continue;
                return;
            }
        }
        System.out.println("Unable to reduce the OctTree");
    }

    @Override
    public int[] buildColorTable() {
        int[] nArray = new int[this.colors];
        this.buildColorTable(this.root, nArray, 0);
        return nArray;
    }

    public void buildColorTable(int[] nArray, int[] nArray2) {
        int n = nArray.length;
        this.maximumColors = nArray2.length;
        for (int i = 0; i < n; ++i) {
            this.insertColor(nArray[i]);
            if (this.colors <= this.reduceColors) continue;
            this.reduceTree(this.reduceColors);
        }
        if (this.colors > this.maximumColors) {
            this.reduceTree(this.maximumColors);
        }
        this.buildColorTable(this.root, nArray2, 0);
    }

    private int buildColorTable(OctTreeNode octTreeNode, int[] nArray, int n) {
        if (this.colors > this.maximumColors) {
            this.reduceTree(this.maximumColors);
        }
        if (octTreeNode.isLeaf) {
            int n2 = octTreeNode.count;
            nArray[n] = 0xFF000000 | octTreeNode.totalRed / n2 << 16 | octTreeNode.totalGreen / n2 << 8 | octTreeNode.totalBlue / n2;
            octTreeNode.index = n++;
        } else {
            for (int i = 0; i < 8; ++i) {
                if (octTreeNode.leaf[i] == null) continue;
                octTreeNode.index = n;
                n = this.buildColorTable(octTreeNode.leaf[i], nArray, n);
            }
        }
        return n;
    }

    class OctTreeNode {
        int children;
        int level;
        OctTreeNode parent;
        OctTreeNode[] leaf;
        boolean isLeaf;
        int count;
        int totalRed;
        int totalGreen;
        int totalBlue;
        int index;
        final OctTreeQuantizer this$0;

        OctTreeNode(OctTreeQuantizer octTreeQuantizer) {
            this.this$0 = octTreeQuantizer;
            this.leaf = new OctTreeNode[8];
        }

        public void list(PrintStream printStream, int n) {
            int n2;
            for (n2 = 0; n2 < n; ++n2) {
                System.out.print(' ');
            }
            if (this.count == 0) {
                System.out.println(this.index + ": count=" + this.count);
            } else {
                System.out.println(this.index + ": count=" + this.count + " red=" + this.totalRed / this.count + " green=" + this.totalGreen / this.count + " blue=" + this.totalBlue / this.count);
            }
            for (n2 = 0; n2 < 8; ++n2) {
                if (this.leaf[n2] == null) continue;
                this.leaf[n2].list(printStream, n + 2);
            }
        }
    }
}

