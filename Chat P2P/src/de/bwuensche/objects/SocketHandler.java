package de.bwuensche.objects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHandler {
    
    private Socket socket;
    
    /**
     * Dieser Handler kümmert sich um den Ablauf des Chats, welcher gewisse Angaben braucht, um kommunizieren zu können.
     * Vorgaben für den Nickname: 
     * - Der Name soll kleiner als 20 Zeichen lang sein.
     * - Der Name soll mindestens ein Zeichen lang sein. 
     * - Der Name soll aus Buchstaben und Zahlen bestehen, 
     *   nur Leerschritte sind nicht erlaubt.
     * 
     * @param host - die gewünschte Adresse
     * @param port - der gewünschte Port
     * @param name - der gewünschte Name des Benutzers
     */
    public SocketHandler(String host, int port) {
        try {
            // Unterscheidung ob ein client oder server erstellt werden soll
            if (host == null) {
                // Server
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    socket = serverSocket.accept();
                    InetAddress adress = socket.getLocalAddress();
                    System.out.println("Lokale Adresse: " + adress + ":" + port);
                }
            } else {
                // Client
                socket = new Socket(host, port);
                InetAddress adress = socket.getLocalAddress();
                System.out.println("Verbinde mit dem Server: " + adress + ":" + port);
            }
        } catch (UnknownHostException e) {
            System.out.println("Der Host ist uns unbekannt, versuche es bitte erneut");
        } catch (IOException e) {
            System.out.println("Es ist ein Fehler aufgetreten, bitte erneut versuchen");
        }
    }
    
    /**
     * Die Methode ermöglicht es Nachrichten zu lesen, welche verschickt werden über den Socket.
     * 
     * @throws IOException Signalisiert einen Fehler des I/Os in jeglicher Form
     */
    public String readMessage() throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        return inputStream.readUTF();
    }
    
    /**
     * Die Methode ermöglicht es Nachrichten zu versenden über den socket
     * 
     * @param message - Die Nachricht, die gesendet werden soll
     * @throws IOException Signalisiert einen Fehler des I/Os in jeglicher Form
     */
    public void sendMessage(String message) throws IOException {
        // Auslesung der gesendeten Nachricht
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        // Prüfung ob Inhalt in der Nachricht übermittelt wird
        if (message.isEmpty()) {
            return;
        }
        // senden der Nachricht
        outputStream.writeUTF(message);
        outputStream.flush();
    }
}
