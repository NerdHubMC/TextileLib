package nerdhub.textilelib.events.render;

import nerdhub.textilelib.events.CancelableEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Gui;

@Environment(EnvType.CLIENT)
public class GuiDrawEvent extends CancelableEvent {

    private Gui gui;
    private int mouseX, mouseY;
    private float partialTicks;

    public GuiDrawEvent(Gui gui, int mouseX, int mouseY, float partialTicks) {
        this.gui = gui;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTicks = partialTicks;
    }

    public Gui getGui() {
        return gui;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
