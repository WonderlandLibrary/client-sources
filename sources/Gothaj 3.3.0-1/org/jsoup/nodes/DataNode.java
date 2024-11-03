package org.jsoup.nodes;

import java.io.IOException;

public class DataNode extends LeafNode {
   public DataNode(String data) {
      this.value = data;
   }

   @Override
   public String nodeName() {
      return "#data";
   }

   public String getWholeData() {
      return this.coreValue();
   }

   public DataNode setWholeData(String data) {
      this.coreValue(data);
      return this;
   }

   @Override
   void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
      accum.append(this.getWholeData());
   }

   @Override
   void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) {
   }

   @Override
   public String toString() {
      return this.outerHtml();
   }

   public DataNode clone() {
      return (DataNode)super.clone();
   }
}
