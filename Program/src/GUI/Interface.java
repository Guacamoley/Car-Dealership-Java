package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Interface {
    public JPanel panelMain;
    private JButton exportToJSONButton;
    private JButton addVehicleButton;
    private JButton enableDisableAcquisitionButton;
    private JButton listVehiclesButton;
    private JComboBox selectDealer;
    private JTextArea outputOfDealershipVehiclesTextArea;
    private JButton chooseInputFileButton;

//    TODO: Debating on making a simple GUI.

    public Interface() {
        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: For selected dealership, when button is pressed, a vehicle can be added to that specified
                 *  dealership. WIP on how the vehicle will be added (either via json file upload or manual entry.
                 *  */
            }
        });
        enableDisableAcquisitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: For selected dealership, when button is pressed, acquisition will change for dealership.
                 *  Allow -> Not allow and vise versa. Will probably add some user output as well to let user know the changes.
                 *  */
            }
        });
        listVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: For selected dealership, when button is pressed, it will list out all vehicles the dealership
                 *  has available. It shall list out vehicles onto the JTextArea
                 * */
            }
        });
        exportToJSONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: When button is pressed, it will export all dealerships and all vehicles out into a new
                 *  JSON file. User feedback will also be outputted into the JTextArea.
                 *  */
            }
        });
        selectDealer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * TODO: JComboBox will display "Select Dealer" at the start. After uploading json input file, it will
                 *  update to list every dealership from json input file. Specified dealer can be selected to perform
                 *   functions on with other various buttons available.*/

            }
        }

        );
        chooseInputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.showOpenDialog(panelMain);
                /*
                * TODO: Try and catch files that may not be the right type, empty, nonexistent file etc.
                *  */
            }
        });
    }
    public void createInterface(){
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new Interface().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
