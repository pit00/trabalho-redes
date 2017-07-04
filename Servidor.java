package projetoredes;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor extends Thread{
  
    ListaCandidatos lc;
    Socket SERVIDOR_SOCKET;
    
    public Servidor (ListaCandidatos lista, Socket s) {
        lc = lista;
        SERVIDOR_SOCKET = s;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("Criando nova thread");
            
            ObjectInputStream RECEBE_OBJETO = new ObjectInputStream(SERVIDOR_SOCKET.getInputStream());
            ObjectOutputStream ENVIA_OBJETO = new ObjectOutputStream(SERVIDOR_SOCKET.getOutputStream());
            //PrintWriter ENVIA = new PrintWriter(new OutputStreamWriter(SERVIDOR_SOCKET.getOutputStream()));
            
            int code = (int) RECEBE_OBJETO.readObject();
            
            if (code == 999){
                ENVIA_OBJETO.writeObject(lc);
                System.out.println((String) RECEBE_OBJETO.readObject());
            }
            if (code == 888){
                lc = (ListaCandidatos) RECEBE_OBJETO.readObject();
                ENVIA_OBJETO.writeObject("votos salvos");
                
                for(Candidato c : lc.candidatos){
                    System.out.println(c.nome_candidato + " - " + c.num_votos + "votos");
                }
            }
            
            System.out.println("Fechando thread");
            
            RECEBE_OBJETO.close();
            ENVIA_OBJETO.close();
            SERVIDOR_SOCKET.close();

            System.out.println("thread Fechada");
            
        } catch(Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
}
