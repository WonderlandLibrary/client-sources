package ninja.leaping.configurate.attributed;

import java.util.List;
import java.util.Map;
import ninja.leaping.configurate.ConfigurationNode;

public interface AttributedConfigurationNode extends ConfigurationNode {
   String getTagName();

   AttributedConfigurationNode setTagName(String var1);

   AttributedConfigurationNode addAttribute(String var1, String var2);

   AttributedConfigurationNode removeAttribute(String var1);

   boolean hasAttributes();

   String getAttribute(String var1);

   Map getAttributes();

   AttributedConfigurationNode setAttributes(Map var1);

   AttributedConfigurationNode getParent();

   List getChildrenList();

   Map getChildrenMap();

   AttributedConfigurationNode setValue(Object var1);

   AttributedConfigurationNode mergeValuesFrom(ConfigurationNode var1);

   AttributedConfigurationNode getAppendedNode();

   AttributedConfigurationNode getNode(Object... var1);

   AttributedConfigurationNode copy();
}
