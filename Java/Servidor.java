package server;

import java.io.*;
import java.net.*;

public class Servidor extends Thread{
  
    ListaCandidatos lc;
    Socket SERVIDOR_SOCKET;
    AtendeRequisicoes atr;
    
    public Servidor (ListaCandidatos lista, Socket s, AtendeRequisicoes ar) {
        lc = lista;
        SERVIDOR_SOCKET = s;
        atr = ar;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("Criando nova thread");
            
            BufferedReader RECEBE = new BufferedReader(new InputStreamReader(SERVIDOR_SOCKET.getInputStream(), "UTF-8"));
            PrintWriter ENVIA = new PrintWriter(new OutputStreamWriter(SERVIDOR_SOCKET.getOutputStream())); //para escrever string somente
            
            int code =  Integer.parseInt(RECEBE.readLine());
            
            if (code == 999){
                System.out.println("enviando dados");
                ENVIA.println(""+lc.candidatos.size());
                ENVIA.flush();
                
                for(Candidato c : lc.candidatos) {
                    ENVIA.println(""+c.codigo_votacao); ENVIA.flush();
                    ENVIA.println(c.nome_candidato); ENVIA.flush();
                    ENVIA.println(c.partido); ENVIA.flush();
                    ENVIA.println(""+c.num_votos); ENVIA.flush();
                }
                
                System.out.println("dados enviados");
                //ENVIA_OBJETO.writeObject(lc);
                //System.out.println((String) RECEBE_OBJETO.readObject());
            }
            
            code =  Integer.parseInt(RECEBE.readLine());
            
            if (code == 888){
                //lc = (ListaCandidatos) RECEBE_OBJETO.readObject();
                //ENVIA_OBJETO.writeObject("votos salvos");
                
                lc.num_votos = Integer.parseInt(RECEBE.readLine());
                
                //printa o resultado da votação
                for(Candidato c : lc.candidatos){
                    c.num_votos = Integer.parseInt(RECEBE.readLine());
                    System.out.println(c.nome_candidato + " - " + c.num_votos + " votos");
                }
                System.out.println();
            }
            
            System.out.println("Fechando thread");
            
            RECEBE.close();
            ENVIA.close();
            SERVIDOR_SOCKET.close();
            
            atr.lc = lc;
            System.out.println("thread Fechada");
            
        } catch(Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
}