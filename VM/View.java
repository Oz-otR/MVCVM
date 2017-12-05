package groupAssignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class View extends JFrame {

	static String[] controls = { "Lock", "Card", "Coin", "Refund" };

	// Null ptr, put image in source folder
	ImageIcon can = new ImageIcon(getClass().getResource("/resources/GenCan3.png"));
	ButtonGroup group = new ButtonGroup();
	////////////////////////////////////////////////////////////////////////

	private JButton abutton = new JButton(can);
	public static JButton[] SelectionButtons = new JButton[12];

	private JButton chuteButton = new JButton("Delivery Chute");
	/*
	 * { { setSize(50, 50); setMaximumSize(getSize()); } };
	 */

	private JPanel ExpensesPanel;
	private JPanel RadioPanel;
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel bottom = new JPanel();
	private JFrame mainFrame;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Color color = Color.WHITE;

	// Construct
	/**
	 * constructor for the View creates and layout the interface
	 */
	public View(String s) {
		super(s);

		this.setResizable(false);
		this.setPreferredSize(new Dimension((int) (450.78), 728));
		System.out.println((screenSize.width * .33));
		System.out.println(screenSize.height - 40);

		mainPanel.setLayout(new GridBagLayout());
		topPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));

		Dimension minSize = new Dimension(5, 5);
		Dimension prefSize = new Dimension(5, 5);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);

		chuteButton.setPreferredSize(new Dimension(300, 60));
		// Change to light Grey When Opened.
		chuteButton.setBackground(Color.DARK_GRAY);
		chuteButton.setForeground(Color.WHITE);

		JPanel ButtonPanel = new JPanel(new GridBagLayout());
		ButtonPanel.setBackground(Color.RED);
		add(ButtonPanel);

		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1.0;
		constraint.gridwidth = 10;
		constraint.gridx = 0;
		constraint.gridy = 0;
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////

		RadioPanel = new JPanel();
		// (rows, columns)
		// RadioPanel.setLayout(new GridLayout(1, 6));
		// makes visble
		// add(RadioPanel);

		for (int i = 0; i < 8; i++) {
			SelectionButtons[i] = new JButton(can);
			SelectionButtons[i].setToolTipText("Buy this Pop");
			SelectionButtons[i].setActionCommand(String.valueOf(i));
			group.add(SelectionButtons[i]);
			if (i < 4) {
				constraint.weightx = 0.5;
				constraint.gridwidth = 2;
				constraint.gridx = 0;
				constraint.gridy = i;
			} else {
				constraint.weightx = 0.5;
				constraint.gridwidth = 2;
				constraint.gridx = 3;
				constraint.gridy = (i - 4);
			}
			SelectionButtons[i].setMaximumSize(new Dimension(50, 50));
			SelectionButtons[i].setPreferredSize(new Dimension(50, 50));
			ButtonPanel.add(SelectionButtons[i], constraint);
		}

		for (int i = 8; i < 12; i++) {
			SelectionButtons[i] = new JButton(controls[i - 8]);

			constraint.weightx = 0.1;
			constraint.gridwidth = 1;
			constraint.gridx = 2;
			constraint.gridy = (i - 8);

			SelectionButtons[i].setMaximumSize(new Dimension(50, 50));
			SelectionButtons[i].setPreferredSize(new Dimension(50, 50));
			// potentially set this up for Pricing
			SelectionButtons[i].setToolTipText("Buy this Pop");
			ButtonPanel.add(SelectionButtons[i], constraint);
		}

		////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////

		SelectionButtons[10].setEnabled(false);

		constraint.anchor = GridBagConstraints.NORTHWEST;
		mainPanel.add(Box.createVerticalStrut(350));

		// mainPanel.add(topPanel);
		mainPanel.setBackground(Color.MAGENTA);
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1;
		constraint.gridwidth = 5;
		constraint.gridheight = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		mainPanel.add(ButtonPanel, constraint);

		constraint.fill = GridBagConstraints.RELATIVE;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.gridwidth = 3;
		constraint.gridheight = 1;
		constraint.gridx = 1;
		constraint.gridy = 1;
		mainPanel.add(chuteButton, constraint);

		add(mainPanel);

	}
}

class MyWindowListener extends WindowAdapter {

    public static final int DELAY_LENGTH = 500;

    public void windowClosing(WindowEvent e) {
        JFrame aFrame = (JFrame) e.getWindow();
        aFrame.getContentPane().setBackground(Color.RED);

        
        aFrame.setTitle("Closing Window");
        try {
            Thread.sleep(DELAY_LENGTH);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted Pause");
        }
        aFrame.setVisible(false);

        System.exit(0);

    }

}

///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
class MyControlDialog extends JFrame {

	JPanel Config = new JPanel();
	JLabel PanelName = new JLabel("Simulated Control Panel");

	// static private M theModel;
	static String[] ControlButtons = { "5", "Invalid",
									   "10", "Valid",
									   "25", "Poor",
									   "100", "Rich",
									   "200", "Use" };

	JButton[] Buttons = new JButton[ControlButtons.length];
	JPanel UI = new JPanel(new GridLayout(6, 2));

	public MyControlDialog() {

		// this.theModel = model;
		GridBagConstraints constraint = new GridBagConstraints();

		BoxLayout boxLayout = new BoxLayout(Config, BoxLayout.Y_AXIS);
		Config.setLayout(boxLayout);

		JLabel Card = new JLabel("          Card");
		Card.setBackground(Color.WHITE);
		JLabel Cash = new JLabel("          Cash");
		Cash.setBackground(Color.WHITE);

		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 0.5;
		constraint.gridwidth = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		UI.add(Cash, constraint);
		constraint.gridx = 1;
		UI.add(Card, constraint);

		Config.add(UI);

		for (int i = 0; i < ControlButtons.length; i++) {
			Buttons[i] = new JButton(ControlButtons[i]);

			constraint.fill = GridBagConstraints.HORIZONTAL;
			constraint.weightx = 1;
			constraint.gridwidth = 1;
			constraint.gridx = 0;
			constraint.gridy = i + 1;

			if (i >= 5) {
				constraint.gridy = (i - 4);
				constraint.gridx = 1;
			}

			UI.add(Buttons[i], constraint);
			UI.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}

		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(Config);

		this.add(PanelName, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.SOUTH);
	}
}

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
class MyConfigDialog extends JFrame {

	JPanel Config = new JPanel();
	JLabel PanelName = new JLabel("Configuration Panel");

	// static private M theModel;
	static String[] ConfigButtons = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
									  "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
									  "a", "s", "d", "f", "g", "h", "j", "k", "l", 
									  "z", "x", "c", "v", "b", "n", "m", "^","+" };

	JButton[] Buttons = new JButton[ConfigButtons.length];
	JPanel UI = new JPanel(new GridBagLayout());

	public MyConfigDialog() {

		// this.theModel = model;
		GridBagConstraints constraint = new GridBagConstraints();

		BoxLayout boxLayout = new BoxLayout(Config, BoxLayout.Y_AXIS);
		Config.setLayout(boxLayout);

		JTextField DisplayField = new JTextField();
		DisplayField.setEditable(false);
		DisplayField.setToolTipText("Internal Display For Config Panel");
		DisplayField.setBackground(Color.WHITE);

		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1.0;
		constraint.gridwidth = 10;
		constraint.gridx = 0;
		constraint.gridy = 0;
		UI.add(DisplayField, constraint);
		Config.add(UI);

		for (int i = 0; i < ConfigButtons.length; i++) {

			Buttons[i] = new JButton(ConfigButtons[i]);

			constraint.fill = GridBagConstraints.HORIZONTAL;
			constraint.weightx = 0.5;
			constraint.gridwidth = 1;
			constraint.gridx = (i % 10);
			constraint.gridy = 2 + (i / 10);

			if (i >= 29 && i < 36) {
				constraint.gridy = 5;
				constraint.gridx = (i - 7) % 10;
			} else if (i == 36) {
				constraint.gridwidth = 2;
				constraint.gridx = 0;
				constraint.gridy = 5;
			} else if (i == 37) {
				constraint.fill = GridBagConstraints.VERTICAL;
				constraint.gridwidth = 1;
				constraint.gridheight = 2;
				constraint.gridx = 9;
				constraint.gridy = 4;
			}
			UI.add(Buttons[i], constraint);
			UI.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}

		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(Config);

		this.add(PanelName, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.SOUTH);
	}
}
