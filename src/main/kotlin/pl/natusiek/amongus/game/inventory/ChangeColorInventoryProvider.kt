package pl.natusiek.amongus.game.inventory

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material

import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.common.extension.fillBorder
import pl.natusiek.amongus.common.extension.fixColor

import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.member.ColorType
import pl.natusiek.amongus.game.structure.queue.Queue
import pl.natusiek.amongus.common.extension.sendFixedMessage

class ChangeColorInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
                .id("changeColorGui")
                .provider(ChangeColorInventoryProvider(plugin))
                .size(5, 9)
                .title(" &8* &eUstaw kolor postaci.".fixColor())
                .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val member = this.plugin.memberRepository.getMemberById(player.uniqueId)
        val queue: Queue = this.plugin.queueRepository.getQueueByMemberId(player.uniqueId)!!
        
        contents.fillBorder()
        queue.colors.forEach { color ->
            contents.add(ClickableItem.of(color.leatherItem(color.color)) {
                if (member.color === color)
                    return@of player.sendFixedMessage(" &8* &cPosiadasz już ten kolor.")

                /* queue.members.forEach {
                    if (it.color === color)
                        return@of player.sendFixedMessage(" &8* &cTen kolor jest już zajęty.")
                } */
                queue.colors.remove(color)
                if (member.color != null) {
                    queue.colors.add(member.color!!)
                }
                member.color = color
                player.closeInventory()
                player.sendFixedMessage("&8* &eWybrales kolor: ${color.colorname}")
            })
        }
    }

    override fun update(player: Player, contents: InventoryContents) {

    }
    
}