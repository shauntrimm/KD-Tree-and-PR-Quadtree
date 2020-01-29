import java.util.ArrayList;
import java.util.Scanner;

public class PRMain {

	public static void main(String[] args) {
		ArrayList<CityPR> cities = new ArrayList<CityPR>();
		Scanner input = new Scanner(System.in);
		int numberOfCities = input.nextInt();
		double maxX = -999999.99;
		double maxY = -999999.99;
		double minX = 999999.99;
		double minY = 999999.99;
		
		for(int j = 0; j < numberOfCities; j++) {
			String cityName = input.next().trim();
			String state = input.next().trim();
			double x = input.nextDouble();
			double y = input.nextDouble();
			cities.add(new CityPR(cityName, state, x, y));
			if(x > maxX) {
				maxX = x;
			}
			if(x < minX) {
				minX = x;
			}
			if(y > maxY) {
				maxY = y;
			}
			if(y < minY) {
				minY = y;
			}
			
		}
		long avgInsertTime = 0;
		PRTree tree = new PRTree(cities.get(0), maxX, maxY, minX, minY, 1.0);
		for(int i = 1; i < cities.size(); i++) {
			long insertStart = System.nanoTime();
			tree.insert(cities.get(i));
			long insertFinish = System.nanoTime() - insertStart;
			avgInsertTime = avgInsertTime + insertFinish;
		}
		
		avgInsertTime = avgInsertTime/numberOfCities;
		long startTime = System.nanoTime();
		System.out.println(tree.contains(1.0, 1.0));
		long endTime = System.nanoTime() - startTime; //Calculates the time of searching
		System.out.println("Search time (Nanoseconds): " + endTime + ", Number of cities: " + numberOfCities);
		startTime = System.nanoTime();
		System.out.println(tree.contains(-85.3393, 40.8275));
		endTime = System.nanoTime() - startTime; //Calculates the time of searching
		System.out.println("Search time (Nanoseconds): " + endTime + ", Number of cities: " + numberOfCities);
		System.out.println("Average Insert time (Nanoseconds): " + avgInsertTime);
		input.close();

	}

}

class PRTree {
	CityPR head;
	CityPR northWestChild;
	CityPR southWestChild;
	CityPR northEastChild;
	CityPR southEastChild;
	
	PRTree northWestTree;
	PRTree southWestTree;
	PRTree northEastTree;
	PRTree southEastTree;
	int children = 0;
	
	double level;
	
	double maxX;
	double maxY;
	double minX;
	double minY;
	
	double northWestMaxX;
	double northWestMinX;
	double northWestMaxY;
	double northWestMinY;
	
	double southWestMaxX;
	double southWestMinX;
	double southWestMaxY;
	double southWestMinY;
	
	double northEastMaxX;
	double northEastMinX;
	double northEastMaxY;
	double northEastMinY;
	
	double southEastMaxX;
	double southEastMinX;
	double southEastMaxY;
	double southEastMinY;
	
	public PRTree(CityPR h, double mxX, double mxY, double mnX, double mnY, double l) {
		this.head = h;
		this.children++;
		this.level = l; // the level of the tree currently at. Used to determine the size of quadrants
		
		this.maxX = mxX;
		this.maxY = mxY;
		this.minX = mnX;
		this.minY = mnY;
		
		
		//Initialize the dimensions for the potential quadrants that can be used, will be the
		//correct for all instances because the max and min, x and y values have to be given to 
		//construct a PRTree.
		
		//West quadrants will contain their maxX and their minX
		//East quadrants will contain only their minX
		
		//North quadrants will contain their maxY and minY
		//South quadrants will contain only their minY
		this.northWestMaxX = this.maxX;
		this.northWestMinX = this.maxX - (maxX/(2.0 * level));
		this.northWestMaxY = this.maxY;
		this.northWestMinY = this.maxY - (maxY/(2.0 * level));
		
		this.southWestMaxX = this.northWestMaxX;
		this.southWestMinX = this.northWestMinX;
		this.southWestMaxY = this.northWestMinY;
		this.southWestMinY = this.minY;
		
		this.northEastMaxX = this.northWestMinX;
		this.northEastMinX = this.minX;
		this.northEastMaxY = this.maxY;
		this.northEastMinY = this.northWestMinY;
		
		this.southEastMaxX = this.northEastMaxX;
		this.southEastMinX = this.northEastMinX;
		this.southEastMaxY = this.northEastMinY;
		this.southEastMinY = this.minY;
		
	}
	
	public void insert(CityPR c) {
		if(this.children == 1 && this.head != null) {
			this.split();
		}
		
		if(this.determineQuadrant(c) == 1) {
			if(northWestTree == null) {
				northWestTree = new PRTree(c, northWestMaxX, northWestMaxY,
						northWestMinX, northWestMinY, this.level + 1.0);
				this.children++;
			}
			else {
				northWestTree.insert(c);
			}
			
		}
		else if(this.determineQuadrant(c) == 2) {
			if(northEastTree == null) {
				northEastTree = new PRTree(c, northEastMaxX, northEastMaxY,
						northEastMinX, northEastMinY, this.level + 1.0);
				this.children++;
			}
			else {
				northEastTree.insert(c);
			}
			
		}
		else if(this.determineQuadrant(c) == 3) {
			if(southWestTree == null) {
				southWestTree = new PRTree(c, southWestMaxX, southWestMaxY,
						southWestMinX, southWestMinY, this.level + 1.0);
				this.children++;
			}
			else {
				southWestTree.insert(c);
			}
			
		}
		else if(this.determineQuadrant(c) == 4) {
			if(southEastTree == null) {
				southEastTree = new PRTree(c, southEastMaxX, southEastMaxY,
						southEastMinX, southEastMinY, this.level + 1.0);
				this.children++;
			}
			else {
				southEastTree.insert(c);
			}
			
		}
		else {
			System.out.println("Coordinate error Inserting");
			System.exit(0);
		}
		
		
	}
	
	//Splits the leaf node city into another PRTree
	public void split() {
		//System.out.println(this.head.cityName);
		/*System.out.println(this.level);
		System.out.println(this.maxX);
		System.out.println(this.minX);
		System.out.println(this.maxY);
		System.out.println(this.minY);*/
		//Head is already assumed to be non-null because to initialize a PRTree
		//You need to provide a head for the tree
		if(this.determineQuadrant(this.head) == 1) {
			this.northWestChild = this.head;
			this.northWestTree = new PRTree(this.northWestChild, this.northWestMaxX, this.northWestMaxY, 
					this.northWestMinX, this.northWestMinY, this.level + 1.0);
			this.head = null;
		}
		else if(this.determineQuadrant(this.head) == 2) {
			this.northEastChild = this.head;
			this.northEastTree = new PRTree(this.northEastChild, this.northEastMaxX, this.northEastMaxY,
					this.northEastMinX, this.northEastMinY, this.level + 1.0);
			this.head = null;
			
		}
		else if(this.determineQuadrant(this.head) == 3) {
			this.southWestChild = this.head;
			this.southWestTree = new PRTree(this.southWestChild, this.southWestMaxX, this.southWestMaxY,
					this.southWestMinY, this.southWestMinY, this.level + 1.0);
			this.head = null;
			
		}
		else if(this.determineQuadrant(this.head) == 4) {
			this.southEastChild = this.head;
			this.southEastTree = new PRTree(this.southEastChild, this.southEastMaxX, this.southEastMaxY,
					this.southEastMinX, this.southEastMinY, this.level + 1.0);
			this.head = null;
			
		}
		else {
			System.out.println("Coordinate error Splitting");
			System.exit(0);
		}
		
	}
	
	public int determineQuadrant(CityPR c) {
		//quad initialized as -1 (1 == northwest, 2 == northeast, 3 == southwest, 4 == southeast)
		int quad = -1;
		
		//Determines if city is in the west half
		if(c.xCoord <= northWestMaxX && c.xCoord >= northWestMinX) {
			
			//Determines if city is in the north half
			if(c.yCoord >= northWestMinY && c.yCoord <= northWestMaxY) {
				//Because the city is in the west (determined by outer if) and in the north
				//(determined by the inner if) the city is in the north-west quadrant
				quad = 1;
				return quad;
			}
			//Determines if city is in the south half
			else if(c.yCoord < northWestMinY && c.yCoord >= southWestMinY) {
				//Because the city is in the west (determined by outer if) and in the south
				//(determined by the inner if) the city is in the south-west quadrant
				quad = 3;
				return quad;
			}
			
		}
		//Determines if city is in the east half
		else if(c.xCoord < northWestMinX && c.xCoord >= northEastMinX) {
			//Determines if city is in the north half
			if(c.yCoord >= northEastMinY && c.yCoord <= northEastMaxY) {
				//Because the city is in the east (determined by outer if) and in the north
				//(determined by the inner if) the city is in the north-east quadrant
				quad = 2;
				return quad;
			}
			//Determines if city is in the south half
			else if(c.yCoord < northEastMinY && c.yCoord >= southEastMinY) {
				//Because the city is in the east (determined by outer if) and in the south
				//(determined by the inner if) the city is in the south-east quadrant
				quad = 4;
				return quad;
			}
			
		}
		
		//Returns -1 if the index is not in any of the quadrants
		return quad;
		
	}
	
	public boolean contains(double x, double y) {
		CityPR cHolder = new CityPR(null, null, x, y);
		boolean contain = false;
		if(this.head != null) {
			if(this.head.xCoord == x && this.head.yCoord == y) {
				contain = true;
				return contain;
			}
		}
		else {
			if(this.determineQuadrant(cHolder) == 1) {
				contain = this.northWestTree.contains(x, y);
				return contain;
				
			}
			else if(this.determineQuadrant(cHolder) == 2) {
				contain = this.northEastTree.contains(x, y);
				return contain;
			}
			else if(this.determineQuadrant(cHolder) == 3) {
				contain = this.southWestTree.contains(x, y);
				return contain;
			}
			else if(this.determineQuadrant(cHolder) == 4) {
				contain = this.southEastTree.contains(x, y);
				return contain;
			}
			else {
				return contain;
			}
		}
		return contain;
		
	}
	
}
