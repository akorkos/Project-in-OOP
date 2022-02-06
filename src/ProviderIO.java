import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Wed Jan 05 21:45:10 EET 2022
 */

/**
 * @author unknown
 */
public class ProviderIO extends IO{
    //protected ArrayList<Room> rooms;
    //protected HashMap<String,String> messages;
    protected JPanel pCards = new JPanel();
    final protected JToolBar tMenu = new JToolBar();
    Provider provider;

    public ProviderIO(Provider provider) {

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("messages.txt"))){
            messages = (HashMap<String, String>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("rooms.txt"))){
            rooms = (ArrayList<Room>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        this.provider = provider;
        this.provider.setRooms(rooms);
        this.provider.setReservations();
        pCards.setLayout(new GridLayout(0,1));
        pCards.setSize(addApartment(null).getPreferredSize());
        initialise();
    }

    private void initialise(){
        tMenu.setLayout(new GridLayout(0,5,2,3));
        tMenu.setFloatable(false);
        tMenu.add(button1());
        tMenu.add(button2());
        tMenu.add(button3());
        tMenu.add(button4());
        tMenu.add(button5());
        tMenu.add(button6());
        tMenu.add(button7());
        tMenu.add(button8());
        tMenu.add(button9());
        tMenu.add(button10());
        pCards.add(addApartment(null));
    }

    public JToolBar getpChoice(){
        return tMenu;
    }

    public JPanel getpCards(){
        return pCards;
    }

    private JButton button1(){
        JButton button1 = new JButton("Προσθήκη Καταλύματος");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                //pCards.setLayout(new GridLayout(0,1));
                //pCards.setSize(addAppartment(null).getPreferredSize());
                pCards.add(addApartment(null));
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button1;
    }

    private JButton button2(){
        JButton button2 = new JButton("Επεξεργασία Καταλύματος");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRoom();
            }
        });
        return button2;
    }

    private JButton button3(){
        JButton button3 = new JButton("Διαγραφή Καταλύματος");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                pCards.add(deleteRoom());
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button3;
    }

    private JButton button4(){
        JButton button4 = new JButton("Πληροφορίες Χρήστη");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                JPanel temp = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Στοιχεία"));
                temp.setLayout(new GridLayout(0,2,5,10));
                JLabel username = new JLabel("Useraneme :");
                JLabel username_ = new JLabel(provider.getUsername());
                JLabel password = new JLabel("Password :");
                JLabel password_ = new JLabel(provider.getPassword());
                JLabel name = new JLabel("Όνομα :");
                JLabel name_ = new JLabel(provider.getName());
                JLabel address = new JLabel("Διεύθυνση :");
                JLabel address_ = new JLabel(provider.getAddress());
                JLabel email = new JLabel("Email :");
                JLabel email_ = new JLabel(provider.getEmail());
                temp.add(username);
                temp.add(username_);
                temp.add(password);
                temp.add(password_);
                temp.add(name);
                temp.add(name_);
                temp.add(address);
                temp.add(address_);
                temp.add(email);
                temp.add(email_);
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button4;
    }

    private JButton button5(){
        JButton button5 = new JButton("Προβολή Καταλυμάτων");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                JPanel temp = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Τα Καταλύματα σας"));
                temp.setLayout(new GridLayout(0,1,2,2));
                temp.add(new JScrollPane(new JList(provider.getRooms().toArray())));
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button5;
    }

    private JButton button6(){
        JButton button6 = new JButton("Αναζήτηση Καταλυμάτων");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel temp = new JPanel();
                JPanel slave = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Αναζήτηση με βάση τον τύπο"));
                temp.setLayout(new GridLayout(0,1));
                JPanel temp2 = new JPanel();

                JLabel labeltype = new JLabel("Τύπος :");
                String[] typelist = {"Σπίτι","Διαμέρισμα","Δωμάτιο Ξενοδοχείου","Μεζονέτα"};
                JComboBox type = new JComboBox(typelist);
                type.setSelectedIndex(0);
                type.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        temp.removeAll();
                        temp.add(temp2,BorderLayout.PAGE_START);
                        temp.add(new JScrollPane(new JList(provider.findType((String) type.getSelectedItem()).toArray())));
                        pCards.removeAll();
                        pCards.add(temp);
                        pCards.repaint();
                        pCards.revalidate();
                    }
                });
                temp2.add(labeltype);
                temp2.add(type);
                temp.add(temp2);
                temp.add(new JScrollPane(new JList(provider.findType((String) type.getSelectedItem()).toArray())));
                pCards.removeAll();
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button6;
    }

    private JButton button7(){
        JButton button7 = new JButton("Συνολικές Κρατήσεις");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                JPanel temp = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Συνολικές Κρατήσεις"));
                temp.setLayout(new GridLayout(0,1,2,2));
                if(provider.getReservation().size()==0) {
                    temp.add(new JLabel("Δεν έχει γίνει καμία κράτηση στα καταλύματα σας!"));
                    JOptionPane.showMessageDialog(null, "Δεν έχει γίνει καμία κράτηση στα καταλύματα σας!",
                            "Πληροφοριακό μήνυμα", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    temp.add(new JScrollPane(new JList(provider.getReservation().toArray())));
                }
                temp.repaint();
                temp.revalidate();
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button7;
    }

    private JButton button8(){
        JButton button8 = new JButton("Συνολικές Ακυρώσεις");
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pCards.removeAll();
                JPanel temp = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Συνολικές Ακυρώσεις"));
                //temp.setLayout(new GridLayout(0,2));
                JLabel cancelations = new JLabel("Συνολικές  Ακυρώσεις :");
                JLabel cancelations_ = new JLabel(Integer.toString(provider.getCancellation()));
                temp.add(cancelations);
                temp.add(cancelations_);
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button8;
    }

    private JButton button9(){
        JButton button9 = new JButton("Μηνύματα");
        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel temp = new JPanel();
                temp.setBorder(BorderFactory.createTitledBorder("Μηνύματα"));
                temp.setLayout(new GridLayout(0,1,2,2));
                if(messages.size()==0) {
                    temp.add(new JLabel("Δεν υπάρχει κάποιο μήνυμα!"));
                    JOptionPane.showMessageDialog(null, "Δεν υπάρχει κάποιο μήνυμα!",
                            "Πληροφοριακό μήνυμα", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    DefaultListModel<String> list = new DefaultListModel<>();
                    for(Map.Entry<String,String> l : messages.entrySet()){
                        if(l.getKey().equals("all")||l.getKey().equals(provider.getUserID()))
                            for(String k : l.getValue().split("\n"))
                                list.addElement(k);
                    }
                    temp.add(new JScrollPane(new JList(list.toArray())));
                }
                pCards.removeAll();
                pCards.add(temp);
                pCards.repaint();
                pCards.revalidate();
            }
        });
        return button9;
    }

    private JButton button10(){
        JButton button10 = new JButton("Αποσύνδεση-Αποθήκευση");
        button10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRooms(provider);   //μέθοδος που ενημερώνει τη λίστα με όλα τα δωμάτια
                try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rooms.txt"))){
                    out.writeObject(rooms);
                    //printRooms(rooms);
                }catch (IOException l){
                    l.printStackTrace();
                }
                int choice = JOptionPane.showConfirmDialog(
                        new Panel(),
                        "Θέλετε να αποσυνδεθείτε (κάθε αλλαγή θα αποθηκευτεί);",
                        "Αποσύνδεση",
                        JOptionPane.YES_NO_OPTION);

                if (choice == 0){
                JFrame home = (JFrame)SwingUtilities.windowForComponent(button10);

                home.dispose();

                home = new UI().getFrame();

                home.setVisible(true);
                }

            }
        });
        return button10;
    }

    private JPanel deleteRoom(){
        JPanel temp = new JPanel();
        temp.setBorder(BorderFactory.createTitledBorder("Διαγραφή Καταλύματος"));
        temp.setLayout(new GridLayout(0,1,2,2));
        final JList<Room>[] l = new JList[]{new JList(provider.getRooms().toArray())};
        final JScrollPane[] sp = {new JScrollPane(l[0])};
        JPanel temp2 = new JPanel();
        JButton button = new JButton("Διαγραφή");
        temp2.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    provider.deleteRoom(provider.getRooms().get(l[0].getSelectedIndex()));
                    simpleMessageNoTitle("Το κατάλυμα Διαγράφηκε!");
                    temp.removeAll();
                    l[0] = new JList(provider.getRooms().toArray());
                    sp[0] = new JScrollPane(l[0]);
                    temp.add(sp[0]);
                    temp.add(temp2);
                    temp.repaint();
                    temp.revalidate();
                }catch (Exception ignored){
                    JOptionPane.showMessageDialog(null, "Δεν έγινε η Διαγραφή", "Λάθος!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        temp.add(sp[0]);
        temp.add(temp2,BorderLayout.PAGE_END);
        return temp;
    }

    private void editRoom(){
        JPanel basic = new JPanel();
        JPanel slave = new JPanel();
        basic.setBorder(BorderFactory.createTitledBorder("Επιλογή καταλύματος για επεξεργασία"));
        basic.setLayout(new GridLayout(0,1,2,2));
        JList<Room> l = new JList(provider.getRooms().toArray());
        JScrollPane sp = new JScrollPane(l);
        JButton button = new JButton("Επιλογή");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(l.getSelectedIndex());
                if (l.getSelectedIndex()!=-1) {
                    pCards.removeAll();
                    pCards.add(addApartment(provider.getRooms().get(l.getSelectedIndex())));
                    pCards.repaint();
                    pCards.revalidate();
                }
                else
                    JOptionPane.showMessageDialog(null, "Δεν έγινε η Επιλογή", "Λάθος!", JOptionPane.ERROR_MESSAGE);
            }
        });
        slave.add(button);
        basic.add(sp);
        pCards.removeAll();
        pCards.add(basic);
        pCards.add(slave,BorderLayout.PAGE_END);
        pCards.repaint();
        pCards.revalidate();
    }

    private JPanel addApartment(Room room){

        String[] typelist = {"Σπίτι","Διαμέρισμα","Δωμάτιο Ξενοδοχείου","Μεζονέτα"};
        HashMap<String,Integer> typemap = new HashMap<>(){{put("Σπίτι",0);put("Διαμέρισμα",1);put("Δωμάτιο Ξενοδοχείου",2);put("Μεζονέτα",3);}};
        JComboBox type = new JComboBox(typelist);
        JLabel labeltype = new JLabel("Τύπος :");
        type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                //System.out.println((String) );
            }
        });

        JLabel labelname = new JLabel("Όνομα :");
        JLabel labelLocation = new JLabel("Τοποθεσία καταλύματος :");
        JLabel labelprice = new JLabel("Τιμή καταλύματος :");
        JLabel labelarea = new JLabel("Τετραγωνικά Μέτρα :");
        JLabel labelbeds = new JLabel("Κρεβάτια Καταλύματος :");
        JCheckBox wifi = new JCheckBox("Wifi");
        JCheckBox parking = new JCheckBox("Parking");
        JCheckBox breakfast = new JCheckBox("Πρωινό");
        JCheckBox kitchen = new JCheckBox("Κουζίνα");
        JCheckBox ac = new JCheckBox("Air Conditioner");
        JLabel labelstart = new JLabel("Έναρξη Διαθεσιμότητας :");
        JLabel labelend = new JLabel("Τέλος Διαθεσιμότητας :");

        JTextField name = new JTextField("");
        JTextField location = new JTextField("");
        JTextField price = new JTextField("");
        JTextField area = new JTextField("");
        JTextField beds = new JTextField("");
        //JTextField start = new JTextField("");
        //JTextField end = new JTextField("");
        DatePicker start, end;
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setAllowKeyboardEditing(false);
        dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateSettings.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");

        DatePickerSettings dateSettings1 = new DatePickerSettings();
        dateSettings1.setAllowEmptyDates(false);
        dateSettings1.setAllowKeyboardEditing(false);
        dateSettings1.setFormatForDatesCommonEra("yyyy-MM-dd");
        dateSettings1.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");
        start = new DatePicker(dateSettings);
        end = new DatePicker(dateSettings1);


        if(room==null) {
            type.setSelectedIndex(0);
        }else {
            type.setSelectedIndex(typemap.get(room.getType()));
            name.setText(room.getName());
            location.setText(room.getLocation());
            price.setText(Integer.toString(room.getPrice()));
            area.setText(Integer.toString(room.getArea()));
            beds.setText(Integer.toString(room.getBeds()));
            //start = new DatePicker(dateSettings);
            //end = new DatePicker(dateSettings1);
            start .setText(room.getStart().toString());
            end.setText(room.getEnd().toString());
            wifi.setSelected(room.hasWifi());
            parking.setSelected(room.hasParking());
            breakfast.setSelected(room.hasBreakfast());
            kitchen.setSelected(room.hasKitchen());
            ac.setSelected(room.hasAc());
        }

        JPanel temp = new JPanel();
        if(room==null)
            temp.setBorder(BorderFactory.createTitledBorder("Προσθήκη Καταλύματος"));
        else
            temp.setBorder(BorderFactory.createTitledBorder("Επεξεργασία Καταλύματος"));
        temp.setLayout(new GridLayout(0,2,2,2));

        temp.add(labeltype);
        temp.add(type);
        temp.add(labelname);
        temp.add(name);
        temp.add(labelLocation);
        temp.add(location);
        temp.add(labelprice);
        temp.add(price);
        temp.add(labelarea);
        temp.add(area);
        temp.add(labelbeds);
        temp.add(beds);
        temp.add(wifi);
        temp.add(parking);
        temp.add(breakfast);
        temp.add(kitchen);
        temp.add(ac);
        temp.add(new Component() {});
        temp.add(labelstart);
        temp.add(start);
        temp.add(labelend);
        temp.add(end);
        temp.add(new Component() {});

        //JPanel panel = new JPanel();
        JButton button = new JButton("Προσθήκη!");
        if (room!=null)
            button.setText("Επεξεργασία!");
        DatePicker finalStart = start;
        DatePicker finalEnd = end;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (room==null) {
                        boolean flag = provider.addRoom(new Room((String) type.getSelectedItem(), name.getText(), location.getText(), Integer.parseInt(price.getText()), Integer.parseInt(area.getText()), Integer.parseInt(beds.getText()), wifi.isSelected(), parking.isSelected(), breakfast.isSelected(), kitchen.isSelected(), ac.isSelected(), LocalDate.parse(finalStart.getText()), LocalDate.parse(finalEnd.getText()), provider.getUserID()));
                        if (flag) {
                            simpleMessageNoTitle("Το κατάλυμα Αποθηκεύτηκε!");
                            pCards.removeAll();
                            pCards.add(addApartment(null));
                            pCards.revalidate();
                        }else
                            JOptionPane.showMessageDialog(null, "Προσοχή!!!\nΛάθος στην εισαγωγή!\nΔεν επιτρέπεται κενό όνομα ή τοποθεσία\nΘετικά ακέραια νούμερα για τις τιμές,κρεβάτια,τ.μ.", "Λάθος στοιχεία", JOptionPane.ERROR_MESSAGE);
                    }else {
                        boolean flag = provider.editRoom(room,(String) type.getSelectedItem(), name.getText(), location.getText(), Integer.parseInt(price.getText()), Integer.parseInt(area.getText()), Integer.parseInt(beds.getText()), wifi.isSelected(), parking.isSelected(), breakfast.isSelected(), kitchen.isSelected(), ac.isSelected(),LocalDate.parse(finalStart.getText()), LocalDate.parse(finalEnd.getText()));
                        if (flag) {
                            simpleMessageNoTitle("Το κατάλυμα Επεξεργάστηκε!");
                            editRoom();
                        } else
                            JOptionPane.showMessageDialog(null, "Προσοχή!!!\nΛάθος στην επεξεργασία!\nΔεν επιτρέπεται κενό όνομα ή τοποθεσία\nΘετικά ακέραια νούμερα για τις τιμές,κρεβάτια,τ.μ.", "Λάθος στοιχεία", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (Exception ignored){
                    JOptionPane.showMessageDialog(null, "Προσοχή!!!\nΛάθος στην εισαγωγή!\nΑκέραια νούμερα για τις τιμές,κρεβάτια,τ.μ.\nΓια την εισαγωγή της ημερομηνίας πατήστε στις τρις τελείες.", "Λάθος στοιχεία", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //panel.add(button);
        temp.add(button);

        return temp;
    }

    /** Η μέθοδος ενημερώνει τη λίστα με όλα τα δωμάτια μετά τις αλλαγές που έκανε στα δωμάτια του ο provider
     * @param prov Ένας Provider για τα δωμάτια του οποίου γίνεται η ενημέρωση*/
    private void updateRooms(Provider prov){
        rooms.removeIf(temp -> temp.getProviderID().equals(prov.getUserID()));
        rooms.addAll(prov.getRooms());
    }

    private void simpleMessageNoTitle(String str) {
        JOptionPane.showMessageDialog(null,str);
    }

}
