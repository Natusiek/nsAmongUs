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
import java.lang.Thread.sleep

import java.util.concurrent.atomic.AtomicInteger

class MeteoriteInventoryProvider(private val plugin: AmongUsPlugin): InventoryProvider {

    private var lastSlotPos = SlotPos(4, 5)

    private val meteorite = AtomicInteger(20)

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
                .id("meteoriteTask")
            .provider(MeteoriteInventoryProvider(plugin))
                .size(6, 9)
                .title(" &8* &eMeteoryty.")
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fillRow(1, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "&8*", 1, 8).build()))
        contents.fillRow(5, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "&8*", 1, 8).build()))

    }

    override fun update(player: Player, contents: InventoryContents) {
        val row = (2..4).random()
        val column = (0..8).random()
        val slotPos = SlotPos(row, column)
        
        contents.set(1, 4, ClickableItem.empty(ItemBuilder(Material.NETHER_STAR, "&8* &4Meteoryty: &f${this.meteorite.get()} &8*").build()))
        contents.set(slotPos, ClickableItem.of(ItemBuilder(Material.STAINED_GLASS, "&4METEORYT", 1, 13).build()) {
            if (this.meteorite.getAndDecrement() == 0) {
                player.closeInventory()
                val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!
                arena.addAndGetProgress(2)
            }
        })
        contents.set(this.lastSlotPos, ClickableItem.empty(null))
        
        this.lastSlotPos = slotPos
        sleep(6000)
    }

}