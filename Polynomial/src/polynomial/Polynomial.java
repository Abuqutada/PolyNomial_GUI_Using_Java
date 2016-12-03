package polynomial;
import java.util.*;
import java.text.*;

public class Polynomial
{
   private Monomial head;
   private double TOLERANCE = 0.00000001;

 /*****   the  Monomial (inner) class      ********************************/

   private class Monomial
   {
      private DecimalFormat precision = new DecimalFormat("#.####");
      private int exp;                 // coeff * x^exp
      private double coeff;
      private Monomial next;

      public Monomial(int exp, double coeff, Monomial next)
      {
         this.exp = exp;
         this.coeff = coeff;
         this.next = next;
      }
      public String toString()
      {
         String form = precision.format(Math.abs(coeff));

         if(exp == 0) return form ;
         else
         if(exp == 1) return form + "*x";
         else
         return form +"*x^" + exp;
      }
      public boolean equals(Monomial mono)
      {
         return exp == mono.exp && coeff == mono.coeff;
      }

   }

   public Polynomial()
   {
      head = null;
   }
 public Polynomial(String poly)
   {
       head=null;
       ReadPoly(poly);
   }
  private void ReadPoly(String Poly)
        {
        String Char ="";
      StringTokenizer _s = new StringTokenizer(Poly,"+-",true);
      StringTokenizer _s2 =null;

            while (_s.hasMoreTokens()) {
                String next = _s.nextToken();
                if("-".equals(next))
                    Char=next;
                else if(next.contains("x^")){
                    _s2 = new StringTokenizer(next,"x^");
                    String Coeff =_s2.nextToken();
                    String power =_s2.countTokens()!=0 ?_s2.nextToken():null;

                    if("-".equals(Char) &&power!=null){
                        this.addTerm(-1*Double.parseDouble(Coeff),Integer.parseInt(power));
                         Char="";
                    }
                    else if(power !=null)
                        this.addTerm(Double.parseDouble(Coeff),Integer.parseInt(power));
                    else if("-".equals(Char) &&power==null){
                       this.addTerm(-1,Integer.parseInt(Coeff));
                         Char="";
                    }
                    else
                       this.addTerm(1,Integer.parseInt(Coeff));
                }
                 else if(next.contains("x")){
                    _s2 = new StringTokenizer(next,"x");
                    
                    String Coeff =_s2.nextToken();
                  if("x".equals(next)){
                        Coeff="1";}

                    String power =_s2.countTokens()!=0 ?_s2.nextToken():"1";

                    if("-".equals(Char) &&power!=null){
                        this.addTerm(-1*Double.parseDouble(Coeff),Integer.parseInt(power));
                         Char="";
                    }
                    else if(power !=null)
                        this.addTerm(Double.parseDouble(Coeff),Integer.parseInt(power));
                    else if("-".equals(Char) &&power==null){
                       this.addTerm(-1,Integer.parseInt(Coeff));
                         Char="";
                    }
                    else
                       this.addTerm(1.0,Integer.parseInt(Coeff));
                }
                else{
                       if("-".equals(Char)) {
                       this.addTerm(-1*Double.parseDouble(next),0);
                         Char="";
                    }
                       else if((!"+".equals(next))&&(!"-".equals(next)))
                       this.addTerm(Double.parseDouble(next),0);
                 }
                _s2=null;
                
                
            }
     
        }
   public void addTerm(double coeff, int exp)
   {
      if( Math.abs(coeff) < TOLERANCE ) return;

      if( head == null || exp > head.exp )
      {
         head = new Monomial(exp, coeff, head);
         return;
      }

      Monomial cur = head;
      Monomial prev = null;

      while( cur != null && exp < cur.exp)
      {
         prev = cur;
         cur = cur.next;
      }
      if( cur == null || exp != cur.exp )
			prev.next = new Monomial(exp, coeff, cur);
      else
      {
         cur.coeff += coeff;
         if( Math.abs(cur.coeff) < TOLERANCE )
           if(prev != null)
               prev.next = cur.next;
            else
               head = head.next;
      }
   }

   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer();

      for(Monomial tmp = head; tmp != null; tmp = tmp.next)
        if(tmp.coeff < 0 )
           sb.append(" - " + tmp.toString());
        else 
           sb.append(" + " + tmp.toString());
        
      
      return sb.toString();
   }

   public Polynomial add(Polynomial poly)
   {
      Polynomial res = clone();

      for(Monomial tmp = poly.head; tmp != null; tmp = tmp.next)
         res.addTerm(tmp.coeff, tmp.exp);

      return res;
   }
      private void Add(Polynomial poly)
   {

      for(Monomial tmp = poly.head; tmp != null; tmp = tmp.next)
         this.addTerm(tmp.coeff, tmp.exp);

   }

    public void Random()
   {
       Random _R = new Random();
       Random _R2 = new Random();
       Random _R3 = new Random();

       int limit = _R.nextInt(10);
       int Coeff = _R2.nextInt(50);
       int exp = _R3.nextInt(8);

for(int i=0;i<limit;i++){
     this.addTerm(Coeff, exp);
Coeff = _R2.nextInt(50);
    exp = _R3.nextInt(8);
}
   }

     public Polynomial minus(Polynomial poly)
   {
      Polynomial res = clone();

      for(Monomial tmp = poly.head; tmp != null; tmp = tmp.next)
         res.addTerm( -tmp.coeff, tmp.exp);

      return res;
   }

   public Polynomial clone()
   {
      Polynomial res = new Polynomial();

      for(Monomial tmp = head; tmp != null; tmp = tmp.next)
         res.addTerm(tmp.coeff , tmp.exp);

      return res;
   }
public void Edit(String x){
    Polynomial tempp= new Polynomial(x);
    boolean flag =false;
    Monomial _temp =tempp.head;
    while(_temp!=null){
    for(Monomial temp =head;temp!=null;temp=temp.next)
        if(_temp.exp==temp.exp){
            temp.coeff=_temp.coeff;
            flag=true;
            break;
        }
    _temp=_temp.next;}
    if(!flag){
       Add(tempp);
       flag=false;
    }
        
}
   public Polynomial multiply(double num)
   {
      Polynomial res = clone();

      for(Monomial tmp = res.head; tmp != null; tmp = tmp.next)
         tmp.coeff *= num;

      return res;
   }
   public Polynomial multiply(Polynomial poly){
       Monomial temp2 = poly.head;
       Monomial temp = this.head;
Polynomial res = new Polynomial();
while(temp!=null){
    while(temp2!=null){
        res.addTerm(temp.coeff*temp2.coeff,temp.exp+temp2.exp);
        temp2=temp2.next;
    }
        temp=temp.next;   
       }
       return res;
   }

   public static void main(String[] args)
   {
     Polynomial p1 = new Polynomial("x^2");
     Polynomial p2 = new Polynomial("2x^2");
    

       System.out.println(p1);

   

   }

}

