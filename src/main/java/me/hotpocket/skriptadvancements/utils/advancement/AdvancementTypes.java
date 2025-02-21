package me.hotpocket.skriptadvancements.utils.advancement;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import com.fren_gor.ultimateAdvancementAPI.visibilities.HiddenVisibility;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Set;

public class AdvancementTypes {

	public class HiddenMultiParentsAdvancement extends MultiParentsAdvancement implements HiddenVisibility {

		public HiddenMultiParentsAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Set<BaseAdvancement> parents) {
			super(key, display, maxProgression, parents);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

	public static class ParentGrantedMultiParentsAdvancement extends MultiParentsAdvancement implements ParentGrantedVisibility {

		public ParentGrantedMultiParentsAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Set<BaseAdvancement> parents) {
			super(key, display, maxProgression, parents);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

	public static class VisibleMultiParentsAdvancement extends MultiParentsAdvancement {

		public VisibleMultiParentsAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Set<BaseAdvancement> parents) {
			super(key, display, maxProgression, parents);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

	public static class HiddenAdvancement extends BaseAdvancement implements HiddenVisibility {

		public HiddenAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Advancement parent) {
			super(key, display, parent, maxProgression);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

	public static class ParentGrantedAdvancement extends BaseAdvancement implements ParentGrantedVisibility {

		public ParentGrantedAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Advancement parent) {
			super(key, display, parent, maxProgression);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

	public static class VisibleAdvancement extends BaseAdvancement {

		public VisibleAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull Advancement parent) {
			super(key, display, parent, maxProgression);
		}

		@Override
		public void giveReward(@NotNull Player player) {
			super.giveReward(player);
			SkriptAdvancements.getInstance().callEvent(player, this);
		}
	}

}
