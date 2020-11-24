package pl.natusiek.amongus.game.structure.arena.event.default

import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.arena.ArenaState
import pl.natusiek.amongus.game.structure.arena.event.ArenaEvent

class ArenaChangeStateEvent(val arena: Arena, val state: ArenaState) : ArenaEvent(arena)