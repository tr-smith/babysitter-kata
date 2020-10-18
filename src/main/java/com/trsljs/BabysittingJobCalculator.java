package com.trsljs;

import com.trsljs.service.CalculatorService;
import com.trsljs.util.TimeUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Vector;

/**
 * Hello world!
 *
 */
public class BabysittingJobCalculator extends JFrame implements ActionListener {
    private JComboBox<String> startTimeInput;
    private JComboBox<String> bedTimeInput;
    private JComboBox<String> endTimeInput;
    private JLabel resultMessage;
    private JButton calcButton;
    private JButton resetButton;
    private boolean enableStart = true;
    private boolean enableEnd = true;
    private final CalculatorService calculatorService = new CalculatorService();


    public BabysittingJobCalculator() {
        Dimension locationDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = locationDimension.width / 2 - this.getSize().width / 2;
        int height = locationDimension.height / 2 - this.getSize().height / 2;

        initComponents();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension dimension = new Dimension(300, 250);

        this.setTitle("Babysitting Calculator");
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setLocation(width, height);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args )
    {
        new BabysittingJobCalculator().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            if (e.getSource() == startTimeInput) {
                validateStartSelection();
            } else if (e.getSource() == endTimeInput) {
                validateEndSelection();
            } else {
                enableCalculate();
            }

        } else if (e.getSource() instanceof JButton) {
            if (e.getSource() == calcButton) {
                performCalculations();
            } else if (e.getSource() == resetButton) {
                resetCalculator();
            }
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 0, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 50));

        inputPanel.add(createInputLabel("Start Time:", startTimeInput));
        startTimeInput = createCombobox(TimeUtilities.getValidStartTimes());
        startTimeInput.addActionListener(this);
        inputPanel.add(startTimeInput);

        inputPanel.add(createInputLabel("Bed Time:", bedTimeInput));
        bedTimeInput = createCombobox(TimeUtilities.getValidBedtimes());
        bedTimeInput.addActionListener(this);
        inputPanel.add(bedTimeInput);

        inputPanel.add(createInputLabel("End Time:", endTimeInput));
        endTimeInput = createCombobox(TimeUtilities.getValidEndTimes());
        endTimeInput.addActionListener(this);
        inputPanel.add(endTimeInput);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(1, 2));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        JLabel resultMessageLabel = new JLabel("Total for job:");
        resultMessageLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        resultMessageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultPanel.add(resultMessageLabel);

        resultMessage = new JLabel();

        resultPanel.add(resultMessage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        calcButton = createButton("Calculate");
        calcButton.setEnabled(false);
        calcButton.addActionListener(this);
        buttonPanel.add(calcButton);

        resetButton = createButton("Reset");
        buttonPanel.add(resetButton);
        resetButton.addActionListener(this);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
    }

    private void resetCalculator() {
        resultMessage.setText("");
        startTimeInput.setSelectedIndex(0);
        bedTimeInput.setSelectedIndex(0);
        endTimeInput.setSelectedIndex(0);
        enableStart = true;
        enableEnd = true;
        calcButton.setEnabled(false);
    }

    private JLabel createInputLabel(String labelText, JComboBox<String> input) {
        JLabel label = new JLabel(labelText);
        Dimension dimension = new Dimension(100, 25);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setSize(dimension);
        label.setMinimumSize(dimension);
        label.setMaximumSize(dimension);
        label.setPreferredSize(dimension);
        label.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        label.setLabelFor(input);
        return label;
    }

    private JComboBox<String> createCombobox(Vector<String> model) {
        JComboBox<String > comboBox = new JComboBox<>(model);
        Dimension dimension = new Dimension(100, 25);
        comboBox.setSize(dimension);
        comboBox.setMaximumSize(dimension);
        comboBox.setMinimumSize(dimension);
        comboBox.setPreferredSize(dimension);
        return comboBox;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension dimension = new Dimension(125, 30);
        button.setSize(dimension);
        button.setMaximumSize(dimension);
        button.setMinimumSize(dimension);
        button.setPreferredSize(dimension);

        return button;
    }

    private void performCalculations() {
        LocalTime startTime = LocalTime.parse((String) Objects.requireNonNull(startTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
        LocalTime bedTime = LocalTime.parse((String) Objects.requireNonNull(bedTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse((String) Objects.requireNonNull(endTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);

        resultMessage.setText(calculatorService.calculateJobTotal(startTime, bedTime, endTime));
    }


    private void enableCalculate() {
        if (startTimeInput.getSelectedIndex() > 0 && bedTimeInput.getSelectedIndex() > 0 &&
                endTimeInput.getSelectedIndex() > 0) {
            calcButton.setEnabled(enableStart && enableEnd);
        } else {
            calcButton.setEnabled(false);
        }
    }

    private void validateStartSelection() {
        if (startTimeInput.getSelectedIndex() > 0 && endTimeInput.getSelectedIndex() > 0) {
            LocalTime startTime = LocalTime.parse((String) Objects.requireNonNull(startTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse((String) Objects.requireNonNull(endTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
            if (calculatorService.isStartTimeAfterEndTime(startTime, endTime)) {
                JOptionPane.showMessageDialog(this, "Start Time must be before End Time.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                enableStart = false;
            } else {
                enableStart = true;
            }
        }
        enableCalculate();
    }

    private void validateEndSelection() {
        if (startTimeInput.getSelectedIndex() > 0 && endTimeInput.getSelectedIndex() > 0) {
            LocalTime startTime = LocalTime.parse((String) Objects.requireNonNull(startTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
            LocalTime endTime = LocalTime.parse((String) Objects.requireNonNull(endTimeInput.getSelectedItem()), TimeUtilities.TIME_FORMATTER);
            if (calculatorService.isStartTimeAfterEndTime(startTime, endTime)) {
                JOptionPane.showMessageDialog(this, "End Time must be after Start Time.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                enableEnd = false;
            } else {
                enableEnd = true;
            }
        }
        enableCalculate();
    }
}
