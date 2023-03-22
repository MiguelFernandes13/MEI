	.file	"matrix_ikj.c"
	.text
	.p2align 4
	.globl	alloc
	.type	alloc, @function
alloc:
.LFB39:
	.cfi_startproc
	endbr64
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	movl	$2097152, %edi
	call	malloc@PLT
	movl	$2097152, %edi
	movq	%rax, A(%rip)
	call	malloc@PLT
	movl	$2097152, %edi
	movq	%rax, B(%rip)
	call	malloc@PLT
	movq	%rax, C(%rip)
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE39:
	.size	alloc, .-alloc
	.p2align 4
	.globl	init
	.type	init, @function
init:
.LFB40:
	.cfi_startproc
	endbr64
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movl	$4096, %ebp
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	subq	$8, %rsp
	.cfi_def_cfa_offset 32
.L5:
	leaq	-4096(%rbp), %rbx
	.p2align 4,,10
	.p2align 3
.L6:
	call	rand@PLT
	pxor	%xmm0, %xmm0
	cvtsi2sdl	%eax, %xmm0
	movq	A(%rip), %rax
	movsd	%xmm0, (%rax,%rbx)
	call	rand@PLT
	pxor	%xmm0, %xmm0
	cvtsi2sdl	%eax, %xmm0
	movq	B(%rip), %rax
	movsd	%xmm0, (%rax,%rbx)
	addq	$8, %rbx
	cmpq	%rbp, %rbx
	jne	.L6
	leaq	4096(%rbx), %rbp
	cmpq	$2097152, %rbx
	jne	.L5
	addq	$8, %rsp
	.cfi_def_cfa_offset 24
	popq	%rbx
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE40:
	.size	init, .-init
	.p2align 4
	.globl	init_zero
	.type	init_zero, @function
init_zero:
.LFB41:
	.cfi_startproc
	endbr64
	movq	C(%rip), %rcx
	leaq	4096(%rcx), %rdx
	addq	$2101248, %rcx
.L11:
	leaq	-4096(%rdx), %rax
	.p2align 4,,10
	.p2align 3
.L12:
	movq	$0x000000000, (%rax)
	addq	$8, %rax
	cmpq	%rdx, %rax
	jne	.L12
	leaq	4096(%rax), %rdx
	cmpq	%rcx, %rdx
	jne	.L11
	ret
	.cfi_endproc
.LFE41:
	.size	init_zero, .-init_zero
	.p2align 4
	.globl	mmult
	.type	mmult, @function
mmult:
.LFB42:
	.cfi_startproc
	endbr64
	movq	C(%rip), %r8
	movq	A(%rip), %r11
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	pushq	%rbx
	.cfi_def_cfa_offset 24
	.cfi_offset 3, -24
	movq	B(%rip), %rbx
	leaq	4096(%r8), %r10
	leaq	2097152(%r8), %rbp
	movq	%r10, %rsi
	leaq	2097152(%rbx), %r9
.L16:
	movq	%rbx, %rdi
	movq	%r11, %rcx
.L20:
	movq	%rdi, %rdx
	movq	%r8, %rax
	.p2align 4,,10
	.p2align 3
.L17:
	movsd	(%rcx), %xmm0
	mulsd	(%rdx), %xmm0
	addq	$8, %rax
	addq	$8, %rdx
	addsd	-8(%rax), %xmm0
	movsd	%xmm0, -8(%rax)
	cmpq	%rax, %rsi
	jne	.L17
	addq	$4096, %rdi
	addq	$8, %rcx
	cmpq	%rdi, %r9
	jne	.L20
	movq	%r10, %r8
	addq	$4096, %rsi
	addq	$4096, %r11
	cmpq	%rbp, %r10
	je	.L15
	addq	$4096, %r10
	jmp	.L16
.L15:
	popq	%rbx
	.cfi_def_cfa_offset 16
	popq	%rbp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE42:
	.size	mmult, .-mmult
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC1:
	.string	"%f\n"
	.section	.text.startup,"ax",@progbits
	.p2align 4
	.globl	main
	.type	main, @function
main:
.LFB43:
	.cfi_startproc
	endbr64
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	xorl	%eax, %eax
	call	alloc
	xorl	%eax, %eax
	call	init
	movq	C(%rip), %rcx
	leaq	4096(%rcx), %rdx
	addq	$2101248, %rcx
.L24:
	leaq	-4096(%rdx), %rax
	.p2align 4,,10
	.p2align 3
.L25:
	movq	$0x000000000, (%rax)
	addq	$8, %rax
	cmpq	%rdx, %rax
	jne	.L25
	leaq	4096(%rax), %rdx
	cmpq	%rcx, %rdx
	jne	.L24
	xorl	%eax, %eax
	call	mmult
	movq	C(%rip), %rax
	movl	$1, %edi
	leaq	.LC1(%rip), %rsi
	movsd	2088(%rax), %xmm0
	movl	$1, %eax
	call	__printf_chk@PLT
	xorl	%eax, %eax
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE43:
	.size	main, .-main
	.comm	C,8,8
	.comm	B,8,8
	.comm	A,8,8
	.ident	"GCC: (Ubuntu 9.3.0-17ubuntu1~20.04) 9.3.0"
	.section	.note.GNU-stack,"",@progbits
	.section	.note.gnu.property,"a"
	.align 8
	.long	 1f - 0f
	.long	 4f - 1f
	.long	 5
0:
	.string	 "GNU"
1:
	.align 8
	.long	 0xc0000002
	.long	 3f - 2f
2:
	.long	 0x3
3:
	.align 8
4:
