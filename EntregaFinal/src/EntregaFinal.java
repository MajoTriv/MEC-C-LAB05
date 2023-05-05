import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EntregaFinal extends JFrame {

    private JComboBox<String> departamentoComboBox;
    private JComboBox<String> municipioComboBox;
    private JComboBox<Integer> añoComboBox;
    private JComboBox<String> generoComboBox;
    private JButton filtrarButton;
    private JLabel resultadosLabel;
    private List<Dato> datos;

    public EntregaFinal() {
        super("Filtro de datos");

        datos = leerArchivo("C:\\Users\\Estudiante\\Desktop\\Archivoguia.csv");

        departamentoComboBox = new JComboBox<>(obtenerDepartamentos());
        departamentoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarMunicipios();
            }
        });

        municipioComboBox = new JComboBox<>(obtenerMunicipios(datos, departamentoComboBox.getSelectedItem().toString()));
        añoComboBox = new JComboBox<>(obtenerAños());
        generoComboBox = new JComboBox<>(new String[]{"Femenino", "Masculino"});

        filtrarButton = new JButton("Filtrar");
        filtrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int total = filtrarDatos();
                resultadosLabel.setText(String.format("Resultados encontrados: %d", total));
            }
        });

        resultadosLabel = new JLabel("Resultados encontrados: 0");

        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Departamento: "));
        panelFiltros.add(departamentoComboBox);
        panelFiltros.add(new JLabel("Municipio: "));
        panelFiltros.add(municipioComboBox);
        panelFiltros.add(new JLabel("Año: "));
        panelFiltros.add(añoComboBox);
        panelFiltros.add(new JLabel("Género: "));
        panelFiltros.add(generoComboBox);
        panelFiltros.add(filtrarButton);

        JPanel panelResultados = new JPanel();
        panelResultados.add(resultadosLabel);

        getContentPane().add(panelFiltros, BorderLayout.NORTH);
        getContentPane().add(panelResultados, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private List<Dato> leerArchivo(String nombreArchivo) {
        List<Dato> listaDatos = new ArrayList<>();
        File archivo = new File(nombreArchivo);

        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            int filaActual = 0;
    while ((linea = br.readLine()) != null) {
filaActual++;
if (filaActual == 1) {
continue; 
}
String[] datos = linea.split(",");
Dato dato = new Dato(datos[0], datos[1], datos[2], Integer.parseInt(datos[3]), datos[4], Integer.parseInt(datos[5]));
listaDatos.add(dato);
}

            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaDatos;
    }

    private String[] obtenerDepartamentos() {
        Set<String> departamentos = new HashSet<>();
        for (Dato dato : datos) {
            departamentos.add(dato.getDepartamento());
        }
        String[] arrDepartamentos = departamentos.toArray(new String[departamentos.size()]);
Arrays.sort(arrDepartamentos);
return arrDepartamentos;
}
    private String[] obtenerMunicipios(List<Dato> datos, String departamento) {
    Set<String> municipios = new HashSet<>();
    for (Dato dato : datos) {
        if (dato.getDepartamento().equals(departamento)) {
            municipios.add(dato.getMunicipio());
        }
    }
    String[] arrMunicipios = municipios.toArray(new String[municipios.size()]);
    Arrays.sort(arrMunicipios);
    return arrMunicipios;
}

private Integer[] obtenerAños() {
    Set<Integer> años = new HashSet<>();
    for (Dato dato : datos) {
        años.add(dato.getAño());
    }
    Integer[] arrAños = años.toArray(new Integer[años.size()]);
    Arrays.sort(arrAños);
    return arrAños;
}

private void actualizarMunicipios() {
    String departamentoSeleccionado = departamentoComboBox.getSelectedItem().toString();
    String[] arrMunicipios = obtenerMunicipios(datos, departamentoSeleccionado);
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(arrMunicipios);
    municipioComboBox.setModel(model);
}

private int filtrarDatos() {
    String departamentoSeleccionado = departamentoComboBox.getSelectedItem().toString();
    String municipioSeleccionado = municipioComboBox.getSelectedItem().toString();
    int añoSeleccionado = (int) añoComboBox.getSelectedItem();
    String generoSeleccionado = generoComboBox.getSelectedItem().toString();

    int total = 0;
    for (Dato dato : datos) {
        if (dato.getDepartamento().equals(departamentoSeleccionado)
                && dato.getMunicipio().equals(municipioSeleccionado)
                && dato.getAño() == añoSeleccionado
                && dato.getGenero().equals(generoSeleccionado)) {
            total++;
        }
    }
    return total;
}

public static void main(String[] args) {
    new EntregaFinal();
}
}

class Dato {
private String departamento;
private String municipio;
private String codigoDepartamento;
private int año;
private String genero;
private int cantidad;
public Dato(String departamento, String municipio, String codigoDepartamento, int año, String genero, int cantidad) {
    this.departamento = departamento;
    this.municipio = municipio;
    this.codigoDepartamento = codigoDepartamento;
    this.año = año;
    this.genero = genero;
    this.cantidad = cantidad;
}

public String getDepartamento() {
    return departamento;
}

public String getMunicipio() {
    return municipio;
}

public String getCodigoDepartamento() {
    return codigoDepartamento;
}

public int getAño() {
    return año;
}

public String getGenero() {
    return genero;
}

public int getCantidad() {
    return cantidad;
}
}
