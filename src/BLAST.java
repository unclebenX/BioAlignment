import java.util.*;
import java.lang.Math;
import java.util.ArrayList;

class BLAST
{
  public static int k = 4;

  public static void main(String[] args)
  {
    if(args.length != 4) { System.out.println("Syntax: BLAST g t th thl - Please read readme.txt for more info."); return; }
    String g = args[0];
    String t = args[1];
    double th = Double.parseDouble(args[2]);
    double thl = Double.parseDouble(args[3]);
    localAlignments(g,t,th,thl);
  }

  public static int score(String s1, String s2)
  {
    int n = s1.length();
    int score = 0;
    for(int i=0; i<n; i++)
    {
      score += Blosum50.getScore(s1.charAt(i),s2.charAt(i));
    }
    return score;
  }

  public static ArrayList<Integer> perfectMatchings(String g, String t, double th)
  {
    KeywordTree kt = KeywordTree.buildFromSubwords(g,k);
    kt.buildSelfScores();
    ArrayList<Integer> perfectMatchings = kt.getBestAlignments(t,th,k);
    return perfectMatchings;
  }

  public static void localAlignments(String g, String t, double th, double thl)
  {
    ArrayList<Integer> perfectMatchings = perfectMatchings(g,t,th);
    int m = g.length(); int n = t.length();
    for(Integer i : perfectMatchings)
    {
      String correspondingSeed = t.substring(i,i+k);
      for(int j=0; j<=m-k; j++)
      {
        if(g.substring(j,j+k).equals(correspondingSeed))
        {
          int extension = 0;
          //Avoiding wrong indices ; we keep extending the alignment until the score stops increasing.
          while(i-extension > 0 && j-extension > 0 && i+k+extension < n-1 && j+k+extension < m-1)
          {
            //If not decreasing.
            int scoreIncrease = Blosum50.getScore(g.charAt(j-extension-1),t.charAt(i-extension-1))+Blosum50.getScore(g.charAt(j+k+extension+1),t.charAt(i+k+extension+1));
            if(scoreIncrease<0)
            {
              break;
            }
            extension++;
          }
          int alignmentScore = score(g.substring(j-extension,j+k+extension),t.substring(i-extension, i+k+extension));
          int selfScore = score(g.substring(j-extension,j+k+extension),t.substring(i-extension, i+k+extension));
          if(alignmentScore >= selfScore * thl)
          {
            System.out.println("Alignment: g[" + (j-extension) + "," + (j+k+extension)+"]: " + g.substring(j-extension, j+k+extension) + " - t[" + (i-extension) + "," + (i+k+extension) + "]: " + t.substring(i-extension, i+k+extension) + " - Score: " + alignmentScore);
          }
        }
      }
    }
  }
}
