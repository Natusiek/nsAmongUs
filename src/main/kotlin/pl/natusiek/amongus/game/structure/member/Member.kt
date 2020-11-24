package pl.natusiek.amongus.game.structure.member

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.Serializable
import java.util.*

data class Member(
        val uniqueId: UUID,
        val name: String
): Serializable {

    val player: Player
        get() = Bukkit.getPlayer(this.uniqueId)

  //  var npc: NPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "&4ZABITY ${this.name}")

    var arenaId: Int? = null

    var died: Boolean = false

    var color: ColorType? = null
    var state: MemberState = MemberState.LOBBY

    enum class MemberState { LOBBY, QUEUE, IN_GAME }

    fun spawnNPC(location: Location) {
 //       this.npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, this.uniqueId)

   //     this.npc.spawn(location)
    }

    fun destroyNPC() {
      //  this.npc.destroy()
    }
}