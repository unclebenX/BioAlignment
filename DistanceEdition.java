import java.util.*;
import java.lang.Math;
import java.util.Arrays;

class Alignment
{
  public static void main(String[] args)
  {
    String s1 = args[0];
    String s2 = args[1];
    minimalEdits(s1, s2);
  }

  public static String hyphenize(String s, int i)
  {
    System.out.println("PROUT");
    String s1 = s.substring(0,i) + "-" + s.substring(i,s.length());
    return s1;
  }

  public static int[][] editDistance(String s1, String s2)
  {
      int m = s1.length();
      int n = s2.length();
      int[][] tab = new int[m+1][n+1];
      /* Prefilling tab */
      for (int i=0; i<=m; i++) { Arrays.fill(tab[i], -1); };
      for (int i=0; i<=m; i++) { tab[i][0] = i; }
      for (int j=0; j<=n; j++) { tab[0][j] = j; }
      /* Computing the edit distance */
      for (int i=1; i<=m; i++)
      {
        for (int j=1; j<=n; j++)
          {
            tab[i][j] = Math.min(Math.min(tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1), s2.charAt(j-1)), tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1), '-')), tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1), '-'));
          }
      }
      return tab;
  }

  public static int[][] editDistanceOLD(String s1, String s2)
  {
      int m = s1.length();
      int n = s2.length();
      int[][] tab = new int[m+1][n+1];
      /* Prefilling tab */
      for (int i=0; i<=m; i++) { Arrays.fill(tab[i], -1); };
      for (int i=0; i<=m; i++) { tab[i][0] = i; }
      for (int j=0; j<=n; j++) { tab[0][j] = j; }
      /* Computing the edit distance */
      for (int i=1; i<=m; i++)
      {
        for (int j=1; j<=n; j++)
        {
          if(s1.charAt(i-1)==s2.charAt(j-1))
          {
            tab[i][j] = Math.min(Math.min(tab[i-1][j-1], tab[i-1][j] + 1), tab[i][j-1] + 1);
          } else {
            tab[i][j] = Math.min(Math.min(tab[i-1][j-1] + 1, tab[i-1][j] + 1), tab[i][j-1] + 1);
          }
        }
      }
      return tab;
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
      int[][] tab = editDistance(s1,s2);
      /* Building the edit sequence */
      int i = m;
      int j = n;
      dispMatrix(tab);
      while(i>0 || j>0)
      {
        System.out.println(Integer.toString(i)+" "+Integer.toString(j));
        if(i>0 && j>0 && tab[i][j]==tab[i-1][j-1]+Blosum50.getScore(s1.charAt(i-1), s2.charAt(j-1))) {i--; j--;}
        if(i>0 && tab[i][j]==tab[i-1][j]+Blosum50.getScore(s1.charAt(i-1), '-')) {s2x = hyphenize(s2x,j); i--;}
        if(j>0 && tab[i][j]==tab[i][j-1]+Blosum50.getScore(s2.charAt(j-1), '-')) {s1x = hyphenize(s1x,i-1); j--;}
      }
      System.out.println(s1x);
      System.out.println(s2x);
      return;
  }

  public static void minimalEditsOLD(String s1, String s2) {
      int m = s1.length();
      int n = s2.length();
      /* Copying both strings before editing them */
      String s1x = s1;
      String s2x = s2;
      /* Initializing the array */
      int[][] tab = editDistance(s1,s2);
      /* Building the edit sequence */
      int i = m;
      int j = n;
      dispMatrix(tab);
      while(i>0 || j>0)
      {
        System.out.println(Integer.toString(i)+" "+Integer.toString(j));
        if(i>0 && j>0 && tab[i][j]==tab[i-1][j-1]+1 && s1.charAt(i-1)!=s2.charAt(j-1)) {i--; j--;}
        if(i>0 && j>0 && tab[i][j]==tab[i-1][j-1] && s1.charAt(i-1)==s2.charAt(j-1)) {i--; j--;}
        if(i>0 && tab[i][j]==tab[i-1][j]+1) {s2x = hyphenize(s2x,j); i--;}
        if(j>0 && tab[i][j]==tab[i][j-1]+1) {s1x = hyphenize(s1x,i-1); j--;}
      }
      System.out.println(s1x);
      System.out.println(s2x);
      return;
  }
}
