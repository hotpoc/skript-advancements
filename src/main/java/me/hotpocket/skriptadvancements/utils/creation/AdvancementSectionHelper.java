package me.hotpocket.skriptadvancements.utils.creation;

import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import me.hotpocket.skriptadvancements.elements.sections.SecPersistentAdvancement;
import ch.njol.skript.lang.parser.ParserInstance;

/**
 * Helper class to support both regular and persistent advancement sections.
 * Allows expressions and effects to work with both section types seamlessly.
 */
public class AdvancementSectionHelper {

	private static ParserInstance parser;

	/**
	 * Get the current advancement (works with both SecAdvancement and SecPersistentAdvancement)
	 */
	public static SkriptAdvancement getCurrentAdvancement(ParserInstance parserInstance) {
		parser = parserInstance;
		
		// Try SecAdvancement first
		SecAdvancement secAdv = parser.getCurrentSection(SecAdvancement.class);
		if (secAdv != null) {
			return secAdv.getAdvancement();
		}
		
		// Try SecPersistentAdvancement
		SecPersistentAdvancement secPersist = parser.getCurrentSection(SecPersistentAdvancement.class);
		if (secPersist != null) {
			return secPersist.getAdvancement();
		}
		
		return null;
	}

	/**
	 * Check if currently in either advancement section type
	 */
	public static boolean isInAdvancementSection(ParserInstance parserInstance) {
		parser = parserInstance;
		return parser.isCurrentSection(SecAdvancement.class) || parser.isCurrentSection(SecPersistentAdvancement.class);
	}
}
