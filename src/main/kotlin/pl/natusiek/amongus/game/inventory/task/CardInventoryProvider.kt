package pl.natusiek.amongus.game.inventory.task

import com.google.common.cache.CacheBuilder
import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import pl.natusiek.amongus.common.builder.ItemBuilder
import pl.natusiek.amongus.common.extension.fill
import pl.natusiek.amongus.game.AmongUsPlugin
import pl.natusiek.amongus.game.structure.arena.Arena
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class CardInventoryProvider(private val plugin: AmongUsPlugin) : InventoryProvider {

    private val click = AtomicInteger(0)

    private val cooldowns = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build<UUID, Long>()


    companion object {
        fun getInventory(plugin: AmongUsPlugin): SmartInventory = SmartInventory.builder()
            .id("cardTask")
            .provider(CardInventoryProvider(plugin))
            .size(4, 9)
            .title(" &8* &eKarta.")
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fill()
        contents.set(2, 7, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "OFF", 1, 14).build()))
        val item = ItemBuilder(Material.STAINED_GLASS_PANE, "KLIK").build()
        repeat((2..5).count()) { number ->
            contents.set(3, number, ClickableItem.of(item) {
                val lastKill = this.cooldowns.getIfPresent(player.uniqueId)
                if (lastKill != null && lastKill <= System.currentTimeMillis()) {
                    contents.set(3, number, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "GOTOWE", 1, 5).build()))
                    this.click.getAndIncrement()
                    if (this.click.get() == 4) {
                        contents.set(2, 7, ClickableItem.empty(ItemBuilder(Material.STAINED_GLASS_PANE, "ON", 1, 13).build()))
                        val arena: Arena = this.plugin.arenaRepository.getArenaByMemberId(player.uniqueId)!!
                        Bukkit.getScheduler().runTaskLater(this.plugin, {
                            player.closeInventory()
                        }, 20)
                        arena.addAndGetProgress(2)
                    }
                } else {
                    player.closeInventory()
                    getInventory(this.plugin).open(player)
                }
                this.cooldowns.put(player.uniqueId, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2))
            })
        }


    }

    override fun update(player: Player, contents: InventoryContents) {

    }

}