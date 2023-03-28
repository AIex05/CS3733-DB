public class l1nodes {
    String nodeID;
    int xcoord;
    int ycoord;
    String floor;
    String building;
    String nodeType;
    String longName;
    String shortName;

    static void DisplayNode(l1nodes Node){
        System.out.println("ID: "+Node.nodeID+"\s"+"xcoord: "+Node.xcoord+"\s"+"ycoord: "+Node.ycoord+"\s"+"floor: "+Node.floor+"\s"+"building: "+Node.building+"\s"+"nodeType: "+Node.nodeType+"\s"+"longName: "+Node.longName+"\s"+"shortName: "+Node.shortName+"\n"+"--------------------------------------------------------------");
    }
    public l1nodes(String nodeID, int xcoord, int ycoord, String floor, String building, String nodeType, String longName, String shortName) {
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }
}
