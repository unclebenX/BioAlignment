import java.util.ArrayList;

class KeywordTree
{
  ArrayList<Node> root;

  public void display()
  {
    for(Node e : this.root)
    {
      System.out.println(e.letter);
      e.display();
    }
  }

  public ArrayList<Integer> getBestAlignments(String t, double th, int k)
  {
    ArrayList<Integer> perfectMatchings = new ArrayList<Integer>();
    for(int i=0; i<=t.length()-k; i++)
    {
      if(this.hasPerfect(t.substring(i, i+k), th))
      {
        perfectMatchings.add(i);
      }
    }
    return perfectMatchings;
  }

  public boolean hasPerfect(String t, double th)
  {
    for(Node node : this.root)
    {
      node.score = Blosum50.getScore(node.letter, t.charAt(0));
      if(node.hasPerfect(th, t.substring(1,t.length()))) { return true; }
    }
    return false;
  }

  public static KeywordTree buildFromSubwords(String s, int k)
  {
    KeywordTree kt = new KeywordTree();
    for(int i=0; i<=s.length()-k; i++)
    {
      kt.add(s.substring(i,i+k));
    }
    return kt;
  }

  public KeywordTree()
  {
    this.root = new ArrayList<Node>();
  }

  public KeywordTree(String s)
  {
    if(s.length()==0) { return; }
    this.root = new ArrayList<Node>();
    this.root.add(new Node(s));
  }

  public int has(char c)
  {
    for(Node n : this.root)
    {
      if(n.letter == c)
      {
        return this.root.indexOf(n);
      }
    }
    return -1;
  }

  public void add(String s)
  {
    if(s.length()==0)
    {
      return;
    }
    int index = this.has(s.charAt(0));
    if(index < 0)
    {
      Node n = new Node(s);
      this.root.add(n);
    } else {
      this.root.get(index).add(s.substring(1,s.length()));
    }
  }

  public void buildSelfScores()
  {
    for(Node n : this.root)
    {
      n.self_score = Blosum50.getScore(n.letter, n.letter);
      n.buildSelfScores();
    }
  }

  public class Nodeoid
  {
    int score = 0;
    int self_score = 0;
  }

  public class EmptyNode extends Nodeoid
  {
  }

  public class Node extends Nodeoid
  {
    public char letter;
    public ArrayList<Node> children;
    public Nodeoid parentNode;
    public int score = 0;
    public int self_score = 0;

    public Node(char letter, Nodeoid parent)
    {
      this.letter = letter;
      this.children = new ArrayList<Node>();
      this.parentNode = parent;
    }

    public void display()
    {
      for(Node n : this.children)
      {
        System.out.println(n.letter);
        n.display();
      }
    }

    public int has(char c)
    {
      for(Node n : this.children)
      {
        if(n.letter == c)
        {
          return this.children.indexOf(n);
        }
      }
      return -1;
    }

    public Node(String s)
    {
      int n = s.length();
      if(n==1)
      {
        this.letter = s.charAt(0);
        this.children = new ArrayList<Node>();
        this.parentNode = new EmptyNode();
        return;
      }
      Node subnode = new Node(s.substring(1,s.length()));
      subnode.parentNode = this;
      this.letter = s.charAt(0);
      this.children = new ArrayList<Node>();
      this.children.add(subnode);
    }

    public void buildSelfScores()
    {
      for(Node n : this.children)
      {
        n.self_score = this.self_score + Blosum50.getScore(n.letter, n.letter);
        n.buildSelfScores();
      }
    }

    public void add(String s)
    {
      if(s.length()==0)
      {
        return;
      }
      int index = this.has(s.charAt(0));
      if(index < 0)
      {
        Node n = new Node(s);
        n.parentNode = this;
        this.children.add(n);
      } else {
        this.children.get(index).add(s.substring(1,s.length()));
      }
    }

    public boolean hasPerfect(double th, String t)
    {
      if(this.children.size()==0) { return ((double)this.score >= ((double)this.self_score)*th); }
      for(Node node : this.children)
      {
        node.score = Blosum50.getScore(node.letter, t.charAt(0)) + this.score;
        if(node.hasPerfect(th, t.substring(1,t.length()))) { return true; }
      }
      return false;
    }
  }
}
