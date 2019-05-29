import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.lang.*;


public class MathExpression {
  public static String[] opa={"+","-","x","/"};
  public static String[] linearr;
  public static String[] temp1;
  public static String[] temp2;
  public static int tcount=0;
  public static int lcount=0;
  public static void main(String[] args) throws FileNotFoundException {
    
    Scanner sc=null;
    try{
      sc=new Scanner(new FileReader("C:\\Users\\jsc\\Desktop\\420Lab\\2\\Input.txt"));
    }
    catch(Exception e){
    }
    
    String line=sc.nextLine().trim();
    String[] arr=line.split("\\ |\\=");
    lcount++;
    int j=Integer.parseInt(arr[0]);
    temp1=new String[j];
    temp2=new String[j];
    for(int i=0;i<j;i++){
      line=sc.nextLine().trim();
      arr=line.split("\\ |\\=");
      temp1[tcount]=arr[0];
      temp2[tcount]=arr[3];
      tcount++;
      lcount++;
    }
    line=sc.nextLine().trim();
    arr=line.split("\\ |\\=");
    j=Integer.parseInt(arr[0]);
    linearr=new String[j];
    for(int i=0;i<j;i++){
      line=sc.nextLine().trim();
      line=line.replaceAll("\\s","");
      linearr[i]=line;
    }
    
    String[] p=postFix(linearr);
    expression(p);
  }
  
  
  public static String[] postFix (String[] a){
    String[] postfix=new String[a.length];
    for(int i=0;i<a.length;i++){
      String line =a[i];
      String[] stack;
      int sp=0;
      String[] output;
      int op=0;
      output=new String[line.length()];
      stack=new String[line.length()];
      for(int j=0;j<line.length();j++){
        if(!check(opa,line.charAt(j))){
          output[op]=Character.toString(line.charAt(j));
          op++;
        }
        else if(sp==0){
          stack[sp]=Character.toString(line.charAt(j));
          sp++;
        }
        
        else{
          
          int p1=indexOf(opa,stack[sp-1]);
          int p2=indexOf(opa,Character.toString(line.charAt(j)));
          if(p1%2!=1){
            p1++;
          }
          if(p2%2!=1){
            p2++;
          }
          if(p2>p1){
            stack[sp]=Character.toString(line.charAt(j));
            sp++;
          }
          else{
            
            output[op]=stack[sp-1];
            op++;
            sp--;
            stack[sp]=Character.toString(line.charAt(j));
            sp++;
          }
          if(sp>1){
            if(check(opa,stack[sp-1].charAt(0)) && check(opa,stack[sp-2].charAt(0))){
              p1=indexOf(opa,stack[sp-1]);
              p2=indexOf(opa,stack[sp-2]);
              if(p1%2!=1){
                p1++;
              }
              if(p2%2!=1){
                p2++;
              }
              if(p2>=p1){
                output[op]=stack[sp-2];
                op++;
                stack[sp-2]=stack[sp-1];
                sp--;
              }
            }
          }
        }
        if(j==(line.length()-1)){
          for(int k=0;k<sp;k++){
            //System.out.println(stack[sp-k]);
            output[op]=stack[sp-1-k];
            op++;
          }
        }
      }
      postfix[i]= arrToStr(output);
      //System.out.println(postfix[i]);
    }
    
    
    
    return postfix;
  }
  
  public static boolean check(String[] a,char s){
    for(int i=0;i<a.length;i++){
      if(Character.toString(s).equals(a[i])){
        return true;
      }
    }
    return false;
  }
  public static int indexOf(String[] a, String c) {
    int index = -1;
    for (int i = 0; i < a.length; ++i) {
      if (c.equals( a[i])) {
        index = i;
        break;
      }
    }
    return index;
  } 
  
  public static String arrToStr(String[] a){
    String s="";
    for(int i=0;i<a.length;i++){
      s=s+a[i];
    }
    return s;
  }
  
  public static void expression(String[] a){
    for(int i=0;i<a.length;i++){
      String line =a[i];
      String[] stack=new String[line.length()];
      int sp=0;
      for(int j=0;j<line.length();j++){
        
        if(!check(opa,line.charAt(j))){
          if(!check(temp1,line.charAt(j))){
            System.out.println("Compilation Error");
            break;
          }
          else{
            stack[sp]=temp2[indexOf(temp1,Character.toString(line.charAt(j)))];
            //System.out.println(stack[sp]);
            sp++;
          }
        }
        else{
          if(sp<2){
            System.out.println("error");
            break;
          }
          int y=Integer.parseInt(stack[sp-1]);
          sp--;
          int x=Integer.parseInt(stack[sp-1]);
          sp--;
          int result=0;
          char s=line.charAt(j);
          // System.out.println(s);
          switch(s){
            case '+': result=x+y;
            break;
            case '-': result=x-y;
            //System.out.println(x);
            //System.out.println(y);
            break;
            case 'x': result=x*y;
            break;
            case '/': result=x/y;
            break;
            
          }
          //System.out.println(result);
          stack[sp]=String.valueOf(result);
          if(j==line.length()-1){
            System.out.println(stack[sp]);
          }
          sp++;
          
          
        }
      }
    }
  }
}
