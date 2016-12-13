import java.util.*;
import java.lang.Math;
import java.util.Arrays;

class TabTuple
{
   int[][] tab;
   int[][] gap;

   public TabTuple(int[][] tab, int[][] gap)
   {
     this.tab = tab;
     this.gap = gap;
   }
}

class AffinePenalty
{
  static int OPENING_GAP_PENALTY = 10;
  static int INCREASING_GAP_PENALTY = 10;

  public static void main(String[] args)
  {
    String s1 = args[0];
    String s2 = args[1];
    minimalEdits(s1, s2);
    System.out.println(optLoc(s1,s2));
  }

  public static String hyphenize(String s, int i)
  {
    String s1 = s.substring(0,i) + "-" + s.substring(i,s.length());
    return s1;
  }

  public static TabTuple editDistance(String s1, String s2)
  {
      int m = s1.length();
      int n = s2.length();
      int[][] tab = new int[m+1][n+1];
      int[][] gap = new int[m+1][n+1];
      /* Prefilling tab */
      for (int i=0; i<=m; i++) { Arrays.fill(tab[i], -1); Arrays.fill(gap[i], 0);};
      tab[0][0] = 0;
      for (int i=1; i<=m; i++) { tab[i][0] = tab[i-1][0] + Blosum50.getScore(s1.charAt(i-1), '-'); }
      for (int j=1; j<=n; j++) { tab[0][j] = tab[0][j-1] + Blosum50.getScore('-', s1.charAt(j-1)); }
      /* Computing the edit distance */
      for (int i=1; i<=m; i++)
      {
        for (int j=1; j<=n; j++)
          {
            int val1 = tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1), s2.charAt(j-1));
            int val2 = tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1), '-');
            int val3 = tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1), '-');
            if (gap[i-1][j] > 0) { val2-=INCREASING_GAP_PENALTY; } else { val2-=OPENING_GAP_PENALTY; }
            if (gap[i][j-1] > 0) { val3-=INCREASING_GAP_PENALTY; } else { val3-=OPENING_GAP_PENALTY; }
            if (val2>val1||val3>val1) { if(gap[i][j]==0) { gap[i][j] = 1; } else { gap[i][j] = 2; } }
            tab[i][j] = Math.max(val1, Math.max(val2, val3));
          }
      }
      return new TabTuple(tab, gap);
  }

  public static void dispMatrix(int[][] tab)
  {
    for(int i=0; i<tab.length; i++)
    {
    System.out.println(Arrays.toString(tab[i]));
    }
    return;
  }

  public static void minimalEdits(String s1, String s2) {
      int m = s1.length();
      int n = s2.length();
      /* Copying both strings before editing them */
      String s1x = s1;
      String s2x = s2;
      /* Initializing the array */
      TabTuple ed = editDistance(s1,s2);
      int[][] tab = ed.tab;
      int[][] gap = ed.gap;
      /* Building the edit sequence */
      int i = m;
      int j = n;
      int gap_i = 0;
      int gap_j = 0;
      while(i>0 || j>0)
      {
        gap_i = 0;
        gap_j = 0;
        if(i>0)
        {
          if(gap[i][j]==2) { gap_i = INCREASING_GAP_PENALTY; }
          if(gap[i][j]==1) { gap_i = OPENING_GAP_PENALTY; }
        }
        if(j>0)
        {
          if(gap[i][j]==2) { gap_j = INCREASING_GAP_PENALTY; }
          if(gap[i][j]==1) { gap_j = OPENING_GAP_PENALTY; }
        }
        if(i>0 && j>0 && tab[i][j]==tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1), s2.charAt(j-1))) {i--; j--;}
        if(i>0 && tab[i][j]==tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1), '-')-gap_i) {s2x = hyphenize(s2x,j); i--;}
        if(j>0 && tab[i][j]==tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1), '-')-gap_j) {s1x = hyphenize(s1x,i-1); j--;}
      }
      System.out.println(s1x);
      System.out.println(s2x);
      System.out.println(tab[m][n]);
      return;
  }

  public static int optLoc(String s1, String s2)
  {
    int m = s1.length();
    int n = s2.length();
    int max = 0;
    int max_i, max_j, max_u, max_v;
    max_i = max_j=  max_u = max_v = 0;
    for(int i=0; i<m; i++)
    {
      for(int j=i; j<m; j++)
      {
        for(int u=0; u<n; u++)
        {
          for(int v=u; v<n; v++)
          {
            if(editDistance(s1.substring(i,j), s2.substring(u,v)).tab[j-i][v-u] > max)
            {
              max_i = i; max_j = j; max_u = u; max_v = v;
            }
          }
        }
      }
    }
    minimalEdits(s1.substring(max_i,max_j), s2.substring(max_u,max_v));
    return max;
  }

  public static int alignmentScore(String s1, String s2)
  {
    int n = s1.length();
    int score = 0;
    for(int i=0; i<n; i++)
    {
      score+=Blosum50.getScore(s1.charAt(i), s2.charAt(i));
    }
    return score;
  }
}
