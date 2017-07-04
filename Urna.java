package projetoredes;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Urna extends JFrame implements ActionListener  {

    ListaCandidatos lc = new ListaCandidatos();
    JButton votar, branco, nulo, carregar, finalizar;
    
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
            votar.setEnabled(true);
            branco.setEnabled(true);
            nulo.setEnabled(true);
            carregar.setEnabled(false);
            //carrega candidatos
            Cliente c = new Cliente(lc, 999, 40010);
            c.start();
            try{ 
               Thread.sleep(1000);
            } catch(Exception e) {}
            lc = c.lc;
        }
        
        if (ae.getSource() == votar) {
            
            System.out.println(lc.candidatos.size());
            Votar v = new Votar(lc);
        }
        
        if (ae.getSource() == branco) {
            lc.num_votos++;
        }
        
        if (ae.getSource() == nulo) {
            lc.num_votos++;
        }
        
        if (ae.getSource() == finalizar) {
            //enviar dados
            Cliente c = new Cliente(lc, 888, 40010);
            c.start();
            
            try{ 
               Thread.sleep(1100);
            } catch(Exception e) {}
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        Urna u = new Urna();
    }
}
