package pl.natusiek.amongus.game.structure.queue

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.*

import kotlin.collections.HashSet

data class QueueEntry(
        val id: UUID
) {

    val player: Player
        get() = Bukkit.getPlayer(id)

}