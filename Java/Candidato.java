package projetoredes;

import java.io.Serializable;

public class Candidato implements Serializable{
    public int codigo_votacao;
    public String nome_candidato;
    public String partido;
    public int num_votos;
    
    Candidato(int c, String n, String p) {
        codigo_votacao = c;
        nome_candidato = n;
        partido = p;
        num_votos = 0;
    
    }
    
}
