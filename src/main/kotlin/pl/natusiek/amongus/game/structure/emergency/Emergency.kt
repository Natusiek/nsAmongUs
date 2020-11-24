package pl.natusiek.amongus.game.structure.emergency

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

data class Emergency(
    val arenaId: Int
) {

    val time = AtomicInteger(60)

    private val voted: MutableSet<UUID> = hashSetOf()
    private val candidates: MutableMap<UUID, Int> = hashMapOf()

    fun addCandidate(uuid: UUID) { this.candidates[uuid] = 0 }

    fun heVote(uuid: UUID) = this.voted.contains(uuid)

    fun whoHasMostVotes() = this.candidates.maxBy { it.value }!!.key

    fun addVote(uuid: UUID) { this.candidates[uuid] = this.candidates[uuid]!! + 1 }

}