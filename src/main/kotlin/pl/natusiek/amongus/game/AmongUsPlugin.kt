package pl.natusiek.amongus.game

import org.bukkit.plugin.java.JavaPlugin
import pl.natusiek.amongus.common.plugin.Plugin
import pl.natusiek.amongus.game.command.TaskCommand
import pl.natusiek.amongus.game.listener.ArenaListener
import pl.natusiek.amongus.game.listener.KillListener
import pl.natusiek.amongus.game.listener.PlayerListener
import pl.natusiek.amongus.game.listener.QueueListener
import pl.natusiek.amongus.game.repositories.ArenaRepository
import pl.natusiek.amongus.game.repositories.MemberRepository
import pl.natusiek.amongus.game.repositories.QueueRepository
import pl.natusiek.amongus.game.structure.task.Task

class AmongUsPlugin : Plugin() {

    //Repositories
    lateinit var arenaRepository: ArenaRepository
    lateinit var queueRepository: QueueRepository
    lateinit var memberRepository: MemberRepository


    // TODO: 24.11.2020 Do zrobienia|skończenia taski/zabojstwa/sabotaże
    override fun onEnable() {
        this.arenaRepository = ArenaRepository(this)
        this.queueRepository = QueueRepository(this)
        this.memberRepository = MemberRepository(this)

        this.registerCommands(
            TaskCommand(this)
        )
        this.registerListeners(
            ArenaListener(this),
            KillListener(this),
            PlayerListener(this),
            QueueListener(this)
        )

        Task(this)
    }


}