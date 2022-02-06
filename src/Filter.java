import java.time.LocalDate;
/**
 * Η κλάση αναπαριστά ενα φίλτρο, δηλ. δέχεται κάποια κριτήρια που χρησιμοποιούνται στις αναζητήσεις του πελάτη (Customer)
 */

public class Filter {
    private Integer area, beds, min, max;
    private Boolean wifi, parking, breakfast, kitchen, ac;
    private String type, name, location;
    private LocalDate start, end;

    public Filter(String location, LocalDate start, LocalDate end, Integer beds){
        area = null;
        kitchen = null;
        breakfast = null;
        parking = null;
        wifi = null;
        type = null;
        name = null;
        ac = null;
        min = null;
        max = null;
        this.beds = beds;
        this.start = start;
        this.end = end;
        this.location = location;
    }

    public Filter(String location, LocalDate start, LocalDate end, Integer beds, String type, Integer area, Integer min, Integer max, Boolean wifi, Boolean parking,
                  Boolean breakfast, Boolean kitchen, Boolean ac){

        this.beds = beds;
        this.start = start;
        this.end = end;
        this.location = location;
        this.area = area;
        this.kitchen = kitchen;
        this.breakfast = breakfast;
        this.parking = parking;
        this.wifi = wifi;
        this.type = type;
        this.ac = ac;
        this.max = max;
        this.min = min;
    }

    /**
     * Συγκρίνει τα κριτήρια του φίλτρου με τις αντίστοιχες τιμές κάποιου δωματίου
     * @param room κάποιο δωμάτιο,
     * @return ψευδής εαν δεν πληρεί τα κριτήρια, αληθής εαν το δωμάτιο συμβαδίζει με τα κριτήρια.
     */

    public boolean extVerify(Room room){
        return verify(room) && (area == 0 || area <= room.getArea()) &&
                (!kitchen || kitchen == room.hasKitchen()) && (!parking || parking == room.hasParking()) &&
                (!breakfast || breakfast == room.hasBreakfast()) && (!wifi || wifi == room.hasWifi()) &&
                (type == null || type.equals(room.getType())) && (!ac || ac == room.hasAc()) && (min == 0 || min <= room.getPrice()) &&
                (max == 0 || max >= room.getPrice());
    }

    public boolean verify(Room room){
        return room.isFree(start,end) && location.equals(room.getLocation()) && beds <= room.getBeds();
    }

    /**
     * Getters της κλάσης.
     */

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }


}
