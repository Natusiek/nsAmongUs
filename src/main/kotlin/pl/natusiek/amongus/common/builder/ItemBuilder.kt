package pl.natusiek.amongus.common.builder

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import pl.natusiek.amongus.common.extension.fixColor


import java.io.Serializable

data class ItemBuilder(
        val material: Material,
        val name: String,
        val amount: Int = 1,
        val data: Short = 0,
        val lore: List<String> = arrayListOf(),
        val enchant: HashSet<Enchant> = hashSetOf()
) : Serializable {


    constructor(material: String, name: String, amount: Int, data: Short, lore: List<String>, enchant: HashSet<Enchant>)
            : this(Material.getMaterial(material), name, amount, data, lore, enchant)

    constructor(material: Material, name: String, lore: List<String>)
            : this(material, name, 1, 0, lore, HashSet())


    fun build(): ItemStack {

        //amount
        var fixedAmount = this.amount
        if (fixedAmount > this.material.maxStackSize) {
            fixedAmount = this.material.maxStackSize
        } else if (fixedAmount <= 0) {
            fixedAmount = 1
        }

        val item = ItemStack(this.material, fixedAmount, this.data)

        //enchant
        this.enchant.forEach {
            val enchant = it.enchant
            val level = it.level
            if (level > it.getMaxLevel()) {
                item.addUnsafeEnchantment(enchant, level)
            } else {
                item.addEnchantment(enchant, level)
            }
        }

        val meta = item.itemMeta ?: return item

        //name
        if(this.name.isNotEmpty()) {
            meta.displayName = this.name.fixColor()
        }

        meta.lore = this.lore.map { it.fixColor() }
        item.itemMeta = meta
        return item
    }

}