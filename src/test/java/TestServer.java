import java.io.*;
import java.net.*;

public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8989);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new FileReceiver(socket)).start();
        }
    }

    static class FileReceiver implements Runnable {
        private Socket socket;

        public FileReceiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                byte[] bytes = new byte[1024];
                InputStream in = socket.getInputStream();
                File file = new File("received_" + System.currentTimeMillis() + ".txt");
                FileOutputStream fos = new FileOutputStream(file);

                int count;
                while ((count = in.read(bytes)) > 0) {
                    fos.write(bytes, 0, count);
                }

                fos.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
