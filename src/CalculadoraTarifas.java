import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**
 * Author:      Francisco Menendez Moya
 *
 * Description: This is a simple class that calculates the amount of money implicated in each transaction
 *              adjusted to the PayPal fees as of January 2016. Currency: Euros
 */
public class CalculadoraTarifas extends JFrame {

    private JLabel label = new JLabel("Introduzca cantidad a comprobar (€):");
    private JTextField textField = new JTextField(20);
    private JButton button1 = new JButton("Calcular");

    //Class constructor
    public CalculadoraTarifas() {
        super("Calculadora de Tarifas de PayPal");

        // sets layout manager
        setLayout(new GridBagLayout());

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.gridx = 0;
        constraint.gridy = 0;

        add(label, constraint);

        constraint.gridx = 1;
        add(textField, constraint);

        constraint.gridx = 0;
        constraint.gridwidth = 2;
        constraint.gridy = 1;

        add(button1, constraint);

        //decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);

        // adds window event listener
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                int reply = JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "¿Esta seguro que quiere salir?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        button1.addActionListener(e -> {
            double number1 = parsearReal(textField.getText(),-2);
            if(number1 > 0){
                double porcentaje = 0.0;

                if(number1 >= 0 && number1 <= 2500){
                    porcentaje = 0.034;
                } else if(number1 > 2500 && number1 <= 10000){
                    porcentaje = 0.029;
                }else if(number1 > 10000 && number1 <= 50000){
                    porcentaje = 0.027;
                }else if(number1 > 50000 && number1 <= 100000){
                    porcentaje = 0.024;
                }else if(number1 > 100000){
                    porcentaje = 0.019;
                }

                double tarifa = 1.000 - porcentaje;

                double enviar = (number1/tarifa) + 0.35;
                double recibido = (number1*tarifa) - 0.35;

                JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "Tarifa aplicada: " + df.format(porcentaje*100) + "% + 0.35€ \n" +
                                "Si envia " + number1 + " euros le llegaran " + df.format(recibido) + "€ \n" +
                                "Si quiere que le lleguen " + number1 + "€ necesitara que le envien " + df.format(enviar) +
                                "€","Resultado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            }else{
                JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "ERROR: Introduzca un numero valido","ERROR",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();

        // centers on screen
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraTarifas::new);
    }

    /**
     * @param s     : number in String format
     * @param def   : number returned as error if conversion fails (no number found in 's')
     * @return      : 's' converted from String to int
     */
    private static double parsearReal(String s, int def) {
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

}
