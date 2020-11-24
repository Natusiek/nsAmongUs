package pl.natusiek.amongus.game.structure.arena.event.default

import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.arena.event.ArenaEvent

class FinishArenaEvent(val arena: Arena, val who: ArenaTeam) : ArenaEvent(arena) {

    enum class ArenaTeam { IMPOSTOR, CREWMATE }

}