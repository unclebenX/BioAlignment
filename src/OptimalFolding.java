import java.util.ArrayList;

//This class is used to iterate on folding trees in order to find the foldings with the highest score.
class OptimalFoldingIterator
{
  //These variables are used to store the current maximum score and its corresponding node.
  public FoldingTree bestNode;
  public int bestScore;
  public int proteinLength;

  public OptimalFoldingIterator(String protein)
  {
    this.bestScore = -1;
    this.bestNode = new EmptyFoldingTree();
    this.proteinLength = protein.length();
  }

  //We use a recursive function to find the highest score in the tree.
  public FoldingTree getBestScoreNode(FoldingTree ft)
  {
    getBestScoreNodeAux(ft, 0);
    return this.bestNode;
  }

  public void getBestScoreNodeAux(FoldingTree ft, int depth)
  {
    if(ft.children.size()==0 && depth == this.proteinLength && ft.score > bestScore) { this.bestNode = ft; this.bestScore = ft.score; return; }
    for(Node n : ft.children) { this.getBestScoreNodeAux(n, depth+1); }
  }
}

class OptimalFolding
{
  public static void main(String[] args)
  {
    System.out.println("Optimal score: " + getBestFoldingScore(args[0]));
  }

  public static int getBestFoldingScore(String protein)
  {
    //Building a folding tree.
    FoldingTree ft = new FoldingTree();
    //Adding our protein to the folding tree.
    ft.extend(protein);
    //Building the scores.
    ft.buildFoldingScores();
    //Getting the optimal folding and returning it.
    OptimalFoldingIterator ofi = new OptimalFoldingIterator(protein);
    FoldingTree bestNode = ofi.getBestScoreNode(ft);
    bestNode.display();
    return bestNode.score;
  }
}
