import java.util.ArrayList;

class OptimalFoldingIterator
{
  public FoldingTree bestNode;
  public int bestScore;
  public int proteinLength;

  public OptimalFoldingIterator(String protein)
  {
    this.bestScore = -1;
    this.bestNode = new EmptyFoldingTree();
    this.proteinLength = protein.length();
  }

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
    FoldingTree ft = new FoldingTree();
    ft.extend(protein);
    ft.buildFoldingScores();
    OptimalFoldingIterator ofi = new OptimalFoldingIterator(protein);
    FoldingTree bestNode = ofi.getBestScoreNode(ft);
    bestNode.display();
    return bestNode.score;
  }
}
