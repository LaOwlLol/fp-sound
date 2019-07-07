package setup;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *  A wrapper for javax.sound.sampled.LineEvent which implements javax.sound.sampled.LineListener and used by Track to provide monitoring functionality.
 */
public class LineStatus implements LineListener {

    private LineEvent status;

    /**
     * Get the latest event status type.
     * @return
     */
    public String getType() {
        if (this.status != null) {
            return this.status.getType().toString();
        }
        else return "closed";
    }

    /**
     * Override of LineListener interface callback for java runtime events.
     * @param event LineEvent.
     */
    @Override
    public void update(LineEvent event) {
        this.status = event;
    }
}
