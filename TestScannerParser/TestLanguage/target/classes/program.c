#include <stdio.h>
void hansi (int lol,int hej,int tommy);
void hansi (int lol,int hej,int tommy){
int *lolp = &lol;
int *hejp = &hej;
int *tommyp = &tommy;
*lolp =9;
**lol = **tommy;
*tommyp =56;
*hejp =*tommyp;

}

int main() { 
int hej = 6;
int *hejp = &hej;
int abe = 8;
int *abep = &abe;
int peter = 13;
int *peterp = &peter;
*peterp =*abep;
hansi(*abep,*hejp,*peterp); 

return 0; 
}