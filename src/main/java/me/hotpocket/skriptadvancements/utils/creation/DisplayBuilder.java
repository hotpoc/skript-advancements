package me.hotpocket.skriptadvancements.utils.creation;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DisplayBuilder {

	private ItemStack icon = new ItemStack(Material.STONE_PICKAXE);
	private String title = "Title";
	private List<String> description = List.of("");
	private AdvancementFrameType frame = AdvancementFrameType.TASK;
	private boolean showToast = false;
	private boolean announceChat = false;
	private float x = 0;
	private float y = 0;

	public DisplayBuilder() {}

	public DisplayBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public DisplayBuilder setDescription(List<String> description) {
		this.description = description;
		return this;
	}

	public DisplayBuilder setIcon(ItemStack icon) {
		this.icon = icon;
		return this;
	}

	public DisplayBuilder setToast(boolean showToast) {
		this.showToast = showToast;
		return this;
	}

	public DisplayBuilder setAnnounce(boolean announceChat) {
		this.announceChat = announceChat;
		return this;
	}

	public DisplayBuilder setX(float x) {
		this.x = x;
		return this;
	}

	public DisplayBuilder setY(float y) {
		this.y = y;
		return this;
	}

	public DisplayBuilder setFrame(AdvancementFrameType frame) {
		this.frame = frame;
		return this;
	}

	public AdvancementDisplay build() {
		return new AdvancementDisplay(getIcon(), getTitle(), getFrame(), showToast(), announceChat(), getX(), getY(), getDescription());
	}

	public String getTitle() {
		return title;
	}

	public List<String> getDescription() {
		return description;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public boolean showToast() {
		return showToast;
	}

	public boolean announceChat() {
		return announceChat;
	}

	public AdvancementFrameType getFrame() {
		return frame;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public static DisplayBuilder fromAdvancementDisplay(AdvancementDisplay display) {
		return new DisplayBuilder()
				.setTitle(display.getTitle())
				.setDescription(display.getDescription())
				.setAnnounce(display.doesAnnounceToChat())
				.setToast(display.doesShowToast())
				.setFrame(display.getFrame())
				.setX(display.getX())
				.setY(display.getY())
				.setIcon(display.getIcon());
	}

}
