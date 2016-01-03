import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Author:      Francisco Menéndez Moya
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
        Locale.setDefault(Locale.UK);

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

        //Decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);

        //Options when pressing 'X' on the window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                int reply = JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "¿Está seguro que quiere salir?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        //Options when pressing the button 'button1'
        button1.addActionListener(e -> {
            double number = parsearReal(textField.getText(),-2);
            if(number > 0){
                double percentage = 0.0;

                if(number >= 0 && number <= 2500){
                    percentage = 0.034;
                } else if(number > 2500 && number <= 10000){
                    percentage = 0.029;
                }else if(number > 10000 && number <= 50000){
                    percentage = 0.027;
                }else if(number > 50000 && number <= 100000){
                    percentage = 0.024;
                }else if(number > 100000){
                    percentage = 0.019;
                }

                double taxes = 1.000 - percentage;

                double send = (number/taxes) + 0.35;
                double receive = (number*taxes) - 0.35;

                JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "Tarifa aplicada: " + df.format(percentage*100) + "% + 0.35€ \n" +
                                "Si envía " + number + " euros le llegarán " + df.format(receive) + "€ \n" +
                                "Si quiere que le lleguen " + number + "€ necesitará que le envíen " + df.format(send) +
                                "€","Resultado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            }else{
                JOptionPane.showConfirmDialog(CalculadoraTarifas.this,
                        "ERROR: Introduzca un número válido","ERROR",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();

        //Center window on screen
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
