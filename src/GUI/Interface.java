package GUI;

import DealershipSystem.Car;
import DealershipSystem.Inventory;
import DealershipSystem.Status;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class creates the interface of the car dealership project. It calls on
 * the logic from AddVehicleInput, Car, and Inventory to complete user actions
 * in the GUI. Actions include: importing & exporting JSON file, disable &
 * enable acquisition, switching dealerships, adding vehicles, and listing
 * vehicles.
 *
 * @author Michael Ha
 */
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

	// the inventory of cars and dealerships being worked on
	private Inventory i = new Inventory();

	// holder for whichever dealership is currently selected
	private String currentDealershipId = null;

	// Create new adder object for adding new vehicles
	private AddVehicleInput add;

	private RemoveVehicleInput remove;

	// the location for the session save file
	final static String SAVE_PATH = "resources\\session.json";

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

				// update GUI to reflect the selected dealership
				selectDealership();
			}
		});

		removeVehicleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove = new RemoveVehicleInput();

				Car newCar = null;
				newCar = remove.addNewVehicle();

				i.removeIncomingVehicle(newCar);
			}
		});

		/**
		 * Will prompt the user to enter information about the car being added into the
		 * system. Creates a newCar object as null. Calls on the addNewVehicle method in
		 * AddVehicleInput class for user input. After user input, will check if the add
		 * was successful. It'll finally update the dealership selector in case a new
		 * dealership was added, and provide user feedback on the add.
		 */
		addVehicleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// create a new input prompt window
				add = new AddVehicleInput();
				String responseMessage = "";

				// Adds a new vehicle
				Car newCar = null;
				newCar = add.addVehID();

				if (i.isExistingDealer(newCar.getDealership_id())
						&& !(i.getDealerAcquisition(newCar.getDealership_id()))) {
					responseMessage = "Not added successfully.\nDealership cannot acquire vehicles.";
					newCar = null;

				} else {
					add.addNewVehicle();
					i.addIncomingVehicle(newCar);
					responseMessage = "Vehicle added successfully";
					responseMessage += ":\n" + newCar;

				}
				// update the dealership selector in case a new dealership was created
				updateDealershipComboBox(dealershipSelector);
				outputArea.setText(responseMessage);

				// cleanup resource
				add = null;
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
		 * When button is pressed, it will export all vehicles for selected dealership
		 * into new .json file A save screen will show up prompting user to select the
		 * location and give the file its name. It will then save the file to that
		 * specified location and provide user feedback in the outputTextArea. If user
		 * cancelled the export, there will also be feedback for that as well in the
		 * text area.
		 */
		exportToJSONButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO testing save feature
				saveSession();

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".json", "json");
				fc.addChoosableFileFilter(fileFilter);
				int choice = fc.showSaveDialog(panelMain);

				if (choice == JFileChooser.APPROVE_OPTION) {
					String fileName = fc.getSelectedFile().getAbsolutePath();
					if (!fileName.endsWith(".json"))
						fileName += ".json";
					i.exportFile(currentDealershipId, fileName);
					outputArea.setText("Successfully exported to " + fileName);
				} else
					outputArea.setText("The export was cancelled by the user");
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
				fc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".json", "json");
				fc.addChoosableFileFilter(fileFilter);
				fc.setCurrentDirectory(new File("."));
				fc.showOpenDialog(panelMain);
				File file = fc.getSelectedFile();
				i.importFile(file);
				updateDealershipComboBox(dealershipSelector);
				outputArea.setText("File " + file + " has been chosen");
			}
		});

		/**
		 * Dealerships will have acquisition enabled by default. Otherwise, if
		 * dealership is currently disabled, and user enables--the dealership can then
		 * add new vehicles into their inventory.
		 */
		enableRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i.enableDealerAcquisition(currentDealershipId);

			}
		});

		disableRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i.disableDealerAcquisition(currentDealershipId);

			}
		});

	}

	/**
	 * Removes all items from the dealership selector ComboBox, and then loops
	 * through Inventory for all dealershipIDs and populates the comboBox. Then
	 * refreshes the GUI to reflect the currently selected dealership.
	 */
	private void updateDealershipComboBox(JComboBox<String> comboBox) {
		comboBox.removeAllItems();
		for (String s : i.getAllDealershipIds()) {
			comboBox.addItem(s);
		}
		selectDealership();
	}

	/**
	 * stores the selected dealership from the drop-down box and updates the radio
	 * buttons to reflect the current status if any.
	 */
	private void selectDealership() {
		// set the current dealership id to whatever was selected in the drop-down
		currentDealershipId = (String) dealershipSelector.getSelectedItem();

		// setup the radio buttons to reflect the current status
		if (currentDealershipId != null) {
			boolean canAcquire = i.getDealerAcquisition(currentDealershipId);
			// this sets the button state without triggering any underlying logic
			enableRadioButton.setSelected(canAcquire);
			disableRadioButton.setSelected(!canAcquire);
		}
	}

	/**
	 * Single method that creates the frame that the GUI will be placed in and sets
	 * it visible afterwards.
	 */
	public void createInterface() {
		JFrame frame = new JFrame("GUI");
		Interface myInterface = new Interface();
		frame.setContentPane(myInterface.panelMain);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		// attempt to load previous saved session
		myInterface.loadSession();

		frame.setVisible(true);
	}

	/**
	 * this method saves the current session to a file path constant defined by the
	 * class. the session consists of all dealers, their cars, and all attributes
	 * thereof. a success/failure message is displayed on the GUI accordingly.
	 */
	private void saveSession() {
		// attempt to save and store the status
		Status saveStatus = i.exportSession(SAVE_PATH);

		// display GUI message depending on status
		switch (saveStatus) {
		case SUCCESS:
			outputArea.setText("Successfully saved session");
			break;
		case FAILURE:
			outputArea.setText("Unable to save session");
			break;
		default:
			break;
		}
	}

	/**
	 * this method attempts to load a previously saved session from the file path
	 * constant defined by the class. it displays a success/failure message on the
	 * GUI accordingly. if successful, it also updates the dealership list for the
	 * drop-down selector.
	 */
	private void loadSession() {
		// locate the file and check if it exists
		File saveFile = new File(SAVE_PATH);
		if (saveFile.isFile()) {

			// load file into current system
			i.importFile(saveFile);

			// update drop-down selector and GUI message
			updateDealershipComboBox(dealershipSelector);
			outputArea.setText("Previous session has been restored");

		} else {
			// display failure message if the save file doesn't exist
			outputArea.setText("Unable to restore previous session");
		}
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
		guiPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
		panelMain.add(guiPanel,
				new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
						null, new Dimension(700, 400), null, 0, false));
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
		dealershipSelector = new JComboBox<String>();
		final DefaultComboBoxModel<String> defaultComboBoxModel1 = new DefaultComboBoxModel<String>();
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
		final JScrollPane scrollPane1 = new JScrollPane();
		guiPanel.add(scrollPane1,
				new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3,
						com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
						com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
						com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK
								| com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
						null, null, null, 0, false));
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setEnabled(true);
		outputArea.setText("Output of vehicles will be displayed here");
		scrollPane1.setViewportView(outputArea);
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
