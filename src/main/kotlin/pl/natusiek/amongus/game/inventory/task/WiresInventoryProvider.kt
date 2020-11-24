package pl.natusiek.amongus.game.inventory.task

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.common.extension.fillBorder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena

class WiresInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    private val glasses = arrayListOf(
            ItemBuilder(Material.STAINED_GLASS_PANE, "&4CZERWONE", 1, 14).build(),
            ItemBuilder(Material.STAINED_GLASS_PANE, "&9NIEBIESKIE", 1, 11).build(),
            ItemBuilder(Material.STAINED_GLASS_PANE, "&eŻÓŁTE", 1, 4).build(),
            ItemBuilder(Material.STAINED_GLASS_PANE, "&dRóżowe", 1, 6).build()
    )

    private var complete = 0
    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
                .id("wiresTask")
            .provider(WiresInventoryProvider(plugin))
                .size(6, 9)
                .title(" &8* &eKabelki.")
                .build()
    }


    override fun init(player: Player, contents: InventoryContents) {
        contents.fillBorder()
        var row = 2

        this.glasses.shuffle()
        this.glasses.forEach { item ->
            contents.set(row, 1, ClickableItem.of(item) {
                if (it.cursor === item) {
                    it.cursor = null
                    return@of
                }
                it.cursor = item
            })
            row += 1
        }
        row = 2

        this.glasses.shuffle()
        this.glasses.forEach { item ->
            contents.set(7, row, ClickableItem.of(item) {
                if (it.cursor === item) {
                    val glass = item
                    glass.addEnchantment(Enchantment.DURABILITY, 10)

                    it.currentItem = glass
                    it.cursor = null
                    this.complete++
                    if (this.complete == 4) {
                        player.closeInventory()
                        val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!
                        arena.addAndGetProgress(2)
                    }
                }
            })
            row += 1
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}