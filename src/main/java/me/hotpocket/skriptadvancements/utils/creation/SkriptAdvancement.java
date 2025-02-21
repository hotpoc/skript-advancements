package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import me.hotpocket.skriptadvancements.utils.advancement.VisibilityType;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class SkriptAdvancement {
	public final String key;
	public final String tab;
	public final DisplayBuilder displayBuilder = new DisplayBuilder();
	public @Range(from = 1, to = Integer.MAX_VALUE) int maxProgression = 1;
	public final List<Advancement> parents = new ArrayList<>();
	public VisibilityType visibilityType = VisibilityType.VISIBLE;
	public boolean root = false;
	public String backgroundTexture = CustomUtils.getTexture(Material.STONE);

	public SkriptAdvancement(@NotNull String tabName, @NotNull String key, @Range(from = 1, to = Integer.MAX_VALUE) int maxProgression, @NotNull List<BaseAdvancement> parents, VisibilityType visibility) {
		this.tab = tabName.toLowerCase().replaceAll(" ", "_");
		this.key = key.toLowerCase().replaceAll(" ", "_").replaceAll("[^a-z0-9/._-]", "");
	}

	public Advancement build(String tabName) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		try {
			if (!root) {
				AtomicReference<RootAdvancement> rootAdvancement = new AtomicReference<>();
				Set<BaseAdvancement> finalParents = new HashSet<>();
				parents.forEach(parent -> {
					if (parent instanceof BaseAdvancement baseAdvancement)
						finalParents.add(baseAdvancement);
					else if (parent instanceof RootAdvancement root)
						rootAdvancement.set(root);
				});

				Class<?> advancementClass = visibilityType.getMultiAdvancementClass();
				if (rootAdvancement.get() == null) {
					return (Advancement) advancementClass.getConstructor(String.class, AdvancementDisplay.class, int.class, Set.class)
							.newInstance(key, displayBuilder.build(), maxProgression, finalParents);
				} else {
					Class<?> advancementClassWithParent = visibilityType.getAdvancementClass();
					return (Advancement) advancementClassWithParent.getConstructor(String.class, AdvancementDisplay.class, int.class, Advancement.class)
							.newInstance(key, displayBuilder.build(), maxProgression, rootAdvancement.get());
				}
			} else {
				AdvancementTab tab = CustomUtils.getAPI().getAdvancementTab(tabName);
				if (tab != null) {
					return new RootAdvancement(tab, key, displayBuilder.build(), backgroundTexture, maxProgression);
				}
			}
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace(); // Print the cause of the exception
			throw e; // Rethrow after logging
		}
		return null;
	}

}
