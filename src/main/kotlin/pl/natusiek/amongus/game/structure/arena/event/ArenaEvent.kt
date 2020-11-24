package pl.natusiek.amongus.game.structure.arena.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import pl.natusiek.amongus.game.structure.arena.Arena

open class ArenaEvent(
        arena: Arena
): Event() {

    override fun getHandlers() = getHandlerList()

    companion object {
        private val handlers = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return this.handlers
        }
    }

}