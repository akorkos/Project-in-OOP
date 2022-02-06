import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import net.miginfocom.swing.*;

public class UI{
    private ArrayList<User> users;
    private JFrame fMain;
    private JPanel pWelcomeScreen;
    private JPanel pChoice;
    private JToolBar tMenu;
    private JButton bLogin;
    private JButton bRegister;
    private JPanel pCards;
    private JPanel pSignIn;
    private JLabel lWelcome;
    private JLabel lUsername;
    private JTextField txtUsername;
    private JLabel lPassword;
    private JPasswordField txtPassword;
    private JButton bSignIn;
    private JPanel pSignUp;
    private JLabel lWelcome2;
    private JLabel lType;
    private JRadioButton rbProvider;
    private JRadioButton rbCustomer;
    private JLabel lUsername2;
    private JTextField txtUsername2;
    private JLabel lPassword2;
    private JTextField txtPassword2;
    private JLabel label3;
    private JTextField txtName;
    private JLabel label4;
    private JTextField txtAddress;
    private JLabel label5;
    private JTextField txtEmail;
    private JButton bSignUp;

    public UI() {
        initComponents();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt"))){
            this.users = (ArrayList<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        fMain.setTitle("Splash Brothers (beta)");
        fMain.setSize(new Dimension(820, 520));
        fMain.setLocationRelativeTo(null);
        fMain.setVisible(true);
        fMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tMenu.setFloatable(false);
    }

    public JFrame getFrame(){
        return fMain;
    }

    private void bLogin(ActionEvent e) {
        pCards.removeAll();
        pCards.add(pSignIn);
        pCards.repaint();
        pCards.revalidate();
    }

    private void bRegister(ActionEvent e) {
        pCards.removeAll();
        pCards.add(pSignUp);
        pCards.repaint();
        pCards.revalidate();
    }

    private void bSignIn(ActionEvent e) {
        Login login = new Login(users);
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        if (login.validation(username, password) == null) {
            JOptionPane.showMessageDialog(fMain,
                    "Λανθασμένο όνομα χρήστη ή κωδικός πρόσβασης!",
                    "Λάθος εισαγωγή",
                    JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
        } else if (!login.validation(username, password).isVerified()){
            JOptionPane.showMessageDialog(fMain,
                    "Ο λογαριασμός σας δεν έχει ενεργοποιηθεί. Παρακαλώ προσπαθήστε ξανά αργότερα.",
                    "Μη επιβεβαιωμένος χρήστης",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (login.validation(username, password) instanceof Customer){
                CustomerIO temp = new CustomerIO((Customer) login.validation(username, password));
                pCards.removeAll();
                pCards.add(temp.getpCards());
                pCards.repaint();
                pCards.revalidate();

                pChoice.removeAll();
                pChoice.add(temp.getpChoice());
                pChoice.repaint();
                pChoice.revalidate();
            }
            else if (login.validation(username, password) instanceof Provider) {
                ProviderIO temp = new ProviderIO((Provider) login.validation(username, password));
                pCards.removeAll();
                pCards.add(temp.getpCards());
                pCards.repaint();
                pCards.revalidate();

                tMenu.removeAll();
                tMenu.add(temp.getpChoice());
                tMenu.repaint();
                tMenu.revalidate();
                fMain.pack();
            }
            else {
                AdminIO temp = new AdminIO((Admin) login.validation(username, password));
                pCards.removeAll();
                pCards.add(temp.getpCards());
                pCards.repaint();
                pCards.revalidate();

                pChoice.removeAll();
                pChoice.add(temp.getpChoice());
                pChoice.repaint();
                pChoice.revalidate();
            }

        }

    }

    private void bSignUp(ActionEvent e) {
        Register register = new Register(users);
        String username = txtUsername2.getText();
        String password = txtPassword2.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String type = null;

        if (rbCustomer.isSelected())
            type = "Πελάτης";
        else if (rbProvider.isSelected())
            type = "Πάροχος";
        else {
            JOptionPane.showMessageDialog(fMain,
                    "Επιλέξτε κάποιον τύπο χρήστη",
                    "Λάθος εισαγωγή",
                    JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
        }

        if (type != null && register.registration(username, password, email, name, address, type) && !username.isBlank() && !password.isBlank() && !email.isBlank()){
            JOptionPane.showMessageDialog(fMain,
                    "Η εγγραφή σας ήταν επιτυχής. Ο λογαριασμός σας δεν έχει εγκριθεί ακόμα, θα αποσυνδεθείτε αυτόματα.");

            JFrame home = fMain;

            home.dispose();

            home = new UI().getFrame();

            home.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(fMain,
                    "Ελέγξτε το όνομα χρήστη ή / και το Email.",
                    "Λάθος εισαγωγή",
                    JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
        }

        users = register.getUsers(); // Ενημερώνει την λίστα που κρατά όλους τους χρήστες του συστήματος.

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.txt"))){
            out.writeObject(users);
        } catch (IOException error){
            error.printStackTrace();
        }
    }

    private void txtPassword(ActionEvent e) {
        bSignIn(e);
    }

    private void fMainWindowOpened(WindowEvent e) {
        txtUsername.requestFocus();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        fMain = new JFrame();
        pWelcomeScreen = new JPanel();
        pChoice = new JPanel();
        tMenu = new JToolBar();
        bLogin = new JButton();
        bRegister = new JButton();
        pCards = new JPanel();
        pSignIn = new JPanel();
        lWelcome = new JLabel();
        lUsername = new JLabel();
        txtUsername = new JTextField();
        lPassword = new JLabel();
        txtPassword = new JPasswordField();
        bSignIn = new JButton();
        pSignUp = new JPanel();
        lWelcome2 = new JLabel();
        lType = new JLabel();
        rbProvider = new JRadioButton();
        rbCustomer = new JRadioButton();
        lUsername2 = new JLabel();
        txtUsername2 = new JTextField();
        lPassword2 = new JLabel();
        txtPassword2 = new JTextField();
        label3 = new JLabel();
        txtName = new JTextField();
        label4 = new JLabel();
        txtAddress = new JTextField();
        label5 = new JLabel();
        txtEmail = new JTextField();
        bSignUp = new JButton();

        //======== fMain ========
        {
            fMain.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    fMainWindowOpened(e);
                }
            });
            var fMainContentPane = fMain.getContentPane();
            fMainContentPane.setLayout(new BorderLayout());

            //======== pWelcomeScreen ========
            {
                pWelcomeScreen.setLayout(new MigLayout(
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
                    "[]" +
                    "[]"));

                //======== pChoice ========
                {
                    pChoice.setLayout(new MigLayout(
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
                        "[]"));

                    //======== tMenu ========
                    {
                        tMenu.setFloatable(false);

                        //---- bLogin ----
                        bLogin.setText("\u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7");
                        bLogin.addActionListener(e -> bLogin(e));
                        tMenu.add(bLogin);

                        //---- bRegister ----
                        bRegister.setText("\u0395\u03b3\u03b3\u03c1\u03b1\u03c6\u03ae");
                        bRegister.addActionListener(e -> bRegister(e));
                        tMenu.add(bRegister);
                    }
                    pChoice.add(tMenu, "cell 0 0 10 1");
                }
                pWelcomeScreen.add(pChoice, "cell 0 1 11 1");

                //======== pCards ========
                {
                    pCards.setLayout(new CardLayout());

                    //======== pSignIn ========
                    {
                        pSignIn.setLayout(new MigLayout(
                            "hidemode 3,alignx center",
                            // columns
                            "[fill]" +
                            "[fill]" +
                            "[fill]",
                            // rows
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]"));

                        //---- lWelcome ----
                        lWelcome.setText("\u03a3\u03c5\u03bc\u03c0\u03bb\u03b7\u03c1\u03ce\u03c3\u03c4\u03b5 \u03c4\u03b1 \u03c3\u03c4\u03bf\u03b9\u03c7\u03b5\u03af\u03b1 \u03c3\u03b1\u03c2: ");
                        pSignIn.add(lWelcome, "cell 0 0 2 1");

                        //---- lUsername ----
                        lUsername.setText("Username: ");
                        pSignIn.add(lUsername, "cell 0 1");
                        pSignIn.add(txtUsername, "cell 1 1 2 1");

                        //---- lPassword ----
                        lPassword.setText("Password: ");
                        pSignIn.add(lPassword, "cell 0 2");

                        //---- txtPassword ----
                        txtPassword.addActionListener(e -> txtPassword(e));
                        pSignIn.add(txtPassword, "cell 1 2 2 1");

                        //---- bSignIn ----
                        bSignIn.setText("\u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7");
                        bSignIn.addActionListener(e -> bSignIn(e));

                        pSignIn.add(bSignIn, "cell 1 3 2 1");
                    }
                    pCards.add(pSignIn, "card1");

                    //======== pSignUp ========
                    {
                        pSignUp.setLayout(new MigLayout(
                            "hidemode 3,alignx center",
                            // columns
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
                            "[]"));

                        //---- lWelcome2 ----
                        lWelcome2.setText("\u03a3\u03c5\u03bc\u03c0\u03bb\u03b7\u03c1\u03ce\u03c3\u03c4\u03b5 \u03c4\u03b1 \u03c3\u03c4\u03bf\u03b9\u03c7\u03b5\u03af\u03b1 \u03c3\u03b1\u03c2: ");
                        pSignUp.add(lWelcome2, "cell 0 0 2 1");

                        //---- lType ----
                        lType.setText("\u03a4\u03cd\u03c0\u03bf\u03c2:");
                        pSignUp.add(lType, "cell 0 1 1 2");

                        //---- rbProvider ----
                        rbProvider.setText("\u03a0\u03ac\u03c1\u03bf\u03c7\u03bf\u03c2");
                        pSignUp.add(rbProvider, "cell 1 1 2 1");

                        //---- rbCustomer ----
                        rbCustomer.setText("\u03a0\u03b5\u03bb\u03ac\u03c4\u03b7\u03c2");
                        pSignUp.add(rbCustomer, "cell 1 2 2 1");

                        //---- lUsername2 ----
                        lUsername2.setText("Username: ");
                        pSignUp.add(lUsername2, "cell 0 3");
                        pSignUp.add(txtUsername2, "cell 1 3 2 1");

                        //---- lPassword2 ----
                        lPassword2.setText("Password: ");
                        pSignUp.add(lPassword2, "cell 0 4");
                        pSignUp.add(txtPassword2, "cell 1 4 2 1");

                        //---- label3 ----
                        label3.setText("\u038c\u03bd\u03bf\u03bc\u03b1: ");
                        pSignUp.add(label3, "cell 0 5");
                        pSignUp.add(txtName, "cell 1 5 2 1");

                        //---- label4 ----
                        label4.setText("\u0394\u03b9\u03b5\u03cd\u03b8\u03c5\u03bd\u03c3\u03b7: ");
                        pSignUp.add(label4, "cell 0 6");
                        pSignUp.add(txtAddress, "cell 1 6 2 1");

                        //---- label5 ----
                        label5.setText("Email: ");
                        pSignUp.add(label5, "cell 0 7");
                        pSignUp.add(txtEmail, "cell 1 7 2 1");

                        //---- bSignUp ----
                        bSignUp.setText("\u0395\u03b3\u03b3\u03c1\u03b1\u03c6\u03ae");
                        bSignUp.addActionListener(e -> bSignUp(e));
                        pSignUp.add(bSignUp, "cell 1 8 2 1");
                    }
                    pCards.add(pSignUp, "card2");
                }
                pWelcomeScreen.add(pCards, "cell 0 2 11 10");
            }
            fMainContentPane.add(pWelcomeScreen, BorderLayout.CENTER);
            fMain.pack();
            fMain.setLocationRelativeTo(fMain.getOwner());
        }

        //---- groupType ----
        var groupType = new ButtonGroup();
        groupType.add(rbProvider);
        groupType.add(rbCustomer);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}