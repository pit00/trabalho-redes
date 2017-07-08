package client;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Urna extends JFrame implements ActionListener  {
    private static int porta = 40010;
    private String endereco = "cosmos.lasdpc.icmc.usp.br";
    
    ListaCandidatos lc = new ListaCandidatos();
    JButton votar, branco, nulo, carregar, finalizar;
    int votos_iniciais = 0;
    Cliente c = new Cliente(lc, porta, endereco);
    
    Urna () {
        super("Projeto de redes - Urna eletronica");
	setLayout(null);
        setSize(500, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        votar = new JButton("Votar");
        votar.setBounds(165, 50, 160, 30);
        votar.addActionListener(this);
        votar.setEnabled(false);
        add(votar);
        
        branco = new JButton("Votar Branco");
        branco.setBounds(165, 100, 160, 30);
        branco.addActionListener(this);
        branco.setEnabled(false);
        add(branco);
        
        nulo = new JButton("Votar Nulo");
        nulo.setBounds(165, 150, 160, 30);
        nulo.addActionListener(this);
        nulo.setEnabled(false);
        add(nulo);
        
        carregar = new JButton("Carregar Candidatos");
        carregar.setBounds(165, 200, 160, 30);
        carregar.addActionListener(this);
        add(carregar);
        
        finalizar = new JButton("Finalizar Votação");
        finalizar.setBounds(165, 250, 160, 30);
        finalizar.addActionListener(this);
        add(finalizar);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == carregar) {
            //carrega candidatos
            c.Conecta();
            c.PegaDados();
            try{ 
               Thread.sleep(500);
            } catch(Exception e) {}
            lc = c.lc;
            
            if(lc.candidatos.size() > 0) {
                votar.setEnabled(true);
                branco.setEnabled(true);
                nulo.setEnabled(true);
                carregar.setEnabled(false);
                
                votos_iniciais = lc.num_votos;
            }
        }
        
        if (ae.getSource() == votar) {
            
            System.out.println(lc.candidatos.size());
            Votar v = new Votar(lc);
        }
        
        if (ae.getSource() == branco) {
            lc.candidatos.get(0).num_votos++;
            lc.num_votos++;
        }
        
        if (ae.getSource() == nulo) {
            lc.candidatos.get(1).num_votos++;
            lc.num_votos++;
        }
        
        if (ae.getSource() == finalizar) {
            //enviar dados
            if(lc.num_votos > votos_iniciais) {
                c.EnviaDados();
                try{ 
                   Thread.sleep(2000);
                } catch(Exception e) {}
                c.Desconecta();
            }
            
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        Urna u = new Urna();
    }
}