import java.util.*;
import java.lang.Math;
import java.util.Arrays;

class BasicAlignment
{
  public static void main(String[] args)
  {
    //Getting the arguments.
    String s1 = args[0];
    String s2 = args[1];
    optimalAlignment(s1, s2);
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
          if(s1.charAt(i-1)==s2.charAt(j-1))
          {
            tab[i][j] = Math.max(Math.max(tab[i-1][j-1]+1, tab[i-1][j]), tab[i][j-1]);
            if(tab[i][j] == tab[i-1][j-1]+1) { origin[i][j] = "TopLeft"; }
            if(tab[i][j] == tab[i-1][j]) { origin[i][j] = "Top"; }
            if(tab[i][j] == tab[i][j-1]) { origin[i][j] = "Left"; }
          } else {
            tab[i][j] = Math.max(Math.max(tab[i-1][j-1], tab[i-1][j]), tab[i][j-1]);
            if(tab[i][j] == tab[i-1][j-1]) { origin[i][j] = "TopLeft"; }
            if(tab[i][j] == tab[i-1][j]) { origin[i][j] = "Top"; }
            if(tab[i][j] == tab[i][j-1]) { origin[i][j] = "Left"; }
          }
        }
      }
      //Displaying the alignment.
      dispAlignment(s1,s2,origin);
      return tab;
  }

  public static void dispAlignment(String s1, String s2, String[][] origin)
  {
    //Displays the optimal alignment by going back through the origin matrix.
    String s1x = ""; String s2x = "";
    int i = s1.length(); int j = s2.length();
    while(i>0 || j>0)
    {
      if(origin[i][j] == "TopLeft") { s1x = s1.charAt(i-1) + s1x; s2x = s2.charAt(j-1) + s2x; i--; j--; }
      if(origin[i][j] == "Top") { s1x = s1.charAt(i-1) + s1x; s2x = '-' + s2x; i--; }
      if(origin[i][j] == "Left") { s2x = s2.charAt(j-1) + s2x; s1x = '-' + s1x; j--; }
    }
    printAlignment(s1x, s2x);
    System.out.println("Alignment score: " + score(s1x,s2x));
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
      if(s1.charAt(i)==s2.charAt(i))
      {
        System.out.printf(ANSI_GREEN + s1.charAt(i) + ANSI_RESET + " ");
      } else {
        System.out.printf(s1.charAt(i) + " ");
      }
    }
    System.out.printf("%n");
    //Displaying the second string.
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

  public static int score(String s1, String s2)
  {
    //Calculating the score of an alignment.
    int n = s1.length();
    int score = 0;
    for(int i=0; i<n; i++)
    {
      if(s1.charAt(i)==s2.charAt(i)) { score++; }
    }
    return score;
  }
}
