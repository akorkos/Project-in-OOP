import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import net.miginfocom.swing.*;

public class CancellationCustomerIO {
    private Customer user;
    private DefaultListModel<Reservation> listModel;
    private JPanel pCancel;
    private JLabel label4;
    private JScrollPane scrollPane4;
    private JList lstReservations;
    private JButton bHelp;
    private JButton bCancel;

    public CancellationCustomerIO(Customer user) {
        initComponents();
        this.user = user;
        listModel = new DefaultListModel<>();
        listModel.addAll(user.getReservations());
        lstReservations.setModel(listModel);
        if (lstReservations.getSelectedIndex() == -1)
            bCancel.setEnabled(false);
    }

    private void bCancel(ActionEvent e) {
        try {
            int choice = lstReservations.getSelectedIndex();
            user.cancellation(choice);
            JOptionPane.showMessageDialog(pCancel, "Η ακύρωση πραγματοποιήθηκε με επιτυχία.");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(pCancel ,"Η ακύρωση απέτυχε. Επιλέξτε κάποια κράτηση.", "Λάθος εισαγωγή", JOptionPane.ERROR_MESSAGE);
        }
        listModel.clear();
        listModel.addAll(user.getReservations());
        lstReservations.setModel(listModel);
        bCancel.setEnabled(lstReservations.getSelectedIndex() != -1);
    }

    public JPanel getPanel(){
        return pCancel;
    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pCancel,"Επιλέξτε μια απο της διαθέσιμες κρατήσεις από \nτην παραπάνω λίστα και έπειτα πατήστε ακύρωση κράτησης.", "Βοήθεια" ,JOptionPane.INFORMATION_MESSAGE);
    }

    private void lstReservationsValueChanged(ListSelectionEvent e) {
        int choice = lstReservations.getSelectedIndex();
        bCancel.setEnabled(choice != -1);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pCancel = new JPanel();
        label4 = new JLabel();
        scrollPane4 = new JScrollPane();
        lstReservations = new JList();
        bHelp = new JButton();
        bCancel = new JButton();

        //======== pCancel ========
        {
            pCancel.setBorder(new TitledBorder("\u0391\u03ba\u03cd\u03c1\u03c9\u03c3\u03b7"));
            pCancel.setLayout(new MigLayout(
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
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- label4 ----
            label4.setText("\u039f\u03b9 \u03ba\u03c1\u03b1\u03c4\u03ae\u03c3\u03b5\u03b9\u03c2 \u03c3\u03b1\u03c2: ");
            label4.setHorizontalAlignment(SwingConstants.LEFT);
            pCancel.add(label4, "cell 0 0 9 1,align center center,grow 0 0");

            //======== scrollPane4 ========
            {

                //---- lstReservations ----
                lstReservations.addListSelectionListener(e -> lstReservationsValueChanged(e));
                scrollPane4.setViewportView(lstReservations);
            }
            pCancel.add(scrollPane4, "cell 0 1 9 1");

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));
            pCancel.add(bHelp, "cell 0 2");

            //---- bCancel ----
            bCancel.setText("\u0391\u03ba\u03cd\u03c1\u03c9\u03c3\u03b7 \u03ba\u03c1\u03ac\u03c4\u03b7\u03c3\u03b7\u03c2");
            bCancel.addActionListener(e -> bCancel(e));
            pCancel.add(bCancel, "cell 1 2 8 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
