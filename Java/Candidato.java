package client;

public class Candidato {
    int codigo_votacao;
    String nome_candidato;
    String partido;
    int num_votos;
    
      Candidato() {
        codigo_votacao = 0;
        nome_candidato = "";
        partido = "";
        num_votos = 0;
    }
    
    Candidato(int c, String n, String p) {
        codigo_votacao = c;
        nome_candidato = n;
        partido = p;
        num_votos = 0;
    }
    
    public void Imprime() {
        System.out.println("--------------------------------");
        System.out.println("cod_votacao: " + codigo_votacao);
        System.out.println("nome: " + nome_candidato);
        System.out.println("part: " + partido);
        System.out.println("votos: " + num_votos);
    }
}
