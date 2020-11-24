package pl.natusiek.amongus.game.structure.member

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import pl.natusiek.amongus.common.builder.ItemBuilder

enum class ColorType(
        val colorname: String,
        val color: Color
) {

    RED("&4Czerwony", Color.fromRGB(255, 0, 0)),
    BLUE("&9Niebieski", Color.fromBGR(0, 0, 255)),
    GREEN("&4Zielony", Color.fromRGB(0, 128, 0)),
    YELLOW("&eŻółty", Color.fromRGB(255, 255, 0)),
    ORANGE("&6Pomarańczowy", Color.fromRGB(255, 102, 0)),
    BLACK("&0Czarny", Color.fromRGB(0, 0, 0)),
    WHITE("&fBiał", Color.fromRGB(255, 255, 255)),
    PURPLE("&5Fioletowy", Color.fromRGB(128, 0, 128)),
    CYAN("&3Cyan", Color.fromRGB(0, 128, 128)),
    BROWN("&8Brązowy", Color.fromRGB(130, 90, 44)),
    LIME("&aJasno Zielony", Color.fromRGB(164, 196, 0));

    fun leatherItem(color: Color): ItemStack {
        val item = ItemBuilder(Material.LEATHER_CHESTPLATE, this.colorname, 1, 0, arrayListOf("", " &8* &eCzy napewno chcesz ten kolor?", "")).build()
        val leatherMeta = item.itemMeta as LeatherArmorMeta
        leatherMeta.color = color
        item.itemMeta = leatherMeta

        return item
    }

}