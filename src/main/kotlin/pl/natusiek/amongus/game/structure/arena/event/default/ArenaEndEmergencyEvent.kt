package pl.natusiek.amongus.game.structure.arena.event.default

import org.bukkit.entity.Player
import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.arena.event.ArenaEvent

class ArenaEndEmergencyEvent(val arena: Arena, val who: Player): ArenaEvent(arena)
