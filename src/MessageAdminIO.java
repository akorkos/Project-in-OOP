import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import net.miginfocom.swing.*;

public class MessageAdminIO {
    private Admin user;
    private String message;
    private JPanel pMessage;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JEditorPane txtMessage;
    private JButton bHelp;
    private JButton bBroadcast;
    public MessageAdminIO(Admin user) {
        initComponents();
        this.user = user;
        bBroadcast.setEnabled(false);
    }

    public JPanel getPanel(){
        return pMessage;
    }

    private void bBroadcast(ActionEvent e) {
        message = txtMessage.getText();

        if (message.isBlank()){
            JOptionPane.showMessageDialog(pMessage,
                    "Το μήνυμα δεν μπορεί να είναι κενό.",
                    "Λάθος εισαγωγή",
                    JOptionPane.ERROR_MESSAGE);
            txtMessage.requestFocus();
            JOptionPane.showMessageDialog(pMessage, "Το μήνυμα απέτυχε να σταλεί!", "Αποτυχία!", JOptionPane.ERROR_MESSAGE);
        } else {
            user.addMessage(message);
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("messages.txt"))){
                out.writeObject(user.getMessages());
            }catch (IOException l){
                l.printStackTrace();
            }
            JOptionPane.showMessageDialog(pMessage, "Το μήνυμα στάλθηκε επιτυχώς!", "Επιτυχία!", JOptionPane.INFORMATION_MESSAGE);
        }
        txtMessage.setText("");
    }

    private void bHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(pMessage,"Πληκτρολογήστε κάποιο μήνυμα που θέλετε να αποστείλετε σε όλους τους χρήστες και \nπατήστε Broadcast.", "Βοήθεια", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pMessage = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        txtMessage = new JEditorPane();
        bHelp = new JButton();
        bBroadcast = new JButton();

        //======== pMessage ========
        {
            pMessage.setBorder(new TitledBorder("\u0391\u03c0\u03bf\u03c3\u03c4\u03bf\u03bb\u03ae \u03bc\u03b7\u03bd\u03cd\u03bc\u03b1\u03c4\u03bf\u03c2"));
            pMessage.setLayout(new MigLayout(
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
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- label1 ----
            label1.setText("\u03a0\u03b1\u03c1\u03b1\u03ba\u03b1\u03bb\u03ce \u03c0\u03bb\u03b7\u03ba\u03c4\u03c1\u03bf\u03bb\u03bf\u03b3\u03ae\u03c3\u03c4\u03b5 \u03ad\u03bd\u03b1 \u03bc\u03ae\u03bd\u03c5\u03bc\u03b1:");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            pMessage.add(label1, "cell 0 0 12 1");

            //======== scrollPane1 ========
            {

                //---- txtMessage ----
                txtMessage.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        bBroadcast.setEnabled(!txtMessage.getText().isBlank());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        bBroadcast.setEnabled(!txtMessage.getText().isBlank());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        bBroadcast.setEnabled(!txtMessage.getText().isBlank());
                    }
                });
                scrollPane1.setViewportView(txtMessage);
            }
            pMessage.add(scrollPane1, "cell 0 1 12 6");

            //---- bHelp ----
            bHelp.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setSelectedIcon(UIManager.getIcon("OptionPane.questionIcon"));
            bHelp.setBorder(null);
            bHelp.addActionListener(e -> bHelp(e));

            pMessage.add(bHelp, "cell 8 7");

            //---- bBroadcast ----
            bBroadcast.setText("Broadcast");
            bBroadcast.addActionListener(e -> bBroadcast(e));
            pMessage.add(bBroadcast, "cell 9 7 3 1");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
