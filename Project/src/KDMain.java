import java.util.ArrayList;
import java.util.Scanner;

public class KDMain {
	
	public static void main(String[] args) {
		ArrayList<CityKD> cities = new ArrayList<CityKD>();
		Scanner input = new Scanner(System.in);
		int numberOfCities = input.nextInt();
		
		for(int j = 0; j < numberOfCities; j++) {
			String cityName = input.next().trim();
			String state = input.next().trim();
			double x = input.nextDouble();
			double y = input.nextDouble();
			cities.add(new CityKD(cityName, state, x, y));
			
		}
		
		input.close();
		
		KDTree tree = new KDTree(cities.get(0));
		tree.head.setDepthInTree(0);
		long avgInsertTime = 0;
		for(int i = 1; i < cities.size(); i++) {
			//begins with 1 for depth because the tree already has a head
			long insertStart = System.nanoTime();
			tree.insertNode(cities.get(i), 1);
			long insertFinish = System.nanoTime() - insertStart;
			avgInsertTime = avgInsertTime + insertFinish;
		}
		avgInsertTime = avgInsertTime/numberOfCities;
		//Should be true
		long startTime = System.nanoTime();
		System.out.println(tree.contains(-87.1974, 41.1987, 1));
		long endTime = System.nanoTime() - startTime; //Calculates the time of searching
		System.out.println("Search time (Nanoseconds): " + endTime + ", Number of cities: " + numberOfCities);
		//Should be false
		startTime = System.nanoTime();
		System.out.println(tree.contains(20.0, 51.0, 1));
		endTime = System.nanoTime() - startTime; //Calculates the time of searching
		System.out.println("Search time (Nanoseconds): " + endTime + ", Number of cities: " + numberOfCities);
		System.out.println("Average Insert time (Nanoseconds): " + avgInsertTime);

}
}
class KDTree {
	CityKD head;
	KDTree leftSubtree;
	KDTree rightSubtree;
	
	public KDTree(CityKD h) {
		this.head = h;
		
	}
	
	public void insertNode(CityKD c, int d) {
		int depth = d;
		if(depth % 2 != 0) {
			if(c.getxCoord() < this.getHead().getxCoord()) {
				if(this.getHead().getLeftChild() == null) {
					this.getHead().setLeftChild(c);
					this.setLeftSubtree(new KDTree(c));
				}
				else {
					leftSubtree.insertNode(c, depth + 1);
					
				}
			}
			
			else {
				if(this.getHead().getRightChild() == null) {
					this.getHead().setRightChild(c);
					this.setRightSubtree(new KDTree(c));
				}
				else {
					rightSubtree.insertNode(c, depth + 1);
				}
				
			}
		}
		else {
			if(c.getyCoord() < this.getHead().getyCoord()) {
				if(this.getHead().getLeftChild() == null) {
					this.getHead().setLeftChild(c);
					this.setLeftSubtree(new KDTree(c));
				}
				else {
					leftSubtree.insertNode(c, depth + 1);
				}
				
			}
			else {
				if(this.getHead().getRightChild() == null) {
					this.getHead().setRightChild(c);
					this.setRightSubtree(new KDTree(c));
				}
				else {
					rightSubtree.insertNode(c, depth + 1);
				}
				
			}
	}
		
	}
	
	public CityKD getHead() {
		return head;
	}

	public void setHead(CityKD head) {
		this.head = head;
	}

	public KDTree getLeftSubtree() {
		return leftSubtree;
	}

	public void setLeftSubtree(KDTree leftSubtree) {
		this.leftSubtree = leftSubtree;
	}

	public KDTree getRightSubtree() {
		return rightSubtree;
	}

	public void setRightSubtree(KDTree rightSubtree) {
		this.rightSubtree = rightSubtree;
	}

	public boolean contains(double x, double y, int d) {
		boolean contain = false;
		int depth = d;
		if(this.getHead() == null) {
			return contain;
		}
		//Checks to see if the coordinates of the current node are equal to given coordinates
		if(this.getHead().getxCoord() == x && this.getHead().getyCoord() == y) {
			contain = true;
			return contain;
		}
		else {
			if(depth % 2 != 0) {
				if(this.getHead().getxCoord() <= x) {
					if(this.getRightSubtree() != null) {
						contain = this.getRightSubtree().contains(x, y, depth + 1);
						return contain;
					}
					else {
						return contain;
					}
				}
				else {
					if(this.getLeftSubtree() != null) {
						contain = this.getLeftSubtree().contains(x, y, depth + 1);
						return contain;
					}
					else {
						return contain;
					}
					
				}
				
			}
			else {
				if(this.getHead().getyCoord() <= y) {
					if(this.getRightSubtree() != null) {
						contain = this.getRightSubtree().contains(x, y, depth + 1);
						return contain;
					}
					else {
						return contain;
					}
				}
				else {
					if(this.getLeftSubtree() != null) {
						contain = this.getLeftSubtree().contains(x, y, depth + 1);
						return contain;
					}
					else {
						return contain;
					}
					
				}
				
			}

		}
		
	}
	
	public void printInorder() {
		if(this.getHead() == null) {
			return;
		}
		
		if(this.getLeftSubtree() != null) {
		this.leftSubtree.printInorder();}
		System.out.println(this.head.toString());
		if(this.getRightSubtree() != null) {
		this.rightSubtree.printInorder();}
	}

	
}
