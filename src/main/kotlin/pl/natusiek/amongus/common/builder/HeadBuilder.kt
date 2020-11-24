package pl.natusiek.amongus.common.builder

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.io.Serializable

data class HeadBuilder(
        val name: String = "",
        val nick: String = "",
        val lore: List<String> = arrayListOf()
) : Serializable {

    fun build(): ItemStack {
        val skull = ItemBuilder(Material.SKULL_ITEM, this.name, 1, 3, this.lore).build()
        val meta = skull.itemMeta as SkullMeta

        meta.owner = this.nick
        skull.itemMeta = meta
        return skull
    }

}