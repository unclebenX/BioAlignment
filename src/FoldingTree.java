import java.util.ArrayList;
import java.lang.Math;

class FoldingTree
{
  int score = 0;
  ArrayList<Node> children = new ArrayList<Node>();

  public void extend(char hp)
  {
    if(this.children.size()==0)
    {
      this.children.add(new Node(hp, new Coordinate(0,0), this));
    } else {
      for(Node n : this.children) { n.extend(hp); }
    }
  }

  public void extend(String protein)
  {
    int n = protein.length();
    for(int i=0; i<n; i++)
    {
      this.extend(protein.charAt(i));
    }
  }

  public boolean notUsed(int x, int y)
  {
    return true;
  }

  public void buildFoldingScores()
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

  public void display() { return; }
}

class EmptyFoldingTree extends FoldingTree
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

  public Node(char letter, Coordinate coord, FoldingTree parentNode)
  {
    this.letter = letter;
    this.coord = coord;
    this.parentNode = parentNode;
    this.children = new ArrayList<Node>();
  }

  public boolean notUsed(int x, int y)
  {
    if(this.coord.x == x && this.coord.y == y) { return false; }
    if(this.parentNode.notUsed(x, y) == false) { return false; }
    return true;
  }

  public void extend(char hp)
  {
    if(this.children.size()>0) { for(Node n : this.children) { n.extend(hp); } return; }
    int x = this.coord.x; int y = this.coord.y;
    if(this.parentNode.notUsed(x-1,y)) { this.children.add(new Node(hp, new Coordinate(x-1,y), this)); }
    if(this.parentNode.notUsed(x,y-1)) { this.children.add(new Node(hp, new Coordinate(x,y-1), this)); }
    if(this.parentNode.notUsed(x,y+1)) { this.children.add(new Node(hp, new Coordinate(x,y+1), this)); }
    if(this.parentNode.notUsed(x+1,y)) { this.children.add(new Node(hp, new Coordinate(x+1,y), this)); }
  }

  public boolean hasHAround(Coordinate coord)
  {
    if(this.letter == 'H' && this.coord.around(coord)) { return true; }
    return this.parentNode.hasHAround(coord);
  }

  public void buildFoldingScores()
  {
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
    System.out.println(this.letter + " " + this.coord.toString());
    this.parentNode.display();
  }
}

class Coordinate
{
  int x = 0;
  int y = 0;

  public boolean around(Coordinate coord)
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
