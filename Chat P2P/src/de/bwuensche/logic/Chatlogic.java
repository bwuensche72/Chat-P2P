package de.bwuensche.logic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import de.bwuensche.objects.SocketHandler;
import de.bwuensche.objects.User;

public class Chatlogic {
    
    private SocketHandler sockethandler;
    private User user;
    private String chatPartnerName;
    
    public Chatlogic(User user) {
        
        this.user = user;
        this.sockethandler = new SocketHandler(user.getHost(), user.getPort());
    }
    
    private void exchangeNickname() {
        try {
            sockethandler.sendMessage(user.getNickname());
            chatPartnerName = sockethandler.readMessage();
            
        } catch (IOException e) {
            System.out.println("Der Austausch zwischen den Chatteilnehmern ist fehlgeschlagen");
        }
    }
    
    public void startChatRoom() {
        
        // Austausch der Nicknamen beider Teilnehmer des Chats
        exchangeNickname();
        
        // starten der Threads die im Hintergrund dauerhaft laufen
        new Thread(this::sendMessageThreadLoop).start();
        new Thread(this::readMessageThreadLoop).start();
    }
    
    private void readMessageThreadLoop() {
        
        while (!Thread.currentThread().isInterrupted()) {
            
            // Lesen einer Nachricht
            try {
                String message = sockethandler.readMessage();
                System.out.println(chatPartnerName + ": " + message);
                
            } catch (IOException e) {
                System.out.println("(" + chatPartnerName + " hat den Chat verlassen)");
                System.exit(0);
            }
        }
        
    }
    
    private void sendMessageThreadLoop() {
        
        while (!Thread.currentThread().isInterrupted()) {
            
            // Senden einer Nachricht
            try {
                DataInputStream inputStream = new DataInputStream(System.in);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                
                String message = bufferedReader.readLine();
                sockethandler.sendMessage(message);
                
            } catch (IOException e) {
                System.out.println("(" + chatPartnerName + " hat den Chat verlassen)");
                System.exit(0);
            }
            
        }
    }
}
