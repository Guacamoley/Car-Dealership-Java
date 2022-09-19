package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import DealershipSystem.*;

public class Interface {
	private JPanel panelMain;
	private JPanel guiPanel;
	private JTextArea outputArea;
	private JButton removeVehicleButton;
	private JButton exportToJSONButton;
	private JButton listVehiclesButton;
	private JComboBox<String> dealershipSelector;
	private JButton addVehicleButton;
	private JButton inputFileChooser;
	private JRadioButton enableRadioButton;
	private JRadioButton disableRadioButton;
	private ButtonGroup acquisitionGroup;

	// the inventory of cars and dealerships being worked on
	private Inventory i = new Inventory();
	
	// holder for whichever dealership is currently selected
	private String currentDealershipId = null;

	public Interface() {

		/**
		 * JComboBox will display "Select Dealer" at the start. After uploading json
		 * input file, it will update to list every dealership from json input file.
		 * Specified dealer can be selected to perform functions on with other various
		 * buttons available.
		 */
		dealershipSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear any results from the output window
				outputArea.setText(null);

				// set the current dealership id to whatever was selected in the drop-down
				currentDealershipId = (String) dealershipSelector.getSelectedItem();

				// TODO: remove this
				System.out.println("dealership set to " + currentDealershipId);
			}
		});

		/**
		 * TODO: Clicking this button will delete one vehicle from the inventory. It is
		 * not yet clear how the user will input the data.
		 */
		removeVehicleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		/**
		 * TODO: For selected dealership, when button is pressed, a vehicle can be added
		 * to that specified dealership. WIP on how the vehicle will be added (either
		 * via json file upload or manual entry.
		 */
		addVehicleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO: remove this, temporary testing
				// when you click "Add Vehicle", it should display all dealership id numbers in
				// console.
				// you'll need to import a json file first.
				java.util.List<String> stuff = i.getAllDealershipIds();
				for (String s : stuff) {
					System.out.println(s);
				}

			}
		});

		/**
		 * For selected dealership, when button is pressed, it will list out all
		 * vehicles the dealership has available. It shall list out vehicles onto the
		 * JTextArea
		 */
		listVehiclesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// set output to display the cars of this dealership using the delimiter "\n"
				// between each entry
				outputArea.setText(i.getCars(currentDealershipId, "\n"));
			}
		});

		/**
		 * TODO: When button is pressed, it will export all dealerships and all vehicles
		 * out into a new JSON file. User feedback will also be outputted into the
		 * JTextArea.
		 */
		exportToJSONButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		/**
		 * This button prompts the user to select a file. It imports cars in json format
		 * from that file into the inventory. Cars will automatically be placed into
		 * their respective dealerships. This also populates the dealership drop-down
		 * selector.
		 */
		inputFileChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.showOpenDialog(panelMain);
				File file = fc.getSelectedFile();
				i.importFile(file);
				updateDealershipComboBox(dealershipSelector);
				/*
				 * TODO: Try and catch files that may not be the right type, empty, nonexistent
				 * file etc.
				 */
			}
		});

		/**
		 * 
		 */
		enableRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		/**
		 * 
		 */
		disableRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	/*
	 * Used to update the dealership combo box after an input file has been
	 * uploaded. Will remove all items and then update combo box with dealership
	 * id's
	 */
	private void updateDealershipComboBox(JComboBox comboBox) {
		comboBox.removeAllItems();
		for (String s : i.getAllDealershipIds()) {
			comboBox.addItem(s);
		}
	}

	/**
	 * 
	 */
	public void createInterface() {
		JFrame frame = new JFrame("GUI");
		frame.setContentPane(new Interface().panelMain);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
	 * edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		panelMain = new JPanel();
		panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		guiPanel = new JPanel();
		guiPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 6, new Insets(0, 0, 0, 0), -1, -1));
		panelMain.add(guiPanel,
				new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						null, new Dimension(700, 400), null, 0, false));
		outputArea = new JTextArea();
		outputArea.setText("Output of vehicles will be displayed here");
		guiPanel.add(outputArea,
				new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 6,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null,
						new Dimension(400, 100), null, 0, false));
		exportToJSONButton = new JButton();
		exportToJSONButton.setText("Export to JSON");
		guiPanel.add(exportToJSONButton,
				new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		listVehiclesButton = new JButton();
		listVehiclesButton.setText("List Vehicles");
		guiPanel.add(listVehiclesButton,
				new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		dealershipSelector = new JComboBox();
		final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
		defaultComboBoxModel1.addElement("Select a dealership");
		dealershipSelector.setModel(defaultComboBoxModel1);
		guiPanel.add(dealershipSelector,
				new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		removeVehicleButton = new JButton();
		removeVehicleButton.setText("Remove Vehicle");
		guiPanel.add(removeVehicleButton,
				new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		inputFileChooser = new JButton();
		inputFileChooser.setText("Select Input File");
		guiPanel.add(inputFileChooser,
				new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		addVehicleButton = new JButton();
		addVehicleButton.setText("Add Vehicle");
		guiPanel.add(addVehicleButton,
				new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		enableRadioButton = new JRadioButton();
		enableRadioButton.setText("Enable");
		guiPanel.add(enableRadioButton,
				new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
						com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		disableRadioButton = new JRadioButton();
		disableRadioButton.setText("Disable");
		guiPanel.add(disableRadioButton,
				new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
						com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		ButtonGroup buttonGroup;
		buttonGroup = new ButtonGroup();
		buttonGroup.add(enableRadioButton);
		buttonGroup.add(disableRadioButton);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return panelMain;
	}

}
