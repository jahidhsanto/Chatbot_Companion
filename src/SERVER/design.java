package SERVER;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class design extends JFrame {
    Container c;
    private Font titleFont, font;

    private JLabel titleLabel, dspLabel;
    private JTextArea dspArea, memberArea;
    private JButton onBtn, offBtn;
    private JPanel dspPanel, buttonPanel, memberPanel;

    design() {
        initComponents();   // Calling initComponents method
    }

    public void initComponents() {
        /*====================This section is for Frame====================*/
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("SERVER");

        c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(240, 240, 240));
        /*====================This section is for Frame====================*/

        /*====================This section is for Font creation====================*/
        titleFont = new Font("Acme", Font.BOLD, 50);
        font = new Font("Roboto", Font.PLAIN, 16);
        /*====================This section is for Font creation====================*/

        /*====================This section is for HEADING====================*/
        titleLabel = new JLabel("SERVER PANEL");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 255, 255));
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(0, 0, 780, 100);
        c.add(titleLabel);
        /*====================This section is for HEADING====================*/

        /*====================This section is for MemberPanel====================*/
        memberPanel = new JPanel(new GridLayout(1, 1));
        memberPanel.setBounds(457, 120, 270, 150);
        memberPanel.setBorder(BorderFactory.createTitledBorder(null, "Owner_Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 18)));
        c.add(memberPanel);

        memberArea = new JTextArea();
        memberArea.setFont(new Font("Roboto", Font.PLAIN, 14));
        memberArea.setBorder(BorderFactory.createTitledBorder(null, "Md. Jahid Hassan", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 18)));
        memberArea.setText(" ID: 201002463\n Section: 201DB\n Batch: 201\n Dept.: CSE\n Green University of Bangladesh");
        memberArea.setEditable(true);
        memberPanel.add(memberArea);
        /*====================This section is for MemberPanel====================*/

        /*====================This section is for dspPanel====================*/
        dspPanel = new JPanel(new GridLayout(1, 1));
        dspPanel.setBounds(30, 120, 389, 270);
        dspPanel.setBorder(BorderFactory.createTitledBorder(null, "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 18)));
        c.add(dspPanel);

        dspArea = new JTextArea();
        dspArea.setFont(font);
        dspPanel.add(dspArea);
        /*====================This section is for dspPanel====================*/

        /*====================This section is for buttonPanel====================*/
        buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBounds(457, 300, 270, 100);
        c.add(buttonPanel);

        onBtn = new JButton("ON");
        onBtn.setFont(font);
        buttonPanel.add(onBtn);

        offBtn = new JButton("OFF");
        offBtn.setFont(font);
        buttonPanel.add(offBtn);
        /*====================This section is for buttonPanel====================*/
    }


    public static void main(String[] args) {
        design frame = new design();
        frame.setVisible(true);
    }
}
