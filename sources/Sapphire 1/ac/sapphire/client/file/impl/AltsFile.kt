package ac.sapphire.client.file.impl

import ac.sapphire.client.alt.AltFactory
import ac.sapphire.client.alt.AltManager
import ac.sapphire.client.file.IFile
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory

class AltsFile(private val altManager: AltManager) : IFile {
    override val name: String = "alts"

    private val mapper = ObjectMapper()

    override fun serialize(): String {
        val array = JsonNodeFactory.instance.arrayNode()

        for (alt in altManager.getAll()) {
            array.add(alt.serialize())
        }

        return array.toString()
    }

    override fun deserialize(data: String) {
        val root = mapper.readTree(data)
        for (node in root) {
            val alt = AltFactory.deserialize(node)
            altManager.add(alt)
        }
    }
}