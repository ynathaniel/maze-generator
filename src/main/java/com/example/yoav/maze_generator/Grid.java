/*************************************************************
 * Name:  Yoav Nathaniel                                    *
 * Project:  Project 1 - Maze Generator                     *
 * Class:  CMPS 331 - Artificial Intelligence               *
 * Date:  February 12, 2015                                 *
 *************************************************************/

package com.example.yoav.maze_generator;

import java.util.Random;
import java.util.Stack;


public class Grid {
    public int dimens;
    public Cell cells[][];
    private Cell current;
    private Cell startCell;
    private Cell goalCell;
    private int steps;
    private Stack<Cell> cellStack = new Stack<Cell>();

    //sets up the Grid object.
    //fills up 2D array cells with cells, walls, and rooms. Each one gets its own set of
    // coordinates.
    //takes in an int for the dimensions of the grid.
    //Formula for the int is (cells*2)-1, it has to account for space for walls between cells
    //startCell and goalCell are determined.
    Grid(int nums) {
        dimens = nums;
        cells = new Cell[dimens][dimens];
        for (int i = 0; i < dimens; i++) {
            for (int o = 0; o < dimens; o++) {
                if ((o % 2 == 0) && (i % 2 == 0)) {
                    cells[i][o] = new Room();
                    cells[i][o].type = 1;
                } else if (((o % 2 != 0) && (i % 2 == 0)) || ((o % 2 == 0) && (i % 2 != 0))) {
                    cells[i][o] = new Wall();
                    cells[i][o].type = 2;
                } else {
                    cells[i][o] = new Cell();
                }
                cells[i][o].setCoords(i, o);
            }
        }
        int center = ((dimens - 1) / 2);
        if (center%2 == 1){
            center--;
        }
        current = cells[0][center];
        startCell = cells[0][center];
        goalCell = cells[dimens - 1][center];
        cellStack.push(current);
        steps = 0;
    }

    //Basically same as the contructor. Should the grid need to be reset.
    public void resetGrid() {
        cells = new Cell[dimens][dimens];
        for (int i = 0; i < dimens; i++) {
            for (int o = 0; o < dimens; o++) {
                if ((o % 2 == 0) && (i % 2 == 0)) {
                    cells[i][o] = new Room();
                    cells[i][o].type = 1;
                } else if (((o % 2 != 0) && (i % 2 == 0)) || ((o % 2 == 0) && (i % 2 != 0))) {
                    cells[i][o] = new Wall();
                    cells[i][o].type = 2;
                } else {
                    cells[i][o] = new Cell();
                }
                cells[i][o].setCoords(i, o);
            }
        }
        int center = ((dimens - 1) / 2);
        if (center%2 == 1){
            center--;
        }
        current = cells[0][center];
        startCell = cells[0][center];
        goalCell = cells[dimens - 1][center];
        cellStack.push(current);
        steps = 0;
    }

    //returns how many steps it took for the maze.
    public int getSteps() {
        return steps;
    }

    //returns startCell
    public Cell getStartCell() {
        return startCell;
    }

    //returns goalCell
    public Cell getGoalCell() {
        return goalCell;
    }

    //returns cellStack, a stack of cells
    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    //returns current cell.
    public Cell getCurrent() {
        return current;
    }

    //main method for building walls around a cell. It uses the current cell
    //and an int array sides (should be 4 ints).
    //sides[0] = left, sides[1] = up, sides[2] = right, sides[3] = down
    //this function calls both squareCheck() and priorityRandomBuild().
    //realWalls() provides an elementary way of checking where walls should be built.
    //it checks that the cell is not in the corner and it wont block the cell it came from.
    //int done starts at 4. It keeps track of the number of walls current cell can possibly build.
    private void realWalls(int[] sides) {
        int x = current.getxCoord();
        int y = current.getyCoord();
        int done = 4;
        int cameFrom = current.getBuiltFrom();
        //check edges and walls
        if (x != 0) {
            sides[0] = 1;
            if (cells[x - 1][y].checkWall()) {
                sides[0] = 0;
                done--;

            }
        } else {
            sides[0] = 0;
            done--;
        }
        if (x != dimens - 1) {
            sides[2] = 1;
            if (cells[x + 1][y].checkWall()) {
                sides[2] = 0;
                done--;
            }
        } else {
            sides[2] = 0;
            done--;
        }
        if (y != 0) {
            sides[1] = 1;
            if (cells[x][y - 1].checkWall()) {
                sides[1] = 0;
                done--;
            }
        } else {
            sides[1] = 0;
            done--;
        }
        if (y != dimens - 1) {
            sides[3] = 1;
            if (cells[x][y + 1].checkWall()) {
                sides[3] = 0;
                done--;
            }
        } else {
            sides[3] = 0;
            done--;
        }
        //check empty squares
        squareCheck(sides, cameFrom, x, y);
        //check cameFrom
        if (cameFrom == 1) {
            sides[0] = 0;
            done--;
        } else if (cameFrom == 2) {
            sides[1] = 0;
            done--;
        } else if (cameFrom == 3) {
            sides[2] = 0;
            done--;
        } else if (cameFrom == 4) {
            sides[3] = 0;
            done--;
        }
        //start building priority 3
        boolean built3 = false;
        boolean built2 = false;
        if (sides[0] == 3) {
            cells[x - 1][y].setWall(true);
            done--;
            built3 = true;
        }
        if (sides[1] == 3) {
            cells[x][y - 1].setWall(true);
            done--;
            built3 = true;
        }
        if (sides[2] == 3) {
            cells[x + 1][y].setWall(true);
            done--;
            built3 = true;
        }
        if (sides[3] == 3) {
            cells[x][y + 1].setWall(true);
            done--;
            built3 = true;
        }
        if (!built3) {
            //start building priority 2
            if (sides[0] == 2) {
                cells[x - 1][y].setWall(true);
                done--;
                built2 = true;
            }
            if (sides[1] == 2) {
                cells[x][y - 1].setWall(true);
                done--;
                built2 = true;
            }
            if (sides[2] == 2) {
                cells[x + 1][y].setWall(true);
                done--;
                built2 = true;
            }
            if (sides[3] == 2) {
                cells[x][y + 1].setWall(true);
                done--;
                built2 = true;
            }
        }
        if (done > 1) {
            priorityRandomBuild(sides, cameFrom, done, x, y);
        }
    }

    //takes an int array sides (should be 4 ints).
    //sides[0] = left, sides[1] = up, sides[2] = right, sides[3] = down
    //takes in cameFrom, direction of where the wall was built from.
    //0 is starting cell, 1 = left, 2 = up, 3 = right, 4 = down
    //takes ints x and y for coordinates of cell.
    //squareCheck() verifies there are no 4 cells forming a square because there are no walls
    // separating them.
    private void squareCheck(int[] sides, int cameFrom, int x, int y) {
        //top left
        if (sides[0] != 0 && sides[1] != 0) {
            if (cells[x - 2][y].getVisited()) {
                if (cells[x - 2][y - 2].getVisited() && !cells[x - 2][y - 1].checkWall()) {
                    if (cells[x][y - 2].getVisited() && !cells[x - 1][y - 2].checkWall()) {
                        if (cameFrom == 1) {
                            sides[1] += 1;
                        } else if (cameFrom == 2) {
                            sides[0] += 1;
                        }
                    }
                }
            }
        }
        //bottom left
        if (sides[0] != 0 && sides[3] != 0) {
            if (cells[x - 2][y].getVisited()) {
                if (cells[x - 2][y + 2].getVisited() && !cells[x - 2][y + 1].checkWall()) {
                    if (cells[x][y + 2].getVisited() && !cells[x - 1][y + 2].checkWall()) {
                        if (cameFrom == 1) {
                            sides[3] += 1;
                        } else if (cameFrom == 4) {
                            sides[0] += 1;
                        }
                    }
                }
            }
        }
        //top right
        if (sides[2] != 0 && sides[1] != 0) {
            if (cells[x + 2][y].getVisited()) {
                if (cells[x + 2][y - 2].getVisited() && !cells[x + 2][y - 1].checkWall()) {
                    if (cells[x][y - 2].getVisited() && !cells[x + 1][y - 2].checkWall()) {
                        if (cameFrom == 2) {
                            sides[2] += 1;
                        } else if (cameFrom == 3) {
                            sides[1] += 1;
                        }
                    }
                }
            }
        }
        //bottom right
        if (sides[2] != 0 && sides[3] != 0) {
            if (cells[x + 2][y].getVisited()) {
                if (cells[x + 2][y + 2].getVisited() && !cells[x + 2][y + 1].checkWall()) {
                    if (cells[x][y + 2].getVisited() && !cells[x + 1][y + 2].checkWall()) {
                        if (cameFrom == 4) {
                            sides[2] += 1;
                        } else if (cameFrom == 3) {
                            sides[3] += 1;
                        }
                    }
                }
            }
        }
    }

    //this method is responsible for randomly building walls, but keeping priority in mind.
    //It attempts to make as many turns as possible.
    //takes an int array sides (should be 4 ints).
    //takes in cameFrom, direction of where the wall was built from.
    //0 is starting cell, 1 = left, 2 = up, 3 = right, 4 = down
    //takes ints x and y for coordinates of cell.
    //takes int done for the possibilities left for building walls.
    private void priorityRandomBuild(int[] sides, int cameFrom, int done, int x, int y) {
        Random r = new Random();
        int priSides[] = new int[4];
        if (cameFrom == 1 || cameFrom == 3) {
            if (sides[1] == 0 && sides[3] == 1) {
                priSides[0] = 3;
                priSides[1] = 1;
            } else if (sides[1] == 1 && sides[3] == 0) {
                priSides[0] = 1;
                priSides[1] = 3;
            } else {
                int num = r.nextInt(2);
                if (num == 1) {
                    priSides[0] = 1;
                    priSides[1] = 3;
                } else {
                    priSides[0] = 3;
                    priSides[1] = 1;
                }
            }
            priSides[2] = 2;
            priSides[3] = 0;
        } else if (cameFrom == 2 || cameFrom == 4) {
            if (sides[0] == 0 && sides[2] == 1) {
                priSides[0] = 2;
                priSides[1] = 0;
            } else if (sides[0] == 1 && sides[2] == 0) {
                priSides[0] = 0;
                priSides[1] = 2;
            } else {
                int num = r.nextInt(2);
                if (num == 1) {
                    priSides[0] = 0;
                    priSides[1] = 2;
                } else {
                    priSides[0] = 2;
                    priSides[1] = 0;
                }
            }
            priSides[2] = 1;
            priSides[3] = 3;
        } else {
            priSides[0] = 0;
            priSides[1] = 1;
            priSides[2] = 2;
            priSides[3] = 3;
        }
        if (steps == 1) {
            done--;
        }
        for (int i = 0; i < 4; i++) {
            if (sides[priSides[i]] == 1) {
                int randomBuilder = r.nextInt(done);
                if (randomBuilder != 0) {
                    sides[priSides[i]] = 0;
                    switch (priSides[i]) {
                        case 0:
                            cells[x - 1][y].setWall(true);
                            break;
                        case 1:
                            cells[x][y - 1].setWall(true);
                            break;
                        case 2:
                            cells[x + 1][y].setWall(true);
                            break;
                        case 3:
                            cells[x][y + 1].setWall(true);
                            break;
                    }
                    done--;
                }
            }
        }
    }

    //responsible for building the maze on cell at a time.
    //increases count of steps each time.
    //pops a cell from cellStack, sends the cell to realWalls(), and randomly
    // pushes neighboring cells that have not been visited and don't have walls between them and
    // the current cell.
    public void explore() {
        if (cellStack.isEmpty()) {
            System.out.println("Warning: stack is empty.");
            return;
        }
        current = cellStack.peek();
        cellStack.pop();
        if (current.getVisited()) {
            explore();
            return;
        }
        steps++;
        //boolean limits[] = new boolean[4];
        int x = current.getxCoord();
        int y = current.getyCoord();

        boolean checkVisit = current.getVisited();
        if (checkVisit == true) {
            System.out.println("Warning: revisited (" + x + "," + y + ").");
            return;
        }

        current.setVisited(true);


        int sides[] = new int[4];
        realWalls(sides);

        boolean selected[] = new boolean[4];
        int selectedAll = 4;
        selected[0] = false;
        selected[1] = false;
        selected[2] = false;
        selected[3] = false;
        while (selectedAll > 0) {
            Random r = new Random();
            int goWith = r.nextInt(4);
            if (!selected[goWith]) {
                selected[goWith] = true;
                selectedAll--;
                switch (goWith) {
                    case 0:
                        if (sides[0] == 1) {
                            if (cells[x - 2][y].getBuiltFrom() == 0) {
                                cells[x - 2][y].setBuiltFrom(3);
                                cellStack.push(cells[x - 2][y]);
                            }
                        }
                        break;
                    case 1:
                        if (sides[1] == 1) {
                            if (cells[x][y - 2].getBuiltFrom() == 0) {
                                cells[x][y - 2].setBuiltFrom(4);
                                cellStack.push(cells[x][y - 2]);
                            }
                        }
                        break;
                    case 2:
                        if (sides[2] == 1) {
                            if (cells[x + 2][y].getBuiltFrom() == 0) {
                                cells[x + 2][y].setBuiltFrom(1);
                                cellStack.push(cells[x + 2][y]);
                            }
                        }
                        break;
                    case 3:
                        if (sides[3] == 1) {
                            if (cells[x][y + 2].getBuiltFrom() == 0) {
                                cells[x][y + 2].setBuiltFrom(2);
                                cellStack.push(cells[x][y + 2]);
                            }
                        }
                        break;
                }
            }
        }
    }
}
