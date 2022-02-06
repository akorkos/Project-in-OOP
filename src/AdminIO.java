import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.*;
import net.miginfocom.swing.*;

public class AdminIO extends IO{
    private Admin user;
    private JPanel pAdmin;
    private JPanel pChoice;
    private JToolBar tMenu;
    private JButton bSearchUser;
    private JButton bSearchRes;
    private JButton bVerify;
    private JButton bMessage;
    private JButton bLogout;
    private JPanel pCards;

    public AdminIO(Admin user) {
        initComponents();
        this.user = user;
        this.user.setRooms(getRooms());
        this.user.setUsers(getUsers());
        this.user.setMessages(getMessages());
    }

    public JPanel getpChoice(){
        return pChoice;
    }

    public JPanel getpCards(){
        return pCards;
    }

    private void bSearchUser(ActionEvent e) {
        SearchUserAdminIO temp = new SearchUserAdminIO(user);
        pCards.removeAll();
        pCards.add(temp.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bSearchRes(ActionEvent e) {
        SearchResAdminIO temp = new SearchResAdminIO(user);
        pCards.removeAll();
        pCards.add(temp.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bVerify(ActionEvent e) {
        VerifyAdminIO temp = new VerifyAdminIO(user);
        pCards.removeAll();
        pCards.add(temp.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bMessage(ActionEvent e) {
        MessageAdminIO temp = new MessageAdminIO(user);
        pCards.removeAll();
        pCards.add(temp.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bLogout(ActionEvent e) {
        users = user.getUsers();

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.txt"))){
            out.writeObject(users);
        }catch (IOException error){
            error.printStackTrace();
        }

        int choice = JOptionPane.showConfirmDialog(
                pAdmin,
                "Θέλετε να αποσυνδεθείτε (κάθε αλλαγή θα αποθηκευτεί);",
                "Αποσύνδεση",
                JOptionPane.YES_NO_OPTION);

        if (choice == 0){
            JFrame home = (JFrame)SwingUtilities.windowForComponent(bMessage);

            home.dispose();

            home = new UI().getFrame();

            home.setVisible(true);
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pAdmin = new JPanel();
        pChoice = new JPanel();
        tMenu = new JToolBar();
        bSearchUser = new JButton();
        bSearchRes = new JButton();
        bVerify = new JButton();
        bMessage = new JButton();
        bLogout = new JButton();
        pCards = new JPanel();

        //======== pAdmin ========
        {
            pAdmin.setLayout(new MigLayout(
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


            {
                pChoice.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[]"));

                //======== tMenu ========
                {
                    tMenu.setFloatable(false);

                    //---- bSearchUser ----
                    bSearchUser.setText("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7");
                    bSearchUser.addActionListener(e -> bSearchUser(e));
                    tMenu.add(bSearchUser);

                    //---- bSearchRes ----
                    bSearchRes.setText("\u0391\u03bd\u03b1\u03b6\u03ae\u03c4\u03b7\u03c3\u03b7 \u03ba\u03c1\u03ac\u03c4\u03b7\u03c3\u03b7\u03c2");
                    bSearchRes.addActionListener(e -> bSearchRes(e));
                    tMenu.add(bSearchRes);

                    //---- bVerify ----
                    bVerify.setText("\u0388\u03b3\u03ba\u03c1\u03b9\u03c3\u03b7 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7");
                    bVerify.addActionListener(e -> bVerify(e));
                    tMenu.add(bVerify);

                    //---- bMessage ----
                    bMessage.setText("\u0391\u03c0\u03bf\u03c3\u03c4\u03bf\u03bb\u03ae \u03bc\u03b7\u03bd\u03cd\u03bc\u03b1\u03c4\u03bf\u03c2");
                    bMessage.addActionListener(e -> bMessage(e));
                    tMenu.add(bMessage);

                    //---- bLogout ----
                    bLogout.setText("\u0391\u03c0\u03bf\u03c3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7");
                    bLogout.addActionListener(e -> bLogout(e));
                    tMenu.add(bLogout);
                }
                pChoice.add(tMenu, "cell 0 0");
            }
            pAdmin.add(pChoice, "cell 0 0 11 1");

            //======== pCards ========
            {
                pCards.setLayout(new CardLayout());
            }
            pAdmin.add(pCards, "cell 0 1 11 10");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}

