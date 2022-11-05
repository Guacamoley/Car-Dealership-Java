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
 * the logic from GetVehicleInput, Car, and Inventory to complete user actions
 * in the GUI. Actions include: importing & exporting JSON file, disable &
 * enable acquisition, switching dealerships, adding vehicles, and listing
 * vehicles.
 *
 * @author Michael Ha
 */
public class Interface {
    // the location for the session save file
    final static String SAVE_PATH = "resources\\session.json";
    private final GetVehicleInput getInput = new GetVehicleInput();
    private JPanel panelMain;
    private JPanel guiPanel;
    private JTextArea outputArea;
    private JButton removeVehicleButton;
    private JButton exportToJSONButton;
    private JButton listVehiclesButton;
    private JComboBox<String> dealershipSelector;
    private JButton addVehicleButton;
    private JButton importJSON;
    private JRadioButton enableRadioButton;
    private JRadioButton disableRadioButton;
    private JButton transferButton;
    private JButton importXMLButton;
    private JButton saveButton;
    private JButton editDealerNameButton;
    private JButton setLoanButton;
    // the inventory of cars and dealerships being worked on
    private final Inventory i = new Inventory();
    // holder for whichever dealership is currently selected
    private String currentDealershipId = null;
    private RemoveVehicleInput remove;

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
         * Will prompt the user to enter information about the car being added into the system.
         * Gets the dealerID first to see if it exists and is acquiring. If true, then creates a new car
         * with createCar.
         */
        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a new input prompt window
                CreateCar createCar = new CreateCar();
                String responseMessage;
                String dealershipID = getInput.receiveDealerID();
                // Adds a new vehicle

                if (i.isExistingDealer(dealershipID) && !(i.getDealerAcquisition(dealershipID))) {
                    responseMessage = "Not added successfully.\nDealership cannot acquire vehicles.";
                } else {
                    Car newCar = createCar.addNewVehicle();
                    newCar.setDealership_id(dealershipID);
                    i.addIncomingVehicle(newCar);
                    responseMessage = "Vehicle added successfully";
                    responseMessage += ":\n" + newCar;

                }
                // update the dealership selector in case a new dealership was created
                updateDealershipComboBox(dealershipSelector);
                outputArea.setText(responseMessage);
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
                outputArea.setText(i.printCars(currentDealershipId, "\n"));
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
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".json", "json");
                fc.addChoosableFileFilter(fileFilter);
                int choice = fc.showSaveDialog(panelMain);

                if (choice == JFileChooser.APPROVE_OPTION) {
                    String fileName = fc.getSelectedFile().getAbsolutePath();
                    if (!fileName.endsWith(".json")) fileName += ".json";
                    i.exportFile(currentDealershipId, fileName);
                    outputArea.setText("Successfully exported to " + fileName);
                } else outputArea.setText("The export was cancelled by the user");
            }
        });

        /**
         * This button prompts the user to select a file. It imports cars in json format
         * from that file into the inventory. Cars will automatically be placed into
         * their respective dealerships. This also populates the dealership drop-down
         * selector.
         */
        importJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".json", "json");
                fc.addChoosableFileFilter(fileFilter);
                fc.setCurrentDirectory(new File("."));
                fc.showOpenDialog(panelMain);
                File file = fc.getSelectedFile();
                i.importFile(file, "json");
                updateDealershipComboBox(dealershipSelector);
                outputArea.setText("File " + file + " has been chosen");
            }
        });
        importXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".xml", "xml");
                fc.addChoosableFileFilter(fileFilter);
                fc.setCurrentDirectory(new File("."));
                fc.showOpenDialog(panelMain);
                File file = fc.getSelectedFile();
                i.importFile(file, "xml");
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

        /*
          Transfers a car from the current selected dealership to another dealership based on user input.
          Checks if that dealership exists and is accepting acquisitions or if dealership is the same as current dealer.
          Else it'll move to get the transferring car's ID and checks if it's valid. If it is valid, then the transfer
          will occur.
          */
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("Select a dealership to transfer to: \n");
                printComboBoxItems(dealershipSelector);
                String dealerToTransfer = getInput.receiveDealerID();

                if (!i.getDealerAcquisition(dealerToTransfer) || dealerToTransfer.equalsIgnoreCase(currentDealershipId)) {
                    outputArea.setText("Dealership you are transferring to either doesn't exist \nor you're trying to transfer" + " into the current dealership.");
                } else {
                    outputArea.setText("Cars available for transfer\n");
                    printCarLoanStatus();
                    String carToTransfer = getInput.receiveVehID();
                    Car carTransfer = getCarObject(currentDealershipId, carToTransfer);
                    if (!i.getDealerCars(currentDealershipId).toString().contains(carToTransfer))
                        outputArea.setText("Car with ID " + carToTransfer + " does not exist!");
                    if (carTransfer.getLoaned()) {
                        outputArea.setText("Car with ID " + carToTransfer + " is currently loaned!");
                    } else {
                        i.transferCar(currentDealershipId, dealerToTransfer, carToTransfer);
                        outputArea.setText("Successfully transferred vehicle " + carToTransfer + " to " + dealerToTransfer);
                    }
                }
            }
        });
        editDealerNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDealerName();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSession();
            }
        });

        setLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLoanStatus();
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

    /*
     * Prints out all items from a comboBox to output area*/
    private void printComboBoxItems(JComboBox<String> comboBox) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            outputArea.append(comboBox.getItemAt(i) + "\n");
        }
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
        outputArea.setText(i.printCars(currentDealershipId, "\n"));
    }

    // Edits the dealership name for each vehicle to user specified name.
    private void editDealerName() {
        String dealerName = getInput.receiveDealerName();
        for (Car car : i.getDealerCars(currentDealershipId)) {
            car.setDealerName(dealerName);
        }
        outputArea.setText("Dealer name for dealerID: " + currentDealershipId + " set to " + dealerName);
    }

    // Get user input for vehicle to be loaned. If vehicle is not loaned, it will be set to loaned, and vice-versa.
    // User feedback will also be provided after based on the vehicles loan status or if it exists.
    private void setLoanStatus() {
        printCarLoanStatus();
        String carToBeLoaned = getInput.receiveVehID();
        for (Car car : i.getDealerCars(currentDealershipId)) {
            if (car.getVehicle_id().matches(carToBeLoaned)) {
                car.setLoaned(!car.getLoaned());
                if (!car.getLoaned())
                    outputArea.setText("Vehicle " + carToBeLoaned + " is no longer loaned.");
                else outputArea.setText("Vehicle " + carToBeLoaned + " is now loaned.");
                break;
            } else outputArea.setText("No vehicle was found for ID " + carToBeLoaned);
        }
    }

    // Helper method for setLoanStatus. Prints out a list of loaned and not loaned vehicle ids.
    private void printCarLoanStatus() {
        outputArea.setText("Available Vehicles: \n");
        for (Car car : i.getDealerCars(currentDealershipId)) {
            if (!car.getLoaned()) {
                outputArea.append(car.getVehicle_id() + "\n");
            }
        }
        outputArea.append("Loaned Vehicles: \n");
        for (Car car : i.getDealerCars(currentDealershipId)) {
            if (car.getLoaned())
                outputArea.append(car.getVehicle_id() + "\n");
        }
    }

    // Returns a car object from specified dealer and carID
    private Car getCarObject(String dealerID, String carID) {
        for (Car car : i.getDealerCars(dealerID)) {
            if (car.getVehicle_id().equals(carID)) {
                return car;
            }
        }
        return null;
    }

    // Prints all car IDs into into output area
    private void printCarID(String dealerID) {
        for (Car car : i.getDealerCars(dealerID)) {
            outputArea.append("\n" + car.getVehicle_id());
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
            i.importFile(saveFile, "json");

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
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        guiPanel = new JPanel();
        guiPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(guiPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(700, 400), null, 0, false));
        exportToJSONButton = new JButton();
        exportToJSONButton.setText("Export to JSON");
        guiPanel.add(exportToJSONButton, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listVehiclesButton = new JButton();
        listVehiclesButton.setText("List Vehicles");
        guiPanel.add(listVehiclesButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dealershipSelector = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Select a dealership");
        dealershipSelector.setModel(defaultComboBoxModel1);
        guiPanel.add(dealershipSelector, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeVehicleButton = new JButton();
        removeVehicleButton.setText("Remove Vehicle");
        guiPanel.add(removeVehicleButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importJSON = new JButton();
        importJSON.setText("Import JSON");
        guiPanel.add(importJSON, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addVehicleButton = new JButton();
        addVehicleButton.setText("Add Vehicle");
        guiPanel.add(addVehicleButton, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableRadioButton = new JRadioButton();
        enableRadioButton.setText("Enable");
        guiPanel.add(enableRadioButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableRadioButton = new JRadioButton();
        disableRadioButton.setText("Disable");
        guiPanel.add(disableRadioButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        guiPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setEnabled(true);
        outputArea.setText("Output of vehicles will be displayed here");
        scrollPane1.setViewportView(outputArea);
        transferButton = new JButton();
        transferButton.setText("Transfer");
        guiPanel.add(transferButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importXMLButton = new JButton();
        importXMLButton.setText("Import XML ");
        guiPanel.add(importXMLButton, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        guiPanel.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editDealerNameButton = new JButton();
        editDealerNameButton.setText("Edit Dealer Name");
        guiPanel.add(editDealerNameButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setLoanButton = new JButton();
        setLoanButton.setText("Set Loan");
        guiPanel.add(setLoanButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
