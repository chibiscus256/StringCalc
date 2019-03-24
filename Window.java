import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }

    private JFrame window;
    private JButton calcButton = new JButton("Calculate");
    private JButton clearButton = new JButton("Clear");
    private JLabel jLabelInput = new JLabel("Input:");
    private JLabel jLabelReturn = new JLabel("Computed expression:");
    private JTextArea jTextDescription = new JTextArea("допустимый ввод: цифры от 0 до 9 \n знаки операций: -, +, /, *, (, )", 3, 25);
    private JTextArea jTextInput = new JTextArea("", 1, 25);
    private JTextArea jTextReturn = new JTextArea(4, 25);



    private Window() {
        window = new JFrame("Calculator SE version 1.0");
        window.setSize(320, 240);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);


        someDesign();

    }

    private void someDesign() {      // располагаем элементы программы на 2х разных панелях, и работаем с кноппками
        JPanel panelButton = new JPanel();
        JPanel panelEnter = new JPanel();
        panelButton.add(calcButton);
        panelButton.add(clearButton);

        calcButton.addActionListener(new calcButtonActionListener());
        clearButton.addActionListener(new clearButtonActionListener());


        System.out.print(window.getRootPane());
        jTextInput.setLineWrap(true);

        panelEnter.add(jTextDescription);
        panelEnter.add(jLabelInput);
        panelEnter.add(jTextInput);
        panelEnter.add(jLabelReturn);
        panelEnter.add(jTextReturn);

        window.add(panelEnter, BorderLayout.CENTER);
        window.add(panelButton, BorderLayout.SOUTH);

        window.getRootPane().setDefaultButton(calcButton);

        jTextReturn.setEditable(false);
        jTextDescription.setEditable(false);
    }

    public class calcButtonActionListener implements ActionListener{   //чтобы считать по нажатию на Calculate

        @Override
        public void actionPerformed(ActionEvent event){
            jTextReturn.setText("");
            jTextReturn.append(FSCalc.performComputation(jTextInput.getText()));
        }

    }

    public class clearButtonActionListener implements ActionListener{   //чтобы Clear стирал

        @Override
        public void actionPerformed(ActionEvent event){
            jTextInput.setText("");
            jTextReturn.setText("");
        }

    }

}