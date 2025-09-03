package de.bwuensche.application;

import java.util.regex.Pattern;

import de.bwuensche.logic.Chatlogic;
import de.bwuensche.objects.User;

public class P2PChatclientMain {
    
    public static void main(String[] args) {
        
        User user;
        
        // Prüfung der Startparameter vor dem Zugriff darauf
        if (args == null) {
            throw new IllegalArgumentException("Es wurden keine Startparameter übergeben");
        }
        
        // Prüfung der Paramteranzahl
        if (args.length != 2) {
            throw new IllegalArgumentException("Falsche Anzahl an übergebenen Startparametern");
        }
        
        // Eingabe und Auslesung der ports und co.
        Pattern equalsPattern = Pattern.compile("=");
        Pattern colonPattern = Pattern.compile(":");
        
        String[] userTypeParamterArray = equalsPattern.split(args[0]);
        String[] nicknameParameterArray = equalsPattern.split(args[1]);
        
        // Prüfung des Arrays vor dem Zugriff
        if (nicknameParameterArray == null) {
            throw new IllegalArgumentException(
                "Es wurden entweder invalide Startparameter übergeben oder "
                    + "diese wurden in der falschen Reihenfolge übergeben");
        }
        
        // Evaluierung des Nicknamens
        String nickname = nicknameParameterArray[1];
        
        // Evaluierung des Typens des Users
        if (userTypeParamterArray[0].equals("-connect")) {
            // Client
            // Herausziehen der Parameter
            String[] parameterArray = colonPattern.split(userTypeParamterArray[1]);
            String host = parameterArray[0];
            int port = Integer.parseInt(parameterArray[1]);
            
            // erstellung des Benutzers mit den übergebenen Parametern
            user = new User(host, port, nickname);
            Chatlogic logic = new Chatlogic(user);
            logic.startChatRoom();
            
        } else if (userTypeParamterArray[0].equals("-listen")) {
            // Server
            // Herausziehen der Parameter
            int port = Integer.parseInt(userTypeParamterArray[1]);
            
            // erstellung des Benutzers mit den übergebenen Parametern
            user = new User(null, port, nickname);
            Chatlogic logic = new Chatlogic(user);
            logic.startChatRoom();
            
        } else {
            throw new IllegalArgumentException(
                "Es wurden entweder invalide Startparameter übergeben oder "
                    + "diese wurden in der falschen Reihenfolge übergeben");
        }
    }
    
}
