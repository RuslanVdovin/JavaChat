import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkService {

    private static NetworkService instance;
    private final ObjectInputStream is;
    private final ObjectOutputStream os;

    private NetworkService() {
        try {
            Socket socket = new Socket("localhost", 8189);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create network connection");
        }
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public Message read() throws IOException, ClassNotFoundException {
        return (Message) is.readObject();
    }

    public String getUserName() {
        try {
            return ((Message) is.readObject()).getAuthor();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void write(Message message) throws IOException {
        os.writeObject(message);
        os.flush();
    }

    public ObjectInputStream getInputStream() {
        return is;
    }

}
