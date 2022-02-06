import javax.swing.*;
import javax.swing.border.*;
import net.miginfocom.swing.*;

public class InfoCustomerIO {
    private Customer user;
    private DefaultListModel<Reservation> listModel;
    private JPanel pInfo;
    private JLabel label6;
    private JLabel lUsername;
    private JLabel label7;
    private JLabel lPassword;
    private JLabel label8;
    private JLabel lName;
    private JLabel label9;
    private JLabel lAddress;
    private JLabel label10;
    private JLabel lEmail;
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JList lstReservations;

    public InfoCustomerIO(Customer user) {
        initComponents();
        this.user = user;
        listModel = new DefaultListModel<>();
        listModel.addAll(user.getReservations());
        lstReservations.setModel(listModel);
        lAddress.setText(user.getAddress());
        lEmail.setText(user.getEmail());
        lName.setText(user.getName());
        lPassword.setText(user.getPassword());
        lUsername.setText(user.getUsername());
    }

    public JPanel getPanel(){
        return pInfo;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pInfo = new JPanel();
        label6 = new JLabel();
        lUsername = new JLabel();
        label7 = new JLabel();
        lPassword = new JLabel();
        label8 = new JLabel();
        lName = new JLabel();
        label9 = new JLabel();
        lAddress = new JLabel();
        label10 = new JLabel();
        lEmail = new JLabel();
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        lstReservations = new JList();

        //======== pInfo ========
        {
            pInfo.setBorder(new TitledBorder("\u03a3\u03c4\u03bf\u03b9\u03c7\u03b5\u03af\u03b1"));
            pInfo.setLayout(new MigLayout(
                "hidemode 3",
                // columns
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
                "[]"));

            //---- label6 ----
            label6.setText("Username: ");
            pInfo.add(label6, "cell 0 1");
            pInfo.add(lUsername, "cell 1 1 4 1");

            //---- label7 ----
            label7.setText("Password: ");
            pInfo.add(label7, "cell 0 2");
            pInfo.add(lPassword, "cell 1 2 4 1");

            //---- label8 ----
            label8.setText("\u038c\u03bd\u03bf\u03bc\u03b1: ");
            pInfo.add(label8, "cell 0 3");
            pInfo.add(lName, "cell 1 3 4 1");

            //---- label9 ----
            label9.setText("\u0394\u03b9\u03b5\u03c5\u03b8\u03c5\u03bd\u03c3\u03b7:");
            pInfo.add(label9, "cell 0 4");
            pInfo.add(lAddress, "cell 1 4 4 1");

            //---- label10 ----
            label10.setText("Email: ");
            pInfo.add(label10, "cell 0 5");
            pInfo.add(lEmail, "cell 1 5 4 1");

            //---- label1 ----
            label1.setText("\u039a\u03c1\u03b1\u03c4\u03ae\u03c3\u03b5\u03b9\u03c2:");
            pInfo.add(label1, "cell 0 6");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(lstReservations);
            }
            pInfo.add(scrollPane2, "cell 0 7 5 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
