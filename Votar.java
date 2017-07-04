package projetoredes;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Votar extends JFrame implements MouseListener, ActionListener, KeyListener  {

    ListaCandidatos lc;
    
    JButton btvoltar, btvotar;
    JTextField txt1,txt2, txt3;
    JLabel lbl1, lbl2, lbl3;
    
    DefaultTableModel modelo;
    JTable tabela;
    JScrollPane scrollpane;
    
    Votar(ListaCandidatos lista) {
        super("Projeto de redes - Urna eletronica");
	setLayout(null);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        String[] colunas = new String[]{"Código","Nome","Partido"};
        lc = lista;
        
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col)  {   
                return false;   
            }   
	};
		  
        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Partido");
        
	tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        tabela.addKeyListener(this);
        tabela.addMouseListener(this);
        
        scrollpane = new JScrollPane(tabela);
        scrollpane.setBounds(10,40,330,400);	
        add(scrollpane);

        lbl1 = new JLabel("Código");
        lbl1.setBounds(390,105,80,20);
        add(lbl1);

        lbl2 = new JLabel("Nome");
        lbl2.setBounds(390,145,80,20);
        add(lbl2);

        lbl3 = new JLabel("Partido");
        lbl3.setBounds(390,185,80,20);
        add(lbl3);

        txt1 = new JTextField();
        txt1.setBounds(470,105,160,30);
        txt1.setEnabled(false);
        add(txt1);

        txt2 = new JTextField();
        txt2.setBounds(470,145,160,30);
        txt2.setEnabled(false);
        add(txt2);

        txt3 = new JTextField();
        txt3.setBounds(470,185,160,30);
        txt3.setEnabled(false);
        add(txt3);
        
        btvotar = new JButton("Votar");
        btvotar.setBounds(430, 245, 160, 30);
        btvotar.addActionListener(this);
        btvotar.setEnabled(false);
        add(btvotar);
        
        btvoltar = new JButton("Voltar");
        btvoltar.setBounds(430,285,160,30);
        btvoltar.addActionListener(this);
        btvoltar.setEnabled(true);
        add(btvoltar);

        setVisible(true);
        
        for(int i=0; i<lc.candidatos.size(); i++) {
            txt1.setText(""+lc.candidatos.get(i).codigo_votacao);
            txt2.setText(lc.candidatos.get(i).nome_candidato);
            txt3.setText(lc.candidatos.get(i).partido);
            String[] linha = new String[]{txt1.getText(), txt2.getText(), txt3.getText()};
            modelo.addRow(linha);	
	}

    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==btvoltar){
            dispose();
            return;
        }  
        if(ae.getSource()==btvotar){
            int linha = tabela.getSelectedRow();
            Candidato c = lc.candidatos.get(linha);
            c.num_votos++;
            lc.num_votos++;
            dispose();
            return;
        }  
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int linha = tabela.getSelectedRow(); //pega linha selecionada
        if( linha != -1){
            String cod =""+ tabela.getValueAt(linha , 0) ;
            String can =""+ tabela.getValueAt(linha , 1);
            String par =""+ tabela.getValueAt(linha , 2);
            txt1.setText(cod);
            txt2.setText(can);
            txt3.setText(par);
            btvotar.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int linha = tabela.getSelectedRow(); //pega linha selecionada
        if( linha != -1){
            String cod =""+ tabela.getValueAt(linha , 0) ;
            String can =""+ tabela.getValueAt(linha , 1);
            String par =""+ tabela.getValueAt(linha , 2);
            txt1.setText(cod);
            txt2.setText(can);
            txt3.setText(par);
            btvotar.setEnabled(true);
        }
    }
}