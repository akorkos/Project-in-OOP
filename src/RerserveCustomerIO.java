import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import net.miginfocom.swing.*;


public class RerserveCustomerIO{
    private Customer user;
    private LocalDate start, end;
    private Integer min, max;
    private DefaultListModel<Room> listModel;
    private ArrayList<String> types;
    private DefaultComboBoxModel<String> comboModel;
    private DatePicker txtStart, txtEnd;
    private JPanel pReserve;
    private JLabel lLocation;
    private JTextField txtLocation;
    private JLabel label12;
    private JComboBox comboBoxType;
    private JLabel lStart;
    private JLabel label13;
    private JSpinner spMin;
    private JLabel lEnd;
    private JLabel label14;
    private JSpinner spMax;
    private JLabel lBeds;
    private JSpinner spBeds;
    private JLabel label15;
    private JSpinner spArea;
    private JCheckBox cbFilters;
    private JCheckBox cbWifi;
    private JScrollPane scrollPane1;
    private JList lstRooms;
    private JCheckBox cbParking;
    private JCheckBox cbBreakfast;
    private JCheckBox cbKitchen;
    private JCheckBox cbAC;
    private JButton bSearch;
    private JButton bReserve;
    private JButton bHelp;

    public RerserveCustomerIO(Customer user) {
        initComponents();
        this.user = user;
        listModel = new DefaultListModel<>();
        comboModel = new DefaultComboBoxModel<>();
        types = new ArrayList<>();
        types.add("Σπίτι");
        types.add("Δωμάτιο Ξενοδοχείου");
        types.add("Μεζονέτα");
        types.add("Διαμέρισμα");
        min = getMinMax().get(0);
        max = getMinMax().get(1);
        spBeds.setModel(new SpinnerNumberModel(1, 1, 16, 1));
        spArea.setModel(new SpinnerNumberModel(1, 1,10000,1));
        spMin.setModel(new SpinnerNumberModel((int) min, (int) min, (int) max, 1));
        spMax.setModel(new SpinnerNumberModel((int) max, (int) min, (int) max, 1));
        comboModel.addAll(types);
        comboBoxType.setModel(comboModel);
        fieldInit(false);
        bReserve.setEnabled(false);
        bSearch.setEnabled(false);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateSettings.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setAllowKeyboardEditing(false);

        DatePickerSettings dateSettings1 = new DatePickerSettings();
        dateSettings1.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateSettings1.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");
        dateSettings1.setAllowEmptyDates(false);
        dateSettings1.setAllowKeyboardEditing(false);

        txtStart = new DatePicker(dateSettings);
        txtEnd = new DatePicker(dateSettings1);

        pReserve.add(txtStart, "cell 1 2 4 1");
        pReserve.add(txtEnd, "cell 1 3 4 1");

    }


    private void fieldInit(Boolean flag){
        cbWifi.setEnabled(flag);
        cbAC.setEnabled(flag);
        cbBreakfast.setEnabled(flag);
        cbKitchen.setEnabled(flag);
        cbParking.setEnabled(flag);
        spMax.setEnabled(flag);
        spMin.setEnabled(flag);
        spArea.setEnabled(flag);
        comboBoxType.setEnabled(flag);
    }

    public JPanel getPanel(){
        return pReserve;
    }

    private void bSearch(ActionEvent e) {
        String location, type;
        Integer min, max, area, beds;
        Boolean wifi, kitchen, breakfast, ac, parking;

        listModel.clear();
        lstRooms.setModel(listModel);

        location = txtLocation.getText();
        beds = (Integer) spBeds.getValue();

        start = LocalDate.parse(txtStart.getText());
        end = LocalDate.parse(txtEnd.getText());

        if (cbFilters.isSelected() && start != null && end != null){
            wifi = cbWifi.isSelected();
            kitchen = cbKitchen.isSelected();
            breakfast = cbBreakfast.isSelected();
            ac = cbAC.isSelected();
            parking = cbParking.isSelected();

            min = (Integer) spMin.getValue();
            max = (Integer) spMax.getValue();
            area = (Integer) spArea.getValue();

            type = (String) comboBoxType.getSelectedItem();

            user.search(new Filter(location, start, end, beds, type, area, min, max, wifi, parking, breakfast, kitchen, ac), cbFilters.isSelected());
        } else if (!cbFilters.isSelected() && start != null && end != null)
            user.search(new Filter(location, start, end, beds), cbFilters.isSelected());

        listModel.addAll(user.getQuery());
        lstRooms.setModel(listModel);
        bReserve.setEnabled(!listModel.isEmpty());
    }

    private ArrayList<Integer> getMinMax(){
        ArrayList<Integer> minmax = new ArrayList<>();
        ArrayList<Room> rooms = user.getRooms();
        minmax.add(rooms.get(0).getPrice());
        minmax.add(rooms.get(0).getPrice());


        for (Room room: rooms) {
            if (minmax.get(0) > room.getPrice())
                minmax.set(0, room.getPrice());
            if (minmax.get(1) < room.getPrice())
                minmax.set(1, room.getPrice());
        }

        return minmax;
    }

    private void bReserve(ActionEvent e) {
        Room choice = (Room) lstRooms.getSelectedValue();
        if (user.reservation(choice, start, end))
            JOptionPane.showMessageDialog(pReserve, "Η κράτηση πραγματοποιήθηκε με επιτυχία. Μπορείτε να την βρείτε στην καρτέλα πληροφορίες χρήστη.");
        else
            JOptionPane.showMessageDialog(pReserve, "Δεν μπορεί η ημερομηνία έναρξης να είναι ίδια με την λήξης! Προσπαθήστε εκ νέου.", "Λάθος εισαγωγή", JOptionPane.ERROR_MESSAGE);
        listModel.clear();
        bReserve.setEnabled(false);
        txtEnd.setDateToToday();
        txtStart.setDateToToday();
        txtLocation.setText("");
        spBeds.setValue(1);

    }

    private void cbFilters(ActionEvent e) {
        fieldInit(cbFilters.isSelected());
    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pReserve,"Συμπληρώστε τα πεδία για να προχωρήσετε στην αναζήτηση, πατώντας αναζήτηση. \nΈπειτα διαλέξτε ένα δωμάτιο από την παρακάτω λίστα και πατήστε κράτηση.", "Βοήθεια" ,JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pReserve = new JPanel();
        lLocation = new JLabel();
        txtLocation = new JTextField();
        label12 = new JLabel();
        comboBoxType = new JComboBox();
        lStart = new JLabel();
        label13 = new JLabel();
        spMin = new JSpinner();
        lEnd = new JLabel();
        label14 = new JLabel();
        spMax = new JSpinner();
        lBeds = new JLabel();
        spBeds = new JSpinner();
        label15 = new JLabel();
        spArea = new JSpinner();
        cbFilters = new JCheckBox();
        cbWifi = new JCheckBox();
        scrollPane1 = new JScrollPane();
        lstRooms = new JList();
        cbParking = new JCheckBox();
        cbBreakfast = new JCheckBox();
        cbKitchen = new JCheckBox();
        cbAC = new JCheckBox();
        bSearch = new JButton();
        bReserve = new JButton();
        bHelp = new JButton();

        //======== pReserve ========
        {
            pReserve.setBorder(new TitledBorder("\u03a7\u03ce\u03c1\u03bf\u03b9 \u03b4\u03b9\u03b1\u03bc\u03bf\u03bd\u03ae\u03c2"));
            pReserve.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- lLocation ----
            lLocation.setText("\u03a0\u03bf\u03c5 \u03b8\u03ad\u03bb\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03c0\u03ac\u03c4\u03b5: ");
            pReserve.add(lLocation, "cell 0 1");
            pReserve.add(txtLocation, "cell 1 1 4 1");
            txtLocation.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtLocation.getText().isBlank());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtLocation.getText().isBlank());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtLocation.getText().isBlank());
                }
            });

            //---- label12 ----
            label12.setText("\u03a4\u03cd\u03c0\u03bf\u03c2 \u03ba\u03b1\u03c4\u03b1\u03bb\u03cd\u03bc\u03b1\u03c4\u03bf\u03c2: ");
            pReserve.add(label12, "cell 5 1");
            pReserve.add(comboBoxType, "cell 6 1 3 1");

            //---- lStart ----
            lStart.setText("\u03a0\u03cc\u03c4\u03b5 \u03b8\u03ad\u03bb\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03c0\u03ac\u03c4\u03b5: ");
            pReserve.add(lStart, "cell 0 2");

            //---- label13 ----
            label13.setText("\u0395\u03bb\u03ac\u03c7\u03b9\u03c3\u03c4\u03b7 \u03c4\u03b9\u03bc\u03ae: ");
            pReserve.add(label13, "cell 5 2");
            pReserve.add(spMin, "cell 6 2 3 1");

            //---- lEnd ----
            lEnd.setText("\u03a0\u03cc\u03c4\u03b5 \u03b8\u03ad\u03bb\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03b3\u03c5\u03c1\u03af\u03c3\u03b5\u03c4\u03b5: ");
            pReserve.add(lEnd, "cell 0 3");

            //---- label14 ----
            label14.setText("\u039c\u03ad\u03b3\u03b9\u03c3\u03c4\u03b7 \u03c4\u03b9\u03bc\u03ae: ");
            pReserve.add(label14, "cell 5 3");
            pReserve.add(spMax, "cell 6 3 3 1");

            //---- lBeds ----
            lBeds.setText("\u03a0\u03cc\u03c3\u03bf\u03b9 \u03b8\u03b1 \u03b5\u03af\u03c3\u03c4\u03b5:");
            pReserve.add(lBeds, "cell 0 4");
            pReserve.add(spBeds, "cell 1 4 4 1");

            //---- label15 ----
            label15.setText("\u0395\u03c0\u03b9\u03c6\u03ac\u03bd\u03b5\u03b9\u03b1 \u03ba\u03b1\u03c4\u03b1\u03bb\u03cd\u03bc\u03b1\u03c4\u03bf\u03c2: ");
            pReserve.add(label15, "cell 5 4");
            pReserve.add(spArea, "cell 6 4 3 1");

            //---- cbFilters ----
            cbFilters.setText("\u03a6\u03af\u03bb\u03c4\u03c1\u03b1 \u03b1\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7\u03c2");
            cbFilters.addActionListener(e -> cbFilters(e));
            pReserve.add(cbFilters, "cell 1 5 4 1");

            //---- cbWifi ----
            cbWifi.setText("WiFi");
            pReserve.add(cbWifi, "cell 6 5");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(lstRooms);
            }
            pReserve.add(scrollPane1, "cell 0 6 6 7");

            //---- cbParking ----
            cbParking.setText("\u03a0\u03ac\u03c1\u03ba\u03b9\u03bd\u03b3\u03ba ");
            pReserve.add(cbParking, "cell 6 6");

            //---- cbBreakfast ----
            cbBreakfast.setText("\u03a0\u03c1\u03c9\u03b9\u03bd\u03cc ");
            pReserve.add(cbBreakfast, "cell 6 7");

            //---- cbKitchen ----
            cbKitchen.setText("\u039a\u03bf\u03c5\u03b6\u03af\u03bd\u03b1 ");
            pReserve.add(cbKitchen, "cell 6 8");

            //---- cbAC ----
            cbAC.setText("\u039a\u03bb\u03b9\u03bc\u03b1\u03c4\u03b9\u03c3\u03bc\u03cc\u03c2 ");
            pReserve.add(cbAC, "cell 6 9");

            //---- bSearch ----
            bSearch.setText("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7");
            bSearch.addActionListener(e -> bSearch(e));
            pReserve.add(bSearch, "cell 6 10 4 1");

            //---- bReserve ----
            bReserve.setText("\u039a\u03c1\u03ac\u03c4\u03b7\u03c3\u03b7");
            bReserve.addActionListener(e -> bReserve(e));
            pReserve.add(bReserve, "cell 6 11 3 1");

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));
            pReserve.add(bHelp, "cell 9 11");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
