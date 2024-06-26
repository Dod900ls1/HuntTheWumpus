import java.io.FileReader;
import java.io.IOException;

import javax.json.*;

public class DrawMap {
    private Renderer renderer;

    public DrawMap(Renderer renderer){
        this.renderer = renderer;
    }

    private void drawCaves(int playerLocation){
        try (JsonReader reader = Json.createReader(new FileReader("NodeGenerator/dodecahedronCorrected.json"))) {
            JsonObject jsonRoot = reader.readObject();
            JsonArray nodesArray = jsonRoot.getJsonArray("nodes");



            // Iterate over the nodes array
            for (JsonValue nodeValue : nodesArray) {
                JsonObject nodeObject = (JsonObject) nodeValue;

                // Access individual properties of each node
                int id = nodeObject.getInt("id");
                double x = nodeObject.getJsonNumber("x").doubleValue();
                double y = nodeObject.getJsonNumber("y").doubleValue();
                renderer.setPlayerLocation(playerLocation);
                renderer.setCircleCoordinates(id, (int) x, (int) y);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void drawEdges(){
        try (JsonReader reader = Json.createReader(new FileReader("NodeGenerator/dodecahedronCorrected.json"))) {
            JsonObject jsonRoot2 = reader.readObject();
            JsonArray nodesArray2 = jsonRoot2.getJsonArray("links");
            
            int index = 1; 
            for (JsonValue jsonValue : nodesArray2) {
                JsonObject nodeObject = (JsonObject) jsonValue;

                int id1 = nodeObject.getInt("source");
                int id2 = nodeObject.getInt("target");

                renderer.setLineIndexes(index, id1, id2);
                index++;
            }
        }catch(IOException e){
            System.out.println("IOException" + e.getMessage());
        }
    }

    public void drawMap(int playerLocation){
        drawEdges();
        drawCaves(playerLocation);
    }

    public void resetMap() {
        renderer.clearCoordinates();
        renderer.clearLineIndexes();
    }

}
