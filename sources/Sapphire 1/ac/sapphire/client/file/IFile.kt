package ac.sapphire.client.file

interface IFile {
    val name: String

    fun serialize(): String

    fun deserialize(data: String)
}