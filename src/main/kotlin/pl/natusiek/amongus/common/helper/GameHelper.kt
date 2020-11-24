package pl.natusiek.amongus.common.helper

import pl.natusiek.amongus.game.structure.arena.Arena


object GameHelper {

    var message: String

    init {
        val buffer = StringBuilder()
        repeat((0..20).count()) {
            buffer.append("|")
        }
        this.message = buffer.toString()
    }

    fun getTask(arena: Arena): String {
        val task: Int = arena.progress

        val green = this.message.substring(0, task * "|".length)
        val red = this.message.substring(task * "|".length, this.message.length)

        return " &4Taski: &e$green&c$red"
    }

}
