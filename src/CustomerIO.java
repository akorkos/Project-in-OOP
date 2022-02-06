import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.*;

public class CustomerIO extends IO{
    private Customer user;
    private LocalDate start, end;
    private JPanel pCustomer;
    private JPanel pChoice;
    private JToolBar tMenu;
    private JButton bReservation;
    private JButton bCancelReservation;
    private JButton bInfos;
    private JButton bMessage;
    private JButton bLogout;
    private JPanel pCards;
    private JLabel lWelcome;
    public CustomerIO(Customer user) {
        initComponents();
        this.user = user;
        user.setRooms(rooms);
        ArrayList<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerID().equals(user.getUserID()))
                userReservations.add(reservation);
        }


        user.setReservations(userReservations);
        lWelcome.setText("Καλώς ήρθες " + user.getName() + " !");
    }

    public JPanel getpChoice(){
        return pChoice;
    }

    public JPanel getpCards(){
        return pCards;
    }

    private void bReservation(ActionEvent e) {
        RerserveCustomerIO resarvationPanel = new RerserveCustomerIO(user);
        pCards.removeAll();
        pCards.add(resarvationPanel.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bCancelReservation(ActionEvent e) {
        CancellationCustomerIO cancellationPanel = new CancellationCustomerIO(user);
        pCards.removeAll();
        pCards.add(cancellationPanel.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bInfos(ActionEvent e) {
        InfoCustomerIO infoPanel = new InfoCustomerIO(user);
        pCards.removeAll();
        pCards.add(infoPanel.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bMessage(ActionEvent e) {
        MessagesCustomerIO messagesPanel = new MessagesCustomerIO(user,getMessages());
        pCards.removeAll();
        pCards.add(messagesPanel.getPanel());
        pCards.repaint();
        pCards.revalidate();
    }

    private void bLogout(ActionEvent e) {
        // Διαδικασία αποσύνδεσης και αποθήκευσης των δεδομένων που αλλάξανε.
        rooms = user.getRooms(); // Ενημερώνει τα δωμάτια του συστήματος.

        reservations.removeIf(temp -> temp.getCustomerID().equals(user.getUserID()));     //Διαγράφει τις παλίες κρατήσεις
        reservations.addAll(user.getReservations());

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rooms.txt"))){
            out.writeObject(rooms);
        }catch (IOException l){
            l.printStackTrace();
        }
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("reservations.txt"))){
            out.writeObject(reservations);
        }catch (IOException l){
            l.printStackTrace();
        }

        int choice = JOptionPane.showConfirmDialog(
                pCustomer,
                "Θέλετε να αποσυνδεθείτε (κάθε αλλαγή θα αποθηκευτεί);",
                "Αποσύνδεση",
                JOptionPane.YES_NO_OPTION);

        if (choice == 0){
            JFrame home = (JFrame)SwingUtilities.windowForComponent(bInfos);

            home.dispose();

            home = new UI().getFrame();

            home.setVisible(true);
        }


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pCustomer = new JPanel();
        pChoice = new JPanel();
        tMenu = new JToolBar();
        bReservation = new JButton();
        bCancelReservation = new JButton();
        bInfos = new JButton();
        bMessage = new JButton();
        bLogout = new JButton();
        pCards = new JPanel();
        lWelcome = new JLabel();

        //======== pCustomer ========
        {
            pCustomer.setLayout(new MigLayout(
                "filly,hidemode 3,align left top",
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

            //======== pChoice ========
            {
                pChoice.setLayout(new MigLayout(
                    "filly,hidemode 3",
                    // columns
                    "[fill]",
                    // rows
                    "[]"));

                //======== tMenu ========
                {
                    tMenu.setFloatable(false);

                    //---- bReservation ----
                    bReservation.setText("\u039a\u03c1\u03ac\u03c4\u03b7\u03c3\u03b7");
                    bReservation.addActionListener(e -> {
			bReservation(e);
			bReservation(e);
		});
                    tMenu.add(bReservation);

                    //---- bCancelReservation ----
                    bCancelReservation.setText("\u0391\u03ba\u03cd\u03c1\u03c9\u03c3\u03b7 \u03ba\u03c1\u03ac\u03c4\u03b7\u03c3\u03b7\u03c2");
                    bCancelReservation.addActionListener(e -> bCancelReservation(e));
                    tMenu.add(bCancelReservation);

                    //---- bInfos ----
                    bInfos.setText("\u03a0\u03bb\u03b7\u03c1\u03bf\u03c6\u03bf\u03c1\u03af\u03b5\u03c2 \u03c7\u03c1\u03ae\u03c3\u03c4\u03b7");
                    bInfos.addActionListener(e -> bInfos(e));
                    tMenu.add(bInfos);

                    //---- bMessage ----
                    bMessage.setText("\u0395\u03b9\u03c3\u03b5\u03c1\u03c7\u03cc\u03bc\u03b5\u03bd\u03b1 \u039c\u03b7\u03bd\u03cd\u03bc\u03b1\u03c4\u03b1");
                    bMessage.addActionListener(e -> bMessage(e));
                    tMenu.add(bMessage);

                    //---- bLogout ----
                    bLogout.setText("\u0391\u03c0\u03bf\u03c3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7");
                    bLogout.addActionListener(e -> bLogout(e));
                    tMenu.add(bLogout);
                }
                pChoice.add(tMenu, "cell 0 0");
            }
            pCustomer.add(pChoice, "cell 0 0 11 1,align left top,grow 0 0");

            //======== pCards ========
            {
                pCards.setLayout(new CardLayout());

                //---- lWelcome ----
                lWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 28));
                lWelcome.setHorizontalAlignment(SwingConstants.CENTER);
                pCards.add(lWelcome, "card1");
            }
            pCustomer.add(pCards, "cell 0 1 11 10,align center center,grow 0 0");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}

