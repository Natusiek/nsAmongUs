package pl.natusiek.amongus.game.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.util.Vector
import pl.natusiek.amongus.common.builder.LocationBuilder
import pl.natusiek.amongus.common.extension.sendTitle

import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.ArenaState
import pl.natusiek.amongus.game.structure.arena.ArenaState.*
import pl.natusiek.amongus.game.structure.arena.event.default.ArenaChangeStateEvent
import pl.natusiek.amongus.game.structure.arena.event.default.ArenaEndEmergencyEvent


class ArenaListener(private val plugin: AmongUsPlugin): Listener {

    @EventHandler
    fun onChangeState(event: ArenaChangeStateEvent) {
        val arena = event.arena
        val state = event.state

        when(state) {
            STARTING -> {

            }
            IN_GAME -> TODO()
            EMERGENCY -> TODO()
            FINISH -> TODO()
            END -> TODO()
            ABYSS -> TODO()
        }
    }

    @EventHandler
    fun onEndEmergency(event: ArenaEndEmergencyEvent) {
        val arena = event.arena
        val who = event.who

        val location = LocationBuilder("AmongUs-${arena.arenaId}", 550.0, 60.0, 550.0).toBukkitLocation()

        arena.players
                .filter { who.uniqueId != it.uniqueId }
                .forEach {
                    it.teleport(location)
                    it.sendTitle("", "&8* &eZostał wyrzucony: ${who.uniqueId} &8*", 3)
                }

        val locationAbyss = LocationBuilder("AmongUs-${arena.arenaId}", 580.0, 60.0, 550.0).toBukkitLocation()
        who.teleport(locationAbyss)
        who.velocity = Vector(0.0, 2.0, 20.0)
    }

}