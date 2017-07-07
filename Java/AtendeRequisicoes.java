package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AtendeRequisicoes {
    
    ListaCandidatos lc;
    int port;

    AtendeRequisicoes(int port) {
        
        lc = new ListaCandidatos();
        lc.num_votos = 0;

        lc.candidatos.add(new Candidato(0, "Branco", "Nenhum"));    
        lc.candidatos.add(new Candidato(1, "Nulo", "Nenhum"));    
        lc.candidatos.add(new Candidato(2, "Julio Estrella", "ICMC"));
        lc.candidatos.add(new Candidato(3, "Elisa", "ICMC"));
        lc.candidatos.add(new Candidato(4, "Simões", "ICMC"));

        lc.candidatos.add(new Candidato(5, "Jonas de Carvalho", "EESC"));
        lc.candidatos.add(new Candidato(6, "Hélio Navarro", "EESC"));
        
        try {
            System.out.println("Criando Servidor TCP ");
            ServerSocket WAIT = new ServerSocket(port);
            
            while(true) { //for(int num_requisiçoes = 0; num_requisiçoes < 5; num_requisiçoes++){
                Socket SERVIDOR_SOCKET = WAIT.accept();
                
                Servidor s = new Servidor(lc, SERVIDOR_SOCKET, this);
                s.start();
                
                System.out.println("Numero de votos (mais brancos e nulos): " + lc.num_votos);
            }
            
        } catch(Exception e) {
            System.out.println("Erro nas requisições - " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
        AtendeRequisicoes ar = new AtendeRequisicoes(40010);
    }
}
