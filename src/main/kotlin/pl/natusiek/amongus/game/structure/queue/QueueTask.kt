package pl.natusiek.amongus.game.structure.queue

import org.bukkit.scheduler.BukkitRunnable
import java.lang.Thread.sleep

class QueueTask(private val queue: Queue): BukkitRunnable() {

    init {
        this.runTaskTimerAsynchronously(this.queue.plugin, 0, 20)
    }

    override fun run() {
        val timeToStart = this.queue.timeToStart.getAndDecrement()
        if (this.queue.size < 4) {
            if (timeToStart < 10) {
                this.queue.timeToStart.set(30)
                this.queue.sendTitle("", "&4W kolejce musi być min 4 osoby")
                return sleep(100)
            }
        }

        when (timeToStart) {
            0 -> this.queue.start()
            else -> this.queue.sendTitle("", "&8* &eArena startuje za: &f${timeToStart} &8*")
        }

    }

}