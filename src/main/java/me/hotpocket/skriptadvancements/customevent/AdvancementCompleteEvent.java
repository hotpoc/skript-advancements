package me.hotpocket.skriptadvancements.customevent;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AdvancementCompleteEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private final Advancement advancement;

	public AdvancementCompleteEvent(Player player, Advancement advancement) {
		this.player = player;
		this.advancement = advancement;
	}

	public Player getPlayer() {
		return player;
	}
	public Advancement getAdvancement() {
		return advancement;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}