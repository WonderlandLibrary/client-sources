package org.jsoup.nodes;

import org.jsoup.helper.*;

private final class NodeList extends ChangeNotifyingArrayList<Node>
{
    NodeList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    @Override
    public void onContentsChanged() {
        Node.this.nodelistChanged();
    }
}
