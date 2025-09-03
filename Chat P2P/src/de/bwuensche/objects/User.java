package de.bwuensche.objects;

/**
 * Dieser User besitzt gleichzeitig einen client als auch einen Server, um die Kommunikation in Sinne von Peer-to-Peer
 * zu ermöglichen.
 */
public class User {
    
    private String nickname;
    private String host;
    private int port;
    
    /**
     * Erstellung eines Benutzers des Chats, welcher gewisse Angaben erfordert, um teilnehmen zu können.
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
    public User(String host, int port, String nickname) {
        if (nickname.length() > 20 || nickname.isEmpty()) {
            throw new IllegalArgumentException("Der Name entspricht nicht den Vorgaben");
        }
        this.host = host;
        this.port = port;
        this.nickname = nickname;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public String getHost() {
        return host;
    }
    
    public int getPort() {
        return port;
    }
}
