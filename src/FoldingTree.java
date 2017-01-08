import java.util.ArrayList;
import java.lang.Math;

//A folding tree is a tree that represents all possible foldings.
class FoldingTree
{
  int score = 0;
  ArrayList<Node> children = new ArrayList<Node>();

  public void extend(char hp)
  //Adds a new char to all of the subnodes of the folding tree.
  {
    if(this.children.size()==0)
    //If the node has no child node - which means we are working on an empty tree - add the char as root.
    {
      this.children.add(new Node(hp, new Coordinate(0,0), this));
    } else {
      //Else, add it to all subnodes.
      for(Node n : this.children) { n.extend(hp); }
    }
  }

  public void extend(String protein)
  {
    //Iteratively adds all chars in a string to the folding tree.
    int n = protein.length();
    for(int i=0; i<n; i++)
    {
      this.extend(protein.charAt(i));
    }
  }

  public boolean notUsed(int x, int y)
  //No coordinate in an empty tree.
  {
    return true;
  }

  public void buildFoldingScores()
  //Building the folding score in all nodes, recursively.
  {
    for(Node n : this.children)
    {
      n.buildFoldingScores();
    }
  }

  public boolean hasHAround(Coordinate coord)
  {
    return false;
  }

  public void display() { return; } //Nothing to display in an empty tree.
}

class EmptyFoldingTree extends FoldingTree
//An "empty" class to represent an empty tree.
{
  public EmptyFoldingTree()
  {
    this.score = 0;
    this.children = new ArrayList<Node>();
  }
}

class Node extends FoldingTree
{
  char letter;
  Coordinate coord;
  FoldingTree parentNode;
  //We constantly keep track of the parent node of each node.

  public Node(char letter, Coordinate coord, FoldingTree parentNode)
  {
    this.letter = letter;
    this.coord = coord;
    this.parentNode = parentNode;
    this.children = new ArrayList<Node>();
  }

  //Returns true if a coordinate is already in use in the current branch.
  public boolean notUsed(int x, int y)
  {
    if(this.coord.x == x && this.coord.y == y) { return false; }
    if(this.parentNode.notUsed(x, y) == false) { return false; }
    return true;
  }

  //Adding all possible foldings to all ending nodes in the current branch.
  public void extend(char hp)
  {
    if(this.children.size()>0) { for(Node n : this.children) { n.extend(hp); } return; }
    int x = this.coord.x; int y = this.coord.y;
    if(this.parentNode.notUsed(x-1,y)) { this.children.add(new Node(hp, new Coordinate(x-1,y), this)); }
    if(this.parentNode.notUsed(x,y-1)) { this.children.add(new Node(hp, new Coordinate(x,y-1), this)); }
    if(this.parentNode.notUsed(x,y+1)) { this.children.add(new Node(hp, new Coordinate(x,y+1), this)); }
    if(this.parentNode.notUsed(x+1,y)) { this.children.add(new Node(hp, new Coordinate(x+1,y), this)); }
  }

  //Returns true if coord has an H around in the current branch.
  public boolean hasHAround(Coordinate coord)
  {
    if(this.letter == 'H' && this.coord.around(coord)) { return true; }
    return this.parentNode.hasHAround(coord);
  }

  public void buildFoldingScores()
  {
    //Building the cumulated folding scores in each node. We need to check whether the curent node's letter is an H and has an H around which is not in its parent node.
    //By going downwards through the tree, we ensure that we do not count any H-pair twice.
    if((this.letter == 'H') && (this.parentNode instanceof Node) && (((Node)this.parentNode).parentNode.hasHAround(this.coord)))
    {
      this.score = this.parentNode.score + 1;
    } else {
      this.score = this.parentNode.score;
    }
    for(Node n : this.children) { n.buildFoldingScores(); }
  }

  public void display()
  {
    //Displays a folding starting from its end node.
    System.out.println(this.letter + " " + this.coord.toString());
    this.parentNode.display();
  }
}

class Coordinate
{
  int x = 0;
  int y = 0;

  public boolean around(Coordinate coord)
  //Returns true if coord is around the current coordinate.
  {
    if((Math.abs(coord.x-this.x)<=1 && coord.y==this.y) || ((Math.abs(coord.y-this.y)<=1 && coord.x==this.x))) { return true; }
    return false;
  }

  Coordinate(int x, int y)
  {
    this.x = x; this.y = y;
  }

  public String toString()
  {
    return "(" + this.x + "," + this.y + ")";
  }
}
