package me.hotpocket.skriptadvancements.elements.effects.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancementTab;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Keep Advancement Tab")
@Description({"Prevents the advancement tab from being cleared when the script reloads.",
	"By default, creating an advancement tab removes all previously registered advancements.",
	"Use this effect to preserve existing advancements and only add new ones.",
	"Must be used inside an advancement tab section."})
@Examples("keep advancement tab")
@Since("2.0.2")

public class EffKeepAdvancementTab extends Effect {

	static {
		Skript.registerEffect(EffKeepAdvancementTab.class, "keep [the] [advancement] tab");
	}

	// Static flag to track if we should preserve advancements
	private static boolean preserveMode = false;

	@Override
	protected void execute(Event e) {
		SecAdvancementTab tab = getParser().getCurrentSection(SecAdvancementTab.class);
		if (tab != null) {
			tab.setPreserveExistingAdvancements(true);
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "keep the advancement tab";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return getParser().isCurrentSection(SecAdvancementTab.class);
	}
}
