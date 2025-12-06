package me.hotpocket.skriptadvancements.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Name("Persistent Advancement Tab Section")
@Description({"Creates or reuses a custom advancement tab without removing existing advancements.",
	"Unlike the regular advancement tab section, this will preserve previously registered advancements",
	"even if the script is reloaded. New advancements are added to existing ones."})
@Since("2.0.2")

public class SecPersistentTab extends EffectSection {

	public RootAdvancement rootAdvancement;
	private List<BaseAdvancement> newAdvancements = new ArrayList<>();
	private String tabName;

	static {
		Skript.registerSection(SecPersistentTab.class, "create [a[n]] [new] persistent advancement tab named %string%");
	}

	private Expression<String> name;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
					@Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
		if (getParser().isCurrentSection(SecPersistentTab.class)) {
			Skript.error("The persistent advancement tab creation section is not meant to be put inside of another persistent advancement tab creation section.");
			return false;
		}
		if (sectionNode != null) {
			loadOptionalCode(sectionNode);
		}
		name = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	@Nullable
	@SuppressWarnings({"unchecked", "rawtypes"})
	protected TriggerItem walk(Event event) {
		tabName = name.getSingle(event).toLowerCase().replaceAll(" ", "_");
		AdvancementTab tab = CustomUtils.getAPI().getAdvancementTab(tabName);
		
		// If tab doesn't exist, create it with a temp root advancement
		if (tab == null) {
			RootAdvancement root = new RootAdvancement(
				CustomUtils.getAPI().createAdvancementTab(tabName),
				"temp_root_advancement_name_1289587",
				new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"),
				CustomUtils.getTexture(Material.DIAMOND_BLOCK)
			);
			BaseAdvancement tempBase = new BaseAdvancement("name1",
				new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"),
				root
			);
			tab = CustomUtils.getAPI().getAdvancementTab(tabName);
			if (tab != null) {
				tab.registerAdvancements(root, tempBase);
				this.rootAdvancement = root;
			}
		} else if (!tab.isInitialised() && !tab.isActive()) {
			// Tab exists but isn't initialized - get or create its root advancement
			RootAdvancement root = new RootAdvancement(tab, "temp_root_advancement_name_1289587",
				new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"),
				CustomUtils.getTexture(Material.DIAMOND_BLOCK)
			);
			BaseAdvancement tempBase = new BaseAdvancement("name1",
				new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"),
				root
			);
			tab.registerAdvancements(root, tempBase);
			this.rootAdvancement = root;
		} else {
			// Tab is already initialized - preserve its existing advancements
			// Extract the root advancement from existing advancements
			for (@NotNull Advancement advancement : tab.getAdvancements()) {
				if (advancement instanceof RootAdvancement) {
					this.rootAdvancement = (RootAdvancement) advancement;
					break;
				}
			}
			// If no root found, create one (shouldn't happen in normal cases)
			if (this.rootAdvancement == null) {
				this.rootAdvancement = new RootAdvancement(tab, "temp_root_advancement_name_1289587",
					new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"),
					CustomUtils.getTexture(Material.DIAMOND_BLOCK)
				);
			}
		}
		
		getParser().getCurrentSections().add(this);
		return walk(event, true);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "create a persistent advancement tab with the name " + name.toString(event, debug);
	}

	public void addAdvancement(BaseAdvancement addition) {
		// Avoid duplicates: if an advancement with same key exists, replace it
		List<BaseAdvancement> toBeRemoved = new ArrayList<>();
		newAdvancements.forEach((advancement) -> {
			if (addition.getKey().getKey().equalsIgnoreCase(advancement.getKey().getKey()))
				toBeRemoved.add(advancement);
		});
		newAdvancements.removeAll(toBeRemoved);
		newAdvancements.add(addition);
	}

	public void removeAdvancement(BaseAdvancement removed) {
		newAdvancements.remove(removed);
	}

	public Collection<BaseAdvancement> getAdvancements() {
		return Collections.unmodifiableCollection(newAdvancements);
	}

	public String getTabName() {
		return tabName;
	}
}
