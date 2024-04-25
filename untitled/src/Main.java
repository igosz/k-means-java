import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //Loading data from a file
        List<IrisData> dataList = FileLoader.loadData("untitled/res/iris_training 3.txt");


        //User enters desired number of clusters
        System.out.println("Enter the desired number of clusters: ");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();


        //Creating initial, random list of centroids
        List<IrisData> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            centroids.add(dataList.get((int)(Math.random() * dataList.size())));
        }

        //Creating HashMap with centroids to records assignments
        Map<IrisData, List<IrisData>> centroidToRecords = new HashMap<>();




        boolean isDone = false;

        while (!isDone) {

            double totalDistance = 0.0;

            //Clearing the cluster assignments
            centroidToRecords.clear();



            //Assigning each data point to the nearest centroid and adding distance between data and closest centroid to the total distance
            for (IrisData data : dataList) {
                IrisData closestCentroid = findClosestCentroid(data, centroids);
                centroidToRecords.computeIfAbsent(closestCentroid, k1 -> new ArrayList<>()).add(data);
                totalDistance += Math.pow(euclideanDistance(data, closestCentroid), 2);
            }


            //Printing sum of squared distances from centroids
            System.out.println(totalDistance);


            //Calculating new centroids
            List<IrisData> newCentroids = new ArrayList<>();
            for (IrisData centroid : centroidToRecords.keySet()) {
                List<IrisData> cluster = centroidToRecords.get(centroid);
                newCentroids.add(calculateCentroid(cluster));
            }


            //Checking if previous centroids are different from current and eventually ending the loop
            isDone = true;
            for (int i = 0; i < centroids.size(); i++) {
                if (!centroids.get(i).attributes.equals(newCentroids.get(i).attributes)) {
                    isDone = false;
                    break;
                }
            }

            //Assigning new centroids to the old variable
            centroids = newCentroids;
        }




        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //Displaying clusters with their entropy
        for (IrisData centroid : centroidToRecords.keySet()) {
            List<IrisData> cluster = centroidToRecords.get(centroid);

            System.out.println("///////////////////////////////");
            System.out.println("Cluster Centroid: " + centroid);
            System.out.println("Cluster Data:");
            System.out.println();
            for (IrisData data : cluster) {
                System.out.println(data);
            }
            double entropy = calculateEntropy(cluster);
            System.out.println("Cluster Entropy: " + entropy);
            System.out.println("///////////////////////////////");
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }












    public static IrisData findClosestCentroid(IrisData data, List<IrisData> centroids) {
        IrisData closestCentroid = centroids.get(0);
        double minDistance = euclideanDistance(data, closestCentroid);

        for (IrisData centroid : centroids) {
            double distance = euclideanDistance(data, centroid);
            if (distance < minDistance) {
                minDistance = distance;
                closestCentroid = centroid;
            }
        }
        return closestCentroid;
    }



    public static double euclideanDistance(IrisData id1, IrisData id2) {
        double sum = 0.0;
        for (int i = 0; i < id1.attributes.size(); i++) {
            double diff = id1.attributes.get(i) - id2.attributes.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }




    public static IrisData calculateCentroid(List<IrisData> cluster) {
        List<Double> newAttributes = new ArrayList<>();

        for (int i = 0; i < cluster.get(0).attributes.size(); i++) {
            double sum = 0.0;
            for (IrisData data : cluster) {
                sum += data.attributes.get(i);
            }
            newAttributes.add(sum / cluster.size());
        }

        return new IrisData(newAttributes);
    }




    public static double calculateEntropy(List<IrisData> cluster) {
        Map<String, Integer> classCounts = new HashMap<>();
        for (IrisData data : cluster) {
            String species = data.name;
            if(classCounts.containsKey(species)) {
                classCounts.put(species, classCounts.get(species) + 1);
            }
            else {
                classCounts.put(species, 1);
            }
        }

        double entropy = 0.0;
        int totalSize = cluster.size();

        for (String species : classCounts.keySet()) {
            double probability = (double) classCounts.get(species) / totalSize;
            entropy -= probability * Math.log(probability);
        }

        return entropy;
    }



}

