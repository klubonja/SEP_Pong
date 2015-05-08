package server;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by laith on 04/05/15.
 */
public class JSONServer {

    private ServerSocket serverSocket;
    private int port;
    public static int clients = 0;


    public void establish(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        System.out.println("JSONServer has been established on port " + port);

    }


    public void accept() throws IOException {

        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new MyThreadHandler(socket);
            Thread t = new Thread(r);
            t.start();
            System.out.println("Server is accepting");
        }
    }

    public static class MyThreadHandler implements Runnable {
        private Socket socket;

        MyThreadHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            clients++;
            System.out.println(clients + " JSONClient(s) connected on port: " + socket.getPort());

            try {
                // For JSON Protocol
                   JSONObject jsonObject = receiveJSON();
                   sendJSON(jsonObject);

            } catch (IOException e) {

            } finally {
                try {
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSocket() throws IOException {
            socket.close();
        }


        /**
         * use the JSON Protocol to receive a json object as
         * String from the client and reconstructs that object
         * @return JSONObejct with the same state (data) as
         * the JSONObject the client sent as a String msg.
         * @throws IOException
         */
        public JSONObject receiveJSON() throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line = in.readLine();
            JSONObject jsonObject = new JSONObject(line);
            System.out.println("Got from client on port " + socket.getPort() + " " + jsonObject.toString(2));
            return jsonObject;
        }


        public void sendJSON(JSONObject jsonObject) throws IOException {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            out.write(jsonObject.toString() + "\n");
            out.flush();
            System.out.println("Sent to client on port " + socket.getPort() + " " + jsonObject.toString(2));
        }
    }


    public void start(int port) throws IOException{
        establish(port);
        accept();
    }

    public static void main(String[] args) {
        JSONServer server = new JSONServer();

        try {
            server.start(7777);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e);
        }
    }
}
