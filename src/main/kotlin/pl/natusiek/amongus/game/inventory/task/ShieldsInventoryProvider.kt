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

class ShieldsInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    private var click = 0

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
                .id("shieldsTask")
            .provider(ShieldsInventoryProvider(plugin))
                .size(5, 9)
                .title(" &8* &eRatowanie świata.")
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        var row = 2
        var column = 4

        val item = ItemBuilder(Material.STAINED_GLASS_PANE, "&4KLICK", 1, 14).build()
        val ready = ItemBuilder(Material.STAINED_GLASS_PANE, "&aGOTOWE").build()
        (0..7)
            .map { SlotPos(row, column)
                .also {
                    row += 1
                    column += 1
                    if (row == 4) { row = 2 }

                    if (column == 6) { column = 4 } }
            }.forEach {
                contents.set(it, ClickableItem.of(item) { _ ->
                    contents.set(it, ClickableItem.empty(ready))
                    this.click += 1
                    if (this.click == 7) {
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