#include<stdio.h>
#include<stdlib.h>

#ifndef size
#define size 512
#endif

double *A, *B, *C;

void alloc() {
    A = (double *) malloc(size*size*sizeof(double));
    B = (double *) malloc(size*size*sizeof(double));
    C = (double *) malloc(size*size*sizeof(double));
}

// O = size^2
void init() {
    for(int i=0; i<size; i++) {
        for(int j=0; j<size; j++) {
            A[i*size+j] = rand();
            B[i*size+j] = rand();
        }
    }
}

void init_zero(){
    for(int i=0; i<size; i++) {
        for(int j=0; j<size; j++) {
            C[i*size+j] = 0;
        }
    }
}
// O = size^3
void mmult() {
    for(int i=0; i<size; i++) {
        for(int k=0; k<size; k++) {
            for(int j=0; j<size; j++) {
                C[i*size+j] += A[i*size+k] * B[k*size+j];
            }
        }
    }
}


int main() {
    alloc();
    init();
    init_zero();
    mmult();
    printf("%f\n", C[size/2+5]);
}