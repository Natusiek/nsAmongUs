package pl.natusiek.amongus.common.plugin

import co.aikar.commands.BaseCommand
import co.aikar.commands.ExceptionHandler
import co.aikar.commands.PaperCommandManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener

import org.bukkit.plugin.java.JavaPlugin
import pl.natusiek.amongus.common.extension.fixColor
import java.util.*

abstract class Plugin : JavaPlugin() {

    private lateinit var paperCommandManager: PaperCommandManager

    private fun initCommand() {
        this.paperCommandManager = PaperCommandManager(this)
        this.paperCommandManager.defaultExceptionHandler = ExceptionHandler { command, _, sender, _, _ ->
            sender.sendMessage("&cWystapil problem z komenda ${command.name}, zglos to jak najszybciej do admina :)".fixColor())
            true
        }
        this.paperCommandManager.addSupportedLanguage(Locale("pl"))
        this.paperCommandManager.locales.defaultLocale = Locale("pl")
    }

    private fun <T> registerCommand(command: T) where T : BaseCommand {
        this.paperCommandManager.registerCommand(command)
    }

    fun <T> registerCommands(vararg commands: T) where T : BaseCommand {
        this.initCommand()
        commands.forEach { registerCommand(it) }
    }

    fun registerListeners(vararg listeners: Listener) {
        listeners.forEach { Bukkit.getPluginManager().registerEvents(it, this) }
    }

}