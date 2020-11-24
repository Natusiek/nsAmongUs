package pl.natusiek.amongus.common.extension

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotPos
import org.bukkit.Material
import pl.natusiek.amongus.common.builder.ItemBuilder


val item: ClickableItem = ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "&8*", 1, 7).build())

fun InventoryContents.fillBorder(): InventoryContents = this.fillBorders(item)

fun InventoryContents.fillRect(from: SlotPos, to: SlotPos): InventoryContents = this.fillRect(from, to, item)

fun InventoryContents.fill(): InventoryContents = this.fill(item)
