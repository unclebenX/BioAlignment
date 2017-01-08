import java.util.*;
import java.lang.Math;
import java.util.Arrays;

class AffinePenalty
{
  //Defining the penalties.
  static int OPENING_GAP_PENALTY;
  static int INCREASING_GAP_PENALTY;

  public static void main(String[] args)
  {
    //Getting the arguments.
    String s1 = args[0];
    String s2 = args[1];
    int OGP = Integer.parseInt(args[2]);
    int IGP = Integer.parseInt(args[3]);
    AffinePenalty.setGapPenalties(OGP, IGP);
    optimalAlignment(s1, s2);
  }

  //Setting the penalties using the arguments.
  public static void setGapPenalties(int OGP, int IGP)
  {
    AffinePenalty.OPENING_GAP_PENALTY = OGP;
    AffinePenalty.INCREASING_GAP_PENALTY = IGP;
    return;
  }

  public static void dispMatrix(String[][] tab)
  {
    //A simple yet useful function that displays a String matrix.
    for(int i=0; i<tab.length; i++)
    {
      System.out.println(Arrays.toString(tab[i]));
    }
    return;
  }

  public static int[][] optimalAlignment(String s1, String s2)
  {
      int m = s1.length();
      int n = s2.length();
      int[][] tab = new int[m+1][n+1];
      //inGap indicated whether or not we are in a gap while processing the optimal alignment.
      boolean[][] inGap = new boolean[m+1][n+1];
      String[][] origin = new String[m+1][n+1];
      /*Prefilling tab*/
      for(int i=1; i<=m; i++) { origin[i][0] = "Top"; }
      for(int j=1; j<=n; j++) { origin[0][j] = "Left"; }
      /* Computing the optimal alignments of subsequences */
      for (int i=1; i<=m; i++)
      {
        for (int j=1; j<=n; j++)
        {
          //Iteratively filling the tables, read the report for more info on this part and the equations used.
            int score1 = tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1),s2.charAt(j-1));
            int score2 = tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1),'-')+getPenalty(i-1,j,inGap);
            int score3 = tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1),'-')+getPenalty(i,j-1,inGap);
            tab[i][j] = Math.max(Math.max(score1,score2),score3);
            if(tab[i][j] == tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1),s2.charAt(j-1))) { origin[i][j] = "TopLeft"; }
            if(tab[i][j] == tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1),'-')+getPenalty(i-1,j,inGap))
            {
              origin[i][j] = "Top";
              if(getPenalty(i-1,j,inGap)<0) { inGap[i][j] = true; }
            }
            if(tab[i][j] == tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1),'-')+getPenalty(i,j-1,inGap))
            {
              origin[i][j] = "Left";
              if(getPenalty(i,j-1,inGap)<0) { inGap[i][j] = true; }
            }
        }
      }
      //Calling an auxiliary function to display the optimal alignment.
      dispAlignment(s1,s2,origin);
      //Getting the alignment score from tab[m][n]
      System.out.println("Alignment score: " + tab[m][n]);
      return tab;
  }

  public static int getPenalty(int i, int j, boolean[][] inGap)
  {
    //Returns the penalty we get at position i,j - are we opening or increasing a gap in the sequence?
    if(i==0 || j==0)
    {
      return 0;
    }
    if(inGap[i][j]==true)
    {
      return 0-INCREASING_GAP_PENALTY;
    } else {
      return 0-OPENING_GAP_PENALTY;
    }
  }

  public static void dispAlignment(String s1, String s2, String[][] origin)
  {
    //Displays the optimal alignment by going back through the matrices we have built.
    String s1x = ""; String s2x = "";
    int i = s1.length(); int j = s2.length();
    while(i>0 || j>0)
    {
      if(origin[i][j] == "TopLeft") { s1x = s1.charAt(i-1) + s1x; s2x = s2.charAt(j-1) + s2x; i--; j--; }
      if(origin[i][j] == "Top") { s1x = s1.charAt(i-1) + s1x; s2x = '-' + s2x; i--; }
      if(origin[i][j] == "Left") { s2x = s2.charAt(j-1) + s2x; s1x = '-' + s1x; j--; }
    }
    printAlignment(s1x, s2x);
  }

  public static void printAlignment(String s1, String s2)
  {
    //Displays an alignment in a fancy way - uses Unix ANSI codes that might not work on Windows.
    int n = s1.length();
    String ANSI_RESET = "\u001B[0m";
    String ANSI_GREEN = "\u001B[32m";
    //Displaying the first string.
    for(int i=0; i<n; i++)
    {
      //Checking whether we have a perfect alignment.
      if(s1.charAt(i)==s2.charAt(i))
      {
        System.out.printf(ANSI_GREEN + s1.charAt(i) + ANSI_RESET + " ");
      } else {
        System.out.printf(s1.charAt(i) + " ");
      }
    }
    //Displaying the second string.
    System.out.printf("%n");
    for(int i=0; i<n; i++)
    {
      if(s1.charAt(i)==s2.charAt(i))
      {
        System.out.printf(ANSI_GREEN + s2.charAt(i) + ANSI_RESET + " ");
      } else {
        System.out.printf(s2.charAt(i) + " ");
      }
    }
    System.out.printf("%n");
  }
}
