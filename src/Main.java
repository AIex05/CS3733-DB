import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.stream.*;

public class Main {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASSWORD = "Tog3th3r!";
    static final String QUERYNODES = "SELECT nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName FROM l1nodes";
    static final String QUERYEDGES = "SELECT edgeID, startNode, endNode FROM l1edges";
    static final String[] COLUMNLIST = {"nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"};


    static void NodeEdgeInfo(Connection connection, String Case_1) {
        PreparedStatement ps = null;
        ResultSet result = null;
        Scanner ID_scan = new Scanner(System.in);
        System.out.println("Please input node/edge ID (if no, hit enter):");
        String ID = ID_scan.nextLine();
        l1nodes ResultNode = null;
        try {
            switch (Case_1) {
                case "1":
                    //1- Display all node information
                    ps = connection.prepareStatement("Select * from l1nodes");
                    result = ps.executeQuery();

                    while (result.next()) {
                        ResultNode = new l1nodes(result.getString("nodeID"), result.getInt("xcoord"), result.getInt("ycoord"), result.getString("floor"), result.getString("building"), result.getString("nodeType"), result.getString("longName"), result.getString("ShortName"));
                        l1nodes.DisplayNode(ResultNode);
                    }
                    break;
                case "2":
                    //2- Display all edge information
                    ps = connection.prepareStatement("Select * from l1edges");
                    result = ps.executeQuery();

                    while (result.next()) {
                        l1edges ResultEdge = new l1edges(result.getString("edgeID"), result.getString("startNode"), result.getString("endNode"));
                        l1edges.DisplayEdge(ResultEdge);
                    }
                    break;
                case "3":
                    //3- Display particular node information by id

                    ps = connection.prepareStatement("Select * from l1nodes where nodeID = ?");
                    ps.setString(1, ID);

                    result = ps.executeQuery();
                    while (result.next()) {
                        ResultNode = new l1nodes(result.getString("nodeID"), result.getInt("xcoord"), result.getInt("ycoord"), result.getString("floor"), result.getString("building"), result.getString("nodeType"), result.getString("longName"), result.getString("ShortName"));
                        l1nodes.DisplayNode(ResultNode);
                    }
                    break;
                case "4":
                    //4- Display particular edge information by id
                    ps = connection.prepareStatement("Select * from l1edges where edgeID = ?");
                    ps.setString(1, ID);

                    result = ps.executeQuery();
                    while (result.next()) {
                        l1edges ResultEdge = new l1edges(result.getString("edgeID"), result.getString("startNode"), result.getString("endNode"));
                        l1edges.DisplayEdge(ResultEdge);
                    }
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void UpdateNodeCoord(Connection connection) {

    }

    static void UpdateNodeLocName(Connection connection) {

    }

    static void ExportNode(Connection connection, String file_name) {
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(QUERYNODES);) {

            File outputFile = new File (file_name);
            PrintWriter printer = new PrintWriter(outputFile);
            ArrayList<String> aquireData = new ArrayList<String>();

            while(rs.next()){
                for(int i = 0; i < COLUMNLIST.length; i++){
                    aquireData.add(rs.getString(COLUMNLIST[i]));
                }
                String dataToExport = String.join(", ", aquireData);
                printer.write(dataToExport); //potentially wont work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void ImportNode(Connection connection, String file_name) {
        try (Statement stmt = connection.createStatement();) {
            Scanner nodeInput = new Scanner(new File(file_name));   //Scan from provided file
            while(nodeInput.hasNext()){
                String node_info = nodeInput.nextLine();
                stmt.executeUpdate(node_info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void DisplayHelp(Connection connection) {
        //?
    }

    static void ExitProgram(Connection connection) {
        //Close connection then exit
        try {
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("""
                        What do you want to do?
                        1- Display node and edge information\s
                        2- Update node coordinates\s
                        3- Update name of location node\s
                        4- Import from a CSV file into the node table\s
                        5- Display Help on how to use your database program\s
                        6- Export node table into a CSV file\s
                        7- Exit the program\s
                            """);

                Scanner input = new Scanner(System.in);
                String CaseNum = input.nextLine();

                switch (CaseNum) {
                    case "1":
                        //Display node and edge information
                        System.out.println("""
                                What do you want to do?
                                1- Display all node information\s
                                2- Display all edge information\s
                                3- Display particular node information by id\s
                                4- Display particular edge information by id\s
                                    """);
                        Scanner input_1 = new Scanner(System.in);
                        String Case_1 = input_1.nextLine();
                        NodeEdgeInfo(connection, Case_1);
                        break;
                    case "2":
                        //Update node coordinates
                        UpdateNodeCoord(connection);
                        break;
                    case "3":
                        //Update name of location node
                        UpdateNodeLocName(connection);
                        break;
                    case "4":
                        //Import from a CSV file into the node table
                        System.out.println("What is the directory of the file?");
                        Scanner inputFileDir = new Scanner(System.in);
                        String fileDir = inputFileDir.nextLine();
                        ImportNode(connection, fileDir);
                        break;
                    case "5":
                        //Display Help on how to use your database program
                        DisplayHelp(connection);
                        break;
                    case "6":
                        //Export node table into a CSV file
                        System.out.println("What do you want to name the file?");
                        Scanner inputFileName = new Scanner(System.in);
                        String fileName = inputFileName.nextLine();
                        ExportNode(connection, fileName);
                        break;
                    case "7":
                        //Exit the program
                        ExitProgram(connection);
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}