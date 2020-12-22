import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SerialHandler implements Closeable, Runnable {

    private static int cnt = 0;
    private String userName;
    private final ObjectInputStream is;
    private final ObjectOutputStream os;
    private boolean running;
    private final byte [] buffer;
    private final ChatServer server;
    private List<String>group;


    public SerialHandler(Socket socket, ChatServer server) throws IOException {
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        cnt++;
        userName = "user#" + cnt;
        running = true;
        buffer = new byte[256];
        this.server = server;
        os.writeObject(Message.of(userName, "OK"));
        os.flush();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = (Message) is.readObject();

                if (message.getMessage().startsWith("/changeNick")) {
                    String[] data = message.getMessage().split(" ");
                    String oldName = userName;
                    userName = data[1];
                    String msg = String.format("User %s change name to %s", oldName, userName);
                    sendMessage(Message.of(userName, msg));
                    continue;
                }
                if (message.getMessage().startsWith("/private")) {
                    String[] data = message.getMessage().split(" ");
                    String nick = data[1];
                    String msg = data[2];
                    sendMessage(message);
                    server.sendMessageTo(userName, nick, msg);
                    continue;
                }
                if (message.getMessage().startsWith("/group")) {
                    String[] data = message.getMessage().split(" ");
                    String nameOfGroup = data[1];
                    ArrayList<String> members = new ArrayList<>();
                    for (int i = 2; i< data.length; i++) {
                        members.add(data[i]);
                    }
                    members.add(userName);
                    server.newGroup(nameOfGroup, members);
                    server.messageToGroup(nameOfGroup,
                            Message.of(" ", "You were added to the group " + nameOfGroup));
                    continue;
                }

                if (message.getMessage().startsWith("/sendGroup")) {
                    String[] data = message.getMessage().split(" ");
                    String name = data[1];
                    if(!server.getGroups().containsKey(name)){
                        continue;
                    }
                    String msgToGroup = data[2];
                    for (int i = 3; i <data.length ; i++) {
                        msgToGroup += data[i];
                    }
                    server.messageToGroup(name, Message.of(userName, msgToGroup));
                    continue;
                }

                message.setAuthor(userName);
                System.out.println(message);
                server.broadCast(message);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Exception while read");
                break;
            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        os.writeObject(message);
        os.flush();
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void close() throws IOException {
        os.close();
        is.close();
    }
}
