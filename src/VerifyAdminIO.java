import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import net.miginfocom.swing.*;

public class VerifyAdminIO {
    private Admin user;
    private ArrayList<User> query = new ArrayList<>();
    private DefaultListModel<User> listModel= new DefaultListModel<>();
    private JPanel pVerify;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList lstUsers;
    private JButton bHelp;
    private JButton bShow;
    private JButton bVerify;
    public VerifyAdminIO(Admin user) {
        initComponents();
        this.user = user;
        bVerify.setEnabled(false);
    }

    public JPanel getPanel(){
        return pVerify;
    }

    private void bVerify(ActionEvent e) {
        int choice = lstUsers.getSelectedIndex();
        user.approveUser(choice);

        query = user.getUnverifiedUsers();
        listModel.clear();
        listModel.addAll(query);
        lstUsers.setModel(listModel);
        lstUsers.revalidate();

    }

    private void lstUsersValueChanged(ListSelectionEvent e) {
        int choice = lstUsers.getSelectedIndex();
        bVerify.setEnabled(choice != -1);
    }

    private void bShow(ActionEvent e) {
        query = user.getUnverifiedUsers();
        listModel.addAll(query);
        lstUsers.setModel(listModel);
        bShow.setEnabled(listModel.isEmpty());
    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pVerify,"Πατήστε εμφάνιση μη επιβεβαιωμένων χρηστών, έπειτα επιλέξτε ένα από τους παραπάνω χρήστες από \nτην παραπάνω λίστα και έπειτα πατήστε επιβεβαίωση χρήστη.", "Βοήθεια" ,JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pVerify = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        lstUsers = new JList();
        bHelp = new JButton();
        bShow = new JButton();
        bVerify = new JButton();

        //======== pVerify ========
        {
            pVerify.setBorder(new TitledBorder("\u0395\u03c0\u03b9\u03b2\u03b5\u03b2\u03b1\u03af\u03c9\u03c3\u03b7 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7"));
            pVerify.setLayout(new MigLayout(
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
                            "[]"));

            //---- label1 ----
            label1.setText("\u039c\u03b7 \u03b5\u03c0\u03b9\u03b2\u03b5\u03b2\u03b1\u03b9\u03c9\u03bc\u03ad\u03bd\u03bf\u03b9 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b5\u03c2: ");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            pVerify.add(label1, "cell 0 0 10 1");

            //======== scrollPane1 ========
            {

                //---- lstUsers ----
                lstUsers.addListSelectionListener(e -> lstUsersValueChanged(e));
                scrollPane1.setViewportView(lstUsers);
            }
            pVerify.add(scrollPane1, "cell 0 1 10 8");

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));
            pVerify.add(bHelp, "cell 0 9");

            //---- bShow ----
            bShow.setText("\u0395\u03bc\u03c6\u03ac\u03bd\u03b9\u03c3\u03b7 \u03bc\u03b7 \u03b5\u03c0\u03b9\u03b2\u03b5\u03b2\u03b1\u03b9\u03c9\u03bc\u03ad\u03bd\u03c9\u03bd \u03c7\u03c1\u03b7\u03c3\u03c4\u03ce\u03bd");
            bShow.addActionListener(e -> bShow(e));
            pVerify.add(bShow, "cell 1 9 6 1");

            //---- bVerify ----
            bVerify.setText("\u0395\u03c0\u03b9\u03b2\u03b5\u03b2\u03b1\u03af\u03c9\u03c3\u03b7 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7");
            bVerify.addActionListener(e -> bVerify(e));
            pVerify.add(bVerify, "cell 7 9 3 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}