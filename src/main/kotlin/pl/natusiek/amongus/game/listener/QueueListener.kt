package pl.natusiek.amongus.game.listener

import org.bukkit.Material.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.inventory.ChangeColorInventoryProvider
import pl.natusiek.amongus.game.inventory.EmergencyInventoryProvider

class QueueListener(private val plugin: AmongUsPlugin): Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player

        val itemInHand = player.itemInHand ?: return
        val action = event.action
        if (action === Action.RIGHT_CLICK_AIR) {
            when(itemInHand.type) {
                DIAMOND -> this.plugin.queueRepository.addToQueue(player)
                BOOK -> ChangeColorInventoryProvider.getInventory(this.plugin).open(player)
                PAPER -> EmergencyInventoryProvider.getInventory(this.plugin).open(player)
                else -> {

                }
            }
        }
    }

}