package server;

import java.util.ArrayList;

public class ListaCandidatos {
    ArrayList<Candidato> candidatos;
    int num_votos;
    
    ListaCandidatos() {
        candidatos = new ArrayList<>();
        num_votos = 0;
    }
}
