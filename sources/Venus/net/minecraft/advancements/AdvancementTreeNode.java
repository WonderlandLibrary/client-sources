/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;

public class AdvancementTreeNode {
    private final Advancement advancement;
    private final AdvancementTreeNode parent;
    private final AdvancementTreeNode sibling;
    private final int index;
    private final List<AdvancementTreeNode> children = Lists.newArrayList();
    private AdvancementTreeNode ancestor;
    private AdvancementTreeNode thread;
    private int x;
    private float y;
    private float mod;
    private float change;
    private float shift;

    public AdvancementTreeNode(Advancement advancement, @Nullable AdvancementTreeNode advancementTreeNode, @Nullable AdvancementTreeNode advancementTreeNode2, int n, int n2) {
        if (advancement.getDisplay() == null) {
            throw new IllegalArgumentException("Can't position an invisible advancement!");
        }
        this.advancement = advancement;
        this.parent = advancementTreeNode;
        this.sibling = advancementTreeNode2;
        this.index = n;
        this.ancestor = this;
        this.x = n2;
        this.y = -1.0f;
        AdvancementTreeNode advancementTreeNode3 = null;
        for (Advancement advancement2 : advancement.getChildren()) {
            advancementTreeNode3 = this.buildSubTree(advancement2, advancementTreeNode3);
        }
    }

    @Nullable
    private AdvancementTreeNode buildSubTree(Advancement advancement, @Nullable AdvancementTreeNode advancementTreeNode) {
        if (advancement.getDisplay() != null) {
            advancementTreeNode = new AdvancementTreeNode(advancement, this, advancementTreeNode, this.children.size() + 1, this.x + 1);
            this.children.add(advancementTreeNode);
        } else {
            for (Advancement advancement2 : advancement.getChildren()) {
                advancementTreeNode = this.buildSubTree(advancement2, advancementTreeNode);
            }
        }
        return advancementTreeNode;
    }

    private void firstWalk() {
        if (this.children.isEmpty()) {
            this.y = this.sibling != null ? this.sibling.y + 1.0f : 0.0f;
        } else {
            AdvancementTreeNode advancementTreeNode = null;
            for (AdvancementTreeNode advancementTreeNode2 : this.children) {
                advancementTreeNode2.firstWalk();
                advancementTreeNode = advancementTreeNode2.apportion(advancementTreeNode == null ? advancementTreeNode2 : advancementTreeNode);
            }
            this.executeShifts();
            float f = (this.children.get((int)0).y + this.children.get((int)(this.children.size() - 1)).y) / 2.0f;
            if (this.sibling != null) {
                this.y = this.sibling.y + 1.0f;
                this.mod = this.y - f;
            } else {
                this.y = f;
            }
        }
    }

    private float secondWalk(float f, int n, float f2) {
        this.y += f;
        this.x = n;
        if (this.y < f2) {
            f2 = this.y;
        }
        for (AdvancementTreeNode advancementTreeNode : this.children) {
            f2 = advancementTreeNode.secondWalk(f + this.mod, n + 1, f2);
        }
        return f2;
    }

    private void thirdWalk(float f) {
        this.y += f;
        for (AdvancementTreeNode advancementTreeNode : this.children) {
            advancementTreeNode.thirdWalk(f);
        }
    }

    private void executeShifts() {
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = this.children.size() - 1; i >= 0; --i) {
            AdvancementTreeNode advancementTreeNode = this.children.get(i);
            advancementTreeNode.y += f;
            advancementTreeNode.mod += f;
            f += advancementTreeNode.shift + (f2 += advancementTreeNode.change);
        }
    }

    @Nullable
    private AdvancementTreeNode getFirstChild() {
        if (this.thread != null) {
            return this.thread;
        }
        return !this.children.isEmpty() ? this.children.get(0) : null;
    }

    @Nullable
    private AdvancementTreeNode getLastChild() {
        if (this.thread != null) {
            return this.thread;
        }
        return !this.children.isEmpty() ? this.children.get(this.children.size() - 1) : null;
    }

    private AdvancementTreeNode apportion(AdvancementTreeNode advancementTreeNode) {
        if (this.sibling == null) {
            return advancementTreeNode;
        }
        AdvancementTreeNode advancementTreeNode2 = this;
        AdvancementTreeNode advancementTreeNode3 = this;
        AdvancementTreeNode advancementTreeNode4 = this.sibling;
        AdvancementTreeNode advancementTreeNode5 = this.parent.children.get(0);
        float f = this.mod;
        float f2 = this.mod;
        float f3 = advancementTreeNode4.mod;
        float f4 = advancementTreeNode5.mod;
        while (advancementTreeNode4.getLastChild() != null && advancementTreeNode2.getFirstChild() != null) {
            advancementTreeNode4 = advancementTreeNode4.getLastChild();
            advancementTreeNode2 = advancementTreeNode2.getFirstChild();
            advancementTreeNode5 = advancementTreeNode5.getFirstChild();
            advancementTreeNode3 = advancementTreeNode3.getLastChild();
            advancementTreeNode3.ancestor = this;
            float f5 = advancementTreeNode4.y + f3 - (advancementTreeNode2.y + f) + 1.0f;
            if (f5 > 0.0f) {
                advancementTreeNode4.getAncestor(this, advancementTreeNode).moveSubtree(this, f5);
                f += f5;
                f2 += f5;
            }
            f3 += advancementTreeNode4.mod;
            f += advancementTreeNode2.mod;
            f4 += advancementTreeNode5.mod;
            f2 += advancementTreeNode3.mod;
        }
        if (advancementTreeNode4.getLastChild() != null && advancementTreeNode3.getLastChild() == null) {
            advancementTreeNode3.thread = advancementTreeNode4.getLastChild();
            advancementTreeNode3.mod += f3 - f2;
        } else {
            if (advancementTreeNode2.getFirstChild() != null && advancementTreeNode5.getFirstChild() == null) {
                advancementTreeNode5.thread = advancementTreeNode2.getFirstChild();
                advancementTreeNode5.mod += f - f4;
            }
            advancementTreeNode = this;
        }
        return advancementTreeNode;
    }

    private void moveSubtree(AdvancementTreeNode advancementTreeNode, float f) {
        float f2 = advancementTreeNode.index - this.index;
        if (f2 != 0.0f) {
            advancementTreeNode.change -= f / f2;
            this.change += f / f2;
        }
        advancementTreeNode.shift += f;
        advancementTreeNode.y += f;
        advancementTreeNode.mod += f;
    }

    private AdvancementTreeNode getAncestor(AdvancementTreeNode advancementTreeNode, AdvancementTreeNode advancementTreeNode2) {
        return this.ancestor != null && advancementTreeNode.parent.children.contains(this.ancestor) ? this.ancestor : advancementTreeNode2;
    }

    private void updatePosition() {
        if (this.advancement.getDisplay() != null) {
            this.advancement.getDisplay().setPosition(this.x, this.y);
        }
        if (!this.children.isEmpty()) {
            for (AdvancementTreeNode advancementTreeNode : this.children) {
                advancementTreeNode.updatePosition();
            }
        }
    }

    public static void layout(Advancement advancement) {
        if (advancement.getDisplay() == null) {
            throw new IllegalArgumentException("Can't position children of an invisible root!");
        }
        AdvancementTreeNode advancementTreeNode = new AdvancementTreeNode(advancement, null, null, 1, 0);
        advancementTreeNode.firstWalk();
        float f = advancementTreeNode.secondWalk(0.0f, 0, advancementTreeNode.y);
        if (f < 0.0f) {
            advancementTreeNode.thirdWalk(-f);
        }
        advancementTreeNode.updatePosition();
    }
}

