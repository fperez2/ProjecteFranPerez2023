package org.milaifontanals.gestiometge;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaMetge extends JFrame {
    private JTable tablaDoctores;
    private JComboBox<String> comboBoxEspecial;
    private DefaultTableModel modeloTablaDoctores;
    private DefaultListModel<String> modeloListaEspecialidades;

    public VistaMetge() {
        // Configuración de la ventana
        super("Lista de médicos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        // Creación de los componentes
        JLabel lblFiltro = new JLabel("Filtrar por especialidad:");
        comboBoxEspecial = new JComboBox<>();
        JButton btnMostrarDatos = new JButton("Mostrar datos del médico");
        JButton btnEliminarEspecialidad = new JButton("Eliminar especialidad");
        JButton btnAgregarEspecialidad = new JButton("Agregar especialidad");
        tablaDoctores = new JTable();
        modeloTablaDoctores = new DefaultTableModel(new Object[]{"Nombre", "Apellido"}, 0);
        tablaDoctores.setModel(modeloTablaDoctores);
        modeloListaEspecialidades = new DefaultListModel<>();
        JList<String> listaEspecialidades = new JList<>(modeloListaEspecialidades);

        // Agregar componentes a la ventana
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(lblFiltro);
        panelSuperior.add(comboBoxEspecial);
        panelSuperior.add(btnMostrarDatos);
        contentPane.add(panelSuperior, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(tablaDoctores), BorderLayout.CENTER);
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnEliminarEspecialidad);
        panelBotones.add(btnAgregarEspecialidad);
        panelInferior.add(panelBotones);
        panelInferior.add(new JScrollPane(listaEspecialidades));
        contentPane.add(panelInferior, BorderLayout.SOUTH);

        // Inicialización de datos
//        DoctorController doctorController = new DoctorController();
//        List<Doctor> doctores = doctorController.getDoctores();
//        doctores.forEach(d -> modeloTablaDoctores.addRow(new Object[]{d.getNombre(), d.getApellido()}));
//        List<String> especialidades = doctorController.getEspecialidades();
        comboBoxEspecial.addItem("");
//        especialidades.forEach(comboBoxEspecial::addItem);

        // Manejo de eventos
        comboBoxEspecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String especialidadSeleccionada = (String) comboBoxEspecial.getSelectedItem();
                //List<Doctor> doctoresFiltrados = doctorController.filtrarDoctores(especialidadSeleccionada);
                modeloTablaDoctores.setRowCount(0);
                //doctoresFiltrados.forEach(d -> modeloTablaDoctores.addRow(new Object[]{d.getNombre(), d.getApellido()}));
            }
        });

        tablaDoctores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaSeleccionada = tablaDoctores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    //Doctor doctorSeleccionado = doctores.get(filaSeleccionada);
                    modeloListaEspecialidades.clear();
                    //doctorSeleccionado.getEspecialidades().forEach(modeloListaEspecialidades::addElement);
                }
            }
        });

        btnMostrarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaDoctores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    //Doctor doctorSeleccionado = doctores.get(filaSeleccionada);
                    JOptionPane.showMessageDialog(VistaMetge.this, "doctorSeleccionado", "Datos del médico", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(VistaMetge.this, "Seleccione un médico de la lista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminarEspecialidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaDoctores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int indiceEspecialidadSeleccionada = listaEspecialidades.getSelectedIndex();
                    if (indiceEspecialidadSeleccionada >= 0) {
                        //Doctor doctorSeleccionado = doctores.get(filaSeleccionada);
                        //String especialidadSeleccionada = doctorSeleccionado.getEspecialidades().get(indiceEspecialidadSeleccionada);
                        //doctorController.eliminarEspecialidad(doctorSeleccionado, especialidadSeleccionada);
                        modeloListaEspecialidades.remove(indiceEspecialidadSeleccionada);
                    } else {
                        JOptionPane.showMessageDialog(VistaMetge.this, "Seleccione una especialidad de la lista", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(VistaMetge.this, "Seleccione un médico de la lista", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAgregarEspecialidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaDoctores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    //Doctor doctorSeleccionado = doctores.get(filaSeleccionada);
//                    String especialidadSeleccionada = (String) JOptionPane.showInputDialog(
//                            VistaMetge.this,
//                            "Seleccione una especialidad",
//                            "Agregar especialidad",
//                            JOptionPane.QUESTION_MESSAGE,
//                            null,
//                            especialidades.toArray(),
//                            especialidades.get(0)
//                    );
//                    if (especialidadSeleccionada != null && !especialidadSeleccionada.isEmpty()) {
//                        doctorController.agregarEspecialidad(doctorSeleccionado, especialidadSeleccionada);
//                        modeloListaEspecialidades.addElement(especialidadSeleccionada);
//                    }
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




