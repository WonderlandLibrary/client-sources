package argo.jdom;

final class JsonNodeBuilders_Null implements JsonNodeBuilder
{
    @Override
    public JsonNode buildNode() {
        return JsonNodeFactories.aJsonNull();
    }
}
