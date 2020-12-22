import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;

public class CharReader extends Thread {

    private final TextArea out;
    private final ObjectInputStream is;
    private boolean running;

    public CharReader(TextArea out, ObjectInputStream is) {
        this.out = out;
        this.is = is;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = (Message) is.readObject();
                out.appendText(message.toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}