package projetoredes;
import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente extends Thread {
    
    ListaCandidatos lc;
    int code;
    int port;
    
    public Cliente (ListaCandidatos lista, int c, int p) {
        lc = lista;
        code = c;
        port = p;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("Criando Cliente TCP ");
            
            Socket CLIENTE_SOCKET = new Socket("cosmos.lasdpc.icmc.usp.br", port);
            ObjectOutputStream ENVIA_OBJETO = new ObjectOutputStream(CLIENTE_SOCKET.getOutputStream());
            ObjectInputStream RECEBE_OBJETO = new ObjectInputStream(CLIENTE_SOCKET.getInputStream());
            //BufferedReader RECEBE = new BufferedReader(new InputStreamReader(CLIENTE_SOCKET.getInputStream())); //para ler strings somente
            
            ENVIA_OBJETO.writeObject(code);
            
            if (code == 888){
                ENVIA_OBJETO.writeObject(lc);
                System.out.println((String) RECEBE_OBJETO.readObject());
            }
            if (code == 999){
                lc = (ListaCandidatos) RECEBE_OBJETO.readObject();
                ENVIA_OBJETO.writeObject("candidatos recebidos");
            }
            
            System.out.println("Fechando Cliente TCP");
            
            ENVIA_OBJETO.close();
            RECEBE_OBJETO.close();
            CLIENTE_SOCKET.close();
            
            System.out.println("Cliente Fechado");
            
            try{ 
               Thread.sleep(1000);
            } catch(Exception e) {}
            
        } catch(Exception e){
            System.out.println("Erro" + e.getMessage());

        }
    }
}
