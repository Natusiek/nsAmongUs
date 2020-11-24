package pl.natusiek.amongus.game.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Syntax
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.inventory.task.*

class TaskCommand(private val plugin: AmongUsPlugin) : BaseCommand() {

    @CommandAlias("task")
    @Syntax("<typ>")
    fun onCommand(sender: CommandSender, type: String) {
        val player = sender as Player
        when (type) {
            "1" -> BinInventoryProvider.getInventory(this.plugin).open(player)
            "2" -> CardInventoryProvider.getInventory(this.plugin).open(player)
            "3" -> MeteoriteInventoryProvider.getInventory(this.plugin).open(player)
            "4" -> ShieldsInventoryProvider.getInventory(this.plugin).open(player)
            "5" -> ThrowingGarbageInventoryProvider.getInventory(this.plugin).open(player)
            "6" -> WiresInventoryProvider.getInventory(this.plugin).open(player)
        }
    }


}