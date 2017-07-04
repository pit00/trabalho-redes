package projetoredes;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaCandidatos implements Serializable{
    ArrayList<Candidato> candidatos;
    int num_votos;
    
    ListaCandidatos() {
        candidatos = new ArrayList<Candidato>();
        num_votos = 0;
    }
}
