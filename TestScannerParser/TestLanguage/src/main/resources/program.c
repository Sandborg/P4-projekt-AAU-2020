#include <stdio.h>
int SwapNumbers (int a,int b);
int SwapNumbers (int a,int b){
int temp = a;
a = b;
b = temp;
int lol = 9;
lol = a;
return 0; 

}

int main() { 
int a = 5;
int b = 10;
SwapNumbers(a,b); 
a = b+3+a;

return 0; 
}