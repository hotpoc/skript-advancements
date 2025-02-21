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
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.utils.CustomUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Name("Advancement Tab Section")
@Description("Creates a custom advancement tab.")
@Since("1.4")

public class SecAdvancementTab extends EffectSection {

    public RootAdvancement rootAdvancement;
    private List<BaseAdvancement> advancements = new ArrayList<>();
    private String tabName;

    static {
        Skript.registerSection(SecAdvancementTab.class, "create [a[n]] [new] advancement tab named %string%");
    }

    private Expression<String> name;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        if (getParser().isCurrentSection(SecAdvancementTab.class)) {
            Skript.error("The advancement tab creation section is not meant to be put inside of another advancement tab creation section.");
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
        if (tab != null && !tab.isInitialised()) {
            RootAdvancement root = new RootAdvancement(tab, "temp_root_advancement_name_1289587", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), CustomUtils.getTexture(Material.DIAMOND_BLOCK));
            BaseAdvancement tempBase = new BaseAdvancement("name1", new AdvancementDisplay(Material.DIAMOND, "title", AdvancementFrameType.TASK, false, false, 0, 0, "description"), root);
            tab.registerAdvancements(root, tempBase);
            CustomUtils.getAPI().unregisterAdvancementTab(tabName);
        }
        if (tab != null && (tab.isInitialised() || tab.isActive())) {
            CustomUtils.getAPI().unregisterAdvancementTab(tabName);
        }
        CustomUtils.getAPI().createAdvancementTab(tabName);
        getParser().getCurrentSections().add(this);
        return walk(event, true);
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create an advancement tab with the name " + name.toString(event, debug);
    }

    public void addAdvancement(BaseAdvancement addition) {
        List<BaseAdvancement> toBeRemoved = new ArrayList<>();
        advancements.forEach((advancement) -> {
            if (addition.getKey().getKey().equalsIgnoreCase(advancement.getKey().getKey()))
                toBeRemoved.add(advancement);
        });
        advancements.removeAll(toBeRemoved);
        advancements.add(addition);
    }

    public void removeAdvancement(BaseAdvancement removed) {
        advancements.remove(removed);
    }

    public Collection<BaseAdvancement> getAdvancements() {
        return Collections.unmodifiableCollection(advancements);
    }

    public String getTabName() {
        return tabName;
    }
}
