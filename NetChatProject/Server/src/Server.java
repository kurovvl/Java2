import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    // Vars
    private boolean working = true;
    private Scanner scanner;
    private HashMap<Socket, HashSet<Thread>> dbgSocketThreads = new HashMap<>();
    public static int port = 5190;

    // Gets
    public boolean isWorking() {
        return working;
    }

    private HashSet<Thread> dbgThreads = new HashSet<>();

    public void start() {
        var thread = new Thread(() -> {
            listenPort(port);
        });
        thread.setDaemon(true);
        thread.start();
        //dbgTimer();
    }

    private void dbgTimer() {
        new Thread(() -> {
            while (true)
                try {
                    Thread.sleep(2000);
                    checkThreadsAlive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    // Func
    private void listenPort(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            onIOException(e);
        }
        if (serverSocket != null) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    createThreads(socket);
                } catch (IOException e) {
                    onIOException(e);
                }
            }
        }
    }


    private void createThreads(Socket socket) {
        var readDaemon = new Thread(() -> {
            readData(socket);
        });
        var writeDaemon = new Thread(() -> {
            writeData(socket);
        });

        writeDaemon.setDaemon(true); // демон автозавершится когда основной поток завершится или поток породивший его?
        readDaemon.setDaemon(true);  // upd: проверка показала, что треды живут дальше.. а как их убить-то?

        var dbgDaemons = new HashSet<Thread>();
        dbgDaemons.add(writeDaemon);
        dbgDaemons.add(readDaemon);
        dbgSocketThreads.put(socket, dbgDaemons);


        dbgThreads.add(writeDaemon);
        dbgThreads.add(readDaemon);
        writeDaemon.start();
        readDaemon.start();
    }


    private void readData(Socket socket) {

        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while (true) {
                String s = input.readUTF();
                System.out.println("Client: " + s);
                if (s.equals("/stop")) {
                    working = false;
                    break;
                }
            }
        } catch (IOException e) {
            onIOException(e);
        }
        closeSocket(socket);
    }

    private void writeData(Socket socket) {
        try {
            scanner = new Scanner(System.in);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            while (true) {
                var message = scanner.nextLine().trim();
                if (!message.isEmpty())
                    output.writeUTF(message);
            }
        } catch (IOException e) {
            onIOException(e);
        }
        closeSocket(socket);
    }

    private void closeSocket(Socket socket) {

        dbgSocketThreads.get(socket).forEach(t -> {
            if (t.isAlive()) t.interrupt();
        });

        try {
            socket.close();
        } catch (IOException e) {
            onIOException(e);
        }
    }

    private void onIOException(IOException e) {
        System.out.println(e.getMessage());
    }

    private void checkThreadsAlive() {
        try {
            System.out.println(dbgThreads.stream().collect(Collectors.groupingBy(r -> r.isAlive(), Collectors.counting())));
            dbgSocketThreads.forEach((socket, threads) -> {
                System.out.print("\n" + socket.getPort() + ": " + socket.isConnected());
                threads.forEach(t -> System.out.print(", " + t.isAlive()));
                System.out.println();
                if (socket.isConnected()) {

                }
                if (socket.isClosed()) {
                    //dbgSocketThreads.remove(socket);
                }
            });
        } catch (Exception exception) {
        }
    }
}
