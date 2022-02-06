import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.*;

import java.util.ArrayList;

public class SearchUserAdminIO {
    private Admin user;
    private ArrayList<User> query;
    private String username;
    private DefaultListModel<User> listModel;
    private JPanel pSearchUser;
    private JLabel label2;
    private JTextField txtUsername;
    private JButton bHelp;
    private JButton bSearch;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JList lstUsers;

    public SearchUserAdminIO(Admin user) {
        initComponents();
        this.user = user;
        listModel = new DefaultListModel<>();
        bSearch.setEnabled(false);
    }

    public JPanel getPanel(){
        return pSearchUser;
    }

    private void bSearch(ActionEvent e) {
        listModel.removeAllElements();
        lstUsers.setModel(listModel);
        username = txtUsername.getText();
        if (username.isBlank()){
            JOptionPane.showMessageDialog(pSearchUser, "Το πεδίο αυτό δεν μπορεί να είναι κενό!", "Αποτυχία.", JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
        } else {
            query = user.searchUser(username);
            listModel.addAll(query);
            lstUsers.setModel(listModel);
        }

    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pSearchUser,"Δώστε ένα όνομα χρήστη προς αναζήτηση \nκαι έπειτα πατήστε Αναζήτηση.", "Βοήθεια" ,JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pSearchUser = new JPanel();
        label2 = new JLabel();
        txtUsername = new JTextField();
        bHelp = new JButton();
        bSearch = new JButton();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        lstUsers = new JList();

        //======== pSearchUser ========
        {
            pSearchUser.setBorder(new TitledBorder("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7"));
            pSearchUser.setLayout(new MigLayout(
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
                "[]"));

            //---- label2 ----
            label2.setText("\u038c\u03bd\u03bf\u03bc\u03b1 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7 \u03c0\u03c1\u03bf\u03c2 \u03b1\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7:");
            label2.setLabelFor(txtUsername);
            pSearchUser.add(label2, "cell 0 1");
            pSearchUser.add(txtUsername, "cell 1 1 9 1");
            txtUsername.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtUsername.getText().isBlank());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtUsername.getText().isBlank());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    bSearch.setEnabled(!txtUsername.getText().isBlank());
                }
            });

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));
            pSearchUser.add(bHelp, "cell 6 2");

            //---- bSearch ----
            bSearch.setText("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7");
            bSearch.addActionListener(e -> bSearch(e));
            pSearchUser.add(bSearch, "cell 7 2 3 1");

            //---- label3 ----
            label3.setText("\u0391\u03c0\u03bf\u03c4\u03ad\u03bb\u03b5\u03c3\u03bc\u03b1 \u03b1\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7\u03c2: ");
            label3.setLabelFor(lstUsers);
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            pSearchUser.add(label3, "cell 0 3 10 1");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(lstUsers);
            }
            pSearchUser.add(scrollPane1, "cell 0 4 10 7");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
