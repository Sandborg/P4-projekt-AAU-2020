#include <stdio.h>
int GetYourMom (int **hej,int med,int dig,int **lol);
int GetYourMom (int **hej,int med,int dig,int **lol){
int *medp = &med;
int *digp = &dig;
int hugo = 8;
int *hugop = &hugo;
*hugop = *medp;
*medp = **hej;
medp = *hej;
**lol =*digp;
*hej =digp;
return hej; 

}

int main() { 
int i = 3;
int *ip = &i;
int j = 8;
int *jp = &j;
int k = 9;
int *kp = &k;
int b = 5;
int *bp = &b;
GetYourMom(&ip,*jp,*kp,&bp); 

return 0; 
}