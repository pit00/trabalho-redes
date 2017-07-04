package projetoredes;

import java.net.ServerSocket;
import java.net.Socket;

public class AtendeRequisicoes {
    
    ListaCandidatos lc;
    
    AtendeRequisicoes(int port) {
        lc = new ListaCandidatos();
        lc.num_votos = 0;
        
	lc.candidatos.add(new Candidato(2, "Julio Estrella", "ICMC"));
	lc.candidatos.add(new Candidato(3, "Thiago Pardo", "ICMC"));
	lc.candidatos.add(new Candidato(4, "Ellen", "ICMC"));
	lc.candidatos.add(new Candidato(5, "Elisa", "ICMC"));
	lc.candidatos.add(new Candidato(6, "Raimundo Nonato", "ICMC"));
	lc.candidatos.add(new Candidato(7, "João Batista", "ICMC"));
	lc.candidatos.add(new Candidato(8, "Pedro Rios", "ICMC"));
	lc.candidatos.add(new Candidato(9, "Maria Carbinatto", "ICMC"));
	lc.candidatos.add(new Candidato(10, "Kalinka", "ICMC"));
	lc.candidatos.add(new Candidato(11, "Monaco", "ICMC"));
	lc.candidatos.add(new Candidato(12, "Simões", "ICMC"));

	lc.candidatos.add(new Candidato(13, "Flávio Marques", "EESC"));
	lc.candidatos.add(new Candidato(14, "Jaime Duduch", "EESC"));
	lc.candidatos.add(new Candidato(15, "Gherhardt Ribatski", "EESC"));
	lc.candidatos.add(new Candidato(16, "Glauco Caurin", "EESC"));
	lc.candidatos.add(new Candidato(17, "Jonas de Carvalho", "EESC"));
	lc.candidatos.add(new Candidato(18, "Hélio Navarro", "EESC"));
        
        try {
            
            System.out.println("Criando Servidor TCP ");
            ServerSocket WAIT = new ServerSocket(port);
            
            while(true) {
                Socket SERVIDOR_SOCKET = WAIT.accept();
                
                Servidor s = new Servidor(lc, SERVIDOR_SOCKET);
                s.start();
                
                try{ 
                   Thread.sleep(500);
                } catch(Exception e) {}
                
                lc = s.lc;
                System.out.println("Numero de votos (mais brancos e nulos): "+lc.num_votos);
                System.out.println();
            }
        } catch(Exception e) {
            System.out.println("Erro nas requisições - " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        AtendeRequisicoes ar = new AtendeRequisicoes(40010);
        
    }
}
