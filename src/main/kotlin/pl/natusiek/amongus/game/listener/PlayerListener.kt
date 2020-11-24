package pl.natusiek.amongus.game.listener

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.weather.WeatherChangeEvent
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import pl.natusiek.amongus.game.structure.member.Member

class PlayerListener(private val plugin: AmongUsPlugin): Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val item = ItemBuilder(Material.DIAMOND, "&8* &eDołącz do kolejki! &8*").build()

        player.inventory.also {
            it.clear()
            it.setItem(4, item)
        }

        Member(player.uniqueId, player.name).also {
            it.state = Member.MemberState.LOBBY
            this.plugin.memberRepository.addMember(it)
        }
        event.joinMessage = null
    }


    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val member = this.plugin.memberRepository.getMemberById(player.uniqueId)

        if (member.state === Member.MemberState.QUEUE) {
            this.plugin.queueRepository.leaveFromQueue(player)
        } else if (member.arenaId !== null) {
            val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!
            if (arena.impostor.isImpostor(player.uniqueId)) {
                arena.impostor.removeImpostor(player.uniqueId)
            }
            arena.checkMembers()
            arena.members.remove(member)
        }
        this.plugin.memberRepository.removeMember(player.uniqueId)
        event.quitMessage = null
    }

    @EventHandler
    fun onKick(event: PlayerKickEvent) {
        Bukkit.getPluginManager().callEvent(PlayerQuitEvent(event.player, null))
        event.leaveMessage = null
    }

    @EventHandler
    fun onInventory(event: InventoryClickEvent) { if (!event.whoClicked.hasPermission("amongus.clickInv")) event.isCancelled = true }

    @EventHandler
    fun onFood(event: FoodLevelChangeEvent) { event.isCancelled = true }

    @EventHandler
    fun onWeather(event: WeatherChangeEvent) { event.isCancelled = true }
}