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
import me.hotpocket.skriptadvancements.utils.advancement.VisibilityType;
import me.hotpocket.skriptadvancements.utils.creation.SkriptAdvancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("Persistent Advancement Section")
@Description({"Creates a custom advancement within a persistent advancement tab.",
	"This advancement will not be removed when the script is reloaded."})
@Since("2.0.2")

public class SecPersistentAdvancement extends EffectSection {

	static {
		Skript.registerSection(SecPersistentAdvancement.class, "create [a[n]] [new] persistent advancement named %string%");
	}

	private SkriptAdvancement advancement;
	private Expression<String> name;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
					@Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
		if (getParser().isCurrentSection(SecPersistentAdvancement.class)) {
			Skript.error("The persistent advancement creation section is not meant to be put inside of another persistent advancement creation section.");
			return false;
		}
		if (!getParser().isCurrentSection(SecPersistentTab.class)) {
			Skript.error("The persistent advancement creation section needs to be inside of a persistent advancement tab creation section.");
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
		advancement = new SkriptAdvancement(getParser().getCurrentSection(SecPersistentTab.class).getTabName(), name.getSingle(event), 1, new ArrayList<>(), VisibilityType.VISIBLE);
		getParser().getCurrentSections().add(this);
		return walk(event, true);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "create a persistent advancement with the name " + name.toString(event, debug);
	}

	public SkriptAdvancement getAdvancement() {
		return advancement;
	}
}
