package pl.natusiek.amongus.game.structure.queue

import org.bukkit.Bukkit
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.member.Member
import pl.natusiek.amongus.game.structure.member.Member.MemberState
import pl.natusiek.amongus.common.extension.sendTitle
import pl.natusiek.amongus.game.structure.impostor.Impostor
import pl.natusiek.amongus.game.structure.member.ColorType
import java.io.Serializable
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class Queue(
        val plugin: AmongUsPlugin,
        val id: Int
) : Serializable {

    var timeToStart = AtomicInteger(60)

    val colors = hashSetOf<ColorType>()
    val entries: MutableSet<QueueEntry> = hashSetOf()


    init {
        QueueTask(this)
        ColorType.values().forEach { this.colors.add(it) }
    }

    val size: Int
        get() = this.entries.size

    private val members: MutableSet<Member>
        get() = this.entries.map { this.plugin.memberRepository.getMemberById(it.id) }.toMutableSet()

    fun start() {
        this.members.forEach {
            if (it.color == null) {
                checkColor(it)
            }
            it.state = MemberState.IN_GAME
            it.arenaId = this.id
        }
        this.plugin.arenaRepository.createArena(Arena(this.plugin, this.members, getImpostor(), this.id))

        this.sendTitle("", "&aStartujemy!")
    }

    private fun checkColor(member: Member) {
        if (member.color == null) {
            this.colors.random().apply {
                member.color = this
                this@Queue.colors.remove(this)
            }
        }
    }

    private fun getImpostor(): Impostor {
        val impostor = this.members.map { it.uniqueId }

        val firstImpostor = impostor.random()
     //   val secondImpostor = impostor.minus(firstImpostor).random()

        return Impostor(hashSetOf(firstImpostor))
    }

    fun getEntry(id: UUID) = this.entries.singleOrNull { it.id == id }

    fun sendTitle(title: String, subTitle: String) =
            this.entries.map { it.player }.forEach { it.sendTitle(title, subTitle, 1) }

    override fun toString(): String {
        return "$members $id"
    }

}