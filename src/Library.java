import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.jar.Attributes.Name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


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

public class Library {
  
	private static String DATABASE = "LibraryFinal.db";
    static BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
    static Connection conn;
    static HashMap artist = new HashMap<String, artist>();
    static HashMap track = new HashMap<String, track>();
    // static HashMap movie = new HashMap<String>(Arrays.asList("Star Wars", "Harry Potter", "Cloudy With A Chance of Meatballs", "Mamma Mia", "Die Hard"));
    static HashMap movieOrder = new HashMap<String, movieOrder>();

    public static void main(String[] args) throws IOException, SQLException {
    	conn= initializeDB(DATABASE);
        int ret = 0;
        while ((ret = printMain()) != 0) {
            if (ret == 2) {
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    /**
     * Connects to the database if it exists, creates it if it does not, and
     * returns the connection object.
     *
     * @param databaseFileName
     *            the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
        /**
         * The "Connection String" or "Connection URL".
         *
         * "jdbc:sqlite:" is the "subprotocol". (If this were a SQL Server
         * database it would be "jdbc:sqlserver:".)
         */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                // Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out
                        .println("The driver name is " + meta.getDriverName());
                System.out.println(
                        "The connection to the database was successful.");
            } else {
                // Provides some feedback in case the connection failed but did not throw an exception.
                System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out
                    .println("There was a problem connecting to the database.");
        }
        return conn;
    }

    /**
     * Queries the database and prints the results.
     *
     * @param conn
     *            a connection object
     * @param sql
     *            a SQL statement that returns rows This query is written with
     *            the Statement class, tipically used for static SQL SELECT
     *            statements
     */
    public static void sqlQuery( String sql) {
//    	StringBuilder sb= new StringBuilder();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String value = rsmd.getColumnName(i);
                System.out.print(value);
//                sb.append(value);
                if (i < columnCount) {
                    System.out.print(",  ");
//                	sb.append(",  ");
                }
            }
            System.out.print("\n");
//            sb.append("\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
//                    sb.append(columnValue);
                    if (i < columnCount) {
                        System.out.print(",  ");
//                    	sb.append(",  ");
                    }
                }
                System.out.print("\n");
//                sb.append("\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("TEST");
//        return sb.toString();
    }
    
    private static int printMain() throws IOException, SQLException {
        System.out.println("Enter:\nSearch\nAdd\nOrder\nEdit\nReports\nExit");
        
        String response = obj.readLine().toLowerCase();

        switch (response){
            case "search":
                search();
                return 1;
            case "add":
            	add();
                return 1;
            case "order":
            	order();
                return 1;
            case "edit":
            	edit();
                return 1;
            case "reports":
            	reports();
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
        String query;
        switch (response){
            case "artist":
                System.out.println("Enter artist name.");
                String artistName = obj.readLine();
                query= "SELECT * FROM Artist WHERE Artist.Name = \""+ artistName+ "\";";
                System.out.print("\n\n\n");
                sqlQuery(query);
                System.out.print("\n\n\n");
                break;
            case "track":
                System.out.println("Enter track name");
                String trackName = obj.readLine();
                query= "SELECT * FROM Track WHERE Track.Name = \""+ trackName+ "\";";
                System.out.print("\n\n\n");
                sqlQuery(query);
                System.out.print("\n\n\n");
                break;
        }
    }

    private static void add() throws IOException {
        System.out.println("Enter:\nArtist\nTrack");

        String response = obj.readLine().toLowerCase();
        String query;
        
        switch (response){
            case "artist":
                System.out.println("Add artist name.");
                String artistName = obj.readLine();
                query= "Insert INTO Artist (Name) VALUES (\""+ artistName+ "\");";
                sqlQuery(query);
                System.out.println("\n\n");//seperate by a few new lines for readability
                System.out.println(artistName + " Added!");
                System.out.println("\n\n");//seperate by a few new lines for readability
//                if (artist.containsKey(artistName)) {
//                    System.out.println("Artist already in database.");
//                } else {
//                    artist.put(artistName, new artist(artistName));
//                    System.out.println("Artist added.");
//                }
                break;
            case "track":
                System.out.println("Add track name");
                String trackName = obj.readLine();
                query= "SELECT * FROM Track WHERE Track.Name = \""+ trackName+ "\";";
			try {
				if (conn.createStatement().executeQuery(query).next()) {
					System.out.println("\n\n");//seperate by a few new lines for readability
                    System.out.println("Track already in database.");
                    System.out.println("\n\n");//seperate by a few new lines for readability
                } else {
                	System.out.println("Enter Album Product Number.");
                    String prodNum = obj.readLine();
                    System.out.println("Enter genre.");
                    String genre = obj.readLine();
                    System.out.println("Enter length.");
                    String length = obj.readLine();
                    System.out.println("Enter ArtistName.");
                    String aName = obj.readLine();
                    
                    query="Insert INTO Track (Name, AlbumProductNumber, Genre, Length, ArtistName) VALUES"+
                    "(\""+trackName+"\","+ prodNum+ ",\""+ 
                    		genre+"\",\""+ length+"\",\""+aName+"\");";
                    System.out.println(query);
                    sqlQuery(query);
                    System.out.println("\n\n");//seperate by a few new lines for readability
                    System.out.println("Track added.");
                    System.out.println("\n\n");//seperate by a few new lines for readability
                }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
        }
    }

    private static void order() throws IOException, SQLException {
        System.out.println("Enter:\nMovie\nActivate");

        String response = obj.readLine().toLowerCase();
        String query;
        switch (response){
            case "movie":
                //lets start by getting required media and movie info
            	System.out.println("Enter Movie Product Number");
            	String prodNum= obj.readLine();
            	System.out.println("Enter Quantity Ordered");
            	String qty= obj.readLine();
            	System.out.println("Enter Movie OrderNumber");
            	String orderNum= obj.readLine();
            	System.out.println("Enter Movie Price");
            	String price= obj.readLine();
            	System.out.println("Enter Movie Estimated Date of Arrival");
            	String EOD= obj.readLine();
            	String Status="Ordered";
            	//only ask for more basic info i the movie isnt already in the system
            	query="SELECT * FROM Media WHERE Media.ProductNumber = \""+ prodNum+ "\";";
            	if(!conn.createStatement().executeQuery(query).next()) {         		
            		//movie not already in system ask preliminary info
            		System.out.println("Enter Movie Name");
            		String movName= obj.readLine();
            		System.out.println("Enter Movie Year");
            		String year= obj.readLine();
            		System.out.println("Enter Movie Type (digital or physical)");
            		String type= obj.readLine();
            		String quantityIn="0";//if not in system none exist in store
            		//quantityordered willl be asked later
            		System.out.println("Enter Movie Content Rating");
            		String Rating= obj.readLine();
            		System.out.println("Enter Movie Length");
            		String Length= obj.readLine();
            		System.out.println("Enter Movie Genre");
            		String Genre= obj.readLine();
            		System.out.println("Enter Movie DirectorName");
            		String DirName= obj.readLine();
            		query= "INSERT INTO Media (ProductNumber, Year, Name, Type, Quantity, QuantityOrdered, "+
            				"Status) VALUES ("+
            				prodNum+", \""+year+"\"\", \""+movName+" \", \" "+type+"\", "+
            				quantityIn+", "+ qty+", \""+Status+"\");";
            		sqlQuery(query);
            		
            		query= "INSERT INTO Movie (ProductNumber, ContentRating, Length, Genre, DirectorName) VALUES("+
            				prodNum+", \""+Rating+"\", \""+Length+"\", \""+Genre+"\", \""+DirName+"\");";
            		sqlQuery(query);
            	}else {
            		query= "UPDATE Media SET QuantityOrdered="+qty+",Status=\""+Status+"\" WHERE Media.ProductNumber="+prodNum;
            		sqlQuery(query);
            	}
            	query= "INSERT INTO Orders (OrderNumber,Price, EstimatedDateOfArrival, ProductNumber) VALUES("+
            			orderNum+", \""+price+"\", \""+EOD+"\", "+ prodNum+");";
            	sqlQuery(query);
            	System.out.println("\n\n");//seperate by a few new lines for readability
            	System.out.println("Order Entered into Database!");
            	System.out.println("\n\n");//seperate by a few new lines for readability
                break;
            case "activate":
            	System.out.println("Enter OrderNumber");
            	String ordNum= obj.readLine();
            	query="SELECT * FROM Orders WHERE Orders.OrderNumber = \""+ ordNum+ "\";";
            	if((conn.createStatement().executeQuery(query).next())) {  
            		query="SELECT ProductNumber FROM Orders WHERE Orders.OrderNumber = \""+ ordNum+ "\";";
            		String prodNumber=conn.createStatement().executeQuery(query).getString(1);
            		query= "UPDATE Media SET QuantityOrdered=0, Status= \"Not Ordered\" WHERE Media.ProductNumber="+prodNumber;
            		sqlQuery(query);
            		query= "DELETE FROM Orders WHERE Orders.OrderNumber="+ordNum;
            		sqlQuery(query);
            		System.out.println("\n\n");//seperate by a few new lines for readability
            		System.out.println("Order Activated!");
            		System.out.println("\n\n");//seperate by a few new lines for readability
                    break;
            	}else {
            		System.out.println("\n\n");//seperate by a few new lines for readability
            		System.out.println("Order Does Not Exist");
            		System.out.println("\n\n");//seperate by a few new lines for readability
            	}
            	
        }
    }

    private static void edit() throws IOException, SQLException {
        System.out.println("Give artist name to edit.");
        String artistName = obj.readLine();
        String query;
        if (conn.createStatement().executeQuery(
    			"SELECT * FROM Artist WHERE Artist.Name = \""+ artistName+ "\";").next()) {
            System.out.println(artistName + " found. What field would you like to edit?\n-Name");
            String field = obj.readLine();
            System.out.println("What would you like to change "+ field+" to?");
            String newField = obj.readLine();
            query= "UPDATE Artist SET " +field+"= \'"+newField+ "\' WHERE Artist.Name=\'"+artistName+"\'";
            sqlQuery(query);
            System.out.println("\n\n");//seperate by a few new lines for readability
            System.out.println("Artist Name Changed");//seperate by a few new lines for readability
            System.out.println("\n\n");//seperate by a few new lines for readability
        } else {
        	System.out.println("\n\n");//seperate by a few new lines for readability
            System.out.println("No artist found.");
            System.out.println("\n\n");//seperate by a few new lines for readability
        }
    }

    private static void reports() throws NumberFormatException, IOException {
        System.out.println("1 - Tracks by artists before year.");
        System.out.println("2 - Albums checked out by patron.");
        System.out.println("3 - Most popular actor.");
        System.out.println("4 - Most listened to artist.");
        System.out.println("5 - Patron who has checked out the most videos.");

        int i= Integer.parseInt(obj.readLine());
        String query="";
        switch (i) {
        case 1:
        	System.out.println("Enter Artist in question");
        	String Artist= obj.readLine();
        	System.out.println("Enter Year in question");
        	String Year= obj.readLine();
        	query="SELECT Track.Name"+
        	" FROM TRACK, MEDIA, ARTIST"+
        	" WHERE Artist.name = Track.artistName"+
        	" AND Media.ProductNumber = Track.AlbumProductNumber"+
        	" AND Artist.name = \'"+ Artist+"\' "+
        	" AND Media.year < \'"+Year+"\';";
    		break;
    	case 2:
    		query="SELECT COUNT(*)"+
    		" FROM Media, Album, ChecksOut, Patron"+
    		" WHERE Album.ProductNumber = Media.ProductNumber"+
    		" AND ChecksOut.ProductNumber = Media.ProductNumber"+
    		" AND ChecksOut.LibraryCardNumber = Patron.LibraryCardNumber";
    		break;
    	case 3:
    		query="SELECT Actor.Name"+
    				" FROM (SELECT COUNT(*) as numOut, Media.ProductNumber as productNum"+
    						" FROM Media, Movie, ChecksOut"+
    						" WHERE Movie.ProductNumber= Media.ProductNumber"+
    						" AND Media.ProductNumber= ChecksOut.ProductNumber) as NumberCheckedOut, Actor, StarsIn"+
    					" WHERE NumberCheckedOut.productNum = StarsIn.ProductNumber"+
    					" AND Actor.Name=StarsIn.Name"+
    					" GROUP BY Actor.Name"+
    					" ORDER BY NumberCheckedOut.numOut"+
    					" LIMIT 1";
    		break;
    	case 4:
    		query="Select Artist.Name"+
    				" From (SELECT COUNT(*)*Album.Length as timeListened,Media.ProductNumber as ProductNum"+
    						" FROM Media, Album, ChecksOut"+
    						" WHERE Album.ProductNumber= Media.ProductNumber"+
    						" AND Media.ProductNumber= ChecksOut.ProductNumber) as AlbumCheckedOut, Artist"+
    					" Order By timeListened"+
    					" Limit 1";
    		break;
    	case 5:
    		query="SELECT MOVIECOUNT.name, MAX(MOVIECOUNT.count)"  +
    				" FROM (SELECT COUNT(*) AS count, Patron.Name AS name" +
    					" FROM Media, Movie, ChecksOut, Patron" +
    					" WHERE Movie.ProductNumber = Media.ProductNumber" +
    					" AND ChecksOut.ProductNumber = Media.ProductNumber" +
    					" AND ChecksOut.LibraryCardNumber = Patron.LibraryCardNumber)" +
    				" AS MOVIECOUNT";
    		break;
    	default:
    		System.out.println("\n\n");//seperate by a few new lines for readability
    		System.err.println("INVALID INPUT");
    		break;
        }
        if(!query.equals("")) {
        	System.out.println("\n\n");//seperate by a few new lines for readability
        	//run query
    		sqlQuery(query);
    		System.out.println("\n\n");//seperate by a few new lines for readability
        }
    }
}
