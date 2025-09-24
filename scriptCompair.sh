#!/bin/bash

for file in ./inputs/*.in; do
    base_name=$(basename "$file" .in)
    echo "Processing $base_name"
    
    first_letter=${base_name:0:1}
    ./judgeHashCode2018.out "$file" ./outputs/out_"$first_letter".txt 

done