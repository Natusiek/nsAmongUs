package pl.natusiek.amongus.game.structure.task

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.amongus.common.extension.sendFixedMessage
import pl.natusiek.amongus.game.AmongUsPlugin
import java.util.*

class Task(
        private val plugin: AmongUsPlugin
) : BukkitRunnable() {

    init {
        this.runTaskTimerAsynchronously(this.plugin, 0, 20)
    }
        override fun run() {
            this.plugin.queueRepository.queues.values.forEach {
                Bukkit.broadcastMessage("Kolejka ${it.toString()}")
            }


            this.plugin.arenaRepository.arenas.values.forEach {
                Bukkit.broadcastMessage("Arena ${it.members}")
            }
        }


}