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
import pl.natusiek.amongus.game.structure.arena.ArenaState
import pl.natusiek.amongus.game.structure.arena.event.default.FinishArenaEvent
import java.util.concurrent.atomic.AtomicInteger

class BinInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    private val clicks = AtomicInteger(3)

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
            .id("binTask")
            .provider(BinInventoryProvider(plugin))
            .size(4, 9)
            .title(" &8* &eWyrzuć śmieci.")
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        repeat((2..4).count()) { slot ->
            val wood = ItemBuilder(Material.WOOD_BUTTON, "&cKLIKNIJ").build()
            contents.set(4, slot, ClickableItem.of(wood) {
                val stone = ItemBuilder(Material.STONE_BUTTON, "&aGOTOWE").build()
                contents.set(4, slot, ClickableItem.empty(stone))
                this.clicks.getAndDecrement()
                if (this.clicks.get() == 0) {
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