#!/bin/bash

#Probar los archivos en dimacs
base=/home/victor/ntfs/proyectos/proyecto1/src
javac *.java 2> /dev/null
cd $base/random
for i in $(ls)
do
  cd $base/random/$i
       for j in $(ls *.input)
       do
               nombre=$(echo $j | cut -d . -f 1)
               #nombre=#{j%.input}
               #echo $nombre
               cd $base
               touch ./random/$i/$nombre.salida
               java Main ./random/$i/$nombre.input  ./random/$i/$nombre.salida 2>> ./random/$i/$nombre.salida
               sort ./random/$i/$nombre.salida > ./random/$i/$nombre.salida.s
               sort ./random/$i/$nombre.output > ./random/$i/$nombre.output.s 
               if (diff ./random/$i/$nombre.salida.s ./random/$i/$nombre.output.s >> /dev/null)
               then
                       echo "Exito en el caso $nombre"
               else
                       echo "Error en el caso $nombre"
               fi
       done
done
cd $base/dimacs
for i in $(ls *.input)
do
       nombre=$(echo $i | cut -d . -f 1)
       #echo $nombre
       cd $base
       touch ./dimacs/$nombre.salida
       java Main ./dimacs/$nombre.input ./dimacs/$nombre.salida 2>> ./dimacs/$nombre.salida
       sort ./dimacs/$nombre.salida > ./dimacs/$nombre.salida.s
       sort ./dimacs/$nombre.output > ./dimacs/$nombre.output.s
       if (diff ./dimacs/$nombre.salida ./dimacs/$nombre.output >> /dev/null)
       then
               echo "Exito en el caso $nombre"
       else
               echo "Error en el caso $nombre"
       fi
done
