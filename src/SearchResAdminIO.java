import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import net.miginfocom.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchResAdminIO {
    private Admin user;
    private HashMap<Reservation, Room> query;
    private ArrayList<Reservation> reservations;
    private ArrayList<Room> rooms;
    private LocalDate start, end;
    private DefaultListModel<Reservation> listModelRes;
    private DefaultListModel<Room> listModelRoom;
    private DatePicker txtEnd, txtStart;
    private JPanel pSearchRes;
    private JLabel label2;
    private JLabel label3;
    private JButton bHelp;
    private JButton bSearch;
    private JLabel label4;
    private JLabel label5;
    private JScrollPane scrollPane1;
    private JList lstReservations;
    private JScrollPane scrollPane2;
    private JList lstRooms;

    public SearchResAdminIO(Admin user) {
        initComponents();
        this.user = user;
        query = new HashMap<>();
        reservations = new ArrayList<>();
        rooms = new ArrayList<>();
        listModelRes = new DefaultListModel<>();
        listModelRoom = new DefaultListModel<>();

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

        pSearchRes.add(txtStart, "cell 3 1 5 1");
        pSearchRes.add(txtEnd, "cell 3 2 5 1");
    }

    public JPanel getPanel(){
        return pSearchRes;
    }

    private void bSearch(ActionEvent e) {

        try {
            listModelRoom.removeAllElements();
            listModelRes.removeAllElements();
            lstRooms.setModel(listModelRoom);
            lstReservations.setModel(listModelRes);

            start = LocalDate.parse(txtStart.getText());
            end = LocalDate.parse(txtEnd.getText());

            query.clear();
            query = user.searchReservation(start, end);

            reservations.addAll(query.keySet());
            listModelRes.addAll(reservations);
            lstReservations.setModel(listModelRes);

            rooms.addAll(query.values());
            listModelRoom.addAll(rooms);
            lstRooms.setModel(listModelRoom);

        } catch (Exception error){
            JOptionPane.showMessageDialog(pSearchRes,
                   "Λανθασμένη εισαγωγή ημερομηνίας. Προσπαθήστε ξανά.",
                   "Λάθος εισαγωγή",
                   JOptionPane.ERROR_MESSAGE);
           txtStart.requestFocus();
        }
    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pSearchRes,"Πληκτρολογήστε το διάστημα των ημερομηνιών (YYYY-MM-DD) προς αναζήτηση και \nπατήστε Αναζήτηση.", "Βοήθεια", JOptionPane.INFORMATION_MESSAGE);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pSearchRes = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        bHelp = new JButton();
        bSearch = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        scrollPane1 = new JScrollPane();
        lstReservations = new JList();
        scrollPane2 = new JScrollPane();
        lstRooms = new JList();

        //======== pSearchRes ========
        {
            pSearchRes.setBorder(new TitledBorder("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7 \u03ba\u03c1\u03b1\u03c4\u03ae\u03c3\u03b5\u03c9\u03bd \u03bc\u03b5 \u03b7\u03bc\u03b5\u03c1\u03bf\u03bc\u03b7\u03bd\u03af\u03b5\u03c2"));
            pSearchRes.setLayout(new MigLayout(
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
                "[]"));

            //---- label2 ----
            label2.setText("\u0391\u03c0\u03cc: ");
            pSearchRes.add(label2, "cell 0 1 3 1");

            //---- label3 ----
            label3.setText("\u0395\u03ce\u03c2: ");
            pSearchRes.add(label3, "cell 0 2 3 1");

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));
            pSearchRes.add(bHelp, "cell 8 2");

            //---- bSearch ----
            bSearch.setText("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7");
            bSearch.addActionListener(e -> bSearch(e));
            pSearchRes.add(bSearch, "cell 9 2 5 1");

            //---- label4 ----
            label4.setText("\u0392\u03c1\u03ad\u03b8\u03b7\u03ba\u03b1\u03bd \u03bf\u03b9 \u03c0\u03b1\u03c1\u03b1\u03ba\u03ac\u03c4\u03c9 \u03ba\u03c1\u03b1\u03c4\u03ae\u03c3\u03b5\u03b9\u03c2: ");
            label4.setLabelFor(lstReservations);
            pSearchRes.add(label4, "cell 0 4 6 1");

            //---- label5 ----
            label5.setText("\u0393\u03b9\u03b1 \u03c4\u03b1 \u03b5\u03be\u03ae\u03c2 \u03b4\u03c9\u03bc\u03ac\u03c4\u03b9\u03b1: ");
            label5.setLabelFor(lstRooms);
            pSearchRes.add(label5, "cell 7 4 6 1");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(lstReservations);
            }
            pSearchRes.add(scrollPane1, "cell 0 5 6 1");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(lstRooms);
            }
            pSearchRes.add(scrollPane2, "cell 7 5 7 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
