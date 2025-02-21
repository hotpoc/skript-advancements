package me.hotpocket.skriptadvancements.elements.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.util.Kleenean;
import me.hotpocket.skriptadvancements.SkriptAdvancements;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.structure.Structure;

import java.util.List;

public class SecComplete extends Section {

	static {
		Skript.registerSection(SecComplete.class, "(trigger|complete) [on] <.+>");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
		if (!getParser().isCurrentSection(SecAdvancement.class)) {
			Skript.error("The advancement complete section needs to be inside of an advancement creation section.");
			return false;
		}
		ParserInstance.Backup backup = getParser().backup();
		String eventRegex = parseResult.regexes.get(0).group();
		SkriptEvent skriptEvent = SkriptEvent.parse(eventRegex, sectionNode, "Unknown event: '" + eventRegex + "'");
		if (skriptEvent != null) {
			try {
				getParser().reset();
				List<Structure> structures = (List<Structure>) SkriptAdvancements.STRUCTURES_FIELD.get(getParser().getCurrentScript());
				structures.add(skriptEvent);
				if (skriptEvent.preLoad() && skriptEvent.load() && skriptEvent.postLoad()) {
					getParser().restoreBackup(backup);
					return true;
				}
				getParser().restoreBackup(backup);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	@Override
	protected @Nullable TriggerItem walk(Event e) {
		return getNext();
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "advancement complete section";
	}

}
