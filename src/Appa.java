import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Appa extends JFrame {
    private HashMap<String, Estudiante> estudiantes;
    private int totalCalificaciones;

    private JTextField nombreField;
    private JTextField calificacionField;
    private JTextArea resultadosTextArea;

    public Appa() {
        estudiantes = new HashMap<>();
        totalCalificaciones = 0;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        nombreField = new JTextField();
        calificacionField = new JTextField();

        inputPanel.add(new JLabel("Nombre del estudiante:"));
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Calificación del estudiante:"));
        inputPanel.add(calificacionField);

        JButton ingresarButton = new JButton("Ingresar");
        ingresarButton.addActionListener(e -> ingresarEstudiante());

        add(inputPanel, BorderLayout.NORTH);
        add(ingresarButton, BorderLayout.CENTER);

        resultadosTextArea = new JTextArea();
        add(new JScrollPane(resultadosTextArea), BorderLayout.SOUTH);

        setTitle("Calificaciones de Estudiantes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    private void ingresarEstudiante() {
        String nombre = nombreField.getText();
        int calificacion;

        try {
            calificacion = Integer.parseInt(calificacionField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una calificación válida (número entero).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        totalCalificaciones += calificacion;
        Estudiante estudiante = new Estudiante(nombre, calificacion);
        estudiantes.put(nombre, estudiante);

        mostrarResultados();
    }

    private void mostrarResultados() {
        resultadosTextArea.setText("El promedio de calificaciones es: " + (double) totalCalificaciones / estudiantes.size() + "\n");
        resultadosTextArea.append("Estudiantes con calificaciones superiores al promedio:\n");

        double promedio = (double) totalCalificaciones / estudiantes.size();
        for (Map.Entry<String, Estudiante> entry : estudiantes.entrySet()) {
            if (entry.getValue().getCalificacion() > promedio) {
                resultadosTextArea.append(entry.getKey() + ": " + entry.getValue().getCalificacion() + "\n");
            }
        }

        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try {
            FileWriter writer = new FileWriter("calificaciones.txt");
            for (Map.Entry<String, Estudiante> entry : estudiantes.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue().getCalificacion() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Appa::new);
    }
}
