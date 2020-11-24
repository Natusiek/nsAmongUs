package pl.natusiek.amongus.game.repositories

import org.apache.commons.io.FileUtils
import org.bukkit.*
import org.bukkit.World.Environment
import org.bukkit.util.FileUtil
import pl.natusiek.amongus.common.builder.LocationBuilder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class ArenaRepository(private val plugin: AmongUsPlugin) {

    val arenas: HashMap<Int, Arena> = hashMapOf()

    private val folder = File(this.plugin.dataFolder, "worlds")
    private val world = File(this.folder, "AmongUs")

    init {
        if (!this.folder.exists()) this.folder.mkdirs()
        if (!this.world.exists()) this.world.mkdirs()
    }

    private fun addArena(arena: Arena) = this.arenas.put(arena.arenaId, arena)

    private fun getArenaBy(arena: (Arena) -> Boolean) = this.arenas.values.find(arena)

    fun getArenaByMemberId(memberId: UUID) = this.getArenaBy { it.members.singleOrNull { it.uniqueId == memberId } != null }

    fun getArenaById(id: Int) = this.getArenaBy { it.arenaId == id }


    fun removeArena(id: Int) {
        val worldName = "AmongUs-${id}"

        this.arenas.remove(id)
        this.plugin.server.unloadWorld(worldName, false)
        FileUtils.deleteDirectory(File(this.folder, worldName))
    }

    fun createArena(arena: Arena) {

        print("Arena create ${arena.arenaId}")

        this.addArena(arena)
        this.createWorld("AmongUs-${arena.arenaId}")

        this.plugin.queueRepository.removeQueueById(arena.arenaId)
    }

    private fun createWorld(worldName: String) {
        val destFile = File(this.plugin.server.worldContainer, worldName)
        WorldCreator(worldName)
                .environment(Environment.NORMAL)
                .generateStructures(false)
                .type(WorldType.FLAT)
                .createWorld().apply {
                    this.isAutoSave = false
                    this.setSpawnFlags(false, false)
                    this.difficulty = Difficulty.PEACEFUL
                    this.pvp = false
                }
        FileUtils.copyDirectory(this.world, destFile)
    }


}