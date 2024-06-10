package ac.sapphire.client.file

import ac.sapphire.client.Sapphire
import ac.sapphire.client.alt.AltManager
import ac.sapphire.client.file.impl.AltsFile
import java.io.File
import java.nio.file.Files

class FileManager(altManager: AltManager) {
    private val files: Array<IFile> = arrayOf(
        AltsFile(altManager)
    )

    private val nativeFiles = hashMapOf<String, File>().also { map ->
        files.forEach {
            map[it.name] = File(Sapphire.dataDir, it.name + ".json")
        }
    }

    fun saveAll() {
        try {
            files.forEach {
                val path = nativeFiles[it.name]!!.toPath()

                Files.deleteIfExists(path)
                Files.createDirectories(path.parent)
                Files.write(path, it.serialize().encodeToByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadAll() {
        try {
            files.forEach {
                val nativeFile = nativeFiles[it.name]!!
                if (nativeFile.exists()) {
                    val contentBytes = Files.readAllBytes(nativeFile.toPath())
                    val content = contentBytes.decodeToString()

                    it.deserialize(content)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}