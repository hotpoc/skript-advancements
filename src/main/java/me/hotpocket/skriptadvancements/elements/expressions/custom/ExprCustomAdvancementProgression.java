package me.hotpocket.skriptadvancements.elements.expressions.custom;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Name("Custom Advancement Progression")
@Description("Allows you to get and set the custom advancement progression for a player.")
@Examples("set the progress of the custom advancement \"tabName/advancementName\" for player to 3")
@Since("1.4")

public class ExprCustomAdvancementProgression extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprCustomAdvancementProgression.class, Integer.class, ExpressionType.SIMPLE,
                "[the] [advancement] progress[ion] of %customadvancements% (for|of) %players%");
    }

    private Expression<Advancement> customAdvancements;
    private Expression<Player> players;

    @Override
    protected @Nullable Integer[] get(Event e) {
        for (Player player : players.getAll(e))
            for (Advancement advancement : customAdvancements.getAll(e))
                return new Integer[]{advancement.getProgression(player)};
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        customAdvancements = (Expression<Advancement>) exprs[0];
        players = (Expression<Player>) exprs[1];
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET, REMOVE, RESET, DELETE, ADD -> CollectionUtils.array(Number.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        int progression;
        Number progress = 0;
        for (Player player : players.getAll(e)) {
            for (Advancement advancement : customAdvancements.getAll(e)) {
                progression = advancement.getProgression(player);
                switch (mode) {
                    case SET:
                        progress = (Number) delta[0];
                        progression = progress.intValue();
                        break;
                    case ADD:
                        progress = (Number) delta[0];
                        progression = progression + progress.intValue();
                        break;
                    case REMOVE:
                        progress = (Number) delta[0];
                        progression = progression - progress.intValue();
                        break;
                    case RESET, DELETE:
                        progression = 0;
                        break;
                    default:
                        break;
                }
                advancement.setProgression(player, min(max(progression, 0), advancement.getMaxProgression()));
            }
        }
    }
}
