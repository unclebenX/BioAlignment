import java.util.*;
import java.lang.Math;
import java.util.Arrays;

class LCS
{
  public static void main(String[] args)
  {
    String s1 = args[0];
    String s2 = args[1];
    String ss = LCS(s1, s2);
    System.out.println(ss);
    System.out.println(ss.length());
  }

  public static String reverse(String input){
    char[] in = input.toCharArray();
    int begin=0;
    int end=in.length-1;
    char temp;
    while(end>begin){
        temp = in[begin];
        in[begin]=in[end];
        in[end] = temp;
        end--;
        begin++;
    }
    return new String(in);
}

  public static String readSubstring(String s1, String[][] origin)
  {
    int m = origin.length - 1;
    if(m==0) { return ""; }
    int n = origin[0].length - 1;
    String substring = "";
    int i = m; int j = n;
    while(i>0 && j>0)
    {
      if(origin[i][j] == "TopLeft") { substring+=s1.charAt(i-1); i--; j--; }
      if(origin[i][j] == "Left") { j--; }
      if(origin[i][j] == "Top") { i--; }
    }
    substring = reverse(substring);
    return substring;
  }

  public static String LCS(String s1, String s2)
  {
      int m = s1.length();
      int n = s2.length();
      int[][] tab = new int[m+1][n+1];
      String[][] origin = new String[m+1][n+1];
      /* Computing the edit distance */
      for (int i=1; i<=m; i++)
      {
        for (int j=1; j<=n; j++)
        {
          if(s1.charAt(i-1)==s2.charAt(j-1))
          {
            tab[i][j] = tab[i-1][j-1] + 1; origin[i][j] = "TopLeft";
          } else {
            if(tab[i-1][j]>=tab[i][j-1])
            {
              tab[i][j] = tab[i-1][j]; origin[i][j] = "Top";
            } else {
              tab[i][j] = tab[i][j-1]; origin[i][j] = "Left";
            }
          }
        }
      }
      String substring = readSubstring(s1, origin);
      return substring;
  }
}
