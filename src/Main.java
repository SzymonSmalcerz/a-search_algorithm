import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
	
	
	public static void main(String[] args) {
		
		//initializing things
		
		int height = 20;
		int width = 50;
		
		int[][] map = new int[width][height];
		Node[][] mapOfNodes = new Node[width][height];
		
		int destinationX = 3;
		int destinationY = 3;
		int startX = 25;
		int startY = 15;
		
		for(int w = 0; w<width;w++) {
			for(int h=0; h<height; h++) {
				map[w][h] = 0;//0 mean that there is no obstacle
				mapOfNodes[w][h] = new Node(w,h);
				mapOfNodes[w][h].setH(Math.abs(h-destinationY + w - destinationX));
			}
		}
		
		for(int w = 5; w<7;w++) {
			for(int h=2; h<20; h++) {
				map[w][h] = 1;//0 mean that there is no obstacle, 1 == obstacle
			}
		}
		
		for(int w = 5; w<37;w++) {
			for(int h=2; h<3; h++) {
				map[w][h] = 1;//0 mean that there is no obstacle, 1 == obstacle
			}
		}
		
		for(int w = 21; w<50;w++) {
			for(int h=10; h<12; h++) {
				map[w][h] = 1;//0 mean that there is no obstacle, 1 == obstacle
			}
		}
		
		System.out.println("\n\n\n\nMAP BEFORE SEARCH : ");
		System.out.println("\n 1 == obstacle (can't move), 0 == free space (can move) : \n");
		printMap(map,width,height);
		System.out.println("\n\n\n\n");
		
		
		
		//our destination point
		Node goal = mapOfNodes[destinationX][destinationY];
		
		//our source point -> initializing its g and f 
		mapOfNodes[startX][startY].setG(0);
		mapOfNodes[startX][startY].setF(mapOfNodes[startX][startY].getH());
		
		/* 
		 *  A* Search Algorithm
		1.  Initialize the open list
		2.  Initialize the closed list
		    put the starting node on the open 
		    list (you can leave its f at zero)
		 */
		
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closedList = new ArrayList<>();
		openList.add(mapOfNodes[startX][startY]);
		
		
		//3.  while the open list is not empty
		while(!openList.isEmpty()) {
			
//			  a) find the node with the least f on 
//	       	  the open list, call it "q"
			Node q = Main.findNodeWithLowestF(openList);
			
			
//			 b) pop q off the open list
			openList.remove(q);
			
			
//			 c) generate q's 4 successors and set their 
//		       parents to q
//		   
//			UP,LEFT,RIGHT,DOWN -> 4 succesors
//			
//					UP
//					|
//					|
//			LEFT -- q -- RIGHT
//					|
//					|
//				  DOWN
			
			
//			 d) for each successor
			for(int w=q.getxPosition()-1;w<=q.getxPosition()+1;w++) {
				for(int h=q.getyPosition()-1;h<=q.getyPosition()+1;h++) {
					
					
//					i) if successor is the goal, stop search
//		          successor.g = q.g + distance between 
//		                              successor and q
//		          successor.h = distance from goal to 
//		          successor (This can be done using many 
//		          ways, we will discuss three heuristics- 
//		          Manhattan, Diagonal and Euclidean 
//		          Heuristics)
//		          
//		          successor.f = successor.g + successor.h
//
//		          ii) if a node with the same position as 
//		            successor is in the OPEN list which has a 
//		           lower f than successor, skip this successor
//
//		          iii) if a node with the same position as 
//		            successor  is in the CLOSED list which has
//		            a lower f than successor, skip this successor
//		            otherwise, add  the node to the open list
					
//					
					if(w==(q.getxPosition()+1) && (h==(q.getyPosition()+1) || h==(q.getyPosition()-1))) {
						continue;
					}
					if(w==(q.getxPosition()-1) && (h==(q.getyPosition()+1) || h==(q.getyPosition()-1))) {
						continue;
					}
					if(w>=0 && w<width && h>=0 && h<height && !(h==q.getyPosition() && w==q.getxPosition())) {
						if(map[w][h] == 1) {
							continue;
						}
						
						
						
						
						int calculatedG = 1 + q.getG();
						
						if(openList.contains(mapOfNodes[w][h]) || closedList.contains(mapOfNodes[w][h])) {
							
							
							if(!(mapOfNodes[w][h].getF() > 0 && mapOfNodes[w][h].getF() < calculatedG + mapOfNodes[w][h].getH())) {
							
								mapOfNodes[w][h].setF(calculatedG + mapOfNodes[w][h].getH()); 
								mapOfNodes[w][h].setG(calculatedG);
								mapOfNodes[w][h].setParent(q);
								map[w][h] = 6;
							}
							
							continue;
							
						}
						
						
							map[w][h] = 6;
							mapOfNodes[w][h].setF(calculatedG + mapOfNodes[w][h].getH()); 
							mapOfNodes[w][h].setG(calculatedG);
							mapOfNodes[w][h].setParent(q);
							if(goal.equals(mapOfNodes[w][h])) {
								System.out.println("	FOUND WAY!:\n");
								System.out.println("number of moves from source to destination point : " + goal.getG());
								
								
								//filling our path in map with '7'
								while(goal !=null ) {
									
									map[goal.getxPosition()][goal.getyPosition()] = 7;
									goal = goal.getParent();
								}
								
								
								map[destinationX][destinationY] = 5;
								map[startX][startY] = 4;
								System.out.println("MAP AFTER SEARCH : ");

								System.out.println("\n 1 == obstacle (can't move), 0 == free space (can move), 7 == our way, ");
								System.out.println(" 6 == we took this point into consideration,");
								System.out.println(" 4 == our source point, 5 == destination point \n");
								printMap(map,width,height);
								System.out.println("\n\n\n\n");
								return;
							}
							
							openList.add(mapOfNodes[w][h]);
						
					}
					
				}
			}
//			end (for loop)
			
			
//			 e) push q on the closed list
			closedList.add(q);
			//printList(closedList);
		}
//		end (while loop) -> way not found, in normal function you could return -1 or false (whatever)
		
		System.out.println("way not found");
		
		
		
	}
	
	@SuppressWarnings("unused")
	private static void printList(List<Node> list) {
		
		Iterator<Node> iter = list.iterator();
		
		System.out.println("/n/nCLOSED LIST:");
		while(iter.hasNext()) {
			Node temp = iter.next();
			
			System.out.println(temp.getxPosition() + " : " + temp.getyPosition());
		}
		
		
	}
	
	
	private static void printMap(int[][] map, int width, int height) {
		
		
			for(int h=0; h<height; h++) {
				for(int w = 0; w<width;w++) {
				System.out.print(map[w][h]);
				}
				System.out.println("");
			}
			
		
		
	}

	private static Node findNodeWithLowestF(ArrayList<Node> openList) {
		
		Iterator<Node> iter = openList.iterator();
		
		Node lowestFNode = openList.get(0);
		int lowestF = openList.get(0).getF();
		
		while(iter.hasNext()) {
			Node temp = iter.next();
			
			if(temp.getF() < lowestF) {
				lowestF = temp.getF();
				lowestFNode = temp;
			}
		}
		
		return lowestFNode;
	}
}
