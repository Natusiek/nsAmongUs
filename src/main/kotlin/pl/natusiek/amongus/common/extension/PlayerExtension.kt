package pl.natusiek.amongus.common.extension

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.sendFixedMessage(vararg message: String) {
    message.forEach {
        this.sendMessage(it.fixColor())
    }
}

fun Player.sendPacket(vararg packet: Packet<*>) {
    val playerConnection = (this as CraftPlayer).handle.playerConnection
    packet.forEach {
        playerConnection.sendPacket(it)
    }
}

fun Player.sendActionBar(message: String, stay: Byte) {
    val iChat = IChatBaseComponent.ChatSerializer.a("{\"text\":\"$message\"}".fixColor())
    val packet = PacketPlayOutChat(iChat, stay)

    this.sendPacket(packet)
}

fun Player.sendTitle(title: String, subTitle: String, stay: Int) {
    val timePacket = PacketPlayOutTitle(50, stay, 50)

    val packetTitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"$title\"}".fixColor()))
    val packetSubtitle = PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"$subTitle\"}".fixColor()))

    this.sendPacket(timePacket, packetTitle, packetSubtitle)
}