package app.viewModel;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import app.model.Cell;
import app.view.*;

import java.io.*;
import java.util.*;

public class MapContainer extends Pane {
    @FXML
    MapComponent mapComponent;

    @FXML
    PlaneComponent planeComponent;

    @FXML
    XButton xButton;

    @FXML
    CellComponent cellComponent;

    double initX, initY;
    double distance;
    Client client;

    public MapContainer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/components/mapContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        planeComponent.planes = getPlanesImages();
        planeComponent.currentPlane = planeComponent.planes[0];
        xButton.img = getXImage();
    }

    public void setMapData(File fileCSV) throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(fileCSV);
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        double x = Double.parseDouble(records.get(0).get(0));
        double y = Double.parseDouble(records.get(0).get(1));
        double distance = Double.parseDouble(records.get(1).get(0));
        List<List<String>> lists = records.subList(2, records.size());
        double[][] coordinates = lists.stream()
                .map(strings -> strings.stream().mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);
        setMapData(coordinates, x, y, distance);
    }

    private static final String COMMA_DELIMITER = ",";

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
            return values;
        }
    }

    public void setMapData(double[][] coordinates, double x, double y, double distance) {
        this.initX = x;
        this.initY = y;
        this.distance = distance;
        double widthBlock = (mapComponent.getWidth()) / coordinates[0].length;
        double heightBlock = (mapComponent.getHeight()) / coordinates.length;
        mapComponent.setCoordinates(coordinates);
        planeComponent.setBlockSize(widthBlock, heightBlock);
        xButton.setBlockSize(widthBlock, heightBlock);
        cellComponent.setBlockSize(widthBlock, heightBlock);
    }

    public void redraw() {
        mapComponent.redraw();
    }

    public void showPoints(String movesSt) {
        cellComponent.showPoints(movesSt, (int) planeComponent.getPlaneX(), (int) planeComponent.getPlaneY());
        cellComponent.redraw();
        LinkedList<Cell> cells = cellComponent.getCells();
        for (int i = 1; i < cells.size(); i++) {
            double v = 90 - Math.toDegrees(
                    Math.atan2(cells.get(i).column - cells.get(i - 1).column, cells.get(i).row - cells.get(i - 1).row));
            if (v < 0)
                v += 360;
        }
    }

    public void movePlane(double posX, double posY) {
        planeComponent.setPlaneCoordinate(posX, posY);
        planeComponent.redraw();
    }

    public void markDestByMouse(double posX, double posY) {
        xButton.markDestByMouse(posX, posY);
        xButton.redraw();
    }

    public void connectToServer(final Client client) {
        this.client = client;
        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Double lat = client.getValue("/position/latitude-deg");
                Double lon = client.getValue("/position/longitude-deg");
                Double heading = client.getValue("/instrumentation/heading-indicator/indicated-heading-deg");
                double x = (lon - initX + distance) / distance;
                double y = (((lat - initY + distance) / distance));
                planeComponent.setHeading(heading);
                movePlane(x, y * -1);
            }
        }, 1000, 250);
    }

    public double[][] getCoordinates() {
        return mapComponent.getCoordinates();
    }

    private Image[] getPlanesImages() {
        Image[] planesImages = new Image[8];

        for (int i = 0; i < planesImages.length; i++) {
            planesImages[i] = getPlanesImage(String.valueOf(i * 45));
        }
        return planesImages;
    }

    private Image getPlanesImage(String imageName) {
        return new Image(getClass().getResourceAsStream("/assets/images/planes/plane-" + imageName + ".jpg"));
    }

    private Image getXImage() {
        return new Image(getClass().getResourceAsStream("/assets/images/x.jpg"));
    }
}
