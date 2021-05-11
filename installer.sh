#!/bin/bash
CURDIR=`pwd` 
gcc ${CURDIR}/Binary_Mask2/Mask2.c -o ${CURDIR}/Binary_Mask2/Mask2
echo "export PATH=${CURDIR}/Binary_Mask2:${PATH}" >> $HOME/.profile
