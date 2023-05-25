package org.milaifontanals.gestiometge;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.TableModel;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.interficie.IGestorCitesMediques;

public class VistaMetge extends JFrame {
    private JTable tableMetges;
    private JComboBox<String> comboBoxEspecialitats;
    private JComboBox<String> comboBoxEspecialitats2;
    private DefaultTableModel modelMetges;
    private DefaultListModel<String> modelEspecialitats;

    public VistaMetge(IGestorCitesMediques obj) {
        //Configuracio finestra
        super("Llista Metges");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        // Creacio dels components
        JLabel lblFiltre = new JLabel("Filtrar per especialitat:");
        comboBoxEspecialitats = new JComboBox<>();
        comboBoxEspecialitats2 = new JComboBox<>();
        JButton btnMostrarMetge = new JButton("Mostrar dades del metge");
        JButton btnEliminarEspecialitat = new JButton("Eliminar especialitat");
        JButton btnAfegirEspecialitat = new JButton("Afegir especialitat");
        tableMetges = new JTable();
        modelMetges = new DefaultTableModel(new Object[]{"Codi", "Nom"}, 0);
        tableMetges.setModel(modelMetges);
        modelEspecialitats = new DefaultListModel<>();
        JList<String> llistaEspecialitats = new JList<>(modelEspecialitats);

        // Afegir components a la finestra
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(lblFiltre);
        panelSuperior.add(comboBoxEspecialitats);
        panelSuperior.add(btnMostrarMetge);
        contentPane.add(panelSuperior, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(tableMetges), BorderLayout.CENTER);
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        JPanel panelBotons = new JPanel(new FlowLayout());
        panelBotons.add(btnEliminarEspecialitat);
        panelBotons.add(btnAfegirEspecialitat);
        panelBotons.add(comboBoxEspecialitats2);
        panelInferior.add(panelBotons);
        panelInferior.add(new JScrollPane(llistaEspecialitats));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        // Inicializacio de dades
        List<Metge> metges = obj.getAllMetges();
        metges.forEach(m -> modelMetges.addRow(new Object[]{m.getCodiEmpleat(), m.getNom()}));
        List<Especialitat> especialitats = obj.getAllEspecialitats();
        comboBoxEspecialitats.addItem("");
        especialitats.forEach(e -> comboBoxEspecialitats.addItem(e.getNom()));
        especialitats.forEach(e -> comboBoxEspecialitats2.addItem(e.getNom()));

        //Events
        comboBoxEspecialitats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelEspecialitats.clear();
                String especialitatSeleccionada = (String) comboBoxEspecialitats.getSelectedItem();
                List<Metge> metgesFiltrats;
                if(especialitatSeleccionada.equals("")){
                    metgesFiltrats = obj.getAllMetges();
                }else{
                    metgesFiltrats = obj.getMetgesByEspecialitat(especialitatSeleccionada); 
                }
                modelMetges.setRowCount(0);
                if(!metgesFiltrats.isEmpty()){
                    metgesFiltrats.forEach(mf -> modelMetges.addRow(new Object[]{mf.getCodiEmpleat(), mf.getNom()}));
                }
            }
        });

        tableMetges.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaSeleccionada = tableMetges.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int codiMetgeFilaSelec = (int) modelMetges.getValueAt(filaSeleccionada, 0);
                    List<Especialitat> metgeEspecialitats = obj.getEspecialitatByMetge(codiMetgeFilaSelec);
                    modelEspecialitats.clear();
                    if(!metgeEspecialitats.isEmpty()){
                    metgeEspecialitats.forEach(me -> modelEspecialitats.addElement(me.getNom()));
                    }
                }
            }
        });

        btnMostrarMetge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableMetges.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int codiMetgeFilaSelec = (int) modelMetges.getValueAt(filaSeleccionada, 0);
                    Persona metgeSeleccionat = obj.getPersonaByMetge(codiMetgeFilaSelec);
                    JOptionPane.showMessageDialog(VistaMetge.this, metgeSeleccionat, "Dades del metge", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(VistaMetge.this, "Selecciona un metge de la llista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminarEspecialitat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableMetges.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int codiMetgeFilaSelec = (int) modelMetges.getValueAt(filaSeleccionada, 0);
                    int indexEspecialitatSeleccionada = llistaEspecialitats.getSelectedIndex();
                    if (indexEspecialitatSeleccionada >= 0) {
                        String especialitatSelec = modelEspecialitats.getElementAt(indexEspecialitatSeleccionada);
                        boolean eliminarEspecialitat = obj.deleteEspecialitatToMetge(codiMetgeFilaSelec, especialitatSelec);
                        modelEspecialitats.remove(indexEspecialitatSeleccionada);
                        
                        String especialitatSeleccionada = (String) comboBoxEspecialitats.getSelectedItem();
                        List<Metge> metgesFiltrats;
                        if(especialitatSeleccionada.equals("")){
                            metgesFiltrats = obj.getAllMetges();
                        }else{
                            metgesFiltrats = obj.getMetgesByEspecialitat(especialitatSeleccionada); 
                        }
                        modelMetges.setRowCount(0);
                        if(!metgesFiltrats.isEmpty()){
                            metgesFiltrats.forEach(mf -> modelMetges.addRow(new Object[]{mf.getCodiEmpleat(), mf.getNom()}));
                        }
                    } else {
                        JOptionPane.showMessageDialog(VistaMetge.this, "Selecciona una especialitat de la llista", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VistaMetge.this, "Selecciona un metge de la llista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAfegirEspecialitat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableMetges.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int codiMetgeFilaSelec = (int) modelMetges.getValueAt(filaSeleccionada, 0);
                    String especialitatSeleccionada = (String) comboBoxEspecialitats2.getSelectedItem();
                    boolean asignarEspecialitat = obj.addEspecialitatToMetge(codiMetgeFilaSelec, especialitatSeleccionada);
                    
                    filaSeleccionada = tableMetges.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    codiMetgeFilaSelec = (int) modelMetges.getValueAt(filaSeleccionada, 0);
                    List<Especialitat> metgeEspecialitats = obj.getEspecialitatByMetge(codiMetgeFilaSelec);
                    modelEspecialitats.clear();
                    if(!metgeEspecialitats.isEmpty()){
                    metgeEspecialitats.forEach(me -> modelEspecialitats.addElement(me.getNom()));
                    }
                }
                } else {
                    JOptionPane.showMessageDialog(VistaMetge.this, "Seleccione un médico de la lista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void mostrar() {
        this.setVisible(true);
    }
}




