package argo.jdom;

final class JsonNodeBuilders_False implements JsonNodeBuilder
{
    @Override
    public JsonNode buildNode() {
        return JsonNodeFactories.aJsonFalse();
    }
}
