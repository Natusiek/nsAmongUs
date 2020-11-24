package pl.natusiek.amongus.game.structure.arena

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.Event
import pl.natusiek.amongus.common.extension.sendFixedMessage

import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.event.default.ArenaChangeStateEvent
import pl.natusiek.amongus.game.structure.arena.event.default.ArenaEndEmergencyEvent
import pl.natusiek.amongus.game.structure.arena.event.default.FinishArenaEvent
import pl.natusiek.amongus.game.structure.arena.event.default.FinishArenaEvent.ArenaTeam

import pl.natusiek.amongus.game.structure.emergency.Emergency
import pl.natusiek.amongus.game.structure.impostor.Impostor
import pl.natusiek.amongus.game.structure.member.Member

import java.io.Serializable

data class Arena(
        val plugin: AmongUsPlugin,
        val members: MutableSet<Member>,
        val impostor: Impostor,
        val arenaId: Int
): Serializable {

    val players: Sequence<Player>
        get() = this.members.asSequence().mapNotNull { it.player }

    val murdered: Sequence<Player>
        get() = this.members.asSequence().filter { it.died }.mapNotNull { it.player }

    init {
        ArenaTask(this)
    }

    var progress: Int = 0

    var emergency: Emergency? = null

    var state: ArenaState =  ArenaState.STARTING


    @JvmName("setState1")
    fun setState(state: ArenaState) {
        this.state = state
        this.callEvent(ArenaChangeStateEvent(this, state))
    }

    fun addGhost(member: Member) {
        member.died = true
        val player = member.player
        this.members
                .filter { !it.died }
                .map { it.player }
                .forEach { it.hidePlayer(player) }
    }

    fun addAndGetProgress(progress: Int) {
        val size = this.members.size
        val max = size * 10

        this.progress += progress
        if (this.progress >= max) {
            this.setState(ArenaState.FINISH)
            this.callEvent(FinishArenaEvent(this, ArenaTeam.CREWMATE))
        }
    }

    fun checkMembers() {
        if (this.murdered.sumBy { 1 } > this.members.size - 1) {
            this.setState(ArenaState.FINISH)
            this.callEvent(FinishArenaEvent(this, ArenaTeam.IMPOSTOR))
        } else if (this.impostor.impostors.isEmpty()) {
            this.setState(ArenaState.FINISH)
            this.callEvent(FinishArenaEvent(this, ArenaTeam.CREWMATE))
        }
    }

    fun callEvent(event: Event) = Bukkit.getPluginManager().callEvent(event)

    override fun toString(): String {
        return super.toString()
    }

}