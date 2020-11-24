package pl.natusiek.amongus.game.listener

import com.google.common.cache.CacheBuilder
import org.bukkit.Bukkit

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.arena.ArenaState

import java.util.*
import java.util.concurrent.TimeUnit

class KillListener(
        private val plugin: AmongUsPlugin
): Listener {

    val died = hashSetOf<UUID>()

    private val cooldowns = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build<UUID, Long>()

    @EventHandler
    fun onEntity(event: EntityDamageByEntityEvent) {
        event.isCancelled = true

        val killer = event.damager as Player
        val member = this.plugin.memberRepository.getMemberById(killer.uniqueId)

        if (member.arenaId == null) return
        if (killer.itemInHand.type !== Material.IRON_SWORD) return

        val arena = this.plugin.arenaRepository.getArenaByMemberId(killer.uniqueId)!!
        if (arena.impostor.isImpostor(killer.uniqueId)) return

        if (arena.state != ArenaState.IN_GAME) {
            val lastKill = this.cooldowns.getIfPresent(killer.uniqueId)
            if (lastKill != null && lastKill >= System.currentTimeMillis()) {
                val target = event.entity as Player
                val targetMember = this.plugin.memberRepository.getMemberById(target.uniqueId)

                val block = target.location.block
                Bukkit.getPluginManager().callEvent(PlayerBedEnterEvent(target, block))
                targetMember.spawnNPC(target.location)
                targetMember.died = true
                arena.checkMembers()
            }
            this.cooldowns.put(killer.uniqueId, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20))
        }

    }

    @EventHandler
    fun onBed(event: PlayerBedLeaveEvent) {
        val player = event.player
        if (this.died.contains(player.uniqueId)) {
            Bukkit.getPluginManager().callEvent(PlayerBedEnterEvent(player, event.bed))
        }
    }

}