#include <stdio.h>
int SwapNumbers (int a,int b);
int SwapNumbers (int a,int b){
int temp = a;
a = b;
b = temp;
return 0; 

}

int main() { 
int a = 5;
int b = 10;
SwapNumbers(a,b); 

return 0; 
}