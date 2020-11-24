package pl.natusiek.amongus.game.structure.arena

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.amongus.common.builder.LocationBuilder
import pl.natusiek.amongus.common.helper.GameHelper
import pl.natusiek.amongus.game.structure.arena.ArenaState.*

import pl.natusiek.amongus.common.extension.sendActionBar
import pl.natusiek.amongus.common.extension.sendTitle
import pl.natusiek.amongus.game.LocationAPI
import pl.natusiek.amongus.game.structure.arena.event.default.ArenaEndEmergencyEvent
import pl.natusiek.amongus.game.structure.arena.event.default.FinishArenaEvent
import pl.natusiek.amongus.game.structure.arena.event.default.FinishArenaEvent.ArenaTeam
import pl.natusiek.amongus.game.structure.emergency.Emergency

class ArenaTask(private val arena: Arena): BukkitRunnable() {

    init {
        this.runTaskTimerAsynchronously(this.arena.plugin, 20L, 20L)
    }

    override fun run() {
        when(this.arena.state) {
            STARTING -> {
                val location = LocationAPI.centerLocation(this.arena.arenaId)
                this.arena.players.forEach {
                    it.teleport(location)
                    it.sendTitle("", if (this.arena.impostor.isImpostor(it.uniqueId)) "&4Impostor" else "&bCrewmate", 3)
                }
                this.arena.impostor.addKnife()
                this.arena.setState(IN_GAME)
            }
            IN_GAME -> {
                val message = GameHelper.getTask(this.arena)
                this.arena.players.forEach { it.sendActionBar(message, 1) }
                this.arena.addAndGetProgress(0)
            }
            EMERGENCY -> {
                val emergency = this.arena.emergency ?: return Bukkit.shutdown() // Jak się tak stanie to beczka wsm.
                val time = emergency.time.get()

                this.arena.players.forEach {
                    it.sendTitle("&cEmergency", "&8* &eCzas: &f$time &8*", 1)
                }
                if (time == 0) {
                    val who = Bukkit.getPlayer(emergency.whoHasMostVotes())
                    this.arena.callEvent(ArenaEndEmergencyEvent(this.arena, who))
                }
                emergency.time.getAndDecrement()
            }
            ABYSS -> {
                Bukkit.getScheduler().runTaskLater(this.arena.plugin, {
                    this.arena.setState(IN_GAME)
                }, 600)
            }
            FINISH -> {
                Bukkit.getScheduler().runTaskLater(this.arena.plugin, {
                    this.arena.setState(END)
                }, 600)
            }
            END -> {
                this.arena.members.filter { it.died }.forEach { it.destroyNPC() }
                this.arena.plugin.arenaRepository.removeArena(this.arena.arenaId)
                this.cancel()
            }
        }
    }

}