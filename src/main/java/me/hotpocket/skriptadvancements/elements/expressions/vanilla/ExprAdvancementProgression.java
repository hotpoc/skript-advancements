package me.hotpocket.skriptadvancements.elements.expressions.vanilla;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.hotpocket.skriptadvancements.utils.VanillaUtils;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprAdvancementProgression extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(ExprAdvancementProgression.class, Integer.class, ExpressionType.SIMPLE,
                "[the] [advancement] progress[ion] of %advancements% (for|of) %players%");
    }

    private Expression<Advancement> advancements;
    private Expression<Player> players;

    @Override
    protected @Nullable Integer[] get(Event e) {
        for (Player player : players.getAll(e))
            for (Advancement advancement : advancements.getAll(e))
                return new Integer[]{VanillaUtils.getProgression(player, advancement)};
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
        advancements = (Expression<Advancement>) exprs[0];
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
        for (Player player : players.getAll(e)) {
            for (Advancement advancement : advancements.getAll(e)) {
                switch (mode) {
                    case SET:
                        VanillaUtils.setProgression(player, advancement, ((Number) delta[0]).intValue());
                        break;
                    case ADD:
                        VanillaUtils.addProgression(player, advancement, ((Number) delta[0]).intValue());
                        break;
                    case REMOVE:
                        VanillaUtils.removeProgression(player, advancement, ((Number) delta[0]).intValue());
                        break;
                    case RESET, DELETE:
                        VanillaUtils.setProgression(player, advancement, 0);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
