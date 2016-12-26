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

  public static KeywordTree buildFromSubwords(String s, int k)
  {
    KeywordTree kt = new KeywordTree();
    for(int i=0; i<s.length()-k; i++)
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

  public class Node
  {
    public char letter;
    public ArrayList<Node> children;

    public Node(char letter)
    {
      this.letter = letter;
      this.children = new ArrayList<Node>();
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
        return;
      }
      Node subnode = new Node(s.substring(1,s.length()));
      this.letter = s.charAt(0);
      this.children = new ArrayList<Node>();
      this.children.add(subnode);
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
        this.children.add(n);
      } else {
        this.children.get(index).add(s.substring(1,s.length()));
      }
    }
  }
}
