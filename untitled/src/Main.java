import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {


        List<IrisData> dataList = FileLoader.loadData("res/iris_test 3.txt");
        List<IrisData> centroids = new ArrayList<>();


        System.out.println("Enter the desired number of clusters: ");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();




        for (int i = 0; i < k; i++) {
            centroids.add(dataList.get((int)(Math.random() * dataList.size())));
        }

        Map<IrisData, List<IrisData>> centroidToRecords = new HashMap<>();

        for (int i = 0; i < centroids.size(); i++) {
            centroidToRecords.put(centroids.get(i), new ArrayList<>());
        }





        boolean isDone = false;
        int x = 0;

        while (x<300) {

            double totalDistance = 0.0;
            isDone = true;


            //k-means algorithm
            for (int i = 0; i < dataList.size(); i++) {

                double closestDistance = euclideanDistance(dataList.get(i), centroids.get(0));
                IrisData closestCluster = centroids.get(0);

                for (int j = 0; j < centroids.size(); j++) {
                    double currDistance = euclideanDistance(dataList.get(i), centroids.get(j));
                    if (currDistance < closestDistance) {
                        closestDistance = currDistance;
                        closestCluster = centroids.get(j);
                        isDone = false;
                    }
                }
                centroidToRecords.get(closestCluster).add(dataList.get(i));
                totalDistance += Math.pow(closestDistance, 2);
            }

            System.out.println("Suma kwadratów odległości od centroidów: " + totalDistance);


            //calculating new centroids
            List<IrisData> newCentroids = new ArrayList<>();

            for (IrisData centroid : centroidToRecords.keySet()) {
                List<IrisData> cluster = centroidToRecords.get(centroid);
                IrisData newCentroid = calculateCentroid(cluster);
                newCentroids.add(newCentroid);
            }


            centroids = newCentroids;
            centroidToRecords.clear();
            for (int i = 0; i < centroids.size(); i++) {
                centroidToRecords.put(centroids.get(i), new ArrayList<>());
            }
            x++;
        }
        for (IrisData centroid : centroidToRecords.keySet()) {
            List<IrisData> cluster = centroidToRecords.get(centroid);
            System.out.println("Grupa oznakowana centroidem: "+centroid);
            System.out.println(cluster);
        }


    }

    public static double euclideanDistance(IrisData id1, IrisData id2) {
        double sum = 0.0;
        for (int i = 0; i < id1.attributes.size(); i++) {
            double diff = id1.attributes.get(i) - id2.attributes.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }


    public static IrisData calculateCentroid(List<IrisData> cluster){
        List<Double> newAttributes = new ArrayList<>();

        for(int i = 0; i<cluster.get(0).attributes.size(); i++){
            double sum = 0.0;
            for(int j = 0; j<cluster.size(); j++){
                sum += cluster.get(j).attributes.get(i);
            }
            newAttributes.add(sum/cluster.size());
        }

        return new IrisData(newAttributes);
    }
}
