import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
    private JTextField display;
    private JTextField syntaxDisplay;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;
    private StringBuilder currentExpression = new StringBuilder();
    
    // Define colors for the theme
    private final Color BACKGROUND_COLOR = new Color(28, 28, 30);
    private final Color DISPLAY_COLOR = new Color(44, 44, 46);
    private final Color TEXT_COLOR = new Color(0, 255, 255);  // Cyan
    private final Color NUMBER_BUTTON_COLOR = new Color(58, 58, 60);
    private final Color OPERATOR_BUTTON_COLOR = new Color(0, 150, 199);
    private final Color EQUALS_BUTTON_COLOR = new Color(0, 255, 255);
    private final Color CLEAR_BUTTON_COLOR = new Color(255, 69, 58);
    private final Color SYNTAX_BACKGROUND = new Color(35, 35, 37);

    public Calculator() {
        // Set up the display with styling
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Consolas", Font.BOLD, 32));
        display.setBackground(DISPLAY_COLOR);
        display.setForeground(TEXT_COLOR);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Set up the display (for syntax)
        syntaxDisplay = new JTextField("");
        syntaxDisplay.setEditable(false);
        syntaxDisplay.setFont(new Font("Consolas", Font.PLAIN, 16));
        syntaxDisplay.setBackground(SYNTAX_BACKGROUND);
        syntaxDisplay.setForeground(TEXT_COLOR);
        syntaxDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        syntaxDisplay.setHorizontalAlignment(JTextField.LEFT);
        
        // Create button panel (with spacing)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons (with styling)
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            buttonPanel.add(button);
            if (Character.isDigit(label.charAt(0)) || label.equals(".")) {
                styleNumberButton(button);
                button.addActionListener(new NumberListener());
            } else {
                if (label.equals("=")) {
                    styleEqualsButton(button);
                } else {
                    styleOperatorButton(button);
                }
                button.addActionListener(new OperatorListener());
            }
        }

        // Add clear button (with styling)
        JButton clearButton = createStyledButton("C");
        styleClearButton(clearButton);
        clearButton.addActionListener(e -> {
            display.setText("0");
            syntaxDisplay.setText("");
            start = true;
            result = 0;
            lastCommand = "=";
            currentExpression.setLength(0);
        });

        // Layout with padding
        setLayout(new BorderLayout(10, 10));
        
        // Top panel for displays
        JPanel displayPanel = new JPanel(new BorderLayout(5, 5));
        displayPanel.setBackground(BACKGROUND_COLOR);
        displayPanel.add(syntaxDisplay, BorderLayout.NORTH);
        displayPanel.add(display, BorderLayout.SOUTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(clearButton, BorderLayout.SOUTH);
        
        add(mainPanel);

        // Window settings
        setTitle("Futuristic Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Consolas", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void styleNumberButton(JButton button) {
        button.setBackground(NUMBER_BUTTON_COLOR);
        button.setBorder(BorderFactory.createLineBorder(NUMBER_BUTTON_COLOR, 2, true));
    }

    private void styleOperatorButton(JButton button) {
        button.setBackground(OPERATOR_BUTTON_COLOR);
        button.setBorder(BorderFactory.createLineBorder(OPERATOR_BUTTON_COLOR, 2, true));
    }

    private void styleEqualsButton(JButton button) {
        button.setBackground(EQUALS_BUTTON_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(EQUALS_BUTTON_COLOR, 2, true));
    }

    private void styleClearButton(JButton button) {
        button.setBackground(CLEAR_BUTTON_COLOR);
        button.setPreferredSize(new Dimension(0, 60));
        button.setBorder(BorderFactory.createLineBorder(CLEAR_BUTTON_COLOR, 2, true));
    }

    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (start) {
                display.setText(digit);
                if (lastCommand.equals("=")) {
                    currentExpression.setLength(0);
                }
                currentExpression.append(digit);
                start = false;
            } else {
                display.setText(display.getText() + digit);
                currentExpression.append(digit);
            }
            syntaxDisplay.setText(currentExpression.toString());
        }
    }

    private class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    currentExpression.append(command);
                    start = false;
                } else if (!command.equals("=")) {
                    currentExpression.append(display.getText()).append(" ").append(command).append(" ");
                    lastCommand = command;
                }
            } else {
                String currentDisplay = display.getText();
                
                if (command.equals("=")) {
                    calculate(Double.parseDouble(currentDisplay));
                    currentExpression.append(" = ").append(result);
                    start = true;
                } else {
                    calculate(Double.parseDouble(currentDisplay));
                    currentExpression.append(" ").append(command).append(" ");
                    lastCommand = command;
                    start = true;
                }
                display.setText(String.valueOf(result));
            }
            syntaxDisplay.setText(currentExpression.toString());
        }
    }

    public void calculate(double x) {
        switch (lastCommand) {
            case "+": result += x; break;
            case "-": result -= x; break;
            case "*": result *= x; break;
            case "/": result /= x; break;
            case "=": result = x; break;
        }
        display.setText("" + result);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
