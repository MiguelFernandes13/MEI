#include<stdio.h>
#include<stdlib.h>
#include<mpi.h>

#ifndef size
#define size 512
#endif

double *A, *B, *C;

void alloc() {
    A = (double *) malloc(size*size*sizeof(double));
    B = (double *) malloc(size*size*sizeof(double));
    C = (double *) malloc(size*size*sizeof(double));
}

void alloc_2(int nprocesses) {
    A = (double *) malloc((size / nprocesses)*size*sizeof(double));
    B = (double *) malloc(size*size*sizeof(double));
    C = (double *) malloc((size / nprocesses)*size*sizeof(double));
}

void init() {
    for(int i=0; i<size; i++) {
        for(int j=0; j<size; j++) {
            A[i*size+j] = rand();
            B[i*size+j] = rand();
        }
    }
}

void mmult(int nprocesses, int rank) {
    for(int i = rank * (size / nprocesses); i < (rank + 1) * (size / nprocesses); i++) {
        for(int j=0; j<size; j++) {
            C[i*size+j] = 0;
            for(int k=0; k<size; k++) {
                C[i*size+j] += A[i*size+k] * B[k*size+j];
            }
        }
    }
}

int main() {
    int nprocesses;
    int myrank;
    
    MPI_Init (&argc, &argv);
    
    MPI_Comm_size (MPI_COMM_WORLD, &nprocesses); // nproc
    MPI_Comm_rank (MPI_COMM_WORLD, &myrank);     // myid

    if(myrank == 0) {
        alloc();
        init();
        for(int i = 1; i < nprocesses; i++) {
            //scatter
            MPI_Send(A + (i * (size/nprocesses)), (size/nprocesses)*size, MPI_FLOAT, i, 0, MPI_COMM_WORLD);
            //broadcast
            MPI_Send(B, size*size, MPI_FLOAT, i, 0, MPI_COMM_WORLD);
            //scatter
            MPI_Send(C + (i * (size/nprocesses)), (size/nprocesses)*size, MPI_FLOAT, i, 0, MPI_COMM_WORLD);
        }
    }else{
        alloc_2(nprocesses);
        MPI_Recv(A, (size/nprocesses)*size, MPI_FLOAT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(B, size*size, MPI_FLOAT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(C, (size/nprocesses)*size, MPI_FLOAT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

    mmult(nprocesses, myrank);

    if(myrank == 0) {
        for(int i = 1; i < nprocesses; i++) {
            //gather
            MPI_Recv(C + (i * (size/nprocesses)), (size/nprocesses)*size, MPI_FLOAT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        printf("%f\n", C[size/2+5]);
    }else{
        MPI_Send(C * (myrank * (size/nprocesses)), (size/nprocesses)*size, MPI_FLOAT, 0, 0, MPI_COMM_WORLD);
    }

    MPI_Finalize ();
}