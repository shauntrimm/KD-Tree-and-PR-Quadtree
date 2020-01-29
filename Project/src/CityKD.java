
public class CityKD {
	String cityName;
	String cityState;
	CityKD leftChild;
    CityKD rightChild;
	double xCoord; //Latitude
    double yCoord; //Longitude
    int depthInTree; //Used to determine which coordinate to compare to when searching/adding to tree
    
    public CityKD(String n, String s, double x, double y) {
    	this.setCityName(n);
    	this.setCityState(s);
    	this.setxCoord(x);
    	this.setyCoord(y);
    }
    
    
    @Override
	public String toString() {
		return cityName + ", " + cityState + "; Longitude: " + xCoord + " Latitude: " + yCoord;
	}


	public String getCityState() {
		return cityState;
	}


	public void setCityState(String cityState) {
		this.cityState = cityState;
	}
    
    
    public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public CityKD getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(CityKD leftChild) {
		this.leftChild = leftChild;
	}
	public CityKD getRightChild() {
		return rightChild;
	}
	public void setRightChild(CityKD rightChild) {
		this.rightChild = rightChild;
	}
	public double getxCoord() {
		return xCoord;
	}
	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}
	public double getyCoord() {
		return yCoord;
	}
	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}
	public int getDepthInTree() {
		return depthInTree;
	}
	public void setDepthInTree(int depthInTree) {
		this.depthInTree = depthInTree;
	}

}
