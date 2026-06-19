<h1>TPE Especial: Benjamin Herrera y Felicitas Giacomaso</h1>

Este proyecto resuelve el problema de asignación de paquetes en una flota de camiones y todos los servicios requeridos de forma eficiente, con codigo legible, sin redundancia y respetando el encapsulamiento



<h1>Servicios:</h1>



<h2>Búsquedas instantáneas:</h2> Usamos un HashMap para buscar paquetes por código. Esto nos da un acceso directo sin tener que recorrer listas.



<h2>Filtros pre-calculados:</h2> En lugar de iterar para filtrar paquetes (por ejemplo, si tienen alimentos o por su nivel de urgencia), el sistema los clasifica en listas separadas y en arreglos indexados durante la lectura del archivo. Así, cuando un servicio pide un dato, solo copiamos lo necesario y le ahorramos trabajo al procesador.





<h1>Asignacion:</h1> (justificaciones de complejidad ampliadas en comentarios del codigo)

<h2>Greedy</h2>

Ordenamos los paquetes de mayor a menor peso para que primero se asignen los paquetes mas pesados asi minimizar el peso no asignado restante. Los camiones se ordenan mandando los que no tienen refrigeración al principio asi minimizamos las chances de que un paquete alimento no se pueda agregar por falta de camiones refrigerados, si no hicieramos esto, habria mas chances de que un paquete no alimento se agregue a un camion refrigerado, dejando a otros paquetes con alimentos sin asignar a ningun camion.



<h2>Backtracking</h2>

Se prueba por cada paquete asignarlo a todos los camiones posibles, y tambien no asignarlo a ninguno, de esta forma exploramos todas las soluciones posibles y damos con la mejor. Se implementa una poda que no permite continuar si el peso no asignado actual es mayor a nuestro mejor peso no asignado.



<h2>Resumen de Complejidades Temporales</h2>

Servicio 1 (Búsqueda por código): O(1) gracias al HashMap.



Servicio 2 (Filtro de alimentos): O(K), donde K es el tamaño de la lista pre-filtrada que se copia.



Servicio 3 (Filtro por urgencia): O(R + K), donde R es el rango solicitado y K los paquetes encontrados en esos indices.



Greedy: O(N log N + M log M + N * M). El costo principal es ordenar las listas y luego el bucle de asignacion.



Backtracking: En el peor caso teórico es O(M^N), pero en la practica el tiempo se reduce drásticamente por las podas de capacidad y restricciones.
