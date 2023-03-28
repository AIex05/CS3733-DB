public class l1edges {
    String edgeID;
    String startNode;
    String endNode;

    static void DisplayEdge(l1edges Edge){
        System.out.println("ID: "+Edge.edgeID+"\s"+"startNode: "+Edge.startNode+"\s"+"endNode: "+Edge.endNode+"\n"+"----------------------------------------------");
    }

    public l1edges(String edgeID, String startNode, String endNode) {
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
