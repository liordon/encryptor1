#!/bin/bash
for i in {0..99} 
do
	content="shambalulu"
	for j in {1..1000} 
	do
		content=${content}"shambalulu "
	done
	echo $content > hello${i}.txt
done
