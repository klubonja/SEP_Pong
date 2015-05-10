package server;

import org.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by laith on 04/05/15.
 */
public class JSONClient {

    private String host;
    private int port;
    private Socket socket;
    private final String DEFAULT_HOST = "localhost";


    public void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        System.out.println("Client has been connected..");
    }


    /**
     * use the JSON Protocol to receive a json object as
     * String from the client and reconstructs that object
     *
     * @return JSONObejct with the same state (data) as
     * the JSONObject the client sent as a String msg.
     * @throws IOException
     */
    public JSONObject receiveJSON() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = in.readLine();
        JSONObject jsonObject = new JSONObject(line);
        System.out.println("Got from server: " + " " + jsonObject.toString(2));
        return jsonObject;
    }


    public void sendJSON(JSONObject jsonObject) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
        out.write(jsonObject.toString() + "\n");
        out.flush();
        System.out.println("Sent to server: " + " " + jsonObject.toString(2));
    }
    
    public String getHost(){
    	return this.host;
    }
    
    public Socket getSocket(){
    	return this.socket;
    }


    public static void main(String[] args) {
        JSONClient client = new JSONClient();
        try{

            client.connect("localhost", 7777);
            // For JSON call sendJSON(JSON json) & receiveJSON();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Player ", "Lars");
            client.sendJSON(jsonObject);
            client.receiveJSON();
        }

        catch (ConnectException e) {
            System.err.println(client.host + " connect refused");
            return;
        }

        catch(UnknownHostException e){
            System.err.println(client.host + " Unknown host");
            client.host = client.DEFAULT_HOST;
            return;
        }

        catch (NoRouteToHostException e) {
            System.err.println(client.host + " Unreachable");
            return;

        }

        catch (IllegalArgumentException e){
            System.err.println(client.host + " wrong port");
            return;
        }

        catch(IOException e){
            System.err.println(client.host + ' ' + e.getMessage());
            System.err.println(e);
        }
        finally {
            try {
                client.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 

    }

}

