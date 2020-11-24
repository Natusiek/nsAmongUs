package pl.natusiek.amongus.game.repositories

import org.bukkit.Material
import org.bukkit.entity.Player
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.member.Member.*
import pl.natusiek.amongus.game.structure.queue.Queue
import pl.natusiek.amongus.game.structure.queue.QueueEntry
import pl.natusiek.amongus.common.extension.sendFixedMessage
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.HashMap

class QueueRepository(private val plugin: AmongUsPlugin) {

    private val numberArena = AtomicInteger(0)

    val queues: MutableMap<Int, Queue> = hashMapOf()

    fun addToQueue(player: Player) {
        val queue = this.getFreeQueue(player)
        if (queue != null) {
            this.plugin.memberRepository.getMemberById(player.uniqueId).also {
                if (it.state === MemberState.QUEUE) {
                    return player.sendFixedMessage("&4! &fJesteś już w kolejce!")
                }
                player.inventory.apply {
                    this.clear()
                    this.setItem(4, ItemBuilder(Material.BOOK, " &8* &eWybierz kolor &8*").build())
                }
                it.state = MemberState.QUEUE
            }
            queue.entries.add(QueueEntry(player.uniqueId))
            player.sendFixedMessage("&8* &eDołączyłeś do kolejki!")
        }
    }

    fun leaveFromQueue(player: Player) {
        this.plugin.memberRepository.getMemberById(player.uniqueId).also {
            if (it.state !== MemberState.QUEUE) {
                return throw NullPointerException("Zapewne osoba się wykoleiła.")
            }
            it.state = MemberState.LOBBY
        }

        val queue: Queue = this.getQueueByMemberId(player.uniqueId)!!
        val entry = queue.getEntry(player.uniqueId)!!
        queue.entries.remove(entry)
        player.sendFixedMessage("&8* &eOpuściłeś kolejkę")
    }

    fun removeQueueById(id: Int) = this.queues.remove(id)

    private fun getQueueBy(queue: (Queue) -> Boolean) = this.queues.values.find(queue)

    fun getQueueByMemberId(id: UUID) = this.getQueueBy { it.getEntry(id) != null }

    fun getQueueById(id: Int) = this.getQueueBy { it.id == id }


    private fun getFreeQueue(player: Player): Queue? {
        // TODO: 14.11.2020 Dodane ze względu na bezpieczeństwo.
        if (this.plugin.arenaRepository.arenas.size >= 5) {
            player.sendFixedMessage(" &4! &fJest za dużo aren w trakcie!")
            return null
        }
        this.queues.values
                .filter { it.size + 1 <= 10 }
                .forEach { return it }

        val queue = Queue(this.plugin, this.numberArena.incrementAndGet())
        this.queues[queue.id] = queue

        return queue
    }


}
