package pl.natusiek.amongus.game.inventory.task

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material

import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import java.util.concurrent.atomic.AtomicInteger

class ThrowingGarbageInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    private var click = 0

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
            .id("throwingGarbarge")
            .provider(ThrowingGarbageInventoryProvider(plugin))
            .size(4, 9)
            .title(" &8* &eWyrzuć śmieci.")
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        repeat((0..8).count()) {
            val row = (1..4).random()
            val column = (0..8).random()
            val slotPos = SlotPos(row, column)
            contents.set(slotPos, ClickableItem.of(ItemBuilder(Material.SAPLING, "&0ŚMIEĆ", 1, row.toShort()).build()) {
                contents.set(slotPos, ClickableItem.empty(null))
                this.click += 1
                if (this.click == 8) {
                    player.closeInventory()
                    val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!
                    arena.addAndGetProgress(2)
                }
            })
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}