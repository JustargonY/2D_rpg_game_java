package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class Pathfinder {

    GamePanel gp;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp) {
        this.gp = gp;

        createNodes();
    }

    public void createNodes() {

        nodes = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            nodes[col][row] = new Node(col, row);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }

    }

    public void resetNodes() {

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow){

            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;

    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){

        resetNodes();

        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            int tileNum = gp.tileManager.mapTileNum[col][row];
            if (gp.tileManager.tiles[tileNum].collision) {
                nodes[col][row].solid = true;
            }

            getCost(nodes[col][row]);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }

    }

    public void getCost(Node node){

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;

    }

    public boolean search() {

        while (!goalReached && step < 500) {

            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }
            if (row + 1 < gp.maxWorldRow){
                openNode(nodes[col][row + 1]);
            }
            if (col + 1 < gp.maxWorldCol){
                openNode(nodes[col + 1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++){

                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodeFCost){
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }

            }

            if (openList.size() == 0){
                break;
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePass();
            }
            step++;

        }

        return goalReached;

    }

    public void openNode(Node node){

        if(!node.open && !node.checked && !node.solid){

            node.open = true;
            node.parent = currentNode;
            openList.add(node);

        }

    }

    public void trackThePass() {

        Node current = goalNode;

        while (current != startNode) {

            pathList.add(0, current);
            current = current.parent;

        }

    }

}
