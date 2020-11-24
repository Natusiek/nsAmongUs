package pl.natusiek.amongus.game.repositories

import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.member.Member
import java.util.*
import kotlin.collections.HashSet

class MemberRepository(private val plugin: AmongUsPlugin) {

    private val members: HashSet<Member> = hashSetOf()


    fun addMember(member: Member) = this.members.add(member)

    fun removeMember(uniqueId: UUID) = this.members.remove(getMemberById(uniqueId))


    private fun getMemberBy(member: (Member) -> Boolean): Member = this.members.find(member)!!

    fun getMemberById(uniqueId: UUID) = this.getMemberBy { it.uniqueId == uniqueId }

}
