package pl.natusiek.amongus.game.structure.impostor

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.ItemBuilder
import java.util.*
import kotlin.collections.HashSet

data class Impostor(
        val impostors: HashSet<UUID>
) {

    private val players: Sequence<Player>
        get() = this.impostors.asSequence().mapNotNull { Bukkit.getPlayer(it) }

    fun removeImpostor(uniqueId: UUID) = this.impostors.remove(uniqueId)

    fun isImpostor(uniqueId: UUID) = this.impostors.contains(uniqueId)

    fun addKnife() = this.players.forEach { it.inventory.setItem(4, ItemBuilder(Material.IRON_SWORD, "&cKill").build()) }

}