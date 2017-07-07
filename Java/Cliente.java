package client;

import java.io.*;
import java.net.*;

public class Cliente {
    
    ListaCandidatos lc;
    int port;
    String endereco;
    private int i;
    private int candidatos;     // numero de candidatos
    Socket CLIENTE_SOCKET;
    BufferedReader RECEBE;
    PrintWriter ENVIA;
    public Cliente (ListaCandidatos lista, int p, String e) {
        lc = lista;
        port = p;
        endereco = e;
    }
    
    public void Conecta() {
        try {
            System.out.println("Criando Cliente TCP ");
            CLIENTE_SOCKET = new Socket(endereco, port);
            //ObjectOutputStream ENVIA_OBJETO = new ObjectOutputStream(CLIENTE_SOCKET.getOutputStream());
            //ObjectInputStream RECEBE_OBJETO = new ObjectInputStream(CLIENTE_SOCKET.getInputStream());
            RECEBE = new BufferedReader(new InputStreamReader(CLIENTE_SOCKET.getInputStream(), "UTF-8"));
            ENVIA = new PrintWriter(new OutputStreamWriter(CLIENTE_SOCKET.getOutputStream())); //para escrever string somente
        } catch(Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
    
    public void Desconecta() {
        try {
            System.out.println("Fechando Cliente TCP");
            RECEBE.close();
            ENVIA.close();
            CLIENTE_SOCKET.close();
            System.out.println("Cliente Fechado");
        } catch(Exception e){
            System.out.println("Erro" + e.getMessage());
        }
    }
    
    public void PegaDados() {
        try {             
            
            ENVIA.println(""+999);
            ENVIA.flush();
            //System.out.println(code);   //teste

            System.out.println("esperando qtdd de dados");
            
            candidatos = Integer.parseInt(RECEBE.readLine());
            System.out.println(candidatos);

            System.out.println("esperando dados");
            
            for(i = 0; i < candidatos; i++) {
                Candidato c = new Candidato();
                c.codigo_votacao = Integer.parseInt(RECEBE.readLine());
                c.nome_candidato = RECEBE.readLine();
                c.partido = RECEBE.readLine();
                c.num_votos = Integer.parseInt(RECEBE.readLine());
                c.Imprime();
                lc.candidatos.add(c);
            }
            //lc = (ListaCandidatos) RECEBE_OBJETO.readObject();
            System.out.println("candidatos recebidos");

            Thread.sleep(500);
           
            
        } catch(Exception e) {}
            
    }
    
    public void EnviaDados(){
        try {
            
            ENVIA.println(""+888);
            ENVIA.flush();
            
            ENVIA.println("" + lc.num_votos);
            ENVIA.flush();
            
            for(Candidato c : lc.candidatos) {
                c.Imprime();
                ENVIA.println("" + c.num_votos);
                ENVIA.flush();
            }
            
            ENVIA.println("desconectou");
            
            Thread.sleep(800);
            
            
        } catch(Exception e) {}
    }
}
