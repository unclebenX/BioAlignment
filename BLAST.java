import java.util.*;
import java.lang.Math;
import java.util.ArrayList;

class BLAST
{
  public static int k = 4;

  public static void main(String[] args)
  {
    perfectMatchings("ACTGCTA", "ATATA", 0.9);
  }

  public static int alignmentScore(String s1, String s2)
  {
    int n = Math.min(s1.length(), s2.length());
    int score = 0;
    for(int i=0; i<n; i++)
    {
      score += Blosum50.getScore(s1.charAt(i), s2.charAt(i));
    }
    return score;
  }

  public static ArrayList<Integer> perfectMatchings(String g, String t, double th)
  {
    ArrayList<Integer> perfectMatchings = new ArrayList<Integer>();
    KeywordTree kt = KeywordTree.buildFromSubwords(g,k);
    return perfectMatchings;
  }
}
