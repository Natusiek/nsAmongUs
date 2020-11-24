package pl.natusiek.amongus.common.builder

import org.bukkit.enchantments.Enchantment

data class Enchant(
        val id: Int,
        val level: Int
) {

    internal var enchant: Enchantment

    init {
        this.enchant = getEnchant()
    }

    private fun getEnchant(): Enchantment = Enchantment.getById(id)

    fun getNameEnchantById(): String = this.getEnchant().name

    fun getMaxLevel(): Int = this.getEnchant().maxLevel

}