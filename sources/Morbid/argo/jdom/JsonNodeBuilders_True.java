package argo.jdom;

final class JsonNodeBuilders_True implements JsonNodeBuilder
{
    @Override
    public JsonNode buildNode() {
        return JsonNodeFactories.aJsonTrue();
    }
}
