for i in $(seq 1 500)
do 
	echo -e "\nrun n. " $i
	java Simulation.Start
done
