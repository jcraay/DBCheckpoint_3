import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.jar.Attributes.Name;

class artist {
    String name;

    public artist(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}

class track {
    String name;
    String lyrics;
    String genre;
    String length;

    public track(String name, String lyrics, String genre, String length) {
        this.name = name;
        this.lyrics = lyrics;
        this.genre = genre;
        this.length = length;
    }

    public String toString() {
        return name + "\n" + lyrics + "\n" + genre + "\n" + length;
    }
}

// class movie {
//     String name;
//     int productNumber;
//     int year;
//     String type;
//     int quantity;
//     int quantityOrdered;
//     int libCardNum;
//     String status;
//     String contentRating;
//     String length;
//     String genre;
//     String directorName;
// }

class movieOrder {
    String name;
    int price;
    int copies;
    String dateOfArrival;

    public movieOrder(String name, int price, int copies, String dateOfArrival) {
        this.name = name;
        this.price = price;
        this.copies = copies;
        this.dateOfArrival = dateOfArrival;
    }
}

public class checkpoint3 {
  
    static BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));

    static HashMap artist = new HashMap<String, artist>();
    static HashMap track = new HashMap<String, track>();
    // static HashMap movie = new HashMap<String>(Arrays.asList("Star Wars", "Harry Potter", "Cloudy With A Chance of Meatballs", "Mamma Mia", "Die Hard"));
    static HashMap movieOrder = new HashMap<String, movieOrder>();

    public static void main(String[] args) throws IOException {
        int ret = 0;
        while ((ret = printMain()) != 0) {
            if (ret == 2) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }
    
    private static int printMain() throws IOException {
        System.out.println("Enter:\nSearch\nAdd\nOrder\nEdit\nReports\nExit");
        
        String response = obj.readLine().toLowerCase();

        switch (response){
            case "search":
                search();
                return 1;
            case "add":
                return 1;
            case "order":
                return 1;
            case "edit":
                return 1;
            case "reports":
                return 1;
            case "exit":
                return 0;
            default:
                return 2;
        }
    }

    private static void search() throws IOException {
        System.out.println("Enter:\nArtist\nTrack");

        String response = obj.readLine().toLowerCase();

        switch (response){
            case "artist":
                System.out.println("Enter artist name.");
                String artistName = obj.readLine().toLowerCase();
                if (artist.containsKey(artistName)) {
                    System.out.println("Found: " + artist.get(artistName).toString());
                } else {
                    System.out.println("Artist not found.");
                }
                break;
            case "track":
                System.out.println("Enter track name");
                String trackName = obj.readLine().toLowerCase();
                if (track.containsKey(trackName)) {
                    System.out.println("Found: " + track.get(trackName).toString());
                } else {
                    System.out.println("Track not found.");
                }
                break;
        }
    }

    private static void add() throws IOException {
        System.out.println("Enter:\nArtist\nTrack");

        String response = obj.readLine().toLowerCase();

        switch (response){
            case "artist":
                System.out.println("Add artist name.");
                String artistName = obj.readLine().toLowerCase();
                if (artist.containsKey(artistName)) {
                    System.out.println("Artist already in database.");
                } else {
                    artist.put(artistName, new artist(artistName));
                    System.out.println("Artist added.");
                }
                break;
            case "track":
                System.out.println("Add track name");
                String trackName = obj.readLine().toLowerCase();
                if (track.containsKey(trackName)) {
                    System.out.println("Track already in database.");
                } else {
                    System.out.println("Enter lyrics.");
                    String lyrics = obj.readLine();
                    System.out.println("Enter genre.");
                    String genre = obj.readLine();
                    System.out.println("Enter length.");
                    String length = obj.readLine();
                    
                    track.put(trackName, new track(trackName, lyrics, genre, length));
                    System.out.println("Track added.");
                }
                break;
        }
    }

    private static void order() throws IOException {
        System.out.println("Enter:\nOrder\nActivate");

        String response = obj.readLine().toLowerCase();

        switch (response){
            case "order":
                System.out.println("Enter Movie Name.");
                String movName = obj.readLine();
                System.out.println("Enter number of copies.");
                int copies = Integer.parseInt(obj.readLine());
                System.out.println("Enter price.");
                int price = Integer.parseInt(obj.readLine());
                System.out.println("Enter estimated date of arrival.");
                String estDate = obj.readLine();
                movieOrder.put(movName, new movieOrder(movName, price, copies, estDate));
                break;
            case "activate":
                // not yet implemented
                break;
        }
    }

    private static void edit() throws IOException {
        System.out.println("Give artist name to edit.");
        String artistName = obj.readLine().toLowerCase();

        if (artist.containsKey(artistName)) {
            System.out.println(artistName + " found. What field would you like to edit?\n-Name");
            String field = obj.readLine().toLowerCase();
            switch (field) {
                case "name":
                    System.out.println("What would you like to change the name to?");
                    String newName = obj.readLine();
                    artist old = (artist) artist.get(artistName);
                    artist.remove(artistName);
                    old.setName(newName);
                    artist.put(newName, old);
                    break;
                default:
                    System.out.println("Field not found.");
                    break;
            }
        } else {
            System.out.println("No artist found.");
        }
    }

    private static void reports() {
        System.out.println("Tracks by artists before year.");
        System.out.println("Albums checked out by patron.");
        System.out.println("Most popular actor.");
        System.out.println("Most listened to artist.");
        System.out.println("Patron who has checked out the most videos.");

        // response not yet implemented
    }
}
