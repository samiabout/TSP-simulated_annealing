import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

/**
 * Created by sami- on 23/09/2017.
 *
 *This is core TSP solver
 */
public class Graph {
    private String filePath;
    private Node[] nodes;
    private int graphOrder;
    private int nbNodes;
    private long duration;
    private boolean dispPath;
    private boolean dispCostChange;
    private int schedule;


    public int getNbNodes() {
        return nbNodes;
    }



    public long getDuration() {
        return duration;
    }



    public Graph(String filePath, boolean dispPath, boolean dispCostChange,int schedule){
        this.dispCostChange=dispCostChange;
        this.dispPath=dispPath;
        this.schedule=schedule;
        this.filePath=filePath;

        //*************parser**********************//
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphOrder=Integer.parseInt(lines.get(0));
        nodes=new Node[graphOrder];
        for (int i = 0; i < graphOrder; i++) {
            String[] node = lines.get(i+1).split(" ");
            nodes[i]=new Node(node[0],Integer.parseInt(node[1]),Integer.parseInt(node[2]));
        }


        //***********calls main solver*************//
        simulatedAnnealing(pathLength());

        if(dispPath){
            disp();
            System.out.println();
        }
    }



    private void simulatedAnnealing(double pathLength){
        double step=0.000001; //default schedule
        double temperature=100000;
        switch (schedule){  //chooses one of the 3 schedules
            case 1:
                step=0.0001;
                temperature=1000;
                break;
            case 2:
                step=0.00001;
                temperature=100000;
                break;
            case 3:
                step=0.000001;
                temperature=100000;
                break;
        }
        while (temperature>=step){
            switch (schedule){ //choose one of the 3 schedules
                case 1:
                    temperature-=step;
                    break;
                case 2:
                    temperature*= 1-step;
                    break;
                case 3:
                    temperature*= 1-step;
                    break;
                default:
                    temperature*= 1-step;
            }

            int[] swapNodes=swapRandom();       //go to random neighbour (swap 2 random nodes)
            double newPathLength=pathLength();
            if(newPathLength<pathLength || exp((pathLength - newPathLength) / temperature)>random()){ //Do we accept the change?
                pathLength=newPathLength;//we accept new changes
                if(dispCostChange){
                    System.out.println(pathLength);
                }
            }else {
                swap(swapNodes);//we do not accept new changes.
            }
        }
        System.out.println(pathLength());
    }

    private int[] swapRandom(){ //chooses 2 random nodes to swap and calls the swap method (goes to random neighbour)
        int[]swapNodes=new int[2];
        swapNodes[0]= ThreadLocalRandom.current().nextInt(0,graphOrder);
        swapNodes[1]=ThreadLocalRandom.current().nextInt(0,graphOrder);
        swap(swapNodes);
        return swapNodes;
    }
    private void swap(int[] swapNodes){//swaps 2 nodes in the path
        Node nodeT=new Node(nodes[swapNodes[0]]);
        nodes[swapNodes[0]]=new Node(nodes[swapNodes[1]]);
        nodes[swapNodes[1]]=new Node(nodeT);
    }
    private double pathLength(){ //calculates total cost of the path
        double pathLength=0;
        for (int i = 0; i < nodes.length-1; i++) {
            pathLength+=distance(nodes[i],nodes[i+1]);
        }
        pathLength+=distance(nodes[0],nodes[nodes.length-1]);
        return pathLength;
    }

    private double distance(Node node1, Node node2) {//calculates the euclidean distance between two nodes
        return sqrt(
                pow(node1.getX()-node2.getX(),2)
                        + pow(node1.getY()-node2.getY(),2)
        );
    }

    public void disp(){//display the path taken
        for (int i = 0; i < nodes.length; i++) {
            System.out.print(nodes[i].getName()+" ");
        }
        System.out.println();
    }



}

