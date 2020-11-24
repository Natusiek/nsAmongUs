package pl.natusiek.amongus.game.inventory

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import fr.minuskube.inv.content.SlotPos
import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.HeadBuilder
import pl.natusiek.amongus.common.extension.fillBorder
import pl.natusiek.amongus.common.extension.fillRect
import pl.natusiek.amongus.common.extension.sendFixedMessage
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena

class EmergencyInventoryProvider(private val plugin: AmongUsPlugin): InventoryProvider {

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
                .id("emergencyProvider")
                .provider(EmergencyInventoryProvider(plugin))
                .size(5, 9)
                .title(" &8* &cWho is Impostor?")
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()
        contents.fillRect(SlotPos(2,2), SlotPos(4, 8))

        val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!

        arena.members.filter { !it.died }.forEach {
            val head = HeadBuilder("&a✔ &f${it.name}", it.name, arrayListOf("", " &8* &eMasz pewność że to on?", "")).build()
            contents.add(ClickableItem.of(head) { _ ->
                if (arena.emergency!!.heVote(player.uniqueId)) {
                    player.closeInventory()
                    player.sendFixedMessage(" &8* &eJuż odałeś swój głos")
                    return@of
                }
                arena.emergency!!.addVote(it.uniqueId)
                player.closeInventory()
            })
            arena.emergency!!.addCandidate(it.uniqueId)
        }
        arena.murdered.forEach {
            val head = HeadBuilder("&c✖ &f${it.name}", it.name, arrayListOf("", " &8* &ePoległy", "")).build()
            contents.add(ClickableItem.empty(head))
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }
}