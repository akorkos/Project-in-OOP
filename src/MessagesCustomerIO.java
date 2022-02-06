import javax.swing.*;
import javax.swing.border.*;
import net.miginfocom.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagesCustomerIO {
    private Customer user;
    private JPanel pMessage;
    private JScrollPane scrollPane3;
    private JList lstMessages;
    public MessagesCustomerIO(Customer user, HashMap<String,String> messages) {
        initComponents();
        this.user = user;
        DefaultListModel<String> list = new DefaultListModel<>();
        for(Map.Entry<String,String> l :  messages.entrySet()){
            if(l.getKey().equals("all")||l.getKey().equals(user.getUserID()))
                for(String k : l.getValue().split("\n"))
                    list.addElement(k);
        }
        lstMessages.setModel(list);
    }

    public JPanel getPanel(){
        return pMessage;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pMessage = new JPanel();
        scrollPane3 = new JScrollPane();
        lstMessages = new JList();

        //======== pMessage ========
        {
            pMessage.setBorder(new TitledBorder("\u039c\u03b7\u03bd\u03cd\u03bc\u03b1\u03c4\u03b1"));
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
                "[]"));

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(lstMessages);
            }
            pMessage.add(scrollPane3, "cell 0 0 10 13");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
