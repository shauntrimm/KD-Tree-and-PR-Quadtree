# Shaun Trimm, C343 Project, KDTrees and PR Quadtrees
In this project I created an implementation for a KD Tree data structure, and a Point Region Quadtree data structure for storing longitude and latitude information for cities using the United States Cities database (Retrieving data from the [uscitiesv1 (version1).csv](http://homes.sice.indiana.edu/yye/c343-2019/uscitiesv1.4.csv) file from the C343 projects page and created my own .txt files for testing using that data. 
## How to Use
### Input format: 
The first line should correspond to the number of cities you are going to input. (ie. 15). Every subsequent line should contain the information for each city in the format:

```
CityName State Longitude Latitude
```

This should result in input similar to this:
```
5
City1 State1 1.0 1.0
City2 State2 2.0 2.0
City3 State3 3.0 3.0
City4 State4 4.0 4.0
City5 State5 5.0 5.0

```

The input file I used to test [(allindiana.txt)](https://github.com/shauntrimm/KD-Tree-and-PR-Quadtree/blob/master/Project/allindiana.txt) is available in this repository.

## The Differences between the KD Tree data structure and the PR Quadtree data structure.
The KDTree and PR Quadtree differ signifigantly, despite both being tree based.
- The KD Tree is essentialy structured like a Binary Search tree, given that:
  - Each node has at max 2 children 
  - The decision making process in terms of inserting and searching is very similar.
- The main difference between the KD Tree and and the Binary Search tree is that the KD Tree rotates between two different data fields to determine insertion and sorting.
  - In my example the two values rotated between are the longitude and latitude. The level of the tree that the program is on determines the value that is used to decide which side of the tree to continue iterating through/ inserting the value into.
- The PR Quadtree is a tree in which each node has 4 children (each can be either a leaf node or another PR Quadtree) that represent 4 quadrants (Northwest, Northeast, Southwest, Southeast). Depending on the x and y coordinates of each item put into the PR Quadtree, the quadrant is chosen, if a node is being put in a quadrant which already holds a node that quadrant splits into 4 seperate quadrants (a single node transforming into a new PR Quadtree) and puts the two nodes in their respective quadrants of the divided original quadrant. 

### Ease of Implementation
- In my opinion, the KD Tree is far easier to implement than the PR Quadtree. This is mainly because the KD Tree is essentially a Binary Search tree that rotates which variable it uses to search and insert at each level, as stated before. The only issue is figuring out how you're going to store the data for what level you're on when you're inserting (I solved this issue by passing another argument for the level of the tree in my insert() and contains() functions).
- The PR Quadtree however can be somewhat difficult to implement. There are issues that need to be solved before implementing such as:
  - How big should each region represented by the tree be?
  - How should I calculate and store these values?
 - After solving these, implementation begins, and while not being overly strenuous it can be unwieldy. If two points inserted into the tree are too close together (as can sometimes happen with cities) the insertion process can take a while and possibly cause a Stack Overflow error if the two points are incredibly close together.
 
### Space Efficiency
- One upside to the PR Quadtree is that data is only stored in leaf nodes, so compared to the KD Tree it is very space efficient.

### Time Efficiency
- Another area where the KD Tree appears to be more effective is time complexity of insertion and search. I have coded in statements to print out the time taken to search and insert around the function calls in my code and in all of the tests I did of the two data structures, the KD Tree appeared to take about 1/4 of the time as the PR Quadtree.
